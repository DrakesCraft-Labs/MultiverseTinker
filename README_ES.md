<div align="center">

# ⚒️ MultiverseTinker (Español)

**Herramientas Modulares, Arqueología Geológica, Crisol de Fundición y Metalurgia para Paper 1.21+ (Java 21)**

<p>
  <img src="https://img.shields.io/badge/Paper-1.21.11-38BDF8?style=for-the-badge&logo=minecraft&logoColor=white" alt="Paper 1.21.11"/>
  <img src="https://img.shields.io/badge/Java-21-F89820?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Licencia-GPLv3-blue?style=for-the-badge" alt="GPLv3"/>
  <img src="https://img.shields.io/badge/Autor-Chagui68-22C55E?style=for-the-badge" alt="Chagui68"/>
  <img src="https://img.shields.io/badge/Minerales-90_Total-purple?style=for-the-badge" alt="90 Minerales"/>
</p>

Parte del **Ecosistema Soberano Multiverse de Chagui68** junto a [MultiverseNets](https://github.com/DrakesCraft-Labs/MultiverseNets), [MultiverseCreatures](https://github.com/DrakesCraft-Labs/MultiverseCreatures) y [MultiverseProgramming](https://github.com/DrakesCraft-Labs/MultiverseProgramming).

[📖 Wiki en Español](Wiki-es/Home.md) · [🏛️ Estructura Forja](Wiki-es/Estructura-Forja.md) · [⚡ Rasgos de Forja](Wiki-es/Rasgos-y-Efectos.md) · [📖 English Wiki](Wiki-en/Home.md) · [🏛️ Forge Multiblock](Wiki-en/Forge-Structure.md) · [⚡ Forge Traits](Wiki-en/Traits-and-Effects.md) · [English (README)](README.md)

</div>

> ### 🏰 ¡Únete a la Comunidad Oficial de DrakesCraft!
> * 🎮 **IP del Servidor**: `mc.drakescraft.cl` *(Java 1.21.11 & Bedrock)*
> * 💬 **Discord Oficial**: [discord.gg/drakescraft](https://discord.gg/rv3vtXZTk7)
> * 🌐 **Web & Guías**: [web.drakescraft.cl](https://web.drakescraft.cl) — 🛒 **Tienda**: [web.drakescraft.cl/store](https://web.drakescraft.cl/store.html)

---

## 🌟 ¿Qué es MultiverseTinker?

**MultiverseTinker** traslada la metalurgia modular, aleaciones avanzadas y geología arqueológica inspiradas en Tinkers' Construct a Minecraft moderno como un **plugin 100% nativo y standalone para Paper/Purpur 1.21+** y **Java 21**, sin dependencias forzosas.

* **Cero Problemas de Generación de Terreno**: Los minerales se descubren mediante un sistema interactivo de **Arqueología y Cepillado Geológico** sobre roca, netherrack y piedra del end sin alterar los generadores de chunks.
* **90 Minerales Únicos**: Distribuidos de forma equilibrada en **exactamente 30 minerales por cada dimensión** (Overworld, Nether y The End).
* **5 Formas Físicas por Mineral**: Cada mineral cuenta con su **Mineral en Bruto (Raw)**, **Balde Fundido Líquido**, **Lingote o Gema**, **Pepita** y **Bloque Compacto** con recetas reversibles de $9\times$.
* **Crisol de Fundición (Smeltery Crucible)**: Estación de fundición que opera con **Lava** (100% velocidad, 10% probabilidad de consumo) o **Bloque de Magma** (70% velocidad, estabilidad inagotable) directamente debajo, con interfaz gráfica interactiva y tiempos de fusión diferenciados.
* **Enfriamiento en Caldero con Moldes**: Moldes cerámicos reutilizables (*Ingot Cast*, *Nugget Cast*, *Block Cast*) templan los baldes fundidos en calderos de agua con efectos de vapor y enfriamiento.
* **Aislamiento Total**: Cada ítem, receta y tag en `PersistentDataContainer` (PDC) lleva el prefijo reservado **`mvtink_`**.

---

## ⚙️ Sistemas Principales

### 1. 🔍 Arqueología Geológica y Cepillado
Extracción de minerales en bruto manteniendo el click derecho con una brocha sobre bloques geológicos naturales:
* **Overworld**: Piedra, Adoquín, Pizarra, Andesita, Diorita, Granito, Toba (Estaño, Zinc, Plata, Rubí, Zafiro, Titanio, Platino, etc.).
* **The Nether**: Netherrack, Piedra Negra, Basalto (Cobalto, Ardita, Azufre, Sangrita, Tungsteno, Witherita, etc.).
* **The End**: Piedra del End (Enderita, Adamantium, Adamita, Celestina, Vacuita, Cosmium, Singularita, etc.).
* **Degradación Geológica**: El bloque se desgasta de forma natural con el uso sostenido (`Piedra -> Adoquín -> Grava -> Aire`) con enfriamiento anti-macros.
* **Rendimiento de Brocha Normal**: Extrae Minerales en Bruto (70%) o Pepitas individuales (30%). No puede extraer bloques completos de almacenamiento.
* **Brocha de Prospector (`mvtink_brush_prospector`)**: Herramienta especializada con **+40% de velocidad**, **+15% de probabilidad de éxito**, **doble suerte para minerales Raros/Épicos/Legendarios**, **50% de probabilidad de no gastar durabilidad**, y la capacidad única de desenterrar **Bloques de Almacenamiento completos** (20% de probabilidad jackpot), Minerales en Bruto (55%) o 1–3 Pepitas (25%).

---

### 2. 🌋 Crisol de Fundición (*Tinker Smeltery Crucible*)
* **Colocación y Fuentes de Calor (`BlockFace.DOWN`)**:
  * **Lava**: 100% de velocidad de fundición. Cuenta con un **10% de probabilidad** de consumirse (convirtiéndose en aire con sonido de extinción y humo) al terminar de fundir un mineral.
  * **Bloque de Magma**: 70% de velocidad de fundición (-30% de velocidad / toma 30% más tiempo). Fuente inagotable y segura que nunca se consume.
* **GUI Diagnóstica Dinámica**:
  * ❌ **Sin Calor**: El indicador central se transforma en una barrera roja explicando la necesidad de colocar Lava o Bloque de Magma.
  * 🔥 **Con Calor Activo**: Se enciende con fuego, siseo y barra de progreso porcentual indicando la fuente de calor activa (Lava al 100% o Magma al 70%).
* **Operación**: Se coloca el mineral en bruto en la ranura 10 y un balde vacío en la ranura 12. Al completarse el tiempo de fundición, entrega el **Balde de Mineral Fundido** (`mvtink_<id>_molten_bucket`).

---

### 3. 💧 Enfriamiento en Caldero de Agua (*Casting System*)
* Se llena un caldero con agua.
* Se sostiene el **Balde de Mineral Fundido** en la mano principal y el **Molde (*Cast*)** en la mano secundaria:
  * **Molde de Lingotes** (`mvtink_cast_ingot`): Genera 1 Lingote.
  * **Molde de Pepitas** (`mvtink_cast_nugget`): Genera 9 Pepitas.
  * **Molde de Bloques** (`mvtink_cast_block`): Genera 1 Bloque.
* Click derecho al caldero con agua:
  * Produce nubes de vapor denso y sonido de templado (`BLOCK_LAVA_EXTINGUISH` + clink de yunque).
  * Consume un nivel de agua del caldero por evaporación.
  * Devuelve el balde vacío y entrega el material solidificado.

---

### 4. 🏛️ Estructura Multibloque de la Forja y GUI de 6 Secciones
* **Construcción Monumental ($11 \times 7 \times 11$)**:
  * Centrada en torno a un Yunque central, construida con Ladrillos de Toba Cincelados, Baldosas y Ladrillos de Pizarra Profunda, Losas/Escaleras de Ladrillos de Toba y 4 pilares esquineros de Lava térmica (243 bloques en total). Admite rotaciones a $0^\circ, 90^\circ, 180^\circ, 270^\circ$.
* **Simulación de Partículas y Aura Ambiental**:
  * La estructura completada cuenta con barrido de validación térmica y un aura continua de brasas volcánicas sobre el yunque central.
* **Nueva GUI de 6 Secciones**:
  * **[1. Codex & Guide]**: Códices interactivos con información de la estructura, forja, recetas de aleaciones, progresión de rarezas y habilidades especiales.
  * **[2. Molds & Parts]**: Tallado rápido de moldes (1 Ladrillo de Arcilla = 1 molde reutilizable) y forja multimaterial (coloca de 1 a 3 materiales para dividir los rasgos al 100%, 50/50 o 33/33/33 en proporción a su concentración).
  * **[3. Alloy Crucible]**: Mezcla **cualquier par de minerales distintos** obtenidos con la brocha o refinados de menas vanilla — 110 minerales mezclables y **5.995 pares de minerales**. Las **16 recetas legendarias** (Bronce, Electro, Manyullyn, Damaso del Vacío, Netherita Cósmica, etc.) son forjables; cada otro par sintetiza su propia aleación compuesta. Un servidor nuevo expone **8.085** combinaciones forjables. La netherita vanilla solo se mezcla dentro de sus dos recetas curadas. Cada compuesta que forjas se guarda en `dynamic-alloys.yml` y se restaura al reiniciar, así que los lingotes antiguos siguen funcionando.
  * **[3b. Aleaciones Primordiales]**: Funde una **aleación legendaria** con otra aleación, un mineral o uno de los **12 catalizadores vanilla** (Estrella del Nether, Hielo Azul, Fragmento de Eco, Aliento de Dragón, Corazón del Mar…) para forjar una **aleación primordial** — rareza Legendary, stats superiores y su propio ultimate cinematográfico más un estado de armadura nuevo. Con todas las compuestas descubiertas el crisol llega a **103.781 aleaciones distintas**.
  * **[4. Weapon Assembly]**: Ensamblado de 7 tipos de armas (Espada, Arco, Ballesta, Tridente, Lanza, Mazo, Escudo) que comienzan en **Rareza de Madera** y evolucionan mediante **Bajas en Combate**.
  * **[5. Tool Assembly]**: Ensamblado de 5 tipos de herramientas (Pico, Hacha, Pala, Azada, Caña de pescar) que comienzan en **Rareza de Madera** y evolucionan mediante **Bloques Rotos**.
  * **[6. Armor Assembly]**: Ensamblado de 4 tipos de armaduras (Casco, Peto, Grebas, Botas) a partir de piezas de Placa, Forro y Ribete, que evolucionan mediante **Daño Absorbido**.
* **Habilidades Especiales en Armas y Herramientas**:
  * **Mazo de Guerra (Seismic Smash)**: Golpes en caída desatan una onda sísmica en el suelo con daño en área.
  * **Arco (Infused Volley)**: Las flechas heredan los rasgos elementales de los brazos y de la cuerda, y un arco enfocado dispara una **flecha de réplica** contra el mismo objetivo (25% base, hasta 60% con enfoque de esencia completo).
  * **Ballesta Pesada (Piercing Velocity)**: Los virotes detonan una explosión cinética real y sin dañar bloques que daña y empuja a todas las criaturas en 4 bloques, más +6.0 de daño directo perforante.
* **Espectáculos de Ataque Cinematográficos**:
  * **12 ultimates de esencia** que se activan con un arma enfocada, cada uno con sus propias partículas, sonidos, retención y multiplicador de daño.
  * **7 ultimates primordiales**: **Absolute Zero** y **Glacier Tomb** hacen brotar **diez pinchos de hielo** en anillo y congelan a la víctima **300–400 ticks**, mientras **Meteor Cascade** hace caer **8 meteoritos** en espiral con fuego y lava. Supernova, Event Horizon, Tectonic Rift y Prismatic Ascension completan el set.
* **Nuevos Estados de Armadura (9)**: Frostbound, Meteor Ward, Gravitic Anchor, Prime Aegis, Stormcall, Ember Veil, Void Shell, Prism Bulwark y Tectonic Guard — la armadura primordial responde a cada golpe con su propia reacción (ráfagas congelantes, guardias meteóricos, rayos, daño reflejado…).
  * **Tridente (Hydraulic Surge)**: Rayos y oleadas hidráulicas bajo el agua o lluvia (+5.0 daño).
  * **Lanza Cinética (Jousting Reach)**: Alcance de ataque extendido y +30% de daño en embestida al esprintar.
  * **Escudo Torre (Retaliation Barrier)**: Refleja el 35% del daño bloqueado de vuelta al atacante.
  * **Espada Ancha (Sweeping Cleave)**: Los tajos de barrido golpean a varios enemigos adyacentes y propagan los rasgos elementales.
  * **Hacha de Guerra (Lumber Cleave)**: Derriba todo el tronco conectado y quiebra los escudos enemigos.
  * **Pico (Vein Resonance)**: Otorga minerales adicionales y Prisa minera I en vetas resonantes.
  * **Pala Excavadora (Seismic Tremor)**: Minar agachado rompe un área de 3x3 de tierra, arena o grava.
  * **Guadaña (Harvest Scythe)**: Cosecha cultivos maduros en 3x3 y replanta automáticamente las semillas de tu inventario.
  * **Caña de Pescar (Abyssal Dredge)**: 15% de probabilidad de pescar minerales raros de las profundidades.
  * **Armadura Modular**: cuatro defensas de ranura distintas — Casco (**Cranium Ward**) mitigación de disparos a la cabeza e inmunidad a peligros, Peto (**Kinetic Dampener**) absorción del 25% de impactos fuertes, Grebas (**Stride Momentum**) recuperación al esprintar y Botas (**Feathered Grounding**) daño de caída reducido a la mitad.
  * **Ultimates de Esencia**: un arma con **≥80% de enfoque de esencia** desata un ultimate cinematográfico ligado a su esencia — lluvia de meteoritos, singularidades, pilares de luz o jaulas del bastión — que inmoviliza al enemigo 2-3 s mientras se reproduce la animación (20 s de enfriamiento). Ver [Ultimates de Esencia](Wiki-es/Ultimates-de-Esencia.md).
  * **Perks según los Materiales**: cada habilidad anterior — tanto de armas como de herramientas y armaduras — se **nombra e impulsa según el mineral de la cabeza o de la placa** (Cobalto → *Infernal Piercing Velocity*, Piedra del Vacío → *Void Piercing Velocity*, Diamante → *Radiant Tajo Enlazado*, pico de Cobalto → *Infernal Vein Resonance*, peto de Piedra del Vacío → *Void Kinetic Dampener*), mientras la empuñadura y el pomo (o el forro y el ribete) fijan el **Essence Focus** que se muestra en el lore; la esencia dominante se canaliza en el golpe primario del perk. Además, cada fila de rasgo de material indica **cuándo** se dispara — `on sweep`, `on arrow hit`, `on bolt impact`, `on surge`, `on thrust`, `on smash`, `on block`, `while mining`, `while chopping`, `while digging`, `while harvesting`, `while fishing` o `when struck` para armaduras —, así que una espada ancha, un arco y unas botas nunca imprimen el mismo bloque de rasgos.
* **Afinidades de Rasgos Deterministas**: Cada mineral y mena vanilla resuelve a hasta 3 de las 12 esencias (Infernal, Void, Primal, Tempered, Radiant, Resonant, Volatile, Terrain, Swift, Brutal, Bulwark, Ascendant). Se comportan de forma **ofensiva en armas, como procs de minería en herramientas y defensivos en armaduras**, y las aleaciones heredan las esencias de ambos progenitores — cada combinación de minerales posee así su propia funcionalidad única. Ver [Afinidades de Rasgos](Wiki-es/Afinidades-de-Rasgos.md).

---

## 🍳 Recetas de Supervivencia

| Objeto | Cuadrícula (3×3) | Ingredientes |
|---|---|---|
| **Crisol de Fundición** | <pre>S F S<br/>M B M<br/>S S S</pre> | S = Piedra lisa · F = Alto horno · M = Bloque de magma · B = Balde |
| **Brocha de Prospector** | <pre>· G ·<br/>C B C<br/>· R ·</pre> | G = Lingote de oro · C = Lingote de cobre · B = Brocha · R = Amatista |
| **Molde de Lingotes** | <pre>B B B<br/>B · B<br/>B B B</pre> | B = Ladrillo de arcilla (centro vacío) |
| **Molde de Pepitas** | <pre>B · B<br/>· C ·<br/>B · B</pre> | B = Ladrillo de arcilla · C = Bola de arcilla |
| **Molde de Bloques** | <pre>B B B<br/>B I B<br/>B B B</pre> | B = Ladrillo de arcilla · I = Bloque de hierro |
| **Molde de Cabeza** | <pre>B G B<br/>B · B<br/>B B B</pre> | B = Ladrillo de arcilla · G = Lingote de oro |
| **Molde de Palo / Varilla** | <pre>B C B<br/>B · B<br/>B · B</pre> | B = Ladrillo de arcilla · C = Lingote de cobre |
| **Molde de Mango / Unión** | <pre>B I B<br/>· C ·<br/>B B B</pre> | B = Ladrillo de arcilla · I = Lingote de hierro · C = Bola de arcilla |

---

## 💻 Comandos y Permisos

* `/mvtink forge build [0|90|180|270]` — Construye la estructura completa de la forja en la ubicación del jugador.
* `/mvtink forge check` — Valida el yunque al que estás apuntando y muestra el porcentaje de coincidencia.
* `/mvtink forge gui` — Abre directamente la interfaz gráfica de la Forja Multiverse.
* `/mvtink give <jugador> <mvtink_id> [cantidad]` — Entrega cualquier ítem (en bruto, lingote, pepita, bloque, balde fundido, piezas de herramienta, moldes, crisol, brocha). El prefijo `mvtink_` es opcional, los ids se resuelven bajo demanda y las aleaciones compuestas/primordiales forjadas después del arranque también se pueden entregar. El autocompletado es **jerárquico**: primero ofrece el id de todos los materiales registrados y, cuando el id está completo, sus tipos de ítem, así que ningún material desaparece de la lista.
* `/mvtink codex [jugador]` — Abre el **Codex de Aleaciones** navegable: el **catálogo de minerales** completo (cada material con su id, dimensión, rareza, rasgo y esencias), recetas legendarias, catalizadores primordiales, compuestas y primordiales forjadas, un explorador de combinaciones y los totales (también disponible in-game desde el botón del libro en la pestaña del Crisol; shift-clic en ese botón imprime los totales en el chat).
* `/mvtink verify` — Diagnostica el registro de ítems: materiales registrados, tipos de ítem por material, ids distintos y una comprobación completa de resolubilidad (cada material × cada tipo).
* `/mvtink reload` — Recarga la configuración y las tablas de arqueología.

> `/mvtink` es el único nombre de comando del plugin y **no registra alias** — ningún otro nombre responderá.

**Permisos:**
* `multiversetinker.admin` — Acceso a los comandos administrativos de `/mvtink` (por defecto: `op`).
* `multiversetinker.archaeology` — Permite usar la brocha para extracción geológica (por defecto: `true`).

---

## ⚙️ Configuración

`config.yml` se genera en la carpeta de datos del plugin en el primer arranque con los valores por defecto. No hace falta reiniciar para aplicar un cambio: edita el archivo y ejecuta `/mvtink reload`.

### 📜 Presentación del lore de los ítems (`lore`)

Minecraft dibuja cada fila de lore como una sola línea y recorta lo que sobresale del área del tooltip, que es lo
que cortaba los perks de arma y las descripciones de rasgos. MultiverseTinker en su lugar **envuelve las filas
largas** conservando colores, degradados y decoraciones. Solo se parten las filas que sobresalen; las filas cortas
mantienen su formato exacto.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `lore.wrap-long-lines` | booleano | `true` | Parte las filas largas de lore en los espacios. Ponlo en `false` para volver a la salida de una sola fila. |
| `lore.max-line-width-pixels` | entero | `190` | Ancho máximo de una fila normal de lore, en píxeles con la tipografía por defecto (un carácter minúsculo promedia ~6 px; el área del tooltip vanilla es ~200 px). Bájalo para tooltips más estrechos con más filas, súbelo para filas más anchas. Los valores por debajo de `60` se recortan. |
| `lore.header-line-width-pixels` | entero | `320` | Presupuesto más amplio para las dos filas de cabecera del equipo modular (etiqueta de tier + barra de progreso). Esas filas se reescriben en su sitio al subir de nivel, así que deben conservar el mismo número de filas. Se recorta a un mínimo de `max-line-width-pixels`. |

```yaml
lore:
  wrap-long-lines: true
  max-line-width-pixels: 190
  header-line-width-pixels: 320
```

La envoltura se **mide, no se cuenta**: el ancho de cada fila se estima glifo a glifo con la tipografía por
defecto de Minecraft, y los glifos anchos (bullets, barras, estrellas) se sobreestiman a propósito para partir
una palabra antes en lugar de recortar. Las filas de continuación llevan sangría colgante, así los bullets `✦ `
y `• ` siguen alineados bajo su texto.

### ✨ Animaciones de los perks (`animations`)

Cada tipo de arma, herramienta y armadura tiene una **coreografía exclusiva**: su propia geometría de partículas, su propio par de partículas y su propio sonido. La animación salta cuando el perk de esa pieza realmente dispara (una espada ancha que encadena un enemigo con el barrido, una flecha del arco que impacta, un golpe de mazo, un casco que recibe un impacto…) y se tiñe con el color del mineral dominante con el que se forjó el ítem. Dos tipos nunca pueden compartir firma: un test hace fallar el build si ocurre.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `animations.enabled` | booleano | `true` | Reproducir las coreografías de los perks. |
| `animations.particle-scale` | número | `1.0` | Multiplicador de partículas de cada animación, limitado a `0.25` – `3.0` (súbelo en servidores potentes, bájalo para clientes justos). |
| `animations.sounds` | booleano | `true` | Reproducir el sonido característico de cada animación. |
| `animations.cooldown-millis` | entero | `400` | Retardo mínimo entre dos animaciones del mismo tipo en el mismo jugador, para que los procs rápidos (barridos, impactos en la pechera) no se conviertan en un estrobo. |

```yaml
animations:
  enabled: true
  particle-scale: 1.0
  sounds: true
  cooldown-millis: 400
```

| Equipo | Animación | Patrón | Sonido |
| --- | --- | --- | --- |
| Espada Ancha | Sweeping Arc | `SWEEP_ATTACK` | `ENTITY_PLAYER_ATTACK_SWEEP` |
| Arco Largo | Volley Trail | `CRIT` | `ENTITY_ARROW_SHOOT` |
| Ballesta | Piercing Lance | `ELECTRIC_SPARK` | `ITEM_CROSSBOW_SHOOT` |
| Tridente | Hydraulic Surge | `SPLASH` | `ITEM_TRIDENT_RIPTIDE_1` |
| Lanza | Jousting Thrust | `CLOUD` | `ENTITY_PLAYER_ATTACK_STRONG` |
| Mazo de Guerra | Seismic Smash | `EXPLOSION` | `ITEM_MACE_SMASH_GROUND_HEAVY` |
| Escudo Torre | Retaliation Bulwark | `ENCHANTED_HIT` | `ITEM_SHIELD_BLOCK` |
| Pico | Vein Resonance | `ENCHANTED_HIT` | `BLOCK_AMETHYST_BLOCK_CHIME` |
| Hacha de Batalla | Lumber Cleave | `CRIT` | `BLOCK_WOOD_BREAK` |
| Pala Excavadora | Seismic Tremor | `CLOUD` | `BLOCK_GRAVEL_BREAK` |
| Guadaña | Harvest Swirl | `HAPPY_VILLAGER` | `ITEM_CROP_PLANT` |
| Caña de Pescar | Abyssal Dredge | `BUBBLE` | `ENTITY_FISHING_BOBBER_SPLASH` |
| Casco | Cranium Halo | `END_ROD` | `BLOCK_AMETHYST_BLOCK_RESONATE` |
| Pechera | Kinetic Dome | `ENCHANTED_HIT` | `BLOCK_ANVIL_LAND` |
| Pantalones | Stride Coil | `CLOUD` | `ENTITY_PHANTOM_FLAP` |
| Botas | Grounding Puff | `SNOWFLAKE` | `BLOCK_POWDER_SNOW_BREAK` |

### ⚔️ Reglas del equipo modular (`equipment`)

Las armas, herramientas y armaduras forjadas llevan su **propio contador de durabilidad**, así que el desgaste vanilla queda desactivado: el ítem es irrompible para el servidor (con el flag oculto, sin fila "Unbreakable" en el tooltip) y la durabilidad restante la informa su propia fila de lore `• Durability: actual / máxima`, que pasa de verde → amarillo → rojo según se desgasta. Una espada ya no puede romperse con el calendario vanilla mientras su contador modular sigue intacto.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `equipment.modular-attack-damage` | booleano | `true` | `true` hace que el arma golpee con el daño de ataque calculado a partir de sus minerales (el valor que imprime su lore). `false` conserva el daño del material vanilla base; la durabilidad sigue siendo modular en ambos casos. |

```yaml
equipment:
  modular-attack-damage: true
```

Con la regla activa, el golpe conserva críticos, fuerza y encantamientos: la contribución vanilla se mide desde el atributo de daño de ataque en vivo del jugador y solo se sustituye la base, de modo que los multiplicadores siguen escalando el daño forjado.

### 🧭 Otras secciones

| Sección | Propósito |
| --- | --- |
| `archaeology` | Sistema de cepillado: interruptor, duración, coste de durabilidad de la brocha, probabilidad de éxito por dimensión, comportamiento de degradación del bloque, enfriamiento anti-macro y rendimientos de la brocha. |
| `animations` | Animaciones exclusivas de los perks: interruptor, multiplicador de partículas, sonido y enfriamiento por tipo. |
| `equipment` | Reglas del equipo modular: si un arma forjada pelea con su daño calculado o con el del material vanilla (el desgaste vanilla siempre está desactivado). |
| `smeltery` | Ajustes del crisol: probabilidad de consumo de lava y multiplicador de lentitud de la fuente de calor con Bloque de Magma. |
| `rarity-weights` | Pesos de botín relativos por rareza de mineral (`common` … `legendary`). |
| `messages` | Textos de chat y barra de acción (formato MiniMessage) para cepillado, enfriamientos y permisos. |

---

<div align="center">

**DrakesCraft Labs** · Diseñado por **Chagui68**  
Licencia: **GPL-3.0**

</div>
