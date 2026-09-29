# 🧪 Índice de Recetas — Cada par y cada rasgo

El **Crisol de Aleaciones** acepta dos minerales distintos (brocha/vanilla) y produce su aleación; también puede fundir una **aleación legendaria** con otro material para forjar una **aleación primordial**. Esta página enumera **todos** los pares válidos, la aleación que producen y las **esencias/rasgos** heredados. La operación paso a paso está en [Mezcla de Materiales](Mezcla-de-Materiales.md).

## ⚗ Las fórmulas de mezcla

Cada par se resuelve de forma determinista: se ordenan los dos ids alfabéticamente (`A` < `B`) y de ahí salen el id, el nombre, el rasgo y las estadísticas.

| Qué | Fórmula | Ejemplo (Tin + Zinc) |
|---|---|---|
| ID | `mvtink_alloy_<A>_<B>` | `mvtink_alloy_tin_zinc` |
| Nombre | `<A>-<B> Alloy` | Tin-Zinc Alloy |
| Rasgo | `<RasgoA>-<RasgoB>` | `Malleable-Galvanized` |
| Durabilidad | `round((durA + durB) × 0.70) + 60` | ver ejemplo |
| Velocidad | `(spdA + spdB) / 2 + 0.6` | ver ejemplo |
| Daño | `(atkA + atkB) / 2 + 1.2` | ver ejemplo |
| Esencias | primeras 3 de `union(A, B)` por prioridad dim. | ver cada fila |
| Salida | 2 lingotes de la aleación | 2x |

## 📜 Las 16 recetas legendarias (curadas)

Estas recetas **siempre ganan**: su par produce la aleación legendaria con su rasgo propio en lugar de una compuesta, y además conservan las esencias de ambos padres. Estadísticas completas en [Mezcla de Materiales](Mezcla-de-Materiales.md).

| Aleación | ID | Material 1 | Material 2 | Rasgo | Esencias | Durabilidad | Velocidad | Daño |
|---|---|---|---|---|---|---:|---:|---:|
| **Adamant Steel** | `mvtink_adamant_steel` | Adamantium (`mvtink_adamantium`) | Titanium (`mvtink_titanium`) | Unbreakable Will | Void, Terrain, Tempered | +1600 | 11.5x | +8.5 |
| **Astral Brass** | `mvtink_astral_brass` | Pyrite (`mvtink_pyrite`) | Astralite (`mvtink_astralite`) | Starlight Grace | Void, Terrain, Tempered | +500 | 8.5x | +6 |
| **Bronze** | `mvtink_bronze` | Copper (`mvtink_copper`) | Tin (`mvtink_tin`) | Dense Temper | Primal, Terrain, Tempered | +350 | 7.5x | +5.5 |
| **Cinder Steel** | `mvtink_cinder_steel` | Steel (`mvtink_steel`) | Netherite (`mvtink_netherite`) | Hellfire Core | Infernal, Primal, Tempered | +1100 | 10x | +9 |
| **Cosmic Netherite** | `mvtink_cosmic_netherite` | Netherite (`mvtink_netherite`) | Celestine (`mvtink_celestine`) | Cosmic Gravity | Void, Primal, Tempered | +1400 | 12x | +10.5 |
| **Electrum** | `mvtink_electrum` | Gold (`mvtink_gold`) | Silver (`mvtink_silver`) | Lightning Conduit | Primal, Terrain, Tempered | +220 | 11x | +6 |
| **Ender Brass** | `mvtink_ender_brass` | Redstone (`mvtink_redstone`) | Enderite (`mvtink_enderite`) | Phase Step | Void, Primal, Tempered | +650 | 8.5x | +7.5 |
| **Glacial Silver** | `mvtink_glacial_silver` | Silver (`mvtink_silver`) | Cryolite (`mvtink_cryolite`) | Absolute Frost | Terrain, Tempered | +480 | 8x | +6.5 |
| **Hellfire Bismuth** | `mvtink_hellfire_bismuth` | Bismuth (`mvtink_bismuth`) | Fire Opal (`mvtink_fire_opal`) | Combustion | Infernal, Terrain, Tempered | +550 | 8x | +8 |
| **Invar** | `mvtink_invar` | Iron (`mvtink_iron`) | Nickel (`mvtink_nickel`) | Thermal Resilience | Primal, Terrain, Tempered | +450 | 8x | +6.5 |
| **Manyullyn** | `mvtink_manyullyn` | Cobalt (`mvtink_cobalt`) | Ardite (`mvtink_ardite`) | Insatiable | Infernal, Tempered, Swift | +800 | 10.5x | +9 |
| **Prismatic Quartz** | `mvtink_prismatic_quartz` | Nether Quartz (`mvtink_quartz`) | Amethyst (`mvtink_amethyst`) | Resonance Shock | Primal, Resonant | +400 | 9x | +7 |
| **Rose Gold** | `mvtink_rose_gold` | Gold (`mvtink_gold`) | Copper (`mvtink_copper`) | Midas Sparkle | Primal, Tempered, Swift | +280 | 9x | +5 |
| **Sanguine Gold** | `mvtink_sanguine_gold` | Gold (`mvtink_gold`) | Sanguinite (`mvtink_sanguinite`) | Vampiric Touch | Infernal, Primal, Tempered | +380 | 9.5x | +7.5 |
| **Shadow Platinum** | `mvtink_shadow_platinum` | Platinum (`mvtink_platinum`) | Obsidianite (`mvtink_obsidianite`) | Umbral Veil | Infernal, Terrain, Tempered | +750 | 9x | +8 |
| **Void Damascus** | `mvtink_void_damascus` | Tungsten (`mvtink_tungsten`) | Voidstone (`mvtink_voidstone`) | Abyssal Cleave | Infernal, Void, Terrain | +950 | 9.5x | +9.5 |

> La **Netherita** vanilla es especial: ya es una aleación, así que no se mezcla libremente. Solo entra en sus dos recetas curadas: `mvtink_steel` + `mvtink_netherite` (**Cinder Steel**) y `mvtink_netherite` + `mvtink_celestine` (**Cosmic Netherite**).

## 🌌 Fusiones primordiales

Una receta primordial empareja **una de las 16 legendarias** con otra legendaria, una compuesta, cualquier mineral o un catalizador. La resultante es `mvtink_prime_<A>_<B>`, se llama `<A> <B> Prime`, y su ultimate/estado lo decide el catalizador. Reglas y espectáculos completos en [Aleaciones Primordiales](Aleaciones-Primordiales.md).

| Catalizador | Item vanilla | Ultimate | Estado de armadura |
|---|---|---|---|
| `mvtink_catalyst_nether_star` | Nether Star (`nether_star`) | Supernova | Prime Aegis |
| `mvtink_catalyst_dragon_breath` | Dragon Breath (`dragon_breath`) | Meteor Cascade | Ember Veil |
| `mvtink_catalyst_blue_ice` | Blue Ice (`blue_ice`) | Absolute Zero | Frostbound |
| `mvtink_catalyst_packed_ice` | Packed Ice (`packed_ice`) | Glacier Tomb | Frostbound |
| `mvtink_catalyst_echo_shard` | Echo Shard (`echo_shard`) | Event Horizon | Void Shell |
| `mvtink_catalyst_heart_of_the_sea` | Heart of the Sea (`heart_of_the_sea`) | Tectonic Rift | Gravitic Anchor |
| `mvtink_catalyst_totem_of_undying` | Totem of Undying (`totem_of_undying`) | Prismatic Ascension | Prime Aegis |
| `mvtink_catalyst_end_crystal` | End Crystal (`end_crystal`) | Supernova | Stormcall |
| `mvtink_catalyst_respawn_anchor` | Respawn Anchor (`respawn_anchor`) | Meteor Cascade | Ember Veil |
| `mvtink_catalyst_prismarine_crystals` | Prismarine Crystals (`prismarine_crystals`) | Prismatic Ascension | Prism Bulwark |
| `mvtink_catalyst_amethyst_cluster` | Amethyst Cluster (`amethyst_cluster`) | Event Horizon | Prism Bulwark |
| `mvtink_catalyst_ancient_debris` | Ancient Debris (`ancient_debris`) | Tectonic Rift | Tectonic Guard |

## 🔗 Índice completo de pares

**5995 pares** mezclables en total (110 minerales mezclables): 14 son recetas legendarias (★) y el resto forjan una aleación compuesta dinámica. Se agrupan por **primer padre** en orden canónico, así que cada par aparece una sola vez: busca el material que tienes y mira con qué puede mezclar.

> 🔎 **Cómo leer el índice:** cada par aparece exactamente una vez, bajo el padre cuyo id va primero en orden alfabético. Para ver todo lo que mezcla un material, pulsa **Ctrl+F** y busca su id (`mvtink_tin`): aparece como cabecera de sección **y** como fila dentro de las secciones de los demás.

### 🌍 Padres del Overworld

#### Amber · `mvtink_amber` · 105 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Amethyst (`mvtink_amethyst`) | Amber-Amethyst Alloy | `mvtink_alloy_amber_amethyst` | Primal, Terrain, Radiant |
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Amber-Amethyst Geode Crystal Alloy | `mvtink_alloy_amber_amethyst_cluster_gem` | Terrain, Radiant, Resonant |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Amber-Ancient Debris Slag Alloy | `mvtink_alloy_amber_ancient_slag` | Infernal, Terrain, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Amber-Aquamarine Alloy | `mvtink_alloy_amber_aquamarine` | Terrain, Radiant |
| Ardite (`mvtink_ardite`) | Amber-Ardite Alloy | `mvtink_alloy_amber_ardite` | Infernal, Terrain, Tempered |
| Astralite (`mvtink_astralite`) | Amber-Astralite Alloy | `mvtink_alloy_amber_astralite` | Void, Terrain, Tempered |
| Bauxite (`mvtink_bauxite`) | Amber-Bauxite Alloy | `mvtink_alloy_amber_bauxite` | Terrain, Radiant |
| Beryllium (`mvtink_beryllium`) | Amber-Beryllium Alloy | `mvtink_alloy_amber_beryllium` | Terrain, Tempered, Radiant |
| Bismuth (`mvtink_bismuth`) | Amber-Bismuth Alloy | `mvtink_alloy_amber_bismuth` | Terrain, Tempered, Radiant |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Amber-Blackstone Pyrite Alloy | `mvtink_alloy_amber_blackstone_pyrite` | Infernal, Terrain, Radiant |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Amber-Blazesteel Shard Alloy | `mvtink_alloy_amber_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Amber-Borax Alloy | `mvtink_alloy_amber_borax` | Terrain, Radiant |
| Pure Calcite (`mvtink_calcite_gem`) | Amber-Pure Calcite Alloy | `mvtink_alloy_amber_calcite_gem` | Terrain, Radiant, Resonant |
| Celestine (`mvtink_celestine`) | Amber-Celestine Alloy | `mvtink_alloy_amber_celestine` | Void, Terrain, Radiant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Amber-Chorus Crystal Alloy | `mvtink_alloy_amber_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Amber-Chromite Alloy | `mvtink_alloy_amber_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Amber-Chrono Crystal Alloy | `mvtink_alloy_amber_chrono_crystal` | Void, Terrain, Radiant |
| Cinderite (`mvtink_cinderite`) | Amber-Cinderite Alloy | `mvtink_alloy_amber_cinderite` | Infernal, Terrain, Radiant |
| Cinnabar (`mvtink_cinnabar`) | Amber-Cinnabar Alloy | `mvtink_alloy_amber_cinnabar` | Terrain, Radiant |
| Coal (`mvtink_coal`) | Amber-Coal Alloy | `mvtink_alloy_amber_coal` | Primal, Terrain, Radiant |
| Cobalt (`mvtink_cobalt`) | Amber-Cobalt Alloy | `mvtink_alloy_amber_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Amber-Copper Alloy | `mvtink_alloy_amber_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Amber-Cosmium Alloy | `mvtink_alloy_amber_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Amber-Crimson Gold Alloy | `mvtink_alloy_amber_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Amber-Crimson Quartz Alloy | `mvtink_alloy_amber_crimson_quartz` | Infernal, Terrain, Radiant |
| Cryolite (`mvtink_cryolite`) | Amber-Cryolite Alloy | `mvtink_alloy_amber_cryolite` | Terrain, Radiant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Amber-Cursed Brimstone Alloy | `mvtink_alloy_amber_cursed_brimstone` | Infernal, Terrain, Radiant |
| Diamond (`mvtink_diamond`) | Amber-Diamond Alloy | `mvtink_alloy_amber_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Amber-Dragon Scale Shard Alloy | `mvtink_alloy_amber_dragon_shard` | Void, Terrain, Radiant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Amber-Eclipse Gem Alloy | `mvtink_alloy_amber_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Amber-Emerald Alloy | `mvtink_alloy_amber_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Amber-End Crystal Shard Alloy | `mvtink_alloy_amber_end_crystal_shard` | Void, Terrain, Radiant |
| Enderite (`mvtink_enderite`) | Amber-Enderite Alloy | `mvtink_alloy_amber_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Amber-Fire Opal Alloy | `mvtink_alloy_amber_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Amber-Flint Alloy | `mvtink_alloy_amber_flint` | Primal, Terrain, Radiant |
| Fluorite (`mvtink_fluorite`) | Amber-Fluorite Alloy | `mvtink_alloy_amber_fluorite` | Terrain, Radiant, Resonant |
| Galena (`mvtink_galena`) | Amber-Galena Alloy | `mvtink_alloy_amber_galena` | Terrain, Tempered, Radiant |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Amber-Ghast Tear Shard Alloy | `mvtink_alloy_amber_ghast_tear_shard` | Infernal, Terrain, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Amber-Glowstone Gem Alloy | `mvtink_alloy_amber_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Amber-Gold Alloy | `mvtink_alloy_amber_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Amber-Graphite Alloy | `mvtink_alloy_amber_graphite` | Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Amber-Gravitite Alloy | `mvtink_alloy_amber_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Amber-Gypsum Alloy | `mvtink_alloy_amber_gypsum` | Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Amber-Helliron Alloy | `mvtink_alloy_amber_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Amber-Ignis Ferrum Alloy | `mvtink_alloy_amber_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Amber-Infernal Obsidian Alloy | `mvtink_alloy_amber_infernal_obsidian` | Infernal, Terrain, Radiant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Amber-Netherite-Infused Quartz Alloy | `mvtink_alloy_amber_infused_quartz` | Infernal, Terrain, Radiant |
| Iron (`mvtink_iron`) | Amber-Iron Alloy | `mvtink_alloy_amber_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Amber-Jade Alloy | `mvtink_alloy_amber_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Amber-Kaolinite Alloy | `mvtink_alloy_amber_kaolinite` | Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Amber-Lapis Lazuli Alloy | `mvtink_alloy_amber_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Amber-Lapis Matrix Alloy | `mvtink_alloy_amber_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Amber-Magma Brimstone Alloy | `mvtink_alloy_amber_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Amber-Magmacite Alloy | `mvtink_alloy_amber_magmacite` | Infernal, Terrain, Radiant |
| Magnetite (`mvtink_magnetite`) | Amber-Magnetite Alloy | `mvtink_alloy_amber_magnetite` | Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Amber-Malachite Alloy | `mvtink_alloy_amber_malachite` | Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Amber-Nebulite Alloy | `mvtink_alloy_amber_nebulite` | Void, Terrain, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Amber-Nether Bismuth Alloy | `mvtink_alloy_amber_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Amber-Nether Tungsten Alloy | `mvtink_alloy_amber_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Amber-Netherite Scrap Shard Alloy | `mvtink_alloy_amber_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Amber-Nickel Alloy | `mvtink_alloy_amber_nickel` | Terrain, Tempered, Radiant |
| Null-Shard (`mvtink_null_shard`) | Amber-Null-Shard Alloy | `mvtink_alloy_amber_null_shard` | Void, Terrain, Radiant |
| Obsidian (`mvtink_obsidian`) | Amber-Obsidian Alloy | `mvtink_alloy_amber_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Amber-Obsidianite Alloy | `mvtink_alloy_amber_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Amber-Opal Alloy | `mvtink_alloy_amber_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Amber-Ender Pearl Core Alloy | `mvtink_alloy_amber_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Amber-Phantomite Alloy | `mvtink_alloy_amber_phantomite` | Void, Terrain, Radiant |
| Platinum (`mvtink_platinum`) | Amber-Platinum Alloy | `mvtink_alloy_amber_platinum` | Terrain, Tempered, Radiant |
| Prismarine (`mvtink_prismarine`) | Amber-Prismarine Alloy | `mvtink_alloy_amber_prismarine` | Primal, Terrain, Radiant |
| Pyrite (`mvtink_pyrite`) | Amber-Pyrite Alloy | `mvtink_alloy_amber_pyrite` | Terrain, Radiant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Amber-Pyrophore Alloy | `mvtink_alloy_amber_pyrophore` | Infernal, Terrain, Radiant |
| Nether Quartz (`mvtink_quartz`) | Amber-Nether Quartz Alloy | `mvtink_alloy_amber_quartz` | Primal, Terrain, Radiant |
| Redstone (`mvtink_redstone`) | Amber-Redstone Alloy | `mvtink_alloy_amber_redstone` | Primal, Terrain, Radiant |
| Resonite (`mvtink_resonite`) | Amber-Resonite Alloy | `mvtink_alloy_amber_resonite` | Void, Terrain, Radiant |
| Ruby (`mvtink_ruby`) | Amber-Ruby Alloy | `mvtink_alloy_amber_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Amber-Sanguinite Alloy | `mvtink_alloy_amber_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Amber-Sapphire Alloy | `mvtink_alloy_amber_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Amber-Shadowgem Alloy | `mvtink_alloy_amber_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Amber-Shulkerite Alloy | `mvtink_alloy_amber_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Amber-Silver Alloy | `mvtink_alloy_amber_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Amber-Singularite Alloy | `mvtink_alloy_amber_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Amber-Soul Glass Crystal Alloy | `mvtink_alloy_amber_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Amber-Spatial Platinum Alloy | `mvtink_alloy_amber_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Amber-Starlight Silver Alloy | `mvtink_alloy_amber_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Amber-Steel Alloy | `mvtink_alloy_amber_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Amber-Stibnite Alloy | `mvtink_alloy_amber_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Amber-Sulfur Alloy | `mvtink_alloy_amber_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Amber-Talc Alloy | `mvtink_alloy_amber_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Amber-Tesseract Crystal Alloy | `mvtink_alloy_amber_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Amber-Tin Alloy | `mvtink_alloy_amber_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Amber-Titanium Alloy | `mvtink_alloy_amber_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Amber-Topaz Alloy | `mvtink_alloy_amber_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Amber-Tourmaline Alloy | `mvtink_alloy_amber_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Amber-Tungsten Alloy | `mvtink_alloy_amber_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Amber-Void Pyrite Alloy | `mvtink_alloy_amber_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Amber-Void Titanium Alloy | `mvtink_alloy_amber_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Amber-Voidstone Alloy | `mvtink_alloy_amber_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Amber-Compacted Volcanic Ash Alloy | `mvtink_alloy_amber_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Amber-Warped Emerald Alloy | `mvtink_alloy_amber_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Amber-Warped Quartz Alloy | `mvtink_alloy_amber_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Amber-Pure Weeping Shard Alloy | `mvtink_alloy_amber_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Amber-Witherite Alloy | `mvtink_alloy_amber_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Amber-Zero-Point Shard Alloy | `mvtink_alloy_amber_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Amber-Zinc Alloy | `mvtink_alloy_amber_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Amber-Zircon Alloy | `mvtink_alloy_amber_zircon` | Terrain, Radiant, Resonant |

#### Amethyst Geode Crystal · `mvtink_amethyst_cluster_gem` · 103 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ancient Debris Slag (`mvtink_ancient_slag`) | Amethyst Geode Crystal-Ancient Debris Slag Alloy | `mvtink_alloy_amethyst_cluster_gem_ancient_slag` | Infernal, Terrain, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Amethyst Geode Crystal-Aquamarine Alloy | `mvtink_alloy_amethyst_cluster_gem_aquamarine` | Terrain, Radiant, Resonant |
| Ardite (`mvtink_ardite`) | Amethyst Geode Crystal-Ardite Alloy | `mvtink_alloy_amethyst_cluster_gem_ardite` | Infernal, Terrain, Tempered |
| Astralite (`mvtink_astralite`) | Amethyst Geode Crystal-Astralite Alloy | `mvtink_alloy_amethyst_cluster_gem_astralite` | Void, Terrain, Tempered |
| Bauxite (`mvtink_bauxite`) | Amethyst Geode Crystal-Bauxite Alloy | `mvtink_alloy_amethyst_cluster_gem_bauxite` | Terrain, Resonant |
| Beryllium (`mvtink_beryllium`) | Amethyst Geode Crystal-Beryllium Alloy | `mvtink_alloy_amethyst_cluster_gem_beryllium` | Terrain, Tempered, Resonant |
| Bismuth (`mvtink_bismuth`) | Amethyst Geode Crystal-Bismuth Alloy | `mvtink_alloy_amethyst_cluster_gem_bismuth` | Terrain, Tempered, Resonant |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Amethyst Geode Crystal-Blackstone Pyrite Alloy | `mvtink_alloy_amethyst_cluster_gem_blackstone_pyrite` | Infernal, Terrain, Resonant |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Amethyst Geode Crystal-Blazesteel Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Amethyst Geode Crystal-Borax Alloy | `mvtink_alloy_amethyst_cluster_gem_borax` | Terrain, Resonant |
| Pure Calcite (`mvtink_calcite_gem`) | Amethyst Geode Crystal-Pure Calcite Alloy | `mvtink_alloy_amethyst_cluster_gem_calcite_gem` | Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Amethyst Geode Crystal-Celestine Alloy | `mvtink_alloy_amethyst_cluster_gem_celestine` | Void, Terrain, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Amethyst Geode Crystal-Chorus Crystal Alloy | `mvtink_alloy_amethyst_cluster_gem_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Amethyst Geode Crystal-Chromite Alloy | `mvtink_alloy_amethyst_cluster_gem_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Amethyst Geode Crystal-Chrono Crystal Alloy | `mvtink_alloy_amethyst_cluster_gem_chrono_crystal` | Void, Terrain, Resonant |
| Cinderite (`mvtink_cinderite`) | Amethyst Geode Crystal-Cinderite Alloy | `mvtink_alloy_amethyst_cluster_gem_cinderite` | Infernal, Terrain, Resonant |
| Cinnabar (`mvtink_cinnabar`) | Amethyst Geode Crystal-Cinnabar Alloy | `mvtink_alloy_amethyst_cluster_gem_cinnabar` | Terrain, Resonant |
| Coal (`mvtink_coal`) | Amethyst Geode Crystal-Coal Alloy | `mvtink_alloy_amethyst_cluster_gem_coal` | Primal, Terrain, Resonant |
| Cobalt (`mvtink_cobalt`) | Amethyst Geode Crystal-Cobalt Alloy | `mvtink_alloy_amethyst_cluster_gem_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Amethyst Geode Crystal-Copper Alloy | `mvtink_alloy_amethyst_cluster_gem_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Amethyst Geode Crystal-Cosmium Alloy | `mvtink_alloy_amethyst_cluster_gem_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Amethyst Geode Crystal-Crimson Gold Alloy | `mvtink_alloy_amethyst_cluster_gem_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Amethyst Geode Crystal-Crimson Quartz Alloy | `mvtink_alloy_amethyst_cluster_gem_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Amethyst Geode Crystal-Cryolite Alloy | `mvtink_alloy_amethyst_cluster_gem_cryolite` | Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Amethyst Geode Crystal-Cursed Brimstone Alloy | `mvtink_alloy_amethyst_cluster_gem_cursed_brimstone` | Infernal, Terrain, Resonant |
| Diamond (`mvtink_diamond`) | Amethyst Geode Crystal-Diamond Alloy | `mvtink_alloy_amethyst_cluster_gem_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Amethyst Geode Crystal-Dragon Scale Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_dragon_shard` | Void, Terrain, Resonant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Amethyst Geode Crystal-Eclipse Gem Alloy | `mvtink_alloy_amethyst_cluster_gem_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Amethyst Geode Crystal-Emerald Alloy | `mvtink_alloy_amethyst_cluster_gem_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Amethyst Geode Crystal-End Crystal Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Amethyst Geode Crystal-Enderite Alloy | `mvtink_alloy_amethyst_cluster_gem_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Amethyst Geode Crystal-Fire Opal Alloy | `mvtink_alloy_amethyst_cluster_gem_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Amethyst Geode Crystal-Flint Alloy | `mvtink_alloy_amethyst_cluster_gem_flint` | Primal, Terrain, Resonant |
| Fluorite (`mvtink_fluorite`) | Amethyst Geode Crystal-Fluorite Alloy | `mvtink_alloy_amethyst_cluster_gem_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Amethyst Geode Crystal-Galena Alloy | `mvtink_alloy_amethyst_cluster_gem_galena` | Terrain, Tempered, Resonant |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Amethyst Geode Crystal-Ghast Tear Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Amethyst Geode Crystal-Glowstone Gem Alloy | `mvtink_alloy_amethyst_cluster_gem_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Amethyst Geode Crystal-Gold Alloy | `mvtink_alloy_amethyst_cluster_gem_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Amethyst Geode Crystal-Graphite Alloy | `mvtink_alloy_amethyst_cluster_gem_graphite` | Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Amethyst Geode Crystal-Gravitite Alloy | `mvtink_alloy_amethyst_cluster_gem_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Amethyst Geode Crystal-Gypsum Alloy | `mvtink_alloy_amethyst_cluster_gem_gypsum` | Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Amethyst Geode Crystal-Helliron Alloy | `mvtink_alloy_amethyst_cluster_gem_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Amethyst Geode Crystal-Ignis Ferrum Alloy | `mvtink_alloy_amethyst_cluster_gem_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Amethyst Geode Crystal-Infernal Obsidian Alloy | `mvtink_alloy_amethyst_cluster_gem_infernal_obsidian` | Infernal, Terrain, Resonant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Amethyst Geode Crystal-Netherite-Infused Quartz Alloy | `mvtink_alloy_amethyst_cluster_gem_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Amethyst Geode Crystal-Iron Alloy | `mvtink_alloy_amethyst_cluster_gem_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Amethyst Geode Crystal-Jade Alloy | `mvtink_alloy_amethyst_cluster_gem_jade` | Terrain, Radiant, Resonant |
| Kaolinite (`mvtink_kaolinite`) | Amethyst Geode Crystal-Kaolinite Alloy | `mvtink_alloy_amethyst_cluster_gem_kaolinite` | Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Amethyst Geode Crystal-Lapis Lazuli Alloy | `mvtink_alloy_amethyst_cluster_gem_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Amethyst Geode Crystal-Lapis Matrix Alloy | `mvtink_alloy_amethyst_cluster_gem_lapis_matrix` | Terrain, Radiant, Resonant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Amethyst Geode Crystal-Magma Brimstone Alloy | `mvtink_alloy_amethyst_cluster_gem_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Amethyst Geode Crystal-Magmacite Alloy | `mvtink_alloy_amethyst_cluster_gem_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Amethyst Geode Crystal-Magnetite Alloy | `mvtink_alloy_amethyst_cluster_gem_magnetite` | Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Amethyst Geode Crystal-Malachite Alloy | `mvtink_alloy_amethyst_cluster_gem_malachite` | Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Amethyst Geode Crystal-Nebulite Alloy | `mvtink_alloy_amethyst_cluster_gem_nebulite` | Void, Terrain, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Amethyst Geode Crystal-Nether Bismuth Alloy | `mvtink_alloy_amethyst_cluster_gem_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Amethyst Geode Crystal-Nether Tungsten Alloy | `mvtink_alloy_amethyst_cluster_gem_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Amethyst Geode Crystal-Netherite Scrap Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Amethyst Geode Crystal-Nickel Alloy | `mvtink_alloy_amethyst_cluster_gem_nickel` | Terrain, Tempered, Resonant |
| Null-Shard (`mvtink_null_shard`) | Amethyst Geode Crystal-Null-Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_null_shard` | Void, Terrain, Resonant |
| Obsidian (`mvtink_obsidian`) | Amethyst Geode Crystal-Obsidian Alloy | `mvtink_alloy_amethyst_cluster_gem_obsidian` | Primal, Terrain, Resonant |
| Obsidianite (`mvtink_obsidianite`) | Amethyst Geode Crystal-Obsidianite Alloy | `mvtink_alloy_amethyst_cluster_gem_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Amethyst Geode Crystal-Opal Alloy | `mvtink_alloy_amethyst_cluster_gem_opal` | Terrain, Radiant, Resonant |
| Ender Pearl Core (`mvtink_pearl_core`) | Amethyst Geode Crystal-Ender Pearl Core Alloy | `mvtink_alloy_amethyst_cluster_gem_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Amethyst Geode Crystal-Phantomite Alloy | `mvtink_alloy_amethyst_cluster_gem_phantomite` | Void, Terrain, Resonant |
| Platinum (`mvtink_platinum`) | Amethyst Geode Crystal-Platinum Alloy | `mvtink_alloy_amethyst_cluster_gem_platinum` | Terrain, Tempered, Resonant |
| Prismarine (`mvtink_prismarine`) | Amethyst Geode Crystal-Prismarine Alloy | `mvtink_alloy_amethyst_cluster_gem_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Amethyst Geode Crystal-Pyrite Alloy | `mvtink_alloy_amethyst_cluster_gem_pyrite` | Terrain, Resonant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Amethyst Geode Crystal-Pyrophore Alloy | `mvtink_alloy_amethyst_cluster_gem_pyrophore` | Infernal, Terrain, Resonant |
| Nether Quartz (`mvtink_quartz`) | Amethyst Geode Crystal-Nether Quartz Alloy | `mvtink_alloy_amethyst_cluster_gem_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Amethyst Geode Crystal-Redstone Alloy | `mvtink_alloy_amethyst_cluster_gem_redstone` | Primal, Terrain, Resonant |
| Resonite (`mvtink_resonite`) | Amethyst Geode Crystal-Resonite Alloy | `mvtink_alloy_amethyst_cluster_gem_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Amethyst Geode Crystal-Ruby Alloy | `mvtink_alloy_amethyst_cluster_gem_ruby` | Terrain, Radiant, Resonant |
| Sanguinite (`mvtink_sanguinite`) | Amethyst Geode Crystal-Sanguinite Alloy | `mvtink_alloy_amethyst_cluster_gem_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Amethyst Geode Crystal-Sapphire Alloy | `mvtink_alloy_amethyst_cluster_gem_sapphire` | Terrain, Radiant, Resonant |
| Shadowgem (`mvtink_shadowgem`) | Amethyst Geode Crystal-Shadowgem Alloy | `mvtink_alloy_amethyst_cluster_gem_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Amethyst Geode Crystal-Shulkerite Alloy | `mvtink_alloy_amethyst_cluster_gem_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Amethyst Geode Crystal-Silver Alloy | `mvtink_alloy_amethyst_cluster_gem_silver` | Terrain, Tempered, Resonant |
| Singularite (`mvtink_singularite`) | Amethyst Geode Crystal-Singularite Alloy | `mvtink_alloy_amethyst_cluster_gem_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Amethyst Geode Crystal-Soul Glass Crystal Alloy | `mvtink_alloy_amethyst_cluster_gem_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Amethyst Geode Crystal-Spatial Platinum Alloy | `mvtink_alloy_amethyst_cluster_gem_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Amethyst Geode Crystal-Starlight Silver Alloy | `mvtink_alloy_amethyst_cluster_gem_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Amethyst Geode Crystal-Steel Alloy | `mvtink_alloy_amethyst_cluster_gem_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Amethyst Geode Crystal-Stibnite Alloy | `mvtink_alloy_amethyst_cluster_gem_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Amethyst Geode Crystal-Sulfur Alloy | `mvtink_alloy_amethyst_cluster_gem_sulfur` | Infernal, Terrain, Resonant |
| Talc (`mvtink_talc`) | Amethyst Geode Crystal-Talc Alloy | `mvtink_alloy_amethyst_cluster_gem_talc` | Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Amethyst Geode Crystal-Tesseract Crystal Alloy | `mvtink_alloy_amethyst_cluster_gem_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Amethyst Geode Crystal-Tin Alloy | `mvtink_alloy_amethyst_cluster_gem_tin` | Terrain, Tempered, Resonant |
| Titanium (`mvtink_titanium`) | Amethyst Geode Crystal-Titanium Alloy | `mvtink_alloy_amethyst_cluster_gem_titanium` | Terrain, Tempered, Resonant |
| Topaz (`mvtink_topaz`) | Amethyst Geode Crystal-Topaz Alloy | `mvtink_alloy_amethyst_cluster_gem_topaz` | Terrain, Radiant, Resonant |
| Tourmaline (`mvtink_tourmaline`) | Amethyst Geode Crystal-Tourmaline Alloy | `mvtink_alloy_amethyst_cluster_gem_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Amethyst Geode Crystal-Tungsten Alloy | `mvtink_alloy_amethyst_cluster_gem_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Amethyst Geode Crystal-Void Pyrite Alloy | `mvtink_alloy_amethyst_cluster_gem_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Amethyst Geode Crystal-Void Titanium Alloy | `mvtink_alloy_amethyst_cluster_gem_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Amethyst Geode Crystal-Voidstone Alloy | `mvtink_alloy_amethyst_cluster_gem_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Amethyst Geode Crystal-Compacted Volcanic Ash Alloy | `mvtink_alloy_amethyst_cluster_gem_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Amethyst Geode Crystal-Warped Emerald Alloy | `mvtink_alloy_amethyst_cluster_gem_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Amethyst Geode Crystal-Warped Quartz Alloy | `mvtink_alloy_amethyst_cluster_gem_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Amethyst Geode Crystal-Pure Weeping Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Amethyst Geode Crystal-Witherite Alloy | `mvtink_alloy_amethyst_cluster_gem_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Amethyst Geode Crystal-Zero-Point Shard Alloy | `mvtink_alloy_amethyst_cluster_gem_zero_point` | Void, Terrain, Resonant |
| Zinc (`mvtink_zinc`) | Amethyst Geode Crystal-Zinc Alloy | `mvtink_alloy_amethyst_cluster_gem_zinc` | Terrain, Tempered, Resonant |
| Zircon (`mvtink_zircon`) | Amethyst Geode Crystal-Zircon Alloy | `mvtink_alloy_amethyst_cluster_gem_zircon` | Terrain, Resonant, Swift |

#### Aquamarine · `mvtink_aquamarine` · 101 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ardite (`mvtink_ardite`) | Aquamarine-Ardite Alloy | `mvtink_alloy_aquamarine_ardite` | Infernal, Terrain, Tempered |
| Astralite (`mvtink_astralite`) | Aquamarine-Astralite Alloy | `mvtink_alloy_aquamarine_astralite` | Void, Terrain, Tempered |
| Bauxite (`mvtink_bauxite`) | Aquamarine-Bauxite Alloy | `mvtink_alloy_aquamarine_bauxite` | Terrain, Radiant |
| Beryllium (`mvtink_beryllium`) | Aquamarine-Beryllium Alloy | `mvtink_alloy_aquamarine_beryllium` | Terrain, Tempered, Radiant |
| Bismuth (`mvtink_bismuth`) | Aquamarine-Bismuth Alloy | `mvtink_alloy_aquamarine_bismuth` | Terrain, Tempered, Radiant |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Aquamarine-Blackstone Pyrite Alloy | `mvtink_alloy_aquamarine_blackstone_pyrite` | Infernal, Terrain, Radiant |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Aquamarine-Blazesteel Shard Alloy | `mvtink_alloy_aquamarine_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Aquamarine-Borax Alloy | `mvtink_alloy_aquamarine_borax` | Terrain, Radiant |
| Pure Calcite (`mvtink_calcite_gem`) | Aquamarine-Pure Calcite Alloy | `mvtink_alloy_aquamarine_calcite_gem` | Terrain, Radiant, Resonant |
| Celestine (`mvtink_celestine`) | Aquamarine-Celestine Alloy | `mvtink_alloy_aquamarine_celestine` | Void, Terrain, Radiant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Aquamarine-Chorus Crystal Alloy | `mvtink_alloy_aquamarine_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Aquamarine-Chromite Alloy | `mvtink_alloy_aquamarine_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Aquamarine-Chrono Crystal Alloy | `mvtink_alloy_aquamarine_chrono_crystal` | Void, Terrain, Radiant |
| Cinderite (`mvtink_cinderite`) | Aquamarine-Cinderite Alloy | `mvtink_alloy_aquamarine_cinderite` | Infernal, Terrain, Radiant |
| Cinnabar (`mvtink_cinnabar`) | Aquamarine-Cinnabar Alloy | `mvtink_alloy_aquamarine_cinnabar` | Terrain, Radiant |
| Coal (`mvtink_coal`) | Aquamarine-Coal Alloy | `mvtink_alloy_aquamarine_coal` | Primal, Terrain, Radiant |
| Cobalt (`mvtink_cobalt`) | Aquamarine-Cobalt Alloy | `mvtink_alloy_aquamarine_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Aquamarine-Copper Alloy | `mvtink_alloy_aquamarine_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Aquamarine-Cosmium Alloy | `mvtink_alloy_aquamarine_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Aquamarine-Crimson Gold Alloy | `mvtink_alloy_aquamarine_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Aquamarine-Crimson Quartz Alloy | `mvtink_alloy_aquamarine_crimson_quartz` | Infernal, Terrain, Radiant |
| Cryolite (`mvtink_cryolite`) | Aquamarine-Cryolite Alloy | `mvtink_alloy_aquamarine_cryolite` | Terrain, Radiant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Aquamarine-Cursed Brimstone Alloy | `mvtink_alloy_aquamarine_cursed_brimstone` | Infernal, Terrain, Radiant |
| Diamond (`mvtink_diamond`) | Aquamarine-Diamond Alloy | `mvtink_alloy_aquamarine_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Aquamarine-Dragon Scale Shard Alloy | `mvtink_alloy_aquamarine_dragon_shard` | Void, Terrain, Radiant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Aquamarine-Eclipse Gem Alloy | `mvtink_alloy_aquamarine_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Aquamarine-Emerald Alloy | `mvtink_alloy_aquamarine_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Aquamarine-End Crystal Shard Alloy | `mvtink_alloy_aquamarine_end_crystal_shard` | Void, Terrain, Radiant |
| Enderite (`mvtink_enderite`) | Aquamarine-Enderite Alloy | `mvtink_alloy_aquamarine_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Aquamarine-Fire Opal Alloy | `mvtink_alloy_aquamarine_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Aquamarine-Flint Alloy | `mvtink_alloy_aquamarine_flint` | Primal, Terrain, Radiant |
| Fluorite (`mvtink_fluorite`) | Aquamarine-Fluorite Alloy | `mvtink_alloy_aquamarine_fluorite` | Terrain, Radiant, Resonant |
| Galena (`mvtink_galena`) | Aquamarine-Galena Alloy | `mvtink_alloy_aquamarine_galena` | Terrain, Tempered, Radiant |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Aquamarine-Ghast Tear Shard Alloy | `mvtink_alloy_aquamarine_ghast_tear_shard` | Infernal, Terrain, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Aquamarine-Glowstone Gem Alloy | `mvtink_alloy_aquamarine_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Aquamarine-Gold Alloy | `mvtink_alloy_aquamarine_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Aquamarine-Graphite Alloy | `mvtink_alloy_aquamarine_graphite` | Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Aquamarine-Gravitite Alloy | `mvtink_alloy_aquamarine_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Aquamarine-Gypsum Alloy | `mvtink_alloy_aquamarine_gypsum` | Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Aquamarine-Helliron Alloy | `mvtink_alloy_aquamarine_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Aquamarine-Ignis Ferrum Alloy | `mvtink_alloy_aquamarine_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Aquamarine-Infernal Obsidian Alloy | `mvtink_alloy_aquamarine_infernal_obsidian` | Infernal, Terrain, Radiant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Aquamarine-Netherite-Infused Quartz Alloy | `mvtink_alloy_aquamarine_infused_quartz` | Infernal, Terrain, Radiant |
| Iron (`mvtink_iron`) | Aquamarine-Iron Alloy | `mvtink_alloy_aquamarine_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Aquamarine-Jade Alloy | `mvtink_alloy_aquamarine_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Aquamarine-Kaolinite Alloy | `mvtink_alloy_aquamarine_kaolinite` | Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Aquamarine-Lapis Lazuli Alloy | `mvtink_alloy_aquamarine_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Aquamarine-Lapis Matrix Alloy | `mvtink_alloy_aquamarine_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Aquamarine-Magma Brimstone Alloy | `mvtink_alloy_aquamarine_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Aquamarine-Magmacite Alloy | `mvtink_alloy_aquamarine_magmacite` | Infernal, Terrain, Radiant |
| Magnetite (`mvtink_magnetite`) | Aquamarine-Magnetite Alloy | `mvtink_alloy_aquamarine_magnetite` | Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Aquamarine-Malachite Alloy | `mvtink_alloy_aquamarine_malachite` | Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Aquamarine-Nebulite Alloy | `mvtink_alloy_aquamarine_nebulite` | Void, Terrain, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Aquamarine-Nether Bismuth Alloy | `mvtink_alloy_aquamarine_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Aquamarine-Nether Tungsten Alloy | `mvtink_alloy_aquamarine_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Aquamarine-Netherite Scrap Shard Alloy | `mvtink_alloy_aquamarine_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Aquamarine-Nickel Alloy | `mvtink_alloy_aquamarine_nickel` | Terrain, Tempered, Radiant |
| Null-Shard (`mvtink_null_shard`) | Aquamarine-Null-Shard Alloy | `mvtink_alloy_aquamarine_null_shard` | Void, Terrain, Radiant |
| Obsidian (`mvtink_obsidian`) | Aquamarine-Obsidian Alloy | `mvtink_alloy_aquamarine_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Aquamarine-Obsidianite Alloy | `mvtink_alloy_aquamarine_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Aquamarine-Opal Alloy | `mvtink_alloy_aquamarine_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Aquamarine-Ender Pearl Core Alloy | `mvtink_alloy_aquamarine_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Aquamarine-Phantomite Alloy | `mvtink_alloy_aquamarine_phantomite` | Void, Terrain, Radiant |
| Platinum (`mvtink_platinum`) | Aquamarine-Platinum Alloy | `mvtink_alloy_aquamarine_platinum` | Terrain, Tempered, Radiant |
| Prismarine (`mvtink_prismarine`) | Aquamarine-Prismarine Alloy | `mvtink_alloy_aquamarine_prismarine` | Primal, Terrain, Radiant |
| Pyrite (`mvtink_pyrite`) | Aquamarine-Pyrite Alloy | `mvtink_alloy_aquamarine_pyrite` | Terrain, Radiant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Aquamarine-Pyrophore Alloy | `mvtink_alloy_aquamarine_pyrophore` | Infernal, Terrain, Radiant |
| Nether Quartz (`mvtink_quartz`) | Aquamarine-Nether Quartz Alloy | `mvtink_alloy_aquamarine_quartz` | Primal, Terrain, Radiant |
| Redstone (`mvtink_redstone`) | Aquamarine-Redstone Alloy | `mvtink_alloy_aquamarine_redstone` | Primal, Terrain, Radiant |
| Resonite (`mvtink_resonite`) | Aquamarine-Resonite Alloy | `mvtink_alloy_aquamarine_resonite` | Void, Terrain, Radiant |
| Ruby (`mvtink_ruby`) | Aquamarine-Ruby Alloy | `mvtink_alloy_aquamarine_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Aquamarine-Sanguinite Alloy | `mvtink_alloy_aquamarine_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Aquamarine-Sapphire Alloy | `mvtink_alloy_aquamarine_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Aquamarine-Shadowgem Alloy | `mvtink_alloy_aquamarine_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Aquamarine-Shulkerite Alloy | `mvtink_alloy_aquamarine_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Aquamarine-Silver Alloy | `mvtink_alloy_aquamarine_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Aquamarine-Singularite Alloy | `mvtink_alloy_aquamarine_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Aquamarine-Soul Glass Crystal Alloy | `mvtink_alloy_aquamarine_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Aquamarine-Spatial Platinum Alloy | `mvtink_alloy_aquamarine_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Aquamarine-Starlight Silver Alloy | `mvtink_alloy_aquamarine_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Aquamarine-Steel Alloy | `mvtink_alloy_aquamarine_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Aquamarine-Stibnite Alloy | `mvtink_alloy_aquamarine_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Aquamarine-Sulfur Alloy | `mvtink_alloy_aquamarine_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Aquamarine-Talc Alloy | `mvtink_alloy_aquamarine_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Aquamarine-Tesseract Crystal Alloy | `mvtink_alloy_aquamarine_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Aquamarine-Tin Alloy | `mvtink_alloy_aquamarine_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Aquamarine-Titanium Alloy | `mvtink_alloy_aquamarine_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Aquamarine-Topaz Alloy | `mvtink_alloy_aquamarine_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Aquamarine-Tourmaline Alloy | `mvtink_alloy_aquamarine_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Aquamarine-Tungsten Alloy | `mvtink_alloy_aquamarine_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Aquamarine-Void Pyrite Alloy | `mvtink_alloy_aquamarine_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Aquamarine-Void Titanium Alloy | `mvtink_alloy_aquamarine_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Aquamarine-Voidstone Alloy | `mvtink_alloy_aquamarine_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Aquamarine-Compacted Volcanic Ash Alloy | `mvtink_alloy_aquamarine_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Aquamarine-Warped Emerald Alloy | `mvtink_alloy_aquamarine_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Aquamarine-Warped Quartz Alloy | `mvtink_alloy_aquamarine_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Aquamarine-Pure Weeping Shard Alloy | `mvtink_alloy_aquamarine_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Aquamarine-Witherite Alloy | `mvtink_alloy_aquamarine_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Aquamarine-Zero-Point Shard Alloy | `mvtink_alloy_aquamarine_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Aquamarine-Zinc Alloy | `mvtink_alloy_aquamarine_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Aquamarine-Zircon Alloy | `mvtink_alloy_aquamarine_zircon` | Terrain, Radiant, Resonant |

#### Bauxite · `mvtink_bauxite` · 98 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Beryllium (`mvtink_beryllium`) | Bauxite-Beryllium Alloy | `mvtink_alloy_bauxite_beryllium` | Terrain, Tempered, Swift |
| Bismuth (`mvtink_bismuth`) | Bauxite-Bismuth Alloy | `mvtink_alloy_bauxite_bismuth` | Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Bauxite-Blackstone Pyrite Alloy | `mvtink_alloy_bauxite_blackstone_pyrite` | Infernal, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Bauxite-Blazesteel Shard Alloy | `mvtink_alloy_bauxite_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Bauxite-Borax Alloy | `mvtink_alloy_bauxite_borax` | Terrain |
| Pure Calcite (`mvtink_calcite_gem`) | Bauxite-Pure Calcite Alloy | `mvtink_alloy_bauxite_calcite_gem` | Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Bauxite-Celestine Alloy | `mvtink_alloy_bauxite_celestine` | Void, Terrain, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Bauxite-Chorus Crystal Alloy | `mvtink_alloy_bauxite_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Bauxite-Chromite Alloy | `mvtink_alloy_bauxite_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Bauxite-Chrono Crystal Alloy | `mvtink_alloy_bauxite_chrono_crystal` | Void, Terrain, Resonant |
| Cinderite (`mvtink_cinderite`) | Bauxite-Cinderite Alloy | `mvtink_alloy_bauxite_cinderite` | Infernal, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Bauxite-Cinnabar Alloy | `mvtink_alloy_bauxite_cinnabar` | Terrain |
| Coal (`mvtink_coal`) | Bauxite-Coal Alloy | `mvtink_alloy_bauxite_coal` | Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Bauxite-Cobalt Alloy | `mvtink_alloy_bauxite_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Bauxite-Copper Alloy | `mvtink_alloy_bauxite_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Bauxite-Cosmium Alloy | `mvtink_alloy_bauxite_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Bauxite-Crimson Gold Alloy | `mvtink_alloy_bauxite_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Bauxite-Crimson Quartz Alloy | `mvtink_alloy_bauxite_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Bauxite-Cryolite Alloy | `mvtink_alloy_bauxite_cryolite` | Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Bauxite-Cursed Brimstone Alloy | `mvtink_alloy_bauxite_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Bauxite-Diamond Alloy | `mvtink_alloy_bauxite_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Bauxite-Dragon Scale Shard Alloy | `mvtink_alloy_bauxite_dragon_shard` | Void, Terrain, Swift |
| Eclipse Gem (`mvtink_eclipse_gem`) | Bauxite-Eclipse Gem Alloy | `mvtink_alloy_bauxite_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Bauxite-Emerald Alloy | `mvtink_alloy_bauxite_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Bauxite-End Crystal Shard Alloy | `mvtink_alloy_bauxite_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Bauxite-Enderite Alloy | `mvtink_alloy_bauxite_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Bauxite-Fire Opal Alloy | `mvtink_alloy_bauxite_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Bauxite-Flint Alloy | `mvtink_alloy_bauxite_flint` | Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Bauxite-Fluorite Alloy | `mvtink_alloy_bauxite_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Bauxite-Galena Alloy | `mvtink_alloy_bauxite_galena` | Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Bauxite-Ghast Tear Shard Alloy | `mvtink_alloy_bauxite_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Bauxite-Glowstone Gem Alloy | `mvtink_alloy_bauxite_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Bauxite-Gold Alloy | `mvtink_alloy_bauxite_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Bauxite-Graphite Alloy | `mvtink_alloy_bauxite_graphite` | Terrain |
| Gravitite (`mvtink_gravitite`) | Bauxite-Gravitite Alloy | `mvtink_alloy_bauxite_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Bauxite-Gypsum Alloy | `mvtink_alloy_bauxite_gypsum` | Terrain |
| Helliron (`mvtink_helliron`) | Bauxite-Helliron Alloy | `mvtink_alloy_bauxite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Bauxite-Ignis Ferrum Alloy | `mvtink_alloy_bauxite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Bauxite-Infernal Obsidian Alloy | `mvtink_alloy_bauxite_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Bauxite-Netherite-Infused Quartz Alloy | `mvtink_alloy_bauxite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Bauxite-Iron Alloy | `mvtink_alloy_bauxite_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Bauxite-Jade Alloy | `mvtink_alloy_bauxite_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Bauxite-Kaolinite Alloy | `mvtink_alloy_bauxite_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Bauxite-Lapis Lazuli Alloy | `mvtink_alloy_bauxite_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Bauxite-Lapis Matrix Alloy | `mvtink_alloy_bauxite_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Bauxite-Magma Brimstone Alloy | `mvtink_alloy_bauxite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Bauxite-Magmacite Alloy | `mvtink_alloy_bauxite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Bauxite-Magnetite Alloy | `mvtink_alloy_bauxite_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Bauxite-Malachite Alloy | `mvtink_alloy_bauxite_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Bauxite-Nebulite Alloy | `mvtink_alloy_bauxite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Bauxite-Nether Bismuth Alloy | `mvtink_alloy_bauxite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Bauxite-Nether Tungsten Alloy | `mvtink_alloy_bauxite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Bauxite-Netherite Scrap Shard Alloy | `mvtink_alloy_bauxite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Bauxite-Nickel Alloy | `mvtink_alloy_bauxite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Bauxite-Null-Shard Alloy | `mvtink_alloy_bauxite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Bauxite-Obsidian Alloy | `mvtink_alloy_bauxite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Bauxite-Obsidianite Alloy | `mvtink_alloy_bauxite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Bauxite-Opal Alloy | `mvtink_alloy_bauxite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Bauxite-Ender Pearl Core Alloy | `mvtink_alloy_bauxite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Bauxite-Phantomite Alloy | `mvtink_alloy_bauxite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Bauxite-Platinum Alloy | `mvtink_alloy_bauxite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Bauxite-Prismarine Alloy | `mvtink_alloy_bauxite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Bauxite-Pyrite Alloy | `mvtink_alloy_bauxite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Bauxite-Pyrophore Alloy | `mvtink_alloy_bauxite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Bauxite-Nether Quartz Alloy | `mvtink_alloy_bauxite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Bauxite-Redstone Alloy | `mvtink_alloy_bauxite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Bauxite-Resonite Alloy | `mvtink_alloy_bauxite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Bauxite-Ruby Alloy | `mvtink_alloy_bauxite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Bauxite-Sanguinite Alloy | `mvtink_alloy_bauxite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Bauxite-Sapphire Alloy | `mvtink_alloy_bauxite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Bauxite-Shadowgem Alloy | `mvtink_alloy_bauxite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Bauxite-Shulkerite Alloy | `mvtink_alloy_bauxite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Bauxite-Silver Alloy | `mvtink_alloy_bauxite_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Bauxite-Singularite Alloy | `mvtink_alloy_bauxite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Bauxite-Soul Glass Crystal Alloy | `mvtink_alloy_bauxite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Bauxite-Spatial Platinum Alloy | `mvtink_alloy_bauxite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Bauxite-Starlight Silver Alloy | `mvtink_alloy_bauxite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Bauxite-Steel Alloy | `mvtink_alloy_bauxite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Bauxite-Stibnite Alloy | `mvtink_alloy_bauxite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Bauxite-Sulfur Alloy | `mvtink_alloy_bauxite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Bauxite-Talc Alloy | `mvtink_alloy_bauxite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Bauxite-Tesseract Crystal Alloy | `mvtink_alloy_bauxite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Bauxite-Tin Alloy | `mvtink_alloy_bauxite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Bauxite-Titanium Alloy | `mvtink_alloy_bauxite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Bauxite-Topaz Alloy | `mvtink_alloy_bauxite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Bauxite-Tourmaline Alloy | `mvtink_alloy_bauxite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Bauxite-Tungsten Alloy | `mvtink_alloy_bauxite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Bauxite-Void Pyrite Alloy | `mvtink_alloy_bauxite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Bauxite-Void Titanium Alloy | `mvtink_alloy_bauxite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Bauxite-Voidstone Alloy | `mvtink_alloy_bauxite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Bauxite-Compacted Volcanic Ash Alloy | `mvtink_alloy_bauxite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Bauxite-Warped Emerald Alloy | `mvtink_alloy_bauxite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Bauxite-Warped Quartz Alloy | `mvtink_alloy_bauxite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Bauxite-Pure Weeping Shard Alloy | `mvtink_alloy_bauxite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Bauxite-Witherite Alloy | `mvtink_alloy_bauxite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Bauxite-Zero-Point Shard Alloy | `mvtink_alloy_bauxite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Bauxite-Zinc Alloy | `mvtink_alloy_bauxite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Bauxite-Zircon Alloy | `mvtink_alloy_bauxite_zircon` | Terrain, Resonant, Swift |

#### Beryllium · `mvtink_beryllium` · 97 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Bismuth (`mvtink_bismuth`) | Beryllium-Bismuth Alloy | `mvtink_alloy_beryllium_bismuth` | Terrain, Tempered, Swift |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Beryllium-Blackstone Pyrite Alloy | `mvtink_alloy_beryllium_blackstone_pyrite` | Infernal, Terrain, Tempered |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Beryllium-Blazesteel Shard Alloy | `mvtink_alloy_beryllium_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Beryllium-Borax Alloy | `mvtink_alloy_beryllium_borax` | Terrain, Tempered, Swift |
| Pure Calcite (`mvtink_calcite_gem`) | Beryllium-Pure Calcite Alloy | `mvtink_alloy_beryllium_calcite_gem` | Terrain, Tempered, Resonant |
| Celestine (`mvtink_celestine`) | Beryllium-Celestine Alloy | `mvtink_alloy_beryllium_celestine` | Void, Terrain, Tempered |
| Chorus Crystal (`mvtink_chorus_crystal`) | Beryllium-Chorus Crystal Alloy | `mvtink_alloy_beryllium_chorus_crystal` | Void, Terrain, Tempered |
| Chromite (`mvtink_chromite`) | Beryllium-Chromite Alloy | `mvtink_alloy_beryllium_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Beryllium-Chrono Crystal Alloy | `mvtink_alloy_beryllium_chrono_crystal` | Void, Terrain, Tempered |
| Cinderite (`mvtink_cinderite`) | Beryllium-Cinderite Alloy | `mvtink_alloy_beryllium_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Beryllium-Cinnabar Alloy | `mvtink_alloy_beryllium_cinnabar` | Terrain, Tempered, Swift |
| Coal (`mvtink_coal`) | Beryllium-Coal Alloy | `mvtink_alloy_beryllium_coal` | Primal, Terrain, Tempered |
| Cobalt (`mvtink_cobalt`) | Beryllium-Cobalt Alloy | `mvtink_alloy_beryllium_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Beryllium-Copper Alloy | `mvtink_alloy_beryllium_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Beryllium-Cosmium Alloy | `mvtink_alloy_beryllium_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Beryllium-Crimson Gold Alloy | `mvtink_alloy_beryllium_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Beryllium-Crimson Quartz Alloy | `mvtink_alloy_beryllium_crimson_quartz` | Infernal, Terrain, Tempered |
| Cryolite (`mvtink_cryolite`) | Beryllium-Cryolite Alloy | `mvtink_alloy_beryllium_cryolite` | Terrain, Tempered, Swift |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Beryllium-Cursed Brimstone Alloy | `mvtink_alloy_beryllium_cursed_brimstone` | Infernal, Terrain, Tempered |
| Diamond (`mvtink_diamond`) | Beryllium-Diamond Alloy | `mvtink_alloy_beryllium_diamond` | Primal, Terrain, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Beryllium-Dragon Scale Shard Alloy | `mvtink_alloy_beryllium_dragon_shard` | Void, Terrain, Tempered |
| Eclipse Gem (`mvtink_eclipse_gem`) | Beryllium-Eclipse Gem Alloy | `mvtink_alloy_beryllium_eclipse_gem` | Void, Terrain, Tempered |
| Emerald (`mvtink_emerald`) | Beryllium-Emerald Alloy | `mvtink_alloy_beryllium_emerald` | Primal, Terrain, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Beryllium-End Crystal Shard Alloy | `mvtink_alloy_beryllium_end_crystal_shard` | Void, Terrain, Tempered |
| Enderite (`mvtink_enderite`) | Beryllium-Enderite Alloy | `mvtink_alloy_beryllium_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Beryllium-Fire Opal Alloy | `mvtink_alloy_beryllium_fire_opal` | Infernal, Terrain, Tempered |
| Flint (`mvtink_flint`) | Beryllium-Flint Alloy | `mvtink_alloy_beryllium_flint` | Primal, Terrain, Tempered |
| Fluorite (`mvtink_fluorite`) | Beryllium-Fluorite Alloy | `mvtink_alloy_beryllium_fluorite` | Terrain, Tempered, Resonant |
| Galena (`mvtink_galena`) | Beryllium-Galena Alloy | `mvtink_alloy_beryllium_galena` | Terrain, Tempered, Swift |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Beryllium-Ghast Tear Shard Alloy | `mvtink_alloy_beryllium_ghast_tear_shard` | Infernal, Terrain, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Beryllium-Glowstone Gem Alloy | `mvtink_alloy_beryllium_glowstone_gem` | Infernal, Terrain, Tempered |
| Gold (`mvtink_gold`) | Beryllium-Gold Alloy | `mvtink_alloy_beryllium_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Beryllium-Graphite Alloy | `mvtink_alloy_beryllium_graphite` | Terrain, Tempered, Swift |
| Gravitite (`mvtink_gravitite`) | Beryllium-Gravitite Alloy | `mvtink_alloy_beryllium_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Beryllium-Gypsum Alloy | `mvtink_alloy_beryllium_gypsum` | Terrain, Tempered, Swift |
| Helliron (`mvtink_helliron`) | Beryllium-Helliron Alloy | `mvtink_alloy_beryllium_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Beryllium-Ignis Ferrum Alloy | `mvtink_alloy_beryllium_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Beryllium-Infernal Obsidian Alloy | `mvtink_alloy_beryllium_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Beryllium-Netherite-Infused Quartz Alloy | `mvtink_alloy_beryllium_infused_quartz` | Infernal, Terrain, Tempered |
| Iron (`mvtink_iron`) | Beryllium-Iron Alloy | `mvtink_alloy_beryllium_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Beryllium-Jade Alloy | `mvtink_alloy_beryllium_jade` | Terrain, Tempered, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Beryllium-Kaolinite Alloy | `mvtink_alloy_beryllium_kaolinite` | Terrain, Tempered, Swift |
| Lapis Lazuli (`mvtink_lapis`) | Beryllium-Lapis Lazuli Alloy | `mvtink_alloy_beryllium_lapis` | Primal, Terrain, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Beryllium-Lapis Matrix Alloy | `mvtink_alloy_beryllium_lapis_matrix` | Terrain, Tempered, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Beryllium-Magma Brimstone Alloy | `mvtink_alloy_beryllium_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Beryllium-Magmacite Alloy | `mvtink_alloy_beryllium_magmacite` | Infernal, Terrain, Tempered |
| Magnetite (`mvtink_magnetite`) | Beryllium-Magnetite Alloy | `mvtink_alloy_beryllium_magnetite` | Terrain, Tempered, Swift |
| Malachite (`mvtink_malachite`) | Beryllium-Malachite Alloy | `mvtink_alloy_beryllium_malachite` | Terrain, Tempered, Swift |
| Nebulite (`mvtink_nebulite`) | Beryllium-Nebulite Alloy | `mvtink_alloy_beryllium_nebulite` | Void, Terrain, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Beryllium-Nether Bismuth Alloy | `mvtink_alloy_beryllium_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Beryllium-Nether Tungsten Alloy | `mvtink_alloy_beryllium_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Beryllium-Netherite Scrap Shard Alloy | `mvtink_alloy_beryllium_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Beryllium-Nickel Alloy | `mvtink_alloy_beryllium_nickel` | Terrain, Tempered, Swift |
| Null-Shard (`mvtink_null_shard`) | Beryllium-Null-Shard Alloy | `mvtink_alloy_beryllium_null_shard` | Void, Terrain, Tempered |
| Obsidian (`mvtink_obsidian`) | Beryllium-Obsidian Alloy | `mvtink_alloy_beryllium_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Beryllium-Obsidianite Alloy | `mvtink_alloy_beryllium_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Beryllium-Opal Alloy | `mvtink_alloy_beryllium_opal` | Terrain, Tempered, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Beryllium-Ender Pearl Core Alloy | `mvtink_alloy_beryllium_pearl_core` | Void, Terrain, Tempered |
| Phantomite (`mvtink_phantomite`) | Beryllium-Phantomite Alloy | `mvtink_alloy_beryllium_phantomite` | Void, Terrain, Tempered |
| Platinum (`mvtink_platinum`) | Beryllium-Platinum Alloy | `mvtink_alloy_beryllium_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Beryllium-Prismarine Alloy | `mvtink_alloy_beryllium_prismarine` | Primal, Terrain, Tempered |
| Pyrite (`mvtink_pyrite`) | Beryllium-Pyrite Alloy | `mvtink_alloy_beryllium_pyrite` | Terrain, Tempered, Swift |
| Pyrophore (`mvtink_pyrophore`) | Beryllium-Pyrophore Alloy | `mvtink_alloy_beryllium_pyrophore` | Infernal, Terrain, Tempered |
| Nether Quartz (`mvtink_quartz`) | Beryllium-Nether Quartz Alloy | `mvtink_alloy_beryllium_quartz` | Primal, Terrain, Tempered |
| Redstone (`mvtink_redstone`) | Beryllium-Redstone Alloy | `mvtink_alloy_beryllium_redstone` | Primal, Terrain, Tempered |
| Resonite (`mvtink_resonite`) | Beryllium-Resonite Alloy | `mvtink_alloy_beryllium_resonite` | Void, Terrain, Tempered |
| Ruby (`mvtink_ruby`) | Beryllium-Ruby Alloy | `mvtink_alloy_beryllium_ruby` | Terrain, Tempered, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Beryllium-Sanguinite Alloy | `mvtink_alloy_beryllium_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Beryllium-Sapphire Alloy | `mvtink_alloy_beryllium_sapphire` | Terrain, Tempered, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Beryllium-Shadowgem Alloy | `mvtink_alloy_beryllium_shadowgem` | Void, Terrain, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Beryllium-Shulkerite Alloy | `mvtink_alloy_beryllium_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Beryllium-Silver Alloy | `mvtink_alloy_beryllium_silver` | Terrain, Tempered, Swift |
| Singularite (`mvtink_singularite`) | Beryllium-Singularite Alloy | `mvtink_alloy_beryllium_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Beryllium-Soul Glass Crystal Alloy | `mvtink_alloy_beryllium_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Beryllium-Spatial Platinum Alloy | `mvtink_alloy_beryllium_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Beryllium-Starlight Silver Alloy | `mvtink_alloy_beryllium_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Beryllium-Steel Alloy | `mvtink_alloy_beryllium_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Beryllium-Stibnite Alloy | `mvtink_alloy_beryllium_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Beryllium-Sulfur Alloy | `mvtink_alloy_beryllium_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Beryllium-Talc Alloy | `mvtink_alloy_beryllium_talc` | Terrain, Tempered, Swift |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Beryllium-Tesseract Crystal Alloy | `mvtink_alloy_beryllium_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Beryllium-Tin Alloy | `mvtink_alloy_beryllium_tin` | Terrain, Tempered, Swift |
| Titanium (`mvtink_titanium`) | Beryllium-Titanium Alloy | `mvtink_alloy_beryllium_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Beryllium-Topaz Alloy | `mvtink_alloy_beryllium_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Beryllium-Tourmaline Alloy | `mvtink_alloy_beryllium_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Beryllium-Tungsten Alloy | `mvtink_alloy_beryllium_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Beryllium-Void Pyrite Alloy | `mvtink_alloy_beryllium_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Beryllium-Void Titanium Alloy | `mvtink_alloy_beryllium_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Beryllium-Voidstone Alloy | `mvtink_alloy_beryllium_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Beryllium-Compacted Volcanic Ash Alloy | `mvtink_alloy_beryllium_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Beryllium-Warped Emerald Alloy | `mvtink_alloy_beryllium_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Beryllium-Warped Quartz Alloy | `mvtink_alloy_beryllium_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Beryllium-Pure Weeping Shard Alloy | `mvtink_alloy_beryllium_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Beryllium-Witherite Alloy | `mvtink_alloy_beryllium_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Beryllium-Zero-Point Shard Alloy | `mvtink_alloy_beryllium_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Beryllium-Zinc Alloy | `mvtink_alloy_beryllium_zinc` | Terrain, Tempered, Swift |
| Zircon (`mvtink_zircon`) | Beryllium-Zircon Alloy | `mvtink_alloy_beryllium_zircon` | Terrain, Tempered, Resonant |

#### Bismuth · `mvtink_bismuth` · 96 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Bismuth-Blackstone Pyrite Alloy | `mvtink_alloy_bismuth_blackstone_pyrite` | Infernal, Terrain, Tempered |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Bismuth-Blazesteel Shard Alloy | `mvtink_alloy_bismuth_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Bismuth-Borax Alloy | `mvtink_alloy_bismuth_borax` | Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Bismuth-Pure Calcite Alloy | `mvtink_alloy_bismuth_calcite_gem` | Terrain, Tempered, Resonant |
| Celestine (`mvtink_celestine`) | Bismuth-Celestine Alloy | `mvtink_alloy_bismuth_celestine` | Void, Terrain, Tempered |
| Chorus Crystal (`mvtink_chorus_crystal`) | Bismuth-Chorus Crystal Alloy | `mvtink_alloy_bismuth_chorus_crystal` | Void, Terrain, Tempered |
| Chromite (`mvtink_chromite`) | Bismuth-Chromite Alloy | `mvtink_alloy_bismuth_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Bismuth-Chrono Crystal Alloy | `mvtink_alloy_bismuth_chrono_crystal` | Void, Terrain, Tempered |
| Cinderite (`mvtink_cinderite`) | Bismuth-Cinderite Alloy | `mvtink_alloy_bismuth_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Bismuth-Cinnabar Alloy | `mvtink_alloy_bismuth_cinnabar` | Terrain, Tempered |
| Coal (`mvtink_coal`) | Bismuth-Coal Alloy | `mvtink_alloy_bismuth_coal` | Primal, Terrain, Tempered |
| Cobalt (`mvtink_cobalt`) | Bismuth-Cobalt Alloy | `mvtink_alloy_bismuth_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Bismuth-Copper Alloy | `mvtink_alloy_bismuth_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Bismuth-Cosmium Alloy | `mvtink_alloy_bismuth_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Bismuth-Crimson Gold Alloy | `mvtink_alloy_bismuth_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Bismuth-Crimson Quartz Alloy | `mvtink_alloy_bismuth_crimson_quartz` | Infernal, Terrain, Tempered |
| Cryolite (`mvtink_cryolite`) | Bismuth-Cryolite Alloy | `mvtink_alloy_bismuth_cryolite` | Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Bismuth-Cursed Brimstone Alloy | `mvtink_alloy_bismuth_cursed_brimstone` | Infernal, Terrain, Tempered |
| Diamond (`mvtink_diamond`) | Bismuth-Diamond Alloy | `mvtink_alloy_bismuth_diamond` | Primal, Terrain, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Bismuth-Dragon Scale Shard Alloy | `mvtink_alloy_bismuth_dragon_shard` | Void, Terrain, Tempered |
| Eclipse Gem (`mvtink_eclipse_gem`) | Bismuth-Eclipse Gem Alloy | `mvtink_alloy_bismuth_eclipse_gem` | Void, Terrain, Tempered |
| Emerald (`mvtink_emerald`) | Bismuth-Emerald Alloy | `mvtink_alloy_bismuth_emerald` | Primal, Terrain, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Bismuth-End Crystal Shard Alloy | `mvtink_alloy_bismuth_end_crystal_shard` | Void, Terrain, Tempered |
| Enderite (`mvtink_enderite`) | Bismuth-Enderite Alloy | `mvtink_alloy_bismuth_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) ★ | Hellfire Bismuth | `mvtink_hellfire_bismuth` | Infernal, Terrain, Tempered |
| Flint (`mvtink_flint`) | Bismuth-Flint Alloy | `mvtink_alloy_bismuth_flint` | Primal, Terrain, Tempered |
| Fluorite (`mvtink_fluorite`) | Bismuth-Fluorite Alloy | `mvtink_alloy_bismuth_fluorite` | Terrain, Tempered, Resonant |
| Galena (`mvtink_galena`) | Bismuth-Galena Alloy | `mvtink_alloy_bismuth_galena` | Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Bismuth-Ghast Tear Shard Alloy | `mvtink_alloy_bismuth_ghast_tear_shard` | Infernal, Terrain, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Bismuth-Glowstone Gem Alloy | `mvtink_alloy_bismuth_glowstone_gem` | Infernal, Terrain, Tempered |
| Gold (`mvtink_gold`) | Bismuth-Gold Alloy | `mvtink_alloy_bismuth_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Bismuth-Graphite Alloy | `mvtink_alloy_bismuth_graphite` | Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Bismuth-Gravitite Alloy | `mvtink_alloy_bismuth_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Bismuth-Gypsum Alloy | `mvtink_alloy_bismuth_gypsum` | Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Bismuth-Helliron Alloy | `mvtink_alloy_bismuth_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Bismuth-Ignis Ferrum Alloy | `mvtink_alloy_bismuth_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Bismuth-Infernal Obsidian Alloy | `mvtink_alloy_bismuth_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Bismuth-Netherite-Infused Quartz Alloy | `mvtink_alloy_bismuth_infused_quartz` | Infernal, Terrain, Tempered |
| Iron (`mvtink_iron`) | Bismuth-Iron Alloy | `mvtink_alloy_bismuth_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Bismuth-Jade Alloy | `mvtink_alloy_bismuth_jade` | Terrain, Tempered, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Bismuth-Kaolinite Alloy | `mvtink_alloy_bismuth_kaolinite` | Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Bismuth-Lapis Lazuli Alloy | `mvtink_alloy_bismuth_lapis` | Primal, Terrain, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Bismuth-Lapis Matrix Alloy | `mvtink_alloy_bismuth_lapis_matrix` | Terrain, Tempered, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Bismuth-Magma Brimstone Alloy | `mvtink_alloy_bismuth_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Bismuth-Magmacite Alloy | `mvtink_alloy_bismuth_magmacite` | Infernal, Terrain, Tempered |
| Magnetite (`mvtink_magnetite`) | Bismuth-Magnetite Alloy | `mvtink_alloy_bismuth_magnetite` | Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Bismuth-Malachite Alloy | `mvtink_alloy_bismuth_malachite` | Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Bismuth-Nebulite Alloy | `mvtink_alloy_bismuth_nebulite` | Void, Terrain, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Bismuth-Nether Bismuth Alloy | `mvtink_alloy_bismuth_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Bismuth-Nether Tungsten Alloy | `mvtink_alloy_bismuth_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Bismuth-Netherite Scrap Shard Alloy | `mvtink_alloy_bismuth_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Bismuth-Nickel Alloy | `mvtink_alloy_bismuth_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Bismuth-Null-Shard Alloy | `mvtink_alloy_bismuth_null_shard` | Void, Terrain, Tempered |
| Obsidian (`mvtink_obsidian`) | Bismuth-Obsidian Alloy | `mvtink_alloy_bismuth_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Bismuth-Obsidianite Alloy | `mvtink_alloy_bismuth_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Bismuth-Opal Alloy | `mvtink_alloy_bismuth_opal` | Terrain, Tempered, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Bismuth-Ender Pearl Core Alloy | `mvtink_alloy_bismuth_pearl_core` | Void, Terrain, Tempered |
| Phantomite (`mvtink_phantomite`) | Bismuth-Phantomite Alloy | `mvtink_alloy_bismuth_phantomite` | Void, Terrain, Tempered |
| Platinum (`mvtink_platinum`) | Bismuth-Platinum Alloy | `mvtink_alloy_bismuth_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Bismuth-Prismarine Alloy | `mvtink_alloy_bismuth_prismarine` | Primal, Terrain, Tempered |
| Pyrite (`mvtink_pyrite`) | Bismuth-Pyrite Alloy | `mvtink_alloy_bismuth_pyrite` | Terrain, Tempered, Swift |
| Pyrophore (`mvtink_pyrophore`) | Bismuth-Pyrophore Alloy | `mvtink_alloy_bismuth_pyrophore` | Infernal, Terrain, Tempered |
| Nether Quartz (`mvtink_quartz`) | Bismuth-Nether Quartz Alloy | `mvtink_alloy_bismuth_quartz` | Primal, Terrain, Tempered |
| Redstone (`mvtink_redstone`) | Bismuth-Redstone Alloy | `mvtink_alloy_bismuth_redstone` | Primal, Terrain, Tempered |
| Resonite (`mvtink_resonite`) | Bismuth-Resonite Alloy | `mvtink_alloy_bismuth_resonite` | Void, Terrain, Tempered |
| Ruby (`mvtink_ruby`) | Bismuth-Ruby Alloy | `mvtink_alloy_bismuth_ruby` | Terrain, Tempered, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Bismuth-Sanguinite Alloy | `mvtink_alloy_bismuth_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Bismuth-Sapphire Alloy | `mvtink_alloy_bismuth_sapphire` | Terrain, Tempered, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Bismuth-Shadowgem Alloy | `mvtink_alloy_bismuth_shadowgem` | Void, Terrain, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Bismuth-Shulkerite Alloy | `mvtink_alloy_bismuth_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Bismuth-Silver Alloy | `mvtink_alloy_bismuth_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Bismuth-Singularite Alloy | `mvtink_alloy_bismuth_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Bismuth-Soul Glass Crystal Alloy | `mvtink_alloy_bismuth_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Bismuth-Spatial Platinum Alloy | `mvtink_alloy_bismuth_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Bismuth-Starlight Silver Alloy | `mvtink_alloy_bismuth_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Bismuth-Steel Alloy | `mvtink_alloy_bismuth_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Bismuth-Stibnite Alloy | `mvtink_alloy_bismuth_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Bismuth-Sulfur Alloy | `mvtink_alloy_bismuth_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Bismuth-Talc Alloy | `mvtink_alloy_bismuth_talc` | Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Bismuth-Tesseract Crystal Alloy | `mvtink_alloy_bismuth_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Bismuth-Tin Alloy | `mvtink_alloy_bismuth_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Bismuth-Titanium Alloy | `mvtink_alloy_bismuth_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Bismuth-Topaz Alloy | `mvtink_alloy_bismuth_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Bismuth-Tourmaline Alloy | `mvtink_alloy_bismuth_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Bismuth-Tungsten Alloy | `mvtink_alloy_bismuth_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Bismuth-Void Pyrite Alloy | `mvtink_alloy_bismuth_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Bismuth-Void Titanium Alloy | `mvtink_alloy_bismuth_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Bismuth-Voidstone Alloy | `mvtink_alloy_bismuth_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Bismuth-Compacted Volcanic Ash Alloy | `mvtink_alloy_bismuth_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Bismuth-Warped Emerald Alloy | `mvtink_alloy_bismuth_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Bismuth-Warped Quartz Alloy | `mvtink_alloy_bismuth_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Bismuth-Pure Weeping Shard Alloy | `mvtink_alloy_bismuth_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Bismuth-Witherite Alloy | `mvtink_alloy_bismuth_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Bismuth-Zero-Point Shard Alloy | `mvtink_alloy_bismuth_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Bismuth-Zinc Alloy | `mvtink_alloy_bismuth_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Bismuth-Zircon Alloy | `mvtink_alloy_bismuth_zircon` | Terrain, Tempered, Resonant |

#### Borax · `mvtink_borax` · 93 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Pure Calcite (`mvtink_calcite_gem`) | Borax-Pure Calcite Alloy | `mvtink_alloy_borax_calcite_gem` | Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Borax-Celestine Alloy | `mvtink_alloy_borax_celestine` | Void, Terrain, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Borax-Chorus Crystal Alloy | `mvtink_alloy_borax_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Borax-Chromite Alloy | `mvtink_alloy_borax_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Borax-Chrono Crystal Alloy | `mvtink_alloy_borax_chrono_crystal` | Void, Terrain, Resonant |
| Cinderite (`mvtink_cinderite`) | Borax-Cinderite Alloy | `mvtink_alloy_borax_cinderite` | Infernal, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Borax-Cinnabar Alloy | `mvtink_alloy_borax_cinnabar` | Terrain |
| Coal (`mvtink_coal`) | Borax-Coal Alloy | `mvtink_alloy_borax_coal` | Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Borax-Cobalt Alloy | `mvtink_alloy_borax_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Borax-Copper Alloy | `mvtink_alloy_borax_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Borax-Cosmium Alloy | `mvtink_alloy_borax_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Borax-Crimson Gold Alloy | `mvtink_alloy_borax_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Borax-Crimson Quartz Alloy | `mvtink_alloy_borax_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Borax-Cryolite Alloy | `mvtink_alloy_borax_cryolite` | Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Borax-Cursed Brimstone Alloy | `mvtink_alloy_borax_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Borax-Diamond Alloy | `mvtink_alloy_borax_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Borax-Dragon Scale Shard Alloy | `mvtink_alloy_borax_dragon_shard` | Void, Terrain, Swift |
| Eclipse Gem (`mvtink_eclipse_gem`) | Borax-Eclipse Gem Alloy | `mvtink_alloy_borax_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Borax-Emerald Alloy | `mvtink_alloy_borax_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Borax-End Crystal Shard Alloy | `mvtink_alloy_borax_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Borax-Enderite Alloy | `mvtink_alloy_borax_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Borax-Fire Opal Alloy | `mvtink_alloy_borax_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Borax-Flint Alloy | `mvtink_alloy_borax_flint` | Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Borax-Fluorite Alloy | `mvtink_alloy_borax_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Borax-Galena Alloy | `mvtink_alloy_borax_galena` | Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Borax-Ghast Tear Shard Alloy | `mvtink_alloy_borax_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Borax-Glowstone Gem Alloy | `mvtink_alloy_borax_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Borax-Gold Alloy | `mvtink_alloy_borax_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Borax-Graphite Alloy | `mvtink_alloy_borax_graphite` | Terrain |
| Gravitite (`mvtink_gravitite`) | Borax-Gravitite Alloy | `mvtink_alloy_borax_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Borax-Gypsum Alloy | `mvtink_alloy_borax_gypsum` | Terrain |
| Helliron (`mvtink_helliron`) | Borax-Helliron Alloy | `mvtink_alloy_borax_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Borax-Ignis Ferrum Alloy | `mvtink_alloy_borax_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Borax-Infernal Obsidian Alloy | `mvtink_alloy_borax_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Borax-Netherite-Infused Quartz Alloy | `mvtink_alloy_borax_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Borax-Iron Alloy | `mvtink_alloy_borax_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Borax-Jade Alloy | `mvtink_alloy_borax_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Borax-Kaolinite Alloy | `mvtink_alloy_borax_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Borax-Lapis Lazuli Alloy | `mvtink_alloy_borax_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Borax-Lapis Matrix Alloy | `mvtink_alloy_borax_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Borax-Magma Brimstone Alloy | `mvtink_alloy_borax_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Borax-Magmacite Alloy | `mvtink_alloy_borax_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Borax-Magnetite Alloy | `mvtink_alloy_borax_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Borax-Malachite Alloy | `mvtink_alloy_borax_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Borax-Nebulite Alloy | `mvtink_alloy_borax_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Borax-Nether Bismuth Alloy | `mvtink_alloy_borax_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Borax-Nether Tungsten Alloy | `mvtink_alloy_borax_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Borax-Netherite Scrap Shard Alloy | `mvtink_alloy_borax_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Borax-Nickel Alloy | `mvtink_alloy_borax_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Borax-Null-Shard Alloy | `mvtink_alloy_borax_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Borax-Obsidian Alloy | `mvtink_alloy_borax_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Borax-Obsidianite Alloy | `mvtink_alloy_borax_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Borax-Opal Alloy | `mvtink_alloy_borax_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Borax-Ender Pearl Core Alloy | `mvtink_alloy_borax_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Borax-Phantomite Alloy | `mvtink_alloy_borax_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Borax-Platinum Alloy | `mvtink_alloy_borax_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Borax-Prismarine Alloy | `mvtink_alloy_borax_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Borax-Pyrite Alloy | `mvtink_alloy_borax_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Borax-Pyrophore Alloy | `mvtink_alloy_borax_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Borax-Nether Quartz Alloy | `mvtink_alloy_borax_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Borax-Redstone Alloy | `mvtink_alloy_borax_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Borax-Resonite Alloy | `mvtink_alloy_borax_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Borax-Ruby Alloy | `mvtink_alloy_borax_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Borax-Sanguinite Alloy | `mvtink_alloy_borax_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Borax-Sapphire Alloy | `mvtink_alloy_borax_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Borax-Shadowgem Alloy | `mvtink_alloy_borax_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Borax-Shulkerite Alloy | `mvtink_alloy_borax_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Borax-Silver Alloy | `mvtink_alloy_borax_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Borax-Singularite Alloy | `mvtink_alloy_borax_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Borax-Soul Glass Crystal Alloy | `mvtink_alloy_borax_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Borax-Spatial Platinum Alloy | `mvtink_alloy_borax_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Borax-Starlight Silver Alloy | `mvtink_alloy_borax_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Borax-Steel Alloy | `mvtink_alloy_borax_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Borax-Stibnite Alloy | `mvtink_alloy_borax_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Borax-Sulfur Alloy | `mvtink_alloy_borax_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Borax-Talc Alloy | `mvtink_alloy_borax_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Borax-Tesseract Crystal Alloy | `mvtink_alloy_borax_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Borax-Tin Alloy | `mvtink_alloy_borax_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Borax-Titanium Alloy | `mvtink_alloy_borax_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Borax-Topaz Alloy | `mvtink_alloy_borax_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Borax-Tourmaline Alloy | `mvtink_alloy_borax_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Borax-Tungsten Alloy | `mvtink_alloy_borax_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Borax-Void Pyrite Alloy | `mvtink_alloy_borax_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Borax-Void Titanium Alloy | `mvtink_alloy_borax_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Borax-Voidstone Alloy | `mvtink_alloy_borax_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Borax-Compacted Volcanic Ash Alloy | `mvtink_alloy_borax_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Borax-Warped Emerald Alloy | `mvtink_alloy_borax_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Borax-Warped Quartz Alloy | `mvtink_alloy_borax_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Borax-Pure Weeping Shard Alloy | `mvtink_alloy_borax_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Borax-Witherite Alloy | `mvtink_alloy_borax_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Borax-Zero-Point Shard Alloy | `mvtink_alloy_borax_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Borax-Zinc Alloy | `mvtink_alloy_borax_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Borax-Zircon Alloy | `mvtink_alloy_borax_zircon` | Terrain, Resonant, Swift |

#### Pure Calcite · `mvtink_calcite_gem` · 92 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Celestine (`mvtink_celestine`) | Pure Calcite-Celestine Alloy | `mvtink_alloy_calcite_gem_celestine` | Void, Terrain, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Pure Calcite-Chorus Crystal Alloy | `mvtink_alloy_calcite_gem_chorus_crystal` | Void, Terrain, Radiant |
| Chromite (`mvtink_chromite`) | Pure Calcite-Chromite Alloy | `mvtink_alloy_calcite_gem_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Pure Calcite-Chrono Crystal Alloy | `mvtink_alloy_calcite_gem_chrono_crystal` | Void, Terrain, Resonant |
| Cinderite (`mvtink_cinderite`) | Pure Calcite-Cinderite Alloy | `mvtink_alloy_calcite_gem_cinderite` | Infernal, Terrain, Resonant |
| Cinnabar (`mvtink_cinnabar`) | Pure Calcite-Cinnabar Alloy | `mvtink_alloy_calcite_gem_cinnabar` | Terrain, Resonant |
| Coal (`mvtink_coal`) | Pure Calcite-Coal Alloy | `mvtink_alloy_calcite_gem_coal` | Primal, Terrain, Resonant |
| Cobalt (`mvtink_cobalt`) | Pure Calcite-Cobalt Alloy | `mvtink_alloy_calcite_gem_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Pure Calcite-Copper Alloy | `mvtink_alloy_calcite_gem_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Pure Calcite-Cosmium Alloy | `mvtink_alloy_calcite_gem_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Pure Calcite-Crimson Gold Alloy | `mvtink_alloy_calcite_gem_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Pure Calcite-Crimson Quartz Alloy | `mvtink_alloy_calcite_gem_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Pure Calcite-Cryolite Alloy | `mvtink_alloy_calcite_gem_cryolite` | Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Pure Calcite-Cursed Brimstone Alloy | `mvtink_alloy_calcite_gem_cursed_brimstone` | Infernal, Terrain, Resonant |
| Diamond (`mvtink_diamond`) | Pure Calcite-Diamond Alloy | `mvtink_alloy_calcite_gem_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Pure Calcite-Dragon Scale Shard Alloy | `mvtink_alloy_calcite_gem_dragon_shard` | Void, Terrain, Resonant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Pure Calcite-Eclipse Gem Alloy | `mvtink_alloy_calcite_gem_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Pure Calcite-Emerald Alloy | `mvtink_alloy_calcite_gem_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Pure Calcite-End Crystal Shard Alloy | `mvtink_alloy_calcite_gem_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Pure Calcite-Enderite Alloy | `mvtink_alloy_calcite_gem_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Pure Calcite-Fire Opal Alloy | `mvtink_alloy_calcite_gem_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Pure Calcite-Flint Alloy | `mvtink_alloy_calcite_gem_flint` | Primal, Terrain, Resonant |
| Fluorite (`mvtink_fluorite`) | Pure Calcite-Fluorite Alloy | `mvtink_alloy_calcite_gem_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Pure Calcite-Galena Alloy | `mvtink_alloy_calcite_gem_galena` | Terrain, Tempered, Resonant |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Pure Calcite-Ghast Tear Shard Alloy | `mvtink_alloy_calcite_gem_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Pure Calcite-Glowstone Gem Alloy | `mvtink_alloy_calcite_gem_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Pure Calcite-Gold Alloy | `mvtink_alloy_calcite_gem_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Pure Calcite-Graphite Alloy | `mvtink_alloy_calcite_gem_graphite` | Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Pure Calcite-Gravitite Alloy | `mvtink_alloy_calcite_gem_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Pure Calcite-Gypsum Alloy | `mvtink_alloy_calcite_gem_gypsum` | Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Pure Calcite-Helliron Alloy | `mvtink_alloy_calcite_gem_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Pure Calcite-Ignis Ferrum Alloy | `mvtink_alloy_calcite_gem_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Pure Calcite-Infernal Obsidian Alloy | `mvtink_alloy_calcite_gem_infernal_obsidian` | Infernal, Terrain, Resonant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Pure Calcite-Netherite-Infused Quartz Alloy | `mvtink_alloy_calcite_gem_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Pure Calcite-Iron Alloy | `mvtink_alloy_calcite_gem_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Pure Calcite-Jade Alloy | `mvtink_alloy_calcite_gem_jade` | Terrain, Radiant, Resonant |
| Kaolinite (`mvtink_kaolinite`) | Pure Calcite-Kaolinite Alloy | `mvtink_alloy_calcite_gem_kaolinite` | Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Pure Calcite-Lapis Lazuli Alloy | `mvtink_alloy_calcite_gem_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Pure Calcite-Lapis Matrix Alloy | `mvtink_alloy_calcite_gem_lapis_matrix` | Terrain, Radiant, Resonant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Pure Calcite-Magma Brimstone Alloy | `mvtink_alloy_calcite_gem_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Pure Calcite-Magmacite Alloy | `mvtink_alloy_calcite_gem_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Pure Calcite-Magnetite Alloy | `mvtink_alloy_calcite_gem_magnetite` | Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Pure Calcite-Malachite Alloy | `mvtink_alloy_calcite_gem_malachite` | Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Pure Calcite-Nebulite Alloy | `mvtink_alloy_calcite_gem_nebulite` | Void, Terrain, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Pure Calcite-Nether Bismuth Alloy | `mvtink_alloy_calcite_gem_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Pure Calcite-Nether Tungsten Alloy | `mvtink_alloy_calcite_gem_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Pure Calcite-Netherite Scrap Shard Alloy | `mvtink_alloy_calcite_gem_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Pure Calcite-Nickel Alloy | `mvtink_alloy_calcite_gem_nickel` | Terrain, Tempered, Resonant |
| Null-Shard (`mvtink_null_shard`) | Pure Calcite-Null-Shard Alloy | `mvtink_alloy_calcite_gem_null_shard` | Void, Terrain, Resonant |
| Obsidian (`mvtink_obsidian`) | Pure Calcite-Obsidian Alloy | `mvtink_alloy_calcite_gem_obsidian` | Primal, Terrain, Resonant |
| Obsidianite (`mvtink_obsidianite`) | Pure Calcite-Obsidianite Alloy | `mvtink_alloy_calcite_gem_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Pure Calcite-Opal Alloy | `mvtink_alloy_calcite_gem_opal` | Terrain, Radiant, Resonant |
| Ender Pearl Core (`mvtink_pearl_core`) | Pure Calcite-Ender Pearl Core Alloy | `mvtink_alloy_calcite_gem_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Pure Calcite-Phantomite Alloy | `mvtink_alloy_calcite_gem_phantomite` | Void, Terrain, Resonant |
| Platinum (`mvtink_platinum`) | Pure Calcite-Platinum Alloy | `mvtink_alloy_calcite_gem_platinum` | Terrain, Tempered, Resonant |
| Prismarine (`mvtink_prismarine`) | Pure Calcite-Prismarine Alloy | `mvtink_alloy_calcite_gem_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Pure Calcite-Pyrite Alloy | `mvtink_alloy_calcite_gem_pyrite` | Terrain, Resonant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Pure Calcite-Pyrophore Alloy | `mvtink_alloy_calcite_gem_pyrophore` | Infernal, Terrain, Resonant |
| Nether Quartz (`mvtink_quartz`) | Pure Calcite-Nether Quartz Alloy | `mvtink_alloy_calcite_gem_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Pure Calcite-Redstone Alloy | `mvtink_alloy_calcite_gem_redstone` | Primal, Terrain, Resonant |
| Resonite (`mvtink_resonite`) | Pure Calcite-Resonite Alloy | `mvtink_alloy_calcite_gem_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Pure Calcite-Ruby Alloy | `mvtink_alloy_calcite_gem_ruby` | Terrain, Radiant, Resonant |
| Sanguinite (`mvtink_sanguinite`) | Pure Calcite-Sanguinite Alloy | `mvtink_alloy_calcite_gem_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Pure Calcite-Sapphire Alloy | `mvtink_alloy_calcite_gem_sapphire` | Terrain, Radiant, Resonant |
| Shadowgem (`mvtink_shadowgem`) | Pure Calcite-Shadowgem Alloy | `mvtink_alloy_calcite_gem_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Pure Calcite-Shulkerite Alloy | `mvtink_alloy_calcite_gem_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Pure Calcite-Silver Alloy | `mvtink_alloy_calcite_gem_silver` | Terrain, Tempered, Resonant |
| Singularite (`mvtink_singularite`) | Pure Calcite-Singularite Alloy | `mvtink_alloy_calcite_gem_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Pure Calcite-Soul Glass Crystal Alloy | `mvtink_alloy_calcite_gem_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Pure Calcite-Spatial Platinum Alloy | `mvtink_alloy_calcite_gem_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Pure Calcite-Starlight Silver Alloy | `mvtink_alloy_calcite_gem_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Pure Calcite-Steel Alloy | `mvtink_alloy_calcite_gem_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Pure Calcite-Stibnite Alloy | `mvtink_alloy_calcite_gem_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Pure Calcite-Sulfur Alloy | `mvtink_alloy_calcite_gem_sulfur` | Infernal, Terrain, Resonant |
| Talc (`mvtink_talc`) | Pure Calcite-Talc Alloy | `mvtink_alloy_calcite_gem_talc` | Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Pure Calcite-Tesseract Crystal Alloy | `mvtink_alloy_calcite_gem_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Pure Calcite-Tin Alloy | `mvtink_alloy_calcite_gem_tin` | Terrain, Tempered, Resonant |
| Titanium (`mvtink_titanium`) | Pure Calcite-Titanium Alloy | `mvtink_alloy_calcite_gem_titanium` | Terrain, Tempered, Resonant |
| Topaz (`mvtink_topaz`) | Pure Calcite-Topaz Alloy | `mvtink_alloy_calcite_gem_topaz` | Terrain, Radiant, Resonant |
| Tourmaline (`mvtink_tourmaline`) | Pure Calcite-Tourmaline Alloy | `mvtink_alloy_calcite_gem_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Pure Calcite-Tungsten Alloy | `mvtink_alloy_calcite_gem_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Pure Calcite-Void Pyrite Alloy | `mvtink_alloy_calcite_gem_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Pure Calcite-Void Titanium Alloy | `mvtink_alloy_calcite_gem_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Pure Calcite-Voidstone Alloy | `mvtink_alloy_calcite_gem_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Pure Calcite-Compacted Volcanic Ash Alloy | `mvtink_alloy_calcite_gem_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Pure Calcite-Warped Emerald Alloy | `mvtink_alloy_calcite_gem_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Pure Calcite-Warped Quartz Alloy | `mvtink_alloy_calcite_gem_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Pure Calcite-Pure Weeping Shard Alloy | `mvtink_alloy_calcite_gem_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Pure Calcite-Witherite Alloy | `mvtink_alloy_calcite_gem_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Pure Calcite-Zero-Point Shard Alloy | `mvtink_alloy_calcite_gem_zero_point` | Void, Terrain, Resonant |
| Zinc (`mvtink_zinc`) | Pure Calcite-Zinc Alloy | `mvtink_alloy_calcite_gem_zinc` | Terrain, Tempered, Resonant |
| Zircon (`mvtink_zircon`) | Pure Calcite-Zircon Alloy | `mvtink_alloy_calcite_gem_zircon` | Terrain, Resonant, Swift |

#### Cinnabar · `mvtink_cinnabar` · 86 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Coal (`mvtink_coal`) | Cinnabar-Coal Alloy | `mvtink_alloy_cinnabar_coal` | Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Cinnabar-Cobalt Alloy | `mvtink_alloy_cinnabar_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Cinnabar-Copper Alloy | `mvtink_alloy_cinnabar_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Cinnabar-Cosmium Alloy | `mvtink_alloy_cinnabar_cosmium` | Void, Terrain, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Cinnabar-Crimson Gold Alloy | `mvtink_alloy_cinnabar_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Cinnabar-Crimson Quartz Alloy | `mvtink_alloy_cinnabar_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Cinnabar-Cryolite Alloy | `mvtink_alloy_cinnabar_cryolite` | Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Cinnabar-Cursed Brimstone Alloy | `mvtink_alloy_cinnabar_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Cinnabar-Diamond Alloy | `mvtink_alloy_cinnabar_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cinnabar-Dragon Scale Shard Alloy | `mvtink_alloy_cinnabar_dragon_shard` | Void, Terrain, Swift |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cinnabar-Eclipse Gem Alloy | `mvtink_alloy_cinnabar_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Cinnabar-Emerald Alloy | `mvtink_alloy_cinnabar_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cinnabar-End Crystal Shard Alloy | `mvtink_alloy_cinnabar_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Cinnabar-Enderite Alloy | `mvtink_alloy_cinnabar_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Cinnabar-Fire Opal Alloy | `mvtink_alloy_cinnabar_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Cinnabar-Flint Alloy | `mvtink_alloy_cinnabar_flint` | Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cinnabar-Fluorite Alloy | `mvtink_alloy_cinnabar_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Cinnabar-Galena Alloy | `mvtink_alloy_cinnabar_galena` | Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cinnabar-Ghast Tear Shard Alloy | `mvtink_alloy_cinnabar_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cinnabar-Glowstone Gem Alloy | `mvtink_alloy_cinnabar_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Cinnabar-Gold Alloy | `mvtink_alloy_cinnabar_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Cinnabar-Graphite Alloy | `mvtink_alloy_cinnabar_graphite` | Terrain |
| Gravitite (`mvtink_gravitite`) | Cinnabar-Gravitite Alloy | `mvtink_alloy_cinnabar_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Cinnabar-Gypsum Alloy | `mvtink_alloy_cinnabar_gypsum` | Terrain |
| Helliron (`mvtink_helliron`) | Cinnabar-Helliron Alloy | `mvtink_alloy_cinnabar_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cinnabar-Ignis Ferrum Alloy | `mvtink_alloy_cinnabar_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cinnabar-Infernal Obsidian Alloy | `mvtink_alloy_cinnabar_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cinnabar-Netherite-Infused Quartz Alloy | `mvtink_alloy_cinnabar_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Cinnabar-Iron Alloy | `mvtink_alloy_cinnabar_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Cinnabar-Jade Alloy | `mvtink_alloy_cinnabar_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Cinnabar-Kaolinite Alloy | `mvtink_alloy_cinnabar_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Cinnabar-Lapis Lazuli Alloy | `mvtink_alloy_cinnabar_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cinnabar-Lapis Matrix Alloy | `mvtink_alloy_cinnabar_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cinnabar-Magma Brimstone Alloy | `mvtink_alloy_cinnabar_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Cinnabar-Magmacite Alloy | `mvtink_alloy_cinnabar_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Cinnabar-Magnetite Alloy | `mvtink_alloy_cinnabar_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Cinnabar-Malachite Alloy | `mvtink_alloy_cinnabar_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Cinnabar-Nebulite Alloy | `mvtink_alloy_cinnabar_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cinnabar-Nether Bismuth Alloy | `mvtink_alloy_cinnabar_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cinnabar-Nether Tungsten Alloy | `mvtink_alloy_cinnabar_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cinnabar-Netherite Scrap Shard Alloy | `mvtink_alloy_cinnabar_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Cinnabar-Nickel Alloy | `mvtink_alloy_cinnabar_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cinnabar-Null-Shard Alloy | `mvtink_alloy_cinnabar_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Cinnabar-Obsidian Alloy | `mvtink_alloy_cinnabar_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Cinnabar-Obsidianite Alloy | `mvtink_alloy_cinnabar_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Cinnabar-Opal Alloy | `mvtink_alloy_cinnabar_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Cinnabar-Ender Pearl Core Alloy | `mvtink_alloy_cinnabar_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Cinnabar-Phantomite Alloy | `mvtink_alloy_cinnabar_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Cinnabar-Platinum Alloy | `mvtink_alloy_cinnabar_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Cinnabar-Prismarine Alloy | `mvtink_alloy_cinnabar_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Cinnabar-Pyrite Alloy | `mvtink_alloy_cinnabar_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Cinnabar-Pyrophore Alloy | `mvtink_alloy_cinnabar_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Cinnabar-Nether Quartz Alloy | `mvtink_alloy_cinnabar_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Cinnabar-Redstone Alloy | `mvtink_alloy_cinnabar_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Cinnabar-Resonite Alloy | `mvtink_alloy_cinnabar_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Cinnabar-Ruby Alloy | `mvtink_alloy_cinnabar_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Cinnabar-Sanguinite Alloy | `mvtink_alloy_cinnabar_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Cinnabar-Sapphire Alloy | `mvtink_alloy_cinnabar_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Cinnabar-Shadowgem Alloy | `mvtink_alloy_cinnabar_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Cinnabar-Shulkerite Alloy | `mvtink_alloy_cinnabar_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Cinnabar-Silver Alloy | `mvtink_alloy_cinnabar_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cinnabar-Singularite Alloy | `mvtink_alloy_cinnabar_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cinnabar-Soul Glass Crystal Alloy | `mvtink_alloy_cinnabar_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cinnabar-Spatial Platinum Alloy | `mvtink_alloy_cinnabar_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Cinnabar-Starlight Silver Alloy | `mvtink_alloy_cinnabar_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Cinnabar-Steel Alloy | `mvtink_alloy_cinnabar_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Cinnabar-Stibnite Alloy | `mvtink_alloy_cinnabar_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Cinnabar-Sulfur Alloy | `mvtink_alloy_cinnabar_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Cinnabar-Talc Alloy | `mvtink_alloy_cinnabar_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cinnabar-Tesseract Crystal Alloy | `mvtink_alloy_cinnabar_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Cinnabar-Tin Alloy | `mvtink_alloy_cinnabar_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cinnabar-Titanium Alloy | `mvtink_alloy_cinnabar_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Cinnabar-Topaz Alloy | `mvtink_alloy_cinnabar_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Cinnabar-Tourmaline Alloy | `mvtink_alloy_cinnabar_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Cinnabar-Tungsten Alloy | `mvtink_alloy_cinnabar_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Cinnabar-Void Pyrite Alloy | `mvtink_alloy_cinnabar_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Cinnabar-Void Titanium Alloy | `mvtink_alloy_cinnabar_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Cinnabar-Voidstone Alloy | `mvtink_alloy_cinnabar_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cinnabar-Compacted Volcanic Ash Alloy | `mvtink_alloy_cinnabar_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Cinnabar-Warped Emerald Alloy | `mvtink_alloy_cinnabar_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Cinnabar-Warped Quartz Alloy | `mvtink_alloy_cinnabar_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cinnabar-Pure Weeping Shard Alloy | `mvtink_alloy_cinnabar_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Cinnabar-Witherite Alloy | `mvtink_alloy_cinnabar_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Cinnabar-Zero-Point Shard Alloy | `mvtink_alloy_cinnabar_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Cinnabar-Zinc Alloy | `mvtink_alloy_cinnabar_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cinnabar-Zircon Alloy | `mvtink_alloy_cinnabar_zircon` | Terrain, Resonant, Swift |

#### Cryolite · `mvtink_cryolite` · 79 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Cryolite-Cursed Brimstone Alloy | `mvtink_alloy_cryolite_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Cryolite-Diamond Alloy | `mvtink_alloy_cryolite_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cryolite-Dragon Scale Shard Alloy | `mvtink_alloy_cryolite_dragon_shard` | Void, Terrain, Swift |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cryolite-Eclipse Gem Alloy | `mvtink_alloy_cryolite_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Cryolite-Emerald Alloy | `mvtink_alloy_cryolite_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cryolite-End Crystal Shard Alloy | `mvtink_alloy_cryolite_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Cryolite-Enderite Alloy | `mvtink_alloy_cryolite_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Cryolite-Fire Opal Alloy | `mvtink_alloy_cryolite_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Cryolite-Flint Alloy | `mvtink_alloy_cryolite_flint` | Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cryolite-Fluorite Alloy | `mvtink_alloy_cryolite_fluorite` | Terrain, Resonant |
| Galena (`mvtink_galena`) | Cryolite-Galena Alloy | `mvtink_alloy_cryolite_galena` | Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cryolite-Ghast Tear Shard Alloy | `mvtink_alloy_cryolite_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cryolite-Glowstone Gem Alloy | `mvtink_alloy_cryolite_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Cryolite-Gold Alloy | `mvtink_alloy_cryolite_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Cryolite-Graphite Alloy | `mvtink_alloy_cryolite_graphite` | Terrain |
| Gravitite (`mvtink_gravitite`) | Cryolite-Gravitite Alloy | `mvtink_alloy_cryolite_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Cryolite-Gypsum Alloy | `mvtink_alloy_cryolite_gypsum` | Terrain |
| Helliron (`mvtink_helliron`) | Cryolite-Helliron Alloy | `mvtink_alloy_cryolite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cryolite-Ignis Ferrum Alloy | `mvtink_alloy_cryolite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cryolite-Infernal Obsidian Alloy | `mvtink_alloy_cryolite_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cryolite-Netherite-Infused Quartz Alloy | `mvtink_alloy_cryolite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Cryolite-Iron Alloy | `mvtink_alloy_cryolite_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Cryolite-Jade Alloy | `mvtink_alloy_cryolite_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Cryolite-Kaolinite Alloy | `mvtink_alloy_cryolite_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Cryolite-Lapis Lazuli Alloy | `mvtink_alloy_cryolite_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cryolite-Lapis Matrix Alloy | `mvtink_alloy_cryolite_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cryolite-Magma Brimstone Alloy | `mvtink_alloy_cryolite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Cryolite-Magmacite Alloy | `mvtink_alloy_cryolite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Cryolite-Magnetite Alloy | `mvtink_alloy_cryolite_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Cryolite-Malachite Alloy | `mvtink_alloy_cryolite_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Cryolite-Nebulite Alloy | `mvtink_alloy_cryolite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cryolite-Nether Bismuth Alloy | `mvtink_alloy_cryolite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cryolite-Nether Tungsten Alloy | `mvtink_alloy_cryolite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cryolite-Netherite Scrap Shard Alloy | `mvtink_alloy_cryolite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Cryolite-Nickel Alloy | `mvtink_alloy_cryolite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cryolite-Null-Shard Alloy | `mvtink_alloy_cryolite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Cryolite-Obsidian Alloy | `mvtink_alloy_cryolite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Cryolite-Obsidianite Alloy | `mvtink_alloy_cryolite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Cryolite-Opal Alloy | `mvtink_alloy_cryolite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Cryolite-Ender Pearl Core Alloy | `mvtink_alloy_cryolite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Cryolite-Phantomite Alloy | `mvtink_alloy_cryolite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Cryolite-Platinum Alloy | `mvtink_alloy_cryolite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Cryolite-Prismarine Alloy | `mvtink_alloy_cryolite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Cryolite-Pyrite Alloy | `mvtink_alloy_cryolite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Cryolite-Pyrophore Alloy | `mvtink_alloy_cryolite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Cryolite-Nether Quartz Alloy | `mvtink_alloy_cryolite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Cryolite-Redstone Alloy | `mvtink_alloy_cryolite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Cryolite-Resonite Alloy | `mvtink_alloy_cryolite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Cryolite-Ruby Alloy | `mvtink_alloy_cryolite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Cryolite-Sanguinite Alloy | `mvtink_alloy_cryolite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Cryolite-Sapphire Alloy | `mvtink_alloy_cryolite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Cryolite-Shadowgem Alloy | `mvtink_alloy_cryolite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Cryolite-Shulkerite Alloy | `mvtink_alloy_cryolite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) ★ | Glacial Silver | `mvtink_glacial_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cryolite-Singularite Alloy | `mvtink_alloy_cryolite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cryolite-Soul Glass Crystal Alloy | `mvtink_alloy_cryolite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cryolite-Spatial Platinum Alloy | `mvtink_alloy_cryolite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Cryolite-Starlight Silver Alloy | `mvtink_alloy_cryolite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Cryolite-Steel Alloy | `mvtink_alloy_cryolite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Cryolite-Stibnite Alloy | `mvtink_alloy_cryolite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Cryolite-Sulfur Alloy | `mvtink_alloy_cryolite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Cryolite-Talc Alloy | `mvtink_alloy_cryolite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cryolite-Tesseract Crystal Alloy | `mvtink_alloy_cryolite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Cryolite-Tin Alloy | `mvtink_alloy_cryolite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cryolite-Titanium Alloy | `mvtink_alloy_cryolite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Cryolite-Topaz Alloy | `mvtink_alloy_cryolite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Cryolite-Tourmaline Alloy | `mvtink_alloy_cryolite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Cryolite-Tungsten Alloy | `mvtink_alloy_cryolite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Cryolite-Void Pyrite Alloy | `mvtink_alloy_cryolite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Cryolite-Void Titanium Alloy | `mvtink_alloy_cryolite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Cryolite-Voidstone Alloy | `mvtink_alloy_cryolite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cryolite-Compacted Volcanic Ash Alloy | `mvtink_alloy_cryolite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Cryolite-Warped Emerald Alloy | `mvtink_alloy_cryolite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Cryolite-Warped Quartz Alloy | `mvtink_alloy_cryolite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cryolite-Pure Weeping Shard Alloy | `mvtink_alloy_cryolite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Cryolite-Witherite Alloy | `mvtink_alloy_cryolite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Cryolite-Zero-Point Shard Alloy | `mvtink_alloy_cryolite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Cryolite-Zinc Alloy | `mvtink_alloy_cryolite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cryolite-Zircon Alloy | `mvtink_alloy_cryolite_zircon` | Terrain, Resonant, Swift |

#### Fluorite · `mvtink_fluorite` · 69 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Galena (`mvtink_galena`) | Fluorite-Galena Alloy | `mvtink_alloy_fluorite_galena` | Terrain, Tempered, Resonant |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Fluorite-Ghast Tear Shard Alloy | `mvtink_alloy_fluorite_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Fluorite-Glowstone Gem Alloy | `mvtink_alloy_fluorite_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Fluorite-Gold Alloy | `mvtink_alloy_fluorite_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Fluorite-Graphite Alloy | `mvtink_alloy_fluorite_graphite` | Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Fluorite-Gravitite Alloy | `mvtink_alloy_fluorite_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Fluorite-Gypsum Alloy | `mvtink_alloy_fluorite_gypsum` | Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Fluorite-Helliron Alloy | `mvtink_alloy_fluorite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Fluorite-Ignis Ferrum Alloy | `mvtink_alloy_fluorite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Fluorite-Infernal Obsidian Alloy | `mvtink_alloy_fluorite_infernal_obsidian` | Infernal, Terrain, Resonant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Fluorite-Netherite-Infused Quartz Alloy | `mvtink_alloy_fluorite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Fluorite-Iron Alloy | `mvtink_alloy_fluorite_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Fluorite-Jade Alloy | `mvtink_alloy_fluorite_jade` | Terrain, Radiant, Resonant |
| Kaolinite (`mvtink_kaolinite`) | Fluorite-Kaolinite Alloy | `mvtink_alloy_fluorite_kaolinite` | Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Fluorite-Lapis Lazuli Alloy | `mvtink_alloy_fluorite_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Fluorite-Lapis Matrix Alloy | `mvtink_alloy_fluorite_lapis_matrix` | Terrain, Radiant, Resonant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Fluorite-Magma Brimstone Alloy | `mvtink_alloy_fluorite_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Fluorite-Magmacite Alloy | `mvtink_alloy_fluorite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Fluorite-Magnetite Alloy | `mvtink_alloy_fluorite_magnetite` | Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Fluorite-Malachite Alloy | `mvtink_alloy_fluorite_malachite` | Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Fluorite-Nebulite Alloy | `mvtink_alloy_fluorite_nebulite` | Void, Terrain, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Fluorite-Nether Bismuth Alloy | `mvtink_alloy_fluorite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Fluorite-Nether Tungsten Alloy | `mvtink_alloy_fluorite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Fluorite-Netherite Scrap Shard Alloy | `mvtink_alloy_fluorite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Fluorite-Nickel Alloy | `mvtink_alloy_fluorite_nickel` | Terrain, Tempered, Resonant |
| Null-Shard (`mvtink_null_shard`) | Fluorite-Null-Shard Alloy | `mvtink_alloy_fluorite_null_shard` | Void, Terrain, Resonant |
| Obsidian (`mvtink_obsidian`) | Fluorite-Obsidian Alloy | `mvtink_alloy_fluorite_obsidian` | Primal, Terrain, Resonant |
| Obsidianite (`mvtink_obsidianite`) | Fluorite-Obsidianite Alloy | `mvtink_alloy_fluorite_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Fluorite-Opal Alloy | `mvtink_alloy_fluorite_opal` | Terrain, Radiant, Resonant |
| Ender Pearl Core (`mvtink_pearl_core`) | Fluorite-Ender Pearl Core Alloy | `mvtink_alloy_fluorite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Fluorite-Phantomite Alloy | `mvtink_alloy_fluorite_phantomite` | Void, Terrain, Resonant |
| Platinum (`mvtink_platinum`) | Fluorite-Platinum Alloy | `mvtink_alloy_fluorite_platinum` | Terrain, Tempered, Resonant |
| Prismarine (`mvtink_prismarine`) | Fluorite-Prismarine Alloy | `mvtink_alloy_fluorite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Fluorite-Pyrite Alloy | `mvtink_alloy_fluorite_pyrite` | Terrain, Resonant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Fluorite-Pyrophore Alloy | `mvtink_alloy_fluorite_pyrophore` | Infernal, Terrain, Resonant |
| Nether Quartz (`mvtink_quartz`) | Fluorite-Nether Quartz Alloy | `mvtink_alloy_fluorite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Fluorite-Redstone Alloy | `mvtink_alloy_fluorite_redstone` | Primal, Terrain, Resonant |
| Resonite (`mvtink_resonite`) | Fluorite-Resonite Alloy | `mvtink_alloy_fluorite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Fluorite-Ruby Alloy | `mvtink_alloy_fluorite_ruby` | Terrain, Radiant, Resonant |
| Sanguinite (`mvtink_sanguinite`) | Fluorite-Sanguinite Alloy | `mvtink_alloy_fluorite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Fluorite-Sapphire Alloy | `mvtink_alloy_fluorite_sapphire` | Terrain, Radiant, Resonant |
| Shadowgem (`mvtink_shadowgem`) | Fluorite-Shadowgem Alloy | `mvtink_alloy_fluorite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Fluorite-Shulkerite Alloy | `mvtink_alloy_fluorite_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Fluorite-Silver Alloy | `mvtink_alloy_fluorite_silver` | Terrain, Tempered, Resonant |
| Singularite (`mvtink_singularite`) | Fluorite-Singularite Alloy | `mvtink_alloy_fluorite_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Fluorite-Soul Glass Crystal Alloy | `mvtink_alloy_fluorite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Fluorite-Spatial Platinum Alloy | `mvtink_alloy_fluorite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Fluorite-Starlight Silver Alloy | `mvtink_alloy_fluorite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Fluorite-Steel Alloy | `mvtink_alloy_fluorite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Fluorite-Stibnite Alloy | `mvtink_alloy_fluorite_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Fluorite-Sulfur Alloy | `mvtink_alloy_fluorite_sulfur` | Infernal, Terrain, Resonant |
| Talc (`mvtink_talc`) | Fluorite-Talc Alloy | `mvtink_alloy_fluorite_talc` | Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Fluorite-Tesseract Crystal Alloy | `mvtink_alloy_fluorite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Fluorite-Tin Alloy | `mvtink_alloy_fluorite_tin` | Terrain, Tempered, Resonant |
| Titanium (`mvtink_titanium`) | Fluorite-Titanium Alloy | `mvtink_alloy_fluorite_titanium` | Terrain, Tempered, Resonant |
| Topaz (`mvtink_topaz`) | Fluorite-Topaz Alloy | `mvtink_alloy_fluorite_topaz` | Terrain, Radiant, Resonant |
| Tourmaline (`mvtink_tourmaline`) | Fluorite-Tourmaline Alloy | `mvtink_alloy_fluorite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Fluorite-Tungsten Alloy | `mvtink_alloy_fluorite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Fluorite-Void Pyrite Alloy | `mvtink_alloy_fluorite_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Fluorite-Void Titanium Alloy | `mvtink_alloy_fluorite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Fluorite-Voidstone Alloy | `mvtink_alloy_fluorite_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Fluorite-Compacted Volcanic Ash Alloy | `mvtink_alloy_fluorite_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Fluorite-Warped Emerald Alloy | `mvtink_alloy_fluorite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Fluorite-Warped Quartz Alloy | `mvtink_alloy_fluorite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Fluorite-Pure Weeping Shard Alloy | `mvtink_alloy_fluorite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Fluorite-Witherite Alloy | `mvtink_alloy_fluorite_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Fluorite-Zero-Point Shard Alloy | `mvtink_alloy_fluorite_zero_point` | Void, Terrain, Resonant |
| Zinc (`mvtink_zinc`) | Fluorite-Zinc Alloy | `mvtink_alloy_fluorite_zinc` | Terrain, Tempered, Resonant |
| Zircon (`mvtink_zircon`) | Fluorite-Zircon Alloy | `mvtink_alloy_fluorite_zircon` | Terrain, Resonant, Swift |

#### Galena · `mvtink_galena` · 68 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Galena-Ghast Tear Shard Alloy | `mvtink_alloy_galena_ghast_tear_shard` | Infernal, Terrain, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Galena-Glowstone Gem Alloy | `mvtink_alloy_galena_glowstone_gem` | Infernal, Terrain, Tempered |
| Gold (`mvtink_gold`) | Galena-Gold Alloy | `mvtink_alloy_galena_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Galena-Graphite Alloy | `mvtink_alloy_galena_graphite` | Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Galena-Gravitite Alloy | `mvtink_alloy_galena_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Galena-Gypsum Alloy | `mvtink_alloy_galena_gypsum` | Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Galena-Helliron Alloy | `mvtink_alloy_galena_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Galena-Ignis Ferrum Alloy | `mvtink_alloy_galena_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Galena-Infernal Obsidian Alloy | `mvtink_alloy_galena_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Galena-Netherite-Infused Quartz Alloy | `mvtink_alloy_galena_infused_quartz` | Infernal, Terrain, Tempered |
| Iron (`mvtink_iron`) | Galena-Iron Alloy | `mvtink_alloy_galena_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Galena-Jade Alloy | `mvtink_alloy_galena_jade` | Terrain, Tempered, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Galena-Kaolinite Alloy | `mvtink_alloy_galena_kaolinite` | Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Galena-Lapis Lazuli Alloy | `mvtink_alloy_galena_lapis` | Primal, Terrain, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Galena-Lapis Matrix Alloy | `mvtink_alloy_galena_lapis_matrix` | Terrain, Tempered, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Galena-Magma Brimstone Alloy | `mvtink_alloy_galena_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Galena-Magmacite Alloy | `mvtink_alloy_galena_magmacite` | Infernal, Terrain, Tempered |
| Magnetite (`mvtink_magnetite`) | Galena-Magnetite Alloy | `mvtink_alloy_galena_magnetite` | Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Galena-Malachite Alloy | `mvtink_alloy_galena_malachite` | Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Galena-Nebulite Alloy | `mvtink_alloy_galena_nebulite` | Void, Terrain, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Galena-Nether Bismuth Alloy | `mvtink_alloy_galena_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Galena-Nether Tungsten Alloy | `mvtink_alloy_galena_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Galena-Netherite Scrap Shard Alloy | `mvtink_alloy_galena_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Galena-Nickel Alloy | `mvtink_alloy_galena_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Galena-Null-Shard Alloy | `mvtink_alloy_galena_null_shard` | Void, Terrain, Tempered |
| Obsidian (`mvtink_obsidian`) | Galena-Obsidian Alloy | `mvtink_alloy_galena_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Galena-Obsidianite Alloy | `mvtink_alloy_galena_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Galena-Opal Alloy | `mvtink_alloy_galena_opal` | Terrain, Tempered, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Galena-Ender Pearl Core Alloy | `mvtink_alloy_galena_pearl_core` | Void, Terrain, Tempered |
| Phantomite (`mvtink_phantomite`) | Galena-Phantomite Alloy | `mvtink_alloy_galena_phantomite` | Void, Terrain, Tempered |
| Platinum (`mvtink_platinum`) | Galena-Platinum Alloy | `mvtink_alloy_galena_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Galena-Prismarine Alloy | `mvtink_alloy_galena_prismarine` | Primal, Terrain, Tempered |
| Pyrite (`mvtink_pyrite`) | Galena-Pyrite Alloy | `mvtink_alloy_galena_pyrite` | Terrain, Tempered, Swift |
| Pyrophore (`mvtink_pyrophore`) | Galena-Pyrophore Alloy | `mvtink_alloy_galena_pyrophore` | Infernal, Terrain, Tempered |
| Nether Quartz (`mvtink_quartz`) | Galena-Nether Quartz Alloy | `mvtink_alloy_galena_quartz` | Primal, Terrain, Tempered |
| Redstone (`mvtink_redstone`) | Galena-Redstone Alloy | `mvtink_alloy_galena_redstone` | Primal, Terrain, Tempered |
| Resonite (`mvtink_resonite`) | Galena-Resonite Alloy | `mvtink_alloy_galena_resonite` | Void, Terrain, Tempered |
| Ruby (`mvtink_ruby`) | Galena-Ruby Alloy | `mvtink_alloy_galena_ruby` | Terrain, Tempered, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Galena-Sanguinite Alloy | `mvtink_alloy_galena_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Galena-Sapphire Alloy | `mvtink_alloy_galena_sapphire` | Terrain, Tempered, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Galena-Shadowgem Alloy | `mvtink_alloy_galena_shadowgem` | Void, Terrain, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Galena-Shulkerite Alloy | `mvtink_alloy_galena_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Galena-Silver Alloy | `mvtink_alloy_galena_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Galena-Singularite Alloy | `mvtink_alloy_galena_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Galena-Soul Glass Crystal Alloy | `mvtink_alloy_galena_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Galena-Spatial Platinum Alloy | `mvtink_alloy_galena_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Galena-Starlight Silver Alloy | `mvtink_alloy_galena_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Galena-Steel Alloy | `mvtink_alloy_galena_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Galena-Stibnite Alloy | `mvtink_alloy_galena_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Galena-Sulfur Alloy | `mvtink_alloy_galena_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Galena-Talc Alloy | `mvtink_alloy_galena_talc` | Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Galena-Tesseract Crystal Alloy | `mvtink_alloy_galena_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Galena-Tin Alloy | `mvtink_alloy_galena_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Galena-Titanium Alloy | `mvtink_alloy_galena_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Galena-Topaz Alloy | `mvtink_alloy_galena_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Galena-Tourmaline Alloy | `mvtink_alloy_galena_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Galena-Tungsten Alloy | `mvtink_alloy_galena_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Galena-Void Pyrite Alloy | `mvtink_alloy_galena_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Galena-Void Titanium Alloy | `mvtink_alloy_galena_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Galena-Voidstone Alloy | `mvtink_alloy_galena_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Galena-Compacted Volcanic Ash Alloy | `mvtink_alloy_galena_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Galena-Warped Emerald Alloy | `mvtink_alloy_galena_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Galena-Warped Quartz Alloy | `mvtink_alloy_galena_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Galena-Pure Weeping Shard Alloy | `mvtink_alloy_galena_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Galena-Witherite Alloy | `mvtink_alloy_galena_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Galena-Zero-Point Shard Alloy | `mvtink_alloy_galena_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Galena-Zinc Alloy | `mvtink_alloy_galena_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Galena-Zircon Alloy | `mvtink_alloy_galena_zircon` | Terrain, Tempered, Resonant |

#### Graphite · `mvtink_graphite` · 64 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Gravitite (`mvtink_gravitite`) | Graphite-Gravitite Alloy | `mvtink_alloy_graphite_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Graphite-Gypsum Alloy | `mvtink_alloy_graphite_gypsum` | Terrain |
| Helliron (`mvtink_helliron`) | Graphite-Helliron Alloy | `mvtink_alloy_graphite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Graphite-Ignis Ferrum Alloy | `mvtink_alloy_graphite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Graphite-Infernal Obsidian Alloy | `mvtink_alloy_graphite_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Graphite-Netherite-Infused Quartz Alloy | `mvtink_alloy_graphite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Graphite-Iron Alloy | `mvtink_alloy_graphite_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Graphite-Jade Alloy | `mvtink_alloy_graphite_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Graphite-Kaolinite Alloy | `mvtink_alloy_graphite_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Graphite-Lapis Lazuli Alloy | `mvtink_alloy_graphite_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Graphite-Lapis Matrix Alloy | `mvtink_alloy_graphite_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Graphite-Magma Brimstone Alloy | `mvtink_alloy_graphite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Graphite-Magmacite Alloy | `mvtink_alloy_graphite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Graphite-Magnetite Alloy | `mvtink_alloy_graphite_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Graphite-Malachite Alloy | `mvtink_alloy_graphite_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Graphite-Nebulite Alloy | `mvtink_alloy_graphite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Graphite-Nether Bismuth Alloy | `mvtink_alloy_graphite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Graphite-Nether Tungsten Alloy | `mvtink_alloy_graphite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Graphite-Netherite Scrap Shard Alloy | `mvtink_alloy_graphite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Graphite-Nickel Alloy | `mvtink_alloy_graphite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Graphite-Null-Shard Alloy | `mvtink_alloy_graphite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Graphite-Obsidian Alloy | `mvtink_alloy_graphite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Graphite-Obsidianite Alloy | `mvtink_alloy_graphite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Graphite-Opal Alloy | `mvtink_alloy_graphite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Graphite-Ender Pearl Core Alloy | `mvtink_alloy_graphite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Graphite-Phantomite Alloy | `mvtink_alloy_graphite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Graphite-Platinum Alloy | `mvtink_alloy_graphite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Graphite-Prismarine Alloy | `mvtink_alloy_graphite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Graphite-Pyrite Alloy | `mvtink_alloy_graphite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Graphite-Pyrophore Alloy | `mvtink_alloy_graphite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Graphite-Nether Quartz Alloy | `mvtink_alloy_graphite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Graphite-Redstone Alloy | `mvtink_alloy_graphite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Graphite-Resonite Alloy | `mvtink_alloy_graphite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Graphite-Ruby Alloy | `mvtink_alloy_graphite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Graphite-Sanguinite Alloy | `mvtink_alloy_graphite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Graphite-Sapphire Alloy | `mvtink_alloy_graphite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Graphite-Shadowgem Alloy | `mvtink_alloy_graphite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Graphite-Shulkerite Alloy | `mvtink_alloy_graphite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Graphite-Silver Alloy | `mvtink_alloy_graphite_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Graphite-Singularite Alloy | `mvtink_alloy_graphite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Graphite-Soul Glass Crystal Alloy | `mvtink_alloy_graphite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Graphite-Spatial Platinum Alloy | `mvtink_alloy_graphite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Graphite-Starlight Silver Alloy | `mvtink_alloy_graphite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Graphite-Steel Alloy | `mvtink_alloy_graphite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Graphite-Stibnite Alloy | `mvtink_alloy_graphite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Graphite-Sulfur Alloy | `mvtink_alloy_graphite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Graphite-Talc Alloy | `mvtink_alloy_graphite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Graphite-Tesseract Crystal Alloy | `mvtink_alloy_graphite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Graphite-Tin Alloy | `mvtink_alloy_graphite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Graphite-Titanium Alloy | `mvtink_alloy_graphite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Graphite-Topaz Alloy | `mvtink_alloy_graphite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Graphite-Tourmaline Alloy | `mvtink_alloy_graphite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Graphite-Tungsten Alloy | `mvtink_alloy_graphite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Graphite-Void Pyrite Alloy | `mvtink_alloy_graphite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Graphite-Void Titanium Alloy | `mvtink_alloy_graphite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Graphite-Voidstone Alloy | `mvtink_alloy_graphite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Graphite-Compacted Volcanic Ash Alloy | `mvtink_alloy_graphite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Graphite-Warped Emerald Alloy | `mvtink_alloy_graphite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Graphite-Warped Quartz Alloy | `mvtink_alloy_graphite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Graphite-Pure Weeping Shard Alloy | `mvtink_alloy_graphite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Graphite-Witherite Alloy | `mvtink_alloy_graphite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Graphite-Zero-Point Shard Alloy | `mvtink_alloy_graphite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Graphite-Zinc Alloy | `mvtink_alloy_graphite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Graphite-Zircon Alloy | `mvtink_alloy_graphite_zircon` | Terrain, Resonant, Swift |

#### Gypsum · `mvtink_gypsum` · 62 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Helliron (`mvtink_helliron`) | Gypsum-Helliron Alloy | `mvtink_alloy_gypsum_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Gypsum-Ignis Ferrum Alloy | `mvtink_alloy_gypsum_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Gypsum-Infernal Obsidian Alloy | `mvtink_alloy_gypsum_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Gypsum-Netherite-Infused Quartz Alloy | `mvtink_alloy_gypsum_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Gypsum-Iron Alloy | `mvtink_alloy_gypsum_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Gypsum-Jade Alloy | `mvtink_alloy_gypsum_jade` | Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Gypsum-Kaolinite Alloy | `mvtink_alloy_gypsum_kaolinite` | Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Gypsum-Lapis Lazuli Alloy | `mvtink_alloy_gypsum_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Gypsum-Lapis Matrix Alloy | `mvtink_alloy_gypsum_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Gypsum-Magma Brimstone Alloy | `mvtink_alloy_gypsum_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Gypsum-Magmacite Alloy | `mvtink_alloy_gypsum_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Gypsum-Magnetite Alloy | `mvtink_alloy_gypsum_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Gypsum-Malachite Alloy | `mvtink_alloy_gypsum_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Gypsum-Nebulite Alloy | `mvtink_alloy_gypsum_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Gypsum-Nether Bismuth Alloy | `mvtink_alloy_gypsum_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Gypsum-Nether Tungsten Alloy | `mvtink_alloy_gypsum_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Gypsum-Netherite Scrap Shard Alloy | `mvtink_alloy_gypsum_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Gypsum-Nickel Alloy | `mvtink_alloy_gypsum_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Gypsum-Null-Shard Alloy | `mvtink_alloy_gypsum_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Gypsum-Obsidian Alloy | `mvtink_alloy_gypsum_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Gypsum-Obsidianite Alloy | `mvtink_alloy_gypsum_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Gypsum-Opal Alloy | `mvtink_alloy_gypsum_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Gypsum-Ender Pearl Core Alloy | `mvtink_alloy_gypsum_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Gypsum-Phantomite Alloy | `mvtink_alloy_gypsum_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Gypsum-Platinum Alloy | `mvtink_alloy_gypsum_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Gypsum-Prismarine Alloy | `mvtink_alloy_gypsum_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Gypsum-Pyrite Alloy | `mvtink_alloy_gypsum_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Gypsum-Pyrophore Alloy | `mvtink_alloy_gypsum_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Gypsum-Nether Quartz Alloy | `mvtink_alloy_gypsum_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Gypsum-Redstone Alloy | `mvtink_alloy_gypsum_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Gypsum-Resonite Alloy | `mvtink_alloy_gypsum_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Gypsum-Ruby Alloy | `mvtink_alloy_gypsum_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Gypsum-Sanguinite Alloy | `mvtink_alloy_gypsum_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Gypsum-Sapphire Alloy | `mvtink_alloy_gypsum_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Gypsum-Shadowgem Alloy | `mvtink_alloy_gypsum_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Gypsum-Shulkerite Alloy | `mvtink_alloy_gypsum_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Gypsum-Silver Alloy | `mvtink_alloy_gypsum_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Gypsum-Singularite Alloy | `mvtink_alloy_gypsum_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Gypsum-Soul Glass Crystal Alloy | `mvtink_alloy_gypsum_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Gypsum-Spatial Platinum Alloy | `mvtink_alloy_gypsum_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Gypsum-Starlight Silver Alloy | `mvtink_alloy_gypsum_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Gypsum-Steel Alloy | `mvtink_alloy_gypsum_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Gypsum-Stibnite Alloy | `mvtink_alloy_gypsum_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Gypsum-Sulfur Alloy | `mvtink_alloy_gypsum_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Gypsum-Talc Alloy | `mvtink_alloy_gypsum_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Gypsum-Tesseract Crystal Alloy | `mvtink_alloy_gypsum_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Gypsum-Tin Alloy | `mvtink_alloy_gypsum_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Gypsum-Titanium Alloy | `mvtink_alloy_gypsum_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Gypsum-Topaz Alloy | `mvtink_alloy_gypsum_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Gypsum-Tourmaline Alloy | `mvtink_alloy_gypsum_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Gypsum-Tungsten Alloy | `mvtink_alloy_gypsum_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Gypsum-Void Pyrite Alloy | `mvtink_alloy_gypsum_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Gypsum-Void Titanium Alloy | `mvtink_alloy_gypsum_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Gypsum-Voidstone Alloy | `mvtink_alloy_gypsum_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Gypsum-Compacted Volcanic Ash Alloy | `mvtink_alloy_gypsum_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Gypsum-Warped Emerald Alloy | `mvtink_alloy_gypsum_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Gypsum-Warped Quartz Alloy | `mvtink_alloy_gypsum_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Gypsum-Pure Weeping Shard Alloy | `mvtink_alloy_gypsum_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Gypsum-Witherite Alloy | `mvtink_alloy_gypsum_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Gypsum-Zero-Point Shard Alloy | `mvtink_alloy_gypsum_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Gypsum-Zinc Alloy | `mvtink_alloy_gypsum_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Gypsum-Zircon Alloy | `mvtink_alloy_gypsum_zircon` | Terrain, Resonant, Swift |

#### Jade · `mvtink_jade` · 56 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Kaolinite (`mvtink_kaolinite`) | Jade-Kaolinite Alloy | `mvtink_alloy_jade_kaolinite` | Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Jade-Lapis Lazuli Alloy | `mvtink_alloy_jade_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Jade-Lapis Matrix Alloy | `mvtink_alloy_jade_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Jade-Magma Brimstone Alloy | `mvtink_alloy_jade_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Jade-Magmacite Alloy | `mvtink_alloy_jade_magmacite` | Infernal, Terrain, Radiant |
| Magnetite (`mvtink_magnetite`) | Jade-Magnetite Alloy | `mvtink_alloy_jade_magnetite` | Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Jade-Malachite Alloy | `mvtink_alloy_jade_malachite` | Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Jade-Nebulite Alloy | `mvtink_alloy_jade_nebulite` | Void, Terrain, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Jade-Nether Bismuth Alloy | `mvtink_alloy_jade_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Jade-Nether Tungsten Alloy | `mvtink_alloy_jade_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Jade-Netherite Scrap Shard Alloy | `mvtink_alloy_jade_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Jade-Nickel Alloy | `mvtink_alloy_jade_nickel` | Terrain, Tempered, Radiant |
| Null-Shard (`mvtink_null_shard`) | Jade-Null-Shard Alloy | `mvtink_alloy_jade_null_shard` | Void, Terrain, Radiant |
| Obsidian (`mvtink_obsidian`) | Jade-Obsidian Alloy | `mvtink_alloy_jade_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Jade-Obsidianite Alloy | `mvtink_alloy_jade_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Jade-Opal Alloy | `mvtink_alloy_jade_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Jade-Ender Pearl Core Alloy | `mvtink_alloy_jade_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Jade-Phantomite Alloy | `mvtink_alloy_jade_phantomite` | Void, Terrain, Radiant |
| Platinum (`mvtink_platinum`) | Jade-Platinum Alloy | `mvtink_alloy_jade_platinum` | Terrain, Tempered, Radiant |
| Prismarine (`mvtink_prismarine`) | Jade-Prismarine Alloy | `mvtink_alloy_jade_prismarine` | Primal, Terrain, Radiant |
| Pyrite (`mvtink_pyrite`) | Jade-Pyrite Alloy | `mvtink_alloy_jade_pyrite` | Terrain, Radiant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Jade-Pyrophore Alloy | `mvtink_alloy_jade_pyrophore` | Infernal, Terrain, Radiant |
| Nether Quartz (`mvtink_quartz`) | Jade-Nether Quartz Alloy | `mvtink_alloy_jade_quartz` | Primal, Terrain, Radiant |
| Redstone (`mvtink_redstone`) | Jade-Redstone Alloy | `mvtink_alloy_jade_redstone` | Primal, Terrain, Radiant |
| Resonite (`mvtink_resonite`) | Jade-Resonite Alloy | `mvtink_alloy_jade_resonite` | Void, Terrain, Radiant |
| Ruby (`mvtink_ruby`) | Jade-Ruby Alloy | `mvtink_alloy_jade_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Jade-Sanguinite Alloy | `mvtink_alloy_jade_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Jade-Sapphire Alloy | `mvtink_alloy_jade_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Jade-Shadowgem Alloy | `mvtink_alloy_jade_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Jade-Shulkerite Alloy | `mvtink_alloy_jade_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Jade-Silver Alloy | `mvtink_alloy_jade_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Jade-Singularite Alloy | `mvtink_alloy_jade_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Jade-Soul Glass Crystal Alloy | `mvtink_alloy_jade_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Jade-Spatial Platinum Alloy | `mvtink_alloy_jade_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Jade-Starlight Silver Alloy | `mvtink_alloy_jade_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Jade-Steel Alloy | `mvtink_alloy_jade_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Jade-Stibnite Alloy | `mvtink_alloy_jade_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Jade-Sulfur Alloy | `mvtink_alloy_jade_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Jade-Talc Alloy | `mvtink_alloy_jade_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Jade-Tesseract Crystal Alloy | `mvtink_alloy_jade_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Jade-Tin Alloy | `mvtink_alloy_jade_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Jade-Titanium Alloy | `mvtink_alloy_jade_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Jade-Topaz Alloy | `mvtink_alloy_jade_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Jade-Tourmaline Alloy | `mvtink_alloy_jade_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Jade-Tungsten Alloy | `mvtink_alloy_jade_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Jade-Void Pyrite Alloy | `mvtink_alloy_jade_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Jade-Void Titanium Alloy | `mvtink_alloy_jade_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Jade-Voidstone Alloy | `mvtink_alloy_jade_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Jade-Compacted Volcanic Ash Alloy | `mvtink_alloy_jade_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Jade-Warped Emerald Alloy | `mvtink_alloy_jade_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Jade-Warped Quartz Alloy | `mvtink_alloy_jade_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Jade-Pure Weeping Shard Alloy | `mvtink_alloy_jade_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Jade-Witherite Alloy | `mvtink_alloy_jade_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Jade-Zero-Point Shard Alloy | `mvtink_alloy_jade_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Jade-Zinc Alloy | `mvtink_alloy_jade_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Jade-Zircon Alloy | `mvtink_alloy_jade_zircon` | Terrain, Radiant, Resonant |

#### Kaolinite · `mvtink_kaolinite` · 55 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Lapis Lazuli (`mvtink_lapis`) | Kaolinite-Lapis Lazuli Alloy | `mvtink_alloy_kaolinite_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Kaolinite-Lapis Matrix Alloy | `mvtink_alloy_kaolinite_lapis_matrix` | Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Kaolinite-Magma Brimstone Alloy | `mvtink_alloy_kaolinite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Kaolinite-Magmacite Alloy | `mvtink_alloy_kaolinite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Kaolinite-Magnetite Alloy | `mvtink_alloy_kaolinite_magnetite` | Terrain |
| Malachite (`mvtink_malachite`) | Kaolinite-Malachite Alloy | `mvtink_alloy_kaolinite_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Kaolinite-Nebulite Alloy | `mvtink_alloy_kaolinite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Kaolinite-Nether Bismuth Alloy | `mvtink_alloy_kaolinite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Kaolinite-Nether Tungsten Alloy | `mvtink_alloy_kaolinite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Kaolinite-Netherite Scrap Shard Alloy | `mvtink_alloy_kaolinite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Kaolinite-Nickel Alloy | `mvtink_alloy_kaolinite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Kaolinite-Null-Shard Alloy | `mvtink_alloy_kaolinite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Kaolinite-Obsidian Alloy | `mvtink_alloy_kaolinite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Kaolinite-Obsidianite Alloy | `mvtink_alloy_kaolinite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Kaolinite-Opal Alloy | `mvtink_alloy_kaolinite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Kaolinite-Ender Pearl Core Alloy | `mvtink_alloy_kaolinite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Kaolinite-Phantomite Alloy | `mvtink_alloy_kaolinite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Kaolinite-Platinum Alloy | `mvtink_alloy_kaolinite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Kaolinite-Prismarine Alloy | `mvtink_alloy_kaolinite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Kaolinite-Pyrite Alloy | `mvtink_alloy_kaolinite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Kaolinite-Pyrophore Alloy | `mvtink_alloy_kaolinite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Kaolinite-Nether Quartz Alloy | `mvtink_alloy_kaolinite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Kaolinite-Redstone Alloy | `mvtink_alloy_kaolinite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Kaolinite-Resonite Alloy | `mvtink_alloy_kaolinite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Kaolinite-Ruby Alloy | `mvtink_alloy_kaolinite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Kaolinite-Sanguinite Alloy | `mvtink_alloy_kaolinite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Kaolinite-Sapphire Alloy | `mvtink_alloy_kaolinite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Kaolinite-Shadowgem Alloy | `mvtink_alloy_kaolinite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Kaolinite-Shulkerite Alloy | `mvtink_alloy_kaolinite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Kaolinite-Silver Alloy | `mvtink_alloy_kaolinite_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Kaolinite-Singularite Alloy | `mvtink_alloy_kaolinite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Kaolinite-Soul Glass Crystal Alloy | `mvtink_alloy_kaolinite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Kaolinite-Spatial Platinum Alloy | `mvtink_alloy_kaolinite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Kaolinite-Starlight Silver Alloy | `mvtink_alloy_kaolinite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Kaolinite-Steel Alloy | `mvtink_alloy_kaolinite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Kaolinite-Stibnite Alloy | `mvtink_alloy_kaolinite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Kaolinite-Sulfur Alloy | `mvtink_alloy_kaolinite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Kaolinite-Talc Alloy | `mvtink_alloy_kaolinite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Kaolinite-Tesseract Crystal Alloy | `mvtink_alloy_kaolinite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Kaolinite-Tin Alloy | `mvtink_alloy_kaolinite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Kaolinite-Titanium Alloy | `mvtink_alloy_kaolinite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Kaolinite-Topaz Alloy | `mvtink_alloy_kaolinite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Kaolinite-Tourmaline Alloy | `mvtink_alloy_kaolinite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Kaolinite-Tungsten Alloy | `mvtink_alloy_kaolinite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Kaolinite-Void Pyrite Alloy | `mvtink_alloy_kaolinite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Kaolinite-Void Titanium Alloy | `mvtink_alloy_kaolinite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Kaolinite-Voidstone Alloy | `mvtink_alloy_kaolinite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Kaolinite-Compacted Volcanic Ash Alloy | `mvtink_alloy_kaolinite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Kaolinite-Warped Emerald Alloy | `mvtink_alloy_kaolinite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Kaolinite-Warped Quartz Alloy | `mvtink_alloy_kaolinite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Kaolinite-Pure Weeping Shard Alloy | `mvtink_alloy_kaolinite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Kaolinite-Witherite Alloy | `mvtink_alloy_kaolinite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Kaolinite-Zero-Point Shard Alloy | `mvtink_alloy_kaolinite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Kaolinite-Zinc Alloy | `mvtink_alloy_kaolinite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Kaolinite-Zircon Alloy | `mvtink_alloy_kaolinite_zircon` | Terrain, Resonant, Swift |

#### Lapis Matrix · `mvtink_lapis_matrix` · 53 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Magma Brimstone (`mvtink_magma_brimstone`) | Lapis Matrix-Magma Brimstone Alloy | `mvtink_alloy_lapis_matrix_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Lapis Matrix-Magmacite Alloy | `mvtink_alloy_lapis_matrix_magmacite` | Infernal, Terrain, Radiant |
| Magnetite (`mvtink_magnetite`) | Lapis Matrix-Magnetite Alloy | `mvtink_alloy_lapis_matrix_magnetite` | Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Lapis Matrix-Malachite Alloy | `mvtink_alloy_lapis_matrix_malachite` | Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Lapis Matrix-Nebulite Alloy | `mvtink_alloy_lapis_matrix_nebulite` | Void, Terrain, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Lapis Matrix-Nether Bismuth Alloy | `mvtink_alloy_lapis_matrix_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Lapis Matrix-Nether Tungsten Alloy | `mvtink_alloy_lapis_matrix_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Lapis Matrix-Netherite Scrap Shard Alloy | `mvtink_alloy_lapis_matrix_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Lapis Matrix-Nickel Alloy | `mvtink_alloy_lapis_matrix_nickel` | Terrain, Tempered, Radiant |
| Null-Shard (`mvtink_null_shard`) | Lapis Matrix-Null-Shard Alloy | `mvtink_alloy_lapis_matrix_null_shard` | Void, Terrain, Radiant |
| Obsidian (`mvtink_obsidian`) | Lapis Matrix-Obsidian Alloy | `mvtink_alloy_lapis_matrix_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Lapis Matrix-Obsidianite Alloy | `mvtink_alloy_lapis_matrix_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Lapis Matrix-Opal Alloy | `mvtink_alloy_lapis_matrix_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Lapis Matrix-Ender Pearl Core Alloy | `mvtink_alloy_lapis_matrix_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Lapis Matrix-Phantomite Alloy | `mvtink_alloy_lapis_matrix_phantomite` | Void, Terrain, Radiant |
| Platinum (`mvtink_platinum`) | Lapis Matrix-Platinum Alloy | `mvtink_alloy_lapis_matrix_platinum` | Terrain, Tempered, Radiant |
| Prismarine (`mvtink_prismarine`) | Lapis Matrix-Prismarine Alloy | `mvtink_alloy_lapis_matrix_prismarine` | Primal, Terrain, Radiant |
| Pyrite (`mvtink_pyrite`) | Lapis Matrix-Pyrite Alloy | `mvtink_alloy_lapis_matrix_pyrite` | Terrain, Radiant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Lapis Matrix-Pyrophore Alloy | `mvtink_alloy_lapis_matrix_pyrophore` | Infernal, Terrain, Radiant |
| Nether Quartz (`mvtink_quartz`) | Lapis Matrix-Nether Quartz Alloy | `mvtink_alloy_lapis_matrix_quartz` | Primal, Terrain, Radiant |
| Redstone (`mvtink_redstone`) | Lapis Matrix-Redstone Alloy | `mvtink_alloy_lapis_matrix_redstone` | Primal, Terrain, Radiant |
| Resonite (`mvtink_resonite`) | Lapis Matrix-Resonite Alloy | `mvtink_alloy_lapis_matrix_resonite` | Void, Terrain, Radiant |
| Ruby (`mvtink_ruby`) | Lapis Matrix-Ruby Alloy | `mvtink_alloy_lapis_matrix_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Lapis Matrix-Sanguinite Alloy | `mvtink_alloy_lapis_matrix_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Lapis Matrix-Sapphire Alloy | `mvtink_alloy_lapis_matrix_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Lapis Matrix-Shadowgem Alloy | `mvtink_alloy_lapis_matrix_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Lapis Matrix-Shulkerite Alloy | `mvtink_alloy_lapis_matrix_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Lapis Matrix-Silver Alloy | `mvtink_alloy_lapis_matrix_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Lapis Matrix-Singularite Alloy | `mvtink_alloy_lapis_matrix_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Lapis Matrix-Soul Glass Crystal Alloy | `mvtink_alloy_lapis_matrix_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Lapis Matrix-Spatial Platinum Alloy | `mvtink_alloy_lapis_matrix_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Lapis Matrix-Starlight Silver Alloy | `mvtink_alloy_lapis_matrix_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Lapis Matrix-Steel Alloy | `mvtink_alloy_lapis_matrix_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Lapis Matrix-Stibnite Alloy | `mvtink_alloy_lapis_matrix_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Lapis Matrix-Sulfur Alloy | `mvtink_alloy_lapis_matrix_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Lapis Matrix-Talc Alloy | `mvtink_alloy_lapis_matrix_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Lapis Matrix-Tesseract Crystal Alloy | `mvtink_alloy_lapis_matrix_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Lapis Matrix-Tin Alloy | `mvtink_alloy_lapis_matrix_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Lapis Matrix-Titanium Alloy | `mvtink_alloy_lapis_matrix_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Lapis Matrix-Topaz Alloy | `mvtink_alloy_lapis_matrix_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Lapis Matrix-Tourmaline Alloy | `mvtink_alloy_lapis_matrix_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Lapis Matrix-Tungsten Alloy | `mvtink_alloy_lapis_matrix_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Lapis Matrix-Void Pyrite Alloy | `mvtink_alloy_lapis_matrix_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Lapis Matrix-Void Titanium Alloy | `mvtink_alloy_lapis_matrix_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Lapis Matrix-Voidstone Alloy | `mvtink_alloy_lapis_matrix_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Lapis Matrix-Compacted Volcanic Ash Alloy | `mvtink_alloy_lapis_matrix_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Lapis Matrix-Warped Emerald Alloy | `mvtink_alloy_lapis_matrix_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Lapis Matrix-Warped Quartz Alloy | `mvtink_alloy_lapis_matrix_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Lapis Matrix-Pure Weeping Shard Alloy | `mvtink_alloy_lapis_matrix_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Lapis Matrix-Witherite Alloy | `mvtink_alloy_lapis_matrix_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Lapis Matrix-Zero-Point Shard Alloy | `mvtink_alloy_lapis_matrix_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Lapis Matrix-Zinc Alloy | `mvtink_alloy_lapis_matrix_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Lapis Matrix-Zircon Alloy | `mvtink_alloy_lapis_matrix_zircon` | Terrain, Radiant, Resonant |

#### Magnetite · `mvtink_magnetite` · 50 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Malachite (`mvtink_malachite`) | Magnetite-Malachite Alloy | `mvtink_alloy_magnetite_malachite` | Terrain |
| Nebulite (`mvtink_nebulite`) | Magnetite-Nebulite Alloy | `mvtink_alloy_magnetite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Magnetite-Nether Bismuth Alloy | `mvtink_alloy_magnetite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Magnetite-Nether Tungsten Alloy | `mvtink_alloy_magnetite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Magnetite-Netherite Scrap Shard Alloy | `mvtink_alloy_magnetite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Magnetite-Nickel Alloy | `mvtink_alloy_magnetite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Magnetite-Null-Shard Alloy | `mvtink_alloy_magnetite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Magnetite-Obsidian Alloy | `mvtink_alloy_magnetite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Magnetite-Obsidianite Alloy | `mvtink_alloy_magnetite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Magnetite-Opal Alloy | `mvtink_alloy_magnetite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Magnetite-Ender Pearl Core Alloy | `mvtink_alloy_magnetite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Magnetite-Phantomite Alloy | `mvtink_alloy_magnetite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Magnetite-Platinum Alloy | `mvtink_alloy_magnetite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Magnetite-Prismarine Alloy | `mvtink_alloy_magnetite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Magnetite-Pyrite Alloy | `mvtink_alloy_magnetite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Magnetite-Pyrophore Alloy | `mvtink_alloy_magnetite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Magnetite-Nether Quartz Alloy | `mvtink_alloy_magnetite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Magnetite-Redstone Alloy | `mvtink_alloy_magnetite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Magnetite-Resonite Alloy | `mvtink_alloy_magnetite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Magnetite-Ruby Alloy | `mvtink_alloy_magnetite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Magnetite-Sanguinite Alloy | `mvtink_alloy_magnetite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Magnetite-Sapphire Alloy | `mvtink_alloy_magnetite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Magnetite-Shadowgem Alloy | `mvtink_alloy_magnetite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Magnetite-Shulkerite Alloy | `mvtink_alloy_magnetite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Magnetite-Silver Alloy | `mvtink_alloy_magnetite_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Magnetite-Singularite Alloy | `mvtink_alloy_magnetite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Magnetite-Soul Glass Crystal Alloy | `mvtink_alloy_magnetite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Magnetite-Spatial Platinum Alloy | `mvtink_alloy_magnetite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Magnetite-Starlight Silver Alloy | `mvtink_alloy_magnetite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Magnetite-Steel Alloy | `mvtink_alloy_magnetite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Magnetite-Stibnite Alloy | `mvtink_alloy_magnetite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Magnetite-Sulfur Alloy | `mvtink_alloy_magnetite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Magnetite-Talc Alloy | `mvtink_alloy_magnetite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Magnetite-Tesseract Crystal Alloy | `mvtink_alloy_magnetite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Magnetite-Tin Alloy | `mvtink_alloy_magnetite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Magnetite-Titanium Alloy | `mvtink_alloy_magnetite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Magnetite-Topaz Alloy | `mvtink_alloy_magnetite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Magnetite-Tourmaline Alloy | `mvtink_alloy_magnetite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Magnetite-Tungsten Alloy | `mvtink_alloy_magnetite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Magnetite-Void Pyrite Alloy | `mvtink_alloy_magnetite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Magnetite-Void Titanium Alloy | `mvtink_alloy_magnetite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Magnetite-Voidstone Alloy | `mvtink_alloy_magnetite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Magnetite-Compacted Volcanic Ash Alloy | `mvtink_alloy_magnetite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Magnetite-Warped Emerald Alloy | `mvtink_alloy_magnetite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Magnetite-Warped Quartz Alloy | `mvtink_alloy_magnetite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Magnetite-Pure Weeping Shard Alloy | `mvtink_alloy_magnetite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Magnetite-Witherite Alloy | `mvtink_alloy_magnetite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Magnetite-Zero-Point Shard Alloy | `mvtink_alloy_magnetite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Magnetite-Zinc Alloy | `mvtink_alloy_magnetite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Magnetite-Zircon Alloy | `mvtink_alloy_magnetite_zircon` | Terrain, Resonant, Swift |

#### Malachite · `mvtink_malachite` · 49 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Nebulite (`mvtink_nebulite`) | Malachite-Nebulite Alloy | `mvtink_alloy_malachite_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Malachite-Nether Bismuth Alloy | `mvtink_alloy_malachite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Malachite-Nether Tungsten Alloy | `mvtink_alloy_malachite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Malachite-Netherite Scrap Shard Alloy | `mvtink_alloy_malachite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Malachite-Nickel Alloy | `mvtink_alloy_malachite_nickel` | Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Malachite-Null-Shard Alloy | `mvtink_alloy_malachite_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Malachite-Obsidian Alloy | `mvtink_alloy_malachite_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Malachite-Obsidianite Alloy | `mvtink_alloy_malachite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Malachite-Opal Alloy | `mvtink_alloy_malachite_opal` | Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Malachite-Ender Pearl Core Alloy | `mvtink_alloy_malachite_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Malachite-Phantomite Alloy | `mvtink_alloy_malachite_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Malachite-Platinum Alloy | `mvtink_alloy_malachite_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Malachite-Prismarine Alloy | `mvtink_alloy_malachite_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Malachite-Pyrite Alloy | `mvtink_alloy_malachite_pyrite` | Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Malachite-Pyrophore Alloy | `mvtink_alloy_malachite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Malachite-Nether Quartz Alloy | `mvtink_alloy_malachite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Malachite-Redstone Alloy | `mvtink_alloy_malachite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Malachite-Resonite Alloy | `mvtink_alloy_malachite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Malachite-Ruby Alloy | `mvtink_alloy_malachite_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Malachite-Sanguinite Alloy | `mvtink_alloy_malachite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Malachite-Sapphire Alloy | `mvtink_alloy_malachite_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Malachite-Shadowgem Alloy | `mvtink_alloy_malachite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Malachite-Shulkerite Alloy | `mvtink_alloy_malachite_shulkerite` | Void, Terrain, Bulwark |
| Silver (`mvtink_silver`) | Malachite-Silver Alloy | `mvtink_alloy_malachite_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Malachite-Singularite Alloy | `mvtink_alloy_malachite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Malachite-Soul Glass Crystal Alloy | `mvtink_alloy_malachite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Malachite-Spatial Platinum Alloy | `mvtink_alloy_malachite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Malachite-Starlight Silver Alloy | `mvtink_alloy_malachite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Malachite-Steel Alloy | `mvtink_alloy_malachite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Malachite-Stibnite Alloy | `mvtink_alloy_malachite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Malachite-Sulfur Alloy | `mvtink_alloy_malachite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Malachite-Talc Alloy | `mvtink_alloy_malachite_talc` | Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Malachite-Tesseract Crystal Alloy | `mvtink_alloy_malachite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Malachite-Tin Alloy | `mvtink_alloy_malachite_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Malachite-Titanium Alloy | `mvtink_alloy_malachite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Malachite-Topaz Alloy | `mvtink_alloy_malachite_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Malachite-Tourmaline Alloy | `mvtink_alloy_malachite_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Malachite-Tungsten Alloy | `mvtink_alloy_malachite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Malachite-Void Pyrite Alloy | `mvtink_alloy_malachite_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Malachite-Void Titanium Alloy | `mvtink_alloy_malachite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Malachite-Voidstone Alloy | `mvtink_alloy_malachite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Malachite-Compacted Volcanic Ash Alloy | `mvtink_alloy_malachite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Malachite-Warped Emerald Alloy | `mvtink_alloy_malachite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Malachite-Warped Quartz Alloy | `mvtink_alloy_malachite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Malachite-Pure Weeping Shard Alloy | `mvtink_alloy_malachite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Malachite-Witherite Alloy | `mvtink_alloy_malachite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Malachite-Zero-Point Shard Alloy | `mvtink_alloy_malachite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Malachite-Zinc Alloy | `mvtink_alloy_malachite_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Malachite-Zircon Alloy | `mvtink_alloy_malachite_zircon` | Terrain, Resonant, Swift |

#### Nickel · `mvtink_nickel` · 44 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Null-Shard (`mvtink_null_shard`) | Nickel-Null-Shard Alloy | `mvtink_alloy_nickel_null_shard` | Void, Terrain, Tempered |
| Obsidian (`mvtink_obsidian`) | Nickel-Obsidian Alloy | `mvtink_alloy_nickel_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Nickel-Obsidianite Alloy | `mvtink_alloy_nickel_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Nickel-Opal Alloy | `mvtink_alloy_nickel_opal` | Terrain, Tempered, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Nickel-Ender Pearl Core Alloy | `mvtink_alloy_nickel_pearl_core` | Void, Terrain, Tempered |
| Phantomite (`mvtink_phantomite`) | Nickel-Phantomite Alloy | `mvtink_alloy_nickel_phantomite` | Void, Terrain, Tempered |
| Platinum (`mvtink_platinum`) | Nickel-Platinum Alloy | `mvtink_alloy_nickel_platinum` | Terrain, Tempered, Swift |
| Prismarine (`mvtink_prismarine`) | Nickel-Prismarine Alloy | `mvtink_alloy_nickel_prismarine` | Primal, Terrain, Tempered |
| Pyrite (`mvtink_pyrite`) | Nickel-Pyrite Alloy | `mvtink_alloy_nickel_pyrite` | Terrain, Tempered, Swift |
| Pyrophore (`mvtink_pyrophore`) | Nickel-Pyrophore Alloy | `mvtink_alloy_nickel_pyrophore` | Infernal, Terrain, Tempered |
| Nether Quartz (`mvtink_quartz`) | Nickel-Nether Quartz Alloy | `mvtink_alloy_nickel_quartz` | Primal, Terrain, Tempered |
| Redstone (`mvtink_redstone`) | Nickel-Redstone Alloy | `mvtink_alloy_nickel_redstone` | Primal, Terrain, Tempered |
| Resonite (`mvtink_resonite`) | Nickel-Resonite Alloy | `mvtink_alloy_nickel_resonite` | Void, Terrain, Tempered |
| Ruby (`mvtink_ruby`) | Nickel-Ruby Alloy | `mvtink_alloy_nickel_ruby` | Terrain, Tempered, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Nickel-Sanguinite Alloy | `mvtink_alloy_nickel_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Nickel-Sapphire Alloy | `mvtink_alloy_nickel_sapphire` | Terrain, Tempered, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Nickel-Shadowgem Alloy | `mvtink_alloy_nickel_shadowgem` | Void, Terrain, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Nickel-Shulkerite Alloy | `mvtink_alloy_nickel_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Nickel-Silver Alloy | `mvtink_alloy_nickel_silver` | Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Nickel-Singularite Alloy | `mvtink_alloy_nickel_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Nickel-Soul Glass Crystal Alloy | `mvtink_alloy_nickel_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Nickel-Spatial Platinum Alloy | `mvtink_alloy_nickel_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Nickel-Starlight Silver Alloy | `mvtink_alloy_nickel_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Nickel-Steel Alloy | `mvtink_alloy_nickel_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Nickel-Stibnite Alloy | `mvtink_alloy_nickel_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Nickel-Sulfur Alloy | `mvtink_alloy_nickel_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Nickel-Talc Alloy | `mvtink_alloy_nickel_talc` | Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Nickel-Tesseract Crystal Alloy | `mvtink_alloy_nickel_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Nickel-Tin Alloy | `mvtink_alloy_nickel_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Nickel-Titanium Alloy | `mvtink_alloy_nickel_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Nickel-Topaz Alloy | `mvtink_alloy_nickel_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Nickel-Tourmaline Alloy | `mvtink_alloy_nickel_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Nickel-Tungsten Alloy | `mvtink_alloy_nickel_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Nickel-Void Pyrite Alloy | `mvtink_alloy_nickel_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Nickel-Void Titanium Alloy | `mvtink_alloy_nickel_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Nickel-Voidstone Alloy | `mvtink_alloy_nickel_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Nickel-Compacted Volcanic Ash Alloy | `mvtink_alloy_nickel_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Nickel-Warped Emerald Alloy | `mvtink_alloy_nickel_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Nickel-Warped Quartz Alloy | `mvtink_alloy_nickel_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Nickel-Pure Weeping Shard Alloy | `mvtink_alloy_nickel_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Nickel-Witherite Alloy | `mvtink_alloy_nickel_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Nickel-Zero-Point Shard Alloy | `mvtink_alloy_nickel_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Nickel-Zinc Alloy | `mvtink_alloy_nickel_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Nickel-Zircon Alloy | `mvtink_alloy_nickel_zircon` | Terrain, Tempered, Resonant |

#### Opal · `mvtink_opal` · 40 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ender Pearl Core (`mvtink_pearl_core`) | Opal-Ender Pearl Core Alloy | `mvtink_alloy_opal_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Opal-Phantomite Alloy | `mvtink_alloy_opal_phantomite` | Void, Terrain, Radiant |
| Platinum (`mvtink_platinum`) | Opal-Platinum Alloy | `mvtink_alloy_opal_platinum` | Terrain, Tempered, Radiant |
| Prismarine (`mvtink_prismarine`) | Opal-Prismarine Alloy | `mvtink_alloy_opal_prismarine` | Primal, Terrain, Radiant |
| Pyrite (`mvtink_pyrite`) | Opal-Pyrite Alloy | `mvtink_alloy_opal_pyrite` | Terrain, Radiant, Swift |
| Pyrophore (`mvtink_pyrophore`) | Opal-Pyrophore Alloy | `mvtink_alloy_opal_pyrophore` | Infernal, Terrain, Radiant |
| Nether Quartz (`mvtink_quartz`) | Opal-Nether Quartz Alloy | `mvtink_alloy_opal_quartz` | Primal, Terrain, Radiant |
| Redstone (`mvtink_redstone`) | Opal-Redstone Alloy | `mvtink_alloy_opal_redstone` | Primal, Terrain, Radiant |
| Resonite (`mvtink_resonite`) | Opal-Resonite Alloy | `mvtink_alloy_opal_resonite` | Void, Terrain, Radiant |
| Ruby (`mvtink_ruby`) | Opal-Ruby Alloy | `mvtink_alloy_opal_ruby` | Terrain, Radiant, Brutal |
| Sanguinite (`mvtink_sanguinite`) | Opal-Sanguinite Alloy | `mvtink_alloy_opal_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Opal-Sapphire Alloy | `mvtink_alloy_opal_sapphire` | Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Opal-Shadowgem Alloy | `mvtink_alloy_opal_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Opal-Shulkerite Alloy | `mvtink_alloy_opal_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Opal-Silver Alloy | `mvtink_alloy_opal_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Opal-Singularite Alloy | `mvtink_alloy_opal_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Opal-Soul Glass Crystal Alloy | `mvtink_alloy_opal_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Opal-Spatial Platinum Alloy | `mvtink_alloy_opal_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Opal-Starlight Silver Alloy | `mvtink_alloy_opal_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Opal-Steel Alloy | `mvtink_alloy_opal_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Opal-Stibnite Alloy | `mvtink_alloy_opal_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Opal-Sulfur Alloy | `mvtink_alloy_opal_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Opal-Talc Alloy | `mvtink_alloy_opal_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Opal-Tesseract Crystal Alloy | `mvtink_alloy_opal_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Opal-Tin Alloy | `mvtink_alloy_opal_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Opal-Titanium Alloy | `mvtink_alloy_opal_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Opal-Topaz Alloy | `mvtink_alloy_opal_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Opal-Tourmaline Alloy | `mvtink_alloy_opal_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Opal-Tungsten Alloy | `mvtink_alloy_opal_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Opal-Void Pyrite Alloy | `mvtink_alloy_opal_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Opal-Void Titanium Alloy | `mvtink_alloy_opal_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Opal-Voidstone Alloy | `mvtink_alloy_opal_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Opal-Compacted Volcanic Ash Alloy | `mvtink_alloy_opal_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Opal-Warped Emerald Alloy | `mvtink_alloy_opal_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Opal-Warped Quartz Alloy | `mvtink_alloy_opal_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Opal-Pure Weeping Shard Alloy | `mvtink_alloy_opal_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Opal-Witherite Alloy | `mvtink_alloy_opal_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Opal-Zero-Point Shard Alloy | `mvtink_alloy_opal_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Opal-Zinc Alloy | `mvtink_alloy_opal_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Opal-Zircon Alloy | `mvtink_alloy_opal_zircon` | Terrain, Radiant, Resonant |

#### Platinum · `mvtink_platinum` · 37 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Prismarine (`mvtink_prismarine`) | Platinum-Prismarine Alloy | `mvtink_alloy_platinum_prismarine` | Primal, Terrain, Tempered |
| Pyrite (`mvtink_pyrite`) | Platinum-Pyrite Alloy | `mvtink_alloy_platinum_pyrite` | Terrain, Tempered, Swift |
| Pyrophore (`mvtink_pyrophore`) | Platinum-Pyrophore Alloy | `mvtink_alloy_platinum_pyrophore` | Infernal, Terrain, Tempered |
| Nether Quartz (`mvtink_quartz`) | Platinum-Nether Quartz Alloy | `mvtink_alloy_platinum_quartz` | Primal, Terrain, Tempered |
| Redstone (`mvtink_redstone`) | Platinum-Redstone Alloy | `mvtink_alloy_platinum_redstone` | Primal, Terrain, Tempered |
| Resonite (`mvtink_resonite`) | Platinum-Resonite Alloy | `mvtink_alloy_platinum_resonite` | Void, Terrain, Tempered |
| Ruby (`mvtink_ruby`) | Platinum-Ruby Alloy | `mvtink_alloy_platinum_ruby` | Terrain, Tempered, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Platinum-Sanguinite Alloy | `mvtink_alloy_platinum_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Platinum-Sapphire Alloy | `mvtink_alloy_platinum_sapphire` | Terrain, Tempered, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Platinum-Shadowgem Alloy | `mvtink_alloy_platinum_shadowgem` | Void, Terrain, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Platinum-Shulkerite Alloy | `mvtink_alloy_platinum_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Platinum-Silver Alloy | `mvtink_alloy_platinum_silver` | Terrain, Tempered, Swift |
| Singularite (`mvtink_singularite`) | Platinum-Singularite Alloy | `mvtink_alloy_platinum_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Platinum-Soul Glass Crystal Alloy | `mvtink_alloy_platinum_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Platinum-Spatial Platinum Alloy | `mvtink_alloy_platinum_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Platinum-Starlight Silver Alloy | `mvtink_alloy_platinum_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Platinum-Steel Alloy | `mvtink_alloy_platinum_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Platinum-Stibnite Alloy | `mvtink_alloy_platinum_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Platinum-Sulfur Alloy | `mvtink_alloy_platinum_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Platinum-Talc Alloy | `mvtink_alloy_platinum_talc` | Terrain, Tempered, Swift |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Platinum-Tesseract Crystal Alloy | `mvtink_alloy_platinum_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Platinum-Tin Alloy | `mvtink_alloy_platinum_tin` | Terrain, Tempered, Swift |
| Titanium (`mvtink_titanium`) | Platinum-Titanium Alloy | `mvtink_alloy_platinum_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Platinum-Topaz Alloy | `mvtink_alloy_platinum_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Platinum-Tourmaline Alloy | `mvtink_alloy_platinum_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Platinum-Tungsten Alloy | `mvtink_alloy_platinum_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Platinum-Void Pyrite Alloy | `mvtink_alloy_platinum_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Platinum-Void Titanium Alloy | `mvtink_alloy_platinum_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Platinum-Voidstone Alloy | `mvtink_alloy_platinum_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Platinum-Compacted Volcanic Ash Alloy | `mvtink_alloy_platinum_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Platinum-Warped Emerald Alloy | `mvtink_alloy_platinum_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Platinum-Warped Quartz Alloy | `mvtink_alloy_platinum_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Platinum-Pure Weeping Shard Alloy | `mvtink_alloy_platinum_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Platinum-Witherite Alloy | `mvtink_alloy_platinum_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Platinum-Zero-Point Shard Alloy | `mvtink_alloy_platinum_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Platinum-Zinc Alloy | `mvtink_alloy_platinum_zinc` | Terrain, Tempered, Swift |
| Zircon (`mvtink_zircon`) | Platinum-Zircon Alloy | `mvtink_alloy_platinum_zircon` | Terrain, Tempered, Resonant |

#### Pyrite · `mvtink_pyrite` · 35 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Pyrophore (`mvtink_pyrophore`) | Pyrite-Pyrophore Alloy | `mvtink_alloy_pyrite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Pyrite-Nether Quartz Alloy | `mvtink_alloy_pyrite_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Pyrite-Redstone Alloy | `mvtink_alloy_pyrite_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Pyrite-Resonite Alloy | `mvtink_alloy_pyrite_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Pyrite-Ruby Alloy | `mvtink_alloy_pyrite_ruby` | Terrain, Radiant, Swift |
| Sanguinite (`mvtink_sanguinite`) | Pyrite-Sanguinite Alloy | `mvtink_alloy_pyrite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Pyrite-Sapphire Alloy | `mvtink_alloy_pyrite_sapphire` | Terrain, Radiant, Swift |
| Shadowgem (`mvtink_shadowgem`) | Pyrite-Shadowgem Alloy | `mvtink_alloy_pyrite_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Pyrite-Shulkerite Alloy | `mvtink_alloy_pyrite_shulkerite` | Void, Terrain, Swift |
| Silver (`mvtink_silver`) | Pyrite-Silver Alloy | `mvtink_alloy_pyrite_silver` | Terrain, Tempered, Swift |
| Singularite (`mvtink_singularite`) | Pyrite-Singularite Alloy | `mvtink_alloy_pyrite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Pyrite-Soul Glass Crystal Alloy | `mvtink_alloy_pyrite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Pyrite-Spatial Platinum Alloy | `mvtink_alloy_pyrite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Pyrite-Starlight Silver Alloy | `mvtink_alloy_pyrite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Pyrite-Steel Alloy | `mvtink_alloy_pyrite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Pyrite-Stibnite Alloy | `mvtink_alloy_pyrite_stibnite` | Infernal, Terrain, Swift |
| Sulfur (`mvtink_sulfur`) | Pyrite-Sulfur Alloy | `mvtink_alloy_pyrite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Pyrite-Talc Alloy | `mvtink_alloy_pyrite_talc` | Terrain, Swift |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Pyrite-Tesseract Crystal Alloy | `mvtink_alloy_pyrite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Pyrite-Tin Alloy | `mvtink_alloy_pyrite_tin` | Terrain, Tempered, Swift |
| Titanium (`mvtink_titanium`) | Pyrite-Titanium Alloy | `mvtink_alloy_pyrite_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Pyrite-Topaz Alloy | `mvtink_alloy_pyrite_topaz` | Terrain, Radiant, Swift |
| Tourmaline (`mvtink_tourmaline`) | Pyrite-Tourmaline Alloy | `mvtink_alloy_pyrite_tourmaline` | Terrain, Resonant, Swift |
| Tungsten (`mvtink_tungsten`) | Pyrite-Tungsten Alloy | `mvtink_alloy_pyrite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Pyrite-Void Pyrite Alloy | `mvtink_alloy_pyrite_void_pyrite` | Void, Terrain, Swift |
| Void Titanium (`mvtink_void_titanium`) | Pyrite-Void Titanium Alloy | `mvtink_alloy_pyrite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Pyrite-Voidstone Alloy | `mvtink_alloy_pyrite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Pyrite-Compacted Volcanic Ash Alloy | `mvtink_alloy_pyrite_volcanic_ash` | Infernal, Terrain, Swift |
| Warped Emerald (`mvtink_warped_emerald`) | Pyrite-Warped Emerald Alloy | `mvtink_alloy_pyrite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Pyrite-Warped Quartz Alloy | `mvtink_alloy_pyrite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Pyrite-Pure Weeping Shard Alloy | `mvtink_alloy_pyrite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Pyrite-Witherite Alloy | `mvtink_alloy_pyrite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Pyrite-Zero-Point Shard Alloy | `mvtink_alloy_pyrite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Pyrite-Zinc Alloy | `mvtink_alloy_pyrite_zinc` | Terrain, Tempered, Swift |
| Zircon (`mvtink_zircon`) | Pyrite-Zircon Alloy | `mvtink_alloy_pyrite_zircon` | Terrain, Resonant, Swift |

#### Ruby · `mvtink_ruby` · 30 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Sanguinite (`mvtink_sanguinite`) | Ruby-Sanguinite Alloy | `mvtink_alloy_ruby_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Ruby-Sapphire Alloy | `mvtink_alloy_ruby_sapphire` | Terrain, Radiant, Brutal |
| Shadowgem (`mvtink_shadowgem`) | Ruby-Shadowgem Alloy | `mvtink_alloy_ruby_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Ruby-Shulkerite Alloy | `mvtink_alloy_ruby_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Ruby-Silver Alloy | `mvtink_alloy_ruby_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Ruby-Singularite Alloy | `mvtink_alloy_ruby_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ruby-Soul Glass Crystal Alloy | `mvtink_alloy_ruby_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ruby-Spatial Platinum Alloy | `mvtink_alloy_ruby_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Ruby-Starlight Silver Alloy | `mvtink_alloy_ruby_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Ruby-Steel Alloy | `mvtink_alloy_ruby_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Ruby-Stibnite Alloy | `mvtink_alloy_ruby_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Ruby-Sulfur Alloy | `mvtink_alloy_ruby_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Ruby-Talc Alloy | `mvtink_alloy_ruby_talc` | Terrain, Radiant, Brutal |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ruby-Tesseract Crystal Alloy | `mvtink_alloy_ruby_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Ruby-Tin Alloy | `mvtink_alloy_ruby_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Ruby-Titanium Alloy | `mvtink_alloy_ruby_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Ruby-Topaz Alloy | `mvtink_alloy_ruby_topaz` | Terrain, Radiant, Brutal |
| Tourmaline (`mvtink_tourmaline`) | Ruby-Tourmaline Alloy | `mvtink_alloy_ruby_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Ruby-Tungsten Alloy | `mvtink_alloy_ruby_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Ruby-Void Pyrite Alloy | `mvtink_alloy_ruby_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Ruby-Void Titanium Alloy | `mvtink_alloy_ruby_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Ruby-Voidstone Alloy | `mvtink_alloy_ruby_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ruby-Compacted Volcanic Ash Alloy | `mvtink_alloy_ruby_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Ruby-Warped Emerald Alloy | `mvtink_alloy_ruby_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Ruby-Warped Quartz Alloy | `mvtink_alloy_ruby_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ruby-Pure Weeping Shard Alloy | `mvtink_alloy_ruby_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Ruby-Witherite Alloy | `mvtink_alloy_ruby_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Ruby-Zero-Point Shard Alloy | `mvtink_alloy_ruby_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Ruby-Zinc Alloy | `mvtink_alloy_ruby_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Ruby-Zircon Alloy | `mvtink_alloy_ruby_zircon` | Terrain, Radiant, Resonant |

#### Sapphire · `mvtink_sapphire` · 28 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Shadowgem (`mvtink_shadowgem`) | Sapphire-Shadowgem Alloy | `mvtink_alloy_sapphire_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Sapphire-Shulkerite Alloy | `mvtink_alloy_sapphire_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Sapphire-Silver Alloy | `mvtink_alloy_sapphire_silver` | Terrain, Tempered, Radiant |
| Singularite (`mvtink_singularite`) | Sapphire-Singularite Alloy | `mvtink_alloy_sapphire_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Sapphire-Soul Glass Crystal Alloy | `mvtink_alloy_sapphire_soulsand_crystal` | Infernal, Terrain, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Sapphire-Spatial Platinum Alloy | `mvtink_alloy_sapphire_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Sapphire-Starlight Silver Alloy | `mvtink_alloy_sapphire_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Sapphire-Steel Alloy | `mvtink_alloy_sapphire_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Sapphire-Stibnite Alloy | `mvtink_alloy_sapphire_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Sapphire-Sulfur Alloy | `mvtink_alloy_sapphire_sulfur` | Infernal, Terrain, Radiant |
| Talc (`mvtink_talc`) | Sapphire-Talc Alloy | `mvtink_alloy_sapphire_talc` | Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Sapphire-Tesseract Crystal Alloy | `mvtink_alloy_sapphire_tesseract_crystal` | Void, Terrain, Radiant |
| Tin (`mvtink_tin`) | Sapphire-Tin Alloy | `mvtink_alloy_sapphire_tin` | Terrain, Tempered, Radiant |
| Titanium (`mvtink_titanium`) | Sapphire-Titanium Alloy | `mvtink_alloy_sapphire_titanium` | Terrain, Tempered, Radiant |
| Topaz (`mvtink_topaz`) | Sapphire-Topaz Alloy | `mvtink_alloy_sapphire_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Sapphire-Tourmaline Alloy | `mvtink_alloy_sapphire_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Sapphire-Tungsten Alloy | `mvtink_alloy_sapphire_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Sapphire-Void Pyrite Alloy | `mvtink_alloy_sapphire_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Sapphire-Void Titanium Alloy | `mvtink_alloy_sapphire_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Sapphire-Voidstone Alloy | `mvtink_alloy_sapphire_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Sapphire-Compacted Volcanic Ash Alloy | `mvtink_alloy_sapphire_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Sapphire-Warped Emerald Alloy | `mvtink_alloy_sapphire_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Sapphire-Warped Quartz Alloy | `mvtink_alloy_sapphire_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Sapphire-Pure Weeping Shard Alloy | `mvtink_alloy_sapphire_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Sapphire-Witherite Alloy | `mvtink_alloy_sapphire_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Sapphire-Zero-Point Shard Alloy | `mvtink_alloy_sapphire_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Sapphire-Zinc Alloy | `mvtink_alloy_sapphire_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Sapphire-Zircon Alloy | `mvtink_alloy_sapphire_zircon` | Terrain, Radiant, Resonant |

#### Silver · `mvtink_silver` · 25 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Singularite (`mvtink_singularite`) | Silver-Singularite Alloy | `mvtink_alloy_silver_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Silver-Soul Glass Crystal Alloy | `mvtink_alloy_silver_soulsand_crystal` | Infernal, Terrain, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Silver-Spatial Platinum Alloy | `mvtink_alloy_silver_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Silver-Starlight Silver Alloy | `mvtink_alloy_silver_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Silver-Steel Alloy | `mvtink_alloy_silver_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Silver-Stibnite Alloy | `mvtink_alloy_silver_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Silver-Sulfur Alloy | `mvtink_alloy_silver_sulfur` | Infernal, Terrain, Tempered |
| Talc (`mvtink_talc`) | Silver-Talc Alloy | `mvtink_alloy_silver_talc` | Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Silver-Tesseract Crystal Alloy | `mvtink_alloy_silver_tesseract_crystal` | Void, Terrain, Tempered |
| Tin (`mvtink_tin`) | Silver-Tin Alloy | `mvtink_alloy_silver_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Silver-Titanium Alloy | `mvtink_alloy_silver_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Silver-Topaz Alloy | `mvtink_alloy_silver_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Silver-Tourmaline Alloy | `mvtink_alloy_silver_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Silver-Tungsten Alloy | `mvtink_alloy_silver_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Silver-Void Pyrite Alloy | `mvtink_alloy_silver_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Silver-Void Titanium Alloy | `mvtink_alloy_silver_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Silver-Voidstone Alloy | `mvtink_alloy_silver_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Silver-Compacted Volcanic Ash Alloy | `mvtink_alloy_silver_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Silver-Warped Emerald Alloy | `mvtink_alloy_silver_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Silver-Warped Quartz Alloy | `mvtink_alloy_silver_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Silver-Pure Weeping Shard Alloy | `mvtink_alloy_silver_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Silver-Witherite Alloy | `mvtink_alloy_silver_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Silver-Zero-Point Shard Alloy | `mvtink_alloy_silver_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Silver-Zinc Alloy | `mvtink_alloy_silver_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Silver-Zircon Alloy | `mvtink_alloy_silver_zircon` | Terrain, Tempered, Resonant |

#### Talc · `mvtink_talc` · 17 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Talc-Tesseract Crystal Alloy | `mvtink_alloy_talc_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Talc-Tin Alloy | `mvtink_alloy_talc_tin` | Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Talc-Titanium Alloy | `mvtink_alloy_talc_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Talc-Topaz Alloy | `mvtink_alloy_talc_topaz` | Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Talc-Tourmaline Alloy | `mvtink_alloy_talc_tourmaline` | Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Talc-Tungsten Alloy | `mvtink_alloy_talc_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Talc-Void Pyrite Alloy | `mvtink_alloy_talc_void_pyrite` | Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Talc-Void Titanium Alloy | `mvtink_alloy_talc_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Talc-Voidstone Alloy | `mvtink_alloy_talc_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Talc-Compacted Volcanic Ash Alloy | `mvtink_alloy_talc_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Talc-Warped Emerald Alloy | `mvtink_alloy_talc_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Talc-Warped Quartz Alloy | `mvtink_alloy_talc_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Talc-Pure Weeping Shard Alloy | `mvtink_alloy_talc_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Talc-Witherite Alloy | `mvtink_alloy_talc_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Talc-Zero-Point Shard Alloy | `mvtink_alloy_talc_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Talc-Zinc Alloy | `mvtink_alloy_talc_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Talc-Zircon Alloy | `mvtink_alloy_talc_zircon` | Terrain, Resonant, Swift |

#### Tin · `mvtink_tin` · 15 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Titanium (`mvtink_titanium`) | Tin-Titanium Alloy | `mvtink_alloy_tin_titanium` | Terrain, Tempered, Swift |
| Topaz (`mvtink_topaz`) | Tin-Topaz Alloy | `mvtink_alloy_tin_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Tin-Tourmaline Alloy | `mvtink_alloy_tin_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Tin-Tungsten Alloy | `mvtink_alloy_tin_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Tin-Void Pyrite Alloy | `mvtink_alloy_tin_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Tin-Void Titanium Alloy | `mvtink_alloy_tin_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Tin-Voidstone Alloy | `mvtink_alloy_tin_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Tin-Compacted Volcanic Ash Alloy | `mvtink_alloy_tin_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Tin-Warped Emerald Alloy | `mvtink_alloy_tin_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Tin-Warped Quartz Alloy | `mvtink_alloy_tin_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Tin-Pure Weeping Shard Alloy | `mvtink_alloy_tin_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Tin-Witherite Alloy | `mvtink_alloy_tin_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Tin-Zero-Point Shard Alloy | `mvtink_alloy_tin_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Tin-Zinc Alloy | `mvtink_alloy_tin_zinc` | Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Tin-Zircon Alloy | `mvtink_alloy_tin_zircon` | Terrain, Tempered, Resonant |

#### Titanium · `mvtink_titanium` · 14 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Topaz (`mvtink_topaz`) | Titanium-Topaz Alloy | `mvtink_alloy_titanium_topaz` | Terrain, Tempered, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Titanium-Tourmaline Alloy | `mvtink_alloy_titanium_tourmaline` | Terrain, Tempered, Resonant |
| Tungsten (`mvtink_tungsten`) | Titanium-Tungsten Alloy | `mvtink_alloy_titanium_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Titanium-Void Pyrite Alloy | `mvtink_alloy_titanium_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Titanium-Void Titanium Alloy | `mvtink_alloy_titanium_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Titanium-Voidstone Alloy | `mvtink_alloy_titanium_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Titanium-Compacted Volcanic Ash Alloy | `mvtink_alloy_titanium_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Titanium-Warped Emerald Alloy | `mvtink_alloy_titanium_warped_emerald` | Infernal, Terrain, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Titanium-Warped Quartz Alloy | `mvtink_alloy_titanium_warped_quartz` | Void, Terrain, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Titanium-Pure Weeping Shard Alloy | `mvtink_alloy_titanium_weeping_shard` | Infernal, Terrain, Tempered |
| Witherite (`mvtink_witherite`) | Titanium-Witherite Alloy | `mvtink_alloy_titanium_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Titanium-Zero-Point Shard Alloy | `mvtink_alloy_titanium_zero_point` | Void, Terrain, Tempered |
| Zinc (`mvtink_zinc`) | Titanium-Zinc Alloy | `mvtink_alloy_titanium_zinc` | Terrain, Tempered, Swift |
| Zircon (`mvtink_zircon`) | Titanium-Zircon Alloy | `mvtink_alloy_titanium_zircon` | Terrain, Tempered, Resonant |

#### Topaz · `mvtink_topaz` · 13 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Tourmaline (`mvtink_tourmaline`) | Topaz-Tourmaline Alloy | `mvtink_alloy_topaz_tourmaline` | Terrain, Radiant, Resonant |
| Tungsten (`mvtink_tungsten`) | Topaz-Tungsten Alloy | `mvtink_alloy_topaz_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Topaz-Void Pyrite Alloy | `mvtink_alloy_topaz_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Topaz-Void Titanium Alloy | `mvtink_alloy_topaz_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Topaz-Voidstone Alloy | `mvtink_alloy_topaz_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Topaz-Compacted Volcanic Ash Alloy | `mvtink_alloy_topaz_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Topaz-Warped Emerald Alloy | `mvtink_alloy_topaz_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Topaz-Warped Quartz Alloy | `mvtink_alloy_topaz_warped_quartz` | Void, Terrain, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Topaz-Pure Weeping Shard Alloy | `mvtink_alloy_topaz_weeping_shard` | Infernal, Terrain, Radiant |
| Witherite (`mvtink_witherite`) | Topaz-Witherite Alloy | `mvtink_alloy_topaz_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Topaz-Zero-Point Shard Alloy | `mvtink_alloy_topaz_zero_point` | Void, Terrain, Radiant |
| Zinc (`mvtink_zinc`) | Topaz-Zinc Alloy | `mvtink_alloy_topaz_zinc` | Terrain, Tempered, Radiant |
| Zircon (`mvtink_zircon`) | Topaz-Zircon Alloy | `mvtink_alloy_topaz_zircon` | Terrain, Radiant, Resonant |

#### Tourmaline · `mvtink_tourmaline` · 12 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Tungsten (`mvtink_tungsten`) | Tourmaline-Tungsten Alloy | `mvtink_alloy_tourmaline_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Tourmaline-Void Pyrite Alloy | `mvtink_alloy_tourmaline_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Tourmaline-Void Titanium Alloy | `mvtink_alloy_tourmaline_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Tourmaline-Voidstone Alloy | `mvtink_alloy_tourmaline_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Tourmaline-Compacted Volcanic Ash Alloy | `mvtink_alloy_tourmaline_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Tourmaline-Warped Emerald Alloy | `mvtink_alloy_tourmaline_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Tourmaline-Warped Quartz Alloy | `mvtink_alloy_tourmaline_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Tourmaline-Pure Weeping Shard Alloy | `mvtink_alloy_tourmaline_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Tourmaline-Witherite Alloy | `mvtink_alloy_tourmaline_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Tourmaline-Zero-Point Shard Alloy | `mvtink_alloy_tourmaline_zero_point` | Void, Terrain, Resonant |
| Zinc (`mvtink_zinc`) | Tourmaline-Zinc Alloy | `mvtink_alloy_tourmaline_zinc` | Terrain, Tempered, Resonant |
| Zircon (`mvtink_zircon`) | Tourmaline-Zircon Alloy | `mvtink_alloy_tourmaline_zircon` | Terrain, Resonant, Swift |

#### Zinc · `mvtink_zinc` · 1 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Zircon (`mvtink_zircon`) | Zinc-Zircon Alloy | `mvtink_alloy_zinc_zircon` | Terrain, Tempered, Resonant |

#### Zircon · `mvtink_zircon` · 0 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|

### 🔥 Padres del Nether

#### Ancient Debris Slag · `mvtink_ancient_slag` · 102 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Aquamarine (`mvtink_aquamarine`) | Ancient Debris Slag-Aquamarine Alloy | `mvtink_alloy_ancient_slag_aquamarine` | Infernal, Terrain, Tempered |
| Ardite (`mvtink_ardite`) | Ancient Debris Slag-Ardite Alloy | `mvtink_alloy_ancient_slag_ardite` | Infernal, Tempered, Brutal |
| Astralite (`mvtink_astralite`) | Ancient Debris Slag-Astralite Alloy | `mvtink_alloy_ancient_slag_astralite` | Infernal, Void, Tempered |
| Bauxite (`mvtink_bauxite`) | Ancient Debris Slag-Bauxite Alloy | `mvtink_alloy_ancient_slag_bauxite` | Infernal, Terrain, Tempered |
| Beryllium (`mvtink_beryllium`) | Ancient Debris Slag-Beryllium Alloy | `mvtink_alloy_ancient_slag_beryllium` | Infernal, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Ancient Debris Slag-Bismuth Alloy | `mvtink_alloy_ancient_slag_bismuth` | Infernal, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Ancient Debris Slag-Blackstone Pyrite Alloy | `mvtink_alloy_ancient_slag_blackstone_pyrite` | Infernal, Terrain, Tempered |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Ancient Debris Slag-Blazesteel Shard Alloy | `mvtink_alloy_ancient_slag_blazesteel_ore` | Infernal, Tempered, Swift |
| Borax (`mvtink_borax`) | Ancient Debris Slag-Borax Alloy | `mvtink_alloy_ancient_slag_borax` | Infernal, Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Ancient Debris Slag-Pure Calcite Alloy | `mvtink_alloy_ancient_slag_calcite_gem` | Infernal, Terrain, Tempered |
| Celestine (`mvtink_celestine`) | Ancient Debris Slag-Celestine Alloy | `mvtink_alloy_ancient_slag_celestine` | Infernal, Void, Tempered |
| Chorus Crystal (`mvtink_chorus_crystal`) | Ancient Debris Slag-Chorus Crystal Alloy | `mvtink_alloy_ancient_slag_chorus_crystal` | Infernal, Void, Tempered |
| Chromite (`mvtink_chromite`) | Ancient Debris Slag-Chromite Alloy | `mvtink_alloy_ancient_slag_chromite` | Infernal, Tempered, Brutal |
| Chrono Crystal (`mvtink_chrono_crystal`) | Ancient Debris Slag-Chrono Crystal Alloy | `mvtink_alloy_ancient_slag_chrono_crystal` | Infernal, Void, Tempered |
| Cinderite (`mvtink_cinderite`) | Ancient Debris Slag-Cinderite Alloy | `mvtink_alloy_ancient_slag_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Ancient Debris Slag-Cinnabar Alloy | `mvtink_alloy_ancient_slag_cinnabar` | Infernal, Terrain, Tempered |
| Coal (`mvtink_coal`) | Ancient Debris Slag-Coal Alloy | `mvtink_alloy_ancient_slag_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Ancient Debris Slag-Cobalt Alloy | `mvtink_alloy_ancient_slag_cobalt` | Infernal, Tempered, Swift |
| Copper (`mvtink_copper`) | Ancient Debris Slag-Copper Alloy | `mvtink_alloy_ancient_slag_copper` | Infernal, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Ancient Debris Slag-Cosmium Alloy | `mvtink_alloy_ancient_slag_cosmium` | Infernal, Void, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Ancient Debris Slag-Crimson Gold Alloy | `mvtink_alloy_ancient_slag_crimson_gold` | Infernal, Tempered, Swift |
| Crimson Quartz (`mvtink_crimson_quartz`) | Ancient Debris Slag-Crimson Quartz Alloy | `mvtink_alloy_ancient_slag_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Ancient Debris Slag-Cryolite Alloy | `mvtink_alloy_ancient_slag_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Ancient Debris Slag-Cursed Brimstone Alloy | `mvtink_alloy_ancient_slag_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Ancient Debris Slag-Diamond Alloy | `mvtink_alloy_ancient_slag_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Ancient Debris Slag-Dragon Scale Shard Alloy | `mvtink_alloy_ancient_slag_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Ancient Debris Slag-Eclipse Gem Alloy | `mvtink_alloy_ancient_slag_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Ancient Debris Slag-Emerald Alloy | `mvtink_alloy_ancient_slag_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Ancient Debris Slag-End Crystal Shard Alloy | `mvtink_alloy_ancient_slag_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Ancient Debris Slag-Enderite Alloy | `mvtink_alloy_ancient_slag_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Ancient Debris Slag-Fire Opal Alloy | `mvtink_alloy_ancient_slag_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Ancient Debris Slag-Flint Alloy | `mvtink_alloy_ancient_slag_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Ancient Debris Slag-Fluorite Alloy | `mvtink_alloy_ancient_slag_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Ancient Debris Slag-Galena Alloy | `mvtink_alloy_ancient_slag_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Ancient Debris Slag-Ghast Tear Shard Alloy | `mvtink_alloy_ancient_slag_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Ancient Debris Slag-Glowstone Gem Alloy | `mvtink_alloy_ancient_slag_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Ancient Debris Slag-Gold Alloy | `mvtink_alloy_ancient_slag_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Ancient Debris Slag-Graphite Alloy | `mvtink_alloy_ancient_slag_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Ancient Debris Slag-Gravitite Alloy | `mvtink_alloy_ancient_slag_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Ancient Debris Slag-Gypsum Alloy | `mvtink_alloy_ancient_slag_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Ancient Debris Slag-Helliron Alloy | `mvtink_alloy_ancient_slag_helliron` | Infernal, Tempered, Brutal |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Ancient Debris Slag-Ignis Ferrum Alloy | `mvtink_alloy_ancient_slag_ignis_ferrum` | Infernal, Tempered, Brutal |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Ancient Debris Slag-Infernal Obsidian Alloy | `mvtink_alloy_ancient_slag_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Ancient Debris Slag-Netherite-Infused Quartz Alloy | `mvtink_alloy_ancient_slag_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Ancient Debris Slag-Iron Alloy | `mvtink_alloy_ancient_slag_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Ancient Debris Slag-Jade Alloy | `mvtink_alloy_ancient_slag_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Ancient Debris Slag-Kaolinite Alloy | `mvtink_alloy_ancient_slag_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Ancient Debris Slag-Lapis Lazuli Alloy | `mvtink_alloy_ancient_slag_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Ancient Debris Slag-Lapis Matrix Alloy | `mvtink_alloy_ancient_slag_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Ancient Debris Slag-Magma Brimstone Alloy | `mvtink_alloy_ancient_slag_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Ancient Debris Slag-Magmacite Alloy | `mvtink_alloy_ancient_slag_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Ancient Debris Slag-Magnetite Alloy | `mvtink_alloy_ancient_slag_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Ancient Debris Slag-Malachite Alloy | `mvtink_alloy_ancient_slag_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Ancient Debris Slag-Nebulite Alloy | `mvtink_alloy_ancient_slag_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Ancient Debris Slag-Nether Bismuth Alloy | `mvtink_alloy_ancient_slag_nether_bismuth` | Infernal, Tempered, Brutal |
| Nether Tungsten (`mvtink_nether_tungsten`) | Ancient Debris Slag-Nether Tungsten Alloy | `mvtink_alloy_ancient_slag_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Ancient Debris Slag-Netherite Scrap Shard Alloy | `mvtink_alloy_ancient_slag_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Ancient Debris Slag-Nickel Alloy | `mvtink_alloy_ancient_slag_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Ancient Debris Slag-Null-Shard Alloy | `mvtink_alloy_ancient_slag_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Ancient Debris Slag-Obsidian Alloy | `mvtink_alloy_ancient_slag_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Ancient Debris Slag-Obsidianite Alloy | `mvtink_alloy_ancient_slag_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Ancient Debris Slag-Opal Alloy | `mvtink_alloy_ancient_slag_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Ancient Debris Slag-Ender Pearl Core Alloy | `mvtink_alloy_ancient_slag_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Ancient Debris Slag-Phantomite Alloy | `mvtink_alloy_ancient_slag_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Ancient Debris Slag-Platinum Alloy | `mvtink_alloy_ancient_slag_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Ancient Debris Slag-Prismarine Alloy | `mvtink_alloy_ancient_slag_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Ancient Debris Slag-Pyrite Alloy | `mvtink_alloy_ancient_slag_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Ancient Debris Slag-Pyrophore Alloy | `mvtink_alloy_ancient_slag_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Ancient Debris Slag-Nether Quartz Alloy | `mvtink_alloy_ancient_slag_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Ancient Debris Slag-Redstone Alloy | `mvtink_alloy_ancient_slag_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Ancient Debris Slag-Resonite Alloy | `mvtink_alloy_ancient_slag_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Ancient Debris Slag-Ruby Alloy | `mvtink_alloy_ancient_slag_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Ancient Debris Slag-Sanguinite Alloy | `mvtink_alloy_ancient_slag_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Ancient Debris Slag-Sapphire Alloy | `mvtink_alloy_ancient_slag_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Ancient Debris Slag-Shadowgem Alloy | `mvtink_alloy_ancient_slag_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Ancient Debris Slag-Shulkerite Alloy | `mvtink_alloy_ancient_slag_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Ancient Debris Slag-Silver Alloy | `mvtink_alloy_ancient_slag_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Ancient Debris Slag-Singularite Alloy | `mvtink_alloy_ancient_slag_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ancient Debris Slag-Soul Glass Crystal Alloy | `mvtink_alloy_ancient_slag_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ancient Debris Slag-Spatial Platinum Alloy | `mvtink_alloy_ancient_slag_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Ancient Debris Slag-Starlight Silver Alloy | `mvtink_alloy_ancient_slag_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Ancient Debris Slag-Steel Alloy | `mvtink_alloy_ancient_slag_steel` | Infernal, Tempered, Brutal |
| Stibnite (`mvtink_stibnite`) | Ancient Debris Slag-Stibnite Alloy | `mvtink_alloy_ancient_slag_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Ancient Debris Slag-Sulfur Alloy | `mvtink_alloy_ancient_slag_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Ancient Debris Slag-Talc Alloy | `mvtink_alloy_ancient_slag_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ancient Debris Slag-Tesseract Crystal Alloy | `mvtink_alloy_ancient_slag_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Ancient Debris Slag-Tin Alloy | `mvtink_alloy_ancient_slag_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Ancient Debris Slag-Titanium Alloy | `mvtink_alloy_ancient_slag_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Ancient Debris Slag-Topaz Alloy | `mvtink_alloy_ancient_slag_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Ancient Debris Slag-Tourmaline Alloy | `mvtink_alloy_ancient_slag_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Ancient Debris Slag-Tungsten Alloy | `mvtink_alloy_ancient_slag_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Ancient Debris Slag-Void Pyrite Alloy | `mvtink_alloy_ancient_slag_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Ancient Debris Slag-Void Titanium Alloy | `mvtink_alloy_ancient_slag_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Ancient Debris Slag-Voidstone Alloy | `mvtink_alloy_ancient_slag_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ancient Debris Slag-Compacted Volcanic Ash Alloy | `mvtink_alloy_ancient_slag_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Ancient Debris Slag-Warped Emerald Alloy | `mvtink_alloy_ancient_slag_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Ancient Debris Slag-Warped Quartz Alloy | `mvtink_alloy_ancient_slag_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ancient Debris Slag-Pure Weeping Shard Alloy | `mvtink_alloy_ancient_slag_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Ancient Debris Slag-Witherite Alloy | `mvtink_alloy_ancient_slag_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Ancient Debris Slag-Zero-Point Shard Alloy | `mvtink_alloy_ancient_slag_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Ancient Debris Slag-Zinc Alloy | `mvtink_alloy_ancient_slag_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Ancient Debris Slag-Zircon Alloy | `mvtink_alloy_ancient_slag_zircon` | Infernal, Terrain, Tempered |

#### Ardite · `mvtink_ardite` · 100 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Astralite (`mvtink_astralite`) | Ardite-Astralite Alloy | `mvtink_alloy_ardite_astralite` | Infernal, Void, Tempered |
| Bauxite (`mvtink_bauxite`) | Ardite-Bauxite Alloy | `mvtink_alloy_ardite_bauxite` | Infernal, Terrain, Tempered |
| Beryllium (`mvtink_beryllium`) | Ardite-Beryllium Alloy | `mvtink_alloy_ardite_beryllium` | Infernal, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Ardite-Bismuth Alloy | `mvtink_alloy_ardite_bismuth` | Infernal, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Ardite-Blackstone Pyrite Alloy | `mvtink_alloy_ardite_blackstone_pyrite` | Infernal, Terrain, Tempered |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Ardite-Blazesteel Shard Alloy | `mvtink_alloy_ardite_blazesteel_ore` | Infernal, Tempered, Swift |
| Borax (`mvtink_borax`) | Ardite-Borax Alloy | `mvtink_alloy_ardite_borax` | Infernal, Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Ardite-Pure Calcite Alloy | `mvtink_alloy_ardite_calcite_gem` | Infernal, Terrain, Tempered |
| Celestine (`mvtink_celestine`) | Ardite-Celestine Alloy | `mvtink_alloy_ardite_celestine` | Infernal, Void, Tempered |
| Chorus Crystal (`mvtink_chorus_crystal`) | Ardite-Chorus Crystal Alloy | `mvtink_alloy_ardite_chorus_crystal` | Infernal, Void, Tempered |
| Chromite (`mvtink_chromite`) | Ardite-Chromite Alloy | `mvtink_alloy_ardite_chromite` | Infernal, Tempered, Brutal |
| Chrono Crystal (`mvtink_chrono_crystal`) | Ardite-Chrono Crystal Alloy | `mvtink_alloy_ardite_chrono_crystal` | Infernal, Void, Tempered |
| Cinderite (`mvtink_cinderite`) | Ardite-Cinderite Alloy | `mvtink_alloy_ardite_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Ardite-Cinnabar Alloy | `mvtink_alloy_ardite_cinnabar` | Infernal, Terrain, Tempered |
| Coal (`mvtink_coal`) | Ardite-Coal Alloy | `mvtink_alloy_ardite_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) ★ | Manyullyn | `mvtink_manyullyn` | Infernal, Tempered, Swift |
| Copper (`mvtink_copper`) | Ardite-Copper Alloy | `mvtink_alloy_ardite_copper` | Infernal, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Ardite-Cosmium Alloy | `mvtink_alloy_ardite_cosmium` | Infernal, Void, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Ardite-Crimson Gold Alloy | `mvtink_alloy_ardite_crimson_gold` | Infernal, Tempered, Swift |
| Crimson Quartz (`mvtink_crimson_quartz`) | Ardite-Crimson Quartz Alloy | `mvtink_alloy_ardite_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Ardite-Cryolite Alloy | `mvtink_alloy_ardite_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Ardite-Cursed Brimstone Alloy | `mvtink_alloy_ardite_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Ardite-Diamond Alloy | `mvtink_alloy_ardite_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Ardite-Dragon Scale Shard Alloy | `mvtink_alloy_ardite_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Ardite-Eclipse Gem Alloy | `mvtink_alloy_ardite_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Ardite-Emerald Alloy | `mvtink_alloy_ardite_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Ardite-End Crystal Shard Alloy | `mvtink_alloy_ardite_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Ardite-Enderite Alloy | `mvtink_alloy_ardite_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Ardite-Fire Opal Alloy | `mvtink_alloy_ardite_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Ardite-Flint Alloy | `mvtink_alloy_ardite_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Ardite-Fluorite Alloy | `mvtink_alloy_ardite_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Ardite-Galena Alloy | `mvtink_alloy_ardite_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Ardite-Ghast Tear Shard Alloy | `mvtink_alloy_ardite_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Ardite-Glowstone Gem Alloy | `mvtink_alloy_ardite_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Ardite-Gold Alloy | `mvtink_alloy_ardite_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Ardite-Graphite Alloy | `mvtink_alloy_ardite_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Ardite-Gravitite Alloy | `mvtink_alloy_ardite_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Ardite-Gypsum Alloy | `mvtink_alloy_ardite_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Ardite-Helliron Alloy | `mvtink_alloy_ardite_helliron` | Infernal, Tempered, Brutal |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Ardite-Ignis Ferrum Alloy | `mvtink_alloy_ardite_ignis_ferrum` | Infernal, Tempered, Brutal |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Ardite-Infernal Obsidian Alloy | `mvtink_alloy_ardite_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Ardite-Netherite-Infused Quartz Alloy | `mvtink_alloy_ardite_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Ardite-Iron Alloy | `mvtink_alloy_ardite_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Ardite-Jade Alloy | `mvtink_alloy_ardite_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Ardite-Kaolinite Alloy | `mvtink_alloy_ardite_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Ardite-Lapis Lazuli Alloy | `mvtink_alloy_ardite_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Ardite-Lapis Matrix Alloy | `mvtink_alloy_ardite_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Ardite-Magma Brimstone Alloy | `mvtink_alloy_ardite_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Ardite-Magmacite Alloy | `mvtink_alloy_ardite_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Ardite-Magnetite Alloy | `mvtink_alloy_ardite_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Ardite-Malachite Alloy | `mvtink_alloy_ardite_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Ardite-Nebulite Alloy | `mvtink_alloy_ardite_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Ardite-Nether Bismuth Alloy | `mvtink_alloy_ardite_nether_bismuth` | Infernal, Tempered, Brutal |
| Nether Tungsten (`mvtink_nether_tungsten`) | Ardite-Nether Tungsten Alloy | `mvtink_alloy_ardite_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Ardite-Netherite Scrap Shard Alloy | `mvtink_alloy_ardite_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Ardite-Nickel Alloy | `mvtink_alloy_ardite_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Ardite-Null-Shard Alloy | `mvtink_alloy_ardite_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Ardite-Obsidian Alloy | `mvtink_alloy_ardite_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Ardite-Obsidianite Alloy | `mvtink_alloy_ardite_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Ardite-Opal Alloy | `mvtink_alloy_ardite_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Ardite-Ender Pearl Core Alloy | `mvtink_alloy_ardite_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Ardite-Phantomite Alloy | `mvtink_alloy_ardite_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Ardite-Platinum Alloy | `mvtink_alloy_ardite_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Ardite-Prismarine Alloy | `mvtink_alloy_ardite_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Ardite-Pyrite Alloy | `mvtink_alloy_ardite_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Ardite-Pyrophore Alloy | `mvtink_alloy_ardite_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Ardite-Nether Quartz Alloy | `mvtink_alloy_ardite_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Ardite-Redstone Alloy | `mvtink_alloy_ardite_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Ardite-Resonite Alloy | `mvtink_alloy_ardite_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Ardite-Ruby Alloy | `mvtink_alloy_ardite_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Ardite-Sanguinite Alloy | `mvtink_alloy_ardite_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Ardite-Sapphire Alloy | `mvtink_alloy_ardite_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Ardite-Shadowgem Alloy | `mvtink_alloy_ardite_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Ardite-Shulkerite Alloy | `mvtink_alloy_ardite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Ardite-Silver Alloy | `mvtink_alloy_ardite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Ardite-Singularite Alloy | `mvtink_alloy_ardite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ardite-Soul Glass Crystal Alloy | `mvtink_alloy_ardite_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ardite-Spatial Platinum Alloy | `mvtink_alloy_ardite_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Ardite-Starlight Silver Alloy | `mvtink_alloy_ardite_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Ardite-Steel Alloy | `mvtink_alloy_ardite_steel` | Infernal, Tempered, Brutal |
| Stibnite (`mvtink_stibnite`) | Ardite-Stibnite Alloy | `mvtink_alloy_ardite_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Ardite-Sulfur Alloy | `mvtink_alloy_ardite_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Ardite-Talc Alloy | `mvtink_alloy_ardite_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ardite-Tesseract Crystal Alloy | `mvtink_alloy_ardite_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Ardite-Tin Alloy | `mvtink_alloy_ardite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Ardite-Titanium Alloy | `mvtink_alloy_ardite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Ardite-Topaz Alloy | `mvtink_alloy_ardite_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Ardite-Tourmaline Alloy | `mvtink_alloy_ardite_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Ardite-Tungsten Alloy | `mvtink_alloy_ardite_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Ardite-Void Pyrite Alloy | `mvtink_alloy_ardite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Ardite-Void Titanium Alloy | `mvtink_alloy_ardite_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Ardite-Voidstone Alloy | `mvtink_alloy_ardite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ardite-Compacted Volcanic Ash Alloy | `mvtink_alloy_ardite_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Ardite-Warped Emerald Alloy | `mvtink_alloy_ardite_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Ardite-Warped Quartz Alloy | `mvtink_alloy_ardite_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ardite-Pure Weeping Shard Alloy | `mvtink_alloy_ardite_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Ardite-Witherite Alloy | `mvtink_alloy_ardite_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Ardite-Zero-Point Shard Alloy | `mvtink_alloy_ardite_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Ardite-Zinc Alloy | `mvtink_alloy_ardite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Ardite-Zircon Alloy | `mvtink_alloy_ardite_zircon` | Infernal, Terrain, Tempered |

#### Blackstone Pyrite · `mvtink_blackstone_pyrite` · 95 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Blackstone Pyrite-Blazesteel Shard Alloy | `mvtink_alloy_blackstone_pyrite_blazesteel_ore` | Infernal, Terrain, Tempered |
| Borax (`mvtink_borax`) | Blackstone Pyrite-Borax Alloy | `mvtink_alloy_blackstone_pyrite_borax` | Infernal, Terrain |
| Pure Calcite (`mvtink_calcite_gem`) | Blackstone Pyrite-Pure Calcite Alloy | `mvtink_alloy_blackstone_pyrite_calcite_gem` | Infernal, Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Blackstone Pyrite-Celestine Alloy | `mvtink_alloy_blackstone_pyrite_celestine` | Infernal, Void, Terrain |
| Chorus Crystal (`mvtink_chorus_crystal`) | Blackstone Pyrite-Chorus Crystal Alloy | `mvtink_alloy_blackstone_pyrite_chorus_crystal` | Infernal, Void, Terrain |
| Chromite (`mvtink_chromite`) | Blackstone Pyrite-Chromite Alloy | `mvtink_alloy_blackstone_pyrite_chromite` | Infernal, Terrain, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Blackstone Pyrite-Chrono Crystal Alloy | `mvtink_alloy_blackstone_pyrite_chrono_crystal` | Infernal, Void, Terrain |
| Cinderite (`mvtink_cinderite`) | Blackstone Pyrite-Cinderite Alloy | `mvtink_alloy_blackstone_pyrite_cinderite` | Infernal, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Blackstone Pyrite-Cinnabar Alloy | `mvtink_alloy_blackstone_pyrite_cinnabar` | Infernal, Terrain |
| Coal (`mvtink_coal`) | Blackstone Pyrite-Coal Alloy | `mvtink_alloy_blackstone_pyrite_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Blackstone Pyrite-Cobalt Alloy | `mvtink_alloy_blackstone_pyrite_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Blackstone Pyrite-Copper Alloy | `mvtink_alloy_blackstone_pyrite_copper` | Infernal, Primal, Terrain |
| Cosmium (`mvtink_cosmium`) | Blackstone Pyrite-Cosmium Alloy | `mvtink_alloy_blackstone_pyrite_cosmium` | Infernal, Void, Terrain |
| Crimson Gold (`mvtink_crimson_gold`) | Blackstone Pyrite-Crimson Gold Alloy | `mvtink_alloy_blackstone_pyrite_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Blackstone Pyrite-Crimson Quartz Alloy | `mvtink_alloy_blackstone_pyrite_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Blackstone Pyrite-Cryolite Alloy | `mvtink_alloy_blackstone_pyrite_cryolite` | Infernal, Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Blackstone Pyrite-Cursed Brimstone Alloy | `mvtink_alloy_blackstone_pyrite_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Blackstone Pyrite-Diamond Alloy | `mvtink_alloy_blackstone_pyrite_diamond` | Infernal, Primal, Terrain |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Blackstone Pyrite-Dragon Scale Shard Alloy | `mvtink_alloy_blackstone_pyrite_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Blackstone Pyrite-Eclipse Gem Alloy | `mvtink_alloy_blackstone_pyrite_eclipse_gem` | Infernal, Void, Terrain |
| Emerald (`mvtink_emerald`) | Blackstone Pyrite-Emerald Alloy | `mvtink_alloy_blackstone_pyrite_emerald` | Infernal, Primal, Terrain |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Blackstone Pyrite-End Crystal Shard Alloy | `mvtink_alloy_blackstone_pyrite_end_crystal_shard` | Infernal, Void, Terrain |
| Enderite (`mvtink_enderite`) | Blackstone Pyrite-Enderite Alloy | `mvtink_alloy_blackstone_pyrite_enderite` | Infernal, Void, Terrain |
| Fire Opal (`mvtink_fire_opal`) | Blackstone Pyrite-Fire Opal Alloy | `mvtink_alloy_blackstone_pyrite_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Blackstone Pyrite-Flint Alloy | `mvtink_alloy_blackstone_pyrite_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Blackstone Pyrite-Fluorite Alloy | `mvtink_alloy_blackstone_pyrite_fluorite` | Infernal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Blackstone Pyrite-Galena Alloy | `mvtink_alloy_blackstone_pyrite_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Blackstone Pyrite-Ghast Tear Shard Alloy | `mvtink_alloy_blackstone_pyrite_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Blackstone Pyrite-Glowstone Gem Alloy | `mvtink_alloy_blackstone_pyrite_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Blackstone Pyrite-Gold Alloy | `mvtink_alloy_blackstone_pyrite_gold` | Infernal, Primal, Terrain |
| Graphite (`mvtink_graphite`) | Blackstone Pyrite-Graphite Alloy | `mvtink_alloy_blackstone_pyrite_graphite` | Infernal, Terrain |
| Gravitite (`mvtink_gravitite`) | Blackstone Pyrite-Gravitite Alloy | `mvtink_alloy_blackstone_pyrite_gravitite` | Infernal, Void, Terrain |
| Gypsum (`mvtink_gypsum`) | Blackstone Pyrite-Gypsum Alloy | `mvtink_alloy_blackstone_pyrite_gypsum` | Infernal, Terrain |
| Helliron (`mvtink_helliron`) | Blackstone Pyrite-Helliron Alloy | `mvtink_alloy_blackstone_pyrite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Blackstone Pyrite-Ignis Ferrum Alloy | `mvtink_alloy_blackstone_pyrite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Blackstone Pyrite-Infernal Obsidian Alloy | `mvtink_alloy_blackstone_pyrite_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Blackstone Pyrite-Netherite-Infused Quartz Alloy | `mvtink_alloy_blackstone_pyrite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Blackstone Pyrite-Iron Alloy | `mvtink_alloy_blackstone_pyrite_iron` | Infernal, Primal, Terrain |
| Jade (`mvtink_jade`) | Blackstone Pyrite-Jade Alloy | `mvtink_alloy_blackstone_pyrite_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Blackstone Pyrite-Kaolinite Alloy | `mvtink_alloy_blackstone_pyrite_kaolinite` | Infernal, Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Blackstone Pyrite-Lapis Lazuli Alloy | `mvtink_alloy_blackstone_pyrite_lapis` | Infernal, Primal, Terrain |
| Lapis Matrix (`mvtink_lapis_matrix`) | Blackstone Pyrite-Lapis Matrix Alloy | `mvtink_alloy_blackstone_pyrite_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Blackstone Pyrite-Magma Brimstone Alloy | `mvtink_alloy_blackstone_pyrite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Blackstone Pyrite-Magmacite Alloy | `mvtink_alloy_blackstone_pyrite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Blackstone Pyrite-Magnetite Alloy | `mvtink_alloy_blackstone_pyrite_magnetite` | Infernal, Terrain |
| Malachite (`mvtink_malachite`) | Blackstone Pyrite-Malachite Alloy | `mvtink_alloy_blackstone_pyrite_malachite` | Infernal, Terrain |
| Nebulite (`mvtink_nebulite`) | Blackstone Pyrite-Nebulite Alloy | `mvtink_alloy_blackstone_pyrite_nebulite` | Infernal, Void, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Blackstone Pyrite-Nether Bismuth Alloy | `mvtink_alloy_blackstone_pyrite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Blackstone Pyrite-Nether Tungsten Alloy | `mvtink_alloy_blackstone_pyrite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Blackstone Pyrite-Netherite Scrap Shard Alloy | `mvtink_alloy_blackstone_pyrite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Blackstone Pyrite-Nickel Alloy | `mvtink_alloy_blackstone_pyrite_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Blackstone Pyrite-Null-Shard Alloy | `mvtink_alloy_blackstone_pyrite_null_shard` | Infernal, Void, Terrain |
| Obsidian (`mvtink_obsidian`) | Blackstone Pyrite-Obsidian Alloy | `mvtink_alloy_blackstone_pyrite_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Blackstone Pyrite-Obsidianite Alloy | `mvtink_alloy_blackstone_pyrite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Blackstone Pyrite-Opal Alloy | `mvtink_alloy_blackstone_pyrite_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Blackstone Pyrite-Ender Pearl Core Alloy | `mvtink_alloy_blackstone_pyrite_pearl_core` | Infernal, Void, Terrain |
| Phantomite (`mvtink_phantomite`) | Blackstone Pyrite-Phantomite Alloy | `mvtink_alloy_blackstone_pyrite_phantomite` | Infernal, Void, Terrain |
| Platinum (`mvtink_platinum`) | Blackstone Pyrite-Platinum Alloy | `mvtink_alloy_blackstone_pyrite_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Blackstone Pyrite-Prismarine Alloy | `mvtink_alloy_blackstone_pyrite_prismarine` | Infernal, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Blackstone Pyrite-Pyrite Alloy | `mvtink_alloy_blackstone_pyrite_pyrite` | Infernal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Blackstone Pyrite-Pyrophore Alloy | `mvtink_alloy_blackstone_pyrite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Blackstone Pyrite-Nether Quartz Alloy | `mvtink_alloy_blackstone_pyrite_quartz` | Infernal, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Blackstone Pyrite-Redstone Alloy | `mvtink_alloy_blackstone_pyrite_redstone` | Infernal, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Blackstone Pyrite-Resonite Alloy | `mvtink_alloy_blackstone_pyrite_resonite` | Infernal, Void, Terrain |
| Ruby (`mvtink_ruby`) | Blackstone Pyrite-Ruby Alloy | `mvtink_alloy_blackstone_pyrite_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Blackstone Pyrite-Sanguinite Alloy | `mvtink_alloy_blackstone_pyrite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Blackstone Pyrite-Sapphire Alloy | `mvtink_alloy_blackstone_pyrite_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Blackstone Pyrite-Shadowgem Alloy | `mvtink_alloy_blackstone_pyrite_shadowgem` | Infernal, Void, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Blackstone Pyrite-Shulkerite Alloy | `mvtink_alloy_blackstone_pyrite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Blackstone Pyrite-Silver Alloy | `mvtink_alloy_blackstone_pyrite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Blackstone Pyrite-Singularite Alloy | `mvtink_alloy_blackstone_pyrite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Blackstone Pyrite-Soul Glass Crystal Alloy | `mvtink_alloy_blackstone_pyrite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Blackstone Pyrite-Spatial Platinum Alloy | `mvtink_alloy_blackstone_pyrite_spatial_platinum` | Infernal, Void, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Blackstone Pyrite-Starlight Silver Alloy | `mvtink_alloy_blackstone_pyrite_starlight_silver` | Infernal, Void, Terrain |
| Steel (`mvtink_steel`) | Blackstone Pyrite-Steel Alloy | `mvtink_alloy_blackstone_pyrite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Blackstone Pyrite-Stibnite Alloy | `mvtink_alloy_blackstone_pyrite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Blackstone Pyrite-Sulfur Alloy | `mvtink_alloy_blackstone_pyrite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Blackstone Pyrite-Talc Alloy | `mvtink_alloy_blackstone_pyrite_talc` | Infernal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Blackstone Pyrite-Tesseract Crystal Alloy | `mvtink_alloy_blackstone_pyrite_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Blackstone Pyrite-Tin Alloy | `mvtink_alloy_blackstone_pyrite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Blackstone Pyrite-Titanium Alloy | `mvtink_alloy_blackstone_pyrite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Blackstone Pyrite-Topaz Alloy | `mvtink_alloy_blackstone_pyrite_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Blackstone Pyrite-Tourmaline Alloy | `mvtink_alloy_blackstone_pyrite_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Blackstone Pyrite-Tungsten Alloy | `mvtink_alloy_blackstone_pyrite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Blackstone Pyrite-Void Pyrite Alloy | `mvtink_alloy_blackstone_pyrite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Blackstone Pyrite-Void Titanium Alloy | `mvtink_alloy_blackstone_pyrite_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Blackstone Pyrite-Voidstone Alloy | `mvtink_alloy_blackstone_pyrite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Blackstone Pyrite-Compacted Volcanic Ash Alloy | `mvtink_alloy_blackstone_pyrite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Blackstone Pyrite-Warped Emerald Alloy | `mvtink_alloy_blackstone_pyrite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Blackstone Pyrite-Warped Quartz Alloy | `mvtink_alloy_blackstone_pyrite_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Blackstone Pyrite-Pure Weeping Shard Alloy | `mvtink_alloy_blackstone_pyrite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Blackstone Pyrite-Witherite Alloy | `mvtink_alloy_blackstone_pyrite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Blackstone Pyrite-Zero-Point Shard Alloy | `mvtink_alloy_blackstone_pyrite_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Blackstone Pyrite-Zinc Alloy | `mvtink_alloy_blackstone_pyrite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Blackstone Pyrite-Zircon Alloy | `mvtink_alloy_blackstone_pyrite_zircon` | Infernal, Terrain, Resonant |

#### Blazesteel Shard · `mvtink_blazesteel_ore` · 94 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Borax (`mvtink_borax`) | Blazesteel Shard-Borax Alloy | `mvtink_alloy_blazesteel_ore_borax` | Infernal, Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Blazesteel Shard-Pure Calcite Alloy | `mvtink_alloy_blazesteel_ore_calcite_gem` | Infernal, Terrain, Tempered |
| Celestine (`mvtink_celestine`) | Blazesteel Shard-Celestine Alloy | `mvtink_alloy_blazesteel_ore_celestine` | Infernal, Void, Tempered |
| Chorus Crystal (`mvtink_chorus_crystal`) | Blazesteel Shard-Chorus Crystal Alloy | `mvtink_alloy_blazesteel_ore_chorus_crystal` | Infernal, Void, Tempered |
| Chromite (`mvtink_chromite`) | Blazesteel Shard-Chromite Alloy | `mvtink_alloy_blazesteel_ore_chromite` | Infernal, Tempered, Swift |
| Chrono Crystal (`mvtink_chrono_crystal`) | Blazesteel Shard-Chrono Crystal Alloy | `mvtink_alloy_blazesteel_ore_chrono_crystal` | Infernal, Void, Tempered |
| Cinderite (`mvtink_cinderite`) | Blazesteel Shard-Cinderite Alloy | `mvtink_alloy_blazesteel_ore_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Blazesteel Shard-Cinnabar Alloy | `mvtink_alloy_blazesteel_ore_cinnabar` | Infernal, Terrain, Tempered |
| Coal (`mvtink_coal`) | Blazesteel Shard-Coal Alloy | `mvtink_alloy_blazesteel_ore_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Blazesteel Shard-Cobalt Alloy | `mvtink_alloy_blazesteel_ore_cobalt` | Infernal, Tempered, Swift |
| Copper (`mvtink_copper`) | Blazesteel Shard-Copper Alloy | `mvtink_alloy_blazesteel_ore_copper` | Infernal, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Blazesteel Shard-Cosmium Alloy | `mvtink_alloy_blazesteel_ore_cosmium` | Infernal, Void, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Blazesteel Shard-Crimson Gold Alloy | `mvtink_alloy_blazesteel_ore_crimson_gold` | Infernal, Tempered, Swift |
| Crimson Quartz (`mvtink_crimson_quartz`) | Blazesteel Shard-Crimson Quartz Alloy | `mvtink_alloy_blazesteel_ore_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Blazesteel Shard-Cryolite Alloy | `mvtink_alloy_blazesteel_ore_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Blazesteel Shard-Cursed Brimstone Alloy | `mvtink_alloy_blazesteel_ore_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Blazesteel Shard-Diamond Alloy | `mvtink_alloy_blazesteel_ore_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Blazesteel Shard-Dragon Scale Shard Alloy | `mvtink_alloy_blazesteel_ore_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Blazesteel Shard-Eclipse Gem Alloy | `mvtink_alloy_blazesteel_ore_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Blazesteel Shard-Emerald Alloy | `mvtink_alloy_blazesteel_ore_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Blazesteel Shard-End Crystal Shard Alloy | `mvtink_alloy_blazesteel_ore_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Blazesteel Shard-Enderite Alloy | `mvtink_alloy_blazesteel_ore_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Blazesteel Shard-Fire Opal Alloy | `mvtink_alloy_blazesteel_ore_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Blazesteel Shard-Flint Alloy | `mvtink_alloy_blazesteel_ore_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Blazesteel Shard-Fluorite Alloy | `mvtink_alloy_blazesteel_ore_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Blazesteel Shard-Galena Alloy | `mvtink_alloy_blazesteel_ore_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Blazesteel Shard-Ghast Tear Shard Alloy | `mvtink_alloy_blazesteel_ore_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Blazesteel Shard-Glowstone Gem Alloy | `mvtink_alloy_blazesteel_ore_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Blazesteel Shard-Gold Alloy | `mvtink_alloy_blazesteel_ore_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Blazesteel Shard-Graphite Alloy | `mvtink_alloy_blazesteel_ore_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Blazesteel Shard-Gravitite Alloy | `mvtink_alloy_blazesteel_ore_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Blazesteel Shard-Gypsum Alloy | `mvtink_alloy_blazesteel_ore_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Blazesteel Shard-Helliron Alloy | `mvtink_alloy_blazesteel_ore_helliron` | Infernal, Tempered, Swift |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Blazesteel Shard-Ignis Ferrum Alloy | `mvtink_alloy_blazesteel_ore_ignis_ferrum` | Infernal, Tempered, Swift |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Blazesteel Shard-Infernal Obsidian Alloy | `mvtink_alloy_blazesteel_ore_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Blazesteel Shard-Netherite-Infused Quartz Alloy | `mvtink_alloy_blazesteel_ore_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Blazesteel Shard-Iron Alloy | `mvtink_alloy_blazesteel_ore_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Blazesteel Shard-Jade Alloy | `mvtink_alloy_blazesteel_ore_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Blazesteel Shard-Kaolinite Alloy | `mvtink_alloy_blazesteel_ore_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Blazesteel Shard-Lapis Lazuli Alloy | `mvtink_alloy_blazesteel_ore_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Blazesteel Shard-Lapis Matrix Alloy | `mvtink_alloy_blazesteel_ore_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Blazesteel Shard-Magma Brimstone Alloy | `mvtink_alloy_blazesteel_ore_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Blazesteel Shard-Magmacite Alloy | `mvtink_alloy_blazesteel_ore_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Blazesteel Shard-Magnetite Alloy | `mvtink_alloy_blazesteel_ore_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Blazesteel Shard-Malachite Alloy | `mvtink_alloy_blazesteel_ore_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Blazesteel Shard-Nebulite Alloy | `mvtink_alloy_blazesteel_ore_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Blazesteel Shard-Nether Bismuth Alloy | `mvtink_alloy_blazesteel_ore_nether_bismuth` | Infernal, Tempered, Swift |
| Nether Tungsten (`mvtink_nether_tungsten`) | Blazesteel Shard-Nether Tungsten Alloy | `mvtink_alloy_blazesteel_ore_nether_tungsten` | Infernal, Tempered, Swift |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Blazesteel Shard-Netherite Scrap Shard Alloy | `mvtink_alloy_blazesteel_ore_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Blazesteel Shard-Nickel Alloy | `mvtink_alloy_blazesteel_ore_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Blazesteel Shard-Null-Shard Alloy | `mvtink_alloy_blazesteel_ore_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Blazesteel Shard-Obsidian Alloy | `mvtink_alloy_blazesteel_ore_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Blazesteel Shard-Obsidianite Alloy | `mvtink_alloy_blazesteel_ore_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Blazesteel Shard-Opal Alloy | `mvtink_alloy_blazesteel_ore_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Blazesteel Shard-Ender Pearl Core Alloy | `mvtink_alloy_blazesteel_ore_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Blazesteel Shard-Phantomite Alloy | `mvtink_alloy_blazesteel_ore_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Blazesteel Shard-Platinum Alloy | `mvtink_alloy_blazesteel_ore_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Blazesteel Shard-Prismarine Alloy | `mvtink_alloy_blazesteel_ore_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Blazesteel Shard-Pyrite Alloy | `mvtink_alloy_blazesteel_ore_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Blazesteel Shard-Pyrophore Alloy | `mvtink_alloy_blazesteel_ore_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Blazesteel Shard-Nether Quartz Alloy | `mvtink_alloy_blazesteel_ore_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Blazesteel Shard-Redstone Alloy | `mvtink_alloy_blazesteel_ore_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Blazesteel Shard-Resonite Alloy | `mvtink_alloy_blazesteel_ore_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Blazesteel Shard-Ruby Alloy | `mvtink_alloy_blazesteel_ore_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Blazesteel Shard-Sanguinite Alloy | `mvtink_alloy_blazesteel_ore_sanguinite` | Infernal, Tempered, Swift |
| Sapphire (`mvtink_sapphire`) | Blazesteel Shard-Sapphire Alloy | `mvtink_alloy_blazesteel_ore_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Blazesteel Shard-Shadowgem Alloy | `mvtink_alloy_blazesteel_ore_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Blazesteel Shard-Shulkerite Alloy | `mvtink_alloy_blazesteel_ore_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Blazesteel Shard-Silver Alloy | `mvtink_alloy_blazesteel_ore_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Blazesteel Shard-Singularite Alloy | `mvtink_alloy_blazesteel_ore_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Blazesteel Shard-Soul Glass Crystal Alloy | `mvtink_alloy_blazesteel_ore_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Blazesteel Shard-Spatial Platinum Alloy | `mvtink_alloy_blazesteel_ore_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Blazesteel Shard-Starlight Silver Alloy | `mvtink_alloy_blazesteel_ore_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Blazesteel Shard-Steel Alloy | `mvtink_alloy_blazesteel_ore_steel` | Infernal, Tempered, Swift |
| Stibnite (`mvtink_stibnite`) | Blazesteel Shard-Stibnite Alloy | `mvtink_alloy_blazesteel_ore_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Blazesteel Shard-Sulfur Alloy | `mvtink_alloy_blazesteel_ore_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Blazesteel Shard-Talc Alloy | `mvtink_alloy_blazesteel_ore_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Blazesteel Shard-Tesseract Crystal Alloy | `mvtink_alloy_blazesteel_ore_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Blazesteel Shard-Tin Alloy | `mvtink_alloy_blazesteel_ore_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Blazesteel Shard-Titanium Alloy | `mvtink_alloy_blazesteel_ore_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Blazesteel Shard-Topaz Alloy | `mvtink_alloy_blazesteel_ore_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Blazesteel Shard-Tourmaline Alloy | `mvtink_alloy_blazesteel_ore_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Blazesteel Shard-Tungsten Alloy | `mvtink_alloy_blazesteel_ore_tungsten` | Infernal, Tempered, Swift |
| Void Pyrite (`mvtink_void_pyrite`) | Blazesteel Shard-Void Pyrite Alloy | `mvtink_alloy_blazesteel_ore_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Blazesteel Shard-Void Titanium Alloy | `mvtink_alloy_blazesteel_ore_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Blazesteel Shard-Voidstone Alloy | `mvtink_alloy_blazesteel_ore_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Blazesteel Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_blazesteel_ore_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Blazesteel Shard-Warped Emerald Alloy | `mvtink_alloy_blazesteel_ore_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Blazesteel Shard-Warped Quartz Alloy | `mvtink_alloy_blazesteel_ore_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Blazesteel Shard-Pure Weeping Shard Alloy | `mvtink_alloy_blazesteel_ore_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Blazesteel Shard-Witherite Alloy | `mvtink_alloy_blazesteel_ore_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Blazesteel Shard-Zero-Point Shard Alloy | `mvtink_alloy_blazesteel_ore_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Blazesteel Shard-Zinc Alloy | `mvtink_alloy_blazesteel_ore_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Blazesteel Shard-Zircon Alloy | `mvtink_alloy_blazesteel_ore_zircon` | Infernal, Terrain, Tempered |

#### Chromite · `mvtink_chromite` · 89 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Chrono Crystal (`mvtink_chrono_crystal`) | Chromite-Chrono Crystal Alloy | `mvtink_alloy_chromite_chrono_crystal` | Infernal, Void, Tempered |
| Cinderite (`mvtink_cinderite`) | Chromite-Cinderite Alloy | `mvtink_alloy_chromite_cinderite` | Infernal, Terrain, Tempered |
| Cinnabar (`mvtink_cinnabar`) | Chromite-Cinnabar Alloy | `mvtink_alloy_chromite_cinnabar` | Infernal, Terrain, Tempered |
| Coal (`mvtink_coal`) | Chromite-Coal Alloy | `mvtink_alloy_chromite_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Chromite-Cobalt Alloy | `mvtink_alloy_chromite_cobalt` | Infernal, Tempered, Swift |
| Copper (`mvtink_copper`) | Chromite-Copper Alloy | `mvtink_alloy_chromite_copper` | Infernal, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Chromite-Cosmium Alloy | `mvtink_alloy_chromite_cosmium` | Infernal, Void, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Chromite-Crimson Gold Alloy | `mvtink_alloy_chromite_crimson_gold` | Infernal, Tempered, Swift |
| Crimson Quartz (`mvtink_crimson_quartz`) | Chromite-Crimson Quartz Alloy | `mvtink_alloy_chromite_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Chromite-Cryolite Alloy | `mvtink_alloy_chromite_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Chromite-Cursed Brimstone Alloy | `mvtink_alloy_chromite_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Chromite-Diamond Alloy | `mvtink_alloy_chromite_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Chromite-Dragon Scale Shard Alloy | `mvtink_alloy_chromite_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Chromite-Eclipse Gem Alloy | `mvtink_alloy_chromite_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Chromite-Emerald Alloy | `mvtink_alloy_chromite_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Chromite-End Crystal Shard Alloy | `mvtink_alloy_chromite_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Chromite-Enderite Alloy | `mvtink_alloy_chromite_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Chromite-Fire Opal Alloy | `mvtink_alloy_chromite_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Chromite-Flint Alloy | `mvtink_alloy_chromite_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Chromite-Fluorite Alloy | `mvtink_alloy_chromite_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Chromite-Galena Alloy | `mvtink_alloy_chromite_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Chromite-Ghast Tear Shard Alloy | `mvtink_alloy_chromite_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Chromite-Glowstone Gem Alloy | `mvtink_alloy_chromite_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Chromite-Gold Alloy | `mvtink_alloy_chromite_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Chromite-Graphite Alloy | `mvtink_alloy_chromite_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Chromite-Gravitite Alloy | `mvtink_alloy_chromite_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Chromite-Gypsum Alloy | `mvtink_alloy_chromite_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Chromite-Helliron Alloy | `mvtink_alloy_chromite_helliron` | Infernal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Chromite-Ignis Ferrum Alloy | `mvtink_alloy_chromite_ignis_ferrum` | Infernal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Chromite-Infernal Obsidian Alloy | `mvtink_alloy_chromite_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Chromite-Netherite-Infused Quartz Alloy | `mvtink_alloy_chromite_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Chromite-Iron Alloy | `mvtink_alloy_chromite_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Chromite-Jade Alloy | `mvtink_alloy_chromite_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Chromite-Kaolinite Alloy | `mvtink_alloy_chromite_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Chromite-Lapis Lazuli Alloy | `mvtink_alloy_chromite_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Chromite-Lapis Matrix Alloy | `mvtink_alloy_chromite_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Chromite-Magma Brimstone Alloy | `mvtink_alloy_chromite_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Chromite-Magmacite Alloy | `mvtink_alloy_chromite_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Chromite-Magnetite Alloy | `mvtink_alloy_chromite_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Chromite-Malachite Alloy | `mvtink_alloy_chromite_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Chromite-Nebulite Alloy | `mvtink_alloy_chromite_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Chromite-Nether Bismuth Alloy | `mvtink_alloy_chromite_nether_bismuth` | Infernal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Chromite-Nether Tungsten Alloy | `mvtink_alloy_chromite_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Chromite-Netherite Scrap Shard Alloy | `mvtink_alloy_chromite_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Chromite-Nickel Alloy | `mvtink_alloy_chromite_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Chromite-Null-Shard Alloy | `mvtink_alloy_chromite_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Chromite-Obsidian Alloy | `mvtink_alloy_chromite_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Chromite-Obsidianite Alloy | `mvtink_alloy_chromite_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Chromite-Opal Alloy | `mvtink_alloy_chromite_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Chromite-Ender Pearl Core Alloy | `mvtink_alloy_chromite_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Chromite-Phantomite Alloy | `mvtink_alloy_chromite_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Chromite-Platinum Alloy | `mvtink_alloy_chromite_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Chromite-Prismarine Alloy | `mvtink_alloy_chromite_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Chromite-Pyrite Alloy | `mvtink_alloy_chromite_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Chromite-Pyrophore Alloy | `mvtink_alloy_chromite_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Chromite-Nether Quartz Alloy | `mvtink_alloy_chromite_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Chromite-Redstone Alloy | `mvtink_alloy_chromite_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Chromite-Resonite Alloy | `mvtink_alloy_chromite_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Chromite-Ruby Alloy | `mvtink_alloy_chromite_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Chromite-Sanguinite Alloy | `mvtink_alloy_chromite_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Chromite-Sapphire Alloy | `mvtink_alloy_chromite_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Chromite-Shadowgem Alloy | `mvtink_alloy_chromite_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Chromite-Shulkerite Alloy | `mvtink_alloy_chromite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Chromite-Silver Alloy | `mvtink_alloy_chromite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Chromite-Singularite Alloy | `mvtink_alloy_chromite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Chromite-Soul Glass Crystal Alloy | `mvtink_alloy_chromite_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Chromite-Spatial Platinum Alloy | `mvtink_alloy_chromite_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Chromite-Starlight Silver Alloy | `mvtink_alloy_chromite_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Chromite-Steel Alloy | `mvtink_alloy_chromite_steel` | Infernal, Tempered |
| Stibnite (`mvtink_stibnite`) | Chromite-Stibnite Alloy | `mvtink_alloy_chromite_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Chromite-Sulfur Alloy | `mvtink_alloy_chromite_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Chromite-Talc Alloy | `mvtink_alloy_chromite_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Chromite-Tesseract Crystal Alloy | `mvtink_alloy_chromite_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Chromite-Tin Alloy | `mvtink_alloy_chromite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Chromite-Titanium Alloy | `mvtink_alloy_chromite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Chromite-Topaz Alloy | `mvtink_alloy_chromite_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Chromite-Tourmaline Alloy | `mvtink_alloy_chromite_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Chromite-Tungsten Alloy | `mvtink_alloy_chromite_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Chromite-Void Pyrite Alloy | `mvtink_alloy_chromite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Chromite-Void Titanium Alloy | `mvtink_alloy_chromite_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Chromite-Voidstone Alloy | `mvtink_alloy_chromite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Chromite-Compacted Volcanic Ash Alloy | `mvtink_alloy_chromite_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Chromite-Warped Emerald Alloy | `mvtink_alloy_chromite_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Chromite-Warped Quartz Alloy | `mvtink_alloy_chromite_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Chromite-Pure Weeping Shard Alloy | `mvtink_alloy_chromite_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Chromite-Witherite Alloy | `mvtink_alloy_chromite_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Chromite-Zero-Point Shard Alloy | `mvtink_alloy_chromite_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Chromite-Zinc Alloy | `mvtink_alloy_chromite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Chromite-Zircon Alloy | `mvtink_alloy_chromite_zircon` | Infernal, Terrain, Tempered |

#### Cinderite · `mvtink_cinderite` · 87 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cinnabar (`mvtink_cinnabar`) | Cinderite-Cinnabar Alloy | `mvtink_alloy_cinderite_cinnabar` | Infernal, Terrain |
| Coal (`mvtink_coal`) | Cinderite-Coal Alloy | `mvtink_alloy_cinderite_coal` | Infernal, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Cinderite-Cobalt Alloy | `mvtink_alloy_cinderite_cobalt` | Infernal, Terrain, Tempered |
| Copper (`mvtink_copper`) | Cinderite-Copper Alloy | `mvtink_alloy_cinderite_copper` | Infernal, Primal, Terrain |
| Cosmium (`mvtink_cosmium`) | Cinderite-Cosmium Alloy | `mvtink_alloy_cinderite_cosmium` | Infernal, Void, Terrain |
| Crimson Gold (`mvtink_crimson_gold`) | Cinderite-Crimson Gold Alloy | `mvtink_alloy_cinderite_crimson_gold` | Infernal, Terrain, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Cinderite-Crimson Quartz Alloy | `mvtink_alloy_cinderite_crimson_quartz` | Infernal, Terrain, Resonant |
| Cryolite (`mvtink_cryolite`) | Cinderite-Cryolite Alloy | `mvtink_alloy_cinderite_cryolite` | Infernal, Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Cinderite-Cursed Brimstone Alloy | `mvtink_alloy_cinderite_cursed_brimstone` | Infernal, Terrain, Volatile |
| Diamond (`mvtink_diamond`) | Cinderite-Diamond Alloy | `mvtink_alloy_cinderite_diamond` | Infernal, Primal, Terrain |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cinderite-Dragon Scale Shard Alloy | `mvtink_alloy_cinderite_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cinderite-Eclipse Gem Alloy | `mvtink_alloy_cinderite_eclipse_gem` | Infernal, Void, Terrain |
| Emerald (`mvtink_emerald`) | Cinderite-Emerald Alloy | `mvtink_alloy_cinderite_emerald` | Infernal, Primal, Terrain |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cinderite-End Crystal Shard Alloy | `mvtink_alloy_cinderite_end_crystal_shard` | Infernal, Void, Terrain |
| Enderite (`mvtink_enderite`) | Cinderite-Enderite Alloy | `mvtink_alloy_cinderite_enderite` | Infernal, Void, Terrain |
| Fire Opal (`mvtink_fire_opal`) | Cinderite-Fire Opal Alloy | `mvtink_alloy_cinderite_fire_opal` | Infernal, Terrain, Radiant |
| Flint (`mvtink_flint`) | Cinderite-Flint Alloy | `mvtink_alloy_cinderite_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cinderite-Fluorite Alloy | `mvtink_alloy_cinderite_fluorite` | Infernal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Cinderite-Galena Alloy | `mvtink_alloy_cinderite_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cinderite-Ghast Tear Shard Alloy | `mvtink_alloy_cinderite_ghast_tear_shard` | Infernal, Terrain, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cinderite-Glowstone Gem Alloy | `mvtink_alloy_cinderite_glowstone_gem` | Infernal, Terrain, Radiant |
| Gold (`mvtink_gold`) | Cinderite-Gold Alloy | `mvtink_alloy_cinderite_gold` | Infernal, Primal, Terrain |
| Graphite (`mvtink_graphite`) | Cinderite-Graphite Alloy | `mvtink_alloy_cinderite_graphite` | Infernal, Terrain |
| Gravitite (`mvtink_gravitite`) | Cinderite-Gravitite Alloy | `mvtink_alloy_cinderite_gravitite` | Infernal, Void, Terrain |
| Gypsum (`mvtink_gypsum`) | Cinderite-Gypsum Alloy | `mvtink_alloy_cinderite_gypsum` | Infernal, Terrain |
| Helliron (`mvtink_helliron`) | Cinderite-Helliron Alloy | `mvtink_alloy_cinderite_helliron` | Infernal, Terrain, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cinderite-Ignis Ferrum Alloy | `mvtink_alloy_cinderite_ignis_ferrum` | Infernal, Terrain, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cinderite-Infernal Obsidian Alloy | `mvtink_alloy_cinderite_infernal_obsidian` | Infernal, Terrain, Brutal |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cinderite-Netherite-Infused Quartz Alloy | `mvtink_alloy_cinderite_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Cinderite-Iron Alloy | `mvtink_alloy_cinderite_iron` | Infernal, Primal, Terrain |
| Jade (`mvtink_jade`) | Cinderite-Jade Alloy | `mvtink_alloy_cinderite_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Cinderite-Kaolinite Alloy | `mvtink_alloy_cinderite_kaolinite` | Infernal, Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Cinderite-Lapis Lazuli Alloy | `mvtink_alloy_cinderite_lapis` | Infernal, Primal, Terrain |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cinderite-Lapis Matrix Alloy | `mvtink_alloy_cinderite_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cinderite-Magma Brimstone Alloy | `mvtink_alloy_cinderite_magma_brimstone` | Infernal, Terrain |
| Magmacite (`mvtink_magmacite`) | Cinderite-Magmacite Alloy | `mvtink_alloy_cinderite_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Cinderite-Magnetite Alloy | `mvtink_alloy_cinderite_magnetite` | Infernal, Terrain |
| Malachite (`mvtink_malachite`) | Cinderite-Malachite Alloy | `mvtink_alloy_cinderite_malachite` | Infernal, Terrain |
| Nebulite (`mvtink_nebulite`) | Cinderite-Nebulite Alloy | `mvtink_alloy_cinderite_nebulite` | Infernal, Void, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cinderite-Nether Bismuth Alloy | `mvtink_alloy_cinderite_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cinderite-Nether Tungsten Alloy | `mvtink_alloy_cinderite_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cinderite-Netherite Scrap Shard Alloy | `mvtink_alloy_cinderite_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Cinderite-Nickel Alloy | `mvtink_alloy_cinderite_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cinderite-Null-Shard Alloy | `mvtink_alloy_cinderite_null_shard` | Infernal, Void, Terrain |
| Obsidian (`mvtink_obsidian`) | Cinderite-Obsidian Alloy | `mvtink_alloy_cinderite_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Cinderite-Obsidianite Alloy | `mvtink_alloy_cinderite_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Cinderite-Opal Alloy | `mvtink_alloy_cinderite_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Cinderite-Ender Pearl Core Alloy | `mvtink_alloy_cinderite_pearl_core` | Infernal, Void, Terrain |
| Phantomite (`mvtink_phantomite`) | Cinderite-Phantomite Alloy | `mvtink_alloy_cinderite_phantomite` | Infernal, Void, Terrain |
| Platinum (`mvtink_platinum`) | Cinderite-Platinum Alloy | `mvtink_alloy_cinderite_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Cinderite-Prismarine Alloy | `mvtink_alloy_cinderite_prismarine` | Infernal, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Cinderite-Pyrite Alloy | `mvtink_alloy_cinderite_pyrite` | Infernal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Cinderite-Pyrophore Alloy | `mvtink_alloy_cinderite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Cinderite-Nether Quartz Alloy | `mvtink_alloy_cinderite_quartz` | Infernal, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Cinderite-Redstone Alloy | `mvtink_alloy_cinderite_redstone` | Infernal, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Cinderite-Resonite Alloy | `mvtink_alloy_cinderite_resonite` | Infernal, Void, Terrain |
| Ruby (`mvtink_ruby`) | Cinderite-Ruby Alloy | `mvtink_alloy_cinderite_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Cinderite-Sanguinite Alloy | `mvtink_alloy_cinderite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Cinderite-Sapphire Alloy | `mvtink_alloy_cinderite_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Cinderite-Shadowgem Alloy | `mvtink_alloy_cinderite_shadowgem` | Infernal, Void, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Cinderite-Shulkerite Alloy | `mvtink_alloy_cinderite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Cinderite-Silver Alloy | `mvtink_alloy_cinderite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cinderite-Singularite Alloy | `mvtink_alloy_cinderite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cinderite-Soul Glass Crystal Alloy | `mvtink_alloy_cinderite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cinderite-Spatial Platinum Alloy | `mvtink_alloy_cinderite_spatial_platinum` | Infernal, Void, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Cinderite-Starlight Silver Alloy | `mvtink_alloy_cinderite_starlight_silver` | Infernal, Void, Terrain |
| Steel (`mvtink_steel`) | Cinderite-Steel Alloy | `mvtink_alloy_cinderite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Cinderite-Stibnite Alloy | `mvtink_alloy_cinderite_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Cinderite-Sulfur Alloy | `mvtink_alloy_cinderite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Cinderite-Talc Alloy | `mvtink_alloy_cinderite_talc` | Infernal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cinderite-Tesseract Crystal Alloy | `mvtink_alloy_cinderite_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Cinderite-Tin Alloy | `mvtink_alloy_cinderite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cinderite-Titanium Alloy | `mvtink_alloy_cinderite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Cinderite-Topaz Alloy | `mvtink_alloy_cinderite_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Cinderite-Tourmaline Alloy | `mvtink_alloy_cinderite_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Cinderite-Tungsten Alloy | `mvtink_alloy_cinderite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Cinderite-Void Pyrite Alloy | `mvtink_alloy_cinderite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Cinderite-Void Titanium Alloy | `mvtink_alloy_cinderite_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Cinderite-Voidstone Alloy | `mvtink_alloy_cinderite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cinderite-Compacted Volcanic Ash Alloy | `mvtink_alloy_cinderite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Cinderite-Warped Emerald Alloy | `mvtink_alloy_cinderite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Cinderite-Warped Quartz Alloy | `mvtink_alloy_cinderite_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cinderite-Pure Weeping Shard Alloy | `mvtink_alloy_cinderite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Cinderite-Witherite Alloy | `mvtink_alloy_cinderite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Cinderite-Zero-Point Shard Alloy | `mvtink_alloy_cinderite_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Cinderite-Zinc Alloy | `mvtink_alloy_cinderite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cinderite-Zircon Alloy | `mvtink_alloy_cinderite_zircon` | Infernal, Terrain, Resonant |

#### Cobalt · `mvtink_cobalt` · 84 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Copper (`mvtink_copper`) | Cobalt-Copper Alloy | `mvtink_alloy_cobalt_copper` | Infernal, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Cobalt-Cosmium Alloy | `mvtink_alloy_cobalt_cosmium` | Infernal, Void, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Cobalt-Crimson Gold Alloy | `mvtink_alloy_cobalt_crimson_gold` | Infernal, Tempered, Swift |
| Crimson Quartz (`mvtink_crimson_quartz`) | Cobalt-Crimson Quartz Alloy | `mvtink_alloy_cobalt_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Cobalt-Cryolite Alloy | `mvtink_alloy_cobalt_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Cobalt-Cursed Brimstone Alloy | `mvtink_alloy_cobalt_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Cobalt-Diamond Alloy | `mvtink_alloy_cobalt_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cobalt-Dragon Scale Shard Alloy | `mvtink_alloy_cobalt_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cobalt-Eclipse Gem Alloy | `mvtink_alloy_cobalt_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Cobalt-Emerald Alloy | `mvtink_alloy_cobalt_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cobalt-End Crystal Shard Alloy | `mvtink_alloy_cobalt_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Cobalt-Enderite Alloy | `mvtink_alloy_cobalt_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Cobalt-Fire Opal Alloy | `mvtink_alloy_cobalt_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Cobalt-Flint Alloy | `mvtink_alloy_cobalt_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cobalt-Fluorite Alloy | `mvtink_alloy_cobalt_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Cobalt-Galena Alloy | `mvtink_alloy_cobalt_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cobalt-Ghast Tear Shard Alloy | `mvtink_alloy_cobalt_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cobalt-Glowstone Gem Alloy | `mvtink_alloy_cobalt_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Cobalt-Gold Alloy | `mvtink_alloy_cobalt_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Cobalt-Graphite Alloy | `mvtink_alloy_cobalt_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Cobalt-Gravitite Alloy | `mvtink_alloy_cobalt_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Cobalt-Gypsum Alloy | `mvtink_alloy_cobalt_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Cobalt-Helliron Alloy | `mvtink_alloy_cobalt_helliron` | Infernal, Tempered, Swift |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cobalt-Ignis Ferrum Alloy | `mvtink_alloy_cobalt_ignis_ferrum` | Infernal, Tempered, Swift |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cobalt-Infernal Obsidian Alloy | `mvtink_alloy_cobalt_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cobalt-Netherite-Infused Quartz Alloy | `mvtink_alloy_cobalt_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Cobalt-Iron Alloy | `mvtink_alloy_cobalt_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Cobalt-Jade Alloy | `mvtink_alloy_cobalt_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Cobalt-Kaolinite Alloy | `mvtink_alloy_cobalt_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Cobalt-Lapis Lazuli Alloy | `mvtink_alloy_cobalt_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cobalt-Lapis Matrix Alloy | `mvtink_alloy_cobalt_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cobalt-Magma Brimstone Alloy | `mvtink_alloy_cobalt_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Cobalt-Magmacite Alloy | `mvtink_alloy_cobalt_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Cobalt-Magnetite Alloy | `mvtink_alloy_cobalt_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Cobalt-Malachite Alloy | `mvtink_alloy_cobalt_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Cobalt-Nebulite Alloy | `mvtink_alloy_cobalt_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cobalt-Nether Bismuth Alloy | `mvtink_alloy_cobalt_nether_bismuth` | Infernal, Tempered, Swift |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cobalt-Nether Tungsten Alloy | `mvtink_alloy_cobalt_nether_tungsten` | Infernal, Tempered, Swift |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cobalt-Netherite Scrap Shard Alloy | `mvtink_alloy_cobalt_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Cobalt-Nickel Alloy | `mvtink_alloy_cobalt_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cobalt-Null-Shard Alloy | `mvtink_alloy_cobalt_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Cobalt-Obsidian Alloy | `mvtink_alloy_cobalt_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Cobalt-Obsidianite Alloy | `mvtink_alloy_cobalt_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Cobalt-Opal Alloy | `mvtink_alloy_cobalt_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Cobalt-Ender Pearl Core Alloy | `mvtink_alloy_cobalt_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Cobalt-Phantomite Alloy | `mvtink_alloy_cobalt_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Cobalt-Platinum Alloy | `mvtink_alloy_cobalt_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Cobalt-Prismarine Alloy | `mvtink_alloy_cobalt_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Cobalt-Pyrite Alloy | `mvtink_alloy_cobalt_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Cobalt-Pyrophore Alloy | `mvtink_alloy_cobalt_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Cobalt-Nether Quartz Alloy | `mvtink_alloy_cobalt_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Cobalt-Redstone Alloy | `mvtink_alloy_cobalt_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Cobalt-Resonite Alloy | `mvtink_alloy_cobalt_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Cobalt-Ruby Alloy | `mvtink_alloy_cobalt_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Cobalt-Sanguinite Alloy | `mvtink_alloy_cobalt_sanguinite` | Infernal, Tempered, Swift |
| Sapphire (`mvtink_sapphire`) | Cobalt-Sapphire Alloy | `mvtink_alloy_cobalt_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Cobalt-Shadowgem Alloy | `mvtink_alloy_cobalt_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Cobalt-Shulkerite Alloy | `mvtink_alloy_cobalt_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Cobalt-Silver Alloy | `mvtink_alloy_cobalt_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cobalt-Singularite Alloy | `mvtink_alloy_cobalt_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cobalt-Soul Glass Crystal Alloy | `mvtink_alloy_cobalt_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cobalt-Spatial Platinum Alloy | `mvtink_alloy_cobalt_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Cobalt-Starlight Silver Alloy | `mvtink_alloy_cobalt_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Cobalt-Steel Alloy | `mvtink_alloy_cobalt_steel` | Infernal, Tempered, Swift |
| Stibnite (`mvtink_stibnite`) | Cobalt-Stibnite Alloy | `mvtink_alloy_cobalt_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Cobalt-Sulfur Alloy | `mvtink_alloy_cobalt_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Cobalt-Talc Alloy | `mvtink_alloy_cobalt_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cobalt-Tesseract Crystal Alloy | `mvtink_alloy_cobalt_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Cobalt-Tin Alloy | `mvtink_alloy_cobalt_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cobalt-Titanium Alloy | `mvtink_alloy_cobalt_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Cobalt-Topaz Alloy | `mvtink_alloy_cobalt_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Cobalt-Tourmaline Alloy | `mvtink_alloy_cobalt_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Cobalt-Tungsten Alloy | `mvtink_alloy_cobalt_tungsten` | Infernal, Tempered, Swift |
| Void Pyrite (`mvtink_void_pyrite`) | Cobalt-Void Pyrite Alloy | `mvtink_alloy_cobalt_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Cobalt-Void Titanium Alloy | `mvtink_alloy_cobalt_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Cobalt-Voidstone Alloy | `mvtink_alloy_cobalt_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cobalt-Compacted Volcanic Ash Alloy | `mvtink_alloy_cobalt_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Cobalt-Warped Emerald Alloy | `mvtink_alloy_cobalt_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Cobalt-Warped Quartz Alloy | `mvtink_alloy_cobalt_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cobalt-Pure Weeping Shard Alloy | `mvtink_alloy_cobalt_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Cobalt-Witherite Alloy | `mvtink_alloy_cobalt_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Cobalt-Zero-Point Shard Alloy | `mvtink_alloy_cobalt_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Cobalt-Zinc Alloy | `mvtink_alloy_cobalt_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cobalt-Zircon Alloy | `mvtink_alloy_cobalt_zircon` | Infernal, Terrain, Tempered |

#### Crimson Gold · `mvtink_crimson_gold` · 81 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Crimson Quartz (`mvtink_crimson_quartz`) | Crimson Gold-Crimson Quartz Alloy | `mvtink_alloy_crimson_gold_crimson_quartz` | Infernal, Tempered, Resonant |
| Cryolite (`mvtink_cryolite`) | Crimson Gold-Cryolite Alloy | `mvtink_alloy_crimson_gold_cryolite` | Infernal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Crimson Gold-Cursed Brimstone Alloy | `mvtink_alloy_crimson_gold_cursed_brimstone` | Infernal, Tempered, Volatile |
| Diamond (`mvtink_diamond`) | Crimson Gold-Diamond Alloy | `mvtink_alloy_crimson_gold_diamond` | Infernal, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Crimson Gold-Dragon Scale Shard Alloy | `mvtink_alloy_crimson_gold_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Crimson Gold-Eclipse Gem Alloy | `mvtink_alloy_crimson_gold_eclipse_gem` | Infernal, Void, Tempered |
| Emerald (`mvtink_emerald`) | Crimson Gold-Emerald Alloy | `mvtink_alloy_crimson_gold_emerald` | Infernal, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Crimson Gold-End Crystal Shard Alloy | `mvtink_alloy_crimson_gold_end_crystal_shard` | Infernal, Void, Tempered |
| Enderite (`mvtink_enderite`) | Crimson Gold-Enderite Alloy | `mvtink_alloy_crimson_gold_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Crimson Gold-Fire Opal Alloy | `mvtink_alloy_crimson_gold_fire_opal` | Infernal, Tempered, Radiant |
| Flint (`mvtink_flint`) | Crimson Gold-Flint Alloy | `mvtink_alloy_crimson_gold_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Crimson Gold-Fluorite Alloy | `mvtink_alloy_crimson_gold_fluorite` | Infernal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Crimson Gold-Galena Alloy | `mvtink_alloy_crimson_gold_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Crimson Gold-Ghast Tear Shard Alloy | `mvtink_alloy_crimson_gold_ghast_tear_shard` | Infernal, Tempered, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Crimson Gold-Glowstone Gem Alloy | `mvtink_alloy_crimson_gold_glowstone_gem` | Infernal, Tempered, Radiant |
| Gold (`mvtink_gold`) | Crimson Gold-Gold Alloy | `mvtink_alloy_crimson_gold_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Crimson Gold-Graphite Alloy | `mvtink_alloy_crimson_gold_graphite` | Infernal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Crimson Gold-Gravitite Alloy | `mvtink_alloy_crimson_gold_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Crimson Gold-Gypsum Alloy | `mvtink_alloy_crimson_gold_gypsum` | Infernal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Crimson Gold-Helliron Alloy | `mvtink_alloy_crimson_gold_helliron` | Infernal, Tempered, Swift |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Crimson Gold-Ignis Ferrum Alloy | `mvtink_alloy_crimson_gold_ignis_ferrum` | Infernal, Tempered, Swift |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Crimson Gold-Infernal Obsidian Alloy | `mvtink_alloy_crimson_gold_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Crimson Gold-Netherite-Infused Quartz Alloy | `mvtink_alloy_crimson_gold_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Crimson Gold-Iron Alloy | `mvtink_alloy_crimson_gold_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Crimson Gold-Jade Alloy | `mvtink_alloy_crimson_gold_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Crimson Gold-Kaolinite Alloy | `mvtink_alloy_crimson_gold_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Crimson Gold-Lapis Lazuli Alloy | `mvtink_alloy_crimson_gold_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Crimson Gold-Lapis Matrix Alloy | `mvtink_alloy_crimson_gold_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Crimson Gold-Magma Brimstone Alloy | `mvtink_alloy_crimson_gold_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Crimson Gold-Magmacite Alloy | `mvtink_alloy_crimson_gold_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Crimson Gold-Magnetite Alloy | `mvtink_alloy_crimson_gold_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Crimson Gold-Malachite Alloy | `mvtink_alloy_crimson_gold_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Crimson Gold-Nebulite Alloy | `mvtink_alloy_crimson_gold_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Crimson Gold-Nether Bismuth Alloy | `mvtink_alloy_crimson_gold_nether_bismuth` | Infernal, Tempered, Swift |
| Nether Tungsten (`mvtink_nether_tungsten`) | Crimson Gold-Nether Tungsten Alloy | `mvtink_alloy_crimson_gold_nether_tungsten` | Infernal, Tempered, Swift |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Crimson Gold-Netherite Scrap Shard Alloy | `mvtink_alloy_crimson_gold_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Crimson Gold-Nickel Alloy | `mvtink_alloy_crimson_gold_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Crimson Gold-Null-Shard Alloy | `mvtink_alloy_crimson_gold_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Crimson Gold-Obsidian Alloy | `mvtink_alloy_crimson_gold_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Crimson Gold-Obsidianite Alloy | `mvtink_alloy_crimson_gold_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Crimson Gold-Opal Alloy | `mvtink_alloy_crimson_gold_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Crimson Gold-Ender Pearl Core Alloy | `mvtink_alloy_crimson_gold_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Crimson Gold-Phantomite Alloy | `mvtink_alloy_crimson_gold_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Crimson Gold-Platinum Alloy | `mvtink_alloy_crimson_gold_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Crimson Gold-Prismarine Alloy | `mvtink_alloy_crimson_gold_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Crimson Gold-Pyrite Alloy | `mvtink_alloy_crimson_gold_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Crimson Gold-Pyrophore Alloy | `mvtink_alloy_crimson_gold_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Crimson Gold-Nether Quartz Alloy | `mvtink_alloy_crimson_gold_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Crimson Gold-Redstone Alloy | `mvtink_alloy_crimson_gold_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Crimson Gold-Resonite Alloy | `mvtink_alloy_crimson_gold_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Crimson Gold-Ruby Alloy | `mvtink_alloy_crimson_gold_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Crimson Gold-Sanguinite Alloy | `mvtink_alloy_crimson_gold_sanguinite` | Infernal, Tempered, Swift |
| Sapphire (`mvtink_sapphire`) | Crimson Gold-Sapphire Alloy | `mvtink_alloy_crimson_gold_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Crimson Gold-Shadowgem Alloy | `mvtink_alloy_crimson_gold_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Crimson Gold-Shulkerite Alloy | `mvtink_alloy_crimson_gold_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Crimson Gold-Silver Alloy | `mvtink_alloy_crimson_gold_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Crimson Gold-Singularite Alloy | `mvtink_alloy_crimson_gold_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Crimson Gold-Soul Glass Crystal Alloy | `mvtink_alloy_crimson_gold_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Crimson Gold-Spatial Platinum Alloy | `mvtink_alloy_crimson_gold_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Crimson Gold-Starlight Silver Alloy | `mvtink_alloy_crimson_gold_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Crimson Gold-Steel Alloy | `mvtink_alloy_crimson_gold_steel` | Infernal, Tempered, Swift |
| Stibnite (`mvtink_stibnite`) | Crimson Gold-Stibnite Alloy | `mvtink_alloy_crimson_gold_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Crimson Gold-Sulfur Alloy | `mvtink_alloy_crimson_gold_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Crimson Gold-Talc Alloy | `mvtink_alloy_crimson_gold_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Crimson Gold-Tesseract Crystal Alloy | `mvtink_alloy_crimson_gold_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Crimson Gold-Tin Alloy | `mvtink_alloy_crimson_gold_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Crimson Gold-Titanium Alloy | `mvtink_alloy_crimson_gold_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Crimson Gold-Topaz Alloy | `mvtink_alloy_crimson_gold_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Crimson Gold-Tourmaline Alloy | `mvtink_alloy_crimson_gold_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Crimson Gold-Tungsten Alloy | `mvtink_alloy_crimson_gold_tungsten` | Infernal, Tempered, Swift |
| Void Pyrite (`mvtink_void_pyrite`) | Crimson Gold-Void Pyrite Alloy | `mvtink_alloy_crimson_gold_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Crimson Gold-Void Titanium Alloy | `mvtink_alloy_crimson_gold_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Crimson Gold-Voidstone Alloy | `mvtink_alloy_crimson_gold_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Crimson Gold-Compacted Volcanic Ash Alloy | `mvtink_alloy_crimson_gold_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Crimson Gold-Warped Emerald Alloy | `mvtink_alloy_crimson_gold_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Crimson Gold-Warped Quartz Alloy | `mvtink_alloy_crimson_gold_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Crimson Gold-Pure Weeping Shard Alloy | `mvtink_alloy_crimson_gold_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Crimson Gold-Witherite Alloy | `mvtink_alloy_crimson_gold_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Crimson Gold-Zero-Point Shard Alloy | `mvtink_alloy_crimson_gold_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Crimson Gold-Zinc Alloy | `mvtink_alloy_crimson_gold_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Crimson Gold-Zircon Alloy | `mvtink_alloy_crimson_gold_zircon` | Infernal, Terrain, Tempered |

#### Crimson Quartz · `mvtink_crimson_quartz` · 80 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cryolite (`mvtink_cryolite`) | Crimson Quartz-Cryolite Alloy | `mvtink_alloy_crimson_quartz_cryolite` | Infernal, Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Crimson Quartz-Cursed Brimstone Alloy | `mvtink_alloy_crimson_quartz_cursed_brimstone` | Infernal, Resonant, Volatile |
| Diamond (`mvtink_diamond`) | Crimson Quartz-Diamond Alloy | `mvtink_alloy_crimson_quartz_diamond` | Infernal, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Crimson Quartz-Dragon Scale Shard Alloy | `mvtink_alloy_crimson_quartz_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Crimson Quartz-Eclipse Gem Alloy | `mvtink_alloy_crimson_quartz_eclipse_gem` | Infernal, Void, Radiant |
| Emerald (`mvtink_emerald`) | Crimson Quartz-Emerald Alloy | `mvtink_alloy_crimson_quartz_emerald` | Infernal, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Crimson Quartz-End Crystal Shard Alloy | `mvtink_alloy_crimson_quartz_end_crystal_shard` | Infernal, Void, Resonant |
| Enderite (`mvtink_enderite`) | Crimson Quartz-Enderite Alloy | `mvtink_alloy_crimson_quartz_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Crimson Quartz-Fire Opal Alloy | `mvtink_alloy_crimson_quartz_fire_opal` | Infernal, Radiant, Resonant |
| Flint (`mvtink_flint`) | Crimson Quartz-Flint Alloy | `mvtink_alloy_crimson_quartz_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Crimson Quartz-Fluorite Alloy | `mvtink_alloy_crimson_quartz_fluorite` | Infernal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Crimson Quartz-Galena Alloy | `mvtink_alloy_crimson_quartz_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Crimson Quartz-Ghast Tear Shard Alloy | `mvtink_alloy_crimson_quartz_ghast_tear_shard` | Infernal, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Crimson Quartz-Glowstone Gem Alloy | `mvtink_alloy_crimson_quartz_glowstone_gem` | Infernal, Radiant, Resonant |
| Gold (`mvtink_gold`) | Crimson Quartz-Gold Alloy | `mvtink_alloy_crimson_quartz_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Crimson Quartz-Graphite Alloy | `mvtink_alloy_crimson_quartz_graphite` | Infernal, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Crimson Quartz-Gravitite Alloy | `mvtink_alloy_crimson_quartz_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Crimson Quartz-Gypsum Alloy | `mvtink_alloy_crimson_quartz_gypsum` | Infernal, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Crimson Quartz-Helliron Alloy | `mvtink_alloy_crimson_quartz_helliron` | Infernal, Tempered, Resonant |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Crimson Quartz-Ignis Ferrum Alloy | `mvtink_alloy_crimson_quartz_ignis_ferrum` | Infernal, Tempered, Resonant |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Crimson Quartz-Infernal Obsidian Alloy | `mvtink_alloy_crimson_quartz_infernal_obsidian` | Infernal, Terrain, Resonant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Crimson Quartz-Netherite-Infused Quartz Alloy | `mvtink_alloy_crimson_quartz_infused_quartz` | Infernal, Resonant, Swift |
| Iron (`mvtink_iron`) | Crimson Quartz-Iron Alloy | `mvtink_alloy_crimson_quartz_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Crimson Quartz-Jade Alloy | `mvtink_alloy_crimson_quartz_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Crimson Quartz-Kaolinite Alloy | `mvtink_alloy_crimson_quartz_kaolinite` | Infernal, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Crimson Quartz-Lapis Lazuli Alloy | `mvtink_alloy_crimson_quartz_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Crimson Quartz-Lapis Matrix Alloy | `mvtink_alloy_crimson_quartz_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Crimson Quartz-Magma Brimstone Alloy | `mvtink_alloy_crimson_quartz_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Crimson Quartz-Magmacite Alloy | `mvtink_alloy_crimson_quartz_magmacite` | Infernal, Resonant, Brutal |
| Magnetite (`mvtink_magnetite`) | Crimson Quartz-Magnetite Alloy | `mvtink_alloy_crimson_quartz_magnetite` | Infernal, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Crimson Quartz-Malachite Alloy | `mvtink_alloy_crimson_quartz_malachite` | Infernal, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Crimson Quartz-Nebulite Alloy | `mvtink_alloy_crimson_quartz_nebulite` | Infernal, Void, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Crimson Quartz-Nether Bismuth Alloy | `mvtink_alloy_crimson_quartz_nether_bismuth` | Infernal, Tempered, Resonant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Crimson Quartz-Nether Tungsten Alloy | `mvtink_alloy_crimson_quartz_nether_tungsten` | Infernal, Tempered, Resonant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Crimson Quartz-Netherite Scrap Shard Alloy | `mvtink_alloy_crimson_quartz_netherite_shard` | Infernal, Tempered, Resonant |
| Nickel (`mvtink_nickel`) | Crimson Quartz-Nickel Alloy | `mvtink_alloy_crimson_quartz_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Crimson Quartz-Null-Shard Alloy | `mvtink_alloy_crimson_quartz_null_shard` | Infernal, Void, Resonant |
| Obsidian (`mvtink_obsidian`) | Crimson Quartz-Obsidian Alloy | `mvtink_alloy_crimson_quartz_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Crimson Quartz-Obsidianite Alloy | `mvtink_alloy_crimson_quartz_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Crimson Quartz-Opal Alloy | `mvtink_alloy_crimson_quartz_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Crimson Quartz-Ender Pearl Core Alloy | `mvtink_alloy_crimson_quartz_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Crimson Quartz-Phantomite Alloy | `mvtink_alloy_crimson_quartz_phantomite` | Infernal, Void, Resonant |
| Platinum (`mvtink_platinum`) | Crimson Quartz-Platinum Alloy | `mvtink_alloy_crimson_quartz_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Crimson Quartz-Prismarine Alloy | `mvtink_alloy_crimson_quartz_prismarine` | Infernal, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Crimson Quartz-Pyrite Alloy | `mvtink_alloy_crimson_quartz_pyrite` | Infernal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Crimson Quartz-Pyrophore Alloy | `mvtink_alloy_crimson_quartz_pyrophore` | Infernal, Resonant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Crimson Quartz-Nether Quartz Alloy | `mvtink_alloy_crimson_quartz_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Crimson Quartz-Redstone Alloy | `mvtink_alloy_crimson_quartz_redstone` | Infernal, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Crimson Quartz-Resonite Alloy | `mvtink_alloy_crimson_quartz_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Crimson Quartz-Ruby Alloy | `mvtink_alloy_crimson_quartz_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Crimson Quartz-Sanguinite Alloy | `mvtink_alloy_crimson_quartz_sanguinite` | Infernal, Tempered, Resonant |
| Sapphire (`mvtink_sapphire`) | Crimson Quartz-Sapphire Alloy | `mvtink_alloy_crimson_quartz_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Crimson Quartz-Shadowgem Alloy | `mvtink_alloy_crimson_quartz_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Crimson Quartz-Shulkerite Alloy | `mvtink_alloy_crimson_quartz_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Crimson Quartz-Silver Alloy | `mvtink_alloy_crimson_quartz_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Crimson Quartz-Singularite Alloy | `mvtink_alloy_crimson_quartz_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Crimson Quartz-Soul Glass Crystal Alloy | `mvtink_alloy_crimson_quartz_soulsand_crystal` | Infernal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Crimson Quartz-Spatial Platinum Alloy | `mvtink_alloy_crimson_quartz_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Crimson Quartz-Starlight Silver Alloy | `mvtink_alloy_crimson_quartz_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Crimson Quartz-Steel Alloy | `mvtink_alloy_crimson_quartz_steel` | Infernal, Tempered, Resonant |
| Stibnite (`mvtink_stibnite`) | Crimson Quartz-Stibnite Alloy | `mvtink_alloy_crimson_quartz_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Crimson Quartz-Sulfur Alloy | `mvtink_alloy_crimson_quartz_sulfur` | Infernal, Resonant, Volatile |
| Talc (`mvtink_talc`) | Crimson Quartz-Talc Alloy | `mvtink_alloy_crimson_quartz_talc` | Infernal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Crimson Quartz-Tesseract Crystal Alloy | `mvtink_alloy_crimson_quartz_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Crimson Quartz-Tin Alloy | `mvtink_alloy_crimson_quartz_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Crimson Quartz-Titanium Alloy | `mvtink_alloy_crimson_quartz_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Crimson Quartz-Topaz Alloy | `mvtink_alloy_crimson_quartz_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Crimson Quartz-Tourmaline Alloy | `mvtink_alloy_crimson_quartz_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Crimson Quartz-Tungsten Alloy | `mvtink_alloy_crimson_quartz_tungsten` | Infernal, Tempered, Resonant |
| Void Pyrite (`mvtink_void_pyrite`) | Crimson Quartz-Void Pyrite Alloy | `mvtink_alloy_crimson_quartz_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Crimson Quartz-Void Titanium Alloy | `mvtink_alloy_crimson_quartz_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Crimson Quartz-Voidstone Alloy | `mvtink_alloy_crimson_quartz_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Crimson Quartz-Compacted Volcanic Ash Alloy | `mvtink_alloy_crimson_quartz_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Crimson Quartz-Warped Emerald Alloy | `mvtink_alloy_crimson_quartz_warped_emerald` | Infernal, Radiant, Resonant |
| Warped Quartz (`mvtink_warped_quartz`) | Crimson Quartz-Warped Quartz Alloy | `mvtink_alloy_crimson_quartz_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Crimson Quartz-Pure Weeping Shard Alloy | `mvtink_alloy_crimson_quartz_weeping_shard` | Infernal, Resonant, Brutal |
| Witherite (`mvtink_witherite`) | Crimson Quartz-Witherite Alloy | `mvtink_alloy_crimson_quartz_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Crimson Quartz-Zero-Point Shard Alloy | `mvtink_alloy_crimson_quartz_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Crimson Quartz-Zinc Alloy | `mvtink_alloy_crimson_quartz_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Crimson Quartz-Zircon Alloy | `mvtink_alloy_crimson_quartz_zircon` | Infernal, Terrain, Resonant |

#### Cursed Brimstone · `mvtink_cursed_brimstone` · 78 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Diamond (`mvtink_diamond`) | Cursed Brimstone-Diamond Alloy | `mvtink_alloy_cursed_brimstone_diamond` | Infernal, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cursed Brimstone-Dragon Scale Shard Alloy | `mvtink_alloy_cursed_brimstone_dragon_shard` | Infernal, Void, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cursed Brimstone-Eclipse Gem Alloy | `mvtink_alloy_cursed_brimstone_eclipse_gem` | Infernal, Void, Radiant |
| Emerald (`mvtink_emerald`) | Cursed Brimstone-Emerald Alloy | `mvtink_alloy_cursed_brimstone_emerald` | Infernal, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cursed Brimstone-End Crystal Shard Alloy | `mvtink_alloy_cursed_brimstone_end_crystal_shard` | Infernal, Void, Resonant |
| Enderite (`mvtink_enderite`) | Cursed Brimstone-Enderite Alloy | `mvtink_alloy_cursed_brimstone_enderite` | Infernal, Void, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Cursed Brimstone-Fire Opal Alloy | `mvtink_alloy_cursed_brimstone_fire_opal` | Infernal, Radiant, Volatile |
| Flint (`mvtink_flint`) | Cursed Brimstone-Flint Alloy | `mvtink_alloy_cursed_brimstone_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cursed Brimstone-Fluorite Alloy | `mvtink_alloy_cursed_brimstone_fluorite` | Infernal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Cursed Brimstone-Galena Alloy | `mvtink_alloy_cursed_brimstone_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cursed Brimstone-Ghast Tear Shard Alloy | `mvtink_alloy_cursed_brimstone_ghast_tear_shard` | Infernal, Resonant, Volatile |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cursed Brimstone-Glowstone Gem Alloy | `mvtink_alloy_cursed_brimstone_glowstone_gem` | Infernal, Radiant, Volatile |
| Gold (`mvtink_gold`) | Cursed Brimstone-Gold Alloy | `mvtink_alloy_cursed_brimstone_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Cursed Brimstone-Graphite Alloy | `mvtink_alloy_cursed_brimstone_graphite` | Infernal, Terrain, Volatile |
| Gravitite (`mvtink_gravitite`) | Cursed Brimstone-Gravitite Alloy | `mvtink_alloy_cursed_brimstone_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Cursed Brimstone-Gypsum Alloy | `mvtink_alloy_cursed_brimstone_gypsum` | Infernal, Terrain, Volatile |
| Helliron (`mvtink_helliron`) | Cursed Brimstone-Helliron Alloy | `mvtink_alloy_cursed_brimstone_helliron` | Infernal, Tempered, Volatile |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cursed Brimstone-Ignis Ferrum Alloy | `mvtink_alloy_cursed_brimstone_ignis_ferrum` | Infernal, Tempered, Volatile |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cursed Brimstone-Infernal Obsidian Alloy | `mvtink_alloy_cursed_brimstone_infernal_obsidian` | Infernal, Terrain, Volatile |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cursed Brimstone-Netherite-Infused Quartz Alloy | `mvtink_alloy_cursed_brimstone_infused_quartz` | Infernal, Resonant, Volatile |
| Iron (`mvtink_iron`) | Cursed Brimstone-Iron Alloy | `mvtink_alloy_cursed_brimstone_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Cursed Brimstone-Jade Alloy | `mvtink_alloy_cursed_brimstone_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Cursed Brimstone-Kaolinite Alloy | `mvtink_alloy_cursed_brimstone_kaolinite` | Infernal, Terrain, Volatile |
| Lapis Lazuli (`mvtink_lapis`) | Cursed Brimstone-Lapis Lazuli Alloy | `mvtink_alloy_cursed_brimstone_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cursed Brimstone-Lapis Matrix Alloy | `mvtink_alloy_cursed_brimstone_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cursed Brimstone-Magma Brimstone Alloy | `mvtink_alloy_cursed_brimstone_magma_brimstone` | Infernal, Terrain, Volatile |
| Magmacite (`mvtink_magmacite`) | Cursed Brimstone-Magmacite Alloy | `mvtink_alloy_cursed_brimstone_magmacite` | Infernal, Resonant, Volatile |
| Magnetite (`mvtink_magnetite`) | Cursed Brimstone-Magnetite Alloy | `mvtink_alloy_cursed_brimstone_magnetite` | Infernal, Terrain, Volatile |
| Malachite (`mvtink_malachite`) | Cursed Brimstone-Malachite Alloy | `mvtink_alloy_cursed_brimstone_malachite` | Infernal, Terrain, Volatile |
| Nebulite (`mvtink_nebulite`) | Cursed Brimstone-Nebulite Alloy | `mvtink_alloy_cursed_brimstone_nebulite` | Infernal, Void, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cursed Brimstone-Nether Bismuth Alloy | `mvtink_alloy_cursed_brimstone_nether_bismuth` | Infernal, Tempered, Volatile |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cursed Brimstone-Nether Tungsten Alloy | `mvtink_alloy_cursed_brimstone_nether_tungsten` | Infernal, Tempered, Volatile |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cursed Brimstone-Netherite Scrap Shard Alloy | `mvtink_alloy_cursed_brimstone_netherite_shard` | Infernal, Tempered, Volatile |
| Nickel (`mvtink_nickel`) | Cursed Brimstone-Nickel Alloy | `mvtink_alloy_cursed_brimstone_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cursed Brimstone-Null-Shard Alloy | `mvtink_alloy_cursed_brimstone_null_shard` | Infernal, Void, Volatile |
| Obsidian (`mvtink_obsidian`) | Cursed Brimstone-Obsidian Alloy | `mvtink_alloy_cursed_brimstone_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Cursed Brimstone-Obsidianite Alloy | `mvtink_alloy_cursed_brimstone_obsidianite` | Infernal, Terrain, Volatile |
| Opal (`mvtink_opal`) | Cursed Brimstone-Opal Alloy | `mvtink_alloy_cursed_brimstone_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Cursed Brimstone-Ender Pearl Core Alloy | `mvtink_alloy_cursed_brimstone_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Cursed Brimstone-Phantomite Alloy | `mvtink_alloy_cursed_brimstone_phantomite` | Infernal, Void, Volatile |
| Platinum (`mvtink_platinum`) | Cursed Brimstone-Platinum Alloy | `mvtink_alloy_cursed_brimstone_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Cursed Brimstone-Prismarine Alloy | `mvtink_alloy_cursed_brimstone_prismarine` | Infernal, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Cursed Brimstone-Pyrite Alloy | `mvtink_alloy_cursed_brimstone_pyrite` | Infernal, Terrain, Volatile |
| Pyrophore (`mvtink_pyrophore`) | Cursed Brimstone-Pyrophore Alloy | `mvtink_alloy_cursed_brimstone_pyrophore` | Infernal, Volatile, Brutal |
| Nether Quartz (`mvtink_quartz`) | Cursed Brimstone-Nether Quartz Alloy | `mvtink_alloy_cursed_brimstone_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Cursed Brimstone-Redstone Alloy | `mvtink_alloy_cursed_brimstone_redstone` | Infernal, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Cursed Brimstone-Resonite Alloy | `mvtink_alloy_cursed_brimstone_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Cursed Brimstone-Ruby Alloy | `mvtink_alloy_cursed_brimstone_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Cursed Brimstone-Sanguinite Alloy | `mvtink_alloy_cursed_brimstone_sanguinite` | Infernal, Tempered, Volatile |
| Sapphire (`mvtink_sapphire`) | Cursed Brimstone-Sapphire Alloy | `mvtink_alloy_cursed_brimstone_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Cursed Brimstone-Shadowgem Alloy | `mvtink_alloy_cursed_brimstone_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Cursed Brimstone-Shulkerite Alloy | `mvtink_alloy_cursed_brimstone_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Cursed Brimstone-Silver Alloy | `mvtink_alloy_cursed_brimstone_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cursed Brimstone-Singularite Alloy | `mvtink_alloy_cursed_brimstone_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cursed Brimstone-Soul Glass Crystal Alloy | `mvtink_alloy_cursed_brimstone_soulsand_crystal` | Infernal, Resonant, Volatile |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cursed Brimstone-Spatial Platinum Alloy | `mvtink_alloy_cursed_brimstone_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Cursed Brimstone-Starlight Silver Alloy | `mvtink_alloy_cursed_brimstone_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Cursed Brimstone-Steel Alloy | `mvtink_alloy_cursed_brimstone_steel` | Infernal, Tempered, Volatile |
| Stibnite (`mvtink_stibnite`) | Cursed Brimstone-Stibnite Alloy | `mvtink_alloy_cursed_brimstone_stibnite` | Infernal, Terrain, Volatile |
| Sulfur (`mvtink_sulfur`) | Cursed Brimstone-Sulfur Alloy | `mvtink_alloy_cursed_brimstone_sulfur` | Infernal, Volatile |
| Talc (`mvtink_talc`) | Cursed Brimstone-Talc Alloy | `mvtink_alloy_cursed_brimstone_talc` | Infernal, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cursed Brimstone-Tesseract Crystal Alloy | `mvtink_alloy_cursed_brimstone_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Cursed Brimstone-Tin Alloy | `mvtink_alloy_cursed_brimstone_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cursed Brimstone-Titanium Alloy | `mvtink_alloy_cursed_brimstone_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Cursed Brimstone-Topaz Alloy | `mvtink_alloy_cursed_brimstone_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Cursed Brimstone-Tourmaline Alloy | `mvtink_alloy_cursed_brimstone_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Cursed Brimstone-Tungsten Alloy | `mvtink_alloy_cursed_brimstone_tungsten` | Infernal, Tempered, Volatile |
| Void Pyrite (`mvtink_void_pyrite`) | Cursed Brimstone-Void Pyrite Alloy | `mvtink_alloy_cursed_brimstone_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Cursed Brimstone-Void Titanium Alloy | `mvtink_alloy_cursed_brimstone_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Cursed Brimstone-Voidstone Alloy | `mvtink_alloy_cursed_brimstone_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cursed Brimstone-Compacted Volcanic Ash Alloy | `mvtink_alloy_cursed_brimstone_volcanic_ash` | Infernal, Terrain, Volatile |
| Warped Emerald (`mvtink_warped_emerald`) | Cursed Brimstone-Warped Emerald Alloy | `mvtink_alloy_cursed_brimstone_warped_emerald` | Infernal, Radiant, Volatile |
| Warped Quartz (`mvtink_warped_quartz`) | Cursed Brimstone-Warped Quartz Alloy | `mvtink_alloy_cursed_brimstone_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cursed Brimstone-Pure Weeping Shard Alloy | `mvtink_alloy_cursed_brimstone_weeping_shard` | Infernal, Resonant, Volatile |
| Witherite (`mvtink_witherite`) | Cursed Brimstone-Witherite Alloy | `mvtink_alloy_cursed_brimstone_witherite` | Infernal, Terrain, Volatile |
| Zero-Point Shard (`mvtink_zero_point`) | Cursed Brimstone-Zero-Point Shard Alloy | `mvtink_alloy_cursed_brimstone_zero_point` | Infernal, Void, Volatile |
| Zinc (`mvtink_zinc`) | Cursed Brimstone-Zinc Alloy | `mvtink_alloy_cursed_brimstone_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cursed Brimstone-Zircon Alloy | `mvtink_alloy_cursed_brimstone_zircon` | Infernal, Terrain, Resonant |

#### Fire Opal · `mvtink_fire_opal` · 71 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Flint (`mvtink_flint`) | Fire Opal-Flint Alloy | `mvtink_alloy_fire_opal_flint` | Infernal, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Fire Opal-Fluorite Alloy | `mvtink_alloy_fire_opal_fluorite` | Infernal, Terrain, Radiant |
| Galena (`mvtink_galena`) | Fire Opal-Galena Alloy | `mvtink_alloy_fire_opal_galena` | Infernal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Fire Opal-Ghast Tear Shard Alloy | `mvtink_alloy_fire_opal_ghast_tear_shard` | Infernal, Radiant, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Fire Opal-Glowstone Gem Alloy | `mvtink_alloy_fire_opal_glowstone_gem` | Infernal, Radiant, Swift |
| Gold (`mvtink_gold`) | Fire Opal-Gold Alloy | `mvtink_alloy_fire_opal_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Fire Opal-Graphite Alloy | `mvtink_alloy_fire_opal_graphite` | Infernal, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Fire Opal-Gravitite Alloy | `mvtink_alloy_fire_opal_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Fire Opal-Gypsum Alloy | `mvtink_alloy_fire_opal_gypsum` | Infernal, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Fire Opal-Helliron Alloy | `mvtink_alloy_fire_opal_helliron` | Infernal, Tempered, Radiant |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Fire Opal-Ignis Ferrum Alloy | `mvtink_alloy_fire_opal_ignis_ferrum` | Infernal, Tempered, Radiant |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Fire Opal-Infernal Obsidian Alloy | `mvtink_alloy_fire_opal_infernal_obsidian` | Infernal, Terrain, Radiant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Fire Opal-Netherite-Infused Quartz Alloy | `mvtink_alloy_fire_opal_infused_quartz` | Infernal, Radiant, Resonant |
| Iron (`mvtink_iron`) | Fire Opal-Iron Alloy | `mvtink_alloy_fire_opal_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Fire Opal-Jade Alloy | `mvtink_alloy_fire_opal_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Fire Opal-Kaolinite Alloy | `mvtink_alloy_fire_opal_kaolinite` | Infernal, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Fire Opal-Lapis Lazuli Alloy | `mvtink_alloy_fire_opal_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Fire Opal-Lapis Matrix Alloy | `mvtink_alloy_fire_opal_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Fire Opal-Magma Brimstone Alloy | `mvtink_alloy_fire_opal_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Fire Opal-Magmacite Alloy | `mvtink_alloy_fire_opal_magmacite` | Infernal, Radiant, Resonant |
| Magnetite (`mvtink_magnetite`) | Fire Opal-Magnetite Alloy | `mvtink_alloy_fire_opal_magnetite` | Infernal, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Fire Opal-Malachite Alloy | `mvtink_alloy_fire_opal_malachite` | Infernal, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Fire Opal-Nebulite Alloy | `mvtink_alloy_fire_opal_nebulite` | Infernal, Void, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Fire Opal-Nether Bismuth Alloy | `mvtink_alloy_fire_opal_nether_bismuth` | Infernal, Tempered, Radiant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Fire Opal-Nether Tungsten Alloy | `mvtink_alloy_fire_opal_nether_tungsten` | Infernal, Tempered, Radiant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Fire Opal-Netherite Scrap Shard Alloy | `mvtink_alloy_fire_opal_netherite_shard` | Infernal, Tempered, Radiant |
| Nickel (`mvtink_nickel`) | Fire Opal-Nickel Alloy | `mvtink_alloy_fire_opal_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Fire Opal-Null-Shard Alloy | `mvtink_alloy_fire_opal_null_shard` | Infernal, Void, Radiant |
| Obsidian (`mvtink_obsidian`) | Fire Opal-Obsidian Alloy | `mvtink_alloy_fire_opal_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Fire Opal-Obsidianite Alloy | `mvtink_alloy_fire_opal_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Fire Opal-Opal Alloy | `mvtink_alloy_fire_opal_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Fire Opal-Ender Pearl Core Alloy | `mvtink_alloy_fire_opal_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Fire Opal-Phantomite Alloy | `mvtink_alloy_fire_opal_phantomite` | Infernal, Void, Radiant |
| Platinum (`mvtink_platinum`) | Fire Opal-Platinum Alloy | `mvtink_alloy_fire_opal_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Fire Opal-Prismarine Alloy | `mvtink_alloy_fire_opal_prismarine` | Infernal, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Fire Opal-Pyrite Alloy | `mvtink_alloy_fire_opal_pyrite` | Infernal, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Fire Opal-Pyrophore Alloy | `mvtink_alloy_fire_opal_pyrophore` | Infernal, Radiant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Fire Opal-Nether Quartz Alloy | `mvtink_alloy_fire_opal_quartz` | Infernal, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Fire Opal-Redstone Alloy | `mvtink_alloy_fire_opal_redstone` | Infernal, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Fire Opal-Resonite Alloy | `mvtink_alloy_fire_opal_resonite` | Infernal, Void, Radiant |
| Ruby (`mvtink_ruby`) | Fire Opal-Ruby Alloy | `mvtink_alloy_fire_opal_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Fire Opal-Sanguinite Alloy | `mvtink_alloy_fire_opal_sanguinite` | Infernal, Tempered, Radiant |
| Sapphire (`mvtink_sapphire`) | Fire Opal-Sapphire Alloy | `mvtink_alloy_fire_opal_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Fire Opal-Shadowgem Alloy | `mvtink_alloy_fire_opal_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Fire Opal-Shulkerite Alloy | `mvtink_alloy_fire_opal_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Fire Opal-Silver Alloy | `mvtink_alloy_fire_opal_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Fire Opal-Singularite Alloy | `mvtink_alloy_fire_opal_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Fire Opal-Soul Glass Crystal Alloy | `mvtink_alloy_fire_opal_soulsand_crystal` | Infernal, Radiant, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Fire Opal-Spatial Platinum Alloy | `mvtink_alloy_fire_opal_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Fire Opal-Starlight Silver Alloy | `mvtink_alloy_fire_opal_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Fire Opal-Steel Alloy | `mvtink_alloy_fire_opal_steel` | Infernal, Tempered, Radiant |
| Stibnite (`mvtink_stibnite`) | Fire Opal-Stibnite Alloy | `mvtink_alloy_fire_opal_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Fire Opal-Sulfur Alloy | `mvtink_alloy_fire_opal_sulfur` | Infernal, Radiant, Volatile |
| Talc (`mvtink_talc`) | Fire Opal-Talc Alloy | `mvtink_alloy_fire_opal_talc` | Infernal, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Fire Opal-Tesseract Crystal Alloy | `mvtink_alloy_fire_opal_tesseract_crystal` | Infernal, Void, Radiant |
| Tin (`mvtink_tin`) | Fire Opal-Tin Alloy | `mvtink_alloy_fire_opal_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Fire Opal-Titanium Alloy | `mvtink_alloy_fire_opal_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Fire Opal-Topaz Alloy | `mvtink_alloy_fire_opal_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Fire Opal-Tourmaline Alloy | `mvtink_alloy_fire_opal_tourmaline` | Infernal, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Fire Opal-Tungsten Alloy | `mvtink_alloy_fire_opal_tungsten` | Infernal, Tempered, Radiant |
| Void Pyrite (`mvtink_void_pyrite`) | Fire Opal-Void Pyrite Alloy | `mvtink_alloy_fire_opal_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Fire Opal-Void Titanium Alloy | `mvtink_alloy_fire_opal_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Fire Opal-Voidstone Alloy | `mvtink_alloy_fire_opal_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Fire Opal-Compacted Volcanic Ash Alloy | `mvtink_alloy_fire_opal_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Fire Opal-Warped Emerald Alloy | `mvtink_alloy_fire_opal_warped_emerald` | Infernal, Radiant, Swift |
| Warped Quartz (`mvtink_warped_quartz`) | Fire Opal-Warped Quartz Alloy | `mvtink_alloy_fire_opal_warped_quartz` | Infernal, Void, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Fire Opal-Pure Weeping Shard Alloy | `mvtink_alloy_fire_opal_weeping_shard` | Infernal, Radiant, Resonant |
| Witherite (`mvtink_witherite`) | Fire Opal-Witherite Alloy | `mvtink_alloy_fire_opal_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Fire Opal-Zero-Point Shard Alloy | `mvtink_alloy_fire_opal_zero_point` | Infernal, Void, Radiant |
| Zinc (`mvtink_zinc`) | Fire Opal-Zinc Alloy | `mvtink_alloy_fire_opal_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Fire Opal-Zircon Alloy | `mvtink_alloy_fire_opal_zircon` | Infernal, Terrain, Radiant |

#### Ghast Tear Shard · `mvtink_ghast_tear_shard` · 67 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Glowstone Gem (`mvtink_glowstone_gem`) | Ghast Tear Shard-Glowstone Gem Alloy | `mvtink_alloy_ghast_tear_shard_glowstone_gem` | Infernal, Radiant, Resonant |
| Gold (`mvtink_gold`) | Ghast Tear Shard-Gold Alloy | `mvtink_alloy_ghast_tear_shard_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Ghast Tear Shard-Graphite Alloy | `mvtink_alloy_ghast_tear_shard_graphite` | Infernal, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Ghast Tear Shard-Gravitite Alloy | `mvtink_alloy_ghast_tear_shard_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Ghast Tear Shard-Gypsum Alloy | `mvtink_alloy_ghast_tear_shard_gypsum` | Infernal, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Ghast Tear Shard-Helliron Alloy | `mvtink_alloy_ghast_tear_shard_helliron` | Infernal, Tempered, Resonant |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Ghast Tear Shard-Ignis Ferrum Alloy | `mvtink_alloy_ghast_tear_shard_ignis_ferrum` | Infernal, Tempered, Resonant |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Ghast Tear Shard-Infernal Obsidian Alloy | `mvtink_alloy_ghast_tear_shard_infernal_obsidian` | Infernal, Terrain, Resonant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Ghast Tear Shard-Netherite-Infused Quartz Alloy | `mvtink_alloy_ghast_tear_shard_infused_quartz` | Infernal, Resonant, Swift |
| Iron (`mvtink_iron`) | Ghast Tear Shard-Iron Alloy | `mvtink_alloy_ghast_tear_shard_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Ghast Tear Shard-Jade Alloy | `mvtink_alloy_ghast_tear_shard_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Ghast Tear Shard-Kaolinite Alloy | `mvtink_alloy_ghast_tear_shard_kaolinite` | Infernal, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Ghast Tear Shard-Lapis Lazuli Alloy | `mvtink_alloy_ghast_tear_shard_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Ghast Tear Shard-Lapis Matrix Alloy | `mvtink_alloy_ghast_tear_shard_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Ghast Tear Shard-Magma Brimstone Alloy | `mvtink_alloy_ghast_tear_shard_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Ghast Tear Shard-Magmacite Alloy | `mvtink_alloy_ghast_tear_shard_magmacite` | Infernal, Resonant, Brutal |
| Magnetite (`mvtink_magnetite`) | Ghast Tear Shard-Magnetite Alloy | `mvtink_alloy_ghast_tear_shard_magnetite` | Infernal, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Ghast Tear Shard-Malachite Alloy | `mvtink_alloy_ghast_tear_shard_malachite` | Infernal, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Ghast Tear Shard-Nebulite Alloy | `mvtink_alloy_ghast_tear_shard_nebulite` | Infernal, Void, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Ghast Tear Shard-Nether Bismuth Alloy | `mvtink_alloy_ghast_tear_shard_nether_bismuth` | Infernal, Tempered, Resonant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Ghast Tear Shard-Nether Tungsten Alloy | `mvtink_alloy_ghast_tear_shard_nether_tungsten` | Infernal, Tempered, Resonant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Ghast Tear Shard-Netherite Scrap Shard Alloy | `mvtink_alloy_ghast_tear_shard_netherite_shard` | Infernal, Tempered, Resonant |
| Nickel (`mvtink_nickel`) | Ghast Tear Shard-Nickel Alloy | `mvtink_alloy_ghast_tear_shard_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Ghast Tear Shard-Null-Shard Alloy | `mvtink_alloy_ghast_tear_shard_null_shard` | Infernal, Void, Resonant |
| Obsidian (`mvtink_obsidian`) | Ghast Tear Shard-Obsidian Alloy | `mvtink_alloy_ghast_tear_shard_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Ghast Tear Shard-Obsidianite Alloy | `mvtink_alloy_ghast_tear_shard_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Ghast Tear Shard-Opal Alloy | `mvtink_alloy_ghast_tear_shard_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Ghast Tear Shard-Ender Pearl Core Alloy | `mvtink_alloy_ghast_tear_shard_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Ghast Tear Shard-Phantomite Alloy | `mvtink_alloy_ghast_tear_shard_phantomite` | Infernal, Void, Resonant |
| Platinum (`mvtink_platinum`) | Ghast Tear Shard-Platinum Alloy | `mvtink_alloy_ghast_tear_shard_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Ghast Tear Shard-Prismarine Alloy | `mvtink_alloy_ghast_tear_shard_prismarine` | Infernal, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Ghast Tear Shard-Pyrite Alloy | `mvtink_alloy_ghast_tear_shard_pyrite` | Infernal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Ghast Tear Shard-Pyrophore Alloy | `mvtink_alloy_ghast_tear_shard_pyrophore` | Infernal, Resonant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Ghast Tear Shard-Nether Quartz Alloy | `mvtink_alloy_ghast_tear_shard_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Ghast Tear Shard-Redstone Alloy | `mvtink_alloy_ghast_tear_shard_redstone` | Infernal, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Ghast Tear Shard-Resonite Alloy | `mvtink_alloy_ghast_tear_shard_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Ghast Tear Shard-Ruby Alloy | `mvtink_alloy_ghast_tear_shard_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Ghast Tear Shard-Sanguinite Alloy | `mvtink_alloy_ghast_tear_shard_sanguinite` | Infernal, Tempered, Resonant |
| Sapphire (`mvtink_sapphire`) | Ghast Tear Shard-Sapphire Alloy | `mvtink_alloy_ghast_tear_shard_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Ghast Tear Shard-Shadowgem Alloy | `mvtink_alloy_ghast_tear_shard_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Ghast Tear Shard-Shulkerite Alloy | `mvtink_alloy_ghast_tear_shard_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Ghast Tear Shard-Silver Alloy | `mvtink_alloy_ghast_tear_shard_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Ghast Tear Shard-Singularite Alloy | `mvtink_alloy_ghast_tear_shard_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ghast Tear Shard-Soul Glass Crystal Alloy | `mvtink_alloy_ghast_tear_shard_soulsand_crystal` | Infernal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ghast Tear Shard-Spatial Platinum Alloy | `mvtink_alloy_ghast_tear_shard_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Ghast Tear Shard-Starlight Silver Alloy | `mvtink_alloy_ghast_tear_shard_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Ghast Tear Shard-Steel Alloy | `mvtink_alloy_ghast_tear_shard_steel` | Infernal, Tempered, Resonant |
| Stibnite (`mvtink_stibnite`) | Ghast Tear Shard-Stibnite Alloy | `mvtink_alloy_ghast_tear_shard_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Ghast Tear Shard-Sulfur Alloy | `mvtink_alloy_ghast_tear_shard_sulfur` | Infernal, Resonant, Volatile |
| Talc (`mvtink_talc`) | Ghast Tear Shard-Talc Alloy | `mvtink_alloy_ghast_tear_shard_talc` | Infernal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ghast Tear Shard-Tesseract Crystal Alloy | `mvtink_alloy_ghast_tear_shard_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Ghast Tear Shard-Tin Alloy | `mvtink_alloy_ghast_tear_shard_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Ghast Tear Shard-Titanium Alloy | `mvtink_alloy_ghast_tear_shard_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Ghast Tear Shard-Topaz Alloy | `mvtink_alloy_ghast_tear_shard_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Ghast Tear Shard-Tourmaline Alloy | `mvtink_alloy_ghast_tear_shard_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Ghast Tear Shard-Tungsten Alloy | `mvtink_alloy_ghast_tear_shard_tungsten` | Infernal, Tempered, Resonant |
| Void Pyrite (`mvtink_void_pyrite`) | Ghast Tear Shard-Void Pyrite Alloy | `mvtink_alloy_ghast_tear_shard_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Ghast Tear Shard-Void Titanium Alloy | `mvtink_alloy_ghast_tear_shard_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Ghast Tear Shard-Voidstone Alloy | `mvtink_alloy_ghast_tear_shard_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ghast Tear Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_ghast_tear_shard_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Ghast Tear Shard-Warped Emerald Alloy | `mvtink_alloy_ghast_tear_shard_warped_emerald` | Infernal, Radiant, Resonant |
| Warped Quartz (`mvtink_warped_quartz`) | Ghast Tear Shard-Warped Quartz Alloy | `mvtink_alloy_ghast_tear_shard_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ghast Tear Shard-Pure Weeping Shard Alloy | `mvtink_alloy_ghast_tear_shard_weeping_shard` | Infernal, Resonant, Brutal |
| Witherite (`mvtink_witherite`) | Ghast Tear Shard-Witherite Alloy | `mvtink_alloy_ghast_tear_shard_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Ghast Tear Shard-Zero-Point Shard Alloy | `mvtink_alloy_ghast_tear_shard_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Ghast Tear Shard-Zinc Alloy | `mvtink_alloy_ghast_tear_shard_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Ghast Tear Shard-Zircon Alloy | `mvtink_alloy_ghast_tear_shard_zircon` | Infernal, Terrain, Resonant |

#### Glowstone Gem · `mvtink_glowstone_gem` · 66 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Gold (`mvtink_gold`) | Glowstone Gem-Gold Alloy | `mvtink_alloy_glowstone_gem_gold` | Infernal, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Glowstone Gem-Graphite Alloy | `mvtink_alloy_glowstone_gem_graphite` | Infernal, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Glowstone Gem-Gravitite Alloy | `mvtink_alloy_glowstone_gem_gravitite` | Infernal, Void, Tempered |
| Gypsum (`mvtink_gypsum`) | Glowstone Gem-Gypsum Alloy | `mvtink_alloy_glowstone_gem_gypsum` | Infernal, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Glowstone Gem-Helliron Alloy | `mvtink_alloy_glowstone_gem_helliron` | Infernal, Tempered, Radiant |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Glowstone Gem-Ignis Ferrum Alloy | `mvtink_alloy_glowstone_gem_ignis_ferrum` | Infernal, Tempered, Radiant |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Glowstone Gem-Infernal Obsidian Alloy | `mvtink_alloy_glowstone_gem_infernal_obsidian` | Infernal, Terrain, Radiant |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Glowstone Gem-Netherite-Infused Quartz Alloy | `mvtink_alloy_glowstone_gem_infused_quartz` | Infernal, Radiant, Resonant |
| Iron (`mvtink_iron`) | Glowstone Gem-Iron Alloy | `mvtink_alloy_glowstone_gem_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Glowstone Gem-Jade Alloy | `mvtink_alloy_glowstone_gem_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Glowstone Gem-Kaolinite Alloy | `mvtink_alloy_glowstone_gem_kaolinite` | Infernal, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Glowstone Gem-Lapis Lazuli Alloy | `mvtink_alloy_glowstone_gem_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Glowstone Gem-Lapis Matrix Alloy | `mvtink_alloy_glowstone_gem_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Glowstone Gem-Magma Brimstone Alloy | `mvtink_alloy_glowstone_gem_magma_brimstone` | Infernal, Terrain, Radiant |
| Magmacite (`mvtink_magmacite`) | Glowstone Gem-Magmacite Alloy | `mvtink_alloy_glowstone_gem_magmacite` | Infernal, Radiant, Resonant |
| Magnetite (`mvtink_magnetite`) | Glowstone Gem-Magnetite Alloy | `mvtink_alloy_glowstone_gem_magnetite` | Infernal, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Glowstone Gem-Malachite Alloy | `mvtink_alloy_glowstone_gem_malachite` | Infernal, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Glowstone Gem-Nebulite Alloy | `mvtink_alloy_glowstone_gem_nebulite` | Infernal, Void, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Glowstone Gem-Nether Bismuth Alloy | `mvtink_alloy_glowstone_gem_nether_bismuth` | Infernal, Tempered, Radiant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Glowstone Gem-Nether Tungsten Alloy | `mvtink_alloy_glowstone_gem_nether_tungsten` | Infernal, Tempered, Radiant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Glowstone Gem-Netherite Scrap Shard Alloy | `mvtink_alloy_glowstone_gem_netherite_shard` | Infernal, Tempered, Radiant |
| Nickel (`mvtink_nickel`) | Glowstone Gem-Nickel Alloy | `mvtink_alloy_glowstone_gem_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Glowstone Gem-Null-Shard Alloy | `mvtink_alloy_glowstone_gem_null_shard` | Infernal, Void, Radiant |
| Obsidian (`mvtink_obsidian`) | Glowstone Gem-Obsidian Alloy | `mvtink_alloy_glowstone_gem_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Glowstone Gem-Obsidianite Alloy | `mvtink_alloy_glowstone_gem_obsidianite` | Infernal, Terrain, Radiant |
| Opal (`mvtink_opal`) | Glowstone Gem-Opal Alloy | `mvtink_alloy_glowstone_gem_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Glowstone Gem-Ender Pearl Core Alloy | `mvtink_alloy_glowstone_gem_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Glowstone Gem-Phantomite Alloy | `mvtink_alloy_glowstone_gem_phantomite` | Infernal, Void, Radiant |
| Platinum (`mvtink_platinum`) | Glowstone Gem-Platinum Alloy | `mvtink_alloy_glowstone_gem_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Glowstone Gem-Prismarine Alloy | `mvtink_alloy_glowstone_gem_prismarine` | Infernal, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Glowstone Gem-Pyrite Alloy | `mvtink_alloy_glowstone_gem_pyrite` | Infernal, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Glowstone Gem-Pyrophore Alloy | `mvtink_alloy_glowstone_gem_pyrophore` | Infernal, Radiant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Glowstone Gem-Nether Quartz Alloy | `mvtink_alloy_glowstone_gem_quartz` | Infernal, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Glowstone Gem-Redstone Alloy | `mvtink_alloy_glowstone_gem_redstone` | Infernal, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Glowstone Gem-Resonite Alloy | `mvtink_alloy_glowstone_gem_resonite` | Infernal, Void, Radiant |
| Ruby (`mvtink_ruby`) | Glowstone Gem-Ruby Alloy | `mvtink_alloy_glowstone_gem_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Glowstone Gem-Sanguinite Alloy | `mvtink_alloy_glowstone_gem_sanguinite` | Infernal, Tempered, Radiant |
| Sapphire (`mvtink_sapphire`) | Glowstone Gem-Sapphire Alloy | `mvtink_alloy_glowstone_gem_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Glowstone Gem-Shadowgem Alloy | `mvtink_alloy_glowstone_gem_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Glowstone Gem-Shulkerite Alloy | `mvtink_alloy_glowstone_gem_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Glowstone Gem-Silver Alloy | `mvtink_alloy_glowstone_gem_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Glowstone Gem-Singularite Alloy | `mvtink_alloy_glowstone_gem_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Glowstone Gem-Soul Glass Crystal Alloy | `mvtink_alloy_glowstone_gem_soulsand_crystal` | Infernal, Radiant, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Glowstone Gem-Spatial Platinum Alloy | `mvtink_alloy_glowstone_gem_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Glowstone Gem-Starlight Silver Alloy | `mvtink_alloy_glowstone_gem_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Glowstone Gem-Steel Alloy | `mvtink_alloy_glowstone_gem_steel` | Infernal, Tempered, Radiant |
| Stibnite (`mvtink_stibnite`) | Glowstone Gem-Stibnite Alloy | `mvtink_alloy_glowstone_gem_stibnite` | Infernal, Terrain, Radiant |
| Sulfur (`mvtink_sulfur`) | Glowstone Gem-Sulfur Alloy | `mvtink_alloy_glowstone_gem_sulfur` | Infernal, Radiant, Volatile |
| Talc (`mvtink_talc`) | Glowstone Gem-Talc Alloy | `mvtink_alloy_glowstone_gem_talc` | Infernal, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Glowstone Gem-Tesseract Crystal Alloy | `mvtink_alloy_glowstone_gem_tesseract_crystal` | Infernal, Void, Radiant |
| Tin (`mvtink_tin`) | Glowstone Gem-Tin Alloy | `mvtink_alloy_glowstone_gem_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Glowstone Gem-Titanium Alloy | `mvtink_alloy_glowstone_gem_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Glowstone Gem-Topaz Alloy | `mvtink_alloy_glowstone_gem_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Glowstone Gem-Tourmaline Alloy | `mvtink_alloy_glowstone_gem_tourmaline` | Infernal, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Glowstone Gem-Tungsten Alloy | `mvtink_alloy_glowstone_gem_tungsten` | Infernal, Tempered, Radiant |
| Void Pyrite (`mvtink_void_pyrite`) | Glowstone Gem-Void Pyrite Alloy | `mvtink_alloy_glowstone_gem_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Glowstone Gem-Void Titanium Alloy | `mvtink_alloy_glowstone_gem_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Glowstone Gem-Voidstone Alloy | `mvtink_alloy_glowstone_gem_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Glowstone Gem-Compacted Volcanic Ash Alloy | `mvtink_alloy_glowstone_gem_volcanic_ash` | Infernal, Terrain, Radiant |
| Warped Emerald (`mvtink_warped_emerald`) | Glowstone Gem-Warped Emerald Alloy | `mvtink_alloy_glowstone_gem_warped_emerald` | Infernal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Glowstone Gem-Warped Quartz Alloy | `mvtink_alloy_glowstone_gem_warped_quartz` | Infernal, Void, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Glowstone Gem-Pure Weeping Shard Alloy | `mvtink_alloy_glowstone_gem_weeping_shard` | Infernal, Radiant, Resonant |
| Witherite (`mvtink_witherite`) | Glowstone Gem-Witherite Alloy | `mvtink_alloy_glowstone_gem_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Glowstone Gem-Zero-Point Shard Alloy | `mvtink_alloy_glowstone_gem_zero_point` | Infernal, Void, Radiant |
| Zinc (`mvtink_zinc`) | Glowstone Gem-Zinc Alloy | `mvtink_alloy_glowstone_gem_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Glowstone Gem-Zircon Alloy | `mvtink_alloy_glowstone_gem_zircon` | Infernal, Terrain, Radiant |

#### Helliron · `mvtink_helliron` · 61 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Helliron-Ignis Ferrum Alloy | `mvtink_alloy_helliron_ignis_ferrum` | Infernal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Helliron-Infernal Obsidian Alloy | `mvtink_alloy_helliron_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Helliron-Netherite-Infused Quartz Alloy | `mvtink_alloy_helliron_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Helliron-Iron Alloy | `mvtink_alloy_helliron_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Helliron-Jade Alloy | `mvtink_alloy_helliron_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Helliron-Kaolinite Alloy | `mvtink_alloy_helliron_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Helliron-Lapis Lazuli Alloy | `mvtink_alloy_helliron_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Helliron-Lapis Matrix Alloy | `mvtink_alloy_helliron_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Helliron-Magma Brimstone Alloy | `mvtink_alloy_helliron_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Helliron-Magmacite Alloy | `mvtink_alloy_helliron_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Helliron-Magnetite Alloy | `mvtink_alloy_helliron_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Helliron-Malachite Alloy | `mvtink_alloy_helliron_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Helliron-Nebulite Alloy | `mvtink_alloy_helliron_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Helliron-Nether Bismuth Alloy | `mvtink_alloy_helliron_nether_bismuth` | Infernal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Helliron-Nether Tungsten Alloy | `mvtink_alloy_helliron_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Helliron-Netherite Scrap Shard Alloy | `mvtink_alloy_helliron_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Helliron-Nickel Alloy | `mvtink_alloy_helliron_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Helliron-Null-Shard Alloy | `mvtink_alloy_helliron_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Helliron-Obsidian Alloy | `mvtink_alloy_helliron_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Helliron-Obsidianite Alloy | `mvtink_alloy_helliron_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Helliron-Opal Alloy | `mvtink_alloy_helliron_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Helliron-Ender Pearl Core Alloy | `mvtink_alloy_helliron_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Helliron-Phantomite Alloy | `mvtink_alloy_helliron_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Helliron-Platinum Alloy | `mvtink_alloy_helliron_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Helliron-Prismarine Alloy | `mvtink_alloy_helliron_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Helliron-Pyrite Alloy | `mvtink_alloy_helliron_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Helliron-Pyrophore Alloy | `mvtink_alloy_helliron_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Helliron-Nether Quartz Alloy | `mvtink_alloy_helliron_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Helliron-Redstone Alloy | `mvtink_alloy_helliron_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Helliron-Resonite Alloy | `mvtink_alloy_helliron_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Helliron-Ruby Alloy | `mvtink_alloy_helliron_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Helliron-Sanguinite Alloy | `mvtink_alloy_helliron_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Helliron-Sapphire Alloy | `mvtink_alloy_helliron_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Helliron-Shadowgem Alloy | `mvtink_alloy_helliron_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Helliron-Shulkerite Alloy | `mvtink_alloy_helliron_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Helliron-Silver Alloy | `mvtink_alloy_helliron_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Helliron-Singularite Alloy | `mvtink_alloy_helliron_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Helliron-Soul Glass Crystal Alloy | `mvtink_alloy_helliron_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Helliron-Spatial Platinum Alloy | `mvtink_alloy_helliron_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Helliron-Starlight Silver Alloy | `mvtink_alloy_helliron_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Helliron-Steel Alloy | `mvtink_alloy_helliron_steel` | Infernal, Tempered |
| Stibnite (`mvtink_stibnite`) | Helliron-Stibnite Alloy | `mvtink_alloy_helliron_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Helliron-Sulfur Alloy | `mvtink_alloy_helliron_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Helliron-Talc Alloy | `mvtink_alloy_helliron_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Helliron-Tesseract Crystal Alloy | `mvtink_alloy_helliron_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Helliron-Tin Alloy | `mvtink_alloy_helliron_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Helliron-Titanium Alloy | `mvtink_alloy_helliron_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Helliron-Topaz Alloy | `mvtink_alloy_helliron_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Helliron-Tourmaline Alloy | `mvtink_alloy_helliron_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Helliron-Tungsten Alloy | `mvtink_alloy_helliron_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Helliron-Void Pyrite Alloy | `mvtink_alloy_helliron_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Helliron-Void Titanium Alloy | `mvtink_alloy_helliron_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Helliron-Voidstone Alloy | `mvtink_alloy_helliron_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Helliron-Compacted Volcanic Ash Alloy | `mvtink_alloy_helliron_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Helliron-Warped Emerald Alloy | `mvtink_alloy_helliron_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Helliron-Warped Quartz Alloy | `mvtink_alloy_helliron_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Helliron-Pure Weeping Shard Alloy | `mvtink_alloy_helliron_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Helliron-Witherite Alloy | `mvtink_alloy_helliron_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Helliron-Zero-Point Shard Alloy | `mvtink_alloy_helliron_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Helliron-Zinc Alloy | `mvtink_alloy_helliron_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Helliron-Zircon Alloy | `mvtink_alloy_helliron_zircon` | Infernal, Terrain, Tempered |

#### Ignis Ferrum · `mvtink_ignis_ferrum` · 60 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Ignis Ferrum-Infernal Obsidian Alloy | `mvtink_alloy_ignis_ferrum_infernal_obsidian` | Infernal, Terrain, Tempered |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Ignis Ferrum-Netherite-Infused Quartz Alloy | `mvtink_alloy_ignis_ferrum_infused_quartz` | Infernal, Tempered, Resonant |
| Iron (`mvtink_iron`) | Ignis Ferrum-Iron Alloy | `mvtink_alloy_ignis_ferrum_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Ignis Ferrum-Jade Alloy | `mvtink_alloy_ignis_ferrum_jade` | Infernal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Ignis Ferrum-Kaolinite Alloy | `mvtink_alloy_ignis_ferrum_kaolinite` | Infernal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Ignis Ferrum-Lapis Lazuli Alloy | `mvtink_alloy_ignis_ferrum_lapis` | Infernal, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Ignis Ferrum-Lapis Matrix Alloy | `mvtink_alloy_ignis_ferrum_lapis_matrix` | Infernal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Ignis Ferrum-Magma Brimstone Alloy | `mvtink_alloy_ignis_ferrum_magma_brimstone` | Infernal, Terrain, Tempered |
| Magmacite (`mvtink_magmacite`) | Ignis Ferrum-Magmacite Alloy | `mvtink_alloy_ignis_ferrum_magmacite` | Infernal, Tempered, Resonant |
| Magnetite (`mvtink_magnetite`) | Ignis Ferrum-Magnetite Alloy | `mvtink_alloy_ignis_ferrum_magnetite` | Infernal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Ignis Ferrum-Malachite Alloy | `mvtink_alloy_ignis_ferrum_malachite` | Infernal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Ignis Ferrum-Nebulite Alloy | `mvtink_alloy_ignis_ferrum_nebulite` | Infernal, Void, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Ignis Ferrum-Nether Bismuth Alloy | `mvtink_alloy_ignis_ferrum_nether_bismuth` | Infernal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Ignis Ferrum-Nether Tungsten Alloy | `mvtink_alloy_ignis_ferrum_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Ignis Ferrum-Netherite Scrap Shard Alloy | `mvtink_alloy_ignis_ferrum_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Ignis Ferrum-Nickel Alloy | `mvtink_alloy_ignis_ferrum_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Ignis Ferrum-Null-Shard Alloy | `mvtink_alloy_ignis_ferrum_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Ignis Ferrum-Obsidian Alloy | `mvtink_alloy_ignis_ferrum_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Ignis Ferrum-Obsidianite Alloy | `mvtink_alloy_ignis_ferrum_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Ignis Ferrum-Opal Alloy | `mvtink_alloy_ignis_ferrum_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Ignis Ferrum-Ender Pearl Core Alloy | `mvtink_alloy_ignis_ferrum_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Ignis Ferrum-Phantomite Alloy | `mvtink_alloy_ignis_ferrum_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Ignis Ferrum-Platinum Alloy | `mvtink_alloy_ignis_ferrum_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Ignis Ferrum-Prismarine Alloy | `mvtink_alloy_ignis_ferrum_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Ignis Ferrum-Pyrite Alloy | `mvtink_alloy_ignis_ferrum_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Ignis Ferrum-Pyrophore Alloy | `mvtink_alloy_ignis_ferrum_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Ignis Ferrum-Nether Quartz Alloy | `mvtink_alloy_ignis_ferrum_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Ignis Ferrum-Redstone Alloy | `mvtink_alloy_ignis_ferrum_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Ignis Ferrum-Resonite Alloy | `mvtink_alloy_ignis_ferrum_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Ignis Ferrum-Ruby Alloy | `mvtink_alloy_ignis_ferrum_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Ignis Ferrum-Sanguinite Alloy | `mvtink_alloy_ignis_ferrum_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Ignis Ferrum-Sapphire Alloy | `mvtink_alloy_ignis_ferrum_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Ignis Ferrum-Shadowgem Alloy | `mvtink_alloy_ignis_ferrum_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Ignis Ferrum-Shulkerite Alloy | `mvtink_alloy_ignis_ferrum_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Ignis Ferrum-Silver Alloy | `mvtink_alloy_ignis_ferrum_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Ignis Ferrum-Singularite Alloy | `mvtink_alloy_ignis_ferrum_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ignis Ferrum-Soul Glass Crystal Alloy | `mvtink_alloy_ignis_ferrum_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ignis Ferrum-Spatial Platinum Alloy | `mvtink_alloy_ignis_ferrum_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Ignis Ferrum-Starlight Silver Alloy | `mvtink_alloy_ignis_ferrum_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Ignis Ferrum-Steel Alloy | `mvtink_alloy_ignis_ferrum_steel` | Infernal, Tempered |
| Stibnite (`mvtink_stibnite`) | Ignis Ferrum-Stibnite Alloy | `mvtink_alloy_ignis_ferrum_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Ignis Ferrum-Sulfur Alloy | `mvtink_alloy_ignis_ferrum_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Ignis Ferrum-Talc Alloy | `mvtink_alloy_ignis_ferrum_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ignis Ferrum-Tesseract Crystal Alloy | `mvtink_alloy_ignis_ferrum_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Ignis Ferrum-Tin Alloy | `mvtink_alloy_ignis_ferrum_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Ignis Ferrum-Titanium Alloy | `mvtink_alloy_ignis_ferrum_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Ignis Ferrum-Topaz Alloy | `mvtink_alloy_ignis_ferrum_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Ignis Ferrum-Tourmaline Alloy | `mvtink_alloy_ignis_ferrum_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Ignis Ferrum-Tungsten Alloy | `mvtink_alloy_ignis_ferrum_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Ignis Ferrum-Void Pyrite Alloy | `mvtink_alloy_ignis_ferrum_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Ignis Ferrum-Void Titanium Alloy | `mvtink_alloy_ignis_ferrum_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Ignis Ferrum-Voidstone Alloy | `mvtink_alloy_ignis_ferrum_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ignis Ferrum-Compacted Volcanic Ash Alloy | `mvtink_alloy_ignis_ferrum_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Ignis Ferrum-Warped Emerald Alloy | `mvtink_alloy_ignis_ferrum_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Ignis Ferrum-Warped Quartz Alloy | `mvtink_alloy_ignis_ferrum_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ignis Ferrum-Pure Weeping Shard Alloy | `mvtink_alloy_ignis_ferrum_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Ignis Ferrum-Witherite Alloy | `mvtink_alloy_ignis_ferrum_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Ignis Ferrum-Zero-Point Shard Alloy | `mvtink_alloy_ignis_ferrum_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Ignis Ferrum-Zinc Alloy | `mvtink_alloy_ignis_ferrum_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Ignis Ferrum-Zircon Alloy | `mvtink_alloy_ignis_ferrum_zircon` | Infernal, Terrain, Tempered |

#### Infernal Obsidian · `mvtink_infernal_obsidian` · 59 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Infernal Obsidian-Netherite-Infused Quartz Alloy | `mvtink_alloy_infernal_obsidian_infused_quartz` | Infernal, Terrain, Resonant |
| Iron (`mvtink_iron`) | Infernal Obsidian-Iron Alloy | `mvtink_alloy_infernal_obsidian_iron` | Infernal, Primal, Terrain |
| Jade (`mvtink_jade`) | Infernal Obsidian-Jade Alloy | `mvtink_alloy_infernal_obsidian_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Infernal Obsidian-Kaolinite Alloy | `mvtink_alloy_infernal_obsidian_kaolinite` | Infernal, Terrain, Brutal |
| Lapis Lazuli (`mvtink_lapis`) | Infernal Obsidian-Lapis Lazuli Alloy | `mvtink_alloy_infernal_obsidian_lapis` | Infernal, Primal, Terrain |
| Lapis Matrix (`mvtink_lapis_matrix`) | Infernal Obsidian-Lapis Matrix Alloy | `mvtink_alloy_infernal_obsidian_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Infernal Obsidian-Magma Brimstone Alloy | `mvtink_alloy_infernal_obsidian_magma_brimstone` | Infernal, Terrain, Brutal |
| Magmacite (`mvtink_magmacite`) | Infernal Obsidian-Magmacite Alloy | `mvtink_alloy_infernal_obsidian_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Infernal Obsidian-Magnetite Alloy | `mvtink_alloy_infernal_obsidian_magnetite` | Infernal, Terrain, Brutal |
| Malachite (`mvtink_malachite`) | Infernal Obsidian-Malachite Alloy | `mvtink_alloy_infernal_obsidian_malachite` | Infernal, Terrain, Brutal |
| Nebulite (`mvtink_nebulite`) | Infernal Obsidian-Nebulite Alloy | `mvtink_alloy_infernal_obsidian_nebulite` | Infernal, Void, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Infernal Obsidian-Nether Bismuth Alloy | `mvtink_alloy_infernal_obsidian_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Infernal Obsidian-Nether Tungsten Alloy | `mvtink_alloy_infernal_obsidian_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Infernal Obsidian-Netherite Scrap Shard Alloy | `mvtink_alloy_infernal_obsidian_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Infernal Obsidian-Nickel Alloy | `mvtink_alloy_infernal_obsidian_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Infernal Obsidian-Null-Shard Alloy | `mvtink_alloy_infernal_obsidian_null_shard` | Infernal, Void, Terrain |
| Obsidian (`mvtink_obsidian`) | Infernal Obsidian-Obsidian Alloy | `mvtink_alloy_infernal_obsidian_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Infernal Obsidian-Obsidianite Alloy | `mvtink_alloy_infernal_obsidian_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Infernal Obsidian-Opal Alloy | `mvtink_alloy_infernal_obsidian_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Infernal Obsidian-Ender Pearl Core Alloy | `mvtink_alloy_infernal_obsidian_pearl_core` | Infernal, Void, Terrain |
| Phantomite (`mvtink_phantomite`) | Infernal Obsidian-Phantomite Alloy | `mvtink_alloy_infernal_obsidian_phantomite` | Infernal, Void, Terrain |
| Platinum (`mvtink_platinum`) | Infernal Obsidian-Platinum Alloy | `mvtink_alloy_infernal_obsidian_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Infernal Obsidian-Prismarine Alloy | `mvtink_alloy_infernal_obsidian_prismarine` | Infernal, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Infernal Obsidian-Pyrite Alloy | `mvtink_alloy_infernal_obsidian_pyrite` | Infernal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Infernal Obsidian-Pyrophore Alloy | `mvtink_alloy_infernal_obsidian_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Infernal Obsidian-Nether Quartz Alloy | `mvtink_alloy_infernal_obsidian_quartz` | Infernal, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Infernal Obsidian-Redstone Alloy | `mvtink_alloy_infernal_obsidian_redstone` | Infernal, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Infernal Obsidian-Resonite Alloy | `mvtink_alloy_infernal_obsidian_resonite` | Infernal, Void, Terrain |
| Ruby (`mvtink_ruby`) | Infernal Obsidian-Ruby Alloy | `mvtink_alloy_infernal_obsidian_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Infernal Obsidian-Sanguinite Alloy | `mvtink_alloy_infernal_obsidian_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Infernal Obsidian-Sapphire Alloy | `mvtink_alloy_infernal_obsidian_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Infernal Obsidian-Shadowgem Alloy | `mvtink_alloy_infernal_obsidian_shadowgem` | Infernal, Void, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Infernal Obsidian-Shulkerite Alloy | `mvtink_alloy_infernal_obsidian_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Infernal Obsidian-Silver Alloy | `mvtink_alloy_infernal_obsidian_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Infernal Obsidian-Singularite Alloy | `mvtink_alloy_infernal_obsidian_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Infernal Obsidian-Soul Glass Crystal Alloy | `mvtink_alloy_infernal_obsidian_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Infernal Obsidian-Spatial Platinum Alloy | `mvtink_alloy_infernal_obsidian_spatial_platinum` | Infernal, Void, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Infernal Obsidian-Starlight Silver Alloy | `mvtink_alloy_infernal_obsidian_starlight_silver` | Infernal, Void, Terrain |
| Steel (`mvtink_steel`) | Infernal Obsidian-Steel Alloy | `mvtink_alloy_infernal_obsidian_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Infernal Obsidian-Stibnite Alloy | `mvtink_alloy_infernal_obsidian_stibnite` | Infernal, Terrain, Brutal |
| Sulfur (`mvtink_sulfur`) | Infernal Obsidian-Sulfur Alloy | `mvtink_alloy_infernal_obsidian_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Infernal Obsidian-Talc Alloy | `mvtink_alloy_infernal_obsidian_talc` | Infernal, Terrain, Brutal |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Infernal Obsidian-Tesseract Crystal Alloy | `mvtink_alloy_infernal_obsidian_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Infernal Obsidian-Tin Alloy | `mvtink_alloy_infernal_obsidian_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Infernal Obsidian-Titanium Alloy | `mvtink_alloy_infernal_obsidian_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Infernal Obsidian-Topaz Alloy | `mvtink_alloy_infernal_obsidian_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Infernal Obsidian-Tourmaline Alloy | `mvtink_alloy_infernal_obsidian_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Infernal Obsidian-Tungsten Alloy | `mvtink_alloy_infernal_obsidian_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Infernal Obsidian-Void Pyrite Alloy | `mvtink_alloy_infernal_obsidian_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Infernal Obsidian-Void Titanium Alloy | `mvtink_alloy_infernal_obsidian_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Infernal Obsidian-Voidstone Alloy | `mvtink_alloy_infernal_obsidian_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Infernal Obsidian-Compacted Volcanic Ash Alloy | `mvtink_alloy_infernal_obsidian_volcanic_ash` | Infernal, Terrain, Brutal |
| Warped Emerald (`mvtink_warped_emerald`) | Infernal Obsidian-Warped Emerald Alloy | `mvtink_alloy_infernal_obsidian_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Infernal Obsidian-Warped Quartz Alloy | `mvtink_alloy_infernal_obsidian_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Infernal Obsidian-Pure Weeping Shard Alloy | `mvtink_alloy_infernal_obsidian_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Infernal Obsidian-Witherite Alloy | `mvtink_alloy_infernal_obsidian_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Infernal Obsidian-Zero-Point Shard Alloy | `mvtink_alloy_infernal_obsidian_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Infernal Obsidian-Zinc Alloy | `mvtink_alloy_infernal_obsidian_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Infernal Obsidian-Zircon Alloy | `mvtink_alloy_infernal_obsidian_zircon` | Infernal, Terrain, Resonant |

#### Netherite-Infused Quartz · `mvtink_infused_quartz` · 58 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Iron (`mvtink_iron`) | Netherite-Infused Quartz-Iron Alloy | `mvtink_alloy_infused_quartz_iron` | Infernal, Primal, Tempered |
| Jade (`mvtink_jade`) | Netherite-Infused Quartz-Jade Alloy | `mvtink_alloy_infused_quartz_jade` | Infernal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Netherite-Infused Quartz-Kaolinite Alloy | `mvtink_alloy_infused_quartz_kaolinite` | Infernal, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Netherite-Infused Quartz-Lapis Lazuli Alloy | `mvtink_alloy_infused_quartz_lapis` | Infernal, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Netherite-Infused Quartz-Lapis Matrix Alloy | `mvtink_alloy_infused_quartz_lapis_matrix` | Infernal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Netherite-Infused Quartz-Magma Brimstone Alloy | `mvtink_alloy_infused_quartz_magma_brimstone` | Infernal, Terrain, Resonant |
| Magmacite (`mvtink_magmacite`) | Netherite-Infused Quartz-Magmacite Alloy | `mvtink_alloy_infused_quartz_magmacite` | Infernal, Resonant, Swift |
| Magnetite (`mvtink_magnetite`) | Netherite-Infused Quartz-Magnetite Alloy | `mvtink_alloy_infused_quartz_magnetite` | Infernal, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Netherite-Infused Quartz-Malachite Alloy | `mvtink_alloy_infused_quartz_malachite` | Infernal, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Netherite-Infused Quartz-Nebulite Alloy | `mvtink_alloy_infused_quartz_nebulite` | Infernal, Void, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Netherite-Infused Quartz-Nether Bismuth Alloy | `mvtink_alloy_infused_quartz_nether_bismuth` | Infernal, Tempered, Resonant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Netherite-Infused Quartz-Nether Tungsten Alloy | `mvtink_alloy_infused_quartz_nether_tungsten` | Infernal, Tempered, Resonant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Netherite-Infused Quartz-Netherite Scrap Shard Alloy | `mvtink_alloy_infused_quartz_netherite_shard` | Infernal, Tempered, Resonant |
| Nickel (`mvtink_nickel`) | Netherite-Infused Quartz-Nickel Alloy | `mvtink_alloy_infused_quartz_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Netherite-Infused Quartz-Null-Shard Alloy | `mvtink_alloy_infused_quartz_null_shard` | Infernal, Void, Resonant |
| Obsidian (`mvtink_obsidian`) | Netherite-Infused Quartz-Obsidian Alloy | `mvtink_alloy_infused_quartz_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Netherite-Infused Quartz-Obsidianite Alloy | `mvtink_alloy_infused_quartz_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Netherite-Infused Quartz-Opal Alloy | `mvtink_alloy_infused_quartz_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Netherite-Infused Quartz-Ender Pearl Core Alloy | `mvtink_alloy_infused_quartz_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Netherite-Infused Quartz-Phantomite Alloy | `mvtink_alloy_infused_quartz_phantomite` | Infernal, Void, Resonant |
| Platinum (`mvtink_platinum`) | Netherite-Infused Quartz-Platinum Alloy | `mvtink_alloy_infused_quartz_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Netherite-Infused Quartz-Prismarine Alloy | `mvtink_alloy_infused_quartz_prismarine` | Infernal, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Netherite-Infused Quartz-Pyrite Alloy | `mvtink_alloy_infused_quartz_pyrite` | Infernal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Netherite-Infused Quartz-Pyrophore Alloy | `mvtink_alloy_infused_quartz_pyrophore` | Infernal, Resonant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Netherite-Infused Quartz-Nether Quartz Alloy | `mvtink_alloy_infused_quartz_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Netherite-Infused Quartz-Redstone Alloy | `mvtink_alloy_infused_quartz_redstone` | Infernal, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Netherite-Infused Quartz-Resonite Alloy | `mvtink_alloy_infused_quartz_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Netherite-Infused Quartz-Ruby Alloy | `mvtink_alloy_infused_quartz_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Netherite-Infused Quartz-Sanguinite Alloy | `mvtink_alloy_infused_quartz_sanguinite` | Infernal, Tempered, Resonant |
| Sapphire (`mvtink_sapphire`) | Netherite-Infused Quartz-Sapphire Alloy | `mvtink_alloy_infused_quartz_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Netherite-Infused Quartz-Shadowgem Alloy | `mvtink_alloy_infused_quartz_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Netherite-Infused Quartz-Shulkerite Alloy | `mvtink_alloy_infused_quartz_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Netherite-Infused Quartz-Silver Alloy | `mvtink_alloy_infused_quartz_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Netherite-Infused Quartz-Singularite Alloy | `mvtink_alloy_infused_quartz_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Netherite-Infused Quartz-Soul Glass Crystal Alloy | `mvtink_alloy_infused_quartz_soulsand_crystal` | Infernal, Resonant, Swift |
| Spatial Platinum (`mvtink_spatial_platinum`) | Netherite-Infused Quartz-Spatial Platinum Alloy | `mvtink_alloy_infused_quartz_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Netherite-Infused Quartz-Starlight Silver Alloy | `mvtink_alloy_infused_quartz_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Netherite-Infused Quartz-Steel Alloy | `mvtink_alloy_infused_quartz_steel` | Infernal, Tempered, Resonant |
| Stibnite (`mvtink_stibnite`) | Netherite-Infused Quartz-Stibnite Alloy | `mvtink_alloy_infused_quartz_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Netherite-Infused Quartz-Sulfur Alloy | `mvtink_alloy_infused_quartz_sulfur` | Infernal, Resonant, Volatile |
| Talc (`mvtink_talc`) | Netherite-Infused Quartz-Talc Alloy | `mvtink_alloy_infused_quartz_talc` | Infernal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Netherite-Infused Quartz-Tesseract Crystal Alloy | `mvtink_alloy_infused_quartz_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Netherite-Infused Quartz-Tin Alloy | `mvtink_alloy_infused_quartz_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Netherite-Infused Quartz-Titanium Alloy | `mvtink_alloy_infused_quartz_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Netherite-Infused Quartz-Topaz Alloy | `mvtink_alloy_infused_quartz_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Netherite-Infused Quartz-Tourmaline Alloy | `mvtink_alloy_infused_quartz_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Netherite-Infused Quartz-Tungsten Alloy | `mvtink_alloy_infused_quartz_tungsten` | Infernal, Tempered, Resonant |
| Void Pyrite (`mvtink_void_pyrite`) | Netherite-Infused Quartz-Void Pyrite Alloy | `mvtink_alloy_infused_quartz_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Netherite-Infused Quartz-Void Titanium Alloy | `mvtink_alloy_infused_quartz_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Netherite-Infused Quartz-Voidstone Alloy | `mvtink_alloy_infused_quartz_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Netherite-Infused Quartz-Compacted Volcanic Ash Alloy | `mvtink_alloy_infused_quartz_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Netherite-Infused Quartz-Warped Emerald Alloy | `mvtink_alloy_infused_quartz_warped_emerald` | Infernal, Radiant, Resonant |
| Warped Quartz (`mvtink_warped_quartz`) | Netherite-Infused Quartz-Warped Quartz Alloy | `mvtink_alloy_infused_quartz_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Netherite-Infused Quartz-Pure Weeping Shard Alloy | `mvtink_alloy_infused_quartz_weeping_shard` | Infernal, Resonant, Swift |
| Witherite (`mvtink_witherite`) | Netherite-Infused Quartz-Witherite Alloy | `mvtink_alloy_infused_quartz_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Netherite-Infused Quartz-Zero-Point Shard Alloy | `mvtink_alloy_infused_quartz_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Netherite-Infused Quartz-Zinc Alloy | `mvtink_alloy_infused_quartz_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Netherite-Infused Quartz-Zircon Alloy | `mvtink_alloy_infused_quartz_zircon` | Infernal, Terrain, Resonant |

#### Magma Brimstone · `mvtink_magma_brimstone` · 52 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Magmacite (`mvtink_magmacite`) | Magma Brimstone-Magmacite Alloy | `mvtink_alloy_magma_brimstone_magmacite` | Infernal, Terrain, Resonant |
| Magnetite (`mvtink_magnetite`) | Magma Brimstone-Magnetite Alloy | `mvtink_alloy_magma_brimstone_magnetite` | Infernal, Terrain |
| Malachite (`mvtink_malachite`) | Magma Brimstone-Malachite Alloy | `mvtink_alloy_magma_brimstone_malachite` | Infernal, Terrain |
| Nebulite (`mvtink_nebulite`) | Magma Brimstone-Nebulite Alloy | `mvtink_alloy_magma_brimstone_nebulite` | Infernal, Void, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Magma Brimstone-Nether Bismuth Alloy | `mvtink_alloy_magma_brimstone_nether_bismuth` | Infernal, Terrain, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Magma Brimstone-Nether Tungsten Alloy | `mvtink_alloy_magma_brimstone_nether_tungsten` | Infernal, Terrain, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Magma Brimstone-Netherite Scrap Shard Alloy | `mvtink_alloy_magma_brimstone_netherite_shard` | Infernal, Terrain, Tempered |
| Nickel (`mvtink_nickel`) | Magma Brimstone-Nickel Alloy | `mvtink_alloy_magma_brimstone_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Magma Brimstone-Null-Shard Alloy | `mvtink_alloy_magma_brimstone_null_shard` | Infernal, Void, Terrain |
| Obsidian (`mvtink_obsidian`) | Magma Brimstone-Obsidian Alloy | `mvtink_alloy_magma_brimstone_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Magma Brimstone-Obsidianite Alloy | `mvtink_alloy_magma_brimstone_obsidianite` | Infernal, Terrain, Brutal |
| Opal (`mvtink_opal`) | Magma Brimstone-Opal Alloy | `mvtink_alloy_magma_brimstone_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Magma Brimstone-Ender Pearl Core Alloy | `mvtink_alloy_magma_brimstone_pearl_core` | Infernal, Void, Terrain |
| Phantomite (`mvtink_phantomite`) | Magma Brimstone-Phantomite Alloy | `mvtink_alloy_magma_brimstone_phantomite` | Infernal, Void, Terrain |
| Platinum (`mvtink_platinum`) | Magma Brimstone-Platinum Alloy | `mvtink_alloy_magma_brimstone_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Magma Brimstone-Prismarine Alloy | `mvtink_alloy_magma_brimstone_prismarine` | Infernal, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Magma Brimstone-Pyrite Alloy | `mvtink_alloy_magma_brimstone_pyrite` | Infernal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Magma Brimstone-Pyrophore Alloy | `mvtink_alloy_magma_brimstone_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Magma Brimstone-Nether Quartz Alloy | `mvtink_alloy_magma_brimstone_quartz` | Infernal, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Magma Brimstone-Redstone Alloy | `mvtink_alloy_magma_brimstone_redstone` | Infernal, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Magma Brimstone-Resonite Alloy | `mvtink_alloy_magma_brimstone_resonite` | Infernal, Void, Terrain |
| Ruby (`mvtink_ruby`) | Magma Brimstone-Ruby Alloy | `mvtink_alloy_magma_brimstone_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Magma Brimstone-Sanguinite Alloy | `mvtink_alloy_magma_brimstone_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Magma Brimstone-Sapphire Alloy | `mvtink_alloy_magma_brimstone_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Magma Brimstone-Shadowgem Alloy | `mvtink_alloy_magma_brimstone_shadowgem` | Infernal, Void, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Magma Brimstone-Shulkerite Alloy | `mvtink_alloy_magma_brimstone_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Magma Brimstone-Silver Alloy | `mvtink_alloy_magma_brimstone_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Magma Brimstone-Singularite Alloy | `mvtink_alloy_magma_brimstone_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Magma Brimstone-Soul Glass Crystal Alloy | `mvtink_alloy_magma_brimstone_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Magma Brimstone-Spatial Platinum Alloy | `mvtink_alloy_magma_brimstone_spatial_platinum` | Infernal, Void, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Magma Brimstone-Starlight Silver Alloy | `mvtink_alloy_magma_brimstone_starlight_silver` | Infernal, Void, Terrain |
| Steel (`mvtink_steel`) | Magma Brimstone-Steel Alloy | `mvtink_alloy_magma_brimstone_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Magma Brimstone-Stibnite Alloy | `mvtink_alloy_magma_brimstone_stibnite` | Infernal, Terrain |
| Sulfur (`mvtink_sulfur`) | Magma Brimstone-Sulfur Alloy | `mvtink_alloy_magma_brimstone_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Magma Brimstone-Talc Alloy | `mvtink_alloy_magma_brimstone_talc` | Infernal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Magma Brimstone-Tesseract Crystal Alloy | `mvtink_alloy_magma_brimstone_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Magma Brimstone-Tin Alloy | `mvtink_alloy_magma_brimstone_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Magma Brimstone-Titanium Alloy | `mvtink_alloy_magma_brimstone_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Magma Brimstone-Topaz Alloy | `mvtink_alloy_magma_brimstone_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Magma Brimstone-Tourmaline Alloy | `mvtink_alloy_magma_brimstone_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Magma Brimstone-Tungsten Alloy | `mvtink_alloy_magma_brimstone_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Magma Brimstone-Void Pyrite Alloy | `mvtink_alloy_magma_brimstone_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Magma Brimstone-Void Titanium Alloy | `mvtink_alloy_magma_brimstone_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Magma Brimstone-Voidstone Alloy | `mvtink_alloy_magma_brimstone_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Magma Brimstone-Compacted Volcanic Ash Alloy | `mvtink_alloy_magma_brimstone_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Magma Brimstone-Warped Emerald Alloy | `mvtink_alloy_magma_brimstone_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Magma Brimstone-Warped Quartz Alloy | `mvtink_alloy_magma_brimstone_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Magma Brimstone-Pure Weeping Shard Alloy | `mvtink_alloy_magma_brimstone_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Magma Brimstone-Witherite Alloy | `mvtink_alloy_magma_brimstone_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Magma Brimstone-Zero-Point Shard Alloy | `mvtink_alloy_magma_brimstone_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Magma Brimstone-Zinc Alloy | `mvtink_alloy_magma_brimstone_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Magma Brimstone-Zircon Alloy | `mvtink_alloy_magma_brimstone_zircon` | Infernal, Terrain, Resonant |

#### Magmacite · `mvtink_magmacite` · 51 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Magnetite (`mvtink_magnetite`) | Magmacite-Magnetite Alloy | `mvtink_alloy_magmacite_magnetite` | Infernal, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Magmacite-Malachite Alloy | `mvtink_alloy_magmacite_malachite` | Infernal, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Magmacite-Nebulite Alloy | `mvtink_alloy_magmacite_nebulite` | Infernal, Void, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Magmacite-Nether Bismuth Alloy | `mvtink_alloy_magmacite_nether_bismuth` | Infernal, Tempered, Resonant |
| Nether Tungsten (`mvtink_nether_tungsten`) | Magmacite-Nether Tungsten Alloy | `mvtink_alloy_magmacite_nether_tungsten` | Infernal, Tempered, Resonant |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Magmacite-Netherite Scrap Shard Alloy | `mvtink_alloy_magmacite_netherite_shard` | Infernal, Tempered, Resonant |
| Nickel (`mvtink_nickel`) | Magmacite-Nickel Alloy | `mvtink_alloy_magmacite_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Magmacite-Null-Shard Alloy | `mvtink_alloy_magmacite_null_shard` | Infernal, Void, Resonant |
| Obsidian (`mvtink_obsidian`) | Magmacite-Obsidian Alloy | `mvtink_alloy_magmacite_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Magmacite-Obsidianite Alloy | `mvtink_alloy_magmacite_obsidianite` | Infernal, Terrain, Resonant |
| Opal (`mvtink_opal`) | Magmacite-Opal Alloy | `mvtink_alloy_magmacite_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Magmacite-Ender Pearl Core Alloy | `mvtink_alloy_magmacite_pearl_core` | Infernal, Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Magmacite-Phantomite Alloy | `mvtink_alloy_magmacite_phantomite` | Infernal, Void, Resonant |
| Platinum (`mvtink_platinum`) | Magmacite-Platinum Alloy | `mvtink_alloy_magmacite_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Magmacite-Prismarine Alloy | `mvtink_alloy_magmacite_prismarine` | Infernal, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Magmacite-Pyrite Alloy | `mvtink_alloy_magmacite_pyrite` | Infernal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Magmacite-Pyrophore Alloy | `mvtink_alloy_magmacite_pyrophore` | Infernal, Resonant, Volatile |
| Nether Quartz (`mvtink_quartz`) | Magmacite-Nether Quartz Alloy | `mvtink_alloy_magmacite_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Magmacite-Redstone Alloy | `mvtink_alloy_magmacite_redstone` | Infernal, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Magmacite-Resonite Alloy | `mvtink_alloy_magmacite_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Magmacite-Ruby Alloy | `mvtink_alloy_magmacite_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Magmacite-Sanguinite Alloy | `mvtink_alloy_magmacite_sanguinite` | Infernal, Tempered, Resonant |
| Sapphire (`mvtink_sapphire`) | Magmacite-Sapphire Alloy | `mvtink_alloy_magmacite_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Magmacite-Shadowgem Alloy | `mvtink_alloy_magmacite_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Magmacite-Shulkerite Alloy | `mvtink_alloy_magmacite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Magmacite-Silver Alloy | `mvtink_alloy_magmacite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Magmacite-Singularite Alloy | `mvtink_alloy_magmacite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Magmacite-Soul Glass Crystal Alloy | `mvtink_alloy_magmacite_soulsand_crystal` | Infernal, Resonant, Brutal |
| Spatial Platinum (`mvtink_spatial_platinum`) | Magmacite-Spatial Platinum Alloy | `mvtink_alloy_magmacite_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Magmacite-Starlight Silver Alloy | `mvtink_alloy_magmacite_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Magmacite-Steel Alloy | `mvtink_alloy_magmacite_steel` | Infernal, Tempered, Resonant |
| Stibnite (`mvtink_stibnite`) | Magmacite-Stibnite Alloy | `mvtink_alloy_magmacite_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Magmacite-Sulfur Alloy | `mvtink_alloy_magmacite_sulfur` | Infernal, Resonant, Volatile |
| Talc (`mvtink_talc`) | Magmacite-Talc Alloy | `mvtink_alloy_magmacite_talc` | Infernal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Magmacite-Tesseract Crystal Alloy | `mvtink_alloy_magmacite_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Magmacite-Tin Alloy | `mvtink_alloy_magmacite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Magmacite-Titanium Alloy | `mvtink_alloy_magmacite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Magmacite-Topaz Alloy | `mvtink_alloy_magmacite_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Magmacite-Tourmaline Alloy | `mvtink_alloy_magmacite_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Magmacite-Tungsten Alloy | `mvtink_alloy_magmacite_tungsten` | Infernal, Tempered, Resonant |
| Void Pyrite (`mvtink_void_pyrite`) | Magmacite-Void Pyrite Alloy | `mvtink_alloy_magmacite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Magmacite-Void Titanium Alloy | `mvtink_alloy_magmacite_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Magmacite-Voidstone Alloy | `mvtink_alloy_magmacite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Magmacite-Compacted Volcanic Ash Alloy | `mvtink_alloy_magmacite_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Magmacite-Warped Emerald Alloy | `mvtink_alloy_magmacite_warped_emerald` | Infernal, Radiant, Resonant |
| Warped Quartz (`mvtink_warped_quartz`) | Magmacite-Warped Quartz Alloy | `mvtink_alloy_magmacite_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Magmacite-Pure Weeping Shard Alloy | `mvtink_alloy_magmacite_weeping_shard` | Infernal, Resonant, Brutal |
| Witherite (`mvtink_witherite`) | Magmacite-Witherite Alloy | `mvtink_alloy_magmacite_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Magmacite-Zero-Point Shard Alloy | `mvtink_alloy_magmacite_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Magmacite-Zinc Alloy | `mvtink_alloy_magmacite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Magmacite-Zircon Alloy | `mvtink_alloy_magmacite_zircon` | Infernal, Terrain, Resonant |

#### Nether Bismuth · `mvtink_nether_bismuth` · 47 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Nether Tungsten (`mvtink_nether_tungsten`) | Nether Bismuth-Nether Tungsten Alloy | `mvtink_alloy_nether_bismuth_nether_tungsten` | Infernal, Tempered, Brutal |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Nether Bismuth-Netherite Scrap Shard Alloy | `mvtink_alloy_nether_bismuth_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Nether Bismuth-Nickel Alloy | `mvtink_alloy_nether_bismuth_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Nether Bismuth-Null-Shard Alloy | `mvtink_alloy_nether_bismuth_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Nether Bismuth-Obsidian Alloy | `mvtink_alloy_nether_bismuth_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Nether Bismuth-Obsidianite Alloy | `mvtink_alloy_nether_bismuth_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Nether Bismuth-Opal Alloy | `mvtink_alloy_nether_bismuth_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Nether Bismuth-Ender Pearl Core Alloy | `mvtink_alloy_nether_bismuth_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Nether Bismuth-Phantomite Alloy | `mvtink_alloy_nether_bismuth_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Nether Bismuth-Platinum Alloy | `mvtink_alloy_nether_bismuth_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Nether Bismuth-Prismarine Alloy | `mvtink_alloy_nether_bismuth_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Nether Bismuth-Pyrite Alloy | `mvtink_alloy_nether_bismuth_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Nether Bismuth-Pyrophore Alloy | `mvtink_alloy_nether_bismuth_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Nether Bismuth-Nether Quartz Alloy | `mvtink_alloy_nether_bismuth_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Nether Bismuth-Redstone Alloy | `mvtink_alloy_nether_bismuth_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Nether Bismuth-Resonite Alloy | `mvtink_alloy_nether_bismuth_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Nether Bismuth-Ruby Alloy | `mvtink_alloy_nether_bismuth_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Nether Bismuth-Sanguinite Alloy | `mvtink_alloy_nether_bismuth_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Nether Bismuth-Sapphire Alloy | `mvtink_alloy_nether_bismuth_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Nether Bismuth-Shadowgem Alloy | `mvtink_alloy_nether_bismuth_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Nether Bismuth-Shulkerite Alloy | `mvtink_alloy_nether_bismuth_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Nether Bismuth-Silver Alloy | `mvtink_alloy_nether_bismuth_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Nether Bismuth-Singularite Alloy | `mvtink_alloy_nether_bismuth_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Nether Bismuth-Soul Glass Crystal Alloy | `mvtink_alloy_nether_bismuth_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Nether Bismuth-Spatial Platinum Alloy | `mvtink_alloy_nether_bismuth_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Nether Bismuth-Starlight Silver Alloy | `mvtink_alloy_nether_bismuth_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Nether Bismuth-Steel Alloy | `mvtink_alloy_nether_bismuth_steel` | Infernal, Tempered |
| Stibnite (`mvtink_stibnite`) | Nether Bismuth-Stibnite Alloy | `mvtink_alloy_nether_bismuth_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Nether Bismuth-Sulfur Alloy | `mvtink_alloy_nether_bismuth_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Nether Bismuth-Talc Alloy | `mvtink_alloy_nether_bismuth_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Nether Bismuth-Tesseract Crystal Alloy | `mvtink_alloy_nether_bismuth_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Nether Bismuth-Tin Alloy | `mvtink_alloy_nether_bismuth_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Nether Bismuth-Titanium Alloy | `mvtink_alloy_nether_bismuth_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Nether Bismuth-Topaz Alloy | `mvtink_alloy_nether_bismuth_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Nether Bismuth-Tourmaline Alloy | `mvtink_alloy_nether_bismuth_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Nether Bismuth-Tungsten Alloy | `mvtink_alloy_nether_bismuth_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Nether Bismuth-Void Pyrite Alloy | `mvtink_alloy_nether_bismuth_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Nether Bismuth-Void Titanium Alloy | `mvtink_alloy_nether_bismuth_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Nether Bismuth-Voidstone Alloy | `mvtink_alloy_nether_bismuth_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Nether Bismuth-Compacted Volcanic Ash Alloy | `mvtink_alloy_nether_bismuth_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Nether Bismuth-Warped Emerald Alloy | `mvtink_alloy_nether_bismuth_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Nether Bismuth-Warped Quartz Alloy | `mvtink_alloy_nether_bismuth_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Nether Bismuth-Pure Weeping Shard Alloy | `mvtink_alloy_nether_bismuth_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Nether Bismuth-Witherite Alloy | `mvtink_alloy_nether_bismuth_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Nether Bismuth-Zero-Point Shard Alloy | `mvtink_alloy_nether_bismuth_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Nether Bismuth-Zinc Alloy | `mvtink_alloy_nether_bismuth_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Nether Bismuth-Zircon Alloy | `mvtink_alloy_nether_bismuth_zircon` | Infernal, Terrain, Tempered |

#### Nether Tungsten · `mvtink_nether_tungsten` · 46 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Nether Tungsten-Netherite Scrap Shard Alloy | `mvtink_alloy_nether_tungsten_netherite_shard` | Infernal, Tempered, Swift |
| Nickel (`mvtink_nickel`) | Nether Tungsten-Nickel Alloy | `mvtink_alloy_nether_tungsten_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Nether Tungsten-Null-Shard Alloy | `mvtink_alloy_nether_tungsten_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Nether Tungsten-Obsidian Alloy | `mvtink_alloy_nether_tungsten_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Nether Tungsten-Obsidianite Alloy | `mvtink_alloy_nether_tungsten_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Nether Tungsten-Opal Alloy | `mvtink_alloy_nether_tungsten_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Nether Tungsten-Ender Pearl Core Alloy | `mvtink_alloy_nether_tungsten_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Nether Tungsten-Phantomite Alloy | `mvtink_alloy_nether_tungsten_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Nether Tungsten-Platinum Alloy | `mvtink_alloy_nether_tungsten_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Nether Tungsten-Prismarine Alloy | `mvtink_alloy_nether_tungsten_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Nether Tungsten-Pyrite Alloy | `mvtink_alloy_nether_tungsten_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Nether Tungsten-Pyrophore Alloy | `mvtink_alloy_nether_tungsten_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Nether Tungsten-Nether Quartz Alloy | `mvtink_alloy_nether_tungsten_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Nether Tungsten-Redstone Alloy | `mvtink_alloy_nether_tungsten_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Nether Tungsten-Resonite Alloy | `mvtink_alloy_nether_tungsten_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Nether Tungsten-Ruby Alloy | `mvtink_alloy_nether_tungsten_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Nether Tungsten-Sanguinite Alloy | `mvtink_alloy_nether_tungsten_sanguinite` | Infernal, Tempered, Brutal |
| Sapphire (`mvtink_sapphire`) | Nether Tungsten-Sapphire Alloy | `mvtink_alloy_nether_tungsten_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Nether Tungsten-Shadowgem Alloy | `mvtink_alloy_nether_tungsten_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Nether Tungsten-Shulkerite Alloy | `mvtink_alloy_nether_tungsten_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Nether Tungsten-Silver Alloy | `mvtink_alloy_nether_tungsten_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Nether Tungsten-Singularite Alloy | `mvtink_alloy_nether_tungsten_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Nether Tungsten-Soul Glass Crystal Alloy | `mvtink_alloy_nether_tungsten_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Nether Tungsten-Spatial Platinum Alloy | `mvtink_alloy_nether_tungsten_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Nether Tungsten-Starlight Silver Alloy | `mvtink_alloy_nether_tungsten_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Nether Tungsten-Steel Alloy | `mvtink_alloy_nether_tungsten_steel` | Infernal, Tempered, Brutal |
| Stibnite (`mvtink_stibnite`) | Nether Tungsten-Stibnite Alloy | `mvtink_alloy_nether_tungsten_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Nether Tungsten-Sulfur Alloy | `mvtink_alloy_nether_tungsten_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Nether Tungsten-Talc Alloy | `mvtink_alloy_nether_tungsten_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Nether Tungsten-Tesseract Crystal Alloy | `mvtink_alloy_nether_tungsten_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Nether Tungsten-Tin Alloy | `mvtink_alloy_nether_tungsten_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Nether Tungsten-Titanium Alloy | `mvtink_alloy_nether_tungsten_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Nether Tungsten-Topaz Alloy | `mvtink_alloy_nether_tungsten_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Nether Tungsten-Tourmaline Alloy | `mvtink_alloy_nether_tungsten_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Nether Tungsten-Tungsten Alloy | `mvtink_alloy_nether_tungsten_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Nether Tungsten-Void Pyrite Alloy | `mvtink_alloy_nether_tungsten_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Nether Tungsten-Void Titanium Alloy | `mvtink_alloy_nether_tungsten_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Nether Tungsten-Voidstone Alloy | `mvtink_alloy_nether_tungsten_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Nether Tungsten-Compacted Volcanic Ash Alloy | `mvtink_alloy_nether_tungsten_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Nether Tungsten-Warped Emerald Alloy | `mvtink_alloy_nether_tungsten_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Nether Tungsten-Warped Quartz Alloy | `mvtink_alloy_nether_tungsten_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Nether Tungsten-Pure Weeping Shard Alloy | `mvtink_alloy_nether_tungsten_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Nether Tungsten-Witherite Alloy | `mvtink_alloy_nether_tungsten_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Nether Tungsten-Zero-Point Shard Alloy | `mvtink_alloy_nether_tungsten_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Nether Tungsten-Zinc Alloy | `mvtink_alloy_nether_tungsten_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Nether Tungsten-Zircon Alloy | `mvtink_alloy_nether_tungsten_zircon` | Infernal, Terrain, Tempered |

#### Netherite Scrap Shard · `mvtink_netherite_shard` · 45 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Nickel (`mvtink_nickel`) | Netherite Scrap Shard-Nickel Alloy | `mvtink_alloy_netherite_shard_nickel` | Infernal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Netherite Scrap Shard-Null-Shard Alloy | `mvtink_alloy_netherite_shard_null_shard` | Infernal, Void, Tempered |
| Obsidian (`mvtink_obsidian`) | Netherite Scrap Shard-Obsidian Alloy | `mvtink_alloy_netherite_shard_obsidian` | Infernal, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Netherite Scrap Shard-Obsidianite Alloy | `mvtink_alloy_netherite_shard_obsidianite` | Infernal, Terrain, Tempered |
| Opal (`mvtink_opal`) | Netherite Scrap Shard-Opal Alloy | `mvtink_alloy_netherite_shard_opal` | Infernal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Netherite Scrap Shard-Ender Pearl Core Alloy | `mvtink_alloy_netherite_shard_pearl_core` | Infernal, Void, Tempered |
| Phantomite (`mvtink_phantomite`) | Netherite Scrap Shard-Phantomite Alloy | `mvtink_alloy_netherite_shard_phantomite` | Infernal, Void, Tempered |
| Platinum (`mvtink_platinum`) | Netherite Scrap Shard-Platinum Alloy | `mvtink_alloy_netherite_shard_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Netherite Scrap Shard-Prismarine Alloy | `mvtink_alloy_netherite_shard_prismarine` | Infernal, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Netherite Scrap Shard-Pyrite Alloy | `mvtink_alloy_netherite_shard_pyrite` | Infernal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Netherite Scrap Shard-Pyrophore Alloy | `mvtink_alloy_netherite_shard_pyrophore` | Infernal, Tempered, Volatile |
| Nether Quartz (`mvtink_quartz`) | Netherite Scrap Shard-Nether Quartz Alloy | `mvtink_alloy_netherite_shard_quartz` | Infernal, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Netherite Scrap Shard-Redstone Alloy | `mvtink_alloy_netherite_shard_redstone` | Infernal, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Netherite Scrap Shard-Resonite Alloy | `mvtink_alloy_netherite_shard_resonite` | Infernal, Void, Tempered |
| Ruby (`mvtink_ruby`) | Netherite Scrap Shard-Ruby Alloy | `mvtink_alloy_netherite_shard_ruby` | Infernal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Netherite Scrap Shard-Sanguinite Alloy | `mvtink_alloy_netherite_shard_sanguinite` | Infernal, Tempered, Swift |
| Sapphire (`mvtink_sapphire`) | Netherite Scrap Shard-Sapphire Alloy | `mvtink_alloy_netherite_shard_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Netherite Scrap Shard-Shadowgem Alloy | `mvtink_alloy_netherite_shard_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Netherite Scrap Shard-Shulkerite Alloy | `mvtink_alloy_netherite_shard_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Netherite Scrap Shard-Silver Alloy | `mvtink_alloy_netherite_shard_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Netherite Scrap Shard-Singularite Alloy | `mvtink_alloy_netherite_shard_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Netherite Scrap Shard-Soul Glass Crystal Alloy | `mvtink_alloy_netherite_shard_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Netherite Scrap Shard-Spatial Platinum Alloy | `mvtink_alloy_netherite_shard_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Netherite Scrap Shard-Starlight Silver Alloy | `mvtink_alloy_netherite_shard_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Netherite Scrap Shard-Steel Alloy | `mvtink_alloy_netherite_shard_steel` | Infernal, Tempered, Swift |
| Stibnite (`mvtink_stibnite`) | Netherite Scrap Shard-Stibnite Alloy | `mvtink_alloy_netherite_shard_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Netherite Scrap Shard-Sulfur Alloy | `mvtink_alloy_netherite_shard_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Netherite Scrap Shard-Talc Alloy | `mvtink_alloy_netherite_shard_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Netherite Scrap Shard-Tesseract Crystal Alloy | `mvtink_alloy_netherite_shard_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Netherite Scrap Shard-Tin Alloy | `mvtink_alloy_netherite_shard_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Netherite Scrap Shard-Titanium Alloy | `mvtink_alloy_netherite_shard_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Netherite Scrap Shard-Topaz Alloy | `mvtink_alloy_netherite_shard_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Netherite Scrap Shard-Tourmaline Alloy | `mvtink_alloy_netherite_shard_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Netherite Scrap Shard-Tungsten Alloy | `mvtink_alloy_netherite_shard_tungsten` | Infernal, Tempered, Swift |
| Void Pyrite (`mvtink_void_pyrite`) | Netherite Scrap Shard-Void Pyrite Alloy | `mvtink_alloy_netherite_shard_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Netherite Scrap Shard-Void Titanium Alloy | `mvtink_alloy_netherite_shard_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Netherite Scrap Shard-Voidstone Alloy | `mvtink_alloy_netherite_shard_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Netherite Scrap Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_netherite_shard_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Netherite Scrap Shard-Warped Emerald Alloy | `mvtink_alloy_netherite_shard_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Netherite Scrap Shard-Warped Quartz Alloy | `mvtink_alloy_netherite_shard_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Netherite Scrap Shard-Pure Weeping Shard Alloy | `mvtink_alloy_netherite_shard_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Netherite Scrap Shard-Witherite Alloy | `mvtink_alloy_netherite_shard_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Netherite Scrap Shard-Zero-Point Shard Alloy | `mvtink_alloy_netherite_shard_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Netherite Scrap Shard-Zinc Alloy | `mvtink_alloy_netherite_shard_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Netherite Scrap Shard-Zircon Alloy | `mvtink_alloy_netherite_shard_zircon` | Infernal, Terrain, Tempered |

#### Obsidianite · `mvtink_obsidianite` · 41 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Opal (`mvtink_opal`) | Obsidianite-Opal Alloy | `mvtink_alloy_obsidianite_opal` | Infernal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Obsidianite-Ender Pearl Core Alloy | `mvtink_alloy_obsidianite_pearl_core` | Infernal, Void, Terrain |
| Phantomite (`mvtink_phantomite`) | Obsidianite-Phantomite Alloy | `mvtink_alloy_obsidianite_phantomite` | Infernal, Void, Terrain |
| Platinum (`mvtink_platinum`) ★ | Shadow Platinum | `mvtink_shadow_platinum` | Infernal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Obsidianite-Prismarine Alloy | `mvtink_alloy_obsidianite_prismarine` | Infernal, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Obsidianite-Pyrite Alloy | `mvtink_alloy_obsidianite_pyrite` | Infernal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Obsidianite-Pyrophore Alloy | `mvtink_alloy_obsidianite_pyrophore` | Infernal, Terrain, Volatile |
| Nether Quartz (`mvtink_quartz`) | Obsidianite-Nether Quartz Alloy | `mvtink_alloy_obsidianite_quartz` | Infernal, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Obsidianite-Redstone Alloy | `mvtink_alloy_obsidianite_redstone` | Infernal, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Obsidianite-Resonite Alloy | `mvtink_alloy_obsidianite_resonite` | Infernal, Void, Terrain |
| Ruby (`mvtink_ruby`) | Obsidianite-Ruby Alloy | `mvtink_alloy_obsidianite_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Obsidianite-Sanguinite Alloy | `mvtink_alloy_obsidianite_sanguinite` | Infernal, Terrain, Tempered |
| Sapphire (`mvtink_sapphire`) | Obsidianite-Sapphire Alloy | `mvtink_alloy_obsidianite_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Obsidianite-Shadowgem Alloy | `mvtink_alloy_obsidianite_shadowgem` | Infernal, Void, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Obsidianite-Shulkerite Alloy | `mvtink_alloy_obsidianite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Obsidianite-Silver Alloy | `mvtink_alloy_obsidianite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Obsidianite-Singularite Alloy | `mvtink_alloy_obsidianite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Obsidianite-Soul Glass Crystal Alloy | `mvtink_alloy_obsidianite_soulsand_crystal` | Infernal, Terrain, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Obsidianite-Spatial Platinum Alloy | `mvtink_alloy_obsidianite_spatial_platinum` | Infernal, Void, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Obsidianite-Starlight Silver Alloy | `mvtink_alloy_obsidianite_starlight_silver` | Infernal, Void, Terrain |
| Steel (`mvtink_steel`) | Obsidianite-Steel Alloy | `mvtink_alloy_obsidianite_steel` | Infernal, Terrain, Tempered |
| Stibnite (`mvtink_stibnite`) | Obsidianite-Stibnite Alloy | `mvtink_alloy_obsidianite_stibnite` | Infernal, Terrain, Brutal |
| Sulfur (`mvtink_sulfur`) | Obsidianite-Sulfur Alloy | `mvtink_alloy_obsidianite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Obsidianite-Talc Alloy | `mvtink_alloy_obsidianite_talc` | Infernal, Terrain, Brutal |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Obsidianite-Tesseract Crystal Alloy | `mvtink_alloy_obsidianite_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Obsidianite-Tin Alloy | `mvtink_alloy_obsidianite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Obsidianite-Titanium Alloy | `mvtink_alloy_obsidianite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Obsidianite-Topaz Alloy | `mvtink_alloy_obsidianite_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Obsidianite-Tourmaline Alloy | `mvtink_alloy_obsidianite_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Obsidianite-Tungsten Alloy | `mvtink_alloy_obsidianite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Obsidianite-Void Pyrite Alloy | `mvtink_alloy_obsidianite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Obsidianite-Void Titanium Alloy | `mvtink_alloy_obsidianite_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Obsidianite-Voidstone Alloy | `mvtink_alloy_obsidianite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Obsidianite-Compacted Volcanic Ash Alloy | `mvtink_alloy_obsidianite_volcanic_ash` | Infernal, Terrain, Brutal |
| Warped Emerald (`mvtink_warped_emerald`) | Obsidianite-Warped Emerald Alloy | `mvtink_alloy_obsidianite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Obsidianite-Warped Quartz Alloy | `mvtink_alloy_obsidianite_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Obsidianite-Pure Weeping Shard Alloy | `mvtink_alloy_obsidianite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Obsidianite-Witherite Alloy | `mvtink_alloy_obsidianite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Obsidianite-Zero-Point Shard Alloy | `mvtink_alloy_obsidianite_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Obsidianite-Zinc Alloy | `mvtink_alloy_obsidianite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Obsidianite-Zircon Alloy | `mvtink_alloy_obsidianite_zircon` | Infernal, Terrain, Resonant |

#### Pyrophore · `mvtink_pyrophore` · 34 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Nether Quartz (`mvtink_quartz`) | Pyrophore-Nether Quartz Alloy | `mvtink_alloy_pyrophore_quartz` | Infernal, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Pyrophore-Redstone Alloy | `mvtink_alloy_pyrophore_redstone` | Infernal, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Pyrophore-Resonite Alloy | `mvtink_alloy_pyrophore_resonite` | Infernal, Void, Resonant |
| Ruby (`mvtink_ruby`) | Pyrophore-Ruby Alloy | `mvtink_alloy_pyrophore_ruby` | Infernal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Pyrophore-Sanguinite Alloy | `mvtink_alloy_pyrophore_sanguinite` | Infernal, Tempered, Volatile |
| Sapphire (`mvtink_sapphire`) | Pyrophore-Sapphire Alloy | `mvtink_alloy_pyrophore_sapphire` | Infernal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Pyrophore-Shadowgem Alloy | `mvtink_alloy_pyrophore_shadowgem` | Infernal, Void, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Pyrophore-Shulkerite Alloy | `mvtink_alloy_pyrophore_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Pyrophore-Silver Alloy | `mvtink_alloy_pyrophore_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Pyrophore-Singularite Alloy | `mvtink_alloy_pyrophore_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Pyrophore-Soul Glass Crystal Alloy | `mvtink_alloy_pyrophore_soulsand_crystal` | Infernal, Resonant, Volatile |
| Spatial Platinum (`mvtink_spatial_platinum`) | Pyrophore-Spatial Platinum Alloy | `mvtink_alloy_pyrophore_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Pyrophore-Starlight Silver Alloy | `mvtink_alloy_pyrophore_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Pyrophore-Steel Alloy | `mvtink_alloy_pyrophore_steel` | Infernal, Tempered, Volatile |
| Stibnite (`mvtink_stibnite`) | Pyrophore-Stibnite Alloy | `mvtink_alloy_pyrophore_stibnite` | Infernal, Terrain, Volatile |
| Sulfur (`mvtink_sulfur`) | Pyrophore-Sulfur Alloy | `mvtink_alloy_pyrophore_sulfur` | Infernal, Volatile, Brutal |
| Talc (`mvtink_talc`) | Pyrophore-Talc Alloy | `mvtink_alloy_pyrophore_talc` | Infernal, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Pyrophore-Tesseract Crystal Alloy | `mvtink_alloy_pyrophore_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Pyrophore-Tin Alloy | `mvtink_alloy_pyrophore_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Pyrophore-Titanium Alloy | `mvtink_alloy_pyrophore_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Pyrophore-Topaz Alloy | `mvtink_alloy_pyrophore_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Pyrophore-Tourmaline Alloy | `mvtink_alloy_pyrophore_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Pyrophore-Tungsten Alloy | `mvtink_alloy_pyrophore_tungsten` | Infernal, Tempered, Volatile |
| Void Pyrite (`mvtink_void_pyrite`) | Pyrophore-Void Pyrite Alloy | `mvtink_alloy_pyrophore_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Pyrophore-Void Titanium Alloy | `mvtink_alloy_pyrophore_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Pyrophore-Voidstone Alloy | `mvtink_alloy_pyrophore_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Pyrophore-Compacted Volcanic Ash Alloy | `mvtink_alloy_pyrophore_volcanic_ash` | Infernal, Terrain, Volatile |
| Warped Emerald (`mvtink_warped_emerald`) | Pyrophore-Warped Emerald Alloy | `mvtink_alloy_pyrophore_warped_emerald` | Infernal, Radiant, Volatile |
| Warped Quartz (`mvtink_warped_quartz`) | Pyrophore-Warped Quartz Alloy | `mvtink_alloy_pyrophore_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Pyrophore-Pure Weeping Shard Alloy | `mvtink_alloy_pyrophore_weeping_shard` | Infernal, Resonant, Volatile |
| Witherite (`mvtink_witherite`) | Pyrophore-Witherite Alloy | `mvtink_alloy_pyrophore_witherite` | Infernal, Terrain, Volatile |
| Zero-Point Shard (`mvtink_zero_point`) | Pyrophore-Zero-Point Shard Alloy | `mvtink_alloy_pyrophore_zero_point` | Infernal, Void, Volatile |
| Zinc (`mvtink_zinc`) | Pyrophore-Zinc Alloy | `mvtink_alloy_pyrophore_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Pyrophore-Zircon Alloy | `mvtink_alloy_pyrophore_zircon` | Infernal, Terrain, Resonant |

#### Sanguinite · `mvtink_sanguinite` · 29 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Sapphire (`mvtink_sapphire`) | Sanguinite-Sapphire Alloy | `mvtink_alloy_sanguinite_sapphire` | Infernal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Sanguinite-Shadowgem Alloy | `mvtink_alloy_sanguinite_shadowgem` | Infernal, Void, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Sanguinite-Shulkerite Alloy | `mvtink_alloy_sanguinite_shulkerite` | Infernal, Void, Terrain |
| Silver (`mvtink_silver`) | Sanguinite-Silver Alloy | `mvtink_alloy_sanguinite_silver` | Infernal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Sanguinite-Singularite Alloy | `mvtink_alloy_sanguinite_singularite` | Infernal, Void, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Sanguinite-Soul Glass Crystal Alloy | `mvtink_alloy_sanguinite_soulsand_crystal` | Infernal, Tempered, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Sanguinite-Spatial Platinum Alloy | `mvtink_alloy_sanguinite_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Sanguinite-Starlight Silver Alloy | `mvtink_alloy_sanguinite_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Sanguinite-Steel Alloy | `mvtink_alloy_sanguinite_steel` | Infernal, Tempered, Brutal |
| Stibnite (`mvtink_stibnite`) | Sanguinite-Stibnite Alloy | `mvtink_alloy_sanguinite_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Sanguinite-Sulfur Alloy | `mvtink_alloy_sanguinite_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Sanguinite-Talc Alloy | `mvtink_alloy_sanguinite_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Sanguinite-Tesseract Crystal Alloy | `mvtink_alloy_sanguinite_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Sanguinite-Tin Alloy | `mvtink_alloy_sanguinite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Sanguinite-Titanium Alloy | `mvtink_alloy_sanguinite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Sanguinite-Topaz Alloy | `mvtink_alloy_sanguinite_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Sanguinite-Tourmaline Alloy | `mvtink_alloy_sanguinite_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Sanguinite-Tungsten Alloy | `mvtink_alloy_sanguinite_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Sanguinite-Void Pyrite Alloy | `mvtink_alloy_sanguinite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Sanguinite-Void Titanium Alloy | `mvtink_alloy_sanguinite_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Sanguinite-Voidstone Alloy | `mvtink_alloy_sanguinite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Sanguinite-Compacted Volcanic Ash Alloy | `mvtink_alloy_sanguinite_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Sanguinite-Warped Emerald Alloy | `mvtink_alloy_sanguinite_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Sanguinite-Warped Quartz Alloy | `mvtink_alloy_sanguinite_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Sanguinite-Pure Weeping Shard Alloy | `mvtink_alloy_sanguinite_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Sanguinite-Witherite Alloy | `mvtink_alloy_sanguinite_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Sanguinite-Zero-Point Shard Alloy | `mvtink_alloy_sanguinite_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Sanguinite-Zinc Alloy | `mvtink_alloy_sanguinite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Sanguinite-Zircon Alloy | `mvtink_alloy_sanguinite_zircon` | Infernal, Terrain, Tempered |

#### Soul Glass Crystal · `mvtink_soulsand_crystal` · 23 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Spatial Platinum (`mvtink_spatial_platinum`) | Soul Glass Crystal-Spatial Platinum Alloy | `mvtink_alloy_soulsand_crystal_spatial_platinum` | Infernal, Void, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Soul Glass Crystal-Starlight Silver Alloy | `mvtink_alloy_soulsand_crystal_starlight_silver` | Infernal, Void, Tempered |
| Steel (`mvtink_steel`) | Soul Glass Crystal-Steel Alloy | `mvtink_alloy_soulsand_crystal_steel` | Infernal, Tempered, Resonant |
| Stibnite (`mvtink_stibnite`) | Soul Glass Crystal-Stibnite Alloy | `mvtink_alloy_soulsand_crystal_stibnite` | Infernal, Terrain, Resonant |
| Sulfur (`mvtink_sulfur`) | Soul Glass Crystal-Sulfur Alloy | `mvtink_alloy_soulsand_crystal_sulfur` | Infernal, Resonant, Volatile |
| Talc (`mvtink_talc`) | Soul Glass Crystal-Talc Alloy | `mvtink_alloy_soulsand_crystal_talc` | Infernal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Soul Glass Crystal-Tesseract Crystal Alloy | `mvtink_alloy_soulsand_crystal_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Soul Glass Crystal-Tin Alloy | `mvtink_alloy_soulsand_crystal_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Soul Glass Crystal-Titanium Alloy | `mvtink_alloy_soulsand_crystal_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Soul Glass Crystal-Topaz Alloy | `mvtink_alloy_soulsand_crystal_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Soul Glass Crystal-Tourmaline Alloy | `mvtink_alloy_soulsand_crystal_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Soul Glass Crystal-Tungsten Alloy | `mvtink_alloy_soulsand_crystal_tungsten` | Infernal, Tempered, Resonant |
| Void Pyrite (`mvtink_void_pyrite`) | Soul Glass Crystal-Void Pyrite Alloy | `mvtink_alloy_soulsand_crystal_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Soul Glass Crystal-Void Titanium Alloy | `mvtink_alloy_soulsand_crystal_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Soul Glass Crystal-Voidstone Alloy | `mvtink_alloy_soulsand_crystal_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Soul Glass Crystal-Compacted Volcanic Ash Alloy | `mvtink_alloy_soulsand_crystal_volcanic_ash` | Infernal, Terrain, Resonant |
| Warped Emerald (`mvtink_warped_emerald`) | Soul Glass Crystal-Warped Emerald Alloy | `mvtink_alloy_soulsand_crystal_warped_emerald` | Infernal, Radiant, Resonant |
| Warped Quartz (`mvtink_warped_quartz`) | Soul Glass Crystal-Warped Quartz Alloy | `mvtink_alloy_soulsand_crystal_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Soul Glass Crystal-Pure Weeping Shard Alloy | `mvtink_alloy_soulsand_crystal_weeping_shard` | Infernal, Resonant, Brutal |
| Witherite (`mvtink_witherite`) | Soul Glass Crystal-Witherite Alloy | `mvtink_alloy_soulsand_crystal_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Soul Glass Crystal-Zero-Point Shard Alloy | `mvtink_alloy_soulsand_crystal_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Soul Glass Crystal-Zinc Alloy | `mvtink_alloy_soulsand_crystal_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Soul Glass Crystal-Zircon Alloy | `mvtink_alloy_soulsand_crystal_zircon` | Infernal, Terrain, Resonant |

#### Steel · `mvtink_steel` · 20 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Stibnite (`mvtink_stibnite`) | Steel-Stibnite Alloy | `mvtink_alloy_steel_stibnite` | Infernal, Terrain, Tempered |
| Sulfur (`mvtink_sulfur`) | Steel-Sulfur Alloy | `mvtink_alloy_steel_sulfur` | Infernal, Tempered, Volatile |
| Talc (`mvtink_talc`) | Steel-Talc Alloy | `mvtink_alloy_steel_talc` | Infernal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Steel-Tesseract Crystal Alloy | `mvtink_alloy_steel_tesseract_crystal` | Infernal, Void, Tempered |
| Tin (`mvtink_tin`) | Steel-Tin Alloy | `mvtink_alloy_steel_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Steel-Titanium Alloy | `mvtink_alloy_steel_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Steel-Topaz Alloy | `mvtink_alloy_steel_topaz` | Infernal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Steel-Tourmaline Alloy | `mvtink_alloy_steel_tourmaline` | Infernal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Steel-Tungsten Alloy | `mvtink_alloy_steel_tungsten` | Infernal, Tempered, Brutal |
| Void Pyrite (`mvtink_void_pyrite`) | Steel-Void Pyrite Alloy | `mvtink_alloy_steel_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Steel-Void Titanium Alloy | `mvtink_alloy_steel_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Steel-Voidstone Alloy | `mvtink_alloy_steel_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Steel-Compacted Volcanic Ash Alloy | `mvtink_alloy_steel_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Steel-Warped Emerald Alloy | `mvtink_alloy_steel_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Steel-Warped Quartz Alloy | `mvtink_alloy_steel_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Steel-Pure Weeping Shard Alloy | `mvtink_alloy_steel_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Steel-Witherite Alloy | `mvtink_alloy_steel_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Steel-Zero-Point Shard Alloy | `mvtink_alloy_steel_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Steel-Zinc Alloy | `mvtink_alloy_steel_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Steel-Zircon Alloy | `mvtink_alloy_steel_zircon` | Infernal, Terrain, Tempered |

#### Stibnite · `mvtink_stibnite` · 19 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Sulfur (`mvtink_sulfur`) | Stibnite-Sulfur Alloy | `mvtink_alloy_stibnite_sulfur` | Infernal, Terrain, Volatile |
| Talc (`mvtink_talc`) | Stibnite-Talc Alloy | `mvtink_alloy_stibnite_talc` | Infernal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Stibnite-Tesseract Crystal Alloy | `mvtink_alloy_stibnite_tesseract_crystal` | Infernal, Void, Terrain |
| Tin (`mvtink_tin`) | Stibnite-Tin Alloy | `mvtink_alloy_stibnite_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Stibnite-Titanium Alloy | `mvtink_alloy_stibnite_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Stibnite-Topaz Alloy | `mvtink_alloy_stibnite_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Stibnite-Tourmaline Alloy | `mvtink_alloy_stibnite_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Stibnite-Tungsten Alloy | `mvtink_alloy_stibnite_tungsten` | Infernal, Terrain, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Stibnite-Void Pyrite Alloy | `mvtink_alloy_stibnite_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Stibnite-Void Titanium Alloy | `mvtink_alloy_stibnite_void_titanium` | Infernal, Void, Terrain |
| Voidstone (`mvtink_voidstone`) | Stibnite-Voidstone Alloy | `mvtink_alloy_stibnite_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Stibnite-Compacted Volcanic Ash Alloy | `mvtink_alloy_stibnite_volcanic_ash` | Infernal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Stibnite-Warped Emerald Alloy | `mvtink_alloy_stibnite_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Stibnite-Warped Quartz Alloy | `mvtink_alloy_stibnite_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Stibnite-Pure Weeping Shard Alloy | `mvtink_alloy_stibnite_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Stibnite-Witherite Alloy | `mvtink_alloy_stibnite_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Stibnite-Zero-Point Shard Alloy | `mvtink_alloy_stibnite_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Stibnite-Zinc Alloy | `mvtink_alloy_stibnite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Stibnite-Zircon Alloy | `mvtink_alloy_stibnite_zircon` | Infernal, Terrain, Resonant |

#### Sulfur · `mvtink_sulfur` · 18 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Talc (`mvtink_talc`) | Sulfur-Talc Alloy | `mvtink_alloy_sulfur_talc` | Infernal, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Sulfur-Tesseract Crystal Alloy | `mvtink_alloy_sulfur_tesseract_crystal` | Infernal, Void, Resonant |
| Tin (`mvtink_tin`) | Sulfur-Tin Alloy | `mvtink_alloy_sulfur_tin` | Infernal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Sulfur-Titanium Alloy | `mvtink_alloy_sulfur_titanium` | Infernal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Sulfur-Topaz Alloy | `mvtink_alloy_sulfur_topaz` | Infernal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Sulfur-Tourmaline Alloy | `mvtink_alloy_sulfur_tourmaline` | Infernal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Sulfur-Tungsten Alloy | `mvtink_alloy_sulfur_tungsten` | Infernal, Tempered, Volatile |
| Void Pyrite (`mvtink_void_pyrite`) | Sulfur-Void Pyrite Alloy | `mvtink_alloy_sulfur_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Sulfur-Void Titanium Alloy | `mvtink_alloy_sulfur_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) | Sulfur-Voidstone Alloy | `mvtink_alloy_sulfur_voidstone` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Sulfur-Compacted Volcanic Ash Alloy | `mvtink_alloy_sulfur_volcanic_ash` | Infernal, Terrain, Volatile |
| Warped Emerald (`mvtink_warped_emerald`) | Sulfur-Warped Emerald Alloy | `mvtink_alloy_sulfur_warped_emerald` | Infernal, Radiant, Volatile |
| Warped Quartz (`mvtink_warped_quartz`) | Sulfur-Warped Quartz Alloy | `mvtink_alloy_sulfur_warped_quartz` | Infernal, Void, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Sulfur-Pure Weeping Shard Alloy | `mvtink_alloy_sulfur_weeping_shard` | Infernal, Resonant, Volatile |
| Witherite (`mvtink_witherite`) | Sulfur-Witherite Alloy | `mvtink_alloy_sulfur_witherite` | Infernal, Terrain, Volatile |
| Zero-Point Shard (`mvtink_zero_point`) | Sulfur-Zero-Point Shard Alloy | `mvtink_alloy_sulfur_zero_point` | Infernal, Void, Volatile |
| Zinc (`mvtink_zinc`) | Sulfur-Zinc Alloy | `mvtink_alloy_sulfur_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Sulfur-Zircon Alloy | `mvtink_alloy_sulfur_zircon` | Infernal, Terrain, Resonant |

#### Tungsten · `mvtink_tungsten` · 11 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Void Pyrite (`mvtink_void_pyrite`) | Tungsten-Void Pyrite Alloy | `mvtink_alloy_tungsten_void_pyrite` | Infernal, Void, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Tungsten-Void Titanium Alloy | `mvtink_alloy_tungsten_void_titanium` | Infernal, Void, Tempered |
| Voidstone (`mvtink_voidstone`) ★ | Void Damascus | `mvtink_void_damascus` | Infernal, Void, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Tungsten-Compacted Volcanic Ash Alloy | `mvtink_alloy_tungsten_volcanic_ash` | Infernal, Terrain, Tempered |
| Warped Emerald (`mvtink_warped_emerald`) | Tungsten-Warped Emerald Alloy | `mvtink_alloy_tungsten_warped_emerald` | Infernal, Tempered, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Tungsten-Warped Quartz Alloy | `mvtink_alloy_tungsten_warped_quartz` | Infernal, Void, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Tungsten-Pure Weeping Shard Alloy | `mvtink_alloy_tungsten_weeping_shard` | Infernal, Tempered, Resonant |
| Witherite (`mvtink_witherite`) | Tungsten-Witherite Alloy | `mvtink_alloy_tungsten_witherite` | Infernal, Terrain, Tempered |
| Zero-Point Shard (`mvtink_zero_point`) | Tungsten-Zero-Point Shard Alloy | `mvtink_alloy_tungsten_zero_point` | Infernal, Void, Tempered |
| Zinc (`mvtink_zinc`) | Tungsten-Zinc Alloy | `mvtink_alloy_tungsten_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Tungsten-Zircon Alloy | `mvtink_alloy_tungsten_zircon` | Infernal, Terrain, Tempered |

#### Compacted Volcanic Ash · `mvtink_volcanic_ash` · 7 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Warped Emerald (`mvtink_warped_emerald`) | Compacted Volcanic Ash-Warped Emerald Alloy | `mvtink_alloy_volcanic_ash_warped_emerald` | Infernal, Terrain, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Compacted Volcanic Ash-Warped Quartz Alloy | `mvtink_alloy_volcanic_ash_warped_quartz` | Infernal, Void, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Compacted Volcanic Ash-Pure Weeping Shard Alloy | `mvtink_alloy_volcanic_ash_weeping_shard` | Infernal, Terrain, Resonant |
| Witherite (`mvtink_witherite`) | Compacted Volcanic Ash-Witherite Alloy | `mvtink_alloy_volcanic_ash_witherite` | Infernal, Terrain, Swift |
| Zero-Point Shard (`mvtink_zero_point`) | Compacted Volcanic Ash-Zero-Point Shard Alloy | `mvtink_alloy_volcanic_ash_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Compacted Volcanic Ash-Zinc Alloy | `mvtink_alloy_volcanic_ash_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Compacted Volcanic Ash-Zircon Alloy | `mvtink_alloy_volcanic_ash_zircon` | Infernal, Terrain, Resonant |

#### Warped Emerald · `mvtink_warped_emerald` · 6 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Warped Quartz (`mvtink_warped_quartz`) | Warped Emerald-Warped Quartz Alloy | `mvtink_alloy_warped_emerald_warped_quartz` | Infernal, Void, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Warped Emerald-Pure Weeping Shard Alloy | `mvtink_alloy_warped_emerald_weeping_shard` | Infernal, Radiant, Resonant |
| Witherite (`mvtink_witherite`) | Warped Emerald-Witherite Alloy | `mvtink_alloy_warped_emerald_witherite` | Infernal, Terrain, Radiant |
| Zero-Point Shard (`mvtink_zero_point`) | Warped Emerald-Zero-Point Shard Alloy | `mvtink_alloy_warped_emerald_zero_point` | Infernal, Void, Radiant |
| Zinc (`mvtink_zinc`) | Warped Emerald-Zinc Alloy | `mvtink_alloy_warped_emerald_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Warped Emerald-Zircon Alloy | `mvtink_alloy_warped_emerald_zircon` | Infernal, Terrain, Radiant |

#### Pure Weeping Shard · `mvtink_weeping_shard` · 4 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Witherite (`mvtink_witherite`) | Pure Weeping Shard-Witherite Alloy | `mvtink_alloy_weeping_shard_witherite` | Infernal, Terrain, Resonant |
| Zero-Point Shard (`mvtink_zero_point`) | Pure Weeping Shard-Zero-Point Shard Alloy | `mvtink_alloy_weeping_shard_zero_point` | Infernal, Void, Resonant |
| Zinc (`mvtink_zinc`) | Pure Weeping Shard-Zinc Alloy | `mvtink_alloy_weeping_shard_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Pure Weeping Shard-Zircon Alloy | `mvtink_alloy_weeping_shard_zircon` | Infernal, Terrain, Resonant |

#### Witherite · `mvtink_witherite` · 3 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Zero-Point Shard (`mvtink_zero_point`) | Witherite-Zero-Point Shard Alloy | `mvtink_alloy_witherite_zero_point` | Infernal, Void, Terrain |
| Zinc (`mvtink_zinc`) | Witherite-Zinc Alloy | `mvtink_alloy_witherite_zinc` | Infernal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Witherite-Zircon Alloy | `mvtink_alloy_witherite_zircon` | Infernal, Terrain, Resonant |

### 🌌 Padres del End

#### Adamantium · `mvtink_adamantium` · 109 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Adamite (`mvtink_adamita`) | Adamantium-Adamite Alloy | `mvtink_alloy_adamantium_adamita` | Void, Tempered, Resonant |
| Aether Pearl (`mvtink_aether_pearl`) | Adamantium-Aether Pearl Alloy | `mvtink_alloy_adamantium_aether_pearl` | Void, Tempered, Radiant |
| Aetherium (`mvtink_aetherium`) | Adamantium-Aetherium Alloy | `mvtink_alloy_adamantium_aetherium` | Void, Tempered, Volatile |
| Amber (`mvtink_amber`) | Adamantium-Amber Alloy | `mvtink_alloy_adamantium_amber` | Void, Terrain, Tempered |
| Amethyst (`mvtink_amethyst`) | Adamantium-Amethyst Alloy | `mvtink_alloy_adamantium_amethyst` | Void, Primal, Tempered |
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Adamantium-Amethyst Geode Crystal Alloy | `mvtink_alloy_adamantium_amethyst_cluster_gem` | Void, Terrain, Tempered |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Adamantium-Ancient Debris Slag Alloy | `mvtink_alloy_adamantium_ancient_slag` | Infernal, Void, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Adamantium-Aquamarine Alloy | `mvtink_alloy_adamantium_aquamarine` | Void, Terrain, Tempered |
| Ardite (`mvtink_ardite`) | Adamantium-Ardite Alloy | `mvtink_alloy_adamantium_ardite` | Infernal, Void, Tempered |
| Astralite (`mvtink_astralite`) | Adamantium-Astralite Alloy | `mvtink_alloy_adamantium_astralite` | Void, Tempered, Swift |
| Bauxite (`mvtink_bauxite`) | Adamantium-Bauxite Alloy | `mvtink_alloy_adamantium_bauxite` | Void, Terrain, Tempered |
| Beryllium (`mvtink_beryllium`) | Adamantium-Beryllium Alloy | `mvtink_alloy_adamantium_beryllium` | Void, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Adamantium-Bismuth Alloy | `mvtink_alloy_adamantium_bismuth` | Void, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Adamantium-Blackstone Pyrite Alloy | `mvtink_alloy_adamantium_blackstone_pyrite` | Infernal, Void, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Adamantium-Blazesteel Shard Alloy | `mvtink_alloy_adamantium_blazesteel_ore` | Infernal, Void, Tempered |
| Borax (`mvtink_borax`) | Adamantium-Borax Alloy | `mvtink_alloy_adamantium_borax` | Void, Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Adamantium-Pure Calcite Alloy | `mvtink_alloy_adamantium_calcite_gem` | Void, Terrain, Tempered |
| Celestine (`mvtink_celestine`) | Adamantium-Celestine Alloy | `mvtink_alloy_adamantium_celestine` | Void, Tempered, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Adamantium-Chorus Crystal Alloy | `mvtink_alloy_adamantium_chorus_crystal` | Void, Tempered, Radiant |
| Chromite (`mvtink_chromite`) | Adamantium-Chromite Alloy | `mvtink_alloy_adamantium_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Adamantium-Chrono Crystal Alloy | `mvtink_alloy_adamantium_chrono_crystal` | Void, Tempered, Resonant |
| Cinderite (`mvtink_cinderite`) | Adamantium-Cinderite Alloy | `mvtink_alloy_adamantium_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Adamantium-Cinnabar Alloy | `mvtink_alloy_adamantium_cinnabar` | Void, Terrain, Tempered |
| Coal (`mvtink_coal`) | Adamantium-Coal Alloy | `mvtink_alloy_adamantium_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Adamantium-Cobalt Alloy | `mvtink_alloy_adamantium_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Adamantium-Copper Alloy | `mvtink_alloy_adamantium_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Adamantium-Cosmium Alloy | `mvtink_alloy_adamantium_cosmium` | Void, Tempered, Swift |
| Crimson Gold (`mvtink_crimson_gold`) | Adamantium-Crimson Gold Alloy | `mvtink_alloy_adamantium_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Adamantium-Crimson Quartz Alloy | `mvtink_alloy_adamantium_crimson_quartz` | Infernal, Void, Tempered |
| Cryolite (`mvtink_cryolite`) | Adamantium-Cryolite Alloy | `mvtink_alloy_adamantium_cryolite` | Void, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Adamantium-Cursed Brimstone Alloy | `mvtink_alloy_adamantium_cursed_brimstone` | Infernal, Void, Tempered |
| Diamond (`mvtink_diamond`) | Adamantium-Diamond Alloy | `mvtink_alloy_adamantium_diamond` | Void, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Adamantium-Dragon Scale Shard Alloy | `mvtink_alloy_adamantium_dragon_shard` | Void, Terrain, Tempered |
| Eclipse Gem (`mvtink_eclipse_gem`) | Adamantium-Eclipse Gem Alloy | `mvtink_alloy_adamantium_eclipse_gem` | Void, Tempered, Radiant |
| Emerald (`mvtink_emerald`) | Adamantium-Emerald Alloy | `mvtink_alloy_adamantium_emerald` | Void, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Adamantium-End Crystal Shard Alloy | `mvtink_alloy_adamantium_end_crystal_shard` | Void, Tempered, Resonant |
| Enderite (`mvtink_enderite`) | Adamantium-Enderite Alloy | `mvtink_alloy_adamantium_enderite` | Void, Tempered, Swift |
| Fire Opal (`mvtink_fire_opal`) | Adamantium-Fire Opal Alloy | `mvtink_alloy_adamantium_fire_opal` | Infernal, Void, Tempered |
| Flint (`mvtink_flint`) | Adamantium-Flint Alloy | `mvtink_alloy_adamantium_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Adamantium-Fluorite Alloy | `mvtink_alloy_adamantium_fluorite` | Void, Terrain, Tempered |
| Galena (`mvtink_galena`) | Adamantium-Galena Alloy | `mvtink_alloy_adamantium_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Adamantium-Ghast Tear Shard Alloy | `mvtink_alloy_adamantium_ghast_tear_shard` | Infernal, Void, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Adamantium-Glowstone Gem Alloy | `mvtink_alloy_adamantium_glowstone_gem` | Infernal, Void, Tempered |
| Gold (`mvtink_gold`) | Adamantium-Gold Alloy | `mvtink_alloy_adamantium_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Adamantium-Graphite Alloy | `mvtink_alloy_adamantium_graphite` | Void, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Adamantium-Gravitite Alloy | `mvtink_alloy_adamantium_gravitite` | Void, Tempered, Swift |
| Gypsum (`mvtink_gypsum`) | Adamantium-Gypsum Alloy | `mvtink_alloy_adamantium_gypsum` | Void, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Adamantium-Helliron Alloy | `mvtink_alloy_adamantium_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Adamantium-Ignis Ferrum Alloy | `mvtink_alloy_adamantium_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Adamantium-Infernal Obsidian Alloy | `mvtink_alloy_adamantium_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Adamantium-Netherite-Infused Quartz Alloy | `mvtink_alloy_adamantium_infused_quartz` | Infernal, Void, Tempered |
| Iron (`mvtink_iron`) | Adamantium-Iron Alloy | `mvtink_alloy_adamantium_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Adamantium-Jade Alloy | `mvtink_alloy_adamantium_jade` | Void, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Adamantium-Kaolinite Alloy | `mvtink_alloy_adamantium_kaolinite` | Void, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Adamantium-Lapis Lazuli Alloy | `mvtink_alloy_adamantium_lapis` | Void, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Adamantium-Lapis Matrix Alloy | `mvtink_alloy_adamantium_lapis_matrix` | Void, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Adamantium-Magma Brimstone Alloy | `mvtink_alloy_adamantium_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Adamantium-Magmacite Alloy | `mvtink_alloy_adamantium_magmacite` | Infernal, Void, Tempered |
| Magnetite (`mvtink_magnetite`) | Adamantium-Magnetite Alloy | `mvtink_alloy_adamantium_magnetite` | Void, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Adamantium-Malachite Alloy | `mvtink_alloy_adamantium_malachite` | Void, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Adamantium-Nebulite Alloy | `mvtink_alloy_adamantium_nebulite` | Void, Tempered, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Adamantium-Nether Bismuth Alloy | `mvtink_alloy_adamantium_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Adamantium-Nether Tungsten Alloy | `mvtink_alloy_adamantium_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Adamantium-Netherite Scrap Shard Alloy | `mvtink_alloy_adamantium_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Adamantium-Nickel Alloy | `mvtink_alloy_adamantium_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Adamantium-Null-Shard Alloy | `mvtink_alloy_adamantium_null_shard` | Void, Tempered, Volatile |
| Obsidian (`mvtink_obsidian`) | Adamantium-Obsidian Alloy | `mvtink_alloy_adamantium_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Adamantium-Obsidianite Alloy | `mvtink_alloy_adamantium_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Adamantium-Opal Alloy | `mvtink_alloy_adamantium_opal` | Void, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Adamantium-Ender Pearl Core Alloy | `mvtink_alloy_adamantium_pearl_core` | Void, Tempered, Radiant |
| Phantomite (`mvtink_phantomite`) | Adamantium-Phantomite Alloy | `mvtink_alloy_adamantium_phantomite` | Void, Tempered, Volatile |
| Platinum (`mvtink_platinum`) | Adamantium-Platinum Alloy | `mvtink_alloy_adamantium_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Adamantium-Prismarine Alloy | `mvtink_alloy_adamantium_prismarine` | Void, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Adamantium-Pyrite Alloy | `mvtink_alloy_adamantium_pyrite` | Void, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Adamantium-Pyrophore Alloy | `mvtink_alloy_adamantium_pyrophore` | Infernal, Void, Tempered |
| Nether Quartz (`mvtink_quartz`) | Adamantium-Nether Quartz Alloy | `mvtink_alloy_adamantium_quartz` | Void, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Adamantium-Redstone Alloy | `mvtink_alloy_adamantium_redstone` | Void, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Adamantium-Resonite Alloy | `mvtink_alloy_adamantium_resonite` | Void, Tempered, Resonant |
| Ruby (`mvtink_ruby`) | Adamantium-Ruby Alloy | `mvtink_alloy_adamantium_ruby` | Void, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Adamantium-Sanguinite Alloy | `mvtink_alloy_adamantium_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Adamantium-Sapphire Alloy | `mvtink_alloy_adamantium_sapphire` | Void, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Adamantium-Shadowgem Alloy | `mvtink_alloy_adamantium_shadowgem` | Void, Tempered, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Adamantium-Shulkerite Alloy | `mvtink_alloy_adamantium_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Adamantium-Silver Alloy | `mvtink_alloy_adamantium_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Adamantium-Singularite Alloy | `mvtink_alloy_adamantium_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Adamantium-Soul Glass Crystal Alloy | `mvtink_alloy_adamantium_soulsand_crystal` | Infernal, Void, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Adamantium-Spatial Platinum Alloy | `mvtink_alloy_adamantium_spatial_platinum` | Void, Tempered, Swift |
| Starlight Silver (`mvtink_starlight_silver`) | Adamantium-Starlight Silver Alloy | `mvtink_alloy_adamantium_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Adamantium-Steel Alloy | `mvtink_alloy_adamantium_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Adamantium-Stibnite Alloy | `mvtink_alloy_adamantium_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Adamantium-Sulfur Alloy | `mvtink_alloy_adamantium_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Adamantium-Talc Alloy | `mvtink_alloy_adamantium_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Adamantium-Tesseract Crystal Alloy | `mvtink_alloy_adamantium_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Adamantium-Tin Alloy | `mvtink_alloy_adamantium_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) ★ | Adamant Steel | `mvtink_adamant_steel` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Adamantium-Topaz Alloy | `mvtink_alloy_adamantium_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Adamantium-Tourmaline Alloy | `mvtink_alloy_adamantium_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Adamantium-Tungsten Alloy | `mvtink_alloy_adamantium_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Adamantium-Void Pyrite Alloy | `mvtink_alloy_adamantium_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Adamantium-Void Titanium Alloy | `mvtink_alloy_adamantium_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Adamantium-Voidstone Alloy | `mvtink_alloy_adamantium_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Adamantium-Compacted Volcanic Ash Alloy | `mvtink_alloy_adamantium_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Adamantium-Warped Emerald Alloy | `mvtink_alloy_adamantium_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Adamantium-Warped Quartz Alloy | `mvtink_alloy_adamantium_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Adamantium-Pure Weeping Shard Alloy | `mvtink_alloy_adamantium_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Adamantium-Witherite Alloy | `mvtink_alloy_adamantium_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Adamantium-Zero-Point Shard Alloy | `mvtink_alloy_adamantium_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Adamantium-Zinc Alloy | `mvtink_alloy_adamantium_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Adamantium-Zircon Alloy | `mvtink_alloy_adamantium_zircon` | Void, Terrain, Tempered |

#### Adamite · `mvtink_adamita` · 108 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Aether Pearl (`mvtink_aether_pearl`) | Adamite-Aether Pearl Alloy | `mvtink_alloy_adamita_aether_pearl` | Void, Radiant, Resonant |
| Aetherium (`mvtink_aetherium`) | Adamite-Aetherium Alloy | `mvtink_alloy_adamita_aetherium` | Void, Resonant, Volatile |
| Amber (`mvtink_amber`) | Adamite-Amber Alloy | `mvtink_alloy_adamita_amber` | Void, Terrain, Radiant |
| Amethyst (`mvtink_amethyst`) | Adamite-Amethyst Alloy | `mvtink_alloy_adamita_amethyst` | Void, Primal, Resonant |
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Adamite-Amethyst Geode Crystal Alloy | `mvtink_alloy_adamita_amethyst_cluster_gem` | Void, Terrain, Resonant |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Adamite-Ancient Debris Slag Alloy | `mvtink_alloy_adamita_ancient_slag` | Infernal, Void, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Adamite-Aquamarine Alloy | `mvtink_alloy_adamita_aquamarine` | Void, Terrain, Radiant |
| Ardite (`mvtink_ardite`) | Adamite-Ardite Alloy | `mvtink_alloy_adamita_ardite` | Infernal, Void, Tempered |
| Astralite (`mvtink_astralite`) | Adamite-Astralite Alloy | `mvtink_alloy_adamita_astralite` | Void, Tempered, Resonant |
| Bauxite (`mvtink_bauxite`) | Adamite-Bauxite Alloy | `mvtink_alloy_adamita_bauxite` | Void, Terrain, Resonant |
| Beryllium (`mvtink_beryllium`) | Adamite-Beryllium Alloy | `mvtink_alloy_adamita_beryllium` | Void, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Adamite-Bismuth Alloy | `mvtink_alloy_adamita_bismuth` | Void, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Adamite-Blackstone Pyrite Alloy | `mvtink_alloy_adamita_blackstone_pyrite` | Infernal, Void, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Adamite-Blazesteel Shard Alloy | `mvtink_alloy_adamita_blazesteel_ore` | Infernal, Void, Tempered |
| Borax (`mvtink_borax`) | Adamite-Borax Alloy | `mvtink_alloy_adamita_borax` | Void, Terrain, Resonant |
| Pure Calcite (`mvtink_calcite_gem`) | Adamite-Pure Calcite Alloy | `mvtink_alloy_adamita_calcite_gem` | Void, Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Adamite-Celestine Alloy | `mvtink_alloy_adamita_celestine` | Void, Resonant, Swift |
| Chorus Crystal (`mvtink_chorus_crystal`) | Adamite-Chorus Crystal Alloy | `mvtink_alloy_adamita_chorus_crystal` | Void, Radiant, Resonant |
| Chromite (`mvtink_chromite`) | Adamite-Chromite Alloy | `mvtink_alloy_adamita_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Adamite-Chrono Crystal Alloy | `mvtink_alloy_adamita_chrono_crystal` | Void, Resonant, Swift |
| Cinderite (`mvtink_cinderite`) | Adamite-Cinderite Alloy | `mvtink_alloy_adamita_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Adamite-Cinnabar Alloy | `mvtink_alloy_adamita_cinnabar` | Void, Terrain, Resonant |
| Coal (`mvtink_coal`) | Adamite-Coal Alloy | `mvtink_alloy_adamita_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Adamite-Cobalt Alloy | `mvtink_alloy_adamita_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Adamite-Copper Alloy | `mvtink_alloy_adamita_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Adamite-Cosmium Alloy | `mvtink_alloy_adamita_cosmium` | Void, Tempered, Resonant |
| Crimson Gold (`mvtink_crimson_gold`) | Adamite-Crimson Gold Alloy | `mvtink_alloy_adamita_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Adamite-Crimson Quartz Alloy | `mvtink_alloy_adamita_crimson_quartz` | Infernal, Void, Resonant |
| Cryolite (`mvtink_cryolite`) | Adamite-Cryolite Alloy | `mvtink_alloy_adamita_cryolite` | Void, Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Adamite-Cursed Brimstone Alloy | `mvtink_alloy_adamita_cursed_brimstone` | Infernal, Void, Resonant |
| Diamond (`mvtink_diamond`) | Adamite-Diamond Alloy | `mvtink_alloy_adamita_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Adamite-Dragon Scale Shard Alloy | `mvtink_alloy_adamita_dragon_shard` | Void, Terrain, Resonant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Adamite-Eclipse Gem Alloy | `mvtink_alloy_adamita_eclipse_gem` | Void, Radiant, Resonant |
| Emerald (`mvtink_emerald`) | Adamite-Emerald Alloy | `mvtink_alloy_adamita_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Adamite-End Crystal Shard Alloy | `mvtink_alloy_adamita_end_crystal_shard` | Void, Resonant, Swift |
| Enderite (`mvtink_enderite`) | Adamite-Enderite Alloy | `mvtink_alloy_adamita_enderite` | Void, Tempered, Resonant |
| Fire Opal (`mvtink_fire_opal`) | Adamite-Fire Opal Alloy | `mvtink_alloy_adamita_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Adamite-Flint Alloy | `mvtink_alloy_adamita_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Adamite-Fluorite Alloy | `mvtink_alloy_adamita_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | Adamite-Galena Alloy | `mvtink_alloy_adamita_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Adamite-Ghast Tear Shard Alloy | `mvtink_alloy_adamita_ghast_tear_shard` | Infernal, Void, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Adamite-Glowstone Gem Alloy | `mvtink_alloy_adamita_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Adamite-Gold Alloy | `mvtink_alloy_adamita_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Adamite-Graphite Alloy | `mvtink_alloy_adamita_graphite` | Void, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Adamite-Gravitite Alloy | `mvtink_alloy_adamita_gravitite` | Void, Tempered, Resonant |
| Gypsum (`mvtink_gypsum`) | Adamite-Gypsum Alloy | `mvtink_alloy_adamita_gypsum` | Void, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Adamite-Helliron Alloy | `mvtink_alloy_adamita_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Adamite-Ignis Ferrum Alloy | `mvtink_alloy_adamita_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Adamite-Infernal Obsidian Alloy | `mvtink_alloy_adamita_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Adamite-Netherite-Infused Quartz Alloy | `mvtink_alloy_adamita_infused_quartz` | Infernal, Void, Resonant |
| Iron (`mvtink_iron`) | Adamite-Iron Alloy | `mvtink_alloy_adamita_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Adamite-Jade Alloy | `mvtink_alloy_adamita_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Adamite-Kaolinite Alloy | `mvtink_alloy_adamita_kaolinite` | Void, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Adamite-Lapis Lazuli Alloy | `mvtink_alloy_adamita_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Adamite-Lapis Matrix Alloy | `mvtink_alloy_adamita_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Adamite-Magma Brimstone Alloy | `mvtink_alloy_adamita_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Adamite-Magmacite Alloy | `mvtink_alloy_adamita_magmacite` | Infernal, Void, Resonant |
| Magnetite (`mvtink_magnetite`) | Adamite-Magnetite Alloy | `mvtink_alloy_adamita_magnetite` | Void, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Adamite-Malachite Alloy | `mvtink_alloy_adamita_malachite` | Void, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Adamite-Nebulite Alloy | `mvtink_alloy_adamita_nebulite` | Void, Resonant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Adamite-Nether Bismuth Alloy | `mvtink_alloy_adamita_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Adamite-Nether Tungsten Alloy | `mvtink_alloy_adamita_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Adamite-Netherite Scrap Shard Alloy | `mvtink_alloy_adamita_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Adamite-Nickel Alloy | `mvtink_alloy_adamita_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Adamite-Null-Shard Alloy | `mvtink_alloy_adamita_null_shard` | Void, Resonant, Volatile |
| Obsidian (`mvtink_obsidian`) | Adamite-Obsidian Alloy | `mvtink_alloy_adamita_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Adamite-Obsidianite Alloy | `mvtink_alloy_adamita_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Adamite-Opal Alloy | `mvtink_alloy_adamita_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Adamite-Ender Pearl Core Alloy | `mvtink_alloy_adamita_pearl_core` | Void, Radiant, Resonant |
| Phantomite (`mvtink_phantomite`) | Adamite-Phantomite Alloy | `mvtink_alloy_adamita_phantomite` | Void, Resonant, Volatile |
| Platinum (`mvtink_platinum`) | Adamite-Platinum Alloy | `mvtink_alloy_adamita_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Adamite-Prismarine Alloy | `mvtink_alloy_adamita_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Adamite-Pyrite Alloy | `mvtink_alloy_adamita_pyrite` | Void, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Adamite-Pyrophore Alloy | `mvtink_alloy_adamita_pyrophore` | Infernal, Void, Resonant |
| Nether Quartz (`mvtink_quartz`) | Adamite-Nether Quartz Alloy | `mvtink_alloy_adamita_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Adamite-Redstone Alloy | `mvtink_alloy_adamita_redstone` | Void, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Adamite-Resonite Alloy | `mvtink_alloy_adamita_resonite` | Void, Resonant, Swift |
| Ruby (`mvtink_ruby`) | Adamite-Ruby Alloy | `mvtink_alloy_adamita_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Adamite-Sanguinite Alloy | `mvtink_alloy_adamita_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Adamite-Sapphire Alloy | `mvtink_alloy_adamita_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Adamite-Shadowgem Alloy | `mvtink_alloy_adamita_shadowgem` | Void, Radiant, Resonant |
| Shulkerite (`mvtink_shulkerite`) | Adamite-Shulkerite Alloy | `mvtink_alloy_adamita_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Adamite-Silver Alloy | `mvtink_alloy_adamita_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Adamite-Singularite Alloy | `mvtink_alloy_adamita_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Adamite-Soul Glass Crystal Alloy | `mvtink_alloy_adamita_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Adamite-Spatial Platinum Alloy | `mvtink_alloy_adamita_spatial_platinum` | Void, Tempered, Resonant |
| Starlight Silver (`mvtink_starlight_silver`) | Adamite-Starlight Silver Alloy | `mvtink_alloy_adamita_starlight_silver` | Void, Tempered, Resonant |
| Steel (`mvtink_steel`) | Adamite-Steel Alloy | `mvtink_alloy_adamita_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Adamite-Stibnite Alloy | `mvtink_alloy_adamita_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Adamite-Sulfur Alloy | `mvtink_alloy_adamita_sulfur` | Infernal, Void, Resonant |
| Talc (`mvtink_talc`) | Adamite-Talc Alloy | `mvtink_alloy_adamita_talc` | Void, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Adamite-Tesseract Crystal Alloy | `mvtink_alloy_adamita_tesseract_crystal` | Void, Resonant, Swift |
| Tin (`mvtink_tin`) | Adamite-Tin Alloy | `mvtink_alloy_adamita_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Adamite-Titanium Alloy | `mvtink_alloy_adamita_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Adamite-Topaz Alloy | `mvtink_alloy_adamita_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Adamite-Tourmaline Alloy | `mvtink_alloy_adamita_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Adamite-Tungsten Alloy | `mvtink_alloy_adamita_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Adamite-Void Pyrite Alloy | `mvtink_alloy_adamita_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Adamite-Void Titanium Alloy | `mvtink_alloy_adamita_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | Adamite-Voidstone Alloy | `mvtink_alloy_adamita_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Adamite-Compacted Volcanic Ash Alloy | `mvtink_alloy_adamita_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Adamite-Warped Emerald Alloy | `mvtink_alloy_adamita_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Adamite-Warped Quartz Alloy | `mvtink_alloy_adamita_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Adamite-Pure Weeping Shard Alloy | `mvtink_alloy_adamita_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Adamite-Witherite Alloy | `mvtink_alloy_adamita_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Adamite-Zero-Point Shard Alloy | `mvtink_alloy_adamita_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Adamite-Zinc Alloy | `mvtink_alloy_adamita_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Adamite-Zircon Alloy | `mvtink_alloy_adamita_zircon` | Void, Terrain, Resonant |

#### Aether Pearl · `mvtink_aether_pearl` · 107 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Aetherium (`mvtink_aetherium`) | Aether Pearl-Aetherium Alloy | `mvtink_alloy_aether_pearl_aetherium` | Void, Radiant, Volatile |
| Amber (`mvtink_amber`) | Aether Pearl-Amber Alloy | `mvtink_alloy_aether_pearl_amber` | Void, Terrain, Radiant |
| Amethyst (`mvtink_amethyst`) | Aether Pearl-Amethyst Alloy | `mvtink_alloy_aether_pearl_amethyst` | Void, Primal, Radiant |
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Aether Pearl-Amethyst Geode Crystal Alloy | `mvtink_alloy_aether_pearl_amethyst_cluster_gem` | Void, Terrain, Radiant |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Aether Pearl-Ancient Debris Slag Alloy | `mvtink_alloy_aether_pearl_ancient_slag` | Infernal, Void, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Aether Pearl-Aquamarine Alloy | `mvtink_alloy_aether_pearl_aquamarine` | Void, Terrain, Radiant |
| Ardite (`mvtink_ardite`) | Aether Pearl-Ardite Alloy | `mvtink_alloy_aether_pearl_ardite` | Infernal, Void, Tempered |
| Astralite (`mvtink_astralite`) | Aether Pearl-Astralite Alloy | `mvtink_alloy_aether_pearl_astralite` | Void, Tempered, Radiant |
| Bauxite (`mvtink_bauxite`) | Aether Pearl-Bauxite Alloy | `mvtink_alloy_aether_pearl_bauxite` | Void, Terrain, Radiant |
| Beryllium (`mvtink_beryllium`) | Aether Pearl-Beryllium Alloy | `mvtink_alloy_aether_pearl_beryllium` | Void, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Aether Pearl-Bismuth Alloy | `mvtink_alloy_aether_pearl_bismuth` | Void, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Aether Pearl-Blackstone Pyrite Alloy | `mvtink_alloy_aether_pearl_blackstone_pyrite` | Infernal, Void, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Aether Pearl-Blazesteel Shard Alloy | `mvtink_alloy_aether_pearl_blazesteel_ore` | Infernal, Void, Tempered |
| Borax (`mvtink_borax`) | Aether Pearl-Borax Alloy | `mvtink_alloy_aether_pearl_borax` | Void, Terrain, Radiant |
| Pure Calcite (`mvtink_calcite_gem`) | Aether Pearl-Pure Calcite Alloy | `mvtink_alloy_aether_pearl_calcite_gem` | Void, Terrain, Radiant |
| Celestine (`mvtink_celestine`) | Aether Pearl-Celestine Alloy | `mvtink_alloy_aether_pearl_celestine` | Void, Radiant, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Aether Pearl-Chorus Crystal Alloy | `mvtink_alloy_aether_pearl_chorus_crystal` | Void, Radiant |
| Chromite (`mvtink_chromite`) | Aether Pearl-Chromite Alloy | `mvtink_alloy_aether_pearl_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Aether Pearl-Chrono Crystal Alloy | `mvtink_alloy_aether_pearl_chrono_crystal` | Void, Radiant, Resonant |
| Cinderite (`mvtink_cinderite`) | Aether Pearl-Cinderite Alloy | `mvtink_alloy_aether_pearl_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Aether Pearl-Cinnabar Alloy | `mvtink_alloy_aether_pearl_cinnabar` | Void, Terrain, Radiant |
| Coal (`mvtink_coal`) | Aether Pearl-Coal Alloy | `mvtink_alloy_aether_pearl_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Aether Pearl-Cobalt Alloy | `mvtink_alloy_aether_pearl_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Aether Pearl-Copper Alloy | `mvtink_alloy_aether_pearl_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Aether Pearl-Cosmium Alloy | `mvtink_alloy_aether_pearl_cosmium` | Void, Tempered, Radiant |
| Crimson Gold (`mvtink_crimson_gold`) | Aether Pearl-Crimson Gold Alloy | `mvtink_alloy_aether_pearl_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Aether Pearl-Crimson Quartz Alloy | `mvtink_alloy_aether_pearl_crimson_quartz` | Infernal, Void, Radiant |
| Cryolite (`mvtink_cryolite`) | Aether Pearl-Cryolite Alloy | `mvtink_alloy_aether_pearl_cryolite` | Void, Terrain, Radiant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Aether Pearl-Cursed Brimstone Alloy | `mvtink_alloy_aether_pearl_cursed_brimstone` | Infernal, Void, Radiant |
| Diamond (`mvtink_diamond`) | Aether Pearl-Diamond Alloy | `mvtink_alloy_aether_pearl_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Aether Pearl-Dragon Scale Shard Alloy | `mvtink_alloy_aether_pearl_dragon_shard` | Void, Terrain, Radiant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Aether Pearl-Eclipse Gem Alloy | `mvtink_alloy_aether_pearl_eclipse_gem` | Void, Radiant, Swift |
| Emerald (`mvtink_emerald`) | Aether Pearl-Emerald Alloy | `mvtink_alloy_aether_pearl_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Aether Pearl-End Crystal Shard Alloy | `mvtink_alloy_aether_pearl_end_crystal_shard` | Void, Radiant, Resonant |
| Enderite (`mvtink_enderite`) | Aether Pearl-Enderite Alloy | `mvtink_alloy_aether_pearl_enderite` | Void, Tempered, Radiant |
| Fire Opal (`mvtink_fire_opal`) | Aether Pearl-Fire Opal Alloy | `mvtink_alloy_aether_pearl_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Aether Pearl-Flint Alloy | `mvtink_alloy_aether_pearl_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Aether Pearl-Fluorite Alloy | `mvtink_alloy_aether_pearl_fluorite` | Void, Terrain, Radiant |
| Galena (`mvtink_galena`) | Aether Pearl-Galena Alloy | `mvtink_alloy_aether_pearl_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Aether Pearl-Ghast Tear Shard Alloy | `mvtink_alloy_aether_pearl_ghast_tear_shard` | Infernal, Void, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Aether Pearl-Glowstone Gem Alloy | `mvtink_alloy_aether_pearl_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Aether Pearl-Gold Alloy | `mvtink_alloy_aether_pearl_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Aether Pearl-Graphite Alloy | `mvtink_alloy_aether_pearl_graphite` | Void, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Aether Pearl-Gravitite Alloy | `mvtink_alloy_aether_pearl_gravitite` | Void, Tempered, Radiant |
| Gypsum (`mvtink_gypsum`) | Aether Pearl-Gypsum Alloy | `mvtink_alloy_aether_pearl_gypsum` | Void, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Aether Pearl-Helliron Alloy | `mvtink_alloy_aether_pearl_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Aether Pearl-Ignis Ferrum Alloy | `mvtink_alloy_aether_pearl_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Aether Pearl-Infernal Obsidian Alloy | `mvtink_alloy_aether_pearl_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Aether Pearl-Netherite-Infused Quartz Alloy | `mvtink_alloy_aether_pearl_infused_quartz` | Infernal, Void, Radiant |
| Iron (`mvtink_iron`) | Aether Pearl-Iron Alloy | `mvtink_alloy_aether_pearl_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Aether Pearl-Jade Alloy | `mvtink_alloy_aether_pearl_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Aether Pearl-Kaolinite Alloy | `mvtink_alloy_aether_pearl_kaolinite` | Void, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Aether Pearl-Lapis Lazuli Alloy | `mvtink_alloy_aether_pearl_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Aether Pearl-Lapis Matrix Alloy | `mvtink_alloy_aether_pearl_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Aether Pearl-Magma Brimstone Alloy | `mvtink_alloy_aether_pearl_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Aether Pearl-Magmacite Alloy | `mvtink_alloy_aether_pearl_magmacite` | Infernal, Void, Radiant |
| Magnetite (`mvtink_magnetite`) | Aether Pearl-Magnetite Alloy | `mvtink_alloy_aether_pearl_magnetite` | Void, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Aether Pearl-Malachite Alloy | `mvtink_alloy_aether_pearl_malachite` | Void, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Aether Pearl-Nebulite Alloy | `mvtink_alloy_aether_pearl_nebulite` | Void, Radiant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Aether Pearl-Nether Bismuth Alloy | `mvtink_alloy_aether_pearl_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Aether Pearl-Nether Tungsten Alloy | `mvtink_alloy_aether_pearl_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Aether Pearl-Netherite Scrap Shard Alloy | `mvtink_alloy_aether_pearl_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Aether Pearl-Nickel Alloy | `mvtink_alloy_aether_pearl_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Aether Pearl-Null-Shard Alloy | `mvtink_alloy_aether_pearl_null_shard` | Void, Radiant, Volatile |
| Obsidian (`mvtink_obsidian`) | Aether Pearl-Obsidian Alloy | `mvtink_alloy_aether_pearl_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Aether Pearl-Obsidianite Alloy | `mvtink_alloy_aether_pearl_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Aether Pearl-Opal Alloy | `mvtink_alloy_aether_pearl_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Aether Pearl-Ender Pearl Core Alloy | `mvtink_alloy_aether_pearl_pearl_core` | Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Aether Pearl-Phantomite Alloy | `mvtink_alloy_aether_pearl_phantomite` | Void, Radiant, Volatile |
| Platinum (`mvtink_platinum`) | Aether Pearl-Platinum Alloy | `mvtink_alloy_aether_pearl_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Aether Pearl-Prismarine Alloy | `mvtink_alloy_aether_pearl_prismarine` | Void, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Aether Pearl-Pyrite Alloy | `mvtink_alloy_aether_pearl_pyrite` | Void, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Aether Pearl-Pyrophore Alloy | `mvtink_alloy_aether_pearl_pyrophore` | Infernal, Void, Radiant |
| Nether Quartz (`mvtink_quartz`) | Aether Pearl-Nether Quartz Alloy | `mvtink_alloy_aether_pearl_quartz` | Void, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Aether Pearl-Redstone Alloy | `mvtink_alloy_aether_pearl_redstone` | Void, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Aether Pearl-Resonite Alloy | `mvtink_alloy_aether_pearl_resonite` | Void, Radiant, Resonant |
| Ruby (`mvtink_ruby`) | Aether Pearl-Ruby Alloy | `mvtink_alloy_aether_pearl_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Aether Pearl-Sanguinite Alloy | `mvtink_alloy_aether_pearl_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Aether Pearl-Sapphire Alloy | `mvtink_alloy_aether_pearl_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Aether Pearl-Shadowgem Alloy | `mvtink_alloy_aether_pearl_shadowgem` | Void, Radiant, Swift |
| Shulkerite (`mvtink_shulkerite`) | Aether Pearl-Shulkerite Alloy | `mvtink_alloy_aether_pearl_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Aether Pearl-Silver Alloy | `mvtink_alloy_aether_pearl_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Aether Pearl-Singularite Alloy | `mvtink_alloy_aether_pearl_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Aether Pearl-Soul Glass Crystal Alloy | `mvtink_alloy_aether_pearl_soulsand_crystal` | Infernal, Void, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Aether Pearl-Spatial Platinum Alloy | `mvtink_alloy_aether_pearl_spatial_platinum` | Void, Tempered, Radiant |
| Starlight Silver (`mvtink_starlight_silver`) | Aether Pearl-Starlight Silver Alloy | `mvtink_alloy_aether_pearl_starlight_silver` | Void, Tempered, Radiant |
| Steel (`mvtink_steel`) | Aether Pearl-Steel Alloy | `mvtink_alloy_aether_pearl_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Aether Pearl-Stibnite Alloy | `mvtink_alloy_aether_pearl_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Aether Pearl-Sulfur Alloy | `mvtink_alloy_aether_pearl_sulfur` | Infernal, Void, Radiant |
| Talc (`mvtink_talc`) | Aether Pearl-Talc Alloy | `mvtink_alloy_aether_pearl_talc` | Void, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Aether Pearl-Tesseract Crystal Alloy | `mvtink_alloy_aether_pearl_tesseract_crystal` | Void, Radiant, Resonant |
| Tin (`mvtink_tin`) | Aether Pearl-Tin Alloy | `mvtink_alloy_aether_pearl_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Aether Pearl-Titanium Alloy | `mvtink_alloy_aether_pearl_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Aether Pearl-Topaz Alloy | `mvtink_alloy_aether_pearl_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Aether Pearl-Tourmaline Alloy | `mvtink_alloy_aether_pearl_tourmaline` | Void, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Aether Pearl-Tungsten Alloy | `mvtink_alloy_aether_pearl_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Aether Pearl-Void Pyrite Alloy | `mvtink_alloy_aether_pearl_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Aether Pearl-Void Titanium Alloy | `mvtink_alloy_aether_pearl_void_titanium` | Void, Tempered, Radiant |
| Voidstone (`mvtink_voidstone`) | Aether Pearl-Voidstone Alloy | `mvtink_alloy_aether_pearl_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Aether Pearl-Compacted Volcanic Ash Alloy | `mvtink_alloy_aether_pearl_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Aether Pearl-Warped Emerald Alloy | `mvtink_alloy_aether_pearl_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Aether Pearl-Warped Quartz Alloy | `mvtink_alloy_aether_pearl_warped_quartz` | Void, Radiant, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Aether Pearl-Pure Weeping Shard Alloy | `mvtink_alloy_aether_pearl_weeping_shard` | Infernal, Void, Radiant |
| Witherite (`mvtink_witherite`) | Aether Pearl-Witherite Alloy | `mvtink_alloy_aether_pearl_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Aether Pearl-Zero-Point Shard Alloy | `mvtink_alloy_aether_pearl_zero_point` | Void, Radiant, Volatile |
| Zinc (`mvtink_zinc`) | Aether Pearl-Zinc Alloy | `mvtink_alloy_aether_pearl_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Aether Pearl-Zircon Alloy | `mvtink_alloy_aether_pearl_zircon` | Void, Terrain, Radiant |

#### Aetherium · `mvtink_aetherium` · 106 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Amber (`mvtink_amber`) | Aetherium-Amber Alloy | `mvtink_alloy_aetherium_amber` | Void, Terrain, Radiant |
| Amethyst (`mvtink_amethyst`) | Aetherium-Amethyst Alloy | `mvtink_alloy_aetherium_amethyst` | Void, Primal, Resonant |
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Aetherium-Amethyst Geode Crystal Alloy | `mvtink_alloy_aetherium_amethyst_cluster_gem` | Void, Terrain, Resonant |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Aetherium-Ancient Debris Slag Alloy | `mvtink_alloy_aetherium_ancient_slag` | Infernal, Void, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Aetherium-Aquamarine Alloy | `mvtink_alloy_aetherium_aquamarine` | Void, Terrain, Radiant |
| Ardite (`mvtink_ardite`) | Aetherium-Ardite Alloy | `mvtink_alloy_aetherium_ardite` | Infernal, Void, Tempered |
| Astralite (`mvtink_astralite`) | Aetherium-Astralite Alloy | `mvtink_alloy_aetherium_astralite` | Void, Tempered, Volatile |
| Bauxite (`mvtink_bauxite`) | Aetherium-Bauxite Alloy | `mvtink_alloy_aetherium_bauxite` | Void, Terrain, Volatile |
| Beryllium (`mvtink_beryllium`) | Aetherium-Beryllium Alloy | `mvtink_alloy_aetherium_beryllium` | Void, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Aetherium-Bismuth Alloy | `mvtink_alloy_aetherium_bismuth` | Void, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Aetherium-Blackstone Pyrite Alloy | `mvtink_alloy_aetherium_blackstone_pyrite` | Infernal, Void, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Aetherium-Blazesteel Shard Alloy | `mvtink_alloy_aetherium_blazesteel_ore` | Infernal, Void, Tempered |
| Borax (`mvtink_borax`) | Aetherium-Borax Alloy | `mvtink_alloy_aetherium_borax` | Void, Terrain, Volatile |
| Pure Calcite (`mvtink_calcite_gem`) | Aetherium-Pure Calcite Alloy | `mvtink_alloy_aetherium_calcite_gem` | Void, Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Aetherium-Celestine Alloy | `mvtink_alloy_aetherium_celestine` | Void, Resonant, Volatile |
| Chorus Crystal (`mvtink_chorus_crystal`) | Aetherium-Chorus Crystal Alloy | `mvtink_alloy_aetherium_chorus_crystal` | Void, Radiant, Volatile |
| Chromite (`mvtink_chromite`) | Aetherium-Chromite Alloy | `mvtink_alloy_aetherium_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Aetherium-Chrono Crystal Alloy | `mvtink_alloy_aetherium_chrono_crystal` | Void, Resonant, Volatile |
| Cinderite (`mvtink_cinderite`) | Aetherium-Cinderite Alloy | `mvtink_alloy_aetherium_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Aetherium-Cinnabar Alloy | `mvtink_alloy_aetherium_cinnabar` | Void, Terrain, Volatile |
| Coal (`mvtink_coal`) | Aetherium-Coal Alloy | `mvtink_alloy_aetherium_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Aetherium-Cobalt Alloy | `mvtink_alloy_aetherium_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Aetherium-Copper Alloy | `mvtink_alloy_aetherium_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Aetherium-Cosmium Alloy | `mvtink_alloy_aetherium_cosmium` | Void, Tempered, Volatile |
| Crimson Gold (`mvtink_crimson_gold`) | Aetherium-Crimson Gold Alloy | `mvtink_alloy_aetherium_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Aetherium-Crimson Quartz Alloy | `mvtink_alloy_aetherium_crimson_quartz` | Infernal, Void, Resonant |
| Cryolite (`mvtink_cryolite`) | Aetherium-Cryolite Alloy | `mvtink_alloy_aetherium_cryolite` | Void, Terrain, Volatile |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Aetherium-Cursed Brimstone Alloy | `mvtink_alloy_aetherium_cursed_brimstone` | Infernal, Void, Volatile |
| Diamond (`mvtink_diamond`) | Aetherium-Diamond Alloy | `mvtink_alloy_aetherium_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Aetherium-Dragon Scale Shard Alloy | `mvtink_alloy_aetherium_dragon_shard` | Void, Terrain, Volatile |
| Eclipse Gem (`mvtink_eclipse_gem`) | Aetherium-Eclipse Gem Alloy | `mvtink_alloy_aetherium_eclipse_gem` | Void, Radiant, Volatile |
| Emerald (`mvtink_emerald`) | Aetherium-Emerald Alloy | `mvtink_alloy_aetherium_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Aetherium-End Crystal Shard Alloy | `mvtink_alloy_aetherium_end_crystal_shard` | Void, Resonant, Volatile |
| Enderite (`mvtink_enderite`) | Aetherium-Enderite Alloy | `mvtink_alloy_aetherium_enderite` | Void, Tempered, Volatile |
| Fire Opal (`mvtink_fire_opal`) | Aetherium-Fire Opal Alloy | `mvtink_alloy_aetherium_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Aetherium-Flint Alloy | `mvtink_alloy_aetherium_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Aetherium-Fluorite Alloy | `mvtink_alloy_aetherium_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | Aetherium-Galena Alloy | `mvtink_alloy_aetherium_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Aetherium-Ghast Tear Shard Alloy | `mvtink_alloy_aetherium_ghast_tear_shard` | Infernal, Void, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Aetherium-Glowstone Gem Alloy | `mvtink_alloy_aetherium_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Aetherium-Gold Alloy | `mvtink_alloy_aetherium_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Aetherium-Graphite Alloy | `mvtink_alloy_aetherium_graphite` | Void, Terrain, Volatile |
| Gravitite (`mvtink_gravitite`) | Aetherium-Gravitite Alloy | `mvtink_alloy_aetherium_gravitite` | Void, Tempered, Volatile |
| Gypsum (`mvtink_gypsum`) | Aetherium-Gypsum Alloy | `mvtink_alloy_aetherium_gypsum` | Void, Terrain, Volatile |
| Helliron (`mvtink_helliron`) | Aetherium-Helliron Alloy | `mvtink_alloy_aetherium_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Aetherium-Ignis Ferrum Alloy | `mvtink_alloy_aetherium_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Aetherium-Infernal Obsidian Alloy | `mvtink_alloy_aetherium_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Aetherium-Netherite-Infused Quartz Alloy | `mvtink_alloy_aetherium_infused_quartz` | Infernal, Void, Resonant |
| Iron (`mvtink_iron`) | Aetherium-Iron Alloy | `mvtink_alloy_aetherium_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Aetherium-Jade Alloy | `mvtink_alloy_aetherium_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Aetherium-Kaolinite Alloy | `mvtink_alloy_aetherium_kaolinite` | Void, Terrain, Volatile |
| Lapis Lazuli (`mvtink_lapis`) | Aetherium-Lapis Lazuli Alloy | `mvtink_alloy_aetherium_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Aetherium-Lapis Matrix Alloy | `mvtink_alloy_aetherium_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Aetherium-Magma Brimstone Alloy | `mvtink_alloy_aetherium_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Aetherium-Magmacite Alloy | `mvtink_alloy_aetherium_magmacite` | Infernal, Void, Resonant |
| Magnetite (`mvtink_magnetite`) | Aetherium-Magnetite Alloy | `mvtink_alloy_aetherium_magnetite` | Void, Terrain, Volatile |
| Malachite (`mvtink_malachite`) | Aetherium-Malachite Alloy | `mvtink_alloy_aetherium_malachite` | Void, Terrain, Volatile |
| Nebulite (`mvtink_nebulite`) | Aetherium-Nebulite Alloy | `mvtink_alloy_aetherium_nebulite` | Void, Volatile, Swift |
| Nether Bismuth (`mvtink_nether_bismuth`) | Aetherium-Nether Bismuth Alloy | `mvtink_alloy_aetherium_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Aetherium-Nether Tungsten Alloy | `mvtink_alloy_aetherium_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Aetherium-Netherite Scrap Shard Alloy | `mvtink_alloy_aetherium_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Aetherium-Nickel Alloy | `mvtink_alloy_aetherium_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Aetherium-Null-Shard Alloy | `mvtink_alloy_aetherium_null_shard` | Void, Volatile, Swift |
| Obsidian (`mvtink_obsidian`) | Aetherium-Obsidian Alloy | `mvtink_alloy_aetherium_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Aetherium-Obsidianite Alloy | `mvtink_alloy_aetherium_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Aetherium-Opal Alloy | `mvtink_alloy_aetherium_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Aetherium-Ender Pearl Core Alloy | `mvtink_alloy_aetherium_pearl_core` | Void, Radiant, Volatile |
| Phantomite (`mvtink_phantomite`) | Aetherium-Phantomite Alloy | `mvtink_alloy_aetherium_phantomite` | Void, Volatile, Swift |
| Platinum (`mvtink_platinum`) | Aetherium-Platinum Alloy | `mvtink_alloy_aetherium_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Aetherium-Prismarine Alloy | `mvtink_alloy_aetherium_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Aetherium-Pyrite Alloy | `mvtink_alloy_aetherium_pyrite` | Void, Terrain, Volatile |
| Pyrophore (`mvtink_pyrophore`) | Aetherium-Pyrophore Alloy | `mvtink_alloy_aetherium_pyrophore` | Infernal, Void, Volatile |
| Nether Quartz (`mvtink_quartz`) | Aetherium-Nether Quartz Alloy | `mvtink_alloy_aetherium_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Aetherium-Redstone Alloy | `mvtink_alloy_aetherium_redstone` | Void, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Aetherium-Resonite Alloy | `mvtink_alloy_aetherium_resonite` | Void, Resonant, Volatile |
| Ruby (`mvtink_ruby`) | Aetherium-Ruby Alloy | `mvtink_alloy_aetherium_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Aetherium-Sanguinite Alloy | `mvtink_alloy_aetherium_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Aetherium-Sapphire Alloy | `mvtink_alloy_aetherium_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Aetherium-Shadowgem Alloy | `mvtink_alloy_aetherium_shadowgem` | Void, Radiant, Volatile |
| Shulkerite (`mvtink_shulkerite`) | Aetherium-Shulkerite Alloy | `mvtink_alloy_aetherium_shulkerite` | Void, Terrain, Volatile |
| Silver (`mvtink_silver`) | Aetherium-Silver Alloy | `mvtink_alloy_aetherium_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Aetherium-Singularite Alloy | `mvtink_alloy_aetherium_singularite` | Void, Terrain, Volatile |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Aetherium-Soul Glass Crystal Alloy | `mvtink_alloy_aetherium_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Aetherium-Spatial Platinum Alloy | `mvtink_alloy_aetherium_spatial_platinum` | Void, Tempered, Volatile |
| Starlight Silver (`mvtink_starlight_silver`) | Aetherium-Starlight Silver Alloy | `mvtink_alloy_aetherium_starlight_silver` | Void, Tempered, Volatile |
| Steel (`mvtink_steel`) | Aetherium-Steel Alloy | `mvtink_alloy_aetherium_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Aetherium-Stibnite Alloy | `mvtink_alloy_aetherium_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Aetherium-Sulfur Alloy | `mvtink_alloy_aetherium_sulfur` | Infernal, Void, Volatile |
| Talc (`mvtink_talc`) | Aetherium-Talc Alloy | `mvtink_alloy_aetherium_talc` | Void, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Aetherium-Tesseract Crystal Alloy | `mvtink_alloy_aetherium_tesseract_crystal` | Void, Resonant, Volatile |
| Tin (`mvtink_tin`) | Aetherium-Tin Alloy | `mvtink_alloy_aetherium_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Aetherium-Titanium Alloy | `mvtink_alloy_aetherium_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Aetherium-Topaz Alloy | `mvtink_alloy_aetherium_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Aetherium-Tourmaline Alloy | `mvtink_alloy_aetherium_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Aetherium-Tungsten Alloy | `mvtink_alloy_aetherium_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Aetherium-Void Pyrite Alloy | `mvtink_alloy_aetherium_void_pyrite` | Void, Terrain, Volatile |
| Void Titanium (`mvtink_void_titanium`) | Aetherium-Void Titanium Alloy | `mvtink_alloy_aetherium_void_titanium` | Void, Tempered, Volatile |
| Voidstone (`mvtink_voidstone`) | Aetherium-Voidstone Alloy | `mvtink_alloy_aetherium_voidstone` | Void, Terrain, Volatile |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Aetherium-Compacted Volcanic Ash Alloy | `mvtink_alloy_aetherium_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Aetherium-Warped Emerald Alloy | `mvtink_alloy_aetherium_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Aetherium-Warped Quartz Alloy | `mvtink_alloy_aetherium_warped_quartz` | Void, Resonant, Volatile |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Aetherium-Pure Weeping Shard Alloy | `mvtink_alloy_aetherium_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Aetherium-Witherite Alloy | `mvtink_alloy_aetherium_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Aetherium-Zero-Point Shard Alloy | `mvtink_alloy_aetherium_zero_point` | Void, Volatile, Swift |
| Zinc (`mvtink_zinc`) | Aetherium-Zinc Alloy | `mvtink_alloy_aetherium_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Aetherium-Zircon Alloy | `mvtink_alloy_aetherium_zircon` | Void, Terrain, Resonant |

#### Astralite · `mvtink_astralite` · 99 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Bauxite (`mvtink_bauxite`) | Astralite-Bauxite Alloy | `mvtink_alloy_astralite_bauxite` | Void, Terrain, Tempered |
| Beryllium (`mvtink_beryllium`) | Astralite-Beryllium Alloy | `mvtink_alloy_astralite_beryllium` | Void, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Astralite-Bismuth Alloy | `mvtink_alloy_astralite_bismuth` | Void, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Astralite-Blackstone Pyrite Alloy | `mvtink_alloy_astralite_blackstone_pyrite` | Infernal, Void, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Astralite-Blazesteel Shard Alloy | `mvtink_alloy_astralite_blazesteel_ore` | Infernal, Void, Tempered |
| Borax (`mvtink_borax`) | Astralite-Borax Alloy | `mvtink_alloy_astralite_borax` | Void, Terrain, Tempered |
| Pure Calcite (`mvtink_calcite_gem`) | Astralite-Pure Calcite Alloy | `mvtink_alloy_astralite_calcite_gem` | Void, Terrain, Tempered |
| Celestine (`mvtink_celestine`) | Astralite-Celestine Alloy | `mvtink_alloy_astralite_celestine` | Void, Tempered, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Astralite-Chorus Crystal Alloy | `mvtink_alloy_astralite_chorus_crystal` | Void, Tempered, Radiant |
| Chromite (`mvtink_chromite`) | Astralite-Chromite Alloy | `mvtink_alloy_astralite_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Astralite-Chrono Crystal Alloy | `mvtink_alloy_astralite_chrono_crystal` | Void, Tempered, Resonant |
| Cinderite (`mvtink_cinderite`) | Astralite-Cinderite Alloy | `mvtink_alloy_astralite_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Astralite-Cinnabar Alloy | `mvtink_alloy_astralite_cinnabar` | Void, Terrain, Tempered |
| Coal (`mvtink_coal`) | Astralite-Coal Alloy | `mvtink_alloy_astralite_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Astralite-Cobalt Alloy | `mvtink_alloy_astralite_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Astralite-Copper Alloy | `mvtink_alloy_astralite_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Astralite-Cosmium Alloy | `mvtink_alloy_astralite_cosmium` | Void, Tempered, Swift |
| Crimson Gold (`mvtink_crimson_gold`) | Astralite-Crimson Gold Alloy | `mvtink_alloy_astralite_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Astralite-Crimson Quartz Alloy | `mvtink_alloy_astralite_crimson_quartz` | Infernal, Void, Tempered |
| Cryolite (`mvtink_cryolite`) | Astralite-Cryolite Alloy | `mvtink_alloy_astralite_cryolite` | Void, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Astralite-Cursed Brimstone Alloy | `mvtink_alloy_astralite_cursed_brimstone` | Infernal, Void, Tempered |
| Diamond (`mvtink_diamond`) | Astralite-Diamond Alloy | `mvtink_alloy_astralite_diamond` | Void, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Astralite-Dragon Scale Shard Alloy | `mvtink_alloy_astralite_dragon_shard` | Void, Terrain, Tempered |
| Eclipse Gem (`mvtink_eclipse_gem`) | Astralite-Eclipse Gem Alloy | `mvtink_alloy_astralite_eclipse_gem` | Void, Tempered, Radiant |
| Emerald (`mvtink_emerald`) | Astralite-Emerald Alloy | `mvtink_alloy_astralite_emerald` | Void, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Astralite-End Crystal Shard Alloy | `mvtink_alloy_astralite_end_crystal_shard` | Void, Tempered, Resonant |
| Enderite (`mvtink_enderite`) | Astralite-Enderite Alloy | `mvtink_alloy_astralite_enderite` | Void, Tempered, Swift |
| Fire Opal (`mvtink_fire_opal`) | Astralite-Fire Opal Alloy | `mvtink_alloy_astralite_fire_opal` | Infernal, Void, Tempered |
| Flint (`mvtink_flint`) | Astralite-Flint Alloy | `mvtink_alloy_astralite_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Astralite-Fluorite Alloy | `mvtink_alloy_astralite_fluorite` | Void, Terrain, Tempered |
| Galena (`mvtink_galena`) | Astralite-Galena Alloy | `mvtink_alloy_astralite_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Astralite-Ghast Tear Shard Alloy | `mvtink_alloy_astralite_ghast_tear_shard` | Infernal, Void, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Astralite-Glowstone Gem Alloy | `mvtink_alloy_astralite_glowstone_gem` | Infernal, Void, Tempered |
| Gold (`mvtink_gold`) | Astralite-Gold Alloy | `mvtink_alloy_astralite_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Astralite-Graphite Alloy | `mvtink_alloy_astralite_graphite` | Void, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Astralite-Gravitite Alloy | `mvtink_alloy_astralite_gravitite` | Void, Tempered, Swift |
| Gypsum (`mvtink_gypsum`) | Astralite-Gypsum Alloy | `mvtink_alloy_astralite_gypsum` | Void, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Astralite-Helliron Alloy | `mvtink_alloy_astralite_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Astralite-Ignis Ferrum Alloy | `mvtink_alloy_astralite_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Astralite-Infernal Obsidian Alloy | `mvtink_alloy_astralite_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Astralite-Netherite-Infused Quartz Alloy | `mvtink_alloy_astralite_infused_quartz` | Infernal, Void, Tempered |
| Iron (`mvtink_iron`) | Astralite-Iron Alloy | `mvtink_alloy_astralite_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Astralite-Jade Alloy | `mvtink_alloy_astralite_jade` | Void, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Astralite-Kaolinite Alloy | `mvtink_alloy_astralite_kaolinite` | Void, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Astralite-Lapis Lazuli Alloy | `mvtink_alloy_astralite_lapis` | Void, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Astralite-Lapis Matrix Alloy | `mvtink_alloy_astralite_lapis_matrix` | Void, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Astralite-Magma Brimstone Alloy | `mvtink_alloy_astralite_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Astralite-Magmacite Alloy | `mvtink_alloy_astralite_magmacite` | Infernal, Void, Tempered |
| Magnetite (`mvtink_magnetite`) | Astralite-Magnetite Alloy | `mvtink_alloy_astralite_magnetite` | Void, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Astralite-Malachite Alloy | `mvtink_alloy_astralite_malachite` | Void, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Astralite-Nebulite Alloy | `mvtink_alloy_astralite_nebulite` | Void, Tempered, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Astralite-Nether Bismuth Alloy | `mvtink_alloy_astralite_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Astralite-Nether Tungsten Alloy | `mvtink_alloy_astralite_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Astralite-Netherite Scrap Shard Alloy | `mvtink_alloy_astralite_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Astralite-Nickel Alloy | `mvtink_alloy_astralite_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Astralite-Null-Shard Alloy | `mvtink_alloy_astralite_null_shard` | Void, Tempered, Volatile |
| Obsidian (`mvtink_obsidian`) | Astralite-Obsidian Alloy | `mvtink_alloy_astralite_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Astralite-Obsidianite Alloy | `mvtink_alloy_astralite_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Astralite-Opal Alloy | `mvtink_alloy_astralite_opal` | Void, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Astralite-Ender Pearl Core Alloy | `mvtink_alloy_astralite_pearl_core` | Void, Tempered, Radiant |
| Phantomite (`mvtink_phantomite`) | Astralite-Phantomite Alloy | `mvtink_alloy_astralite_phantomite` | Void, Tempered, Volatile |
| Platinum (`mvtink_platinum`) | Astralite-Platinum Alloy | `mvtink_alloy_astralite_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Astralite-Prismarine Alloy | `mvtink_alloy_astralite_prismarine` | Void, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) ★ | Astral Brass | `mvtink_astral_brass` | Void, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Astralite-Pyrophore Alloy | `mvtink_alloy_astralite_pyrophore` | Infernal, Void, Tempered |
| Nether Quartz (`mvtink_quartz`) | Astralite-Nether Quartz Alloy | `mvtink_alloy_astralite_quartz` | Void, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Astralite-Redstone Alloy | `mvtink_alloy_astralite_redstone` | Void, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Astralite-Resonite Alloy | `mvtink_alloy_astralite_resonite` | Void, Tempered, Resonant |
| Ruby (`mvtink_ruby`) | Astralite-Ruby Alloy | `mvtink_alloy_astralite_ruby` | Void, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Astralite-Sanguinite Alloy | `mvtink_alloy_astralite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Astralite-Sapphire Alloy | `mvtink_alloy_astralite_sapphire` | Void, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Astralite-Shadowgem Alloy | `mvtink_alloy_astralite_shadowgem` | Void, Tempered, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Astralite-Shulkerite Alloy | `mvtink_alloy_astralite_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Astralite-Silver Alloy | `mvtink_alloy_astralite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Astralite-Singularite Alloy | `mvtink_alloy_astralite_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Astralite-Soul Glass Crystal Alloy | `mvtink_alloy_astralite_soulsand_crystal` | Infernal, Void, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Astralite-Spatial Platinum Alloy | `mvtink_alloy_astralite_spatial_platinum` | Void, Tempered, Swift |
| Starlight Silver (`mvtink_starlight_silver`) | Astralite-Starlight Silver Alloy | `mvtink_alloy_astralite_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Astralite-Steel Alloy | `mvtink_alloy_astralite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Astralite-Stibnite Alloy | `mvtink_alloy_astralite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Astralite-Sulfur Alloy | `mvtink_alloy_astralite_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Astralite-Talc Alloy | `mvtink_alloy_astralite_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Astralite-Tesseract Crystal Alloy | `mvtink_alloy_astralite_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Astralite-Tin Alloy | `mvtink_alloy_astralite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Astralite-Titanium Alloy | `mvtink_alloy_astralite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Astralite-Topaz Alloy | `mvtink_alloy_astralite_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Astralite-Tourmaline Alloy | `mvtink_alloy_astralite_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Astralite-Tungsten Alloy | `mvtink_alloy_astralite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Astralite-Void Pyrite Alloy | `mvtink_alloy_astralite_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Astralite-Void Titanium Alloy | `mvtink_alloy_astralite_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Astralite-Voidstone Alloy | `mvtink_alloy_astralite_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Astralite-Compacted Volcanic Ash Alloy | `mvtink_alloy_astralite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Astralite-Warped Emerald Alloy | `mvtink_alloy_astralite_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Astralite-Warped Quartz Alloy | `mvtink_alloy_astralite_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Astralite-Pure Weeping Shard Alloy | `mvtink_alloy_astralite_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Astralite-Witherite Alloy | `mvtink_alloy_astralite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Astralite-Zero-Point Shard Alloy | `mvtink_alloy_astralite_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Astralite-Zinc Alloy | `mvtink_alloy_astralite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Astralite-Zircon Alloy | `mvtink_alloy_astralite_zircon` | Void, Terrain, Tempered |

#### Celestine · `mvtink_celestine` · 91 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Chorus Crystal (`mvtink_chorus_crystal`) | Celestine-Chorus Crystal Alloy | `mvtink_alloy_celestine_chorus_crystal` | Void, Radiant, Resonant |
| Chromite (`mvtink_chromite`) | Celestine-Chromite Alloy | `mvtink_alloy_celestine_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Celestine-Chrono Crystal Alloy | `mvtink_alloy_celestine_chrono_crystal` | Void, Resonant, Swift |
| Cinderite (`mvtink_cinderite`) | Celestine-Cinderite Alloy | `mvtink_alloy_celestine_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Celestine-Cinnabar Alloy | `mvtink_alloy_celestine_cinnabar` | Void, Terrain, Resonant |
| Coal (`mvtink_coal`) | Celestine-Coal Alloy | `mvtink_alloy_celestine_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Celestine-Cobalt Alloy | `mvtink_alloy_celestine_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Celestine-Copper Alloy | `mvtink_alloy_celestine_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Celestine-Cosmium Alloy | `mvtink_alloy_celestine_cosmium` | Void, Tempered, Resonant |
| Crimson Gold (`mvtink_crimson_gold`) | Celestine-Crimson Gold Alloy | `mvtink_alloy_celestine_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Celestine-Crimson Quartz Alloy | `mvtink_alloy_celestine_crimson_quartz` | Infernal, Void, Resonant |
| Cryolite (`mvtink_cryolite`) | Celestine-Cryolite Alloy | `mvtink_alloy_celestine_cryolite` | Void, Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Celestine-Cursed Brimstone Alloy | `mvtink_alloy_celestine_cursed_brimstone` | Infernal, Void, Resonant |
| Diamond (`mvtink_diamond`) | Celestine-Diamond Alloy | `mvtink_alloy_celestine_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Celestine-Dragon Scale Shard Alloy | `mvtink_alloy_celestine_dragon_shard` | Void, Terrain, Resonant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Celestine-Eclipse Gem Alloy | `mvtink_alloy_celestine_eclipse_gem` | Void, Radiant, Resonant |
| Emerald (`mvtink_emerald`) | Celestine-Emerald Alloy | `mvtink_alloy_celestine_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Celestine-End Crystal Shard Alloy | `mvtink_alloy_celestine_end_crystal_shard` | Void, Resonant, Swift |
| Enderite (`mvtink_enderite`) | Celestine-Enderite Alloy | `mvtink_alloy_celestine_enderite` | Void, Tempered, Resonant |
| Fire Opal (`mvtink_fire_opal`) | Celestine-Fire Opal Alloy | `mvtink_alloy_celestine_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Celestine-Flint Alloy | `mvtink_alloy_celestine_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Celestine-Fluorite Alloy | `mvtink_alloy_celestine_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | Celestine-Galena Alloy | `mvtink_alloy_celestine_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Celestine-Ghast Tear Shard Alloy | `mvtink_alloy_celestine_ghast_tear_shard` | Infernal, Void, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Celestine-Glowstone Gem Alloy | `mvtink_alloy_celestine_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Celestine-Gold Alloy | `mvtink_alloy_celestine_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Celestine-Graphite Alloy | `mvtink_alloy_celestine_graphite` | Void, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Celestine-Gravitite Alloy | `mvtink_alloy_celestine_gravitite` | Void, Tempered, Resonant |
| Gypsum (`mvtink_gypsum`) | Celestine-Gypsum Alloy | `mvtink_alloy_celestine_gypsum` | Void, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Celestine-Helliron Alloy | `mvtink_alloy_celestine_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Celestine-Ignis Ferrum Alloy | `mvtink_alloy_celestine_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Celestine-Infernal Obsidian Alloy | `mvtink_alloy_celestine_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Celestine-Netherite-Infused Quartz Alloy | `mvtink_alloy_celestine_infused_quartz` | Infernal, Void, Resonant |
| Iron (`mvtink_iron`) | Celestine-Iron Alloy | `mvtink_alloy_celestine_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Celestine-Jade Alloy | `mvtink_alloy_celestine_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Celestine-Kaolinite Alloy | `mvtink_alloy_celestine_kaolinite` | Void, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Celestine-Lapis Lazuli Alloy | `mvtink_alloy_celestine_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Celestine-Lapis Matrix Alloy | `mvtink_alloy_celestine_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Celestine-Magma Brimstone Alloy | `mvtink_alloy_celestine_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Celestine-Magmacite Alloy | `mvtink_alloy_celestine_magmacite` | Infernal, Void, Resonant |
| Magnetite (`mvtink_magnetite`) | Celestine-Magnetite Alloy | `mvtink_alloy_celestine_magnetite` | Void, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Celestine-Malachite Alloy | `mvtink_alloy_celestine_malachite` | Void, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Celestine-Nebulite Alloy | `mvtink_alloy_celestine_nebulite` | Void, Resonant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Celestine-Nether Bismuth Alloy | `mvtink_alloy_celestine_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Celestine-Nether Tungsten Alloy | `mvtink_alloy_celestine_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Celestine-Netherite Scrap Shard Alloy | `mvtink_alloy_celestine_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Celestine-Nickel Alloy | `mvtink_alloy_celestine_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Celestine-Null-Shard Alloy | `mvtink_alloy_celestine_null_shard` | Void, Resonant, Volatile |
| Obsidian (`mvtink_obsidian`) | Celestine-Obsidian Alloy | `mvtink_alloy_celestine_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Celestine-Obsidianite Alloy | `mvtink_alloy_celestine_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Celestine-Opal Alloy | `mvtink_alloy_celestine_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Celestine-Ender Pearl Core Alloy | `mvtink_alloy_celestine_pearl_core` | Void, Radiant, Resonant |
| Phantomite (`mvtink_phantomite`) | Celestine-Phantomite Alloy | `mvtink_alloy_celestine_phantomite` | Void, Resonant, Volatile |
| Platinum (`mvtink_platinum`) | Celestine-Platinum Alloy | `mvtink_alloy_celestine_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Celestine-Prismarine Alloy | `mvtink_alloy_celestine_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Celestine-Pyrite Alloy | `mvtink_alloy_celestine_pyrite` | Void, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Celestine-Pyrophore Alloy | `mvtink_alloy_celestine_pyrophore` | Infernal, Void, Resonant |
| Nether Quartz (`mvtink_quartz`) | Celestine-Nether Quartz Alloy | `mvtink_alloy_celestine_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Celestine-Redstone Alloy | `mvtink_alloy_celestine_redstone` | Void, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Celestine-Resonite Alloy | `mvtink_alloy_celestine_resonite` | Void, Resonant, Swift |
| Ruby (`mvtink_ruby`) | Celestine-Ruby Alloy | `mvtink_alloy_celestine_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Celestine-Sanguinite Alloy | `mvtink_alloy_celestine_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Celestine-Sapphire Alloy | `mvtink_alloy_celestine_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Celestine-Shadowgem Alloy | `mvtink_alloy_celestine_shadowgem` | Void, Radiant, Resonant |
| Shulkerite (`mvtink_shulkerite`) | Celestine-Shulkerite Alloy | `mvtink_alloy_celestine_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Celestine-Silver Alloy | `mvtink_alloy_celestine_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Celestine-Singularite Alloy | `mvtink_alloy_celestine_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Celestine-Soul Glass Crystal Alloy | `mvtink_alloy_celestine_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Celestine-Spatial Platinum Alloy | `mvtink_alloy_celestine_spatial_platinum` | Void, Tempered, Resonant |
| Starlight Silver (`mvtink_starlight_silver`) | Celestine-Starlight Silver Alloy | `mvtink_alloy_celestine_starlight_silver` | Void, Tempered, Resonant |
| Steel (`mvtink_steel`) | Celestine-Steel Alloy | `mvtink_alloy_celestine_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Celestine-Stibnite Alloy | `mvtink_alloy_celestine_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Celestine-Sulfur Alloy | `mvtink_alloy_celestine_sulfur` | Infernal, Void, Resonant |
| Talc (`mvtink_talc`) | Celestine-Talc Alloy | `mvtink_alloy_celestine_talc` | Void, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Celestine-Tesseract Crystal Alloy | `mvtink_alloy_celestine_tesseract_crystal` | Void, Resonant, Swift |
| Tin (`mvtink_tin`) | Celestine-Tin Alloy | `mvtink_alloy_celestine_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Celestine-Titanium Alloy | `mvtink_alloy_celestine_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Celestine-Topaz Alloy | `mvtink_alloy_celestine_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Celestine-Tourmaline Alloy | `mvtink_alloy_celestine_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Celestine-Tungsten Alloy | `mvtink_alloy_celestine_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Celestine-Void Pyrite Alloy | `mvtink_alloy_celestine_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Celestine-Void Titanium Alloy | `mvtink_alloy_celestine_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | Celestine-Voidstone Alloy | `mvtink_alloy_celestine_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Celestine-Compacted Volcanic Ash Alloy | `mvtink_alloy_celestine_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Celestine-Warped Emerald Alloy | `mvtink_alloy_celestine_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Celestine-Warped Quartz Alloy | `mvtink_alloy_celestine_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Celestine-Pure Weeping Shard Alloy | `mvtink_alloy_celestine_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Celestine-Witherite Alloy | `mvtink_alloy_celestine_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Celestine-Zero-Point Shard Alloy | `mvtink_alloy_celestine_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Celestine-Zinc Alloy | `mvtink_alloy_celestine_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Celestine-Zircon Alloy | `mvtink_alloy_celestine_zircon` | Void, Terrain, Resonant |

#### Chorus Crystal · `mvtink_chorus_crystal` · 90 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Chromite (`mvtink_chromite`) | Chorus Crystal-Chromite Alloy | `mvtink_alloy_chorus_crystal_chromite` | Infernal, Void, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Chorus Crystal-Chrono Crystal Alloy | `mvtink_alloy_chorus_crystal_chrono_crystal` | Void, Radiant, Resonant |
| Cinderite (`mvtink_cinderite`) | Chorus Crystal-Cinderite Alloy | `mvtink_alloy_chorus_crystal_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Chorus Crystal-Cinnabar Alloy | `mvtink_alloy_chorus_crystal_cinnabar` | Void, Terrain, Radiant |
| Coal (`mvtink_coal`) | Chorus Crystal-Coal Alloy | `mvtink_alloy_chorus_crystal_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Chorus Crystal-Cobalt Alloy | `mvtink_alloy_chorus_crystal_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Chorus Crystal-Copper Alloy | `mvtink_alloy_chorus_crystal_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Chorus Crystal-Cosmium Alloy | `mvtink_alloy_chorus_crystal_cosmium` | Void, Tempered, Radiant |
| Crimson Gold (`mvtink_crimson_gold`) | Chorus Crystal-Crimson Gold Alloy | `mvtink_alloy_chorus_crystal_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Chorus Crystal-Crimson Quartz Alloy | `mvtink_alloy_chorus_crystal_crimson_quartz` | Infernal, Void, Radiant |
| Cryolite (`mvtink_cryolite`) | Chorus Crystal-Cryolite Alloy | `mvtink_alloy_chorus_crystal_cryolite` | Void, Terrain, Radiant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Chorus Crystal-Cursed Brimstone Alloy | `mvtink_alloy_chorus_crystal_cursed_brimstone` | Infernal, Void, Radiant |
| Diamond (`mvtink_diamond`) | Chorus Crystal-Diamond Alloy | `mvtink_alloy_chorus_crystal_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Chorus Crystal-Dragon Scale Shard Alloy | `mvtink_alloy_chorus_crystal_dragon_shard` | Void, Terrain, Radiant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Chorus Crystal-Eclipse Gem Alloy | `mvtink_alloy_chorus_crystal_eclipse_gem` | Void, Radiant, Swift |
| Emerald (`mvtink_emerald`) | Chorus Crystal-Emerald Alloy | `mvtink_alloy_chorus_crystal_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Chorus Crystal-End Crystal Shard Alloy | `mvtink_alloy_chorus_crystal_end_crystal_shard` | Void, Radiant, Resonant |
| Enderite (`mvtink_enderite`) | Chorus Crystal-Enderite Alloy | `mvtink_alloy_chorus_crystal_enderite` | Void, Tempered, Radiant |
| Fire Opal (`mvtink_fire_opal`) | Chorus Crystal-Fire Opal Alloy | `mvtink_alloy_chorus_crystal_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Chorus Crystal-Flint Alloy | `mvtink_alloy_chorus_crystal_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Chorus Crystal-Fluorite Alloy | `mvtink_alloy_chorus_crystal_fluorite` | Void, Terrain, Radiant |
| Galena (`mvtink_galena`) | Chorus Crystal-Galena Alloy | `mvtink_alloy_chorus_crystal_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Chorus Crystal-Ghast Tear Shard Alloy | `mvtink_alloy_chorus_crystal_ghast_tear_shard` | Infernal, Void, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Chorus Crystal-Glowstone Gem Alloy | `mvtink_alloy_chorus_crystal_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Chorus Crystal-Gold Alloy | `mvtink_alloy_chorus_crystal_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Chorus Crystal-Graphite Alloy | `mvtink_alloy_chorus_crystal_graphite` | Void, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Chorus Crystal-Gravitite Alloy | `mvtink_alloy_chorus_crystal_gravitite` | Void, Tempered, Radiant |
| Gypsum (`mvtink_gypsum`) | Chorus Crystal-Gypsum Alloy | `mvtink_alloy_chorus_crystal_gypsum` | Void, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Chorus Crystal-Helliron Alloy | `mvtink_alloy_chorus_crystal_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Chorus Crystal-Ignis Ferrum Alloy | `mvtink_alloy_chorus_crystal_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Chorus Crystal-Infernal Obsidian Alloy | `mvtink_alloy_chorus_crystal_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Chorus Crystal-Netherite-Infused Quartz Alloy | `mvtink_alloy_chorus_crystal_infused_quartz` | Infernal, Void, Radiant |
| Iron (`mvtink_iron`) | Chorus Crystal-Iron Alloy | `mvtink_alloy_chorus_crystal_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Chorus Crystal-Jade Alloy | `mvtink_alloy_chorus_crystal_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Chorus Crystal-Kaolinite Alloy | `mvtink_alloy_chorus_crystal_kaolinite` | Void, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Chorus Crystal-Lapis Lazuli Alloy | `mvtink_alloy_chorus_crystal_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Chorus Crystal-Lapis Matrix Alloy | `mvtink_alloy_chorus_crystal_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Chorus Crystal-Magma Brimstone Alloy | `mvtink_alloy_chorus_crystal_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Chorus Crystal-Magmacite Alloy | `mvtink_alloy_chorus_crystal_magmacite` | Infernal, Void, Radiant |
| Magnetite (`mvtink_magnetite`) | Chorus Crystal-Magnetite Alloy | `mvtink_alloy_chorus_crystal_magnetite` | Void, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Chorus Crystal-Malachite Alloy | `mvtink_alloy_chorus_crystal_malachite` | Void, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Chorus Crystal-Nebulite Alloy | `mvtink_alloy_chorus_crystal_nebulite` | Void, Radiant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Chorus Crystal-Nether Bismuth Alloy | `mvtink_alloy_chorus_crystal_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Chorus Crystal-Nether Tungsten Alloy | `mvtink_alloy_chorus_crystal_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Chorus Crystal-Netherite Scrap Shard Alloy | `mvtink_alloy_chorus_crystal_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Chorus Crystal-Nickel Alloy | `mvtink_alloy_chorus_crystal_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Chorus Crystal-Null-Shard Alloy | `mvtink_alloy_chorus_crystal_null_shard` | Void, Radiant, Volatile |
| Obsidian (`mvtink_obsidian`) | Chorus Crystal-Obsidian Alloy | `mvtink_alloy_chorus_crystal_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Chorus Crystal-Obsidianite Alloy | `mvtink_alloy_chorus_crystal_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Chorus Crystal-Opal Alloy | `mvtink_alloy_chorus_crystal_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Chorus Crystal-Ender Pearl Core Alloy | `mvtink_alloy_chorus_crystal_pearl_core` | Void, Radiant |
| Phantomite (`mvtink_phantomite`) | Chorus Crystal-Phantomite Alloy | `mvtink_alloy_chorus_crystal_phantomite` | Void, Radiant, Volatile |
| Platinum (`mvtink_platinum`) | Chorus Crystal-Platinum Alloy | `mvtink_alloy_chorus_crystal_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Chorus Crystal-Prismarine Alloy | `mvtink_alloy_chorus_crystal_prismarine` | Void, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Chorus Crystal-Pyrite Alloy | `mvtink_alloy_chorus_crystal_pyrite` | Void, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Chorus Crystal-Pyrophore Alloy | `mvtink_alloy_chorus_crystal_pyrophore` | Infernal, Void, Radiant |
| Nether Quartz (`mvtink_quartz`) | Chorus Crystal-Nether Quartz Alloy | `mvtink_alloy_chorus_crystal_quartz` | Void, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Chorus Crystal-Redstone Alloy | `mvtink_alloy_chorus_crystal_redstone` | Void, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Chorus Crystal-Resonite Alloy | `mvtink_alloy_chorus_crystal_resonite` | Void, Radiant, Resonant |
| Ruby (`mvtink_ruby`) | Chorus Crystal-Ruby Alloy | `mvtink_alloy_chorus_crystal_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Chorus Crystal-Sanguinite Alloy | `mvtink_alloy_chorus_crystal_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Chorus Crystal-Sapphire Alloy | `mvtink_alloy_chorus_crystal_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Chorus Crystal-Shadowgem Alloy | `mvtink_alloy_chorus_crystal_shadowgem` | Void, Radiant, Swift |
| Shulkerite (`mvtink_shulkerite`) | Chorus Crystal-Shulkerite Alloy | `mvtink_alloy_chorus_crystal_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Chorus Crystal-Silver Alloy | `mvtink_alloy_chorus_crystal_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Chorus Crystal-Singularite Alloy | `mvtink_alloy_chorus_crystal_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Chorus Crystal-Soul Glass Crystal Alloy | `mvtink_alloy_chorus_crystal_soulsand_crystal` | Infernal, Void, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Chorus Crystal-Spatial Platinum Alloy | `mvtink_alloy_chorus_crystal_spatial_platinum` | Void, Tempered, Radiant |
| Starlight Silver (`mvtink_starlight_silver`) | Chorus Crystal-Starlight Silver Alloy | `mvtink_alloy_chorus_crystal_starlight_silver` | Void, Tempered, Radiant |
| Steel (`mvtink_steel`) | Chorus Crystal-Steel Alloy | `mvtink_alloy_chorus_crystal_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Chorus Crystal-Stibnite Alloy | `mvtink_alloy_chorus_crystal_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Chorus Crystal-Sulfur Alloy | `mvtink_alloy_chorus_crystal_sulfur` | Infernal, Void, Radiant |
| Talc (`mvtink_talc`) | Chorus Crystal-Talc Alloy | `mvtink_alloy_chorus_crystal_talc` | Void, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Chorus Crystal-Tesseract Crystal Alloy | `mvtink_alloy_chorus_crystal_tesseract_crystal` | Void, Radiant, Resonant |
| Tin (`mvtink_tin`) | Chorus Crystal-Tin Alloy | `mvtink_alloy_chorus_crystal_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Chorus Crystal-Titanium Alloy | `mvtink_alloy_chorus_crystal_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Chorus Crystal-Topaz Alloy | `mvtink_alloy_chorus_crystal_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Chorus Crystal-Tourmaline Alloy | `mvtink_alloy_chorus_crystal_tourmaline` | Void, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Chorus Crystal-Tungsten Alloy | `mvtink_alloy_chorus_crystal_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Chorus Crystal-Void Pyrite Alloy | `mvtink_alloy_chorus_crystal_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Chorus Crystal-Void Titanium Alloy | `mvtink_alloy_chorus_crystal_void_titanium` | Void, Tempered, Radiant |
| Voidstone (`mvtink_voidstone`) | Chorus Crystal-Voidstone Alloy | `mvtink_alloy_chorus_crystal_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Chorus Crystal-Compacted Volcanic Ash Alloy | `mvtink_alloy_chorus_crystal_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Chorus Crystal-Warped Emerald Alloy | `mvtink_alloy_chorus_crystal_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Chorus Crystal-Warped Quartz Alloy | `mvtink_alloy_chorus_crystal_warped_quartz` | Void, Radiant, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Chorus Crystal-Pure Weeping Shard Alloy | `mvtink_alloy_chorus_crystal_weeping_shard` | Infernal, Void, Radiant |
| Witherite (`mvtink_witherite`) | Chorus Crystal-Witherite Alloy | `mvtink_alloy_chorus_crystal_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Chorus Crystal-Zero-Point Shard Alloy | `mvtink_alloy_chorus_crystal_zero_point` | Void, Radiant, Volatile |
| Zinc (`mvtink_zinc`) | Chorus Crystal-Zinc Alloy | `mvtink_alloy_chorus_crystal_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Chorus Crystal-Zircon Alloy | `mvtink_alloy_chorus_crystal_zircon` | Void, Terrain, Radiant |

#### Chrono Crystal · `mvtink_chrono_crystal` · 88 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cinderite (`mvtink_cinderite`) | Chrono Crystal-Cinderite Alloy | `mvtink_alloy_chrono_crystal_cinderite` | Infernal, Void, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Chrono Crystal-Cinnabar Alloy | `mvtink_alloy_chrono_crystal_cinnabar` | Void, Terrain, Resonant |
| Coal (`mvtink_coal`) | Chrono Crystal-Coal Alloy | `mvtink_alloy_chrono_crystal_coal` | Void, Primal, Terrain |
| Cobalt (`mvtink_cobalt`) | Chrono Crystal-Cobalt Alloy | `mvtink_alloy_chrono_crystal_cobalt` | Infernal, Void, Tempered |
| Copper (`mvtink_copper`) | Chrono Crystal-Copper Alloy | `mvtink_alloy_chrono_crystal_copper` | Void, Primal, Tempered |
| Cosmium (`mvtink_cosmium`) | Chrono Crystal-Cosmium Alloy | `mvtink_alloy_chrono_crystal_cosmium` | Void, Tempered, Resonant |
| Crimson Gold (`mvtink_crimson_gold`) | Chrono Crystal-Crimson Gold Alloy | `mvtink_alloy_chrono_crystal_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Chrono Crystal-Crimson Quartz Alloy | `mvtink_alloy_chrono_crystal_crimson_quartz` | Infernal, Void, Resonant |
| Cryolite (`mvtink_cryolite`) | Chrono Crystal-Cryolite Alloy | `mvtink_alloy_chrono_crystal_cryolite` | Void, Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Chrono Crystal-Cursed Brimstone Alloy | `mvtink_alloy_chrono_crystal_cursed_brimstone` | Infernal, Void, Resonant |
| Diamond (`mvtink_diamond`) | Chrono Crystal-Diamond Alloy | `mvtink_alloy_chrono_crystal_diamond` | Void, Primal, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Chrono Crystal-Dragon Scale Shard Alloy | `mvtink_alloy_chrono_crystal_dragon_shard` | Void, Terrain, Resonant |
| Eclipse Gem (`mvtink_eclipse_gem`) | Chrono Crystal-Eclipse Gem Alloy | `mvtink_alloy_chrono_crystal_eclipse_gem` | Void, Radiant, Resonant |
| Emerald (`mvtink_emerald`) | Chrono Crystal-Emerald Alloy | `mvtink_alloy_chrono_crystal_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Chrono Crystal-End Crystal Shard Alloy | `mvtink_alloy_chrono_crystal_end_crystal_shard` | Void, Resonant, Swift |
| Enderite (`mvtink_enderite`) | Chrono Crystal-Enderite Alloy | `mvtink_alloy_chrono_crystal_enderite` | Void, Tempered, Resonant |
| Fire Opal (`mvtink_fire_opal`) | Chrono Crystal-Fire Opal Alloy | `mvtink_alloy_chrono_crystal_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Chrono Crystal-Flint Alloy | `mvtink_alloy_chrono_crystal_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Chrono Crystal-Fluorite Alloy | `mvtink_alloy_chrono_crystal_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | Chrono Crystal-Galena Alloy | `mvtink_alloy_chrono_crystal_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Chrono Crystal-Ghast Tear Shard Alloy | `mvtink_alloy_chrono_crystal_ghast_tear_shard` | Infernal, Void, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Chrono Crystal-Glowstone Gem Alloy | `mvtink_alloy_chrono_crystal_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Chrono Crystal-Gold Alloy | `mvtink_alloy_chrono_crystal_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Chrono Crystal-Graphite Alloy | `mvtink_alloy_chrono_crystal_graphite` | Void, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Chrono Crystal-Gravitite Alloy | `mvtink_alloy_chrono_crystal_gravitite` | Void, Tempered, Resonant |
| Gypsum (`mvtink_gypsum`) | Chrono Crystal-Gypsum Alloy | `mvtink_alloy_chrono_crystal_gypsum` | Void, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Chrono Crystal-Helliron Alloy | `mvtink_alloy_chrono_crystal_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Chrono Crystal-Ignis Ferrum Alloy | `mvtink_alloy_chrono_crystal_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Chrono Crystal-Infernal Obsidian Alloy | `mvtink_alloy_chrono_crystal_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Chrono Crystal-Netherite-Infused Quartz Alloy | `mvtink_alloy_chrono_crystal_infused_quartz` | Infernal, Void, Resonant |
| Iron (`mvtink_iron`) | Chrono Crystal-Iron Alloy | `mvtink_alloy_chrono_crystal_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Chrono Crystal-Jade Alloy | `mvtink_alloy_chrono_crystal_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Chrono Crystal-Kaolinite Alloy | `mvtink_alloy_chrono_crystal_kaolinite` | Void, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Chrono Crystal-Lapis Lazuli Alloy | `mvtink_alloy_chrono_crystal_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Chrono Crystal-Lapis Matrix Alloy | `mvtink_alloy_chrono_crystal_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Chrono Crystal-Magma Brimstone Alloy | `mvtink_alloy_chrono_crystal_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Chrono Crystal-Magmacite Alloy | `mvtink_alloy_chrono_crystal_magmacite` | Infernal, Void, Resonant |
| Magnetite (`mvtink_magnetite`) | Chrono Crystal-Magnetite Alloy | `mvtink_alloy_chrono_crystal_magnetite` | Void, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Chrono Crystal-Malachite Alloy | `mvtink_alloy_chrono_crystal_malachite` | Void, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Chrono Crystal-Nebulite Alloy | `mvtink_alloy_chrono_crystal_nebulite` | Void, Resonant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Chrono Crystal-Nether Bismuth Alloy | `mvtink_alloy_chrono_crystal_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Chrono Crystal-Nether Tungsten Alloy | `mvtink_alloy_chrono_crystal_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Chrono Crystal-Netherite Scrap Shard Alloy | `mvtink_alloy_chrono_crystal_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Chrono Crystal-Nickel Alloy | `mvtink_alloy_chrono_crystal_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Chrono Crystal-Null-Shard Alloy | `mvtink_alloy_chrono_crystal_null_shard` | Void, Resonant, Volatile |
| Obsidian (`mvtink_obsidian`) | Chrono Crystal-Obsidian Alloy | `mvtink_alloy_chrono_crystal_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Chrono Crystal-Obsidianite Alloy | `mvtink_alloy_chrono_crystal_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Chrono Crystal-Opal Alloy | `mvtink_alloy_chrono_crystal_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Chrono Crystal-Ender Pearl Core Alloy | `mvtink_alloy_chrono_crystal_pearl_core` | Void, Radiant, Resonant |
| Phantomite (`mvtink_phantomite`) | Chrono Crystal-Phantomite Alloy | `mvtink_alloy_chrono_crystal_phantomite` | Void, Resonant, Volatile |
| Platinum (`mvtink_platinum`) | Chrono Crystal-Platinum Alloy | `mvtink_alloy_chrono_crystal_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Chrono Crystal-Prismarine Alloy | `mvtink_alloy_chrono_crystal_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Chrono Crystal-Pyrite Alloy | `mvtink_alloy_chrono_crystal_pyrite` | Void, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Chrono Crystal-Pyrophore Alloy | `mvtink_alloy_chrono_crystal_pyrophore` | Infernal, Void, Resonant |
| Nether Quartz (`mvtink_quartz`) | Chrono Crystal-Nether Quartz Alloy | `mvtink_alloy_chrono_crystal_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Chrono Crystal-Redstone Alloy | `mvtink_alloy_chrono_crystal_redstone` | Void, Primal, Resonant |
| Resonite (`mvtink_resonite`) | Chrono Crystal-Resonite Alloy | `mvtink_alloy_chrono_crystal_resonite` | Void, Resonant, Swift |
| Ruby (`mvtink_ruby`) | Chrono Crystal-Ruby Alloy | `mvtink_alloy_chrono_crystal_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Chrono Crystal-Sanguinite Alloy | `mvtink_alloy_chrono_crystal_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Chrono Crystal-Sapphire Alloy | `mvtink_alloy_chrono_crystal_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Chrono Crystal-Shadowgem Alloy | `mvtink_alloy_chrono_crystal_shadowgem` | Void, Radiant, Resonant |
| Shulkerite (`mvtink_shulkerite`) | Chrono Crystal-Shulkerite Alloy | `mvtink_alloy_chrono_crystal_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Chrono Crystal-Silver Alloy | `mvtink_alloy_chrono_crystal_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Chrono Crystal-Singularite Alloy | `mvtink_alloy_chrono_crystal_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Chrono Crystal-Soul Glass Crystal Alloy | `mvtink_alloy_chrono_crystal_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Chrono Crystal-Spatial Platinum Alloy | `mvtink_alloy_chrono_crystal_spatial_platinum` | Void, Tempered, Resonant |
| Starlight Silver (`mvtink_starlight_silver`) | Chrono Crystal-Starlight Silver Alloy | `mvtink_alloy_chrono_crystal_starlight_silver` | Void, Tempered, Resonant |
| Steel (`mvtink_steel`) | Chrono Crystal-Steel Alloy | `mvtink_alloy_chrono_crystal_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Chrono Crystal-Stibnite Alloy | `mvtink_alloy_chrono_crystal_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Chrono Crystal-Sulfur Alloy | `mvtink_alloy_chrono_crystal_sulfur` | Infernal, Void, Resonant |
| Talc (`mvtink_talc`) | Chrono Crystal-Talc Alloy | `mvtink_alloy_chrono_crystal_talc` | Void, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Chrono Crystal-Tesseract Crystal Alloy | `mvtink_alloy_chrono_crystal_tesseract_crystal` | Void, Resonant, Swift |
| Tin (`mvtink_tin`) | Chrono Crystal-Tin Alloy | `mvtink_alloy_chrono_crystal_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Chrono Crystal-Titanium Alloy | `mvtink_alloy_chrono_crystal_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Chrono Crystal-Topaz Alloy | `mvtink_alloy_chrono_crystal_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Chrono Crystal-Tourmaline Alloy | `mvtink_alloy_chrono_crystal_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Chrono Crystal-Tungsten Alloy | `mvtink_alloy_chrono_crystal_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Chrono Crystal-Void Pyrite Alloy | `mvtink_alloy_chrono_crystal_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Chrono Crystal-Void Titanium Alloy | `mvtink_alloy_chrono_crystal_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | Chrono Crystal-Voidstone Alloy | `mvtink_alloy_chrono_crystal_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Chrono Crystal-Compacted Volcanic Ash Alloy | `mvtink_alloy_chrono_crystal_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Chrono Crystal-Warped Emerald Alloy | `mvtink_alloy_chrono_crystal_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Chrono Crystal-Warped Quartz Alloy | `mvtink_alloy_chrono_crystal_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Chrono Crystal-Pure Weeping Shard Alloy | `mvtink_alloy_chrono_crystal_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Chrono Crystal-Witherite Alloy | `mvtink_alloy_chrono_crystal_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Chrono Crystal-Zero-Point Shard Alloy | `mvtink_alloy_chrono_crystal_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Chrono Crystal-Zinc Alloy | `mvtink_alloy_chrono_crystal_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Chrono Crystal-Zircon Alloy | `mvtink_alloy_chrono_crystal_zircon` | Void, Terrain, Resonant |

#### Cosmium · `mvtink_cosmium` · 82 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Crimson Gold (`mvtink_crimson_gold`) | Cosmium-Crimson Gold Alloy | `mvtink_alloy_cosmium_crimson_gold` | Infernal, Void, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Cosmium-Crimson Quartz Alloy | `mvtink_alloy_cosmium_crimson_quartz` | Infernal, Void, Tempered |
| Cryolite (`mvtink_cryolite`) | Cosmium-Cryolite Alloy | `mvtink_alloy_cosmium_cryolite` | Void, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Cosmium-Cursed Brimstone Alloy | `mvtink_alloy_cosmium_cursed_brimstone` | Infernal, Void, Tempered |
| Diamond (`mvtink_diamond`) | Cosmium-Diamond Alloy | `mvtink_alloy_cosmium_diamond` | Void, Primal, Tempered |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Cosmium-Dragon Scale Shard Alloy | `mvtink_alloy_cosmium_dragon_shard` | Void, Terrain, Tempered |
| Eclipse Gem (`mvtink_eclipse_gem`) | Cosmium-Eclipse Gem Alloy | `mvtink_alloy_cosmium_eclipse_gem` | Void, Tempered, Radiant |
| Emerald (`mvtink_emerald`) | Cosmium-Emerald Alloy | `mvtink_alloy_cosmium_emerald` | Void, Primal, Tempered |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Cosmium-End Crystal Shard Alloy | `mvtink_alloy_cosmium_end_crystal_shard` | Void, Tempered, Resonant |
| Enderite (`mvtink_enderite`) | Cosmium-Enderite Alloy | `mvtink_alloy_cosmium_enderite` | Void, Tempered, Swift |
| Fire Opal (`mvtink_fire_opal`) | Cosmium-Fire Opal Alloy | `mvtink_alloy_cosmium_fire_opal` | Infernal, Void, Tempered |
| Flint (`mvtink_flint`) | Cosmium-Flint Alloy | `mvtink_alloy_cosmium_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Cosmium-Fluorite Alloy | `mvtink_alloy_cosmium_fluorite` | Void, Terrain, Tempered |
| Galena (`mvtink_galena`) | Cosmium-Galena Alloy | `mvtink_alloy_cosmium_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Cosmium-Ghast Tear Shard Alloy | `mvtink_alloy_cosmium_ghast_tear_shard` | Infernal, Void, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Cosmium-Glowstone Gem Alloy | `mvtink_alloy_cosmium_glowstone_gem` | Infernal, Void, Tempered |
| Gold (`mvtink_gold`) | Cosmium-Gold Alloy | `mvtink_alloy_cosmium_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Cosmium-Graphite Alloy | `mvtink_alloy_cosmium_graphite` | Void, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Cosmium-Gravitite Alloy | `mvtink_alloy_cosmium_gravitite` | Void, Tempered, Swift |
| Gypsum (`mvtink_gypsum`) | Cosmium-Gypsum Alloy | `mvtink_alloy_cosmium_gypsum` | Void, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Cosmium-Helliron Alloy | `mvtink_alloy_cosmium_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Cosmium-Ignis Ferrum Alloy | `mvtink_alloy_cosmium_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Cosmium-Infernal Obsidian Alloy | `mvtink_alloy_cosmium_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Cosmium-Netherite-Infused Quartz Alloy | `mvtink_alloy_cosmium_infused_quartz` | Infernal, Void, Tempered |
| Iron (`mvtink_iron`) | Cosmium-Iron Alloy | `mvtink_alloy_cosmium_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Cosmium-Jade Alloy | `mvtink_alloy_cosmium_jade` | Void, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Cosmium-Kaolinite Alloy | `mvtink_alloy_cosmium_kaolinite` | Void, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Cosmium-Lapis Lazuli Alloy | `mvtink_alloy_cosmium_lapis` | Void, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Cosmium-Lapis Matrix Alloy | `mvtink_alloy_cosmium_lapis_matrix` | Void, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Cosmium-Magma Brimstone Alloy | `mvtink_alloy_cosmium_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Cosmium-Magmacite Alloy | `mvtink_alloy_cosmium_magmacite` | Infernal, Void, Tempered |
| Magnetite (`mvtink_magnetite`) | Cosmium-Magnetite Alloy | `mvtink_alloy_cosmium_magnetite` | Void, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Cosmium-Malachite Alloy | `mvtink_alloy_cosmium_malachite` | Void, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Cosmium-Nebulite Alloy | `mvtink_alloy_cosmium_nebulite` | Void, Tempered, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Cosmium-Nether Bismuth Alloy | `mvtink_alloy_cosmium_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Cosmium-Nether Tungsten Alloy | `mvtink_alloy_cosmium_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Cosmium-Netherite Scrap Shard Alloy | `mvtink_alloy_cosmium_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Cosmium-Nickel Alloy | `mvtink_alloy_cosmium_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Cosmium-Null-Shard Alloy | `mvtink_alloy_cosmium_null_shard` | Void, Tempered, Volatile |
| Obsidian (`mvtink_obsidian`) | Cosmium-Obsidian Alloy | `mvtink_alloy_cosmium_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Cosmium-Obsidianite Alloy | `mvtink_alloy_cosmium_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Cosmium-Opal Alloy | `mvtink_alloy_cosmium_opal` | Void, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Cosmium-Ender Pearl Core Alloy | `mvtink_alloy_cosmium_pearl_core` | Void, Tempered, Radiant |
| Phantomite (`mvtink_phantomite`) | Cosmium-Phantomite Alloy | `mvtink_alloy_cosmium_phantomite` | Void, Tempered, Volatile |
| Platinum (`mvtink_platinum`) | Cosmium-Platinum Alloy | `mvtink_alloy_cosmium_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Cosmium-Prismarine Alloy | `mvtink_alloy_cosmium_prismarine` | Void, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Cosmium-Pyrite Alloy | `mvtink_alloy_cosmium_pyrite` | Void, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Cosmium-Pyrophore Alloy | `mvtink_alloy_cosmium_pyrophore` | Infernal, Void, Tempered |
| Nether Quartz (`mvtink_quartz`) | Cosmium-Nether Quartz Alloy | `mvtink_alloy_cosmium_quartz` | Void, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Cosmium-Redstone Alloy | `mvtink_alloy_cosmium_redstone` | Void, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Cosmium-Resonite Alloy | `mvtink_alloy_cosmium_resonite` | Void, Tempered, Resonant |
| Ruby (`mvtink_ruby`) | Cosmium-Ruby Alloy | `mvtink_alloy_cosmium_ruby` | Void, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Cosmium-Sanguinite Alloy | `mvtink_alloy_cosmium_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Cosmium-Sapphire Alloy | `mvtink_alloy_cosmium_sapphire` | Void, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Cosmium-Shadowgem Alloy | `mvtink_alloy_cosmium_shadowgem` | Void, Tempered, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Cosmium-Shulkerite Alloy | `mvtink_alloy_cosmium_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Cosmium-Silver Alloy | `mvtink_alloy_cosmium_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Cosmium-Singularite Alloy | `mvtink_alloy_cosmium_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Cosmium-Soul Glass Crystal Alloy | `mvtink_alloy_cosmium_soulsand_crystal` | Infernal, Void, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Cosmium-Spatial Platinum Alloy | `mvtink_alloy_cosmium_spatial_platinum` | Void, Tempered, Swift |
| Starlight Silver (`mvtink_starlight_silver`) | Cosmium-Starlight Silver Alloy | `mvtink_alloy_cosmium_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Cosmium-Steel Alloy | `mvtink_alloy_cosmium_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Cosmium-Stibnite Alloy | `mvtink_alloy_cosmium_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Cosmium-Sulfur Alloy | `mvtink_alloy_cosmium_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Cosmium-Talc Alloy | `mvtink_alloy_cosmium_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Cosmium-Tesseract Crystal Alloy | `mvtink_alloy_cosmium_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Cosmium-Tin Alloy | `mvtink_alloy_cosmium_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Cosmium-Titanium Alloy | `mvtink_alloy_cosmium_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Cosmium-Topaz Alloy | `mvtink_alloy_cosmium_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Cosmium-Tourmaline Alloy | `mvtink_alloy_cosmium_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Cosmium-Tungsten Alloy | `mvtink_alloy_cosmium_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Cosmium-Void Pyrite Alloy | `mvtink_alloy_cosmium_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Cosmium-Void Titanium Alloy | `mvtink_alloy_cosmium_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Cosmium-Voidstone Alloy | `mvtink_alloy_cosmium_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Cosmium-Compacted Volcanic Ash Alloy | `mvtink_alloy_cosmium_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Cosmium-Warped Emerald Alloy | `mvtink_alloy_cosmium_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Cosmium-Warped Quartz Alloy | `mvtink_alloy_cosmium_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Cosmium-Pure Weeping Shard Alloy | `mvtink_alloy_cosmium_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Cosmium-Witherite Alloy | `mvtink_alloy_cosmium_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Cosmium-Zero-Point Shard Alloy | `mvtink_alloy_cosmium_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Cosmium-Zinc Alloy | `mvtink_alloy_cosmium_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Cosmium-Zircon Alloy | `mvtink_alloy_cosmium_zircon` | Void, Terrain, Tempered |

#### Dragon Scale Shard · `mvtink_dragon_shard` · 76 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Eclipse Gem (`mvtink_eclipse_gem`) | Dragon Scale Shard-Eclipse Gem Alloy | `mvtink_alloy_dragon_shard_eclipse_gem` | Void, Terrain, Radiant |
| Emerald (`mvtink_emerald`) | Dragon Scale Shard-Emerald Alloy | `mvtink_alloy_dragon_shard_emerald` | Void, Primal, Terrain |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Dragon Scale Shard-End Crystal Shard Alloy | `mvtink_alloy_dragon_shard_end_crystal_shard` | Void, Terrain, Resonant |
| Enderite (`mvtink_enderite`) | Dragon Scale Shard-Enderite Alloy | `mvtink_alloy_dragon_shard_enderite` | Void, Terrain, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Dragon Scale Shard-Fire Opal Alloy | `mvtink_alloy_dragon_shard_fire_opal` | Infernal, Void, Terrain |
| Flint (`mvtink_flint`) | Dragon Scale Shard-Flint Alloy | `mvtink_alloy_dragon_shard_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Dragon Scale Shard-Fluorite Alloy | `mvtink_alloy_dragon_shard_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | Dragon Scale Shard-Galena Alloy | `mvtink_alloy_dragon_shard_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Dragon Scale Shard-Ghast Tear Shard Alloy | `mvtink_alloy_dragon_shard_ghast_tear_shard` | Infernal, Void, Terrain |
| Glowstone Gem (`mvtink_glowstone_gem`) | Dragon Scale Shard-Glowstone Gem Alloy | `mvtink_alloy_dragon_shard_glowstone_gem` | Infernal, Void, Terrain |
| Gold (`mvtink_gold`) | Dragon Scale Shard-Gold Alloy | `mvtink_alloy_dragon_shard_gold` | Void, Primal, Terrain |
| Graphite (`mvtink_graphite`) | Dragon Scale Shard-Graphite Alloy | `mvtink_alloy_dragon_shard_graphite` | Void, Terrain, Swift |
| Gravitite (`mvtink_gravitite`) | Dragon Scale Shard-Gravitite Alloy | `mvtink_alloy_dragon_shard_gravitite` | Void, Terrain, Tempered |
| Gypsum (`mvtink_gypsum`) | Dragon Scale Shard-Gypsum Alloy | `mvtink_alloy_dragon_shard_gypsum` | Void, Terrain, Swift |
| Helliron (`mvtink_helliron`) | Dragon Scale Shard-Helliron Alloy | `mvtink_alloy_dragon_shard_helliron` | Infernal, Void, Terrain |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Dragon Scale Shard-Ignis Ferrum Alloy | `mvtink_alloy_dragon_shard_ignis_ferrum` | Infernal, Void, Terrain |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Dragon Scale Shard-Infernal Obsidian Alloy | `mvtink_alloy_dragon_shard_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Dragon Scale Shard-Netherite-Infused Quartz Alloy | `mvtink_alloy_dragon_shard_infused_quartz` | Infernal, Void, Terrain |
| Iron (`mvtink_iron`) | Dragon Scale Shard-Iron Alloy | `mvtink_alloy_dragon_shard_iron` | Void, Primal, Terrain |
| Jade (`mvtink_jade`) | Dragon Scale Shard-Jade Alloy | `mvtink_alloy_dragon_shard_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Dragon Scale Shard-Kaolinite Alloy | `mvtink_alloy_dragon_shard_kaolinite` | Void, Terrain, Swift |
| Lapis Lazuli (`mvtink_lapis`) | Dragon Scale Shard-Lapis Lazuli Alloy | `mvtink_alloy_dragon_shard_lapis` | Void, Primal, Terrain |
| Lapis Matrix (`mvtink_lapis_matrix`) | Dragon Scale Shard-Lapis Matrix Alloy | `mvtink_alloy_dragon_shard_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Dragon Scale Shard-Magma Brimstone Alloy | `mvtink_alloy_dragon_shard_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Dragon Scale Shard-Magmacite Alloy | `mvtink_alloy_dragon_shard_magmacite` | Infernal, Void, Terrain |
| Magnetite (`mvtink_magnetite`) | Dragon Scale Shard-Magnetite Alloy | `mvtink_alloy_dragon_shard_magnetite` | Void, Terrain, Swift |
| Malachite (`mvtink_malachite`) | Dragon Scale Shard-Malachite Alloy | `mvtink_alloy_dragon_shard_malachite` | Void, Terrain, Swift |
| Nebulite (`mvtink_nebulite`) | Dragon Scale Shard-Nebulite Alloy | `mvtink_alloy_dragon_shard_nebulite` | Void, Terrain, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Dragon Scale Shard-Nether Bismuth Alloy | `mvtink_alloy_dragon_shard_nether_bismuth` | Infernal, Void, Terrain |
| Nether Tungsten (`mvtink_nether_tungsten`) | Dragon Scale Shard-Nether Tungsten Alloy | `mvtink_alloy_dragon_shard_nether_tungsten` | Infernal, Void, Terrain |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Dragon Scale Shard-Netherite Scrap Shard Alloy | `mvtink_alloy_dragon_shard_netherite_shard` | Infernal, Void, Terrain |
| Nickel (`mvtink_nickel`) | Dragon Scale Shard-Nickel Alloy | `mvtink_alloy_dragon_shard_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Dragon Scale Shard-Null-Shard Alloy | `mvtink_alloy_dragon_shard_null_shard` | Void, Terrain, Volatile |
| Obsidian (`mvtink_obsidian`) | Dragon Scale Shard-Obsidian Alloy | `mvtink_alloy_dragon_shard_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Dragon Scale Shard-Obsidianite Alloy | `mvtink_alloy_dragon_shard_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Dragon Scale Shard-Opal Alloy | `mvtink_alloy_dragon_shard_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Dragon Scale Shard-Ender Pearl Core Alloy | `mvtink_alloy_dragon_shard_pearl_core` | Void, Terrain, Radiant |
| Phantomite (`mvtink_phantomite`) | Dragon Scale Shard-Phantomite Alloy | `mvtink_alloy_dragon_shard_phantomite` | Void, Terrain, Volatile |
| Platinum (`mvtink_platinum`) | Dragon Scale Shard-Platinum Alloy | `mvtink_alloy_dragon_shard_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Dragon Scale Shard-Prismarine Alloy | `mvtink_alloy_dragon_shard_prismarine` | Void, Primal, Terrain |
| Pyrite (`mvtink_pyrite`) | Dragon Scale Shard-Pyrite Alloy | `mvtink_alloy_dragon_shard_pyrite` | Void, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Dragon Scale Shard-Pyrophore Alloy | `mvtink_alloy_dragon_shard_pyrophore` | Infernal, Void, Terrain |
| Nether Quartz (`mvtink_quartz`) | Dragon Scale Shard-Nether Quartz Alloy | `mvtink_alloy_dragon_shard_quartz` | Void, Primal, Terrain |
| Redstone (`mvtink_redstone`) | Dragon Scale Shard-Redstone Alloy | `mvtink_alloy_dragon_shard_redstone` | Void, Primal, Terrain |
| Resonite (`mvtink_resonite`) | Dragon Scale Shard-Resonite Alloy | `mvtink_alloy_dragon_shard_resonite` | Void, Terrain, Resonant |
| Ruby (`mvtink_ruby`) | Dragon Scale Shard-Ruby Alloy | `mvtink_alloy_dragon_shard_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Dragon Scale Shard-Sanguinite Alloy | `mvtink_alloy_dragon_shard_sanguinite` | Infernal, Void, Terrain |
| Sapphire (`mvtink_sapphire`) | Dragon Scale Shard-Sapphire Alloy | `mvtink_alloy_dragon_shard_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Dragon Scale Shard-Shadowgem Alloy | `mvtink_alloy_dragon_shard_shadowgem` | Void, Terrain, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Dragon Scale Shard-Shulkerite Alloy | `mvtink_alloy_dragon_shard_shulkerite` | Void, Terrain, Swift |
| Silver (`mvtink_silver`) | Dragon Scale Shard-Silver Alloy | `mvtink_alloy_dragon_shard_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Dragon Scale Shard-Singularite Alloy | `mvtink_alloy_dragon_shard_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Dragon Scale Shard-Soul Glass Crystal Alloy | `mvtink_alloy_dragon_shard_soulsand_crystal` | Infernal, Void, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Dragon Scale Shard-Spatial Platinum Alloy | `mvtink_alloy_dragon_shard_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Dragon Scale Shard-Starlight Silver Alloy | `mvtink_alloy_dragon_shard_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Dragon Scale Shard-Steel Alloy | `mvtink_alloy_dragon_shard_steel` | Infernal, Void, Terrain |
| Stibnite (`mvtink_stibnite`) | Dragon Scale Shard-Stibnite Alloy | `mvtink_alloy_dragon_shard_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Dragon Scale Shard-Sulfur Alloy | `mvtink_alloy_dragon_shard_sulfur` | Infernal, Void, Terrain |
| Talc (`mvtink_talc`) | Dragon Scale Shard-Talc Alloy | `mvtink_alloy_dragon_shard_talc` | Void, Terrain, Swift |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Dragon Scale Shard-Tesseract Crystal Alloy | `mvtink_alloy_dragon_shard_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Dragon Scale Shard-Tin Alloy | `mvtink_alloy_dragon_shard_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Dragon Scale Shard-Titanium Alloy | `mvtink_alloy_dragon_shard_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Dragon Scale Shard-Topaz Alloy | `mvtink_alloy_dragon_shard_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Dragon Scale Shard-Tourmaline Alloy | `mvtink_alloy_dragon_shard_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Dragon Scale Shard-Tungsten Alloy | `mvtink_alloy_dragon_shard_tungsten` | Infernal, Void, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Dragon Scale Shard-Void Pyrite Alloy | `mvtink_alloy_dragon_shard_void_pyrite` | Void, Terrain, Swift |
| Void Titanium (`mvtink_void_titanium`) | Dragon Scale Shard-Void Titanium Alloy | `mvtink_alloy_dragon_shard_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Dragon Scale Shard-Voidstone Alloy | `mvtink_alloy_dragon_shard_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Dragon Scale Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_dragon_shard_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Dragon Scale Shard-Warped Emerald Alloy | `mvtink_alloy_dragon_shard_warped_emerald` | Infernal, Void, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Dragon Scale Shard-Warped Quartz Alloy | `mvtink_alloy_dragon_shard_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Dragon Scale Shard-Pure Weeping Shard Alloy | `mvtink_alloy_dragon_shard_weeping_shard` | Infernal, Void, Terrain |
| Witherite (`mvtink_witherite`) | Dragon Scale Shard-Witherite Alloy | `mvtink_alloy_dragon_shard_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Dragon Scale Shard-Zero-Point Shard Alloy | `mvtink_alloy_dragon_shard_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Dragon Scale Shard-Zinc Alloy | `mvtink_alloy_dragon_shard_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Dragon Scale Shard-Zircon Alloy | `mvtink_alloy_dragon_shard_zircon` | Void, Terrain, Resonant |

#### Eclipse Gem · `mvtink_eclipse_gem` · 75 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Emerald (`mvtink_emerald`) | Eclipse Gem-Emerald Alloy | `mvtink_alloy_eclipse_gem_emerald` | Void, Primal, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Eclipse Gem-End Crystal Shard Alloy | `mvtink_alloy_eclipse_gem_end_crystal_shard` | Void, Radiant, Resonant |
| Enderite (`mvtink_enderite`) | Eclipse Gem-Enderite Alloy | `mvtink_alloy_eclipse_gem_enderite` | Void, Tempered, Radiant |
| Fire Opal (`mvtink_fire_opal`) | Eclipse Gem-Fire Opal Alloy | `mvtink_alloy_eclipse_gem_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | Eclipse Gem-Flint Alloy | `mvtink_alloy_eclipse_gem_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Eclipse Gem-Fluorite Alloy | `mvtink_alloy_eclipse_gem_fluorite` | Void, Terrain, Radiant |
| Galena (`mvtink_galena`) | Eclipse Gem-Galena Alloy | `mvtink_alloy_eclipse_gem_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Eclipse Gem-Ghast Tear Shard Alloy | `mvtink_alloy_eclipse_gem_ghast_tear_shard` | Infernal, Void, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Eclipse Gem-Glowstone Gem Alloy | `mvtink_alloy_eclipse_gem_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | Eclipse Gem-Gold Alloy | `mvtink_alloy_eclipse_gem_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Eclipse Gem-Graphite Alloy | `mvtink_alloy_eclipse_gem_graphite` | Void, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Eclipse Gem-Gravitite Alloy | `mvtink_alloy_eclipse_gem_gravitite` | Void, Tempered, Radiant |
| Gypsum (`mvtink_gypsum`) | Eclipse Gem-Gypsum Alloy | `mvtink_alloy_eclipse_gem_gypsum` | Void, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Eclipse Gem-Helliron Alloy | `mvtink_alloy_eclipse_gem_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Eclipse Gem-Ignis Ferrum Alloy | `mvtink_alloy_eclipse_gem_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Eclipse Gem-Infernal Obsidian Alloy | `mvtink_alloy_eclipse_gem_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Eclipse Gem-Netherite-Infused Quartz Alloy | `mvtink_alloy_eclipse_gem_infused_quartz` | Infernal, Void, Radiant |
| Iron (`mvtink_iron`) | Eclipse Gem-Iron Alloy | `mvtink_alloy_eclipse_gem_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Eclipse Gem-Jade Alloy | `mvtink_alloy_eclipse_gem_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Eclipse Gem-Kaolinite Alloy | `mvtink_alloy_eclipse_gem_kaolinite` | Void, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Eclipse Gem-Lapis Lazuli Alloy | `mvtink_alloy_eclipse_gem_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Eclipse Gem-Lapis Matrix Alloy | `mvtink_alloy_eclipse_gem_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Eclipse Gem-Magma Brimstone Alloy | `mvtink_alloy_eclipse_gem_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Eclipse Gem-Magmacite Alloy | `mvtink_alloy_eclipse_gem_magmacite` | Infernal, Void, Radiant |
| Magnetite (`mvtink_magnetite`) | Eclipse Gem-Magnetite Alloy | `mvtink_alloy_eclipse_gem_magnetite` | Void, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Eclipse Gem-Malachite Alloy | `mvtink_alloy_eclipse_gem_malachite` | Void, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Eclipse Gem-Nebulite Alloy | `mvtink_alloy_eclipse_gem_nebulite` | Void, Radiant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Eclipse Gem-Nether Bismuth Alloy | `mvtink_alloy_eclipse_gem_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Eclipse Gem-Nether Tungsten Alloy | `mvtink_alloy_eclipse_gem_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Eclipse Gem-Netherite Scrap Shard Alloy | `mvtink_alloy_eclipse_gem_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Eclipse Gem-Nickel Alloy | `mvtink_alloy_eclipse_gem_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Eclipse Gem-Null-Shard Alloy | `mvtink_alloy_eclipse_gem_null_shard` | Void, Radiant, Volatile |
| Obsidian (`mvtink_obsidian`) | Eclipse Gem-Obsidian Alloy | `mvtink_alloy_eclipse_gem_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Eclipse Gem-Obsidianite Alloy | `mvtink_alloy_eclipse_gem_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Eclipse Gem-Opal Alloy | `mvtink_alloy_eclipse_gem_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Eclipse Gem-Ender Pearl Core Alloy | `mvtink_alloy_eclipse_gem_pearl_core` | Void, Radiant, Swift |
| Phantomite (`mvtink_phantomite`) | Eclipse Gem-Phantomite Alloy | `mvtink_alloy_eclipse_gem_phantomite` | Void, Radiant, Volatile |
| Platinum (`mvtink_platinum`) | Eclipse Gem-Platinum Alloy | `mvtink_alloy_eclipse_gem_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Eclipse Gem-Prismarine Alloy | `mvtink_alloy_eclipse_gem_prismarine` | Void, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Eclipse Gem-Pyrite Alloy | `mvtink_alloy_eclipse_gem_pyrite` | Void, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Eclipse Gem-Pyrophore Alloy | `mvtink_alloy_eclipse_gem_pyrophore` | Infernal, Void, Radiant |
| Nether Quartz (`mvtink_quartz`) | Eclipse Gem-Nether Quartz Alloy | `mvtink_alloy_eclipse_gem_quartz` | Void, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Eclipse Gem-Redstone Alloy | `mvtink_alloy_eclipse_gem_redstone` | Void, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Eclipse Gem-Resonite Alloy | `mvtink_alloy_eclipse_gem_resonite` | Void, Radiant, Resonant |
| Ruby (`mvtink_ruby`) | Eclipse Gem-Ruby Alloy | `mvtink_alloy_eclipse_gem_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Eclipse Gem-Sanguinite Alloy | `mvtink_alloy_eclipse_gem_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Eclipse Gem-Sapphire Alloy | `mvtink_alloy_eclipse_gem_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Eclipse Gem-Shadowgem Alloy | `mvtink_alloy_eclipse_gem_shadowgem` | Void, Radiant, Swift |
| Shulkerite (`mvtink_shulkerite`) | Eclipse Gem-Shulkerite Alloy | `mvtink_alloy_eclipse_gem_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Eclipse Gem-Silver Alloy | `mvtink_alloy_eclipse_gem_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Eclipse Gem-Singularite Alloy | `mvtink_alloy_eclipse_gem_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Eclipse Gem-Soul Glass Crystal Alloy | `mvtink_alloy_eclipse_gem_soulsand_crystal` | Infernal, Void, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Eclipse Gem-Spatial Platinum Alloy | `mvtink_alloy_eclipse_gem_spatial_platinum` | Void, Tempered, Radiant |
| Starlight Silver (`mvtink_starlight_silver`) | Eclipse Gem-Starlight Silver Alloy | `mvtink_alloy_eclipse_gem_starlight_silver` | Void, Tempered, Radiant |
| Steel (`mvtink_steel`) | Eclipse Gem-Steel Alloy | `mvtink_alloy_eclipse_gem_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Eclipse Gem-Stibnite Alloy | `mvtink_alloy_eclipse_gem_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Eclipse Gem-Sulfur Alloy | `mvtink_alloy_eclipse_gem_sulfur` | Infernal, Void, Radiant |
| Talc (`mvtink_talc`) | Eclipse Gem-Talc Alloy | `mvtink_alloy_eclipse_gem_talc` | Void, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Eclipse Gem-Tesseract Crystal Alloy | `mvtink_alloy_eclipse_gem_tesseract_crystal` | Void, Radiant, Resonant |
| Tin (`mvtink_tin`) | Eclipse Gem-Tin Alloy | `mvtink_alloy_eclipse_gem_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Eclipse Gem-Titanium Alloy | `mvtink_alloy_eclipse_gem_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Eclipse Gem-Topaz Alloy | `mvtink_alloy_eclipse_gem_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Eclipse Gem-Tourmaline Alloy | `mvtink_alloy_eclipse_gem_tourmaline` | Void, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Eclipse Gem-Tungsten Alloy | `mvtink_alloy_eclipse_gem_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Eclipse Gem-Void Pyrite Alloy | `mvtink_alloy_eclipse_gem_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Eclipse Gem-Void Titanium Alloy | `mvtink_alloy_eclipse_gem_void_titanium` | Void, Tempered, Radiant |
| Voidstone (`mvtink_voidstone`) | Eclipse Gem-Voidstone Alloy | `mvtink_alloy_eclipse_gem_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Eclipse Gem-Compacted Volcanic Ash Alloy | `mvtink_alloy_eclipse_gem_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Eclipse Gem-Warped Emerald Alloy | `mvtink_alloy_eclipse_gem_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Eclipse Gem-Warped Quartz Alloy | `mvtink_alloy_eclipse_gem_warped_quartz` | Void, Radiant, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Eclipse Gem-Pure Weeping Shard Alloy | `mvtink_alloy_eclipse_gem_weeping_shard` | Infernal, Void, Radiant |
| Witherite (`mvtink_witherite`) | Eclipse Gem-Witherite Alloy | `mvtink_alloy_eclipse_gem_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Eclipse Gem-Zero-Point Shard Alloy | `mvtink_alloy_eclipse_gem_zero_point` | Void, Radiant, Volatile |
| Zinc (`mvtink_zinc`) | Eclipse Gem-Zinc Alloy | `mvtink_alloy_eclipse_gem_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Eclipse Gem-Zircon Alloy | `mvtink_alloy_eclipse_gem_zircon` | Void, Terrain, Radiant |

#### End Crystal Shard · `mvtink_end_crystal_shard` · 73 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Enderite (`mvtink_enderite`) | End Crystal Shard-Enderite Alloy | `mvtink_alloy_end_crystal_shard_enderite` | Void, Tempered, Resonant |
| Fire Opal (`mvtink_fire_opal`) | End Crystal Shard-Fire Opal Alloy | `mvtink_alloy_end_crystal_shard_fire_opal` | Infernal, Void, Radiant |
| Flint (`mvtink_flint`) | End Crystal Shard-Flint Alloy | `mvtink_alloy_end_crystal_shard_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | End Crystal Shard-Fluorite Alloy | `mvtink_alloy_end_crystal_shard_fluorite` | Void, Terrain, Resonant |
| Galena (`mvtink_galena`) | End Crystal Shard-Galena Alloy | `mvtink_alloy_end_crystal_shard_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | End Crystal Shard-Ghast Tear Shard Alloy | `mvtink_alloy_end_crystal_shard_ghast_tear_shard` | Infernal, Void, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | End Crystal Shard-Glowstone Gem Alloy | `mvtink_alloy_end_crystal_shard_glowstone_gem` | Infernal, Void, Radiant |
| Gold (`mvtink_gold`) | End Crystal Shard-Gold Alloy | `mvtink_alloy_end_crystal_shard_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | End Crystal Shard-Graphite Alloy | `mvtink_alloy_end_crystal_shard_graphite` | Void, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | End Crystal Shard-Gravitite Alloy | `mvtink_alloy_end_crystal_shard_gravitite` | Void, Tempered, Resonant |
| Gypsum (`mvtink_gypsum`) | End Crystal Shard-Gypsum Alloy | `mvtink_alloy_end_crystal_shard_gypsum` | Void, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | End Crystal Shard-Helliron Alloy | `mvtink_alloy_end_crystal_shard_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | End Crystal Shard-Ignis Ferrum Alloy | `mvtink_alloy_end_crystal_shard_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | End Crystal Shard-Infernal Obsidian Alloy | `mvtink_alloy_end_crystal_shard_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | End Crystal Shard-Netherite-Infused Quartz Alloy | `mvtink_alloy_end_crystal_shard_infused_quartz` | Infernal, Void, Resonant |
| Iron (`mvtink_iron`) | End Crystal Shard-Iron Alloy | `mvtink_alloy_end_crystal_shard_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | End Crystal Shard-Jade Alloy | `mvtink_alloy_end_crystal_shard_jade` | Void, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | End Crystal Shard-Kaolinite Alloy | `mvtink_alloy_end_crystal_shard_kaolinite` | Void, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | End Crystal Shard-Lapis Lazuli Alloy | `mvtink_alloy_end_crystal_shard_lapis` | Void, Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | End Crystal Shard-Lapis Matrix Alloy | `mvtink_alloy_end_crystal_shard_lapis_matrix` | Void, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | End Crystal Shard-Magma Brimstone Alloy | `mvtink_alloy_end_crystal_shard_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | End Crystal Shard-Magmacite Alloy | `mvtink_alloy_end_crystal_shard_magmacite` | Infernal, Void, Resonant |
| Magnetite (`mvtink_magnetite`) | End Crystal Shard-Magnetite Alloy | `mvtink_alloy_end_crystal_shard_magnetite` | Void, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | End Crystal Shard-Malachite Alloy | `mvtink_alloy_end_crystal_shard_malachite` | Void, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | End Crystal Shard-Nebulite Alloy | `mvtink_alloy_end_crystal_shard_nebulite` | Void, Resonant, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | End Crystal Shard-Nether Bismuth Alloy | `mvtink_alloy_end_crystal_shard_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | End Crystal Shard-Nether Tungsten Alloy | `mvtink_alloy_end_crystal_shard_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | End Crystal Shard-Netherite Scrap Shard Alloy | `mvtink_alloy_end_crystal_shard_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | End Crystal Shard-Nickel Alloy | `mvtink_alloy_end_crystal_shard_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | End Crystal Shard-Null-Shard Alloy | `mvtink_alloy_end_crystal_shard_null_shard` | Void, Resonant, Volatile |
| Obsidian (`mvtink_obsidian`) | End Crystal Shard-Obsidian Alloy | `mvtink_alloy_end_crystal_shard_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | End Crystal Shard-Obsidianite Alloy | `mvtink_alloy_end_crystal_shard_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | End Crystal Shard-Opal Alloy | `mvtink_alloy_end_crystal_shard_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | End Crystal Shard-Ender Pearl Core Alloy | `mvtink_alloy_end_crystal_shard_pearl_core` | Void, Radiant, Resonant |
| Phantomite (`mvtink_phantomite`) | End Crystal Shard-Phantomite Alloy | `mvtink_alloy_end_crystal_shard_phantomite` | Void, Resonant, Volatile |
| Platinum (`mvtink_platinum`) | End Crystal Shard-Platinum Alloy | `mvtink_alloy_end_crystal_shard_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | End Crystal Shard-Prismarine Alloy | `mvtink_alloy_end_crystal_shard_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | End Crystal Shard-Pyrite Alloy | `mvtink_alloy_end_crystal_shard_pyrite` | Void, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | End Crystal Shard-Pyrophore Alloy | `mvtink_alloy_end_crystal_shard_pyrophore` | Infernal, Void, Resonant |
| Nether Quartz (`mvtink_quartz`) | End Crystal Shard-Nether Quartz Alloy | `mvtink_alloy_end_crystal_shard_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | End Crystal Shard-Redstone Alloy | `mvtink_alloy_end_crystal_shard_redstone` | Void, Primal, Resonant |
| Resonite (`mvtink_resonite`) | End Crystal Shard-Resonite Alloy | `mvtink_alloy_end_crystal_shard_resonite` | Void, Resonant, Swift |
| Ruby (`mvtink_ruby`) | End Crystal Shard-Ruby Alloy | `mvtink_alloy_end_crystal_shard_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | End Crystal Shard-Sanguinite Alloy | `mvtink_alloy_end_crystal_shard_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | End Crystal Shard-Sapphire Alloy | `mvtink_alloy_end_crystal_shard_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | End Crystal Shard-Shadowgem Alloy | `mvtink_alloy_end_crystal_shard_shadowgem` | Void, Radiant, Resonant |
| Shulkerite (`mvtink_shulkerite`) | End Crystal Shard-Shulkerite Alloy | `mvtink_alloy_end_crystal_shard_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | End Crystal Shard-Silver Alloy | `mvtink_alloy_end_crystal_shard_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | End Crystal Shard-Singularite Alloy | `mvtink_alloy_end_crystal_shard_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | End Crystal Shard-Soul Glass Crystal Alloy | `mvtink_alloy_end_crystal_shard_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | End Crystal Shard-Spatial Platinum Alloy | `mvtink_alloy_end_crystal_shard_spatial_platinum` | Void, Tempered, Resonant |
| Starlight Silver (`mvtink_starlight_silver`) | End Crystal Shard-Starlight Silver Alloy | `mvtink_alloy_end_crystal_shard_starlight_silver` | Void, Tempered, Resonant |
| Steel (`mvtink_steel`) | End Crystal Shard-Steel Alloy | `mvtink_alloy_end_crystal_shard_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | End Crystal Shard-Stibnite Alloy | `mvtink_alloy_end_crystal_shard_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | End Crystal Shard-Sulfur Alloy | `mvtink_alloy_end_crystal_shard_sulfur` | Infernal, Void, Resonant |
| Talc (`mvtink_talc`) | End Crystal Shard-Talc Alloy | `mvtink_alloy_end_crystal_shard_talc` | Void, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | End Crystal Shard-Tesseract Crystal Alloy | `mvtink_alloy_end_crystal_shard_tesseract_crystal` | Void, Resonant, Swift |
| Tin (`mvtink_tin`) | End Crystal Shard-Tin Alloy | `mvtink_alloy_end_crystal_shard_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | End Crystal Shard-Titanium Alloy | `mvtink_alloy_end_crystal_shard_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | End Crystal Shard-Topaz Alloy | `mvtink_alloy_end_crystal_shard_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | End Crystal Shard-Tourmaline Alloy | `mvtink_alloy_end_crystal_shard_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | End Crystal Shard-Tungsten Alloy | `mvtink_alloy_end_crystal_shard_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | End Crystal Shard-Void Pyrite Alloy | `mvtink_alloy_end_crystal_shard_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | End Crystal Shard-Void Titanium Alloy | `mvtink_alloy_end_crystal_shard_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | End Crystal Shard-Voidstone Alloy | `mvtink_alloy_end_crystal_shard_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | End Crystal Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_end_crystal_shard_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | End Crystal Shard-Warped Emerald Alloy | `mvtink_alloy_end_crystal_shard_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | End Crystal Shard-Warped Quartz Alloy | `mvtink_alloy_end_crystal_shard_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | End Crystal Shard-Pure Weeping Shard Alloy | `mvtink_alloy_end_crystal_shard_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | End Crystal Shard-Witherite Alloy | `mvtink_alloy_end_crystal_shard_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | End Crystal Shard-Zero-Point Shard Alloy | `mvtink_alloy_end_crystal_shard_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | End Crystal Shard-Zinc Alloy | `mvtink_alloy_end_crystal_shard_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | End Crystal Shard-Zircon Alloy | `mvtink_alloy_end_crystal_shard_zircon` | Void, Terrain, Resonant |

#### Enderite · `mvtink_enderite` · 72 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Fire Opal (`mvtink_fire_opal`) | Enderite-Fire Opal Alloy | `mvtink_alloy_enderite_fire_opal` | Infernal, Void, Tempered |
| Flint (`mvtink_flint`) | Enderite-Flint Alloy | `mvtink_alloy_enderite_flint` | Void, Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Enderite-Fluorite Alloy | `mvtink_alloy_enderite_fluorite` | Void, Terrain, Tempered |
| Galena (`mvtink_galena`) | Enderite-Galena Alloy | `mvtink_alloy_enderite_galena` | Void, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Enderite-Ghast Tear Shard Alloy | `mvtink_alloy_enderite_ghast_tear_shard` | Infernal, Void, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Enderite-Glowstone Gem Alloy | `mvtink_alloy_enderite_glowstone_gem` | Infernal, Void, Tempered |
| Gold (`mvtink_gold`) | Enderite-Gold Alloy | `mvtink_alloy_enderite_gold` | Void, Primal, Tempered |
| Graphite (`mvtink_graphite`) | Enderite-Graphite Alloy | `mvtink_alloy_enderite_graphite` | Void, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Enderite-Gravitite Alloy | `mvtink_alloy_enderite_gravitite` | Void, Tempered, Swift |
| Gypsum (`mvtink_gypsum`) | Enderite-Gypsum Alloy | `mvtink_alloy_enderite_gypsum` | Void, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Enderite-Helliron Alloy | `mvtink_alloy_enderite_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Enderite-Ignis Ferrum Alloy | `mvtink_alloy_enderite_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Enderite-Infernal Obsidian Alloy | `mvtink_alloy_enderite_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Enderite-Netherite-Infused Quartz Alloy | `mvtink_alloy_enderite_infused_quartz` | Infernal, Void, Tempered |
| Iron (`mvtink_iron`) | Enderite-Iron Alloy | `mvtink_alloy_enderite_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Enderite-Jade Alloy | `mvtink_alloy_enderite_jade` | Void, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Enderite-Kaolinite Alloy | `mvtink_alloy_enderite_kaolinite` | Void, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Enderite-Lapis Lazuli Alloy | `mvtink_alloy_enderite_lapis` | Void, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Enderite-Lapis Matrix Alloy | `mvtink_alloy_enderite_lapis_matrix` | Void, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Enderite-Magma Brimstone Alloy | `mvtink_alloy_enderite_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Enderite-Magmacite Alloy | `mvtink_alloy_enderite_magmacite` | Infernal, Void, Tempered |
| Magnetite (`mvtink_magnetite`) | Enderite-Magnetite Alloy | `mvtink_alloy_enderite_magnetite` | Void, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Enderite-Malachite Alloy | `mvtink_alloy_enderite_malachite` | Void, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Enderite-Nebulite Alloy | `mvtink_alloy_enderite_nebulite` | Void, Tempered, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Enderite-Nether Bismuth Alloy | `mvtink_alloy_enderite_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Enderite-Nether Tungsten Alloy | `mvtink_alloy_enderite_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Enderite-Netherite Scrap Shard Alloy | `mvtink_alloy_enderite_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Enderite-Nickel Alloy | `mvtink_alloy_enderite_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Enderite-Null-Shard Alloy | `mvtink_alloy_enderite_null_shard` | Void, Tempered, Volatile |
| Obsidian (`mvtink_obsidian`) | Enderite-Obsidian Alloy | `mvtink_alloy_enderite_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Enderite-Obsidianite Alloy | `mvtink_alloy_enderite_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Enderite-Opal Alloy | `mvtink_alloy_enderite_opal` | Void, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Enderite-Ender Pearl Core Alloy | `mvtink_alloy_enderite_pearl_core` | Void, Tempered, Radiant |
| Phantomite (`mvtink_phantomite`) | Enderite-Phantomite Alloy | `mvtink_alloy_enderite_phantomite` | Void, Tempered, Volatile |
| Platinum (`mvtink_platinum`) | Enderite-Platinum Alloy | `mvtink_alloy_enderite_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Enderite-Prismarine Alloy | `mvtink_alloy_enderite_prismarine` | Void, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Enderite-Pyrite Alloy | `mvtink_alloy_enderite_pyrite` | Void, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Enderite-Pyrophore Alloy | `mvtink_alloy_enderite_pyrophore` | Infernal, Void, Tempered |
| Nether Quartz (`mvtink_quartz`) | Enderite-Nether Quartz Alloy | `mvtink_alloy_enderite_quartz` | Void, Primal, Tempered |
| Redstone (`mvtink_redstone`) ★ | Ender Brass | `mvtink_ender_brass` | Void, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Enderite-Resonite Alloy | `mvtink_alloy_enderite_resonite` | Void, Tempered, Resonant |
| Ruby (`mvtink_ruby`) | Enderite-Ruby Alloy | `mvtink_alloy_enderite_ruby` | Void, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Enderite-Sanguinite Alloy | `mvtink_alloy_enderite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Enderite-Sapphire Alloy | `mvtink_alloy_enderite_sapphire` | Void, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Enderite-Shadowgem Alloy | `mvtink_alloy_enderite_shadowgem` | Void, Tempered, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Enderite-Shulkerite Alloy | `mvtink_alloy_enderite_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Enderite-Silver Alloy | `mvtink_alloy_enderite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Enderite-Singularite Alloy | `mvtink_alloy_enderite_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Enderite-Soul Glass Crystal Alloy | `mvtink_alloy_enderite_soulsand_crystal` | Infernal, Void, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Enderite-Spatial Platinum Alloy | `mvtink_alloy_enderite_spatial_platinum` | Void, Tempered, Swift |
| Starlight Silver (`mvtink_starlight_silver`) | Enderite-Starlight Silver Alloy | `mvtink_alloy_enderite_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Enderite-Steel Alloy | `mvtink_alloy_enderite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Enderite-Stibnite Alloy | `mvtink_alloy_enderite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Enderite-Sulfur Alloy | `mvtink_alloy_enderite_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Enderite-Talc Alloy | `mvtink_alloy_enderite_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Enderite-Tesseract Crystal Alloy | `mvtink_alloy_enderite_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Enderite-Tin Alloy | `mvtink_alloy_enderite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Enderite-Titanium Alloy | `mvtink_alloy_enderite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Enderite-Topaz Alloy | `mvtink_alloy_enderite_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Enderite-Tourmaline Alloy | `mvtink_alloy_enderite_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Enderite-Tungsten Alloy | `mvtink_alloy_enderite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Enderite-Void Pyrite Alloy | `mvtink_alloy_enderite_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Enderite-Void Titanium Alloy | `mvtink_alloy_enderite_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Enderite-Voidstone Alloy | `mvtink_alloy_enderite_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Enderite-Compacted Volcanic Ash Alloy | `mvtink_alloy_enderite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Enderite-Warped Emerald Alloy | `mvtink_alloy_enderite_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Enderite-Warped Quartz Alloy | `mvtink_alloy_enderite_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Enderite-Pure Weeping Shard Alloy | `mvtink_alloy_enderite_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Enderite-Witherite Alloy | `mvtink_alloy_enderite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Enderite-Zero-Point Shard Alloy | `mvtink_alloy_enderite_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Enderite-Zinc Alloy | `mvtink_alloy_enderite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Enderite-Zircon Alloy | `mvtink_alloy_enderite_zircon` | Void, Terrain, Tempered |

#### Gravitite · `mvtink_gravitite` · 63 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Gypsum (`mvtink_gypsum`) | Gravitite-Gypsum Alloy | `mvtink_alloy_gravitite_gypsum` | Void, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Gravitite-Helliron Alloy | `mvtink_alloy_gravitite_helliron` | Infernal, Void, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Gravitite-Ignis Ferrum Alloy | `mvtink_alloy_gravitite_ignis_ferrum` | Infernal, Void, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Gravitite-Infernal Obsidian Alloy | `mvtink_alloy_gravitite_infernal_obsidian` | Infernal, Void, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Gravitite-Netherite-Infused Quartz Alloy | `mvtink_alloy_gravitite_infused_quartz` | Infernal, Void, Tempered |
| Iron (`mvtink_iron`) | Gravitite-Iron Alloy | `mvtink_alloy_gravitite_iron` | Void, Primal, Tempered |
| Jade (`mvtink_jade`) | Gravitite-Jade Alloy | `mvtink_alloy_gravitite_jade` | Void, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Gravitite-Kaolinite Alloy | `mvtink_alloy_gravitite_kaolinite` | Void, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Gravitite-Lapis Lazuli Alloy | `mvtink_alloy_gravitite_lapis` | Void, Primal, Tempered |
| Lapis Matrix (`mvtink_lapis_matrix`) | Gravitite-Lapis Matrix Alloy | `mvtink_alloy_gravitite_lapis_matrix` | Void, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Gravitite-Magma Brimstone Alloy | `mvtink_alloy_gravitite_magma_brimstone` | Infernal, Void, Terrain |
| Magmacite (`mvtink_magmacite`) | Gravitite-Magmacite Alloy | `mvtink_alloy_gravitite_magmacite` | Infernal, Void, Tempered |
| Magnetite (`mvtink_magnetite`) | Gravitite-Magnetite Alloy | `mvtink_alloy_gravitite_magnetite` | Void, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Gravitite-Malachite Alloy | `mvtink_alloy_gravitite_malachite` | Void, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Gravitite-Nebulite Alloy | `mvtink_alloy_gravitite_nebulite` | Void, Tempered, Volatile |
| Nether Bismuth (`mvtink_nether_bismuth`) | Gravitite-Nether Bismuth Alloy | `mvtink_alloy_gravitite_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Gravitite-Nether Tungsten Alloy | `mvtink_alloy_gravitite_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Gravitite-Netherite Scrap Shard Alloy | `mvtink_alloy_gravitite_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Gravitite-Nickel Alloy | `mvtink_alloy_gravitite_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Gravitite-Null-Shard Alloy | `mvtink_alloy_gravitite_null_shard` | Void, Tempered, Volatile |
| Obsidian (`mvtink_obsidian`) | Gravitite-Obsidian Alloy | `mvtink_alloy_gravitite_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Gravitite-Obsidianite Alloy | `mvtink_alloy_gravitite_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Gravitite-Opal Alloy | `mvtink_alloy_gravitite_opal` | Void, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Gravitite-Ender Pearl Core Alloy | `mvtink_alloy_gravitite_pearl_core` | Void, Tempered, Radiant |
| Phantomite (`mvtink_phantomite`) | Gravitite-Phantomite Alloy | `mvtink_alloy_gravitite_phantomite` | Void, Tempered, Volatile |
| Platinum (`mvtink_platinum`) | Gravitite-Platinum Alloy | `mvtink_alloy_gravitite_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Gravitite-Prismarine Alloy | `mvtink_alloy_gravitite_prismarine` | Void, Primal, Tempered |
| Pyrite (`mvtink_pyrite`) | Gravitite-Pyrite Alloy | `mvtink_alloy_gravitite_pyrite` | Void, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Gravitite-Pyrophore Alloy | `mvtink_alloy_gravitite_pyrophore` | Infernal, Void, Tempered |
| Nether Quartz (`mvtink_quartz`) | Gravitite-Nether Quartz Alloy | `mvtink_alloy_gravitite_quartz` | Void, Primal, Tempered |
| Redstone (`mvtink_redstone`) | Gravitite-Redstone Alloy | `mvtink_alloy_gravitite_redstone` | Void, Primal, Tempered |
| Resonite (`mvtink_resonite`) | Gravitite-Resonite Alloy | `mvtink_alloy_gravitite_resonite` | Void, Tempered, Resonant |
| Ruby (`mvtink_ruby`) | Gravitite-Ruby Alloy | `mvtink_alloy_gravitite_ruby` | Void, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Gravitite-Sanguinite Alloy | `mvtink_alloy_gravitite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Gravitite-Sapphire Alloy | `mvtink_alloy_gravitite_sapphire` | Void, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Gravitite-Shadowgem Alloy | `mvtink_alloy_gravitite_shadowgem` | Void, Tempered, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Gravitite-Shulkerite Alloy | `mvtink_alloy_gravitite_shulkerite` | Void, Terrain, Tempered |
| Silver (`mvtink_silver`) | Gravitite-Silver Alloy | `mvtink_alloy_gravitite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Gravitite-Singularite Alloy | `mvtink_alloy_gravitite_singularite` | Void, Terrain, Tempered |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Gravitite-Soul Glass Crystal Alloy | `mvtink_alloy_gravitite_soulsand_crystal` | Infernal, Void, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Gravitite-Spatial Platinum Alloy | `mvtink_alloy_gravitite_spatial_platinum` | Void, Tempered, Swift |
| Starlight Silver (`mvtink_starlight_silver`) | Gravitite-Starlight Silver Alloy | `mvtink_alloy_gravitite_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Gravitite-Steel Alloy | `mvtink_alloy_gravitite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Gravitite-Stibnite Alloy | `mvtink_alloy_gravitite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Gravitite-Sulfur Alloy | `mvtink_alloy_gravitite_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Gravitite-Talc Alloy | `mvtink_alloy_gravitite_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Gravitite-Tesseract Crystal Alloy | `mvtink_alloy_gravitite_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Gravitite-Tin Alloy | `mvtink_alloy_gravitite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Gravitite-Titanium Alloy | `mvtink_alloy_gravitite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Gravitite-Topaz Alloy | `mvtink_alloy_gravitite_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Gravitite-Tourmaline Alloy | `mvtink_alloy_gravitite_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Gravitite-Tungsten Alloy | `mvtink_alloy_gravitite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Gravitite-Void Pyrite Alloy | `mvtink_alloy_gravitite_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Gravitite-Void Titanium Alloy | `mvtink_alloy_gravitite_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Gravitite-Voidstone Alloy | `mvtink_alloy_gravitite_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Gravitite-Compacted Volcanic Ash Alloy | `mvtink_alloy_gravitite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Gravitite-Warped Emerald Alloy | `mvtink_alloy_gravitite_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Gravitite-Warped Quartz Alloy | `mvtink_alloy_gravitite_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Gravitite-Pure Weeping Shard Alloy | `mvtink_alloy_gravitite_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Gravitite-Witherite Alloy | `mvtink_alloy_gravitite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Gravitite-Zero-Point Shard Alloy | `mvtink_alloy_gravitite_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Gravitite-Zinc Alloy | `mvtink_alloy_gravitite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Gravitite-Zircon Alloy | `mvtink_alloy_gravitite_zircon` | Void, Terrain, Tempered |

#### Nebulite · `mvtink_nebulite` · 48 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Nether Bismuth (`mvtink_nether_bismuth`) | Nebulite-Nether Bismuth Alloy | `mvtink_alloy_nebulite_nether_bismuth` | Infernal, Void, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Nebulite-Nether Tungsten Alloy | `mvtink_alloy_nebulite_nether_tungsten` | Infernal, Void, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Nebulite-Netherite Scrap Shard Alloy | `mvtink_alloy_nebulite_netherite_shard` | Infernal, Void, Tempered |
| Nickel (`mvtink_nickel`) | Nebulite-Nickel Alloy | `mvtink_alloy_nebulite_nickel` | Void, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Nebulite-Null-Shard Alloy | `mvtink_alloy_nebulite_null_shard` | Void, Volatile, Swift |
| Obsidian (`mvtink_obsidian`) | Nebulite-Obsidian Alloy | `mvtink_alloy_nebulite_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Nebulite-Obsidianite Alloy | `mvtink_alloy_nebulite_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Nebulite-Opal Alloy | `mvtink_alloy_nebulite_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Nebulite-Ender Pearl Core Alloy | `mvtink_alloy_nebulite_pearl_core` | Void, Radiant, Volatile |
| Phantomite (`mvtink_phantomite`) | Nebulite-Phantomite Alloy | `mvtink_alloy_nebulite_phantomite` | Void, Volatile, Swift |
| Platinum (`mvtink_platinum`) | Nebulite-Platinum Alloy | `mvtink_alloy_nebulite_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Nebulite-Prismarine Alloy | `mvtink_alloy_nebulite_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Nebulite-Pyrite Alloy | `mvtink_alloy_nebulite_pyrite` | Void, Terrain, Volatile |
| Pyrophore (`mvtink_pyrophore`) | Nebulite-Pyrophore Alloy | `mvtink_alloy_nebulite_pyrophore` | Infernal, Void, Volatile |
| Nether Quartz (`mvtink_quartz`) | Nebulite-Nether Quartz Alloy | `mvtink_alloy_nebulite_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Nebulite-Redstone Alloy | `mvtink_alloy_nebulite_redstone` | Void, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Nebulite-Resonite Alloy | `mvtink_alloy_nebulite_resonite` | Void, Resonant, Volatile |
| Ruby (`mvtink_ruby`) | Nebulite-Ruby Alloy | `mvtink_alloy_nebulite_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Nebulite-Sanguinite Alloy | `mvtink_alloy_nebulite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Nebulite-Sapphire Alloy | `mvtink_alloy_nebulite_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Nebulite-Shadowgem Alloy | `mvtink_alloy_nebulite_shadowgem` | Void, Radiant, Volatile |
| Shulkerite (`mvtink_shulkerite`) | Nebulite-Shulkerite Alloy | `mvtink_alloy_nebulite_shulkerite` | Void, Terrain, Volatile |
| Silver (`mvtink_silver`) | Nebulite-Silver Alloy | `mvtink_alloy_nebulite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Nebulite-Singularite Alloy | `mvtink_alloy_nebulite_singularite` | Void, Terrain, Volatile |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Nebulite-Soul Glass Crystal Alloy | `mvtink_alloy_nebulite_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Nebulite-Spatial Platinum Alloy | `mvtink_alloy_nebulite_spatial_platinum` | Void, Tempered, Volatile |
| Starlight Silver (`mvtink_starlight_silver`) | Nebulite-Starlight Silver Alloy | `mvtink_alloy_nebulite_starlight_silver` | Void, Tempered, Volatile |
| Steel (`mvtink_steel`) | Nebulite-Steel Alloy | `mvtink_alloy_nebulite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Nebulite-Stibnite Alloy | `mvtink_alloy_nebulite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Nebulite-Sulfur Alloy | `mvtink_alloy_nebulite_sulfur` | Infernal, Void, Volatile |
| Talc (`mvtink_talc`) | Nebulite-Talc Alloy | `mvtink_alloy_nebulite_talc` | Void, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Nebulite-Tesseract Crystal Alloy | `mvtink_alloy_nebulite_tesseract_crystal` | Void, Resonant, Volatile |
| Tin (`mvtink_tin`) | Nebulite-Tin Alloy | `mvtink_alloy_nebulite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Nebulite-Titanium Alloy | `mvtink_alloy_nebulite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Nebulite-Topaz Alloy | `mvtink_alloy_nebulite_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Nebulite-Tourmaline Alloy | `mvtink_alloy_nebulite_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Nebulite-Tungsten Alloy | `mvtink_alloy_nebulite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Nebulite-Void Pyrite Alloy | `mvtink_alloy_nebulite_void_pyrite` | Void, Terrain, Volatile |
| Void Titanium (`mvtink_void_titanium`) | Nebulite-Void Titanium Alloy | `mvtink_alloy_nebulite_void_titanium` | Void, Tempered, Volatile |
| Voidstone (`mvtink_voidstone`) | Nebulite-Voidstone Alloy | `mvtink_alloy_nebulite_voidstone` | Void, Terrain, Volatile |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Nebulite-Compacted Volcanic Ash Alloy | `mvtink_alloy_nebulite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Nebulite-Warped Emerald Alloy | `mvtink_alloy_nebulite_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Nebulite-Warped Quartz Alloy | `mvtink_alloy_nebulite_warped_quartz` | Void, Resonant, Volatile |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Nebulite-Pure Weeping Shard Alloy | `mvtink_alloy_nebulite_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Nebulite-Witherite Alloy | `mvtink_alloy_nebulite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Nebulite-Zero-Point Shard Alloy | `mvtink_alloy_nebulite_zero_point` | Void, Volatile, Swift |
| Zinc (`mvtink_zinc`) | Nebulite-Zinc Alloy | `mvtink_alloy_nebulite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Nebulite-Zircon Alloy | `mvtink_alloy_nebulite_zircon` | Void, Terrain, Resonant |

#### Null-Shard · `mvtink_null_shard` · 43 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Obsidian (`mvtink_obsidian`) | Null-Shard-Obsidian Alloy | `mvtink_alloy_null_shard_obsidian` | Void, Primal, Terrain |
| Obsidianite (`mvtink_obsidianite`) | Null-Shard-Obsidianite Alloy | `mvtink_alloy_null_shard_obsidianite` | Infernal, Void, Terrain |
| Opal (`mvtink_opal`) | Null-Shard-Opal Alloy | `mvtink_alloy_null_shard_opal` | Void, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Null-Shard-Ender Pearl Core Alloy | `mvtink_alloy_null_shard_pearl_core` | Void, Radiant, Volatile |
| Phantomite (`mvtink_phantomite`) | Null-Shard-Phantomite Alloy | `mvtink_alloy_null_shard_phantomite` | Void, Volatile, Swift |
| Platinum (`mvtink_platinum`) | Null-Shard-Platinum Alloy | `mvtink_alloy_null_shard_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Null-Shard-Prismarine Alloy | `mvtink_alloy_null_shard_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Null-Shard-Pyrite Alloy | `mvtink_alloy_null_shard_pyrite` | Void, Terrain, Volatile |
| Pyrophore (`mvtink_pyrophore`) | Null-Shard-Pyrophore Alloy | `mvtink_alloy_null_shard_pyrophore` | Infernal, Void, Volatile |
| Nether Quartz (`mvtink_quartz`) | Null-Shard-Nether Quartz Alloy | `mvtink_alloy_null_shard_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Null-Shard-Redstone Alloy | `mvtink_alloy_null_shard_redstone` | Void, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Null-Shard-Resonite Alloy | `mvtink_alloy_null_shard_resonite` | Void, Resonant, Volatile |
| Ruby (`mvtink_ruby`) | Null-Shard-Ruby Alloy | `mvtink_alloy_null_shard_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Null-Shard-Sanguinite Alloy | `mvtink_alloy_null_shard_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Null-Shard-Sapphire Alloy | `mvtink_alloy_null_shard_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Null-Shard-Shadowgem Alloy | `mvtink_alloy_null_shard_shadowgem` | Void, Radiant, Volatile |
| Shulkerite (`mvtink_shulkerite`) | Null-Shard-Shulkerite Alloy | `mvtink_alloy_null_shard_shulkerite` | Void, Terrain, Volatile |
| Silver (`mvtink_silver`) | Null-Shard-Silver Alloy | `mvtink_alloy_null_shard_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Null-Shard-Singularite Alloy | `mvtink_alloy_null_shard_singularite` | Void, Terrain, Volatile |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Null-Shard-Soul Glass Crystal Alloy | `mvtink_alloy_null_shard_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Null-Shard-Spatial Platinum Alloy | `mvtink_alloy_null_shard_spatial_platinum` | Void, Tempered, Volatile |
| Starlight Silver (`mvtink_starlight_silver`) | Null-Shard-Starlight Silver Alloy | `mvtink_alloy_null_shard_starlight_silver` | Void, Tempered, Volatile |
| Steel (`mvtink_steel`) | Null-Shard-Steel Alloy | `mvtink_alloy_null_shard_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Null-Shard-Stibnite Alloy | `mvtink_alloy_null_shard_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Null-Shard-Sulfur Alloy | `mvtink_alloy_null_shard_sulfur` | Infernal, Void, Volatile |
| Talc (`mvtink_talc`) | Null-Shard-Talc Alloy | `mvtink_alloy_null_shard_talc` | Void, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Null-Shard-Tesseract Crystal Alloy | `mvtink_alloy_null_shard_tesseract_crystal` | Void, Resonant, Volatile |
| Tin (`mvtink_tin`) | Null-Shard-Tin Alloy | `mvtink_alloy_null_shard_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Null-Shard-Titanium Alloy | `mvtink_alloy_null_shard_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Null-Shard-Topaz Alloy | `mvtink_alloy_null_shard_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Null-Shard-Tourmaline Alloy | `mvtink_alloy_null_shard_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Null-Shard-Tungsten Alloy | `mvtink_alloy_null_shard_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Null-Shard-Void Pyrite Alloy | `mvtink_alloy_null_shard_void_pyrite` | Void, Terrain, Volatile |
| Void Titanium (`mvtink_void_titanium`) | Null-Shard-Void Titanium Alloy | `mvtink_alloy_null_shard_void_titanium` | Void, Tempered, Volatile |
| Voidstone (`mvtink_voidstone`) | Null-Shard-Voidstone Alloy | `mvtink_alloy_null_shard_voidstone` | Void, Terrain, Volatile |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Null-Shard-Compacted Volcanic Ash Alloy | `mvtink_alloy_null_shard_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Null-Shard-Warped Emerald Alloy | `mvtink_alloy_null_shard_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Null-Shard-Warped Quartz Alloy | `mvtink_alloy_null_shard_warped_quartz` | Void, Resonant, Volatile |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Null-Shard-Pure Weeping Shard Alloy | `mvtink_alloy_null_shard_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Null-Shard-Witherite Alloy | `mvtink_alloy_null_shard_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Null-Shard-Zero-Point Shard Alloy | `mvtink_alloy_null_shard_zero_point` | Void, Volatile, Swift |
| Zinc (`mvtink_zinc`) | Null-Shard-Zinc Alloy | `mvtink_alloy_null_shard_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Null-Shard-Zircon Alloy | `mvtink_alloy_null_shard_zircon` | Void, Terrain, Resonant |

#### Ender Pearl Core · `mvtink_pearl_core` · 39 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Phantomite (`mvtink_phantomite`) | Ender Pearl Core-Phantomite Alloy | `mvtink_alloy_pearl_core_phantomite` | Void, Radiant, Volatile |
| Platinum (`mvtink_platinum`) | Ender Pearl Core-Platinum Alloy | `mvtink_alloy_pearl_core_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Ender Pearl Core-Prismarine Alloy | `mvtink_alloy_pearl_core_prismarine` | Void, Primal, Radiant |
| Pyrite (`mvtink_pyrite`) | Ender Pearl Core-Pyrite Alloy | `mvtink_alloy_pearl_core_pyrite` | Void, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Ender Pearl Core-Pyrophore Alloy | `mvtink_alloy_pearl_core_pyrophore` | Infernal, Void, Radiant |
| Nether Quartz (`mvtink_quartz`) | Ender Pearl Core-Nether Quartz Alloy | `mvtink_alloy_pearl_core_quartz` | Void, Primal, Radiant |
| Redstone (`mvtink_redstone`) | Ender Pearl Core-Redstone Alloy | `mvtink_alloy_pearl_core_redstone` | Void, Primal, Radiant |
| Resonite (`mvtink_resonite`) | Ender Pearl Core-Resonite Alloy | `mvtink_alloy_pearl_core_resonite` | Void, Radiant, Resonant |
| Ruby (`mvtink_ruby`) | Ender Pearl Core-Ruby Alloy | `mvtink_alloy_pearl_core_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Ender Pearl Core-Sanguinite Alloy | `mvtink_alloy_pearl_core_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Ender Pearl Core-Sapphire Alloy | `mvtink_alloy_pearl_core_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Ender Pearl Core-Shadowgem Alloy | `mvtink_alloy_pearl_core_shadowgem` | Void, Radiant, Swift |
| Shulkerite (`mvtink_shulkerite`) | Ender Pearl Core-Shulkerite Alloy | `mvtink_alloy_pearl_core_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Ender Pearl Core-Silver Alloy | `mvtink_alloy_pearl_core_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Ender Pearl Core-Singularite Alloy | `mvtink_alloy_pearl_core_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Ender Pearl Core-Soul Glass Crystal Alloy | `mvtink_alloy_pearl_core_soulsand_crystal` | Infernal, Void, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Ender Pearl Core-Spatial Platinum Alloy | `mvtink_alloy_pearl_core_spatial_platinum` | Void, Tempered, Radiant |
| Starlight Silver (`mvtink_starlight_silver`) | Ender Pearl Core-Starlight Silver Alloy | `mvtink_alloy_pearl_core_starlight_silver` | Void, Tempered, Radiant |
| Steel (`mvtink_steel`) | Ender Pearl Core-Steel Alloy | `mvtink_alloy_pearl_core_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Ender Pearl Core-Stibnite Alloy | `mvtink_alloy_pearl_core_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Ender Pearl Core-Sulfur Alloy | `mvtink_alloy_pearl_core_sulfur` | Infernal, Void, Radiant |
| Talc (`mvtink_talc`) | Ender Pearl Core-Talc Alloy | `mvtink_alloy_pearl_core_talc` | Void, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Ender Pearl Core-Tesseract Crystal Alloy | `mvtink_alloy_pearl_core_tesseract_crystal` | Void, Radiant, Resonant |
| Tin (`mvtink_tin`) | Ender Pearl Core-Tin Alloy | `mvtink_alloy_pearl_core_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Ender Pearl Core-Titanium Alloy | `mvtink_alloy_pearl_core_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Ender Pearl Core-Topaz Alloy | `mvtink_alloy_pearl_core_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Ender Pearl Core-Tourmaline Alloy | `mvtink_alloy_pearl_core_tourmaline` | Void, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Ender Pearl Core-Tungsten Alloy | `mvtink_alloy_pearl_core_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Ender Pearl Core-Void Pyrite Alloy | `mvtink_alloy_pearl_core_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Ender Pearl Core-Void Titanium Alloy | `mvtink_alloy_pearl_core_void_titanium` | Void, Tempered, Radiant |
| Voidstone (`mvtink_voidstone`) | Ender Pearl Core-Voidstone Alloy | `mvtink_alloy_pearl_core_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Ender Pearl Core-Compacted Volcanic Ash Alloy | `mvtink_alloy_pearl_core_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Ender Pearl Core-Warped Emerald Alloy | `mvtink_alloy_pearl_core_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Ender Pearl Core-Warped Quartz Alloy | `mvtink_alloy_pearl_core_warped_quartz` | Void, Radiant, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Ender Pearl Core-Pure Weeping Shard Alloy | `mvtink_alloy_pearl_core_weeping_shard` | Infernal, Void, Radiant |
| Witherite (`mvtink_witherite`) | Ender Pearl Core-Witherite Alloy | `mvtink_alloy_pearl_core_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Ender Pearl Core-Zero-Point Shard Alloy | `mvtink_alloy_pearl_core_zero_point` | Void, Radiant, Volatile |
| Zinc (`mvtink_zinc`) | Ender Pearl Core-Zinc Alloy | `mvtink_alloy_pearl_core_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Ender Pearl Core-Zircon Alloy | `mvtink_alloy_pearl_core_zircon` | Void, Terrain, Radiant |

#### Phantomite · `mvtink_phantomite` · 38 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Platinum (`mvtink_platinum`) | Phantomite-Platinum Alloy | `mvtink_alloy_phantomite_platinum` | Void, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Phantomite-Prismarine Alloy | `mvtink_alloy_phantomite_prismarine` | Void, Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Phantomite-Pyrite Alloy | `mvtink_alloy_phantomite_pyrite` | Void, Terrain, Volatile |
| Pyrophore (`mvtink_pyrophore`) | Phantomite-Pyrophore Alloy | `mvtink_alloy_phantomite_pyrophore` | Infernal, Void, Volatile |
| Nether Quartz (`mvtink_quartz`) | Phantomite-Nether Quartz Alloy | `mvtink_alloy_phantomite_quartz` | Void, Primal, Resonant |
| Redstone (`mvtink_redstone`) | Phantomite-Redstone Alloy | `mvtink_alloy_phantomite_redstone` | Void, Primal, Volatile |
| Resonite (`mvtink_resonite`) | Phantomite-Resonite Alloy | `mvtink_alloy_phantomite_resonite` | Void, Resonant, Volatile |
| Ruby (`mvtink_ruby`) | Phantomite-Ruby Alloy | `mvtink_alloy_phantomite_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Phantomite-Sanguinite Alloy | `mvtink_alloy_phantomite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Phantomite-Sapphire Alloy | `mvtink_alloy_phantomite_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Phantomite-Shadowgem Alloy | `mvtink_alloy_phantomite_shadowgem` | Void, Radiant, Volatile |
| Shulkerite (`mvtink_shulkerite`) | Phantomite-Shulkerite Alloy | `mvtink_alloy_phantomite_shulkerite` | Void, Terrain, Volatile |
| Silver (`mvtink_silver`) | Phantomite-Silver Alloy | `mvtink_alloy_phantomite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Phantomite-Singularite Alloy | `mvtink_alloy_phantomite_singularite` | Void, Terrain, Volatile |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Phantomite-Soul Glass Crystal Alloy | `mvtink_alloy_phantomite_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Phantomite-Spatial Platinum Alloy | `mvtink_alloy_phantomite_spatial_platinum` | Void, Tempered, Volatile |
| Starlight Silver (`mvtink_starlight_silver`) | Phantomite-Starlight Silver Alloy | `mvtink_alloy_phantomite_starlight_silver` | Void, Tempered, Volatile |
| Steel (`mvtink_steel`) | Phantomite-Steel Alloy | `mvtink_alloy_phantomite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Phantomite-Stibnite Alloy | `mvtink_alloy_phantomite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Phantomite-Sulfur Alloy | `mvtink_alloy_phantomite_sulfur` | Infernal, Void, Volatile |
| Talc (`mvtink_talc`) | Phantomite-Talc Alloy | `mvtink_alloy_phantomite_talc` | Void, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Phantomite-Tesseract Crystal Alloy | `mvtink_alloy_phantomite_tesseract_crystal` | Void, Resonant, Volatile |
| Tin (`mvtink_tin`) | Phantomite-Tin Alloy | `mvtink_alloy_phantomite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Phantomite-Titanium Alloy | `mvtink_alloy_phantomite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Phantomite-Topaz Alloy | `mvtink_alloy_phantomite_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Phantomite-Tourmaline Alloy | `mvtink_alloy_phantomite_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Phantomite-Tungsten Alloy | `mvtink_alloy_phantomite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Phantomite-Void Pyrite Alloy | `mvtink_alloy_phantomite_void_pyrite` | Void, Terrain, Volatile |
| Void Titanium (`mvtink_void_titanium`) | Phantomite-Void Titanium Alloy | `mvtink_alloy_phantomite_void_titanium` | Void, Tempered, Volatile |
| Voidstone (`mvtink_voidstone`) | Phantomite-Voidstone Alloy | `mvtink_alloy_phantomite_voidstone` | Void, Terrain, Volatile |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Phantomite-Compacted Volcanic Ash Alloy | `mvtink_alloy_phantomite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Phantomite-Warped Emerald Alloy | `mvtink_alloy_phantomite_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Phantomite-Warped Quartz Alloy | `mvtink_alloy_phantomite_warped_quartz` | Void, Resonant, Volatile |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Phantomite-Pure Weeping Shard Alloy | `mvtink_alloy_phantomite_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Phantomite-Witherite Alloy | `mvtink_alloy_phantomite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Phantomite-Zero-Point Shard Alloy | `mvtink_alloy_phantomite_zero_point` | Void, Volatile, Swift |
| Zinc (`mvtink_zinc`) | Phantomite-Zinc Alloy | `mvtink_alloy_phantomite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Phantomite-Zircon Alloy | `mvtink_alloy_phantomite_zircon` | Void, Terrain, Resonant |

#### Resonite · `mvtink_resonite` · 31 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Ruby (`mvtink_ruby`) | Resonite-Ruby Alloy | `mvtink_alloy_resonite_ruby` | Void, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Resonite-Sanguinite Alloy | `mvtink_alloy_resonite_sanguinite` | Infernal, Void, Tempered |
| Sapphire (`mvtink_sapphire`) | Resonite-Sapphire Alloy | `mvtink_alloy_resonite_sapphire` | Void, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Resonite-Shadowgem Alloy | `mvtink_alloy_resonite_shadowgem` | Void, Radiant, Resonant |
| Shulkerite (`mvtink_shulkerite`) | Resonite-Shulkerite Alloy | `mvtink_alloy_resonite_shulkerite` | Void, Terrain, Resonant |
| Silver (`mvtink_silver`) | Resonite-Silver Alloy | `mvtink_alloy_resonite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Resonite-Singularite Alloy | `mvtink_alloy_resonite_singularite` | Void, Terrain, Resonant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Resonite-Soul Glass Crystal Alloy | `mvtink_alloy_resonite_soulsand_crystal` | Infernal, Void, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Resonite-Spatial Platinum Alloy | `mvtink_alloy_resonite_spatial_platinum` | Void, Tempered, Resonant |
| Starlight Silver (`mvtink_starlight_silver`) | Resonite-Starlight Silver Alloy | `mvtink_alloy_resonite_starlight_silver` | Void, Tempered, Resonant |
| Steel (`mvtink_steel`) | Resonite-Steel Alloy | `mvtink_alloy_resonite_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Resonite-Stibnite Alloy | `mvtink_alloy_resonite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Resonite-Sulfur Alloy | `mvtink_alloy_resonite_sulfur` | Infernal, Void, Resonant |
| Talc (`mvtink_talc`) | Resonite-Talc Alloy | `mvtink_alloy_resonite_talc` | Void, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Resonite-Tesseract Crystal Alloy | `mvtink_alloy_resonite_tesseract_crystal` | Void, Resonant, Swift |
| Tin (`mvtink_tin`) | Resonite-Tin Alloy | `mvtink_alloy_resonite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Resonite-Titanium Alloy | `mvtink_alloy_resonite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Resonite-Topaz Alloy | `mvtink_alloy_resonite_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Resonite-Tourmaline Alloy | `mvtink_alloy_resonite_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Resonite-Tungsten Alloy | `mvtink_alloy_resonite_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Resonite-Void Pyrite Alloy | `mvtink_alloy_resonite_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Resonite-Void Titanium Alloy | `mvtink_alloy_resonite_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | Resonite-Voidstone Alloy | `mvtink_alloy_resonite_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Resonite-Compacted Volcanic Ash Alloy | `mvtink_alloy_resonite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Resonite-Warped Emerald Alloy | `mvtink_alloy_resonite_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Resonite-Warped Quartz Alloy | `mvtink_alloy_resonite_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Resonite-Pure Weeping Shard Alloy | `mvtink_alloy_resonite_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Resonite-Witherite Alloy | `mvtink_alloy_resonite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Resonite-Zero-Point Shard Alloy | `mvtink_alloy_resonite_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Resonite-Zinc Alloy | `mvtink_alloy_resonite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Resonite-Zircon Alloy | `mvtink_alloy_resonite_zircon` | Void, Terrain, Resonant |

#### Shadowgem · `mvtink_shadowgem` · 27 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Shulkerite (`mvtink_shulkerite`) | Shadowgem-Shulkerite Alloy | `mvtink_alloy_shadowgem_shulkerite` | Void, Terrain, Radiant |
| Silver (`mvtink_silver`) | Shadowgem-Silver Alloy | `mvtink_alloy_shadowgem_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Shadowgem-Singularite Alloy | `mvtink_alloy_shadowgem_singularite` | Void, Terrain, Radiant |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Shadowgem-Soul Glass Crystal Alloy | `mvtink_alloy_shadowgem_soulsand_crystal` | Infernal, Void, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Shadowgem-Spatial Platinum Alloy | `mvtink_alloy_shadowgem_spatial_platinum` | Void, Tempered, Radiant |
| Starlight Silver (`mvtink_starlight_silver`) | Shadowgem-Starlight Silver Alloy | `mvtink_alloy_shadowgem_starlight_silver` | Void, Tempered, Radiant |
| Steel (`mvtink_steel`) | Shadowgem-Steel Alloy | `mvtink_alloy_shadowgem_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Shadowgem-Stibnite Alloy | `mvtink_alloy_shadowgem_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Shadowgem-Sulfur Alloy | `mvtink_alloy_shadowgem_sulfur` | Infernal, Void, Radiant |
| Talc (`mvtink_talc`) | Shadowgem-Talc Alloy | `mvtink_alloy_shadowgem_talc` | Void, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Shadowgem-Tesseract Crystal Alloy | `mvtink_alloy_shadowgem_tesseract_crystal` | Void, Radiant, Resonant |
| Tin (`mvtink_tin`) | Shadowgem-Tin Alloy | `mvtink_alloy_shadowgem_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Shadowgem-Titanium Alloy | `mvtink_alloy_shadowgem_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Shadowgem-Topaz Alloy | `mvtink_alloy_shadowgem_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Shadowgem-Tourmaline Alloy | `mvtink_alloy_shadowgem_tourmaline` | Void, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Shadowgem-Tungsten Alloy | `mvtink_alloy_shadowgem_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Shadowgem-Void Pyrite Alloy | `mvtink_alloy_shadowgem_void_pyrite` | Void, Terrain, Radiant |
| Void Titanium (`mvtink_void_titanium`) | Shadowgem-Void Titanium Alloy | `mvtink_alloy_shadowgem_void_titanium` | Void, Tempered, Radiant |
| Voidstone (`mvtink_voidstone`) | Shadowgem-Voidstone Alloy | `mvtink_alloy_shadowgem_voidstone` | Void, Terrain, Radiant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Shadowgem-Compacted Volcanic Ash Alloy | `mvtink_alloy_shadowgem_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Shadowgem-Warped Emerald Alloy | `mvtink_alloy_shadowgem_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Shadowgem-Warped Quartz Alloy | `mvtink_alloy_shadowgem_warped_quartz` | Void, Radiant, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Shadowgem-Pure Weeping Shard Alloy | `mvtink_alloy_shadowgem_weeping_shard` | Infernal, Void, Radiant |
| Witherite (`mvtink_witherite`) | Shadowgem-Witherite Alloy | `mvtink_alloy_shadowgem_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Shadowgem-Zero-Point Shard Alloy | `mvtink_alloy_shadowgem_zero_point` | Void, Radiant, Volatile |
| Zinc (`mvtink_zinc`) | Shadowgem-Zinc Alloy | `mvtink_alloy_shadowgem_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Shadowgem-Zircon Alloy | `mvtink_alloy_shadowgem_zircon` | Void, Terrain, Radiant |

#### Shulkerite · `mvtink_shulkerite` · 26 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Silver (`mvtink_silver`) | Shulkerite-Silver Alloy | `mvtink_alloy_shulkerite_silver` | Void, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Shulkerite-Singularite Alloy | `mvtink_alloy_shulkerite_singularite` | Void, Terrain, Swift |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Shulkerite-Soul Glass Crystal Alloy | `mvtink_alloy_shulkerite_soulsand_crystal` | Infernal, Void, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Shulkerite-Spatial Platinum Alloy | `mvtink_alloy_shulkerite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Shulkerite-Starlight Silver Alloy | `mvtink_alloy_shulkerite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Shulkerite-Steel Alloy | `mvtink_alloy_shulkerite_steel` | Infernal, Void, Terrain |
| Stibnite (`mvtink_stibnite`) | Shulkerite-Stibnite Alloy | `mvtink_alloy_shulkerite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Shulkerite-Sulfur Alloy | `mvtink_alloy_shulkerite_sulfur` | Infernal, Void, Terrain |
| Talc (`mvtink_talc`) | Shulkerite-Talc Alloy | `mvtink_alloy_shulkerite_talc` | Void, Terrain, Bulwark |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Shulkerite-Tesseract Crystal Alloy | `mvtink_alloy_shulkerite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Shulkerite-Tin Alloy | `mvtink_alloy_shulkerite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Shulkerite-Titanium Alloy | `mvtink_alloy_shulkerite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Shulkerite-Topaz Alloy | `mvtink_alloy_shulkerite_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Shulkerite-Tourmaline Alloy | `mvtink_alloy_shulkerite_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Shulkerite-Tungsten Alloy | `mvtink_alloy_shulkerite_tungsten` | Infernal, Void, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Shulkerite-Void Pyrite Alloy | `mvtink_alloy_shulkerite_void_pyrite` | Void, Terrain, Bulwark |
| Void Titanium (`mvtink_void_titanium`) | Shulkerite-Void Titanium Alloy | `mvtink_alloy_shulkerite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Shulkerite-Voidstone Alloy | `mvtink_alloy_shulkerite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Shulkerite-Compacted Volcanic Ash Alloy | `mvtink_alloy_shulkerite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Shulkerite-Warped Emerald Alloy | `mvtink_alloy_shulkerite_warped_emerald` | Infernal, Void, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Shulkerite-Warped Quartz Alloy | `mvtink_alloy_shulkerite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Shulkerite-Pure Weeping Shard Alloy | `mvtink_alloy_shulkerite_weeping_shard` | Infernal, Void, Terrain |
| Witherite (`mvtink_witherite`) | Shulkerite-Witherite Alloy | `mvtink_alloy_shulkerite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Shulkerite-Zero-Point Shard Alloy | `mvtink_alloy_shulkerite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Shulkerite-Zinc Alloy | `mvtink_alloy_shulkerite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Shulkerite-Zircon Alloy | `mvtink_alloy_shulkerite_zircon` | Void, Terrain, Resonant |

#### Singularite · `mvtink_singularite` · 24 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Singularite-Soul Glass Crystal Alloy | `mvtink_alloy_singularite_soulsand_crystal` | Infernal, Void, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Singularite-Spatial Platinum Alloy | `mvtink_alloy_singularite_spatial_platinum` | Void, Terrain, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Singularite-Starlight Silver Alloy | `mvtink_alloy_singularite_starlight_silver` | Void, Terrain, Tempered |
| Steel (`mvtink_steel`) | Singularite-Steel Alloy | `mvtink_alloy_singularite_steel` | Infernal, Void, Terrain |
| Stibnite (`mvtink_stibnite`) | Singularite-Stibnite Alloy | `mvtink_alloy_singularite_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Singularite-Sulfur Alloy | `mvtink_alloy_singularite_sulfur` | Infernal, Void, Terrain |
| Talc (`mvtink_talc`) | Singularite-Talc Alloy | `mvtink_alloy_singularite_talc` | Void, Terrain, Swift |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Singularite-Tesseract Crystal Alloy | `mvtink_alloy_singularite_tesseract_crystal` | Void, Terrain, Resonant |
| Tin (`mvtink_tin`) | Singularite-Tin Alloy | `mvtink_alloy_singularite_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Singularite-Titanium Alloy | `mvtink_alloy_singularite_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Singularite-Topaz Alloy | `mvtink_alloy_singularite_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Singularite-Tourmaline Alloy | `mvtink_alloy_singularite_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Singularite-Tungsten Alloy | `mvtink_alloy_singularite_tungsten` | Infernal, Void, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Singularite-Void Pyrite Alloy | `mvtink_alloy_singularite_void_pyrite` | Void, Terrain, Swift |
| Void Titanium (`mvtink_void_titanium`) | Singularite-Void Titanium Alloy | `mvtink_alloy_singularite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Singularite-Voidstone Alloy | `mvtink_alloy_singularite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Singularite-Compacted Volcanic Ash Alloy | `mvtink_alloy_singularite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Singularite-Warped Emerald Alloy | `mvtink_alloy_singularite_warped_emerald` | Infernal, Void, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Singularite-Warped Quartz Alloy | `mvtink_alloy_singularite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Singularite-Pure Weeping Shard Alloy | `mvtink_alloy_singularite_weeping_shard` | Infernal, Void, Terrain |
| Witherite (`mvtink_witherite`) | Singularite-Witherite Alloy | `mvtink_alloy_singularite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Singularite-Zero-Point Shard Alloy | `mvtink_alloy_singularite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Singularite-Zinc Alloy | `mvtink_alloy_singularite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Singularite-Zircon Alloy | `mvtink_alloy_singularite_zircon` | Void, Terrain, Resonant |

#### Spatial Platinum · `mvtink_spatial_platinum` · 22 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Starlight Silver (`mvtink_starlight_silver`) | Spatial Platinum-Starlight Silver Alloy | `mvtink_alloy_spatial_platinum_starlight_silver` | Void, Tempered, Swift |
| Steel (`mvtink_steel`) | Spatial Platinum-Steel Alloy | `mvtink_alloy_spatial_platinum_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Spatial Platinum-Stibnite Alloy | `mvtink_alloy_spatial_platinum_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Spatial Platinum-Sulfur Alloy | `mvtink_alloy_spatial_platinum_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Spatial Platinum-Talc Alloy | `mvtink_alloy_spatial_platinum_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Spatial Platinum-Tesseract Crystal Alloy | `mvtink_alloy_spatial_platinum_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Spatial Platinum-Tin Alloy | `mvtink_alloy_spatial_platinum_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Spatial Platinum-Titanium Alloy | `mvtink_alloy_spatial_platinum_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Spatial Platinum-Topaz Alloy | `mvtink_alloy_spatial_platinum_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Spatial Platinum-Tourmaline Alloy | `mvtink_alloy_spatial_platinum_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Spatial Platinum-Tungsten Alloy | `mvtink_alloy_spatial_platinum_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Spatial Platinum-Void Pyrite Alloy | `mvtink_alloy_spatial_platinum_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Spatial Platinum-Void Titanium Alloy | `mvtink_alloy_spatial_platinum_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Spatial Platinum-Voidstone Alloy | `mvtink_alloy_spatial_platinum_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Spatial Platinum-Compacted Volcanic Ash Alloy | `mvtink_alloy_spatial_platinum_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Spatial Platinum-Warped Emerald Alloy | `mvtink_alloy_spatial_platinum_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Spatial Platinum-Warped Quartz Alloy | `mvtink_alloy_spatial_platinum_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Spatial Platinum-Pure Weeping Shard Alloy | `mvtink_alloy_spatial_platinum_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Spatial Platinum-Witherite Alloy | `mvtink_alloy_spatial_platinum_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Spatial Platinum-Zero-Point Shard Alloy | `mvtink_alloy_spatial_platinum_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Spatial Platinum-Zinc Alloy | `mvtink_alloy_spatial_platinum_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Spatial Platinum-Zircon Alloy | `mvtink_alloy_spatial_platinum_zircon` | Void, Terrain, Tempered |

#### Starlight Silver · `mvtink_starlight_silver` · 21 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Steel (`mvtink_steel`) | Starlight Silver-Steel Alloy | `mvtink_alloy_starlight_silver_steel` | Infernal, Void, Tempered |
| Stibnite (`mvtink_stibnite`) | Starlight Silver-Stibnite Alloy | `mvtink_alloy_starlight_silver_stibnite` | Infernal, Void, Terrain |
| Sulfur (`mvtink_sulfur`) | Starlight Silver-Sulfur Alloy | `mvtink_alloy_starlight_silver_sulfur` | Infernal, Void, Tempered |
| Talc (`mvtink_talc`) | Starlight Silver-Talc Alloy | `mvtink_alloy_starlight_silver_talc` | Void, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Starlight Silver-Tesseract Crystal Alloy | `mvtink_alloy_starlight_silver_tesseract_crystal` | Void, Tempered, Resonant |
| Tin (`mvtink_tin`) | Starlight Silver-Tin Alloy | `mvtink_alloy_starlight_silver_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Starlight Silver-Titanium Alloy | `mvtink_alloy_starlight_silver_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Starlight Silver-Topaz Alloy | `mvtink_alloy_starlight_silver_topaz` | Void, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Starlight Silver-Tourmaline Alloy | `mvtink_alloy_starlight_silver_tourmaline` | Void, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Starlight Silver-Tungsten Alloy | `mvtink_alloy_starlight_silver_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Starlight Silver-Void Pyrite Alloy | `mvtink_alloy_starlight_silver_void_pyrite` | Void, Terrain, Tempered |
| Void Titanium (`mvtink_void_titanium`) | Starlight Silver-Void Titanium Alloy | `mvtink_alloy_starlight_silver_void_titanium` | Void, Tempered, Swift |
| Voidstone (`mvtink_voidstone`) | Starlight Silver-Voidstone Alloy | `mvtink_alloy_starlight_silver_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Starlight Silver-Compacted Volcanic Ash Alloy | `mvtink_alloy_starlight_silver_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Starlight Silver-Warped Emerald Alloy | `mvtink_alloy_starlight_silver_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Starlight Silver-Warped Quartz Alloy | `mvtink_alloy_starlight_silver_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Starlight Silver-Pure Weeping Shard Alloy | `mvtink_alloy_starlight_silver_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Starlight Silver-Witherite Alloy | `mvtink_alloy_starlight_silver_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Starlight Silver-Zero-Point Shard Alloy | `mvtink_alloy_starlight_silver_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Starlight Silver-Zinc Alloy | `mvtink_alloy_starlight_silver_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Starlight Silver-Zircon Alloy | `mvtink_alloy_starlight_silver_zircon` | Void, Terrain, Tempered |

#### Tesseract Crystal · `mvtink_tesseract_crystal` · 16 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Tin (`mvtink_tin`) | Tesseract Crystal-Tin Alloy | `mvtink_alloy_tesseract_crystal_tin` | Void, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Tesseract Crystal-Titanium Alloy | `mvtink_alloy_tesseract_crystal_titanium` | Void, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Tesseract Crystal-Topaz Alloy | `mvtink_alloy_tesseract_crystal_topaz` | Void, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Tesseract Crystal-Tourmaline Alloy | `mvtink_alloy_tesseract_crystal_tourmaline` | Void, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Tesseract Crystal-Tungsten Alloy | `mvtink_alloy_tesseract_crystal_tungsten` | Infernal, Void, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Tesseract Crystal-Void Pyrite Alloy | `mvtink_alloy_tesseract_crystal_void_pyrite` | Void, Terrain, Resonant |
| Void Titanium (`mvtink_void_titanium`) | Tesseract Crystal-Void Titanium Alloy | `mvtink_alloy_tesseract_crystal_void_titanium` | Void, Tempered, Resonant |
| Voidstone (`mvtink_voidstone`) | Tesseract Crystal-Voidstone Alloy | `mvtink_alloy_tesseract_crystal_voidstone` | Void, Terrain, Resonant |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Tesseract Crystal-Compacted Volcanic Ash Alloy | `mvtink_alloy_tesseract_crystal_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Tesseract Crystal-Warped Emerald Alloy | `mvtink_alloy_tesseract_crystal_warped_emerald` | Infernal, Void, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Tesseract Crystal-Warped Quartz Alloy | `mvtink_alloy_tesseract_crystal_warped_quartz` | Void, Resonant, Swift |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Tesseract Crystal-Pure Weeping Shard Alloy | `mvtink_alloy_tesseract_crystal_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Tesseract Crystal-Witherite Alloy | `mvtink_alloy_tesseract_crystal_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Tesseract Crystal-Zero-Point Shard Alloy | `mvtink_alloy_tesseract_crystal_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Tesseract Crystal-Zinc Alloy | `mvtink_alloy_tesseract_crystal_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Tesseract Crystal-Zircon Alloy | `mvtink_alloy_tesseract_crystal_zircon` | Void, Terrain, Resonant |

#### Void Pyrite · `mvtink_void_pyrite` · 10 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Void Titanium (`mvtink_void_titanium`) | Void Pyrite-Void Titanium Alloy | `mvtink_alloy_void_pyrite_void_titanium` | Void, Terrain, Tempered |
| Voidstone (`mvtink_voidstone`) | Void Pyrite-Voidstone Alloy | `mvtink_alloy_void_pyrite_voidstone` | Void, Terrain, Swift |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Void Pyrite-Compacted Volcanic Ash Alloy | `mvtink_alloy_void_pyrite_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Void Pyrite-Warped Emerald Alloy | `mvtink_alloy_void_pyrite_warped_emerald` | Infernal, Void, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Void Pyrite-Warped Quartz Alloy | `mvtink_alloy_void_pyrite_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Void Pyrite-Pure Weeping Shard Alloy | `mvtink_alloy_void_pyrite_weeping_shard` | Infernal, Void, Terrain |
| Witherite (`mvtink_witherite`) | Void Pyrite-Witherite Alloy | `mvtink_alloy_void_pyrite_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Void Pyrite-Zero-Point Shard Alloy | `mvtink_alloy_void_pyrite_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Void Pyrite-Zinc Alloy | `mvtink_alloy_void_pyrite_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Void Pyrite-Zircon Alloy | `mvtink_alloy_void_pyrite_zircon` | Void, Terrain, Resonant |

#### Void Titanium · `mvtink_void_titanium` · 9 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Voidstone (`mvtink_voidstone`) | Void Titanium-Voidstone Alloy | `mvtink_alloy_void_titanium_voidstone` | Void, Terrain, Tempered |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Void Titanium-Compacted Volcanic Ash Alloy | `mvtink_alloy_void_titanium_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Void Titanium-Warped Emerald Alloy | `mvtink_alloy_void_titanium_warped_emerald` | Infernal, Void, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Void Titanium-Warped Quartz Alloy | `mvtink_alloy_void_titanium_warped_quartz` | Void, Tempered, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Void Titanium-Pure Weeping Shard Alloy | `mvtink_alloy_void_titanium_weeping_shard` | Infernal, Void, Tempered |
| Witherite (`mvtink_witherite`) | Void Titanium-Witherite Alloy | `mvtink_alloy_void_titanium_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Void Titanium-Zero-Point Shard Alloy | `mvtink_alloy_void_titanium_zero_point` | Void, Tempered, Volatile |
| Zinc (`mvtink_zinc`) | Void Titanium-Zinc Alloy | `mvtink_alloy_void_titanium_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Void Titanium-Zircon Alloy | `mvtink_alloy_void_titanium_zircon` | Void, Terrain, Tempered |

#### Voidstone · `mvtink_voidstone` · 8 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Voidstone-Compacted Volcanic Ash Alloy | `mvtink_alloy_voidstone_volcanic_ash` | Infernal, Void, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Voidstone-Warped Emerald Alloy | `mvtink_alloy_voidstone_warped_emerald` | Infernal, Void, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Voidstone-Warped Quartz Alloy | `mvtink_alloy_voidstone_warped_quartz` | Void, Terrain, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Voidstone-Pure Weeping Shard Alloy | `mvtink_alloy_voidstone_weeping_shard` | Infernal, Void, Terrain |
| Witherite (`mvtink_witherite`) | Voidstone-Witherite Alloy | `mvtink_alloy_voidstone_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Voidstone-Zero-Point Shard Alloy | `mvtink_alloy_voidstone_zero_point` | Void, Terrain, Volatile |
| Zinc (`mvtink_zinc`) | Voidstone-Zinc Alloy | `mvtink_alloy_voidstone_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Voidstone-Zircon Alloy | `mvtink_alloy_voidstone_zircon` | Void, Terrain, Resonant |

#### Warped Quartz · `mvtink_warped_quartz` · 5 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Pure Weeping Shard (`mvtink_weeping_shard`) | Warped Quartz-Pure Weeping Shard Alloy | `mvtink_alloy_warped_quartz_weeping_shard` | Infernal, Void, Resonant |
| Witherite (`mvtink_witherite`) | Warped Quartz-Witherite Alloy | `mvtink_alloy_warped_quartz_witherite` | Infernal, Void, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Warped Quartz-Zero-Point Shard Alloy | `mvtink_alloy_warped_quartz_zero_point` | Void, Resonant, Volatile |
| Zinc (`mvtink_zinc`) | Warped Quartz-Zinc Alloy | `mvtink_alloy_warped_quartz_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Warped Quartz-Zircon Alloy | `mvtink_alloy_warped_quartz_zircon` | Void, Terrain, Resonant |

#### Zero-Point Shard · `mvtink_zero_point` · 2 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Zinc (`mvtink_zinc`) | Zero-Point Shard-Zinc Alloy | `mvtink_alloy_zero_point_zinc` | Void, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Zero-Point Shard-Zircon Alloy | `mvtink_alloy_zero_point_zircon` | Void, Terrain, Resonant |

### 🧱 Padres vanilla

#### Amethyst · `mvtink_amethyst` · 104 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Amethyst Geode Crystal (`mvtink_amethyst_cluster_gem`) | Amethyst-Amethyst Geode Crystal Alloy | `mvtink_alloy_amethyst_amethyst_cluster_gem` | Primal, Terrain, Resonant |
| Ancient Debris Slag (`mvtink_ancient_slag`) | Amethyst-Ancient Debris Slag Alloy | `mvtink_alloy_amethyst_ancient_slag` | Infernal, Primal, Tempered |
| Aquamarine (`mvtink_aquamarine`) | Amethyst-Aquamarine Alloy | `mvtink_alloy_amethyst_aquamarine` | Primal, Terrain, Radiant |
| Ardite (`mvtink_ardite`) | Amethyst-Ardite Alloy | `mvtink_alloy_amethyst_ardite` | Infernal, Primal, Tempered |
| Astralite (`mvtink_astralite`) | Amethyst-Astralite Alloy | `mvtink_alloy_amethyst_astralite` | Void, Primal, Tempered |
| Bauxite (`mvtink_bauxite`) | Amethyst-Bauxite Alloy | `mvtink_alloy_amethyst_bauxite` | Primal, Terrain, Resonant |
| Beryllium (`mvtink_beryllium`) | Amethyst-Beryllium Alloy | `mvtink_alloy_amethyst_beryllium` | Primal, Terrain, Tempered |
| Bismuth (`mvtink_bismuth`) | Amethyst-Bismuth Alloy | `mvtink_alloy_amethyst_bismuth` | Primal, Terrain, Tempered |
| Blackstone Pyrite (`mvtink_blackstone_pyrite`) | Amethyst-Blackstone Pyrite Alloy | `mvtink_alloy_amethyst_blackstone_pyrite` | Infernal, Primal, Terrain |
| Blazesteel Shard (`mvtink_blazesteel_ore`) | Amethyst-Blazesteel Shard Alloy | `mvtink_alloy_amethyst_blazesteel_ore` | Infernal, Primal, Tempered |
| Borax (`mvtink_borax`) | Amethyst-Borax Alloy | `mvtink_alloy_amethyst_borax` | Primal, Terrain, Resonant |
| Pure Calcite (`mvtink_calcite_gem`) | Amethyst-Pure Calcite Alloy | `mvtink_alloy_amethyst_calcite_gem` | Primal, Terrain, Resonant |
| Celestine (`mvtink_celestine`) | Amethyst-Celestine Alloy | `mvtink_alloy_amethyst_celestine` | Void, Primal, Resonant |
| Chorus Crystal (`mvtink_chorus_crystal`) | Amethyst-Chorus Crystal Alloy | `mvtink_alloy_amethyst_chorus_crystal` | Void, Primal, Radiant |
| Chromite (`mvtink_chromite`) | Amethyst-Chromite Alloy | `mvtink_alloy_amethyst_chromite` | Infernal, Primal, Tempered |
| Chrono Crystal (`mvtink_chrono_crystal`) | Amethyst-Chrono Crystal Alloy | `mvtink_alloy_amethyst_chrono_crystal` | Void, Primal, Resonant |
| Cinderite (`mvtink_cinderite`) | Amethyst-Cinderite Alloy | `mvtink_alloy_amethyst_cinderite` | Infernal, Primal, Terrain |
| Cinnabar (`mvtink_cinnabar`) | Amethyst-Cinnabar Alloy | `mvtink_alloy_amethyst_cinnabar` | Primal, Terrain, Resonant |
| Coal (`mvtink_coal`) | Amethyst-Coal Alloy | `mvtink_alloy_amethyst_coal` | Primal, Terrain, Resonant |
| Cobalt (`mvtink_cobalt`) | Amethyst-Cobalt Alloy | `mvtink_alloy_amethyst_cobalt` | Infernal, Primal, Tempered |
| Copper (`mvtink_copper`) | Amethyst-Copper Alloy | `mvtink_alloy_amethyst_copper` | Primal, Tempered, Resonant |
| Cosmium (`mvtink_cosmium`) | Amethyst-Cosmium Alloy | `mvtink_alloy_amethyst_cosmium` | Void, Primal, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Amethyst-Crimson Gold Alloy | `mvtink_alloy_amethyst_crimson_gold` | Infernal, Primal, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Amethyst-Crimson Quartz Alloy | `mvtink_alloy_amethyst_crimson_quartz` | Infernal, Primal, Resonant |
| Cryolite (`mvtink_cryolite`) | Amethyst-Cryolite Alloy | `mvtink_alloy_amethyst_cryolite` | Primal, Terrain, Resonant |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Amethyst-Cursed Brimstone Alloy | `mvtink_alloy_amethyst_cursed_brimstone` | Infernal, Primal, Resonant |
| Diamond (`mvtink_diamond`) | Amethyst-Diamond Alloy | `mvtink_alloy_amethyst_diamond` | Primal, Radiant, Resonant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Amethyst-Dragon Scale Shard Alloy | `mvtink_alloy_amethyst_dragon_shard` | Void, Primal, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Amethyst-Eclipse Gem Alloy | `mvtink_alloy_amethyst_eclipse_gem` | Void, Primal, Radiant |
| Emerald (`mvtink_emerald`) | Amethyst-Emerald Alloy | `mvtink_alloy_amethyst_emerald` | Primal, Radiant, Resonant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Amethyst-End Crystal Shard Alloy | `mvtink_alloy_amethyst_end_crystal_shard` | Void, Primal, Resonant |
| Enderite (`mvtink_enderite`) | Amethyst-Enderite Alloy | `mvtink_alloy_amethyst_enderite` | Void, Primal, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Amethyst-Fire Opal Alloy | `mvtink_alloy_amethyst_fire_opal` | Infernal, Primal, Radiant |
| Flint (`mvtink_flint`) | Amethyst-Flint Alloy | `mvtink_alloy_amethyst_flint` | Primal, Terrain, Resonant |
| Fluorite (`mvtink_fluorite`) | Amethyst-Fluorite Alloy | `mvtink_alloy_amethyst_fluorite` | Primal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Amethyst-Galena Alloy | `mvtink_alloy_amethyst_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Amethyst-Ghast Tear Shard Alloy | `mvtink_alloy_amethyst_ghast_tear_shard` | Infernal, Primal, Resonant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Amethyst-Glowstone Gem Alloy | `mvtink_alloy_amethyst_glowstone_gem` | Infernal, Primal, Radiant |
| Gold (`mvtink_gold`) | Amethyst-Gold Alloy | `mvtink_alloy_amethyst_gold` | Primal, Tempered, Resonant |
| Graphite (`mvtink_graphite`) | Amethyst-Graphite Alloy | `mvtink_alloy_amethyst_graphite` | Primal, Terrain, Resonant |
| Gravitite (`mvtink_gravitite`) | Amethyst-Gravitite Alloy | `mvtink_alloy_amethyst_gravitite` | Void, Primal, Tempered |
| Gypsum (`mvtink_gypsum`) | Amethyst-Gypsum Alloy | `mvtink_alloy_amethyst_gypsum` | Primal, Terrain, Resonant |
| Helliron (`mvtink_helliron`) | Amethyst-Helliron Alloy | `mvtink_alloy_amethyst_helliron` | Infernal, Primal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Amethyst-Ignis Ferrum Alloy | `mvtink_alloy_amethyst_ignis_ferrum` | Infernal, Primal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Amethyst-Infernal Obsidian Alloy | `mvtink_alloy_amethyst_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Amethyst-Netherite-Infused Quartz Alloy | `mvtink_alloy_amethyst_infused_quartz` | Infernal, Primal, Resonant |
| Iron (`mvtink_iron`) | Amethyst-Iron Alloy | `mvtink_alloy_amethyst_iron` | Primal, Tempered, Resonant |
| Jade (`mvtink_jade`) | Amethyst-Jade Alloy | `mvtink_alloy_amethyst_jade` | Primal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Amethyst-Kaolinite Alloy | `mvtink_alloy_amethyst_kaolinite` | Primal, Terrain, Resonant |
| Lapis Lazuli (`mvtink_lapis`) | Amethyst-Lapis Lazuli Alloy | `mvtink_alloy_amethyst_lapis` | Primal, Radiant, Resonant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Amethyst-Lapis Matrix Alloy | `mvtink_alloy_amethyst_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Amethyst-Magma Brimstone Alloy | `mvtink_alloy_amethyst_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Amethyst-Magmacite Alloy | `mvtink_alloy_amethyst_magmacite` | Infernal, Primal, Resonant |
| Magnetite (`mvtink_magnetite`) | Amethyst-Magnetite Alloy | `mvtink_alloy_amethyst_magnetite` | Primal, Terrain, Resonant |
| Malachite (`mvtink_malachite`) | Amethyst-Malachite Alloy | `mvtink_alloy_amethyst_malachite` | Primal, Terrain, Resonant |
| Nebulite (`mvtink_nebulite`) | Amethyst-Nebulite Alloy | `mvtink_alloy_amethyst_nebulite` | Void, Primal, Resonant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Amethyst-Nether Bismuth Alloy | `mvtink_alloy_amethyst_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Amethyst-Nether Tungsten Alloy | `mvtink_alloy_amethyst_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Amethyst-Netherite Scrap Shard Alloy | `mvtink_alloy_amethyst_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Amethyst-Nickel Alloy | `mvtink_alloy_amethyst_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Amethyst-Null-Shard Alloy | `mvtink_alloy_amethyst_null_shard` | Void, Primal, Resonant |
| Obsidian (`mvtink_obsidian`) | Amethyst-Obsidian Alloy | `mvtink_alloy_amethyst_obsidian` | Primal, Terrain, Resonant |
| Obsidianite (`mvtink_obsidianite`) | Amethyst-Obsidianite Alloy | `mvtink_alloy_amethyst_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Amethyst-Opal Alloy | `mvtink_alloy_amethyst_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Amethyst-Ender Pearl Core Alloy | `mvtink_alloy_amethyst_pearl_core` | Void, Primal, Radiant |
| Phantomite (`mvtink_phantomite`) | Amethyst-Phantomite Alloy | `mvtink_alloy_amethyst_phantomite` | Void, Primal, Resonant |
| Platinum (`mvtink_platinum`) | Amethyst-Platinum Alloy | `mvtink_alloy_amethyst_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Amethyst-Prismarine Alloy | `mvtink_alloy_amethyst_prismarine` | Primal, Resonant |
| Pyrite (`mvtink_pyrite`) | Amethyst-Pyrite Alloy | `mvtink_alloy_amethyst_pyrite` | Primal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Amethyst-Pyrophore Alloy | `mvtink_alloy_amethyst_pyrophore` | Infernal, Primal, Resonant |
| Nether Quartz (`mvtink_quartz`) ★ | Prismatic Quartz | `mvtink_prismatic_quartz` | Primal, Resonant |
| Redstone (`mvtink_redstone`) | Amethyst-Redstone Alloy | `mvtink_alloy_amethyst_redstone` | Primal, Resonant, Volatile |
| Resonite (`mvtink_resonite`) | Amethyst-Resonite Alloy | `mvtink_alloy_amethyst_resonite` | Void, Primal, Resonant |
| Ruby (`mvtink_ruby`) | Amethyst-Ruby Alloy | `mvtink_alloy_amethyst_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Amethyst-Sanguinite Alloy | `mvtink_alloy_amethyst_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Amethyst-Sapphire Alloy | `mvtink_alloy_amethyst_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Amethyst-Shadowgem Alloy | `mvtink_alloy_amethyst_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Amethyst-Shulkerite Alloy | `mvtink_alloy_amethyst_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Amethyst-Silver Alloy | `mvtink_alloy_amethyst_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Amethyst-Singularite Alloy | `mvtink_alloy_amethyst_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Amethyst-Soul Glass Crystal Alloy | `mvtink_alloy_amethyst_soulsand_crystal` | Infernal, Primal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Amethyst-Spatial Platinum Alloy | `mvtink_alloy_amethyst_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Amethyst-Starlight Silver Alloy | `mvtink_alloy_amethyst_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Amethyst-Steel Alloy | `mvtink_alloy_amethyst_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Amethyst-Stibnite Alloy | `mvtink_alloy_amethyst_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Amethyst-Sulfur Alloy | `mvtink_alloy_amethyst_sulfur` | Infernal, Primal, Resonant |
| Talc (`mvtink_talc`) | Amethyst-Talc Alloy | `mvtink_alloy_amethyst_talc` | Primal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Amethyst-Tesseract Crystal Alloy | `mvtink_alloy_amethyst_tesseract_crystal` | Void, Primal, Resonant |
| Tin (`mvtink_tin`) | Amethyst-Tin Alloy | `mvtink_alloy_amethyst_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Amethyst-Titanium Alloy | `mvtink_alloy_amethyst_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Amethyst-Topaz Alloy | `mvtink_alloy_amethyst_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Amethyst-Tourmaline Alloy | `mvtink_alloy_amethyst_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Amethyst-Tungsten Alloy | `mvtink_alloy_amethyst_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Amethyst-Void Pyrite Alloy | `mvtink_alloy_amethyst_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Amethyst-Void Titanium Alloy | `mvtink_alloy_amethyst_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Amethyst-Voidstone Alloy | `mvtink_alloy_amethyst_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Amethyst-Compacted Volcanic Ash Alloy | `mvtink_alloy_amethyst_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Amethyst-Warped Emerald Alloy | `mvtink_alloy_amethyst_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Amethyst-Warped Quartz Alloy | `mvtink_alloy_amethyst_warped_quartz` | Void, Primal, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Amethyst-Pure Weeping Shard Alloy | `mvtink_alloy_amethyst_weeping_shard` | Infernal, Primal, Resonant |
| Witherite (`mvtink_witherite`) | Amethyst-Witherite Alloy | `mvtink_alloy_amethyst_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Amethyst-Zero-Point Shard Alloy | `mvtink_alloy_amethyst_zero_point` | Void, Primal, Resonant |
| Zinc (`mvtink_zinc`) | Amethyst-Zinc Alloy | `mvtink_alloy_amethyst_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Amethyst-Zircon Alloy | `mvtink_alloy_amethyst_zircon` | Primal, Terrain, Resonant |

#### Coal · `mvtink_coal` · 85 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cobalt (`mvtink_cobalt`) | Coal-Cobalt Alloy | `mvtink_alloy_coal_cobalt` | Infernal, Primal, Terrain |
| Copper (`mvtink_copper`) | Coal-Copper Alloy | `mvtink_alloy_coal_copper` | Primal, Terrain, Tempered |
| Cosmium (`mvtink_cosmium`) | Coal-Cosmium Alloy | `mvtink_alloy_coal_cosmium` | Void, Primal, Terrain |
| Crimson Gold (`mvtink_crimson_gold`) | Coal-Crimson Gold Alloy | `mvtink_alloy_coal_crimson_gold` | Infernal, Primal, Terrain |
| Crimson Quartz (`mvtink_crimson_quartz`) | Coal-Crimson Quartz Alloy | `mvtink_alloy_coal_crimson_quartz` | Infernal, Primal, Terrain |
| Cryolite (`mvtink_cryolite`) | Coal-Cryolite Alloy | `mvtink_alloy_coal_cryolite` | Primal, Terrain |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Coal-Cursed Brimstone Alloy | `mvtink_alloy_coal_cursed_brimstone` | Infernal, Primal, Terrain |
| Diamond (`mvtink_diamond`) | Coal-Diamond Alloy | `mvtink_alloy_coal_diamond` | Primal, Terrain, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Coal-Dragon Scale Shard Alloy | `mvtink_alloy_coal_dragon_shard` | Void, Primal, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Coal-Eclipse Gem Alloy | `mvtink_alloy_coal_eclipse_gem` | Void, Primal, Terrain |
| Emerald (`mvtink_emerald`) | Coal-Emerald Alloy | `mvtink_alloy_coal_emerald` | Primal, Terrain, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Coal-End Crystal Shard Alloy | `mvtink_alloy_coal_end_crystal_shard` | Void, Primal, Terrain |
| Enderite (`mvtink_enderite`) | Coal-Enderite Alloy | `mvtink_alloy_coal_enderite` | Void, Primal, Terrain |
| Fire Opal (`mvtink_fire_opal`) | Coal-Fire Opal Alloy | `mvtink_alloy_coal_fire_opal` | Infernal, Primal, Terrain |
| Flint (`mvtink_flint`) | Coal-Flint Alloy | `mvtink_alloy_coal_flint` | Primal, Terrain |
| Fluorite (`mvtink_fluorite`) | Coal-Fluorite Alloy | `mvtink_alloy_coal_fluorite` | Primal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Coal-Galena Alloy | `mvtink_alloy_coal_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Coal-Ghast Tear Shard Alloy | `mvtink_alloy_coal_ghast_tear_shard` | Infernal, Primal, Terrain |
| Glowstone Gem (`mvtink_glowstone_gem`) | Coal-Glowstone Gem Alloy | `mvtink_alloy_coal_glowstone_gem` | Infernal, Primal, Terrain |
| Gold (`mvtink_gold`) | Coal-Gold Alloy | `mvtink_alloy_coal_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Coal-Graphite Alloy | `mvtink_alloy_coal_graphite` | Primal, Terrain |
| Gravitite (`mvtink_gravitite`) | Coal-Gravitite Alloy | `mvtink_alloy_coal_gravitite` | Void, Primal, Terrain |
| Gypsum (`mvtink_gypsum`) | Coal-Gypsum Alloy | `mvtink_alloy_coal_gypsum` | Primal, Terrain |
| Helliron (`mvtink_helliron`) | Coal-Helliron Alloy | `mvtink_alloy_coal_helliron` | Infernal, Primal, Terrain |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Coal-Ignis Ferrum Alloy | `mvtink_alloy_coal_ignis_ferrum` | Infernal, Primal, Terrain |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Coal-Infernal Obsidian Alloy | `mvtink_alloy_coal_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Coal-Netherite-Infused Quartz Alloy | `mvtink_alloy_coal_infused_quartz` | Infernal, Primal, Terrain |
| Iron (`mvtink_iron`) | Coal-Iron Alloy | `mvtink_alloy_coal_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Coal-Jade Alloy | `mvtink_alloy_coal_jade` | Primal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Coal-Kaolinite Alloy | `mvtink_alloy_coal_kaolinite` | Primal, Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Coal-Lapis Lazuli Alloy | `mvtink_alloy_coal_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Coal-Lapis Matrix Alloy | `mvtink_alloy_coal_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Coal-Magma Brimstone Alloy | `mvtink_alloy_coal_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Coal-Magmacite Alloy | `mvtink_alloy_coal_magmacite` | Infernal, Primal, Terrain |
| Magnetite (`mvtink_magnetite`) | Coal-Magnetite Alloy | `mvtink_alloy_coal_magnetite` | Primal, Terrain |
| Malachite (`mvtink_malachite`) | Coal-Malachite Alloy | `mvtink_alloy_coal_malachite` | Primal, Terrain |
| Nebulite (`mvtink_nebulite`) | Coal-Nebulite Alloy | `mvtink_alloy_coal_nebulite` | Void, Primal, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Coal-Nether Bismuth Alloy | `mvtink_alloy_coal_nether_bismuth` | Infernal, Primal, Terrain |
| Nether Tungsten (`mvtink_nether_tungsten`) | Coal-Nether Tungsten Alloy | `mvtink_alloy_coal_nether_tungsten` | Infernal, Primal, Terrain |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Coal-Netherite Scrap Shard Alloy | `mvtink_alloy_coal_netherite_shard` | Infernal, Primal, Terrain |
| Nickel (`mvtink_nickel`) | Coal-Nickel Alloy | `mvtink_alloy_coal_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Coal-Null-Shard Alloy | `mvtink_alloy_coal_null_shard` | Void, Primal, Terrain |
| Obsidian (`mvtink_obsidian`) | Coal-Obsidian Alloy | `mvtink_alloy_coal_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Coal-Obsidianite Alloy | `mvtink_alloy_coal_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Coal-Opal Alloy | `mvtink_alloy_coal_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Coal-Ender Pearl Core Alloy | `mvtink_alloy_coal_pearl_core` | Void, Primal, Terrain |
| Phantomite (`mvtink_phantomite`) | Coal-Phantomite Alloy | `mvtink_alloy_coal_phantomite` | Void, Primal, Terrain |
| Platinum (`mvtink_platinum`) | Coal-Platinum Alloy | `mvtink_alloy_coal_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Coal-Prismarine Alloy | `mvtink_alloy_coal_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Coal-Pyrite Alloy | `mvtink_alloy_coal_pyrite` | Primal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Coal-Pyrophore Alloy | `mvtink_alloy_coal_pyrophore` | Infernal, Primal, Terrain |
| Nether Quartz (`mvtink_quartz`) | Coal-Nether Quartz Alloy | `mvtink_alloy_coal_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Coal-Redstone Alloy | `mvtink_alloy_coal_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Coal-Resonite Alloy | `mvtink_alloy_coal_resonite` | Void, Primal, Terrain |
| Ruby (`mvtink_ruby`) | Coal-Ruby Alloy | `mvtink_alloy_coal_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Coal-Sanguinite Alloy | `mvtink_alloy_coal_sanguinite` | Infernal, Primal, Terrain |
| Sapphire (`mvtink_sapphire`) | Coal-Sapphire Alloy | `mvtink_alloy_coal_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Coal-Shadowgem Alloy | `mvtink_alloy_coal_shadowgem` | Void, Primal, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Coal-Shulkerite Alloy | `mvtink_alloy_coal_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Coal-Silver Alloy | `mvtink_alloy_coal_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Coal-Singularite Alloy | `mvtink_alloy_coal_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Coal-Soul Glass Crystal Alloy | `mvtink_alloy_coal_soulsand_crystal` | Infernal, Primal, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Coal-Spatial Platinum Alloy | `mvtink_alloy_coal_spatial_platinum` | Void, Primal, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Coal-Starlight Silver Alloy | `mvtink_alloy_coal_starlight_silver` | Void, Primal, Terrain |
| Steel (`mvtink_steel`) | Coal-Steel Alloy | `mvtink_alloy_coal_steel` | Infernal, Primal, Terrain |
| Stibnite (`mvtink_stibnite`) | Coal-Stibnite Alloy | `mvtink_alloy_coal_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Coal-Sulfur Alloy | `mvtink_alloy_coal_sulfur` | Infernal, Primal, Terrain |
| Talc (`mvtink_talc`) | Coal-Talc Alloy | `mvtink_alloy_coal_talc` | Primal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Coal-Tesseract Crystal Alloy | `mvtink_alloy_coal_tesseract_crystal` | Void, Primal, Terrain |
| Tin (`mvtink_tin`) | Coal-Tin Alloy | `mvtink_alloy_coal_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Coal-Titanium Alloy | `mvtink_alloy_coal_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Coal-Topaz Alloy | `mvtink_alloy_coal_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Coal-Tourmaline Alloy | `mvtink_alloy_coal_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Coal-Tungsten Alloy | `mvtink_alloy_coal_tungsten` | Infernal, Primal, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Coal-Void Pyrite Alloy | `mvtink_alloy_coal_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Coal-Void Titanium Alloy | `mvtink_alloy_coal_void_titanium` | Void, Primal, Terrain |
| Voidstone (`mvtink_voidstone`) | Coal-Voidstone Alloy | `mvtink_alloy_coal_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Coal-Compacted Volcanic Ash Alloy | `mvtink_alloy_coal_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Coal-Warped Emerald Alloy | `mvtink_alloy_coal_warped_emerald` | Infernal, Primal, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Coal-Warped Quartz Alloy | `mvtink_alloy_coal_warped_quartz` | Void, Primal, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Coal-Pure Weeping Shard Alloy | `mvtink_alloy_coal_weeping_shard` | Infernal, Primal, Terrain |
| Witherite (`mvtink_witherite`) | Coal-Witherite Alloy | `mvtink_alloy_coal_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Coal-Zero-Point Shard Alloy | `mvtink_alloy_coal_zero_point` | Void, Primal, Terrain |
| Zinc (`mvtink_zinc`) | Coal-Zinc Alloy | `mvtink_alloy_coal_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Coal-Zircon Alloy | `mvtink_alloy_coal_zircon` | Primal, Terrain, Resonant |

#### Copper · `mvtink_copper` · 83 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Cosmium (`mvtink_cosmium`) | Copper-Cosmium Alloy | `mvtink_alloy_copper_cosmium` | Void, Primal, Tempered |
| Crimson Gold (`mvtink_crimson_gold`) | Copper-Crimson Gold Alloy | `mvtink_alloy_copper_crimson_gold` | Infernal, Primal, Tempered |
| Crimson Quartz (`mvtink_crimson_quartz`) | Copper-Crimson Quartz Alloy | `mvtink_alloy_copper_crimson_quartz` | Infernal, Primal, Tempered |
| Cryolite (`mvtink_cryolite`) | Copper-Cryolite Alloy | `mvtink_alloy_copper_cryolite` | Primal, Terrain, Tempered |
| Cursed Brimstone (`mvtink_cursed_brimstone`) | Copper-Cursed Brimstone Alloy | `mvtink_alloy_copper_cursed_brimstone` | Infernal, Primal, Tempered |
| Diamond (`mvtink_diamond`) | Copper-Diamond Alloy | `mvtink_alloy_copper_diamond` | Primal, Tempered, Radiant |
| Dragon Scale Shard (`mvtink_dragon_shard`) | Copper-Dragon Scale Shard Alloy | `mvtink_alloy_copper_dragon_shard` | Void, Primal, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Copper-Eclipse Gem Alloy | `mvtink_alloy_copper_eclipse_gem` | Void, Primal, Tempered |
| Emerald (`mvtink_emerald`) | Copper-Emerald Alloy | `mvtink_alloy_copper_emerald` | Primal, Tempered, Radiant |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Copper-End Crystal Shard Alloy | `mvtink_alloy_copper_end_crystal_shard` | Void, Primal, Tempered |
| Enderite (`mvtink_enderite`) | Copper-Enderite Alloy | `mvtink_alloy_copper_enderite` | Void, Primal, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Copper-Fire Opal Alloy | `mvtink_alloy_copper_fire_opal` | Infernal, Primal, Tempered |
| Flint (`mvtink_flint`) | Copper-Flint Alloy | `mvtink_alloy_copper_flint` | Primal, Terrain, Tempered |
| Fluorite (`mvtink_fluorite`) | Copper-Fluorite Alloy | `mvtink_alloy_copper_fluorite` | Primal, Terrain, Tempered |
| Galena (`mvtink_galena`) | Copper-Galena Alloy | `mvtink_alloy_copper_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Copper-Ghast Tear Shard Alloy | `mvtink_alloy_copper_ghast_tear_shard` | Infernal, Primal, Tempered |
| Glowstone Gem (`mvtink_glowstone_gem`) | Copper-Glowstone Gem Alloy | `mvtink_alloy_copper_glowstone_gem` | Infernal, Primal, Tempered |
| Gold (`mvtink_gold`) ★ | Rose Gold | `mvtink_rose_gold` | Primal, Tempered, Swift |
| Graphite (`mvtink_graphite`) | Copper-Graphite Alloy | `mvtink_alloy_copper_graphite` | Primal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Copper-Gravitite Alloy | `mvtink_alloy_copper_gravitite` | Void, Primal, Tempered |
| Gypsum (`mvtink_gypsum`) | Copper-Gypsum Alloy | `mvtink_alloy_copper_gypsum` | Primal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Copper-Helliron Alloy | `mvtink_alloy_copper_helliron` | Infernal, Primal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Copper-Ignis Ferrum Alloy | `mvtink_alloy_copper_ignis_ferrum` | Infernal, Primal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Copper-Infernal Obsidian Alloy | `mvtink_alloy_copper_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Copper-Netherite-Infused Quartz Alloy | `mvtink_alloy_copper_infused_quartz` | Infernal, Primal, Tempered |
| Iron (`mvtink_iron`) | Copper-Iron Alloy | `mvtink_alloy_copper_iron` | Primal, Tempered |
| Jade (`mvtink_jade`) | Copper-Jade Alloy | `mvtink_alloy_copper_jade` | Primal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Copper-Kaolinite Alloy | `mvtink_alloy_copper_kaolinite` | Primal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Copper-Lapis Lazuli Alloy | `mvtink_alloy_copper_lapis` | Primal, Tempered, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Copper-Lapis Matrix Alloy | `mvtink_alloy_copper_lapis_matrix` | Primal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Copper-Magma Brimstone Alloy | `mvtink_alloy_copper_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Copper-Magmacite Alloy | `mvtink_alloy_copper_magmacite` | Infernal, Primal, Tempered |
| Magnetite (`mvtink_magnetite`) | Copper-Magnetite Alloy | `mvtink_alloy_copper_magnetite` | Primal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Copper-Malachite Alloy | `mvtink_alloy_copper_malachite` | Primal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Copper-Nebulite Alloy | `mvtink_alloy_copper_nebulite` | Void, Primal, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Copper-Nether Bismuth Alloy | `mvtink_alloy_copper_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Copper-Nether Tungsten Alloy | `mvtink_alloy_copper_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Copper-Netherite Scrap Shard Alloy | `mvtink_alloy_copper_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Copper-Nickel Alloy | `mvtink_alloy_copper_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Copper-Null-Shard Alloy | `mvtink_alloy_copper_null_shard` | Void, Primal, Tempered |
| Obsidian (`mvtink_obsidian`) | Copper-Obsidian Alloy | `mvtink_alloy_copper_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Copper-Obsidianite Alloy | `mvtink_alloy_copper_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Copper-Opal Alloy | `mvtink_alloy_copper_opal` | Primal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Copper-Ender Pearl Core Alloy | `mvtink_alloy_copper_pearl_core` | Void, Primal, Tempered |
| Phantomite (`mvtink_phantomite`) | Copper-Phantomite Alloy | `mvtink_alloy_copper_phantomite` | Void, Primal, Tempered |
| Platinum (`mvtink_platinum`) | Copper-Platinum Alloy | `mvtink_alloy_copper_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Copper-Prismarine Alloy | `mvtink_alloy_copper_prismarine` | Primal, Tempered, Resonant |
| Pyrite (`mvtink_pyrite`) | Copper-Pyrite Alloy | `mvtink_alloy_copper_pyrite` | Primal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Copper-Pyrophore Alloy | `mvtink_alloy_copper_pyrophore` | Infernal, Primal, Tempered |
| Nether Quartz (`mvtink_quartz`) | Copper-Nether Quartz Alloy | `mvtink_alloy_copper_quartz` | Primal, Tempered, Resonant |
| Redstone (`mvtink_redstone`) | Copper-Redstone Alloy | `mvtink_alloy_copper_redstone` | Primal, Tempered, Volatile |
| Resonite (`mvtink_resonite`) | Copper-Resonite Alloy | `mvtink_alloy_copper_resonite` | Void, Primal, Tempered |
| Ruby (`mvtink_ruby`) | Copper-Ruby Alloy | `mvtink_alloy_copper_ruby` | Primal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Copper-Sanguinite Alloy | `mvtink_alloy_copper_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Copper-Sapphire Alloy | `mvtink_alloy_copper_sapphire` | Primal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Copper-Shadowgem Alloy | `mvtink_alloy_copper_shadowgem` | Void, Primal, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Copper-Shulkerite Alloy | `mvtink_alloy_copper_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Copper-Silver Alloy | `mvtink_alloy_copper_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Copper-Singularite Alloy | `mvtink_alloy_copper_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Copper-Soul Glass Crystal Alloy | `mvtink_alloy_copper_soulsand_crystal` | Infernal, Primal, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Copper-Spatial Platinum Alloy | `mvtink_alloy_copper_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Copper-Starlight Silver Alloy | `mvtink_alloy_copper_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Copper-Steel Alloy | `mvtink_alloy_copper_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Copper-Stibnite Alloy | `mvtink_alloy_copper_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Copper-Sulfur Alloy | `mvtink_alloy_copper_sulfur` | Infernal, Primal, Tempered |
| Talc (`mvtink_talc`) | Copper-Talc Alloy | `mvtink_alloy_copper_talc` | Primal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Copper-Tesseract Crystal Alloy | `mvtink_alloy_copper_tesseract_crystal` | Void, Primal, Tempered |
| Tin (`mvtink_tin`) ★ | Bronze | `mvtink_bronze` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Copper-Titanium Alloy | `mvtink_alloy_copper_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Copper-Topaz Alloy | `mvtink_alloy_copper_topaz` | Primal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Copper-Tourmaline Alloy | `mvtink_alloy_copper_tourmaline` | Primal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Copper-Tungsten Alloy | `mvtink_alloy_copper_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Copper-Void Pyrite Alloy | `mvtink_alloy_copper_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Copper-Void Titanium Alloy | `mvtink_alloy_copper_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Copper-Voidstone Alloy | `mvtink_alloy_copper_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Copper-Compacted Volcanic Ash Alloy | `mvtink_alloy_copper_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Copper-Warped Emerald Alloy | `mvtink_alloy_copper_warped_emerald` | Infernal, Primal, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Copper-Warped Quartz Alloy | `mvtink_alloy_copper_warped_quartz` | Void, Primal, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Copper-Pure Weeping Shard Alloy | `mvtink_alloy_copper_weeping_shard` | Infernal, Primal, Tempered |
| Witherite (`mvtink_witherite`) | Copper-Witherite Alloy | `mvtink_alloy_copper_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Copper-Zero-Point Shard Alloy | `mvtink_alloy_copper_zero_point` | Void, Primal, Tempered |
| Zinc (`mvtink_zinc`) | Copper-Zinc Alloy | `mvtink_alloy_copper_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Copper-Zircon Alloy | `mvtink_alloy_copper_zircon` | Primal, Terrain, Tempered |

#### Diamond · `mvtink_diamond` · 77 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Dragon Scale Shard (`mvtink_dragon_shard`) | Diamond-Dragon Scale Shard Alloy | `mvtink_alloy_diamond_dragon_shard` | Void, Primal, Terrain |
| Eclipse Gem (`mvtink_eclipse_gem`) | Diamond-Eclipse Gem Alloy | `mvtink_alloy_diamond_eclipse_gem` | Void, Primal, Radiant |
| Emerald (`mvtink_emerald`) | Diamond-Emerald Alloy | `mvtink_alloy_diamond_emerald` | Primal, Radiant, Brutal |
| End Crystal Shard (`mvtink_end_crystal_shard`) | Diamond-End Crystal Shard Alloy | `mvtink_alloy_diamond_end_crystal_shard` | Void, Primal, Radiant |
| Enderite (`mvtink_enderite`) | Diamond-Enderite Alloy | `mvtink_alloy_diamond_enderite` | Void, Primal, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Diamond-Fire Opal Alloy | `mvtink_alloy_diamond_fire_opal` | Infernal, Primal, Radiant |
| Flint (`mvtink_flint`) | Diamond-Flint Alloy | `mvtink_alloy_diamond_flint` | Primal, Terrain, Radiant |
| Fluorite (`mvtink_fluorite`) | Diamond-Fluorite Alloy | `mvtink_alloy_diamond_fluorite` | Primal, Terrain, Radiant |
| Galena (`mvtink_galena`) | Diamond-Galena Alloy | `mvtink_alloy_diamond_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Diamond-Ghast Tear Shard Alloy | `mvtink_alloy_diamond_ghast_tear_shard` | Infernal, Primal, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Diamond-Glowstone Gem Alloy | `mvtink_alloy_diamond_glowstone_gem` | Infernal, Primal, Radiant |
| Gold (`mvtink_gold`) | Diamond-Gold Alloy | `mvtink_alloy_diamond_gold` | Primal, Tempered, Radiant |
| Graphite (`mvtink_graphite`) | Diamond-Graphite Alloy | `mvtink_alloy_diamond_graphite` | Primal, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Diamond-Gravitite Alloy | `mvtink_alloy_diamond_gravitite` | Void, Primal, Tempered |
| Gypsum (`mvtink_gypsum`) | Diamond-Gypsum Alloy | `mvtink_alloy_diamond_gypsum` | Primal, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Diamond-Helliron Alloy | `mvtink_alloy_diamond_helliron` | Infernal, Primal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Diamond-Ignis Ferrum Alloy | `mvtink_alloy_diamond_ignis_ferrum` | Infernal, Primal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Diamond-Infernal Obsidian Alloy | `mvtink_alloy_diamond_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Diamond-Netherite-Infused Quartz Alloy | `mvtink_alloy_diamond_infused_quartz` | Infernal, Primal, Radiant |
| Iron (`mvtink_iron`) | Diamond-Iron Alloy | `mvtink_alloy_diamond_iron` | Primal, Tempered, Radiant |
| Jade (`mvtink_jade`) | Diamond-Jade Alloy | `mvtink_alloy_diamond_jade` | Primal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Diamond-Kaolinite Alloy | `mvtink_alloy_diamond_kaolinite` | Primal, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Diamond-Lapis Lazuli Alloy | `mvtink_alloy_diamond_lapis` | Primal, Radiant, Brutal |
| Lapis Matrix (`mvtink_lapis_matrix`) | Diamond-Lapis Matrix Alloy | `mvtink_alloy_diamond_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Diamond-Magma Brimstone Alloy | `mvtink_alloy_diamond_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Diamond-Magmacite Alloy | `mvtink_alloy_diamond_magmacite` | Infernal, Primal, Radiant |
| Magnetite (`mvtink_magnetite`) | Diamond-Magnetite Alloy | `mvtink_alloy_diamond_magnetite` | Primal, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Diamond-Malachite Alloy | `mvtink_alloy_diamond_malachite` | Primal, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Diamond-Nebulite Alloy | `mvtink_alloy_diamond_nebulite` | Void, Primal, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Diamond-Nether Bismuth Alloy | `mvtink_alloy_diamond_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Diamond-Nether Tungsten Alloy | `mvtink_alloy_diamond_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Diamond-Netherite Scrap Shard Alloy | `mvtink_alloy_diamond_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Diamond-Nickel Alloy | `mvtink_alloy_diamond_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Diamond-Null-Shard Alloy | `mvtink_alloy_diamond_null_shard` | Void, Primal, Radiant |
| Obsidian (`mvtink_obsidian`) | Diamond-Obsidian Alloy | `mvtink_alloy_diamond_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Diamond-Obsidianite Alloy | `mvtink_alloy_diamond_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Diamond-Opal Alloy | `mvtink_alloy_diamond_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Diamond-Ender Pearl Core Alloy | `mvtink_alloy_diamond_pearl_core` | Void, Primal, Radiant |
| Phantomite (`mvtink_phantomite`) | Diamond-Phantomite Alloy | `mvtink_alloy_diamond_phantomite` | Void, Primal, Radiant |
| Platinum (`mvtink_platinum`) | Diamond-Platinum Alloy | `mvtink_alloy_diamond_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Diamond-Prismarine Alloy | `mvtink_alloy_diamond_prismarine` | Primal, Radiant, Resonant |
| Pyrite (`mvtink_pyrite`) | Diamond-Pyrite Alloy | `mvtink_alloy_diamond_pyrite` | Primal, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Diamond-Pyrophore Alloy | `mvtink_alloy_diamond_pyrophore` | Infernal, Primal, Radiant |
| Nether Quartz (`mvtink_quartz`) | Diamond-Nether Quartz Alloy | `mvtink_alloy_diamond_quartz` | Primal, Radiant, Resonant |
| Redstone (`mvtink_redstone`) | Diamond-Redstone Alloy | `mvtink_alloy_diamond_redstone` | Primal, Radiant, Volatile |
| Resonite (`mvtink_resonite`) | Diamond-Resonite Alloy | `mvtink_alloy_diamond_resonite` | Void, Primal, Radiant |
| Ruby (`mvtink_ruby`) | Diamond-Ruby Alloy | `mvtink_alloy_diamond_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Diamond-Sanguinite Alloy | `mvtink_alloy_diamond_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Diamond-Sapphire Alloy | `mvtink_alloy_diamond_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Diamond-Shadowgem Alloy | `mvtink_alloy_diamond_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Diamond-Shulkerite Alloy | `mvtink_alloy_diamond_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Diamond-Silver Alloy | `mvtink_alloy_diamond_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Diamond-Singularite Alloy | `mvtink_alloy_diamond_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Diamond-Soul Glass Crystal Alloy | `mvtink_alloy_diamond_soulsand_crystal` | Infernal, Primal, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Diamond-Spatial Platinum Alloy | `mvtink_alloy_diamond_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Diamond-Starlight Silver Alloy | `mvtink_alloy_diamond_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Diamond-Steel Alloy | `mvtink_alloy_diamond_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Diamond-Stibnite Alloy | `mvtink_alloy_diamond_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Diamond-Sulfur Alloy | `mvtink_alloy_diamond_sulfur` | Infernal, Primal, Radiant |
| Talc (`mvtink_talc`) | Diamond-Talc Alloy | `mvtink_alloy_diamond_talc` | Primal, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Diamond-Tesseract Crystal Alloy | `mvtink_alloy_diamond_tesseract_crystal` | Void, Primal, Radiant |
| Tin (`mvtink_tin`) | Diamond-Tin Alloy | `mvtink_alloy_diamond_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Diamond-Titanium Alloy | `mvtink_alloy_diamond_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Diamond-Topaz Alloy | `mvtink_alloy_diamond_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Diamond-Tourmaline Alloy | `mvtink_alloy_diamond_tourmaline` | Primal, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Diamond-Tungsten Alloy | `mvtink_alloy_diamond_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Diamond-Void Pyrite Alloy | `mvtink_alloy_diamond_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Diamond-Void Titanium Alloy | `mvtink_alloy_diamond_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Diamond-Voidstone Alloy | `mvtink_alloy_diamond_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Diamond-Compacted Volcanic Ash Alloy | `mvtink_alloy_diamond_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Diamond-Warped Emerald Alloy | `mvtink_alloy_diamond_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Diamond-Warped Quartz Alloy | `mvtink_alloy_diamond_warped_quartz` | Void, Primal, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Diamond-Pure Weeping Shard Alloy | `mvtink_alloy_diamond_weeping_shard` | Infernal, Primal, Radiant |
| Witherite (`mvtink_witherite`) | Diamond-Witherite Alloy | `mvtink_alloy_diamond_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Diamond-Zero-Point Shard Alloy | `mvtink_alloy_diamond_zero_point` | Void, Primal, Radiant |
| Zinc (`mvtink_zinc`) | Diamond-Zinc Alloy | `mvtink_alloy_diamond_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Diamond-Zircon Alloy | `mvtink_alloy_diamond_zircon` | Primal, Terrain, Radiant |

#### Emerald · `mvtink_emerald` · 74 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| End Crystal Shard (`mvtink_end_crystal_shard`) | Emerald-End Crystal Shard Alloy | `mvtink_alloy_emerald_end_crystal_shard` | Void, Primal, Radiant |
| Enderite (`mvtink_enderite`) | Emerald-Enderite Alloy | `mvtink_alloy_emerald_enderite` | Void, Primal, Tempered |
| Fire Opal (`mvtink_fire_opal`) | Emerald-Fire Opal Alloy | `mvtink_alloy_emerald_fire_opal` | Infernal, Primal, Radiant |
| Flint (`mvtink_flint`) | Emerald-Flint Alloy | `mvtink_alloy_emerald_flint` | Primal, Terrain, Radiant |
| Fluorite (`mvtink_fluorite`) | Emerald-Fluorite Alloy | `mvtink_alloy_emerald_fluorite` | Primal, Terrain, Radiant |
| Galena (`mvtink_galena`) | Emerald-Galena Alloy | `mvtink_alloy_emerald_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Emerald-Ghast Tear Shard Alloy | `mvtink_alloy_emerald_ghast_tear_shard` | Infernal, Primal, Radiant |
| Glowstone Gem (`mvtink_glowstone_gem`) | Emerald-Glowstone Gem Alloy | `mvtink_alloy_emerald_glowstone_gem` | Infernal, Primal, Radiant |
| Gold (`mvtink_gold`) | Emerald-Gold Alloy | `mvtink_alloy_emerald_gold` | Primal, Tempered, Radiant |
| Graphite (`mvtink_graphite`) | Emerald-Graphite Alloy | `mvtink_alloy_emerald_graphite` | Primal, Terrain, Radiant |
| Gravitite (`mvtink_gravitite`) | Emerald-Gravitite Alloy | `mvtink_alloy_emerald_gravitite` | Void, Primal, Tempered |
| Gypsum (`mvtink_gypsum`) | Emerald-Gypsum Alloy | `mvtink_alloy_emerald_gypsum` | Primal, Terrain, Radiant |
| Helliron (`mvtink_helliron`) | Emerald-Helliron Alloy | `mvtink_alloy_emerald_helliron` | Infernal, Primal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Emerald-Ignis Ferrum Alloy | `mvtink_alloy_emerald_ignis_ferrum` | Infernal, Primal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Emerald-Infernal Obsidian Alloy | `mvtink_alloy_emerald_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Emerald-Netherite-Infused Quartz Alloy | `mvtink_alloy_emerald_infused_quartz` | Infernal, Primal, Radiant |
| Iron (`mvtink_iron`) | Emerald-Iron Alloy | `mvtink_alloy_emerald_iron` | Primal, Tempered, Radiant |
| Jade (`mvtink_jade`) | Emerald-Jade Alloy | `mvtink_alloy_emerald_jade` | Primal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Emerald-Kaolinite Alloy | `mvtink_alloy_emerald_kaolinite` | Primal, Terrain, Radiant |
| Lapis Lazuli (`mvtink_lapis`) | Emerald-Lapis Lazuli Alloy | `mvtink_alloy_emerald_lapis` | Primal, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Emerald-Lapis Matrix Alloy | `mvtink_alloy_emerald_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Emerald-Magma Brimstone Alloy | `mvtink_alloy_emerald_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Emerald-Magmacite Alloy | `mvtink_alloy_emerald_magmacite` | Infernal, Primal, Radiant |
| Magnetite (`mvtink_magnetite`) | Emerald-Magnetite Alloy | `mvtink_alloy_emerald_magnetite` | Primal, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Emerald-Malachite Alloy | `mvtink_alloy_emerald_malachite` | Primal, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Emerald-Nebulite Alloy | `mvtink_alloy_emerald_nebulite` | Void, Primal, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Emerald-Nether Bismuth Alloy | `mvtink_alloy_emerald_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Emerald-Nether Tungsten Alloy | `mvtink_alloy_emerald_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Emerald-Netherite Scrap Shard Alloy | `mvtink_alloy_emerald_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Emerald-Nickel Alloy | `mvtink_alloy_emerald_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Emerald-Null-Shard Alloy | `mvtink_alloy_emerald_null_shard` | Void, Primal, Radiant |
| Obsidian (`mvtink_obsidian`) | Emerald-Obsidian Alloy | `mvtink_alloy_emerald_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Emerald-Obsidianite Alloy | `mvtink_alloy_emerald_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Emerald-Opal Alloy | `mvtink_alloy_emerald_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Emerald-Ender Pearl Core Alloy | `mvtink_alloy_emerald_pearl_core` | Void, Primal, Radiant |
| Phantomite (`mvtink_phantomite`) | Emerald-Phantomite Alloy | `mvtink_alloy_emerald_phantomite` | Void, Primal, Radiant |
| Platinum (`mvtink_platinum`) | Emerald-Platinum Alloy | `mvtink_alloy_emerald_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Emerald-Prismarine Alloy | `mvtink_alloy_emerald_prismarine` | Primal, Radiant, Resonant |
| Pyrite (`mvtink_pyrite`) | Emerald-Pyrite Alloy | `mvtink_alloy_emerald_pyrite` | Primal, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Emerald-Pyrophore Alloy | `mvtink_alloy_emerald_pyrophore` | Infernal, Primal, Radiant |
| Nether Quartz (`mvtink_quartz`) | Emerald-Nether Quartz Alloy | `mvtink_alloy_emerald_quartz` | Primal, Radiant, Resonant |
| Redstone (`mvtink_redstone`) | Emerald-Redstone Alloy | `mvtink_alloy_emerald_redstone` | Primal, Radiant, Volatile |
| Resonite (`mvtink_resonite`) | Emerald-Resonite Alloy | `mvtink_alloy_emerald_resonite` | Void, Primal, Radiant |
| Ruby (`mvtink_ruby`) | Emerald-Ruby Alloy | `mvtink_alloy_emerald_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Emerald-Sanguinite Alloy | `mvtink_alloy_emerald_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Emerald-Sapphire Alloy | `mvtink_alloy_emerald_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Emerald-Shadowgem Alloy | `mvtink_alloy_emerald_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Emerald-Shulkerite Alloy | `mvtink_alloy_emerald_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Emerald-Silver Alloy | `mvtink_alloy_emerald_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Emerald-Singularite Alloy | `mvtink_alloy_emerald_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Emerald-Soul Glass Crystal Alloy | `mvtink_alloy_emerald_soulsand_crystal` | Infernal, Primal, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Emerald-Spatial Platinum Alloy | `mvtink_alloy_emerald_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Emerald-Starlight Silver Alloy | `mvtink_alloy_emerald_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Emerald-Steel Alloy | `mvtink_alloy_emerald_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Emerald-Stibnite Alloy | `mvtink_alloy_emerald_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Emerald-Sulfur Alloy | `mvtink_alloy_emerald_sulfur` | Infernal, Primal, Radiant |
| Talc (`mvtink_talc`) | Emerald-Talc Alloy | `mvtink_alloy_emerald_talc` | Primal, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Emerald-Tesseract Crystal Alloy | `mvtink_alloy_emerald_tesseract_crystal` | Void, Primal, Radiant |
| Tin (`mvtink_tin`) | Emerald-Tin Alloy | `mvtink_alloy_emerald_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Emerald-Titanium Alloy | `mvtink_alloy_emerald_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Emerald-Topaz Alloy | `mvtink_alloy_emerald_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Emerald-Tourmaline Alloy | `mvtink_alloy_emerald_tourmaline` | Primal, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Emerald-Tungsten Alloy | `mvtink_alloy_emerald_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Emerald-Void Pyrite Alloy | `mvtink_alloy_emerald_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Emerald-Void Titanium Alloy | `mvtink_alloy_emerald_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Emerald-Voidstone Alloy | `mvtink_alloy_emerald_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Emerald-Compacted Volcanic Ash Alloy | `mvtink_alloy_emerald_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Emerald-Warped Emerald Alloy | `mvtink_alloy_emerald_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Emerald-Warped Quartz Alloy | `mvtink_alloy_emerald_warped_quartz` | Void, Primal, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Emerald-Pure Weeping Shard Alloy | `mvtink_alloy_emerald_weeping_shard` | Infernal, Primal, Radiant |
| Witherite (`mvtink_witherite`) | Emerald-Witherite Alloy | `mvtink_alloy_emerald_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Emerald-Zero-Point Shard Alloy | `mvtink_alloy_emerald_zero_point` | Void, Primal, Radiant |
| Zinc (`mvtink_zinc`) | Emerald-Zinc Alloy | `mvtink_alloy_emerald_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Emerald-Zircon Alloy | `mvtink_alloy_emerald_zircon` | Primal, Terrain, Radiant |

#### Flint · `mvtink_flint` · 70 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Fluorite (`mvtink_fluorite`) | Flint-Fluorite Alloy | `mvtink_alloy_flint_fluorite` | Primal, Terrain, Resonant |
| Galena (`mvtink_galena`) | Flint-Galena Alloy | `mvtink_alloy_flint_galena` | Primal, Terrain, Tempered |
| Ghast Tear Shard (`mvtink_ghast_tear_shard`) | Flint-Ghast Tear Shard Alloy | `mvtink_alloy_flint_ghast_tear_shard` | Infernal, Primal, Terrain |
| Glowstone Gem (`mvtink_glowstone_gem`) | Flint-Glowstone Gem Alloy | `mvtink_alloy_flint_glowstone_gem` | Infernal, Primal, Terrain |
| Gold (`mvtink_gold`) | Flint-Gold Alloy | `mvtink_alloy_flint_gold` | Primal, Terrain, Tempered |
| Graphite (`mvtink_graphite`) | Flint-Graphite Alloy | `mvtink_alloy_flint_graphite` | Primal, Terrain |
| Gravitite (`mvtink_gravitite`) | Flint-Gravitite Alloy | `mvtink_alloy_flint_gravitite` | Void, Primal, Terrain |
| Gypsum (`mvtink_gypsum`) | Flint-Gypsum Alloy | `mvtink_alloy_flint_gypsum` | Primal, Terrain |
| Helliron (`mvtink_helliron`) | Flint-Helliron Alloy | `mvtink_alloy_flint_helliron` | Infernal, Primal, Terrain |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Flint-Ignis Ferrum Alloy | `mvtink_alloy_flint_ignis_ferrum` | Infernal, Primal, Terrain |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Flint-Infernal Obsidian Alloy | `mvtink_alloy_flint_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Flint-Netherite-Infused Quartz Alloy | `mvtink_alloy_flint_infused_quartz` | Infernal, Primal, Terrain |
| Iron (`mvtink_iron`) | Flint-Iron Alloy | `mvtink_alloy_flint_iron` | Primal, Terrain, Tempered |
| Jade (`mvtink_jade`) | Flint-Jade Alloy | `mvtink_alloy_flint_jade` | Primal, Terrain, Radiant |
| Kaolinite (`mvtink_kaolinite`) | Flint-Kaolinite Alloy | `mvtink_alloy_flint_kaolinite` | Primal, Terrain |
| Lapis Lazuli (`mvtink_lapis`) | Flint-Lapis Lazuli Alloy | `mvtink_alloy_flint_lapis` | Primal, Terrain, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Flint-Lapis Matrix Alloy | `mvtink_alloy_flint_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Flint-Magma Brimstone Alloy | `mvtink_alloy_flint_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Flint-Magmacite Alloy | `mvtink_alloy_flint_magmacite` | Infernal, Primal, Terrain |
| Magnetite (`mvtink_magnetite`) | Flint-Magnetite Alloy | `mvtink_alloy_flint_magnetite` | Primal, Terrain |
| Malachite (`mvtink_malachite`) | Flint-Malachite Alloy | `mvtink_alloy_flint_malachite` | Primal, Terrain |
| Nebulite (`mvtink_nebulite`) | Flint-Nebulite Alloy | `mvtink_alloy_flint_nebulite` | Void, Primal, Terrain |
| Nether Bismuth (`mvtink_nether_bismuth`) | Flint-Nether Bismuth Alloy | `mvtink_alloy_flint_nether_bismuth` | Infernal, Primal, Terrain |
| Nether Tungsten (`mvtink_nether_tungsten`) | Flint-Nether Tungsten Alloy | `mvtink_alloy_flint_nether_tungsten` | Infernal, Primal, Terrain |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Flint-Netherite Scrap Shard Alloy | `mvtink_alloy_flint_netherite_shard` | Infernal, Primal, Terrain |
| Nickel (`mvtink_nickel`) | Flint-Nickel Alloy | `mvtink_alloy_flint_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Flint-Null-Shard Alloy | `mvtink_alloy_flint_null_shard` | Void, Primal, Terrain |
| Obsidian (`mvtink_obsidian`) | Flint-Obsidian Alloy | `mvtink_alloy_flint_obsidian` | Primal, Terrain, Brutal |
| Obsidianite (`mvtink_obsidianite`) | Flint-Obsidianite Alloy | `mvtink_alloy_flint_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Flint-Opal Alloy | `mvtink_alloy_flint_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Flint-Ender Pearl Core Alloy | `mvtink_alloy_flint_pearl_core` | Void, Primal, Terrain |
| Phantomite (`mvtink_phantomite`) | Flint-Phantomite Alloy | `mvtink_alloy_flint_phantomite` | Void, Primal, Terrain |
| Platinum (`mvtink_platinum`) | Flint-Platinum Alloy | `mvtink_alloy_flint_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Flint-Prismarine Alloy | `mvtink_alloy_flint_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Flint-Pyrite Alloy | `mvtink_alloy_flint_pyrite` | Primal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Flint-Pyrophore Alloy | `mvtink_alloy_flint_pyrophore` | Infernal, Primal, Terrain |
| Nether Quartz (`mvtink_quartz`) | Flint-Nether Quartz Alloy | `mvtink_alloy_flint_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Flint-Redstone Alloy | `mvtink_alloy_flint_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Flint-Resonite Alloy | `mvtink_alloy_flint_resonite` | Void, Primal, Terrain |
| Ruby (`mvtink_ruby`) | Flint-Ruby Alloy | `mvtink_alloy_flint_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Flint-Sanguinite Alloy | `mvtink_alloy_flint_sanguinite` | Infernal, Primal, Terrain |
| Sapphire (`mvtink_sapphire`) | Flint-Sapphire Alloy | `mvtink_alloy_flint_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Flint-Shadowgem Alloy | `mvtink_alloy_flint_shadowgem` | Void, Primal, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Flint-Shulkerite Alloy | `mvtink_alloy_flint_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Flint-Silver Alloy | `mvtink_alloy_flint_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Flint-Singularite Alloy | `mvtink_alloy_flint_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Flint-Soul Glass Crystal Alloy | `mvtink_alloy_flint_soulsand_crystal` | Infernal, Primal, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Flint-Spatial Platinum Alloy | `mvtink_alloy_flint_spatial_platinum` | Void, Primal, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Flint-Starlight Silver Alloy | `mvtink_alloy_flint_starlight_silver` | Void, Primal, Terrain |
| Steel (`mvtink_steel`) | Flint-Steel Alloy | `mvtink_alloy_flint_steel` | Infernal, Primal, Terrain |
| Stibnite (`mvtink_stibnite`) | Flint-Stibnite Alloy | `mvtink_alloy_flint_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Flint-Sulfur Alloy | `mvtink_alloy_flint_sulfur` | Infernal, Primal, Terrain |
| Talc (`mvtink_talc`) | Flint-Talc Alloy | `mvtink_alloy_flint_talc` | Primal, Terrain |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Flint-Tesseract Crystal Alloy | `mvtink_alloy_flint_tesseract_crystal` | Void, Primal, Terrain |
| Tin (`mvtink_tin`) | Flint-Tin Alloy | `mvtink_alloy_flint_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Flint-Titanium Alloy | `mvtink_alloy_flint_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Flint-Topaz Alloy | `mvtink_alloy_flint_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Flint-Tourmaline Alloy | `mvtink_alloy_flint_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Flint-Tungsten Alloy | `mvtink_alloy_flint_tungsten` | Infernal, Primal, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Flint-Void Pyrite Alloy | `mvtink_alloy_flint_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Flint-Void Titanium Alloy | `mvtink_alloy_flint_void_titanium` | Void, Primal, Terrain |
| Voidstone (`mvtink_voidstone`) | Flint-Voidstone Alloy | `mvtink_alloy_flint_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Flint-Compacted Volcanic Ash Alloy | `mvtink_alloy_flint_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Flint-Warped Emerald Alloy | `mvtink_alloy_flint_warped_emerald` | Infernal, Primal, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Flint-Warped Quartz Alloy | `mvtink_alloy_flint_warped_quartz` | Void, Primal, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Flint-Pure Weeping Shard Alloy | `mvtink_alloy_flint_weeping_shard` | Infernal, Primal, Terrain |
| Witherite (`mvtink_witherite`) | Flint-Witherite Alloy | `mvtink_alloy_flint_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Flint-Zero-Point Shard Alloy | `mvtink_alloy_flint_zero_point` | Void, Primal, Terrain |
| Zinc (`mvtink_zinc`) | Flint-Zinc Alloy | `mvtink_alloy_flint_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Flint-Zircon Alloy | `mvtink_alloy_flint_zircon` | Primal, Terrain, Resonant |

#### Gold · `mvtink_gold` · 65 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Graphite (`mvtink_graphite`) | Gold-Graphite Alloy | `mvtink_alloy_gold_graphite` | Primal, Terrain, Tempered |
| Gravitite (`mvtink_gravitite`) | Gold-Gravitite Alloy | `mvtink_alloy_gold_gravitite` | Void, Primal, Tempered |
| Gypsum (`mvtink_gypsum`) | Gold-Gypsum Alloy | `mvtink_alloy_gold_gypsum` | Primal, Terrain, Tempered |
| Helliron (`mvtink_helliron`) | Gold-Helliron Alloy | `mvtink_alloy_gold_helliron` | Infernal, Primal, Tempered |
| Ignis Ferrum (`mvtink_ignis_ferrum`) | Gold-Ignis Ferrum Alloy | `mvtink_alloy_gold_ignis_ferrum` | Infernal, Primal, Tempered |
| Infernal Obsidian (`mvtink_infernal_obsidian`) | Gold-Infernal Obsidian Alloy | `mvtink_alloy_gold_infernal_obsidian` | Infernal, Primal, Terrain |
| Netherite-Infused Quartz (`mvtink_infused_quartz`) | Gold-Netherite-Infused Quartz Alloy | `mvtink_alloy_gold_infused_quartz` | Infernal, Primal, Tempered |
| Iron (`mvtink_iron`) | Gold-Iron Alloy | `mvtink_alloy_gold_iron` | Primal, Tempered, Swift |
| Jade (`mvtink_jade`) | Gold-Jade Alloy | `mvtink_alloy_gold_jade` | Primal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Gold-Kaolinite Alloy | `mvtink_alloy_gold_kaolinite` | Primal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Gold-Lapis Lazuli Alloy | `mvtink_alloy_gold_lapis` | Primal, Tempered, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Gold-Lapis Matrix Alloy | `mvtink_alloy_gold_lapis_matrix` | Primal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Gold-Magma Brimstone Alloy | `mvtink_alloy_gold_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Gold-Magmacite Alloy | `mvtink_alloy_gold_magmacite` | Infernal, Primal, Tempered |
| Magnetite (`mvtink_magnetite`) | Gold-Magnetite Alloy | `mvtink_alloy_gold_magnetite` | Primal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Gold-Malachite Alloy | `mvtink_alloy_gold_malachite` | Primal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Gold-Nebulite Alloy | `mvtink_alloy_gold_nebulite` | Void, Primal, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Gold-Nether Bismuth Alloy | `mvtink_alloy_gold_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Gold-Nether Tungsten Alloy | `mvtink_alloy_gold_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Gold-Netherite Scrap Shard Alloy | `mvtink_alloy_gold_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Gold-Nickel Alloy | `mvtink_alloy_gold_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Gold-Null-Shard Alloy | `mvtink_alloy_gold_null_shard` | Void, Primal, Tempered |
| Obsidian (`mvtink_obsidian`) | Gold-Obsidian Alloy | `mvtink_alloy_gold_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Gold-Obsidianite Alloy | `mvtink_alloy_gold_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Gold-Opal Alloy | `mvtink_alloy_gold_opal` | Primal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Gold-Ender Pearl Core Alloy | `mvtink_alloy_gold_pearl_core` | Void, Primal, Tempered |
| Phantomite (`mvtink_phantomite`) | Gold-Phantomite Alloy | `mvtink_alloy_gold_phantomite` | Void, Primal, Tempered |
| Platinum (`mvtink_platinum`) | Gold-Platinum Alloy | `mvtink_alloy_gold_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Gold-Prismarine Alloy | `mvtink_alloy_gold_prismarine` | Primal, Tempered, Resonant |
| Pyrite (`mvtink_pyrite`) | Gold-Pyrite Alloy | `mvtink_alloy_gold_pyrite` | Primal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Gold-Pyrophore Alloy | `mvtink_alloy_gold_pyrophore` | Infernal, Primal, Tempered |
| Nether Quartz (`mvtink_quartz`) | Gold-Nether Quartz Alloy | `mvtink_alloy_gold_quartz` | Primal, Tempered, Resonant |
| Redstone (`mvtink_redstone`) | Gold-Redstone Alloy | `mvtink_alloy_gold_redstone` | Primal, Tempered, Volatile |
| Resonite (`mvtink_resonite`) | Gold-Resonite Alloy | `mvtink_alloy_gold_resonite` | Void, Primal, Tempered |
| Ruby (`mvtink_ruby`) | Gold-Ruby Alloy | `mvtink_alloy_gold_ruby` | Primal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) ★ | Sanguine Gold | `mvtink_sanguine_gold` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Gold-Sapphire Alloy | `mvtink_alloy_gold_sapphire` | Primal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Gold-Shadowgem Alloy | `mvtink_alloy_gold_shadowgem` | Void, Primal, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Gold-Shulkerite Alloy | `mvtink_alloy_gold_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) ★ | Electrum | `mvtink_electrum` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Gold-Singularite Alloy | `mvtink_alloy_gold_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Gold-Soul Glass Crystal Alloy | `mvtink_alloy_gold_soulsand_crystal` | Infernal, Primal, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Gold-Spatial Platinum Alloy | `mvtink_alloy_gold_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Gold-Starlight Silver Alloy | `mvtink_alloy_gold_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Gold-Steel Alloy | `mvtink_alloy_gold_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Gold-Stibnite Alloy | `mvtink_alloy_gold_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Gold-Sulfur Alloy | `mvtink_alloy_gold_sulfur` | Infernal, Primal, Tempered |
| Talc (`mvtink_talc`) | Gold-Talc Alloy | `mvtink_alloy_gold_talc` | Primal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Gold-Tesseract Crystal Alloy | `mvtink_alloy_gold_tesseract_crystal` | Void, Primal, Tempered |
| Tin (`mvtink_tin`) | Gold-Tin Alloy | `mvtink_alloy_gold_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Gold-Titanium Alloy | `mvtink_alloy_gold_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Gold-Topaz Alloy | `mvtink_alloy_gold_topaz` | Primal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Gold-Tourmaline Alloy | `mvtink_alloy_gold_tourmaline` | Primal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Gold-Tungsten Alloy | `mvtink_alloy_gold_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Gold-Void Pyrite Alloy | `mvtink_alloy_gold_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Gold-Void Titanium Alloy | `mvtink_alloy_gold_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Gold-Voidstone Alloy | `mvtink_alloy_gold_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Gold-Compacted Volcanic Ash Alloy | `mvtink_alloy_gold_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Gold-Warped Emerald Alloy | `mvtink_alloy_gold_warped_emerald` | Infernal, Primal, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Gold-Warped Quartz Alloy | `mvtink_alloy_gold_warped_quartz` | Void, Primal, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Gold-Pure Weeping Shard Alloy | `mvtink_alloy_gold_weeping_shard` | Infernal, Primal, Tempered |
| Witherite (`mvtink_witherite`) | Gold-Witherite Alloy | `mvtink_alloy_gold_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Gold-Zero-Point Shard Alloy | `mvtink_alloy_gold_zero_point` | Void, Primal, Tempered |
| Zinc (`mvtink_zinc`) | Gold-Zinc Alloy | `mvtink_alloy_gold_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Gold-Zircon Alloy | `mvtink_alloy_gold_zircon` | Primal, Terrain, Tempered |

#### Iron · `mvtink_iron` · 57 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Jade (`mvtink_jade`) | Iron-Jade Alloy | `mvtink_alloy_iron_jade` | Primal, Terrain, Tempered |
| Kaolinite (`mvtink_kaolinite`) | Iron-Kaolinite Alloy | `mvtink_alloy_iron_kaolinite` | Primal, Terrain, Tempered |
| Lapis Lazuli (`mvtink_lapis`) | Iron-Lapis Lazuli Alloy | `mvtink_alloy_iron_lapis` | Primal, Tempered, Radiant |
| Lapis Matrix (`mvtink_lapis_matrix`) | Iron-Lapis Matrix Alloy | `mvtink_alloy_iron_lapis_matrix` | Primal, Terrain, Tempered |
| Magma Brimstone (`mvtink_magma_brimstone`) | Iron-Magma Brimstone Alloy | `mvtink_alloy_iron_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Iron-Magmacite Alloy | `mvtink_alloy_iron_magmacite` | Infernal, Primal, Tempered |
| Magnetite (`mvtink_magnetite`) | Iron-Magnetite Alloy | `mvtink_alloy_iron_magnetite` | Primal, Terrain, Tempered |
| Malachite (`mvtink_malachite`) | Iron-Malachite Alloy | `mvtink_alloy_iron_malachite` | Primal, Terrain, Tempered |
| Nebulite (`mvtink_nebulite`) | Iron-Nebulite Alloy | `mvtink_alloy_iron_nebulite` | Void, Primal, Tempered |
| Nether Bismuth (`mvtink_nether_bismuth`) | Iron-Nether Bismuth Alloy | `mvtink_alloy_iron_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Iron-Nether Tungsten Alloy | `mvtink_alloy_iron_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Iron-Netherite Scrap Shard Alloy | `mvtink_alloy_iron_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) ★ | Invar | `mvtink_invar` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Iron-Null-Shard Alloy | `mvtink_alloy_iron_null_shard` | Void, Primal, Tempered |
| Obsidian (`mvtink_obsidian`) | Iron-Obsidian Alloy | `mvtink_alloy_iron_obsidian` | Primal, Terrain, Tempered |
| Obsidianite (`mvtink_obsidianite`) | Iron-Obsidianite Alloy | `mvtink_alloy_iron_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Iron-Opal Alloy | `mvtink_alloy_iron_opal` | Primal, Terrain, Tempered |
| Ender Pearl Core (`mvtink_pearl_core`) | Iron-Ender Pearl Core Alloy | `mvtink_alloy_iron_pearl_core` | Void, Primal, Tempered |
| Phantomite (`mvtink_phantomite`) | Iron-Phantomite Alloy | `mvtink_alloy_iron_phantomite` | Void, Primal, Tempered |
| Platinum (`mvtink_platinum`) | Iron-Platinum Alloy | `mvtink_alloy_iron_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Iron-Prismarine Alloy | `mvtink_alloy_iron_prismarine` | Primal, Tempered, Resonant |
| Pyrite (`mvtink_pyrite`) | Iron-Pyrite Alloy | `mvtink_alloy_iron_pyrite` | Primal, Terrain, Tempered |
| Pyrophore (`mvtink_pyrophore`) | Iron-Pyrophore Alloy | `mvtink_alloy_iron_pyrophore` | Infernal, Primal, Tempered |
| Nether Quartz (`mvtink_quartz`) | Iron-Nether Quartz Alloy | `mvtink_alloy_iron_quartz` | Primal, Tempered, Resonant |
| Redstone (`mvtink_redstone`) | Iron-Redstone Alloy | `mvtink_alloy_iron_redstone` | Primal, Tempered, Volatile |
| Resonite (`mvtink_resonite`) | Iron-Resonite Alloy | `mvtink_alloy_iron_resonite` | Void, Primal, Tempered |
| Ruby (`mvtink_ruby`) | Iron-Ruby Alloy | `mvtink_alloy_iron_ruby` | Primal, Terrain, Tempered |
| Sanguinite (`mvtink_sanguinite`) | Iron-Sanguinite Alloy | `mvtink_alloy_iron_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Iron-Sapphire Alloy | `mvtink_alloy_iron_sapphire` | Primal, Terrain, Tempered |
| Shadowgem (`mvtink_shadowgem`) | Iron-Shadowgem Alloy | `mvtink_alloy_iron_shadowgem` | Void, Primal, Tempered |
| Shulkerite (`mvtink_shulkerite`) | Iron-Shulkerite Alloy | `mvtink_alloy_iron_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Iron-Silver Alloy | `mvtink_alloy_iron_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Iron-Singularite Alloy | `mvtink_alloy_iron_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Iron-Soul Glass Crystal Alloy | `mvtink_alloy_iron_soulsand_crystal` | Infernal, Primal, Tempered |
| Spatial Platinum (`mvtink_spatial_platinum`) | Iron-Spatial Platinum Alloy | `mvtink_alloy_iron_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Iron-Starlight Silver Alloy | `mvtink_alloy_iron_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Iron-Steel Alloy | `mvtink_alloy_iron_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Iron-Stibnite Alloy | `mvtink_alloy_iron_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Iron-Sulfur Alloy | `mvtink_alloy_iron_sulfur` | Infernal, Primal, Tempered |
| Talc (`mvtink_talc`) | Iron-Talc Alloy | `mvtink_alloy_iron_talc` | Primal, Terrain, Tempered |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Iron-Tesseract Crystal Alloy | `mvtink_alloy_iron_tesseract_crystal` | Void, Primal, Tempered |
| Tin (`mvtink_tin`) | Iron-Tin Alloy | `mvtink_alloy_iron_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Iron-Titanium Alloy | `mvtink_alloy_iron_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Iron-Topaz Alloy | `mvtink_alloy_iron_topaz` | Primal, Terrain, Tempered |
| Tourmaline (`mvtink_tourmaline`) | Iron-Tourmaline Alloy | `mvtink_alloy_iron_tourmaline` | Primal, Terrain, Tempered |
| Tungsten (`mvtink_tungsten`) | Iron-Tungsten Alloy | `mvtink_alloy_iron_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Iron-Void Pyrite Alloy | `mvtink_alloy_iron_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Iron-Void Titanium Alloy | `mvtink_alloy_iron_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Iron-Voidstone Alloy | `mvtink_alloy_iron_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Iron-Compacted Volcanic Ash Alloy | `mvtink_alloy_iron_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Iron-Warped Emerald Alloy | `mvtink_alloy_iron_warped_emerald` | Infernal, Primal, Tempered |
| Warped Quartz (`mvtink_warped_quartz`) | Iron-Warped Quartz Alloy | `mvtink_alloy_iron_warped_quartz` | Void, Primal, Tempered |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Iron-Pure Weeping Shard Alloy | `mvtink_alloy_iron_weeping_shard` | Infernal, Primal, Tempered |
| Witherite (`mvtink_witherite`) | Iron-Witherite Alloy | `mvtink_alloy_iron_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Iron-Zero-Point Shard Alloy | `mvtink_alloy_iron_zero_point` | Void, Primal, Tempered |
| Zinc (`mvtink_zinc`) | Iron-Zinc Alloy | `mvtink_alloy_iron_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Iron-Zircon Alloy | `mvtink_alloy_iron_zircon` | Primal, Terrain, Tempered |

#### Lapis Lazuli · `mvtink_lapis` · 54 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Lapis Matrix (`mvtink_lapis_matrix`) | Lapis Lazuli-Lapis Matrix Alloy | `mvtink_alloy_lapis_lapis_matrix` | Primal, Terrain, Radiant |
| Magma Brimstone (`mvtink_magma_brimstone`) | Lapis Lazuli-Magma Brimstone Alloy | `mvtink_alloy_lapis_magma_brimstone` | Infernal, Primal, Terrain |
| Magmacite (`mvtink_magmacite`) | Lapis Lazuli-Magmacite Alloy | `mvtink_alloy_lapis_magmacite` | Infernal, Primal, Radiant |
| Magnetite (`mvtink_magnetite`) | Lapis Lazuli-Magnetite Alloy | `mvtink_alloy_lapis_magnetite` | Primal, Terrain, Radiant |
| Malachite (`mvtink_malachite`) | Lapis Lazuli-Malachite Alloy | `mvtink_alloy_lapis_malachite` | Primal, Terrain, Radiant |
| Nebulite (`mvtink_nebulite`) | Lapis Lazuli-Nebulite Alloy | `mvtink_alloy_lapis_nebulite` | Void, Primal, Radiant |
| Nether Bismuth (`mvtink_nether_bismuth`) | Lapis Lazuli-Nether Bismuth Alloy | `mvtink_alloy_lapis_nether_bismuth` | Infernal, Primal, Tempered |
| Nether Tungsten (`mvtink_nether_tungsten`) | Lapis Lazuli-Nether Tungsten Alloy | `mvtink_alloy_lapis_nether_tungsten` | Infernal, Primal, Tempered |
| Netherite Scrap Shard (`mvtink_netherite_shard`) | Lapis Lazuli-Netherite Scrap Shard Alloy | `mvtink_alloy_lapis_netherite_shard` | Infernal, Primal, Tempered |
| Nickel (`mvtink_nickel`) | Lapis Lazuli-Nickel Alloy | `mvtink_alloy_lapis_nickel` | Primal, Terrain, Tempered |
| Null-Shard (`mvtink_null_shard`) | Lapis Lazuli-Null-Shard Alloy | `mvtink_alloy_lapis_null_shard` | Void, Primal, Radiant |
| Obsidian (`mvtink_obsidian`) | Lapis Lazuli-Obsidian Alloy | `mvtink_alloy_lapis_obsidian` | Primal, Terrain, Radiant |
| Obsidianite (`mvtink_obsidianite`) | Lapis Lazuli-Obsidianite Alloy | `mvtink_alloy_lapis_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Lapis Lazuli-Opal Alloy | `mvtink_alloy_lapis_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Lapis Lazuli-Ender Pearl Core Alloy | `mvtink_alloy_lapis_pearl_core` | Void, Primal, Radiant |
| Phantomite (`mvtink_phantomite`) | Lapis Lazuli-Phantomite Alloy | `mvtink_alloy_lapis_phantomite` | Void, Primal, Radiant |
| Platinum (`mvtink_platinum`) | Lapis Lazuli-Platinum Alloy | `mvtink_alloy_lapis_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Lapis Lazuli-Prismarine Alloy | `mvtink_alloy_lapis_prismarine` | Primal, Radiant, Resonant |
| Pyrite (`mvtink_pyrite`) | Lapis Lazuli-Pyrite Alloy | `mvtink_alloy_lapis_pyrite` | Primal, Terrain, Radiant |
| Pyrophore (`mvtink_pyrophore`) | Lapis Lazuli-Pyrophore Alloy | `mvtink_alloy_lapis_pyrophore` | Infernal, Primal, Radiant |
| Nether Quartz (`mvtink_quartz`) | Lapis Lazuli-Nether Quartz Alloy | `mvtink_alloy_lapis_quartz` | Primal, Radiant, Resonant |
| Redstone (`mvtink_redstone`) | Lapis Lazuli-Redstone Alloy | `mvtink_alloy_lapis_redstone` | Primal, Radiant, Volatile |
| Resonite (`mvtink_resonite`) | Lapis Lazuli-Resonite Alloy | `mvtink_alloy_lapis_resonite` | Void, Primal, Radiant |
| Ruby (`mvtink_ruby`) | Lapis Lazuli-Ruby Alloy | `mvtink_alloy_lapis_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Lapis Lazuli-Sanguinite Alloy | `mvtink_alloy_lapis_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Lapis Lazuli-Sapphire Alloy | `mvtink_alloy_lapis_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Lapis Lazuli-Shadowgem Alloy | `mvtink_alloy_lapis_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Lapis Lazuli-Shulkerite Alloy | `mvtink_alloy_lapis_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Lapis Lazuli-Silver Alloy | `mvtink_alloy_lapis_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Lapis Lazuli-Singularite Alloy | `mvtink_alloy_lapis_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Lapis Lazuli-Soul Glass Crystal Alloy | `mvtink_alloy_lapis_soulsand_crystal` | Infernal, Primal, Radiant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Lapis Lazuli-Spatial Platinum Alloy | `mvtink_alloy_lapis_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Lapis Lazuli-Starlight Silver Alloy | `mvtink_alloy_lapis_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Lapis Lazuli-Steel Alloy | `mvtink_alloy_lapis_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Lapis Lazuli-Stibnite Alloy | `mvtink_alloy_lapis_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Lapis Lazuli-Sulfur Alloy | `mvtink_alloy_lapis_sulfur` | Infernal, Primal, Radiant |
| Talc (`mvtink_talc`) | Lapis Lazuli-Talc Alloy | `mvtink_alloy_lapis_talc` | Primal, Terrain, Radiant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Lapis Lazuli-Tesseract Crystal Alloy | `mvtink_alloy_lapis_tesseract_crystal` | Void, Primal, Radiant |
| Tin (`mvtink_tin`) | Lapis Lazuli-Tin Alloy | `mvtink_alloy_lapis_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Lapis Lazuli-Titanium Alloy | `mvtink_alloy_lapis_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Lapis Lazuli-Topaz Alloy | `mvtink_alloy_lapis_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Lapis Lazuli-Tourmaline Alloy | `mvtink_alloy_lapis_tourmaline` | Primal, Terrain, Radiant |
| Tungsten (`mvtink_tungsten`) | Lapis Lazuli-Tungsten Alloy | `mvtink_alloy_lapis_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Lapis Lazuli-Void Pyrite Alloy | `mvtink_alloy_lapis_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Lapis Lazuli-Void Titanium Alloy | `mvtink_alloy_lapis_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Lapis Lazuli-Voidstone Alloy | `mvtink_alloy_lapis_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Lapis Lazuli-Compacted Volcanic Ash Alloy | `mvtink_alloy_lapis_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Lapis Lazuli-Warped Emerald Alloy | `mvtink_alloy_lapis_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Lapis Lazuli-Warped Quartz Alloy | `mvtink_alloy_lapis_warped_quartz` | Void, Primal, Radiant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Lapis Lazuli-Pure Weeping Shard Alloy | `mvtink_alloy_lapis_weeping_shard` | Infernal, Primal, Radiant |
| Witherite (`mvtink_witherite`) | Lapis Lazuli-Witherite Alloy | `mvtink_alloy_lapis_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Lapis Lazuli-Zero-Point Shard Alloy | `mvtink_alloy_lapis_zero_point` | Void, Primal, Radiant |
| Zinc (`mvtink_zinc`) | Lapis Lazuli-Zinc Alloy | `mvtink_alloy_lapis_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Lapis Lazuli-Zircon Alloy | `mvtink_alloy_lapis_zircon` | Primal, Terrain, Radiant |

#### Obsidian · `mvtink_obsidian` · 42 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Obsidianite (`mvtink_obsidianite`) | Obsidian-Obsidianite Alloy | `mvtink_alloy_obsidian_obsidianite` | Infernal, Primal, Terrain |
| Opal (`mvtink_opal`) | Obsidian-Opal Alloy | `mvtink_alloy_obsidian_opal` | Primal, Terrain, Radiant |
| Ender Pearl Core (`mvtink_pearl_core`) | Obsidian-Ender Pearl Core Alloy | `mvtink_alloy_obsidian_pearl_core` | Void, Primal, Terrain |
| Phantomite (`mvtink_phantomite`) | Obsidian-Phantomite Alloy | `mvtink_alloy_obsidian_phantomite` | Void, Primal, Terrain |
| Platinum (`mvtink_platinum`) | Obsidian-Platinum Alloy | `mvtink_alloy_obsidian_platinum` | Primal, Terrain, Tempered |
| Prismarine (`mvtink_prismarine`) | Obsidian-Prismarine Alloy | `mvtink_alloy_obsidian_prismarine` | Primal, Terrain, Resonant |
| Pyrite (`mvtink_pyrite`) | Obsidian-Pyrite Alloy | `mvtink_alloy_obsidian_pyrite` | Primal, Terrain, Swift |
| Pyrophore (`mvtink_pyrophore`) | Obsidian-Pyrophore Alloy | `mvtink_alloy_obsidian_pyrophore` | Infernal, Primal, Terrain |
| Nether Quartz (`mvtink_quartz`) | Obsidian-Nether Quartz Alloy | `mvtink_alloy_obsidian_quartz` | Primal, Terrain, Resonant |
| Redstone (`mvtink_redstone`) | Obsidian-Redstone Alloy | `mvtink_alloy_obsidian_redstone` | Primal, Terrain, Volatile |
| Resonite (`mvtink_resonite`) | Obsidian-Resonite Alloy | `mvtink_alloy_obsidian_resonite` | Void, Primal, Terrain |
| Ruby (`mvtink_ruby`) | Obsidian-Ruby Alloy | `mvtink_alloy_obsidian_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Obsidian-Sanguinite Alloy | `mvtink_alloy_obsidian_sanguinite` | Infernal, Primal, Terrain |
| Sapphire (`mvtink_sapphire`) | Obsidian-Sapphire Alloy | `mvtink_alloy_obsidian_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Obsidian-Shadowgem Alloy | `mvtink_alloy_obsidian_shadowgem` | Void, Primal, Terrain |
| Shulkerite (`mvtink_shulkerite`) | Obsidian-Shulkerite Alloy | `mvtink_alloy_obsidian_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Obsidian-Silver Alloy | `mvtink_alloy_obsidian_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Obsidian-Singularite Alloy | `mvtink_alloy_obsidian_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Obsidian-Soul Glass Crystal Alloy | `mvtink_alloy_obsidian_soulsand_crystal` | Infernal, Primal, Terrain |
| Spatial Platinum (`mvtink_spatial_platinum`) | Obsidian-Spatial Platinum Alloy | `mvtink_alloy_obsidian_spatial_platinum` | Void, Primal, Terrain |
| Starlight Silver (`mvtink_starlight_silver`) | Obsidian-Starlight Silver Alloy | `mvtink_alloy_obsidian_starlight_silver` | Void, Primal, Terrain |
| Steel (`mvtink_steel`) | Obsidian-Steel Alloy | `mvtink_alloy_obsidian_steel` | Infernal, Primal, Terrain |
| Stibnite (`mvtink_stibnite`) | Obsidian-Stibnite Alloy | `mvtink_alloy_obsidian_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Obsidian-Sulfur Alloy | `mvtink_alloy_obsidian_sulfur` | Infernal, Primal, Terrain |
| Talc (`mvtink_talc`) | Obsidian-Talc Alloy | `mvtink_alloy_obsidian_talc` | Primal, Terrain, Brutal |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Obsidian-Tesseract Crystal Alloy | `mvtink_alloy_obsidian_tesseract_crystal` | Void, Primal, Terrain |
| Tin (`mvtink_tin`) | Obsidian-Tin Alloy | `mvtink_alloy_obsidian_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Obsidian-Titanium Alloy | `mvtink_alloy_obsidian_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Obsidian-Topaz Alloy | `mvtink_alloy_obsidian_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Obsidian-Tourmaline Alloy | `mvtink_alloy_obsidian_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Obsidian-Tungsten Alloy | `mvtink_alloy_obsidian_tungsten` | Infernal, Primal, Terrain |
| Void Pyrite (`mvtink_void_pyrite`) | Obsidian-Void Pyrite Alloy | `mvtink_alloy_obsidian_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Obsidian-Void Titanium Alloy | `mvtink_alloy_obsidian_void_titanium` | Void, Primal, Terrain |
| Voidstone (`mvtink_voidstone`) | Obsidian-Voidstone Alloy | `mvtink_alloy_obsidian_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Obsidian-Compacted Volcanic Ash Alloy | `mvtink_alloy_obsidian_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Obsidian-Warped Emerald Alloy | `mvtink_alloy_obsidian_warped_emerald` | Infernal, Primal, Terrain |
| Warped Quartz (`mvtink_warped_quartz`) | Obsidian-Warped Quartz Alloy | `mvtink_alloy_obsidian_warped_quartz` | Void, Primal, Terrain |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Obsidian-Pure Weeping Shard Alloy | `mvtink_alloy_obsidian_weeping_shard` | Infernal, Primal, Terrain |
| Witherite (`mvtink_witherite`) | Obsidian-Witherite Alloy | `mvtink_alloy_obsidian_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Obsidian-Zero-Point Shard Alloy | `mvtink_alloy_obsidian_zero_point` | Void, Primal, Terrain |
| Zinc (`mvtink_zinc`) | Obsidian-Zinc Alloy | `mvtink_alloy_obsidian_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Obsidian-Zircon Alloy | `mvtink_alloy_obsidian_zircon` | Primal, Terrain, Resonant |

#### Prismarine · `mvtink_prismarine` · 36 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Pyrite (`mvtink_pyrite`) | Prismarine-Pyrite Alloy | `mvtink_alloy_prismarine_pyrite` | Primal, Terrain, Resonant |
| Pyrophore (`mvtink_pyrophore`) | Prismarine-Pyrophore Alloy | `mvtink_alloy_prismarine_pyrophore` | Infernal, Primal, Resonant |
| Nether Quartz (`mvtink_quartz`) | Prismarine-Nether Quartz Alloy | `mvtink_alloy_prismarine_quartz` | Primal, Resonant |
| Redstone (`mvtink_redstone`) | Prismarine-Redstone Alloy | `mvtink_alloy_prismarine_redstone` | Primal, Resonant, Volatile |
| Resonite (`mvtink_resonite`) | Prismarine-Resonite Alloy | `mvtink_alloy_prismarine_resonite` | Void, Primal, Resonant |
| Ruby (`mvtink_ruby`) | Prismarine-Ruby Alloy | `mvtink_alloy_prismarine_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Prismarine-Sanguinite Alloy | `mvtink_alloy_prismarine_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Prismarine-Sapphire Alloy | `mvtink_alloy_prismarine_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Prismarine-Shadowgem Alloy | `mvtink_alloy_prismarine_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Prismarine-Shulkerite Alloy | `mvtink_alloy_prismarine_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Prismarine-Silver Alloy | `mvtink_alloy_prismarine_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Prismarine-Singularite Alloy | `mvtink_alloy_prismarine_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Prismarine-Soul Glass Crystal Alloy | `mvtink_alloy_prismarine_soulsand_crystal` | Infernal, Primal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Prismarine-Spatial Platinum Alloy | `mvtink_alloy_prismarine_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Prismarine-Starlight Silver Alloy | `mvtink_alloy_prismarine_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Prismarine-Steel Alloy | `mvtink_alloy_prismarine_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Prismarine-Stibnite Alloy | `mvtink_alloy_prismarine_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Prismarine-Sulfur Alloy | `mvtink_alloy_prismarine_sulfur` | Infernal, Primal, Resonant |
| Talc (`mvtink_talc`) | Prismarine-Talc Alloy | `mvtink_alloy_prismarine_talc` | Primal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Prismarine-Tesseract Crystal Alloy | `mvtink_alloy_prismarine_tesseract_crystal` | Void, Primal, Resonant |
| Tin (`mvtink_tin`) | Prismarine-Tin Alloy | `mvtink_alloy_prismarine_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Prismarine-Titanium Alloy | `mvtink_alloy_prismarine_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Prismarine-Topaz Alloy | `mvtink_alloy_prismarine_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Prismarine-Tourmaline Alloy | `mvtink_alloy_prismarine_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Prismarine-Tungsten Alloy | `mvtink_alloy_prismarine_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Prismarine-Void Pyrite Alloy | `mvtink_alloy_prismarine_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Prismarine-Void Titanium Alloy | `mvtink_alloy_prismarine_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Prismarine-Voidstone Alloy | `mvtink_alloy_prismarine_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Prismarine-Compacted Volcanic Ash Alloy | `mvtink_alloy_prismarine_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Prismarine-Warped Emerald Alloy | `mvtink_alloy_prismarine_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Prismarine-Warped Quartz Alloy | `mvtink_alloy_prismarine_warped_quartz` | Void, Primal, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Prismarine-Pure Weeping Shard Alloy | `mvtink_alloy_prismarine_weeping_shard` | Infernal, Primal, Resonant |
| Witherite (`mvtink_witherite`) | Prismarine-Witherite Alloy | `mvtink_alloy_prismarine_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Prismarine-Zero-Point Shard Alloy | `mvtink_alloy_prismarine_zero_point` | Void, Primal, Resonant |
| Zinc (`mvtink_zinc`) | Prismarine-Zinc Alloy | `mvtink_alloy_prismarine_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Prismarine-Zircon Alloy | `mvtink_alloy_prismarine_zircon` | Primal, Terrain, Resonant |

#### Nether Quartz · `mvtink_quartz` · 33 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Redstone (`mvtink_redstone`) | Nether Quartz-Redstone Alloy | `mvtink_alloy_quartz_redstone` | Primal, Resonant, Volatile |
| Resonite (`mvtink_resonite`) | Nether Quartz-Resonite Alloy | `mvtink_alloy_quartz_resonite` | Void, Primal, Resonant |
| Ruby (`mvtink_ruby`) | Nether Quartz-Ruby Alloy | `mvtink_alloy_quartz_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Nether Quartz-Sanguinite Alloy | `mvtink_alloy_quartz_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Nether Quartz-Sapphire Alloy | `mvtink_alloy_quartz_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Nether Quartz-Shadowgem Alloy | `mvtink_alloy_quartz_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Nether Quartz-Shulkerite Alloy | `mvtink_alloy_quartz_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Nether Quartz-Silver Alloy | `mvtink_alloy_quartz_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Nether Quartz-Singularite Alloy | `mvtink_alloy_quartz_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Nether Quartz-Soul Glass Crystal Alloy | `mvtink_alloy_quartz_soulsand_crystal` | Infernal, Primal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Nether Quartz-Spatial Platinum Alloy | `mvtink_alloy_quartz_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Nether Quartz-Starlight Silver Alloy | `mvtink_alloy_quartz_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Nether Quartz-Steel Alloy | `mvtink_alloy_quartz_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Nether Quartz-Stibnite Alloy | `mvtink_alloy_quartz_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Nether Quartz-Sulfur Alloy | `mvtink_alloy_quartz_sulfur` | Infernal, Primal, Resonant |
| Talc (`mvtink_talc`) | Nether Quartz-Talc Alloy | `mvtink_alloy_quartz_talc` | Primal, Terrain, Resonant |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Nether Quartz-Tesseract Crystal Alloy | `mvtink_alloy_quartz_tesseract_crystal` | Void, Primal, Resonant |
| Tin (`mvtink_tin`) | Nether Quartz-Tin Alloy | `mvtink_alloy_quartz_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Nether Quartz-Titanium Alloy | `mvtink_alloy_quartz_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Nether Quartz-Topaz Alloy | `mvtink_alloy_quartz_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Nether Quartz-Tourmaline Alloy | `mvtink_alloy_quartz_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Nether Quartz-Tungsten Alloy | `mvtink_alloy_quartz_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Nether Quartz-Void Pyrite Alloy | `mvtink_alloy_quartz_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Nether Quartz-Void Titanium Alloy | `mvtink_alloy_quartz_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Nether Quartz-Voidstone Alloy | `mvtink_alloy_quartz_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Nether Quartz-Compacted Volcanic Ash Alloy | `mvtink_alloy_quartz_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Nether Quartz-Warped Emerald Alloy | `mvtink_alloy_quartz_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Nether Quartz-Warped Quartz Alloy | `mvtink_alloy_quartz_warped_quartz` | Void, Primal, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Nether Quartz-Pure Weeping Shard Alloy | `mvtink_alloy_quartz_weeping_shard` | Infernal, Primal, Resonant |
| Witherite (`mvtink_witherite`) | Nether Quartz-Witherite Alloy | `mvtink_alloy_quartz_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Nether Quartz-Zero-Point Shard Alloy | `mvtink_alloy_quartz_zero_point` | Void, Primal, Resonant |
| Zinc (`mvtink_zinc`) | Nether Quartz-Zinc Alloy | `mvtink_alloy_quartz_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Nether Quartz-Zircon Alloy | `mvtink_alloy_quartz_zircon` | Primal, Terrain, Resonant |

#### Redstone · `mvtink_redstone` · 32 socios

| Socio | Aleación resultante | ID | Esencias |
|---|---|---|---|
| Resonite (`mvtink_resonite`) | Redstone-Resonite Alloy | `mvtink_alloy_redstone_resonite` | Void, Primal, Resonant |
| Ruby (`mvtink_ruby`) | Redstone-Ruby Alloy | `mvtink_alloy_redstone_ruby` | Primal, Terrain, Radiant |
| Sanguinite (`mvtink_sanguinite`) | Redstone-Sanguinite Alloy | `mvtink_alloy_redstone_sanguinite` | Infernal, Primal, Tempered |
| Sapphire (`mvtink_sapphire`) | Redstone-Sapphire Alloy | `mvtink_alloy_redstone_sapphire` | Primal, Terrain, Radiant |
| Shadowgem (`mvtink_shadowgem`) | Redstone-Shadowgem Alloy | `mvtink_alloy_redstone_shadowgem` | Void, Primal, Radiant |
| Shulkerite (`mvtink_shulkerite`) | Redstone-Shulkerite Alloy | `mvtink_alloy_redstone_shulkerite` | Void, Primal, Terrain |
| Silver (`mvtink_silver`) | Redstone-Silver Alloy | `mvtink_alloy_redstone_silver` | Primal, Terrain, Tempered |
| Singularite (`mvtink_singularite`) | Redstone-Singularite Alloy | `mvtink_alloy_redstone_singularite` | Void, Primal, Terrain |
| Soul Glass Crystal (`mvtink_soulsand_crystal`) | Redstone-Soul Glass Crystal Alloy | `mvtink_alloy_redstone_soulsand_crystal` | Infernal, Primal, Resonant |
| Spatial Platinum (`mvtink_spatial_platinum`) | Redstone-Spatial Platinum Alloy | `mvtink_alloy_redstone_spatial_platinum` | Void, Primal, Tempered |
| Starlight Silver (`mvtink_starlight_silver`) | Redstone-Starlight Silver Alloy | `mvtink_alloy_redstone_starlight_silver` | Void, Primal, Tempered |
| Steel (`mvtink_steel`) | Redstone-Steel Alloy | `mvtink_alloy_redstone_steel` | Infernal, Primal, Tempered |
| Stibnite (`mvtink_stibnite`) | Redstone-Stibnite Alloy | `mvtink_alloy_redstone_stibnite` | Infernal, Primal, Terrain |
| Sulfur (`mvtink_sulfur`) | Redstone-Sulfur Alloy | `mvtink_alloy_redstone_sulfur` | Infernal, Primal, Volatile |
| Talc (`mvtink_talc`) | Redstone-Talc Alloy | `mvtink_alloy_redstone_talc` | Primal, Terrain, Volatile |
| Tesseract Crystal (`mvtink_tesseract_crystal`) | Redstone-Tesseract Crystal Alloy | `mvtink_alloy_redstone_tesseract_crystal` | Void, Primal, Resonant |
| Tin (`mvtink_tin`) | Redstone-Tin Alloy | `mvtink_alloy_redstone_tin` | Primal, Terrain, Tempered |
| Titanium (`mvtink_titanium`) | Redstone-Titanium Alloy | `mvtink_alloy_redstone_titanium` | Primal, Terrain, Tempered |
| Topaz (`mvtink_topaz`) | Redstone-Topaz Alloy | `mvtink_alloy_redstone_topaz` | Primal, Terrain, Radiant |
| Tourmaline (`mvtink_tourmaline`) | Redstone-Tourmaline Alloy | `mvtink_alloy_redstone_tourmaline` | Primal, Terrain, Resonant |
| Tungsten (`mvtink_tungsten`) | Redstone-Tungsten Alloy | `mvtink_alloy_redstone_tungsten` | Infernal, Primal, Tempered |
| Void Pyrite (`mvtink_void_pyrite`) | Redstone-Void Pyrite Alloy | `mvtink_alloy_redstone_void_pyrite` | Void, Primal, Terrain |
| Void Titanium (`mvtink_void_titanium`) | Redstone-Void Titanium Alloy | `mvtink_alloy_redstone_void_titanium` | Void, Primal, Tempered |
| Voidstone (`mvtink_voidstone`) | Redstone-Voidstone Alloy | `mvtink_alloy_redstone_voidstone` | Void, Primal, Terrain |
| Compacted Volcanic Ash (`mvtink_volcanic_ash`) | Redstone-Compacted Volcanic Ash Alloy | `mvtink_alloy_redstone_volcanic_ash` | Infernal, Primal, Terrain |
| Warped Emerald (`mvtink_warped_emerald`) | Redstone-Warped Emerald Alloy | `mvtink_alloy_redstone_warped_emerald` | Infernal, Primal, Radiant |
| Warped Quartz (`mvtink_warped_quartz`) | Redstone-Warped Quartz Alloy | `mvtink_alloy_redstone_warped_quartz` | Void, Primal, Resonant |
| Pure Weeping Shard (`mvtink_weeping_shard`) | Redstone-Pure Weeping Shard Alloy | `mvtink_alloy_redstone_weeping_shard` | Infernal, Primal, Resonant |
| Witherite (`mvtink_witherite`) | Redstone-Witherite Alloy | `mvtink_alloy_redstone_witherite` | Infernal, Primal, Terrain |
| Zero-Point Shard (`mvtink_zero_point`) | Redstone-Zero-Point Shard Alloy | `mvtink_alloy_redstone_zero_point` | Void, Primal, Volatile |
| Zinc (`mvtink_zinc`) | Redstone-Zinc Alloy | `mvtink_alloy_redstone_zinc` | Primal, Terrain, Tempered |
| Zircon (`mvtink_zircon`) | Redstone-Zircon Alloy | `mvtink_alloy_redstone_zircon` | Primal, Terrain, Resonant |

