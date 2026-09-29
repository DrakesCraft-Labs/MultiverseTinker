# 🌌 Aleaciones Primordiales (Primes)

Las aleaciones primordiales son el tercer y último nivel del **Crisol de Aleaciones** (pestaña 3 de la
GUI de forja). Donde una aleación compuesta funde dos minerales, una primordial funde **una de las 16
aleaciones legendarias** con otra legendaria, una compuesta, un mineral o un **objeto catalizador
vanilla** — y el resultado es lo que desbloquea el endgame cinematográfico: ataques congelantes,
cascadas de meteoritos y estados de armadura totalmente nuevos.

---

## ⚗️ Cómo se forja una primordial

Mismos dos slots que cualquier aleación (slots **29** y **33**), mismo botón *Ignite Crucible*. El
crisol solo acepta el par cuando **al menos una entrada es una aleación legendaria** y ninguna de las
dos es ya una primordial:

| Entrada A | Entrada B | Resultado |
|---|---|---|
| Aleación legendaria | Aleación legendaria | ✅ Primordial (p. ej. `mvtink_prime_bronze_manyullyn`) |
| Aleación legendaria | Aleación compuesta | ✅ Primordial |
| Aleación legendaria | Mineral (brocha/vanilla) | ✅ Primordial |
| Aleación legendaria | Catalizador vanilla | ✅ Primordial con **sigilo** de catalizador |
| Mineral | Mineral | ✅ compuesta normal (o receta legendaria) |
| Compuesta | Compuesta | ❌ rechazado — una primordial siempre necesita un padre legendario |
| Catalizador | Catalizador | ❌ rechazado — un catalizador no sirve solo |
| Primordial | cualquier cosa | ❌ rechazado — las primordiales no se refunden (sin escalera infinita) |

Una primordial se llama siempre `<Aleación A> <Aleación B> Prime`, tiene rareza **Legendary**, obtiene
`0.78 × (durabilidad de los padres) + 120` de durabilidad, velocidad media **+1.1** y daño medio
**+2.2** — estrictamente mejor que una compuesta de los mismos padres. Los catalizadores no aportan
masa metalúrgica, así que una primordial legendaria + catalizador se construye desde la aleación que
catalizó.

> 💡 Las primordiales se forjan **por descubrimiento**: solo se guardan las que realmente fundes, en
> `plugins/MultiverseTinker/dynamic-alloys.yml`, así que tu crisol sigue creciendo entre sesiones.

---

## 📊 Todo el espacio de aleaciones

| Nivel | Reglas | Aleaciones distintas |
|---|---|---|
| **Aleaciones de minerales** | cualquier par de los 110 minerales mezclables | **5.995** |
| **Recetas legendarias** | 16 fusiones curadas (14 libres + Cinder Steel y Netherita Cósmica) | **16** |
| **Primordiales** | una legendaria + otra aleación | hasta **95.712** |
| | una legendaria + un mineral | **1.760** |
| | una legendaria + un catalizador vanilla | **192** |
| | dos aleaciones legendarias | **120** |
| **Total** | | **hasta 103.781** |

Un servidor nuevo ya expone **8.085** combinaciones forjables; el resto de primordiales aparece según
descubras compuestas. El códice del crisol (botón del libro) informa cuántas has encontrado.

---

## ❖ Los 12 catalizadores vanilla

Coloca el objeto vanilla en un slot del crisol junto a cualquier aleación legendaria. El **sigilo** del
catalizador decide qué ultimate lanza el arma forjada y con qué estado responde la armadura forjada.

| Catalizador | Id de material | Ultimate que otorga | Estado de armadura |
|---|---|---|---|
| Estrella del Nether | `mvtink_catalyst_nether_star` | **Supernova** | Prime Aegis |
| Aliento de Dragón | `mvtink_catalyst_dragon_breath` | **Meteor Cascade** | Ember Veil |
| Hielo Azul | `mvtink_catalyst_blue_ice` | **Absolute Zero** ❄ | Frostbound |
| Hielo Compacto | `mvtink_catalyst_packed_ice` | **Glacier Tomb** ❄ | Frostbound |
| Fragmento de Eco | `mvtink_catalyst_echo_shard` | **Event Horizon** | Void Shell |
| Corazón del Mar | `mvtink_catalyst_heart_of_the_sea` | **Tectonic Rift** | Gravitic Anchor |
| Tótem de Inmortalidad | `mvtink_catalyst_totem_of_undying` | **Prismatic Ascension** | Prime Aegis |
| Cristal del End | `mvtink_catalyst_end_crystal` | **Supernova** | Stormcall |
| Ancla de Reaparición | `mvtink_catalyst_respawn_anchor` | **Meteor Cascade** | Ember Veil |
| Cristales de Prismarina | `mvtink_catalyst_prismarine_crystals` | **Prismatic Ascension** | Prism Bulwark |
| Cúmulo de Amatista | `mvtink_catalyst_amethyst_cluster` | **Event Horizon** | Prism Bulwark |
| Escombros Antiguos | `mvtink_catalyst_ancient_debris` | **Tectonic Rift** | Tectonic Guard |

