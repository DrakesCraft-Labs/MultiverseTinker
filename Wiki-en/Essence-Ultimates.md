# ⚡ Essence Ultimates

Every modular weapon carries an **essence ultimate**: a cinematic attack bound to the dominant
essence of the minerals it was forged from (see [Trait Affinities](Trait-Affinities.md)).

An ultimate is not an ordinary proc. The first strike that qualifies locks the enemy in place while a
**three-phase spectacle** plays out over roughly two seconds:

1. **Wind-up** — a ring swells around the target and streaks climb skyward.
2. **Impact** — meteors fall, a light pillar descends, a vortex implodes or a cage slams shut, and a
   heavy blow lands on the target plus a shockwave on everything nearby.
3. **Root hold** — the enemy stays pinned inside an animated cage, unable to move or be knocked back.

---

## 🎯 Trigger conditions

| Requirement | Value |
|---|---|
| Weapon type | Any modular weapon (sword, bow, crossbow, spear, trident, mace, shield) |
| **Essence focus** | **≥ 80%** — the share of the weapon's forged mass carrying its identity essence |
| Cooldown | **20 seconds** per player (the action bar announces the cast) |
| Damage | **1.20x – 1.45x** the triggering hit on the target, **60%** of it on everything in radius |
| Root | 40 – 60 ticks (2 – 3 seconds) of heavy Slowness with velocity locked |

Reach 80% focus by forging the head, handle and pommel from minerals of the *same* essence: a pure
mineral build sits at 100%, while an exotic head with a common handle only reaches 50% and will not
cast. The **Essence Focus** line in the weapon lore tells you exactly where you stand.

---

## 📜 The twelve ultimates

| Essence | Ultimate | Animation | Root | Radius | Damage |
|---|---|---|---|---|---|
| **Infernal** | Meteor Storm | Meteor storm | 3.0s | 4.0 | 1.40x |
| **Void** | Singularity Collapse | Vortex | 3.0s | 4.0 | 1.35x |
| **Primal** | Primal Outburst | Nova | 2.0s | 3.5 | 1.30x |
| **Tempered** | Tempered Slam | Quake | 2.3s | 3.5 | 1.25x |
| **Radiant** | Radiant Judgement | Light pillar | 2.5s | 3.5 | 1.30x |
| **Resonant** | Harmonic Overload | Nova | 2.0s | 4.0 | 1.30x |
| **Volatile** | Volatile Cataclysm | Meteors | 2.0s | 4.0 | 1.45x |
| **Terrain** | Earthen Grasp | Quake | 2.8s | 4.0 | 1.30x |
| **Swift** | Gale Cyclone | Vortex | 2.0s | 3.5 | 1.25x |
| **Brutal** | Crushing Impact | Quake | 2.5s | 4.0 | 1.40x |
| **Bulwark** | Bastion Cage | Cage | 3.0s | 3.0 | 1.20x |
| **Ascendant** | Ascendant Descension | Light pillar | 2.5s | 3.5 | 1.35x |

Each ultimate also picks its own particle palette from the essence colour and its own charge/impact
sound, so a Nether-forged crossbow and an End-forged crossbow look and sound nothing alike.
**Ascendant** additionally heals the wielder for 4 health on impact.

---

## 🧪 Forging for ultimates

| Build | Essence focus | Ultimate |
|---|---|---|
| Cobalt head + cobalt handle + cobalt pommel | 100% | ✅ Infernal Meteor Shower |
| Cobalt head + iron handle + iron pommel | 50% | ❌ too diluted |
| Cobalt head + cobalt handle + iron pommel | 83% | ✅ Infernal Meteor Shower |
| Any alloy | inherits both parents | ✅ if the blend carries one essence through every part |

Alloy parents unfold into their two minerals at half potency, so an alloy forged from two Nether
minerals is still fully Infernal — which makes alloy weapons the most reliable way to reach 100%
focus while keeping curated abilities like *Phase Step* or *Hellfire Core*.

---

## 🌌 Prime ultimates

Prime alloys add a second family of spectacles. They sit **above** the essence ultimates: a weapon
forged with a prime alloy skips the 80% focus requirement (the legendary fusion is the gate instead),
hits for **1.55×–1.75×** and reuses the same 20 second cooldown.

| Prime ultimate | Animation | Damage | Root | Freeze | Source catalyst |
|---|---|---|---|---|---|
| **Absolute Zero** | **Ice field** ❄ | 1.60x | 70 t | **400 t** | Blue Ice |
| **Glacier Tomb** | **Ice field** ❄ | 1.55x | 90 t | **300 t** | Packed Ice |
| **Meteor Cascade** | **Meteor storm** ☄ | 1.75x | 70 t | — | Dragon Breath / Respawn Anchor |
| **Supernova** | Nova | 1.70x | 60 t | — | Nether Star / End Crystal |
| **Event Horizon** | Vortex | 1.65x | 80 t | — | Echo Shard / Amethyst Cluster |
| **Tectonic Rift** | Quake | 1.65x | 60 t | — | Heart of the Sea / Ancient Debris |
| **Prismatic Ascension** | Pillar | 1.60x | 60 t | — | Totem of Undying / Prismarine Crystals |

### ❄️ Freezing: the CRYO animation

The frost attacks wind up with snow falling and frost crawling across the floor, then **ten ice spikes
erupt in a ring** around the epicentre — block dust of blue ice and packed ice, snowflake columns and
a glass-shatter plus powder-snow crash. The victim takes **300–400 freeze ticks** (mobs fully freeze
over) with Slowness VI, and during the hold phase a rotating frost cage re-applies 140 freeze ticks
every tick while snowflakes pour off the body.

### ☄️ Meteor cascade: the METEOR_STORM animation

Instead of three lanes, **eight meteors** spiral down from 16 blocks, each leaving a flame and lava
trail plus a smoke plume, converging into a double explosion that throws nearby enemies outward and
splashes coloured dust across a 5-block radius.

Full details, the 12 vanilla catalysts and the 9 new prime armor states live in
**[Prime Alloys](Prime-Alloys.md)**.
