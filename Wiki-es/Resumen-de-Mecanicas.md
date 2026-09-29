# 🗺️ Resumen de Mecánicas — Qué hace cada bloque, ítem y función

Una sola página que responde a "…¿y eso para qué sirve?" en todo el plugin. Cada sección termina
enlazando la página que la explica a fondo.

---

## 🔁 El proceso en seis pasos

1. **Extraer** — la **[Brocha de Prospector](Arqueologia.md)** cepilla geología del Overworld, Nether o
   End para soltar minerales en bruto (`mvtink_*_raw`), pepitas o bloques de almacenamiento.
2. **Fundir** — la **[Fundición](Fundicion-y-Caldero.md)** funde los minerales en bruto sobre lava o
   magma y los convierte en **cubos de metal fundido**.
3. **Moldear** — un **molde** tallado más un caldero con agua convierten el metal fundido en lingotes,
   pepitas, bloques o **piezas** de herramienta.
4. **Alear** — el **[Crisol de Aleaciones](Mezcla-de-Materiales.md)** mezcla dos minerales en una
   aleación, y una aleación legendaria con otro material en una
   **[aleación primordial](Aleaciones-Primordiales.md)**.
5. **Ensamblar** — la **[GUI de la Forja](Guia-GUI-Forja.md)** une las piezas en armas, herramientas y
   armaduras modulares.
6. **Evolucionar y documentar** — el equipo sube **7 tiers**, dispara su **perk y su animación
   exclusiva**, y el **[Codex de Aleaciones](Aleaciones-Primordiales.md)** documenta cada material y
   receta.

---

## ⛏ La Brocha de Prospector — `mvtink_brush_prospector`

Una herramienta con clic derecho que extrae **geología** en vez de piedra. Cepillar una superficie
válida inicia un temporizador (`archaeology.brushing-duration-ticks`, 30t por defecto) y luego tira un
mineral de esa dimensión.

| Qué hace | Detalle |
|---|---|
| **Objetivos válidos** | Overworld: Stone / Cobblestone · Nether: Netherrack / Blackstone · End: End Stone |
| **Tirada de éxito** | `archaeology.success-chance` por dimensión (0.45 / 0.40 / 0.35) |
| **Qué suelta** | Un mineral en bruto, o una pepita (`nugget-chance`), o — con la **brocha de prospector** — un **bloque** de almacenamiento entero (`block-chance`) |
| **Qué mineral** | Ponderado por rareza (común 50, poco común 30, raro 14, épico 5, legendario 1). La brocha de prospector **duplica** el peso de los raros, épicos y legendarios |
| **Estado del bloque** | `archaeology.block-behavior`: `DEGRADE` (stone → cobblestone → gravel → aire), `COOLDOWN` o `NONE` |
| **Anti-macro** | Cada bloque tiene su temporizador `block-cooldown-seconds` (15s) |
| **Coste** | `brush-durability-cost` de durabilidad por extracción; requiere `multiversetinker.archaeology` (por defecto **true**) |

Todos los minerales que puede soltar, con sus rasgos, están en la
**[Referencia de Materiales](Fuentes-de-Materiales.md)**.

---

## 🔥 La Fundición — `mvtink_smeltery`

Un crisol colocable que **funde** minerales. Colócalo sobre una fuente de calor y aliméntalo con
mineral en bruto, lingotes, pepitas o bloques; devuelve **cubos de metal fundido** que puedes verter
después.

| Qué hace | Detalle |
|---|---|
| **Calor** | La lava es la fuente más rápida; un bloque de magma funde un `smeltery.magma-duration-multiplier` (1.30 = 30%) más lento |
| **Coste** | Cada fundición completa tiene `smeltery.lava-consume-chance` (10%) de consumir la lava de debajo |
| **Velocidad** | Cada mineral tiene su propio `meltingDurationTicks` (50t para el Talco hasta 240t para los de endgame) |
| **Diagnóstico** | La GUI de la fundición reporta su calor, progreso y contenido |

---

## ⚗ Moldeo y moldes — los 13 moldes `CastType`

Los moldes convierten el metal fundido en un ítem usable. Se tallan en la GUI de la Forja y se enfrían
en un caldero con agua; el mismo molde es **reutilizable**.

| Molde | Convierte el metal fundido en |
|---|---|
| Molde de lingote / pepita / bloque | Lingotes estándar, **9 pepitas**, bloques de almacenamiento |
| Molde de cabeza / mango / pomo | **Cabezas**, **mangos (varillas)** y **pomos (ataduras)** de armas y herramientas |
| Molde de brazos de arco / cuerda | **Brazos** y **cuerdas** de arco |
| Molde de placa / jefe de escudo | **Placas** y **jefes** de escudo |
| Molde de placa / forro / ribete de armadura | **Placas**, **forros** y **ribetes** de armadura |

Cada pieza es un ítem real (`mvtink_*_head`, `_rod`, `_binding`, …) que la Forja ensambla después.

---

## 🏗️ La Forja multibloque

