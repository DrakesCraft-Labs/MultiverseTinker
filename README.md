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

[📖 English Wiki](Wiki-en/Home.md) · [⚡ Forge Traits](Wiki-en/Traits-and-Effects.md) · [📖 Wiki en Español](Wiki-es/Home.md) · [⚡ Rasgos de Forja](Wiki-es/Rasgos-y-Efectos.md) · [Español (README)](README_ES.md)

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

## 🍳 Survival Crafting Recipes

| Item | Grid (3×3) | Ingredients |
|---|---|---|
| **Smeltery Crucible** | <pre>S F S<br/>M B M<br/>S S S</pre> | S = Smooth Stone · F = Blast Furnace · M = Magma Block · B = Bucket |
| **Prospector Brush** | <pre>· G ·<br/>C B C<br/>· R ·</pre> | G = Gold Ingot · C = Copper Ingot · B = Brush · R = Amethyst Shard |
| **Ingot Cast** | <pre>B B B<br/>B · B<br/>B B B</pre> | B = Clay Brick (hollow center) |
| **Nugget Cast** | <pre>B · B<br/>· C ·<br/>B · B</pre> | B = Clay Brick · C = Clay Ball |
| **Block Cast** | <pre>B B B<br/>B I B<br/>B B B</pre> | B = Clay Brick · I = Iron Block |

---

## 💻 Commands & Permissions

* `/mvtink give <player> <mvtink_id> [amount]` — Give any mineral (raw, ingot, nugget, block, molten bucket), cast, smeltery, or prospector brush.
* `/mvtink list [OVERWORLD|NETHER|THE_END]` — Inspect all registered materials, colors, origins, and traits.
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
