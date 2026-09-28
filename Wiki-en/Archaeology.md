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

## 4. Archaeological Prospector Brush (`mvtink_brush_prospector`)

A specialized survival excavation tool crafted with gold, copper, an amethyst shard, and a vanilla brush:

* **+40% Brushing Speed**: Completes excavation in just 3 interaction pulses (<1 second).
* **+15% Success Chance Bonus**: Overworld 60%, Nether 55%, The End 50%.
* **Geological Luck**: **2x chance** to roll Rare, Epic, and Legendary minerals.
* **Reinforced Bristles**: **50% chance** to negate durability loss per completed extraction.
* **Visual FX**: Golden shimmer and mystical enchantment particles during excavation.
