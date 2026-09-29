# 🌌 Prime Alloys (Primes)

Prime alloys are the third and final tier of the **Alloy Crucible** (forge GUI tab 3). Where a
composite alloy fuses two minerals, a prime fuses **one of the 16 legendary alloys** with another
legendary, a composite, a mineral or a **vanilla catalyst item** — and the result is what unlocks the
cinematic endgame: freezing attacks, meteor cascades and brand new armor states.

---

## ⚗️ How a prime is forged

Same two slots as any other alloy (slots **29** and **33**), same *Ignite Crucible* button. The
crucible only accepts a pair when **at least one input is a legendary alloy** and neither input is
already a prime:

| Input A | Input B | Result |
|---|---|---|
| Legendary alloy | Legendary alloy | ✅ Prime (e.g. `mvtink_prime_bronze_manyullyn`) |
| Legendary alloy | Composite alloy | ✅ Prime |
| Legendary alloy | Mineral (brush/vanilla) | ✅ Prime |
| Legendary alloy | Vanilla catalyst item | ✅ Prime with a catalyst **sigil** |
| Mineral | Mineral | ✅ ordinary composite (or a legendary recipe) |
| Composite | Composite | ❌ rejected — a prime always needs a legendary parent |
| Catalyst | Catalyst | ❌ rejected — a catalyst cannot be used alone |
| Prime | anything | ❌ rejected — primes cannot be reforged (no infinite ladder) |

A prime is always named `<Alloy A> <Alloy B> Prime`, is typed **Legendary** rarity, gets
`0.78 × (parents durability) + 120` durability, average speed **+1.1** and average damage **+2.2** —
strictly better than a composite of the same parents. Catalysts contribute no metallurgical mass, so
a legendary + catalyst prime is built from the alloy it catalysed.

> 💡 Prime alloys are forged **per player discovery**: only the ones you actually smelt are stored in
> `plugins/MultiverseTinker/dynamic-alloys.yml`, so your crucible keeps growing between sessions.

---

## 📊 The full alloy space

| Tier | Rules | Distinct alloys |
|---|---|---|
| **Mineral alloys** | any 2 of the 110 blendable minerals | **5,995** |
| **Legendary recipes** | 16 curated fusions (14 free-form + Cinder Steel and Cosmic Netherite) | **16** |
| **Prime alloys** | one legendary + another alloy | up to **95,712** |
| | one legendary + a mineral | **1,760** |
| | one legendary + a vanilla catalyst | **192** |
| | two legendary alloys | **120** |
| **Total** | | **up to 103,781** |

A fresh server already exposes **8,085** forgeable combinations; the remaining primes appear as you
discover composites. The crucible codex (book button) reports how many you have found so far.

---

## ❖ The 12 vanilla catalysts

Drop the vanilla item itself in a crucible slot together with any legendary alloy. The catalyst's
**sigil** decides which ultimate the forged weapon casts and which state the forged armor answers with.

| Catalyst item | Material id | Ultimate it grants | Armor state it grants |
|---|---|---|---|
| Nether Star | `mvtink_catalyst_nether_star` | **Supernova** | Prime Aegis |
| Dragon Breath | `mvtink_catalyst_dragon_breath` | **Meteor Cascade** | Ember Veil |
| Blue Ice | `mvtink_catalyst_blue_ice` | **Absolute Zero** ❄ | Frostbound |
| Packed Ice | `mvtink_catalyst_packed_ice` | **Glacier Tomb** ❄ | Frostbound |
| Echo Shard | `mvtink_catalyst_echo_shard` | **Event Horizon** | Void Shell |
| Heart of the Sea | `mvtink_catalyst_heart_of_the_sea` | **Tectonic Rift** | Gravitic Anchor |
| Totem of Undying | `mvtink_catalyst_totem_of_undying` | **Prismatic Ascension** | Prime Aegis |
| End Crystal | `mvtink_catalyst_end_crystal` | **Supernova** | Stormcall |
| Respawn Anchor | `mvtink_catalyst_respawn_anchor` | **Meteor Cascade** | Ember Veil |
| Prismarine Crystals | `mvtink_catalyst_prismarine_crystals` | **Prismatic Ascension** | Prism Bulwark |
| Amethyst Cluster | `mvtink_catalyst_amethyst_cluster` | **Event Horizon** | Prism Bulwark |
| Ancient Debris | `mvtink_catalyst_ancient_debris` | **Tectonic Rift** | Tectonic Guard |

Primes built **without** a catalyst inherit the spectacle of their dominant essence instead
(Infernal → Meteor Cascade, Void → Event Horizon, Terrain/Primal → Tectonic Rift, Swift/Radiant/
Ascendant → Prismatic Ascension, Tempered/Resonant → Supernova, Brutal/Volatile → Meteor Cascade or
Ember Veil, Bulwark → Event Horizon).

---

## ⚡ Prime ultimates

Prime weapons cast on a **20 second cooldown**, exactly like essence ultimates, and they skip the 80%
essence-focus requirement — the legendary alloy is the gate. Their impact lands **1.55×–1.75×** the
triggering hit, roots the target for 60–90 ticks and pushes nearby foes away at 60% damage.

