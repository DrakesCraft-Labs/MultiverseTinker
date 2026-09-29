package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

public class ModularToolListener implements Listener {

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final Random random = new Random();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ModularToolListener(@Nonnull MultiverseTinker plugin, @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.materialRegistry = materialRegistry;
    }

    // ==========================================
    // COMBAT & ATTACK LISTENER
    // ==========================================
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent event) {
        // 1. Handle Shield Blocking & Retaliation
        if (event.getEntity() instanceof Player victim && victim.isBlocking()) {
            ItemStack shieldItem = victim.getInventory().getItemInOffHand();
            if (!isModularEquipment(shieldItem)) {
                shieldItem = victim.getInventory().getItemInMainHand();
            }
            if (isModularEquipment(shieldItem) && shieldItem.getType() == Material.SHIELD) {
                handleShieldBlock(victim, event.getDamager(), event, shieldItem);
            }
        }

        // 1.5 Handle Modular Armor defense & absorption
        if (event.getEntity() instanceof Player victimPlayer) {
            handleArmorDefensiveEffects(victimPlayer, event);
        }

        // 2. Handle Player Attacking
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String weaponTypeName = pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING);
        String toolTypeName = pdc.get(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING);

        // Specialized Weapon Perks
        if (weaponTypeName != null) {
            handleSpecializedWeaponCombat(player, target, event, weaponTypeName);
        }

        // Specialized Tool Perks (e.g. Axe shield disabler)
        if (toolTypeName != null) {
            handleSpecializedToolCombat(player, target, event, toolTypeName);
        }

        // Multi-Material Elemental Traits
        triggerMultiMaterialTraits(player, target, event, pdc);

        // Durability wear on attack FIRST (so it does not overwrite kill tracking)
        damageEquipment(player, hand);

        // Lethal hit check for instant kill progression
        if (pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE)) {
            NamespacedKey killKey = new NamespacedKey(plugin, "killed_by_mvtink");
            if (target.getHealth() - event.getFinalDamage() <= 0 && !target.getPersistentDataContainer().has(killKey, PersistentDataType.BYTE)) {
                target.getPersistentDataContainer().set(killKey, PersistentDataType.BYTE, (byte) 1);
                progressWeaponEvolution(player, player.getInventory().getItemInMainHand());
            }
        }
    }

    private void handleSpecializedWeaponCombat(Player player, LivingEntity target, EntityDamageByEntityEvent event, String weaponType) {
        ModularWeaponType type;
        try {
            type = ModularWeaponType.valueOf(weaponType);
        } catch (IllegalArgumentException e) {
            return;
        }

        switch (type) {
            case MACE -> {
                // Downward smash ground shockwave on jump-attack or sneak
                if (player.getFallDistance() > 0.5 || player.isSneaking()) {
                    double shockDamage = event.getDamage() * 0.65;
                    target.getWorld().spawnParticle(Particle.EXPLOSION, target.getLocation().add(0, 0.5, 0), 3, 0.3, 0.3, 0.3, 0.0);
                    target.getWorld().playSound(target.getLocation(), Sound.ITEM_MACE_SMASH_GROUND, 1.2f, 0.8f);

                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    PersistentDataContainer pdc = (meta != null) ? meta.getPersistentDataContainer() : null;

                    for (Entity nearby : target.getNearbyEntities(4.0, 2.0, 4.0)) {
                        if (nearby instanceof LivingEntity mob && !nearby.equals(player) && !nearby.equals(target)) {
                            mob.damage(shockDamage, player);
                            mob.setVelocity(mob.getLocation().toVector().subtract(target.getLocation().toVector()).normalize().multiply(0.6).setY(0.4));
                            if (pdc != null) {
                                triggerMultiMaterialTraits(player, mob, null, pdc);
                            }
                        }
                    }
                }
            }
            case SPEAR -> {
                // Jousting charge bonus on sprint or mounted
                if (player.isSprinting() || player.isInsideVehicle()) {
                    event.setDamage(event.getDamage() * 1.30);
                    target.setVelocity(player.getLocation().getDirection().multiply(0.85).setY(0.35));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.2f);
                    target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 15, 0.3, 0.3, 0.3, 0.1);
                }
            }
            case TRIDENT -> {
                // Oceanic / rain hydraulic surge
                if (target.isInWaterOrRain() || player.isInWaterOrRain()) {
                    event.setDamage(event.getDamage() + 5.0);
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    target.getWorld().playSound(target.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8f, 1.4f);
                    target.getWorld().spawnParticle(Particle.SPLASH, target.getLocation().add(0, 1, 0), 25, 0.4, 0.4, 0.4, 0.1);
                }
            }
            case SWORD -> {
                // Sweeping elemental cleave
                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                PersistentDataContainer pdc = (meta != null) ? meta.getPersistentDataContainer() : null;

                for (Entity nearby : target.getNearbyEntities(2.5, 1.5, 2.5)) {
                    if (nearby instanceof LivingEntity mob && !nearby.equals(player) && !nearby.equals(target)) {
                        mob.damage(event.getDamage() * 0.4, player);
                        mob.getWorld().spawnParticle(Particle.SWEEP_ATTACK, mob.getLocation().add(0, 0.8, 0), 1);
                        if (pdc != null) {
                            triggerMultiMaterialTraits(player, mob, null, pdc);
                        }
                    }
                }
            }
            default -> {}
        }
    }

    private void handleSpecializedToolCombat(Player player, LivingEntity target, EntityDamageByEntityEvent event, String toolType) {
        if (toolType.equalsIgnoreCase(ModularToolType.AXE.name())) {
            // Shield breaker on jump crit or fall
            if (player.getFallDistance() > 0.0) {
                event.setDamage(event.getDamage() * 1.25);
                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 0.9f);
                target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 12, 0.3, 0.3, 0.3, 0.1);

                if (target instanceof Player targetPlayer && targetPlayer.isBlocking()) {
                    targetPlayer.setCooldown(Material.SHIELD, 100);
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.2f, 0.8f);
                }
            }
        }
    }

    private void handleShieldBlock(Player victim, Entity damager, EntityDamageByEntityEvent event, ItemStack shield) {
        LivingEntity attacker = null;
        if (damager instanceof LivingEntity le) {
            attacker = le;
        } else if (damager instanceof Projectile proj && proj.getShooter() instanceof LivingEntity le) {
            attacker = le;
        }
        if (attacker == null) return;

        // Retaliation: reflect 35% damage & push attacker
        double reflectedDamage = Math.max(1.0, event.getDamage() * 0.35);
        attacker.damage(reflectedDamage, victim);
        attacker.setVelocity(victim.getLocation().getDirection().multiply(0.7).setY(0.2));

        victim.getWorld().playSound(victim.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1.2f, 1.1f);
        victim.getWorld().spawnParticle(Particle.EXPLOSION, victim.getLocation().add(0, 1, 0), 1);

        // Apply Shield plate & boss elemental traits to attacker
        ItemMeta meta = shield.getItemMeta();
        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            triggerMultiMaterialTraits(victim, attacker, event, pdc);
        }

        damageEquipment(victim, shield);
    }

    // ==========================================
    // BOW, CROSSBOW & PROJECTILE SHOOTING
    // ==========================================
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onShootBow(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (bow == null || !isModularEquipment(bow)) return;

        ItemMeta meta = bow.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        Entity proj = event.getProjectile();
        PersistentDataContainer projPdc = proj.getPersistentDataContainer();

        // Copy composition data and weapon type to projectile
        String hComp = pdc.get(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING);
        String rComp = pdc.get(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING);
        String bComp = pdc.get(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING);
        String wType = pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING);

        if (hComp != null) projPdc.set(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING, hComp);
        if (rComp != null) projPdc.set(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING, rComp);
        if (bComp != null) projPdc.set(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING, bComp);
        if (wType != null) projPdc.set(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING, wType);

        projPdc.set(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE, (byte) 1);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Trident trident)) return;
        if (!(trident.getShooter() instanceof Player player)) return;

        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand) || hand.getType() != Material.TRIDENT) {
            hand = player.getInventory().getItemInOffHand();
        }
        if (!isModularEquipment(hand) || hand.getType() != Material.TRIDENT) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        PersistentDataContainer projPdc = trident.getPersistentDataContainer();
        String hComp = pdc.get(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING);
        String rComp = pdc.get(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING);
        String bComp = pdc.get(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING);

        if (hComp != null) projPdc.set(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING, hComp);
        if (rComp != null) projPdc.set(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING, rComp);
        if (bComp != null) projPdc.set(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING, bComp);
        projPdc.set(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING, ModularWeaponType.TRIDENT.name());
        projPdc.set(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE, (byte) 1);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile proj = event.getEntity();
        PersistentDataContainer pdc = proj.getPersistentDataContainer();
        if (!pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE)) return;

        Player shooter = (proj.getShooter() instanceof Player p) ? p : null;
        String wType = pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING);
        Entity hitEntity = event.getHitEntity();
        Block hitBlock = event.getHitBlock();
        Location hitLoc = (hitEntity != null) ? hitEntity.getLocation()
                : (hitBlock != null ? hitBlock.getLocation().add(0.5, 0.5, 0.5) : proj.getLocation());

        // 1. CROSSBOW: Piercing Velocity & Kinetic Explosion
        if (wType != null && wType.equalsIgnoreCase(ModularWeaponType.CROSSBOW.name())) {
            World world = hitLoc.getWorld();
            if (world != null) {
                // Visual & sound explosion
                world.spawnParticle(Particle.EXPLOSION, hitLoc, 4, 0.4, 0.4, 0.4, 0.0);
                world.spawnParticle(Particle.CRIT, hitLoc, 20, 0.5, 0.5, 0.5, 0.2);
                world.playSound(hitLoc, Sound.ENTITY_GENERIC_EXPLODE, 1.2f, 1.3f);

                // Direct Armor-Penetrating Damage
                if (hitEntity instanceof LivingEntity directTarget) {
                    directTarget.damage(6.0, shooter);
                }

                // Blast wave damage to all nearby mobs in 4-block radius
                for (Entity nearby : world.getNearbyEntities(hitLoc, 4.0, 2.5, 4.0)) {
                    if (nearby instanceof LivingEntity mob && !nearby.equals(shooter) && !nearby.equals(hitEntity)) {
                        mob.damage(5.0, shooter);
                        mob.setVelocity(mob.getLocation().toVector().subtract(hitLoc.toVector()).normalize().multiply(0.6).setY(0.35));
                    }
                }
            }
        }

        // 2. TRIDENT: Hydraulic Surge on hit
        if (wType != null && wType.equalsIgnoreCase(ModularWeaponType.TRIDENT.name())) {
            World world = hitLoc.getWorld();
            if (world != null) {
                boolean inMoisture = (hitEntity != null && hitEntity.isInWaterOrRain()) || hitLoc.getBlock().isLiquid();
                if (inMoisture) {
                    world.strikeLightningEffect(hitLoc);
                    world.playSound(hitLoc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8f, 1.4f);
                    world.spawnParticle(Particle.SPLASH, hitLoc, 30, 0.5, 0.5, 0.5, 0.15);
                    if (hitEntity instanceof LivingEntity directTarget) {
                        directTarget.damage(5.0, shooter);
                    }
                }
            }
        }

        // 3. Multi-Material Elemental Traits on Direct Target
        if (hitEntity instanceof LivingEntity target) {
            triggerMultiMaterialTraits(shooter, target, null, pdc);
        }
    }

    // ==========================================
    // BLOCK MINING & TOOL PERKS
    // ==========================================
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String toolType = pdc.get(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING);
        Block block = event.getBlock();

        // 1. Specialized Tool Mining Perks
        if (toolType != null) {
            handleSpecializedToolMining(player, block, toolType);
        }

        // 2. Durability wear
        damageEquipment(player, hand);

        // 3. Tool Evolution Progression (Blocks Broken)
        if (pdc.has(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE)) {
            progressToolEvolution(player, player.getInventory().getItemInMainHand());
        }
    }

    private void handleSpecializedToolMining(Player player, Block block, String toolType) {
        // Pickaxe: Vein Resonance
        if (toolType.equalsIgnoreCase(ModularToolType.PICKAXE.name())) {
            if (block.getType().name().endsWith("_ORE") || block.getType() == Material.ANCIENT_DEBRIS) {
                if (random.nextDouble() < 0.15) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 120, 0, false, false));
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                    player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.4f);
                }
            }
        }

        // Shovel: 3x3 Seismic Tremor
        if (toolType.equalsIgnoreCase(ModularToolType.SHOVEL.name()) && player.isSneaking()) {
            if (isLooseBlock(block.getType())) {
                Material origMat = block.getType();
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && z == 0) continue;
                        Block relative = block.getRelative(x, 0, z);
                        if (relative.getType() == origMat) {
                            relative.breakNaturally(player.getInventory().getItemInMainHand());
                        }
                    }
                }
            }
        }

        // Hoe: 3x3 Crop Harvest & Replant
        if (toolType.equalsIgnoreCase(ModularToolType.HOE.name())) {
            if (block.getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()) {
                harvestAndReplant(player, block);
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && z == 0) continue;
                        Block relative = block.getRelative(x, 0, z);
                        if (relative.getBlockData() instanceof Ageable relAgeable && relAgeable.getAge() == relAgeable.getMaximumAge()) {
                            harvestAndReplant(player, relative);
                        }
                    }
                }
            }
        }
    }

    private void harvestAndReplant(Player player, Block block) {
        Material seed = switch (block.getType()) {
            case WHEAT -> Material.WHEAT_SEEDS;
            case CARROTS -> Material.CARROT;
            case POTATOES -> Material.POTATO;
            case BEETROOTS -> Material.BEETROOT_SEEDS;
            default -> null;
        };

        block.breakNaturally(player.getInventory().getItemInMainHand());

        if (seed != null && player.getInventory().contains(seed)) {
            player.getInventory().removeItem(new ItemStack(seed, 1));
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (block.getRelative(BlockFace.DOWN).getType() == Material.FARMLAND) {
                    block.setType(switch (seed) {
                        case WHEAT_SEEDS -> Material.WHEAT;
                        case CARROT -> Material.CARROTS;
                        case POTATO -> Material.POTATOES;
                        case BEETROOT_SEEDS -> Material.BEETROOTS;
                        default -> Material.AIR;
                    });
                }
            });
        }
    }

    private boolean isLooseBlock(Material mat) {
        return mat == Material.DIRT || mat == Material.SAND || mat == Material.GRAVEL ||
                mat == Material.CLAY || mat == Material.SOUL_SAND || mat == Material.SOUL_SOIL ||
                mat == Material.MUD || mat == Material.COARSE_DIRT || mat == Material.ROOTED_DIRT;
    }

    // ==========================================
    // FISHING ROD SPECIAL PERK
    // ==========================================
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand)) return;

        // Abyssal Dredge: 15% chance to hook rare raw mineral
        if (random.nextDouble() < 0.15) {
            List<TinkerMaterial> allMats = new ArrayList<>(materialRegistry.getAll());
            if (!allMats.isEmpty()) {
                TinkerMaterial chosen = allMats.get(random.nextInt(allMats.size()));
                ItemStack raw = plugin.getItemRegistry().getRawItem(chosen.getId());
                if (raw != null && event.getCaught() != null) {
                    event.getCaught().getWorld().dropItemNaturally(event.getCaught().getLocation(), raw);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.5f);
                    player.sendMessage(miniMessage.deserialize("<gradient:#00f2fe:#4facfe>✦ Abyssal Dredge hooked a submerged " + chosen.getName() + "!</gradient>"));
                }
            }
        }
    }

    // ==========================================
    // WEAPON EVOLUTION ON KILL
    // ==========================================
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ArmorStand) return;

        NamespacedKey killKey = new NamespacedKey(plugin, "killed_by_mvtink");
        if (entity.getPersistentDataContainer().has(killKey, PersistentDataType.BYTE)) {
            return; // Already counted via lethal combat damage
        }

        Player killer = entity.getKiller();
        if (killer == null && entity.getLastDamageCause() instanceof EntityDamageByEntityEvent edbe) {
            if (edbe.getDamager() instanceof Player p) {
                killer = p;
            } else if (edbe.getDamager() instanceof Arrow arr && arr.getShooter() instanceof Player p) {
                killer = p;
            } else if (edbe.getDamager() instanceof Trident tri && tri.getShooter() instanceof Player p) {
                killer = p;
            }
        }
        if (killer == null) return;

        ItemStack hand = killer.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE)) {
            entity.getPersistentDataContainer().set(killKey, PersistentDataType.BYTE, (byte) 1);
            progressWeaponEvolution(killer, hand);
        }
    }

    private void progressWeaponEvolution(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int kills = pdc.getOrDefault(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, 0) + 1;
        pdc.set(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, kills);
        item.setItemMeta(meta);

        String currentTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
        EvolutionTier currentTier = EvolutionTier.fromString(currentTierStr);
        EvolutionTier nextTier = currentTier.getNextTier();

        if (nextTier != null && kills >= nextTier.getKillRequirement()) {
            // Evolve Weapon!
            meta = item.getItemMeta();
            if (meta != null) {
                meta.getPersistentDataContainer().set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
                item.setItemMeta(meta);
            }
            applyTierUpgrade(player, item, nextTier, true);
        } else {
            TinkerItemBuilder.updateWeaponProgress(item, currentTier, kills);
        }

        player.getInventory().setItemInMainHand(item);
        player.updateInventory();

        // Action bar feedback
        if (nextTier != null) {
            int prevMilestone = currentTier.getKillRequirement();
            int needed = Math.max(1, nextTier.getKillRequirement() - prevMilestone);
            int curProgress = Math.max(0, kills - prevMilestone);
            player.sendActionBar(miniMessage.deserialize("<gradient:#ffd700:#ff8c00>⚔ Kill Count: </gradient><yellow>" + kills + "</yellow> <gray>(" + curProgress + "/" + needed + " to " + nextTier.getDisplayName() + ")</gray>"));
        } else {
            player.sendActionBar(miniMessage.deserialize("<gradient:#ffd700:#ff8c00>⚔ Master Tier: </gradient><yellow>" + kills + " Total Kills</yellow>"));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.8f);
    }

    private void progressToolEvolution(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int blocks = pdc.getOrDefault(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, 0) + 1;
        pdc.set(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, blocks);
        item.setItemMeta(meta);

        String currentTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
        EvolutionTier currentTier = EvolutionTier.fromString(currentTierStr);
        EvolutionTier nextTier = currentTier.getNextTier();

        if (nextTier != null && blocks >= nextTier.getBlockBreakRequirement()) {
            // Evolve Tool!
            meta = item.getItemMeta();
            if (meta != null) {
                meta.getPersistentDataContainer().set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
                item.setItemMeta(meta);
            }
            applyTierUpgrade(player, item, nextTier, false);
        } else {
            TinkerItemBuilder.updateToolProgress(item, currentTier, blocks);
        }

        player.getInventory().setItemInMainHand(item);
        player.updateInventory();

        // Action bar feedback
        if (nextTier != null) {
            int prevMilestone = currentTier.getBlockBreakRequirement();
            int needed = Math.max(1, nextTier.getBlockBreakRequirement() - prevMilestone);
            int curProgress = Math.max(0, blocks - prevMilestone);
            player.sendActionBar(miniMessage.deserialize("<gradient:#2ecc71:#27ae60>⛏ Block Mined: </gradient><yellow>" + blocks + "</yellow> <gray>(" + curProgress + "/" + needed + " to " + nextTier.getDisplayName() + ")</gray>"));
        } else {
            player.sendActionBar(miniMessage.deserialize("<gradient:#2ecc71:#27ae60>⛏ Master Tier: </gradient><yellow>" + blocks + " Total Blocks</yellow>"));
        }
    }

    private void applyTierUpgrade(Player player, ItemStack item, EvolutionTier newTier, boolean isWeapon) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Upgrade base material if applicable
        if (isWeapon) {
            String wType = pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING);
            if (wType != null) {
                if (wType.equalsIgnoreCase(ModularWeaponType.SWORD.name())) {
                    item.setType(newTier.getMatchingSwordMaterial());
                } else if (wType.equalsIgnoreCase(ModularWeaponType.SPEAR.name())) {
                    item.setType(newTier.getMatchingSpearMaterial());
                }
            }
            int kills = pdc.getOrDefault(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, 0);
            TinkerItemBuilder.updateWeaponProgress(item, newTier, kills);
        } else {
            String tType = pdc.get(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING);
            if (tType != null) {
                if (tType.equalsIgnoreCase(ModularToolType.PICKAXE.name())) item.setType(newTier.getMatchingPickaxeMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.AXE.name())) item.setType(newTier.getMatchingAxeMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.SHOVEL.name())) item.setType(newTier.getMatchingShovelMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.HOE.name())) item.setType(newTier.getMatchingHoeMaterial());
            }
            int blocks = pdc.getOrDefault(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, 0);
            TinkerItemBuilder.updateToolProgress(item, newTier, blocks);
        }

        player.getInventory().setItemInMainHand(item);
        player.updateInventory();

        // Play level-up sound, particle effects, and title
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.2f, 1.1f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 40, 0.4, 0.4, 0.4, 0.15);

        Title title = Title.title(
                miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>TIER EVOLUTION!</b></gradient>"),
                miniMessage.deserialize("<gray>Your equipment evolved into </gray>" + newTier.getMiniMessageTag() + "<gray>!</gray>"),
                Title.Times.times(Duration.ofMillis(300), Duration.ofMillis(2500), Duration.ofMillis(600))
        );
        player.showTitle(title);
    }

    // ==========================================
    // MULTI-MATERIAL TRAITS TRIGGER
    // ==========================================
    private void triggerMultiMaterialTraits(@Nullable Player player, @Nonnull LivingEntity target,
                                            @Nullable EntityDamageByEntityEvent event,
                                            @Nonnull PersistentDataContainer pdc) {
        List<String> rawCompositions = new ArrayList<>();
        addIfNotNull(pdc.get(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING), rawCompositions);
        addIfNotNull(pdc.get(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING), rawCompositions);
        addIfNotNull(pdc.get(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING), rawCompositions);
        addIfNotNull(pdc.get(TinkerKeys.ARMOR_PLATE_COMP, PersistentDataType.STRING), rawCompositions);
        addIfNotNull(pdc.get(TinkerKeys.ARMOR_LINING_COMP, PersistentDataType.STRING), rawCompositions);
        addIfNotNull(pdc.get(TinkerKeys.ARMOR_TRIM_COMP, PersistentDataType.STRING), rawCompositions);

        for (String raw : rawCompositions) {
            PartComposition comp = PartComposition.deserialize(raw, materialRegistry);
            if (comp == null) continue;

            for (PartComposition.Entry entry : comp.getEntries()) {
                String matId = entry.material().getId();
                double ratio = entry.ratio();

                // Multi-material concentration check: random chance or scaled effect!
                if (comp.triggersTrait(matId, random)) {
                    applyTraitEffect(player, target, event, matId, ratio);
                }
            }
        }
    }

    private void addIfNotNull(String s, List<String> list) {
        if (s != null) list.add(s);
    }

    private void applyTraitEffect(@Nullable Player player, @Nonnull LivingEntity target,
                                  @Nullable EntityDamageByEntityEvent event,
                                  @Nonnull String matId, double ratio) {
        // Gold - Midas Greed & Auric Strike (+30% attack damage!)
        if (matId.equalsIgnoreCase("mvtink_gold") && event != null) {
            event.setDamage(event.getDamage() * (1.0 + 0.30 * ratio));
            target.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, target.getLocation().add(0, 1, 0), 6, 0.2, 0.2, 0.2, 0.0);
        }

        // Ruby - Flame Edge
        if (matId.equalsIgnoreCase("mvtink_ruby")) {
            int ticks = (int) Math.round(80 * ratio);
            target.setFireTicks(Math.max(20, ticks));
            target.getWorld().spawnParticle(Particle.FLAME, target.getLocation().add(0, 1, 0), 8, 0.2, 0.2, 0.2, 0.05);
        }

        // Sapphire - Glacial Slowness
        if (matId.equalsIgnoreCase("mvtink_sapphire")) {
            int duration = (int) Math.round(60 * ratio);
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Math.max(20, duration), 1));
            target.getWorld().spawnParticle(Particle.SNOWFLAKE, target.getLocation().add(0, 1, 0), 10, 0.2, 0.2, 0.2, 0.02);
        }

        // Silver - Exorcism
        if (matId.equalsIgnoreCase("mvtink_silver") && isUndead(target) && event != null) {
            event.setDamage(event.getDamage() * (1.0 + 0.30 * ratio));
            target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 12, 0.3, 0.3, 0.3, 0.1);
        }

        // Borax - Thermal Flux / Flame & Fire Resistance
        if (matId.equalsIgnoreCase("mvtink_borax")) {
            target.setFireTicks((int) Math.max(30, 60 * ratio));
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (int) Math.max(40, 100 * ratio), 0));
            }
        }

        // Gypsum - Structural Lightness / Agile Momentum
        if (matId.equalsIgnoreCase("mvtink_gypsum") && player != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) Math.max(30, 80 * ratio), 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, (int) Math.max(30, 80 * ratio), 0));
        }

        // Titanium - Colossal Fortitude
        if (matId.equalsIgnoreCase("mvtink_titanium") && player != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) Math.max(40, 80 * ratio), 0));
        }

        // Stibnite - Sulfuric Thorns
        if (matId.equalsIgnoreCase("mvtink_stibnite") && event != null) {
            target.damage(2.0 * ratio);
            target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 8, 0.2, 0.2, 0.2, 0.1);
        }

        // Malachite - Toxic Patina
        if (matId.equalsIgnoreCase("mvtink_malachite")) {
            int duration = (int) Math.round(80 * ratio);
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Math.max(20, duration), 0));
        }

        // Witherite - Wither Decay
        if (matId.equalsIgnoreCase("mvtink_witherite")) {
            int duration = (int) Math.round(80 * ratio);
            target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Math.max(20, duration), 1));
        }

        // Sanguinite - Vampiric Drain
        if (matId.equalsIgnoreCase("mvtink_sanguinite") && player != null) {
            double heal = 1.5 * ratio;
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + heal));
            player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 1.5, 0), 2, 0.2, 0.2, 0.2, 0.0);
        }

        // Gravitite - Levitation
        if (matId.equalsIgnoreCase("mvtink_gravitite")) {
            int duration = (int) Math.round(30 * ratio);
            target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Math.max(10, duration), 1));
        }

        // Pyrite - Blinding Spark
        if (matId.equalsIgnoreCase("mvtink_pyrite")) {
            int duration = (int) Math.round(40 * ratio);
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.max(15, duration), 0));
        }

        // Manyullyn Alloy - Insatiable
        if (matId.equalsIgnoreCase("mvtink_manyullyn") && event != null) {
            event.setDamage(event.getDamage() + (3.0 * ratio));
            target.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, target.getLocation().add(0, 1, 0), 5, 0.2, 0.2, 0.2, 0.05);
        }

        // Cosmic Netherite Alloy - Cosmic Gravity
        if (matId.equalsIgnoreCase("mvtink_cosmic_netherite")) {
            for (Entity nearby : target.getNearbyEntities(6.0, 3.0, 6.0)) {
                if (nearby instanceof LivingEntity mob && !mob.equals(player) && !mob.equals(target)) {
                    mob.setVelocity(target.getLocation().toVector().subtract(mob.getLocation().toVector()).normalize().multiply(0.5));
                }
            }
        }
    }

    private void damageEquipment(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Adamantium: 75% chance to ignore wear
        String hComp = pdc.get(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING);
        if (hComp != null && hComp.contains("mvtink_adamantium") && random.nextDouble() < 0.75) {
            return;
        }

        int maxDur = pdc.getOrDefault(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, 1000);
        int curDur = pdc.getOrDefault(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, maxDur);

        curDur -= 1;
        if (curDur <= 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            player.spawnParticle(Particle.ITEM, player.getLocation().add(0, 1, 0), 15, 0.2, 0.2, 0.2, 0.1, item);
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            player.sendMessage(miniMessage.deserialize("<red>⚒ Your modular equipment shattered from durability fatigue!</red>"));
            return;
        }

        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, curDur);

        if (meta instanceof Damageable damageable) {
            int vanillaMax = item.getType().getMaxDurability();
            double damageRatio = 1.0 - ((double) curDur / maxDur);
            damageable.setDamage((int) (vanillaMax * damageRatio));
        }

        item.setItemMeta(meta);
        player.getInventory().setItemInMainHand(item);
    }

    private boolean isUndead(LivingEntity entity) {
        return switch (entity.getType()) {
            case ZOMBIE, ZOMBIE_VILLAGER, DROWNED, HUSK, SKELETON, STRAY, WITHER_SKELETON, PHANTOM, WITHER, ZOMBIFIED_PIGLIN, ZOGLIN -> true;
            default -> false;
        };
    }

    private void handleArmorDefensiveEffects(Player player, EntityDamageByEntityEvent event) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        double damage = event.getDamage();
        for (int i = 0; i < armor.length; i++) {
            ItemStack piece = armor[i];
            if (!isModularEquipment(piece)) continue;
            ItemMeta meta = piece.getItemMeta();
            if (meta == null) continue;
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            if (!pdc.has(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE)) continue;

            // Damage absorbed progress
            int absorbed = pdc.getOrDefault(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, 0) + (int) Math.max(1, damage);
            pdc.set(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, absorbed);
            piece.setItemMeta(meta);

            // Trigger defensive traits on damager
            if (event.getDamager() instanceof LivingEntity damager) {
                triggerMultiMaterialTraits(player, damager, event, pdc);
            }

            // Check tier evolution
            String curTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
            EvolutionTier curTier = EvolutionTier.fromString(curTierStr);
            EvolutionTier nextTier = curTier.getNextTier();
            if (nextTier != null && absorbed >= nextTier.getArmorDamageRequirement()) {
                pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
                piece.setItemMeta(meta);
                applyArmorTierUpgrade(player, piece, nextTier);
            } else {
                TinkerItemBuilder.updateArmorProgress(piece, curTier, absorbed);
            }

            // Action bar feedback
            if (nextTier != null) {
                int prevMilestone = curTier.getArmorDamageRequirement();
                int needed = Math.max(1, nextTier.getArmorDamageRequirement() - prevMilestone);
                int curProgress = Math.max(0, absorbed - prevMilestone);
                player.sendActionBar(miniMessage.deserialize("<gradient:#9b59b6:#8e44ad>🛡 Damage Absorbed: </gradient><yellow>" + absorbed + "</yellow> <gray>(" + curProgress + "/" + needed + " to " + nextTier.getArmorDisplayName() + ")</gray>"));
            } else {
                player.sendActionBar(miniMessage.deserialize("<gradient:#9b59b6:#8e44ad>🛡 Master Armor: </gradient><yellow>" + absorbed + " Total Absorbed</yellow>"));
            }

            // Durability wear on armor piece
            damageArmorPiece(player, piece, i);
        }
    }

    private void applyArmorTierUpgrade(Player player, ItemStack item, EvolutionTier newTier) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String aType = pdc.get(TinkerKeys.ARMOR_TYPE, PersistentDataType.STRING);
        if (aType != null) {
            try {
                ModularArmorType mat = ModularArmorType.valueOf(aType);
                switch (mat) {
                    case HELMET -> item.setType(newTier.getMatchingHelmetMaterial());
                    case CHESTPLATE -> item.setType(newTier.getMatchingChestplateMaterial());
                    case LEGGINGS -> item.setType(newTier.getMatchingLeggingsMaterial());
                    case BOOTS -> item.setType(newTier.getMatchingBootsMaterial());
                }
            } catch (IllegalArgumentException ignored) {}
        }

        // Apply copper/leather dye if leather-based tier
        meta = item.getItemMeta();
        if (meta instanceof LeatherArmorMeta lam) {
            if (newTier == EvolutionTier.STONE) {
                lam.setColor(Color.fromRGB(200, 100, 50));
            } else if (newTier == EvolutionTier.WOOD) {
                lam.setColor(Color.fromRGB(160, 101, 64));
            }
            item.setItemMeta(lam);
        }

        int absorbed = pdc.getOrDefault(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, 0);
        TinkerItemBuilder.updateArmorProgress(item, newTier, absorbed);

        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.2f, 1.1f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 40, 0.4, 0.4, 0.4, 0.15);

        Title title = Title.title(
                miniMessage.deserialize("<gradient:#9b59b6:#8e44ad><b>ARMOR EVOLUTION!</b></gradient>"),
                miniMessage.deserialize("<gray>Your armor evolved into </gray>" + newTier.getArmorMiniMessageTag() + "<gray>!</gray>"),
                Title.Times.times(Duration.ofMillis(300), Duration.ofMillis(2500), Duration.ofMillis(600))
        );
        player.showTitle(title);
    }

    private void damageArmorPiece(Player player, ItemStack item, int armorSlotIndex) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String pComp = pdc.get(TinkerKeys.ARMOR_PLATE_COMP, PersistentDataType.STRING);
        if (pComp != null && pComp.contains("mvtink_adamantium") && random.nextDouble() < 0.75) {
            return;
        }

        int maxDur = pdc.getOrDefault(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, 1000);
        int curDur = pdc.getOrDefault(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, maxDur);

        curDur -= 1;
        if (curDur <= 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            player.spawnParticle(Particle.ITEM, player.getLocation().add(0, 1, 0), 15, 0.2, 0.2, 0.2, 0.1, item);
            ItemStack[] currentArmor = player.getInventory().getArmorContents();
            currentArmor[armorSlotIndex] = new ItemStack(Material.AIR);
            player.getInventory().setArmorContents(currentArmor);
            player.sendMessage(miniMessage.deserialize("<red>🛡 Your modular armor piece shattered from durability fatigue!</red>"));
            return;
        }

        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, curDur);

        if (meta instanceof Damageable damageable) {
            int vanillaMax = item.getType().getMaxDurability();
            double damageRatio = 1.0 - ((double) curDur / maxDur);
            damageable.setDamage((int) (vanillaMax * damageRatio));
        }

        item.setItemMeta(meta);
    }

    private boolean isModularEquipment(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE) ||
                pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE) ||
                pdc.has(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE);
    }
}
