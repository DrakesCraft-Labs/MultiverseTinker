package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.compat.ServerCompat;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Universal material effect engine.
 *
 * <p>Guarantees that <b>every</b> mineral, vanilla ore and alloy produces a concrete, deterministic
 * effect for weapons (on hit), tools (on block break) and armor (on damage taken). Binary alloys
 * recursively expand into their two parent minerals so that each forge combination owns its own
 * signature blend of affinities.</p>
 */
public final class TraitEffectEngine {

    private TraitEffectEngine() {
    }

    // ==========================================
    // WEAPONS - ON HIT
    // ==========================================
    public static void applyWeaponAffinities(@Nullable Player player,
                                             @Nonnull LivingEntity target,
                                             @Nullable EntityDamageByEntityEvent event,
                                             @Nullable String matId,
                                             double ratio,
                                             @Nonnull MaterialRegistry registry) {
        if (matId == null || ratio <= 0.0) return;
        Random rnd = ThreadLocalRandom.current();

        for (Resolved resolved : resolve(matId, ratio, registry)) {
            for (TraitAffinity affinity : TraitAffinity.of(resolved.material())) {
                applyWeaponAffinity(affinity, player, target, event, resolved.ratio(), rnd);
            }
        }

        applyNamedAlloyWeapon(player, target, event, matId, ratio, rnd);
    }

    /**
     * Signature echo of a weapon's material-driven perk.
     *
     * <p>The dominant essence of the forged composition is channelled into the perk's primary
     * strike on top of the ordinary per-material affinities, scaled by how concentrated that
     * essence is ({@link WeaponPerkProfile#getPotency()}). This is what makes two weapons of the
     * same type but different minerals feel different in combat.</p>
     */
    public static void applyWeaponPerkEcho(@Nullable Player player,
                                           @Nonnull LivingEntity target,
                                           @Nullable EntityDamageByEntityEvent event,
                                           @Nonnull TraitAffinity affinity,
                                           double potency) {
        double ratio = Math.max(0.5, Math.min(1.5, 0.75 + potency));
        applyWeaponAffinity(affinity, player, target, event, ratio, ThreadLocalRandom.current());
    }