Una estructura de **243 bloques** centrada en un **yunque**, con columnas de lava en las esquinas,
ladrillos de toba cincelada y baldosas de pizarra profunda (ver
[Estructura de la Forja](Estructura-Forja.md)). Haz clic derecho en el yunque central para validarla,
activarla y abrir la GUI. `/mvtink forge build` la construye al instante y `/mvtink forge check`
informa de cuántos bloques coinciden.

---

## 🧰 La GUI de la Forja — seis pestañas

| Pestaña | Qué hace |
|---|---|
| **1. Guías** | Libros interactivos: estructura, forjado multimaterial, crisol, tiers y perks — y el **botón del libro** que abre el Codex de Aleaciones |
| **2. Moldes y Piezas** | Talla moldes con ladrillos de arcilla y moldea **hasta 3 materiales por pieza**; la concentración (100% / 50-50 / 33-33-33) decide con cuánta fuerza se expresa el rasgo de cada material |
| **3. Crisol de Aleaciones** | Dos casillas de entrada y un botón **Melt & Blend**; produce **2 lingotes** de la aleación |
| **4. Ensamblado de Armas** | Recorre los 7 tipos de arma y ensambla las piezas forjadas |
| **5. Ensamblado de Herramientas** | Recorre los 5 tipos de herramienta y las ensambla |
| **6. Ensamblado de Armaduras** | Recorre las 4 piezas de armadura y las ensambla |

---

## 🛠️ Equipo modular

Un ítem forjado conserva su propio **contador de durabilidad**, su **tier** y su **mezcla de
esencias**. El desgaste vanilla siempre está desactivado (`equipment.modular-attack-damage` decide si
el daño también sale de los minerales) para que el plugin y Minecraft nunca gasten durabilidad en el
mismo golpe.

### Armas (7)

| Arma | Piezas | Perk característico | Animación exclusiva |
|---|---|---|---|
| **Broadsword** | cabeza + mango + pomo | **Sweeping Cleave** | Sweeping Arc |
| **Longbow** | brazos + cuerda | **Infused Volley** | Volley Trail |
| **Ballesta Pesada** | cabeza + mango + pomo | **Piercing Velocity** | Piercing Lance |
| **Tridente Anciano** | cabeza + mango + pomo | **Hydraulic Surge** | Surge Column |
| **Lanza Cinética** | cabeza + mango + pomo | **Jousting Reach** | Thrust Line |
| **Maza de Guerra** | cabeza + mango + pomo | **Seismic Smash** | Shock Ring |
| **Escudo Torre** | placa + jefe | **Retaliation Barrier** | Parapet Arc |

### Herramientas (5 + el broadsword)

| Herramienta | Perk característico | Animación exclusiva |
|---|---|---|
| **Pico** | **Vein Resonance** — menas extra de vetas resonantes + Haste temporal | Vein Strike |
| **Hacha de Batalla** | **Lumber Cleave** — tala troncos enteros y rompe escudos | Lumber Splash |
| **Excavadora** | **Seismic Tremor** — excavar agachado limpia un área 3×3 | Ground Wave |
| **Guadaña** | **Harvest Scythe** — cosecha un campo 3×3 y replanta las semillas | Harvest Swirl |
| **Caña de Pescar** | **Abyssal Dredge** — pescar en aguas profundas puede enganchar minerales raros | Dredge Drip |

### Armadura (4)

| Pieza | Perk característico | Animación exclusiva |
|---|---|---|
| **Casco** | **Cranium Ward** — reduce daño a la cabeza y filtra peligros ambientales | Cranium Halo |
| **Pechera** | **Kinetic Dampener** — absorbe el 25% de los impactos fuertes | Kinetic Dome |
| **Perneras** | **Stride Momentum** — mitiga el desgaste al esprintar | Stride Coil |
| **Botas** | **Feathered Grounding** — anula hasta el 50% del daño por caída y mantiene tracción | Grounding Puff |

### Las 10 piezas

`HEAD` (daño, velocidad, golpe primario) · `ROD` (multiplicador de durabilidad) · `BINDING`
(durabilidad + utilidad) · `BOW_LIMBS` (velocidad de tensado) · `BOWSTRING` (energía de disparo) ·
`SHIELD_PLATE` (bloqueo frontal) · `SHIELD_BOSS` (estabilidad de parada) · `ARMOR_PLATE` (defensa) ·
`ARMOR_LINING` (dureza) · `ARMOR_TRIM` (resistencia al empuje). Los ids heredados `_processed`
(lingote), `_handle` (mango) y `_pommel` (pomo) siguen resolviendo.

### Los 7 tiers

El equipo sube de nivel **usándose**: las armas ganan XP con las bajas, las herramientas con bloques
rotos y las armaduras con daño absorbido.