Las primordiales forjadas **sin** catalizador heredan el espectáculo de su esencia dominante
(Infernal → Meteor Cascade, Void → Event Horizon, Terrain/Primal → Tectonic Rift, Swift/Radiant/
Ascendant → Prismatic Ascension, Tempered/Resonant → Supernova, Brutal/Volatile → Meteor Cascade o
Ember Veil, Bulwark → Event Horizon).

---

## ⚡ Ultimates primordiales

Las armas primordiales se lanzan con **20 segundos de enfriamiento**, igual que los ultimates de
esencia, y **no** exigen el 80 % de enfoque: la aleación legendaria es el requisito. Su impacto aplica
**1.55×–1.75×** el daño del golpe que lo activa, inmoviliza al objetivo 60–90 ticks y empuja a los
enemigos cercanos con el 60 % del daño.

| Ultimate | Animación | Daño | Radio | Raíz | Extra |
|---|---|---|---|---|---|
| **Absolute Zero** | Campo de hielo | 1.60× | 5.0 | 70 t | **400 ticks de congelación** — el objetivo se congela del todo |
| **Glacier Tomb** | Campo de hielo (jaula) | 1.55× | 4.5 | 90 t | **300 ticks de congelación** + escarcha cada 2 ticks |
| **Meteor Cascade** | Tormenta de meteoritos | 1.75× | 5.0 | 70 t | 8 meteoritos caen en espiral con fuego y lava |
| **Supernova** | Nova | 1.70× | 5.5 | 60 t | Detonación estelar + anillo de sonic boom |
| **Event Horizon** | Vórtice | 1.65× | 5.5 | 80 t | El agujero negro arrastra a los enemigos al centro |
| **Tectonic Rift** | Terremoto | 1.65× | 5.0 | 60 t | La corteza se parte y lanza a todos por los aires |
| **Prismatic Ascension** | Pilar | 1.60× | 4.5 | 60 t | El pilar prismático también cura 4 PV al portador |

### 🧊 El espectáculo de congelación (nuevo)

`Absolute Zero` y `Glacier Tomb` usan la nueva animación **CRYO**:

1. **Carga** — la escarcha se arrastra por el suelo, cae nieve desde arriba y cruje la nieve en polvo.
2. **Impacto** — **diez pinchos de hielo** brotan en anillo alrededor del epicentro (columnas de
   partículas de bloque con copos y escombros de hielo azul), suena un sonic boom y la víctima recibe
   300–400 ticks de congelación más Lentitud VI.
3. **Retención** — una jaula de escarcha giratoria mantiene al objetivo clavado mientras caen copos de
   su cuerpo, re-aplicando 140 ticks de congelación cada tick.

### ☄️ La tormenta de meteoritos (nueva)

`Meteor Cascade` usa la nueva animación **METEOR_STORM**: en lugar de tres carriles, **ocho
meteoritos caen en espiral** desde 16 bloques, cada uno con estela de fuego y lava, humo y polvo de
color, terminando en doble explosión en el epicentro. Los enemigos a 5 bloques reciben el 60 % del
impacto y salen despedidos.

---

## 🛡️ Estados de armadura primordiales (nuevos)

Un set primordial ya no es solo "más Resistencia". Recibir un golpe activa el **estado** del set,
determinado por la primera aleación primordial de la armadura (sigilo de catalizador primero, esencia
como respaldo). Cada estado tiene su propio enfriamiento para no spamear.

