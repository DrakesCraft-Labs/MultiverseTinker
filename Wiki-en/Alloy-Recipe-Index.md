# 🧪 Alloy Recipe Index — Every pair, its numbers and its effects

The **Alloy Crucible** accepts two distinct brush/vanilla minerals and produces their alloy; it can also fuse a **legendary alloy** with another material to forge a **prime alloy**. This page holds the rules, the **12 essence effects** and the curated recipes; the **pairs** live on the pages below, because there are **5,995** of them and they do not fit on one page.

## 📚 The pair pages

| Page | Parent materials | Pairs |
|---|---|---:|
| **[🌍 Alloy Pairs — Overworld parents (1/2)](Alloy-Pairs-Overworld-1.md)** | 9 | 871 |
| **[🌍 Alloy Pairs — Overworld parents (2/2)](Alloy-Pairs-Overworld-2.md)** | 24 | 916 |
| **[🔥 Alloy Pairs — Nether parents (1/2)](Alloy-Pairs-Nether-1.md)** | 10 | 890 |
| **[🔥 Alloy Pairs — Nether parents (2/2)](Alloy-Pairs-Nether-2.md)** | 24 | 898 |
| **[🌌 Alloy Pairs — The End parents (1/2)](Alloy-Pairs-The-End-1.md)** | 8 | 798 |
| **[🌌 Alloy Pairs — The End parents (2/2)](Alloy-Pairs-The-End-2.md)** | 22 | 810 |
| **[🧱 Alloy Pairs — Vanilla parents](Alloy-Pairs-Vanilla.md)** | 13 | 812 |

**5995 pairs** in total, and they are **unique** — never repeated. Each one appears under the parent whose id comes first alphabetically, and every material section also lists **all** of its partners.

> 🔎 **How to search:** press **Ctrl+F** with the material's id (`mvtink_tin`). If it is not on the page you have open, the table above tells you which one holds it.

## 📖 How to read a row

A real example (`mvtink_tin` + `mvtink_zinc`):

