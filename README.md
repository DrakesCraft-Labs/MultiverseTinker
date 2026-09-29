<div align="center">

# ⚒️ MultiverseTinker

**Modular Tools, Geological Archaeology, Smeltery Crucible & Metallurgy for Paper 1.21+ (Java 21)**

<p>
  <img src="https://img.shields.io/badge/Paper-1.21.11-38BDF8?style=for-the-badge&logo=minecraft&logoColor=white" alt="Paper 1.21.11"/>
  <img src="https://img.shields.io/badge/Java-21-F89820?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge" alt="GPLv3"/>
  <img src="https://img.shields.io/badge/Author-Chagui68-22C55E?style=for-the-badge" alt="Chagui68"/>
  <img src="https://img.shields.io/badge/Minerals-90_Total-purple?style=for-the-badge" alt="90 Minerals"/>
</p>

Part of **Chagui68's Sovereign Multiverse Suite** alongside [MultiverseNets](https://github.com/DrakesCraft-Labs/MultiverseNets), [MultiverseCreatures](https://github.com/DrakesCraft-Labs/MultiverseCreatures), and [MultiverseProgramming](https://github.com/DrakesCraft-Labs/MultiverseProgramming).

[📖 English Wiki](Wiki-en/Home.md) · [🏛️ Forge Multiblock](Wiki-en/Forge-Structure.md) · [⚡ Forge Traits](Wiki-en/Traits-and-Effects.md) · [🔮 Trait Affinities](Wiki-en/Trait-Affinities.md) · [📖 Wiki en Español](Wiki-es/Home.md) · [🏛️ Estructura Forja](Wiki-es/Estructura-Forja.md) · [⚡ Rasgos de Forja](Wiki-es/Rasgos-y-Efectos.md) · [🔮 Afinidades](Wiki-es/Afinidades-de-Rasgos.md) · [Español (README)](README_ES.md)

</div>

