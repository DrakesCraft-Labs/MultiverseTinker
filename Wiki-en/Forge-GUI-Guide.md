# ⚒ Multiverse Forge GUI & Equipment Guide

The **Multiverse Forge** features a comprehensive 6-tab user interface accessible by right-clicking the central anvil of an active Forge Multiblock structure.

---

## 🧭 Navigation Bar (Row 0)

The top row (slots 0–8) contains persistent navigation controls:
- **Slot 0**: Border Pane.
- **Slot 1**: `[ 1. Codex & Guide ]` - In-game encyclopedia, tier guides, and multiblock structure details.
- **Slot 2**: `[ 2. Molds & Parts ]` - Quick mold carver and multi-material component forging.
- **Slot 3**: `[ 3. Alloy Crucible ]` - Alloy smelting station to blend 2 materials into alloy ingots.
- **Slot 4**: Central Forge Banner & Divider.
- **Slot 5**: `[ 4. Weapon Assembly ]` - Modular weapon assembly with weapon type cycling.
- **Slot 6**: `[ 5. Tool Assembly ]` - Modular tool assembly with tool type cycling.
- **Slot 7**: `[ 6. Armor Assembly ]` - Modular armor assembly with armor piece cycling.
- **Slot 8**: Border Pane.

---

## 📖 Section 1: Informational Codex & Guides
Displays interactive codex books explaining:
1. **Multiblock Structure**: 243 blocks, centered anvil, 4 lava corner columns, chiseled tuff bricks, deepslate tiles, and tuff brick slabs/stairs.
2. **Multi-Material Forging**: Concentration rules (1, 2, or 3 materials per part with proportional trait potency).
3. **Alloy Crucible**: The 16 alloy recipes and metallurgical blending mechanics.
4. **Tier Evolution**: Progression from Wood to Netherite via kills (weapons), blocks broken (tools), and damage absorbed (armor).
5. **Specialized Perks**: Unique mechanics for all 7 weapons, 5 tools, and 4 armor pieces.

---

## 🔨 Section 2: Molds & Multi-Material Part Forging

### Single Mold Selector & Quick Carving
Cycle through available casting molds using a single, uncluttered selector in Row 1:
- **Slot 12**: Previous Mold (`◀`)
- **Slot 13**: **Mold Selector** (Click to cycle next mold, Right-click for previous).
- **Slot 14**: **Carve Mold Button** (Consumes **1 Clay Brick** from inventory).

#### Available Molds:
- **Head Cast** (`mvtink_cast_head`): Tool & weapon heads.
- **Handle Cast** (`mvtink_cast_rod`): Handles & shafts.
- **Pommel Cast** (`mvtink_cast_binding`): Pommels, bindings & counterweights.
- **Bow Limbs Cast** (`mvtink_cast_bow_limbs`): Flexible bowstaves.
- **Bowstring Mold** (`mvtink_cast_bowstring`): High-tension woven cord.
- **Shield Plate Cast** (`mvtink_cast_shield_plate`): Frontal defense plates.
- **Shield Boss Cast** (`mvtink_cast_shield_boss`): Center shield bosses & frame.
- **Armor Plate Cast** (`mvtink_cast_armor_plate`): Heavy protective armor plates.
- **Armor Lining Cast** (`mvtink_cast_armor_lining`): Flexible chainmail mesh & padding.
- **Armor Trim Cast** (`mvtink_cast_armor_trim`): Reinforced trims, joint rivets & buckles.
- **Ingot / Nugget / Block Casts**: Metal storage and conversion casts.

### Multi-Material Forging (1 to 3 Materials)
- Place **1 Cast** in **Slot 29**.
- Place up to 3 Materials (Ingots, Gems, Minerals, or Molten Liquid Buckets) in **Slots 30, 31, and 32**:
  - **1 Material placed**: 100% concentration (full stats and 100% trait proc rate).
  - **2 Materials placed**: 50% / 50% concentration split across both traits.
  - **3 Materials placed**: 33.3% / 33.3% / 33.4% concentration split across all 3 traits.
