# 🗺️ Mechanics Overview — What every block, item and feature does

One page that answers "…and what does *that* do?" for the whole plugin. Each section ends with the
page that explains it in depth.

---

## 🔁 The pipeline in six steps

1. **Extract** — the **[Prospector Brush](Archaeology.md)** brushes Overworld, Nether or End geology to
   drop raw minerals (`mvtink_*_raw`), nuggets or storage blocks.
2. **Melt** — the **[Smeltery](Smeltery-and-Casting.md)** melts raw minerals over lava or magma into
   **molten buckets**.
3. **Cast** — a carved **mold** plus a water cauldron turns molten metal into ingots, nuggets, blocks
   or tool **parts**.
4. **Alloy** — the **[Alloy Crucible](Alloy-Mixing.md)** blends two minerals into an alloy, and a
   legendary alloy with another material into a **[prime alloy](Prime-Alloys.md)**.
5. **Assemble** — the **[Forge GUI](Forge-GUI-Guide.md)** bolts parts into modular weapons, tools and
   armor.
6. **Evolve & document** — equipment climbs **7 tiers**, fires its **signature perk and animation**,
   and the **[Alloy Codex](Prime-Alloys.md)** documents every material and recipe.

---

## ⛏ The Prospector Brush — `mvtink_brush_prospector`

A right-click mining tool that extracts **geology** instead of stone. Brushing a valid surface starts a
timer (`archaeology.brushing-duration-ticks`, 30t default) and then rolls a mineral for that
dimension.

| What it does | Detail |
|---|---|
| **Valid targets** | Overworld: Stone / Cobblestone · Nether: Netherrack / Blackstone · End: End Stone |
| **Success roll** | `archaeology.success-chance` per dimension (0.45 / 0.40 / 0.35) |
| **What drops** | A raw mineral, or a nugget (`nugget-chance`), or — with the **prospector** upgrade — a whole storage **block** (`block-chance`) |
| **Which mineral** | Weighted by rarity (common 50, uncommon 30, rare 14, epic 5, legendary 1). The prospector brush **doubles** the weight of rare, epic and legendary minerals |
| **Block after** | `archaeology.block-behavior`: `DEGRADE` (stone → cobblestone → gravel → air), `COOLDOWN`, or `NONE` |
| **Anti-macro** | Every block has a `block-cooldown-seconds` (15s) per-block timer |
| **Cost** | `brush-durability-cost` durability per extraction; requires `multiversetinker.archaeology` (default **true**) |

Every mineral it can drop, with its traits, is listed in the **[Material Reference](Material-Reference.md)**.

---

## 🔥 The Smeltery — `mvtink_smeltery`

A placeable crucible that **melts** minerals. Stand over a heat source and feed it raw ore, ingots,
nuggets or blocks; it returns **molten buckets** you can pour later.

| What it does | Detail |
|---|---|
| **Heat** | Lava is the fastest source; a magma block melts `smeltery.magma-duration-multiplier` (1.30 = 30%) slower |
| **Cost** | Each completed melt has `smeltery.lava-consume-chance` (10%) to consume the lava beneath |
| **Speed** | Each mineral has its own `meltingDurationTicks` (50t for Talc up to 240t for endgame minerals) |
| **Diagnostics** | The smeltery GUI reports its current heat, progress and contents |

---

## ⚗ Casting & molds — the 13 `CastType` molds

Molds turn molten metal into a usable item. Carve them in the Forge GUI and cool them in a water
cauldron; the same mold is **reusable**.

| Mold | Turns molten metal into |
|---|---|
| Ingot / Nugget / Block cast | Standard ingots, **9 nuggets**, storage blocks |
| Head / Handle / Pommel cast | Weapon and tool **heads**, **handles (rods)** and **pommels (bindings)** |
| Bow Limbs / Bowstring mold | Bow **limbs** and **bowstrings** |
| Shield Faceplate / Shield Boss cast | Shield **plates** and **bosses** |
| Armor Plate / Lining / Trim cast | Armor **plates**, **linings** and **trims** |

Each part is a real item (`mvtink_*_head`, `_rod`, `_binding`, …) that the Forge later assembles.

---

## 🏗️ The Multiverse Forge multiblock

