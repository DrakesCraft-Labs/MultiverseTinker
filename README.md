<div align="center">

<img src="https://raw.githubusercontent.com/DrakesCraft-Labs/MultiverseTinker/main/banner.svg" width="100%" alt="MultiverseTinker — animated forge banner" />

# ⚒️ MultiverseTinker

**Modular Tools, Geological Archaeology, Smeltery Crucible & Metallurgy for Paper 1.21.11, 26.1 & 26.2**

<p>
  <img src="https://img.shields.io/badge/Paper-1.21.11_·_26.1_·_26.2-38BDF8?style=for-the-badge&logo=minecraft&logoColor=white" alt="Paper 1.21.11 · 26.1 · 26.2"/>
  <img src="https://img.shields.io/badge/Java-21+-F89820?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21+"/>
  <a href="https://github.com/DrakesCraft-Labs/MultiverseTinker/actions/workflows/tests.yml"><img src="https://img.shields.io/github/actions/workflow/status/DrakesCraft-Labs/MultiverseTinker/tests.yml?branch=main&style=for-the-badge&label=Tests" alt="Tests"/></a>
  <img src="https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge" alt="GPLv3"/>
  <img src="https://img.shields.io/badge/Author-Chagui68-22C55E?style=for-the-badge" alt="Chagui68"/>
  <img src="https://img.shields.io/badge/Minerals-90_Total-purple?style=for-the-badge" alt="90 Minerals"/>
</p>

Part of **Chagui68's Sovereign Multiverse Suite** alongside [MultiverseNets](https://github.com/DrakesCraft-Labs/MultiverseNets), [MultiverseCreatures](https://github.com/DrakesCraft-Labs/MultiverseCreatures), and [MultiverseProgramming](https://github.com/DrakesCraft-Labs/MultiverseProgramming).

[📖 English Wiki](Wiki-en/Home.md) · [🗺️ Mechanics Overview](Wiki-en/Mechanics-Overview.md) · [🧩 Material Reference](Wiki-en/Material-Reference.md) · [🧪 Alloy Recipe Index](Wiki-en/Alloy-Recipe-Index.md) · [🏛️ Forge Multiblock](Wiki-en/Forge-Structure.md) · [⚡ Forge Traits](Wiki-en/Traits-and-Effects.md) · [🔮 Trait Affinities](Wiki-en/Trait-Affinities.md) · [📖 Wiki en Español](Wiki-es/Home.md) · [🗺️ Resumen de Mecánicas](Wiki-es/Resumen-de-Mecanicas.md) · [🧩 Referencia de Materiales](Wiki-es/Fuentes-de-Materiales.md) · [🧪 Índice de Recetas](Wiki-es/Indice-de-Recetas.md) · [🏛️ Estructura Forja](Wiki-es/Estructura-Forja.md) · [⚡ Rasgos de Forja](Wiki-es/Rasgos-y-Efectos.md) · [🔮 Afinidades](Wiki-es/Afinidades-de-Rasgos.md) · [Español (README)](README_ES.md)

</div>