- Click **⚒ Strike Anvil to Forge Part** (Slot 40) to produce the finished modular component in **Slot 33**.
- Casts are **reusable** and never consumed!

---

## 🧪 Section 3: Alloy Crucible (Material Mixing)
- Place Material 1 in **Slot 29** and Material 2 in **Slot 33**.
- Click **♨ Ignite Crucible & Smelt Alloy** (Slot 31).
- Yields **2x Finished Alloy Ingots** in **Slot 40**.
- Click the **Alloy Recipes Codex** (Slot 49) to browse all 16 registered alloy formulas in chat.
- Every pair of the **110 blendable minerals** (97 geological + 13 non-netherite vanilla) yields its own alloy: 5,995 blendable pairs, of which 14 resolve to a legendary recipe. Vanilla netherite is the one vanilla material that cannot be freely blended (it is already an alloy), but it still works inside its own two curated recipes (**Cinder Steel** and **Cosmic Netherite**), so the crucible can produce **5,997 distinct mineral alloys** in total.
- **Prime fusion**: a legendary alloy can be dropped in again alongside another alloy, any mineral, or one of the **12 vanilla catalyst items** (Nether Star, Dragon Breath, Blue Ice, Packed Ice, Echo Shard, Heart of the Sea, Totem of Undying, End Crystal, Respawn Anchor, Prismarine Crystals, Amethyst Cluster, Ancient Debris). The prime alloy that comes out is Legendary rarity, outscales every composite, and unlocks prime ultimates plus the 9 prime armor states. See [Prime Alloys](Prime-Alloys.md). A fresh server exposes **8,085** forgeable combinations, up to **103,781** once every composite is discovered.
- See [Alloy Mixing Guide](Alloy-Mixing.md) for full details.

---

## ⚔ Section 4: Modular Weapon Crafting

Click the **Weapon Selector** in **Slot 13** to cycle between all 7 weapon types:

### 3-Part Weapons (Head, Handle, Pommel)
- **Modular Broadsword**: Head (Blade) + Handle (Hilt) + Pommel (Guard).
- **Modular Heavy Crossbow**: Head (Prod) + Handle (Stock) + Pommel (Mechanism).
- **Modular Elder Trident**: Head (Prongs) + Handle (Shaft) + Pommel (Counterweight).
- **Modular Kinetic Spear**: Head (Spearhead) + Handle (Long Shaft) + Pommel (Butt Cap).
- **Modular War Mace**: Head (Heavy Mace Head) + Handle (Reinforced Shaft) + Pommel (Flanged Pommel).

### 2-Part Weapons
- **Modular Longbow**: Bow Limbs + Bowstring.
- **Modular Tower Shield**: Shield Faceplate + Shield Boss.

### Weapon Tier Evolution & Perks
- Every weapon starts at **Wood Tier** (`0` kills).
- Combat kills increase the kill counter and advance the weapon through tiers:
  `Wood → Stone (15 kills) → Copper (40) → Iron (80) → Gold (150) → Diamond (300) → Netherite (600)`.
- **Specialized Combat Perks**:
  - **War Mace**: Downward fall strikes trigger seismic ground shockwaves dealing AOE damage.
  - **Longbow**: Arrows inherit limb and string elemental traits.
  - **Heavy Crossbow**: *Piercing Velocity* — bolts deal +6.0 armor-piercing direct damage and detonate a **block-safe kinetic explosion** that damages (5.0) and knocks back every creature within 4 blocks.
  - **Elder Trident**: Summons hydraulic lightning strikes in water or rain.
  - **Kinetic Spear**: Extended attack reach and +30% sprint charge damage.
  - **Tower Shield**: Reflects 35% of blocked damage back to the attacker.
  - **Broadsword**: Sweeping melee attacks chain elemental traits across adjacent foes.