> ### 🏰 Join the Official DrakesCraft Community!
> * 🎮 **Server IP**: `mc.drakescraft.cl` *(Java 1.21.11 & Bedrock)*
> * 💬 **Official Discord**: [discord.gg/drakescraft](https://discord.gg/rv3vtXZTk7)
> * 🌐 **Web & Guides**: [web.drakescraft.cl](https://web.drakescraft.cl) — 🛒 **Store**: [web.drakescraft.cl/store](https://web.drakescraft.cl/store.html)

---

## 🌟 What is MultiverseTinker?

**MultiverseTinker** brings the modular metallurgy, custom alloys, and archaeological geology of Tinkers' Construct into modern Minecraft as a **100% standalone Paper/Purpur plugin** built natively for **Java 21** and **Paper 1.21+**.

* **Zero Worldgen Issues**: Minerals are discovered through an interactive **Geological Archaeology Brushing System** across stone, netherrack, and end stone without modifying chunk terrain generators.
* **90 Unique Geological Materials**: Balanced with **exactly 30 minerals per dimension** (Overworld, Nether, and The End).
* **5 Physical States per Material**: Every mineral features its **Raw Ore**, **Molten Liquid Bucket**, **Solid Ingot / Gem**, **Nugget**, and **Storage Block** with reversible $9\times$ crafting recipes.
* **Smeltery Crucible**: A dedicated melting station heated by **Lava** (100% speed, 10% consume chance per melt) or **Magma Block** (70% speed, infinite stability) directly beneath it, featuring dynamic interactive GUI diagnostics and distinct melting durations.
* **Water Cauldron Casting**: Reusable ceramic and iron molds (*Ingot Cast*, *Nugget Cast*, *Block Cast*) quench hot molten liquid buckets in water cauldrons with steam and cooling effects.
* **Strict Collision Protection**: Every single item, material, recipe, and PersistentDataContainer (PDC) tag is prefixed with **`mvtink_`**.

---

## ⚙️ Core Systems

### 1. 🔍 Geological Archaeology & Brushing
Extract raw mineral fragments directly from natural stone surfaces by holding right-click with a Brush:
* **Overworld (Stone, Cobblestone, Deepslate, Andesite, Diorita, Granite, Tuff)**: Yields Overworld minerals (Tin, Zinc, Silver, Ruby, Sapphire, Titanium, Platinum, etc.).
* **The Nether (Netherrack, Blackstone, Basalt)**: Yields infernal minerals (Cobalt, Ardite, Sulfur, Sanguinite, Nether Tungsten, Witherite, etc.).
* **The End (End Stone)**: Yields cosmic void minerals (Enderite, Adamantium, Adamite, Celestine, Voidstone, Cosmium, Singularite, etc.).
* **Geological Degradation**: Blocks naturally weather down over sustained excavation (`Stone -> Cobblestone -> Gravel -> Air`) with anti-macro cooldowns.
* **Normal Brush Yield**: Extracts Raw Ores (70%) or single Nuggets (30%). Cannot excavate storage blocks directly.
* **Archaeological Prospector Brush (`mvtink_brush_prospector`)**: Special survival craftable brush with **+40% faster brushing speed**, **+15% higher extraction success rate**, **2x luck for Rare/Epic/Legendary materials**, a **50% chance to conserve bristle durability**, and the ability to excavate **full mineral Storage Blocks** (20% jackpot chance), Raw Ores (55%), or 1–3 Nuggets (25%).

---

### 2. 🌋 Tinker Smeltery Crucible (`mvtink_smeltery`)
* **Placement & Thermal Heat Sources (`BlockFace.DOWN`)**:
  * **Lava**: 100% melting speed. Has a **10% chance** to consume the lava block (turning it into air with extinguishing sounds and smoke) upon finishing a melt.
  * **Magma Block**: 70% melting speed (-30% speed / takes 30% longer). Permanent, safe heat source that is never consumed.
* **Interactive Diagnostics GUI**:
  * ❌ **No Heat Source**: Status indicator turns into a Barrier explaining why the crucible cannot melt.
  * 🔥 **Heat Detected**: The crucible ignites, showing active flames, crackling sounds, and a real-time percentage progress bar indicating whether Lava (100%) or Magma Block (70%) is fueling the melt.
* **Operation**: Place raw minerals in Slot 10 and empty buckets in Slot 12. Once the material reaches its thermal melting duration, it produces a **Molten Liquid Bucket** (`mvtink_<id>_molten_bucket`).

---

### 3. 💧 Water Cauldron Casting (Solidification)
* Fill any vanilla Cauldron with Water.
* Hold a **Molten Liquid Bucket** in your main hand and a **Casting Mold** in your off-hand (or inventory):
  * **Ingot Cast** (`mvtink_cast_ingot`): Yields 1 Ingot.
  * **Nugget Cast** (`mvtink_cast_nugget`): Yields 9 Nuggets.
  * **Block Cast** (`mvtink_cast_block`): Yields 1 Storage Block.
* Right-click the Water Cauldron:
  * Generates boiling steam clouds and lava quench audio (`BLOCK_LAVA_EXTINGUISH` + anvil clink).
  * Evaporates 1 level of water from the cauldron.
  * Converts the molten bucket into an empty `BUCKET` and drops the finished solidified item.

---

### 4. 🏛️ The Multiverse Forge Multiblock & 5-Tab GUI
* **Monumental Structure ($11 \times 7 \times 11$)**:
  * Centered on an Anvil, constructed with Chiseled Tuff Bricks, Deepslate Tiles, Deepslate Bricks, Tuff Brick Slabs/Stairs, and 4 corner thermal Lava columns (243 blocks total). Supports all rotations ($0^\circ, 90^\circ, 180^\circ, 270^\circ$).
* **Validation Particle Sweep & Ambient Aura**:
  * Completed forges feature a multi-phase validation particle sweep and continuous volcanic embers/smoke orbiting the anvil.
* **Redesigned 5-Section GUI**:
  * **[1. Codex & Guide]**: In-game encyclopedias covering multiblock structure, casting, alloy recipes, tier progression, and specialized perks.
  * **[2. Molds & Parts]**: Quick mold carving (1 Clay Brick = 1 reusable cast) and multi-material forging (place 1 to 3 materials for 100%, 50/50, or 33/33/33 concentration-based trait splitting!).
  * **[3. Alloy Crucible]**: Blend **any 2 distinct brush-extracted or vanilla minerals** into a unique alloy. 16 legendary recipes (Bronze, Electrum, Manyullyn, Cosmic Netherite, etc.) keep curated abilities; every other pair synthesizes its own dynamic composite alloy. Finished alloys cannot be re-blended.
  * **[4. Weapon Assembly]**: Assemble 7 weapon types (Broadsword, Longbow, Heavy Crossbow, Elder Trident, Kinetic Spear, War Mace, Tower Shield) starting at **Wood Tier** and leveling up through **Combat Kills**!
  * **[5. Tool Assembly]**: Assemble 5 tool types (Pickaxe, Battleaxe, Excavator/Shovel, Scythe/Hoe, Fishing Rod) starting at **Wood Tier** and leveling up through **Blocks Broken**!
* **Specialized Weapon & Tool Perks**:
  * **War Mace**: Downward fall strikes trigger seismic ground shockwaves dealing AOE damage.
  * **Longbow**: Arrows inherit limb and string elemental traits.
  * **Heavy Crossbow**: Bolts trigger a real, block-safe kinetic explosion that damages and knocks back every creature in a 4-block radius, plus +6.0 armor-piercing direct damage.
  * **Elder Trident**: Water/rain strikes summon hydraulic lightning (+5.0 damage).
  * **Kinetic Spear**: Extended attack reach and +30% charge damage while sprinting.
  * **Tower Shield**: Reflects 35% blocked damage back to attackers.
  * **Battleaxe (Axe)**: Lumber Cleave fells the whole connected tree trunk and shatters enemy shields.
  * **Pickaxe**: Vein Resonance grants bonus ores and Haste I.
  * **Excavator (Shovel)**: Sneak-digging excavates a 3x3 area of soil/sand/gravel.
  * **Scythe (Hoe)**: Harvests 3x3 mature crops and auto-replants seeds from your inventory.
  * **Fishing Rod**: Abyssal Dredge has a 15% chance to hook rare raw Multiverse minerals.
  * **Modular Armor**: Helmet hazard warding, Chestplate kinetic dampening (25% heavy-impact absorption), Leggings stride momentum and Boots fall-damage halving.
  * **Material-Driven Perks**: every perk above is **named and powered by the weapon's head mineral** (Cobalt → *Infernal Piercing Velocity*, Voidstone → *Void Piercing Velocity*, Diamond → *Radiant Sweeping Cleave*) while the handle and pommel set the **Essence Focus** shown in the lore; the dominant essence is channelled into the perk's primary strike.
* **Deterministic Trait Affinities**: Every mineral and vanilla ore resolves to up to 3 of the 12 essences (Infernal, Void, Primal, Tempered, Radiant, Resonant, Volatile, Terrain, Swift, Brutal, Bulwark, Ascendant). They behave **offensively on weapons, as mining procs on tools and as defensive procs on armor**, and alloys inherit both parents' essences — so every mineral combination owns its own unique functionality. See [Trait Affinities](Wiki-en/Trait-Affinities.md).

---

## 🍳 Survival Crafting Recipes

| Item | Grid (3×3) | Ingredients |
|---|---|---|
| **Smeltery Crucible** | <pre>S F S<br/>M B M<br/>S S S</pre> | S = Smooth Stone · F = Blast Furnace · M = Magma Block · B = Bucket |
| **Prospector Brush** | <pre>· G ·<br/>C B C<br/>· R ·</pre> | G = Gold Ingot · C = Copper Ingot · B = Brush · R = Amethyst Shard |
| **Ingot Cast** | <pre>B B B<br/>B · B<br/>B B B</pre> | B = Clay Brick (hollow center) |
| **Nugget Cast** | <pre>B · B<br/>· C ·<br/>B · B</pre> | B = Clay Brick · C = Clay Ball |
| **Block Cast** | <pre>B B B<br/>B I B<br/>B B B</pre> | B = Clay Brick · I = Iron Block |
| **Tool Head Cast** | <pre>B G B<br/>B · B<br/>B B B</pre> | B = Clay Brick · G = Gold Ingot |
| **Tool Rod Cast** | <pre>B C B<br/>B · B<br/>B · B</pre> | B = Clay Brick · C = Copper Ingot |
| **Tool Binding Cast** | <pre>B I B<br/>· C ·<br/>B B B</pre> | B = Clay Brick · I = Iron Ingot · C = Clay Ball |

---

## 💻 Commands & Permissions

* `/mvtink forge build [0|90|180|270]` — Construct the complete multiblock Forge structure at your location.
* `/mvtink forge check` — Validate the targeted anvil and display structure match percentage and diagnostics.
* `/mvtink forge gui` — Open the custom Multiverse Forge GUI directly.
* `/mvtink give <player> <mvtink_id> [amount]` — Give any item (raw, ingot, nugget, block, molten bucket, tool parts, casts, smeltery, prospector brush).
* `/mvtink list [OVERWORLD|NETHER|THE_END]` — Inspect all 90 registered materials, colors, origins, and traits.
* `/mvtink reload` — Reload configuration, items, and loot tables.

**Permissions:**
* `multiversetinker.admin` — Access to `/mvtink` administrative commands (default: `op`).
* `multiversetinker.archaeology` — Allows using brushes for geological extraction (default: `true`).

---

## 🛠️ Build & Compilation

```bash
mvn clean package
```

Built and verified for **Paper / Purpur 1.21+** with **Java 21**.

---

<div align="center">

**DrakesCraft Labs** · Engineered by **Chagui68**  
License: **GPL-3.0**

</div>
