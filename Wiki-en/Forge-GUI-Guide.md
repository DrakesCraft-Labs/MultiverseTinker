# ⚒ Multiverse Forge GUI & Equipment Guide

The **Multiverse Forge** features a comprehensive 5-tab user interface accessible by right-clicking the central anvil of an active Forge Multiblock structure.

---

## 🧭 Navigation Bar (Row 0)

The top row (slots 0–8) contains persistent navigation controls:
- **Slot 0**: `[ 1. Codex & Guide ]` - In-game encyclopedia, tier guides, and multiblock structure details.
- **Slot 2**: `[ 2. Molds & Parts ]` - Quick mold carver and multi-material component forging.
- **Slot 4**: `[ 3. Alloy Crucible ]` - Alloy smelting station to blend 2 materials into alloy ingots.
- **Slot 6**: `[ 4. Weapon Assembly ]` - Modular weapon assembly with weapon type cycling.
- **Slot 8**: `[ 5. Tool Assembly ]` - Modular tool assembly with tool type cycling.

---

## 📖 Section 1: Informational Codex & Guides
- Displays interactive codex books explaining:
  1. **Multiblock Structure**: 243 blocks, centered anvil, 4 lava corner columns, chiseled tuff bricks, deepslate tiles, and tuff brick slabs/stairs.
  2. **Multi-Material Forging**: Concentration rules (1, 2, or 3 materials per part with proportional trait potency).
  3. **Alloy Crucible**: The 16 alloy recipes and mechanics.
  4. **Tier Evolution**: Progression from Wood to Netherite via kills and blocks broken.
  5. **Specialized Perks**: Unique mechanics for all 7 weapons and 5 tools.

---

## 🔨 Section 2: Molds & Multi-Material Part Forging

### Quick Mold Carving
Click any mold button on the top row while holding **1 Clay Brick** in your inventory to carve a reusable casting mold:
- **Head Cast** (`mvtink_cast_head`): Tool & weapon heads (Cabeza).
- **Handle Cast** (`mvtink_cast_rod`): Handles & shafts (Mango).
- **Pommel Cast** (`mvtink_cast_binding`): Pommels, bindings & counterweights (Pomo).
- **Bow Limbs Cast** (`mvtink_cast_bow_limbs`): Flexible bowstaves (Brazos del Arco).
- **Bowstring Mold** (`mvtink_cast_bowstring`): High-tension strings (Cuerda Tensora).
- **Shield Plate Cast** (`mvtink_cast_shield_plate`): Frontal defense plates (Placa Frontal).
- **Shield Boss Cast** (`mvtink_cast_shield_boss`): Center shield bosses & frame (Umbo / Armazón).

### Multi-Material Forging (1 to 3 Materials)
- Place **1 Cast** in **Slot 28**.
- Place up to 3 Materials (Ingots, Gems, Minerals, or Molten Liquid Buckets) in **Slots 30, 31, and 32**:
  - **1 Material placed**: 100% concentration (full stats and 100% trait proc rate).
  - **2 Materials placed**: 50% / 50% concentration (e.g. 50% Diamond + 50% Quartz gives 50% potency to each trait).
  - **3 Materials placed**: 33.3% / 33.3% / 33.4% concentration split across all 3 traits.
- Click **⚒ Strike Anvil to Forge Part** (Slot 38) to produce the finished modular component in **Slot 42**.
- Casts are **reusable** and never consumed!

---

## 🧪 Section 3: Alloy Crucible (Material Mixing)
- Place Material 1 in **Slot 20** and Material 2 in **Slot 24**.
- Click **🔥 Melt & Blend Alloy** (Slot 31).
- Yields **2x Finished Alloy Ingots** in **Slot 33**.
- See [Alloy Mixing Guide](Alloy-Mixing.md) for all 16 recipes.

---

## ⚔ Section 4: Modular Weapon Crafting

Click the **Weapon Selector** in **Slot 13** to cycle between all 7 weapon types:

### 3-Part Weapons (Head, Handle, Pommel)
- **Modular Broadsword**: Head (Blade) + Handle (Hilt) + Pommel (Guard).
- **Modular Heavy Crossbow**: Head (Prod/Limbs) + Handle (Stock) + Pommel (Trigger).
- **Modular Elder Trident**: Head (Prongs) + Handle (Shaft) + Pommel (Counterweight).
- **Modular Kinetic Spear**: Head (Spearhead) + Handle (Long Shaft) + Pommel (Butt Cap).
- **Modular War Mace**: Head (Heavy Mace Head) + Handle (Reinforced Shaft) + Pommel (Flanged Pommel).

### 2-Part Weapons
- **Modular Longbow**: Bow Limbs (Brazos del Arco) + Bowstring (Cuerda Tensora).
- **Modular Tower Shield**: Shield Faceplate (Placa Frontal) + Shield Boss (Umbo / Armazón).

### Weapon Tier Evolution & Perks
- Every weapon starts at **Wood Tier** (`0` kills).
- Combat kills increase the kill counter and advance the weapon through tiers:
  `Wood → Stone (15 kills) → Copper (40) → Iron (80) → Gold (150) → Diamond (300) → Netherite (600)`.
- **Specialized Combat Perks**:
  - **War Mace**: Downward fall strikes trigger seismic ground shockwaves dealing AOE damage and launching enemies.
  - **Longbow**: Arrows inherit limb and string elemental traits.
  - **Heavy Crossbow**: Bolts bypass 30% of target armor and trigger explosive impact.
  - **Elder Trident**: Melee and thrown strikes summon hydraulic lightning in water/rain (+5.0 damage).
  - **Kinetic Spear**: Extended attack reach and +30% damage while sprinting.
  - **Tower Shield**: Reflects 35% of blocked damage back to the attacker and applies shield traits.
  - **Broadsword**: Sweeping melee attacks chain elemental traits across adjacent foes.

---

## ⛏ Section 5: Modular Tool Crafting

Click the **Tool Selector** in **Slot 13** to cycle between all 5 tool types:
- **Modular Pickaxe**: Head (Cabeza) + Handle (Mango) + Pommel (Pomo).
- **Modular Battleaxe**: Head (Cabeza) + Handle (Mango) + Pommel (Pomo).
- **Modular Excavator (Shovel)**: Head (Cabeza) + Handle (Mango) + Pommel (Pomo).
- **Modular Scythe (Hoe)**: Head (Cabeza) + Handle (Mango) + Pommel (Pomo).
- **Modular Fishing Rod**: Head (Tip & Line) + Handle (Shaft) + Pommel (Reel & Grip).

### Tool Tier Evolution & Perks
- Every tool starts at **Wood Tier** (`0` blocks broken).
- Mining blocks increases the blocks broken counter and advances the tool through tiers:
  `Wood → Stone (50 blocks) → Copper (150) → Iron (350) → Gold (750) → Diamond (1500) → Netherite (3000)`.
- **Specialized Mining Perks**:
  - **Pickaxe**: Deep Vein Resonance has a 15% chance to drop bonus ores and grant Haste I for 6s.
  - **Battleaxe**: Cleaves whole log columns and disables enemy shields on critical hits.
  - **Excavator (Shovel)**: Sneak-digging breaks a 3x3 area of matching loose blocks (dirt, sand, gravel).
  - **Scythe (Hoe)**: Harvests 3x3 mature crops and automatically replants seeds from your inventory.
  - **Fishing Rod**: Abyssal Dredge has a 15% chance to hook rare raw Multiverse minerals while fishing.