- **Material-Driven Perks**: every perk above is **named and powered by the weapon's head mineral** (a Cobalt head yields *Infernal Piercing Velocity*, a Voidstone head yields *Void Piercing Velocity*), while the handle and pommel set the **Essence Focus** percentage shown in the lore. The dominant essence is channelled into the perk's primary strike, so identical weapon types forged from different minerals fight differently.

---

## ⛏ Section 5: Modular Tool Crafting

Click the **Tool Selector** in **Slot 13** to cycle between all 5 tool types:
- **Modular Pickaxe**: Head + Handle + Pommel.
- **Modular Battleaxe**: Head + Handle + Pommel.
- **Modular Excavator (Shovel)**: Head + Handle + Pommel.
- **Modular Scythe (Hoe)**: Head + Handle + Pommel.
- **Modular Fishing Rod**: Head + Handle + Pommel.

### Tool Tier Evolution & Perks
- Every tool starts at **Wood Tier** (`0` blocks broken).
- Mining blocks increases the blocks broken counter and advances the tool through tiers:
  `Wood → Stone (50 blocks) → Copper (150) → Iron (350) → Gold (750) → Diamond (1500) → Netherite (3000)`.
- **Specialized Tool Perks**:
  - **Pickaxe**: Deep Vein Resonance grants a 15% chance for extra ore drops and temporary Haste.
  - **Battleaxe**: Lumber Cleave fells entire logs and shatters mob shields on critical hits.
  - **Excavator**: Seismic Tremor excavates a 3x3 area of soil, sand, and gravel while sneaking.
  - **Scythe**: Harvest Scythe harvests 3x3 crops and automatically replants seeds from your inventory.
  - **Fishing Rod**: Abyssal Dredge gives a 15% chance to fish up rare geological minerals.

> 🔮 **Mineral Affinities**: on top of the perks above, every part contributes up to 3 trait affinities (see [Trait Affinities](Trait-Affinities.md)). They fire **offensively on weapons, as mining procs on tools and as defensive procs on armor**, so different mineral combinations genuinely play differently.

---

## 🛡 Section 6: Modular Armor Crafting (NEW!)

Click the **Armor Selector** in **Slot 13** to cycle between all 4 armor types:
- **Modular Helmet**: Armor Plate + Armor Lining + Armor Trim.
- **Modular Chestplate**: Armor Plate + Armor Lining + Armor Trim.
- **Modular Leggings**: Armor Plate + Armor Lining + Armor Trim.
- **Modular Boots**: Armor Plate + Armor Lining + Armor Trim.

### Armor Component Roles
- **Armor Plate**: Heavy protective outer plating. Determines primary defense points, base durability, and core protection traits.
- **Armor Lining**: Flexible interior chainmail mesh and padding. Determines armor toughness and secondary defense traits.
- **Armor Trim**: Reinforced fasteners, rivets, and joint buckles. Determines knockback resistance and passive utility traits.

### Armor Tier Evolution & Perks
- Every armor piece starts at **Wood Tier** (`0` damage absorbed).
- Absorbing incoming damage advances the armor piece through tiers:
  `Wood → Stone (50 dmg) → Copper (150) → Iron (350) → Gold (750) → Diamond (1500) → Netherite (3000)`.
- As armor evolves, its vanilla material transforms (Leather → Chainmail → Iron → Gold → Diamond → Netherite), enhancing baseline armor stats, toughness, and durability!
- **Specialized Armor Perks**:
  - **Helmet (Cranium Ward)**: Reduces critical headshot damage and grants hazard immunity.
  - **Chestplate (Kinetic Dampener)**: Absorbs 25% of heavy impacts and releases protective energy.
  - **Leggings (Stride Momentum)**: Mitigates sprint stamina drain and boosts movement recovery.
  - **Boots (Feathered Grounding)**: Negates up to 50% fall damage and provides anti-slip traction.