A **243-block** structure centred on an **anvil**, with lava corner columns, chiseled tuff bricks and
deepslate tiles (see [Forge Structure](Forge-Structure.md)). Right-click the central anvil to validate
it, activate it and open the GUI. `/mvtink forge build` constructs it instantly and
`/mvtink forge check` reports how many blocks match.

---

## 🧰 The Forge GUI — six tabs

| Tab | What it does |
|---|---|
| **1. Guides** | Interactive books: structure, multi-material forging, crucible, tiers and perks — plus the **book button** that opens the Alloy Codex |
| **2. Molds & Parts** | Carve molds from clay bricks and cast **up to 3 materials per part**; the concentration (100% / 50-50 / 33-33-33) decides how strongly each material's traits express |
| **3. Alloy Crucible** | Two input slots and one **Melt & Blend** button; yields **2 ingots** of the alloy |
| **4. Weapon Assembly** | Cycles the 7 weapon types and assembles the forged parts |
| **5. Tool Assembly** | Cycles the 5 tool types and assembles them |
| **6. Armor Assembly** | Cycles the 4 armor pieces and assembles them |

---

## 🛠️ Modular equipment

A forged item keeps its own **durability counter**, its **tier** and its **essence blend**. Vanilla
wear is always disabled (`equipment.modular-attack-damage` chooses whether the damage also comes from
the minerals) so the plugin and Minecraft never spend durability on the same swing.

### Weapons (7)

| Weapon | Parts | Signature perk | Exclusive animation |
|---|---|---|---|
| **Broadsword** | head + handle + pommel | **Sweeping Cleave** | Sweeping Arc |
| **Longbow** | limbs + bowstring | **Infused Volley** | Volley Trail |
| **Heavy Crossbow** | head + handle + pommel | **Piercing Velocity** | Piercing Lance |
| **Elder Trident** | head + handle + pommel | **Hydraulic Surge** | Surge Column |
| **Kinetic Spear** | head + handle + pommel | **Jousting Reach** | Thrust Line |
| **War Mace** | head + handle + pommel | **Seismic Smash** | Shock Ring |
| **Tower Shield** | plate + boss | **Retaliation Barrier** | Parapet Arc |

### Tools (5 + the broadsword)

| Tool | Signature perk | Exclusive animation |
|---|---|---|
| **Pickaxe** | **Vein Resonance** — bonus ores from resonating seams + temporary Haste | Vein Strike |
| **Battleaxe** | **Lumber Cleave** — fells whole trunks, shatters shields | Lumber Splash |
| **Excavator** | **Seismic Tremor** — sneak-digging clears a 3×3 area | Ground Wave |
| **Scythe** | **Harvest Scythe** — reaps a 3×3 field and replants seeds | Harvest Swirl |
| **Fishing Rod** | **Abyssal Dredge** — deep water can hook rare raw minerals | Dredge Drip |

### Armor (4)

| Piece | Signature perk | Exclusive animation |
|---|---|---|
| **Helmet** | **Cranium Ward** — reduces headshot damage, filters hazards | Cranium Halo |
| **Chestplate** | **Kinetic Dampener** — absorbs 25% of heavy impacts | Kinetic Dome |
| **Leggings** | **Stride Momentum** — mitigates sprint drain | Stride Coil |
| **Boots** | **Feathered Grounding** — negates up to 50% fall damage, keeps traction | Grounding Puff |

### The 10 parts

`HEAD` (damage, speed, primary strike) · `ROD` (durability multiplier) · `BINDING` (durability + utility)
· `BOW_LIMBS` (draw speed, velocity) · `BOWSTRING` (firing energy) · `SHIELD_PLATE` (frontal block)
· `SHIELD_BOSS` (parry stability) · `ARMOR_PLATE` (defense) · `ARMOR_LINING` (toughness) · `ARMOR_TRIM`
(knockback resistance). Legacy ids `_processed` (ingot), `_handle` (rod) and `_pommel` (binding) still
resolve.

### The 7 tiers

Equipment levels **by being used**: weapons gain XP on kills, tools on blocks broken, armor on damage
absorbed.