| Tier | Bajas | Bloques | Daño absorbido | Multiplicador de daño | Multiplicador de durabilidad |
|---|---:|---:|---:|---:|---:|
| **Wood** | 0 | 0 | 0 | 0.0 | 1.00x |
| **Stone** | 15 | 50 | 150 | 0.8 | 1.15x |
| **Copper** | 40 | 150 | 350 | 1.5 | 1.30x |
| **Iron** | 80 | 350 | 650 | 2.5 | 1.50x |
| **Gold** | 150 | 750 | 900 | 3.5 | 1.80x |
| **Diamond** | 300 | 1.500 | 1.500 | 5.0 | 2.20x |
| **Netherite ★ MAX** | 600 | 3.000 | 2.500 | 7.0 | 2.80x |

### Esencias y animaciones

Cada material enseña hasta **3 esencias deterministas** (Infernal, Void, Primal, Terrain, Tempered,
Radiant, Resonant, Volatile, Swift, Brutal, Bulwark, Ascendant) que se comportan distinto en armas,
herramientas y armaduras — ver [Afinidades de Rasgos](Afinidades-de-Rasgos.md). La **cabeza** nombra el
perk, el **mango/pomo** fijan su potencia, y un **80% de enfoque de esencia** desbloquea el
**[ultimate](Ultimates-de-Esencia.md)** de esa esencia.

Cada uno de los **16 tipos de equipo** tiene su propia coreografía de partículas y sonido, que se
reproduce cuando su perk realmente salta y se tiñe con el color del mineral dominante. Se ajusta con
las claves `animations` (`enabled`, `particle-scale`, `sounds`, `cooldown-millis`).

---

## 🧪 Los tres niveles de aleación

| Nivel | Entradas | Resultado |
|---|---|---|
| **Legendaria** | Dos minerales concretos (16 recetas curadas) | La aleación curada con su rasgo propio |
| **Compuesta** | Cualquier par de minerales mezclables | `<A>-<B> Alloy` con las esencias heredadas |
| **Primordial** | Una aleación legendaria + (otra aleación, un mineral o un catalizador) | `<A> <B> Prime`, con el ultimate y el estado de armadura del catalizador |

Los **catalizadores** son objetos vanilla reales (Estrella del Nether, Hielo Azul, Fragmento de Eco…)
que nunca se mezclan solos: moldean la build de endgame. La lista completa de pares y rasgos está en el
**[Índice de Recetas](Indice-de-Recetas.md)**.

---

## 📖 El Codex de Aleaciones

Un menú paginado de 54 casillas abierto a **todos los jugadores** con `/mvtink codex`, también
accesible desde el botón del libro en la pestaña del Crisol. Sus **siete secciones**: Recetas
Legendarias · Catalizadores Primordiales · Compuestas Forjadas · Aleaciones Primordiales · Explorador
de Combinaciones · Resumen del Espacio de Aleaciones · **Catálogo de Minerales**.

El catálogo tiene dos **alcances** (botón **Scope**) — la lista curada de materiales y la lista plana
de **todos los ids registrados** (2.656 hoy) —, un filtro **Kind**, y un despliegue que lista cada id
de un material, con la línea `/mvtink give <jugador> <id>` lista para copiar.

---

## 💻 Comandos y permisos

| Comando | Quién | Qué hace |
|---|---|---|
| `/mvtink` | todos | Ayuda pública (solo `codex`). Con `multiversetinker.admin`: la ayuda completa |
| `/mvtink codex [jugador]` | usuarios del codex; apuntar a otro jugador necesita admin | Abre el Codex de Aleaciones |
| `/mvtink craft <weapon\|tool\|armor> <tipo> <m1> <m2> [m3] [tier]` | admin | Forja equipo al instante |
| `/mvtink give <jugador> <id> [cantidad]` | admin | Entrega cualquier ítem registrado |
| `/mvtink forge <build\|check\|gui> [rotación]` | admin | Construye / valida / abre la Forja |
| `/mvtink verify` | admin | Diagnostica el registro de ítems |
| `/mvtink reload` | admin | Recarga config, ítems y loot |

Permisos: `multiversetinker.admin` (op) · `multiversetinker.codex` (todos) ·
`multiversetinker.archaeology` (todos).

El autocompletado ofrece **todos los ids de ítem registrados** en una sola lista y la filtra según
escribes, y el prefijo `mvtink_` es opcional en todos sitios: escribiendo `tin` aparecen `mvtink_tin`,
`mvtink_tin_ingot` y el resto. En `/mvtink craft` las casillas de material aceptan también esos ids
de ítem, así que un id copiado del codex se puede pegar directamente.

---

## ⚙️ Configuración

`archaeology` (tiempos, botín y comportamiento del bloque) · `smeltery` (coste de lava, velocidad con
magma) · `lore` (ajuste de líneas del tooltip) · `animations` (coreografías de los perks) · `equipment`
(daño de ataque modular) · `rarity-weights` · `messages`. Cada clave está documentada en la
**[Referencia de Configuración](Configuracion.md)**.

---

## 💾 Qué persiste

Todo lo que descubren los jugadores se guarda en `plugins/MultiverseTinker/`: las aleaciones compuestas
y primordiales en `dynamic-alloys.yml`, así que un lingote forjado la semana pasada sigue sirviendo
como componente de forja tras un reinicio.
