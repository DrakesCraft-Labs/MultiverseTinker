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
  - **War Mace** (*Seismic Smash*): Downward fall strikes trigger seismic ground shockwaves dealing AOE damage.
  - **Longbow** (*Infused Volley*): Arrows inherit limb and string elemental traits, and a focused bow looses a **follow-up volley arrow** at the same target — base 25% chance, rising to 60% at 100% essence focus. The volley arrow carries no composition, so the perk fires exactly once per shot.
  - **Heavy Crossbow**: *Piercing Velocity* — bolts deal +6.0 armor-piercing direct damage and detonate a **block-safe kinetic explosion** that damages (5.0) and knocks back every creature within 4 blocks.
  - **Elder Trident**: Summons hydraulic lightning strikes in water or rain.
  - **Kinetic Spear**: Extended attack reach and +30% sprint charge damage.
  - **Tower Shield**: Reflects 35% of blocked damage back to the attacker.
  - **Broadsword**: Sweeping melee attacks chain elemental traits across adjacent foes.
- **Material-Driven Perks**: every perk above is **named and powered by the weapon's head mineral** (a Cobalt head yields *Infernal Piercing Velocity*, a Voidstone head yields *Void Piercing Velocity*), while the handle and pommel set the **Essence Focus** percentage shown in the lore. The dominant essence is channelled into the perk's primary strike, so identical weapon types forged from different minerals fight differently.
- **Trait Channels**: each trait row in the lore is labelled with the moment that weapon fires it — `on sweep`, `on arrow hit` (longbow), `on bolt impact`, `on surge`, `on thrust`, `on smash` and `on block` — so a broadsword, a longbow and a tower shield never show the same trait block.

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
- **Specialized Tool Perks** — the mechanic belongs to the tool type, the essence belongs to the minerals. The head part dictates the elemental identity (and therefore the perk's name and its extra effect), the handle and pommel set the Essence Focus:
  - **Pickaxe** (*Vein Resonance*): a 15% chance for extra ore drops and temporary Haste. A Cobalt head prints *Infernal Vein Resonance*, a Voidstone head *Void Vein Resonance*.
  - **Battleaxe** (*Lumber Cleave*): fells whole logs and shatters mob shields on critical hits.
  - **Excavator** (*Seismic Tremor*): excavates a 3x3 area of soil, sand, and gravel while sneaking.
  - **Scythe** (*Harvest Scythe*): harvests 3x3 crops and automatically replants seeds from your inventory.
  - **Fishing Rod** (*Abyssal Dredge*): a 15% chance to fish up rare geological minerals.
  - Each tool prints `✦ Tool Perk: <Essence> <Mechanic>` plus its own `• Essence Focus: <Essence> essence (<n>%)` line, and its trait rows are labelled with the moment they fire: `while mining`, `while chopping`, `while digging`, `while harvesting` or `while fishing`.

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
- **Specialized Armor Perks** — the slot owns the defense, the plate mineral owns its essence:
  - **Helmet** (*Cranium Ward*): reduces critical headshot damage and grants hazard immunity.
  - **Chestplate** (*Kinetic Dampener*): absorbs 25% of heavy impacts and releases protective energy.
  - **Leggings** (*Stride Momentum*): mitigates sprint stamina drain and boosts movement recovery.
  - **Boots** (*Feathered Grounding*): negates up to 50% fall damage and provides anti-slip traction.
  - A Voidstone chestplate prints *Void Kinetic Dampener* while an Infernal one prints *Infernal Kinetic Dampener*: same slot mechanic, different essence reaction, different Essence Focus. Armor trait rows are labelled `when struck`, because armor always answers a hit.

---

## ✨ Signature Animations

Perks are only half of the promise: every equipment type also owns an **exclusive animation**, played at the exact moment its perk fires. No pattern, particle pair or sound is shared between two types — a unit test fails the build if one ever is, so no family can look more finished than another.

* **Broadsword** — *Sweeping Arc*: a horizontal arc is carved through the air whenever the sweep chains a foe.
* **Longbow** — *Volley Trail*: a dotted trail links you to the arrow that just landed.
* **Heavy Crossbow** — *Piercing Lance*: a taut line of sparks spears straight through the bolt impact.
* **Elder Trident** — *Hydraulic Surge*: a rising column of water and sparks wraps the wielder.
* **Kinetic Spear** — *Jousting Thrust*: a low air-pressure lane marks the reach of the thrust.
* **War Mace** — *Seismic Smash*: a shock ring breaks outward from the point of impact.
* **Tower Shield** — *Retaliation Bulwark*: a curved rampart of ward-light rises in front of the blocker.
* **Pickaxe** — *Vein Resonance*: a vein of light runs down the face of the mined ore.
* **Battleaxe** — *Lumber Cleave*: splinters and leaves burst sideways from the felled trunk.
* **Excavator** — *Seismic Tremor*: a dust ring rides across the loosened soil.
* **Scythe** — *Harvest Swirl*: harvest sparks spiral up over the reaped crops.
* **Fishing Rod** — *Abyssal Dredge*: water and bubbles drip down the line as something is pulled up.
* **Helmet** — *Cranium Halo*: a halo of ward-light closes around your head.
* **Chestplate** — *Kinetic Dome*: a dome of dampening force swells out of the plate and swallows the blow.
* **Leggings** — *Stride Coil*: a coil of momentum spins up around the legs while striding.
* **Boots** — *Grounding Puff*: a cushion of air and frost puffs out under the soles on landing.

Every animation is **tinted with the dominant mineral colour** of the item, so the choreography belongs to the type while the hue belongs to the build. Intensity, sound and cooldown live in `config.yml` — see the **[Configuration Reference](Configuration.md)** for the full table of particles and sounds.

---

> ⚙️ **Tooltip width**: long perk and trait rows are word-wrapped so nothing is clipped off the screen. The row width, the wider header budget and the on/off switch all live in `config.yml` — see the **[Configuration Reference](Configuration.md)**.