| Tier | Kills | Blocks | Damage absorbed | Damage multiplier | Durability multiplier |
|---|---:|---:|---:|---:|---:|
| **Wood** | 0 | 0 | 0 | 0.0 | 1.00x |
| **Stone** | 15 | 50 | 150 | 0.8 | 1.15x |
| **Copper** | 40 | 150 | 350 | 1.5 | 1.30x |
| **Iron** | 80 | 350 | 650 | 2.5 | 1.50x |
| **Gold** | 150 | 750 | 900 | 3.5 | 1.80x |
| **Diamond** | 300 | 1,500 | 1,500 | 5.0 | 2.20x |
| **Netherite ★ MAX** | 600 | 3,000 | 2,500 | 7.0 | 2.80x |

### Essences and animations

Every material teaches up to **3 deterministic essences** (Infernal, Void, Primal, Terrain, Tempered,
Radiant, Resonant, Volatile, Swift, Brutal, Bulwark, Ascendant) that behave differently on weapons,
tools and armor — see [Trait Affinities](Trait-Affinities.md). The **head** names the perk, the
**handle/pommel** set its potency, and an **80% essence focus** unlocks that essence'
**[ultimate](Essence-Ultimates.md)**.

Each of the **16 equipment types** owns an exclusive particle choreography and sound, played when its
perk actually fires and tinted with the dominant mineral's colour. Tune them with the
`animations` keys (`enabled`, `particle-scale`, `sounds`, `cooldown-millis`).

---

## 🧪 The three alloy tiers

| Tier | Inputs | Result |
|---|---|---|
| **Legendary** | Two specific minerals (16 curated recipes) | The curated alloy with its own trait |
| **Composite** | Any two blendable minerals | `<A>-<B> Alloy` with the inherited essences |
| **Prime** | A legendary alloy + (another alloy, a mineral or a catalyst) | `<A> <B> Prime`, with the catalyst's ultimate and armor state |

**Catalysts** are real vanilla items (Nether Star, Blue Ice, Echo Shard…) that are never blended alone:
they shape the endgame build. The complete list of pairs and traits lives in the
**[Alloy Recipe Index](Alloy-Recipe-Index.md)**.

---

## 📖 The Alloy Codex

A 54-slot, paginated menu open to **every player** with `/mvtink codex`, also reachable from the book
button in the Crucible tab. Its **seven sections**: Legendary Recipes · Prime Catalysts · Forged
Composites · Prime Alloys · Combination Explorer · Alloy Space Summary · **Mineral Catalog**.

The catalog has two **scopes** (**Scope** button) — the curated material list and the flat list of
**every registered item id** (2,656 today) — a **Kind** filter, and a drill-down that lists every id a
material owns, each entry spelling out the `/mvtink give <player> <id>` line.

---

## 💻 Commands & permissions

| Command | Who | What it does |
|---|---|---|
| `/mvtink` | everyone | Public help (only `codex`). With `multiversetinker.admin`: the full help |
| `/mvtink codex [player]` | codex users; another player needs admin | Opens the Alloy Codex |
| `/mvtink craft <weapon\|tool\|armor> <type> <m1> <m2> [m3] [tier]` | admin | Forges equipment instantly |
| `/mvtink give <player> <id> [amount]` | admin | Gives any registered item |
| `/mvtink forge <build\|check\|gui> [rotation]` | admin | Builds / validates / opens the Forge |
| `/mvtink verify` | admin | Diagnoses the item registry |
| `/mvtink reload` | admin | Reloads config, items and loot |

Permissions: `multiversetinker.admin` (op) · `multiversetinker.codex` (everyone) ·
`multiversetinker.archaeology` (everyone).

Tab completion offers **every registered item id** in one list and filters it as you type, and the
`mvtink_` prefix is optional everywhere — typing `tin` finds `mvtink_tin`, `mvtink_tin_ingot` and the
rest. In `/mvtink craft` the material slots accept those item ids too, so an id copied out of the
codex can be pasted straight in.

---

## ⚙️ Configuration

`archaeology` (brush timings, yields, block behaviour) · `smeltery` (lava cost, magma speed) · `lore`
(tooltip word-wrapping) · `animations` (perk choreographies) · `equipment` (modular attack damage) ·
`rarity-weights` · `messages`. Every key is documented in the
**[Configuration Reference](Configuration.md)**.

---

## 💾 What persists

Everything players discover is written to `plugins/MultiverseTinker/`: composite and prime alloys to
`dynamic-alloys.yml`, so an ingot forged last week still works as a forging component after a restart.