| Estado | Efecto al ser golpeado | Enfriamiento |
|---|---|---|
| **Frostbound** | El atacante recibe Lentitud II + 160 ticks de congelación; tú ganas Resistencia | 10 s |
| **Meteor Ward** | Meteoritos ardientes caen sobre el atacante: 3 de daño + 3 s de fuego | 10 s |
| **Gravitic Anchor** | El atacante es arrastrado a cuerpo a cuerpo; tú ganas Resistencia II | 8 s |
| **Prime Aegis** | Absorción II + Resistencia II para ti | 8 s |
| **Stormcall** | Un rayo real golpea al atacante (2.5 de daño); tú ganas Velocidad | 12 s |
| **Ember Veil** | El atacante arde 6 s; tú ganas Resistencia al Fuego | 6 s |
| **Void Shell** | El atacante recibe Levitación II + Lentitud; tú ganas Caída Lenta y curas 1 PV | 10 s |
| **Prism Bulwark** | Devuelve 2 de daño y empuja al atacante; tú ganas Regeneración | 12 s |
| **Tectonic Guard** | El atacante sale lanzado hacia arriba y ralentizado; brotan escombros | 9 s |

El lore de una pieza primordial muestra `✦ Prime State: <estado> — <descripción>`, y cada arma,
herramienta o armadura primordial añade además una línea `✦ Prime Alloy:` con el ultimate y el estado
que porta.

---

## 📖 El Codex de Aleaciones

Todo lo anterior es navegable in-game. Abre el **Codex de Aleaciones** con `/mvtink codex` o pulsando el
botón del libro en la pestaña del Crisol de Aleaciones (shift-clic para imprimir los totales en el
chat). El codex está **abierto a todos los jugadores** — es material de referencia, no una herramienta de
administración — y solo apuntarlo a otro jugador con `/mvtink codex <jugador>` requiere el permiso de admin. El codex es un menú paginado de 54 slots con siete secciones:

| Sección | Qué muestra |
|---|---|
| **Catálogo de Minerales** | Cada material que conoce este servidor — geológico, vanilla y aleación forjada — con su id, dimensión, rareza, rasgo y esencias. Es el reemplazo del antiguo listado por chat. |
| **Recetas Legendarias** | Las 16 aleaciones curadas: padres, stats, esencias y rasgo curado. |
| **Catalizadores Primordiales** | Los 12 catalizadores vanilla, con el ultimate y el estado que otorga cada uno. |
| **Compuestas Forjadas** | Cada compuesta descubierta en este servidor, con padres, stats y esencias. |
| **Aleaciones Primordiales** | Cada primordial forjada hasta ahora, con su ultimate y su estado. |
| **Explorador de Combinaciones** | Elige cualquier material y ve **todos** los socios que acepta el crisol, con el nombre e id exactos del resultado. |
| **Resumen del Espacio de Aleaciones** | Pares de minerales, recetas legendarias, conteo de fusiones primordiales y el gran total. |

El catálogo tiene dos **alcances**, que se cambian con el botón **Scope** de la fila inferior:

* **Materiales** — la lista curada de arriba, una entrada por material, con su rasgo y sus esencias.
* **Todos los ítems** — el registro plano: los **2.656 ids registrados** que el servidor puede entregar ahora mismo,
  incluidas piezas de herramienta, moldes, el crisol, baldes fundidos y los alias heredados `_processed` /
  `_handle` / `_pommel`. El botón **Kind** recorre esa lista por tipo de ítem (en bruto, lingote, pepita,
  bloque, balde fundido y los diez tipos de pieza) para saltar directo, por ejemplo, a todos los minerales en bruto.

Al hacer clic en un material del alcance **Materiales** se **despliega** ese material y se listan todos los ids
que posee, así compruebas de un vistazo que el cobre no es un solo ítem sino su mineral en bruto, lingote, pepita,
bloque, balde y piezas. Cada entrada del registro escribe su propio id en el lore, y al hacer clic imprime en el
chat la línea `/mvtink give <jugador> <id>` correspondiente — el catálogo es una herramienta de consulta, no una forja.

> 🔍 El explorador no tiene efectos secundarios: previsualiza el id, el nombre y las esencias del
> resultado sin forjar nada, así puedes planificar una build antes de gastar un solo lingote. Las
> aleaciones primordiales nunca aparecen como socias, porque no se pueden refundir.

---

## Páginas relacionadas

* [Mezcla de Materiales y Aleaciones](Mezcla-de-Materiales.md) — el crisol, las 16 recetas legendarias y los 5.995 pares de minerales.
* [Afinidades de Rasgos](Afinidades-de-Rasgos.md) — cómo una primordial conserva las esencias de ambos padres.
* [Ultimates de Esencia](Ultimates-de-Esencia.md) — los 12 espectáculos de esencia que las primordiales mejoran.
