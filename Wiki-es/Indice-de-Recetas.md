# 🧪 Índice de Recetas — Cada par, sus números y sus efectos

El **Crisol de Aleaciones** acepta dos minerales distintos (brocha/vanilla) y produce su aleación; también puede fundir una **aleación legendaria** con otro material para forjar una **aleación primordial**. Aquí están las reglas, las **12 esencias con sus efectos** y las recetas curadas; los **pares** están repartidos en las páginas de abajo, porque son **5.995** y no caben en una sola.

## 📚 Las páginas de pares

| Página | Materiales padre | Pares |
|---|---|---:|
| **[🌍 Pares de Aleación — Padres del Overworld (1/2)](Pares-Overworld-1.md)** | 9 | 871 |
| **[🌍 Pares de Aleación — Padres del Overworld (2/2)](Pares-Overworld-2.md)** | 24 | 916 |
| **[🔥 Pares de Aleación — Padres del Nether (1/2)](Pares-Nether-1.md)** | 10 | 890 |
| **[🔥 Pares de Aleación — Padres del Nether (2/2)](Pares-Nether-2.md)** | 24 | 898 |
| **[🌌 Pares de Aleación — Padres del End (1/2)](Pares-The-End-1.md)** | 8 | 798 |
| **[🌌 Pares de Aleación — Padres del End (2/2)](Pares-The-End-2.md)** | 22 | 810 |
| **[🧱 Pares de Aleación — Padres vanilla](Pares-Vanilla.md)** | 13 | 812 |

**5995 pares** en total: son **únicos**, no repetidos. Cada uno aparece bajo el padre cuyo id va antes en orden alfabético, y cada sección de material lleva además la lista con **todos** sus compañeros.

> 🔎 **Cómo buscar:** pulsa **Ctrl+F** con el id del material (`mvtink_tin`). Si no está en la página que tienes abierta, la tabla de arriba te dice en cuál está.

## 📖 Cómo leer una fila

Ejemplo real (`mvtink_tin` + `mvtink_zinc`):