> ### 🏰 Join the Official DrakesCraft Community!
> * 🎮 **Server IP**: `mc.drakescraft.cl` *(Java 1.21.11 & Bedrock)*
> * 💬 **Official Discord**: [discord.gg/drakescraft](https://discord.gg/rv3vtXZTk7)
> * 🌐 **Web & Guides**: [web.drakescraft.cl](https://web.drakescraft.cl) — 🛒 **Store**: [web.drakescraft.cl/store](https://web.drakescraft.cl/store.html)

---

## 🌟 What is MultiverseTinker?

**MultiverseTinker** brings the modular metallurgy, custom alloys, and archaeological geology of Tinkers' Construct into modern Minecraft as a **100% standalone Paper/Purpur plugin**. A single jar runs on **Paper 1.21.11** (Java 21) and on **Paper 26.1 and 26.2** (Java 25).

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
* **Redesigned 6-Section GUI**: a colour-themed frame per section, live previews (part, crucible fusion with pedigree and art, perk), smart shift-click routing, a pedigree guide and close button in the footer, and results that are never overwritten:
  * **[1. Codex & Guide]**: In-game encyclopedias covering multiblock structure, casting, alloy recipes, tier progression, and specialized perks.
  * **[2. Molds & Parts]**: Quick mold carving (1 Clay Brick = 1 reusable cast) and three-material forging (every part takes 3 materials, 33/33/33 — repeat a mineral for a pure part), with a live **part preview**.
  * **[3. Alloy Crucible]**: Blend **any 2 distinct brush-extracted or vanilla minerals** into a unique alloy — 110 blendable minerals and **5,995 mineral pairs**. All **16 legendary recipes** (Bronze, Electrum, Manyullyn, Void Damascus, Cosmic Netherite, etc.) are craftable; every other pair synthesizes its own dynamic composite alloy. A fresh server exposes **8,085** forgeable combinations. Vanilla netherite only blends inside its own two curated recipes. Every composite you forge is saved to `dynamic-alloys.yml` and restored on restart, so old ingots keep working.
  * **[3b. Prime Alloys]**: Fuse a **legendary alloy** with a second alloy, a mineral or one of the **12 vanilla catalysts** (Nether Star, Blue Ice, Echo Shard, Dragon Breath, Heart of the Sea…) to forge a **prime alloy** — Legendary rarity, boosted stats, and its own cinematic ultimate plus a brand new armor state. With every composite discovered the crucible reaches **103,781 distinct alloys**.
  * **[4. Weapon Assembly]**: Assemble 7 weapon types (Broadsword, Longbow, Heavy Crossbow, Elder Trident, Kinetic Spear, War Mace, Tower Shield) starting at **Wood Tier** and leveling up through **Combat Kills**!
  * **[5. Tool Assembly]**: Assemble 5 tool types (Pickaxe, Battleaxe, Excavator/Shovel, Scythe/Hoe, Fishing Rod) starting at **Wood Tier** and leveling up through **Blocks Broken**!
  * **[6. Armor Assembly]**: Assemble 4 armor types (Helmet, Chestplate, Leggings, Boots) from Plate, Lining and Trim parts, leveling up through **Damage Absorbed**.
* **Specialized Weapon & Tool Perks**:
  * **War Mace (Seismic Smash)**: Downward fall strikes trigger seismic ground shockwaves dealing AOE damage.
  * **Longbow (Infused Volley)**: Arrows inherit limb and string elemental traits, and a focused bow looses a **follow-up volley arrow** at the same target (25% base chance, up to 60% at full essence focus).
  * **Heavy Crossbow (Piercing Velocity)**: Bolts trigger a real, block-safe kinetic explosion that damages and knocks back every creature in a 4-block radius, plus +6.0 armor-piercing direct damage.
* **✦ Signature Arts & Forge Pedigree**: every alloy forged into a weapon awakens a **named ability with its own mechanic and animation**, and the harder the alloy was to reach, the bigger it gets. Each of the **5,981 composites** plays its own fusion art, read through both of its minerals — named after both epithets (*Verdant-Rubicund Fault*), the first mineral's payload in the second mineral's shape, drawn in both colours, and echoing both minerals' own forge traits on every foe it reaches; each of the **16 legendary alloys** owns a hand-made art — Bronze drops a *Bell of the First Age*, Electrum chains a *Thunderchain Conduit*, Void Damascus tears an armor-piercing *Abyssal Rend*, Cosmic Netherite triggers a *Gravity Collapse*; primes play that art **ascended** with an overlay from their second ingredient, and **mythic** primes (legendary + catalyst or legendary + legendary) chain two arts and end in a finale of thunder, glowing obelisks and an on-screen title. Legendary-and-above weapons wear a pedigree aura, and ultimates scale with the pedigree too. See [Signature Arts](Wiki-en/Signature-Arts.md).
* **Cinematic Attack Spectacles**:
  * **12 essence ultimates** triggered by a focused weapon, each with its own particles, sounds, root and damage multiplier.
  * **7 prime ultimates**: **Absolute Zero** and **Glacier Tomb** erupt **ten ice spikes** in a ring and freeze the victim for **300–400 freeze ticks**, while **Meteor Cascade** spirals **8 meteors** down in flame and lava. Supernova, Event Horizon, Tectonic Rift and Prismatic Ascension complete the set.
* **New Armor States (9)**: Frostbound, Meteor Ward, Gravitic Anchor, Prime Aegis, Stormcall, Ember Veil, Void Shell, Prism Bulwark and Tectonic Guard — prime armor answers every hit with its own reaction (freezing blasts, meteor wards, lightning, reflected damage…).
  * **Elder Trident (Hydraulic Surge)**: Water/rain strikes summon hydraulic lightning (+5.0 damage).
  * **Kinetic Spear (Jousting Reach)**: Extended attack reach and +30% charge damage while sprinting.
  * **Tower Shield (Retaliation Barrier)**: Reflects 35% blocked damage back to attackers.
  * **Broadsword (Sweeping Cleave)**: Sweeping strikes hit multiple adjacent foes and chain elemental traits.
  * **Battleaxe (Lumber Cleave)**: Fells the whole connected tree trunk and shatters enemy shields.
  * **Pickaxe (Vein Resonance)**: Grants bonus ores and Haste I on resonating seams.
  * **Excavator (Seismic Tremor)**: Sneak-digging excavates a 3x3 area of soil/sand/gravel.
  * **Scythe (Harvest Scythe)**: Harvests 3x3 mature crops and auto-replants seeds from your inventory.
  * **Fishing Rod (Abyssal Dredge)**: 15% chance to hook rare raw Multiverse minerals.
  * **Modular Armor**: four distinct slot defenses — Helmet (**Cranium Ward**) headshot mitigation and hazard immunity, Chestplate (**Kinetic Dampener**) heavy-impact absorption, Leggings (**Stride Momentum**) sprint recovery and Boots (**Feathered Grounding**) fall-damage negation. Each mitigation share is rolled with the piece — 30% / 25% / 50% on a bare slot, up to 65% / 60% / 75% as its Defense and Toughness grow, and the lore prints the share it applies.
  * **Essence Ultimates**: a weapon with **≥80% essence focus** unleashes a cinematic ultimate tied to its essence — meteor showers, singularities, light pillars or bastion cages — pinning the enemy in place for 2-3s while the animation plays (20s cooldown). See [Essence Ultimates](Wiki-en/Essence-Ultimates.md).
  * **Material-Driven Perks**: every perk above — weapons, tools and armor alike — is **named after every mineral it was forged from** and **powered by the essence of its head / plate part** — each part contributes the epithet of its own mineral ahead of the type's mechanic, so a Borax · Amethyst · Gold sword prints *Fluxforged Resonant Auric Sweeping Cleave* while the same sword with a Diamond pommel prints *Fluxforged Resonant Adamant Sweeping Cleave* (Cobalt pickaxe → *Lightfooted … Vein Resonance*, Voidstone chestplate → *Warping … Kinetic Dampener*). The head/plate mineral still owns the **identity essence** and the handle and pommel (or lining and trim) set the **Essence Focus** shown in the lore; the dominant essence is channelled into the perk's primary strike, and the [139 epithets](Wiki-en/Perk-Names.md) are the words that name it. Each material trait row is also labelled with the moment it fires — `on sweep`, `on arrow hit`, `on bolt impact`, `on surge`, `on thrust`, `on smash`, `on block`, `while mining`, `while chopping`, `while digging`, `while harvesting`, `while fishing` or `when struck` for armor — so a broadsword, a longbow and a pair of boots never print the same trait block.
  * **Perk Preview**: the weapon, tool and armor assembly tabs print the perk the parts already in their slots would forge — in slot **48**, directly under the assemble anvil — so a build can be read before its parts are spent. The tooltip breaks the compound epithet down per part (`Borax → Fluxforged`, `Amethyst → Resonant`, `Gold → Auric`), names the mechanic of the chosen type from the first click, and adds the full mechanic sentence and the **Essence Focus** the build would reach once every part is in place. It is the same name the forged item prints, so a combination can be checked against the lore before it costs a single ingot.
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
* `/mvtink craft <weapon|tool|armor> <type> <m1> <m2> [m3] [tier]` — Forge any modular equipment instantly, without the multiblock Forge. Every material argument accepts either a material id (`cobalt`) or any of its item ids (`mvtink_cobalt_ingot`, `cobalt_block`), so an id copied straight out of the codex works as it is; tab completion offers all of them. Admin-only.
* `/mvtink give <player> <mvtink_id> [amount]` — Give any item (raw, ingot, nugget, block, molten bucket, tool parts, casts, smeltery, prospector brush). The `mvtink_` prefix is optional, ids are resolved on demand, and composite/prime alloys forged after startup are givable too. Tab completion hands out **every registered id in one go** — all 2,600+ of them, item kinds and legacy aliases included — and filters as you type, with the `mvtink_` prefix optional on both sides. A **crucible pair id** copied out of the codex works even when nobody has smelted that pair yet: the command forges and registers that alloy exactly like the crucible would — under the same rules, so a pair the crucible would refuse is still refused — and reports the alloy it made and what the crucible would produce for a curated recipe. An item kind rides along, so `…_ingot` gives the ingot of the new alloy. Admin-only.
* `/mvtink codex [player]` — Open the browsable **Alloy Codex GUI**: the **Mineral Catalog**, which browses either the curated **material list** (every material with its id, dimension, rarity, trait and essences) or the flat **every item id** scope (all **2,656** ids the registry hands out today — parts, casts, smeltery, buckets and legacy aliases — optionally narrowed with the **Kind** filter, and clicking a material opens the **Combination Explorer** on it, while shift-clicking drills into every id it owns, each entry spelling out its id for `/mvtink give`). Finding one mineral among the 139 is two clicks: the three filter buttons — **Dimension**, **Rarity** and **Essence** — open a picker that lists every accepted value next to how many materials it would leave, they **stack** with each other and with Kind, and a combination that matches nothing says so instead of showing a blank grid. Legendary recipes, prime catalysts, forged composites and primes, a combination explorer and the totals. **Open to every player** (targeting another player is admin-only) and also reachable in-game from the book button in the Alloy Crucible tab — sneak-click that button for the totals in chat.
* `/mvtink verify` — Diagnose the item registry: registered materials, item kinds per material, distinct ids and a full resolvability check (every material × every kind). Admin-only.
* `/mvtink reload` — Reload configuration, items, and loot tables. Admin-only.

> `/mvtink` is the plugin's only command name and it registers **no aliases** — nothing else will ever respond to it.

**Permissions:**
* `multiversetinker.admin` — The umbrella: access to **every** administrative `/mvtink` subcommand (`craft`, `give`, `forge`, `verify`, `reload`) and to aiming the codex at another player (default: `op`, so operators hold it out of the box). This one is **never configurable**: `/mvtink codex` is the only command players are meant to have.
* `multiversetinker.admin.craft`, `.give`, `.forge`, `.verify`, `.reload` — One node per administrative subcommand, so a server can hand out **a single slice** of the administration instead of all of it (default: `op`). A player granted only `multiversetinker.admin.give` can give items and nothing else; the umbrella still grants all five, and the plugin honours that itself, so the answer is the same with LuckPerms, a vanilla `permissions.yml` or no permissions plugin at all.
* `multiversetinker.forge` — Allows using the multiverse Forge, its GUI, the Alloy Crucible and the casting cauldron (default: `true`).
* `multiversetinker.codex` — Allows opening the Alloy Codex, the public reference menu (default: `true`).
* `multiversetinker.archaeology` — Allows using brushes for geological extraction (default: `true`).

Every one of those surfaces can also be opened to everyone or restricted to operators **from `config.yml`**, so a server never has to install a permissions plugin just to decide who may forge. See [Access control](#-who-may-use-what-access).

---

## ⚙️ Configuration

`config.yml` is written to the plugin data folder on first startup with the shipped defaults. No restart is needed to apply a change: edit the file and run `/mvtink reload`.

### 📜 Item lore presentation (`lore`)

Minecraft renders every lore row as a single line and clips anything wider than the tooltip area, which used
to cut off weapon perks and trait descriptions. MultiverseTinker word-wraps long lore rows instead, keeping their
colours, gradients and decorations intact. Only rows that overflow are split — short rows keep their exact
formatting.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `lore.wrap-long-lines` | boolean | `true` | Split long lore rows at word boundaries. Set to `false` to keep the old single-row output. |
| `lore.max-line-width-pixels` | integer | `190` | Width budget of a normal lore row, in default-font pixels (a lowercase glyph averages ~6 px; the vanilla tooltip area is ~200 px). Lower it for narrower tooltips with more rows, raise it for wider rows. Values under `60` are clamped. |
| `lore.header-line-width-pixels` | integer | `320` | Wider budget for the two header rows of modular equipment (tier tag + progress bar). Those rows are rewritten in place on every level-up, so they must keep the same row count. Clamped to at least `max-line-width-pixels`. |

```yaml
lore:
  wrap-long-lines: true
  max-line-width-pixels: 190
  header-line-width-pixels: 320
```

Wrapping is **measured, not counted**: the row width is estimated glyph by glyph with the default Minecraft font,
and wide glyphs (bullets, bars, stars) are deliberately over-estimated so text wraps one word early rather than
clipping. Continuation rows get a hanging indent, so `✦ ` and `• ` bullets keep lining up under their text.

### ✨ Perk animations (`animations`)

Every weapon, tool and armor type owns an **exclusive choreography** — its own particle geometry, its own particle pair and its own signature sound. The animation plays when that piece's perk actually fires (a broadsword sweep that chains a foe, a longbow arrow that lands, a mace smash, a helmet that takes a hit…) and is tinted with the colour of the dominant mineral the item was forged from. Two types can never share a signature: a test fails the build if they do.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `animations.enabled` | boolean | `true` | Play the perk choreographies. |
| `animations.particle-scale` | number | `1.0` | Particle-count multiplier for every animation, clamped to `0.25` – `3.0` (strong servers can raise it, weak clients can lower it). |
| `animations.sounds` | boolean | `true` | Play the signature sound of each animation. |
| `animations.cooldown-millis` | integer | `400` | Minimum delay between two animations of the same type on the same player, so fast procs (sweeps, chestplate hits) cannot strobe. |

```yaml
animations:
  enabled: true
  particle-scale: 1.0
  sounds: true
  cooldown-millis: 400
```

| Equipment | Animation | Pattern | Sound |
| --- | --- | --- | --- |
| Broadsword | Sweeping Arc | `SWEEP_ATTACK` | `ENTITY_PLAYER_ATTACK_SWEEP` |
| Longbow | Volley Trail | `CRIT` | `ENTITY_ARROW_SHOOT` |
| Crossbow | Piercing Lance | `ELECTRIC_SPARK` | `ITEM_CROSSBOW_SHOOT` |
| Trident | Hydraulic Surge | `SPLASH` | `ITEM_TRIDENT_RIPTIDE_1` |
| Spear | Jousting Thrust | `CLOUD` | `ENTITY_PLAYER_ATTACK_STRONG` |
| War Mace | Seismic Smash | `EXPLOSION` | `ITEM_MACE_SMASH_GROUND_HEAVY` |
| Tower Shield | Retaliation Bulwark | `ENCHANTED_HIT` | `ITEM_SHIELD_BLOCK` |
| Pickaxe | Vein Resonance | `ENCHANTED_HIT` | `BLOCK_AMETHYST_BLOCK_CHIME` |
| Battleaxe | Lumber Cleave | `CRIT` | `BLOCK_WOOD_BREAK` |
| Excavator | Seismic Tremor | `CLOUD` | `BLOCK_GRAVEL_BREAK` |
| Scythe | Harvest Swirl | `HAPPY_VILLAGER` | `ITEM_CROP_PLANT` |
| Fishing Rod | Abyssal Dredge | `BUBBLE` | `ENTITY_FISHING_BOBBER_SPLASH` |
| Helmet | Cranium Halo | `END_ROD` | `BLOCK_AMETHYST_BLOCK_RESONATE` |
| Chestplate | Kinetic Dome | `ENCHANTED_HIT` | `BLOCK_ANVIL_LAND` |
| Leggings | Stride Coil | `CLOUD` | `ENTITY_PHANTOM_FLAP` |
| Boots | Grounding Puff | `SNOWFLAKE` | `BLOCK_POWDER_SNOW_BREAK` |

### ⚔️ Modular equipment rules (`equipment`)

Forged weapons, tools and armor carry their **own durability counter**, so vanilla wear is disabled: the item is unbreakable for the server (with the flag hidden, no "Unbreakable" row in the tooltip) and the remaining durability is reported by the item's own `• Durability: current / max` lore row, which turns green → yellow → red as the piece wears down. A sword can therefore never break on a vanilla schedule while its modular counter sits untouched.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `equipment.modular-attack-damage` | boolean | `true` | `true` makes a weapon hit for the attack damage rolled from its minerals (the value printed in its lore). `false` keeps the base vanilla material's damage; durability stays modular either way. |
| `equipment.modular-armor-defense` | boolean | `true` | `true` makes armor defend with the **Defense**, **Toughness** and knockback resistance rolled from its minerals and its evolution tier (the values printed in its lore). `false` keeps the vanilla protection of the tier material. Already-forged pieces are refreshed when their tier evolves. |
| `equipment.armor-perk.scale-per-point` | decimal | `0.02` | Mitigation one point of rolled Defense and Toughness buys a slot's armor perk. `0` turns the perk into a flat share per slot. |
| `equipment.armor-perk.floors.helmet` | decimal | `0.30` | Share the Cranium Ward starts at, before a single rolled point is bought. |
| `equipment.armor-perk.floors.chestplate` | decimal | `0.25` | Starting share of the Kinetic Dampener. |
| `equipment.armor-perk.floors.boots` | decimal | `0.50` | Starting share of the Feathered Grounding. |
| `equipment.armor-perk.caps.helmet` | decimal | `0.65` | Ceiling of the Cranium Ward's share. |
| `equipment.armor-perk.caps.chestplate` | decimal | `0.60` | Ceiling of the Kinetic Dampener's share. |
| `equipment.armor-perk.caps.boots` | decimal | `0.75` | Ceiling of the Feathered Grounding's share. |

```yaml
equipment:
  modular-attack-damage: true
  modular-armor-defense: true
  armor-perk:
    scale-per-point: 0.02
    floors:
      helmet: 0.30
      chestplate: 0.25
      boots: 0.50
    caps:
      helmet: 0.65
      chestplate: 0.60
      boots: 0.75
```

**Armor perks** (Cranium Ward, Kinetic Dampener, Feathered Grounding) answer a hit with a share of it, and that share grows with the piece's roll: it starts at the slot's floor, and every point of Defense and Toughness above what the bare slot rolls buys the configured step, up to the cap of the slot. The step, the floors and the caps are all yours to retune on `/mvtink reload`, so a server can make the perks matter more or less without a rebuild. Values are clamped — a negative step is treated as no scaling, a share above `1.0` as `1.0` — and a cap below a slot's floor pins that slot at the cap, because what the file says wins. **Leggings** are not on the list: Stride Momentum answers with mobility, so it has no curve to tune. A slot left out of `floors` or `caps` keeps the value the plugin ships with, so deleting a key restores it.

A retune takes effect at once for everything that is **fought or forged** from that moment on, but a piece forged earlier keeps the percentage already written into its lore until it evolves or is forged again — that number is part of the item. So after a retune, expect pieces forged before it to still print the old share until they are re-forged.

When the rule is on, the strike keeps its critical hits, strength and enchantments: the vanilla contribution is measured from the player's live attack-damage attribute and only the base is swapped, so the multipliers still scale the forged damage.

**Armor** works the same way. The piece is built on a vanilla armor item, so the server would otherwise grant the protection of that base material — a diamond-plated helmet used to defend exactly like the tier it happened to be made of. With the rule on, those modifiers are replaced by the rolled **Defense** (from the plate), **Toughness** (from the lining) and **knockback resistance** (from the trim), bound to the slot the piece is worn in, and the vanilla attribute tooltip is hidden so the numbers are not printed twice. The evolution tier is part of the roll, so a piece re-arms with stronger numbers as it levels up.

### 🔐 Who may use what (`access`)

Whether the codex, the forge or the brush is open to everyone is a server decision, not necessarily a permissions-plugin one. Each surface carries a **mode**, and the plugin reads it from `config.yml`, so a server with no permissions plugin still decides who may forge:

| Mode | Who gets in |
| --- | --- |
| `public` | **Everyone**, without consulting any permission node. |
| `op` | **Server operators only** — for a server that runs without a permissions plugin. |
| `permission` | The node declared in `plugin.yml`, so LuckPerms, PermissionsEx or a vanilla `permissions.yml` can narrow it further. |

| Key | Type | Default | What it controls |
| --- | --- | --- | --- |
| `access.codex` | string | `public` | Opening the Alloy Codex — both `/mvtink codex` and the book button in the crucible tab. |
| `access.forge` | string | `public` | The multiblock Forge, its GUI, the Alloy Crucible and the casting cauldron. |
| `access.archaeology` | string | `public` | Brushing a valid geological block for minerals. |

```yaml
access:
  codex: public
  forge: public
  archaeology: public
```

The administrative `/mvtink` subcommands are deliberately **not** in that table. Giving items, forging equipment, counting the registry and reloading the plugin are administrator work, so each of them always requires a node: the umbrella `multiversetinker.admin` grants all five, while `multiversetinker.admin.craft`, `.give`, `.forge`, `.verify` and `.reload` grant one subcommand each — enough to hand an event host the item giver without the reload. Aiming the codex at another player stays with the umbrella. Operators hold the umbrella (and every slice) by default and a permissions plugin can grant either the umbrella or a single node to a player it trusts. `/mvtink codex` is the only command meant for players, and `access.codex` is what decides whether they get it. A config that still carries `access.admin-commands` is ignored rather than obeyed, and the plugin says so in the log.

Anything unrecognised (a typo such as `flase`) **falls back to the default** instead of locking the server out of its own forge, and the values a server owner is likely to write are accepted: `everyone` and `all` mean `public`, `ops` and `admin` mean `op`, `node` means `permission`. Refusals are configurable too — `messages.access-denied.codex`, `.forge` and `.archaeology` word the surfaces above, and `.admin-commands` words the refusal a player meets on a command only administrators may run. All four are MiniMessage strings, so a Spanish server can phrase them its own way.

The mode applies **before** the permission node: with `access.archaeology: op`, even a player holding `multiversetinker.archaeology` is turned away, and the refusal is the configured message. The opposite is equally true: `access.archaeology: public` never consults the node at all. A plain vanilla anvil is left untouched for players who are not allowed to use the Forge — only the recognised multiblock answers to the rule.

Two rules are called out on startup **and** on `/mvtink reload`, because neither is something a player would report:

* `UNUSABLE` — a surface set to `op` on a server with **no operators at all** can never be reached, so the warning names the key and the surface (closing `access.codex` that way is reported as having closed every `/mvtink` command players had). Give someone the operator flag, or move the mode back to `public`.
* `DANGEROUS` — a leftover `access.admin-commands` left at a value that would have opened the administrative subcommands (`public`, `everyone`, `all`). It is ignored, but that is the value that used to hand every player the item giver and the instant forger, so it is worth deleting.

### 🧭 Other sections

| Section | Purpose |
| --- | --- |
| `access` | Who may use the configurable surfaces — the codex, the forge (GUI, crucible and casting) and archaeology — as `public`, `op` or `permission`. The administrative commands are not configurable: they always need a node — the umbrella `multiversetinker.admin`, or its subcommand nodes to grant a single slice. |
| `archaeology` | Brushing system: enable flag, brushing duration, brush durability cost, per-dimension success chance, block degradation behaviour, anti-macro cooldown and brush yields. |
| `animations` | Signature perk animations: enable flag, particle multiplier, sound toggle and per-type cooldown. |
| `equipment` | Modular equipment rules: whether a forged weapon fights with its rolled attack damage and whether armor defends with the protection rolled from its minerals, or the vanilla material values instead (vanilla wear is always disabled). |
| `smeltery` | Crucible tuning: lava consumption chance and the Magma Block heat-source slowdown multiplier. |
| `rarity-weights` | Rarity weights of the archaeology drop table (`common` … `legendary`). Relative, so any numbers work; `0` takes a rarity off geology entirely, and an all-zero section falls back to the shipped values. |
| `messages` | Chat and action bar texts (MiniMessage format) for brushing, cooldowns and the refusal of each surface. |

---

## 🌐 Interactive Alloy Site

**[drakescraft-labs.github.io/MultiverseTinker](https://drakescraft-labs.github.io/MultiverseTinker/)** — the whole material catalog in a browser, in **English and Spanish**, switched with one button. Browse the 139 shipped materials with their forge stats, trait channels, essences, perk epithets, legendary recipes and catalyst ultimates, and pick any **two materials** to see exactly what the Alloy Crucible would forge from them: name, id, durability, mining speed, damage and inherited essences.

The same page carries a **build calculator**: choose a weapon, a tool or an armor piece, the mineral of each of its parts and its evolution tier, and read the numbers the Forge would give the item — final durability and attack damage, mining speed for a tool, and Defense, Toughness and knockback resistance for a piece of armor. The arithmetic is the plugin's own, ported to the browser rather than guessed, and the tier ladder and the part names come from the same generated file as the catalog.

The page is a static site in [`docs/`](docs), deployed by the [`pages` workflow](.github/workflows/pages.yml). Its data file is **generated from the registries** and guarded the same way the wiki is: `./mvnw -o test -Dtest=SiteDataTest` fails when the committed `docs/data/alloys.json` drifts from the plugin (regenerate with `-Dmvtink.site.write=true`), and `node docs/js/selfcheck.mjs` replays pairs the real crucible forged and builds the Forge really assembled through the page's own math, so the browser preview can never promise an alloy or a stat the plugin would not produce.

---

## ✅ Supported Versions

One jar, every supported server — there is no per-version download.

| Server | Minecraft | Java on the server | Status |
|---|---|---|---|
| Paper / Purpur | **1.21.11** | 21+ | ✅ Supported (minimum) |
| Paper / Purpur | **26.1** (26.1.1, 26.1.2) | 25+ | ✅ Supported |
| Paper / Purpur | **26.2** | 25+ | ✅ Supported |

The jar is compiled against the oldest supported API (Paper 1.21.11, Java 21 bytecode), so it loads unchanged on the newer servers; `plugin.yml` declares `api-version: 1.21.11`, so Paper refuses it on anything older instead of failing at runtime. The few calls Paper has marked for removal in 26.x go through [`ServerCompat`](src/main/java/com/chagui68/multiversetinker/compat/ServerCompat.java), which picks the newest form the running server offers.

---

## 🛠️ Build & Compilation

```bash
./mvnw clean package
```

Builds `target/MultiverseTinker-v<version>.jar` against Paper 1.21.11 with **JDK 21** or newer. To build and test against a newer API, pick its profile (these need **JDK 25**, the Java Paper 26.x is published for):

```bash
./mvnw clean test -Pmc-26.1
./mvnw clean test -Pmc-26.2
```

### Continuous integration

Every push and pull request runs the [`Tests` workflow](.github/workflows/tests.yml) on GitHub Actions:

* **Build** — JDK 21 builds the release jar against Paper 1.21.11 and runs the whole MockBukkit suite.
* **Compatibility** — JDK 25 runs the suite on Paper 26.1 and 26.2 twice: on the exact 1.21.11 bytecode that ships in the jar (it must still link against the newer API) and recompiled from source.
* **Real server** — boots real Paper **1.21.11**, **26.1.2** and **26.2** servers with the built jar, runs `/mvtink verify` and `/mvtink reload` from the console, and fails on any error or linkage problem the plugin logs.

Tests live in `src/test/java/com/chagui68/multiversetinker/`, in one folder per subsystem they cover — `alloys`, `items`, `tools`, `forge`, `archaeology`, `commands`, `materials`, `evolution`, `access` and `compat` — plus `wiki` and `site` for the generated pages and the JSON behind them. Run them all with `./mvnw -o test`, or a single class by its simple name with `./mvnw -o test -Dtest=SiteDataTest`, whatever folder it sits in.

---

<div align="center">

**DrakesCraft Labs** · Engineered by **Chagui68**  
License: **GPL-3.0**

</div>

---

## 📄 License & Sovereign Authorship

Copyright © 2026 [**Chagui68**](https://github.com/Chagui68) · [**DrakesCraft Labs**](https://github.com/DrakesCraft-Labs).

This project is an **original sovereign creation** engineered by **Chagui68** for the DrakesCraft network. All intellectual authorship belongs to Chagui68. Commercial resale, repackaging in paid setups, or removing creator attribution is strictly prohibited.