| Ultimate | Animation | Damage | Radius | Root | Extra |
|---|---|---|---|---|---|
| **Absolute Zero** | Ice field | 1.60× | 5.0 | 70 t | **400 freeze ticks** — the target freezes solid |
| **Glacier Tomb** | Ice field (cage) | 1.55× | 4.5 | 90 t | **300 freeze ticks** + frost held every 2 ticks |
| **Meteor Cascade** | Meteor storm | 1.75× | 5.0 | 70 t | 8 meteors spiral down with fire + lava trails |
| **Supernova** | Nova | 1.70× | 5.5 | 60 t | Star detonation + sonic boom ring |
| **Event Horizon** | Vortex | 1.65× | 5.5 | 80 t | Black hole drags nearby foes into the centre |
| **Tectonic Rift** | Quake | 1.65× | 5.0 | 60 t | Crust cracks and hurls everyone off their feet |
| **Prismatic Ascension** | Pillar | 1.60× | 4.5 | 60 t | Prismatic pillar also heals the wielder 4 HP |

### 🧊 The freezing spectacle (new)

`Absolute Zero` and `Glacier Tomb` use the brand new **CRYO** animation:

1. **Wind-up** — frost crawls across the ground, snow falls from above and powder-snow crackles.
2. **Impact** — **ten ice spikes** erupt in a ring around the epicentre (block-dust snowflake
   columns with blue-ice debris), a sonic boom rings out and the victim takes 300–400 freeze ticks
   plus Slowness VI.
3. **Hold** — a rotating frost cage keeps the target pinned while snowflakes spill from its body,
   re-applying 140 freeze ticks every tick.

### ☄️ The meteor storm (new)

`Meteor Cascade` uses the new **METEOR_STORM** animation: instead of three lanes, **eight meteors
spiral down** across a 16-block drop, each leaving a flame + lava trail, smoke plume and coloured
dust, with a double explosion emitting at the epicentre. Nearby enemies inside the 5-block radius
take 60% of the impact and are thrown outward.

---

## 🛡️ Prime armor states (new)

A full prime set is no longer just "more Resistance". Being hit triggers the set's own **state**,
determined by the first prime alloy in the armor composition (catalyst sigil first, essence fallback
otherwise). Each state has its own cooldown so it cannot spam.

| State | Effect when struck | Cooldown |
|---|---|---|
| **Frostbound** | Attacker gets Slowness II + 160 freeze ticks; you gain Resistance | 10 s |
| **Meteor Ward** | Burning meteors crash on the attacker for 3 damage + 3 s fire | 10 s |
| **Gravitic Anchor** | Attacker is yanked into melee range; you gain Resistance II | 8 s |
| **Prime Aegis** | Absorption II + Resistance II for you | 8 s |
| **Stormcall** | Real lightning strikes the attacker (2.5 damage); you gain Speed | 12 s |
| **Ember Veil** | Attacker burns for 6 s; you gain Fire Resistance | 6 s |
| **Void Shell** | Attacker gets Levitation II + Slowness; you gain Slow Falling and heal 1 HP | 10 s |
| **Prism Bulwark** | 2 damage reflected back, attacker knocked away; you gain Regeneration | 12 s |
| **Tectonic Guard** | Attacker is launched upward and slowed; stone debris erupts | 9 s |

Lore on a prime armor piece reads `✦ Prime State: <state> — <description>`, and every prime weapon,
tool or armor piece also shows a `✦ Prime Alloy:` line naming the ultimate and the state it carries.

---

## 📖 The Alloy Codex

Everything above is browsable in game. Open the **Alloy Codex** with `/mvtink codex` or by clicking the
book button in the Alloy Crucible tab (sneak-click it to print the totals in chat instead). The codex is
**open to every player** — it is reference material, not an admin tool — and only aiming it at somebody
else with `/mvtink codex <player>` requires the admin permission. It is a paginated 54-slot menu with seven
sections:

| Section | What it shows |
|---|---|
| **Mineral Catalog** | Every material this server knows — geological, vanilla and forged alloy — with its id, dimension, rarity, trait and essences. This is the codex replacement for the old chat listing. |
| **Legendary Recipes** | The 16 curated alloys with parents, stats, essences and curated trait. |
| **Prime Catalysts** | The 12 vanilla catalysts with the ultimate and state each one grants. |
| **Forged Composites** | Every composite this server has discovered, with parents, stats and essences. |
| **Prime Alloys** | Every legendary fusion forged so far, with its ultimate and armor state. |
| **Combination Explorer** | Pick any material and see **every** partner the crucible accepts, with the exact result name and id. |
| **Alloy Space Summary** | Mineral pairs, curated recipes, prime fusion counts and the grand total. |

The catalog has two **scopes**, toggled with the **Scope** button on the bottom row:

* **Materials** — the curated list above, one entry per material, with its trait and essences.
* **Every item** — the flat registry: all **2,656 registered ids** the server can hand out right now,
  including tool parts, casts, the smeltery, molten buckets and the legacy `_processed` / `_handle` /
  `_pommel` aliases. The **Kind** button cycles that list through every item kind (raw, ingot, nugget,
  block, molten bucket and the ten part types) so you can jump straight to, say, every raw ore.

Clicking a material in the **Materials** scope **drills into** that material and lists every id it owns,
so you can see at a glance that copper is not one item but its raw ore, ingot, nugget, block, bucket and
parts. Every registry entry spells out its own id in its lore, and clicking it prints the matching
`/mvtink give <player> <id>` line in chat — the catalog is a lookup tool, not a forge.

> 🔍 The explorer has no side effects: it previews the resulting id, name and essences without forging
> anything, so you can plan a build before spending a single ingot. Prime alloys never appear as
> partners, because they cannot be reforged.

---

## 🔗 Related pages

* [Alloy Mixing & Metallurgy](Alloy-Mixing.md) — the crucible, the 16 legendary recipes and the 5,995 mineral pairs.
* [Trait Affinities](Trait-Affinities.md) — how a prime keeps the essences of both parents.
* [Essence Ultimates](Essence-Ultimates.md) — the 12 essence spectacles that primes upgrade.