| Partner | Resulting alloy | ID | Stats (dur · speed · damage) | Trait | Essences |
|---|---|---|---|---|---|
| Zinc (`mvtink_zinc`) | Tin-Zinc Alloy | `mvtink_alloy_tin_zinc` | +333 · 6.7x · +2.8 | Malleable-Galvanized | [Terrain](#terrain), [Tempered](#tempered)<br><sub>slow, extra blocks, stun · +10% dmg, self-repair, resistance</sub> |

* **Stats**: the **alloy material's** own values (`+durability · mining speed x · +attack damage`). A piece forged from it uses those numbers through its own part formula (head, handle, pommel…), so the alloy is a component, not a weapon.
* **Trait**: the composite trait name (`TraitA-TraitB`), which is **only a flavour label** inherited from both parents. What actually triggers on the piece are the essences; the 16 legendary recipes do carry a bespoke curated effect.
* **Essences**: up to 3, and they decide the **real effects**. Every name links to its section below, and the small line under it is the shorthand of what it does on **weapon / tool / armor**. They scale with **concentration**: 100% for a single-material part, 50/50 for two, 33/33/33 for three.

## ⚗ The blending formulas

Every pair resolves deterministically: the two ids are sorted alphabetically (`A` < `B`) and the id, name, trait and stats follow from that order.

| What | Composite | Legendary | Prime |
|---|---|---|---|
| ID | `mvtink_alloy_<A>_<B>` | curated id | `mvtink_prime_<A>_<B>` |
| Name | `<A>-<B> Alloy` | curated name | `<A> <B> Prime` |
| Durability | `round((durA+durB) × 0.70) + 60` | curated | `round((durA+durB) × 0.78) + 120` |
| Speed | `(spdA+spdB)/2 + 0.6` | curated | `(spdA+spdB)/2 + 1.1` |
| Damage | `(atkA+atkB)/2 + 1.2` | curated | `(atkA+atkB)/2 + 2.2` |
| Trait | `<TraitA>-<TraitB>` | curated trait | `Prime <TraitA>` |
| Essences | first 3 of `union(A, B)` by dimensional priority | both parents' | both parents' |
| Output | 2 ingots | 2 ingots | 2 ingots |

> ⚗ **The catalyst trick:** a catalyst carries no metallurgical mass, so a prime forged with one is computed as if the legendary had been fused with itself: **+2.2 damage** and **×1.56 durability** over that legendary (Cosmic Netherite goes from `10.5 / 1400` to `12.7 / 2304`). All 12 catalysts give **the same numbers**: they only change the weapon's ultimate and the armor's state.

## 🔮 What each essence does

Every row on the pair pages links here. The effects apply **differently per equipment family** and scale with the essence's concentration in the piece.

Every pair-page row carries the same shorthand in small print under its essences, in **weapon / tool / armor** order.

| Essence | ⚔ Weapon | ⛏ Tool | 🛡 Armor |
|---|---|---|---|
| **Infernal** | Searing hits that set foes ablaze. | Chance to auto-smelt excavated ores. | Grants Fire Resistance when struck. |
| **Void** | Warping strikes that drag foes inward. | Chance to rip bonus experience from stone. | Grants Slow Falling when struck. |
| **Primal** | Raw additional impact damage. | Chance for bonus natural drops. | Grants a small Absorption shield when struck. |
| **Tempered** | Hardened edge that boosts raw damage. | Chance to self-repair the tool while mining. | Grants Resistance when struck. |
| **Radiant** | Radiant blows that mark struck foes. | Grants Night Vision while excavating. | Mends the wielder's wounds when struck. |
| **Resonant** | Harmonic shockwaves damage nearby foes. | Chimes reveal the surrounding ore seams. | Releases a concussive pulse that shoves attackers. |
| **Volatile** | Unstable strikes that erupt in flame. | Chance to ignite a volatile spark for experience. | Sets melee attackers alight. |
| **Terrain** | Earthen blows that slow struck foes. | Chance for bonus excavated blocks. | Stuns attackers with heavy Slow. |
| **Swift** | Lightning cadence that hastens the wielder. | Grants Haste while mining. | Grants Speed when struck. |
| **Brutal** | Crushing force that hurls foes backward. | Chance to shatter out extra ore. | Reflects part of the damage back at attackers. |
| **Bulwark** | Immovable mass that hardens the wielder. | Chance to absorb durability wear entirely. | Grants extra Resistance when struck. |
| **Ascendant** | Transcendent strikes that sustain the wielder. | Chance for bonus experience while mining. | Grants Regeneration when struck. |

### Infernal

- ⚔ **On weapons:** Searing hits that set foes ablaze.
- ⛏ **On tools:** Chance to auto-smelt excavated ores.
- 🛡 **On armor:** Grants Fire Resistance when struck.

### Void

- ⚔ **On weapons:** Warping strikes that drag foes inward.
- ⛏ **On tools:** Chance to rip bonus experience from stone.
- 🛡 **On armor:** Grants Slow Falling when struck.

### Primal

- ⚔ **On weapons:** Raw additional impact damage.
- ⛏ **On tools:** Chance for bonus natural drops.
- 🛡 **On armor:** Grants a small Absorption shield when struck.

### Tempered

- ⚔ **On weapons:** Hardened edge that boosts raw damage.
- ⛏ **On tools:** Chance to self-repair the tool while mining.
- 🛡 **On armor:** Grants Resistance when struck.

### Radiant

- ⚔ **On weapons:** Radiant blows that mark struck foes.
- ⛏ **On tools:** Grants Night Vision while excavating.
- 🛡 **On armor:** Mends the wielder's wounds when struck.

### Resonant

- ⚔ **On weapons:** Harmonic shockwaves damage nearby foes.
- ⛏ **On tools:** Chimes reveal the surrounding ore seams.
- 🛡 **On armor:** Releases a concussive pulse that shoves attackers.

### Volatile

- ⚔ **On weapons:** Unstable strikes that erupt in flame.
- ⛏ **On tools:** Chance to ignite a volatile spark for experience.
- 🛡 **On armor:** Sets melee attackers alight.

### Terrain

- ⚔ **On weapons:** Earthen blows that slow struck foes.
- ⛏ **On tools:** Chance for bonus excavated blocks.
- 🛡 **On armor:** Stuns attackers with heavy Slow.

### Swift

- ⚔ **On weapons:** Lightning cadence that hastens the wielder.
- ⛏ **On tools:** Grants Haste while mining.
- 🛡 **On armor:** Grants Speed when struck.

### Brutal

- ⚔ **On weapons:** Crushing force that hurls foes backward.
- ⛏ **On tools:** Chance to shatter out extra ore.
- 🛡 **On armor:** Reflects part of the damage back at attackers.

### Bulwark

- ⚔ **On weapons:** Immovable mass that hardens the wielder.
- ⛏ **On tools:** Chance to absorb durability wear entirely.
- 🛡 **On armor:** Grants extra Resistance when struck.

### Ascendant

- ⚔ **On weapons:** Transcendent strikes that sustain the wielder.
- ⛏ **On tools:** Chance for bonus experience while mining.
- 🛡 **On armor:** Grants Regeneration when struck.

> 📚 Full detail, inheritance priority and how each essence is assigned in [Trait Affinities](Trait-Affinities.md).

## 📜 The 16 legendary recipes (curated)

These recipes **always win**: their pair produces the legendary alloy with its own curated trait instead of a composite, and it keeps both parents' essences.

| Alloy | ID | Material 1 | Material 2 | Stats | Trait | Trait effect | Essences |
|---|---|---|---|---|---|---|---|
| **Adamant Steel** | `mvtink_adamant_steel` | Adamantium (`mvtink_adamantium`) | Titanium (`mvtink_titanium`) | +1600 · 11.5x · +8.5 | Unbreakable Will | Indomitable metallurgy. 80% chance to completely ignore durability consumption. | [Void](#void), [Terrain](#terrain), [Tempered](#tempered) |
| **Astral Brass** | `mvtink_astral_brass` | Pyrite (`mvtink_pyrite`) | Astralite (`mvtink_astralite`) | +500 · 8.5x · +6 | Starlight Grace | Infused with cosmic dust. Grants permanent Feather Falling and radiant starlight particles. | [Void](#void), [Terrain](#terrain), [Tempered](#tempered) |
| **Bronze** | `mvtink_bronze` | Copper (`mvtink_copper`) | Tin (`mvtink_tin`) | +350 · 7.5x · +5.5 | Dense Temper | High structural density. Grants +350 Durability and -20% knockback received. | [Primal](#primal), [Terrain](#terrain), [Tempered](#tempered) |
| **Cinder Steel** | `mvtink_cinder_steel` | Steel (`mvtink_steel`) | Netherite (`mvtink_netherite`) | +1100 · 10x · +9 | Hellfire Core | Forged in nether magma. Ignites foes for 8 seconds and renders item fireproof. | [Infernal](#infernal), [Primal](#primal), [Tempered](#tempered) |
| **Cosmic Netherite** | `mvtink_cosmic_netherite` | Netherite (`mvtink_netherite`) | Celestine (`mvtink_celestine`) | +1400 · 12x · +10.5 | Cosmic Gravity | Singularity-infused netherite. Melee strikes pull surrounding foes within 6 blocks together. | [Void](#void), [Primal](#primal), [Tempered](#tempered) |
| **Electrum** | `mvtink_electrum` | Gold (`mvtink_gold`) | Silver (`mvtink_silver`) | +220 · 11x · +6 | Lightning Conduit | Conductive precious alloy. +25% attack speed and sparks shock damage on critical hits. | [Primal](#primal), [Terrain](#terrain), [Tempered](#tempered) |
| **Ender Brass** | `mvtink_ender_brass` | Redstone (`mvtink_redstone`) | Enderite (`mvtink_enderite`) | +650 · 8.5x · +7.5 | Phase Step | Resonant spatial conductor. Shift-Right-Click teleports player forward 10 blocks. | [Void](#void), [Primal](#primal), [Tempered](#tempered) |
| **Glacial Silver** | `mvtink_glacial_silver` | Silver (`mvtink_silver`) | Cryolite (`mvtink_cryolite`) | +480 · 8x · +6.5 | Absolute Frost | Sub-zero cryo-metal. Freezes targets with Slowness III and powder-snow frostbite for 4s. | [Terrain](#terrain), [Tempered](#tempered) |
| **Hellfire Bismuth** | `mvtink_hellfire_bismuth` | Bismuth (`mvtink_bismuth`) | Fire Opal (`mvtink_fire_opal`) | +550 · 8x · +8 | Combustion | Volatile crystalline metal. Critical hits trigger miniature non-destructive thermal explosions. | [Infernal](#infernal), [Terrain](#terrain), [Tempered](#tempered) |
| **Invar** | `mvtink_invar` | Iron (`mvtink_iron`) | Nickel (`mvtink_nickel`) | +450 · 8x · +6.5 | Thermal Resilience | Low thermal expansion. Completely immune to fire wear and grants +450 Durability. | [Primal](#primal), [Terrain](#terrain), [Tempered](#tempered) |
| **Manyullyn** | `mvtink_manyullyn` | Cobalt (`mvtink_cobalt`) | Ardite (`mvtink_ardite`) | +800 · 10.5x · +9 | Insatiable | Deep Nether blood alloy. Consecutive strikes ramp up attack damage by +1.0 (stacks to +5.0). | [Infernal](#infernal), [Tempered](#tempered), [Swift](#swift) |
| **Prismatic Quartz** | `mvtink_prismatic_quartz` | Nether Quartz (`mvtink_quartz`) | Amethyst (`mvtink_amethyst`) | +400 · 9x · +7 | Resonance Shock | Harmonic crystal matrix. Striking produces an acoustic wave dealing 2.5 AOE damage. | [Primal](#primal), [Resonant](#resonant) |
| **Rose Gold** | `mvtink_rose_gold` | Gold (`mvtink_gold`) | Copper (`mvtink_copper`) | +280 · 9x · +5 | Midas Sparkle | Opulent blend. Increases experience orbs gained from mining and combat by +40%. | [Primal](#primal), [Tempered](#tempered), [Swift](#swift) |
| **Sanguine Gold** | `mvtink_sanguine_gold` | Gold (`mvtink_gold`) | Sanguinite (`mvtink_sanguinite`) | +380 · 9.5x · +7.5 | Vampiric Touch | Cursed lifedrinking gold. Restores 25% of all melee damage dealt as player health. | [Infernal](#infernal), [Primal](#primal), [Tempered](#tempered) |
| **Shadow Platinum** | `mvtink_shadow_platinum` | Platinum (`mvtink_platinum`) | Obsidianite (`mvtink_obsidianite`) | +750 · 9x · +8 | Umbral Veil | Light-absorbing noble alloy. Sneaking grants brief invisibility and +50% backstab damage. | [Infernal](#infernal), [Terrain](#terrain), [Tempered](#tempered) |
| **Void Damascus** | `mvtink_void_damascus` | Tungsten (`mvtink_tungsten`) | Voidstone (`mvtink_voidstone`) | +950 · 9.5x · +9.5 | Abyssal Cleave | Folded space-metal. True armor piercing attacks that bypass 30% of target defense. | [Infernal](#infernal), [Void](#void), [Terrain](#terrain) |

> Vanilla **Netherite** is special: it is already an alloy, so it never blends freely. It only enters its two curated recipes: `mvtink_steel` + `mvtink_netherite` (**Cinder Steel**) and `mvtink_netherite` + `mvtink_celestine` (**Cosmic Netherite**).

## 🌌 Prime fusions

A prime recipe pairs **one of the 16 legendary alloys** with another legendary, a composite, any mineral or a catalyst. The result is `mvtink_prime_<A>_<B>`, named `<A> <B> Prime`, and the catalyst decides its ultimate/state. Full rules and spectacles in [Prime Alloys](Prime-Alloys.md).

| Catalyst | Vanilla item | Ultimate | Armor state |
|---|---|---|---|
| `mvtink_catalyst_nether_star` | Nether Star (`nether_star`) | Supernova — The alloy detonates like a star, blasting everything nearby away from the epicentre. | Prime Aegis — Spins up a hardened aegis of absorption and resistance when struck. |
| `mvtink_catalyst_dragon_breath` | Dragon Breath (`dragon_breath`) | Meteor Cascade — A burning storm of meteors rains over the whole area and buries the target. | Ember Veil — Sets the attacker ablaze and wraps you in fire resistance. |
| `mvtink_catalyst_blue_ice` | Blue Ice (`blue_ice`) | Absolute Zero — The air turns to glass: ice spikes erupt around the target and it freezes solid. | Frostbound — Answers every hit with a freezing blast that slows and frostbites the attacker. |
| `mvtink_catalyst_packed_ice` | Packed Ice (`packed_ice`) | Glacier Tomb — A glacier slams shut around the target, locking it inside a tomb of frost. | Frostbound — Answers every hit with a freezing blast that slows and frostbites the attacker. |
| `mvtink_catalyst_echo_shard` | Echo Shard (`echo_shard`) | Event Horizon — A black hole opens and drags nearby foes into the crushing centre. | Void Shell — Lifts the attacker off the ground and lets you fall like a feather. |
| `mvtink_catalyst_heart_of_the_sea` | Heart of the Sea (`heart_of_the_sea`) | Tectonic Rift — The crust splits and the shockwave hurls every nearby foe off their feet. | Gravitic Anchor — Pulls attackers into melee range and hardens you against knockback. |
| `mvtink_catalyst_totem_of_undying` | Totem of Undying (`totem_of_undying`) | Prismatic Ascension — A prismatic pillar descends, transfixing the target and mending the wielder. | Prime Aegis — Spins up a hardened aegis of absorption and resistance when struck. |
| `mvtink_catalyst_end_crystal` | End Crystal (`end_crystal`) | Supernova — The alloy detonates like a star, blasting everything nearby away from the epicentre. | Stormcall — Calls lightning down on the attacker and hastens your own step. |
| `mvtink_catalyst_respawn_anchor` | Respawn Anchor (`respawn_anchor`) | Meteor Cascade — A burning storm of meteors rains over the whole area and buries the target. | Ember Veil — Sets the attacker ablaze and wraps you in fire resistance. |
| `mvtink_catalyst_prismarine_crystals` | Prismarine Crystals (`prismarine_crystals`) | Prismatic Ascension — A prismatic pillar descends, transfixing the target and mending the wielder. | Prism Bulwark — Reflects part of the blow back at the attacker and slowly mends your wounds. |
| `mvtink_catalyst_amethyst_cluster` | Amethyst Cluster (`amethyst_cluster`) | Event Horizon — A black hole opens and drags nearby foes into the crushing centre. | Prism Bulwark — Reflects part of the blow back at the attacker and slowly mends your wounds. |
| `mvtink_catalyst_ancient_debris` | Ancient Debris (`ancient_debris`) | Tectonic Rift — The crust splits and the shockwave hurls every nearby foe off their feet. | Tectonic Guard — Erupts a slab of bedrock that knocks the attacker prone. |

---

* [Back to the wiki home](Home.md)
