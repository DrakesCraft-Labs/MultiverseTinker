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
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
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

        // Durability wear
        damageEquipment(player, hand, meta, pdc);
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
                // Downward smash ground shockwave
                if (player.getFallDistance() > 1.2 || player.isSneaking()) {
                    double shockDamage = event.getDamage() * 0.65;
                    target.getWorld().spawnParticle(Particle.EXPLOSION, target.getLocation().add(0, 0.5, 0), 2, 0.2, 0.2, 0.2, 0.0);
                    target.getWorld().playSound(target.getLocation(), Sound.ITEM_MACE_SMASH_GROUND, 1.2f, 0.8f);

                    for (Entity nearby : target.getNearbyEntities(4.0, 2.0, 4.0)) {
                        if (nearby instanceof LivingEntity mob && !nearby.equals(player) && !nearby.equals(target)) {
                            mob.damage(shockDamage, player);
                            mob.setVelocity(mob.getLocation().toVector().subtract(target.getLocation().toVector()).normalize().multiply(0.6).setY(0.4));
                        }
                    }
                }
            }
            case SPEAR -> {
                // Jousting charge bonus
                if (player.isSprinting()) {
                    event.setDamage(event.getDamage() * 1.30);
                    target.setVelocity(player.getLocation().getDirection().multiply(0.8).setY(0.3));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.2f);
                    target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 15, 0.3, 0.3, 0.3, 0.1);
                }
            }
            case TRIDENT -> {
                // Oceanic / rain hydraulic surge
                if (target.isInWaterOrRain()) {
                    event.setDamage(event.getDamage() + 5.0);
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    target.getWorld().playSound(target.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8f, 1.4f);
                    target.getWorld().spawnParticle(Particle.SPLASH, target.getLocation().add(0, 1, 0), 25, 0.4, 0.4, 0.4, 0.1);
                }
            }
            case SWORD -> {
                // Sweeping elemental cleave
                for (Entity nearby : target.getNearbyEntities(2.5, 1.5, 2.5)) {
                    if (nearby instanceof LivingEntity mob && !nearby.equals(player) && !nearby.equals(target)) {
                        mob.damage(event.getDamage() * 0.4, player);
                        mob.getWorld().spawnParticle(Particle.SWEEP_ATTACK, mob.getLocation().add(0, 0.8, 0), 1);
                    }
                }
            }
            default -> {}
        }
    }

    private void handleSpecializedToolCombat(Player player, LivingEntity target, EntityDamageByEntityEvent event, String toolType) {
        if (toolType.equalsIgnoreCase(ModularToolType.AXE.name())) {
            // Shield breaker on crit
            if (player.getFallDistance() > 0.0) {
                event.setDamage(event.getDamage() * 1.25);
                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 0.9f);
                target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 10, 0.3, 0.3, 0.3, 0.1);
            }
        }
    }

    private void handleShieldBlock(Player victim, Entity damager, EntityDamageByEntityEvent event, ItemStack shield) {
        if (!(damager instanceof LivingEntity attacker)) return;

        // Retaliation: reflect 35% damage & push attacker
        double reflectedDamage = event.getDamage() * 0.35;
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
    }

    // ==========================================
    // BOW & PROJECTILE SHOOTING
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

        // Copy composition data to arrow
        String hComp = pdc.get(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING);
        String rComp = pdc.get(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING);
        if (hComp != null) projPdc.set(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING, hComp);
        if (rComp != null) projPdc.set(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING, rComp);

        projPdc.set(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE, (byte) 1);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(event.getHitEntity() instanceof LivingEntity target)) return;

        PersistentDataContainer pdc = arrow.getPersistentDataContainer();
        if (!pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE)) return;

        Player shooter = (arrow.getShooter() instanceof Player p) ? p : null;
        triggerMultiMaterialTraits(shooter, target, null, pdc);
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
        damageEquipment(player, hand, meta, pdc);

        // 3. Tool Evolution Progression (Blocks Broken)
        if (pdc.has(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE)) {
            progressToolEvolution(player, hand);
        }
    }

    private void handleSpecializedToolMining(Player player, Block block, String toolType) {
        // Pickaxe: Vein Resonance
        if (toolType.equalsIgnoreCase(ModularToolType.PICKAXE.name())) {
            if (block.getType().name().endsWith("_ORE")) {
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
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer == null) return;

        ItemStack hand = killer.getInventory().getItemInMainHand();
        if (!isModularEquipment(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE)) {
            progressWeaponEvolution(killer, hand);
        }
    }

    private void progressWeaponEvolution(Player player, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int kills = pdc.getOrDefault(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, 0) + 1;
        pdc.set(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, kills);

        String currentTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
        EvolutionTier currentTier = EvolutionTier.fromString(currentTierStr);
        EvolutionTier nextTier = currentTier.getNextTier();

        if (nextTier != null && kills >= nextTier.getKillRequirement()) {
            // Evolve Weapon!
            pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
            applyTierUpgrade(player, item, meta, pdc, nextTier, true);
        } else {
            item.setItemMeta(meta);
        }
    }

    private void progressToolEvolution(Player player, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int blocks = pdc.getOrDefault(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, 0) + 1;
        pdc.set(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, blocks);

        String currentTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
        EvolutionTier currentTier = EvolutionTier.fromString(currentTierStr);
        EvolutionTier nextTier = currentTier.getNextTier();

        if (nextTier != null && blocks >= nextTier.getBlockBreakRequirement()) {
            // Evolve Tool!
            pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
            applyTierUpgrade(player, item, meta, pdc, nextTier, false);
        } else {
            item.setItemMeta(meta);
        }
    }

    private void applyTierUpgrade(Player player, ItemStack item, ItemMeta meta, PersistentDataContainer pdc, EvolutionTier newTier, boolean isWeapon) {
        // Upgrade base material if applicable
        if (isWeapon) {
            String wType = pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING);
            if (wType != null && (wType.equalsIgnoreCase(ModularWeaponType.SWORD.name()) || wType.equalsIgnoreCase(ModularWeaponType.SPEAR.name()))) {
                item.setType(newTier.getMatchingSwordMaterial());
            }
        } else {
            String tType = pdc.get(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING);
            if (tType != null) {
                if (tType.equalsIgnoreCase(ModularToolType.PICKAXE.name())) item.setType(newTier.getMatchingPickaxeMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.AXE.name())) item.setType(newTier.getMatchingAxeMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.SHOVEL.name())) item.setType(newTier.getMatchingShovelMaterial());
                else if (tType.equalsIgnoreCase(ModularToolType.HOE.name())) item.setType(newTier.getMatchingHoeMaterial());
            }
        }

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

        item.setItemMeta(meta);
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

    private void damageEquipment(Player player, ItemStack item, ItemMeta meta, PersistentDataContainer pdc) {
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
        for (ItemStack piece : armor) {
            if (!isModularEquipment(piece)) continue;
            ItemMeta meta = piece.getItemMeta();
            if (meta == null) continue;
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            if (!pdc.has(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE)) continue;

            // Damage absorbed progress
            int absorbed = pdc.getOrDefault(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, 0) + (int) Math.max(1, damage);
            pdc.set(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, absorbed);

            // Trigger defensive traits on damager
            if (event.getDamager() instanceof LivingEntity damager) {
                triggerMultiMaterialTraits(player, damager, null, pdc);
            }

            // Check tier evolution
            String curTierStr = pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, EvolutionTier.WOOD.name());
            EvolutionTier curTier = EvolutionTier.fromString(curTierStr);
            EvolutionTier nextTier = curTier.getNextTier();
            if (nextTier != null && absorbed >= nextTier.getArmorDamageRequirement()) {
                pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, nextTier.name());
                applyArmorTierUpgrade(player, piece, meta, pdc, nextTier);
            } else {
                piece.setItemMeta(meta);
            }

            // Durability wear on armor piece
            damageEquipment(player, piece, meta, pdc);
        }
    }

    private void applyArmorTierUpgrade(Player player, ItemStack item, ItemMeta meta, PersistentDataContainer pdc, EvolutionTier newTier) {
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
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.2f, 1.1f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 40, 0.4, 0.4, 0.4, 0.15);

        Title title = Title.title(
                miniMessage.deserialize("<gradient:#9b59b6:#8e44ad><b>ARMOR EVOLUTION!</b></gradient>"),
                miniMessage.deserialize("<gray>Your armor evolved into </gray>" + newTier.getMiniMessageTag() + "<gray>!</gray>"),
                Title.Times.times(Duration.ofMillis(300), Duration.ofMillis(2500), Duration.ofMillis(600))
        );
        player.showTitle(title);
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
