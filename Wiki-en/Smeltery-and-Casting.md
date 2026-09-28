# 🌋 Smeltery Crucible & Water Cauldron Casting

MultiverseTinker implements a thermal metallurgy progression system consisting of two key stations: the **Smeltery Crucible** and the **Water Cauldron Casting**.

---

## 1. The Smeltery Crucible (`mvtink_smeltery`)

The Smeltery Crucible is a heavy-duty melting furnace designed to turn raw mineral ores into liquid molten metals.

### ⚠ Thermal Heat Source Requirements (`BlockFace.DOWN`)
The Smeltery Crucible strictly requires a valid heat source directly underneath it:

| Heat Source Block | Smelting Speed | Consumption Chance | Behavior |
|---|---|---|---|
| **Lava** (`Material.LAVA`) | **100%** (Optimal) | **10%** per completed melt | Fastest melting; has a 10% chance to extinguish into air when an ore is smelted. |
| **Magma Block** (`Material.MAGMA_BLOCK`) | **70%** (-30% Speed) | **0%** (Infinite) | Slower melting (+30% duration); permanent stability, never consumed. |

* If placed over air, water, stone, or any block other than Lava or a Magma Block, the crucible will remain dormant.

### Interactive GUI Layout (27 Slots)
Right-clicking the Smeltery Crucible opens its custom graphical interface:
* **Slot 10**: Raw Mineral Input (`mvtink_*_raw`).
* **Slot 12**: Empty Bucket Input (`Material.BUCKET`).
* **Slot 14**: Dynamic Heat & Progress Indicator:
  * ❌ **Cold / Inactive**: If a heat source is missing beneath the block, a barrier icon appears with detailed instructions:
    > *"❌ Inactive: No Heat Source. Place a block of Lava or Magma Block directly beneath this Smeltery block to ignite the melting crucible!"*
  * 🔥 **Heated / Ready**: Displays the active heat source type:
    * *Lava Detected*: 100% Thermal Output, warns about the 10% consumption chance.
    * *Magma Block Detected*: 70% Thermal Output, confirms permanent heat stability.
  * ⚡ **Smelting Progress**: Displays real-time melting completion percentage and an animated progress bar:
    > *"🔥 Smelting in Progress... [██████----] 60%"*
* **Slot 16**: Output Slot containing the finished **Molten Liquid Bucket** (`mvtink_<id>_molten_bucket`).

---

## 2. Water Cauldron Casting

Once liquid metal is contained within a Molten Bucket, it must be cooled and solidified using a standard vanilla **Water Cauldron** and a reusable **Casting Mold**.

### Reusable Casting Molds
* **Ingot Cast** (`mvtink_cast_ingot`): Casts molten metal into 1 standard Ingot.
* **Nugget Cast** (`mvtink_cast_nugget`): Casts molten metal into 9 small Nuggets.
* **Block Cast** (`mvtink_cast_block`): Casts molten metal into 1 dense Storage Block.

### Solidification Procedure
1. Place a Cauldron and fill it with Water (`Material.WATER_CAULDRON`).
2. Hold the **Molten Liquid Bucket** in your **Main Hand**.
3. Hold your chosen **Casting Mold** in your **Off-Hand** (or have it present in your inventory).
4. **Right-Click the Water Cauldron**:
   * Generates a thermal reaction: loud hissing steam sound (`BLOCK_LAVA_EXTINGUISH`), anvil quench clink (`BLOCK_ANVIL_USE`), and rising campfire smoke particles.
   * Evaporates 1 water level from the cauldron.
   * Empties the molten bucket, returning a vanilla `BUCKET`.
   * Preserves the casting mold (reusable indefinitely).
   * Delivers the finished solid metal piece directly to the player.