| Socio | Aleación resultante | ID | Estadísticas (dur · vel · daño) | Rasgo | Esencias |
|---|---|---|---|---|---|
| Zinc (`mvtink_zinc`) | Tin-Zinc Alloy | `mvtink_alloy_tin_zinc` | +333 · 6.7x · +2.8 | Malleable-Galvanized | [Terrain](#terrain), [Tempered](#tempered)<br><sub>ralentiza, bloques extra, aturde · +10% dano, autoreparacion, resistencia</sub> |

* **Estadísticas**: son los valores **del material de aleación** (`+durabilidad · velocidad de minado x · +daño de ataque`). Una pieza forjada con ella usa esas cifras según la fórmula de su parte (cabeza, mango, pomo…), así que la aleación es un componente, no un arma.
* **Rasgo**: el nombre del rasgo compuesto (`RasgoA-RasgoB`), que es **solo una etiqueta de sabor** heredada de los dos padres. Lo que de verdad se activa en la pieza son las esencias; las 16 legendarias, en cambio, sí traen un efecto curado propio.
* **Esencias**: hasta 3, y son las que deciden los **efectos reales**. Cada nombre enlaza a su sección de abajo y debajo viene el atajo de lo que hace en **arma / herramienta / armadura**. Escalan con la **concentración**: 100% en una parte de un solo material, 50/50 en dos, 33/33/33 en tres.

## ⚗ Las fórmulas de mezcla

Cada par se resuelve de forma determinista: se ordenan los dos ids alfabéticamente (`A` < `B`) y de ahí salen el id, el nombre, el rasgo y las estadísticas.

| Qué | Compuesta | Legendaria | Primordial |
|---|---|---|---|
| ID | `mvtink_alloy_<A>_<B>` | id curado | `mvtink_prime_<A>_<B>` |
| Nombre | `<A>-<B> Alloy` | nombre curado | `<A> <B> Prime` |
| Durabilidad | `round((durA+durB) × 0.70) + 60` | curada | `round((durA+durB) × 0.78) + 120` |
| Velocidad | `(spdA+spdB)/2 + 0.6` | curada | `(spdA+spdB)/2 + 1.1` |
| Daño | `(atkA+atkB)/2 + 1.2` | curado | `(atkA+atkB)/2 + 2.2` |
| Rasgo | `<RasgoA>-<RasgoB>` | rasgo curado | `Prime <RasgoA>` |
| Esencias | primeras 3 de `union(A, B)` por prioridad dimensional | las de ambos padres | las de ambos padres |
| Salida | 2 lingotes | 2 lingotes | 2 lingotes |

> ⚗ **El truco del catalizador:** un catalizador no aporta masa metalúrgica, así que una primordial forjada con uno se calcula como si la legendaria se hubiera fusionado consigo misma: **+2.2 de daño** y **×1.56 de durabilidad** sobre esa legendaria (Cosmic Netherite pasa de `10.5 / 1400` a `12.7 / 2304`). Los 12 catalizadores dan **los mismos números**: solo cambian el ultimate del arma y el estado de la armadura.

## 🔮 Qué hace cada esencia

Todas las filas de las páginas de pares enlazan aquí. Los efectos se aplican **distinto según el equipo** y escalan con la concentración de la esencia en la pieza.

Cada fila de las páginas de pares lleva el mismo atajo en pequeño debajo de las esencias, en el orden **arma / herramienta / armadura**.

| Esencia | ⚔ Arma | ⛏ Herramienta | 🛡 Armadura |
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

- ⚔ **En arma:** Searing hits that set foes ablaze.
- ⛏ **En herramienta:** Chance to auto-smelt excavated ores.
- 🛡 **En armadura:** Grants Fire Resistance when struck.

### Void

- ⚔ **En arma:** Warping strikes that drag foes inward.
- ⛏ **En herramienta:** Chance to rip bonus experience from stone.
- 🛡 **En armadura:** Grants Slow Falling when struck.

### Primal

- ⚔ **En arma:** Raw additional impact damage.
- ⛏ **En herramienta:** Chance for bonus natural drops.
- 🛡 **En armadura:** Grants a small Absorption shield when struck.

### Tempered

- ⚔ **En arma:** Hardened edge that boosts raw damage.
- ⛏ **En herramienta:** Chance to self-repair the tool while mining.
- 🛡 **En armadura:** Grants Resistance when struck.

### Radiant

- ⚔ **En arma:** Radiant blows that mark struck foes.
- ⛏ **En herramienta:** Grants Night Vision while excavating.
- 🛡 **En armadura:** Mends the wielder's wounds when struck.

### Resonant

- ⚔ **En arma:** Harmonic shockwaves damage nearby foes.
- ⛏ **En herramienta:** Chimes reveal the surrounding ore seams.
- 🛡 **En armadura:** Releases a concussive pulse that shoves attackers.

### Volatile

- ⚔ **En arma:** Unstable strikes that erupt in flame.
- ⛏ **En herramienta:** Chance to ignite a volatile spark for experience.
- 🛡 **En armadura:** Sets melee attackers alight.

### Terrain

- ⚔ **En arma:** Earthen blows that slow struck foes.
- ⛏ **En herramienta:** Chance for bonus excavated blocks.
- 🛡 **En armadura:** Stuns attackers with heavy Slow.

### Swift

- ⚔ **En arma:** Lightning cadence that hastens the wielder.
- ⛏ **En herramienta:** Grants Haste while mining.
- 🛡 **En armadura:** Grants Speed when struck.

### Brutal

- ⚔ **En arma:** Crushing force that hurls foes backward.
- ⛏ **En herramienta:** Chance to shatter out extra ore.
- 🛡 **En armadura:** Reflects part of the damage back at attackers.

### Bulwark

- ⚔ **En arma:** Immovable mass that hardens the wielder.
- ⛏ **En herramienta:** Chance to absorb durability wear entirely.
- 🛡 **En armadura:** Grants extra Resistance when struck.

### Ascendant

- ⚔ **En arma:** Transcendent strikes that sustain the wielder.
- ⛏ **En herramienta:** Chance for bonus experience while mining.
- 🛡 **En armadura:** Grants Regeneration when struck.

> 📚 Detalle completo, prioridades de herencia y cómo se asigna cada esencia en [Afinidades de Rasgos](Afinidades-de-Rasgos.md).

## 📜 Las 16 recetas legendarias (curadas)

Estas recetas **siempre ganan**: su par produce la aleación legendaria con su rasgo propio en lugar de una compuesta, y además conserva las esencias de ambos padres.

| Aleación | ID | Material 1 | Material 2 | Estadísticas | Rasgo | Efecto del rasgo | Esencias |
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

> La **Netherita** vanilla es especial: ya es una aleación, así que no se mezcla libremente. Solo entra en sus dos recetas curadas: `mvtink_steel` + `mvtink_netherite` (**Cinder Steel**) y `mvtink_netherite` + `mvtink_celestine` (**Cosmic Netherite**).

## 🌌 Fusiones primordiales

Una receta primordial empareja **una de las 16 legendarias** con otra legendaria, una compuesta, cualquier mineral o un catalizador. La resultante es `mvtink_prime_<A>_<B>`, se llama `<A> <B> Prime`, y su ultimate/estado lo decide el catalizador. Reglas y espectáculos completos en [Aleaciones Primordiales](Aleaciones-Primordiales.md).

| Catalizador | Item vanilla | Ultimate | Estado de armadura |
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

* [Volver al inicio de la Wiki](Home.md)
