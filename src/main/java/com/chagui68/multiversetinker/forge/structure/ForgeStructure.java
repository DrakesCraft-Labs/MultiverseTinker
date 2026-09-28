package com.chagui68.multiversetinker.forge.structure;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

public class ForgeStructure {

    public record ValidationResult(boolean isValid, int matchedBlocks, int totalBlocks, double percentage, int rotation) {}

    private final List<ForgeStructureBlock> baseBlocks = new ArrayList<>();
    private final Map<Integer, List<ForgeStructureBlock>> rotatedBlocks = new HashMap<>();
    private int anvilOriginX = 5;
    private int anvilOriginY = 1;
    private int anvilOriginZ = 5;

    public void loadFromStream(@Nonnull InputStream inputStream, @Nullable Logger logger) throws IOException {
        baseBlocks.clear();
        rotatedBlocks.clear();

        Map<String, Object> root = NbtReader.readCompressed(inputStream);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> paletteList = (List<Map<String, Object>>) root.get("palette");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> blocksList = (List<Map<String, Object>>) root.get("blocks");

        if (paletteList == null || blocksList == null) {
            throw new IOException("Invalid NBT structure: missing palette or blocks.");
        }

        // Find anvil position first to center the multiblock
        for (Map<String, Object> blockEntry : blocksList) {
            int state = ((Number) blockEntry.get("state")).intValue();
            Map<String, Object> paletteEntry = paletteList.get(state);
            String name = (String) paletteEntry.get("Name");
            if (name != null && name.toLowerCase(Locale.ROOT).contains("anvil")) {
                @SuppressWarnings("unchecked")
                List<Number> pos = (List<Number>) blockEntry.get("pos");
                this.anvilOriginX = pos.get(0).intValue();
                this.anvilOriginY = pos.get(1).intValue();
                this.anvilOriginZ = pos.get(2).intValue();
                break;
            }
        }

        // Populate relative blocks
        for (Map<String, Object> blockEntry : blocksList) {
            int state = ((Number) blockEntry.get("state")).intValue();
            Map<String, Object> paletteEntry = paletteList.get(state);
            String rawName = (String) paletteEntry.get("Name");
            if (rawName == null) continue;

            String cleanName = rawName.replace("minecraft:", "").toUpperCase(Locale.ROOT);
            if (cleanName.equals("AIR")) continue;

            Material material;
            try {
                material = Material.valueOf(cleanName);
            } catch (IllegalArgumentException e) {
                material = Material.AIR;
            }

            if (material == Material.AIR) continue;

            @SuppressWarnings("unchecked")
            List<Number> pos = (List<Number>) blockEntry.get("pos");
            int dx = pos.get(0).intValue() - anvilOriginX;
            int dy = pos.get(1).intValue() - anvilOriginY;
            int dz = pos.get(2).intValue() - anvilOriginZ;

            Map<String, String> properties = new HashMap<>();
            @SuppressWarnings("unchecked")
            Map<String, Object> rawProps = (Map<String, Object>) paletteEntry.get("Properties");
            if (rawProps != null) {
                for (Map.Entry<String, Object> entry : rawProps.entrySet()) {
                    properties.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            baseBlocks.add(new ForgeStructureBlock(dx, dy, dz, material, properties));
        }

        // Compute 4 rotational variants (0, 90, 180, 270)
        for (int rot : new int[]{0, 90, 180, 270}) {
            List<ForgeStructureBlock> rotated = new ArrayList<>(baseBlocks.size());
            for (ForgeStructureBlock b : baseBlocks) {
                rotated.add(b.rotate(rot));
            }
            rotatedBlocks.put(rot, rotated);
        }

        if (logger != null) {
            logger.info("ForgeStructure loaded " + baseBlocks.size() + " relative blocks centered on anvil ("
                    + anvilOriginX + ", " + anvilOriginY + ", " + anvilOriginZ + ")");
        }
    }

    public List<ForgeStructureBlock> getBlocks(int rotation) {
        return rotatedBlocks.getOrDefault((rotation % 360 + 360) % 360, baseBlocks);
    }

    public ValidationResult validate(@Nonnull Location anvilLoc) {
        World world = anvilLoc.getWorld();
        if (world == null || baseBlocks.isEmpty()) {
            return new ValidationResult(false, 0, baseBlocks.size(), 0.0, 0);
        }

        int total = baseBlocks.size();
        int bestMatched = 0;
        int bestRot = 0;

        for (int rot : new int[]{0, 90, 180, 270}) {
            List<ForgeStructureBlock> blocks = rotatedBlocks.get(rot);
            int matched = 0;
            int lavaCount = 0;

            for (ForgeStructureBlock expected : blocks) {
                Block actual = world.getBlockAt(
                        anvilLoc.getBlockX() + expected.dx(),
                        anvilLoc.getBlockY() + expected.dy(),
                        anvilLoc.getBlockZ() + expected.dz()
                );

                if (matches(expected.material(), actual.getType())) {
                    matched++;
                    if (expected.material() == Material.LAVA && actual.getType() == Material.LAVA) {
                        lavaCount++;
                    }
                }
            }

            if (matched > bestMatched) {
                bestMatched = matched;
                bestRot = rot;
            }

            // At least 90% matching and has heat/lava present
            double pct = (double) matched / (double) total;
            if (pct >= 0.90 && lavaCount >= 4) {
                return new ValidationResult(true, matched, total, pct * 100.0, rot);
            }
        }

        return new ValidationResult(false, bestMatched, total, ((double) bestMatched / total) * 100.0, bestRot);
    }

    public void build(@Nonnull Location anvilLoc, int rotation) {
        World world = anvilLoc.getWorld();
        if (world == null) return;

        List<ForgeStructureBlock> blocks = getBlocks(rotation);
        for (ForgeStructureBlock b : blocks) {
            Block block = world.getBlockAt(
                    anvilLoc.getBlockX() + b.dx(),
                    anvilLoc.getBlockY() + b.dy(),
                    anvilLoc.getBlockZ() + b.dz()
            );
            block.setType(b.material(), false);
        }
    }

    private boolean matches(@Nonnull Material expected, @Nonnull Material actual) {
        if (expected == actual) return true;
        if (expected == Material.ANVIL) {
            return actual == Material.ANVIL || actual == Material.CHIPPED_ANVIL || actual == Material.DAMAGED_ANVIL;
        }
        if (expected == Material.TUFF_BRICK_SLAB && actual == Material.TUFF_BRICK_SLAB) return true;
        if (expected == Material.TUFF_BRICK_STAIRS && actual == Material.TUFF_BRICK_STAIRS) return true;
        return false;
    }
}
