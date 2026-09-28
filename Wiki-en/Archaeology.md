# 🔍 Geological Archaeology & Brushing

MultiverseTinker replaces traditional chunk-based ore worldgen with an interactive **Geological Archaeology System**. This prevents chunk generation lag, terrain border seams, and compatibility conflicts with custom world generators.

---

## 1. How It Works

1. Hold a **Brush** in your main hand (vanilla `BRUSH` or the `Prospector Brush`).
2. **Hold Right-Click** continuously on a valid geological block.
3. Every pulse advances archaeological excavation progress with brushing sounds (`ITEM_BRUSH_BRUSHING_GENERIC`) and dust particles.
4. Upon reaching full duration (default: 30 ticks = 1.5 seconds), the mineral is extracted with a geological chime (`BLOCK_AMETHYST_BLOCK_CHIME`) and celebratory sparkles.

---

## 2. Geological Targets by Dimension

| Dimension | Valid Target Blocks | Yielded Minerals | Base Success Chance |
|---|---|---|---|
| **Overworld** | `STONE`, `COBBLESTONE`, `DEEPSLATE`, `COBBLED_DEEPSLATE`, `ANDESITE`, `DIORITE`, `GRANITE`, `TUFF` | 30 Overworld Minerals | **45%** |
| **The Nether** | `NETHERRACK`, `BLACKSTONE`, `BASALT` | 30 Nether Minerals | **40%** |
| **The End** | `END_STONE` | 30 The End Minerals | **35%** |

---

## 3. Geological Degradation & Anti-Macro Protection

* **Degradation System**: Rocks weather down realistically after excavation:
  * Stone $\rightarrow$ Cobblestone $\rightarrow$ Gravel $\rightarrow$ Air
  * Deepslate $\rightarrow$ Cobbled Deepslate $\rightarrow$ Gravel $\rightarrow$ Air
  * Blackstone $\rightarrow$ Basalt $\rightarrow$ Netherrack $\rightarrow$ Air
  * End Stone $\rightarrow$ Air
* **Cooldown Protection**: Every block coordinates position enters an internal 15-second cooldown to prevent automated macro-clicking abuse.

---

## 4. Brush Comparison: Standard vs. Prospector Brush

MultiverseTinker features two excavation tiers with fundamentally distinct yield distributions:

| Feature / Yield | Standard Vanilla Brush (`BRUSH`) | Archaeological Prospector Brush (`mvtink_brush_prospector`) |
|---|---|---|
| **Excavation Speed** | Standard (6 progress / pulse) | **+40% Faster** (10 progress / pulse) |
| **Base Success Rate** | 45% Overworld / 40% Nether / 35% End | **+15% Higher** (60% / 55% / 50%) |
| **Rare / Epic / Legendary Luck** | $1\times$ Standard Weights | **$2\times$ Weight Multiplier** |
| **Durability Preservation** | None (1 dmg per completed cycle) | **50% Chance** to negate durability loss |
| **Raw Ore Yield (`mvtink_*_raw`)** | **70%** (used in Smeltery to cast 1 Ingot) | **55%** |
| **Nugget Yield (`mvtink_*_nugget`)** | **30%** (1 Nugget) | **25%** (1 to 3 Nuggets) |
| **Storage Block Yield (`mvtink_*_block`)** | ❌ **0% (Cannot drop blocks)** | ⭐ **20% (Jackpot drop: full 9x block!)** |

### Synergy with the Block Cast (`mvtink_cast_block`)
While the Standard Brush strictly limits discoveries to single Raw Ores and loose Nuggets, the Prospector Brush enables players to unearth full mineral blocks. Combined with the **Block Cast** in the Smeltery Casting basin, players can compact and manage high-tier metallurgical production without manual $9\times$ crafting.