    private static void applyWeaponAffinity(@Nonnull TraitAffinity affinity,
                                            @Nullable Player player,
                                            @Nonnull LivingEntity target,
                                            @Nullable EntityDamageByEntityEvent event,
                                            double r,
                                            @Nonnull Random rnd) {
        World world = target.getWorld();
        Location at = target.getLocation().add(0, 1, 0);

        switch (affinity) {
            case TEMPERED -> {
                if (event != null) event.setDamage(event.getDamage() * (1.0 + 0.10 * r));
                world.spawnParticle(Particle.CRIT, at, 4, 0.2, 0.2, 0.2, 0.05);
            }
            case RADIANT -> {
                target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (int) Math.max(30, 60 * r), 0, false, false));
                world.spawnParticle(Particle.HAPPY_VILLAGER, at, 4, 0.2, 0.2, 0.2, 0.0);
            }
            case RESONANT -> {
                for (Entity nearby : target.getNearbyEntities(3.0, 2.0, 3.0)) {
                    if (nearby instanceof LivingEntity mob && !mob.equals(target) && !mob.equals(player)) {
                        mob.setNoDamageTicks(0);
                        mob.damage(2.0 * r, player);
                    }
                }
                world.playSound(target.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.9f, 1.5f);
                world.spawnParticle(Particle.NOTE, at, 5, 0.3, 0.3, 0.3, 0.1);
            }
            case VOLATILE -> {
                target.setFireTicks((int) Math.max(20, 40 * r));
                world.spawnParticle(Particle.FLAME, at, 6, 0.2, 0.2, 0.2, 0.02);
                world.playSound(target.getLocation(), Sound.ENTITY_GENERIC_BURN, 0.6f, 1.4f);
            }
            case TERRAIN -> {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) Math.max(20, 30 * r), 0, false, true));
                world.spawnParticle(Particle.BLOCK, at, 8, 0.25, 0.25, 0.25, 0.05, Material.STONE.createBlockData());
            }
            case INFERNAL -> {
                target.setFireTicks((int) Math.max(40, 80 * r));
                world.spawnParticle(Particle.SOUL_FIRE_FLAME, at, 6, 0.25, 0.25, 0.25, 0.03);
            }
            case VOID -> {
                Vector pull = (player != null)
                        ? player.getLocation().toVector().subtract(target.getLocation().toVector())
                        : target.getVelocity().multiply(-1);
                if (pull.lengthSquared() > 0.0001) {
                    target.setVelocity(pull.normalize().multiply(0.35 * r).setY(0.12));
                }
                world.spawnParticle(Particle.PORTAL, at, 10, 0.25, 0.25, 0.25, 0.1);
            }
            case PRIMAL -> {
                if (event != null) event.setDamage(event.getDamage() + (0.8 * r));
                world.spawnParticle(Particle.CRIT, at, 3, 0.2, 0.2, 0.2, 0.05);
            }
            case SWIFT -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, (int) Math.max(40, 80 * r), 0, false, false));
                world.spawnParticle(Particle.ELECTRIC_SPARK, at, 4, 0.2, 0.2, 0.2, 0.05);
            }
            case BRUTAL -> {
                if (event != null) event.setDamage(event.getDamage() + (2.0 * r));
                Vector away = (player != null)
                        ? target.getLocation().toVector().subtract(player.getLocation().toVector())
                        : target.getVelocity().multiply(-1);
                if (away.lengthSquared() > 0.0001) {
                    target.setVelocity(away.normalize().multiply(0.35 + 0.35 * r).setY(0.25));
                }
                world.spawnParticle(Particle.EXPLOSION, at, 1);
            }
            case BULWARK -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) Math.max(40, 80 * r), 0, false, false));
                world.spawnParticle(Particle.ENCHANTED_HIT, at, 6, 0.3, 0.3, 0.3, 0.05);
            }
            case ASCENDANT -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) Math.max(30, 50 * r), 0, false, false));
                world.spawnParticle(Particle.TOTEM_OF_UNDYING, at, 8, 0.3, 0.3, 0.3, 0.05);
            }
        }
    }

    // ==========================================
    // CURATED NAMED ALLOY WEAPON TRAITS
    // ==========================================
    private static void applyNamedAlloyWeapon(@Nullable Player player,
                                              @Nonnull LivingEntity target,
                                              @Nullable EntityDamageByEntityEvent event,
                                              @Nonnull String matId,
                                              double r,
                                              @Nonnull Random rnd) {
        World world = target.getWorld();
        Location at = target.getLocation().add(0, 1, 0);
        String id = matId.toLowerCase(Locale.ROOT);

        switch (id) {
            case "mvtink_bronze" -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) Math.max(40, 80 * r), 0, false, false));
                if (event != null) event.setDamage(event.getDamage() * (1.0 + 0.08 * r));
                world.spawnParticle(Particle.CRIT, at, 5, 0.2, 0.2, 0.2, 0.05);
            }
            case "mvtink_electrum" -> {
                if (rnd.nextDouble() < 0.25 * r) {
                    target.setNoDamageTicks(0);
                    target.damage(2.0 * r, player);
                    world.strikeLightningEffect(target.getLocation());
                    world.playSound(target.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.6f, 1.7f);
                    world.spawnParticle(Particle.ELECTRIC_SPARK, at, 12, 0.3, 0.3, 0.3, 0.1);
                }
            }
            case "mvtink_invar" -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (int) Math.max(60, 120 * r), 0, false, false));
                if (event != null) event.setDamage(event.getDamage() + (1.0 * r));
            }
            case "mvtink_rose_gold" -> {
                if (player != null) player.giveExp((int) Math.max(1, Math.round(3 * r)));
                world.spawnParticle(Particle.HAPPY_VILLAGER, at, 6, 0.25, 0.25, 0.25, 0.0);
            }
            case "mvtink_astral_brass" -> {
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, (int) Math.max(60, 120 * r), 0, false, false));
                world.spawnParticle(Particle.END_ROD, at, 8, 0.3, 0.3, 0.3, 0.02);
            }
            case "mvtink_void_damascus" -> {
                if (event != null) event.setDamage(event.getDamage() * (1.0 + 0.30 * r));
                world.spawnParticle(Particle.PORTAL, at, 12, 0.3, 0.3, 0.3, 0.15);
            }
            case "mvtink_cinder_steel" -> {
                target.setFireTicks((int) Math.max(80, 160 * r));
                world.spawnParticle(Particle.FLAME, at, 12, 0.3, 0.3, 0.3, 0.05);
                world.spawnParticle(Particle.LAVA, at, 4, 0.2, 0.2, 0.2, 0.0);
            }
            case "mvtink_prismatic_quartz" -> {
                for (Entity nearby : target.getNearbyEntities(3.5, 2.0, 3.5)) {
                    if (nearby instanceof LivingEntity mob && !mob.equals(target) && !mob.equals(player)) {
                        mob.setNoDamageTicks(0);
                        mob.damage(2.5 * r, player);
                    }
                }
                world.playSound(target.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.1f, 1.6f);
                world.spawnParticle(Particle.NOTE, at, 10, 0.35, 0.35, 0.35, 0.15);
            }
            case "mvtink_shadow_platinum" -> {
                if (player != null && player.isSneaking() && event != null) {
                    event.setDamage(event.getDamage() * (1.0 + 0.50 * r));
                }
                world.spawnParticle(Particle.SMOKE, at, 10, 0.3, 0.3, 0.3, 0.02);
            }
            case "mvtink_ender_brass" -> {
                if (rnd.nextDouble() < 0.25 * r) {
                    Location warp = target.getLocation().add(rnd.nextDouble() * 2 - 1, 0, rnd.nextDouble() * 2 - 1);
                    target.teleport(warp);
                    world.spawnParticle(Particle.PORTAL, warp, 15, 0.2, 0.4, 0.2, 0.2);
                    world.playSound(warp, Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 1.3f);
                }
            }
            case "mvtink_adamant_steel" -> {
                if (event != null) event.setDamage(event.getDamage() + (1.5 * r));
                if (player != null) player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) Math.max(40, 80 * r), 0, false, false));
            }
            case "mvtink_hellfire_bismuth" -> {
                if (player != null && player.getFallDistance() > 0.0f) {
                    world.spawnParticle(Particle.EXPLOSION_EMITTER, at, 1);
                    world.spawnParticle(Particle.FLAME, at, 18, 0.35, 0.35, 0.35, 0.08);
                    world.playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.2f);
                    for (Entity nearby : target.getNearbyEntities(3.0, 2.0, 3.0)) {
                        if (nearby instanceof LivingEntity mob && !mob.equals(target) && !mob.equals(player)) {
                            mob.setNoDamageTicks(0);
                            mob.damage(3.0 * r, player);
                        }
                    }
                }
            }
            case "mvtink_glacial_silver" -> {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) Math.max(40, 80 * r), 2, false, true));
                target.setFreezeTicks((int) Math.max(60, 100 * r));
                world.spawnParticle(Particle.SNOWFLAKE, at, 12, 0.3, 0.3, 0.3, 0.02);
            }
            case "mvtink_sanguine_gold" -> {
                if (player != null && event != null) {
                    double heal = Math.max(1.0, event.getDamage() * 0.25 * r);
                    player.setHealth(Math.min(ServerCompat.maxHealth(player), player.getHealth() + heal));
                    world.spawnParticle(Particle.HEART, player.getLocation().add(0, 1.5, 0), 3, 0.2, 0.2, 0.2, 0.0);
                }
            }
            default -> {
                // Dynamic alloys are fully covered by parent affinity expansion.
            }
        }
    }

    // ==========================================
    // TOOLS - ON BLOCK BREAK
    // ==========================================
    public static void applyToolAffinities(@Nonnull Player player,
                                           @Nonnull BlockBreakEvent event,
                                           @Nonnull ItemStack tool,
                                           @Nullable String matId,
                                           double ratio,
                                           @Nonnull MaterialRegistry registry) {
        if (matId == null || ratio <= 0.0) return;
        Random rnd = ThreadLocalRandom.current();

        for (Resolved resolved : resolve(matId, ratio, registry)) {
            for (TraitAffinity affinity : TraitAffinity.of(resolved.material())) {
                applyToolAffinity(affinity, player, event, tool, resolved.ratio(), rnd);
            }
        }
    }

    private static void applyToolAffinity(@Nonnull TraitAffinity affinity,
                                          @Nonnull Player player,
                                          @Nonnull BlockBreakEvent event,
                                          @Nonnull ItemStack tool,
                                          double r,
                                          @Nonnull Random rnd) {
        Block block = event.getBlock();
        World world = block.getWorld();
        Location at = block.getLocation().add(0.5, 0.5, 0.5);

        switch (affinity) {
            case TEMPERED -> {
                if (rnd.nextDouble() < 0.15 * r) {
                    repairTool(tool, 1);
                    world.spawnParticle(Particle.ENCHANTED_HIT, at, 4, 0.3, 0.3, 0.3, 0.02);
                }
            }
            case RADIANT -> player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, (int) Math.max(100, 200 * r), 0, false, false));
            case RESONANT -> {
                world.playSound(block.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.8f, 1.6f);
                world.spawnParticle(Particle.NOTE, at, 6, 0.4, 0.4, 0.4, 0.15);
            }
            case VOLATILE -> {
                if (rnd.nextDouble() < 0.15 * r) {
                    player.giveExp(1);
                    world.spawnParticle(Particle.FLAME, at, 6, 0.3, 0.3, 0.3, 0.02);
                }
            }
            case TERRAIN -> {
                if (rnd.nextDouble() < 0.15 * r) spawnBonusDrop(block, tool, world);
            }
            case INFERNAL -> autoSmelt(player, event, tool, rnd, 0.20 * r);
            case VOID -> {
                if (rnd.nextDouble() < 0.15 * r) {
                    player.giveExp(1);
                    world.spawnParticle(Particle.PORTAL, at, 8, 0.3, 0.3, 0.3, 0.1);
                }
            }
            case PRIMAL -> {
                if (rnd.nextDouble() < 0.10 * r) {
                    player.giveExp(2);
                    world.spawnParticle(Particle.HAPPY_VILLAGER, at, 4, 0.3, 0.3, 0.3, 0.0);
                }
            }
            case SWIFT -> player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, (int) Math.max(40, 100 * r), 0, false, false));
            case BRUTAL -> {
                if (rnd.nextDouble() < 0.15 * r) spawnBonusDrop(block, tool, world);
            }
            case BULWARK -> {
                if (rnd.nextDouble() < 0.20 * r) {
                    repairTool(tool, 1);
                    world.spawnParticle(Particle.ENCHANTED_HIT, at, 5, 0.3, 0.3, 0.3, 0.02);
                }
            }
            case ASCENDANT -> {
                if (rnd.nextDouble() < 0.18 * r) {
                    player.giveExp(2);
                    world.spawnParticle(Particle.TOTEM_OF_UNDYING, at, 6, 0.3, 0.3, 0.3, 0.03);
                }
            }
        }
    }

    private static void spawnBonusDrop(@Nonnull Block block, @Nonnull ItemStack tool, @Nonnull World world) {
        List<ItemStack> drops = new ArrayList<>(block.getDrops(tool));
        if (drops.isEmpty()) return;
        ItemStack source = drops.get(ThreadLocalRandom.current().nextInt(drops.size()));
        ItemStack bonus = source.clone();
        bonus.setAmount(1);
        world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), bonus);
    }

    private static void autoSmelt(@Nonnull Player player,
                                  @Nonnull BlockBreakEvent event,
                                  @Nonnull ItemStack tool,
                                  @Nonnull Random rnd,
                                  double chance) {
        if (rnd.nextDouble() >= chance) return;
        Block block = event.getBlock();
        Collection<ItemStack> drops = block.getDrops(tool);
        if (drops.isEmpty()) return;

        List<ItemStack> output = new ArrayList<>();
        boolean changed = false;
        for (ItemStack drop : drops) {
            Material smelted = smeltResult(drop.getType());
            if (smelted != null) {
                changed = true;
                output.add(new ItemStack(smelted, drop.getAmount()));
            } else {
                output.add(drop);
            }
        }
        if (!changed) return;

        event.setDropItems(false);
        World world = block.getWorld();
        for (ItemStack stack : output) {
            world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), stack);
        }
        world.playSound(block.getLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 0.9f, 1.4f);
        world.spawnParticle(Particle.LAVA, block.getLocation().add(0.5, 0.6, 0.5), 6, 0.3, 0.2, 0.3, 0.0);
    }

    @Nullable
    private static Material smeltResult(@Nonnull Material material) {
        return switch (material) {
            case RAW_IRON, IRON_ORE, DEEPSLATE_IRON_ORE -> Material.IRON_INGOT;
            case RAW_COPPER, COPPER_ORE, DEEPSLATE_COPPER_ORE -> Material.COPPER_INGOT;
            case RAW_GOLD, GOLD_ORE, DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE -> Material.GOLD_INGOT;
            case SAND, RED_SAND -> Material.GLASS;
            case COBBLESTONE, COBBLED_DEEPSLATE -> Material.STONE;
            case CLAY -> Material.TERRACOTTA;
            case ANCIENT_DEBRIS -> Material.NETHERITE_SCRAP;
            case NETHER_QUARTZ_ORE -> Material.QUARTZ;
            case POTATO -> Material.BAKED_POTATO;
            default -> null;
        };
    }

    private static void repairTool(@Nonnull ItemStack tool, int amount) {
        ItemMeta meta = tool.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int max = pdc.getOrDefault(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, -1);
        if (max <= 0) return;
        int current = pdc.getOrDefault(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, max);
        if (current >= max) return;

        current = Math.min(max, current + amount);
        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, current);
        if (meta instanceof Damageable damageable) {
            int vanillaMax = tool.getType().getMaxDurability();
            if (vanillaMax > 0) {
                double damageRatio = 1.0 - ((double) current / max);
                damageable.setDamage((int) (vanillaMax * damageRatio));
            }
        }
        tool.setItemMeta(meta);
    }

    // ==========================================
    // ARMOR - ON DAMAGE TAKEN
    // ==========================================
    public static void applyArmorAffinities(@Nonnull Player player,
                                            @Nullable LivingEntity attacker,
                                            @Nullable EntityDamageByEntityEvent event,
                                            @Nullable String matId,
                                            double ratio,
                                            @Nonnull MaterialRegistry registry) {
        if (matId == null || ratio <= 0.0) return;
        Random rnd = ThreadLocalRandom.current();

        for (Resolved resolved : resolve(matId, ratio, registry)) {
            for (TraitAffinity affinity : TraitAffinity.of(resolved.material())) {
                applyArmorAffinity(affinity, player, attacker, resolved.ratio(), rnd);
            }
        }
    }

    private static void applyArmorAffinity(@Nonnull TraitAffinity affinity,
                                           @Nonnull Player player,
                                           @Nullable LivingEntity attacker,
                                           double r,
                                           @Nonnull Random rnd) {
        World world = player.getWorld();
        Location at = player.getLocation().add(0, 1, 0);

        switch (affinity) {
            case TEMPERED, BULWARK -> player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) Math.max(30, 60 * r), 0, false, false));
            case RADIANT -> {
                player.setHealth(Math.min(ServerCompat.maxHealth(player), player.getHealth() + 0.5));
                world.spawnParticle(Particle.HEART, at, 2, 0.2, 0.2, 0.2, 0.0);
            }
            case RESONANT -> {
                if (attacker != null) {
                    Vector push = attacker.getLocation().toVector().subtract(player.getLocation().toVector());
                    if (push.lengthSquared() > 0.0001) {
                        attacker.setVelocity(push.normalize().multiply(0.6 * r).setY(0.25));
                    }
                    world.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.4f);
                }
            }
            case VOLATILE -> {
                if (attacker != null) attacker.setFireTicks((int) Math.max(20, 60 * r));
            }
            case TERRAIN -> {
                if (attacker != null) attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) Math.max(20, 50 * r), 1, false, true));
            }
            case INFERNAL -> player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (int) Math.max(40, 80 * r), 0, false, false));
            case VOID -> player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, (int) Math.max(40, 80 * r), 0, false, false));
            case PRIMAL -> player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) Math.max(30, 60 * r), 0, false, false));
            case SWIFT -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) Math.max(30, 60 * r), 0, false, false));
            case BRUTAL -> {
                if (attacker != null) {
                    attacker.setNoDamageTicks(0);
                    attacker.damage(1.5 * r, player);
                }
            }
            case ASCENDANT -> player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) Math.max(30, 50 * r), 0, false, false));
        }
    }

    // ==========================================
    // ALLOY EXPANSION
    // ==========================================
    /** A concrete material paired with the potency it contributes. */
    private record Resolved(TinkerMaterial material, double ratio) {
    }

    /**
     * Expands a material into its contributing essences. Alloys recursively unfold into their
     * two parent minerals at half potency, so every forge combination inherits a distinct blend.
     */
    @Nonnull
    private static List<Resolved> resolve(@Nonnull String matId, double ratio, @Nonnull MaterialRegistry registry) {
        List<Resolved> out = new ArrayList<>(2);
        TinkerMaterial material = registry.get(matId);
        if (material == null) return out;
        unfold(material, ratio, registry, out, 0);
        return out;
    }

    private static void unfold(@Nonnull TinkerMaterial material,
                               double ratio,
                               @Nonnull MaterialRegistry registry,
                               @Nonnull List<Resolved> out,
                               int depth) {
        String parents = material.getAlloyParents();
        if (depth < 2 && material.getType() == MaterialType.ALLOY && parents != null && parents.contains(",")) {
            String[] ids = parents.split(",");
            for (String parentId : ids) {
                TinkerMaterial parent = registry.get(parentId.trim());
                if (parent != null) {
                    unfold(parent, ratio / ids.length, registry, out, depth + 1);
                }
            }
            return;
        }
        out.add(new Resolved(material, ratio));
    }
}
