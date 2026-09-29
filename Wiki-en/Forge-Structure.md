# ⚒️ The Multiverse Forge Multiblock Structure

The **Multiverse Forge** is an ancient, monumental multiblock metallurgy structure ($11 \times 7 \times 11$) built around a central Anvil. Once validated, the structure activates with dynamic particle sweeps, bestows an ambient particle aura to the anvil, and replaces the standard vanilla anvil interface with the custom **Multiverse Forge GUI** for forging tool parts and assembling high-performance modular equipment.

---

## 🏛️ Multiblock Architecture & Dimensions

* **Footprint**: $11 \times 11$ blocks wide, $7$ blocks high ($11 \times 7 \times 11$).
* **Focal Center**: Central Anvil (`minecraft:anvil`) located at layer $Y=1$ (relative coordinates $(0, 0, 0)$).
* **Composition**: Total of **243 non-air blocks**:
  * **32** Chiseled Tuff Bricks (`minecraft:chiseled_tuff_bricks`)
  * **29** Deepslate Tiles (`minecraft:deepslate_tiles`)
  * **16** Deepslate Bricks (`minecraft:deepslate_bricks`)
  * **97** Tuff Brick Slabs (`minecraft:tuff_brick_slab`)
  * **48** Tuff Brick Stairs (`minecraft:tuff_brick_stairs`)
  * **20** Thermal Lava Source Blocks (`minecraft:lava`) inside 4 corner furnace pillars
  * **1** Central Anvil (`minecraft:anvil`, `chipped_anvil`, or `damaged_anvil`)

### Rotational Tolerance
The forge supports all **4 cardinal rotations** ($0^\circ, 90^\circ, 180^\circ, 270^\circ$). Players may orient the entrance towards North, South, East, or West. Slabs, stairs, and anvil variants are forgivingly matched so minor facing differences do not impede activation.

---

## ⚡ Activation & Particle Simulation

When an anvil is right-clicked within an intact multiblock structure:
1. **Validation Sweep**:
   * **Phase 1**: High-temperature flames and soul embers surge up the 4 corner lava pillars.
   * **Phase 2**: Radiant starlight and glyph particles (`ENCHANT`, `WAX_ON`) sweep inward across the floor towards the anvil.
   * **Phase 3**: Flash burst (`TOTEM_OF_UNDYING` + `TRIAL_SPAWNER_DETECTION_OMINOUS`) converges at the anvil alongside resonant beacon and anvil chime audio.
   * **On-Screen Title**: Displays `MULTIVERSE FORGE - Structure validated! The Forge is now active.`
2. **Persistent Anvil Aura**:
   * The central anvil continuously radiates an orbital aura of `SMALL_FLAME`, `WAX_OFF`, and warm ember smoke with ambient blast furnace audio.
   * If the central anvil or key pillar blocks are broken, the forge automatically deactivates.

---

## 🖥️ Custom Multiverse Forge GUI

Right-clicking the validated central anvil cancels the vanilla anvil interface and opens the **Multiverse Forge Custom GUI** (54 slots).

```
+-----------------------------------------------------------+
|  [D] [1] [D] [D]  [STATUS BEACON]  [D] [D] [2] [D]        |
|  [MAT] [->] [CAST] [|]  [TOOL TYPE SELECTOR]              |
|  [CARV] [HAMMER] [OUT] [|]  [HEAD] [|] [ROD] [|] [BIND]   |
|  [D] [D] [D] [D]   [|]   [ASSEMBLE BUTTON] [->] [PREVIEW] |
|  [D] [D] [D] [D]   [|]   [CODEX GUIDE]                    |
+-----------------------------------------------------------+
```

### 1. Left Section: Tool Part Forging
* **Slot 10 (Material Input)**: Place a Molten Liquid Bucket (`mvtink_<id>_molten_bucket`).
* **Slot 12 (Cast Input)**: Place a reusable casting mold:
  * **Tool Head Cast** (`mvtink_cast_head`): Yields `mvtink_<id>_head`.
  * **Tool Rod Cast** (`mvtink_cast_rod`): Yields `mvtink_<id>_rod`.
  * **Tool Binding Cast** (`mvtink_cast_binding`): Yields `mvtink_<id>_binding`.
  * Also compatible with Ingot Cast, Nugget Cast, and Block Cast.
* **Slot 20 (Strike Anvil)**: Click to hammer the molten metal. Returns an empty bucket and deposits the forged part in **Slot 21**.
* **Slot 18 (Quick Mold Carver)**: Click with Clay Bricks in your inventory to quickly carve new Head, Rod, or Binding molds.

### 2. Right Section: Modular Tool Assembly
* **Slot 24 (Tool Type Selector)**: Click to toggle between **Pickaxe**, **Broadsword**, **Battleaxe**, **Excavator**, and **Scythe**.
* **Slot 30 (Head / Cabeza)**: Determines attack damage, mining efficiency, and primary trait.
* **Slot 32 (Handle / Rod / Palo)**: Dictates baseline durability multiplier and handle trait.
* **Slot 34 (Binding / Mango)**: Grants auxiliary durability and secondary utility trait.
* **Slot 41 (Assemble Button)**: Combines the 3 components into a permanent **Modular Tool** (`mvtink_is_modular_tool`) inheriting all physical traits and stats!

---

## 🛠️ Administrative & Testing Commands

* `/mvtink forge build [0|90|180|270]` — Instantly constructs the full multiblock structure centered on the player or targeted block.
* `/mvtink forge check` — Inspects the multiblock structure around the targeted anvil, reporting exact match percentage and missing blocks.
* `/mvtink forge gui` — Directly opens the custom Forge GUI for testing.

> 📖 The **Alloy Codex** (`/mvtink codex`) needs no permission at all: it is the public reference menu for every player, and it is also reachable from the book button in the Alloy Crucible tab. Its **Mineral Catalog** tab browses both the curated material list and the flat list of **every registered item id** (**2,656** of them), filterable by item kind.
