# ⚙️ Referencia de Configuración

MultiverseTinker genera `config.yml` en `plugins/MultiverseTinker/` en su primer arranque, con los valores por
defecto. Nada de ahí exige reiniciar el servidor: edita el archivo y ejecuta `/mvtink reload` para releer de una
vez la configuración, el registro de ítems y las tablas de botín.

---

## 📜 Presentación del lore de los ítems (`lore`)

Minecraft dibuja **cada fila de lore como una sola línea** y recorta sin más lo que sobresale del área del
tooltip. Por eso los perks largos y las descripciones de rasgos se salían de la pantalla: la frase era una sola
fila, no un párrafo. MultiverseTinker ahora envuelve las filas largas para que todo el texto siga siendo legible.

La envoltura conserva **colores, degradados y decoraciones** (la fila se divide en tramos con estilo y se
reconstruye, así un degradado mantiene su color por carácter). Las filas que ya caben se devuelven intactas y las
filas en blanco separadoras nunca se tocan.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `lore.wrap-long-lines` | booleano | `true` | Parte las filas largas en los espacios. Ponlo en `false` para volver a la salida de una sola fila (las líneas largas volverán a recortarse). |
| `lore.max-line-width-pixels` | entero | `190` | Ancho máximo de una fila normal de lore, en píxeles con la tipografía por defecto. Un carácter minúsculo promedia ~6 px y el área del tooltip vanilla mide ~200 px. Bájalo para tooltips más estrechos con más filas, súbelo para filas más anchas con menos filas. Los valores por debajo de `60` se recortan, porque a partir de ahí deja de ser legible. |
| `lore.header-line-width-pixels` | entero | `320` | Presupuesto más amplio reservado para las **dos filas de cabecera** del equipo modular: la etiqueta de tier y la barra de progreso. Esas filas se reescriben en su sitio cada vez que el ítem sube de nivel, así que deben seguir siendo las mismas antes y después de la actualización. Se recorta a un mínimo de `max-line-width-pixels`. |

```yaml
# Item lore presentation (tooltips)
lore:
  # Split long lore rows at word boundaries so nothing runs off the screen
  wrap-long-lines: true

  # Maximum width of a wrapped lore row, in default-font pixels
  max-line-width-pixels: 190

  # Wider budget for the first two rows of modular equipment (tier tag + progress bar)
  header-line-width-pixels: 320
```

### Cómo se mide el ancho

La envoltura se **mide, no se cuenta**: en lugar de partir cada cierto número de caracteres, se estima el ancho de
cada glifo con la tipografía por defecto de Minecraft y los glifos anchos (bullets, barras, marcadores de estrella)
se **sobreestiman** a propósito. Sobrestimar es seguro: partir una palabra antes cuesta una fila, mientras que
partir demasiado tarde vuelve a recortar el texto.

Las filas de continuación llevan una **sangría colgante**, así una fila que empezó con `✦ ` o `• ` continúa bajo su
propio texto en lugar de bajo el bullet:

```
✦ Weapon Perk: Fluxforged Resonant Auric
  Sweeping Cleave: hits multiple
  adjacent foes and chains elemental
  traits. Imbued with Infernal,
  Tempered, Swift essence: searing
  hits that set foes ablaze.
```

### Cómo desactivarlo

`lore.wrap-long-lines: false` apaga la función completa e imprime el lore exactamente como antes. Útil si usas un
resource pack con una tipografía muy distinta o si prefieres el recorte antes que las filas extra.

Al arrancar (y en cada `/mvtink reload`) la consola informa lo que se aplicó, por ejemplo:

```
Item lore wrapping enabled at 190px (headers 320px) per row.
```

---

## ✨ Animaciones exclusivas de los perks (`animations`)

Los perks no son lo único que posee cada tipo de equipo: cada arma, herramienta y pieza de armadura tiene su
propia **coreografía exclusiva**. Dos tipos no comparten patrón, par de partículas ni sonido, y un test hace
fallar el build si alguna vez ocurre — así es como «sin favoritismos» se garantiza en lugar de prometerse.

La animación salta cuando el perk de esa pieza realmente dispara, y se tiñe con el color del mineral dominante
con el que se forjó el ítem, así una espada ancha de Cobalto y una de Piedra del Vacío comparten el arco pero no su tono.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `animations.enabled` | booleano | `true` | Reproducir o no las coreografías de los perks. |
| `animations.particle-scale` | número | `1.0` | Multiplicador de partículas de cada animación, limitado a `0.25` – `3.0`. Bájalo en servidores cargados, súbelo para un espectáculo más fuerte. |
| `animations.sounds` | booleano | `true` | Reproducir el sonido característico que acompaña a cada patrón. |
| `animations.cooldown-millis` | entero | `400` | Retardo mínimo entre dos animaciones del mismo tipo en el mismo jugador (limitado a `0` – `5000`). Los procs rápidos — un barrido que encadena, una pechera que come un golpe — se mantienen legibles en vez de estroboscópicos. |

```yaml
# Animaciones exclusivas de los perks
animations:
  enabled: true
  particle-scale: 1.0
  sounds: true
  cooldown-millis: 400
```

### Las dieciséis coreografías

| Equipo | Animación | Patrón | Sonido característico |
| --- | --- | --- | --- |
| Espada Ancha | Sweeping Arc | `SWEEP_ATTACK` + tinte | `ENTITY_PLAYER_ATTACK_SWEEP` |
| Arco Largo | Volley Trail | `CRIT` + `END_ROD` | `ENTITY_ARROW_SHOOT` |
| Ballesta Pesada | Piercing Lance | `ELECTRIC_SPARK` + `CRIT` | `ITEM_CROSSBOW_SHOOT` |
| Tridente Anciano | Hydraulic Surge | `SPLASH` + `ELECTRIC_SPARK` | `ITEM_TRIDENT_RIPTIDE_1` |
| Lanza Cinética | Jousting Thrust | `CLOUD` + `CRIT` | `ENTITY_PLAYER_ATTACK_STRONG` |
| Mazo de Guerra | Seismic Smash | `EXPLOSION` + `CLOUD` | `ITEM_MACE_SMASH_GROUND_HEAVY` |
| Escudo Torre | Retaliation Bulwark | `ENCHANTED_HIT` + `END_ROD` | `ITEM_SHIELD_BLOCK` |
| Pico | Vein Resonance | `ENCHANTED_HIT` + `ELECTRIC_SPARK` | `BLOCK_AMETHYST_BLOCK_CHIME` |
| Hacha de Batalla | Lumber Cleave | `CRIT` + `CHERRY_LEAVES` | `BLOCK_WOOD_BREAK` |
| Pala Excavadora | Seismic Tremor | `CLOUD` + `CAMPFIRE_COSY_SMOKE` | `BLOCK_GRAVEL_BREAK` |
| Guadaña | Harvest Swirl | `HAPPY_VILLAGER` + `NOTE` | `ITEM_CROP_PLANT` |
| Caña de Pescar | Abyssal Dredge | `BUBBLE` + `SPLASH` | `ENTITY_FISHING_BOBBER_SPLASH` |
| Casco | Cranium Halo | `END_ROD` + `ENCHANTED_HIT` | `BLOCK_AMETHYST_BLOCK_RESONATE` |
| Pechera | Kinetic Dome | `ENCHANTED_HIT` + `CLOUD` | `BLOCK_ANVIL_LAND` |
| Pantalones | Stride Coil | `CLOUD` + `ELECTRIC_SPARK` | `ENTITY_PHANTOM_FLAP` |
| Botas | Grounding Puff | `SNOWFLAKE` + `CLOUD` | `BLOCK_POWDER_SNOW_BREAK` |

Cuándo salta cada animación:

* **Espada Ancha** — el barrido encadenó al menos un enemigo adyacente.
* **Arco Largo / Ballesta / Tridente (lanzado)** — la flecha, el virote o el tridente impactaron en algo.
* **Tridente (cuerpo a cuerpo), Lanza, Mazo** — se activó la marea, la estocada corriendo/a caballo o el golpe descendente.
* **Escudo Torre** — se reflejó un golpe bloqueado.
* **Pico, Guadaña, Caña de Pescar** — el proc del 15% de botín extra tuvo éxito.
* **Hacha de Batalla, Pala Excavadora** — corrió la tala del árbol o la excavación 3×3 agachado.
* **Armadura** — la pieza respondió a un golpe (casco, pechera, pantalones al correr) o absorbió una caída (botas).

---

## ✦ Artes de firma (`signature-arts`)

Cada aleación forjada en un arma despierta un **arte de firma** cuyo tamaño sigue el pedigrí de forja del arma (ver **[Artes de Firma](Artes-de-Firma.md)**). Las partículas y sonidos siguen la sección `animations`; la mecánica se aplica aunque las animaciones estén desactivadas.

| Clave | Tipo | Por defecto | Significado |
| --- | --- | --- | --- |
| `signature-arts.enabled` | booleano | `true` | Permite que las armas de aleación disparen su arte al golpear. |
| `signature-arts.chance-multiplier` | número | `1.0` | Multiplica la probabilidad de cada pedigrí (II 15% · III 20% · IV 24% · V 28%), limitado a `0.0` – `5.0`. `0` apaga las artes sin tocar los ultimates. |
| `signature-arts.cooldown-multiplier` | número | `1.0` | Multiplica el enfriamiento por jugador (II 8 s · III 10 s · IV 12 s · V 14 s), limitado a `0.1` – `10.0`. |
| `signature-arts.mythic-titles` | booleano | `true` | Muestra el nombre del arte como título en pantalla para artes y ultimates primordiales y míticos. |
| `signature-arts.display-entities` | booleano | `true` | Permite que las artes levanten entidades de bloque temporales (la campana de bronce, géiseres de magma, picos de hielo, obeliscos míticos). Nunca se guardan en el mundo y se eliminan al terminar el arte o al apagar el plugin. |

```yaml
signature-arts:
  enabled: true
  chance-multiplier: 1.0
  cooldown-multiplier: 1.0
  mythic-titles: true
  display-entities: true
```

`/mvtink reload` aplica un valor cambiado sin reiniciar.

---

## ⚔️ Reglas del equipo modular (`equipment`)

Un arma, herramienta o pieza de armadura forjada lleva su **propio contador de durabilidad**. Por eso el desgaste
vanilla está apagado: el ítem es irrompible para el servidor (el flag va oculto, así el tooltip nunca imprime una
fila "Unbreakable") y la durabilidad restante la informa su propia fila de lore `• Durability: actual / máxima`, que
pasa de verde → amarillo → rojo según se desgasta. Sin esto, Minecraft y el plugin gastarían durabilidad en el mismo
golpe y una espada se rompería con el calendario vanilla mientras su contador modular seguía intacto.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `equipment.modular-attack-damage` | booleano | `true` | `true` hace que el arma golpee con el daño de ataque calculado a partir de sus minerales — el valor que imprime su lore. `false` conserva el daño del material vanilla base. La durabilidad sigue siendo modular en ambos casos. |
| `equipment.modular-armor-defense` | booleano | `true` | `true` hace que la armadura defienda con la **Defensa**, la **Dureza** y la resistencia al empuje calculadas a partir de sus minerales y su tier de evolución — los valores que imprime su lore. `false` conserva la protección vanilla del material del tier. Las piezas ya forjadas se refrescan cuando evoluciona su tier. |
| `equipment.armor-perk.scale-per-point` | decimal | `0.02` | Mitigación que compra cada punto de Defensa y Dureza calculados para el perk de armadura de un hueco. `0` convierte el perk en un porcentaje fijo por hueco. |
| `equipment.armor-perk.floors.helmet` | decimal | `0.30` | Porcentaje con el que arranca la Guarda Craneal, antes de comprar un solo punto calculado. |
| `equipment.armor-perk.floors.chestplate` | decimal | `0.25` | Porcentaje inicial del Amortiguador Cinético. |
| `equipment.armor-perk.floors.boots` | decimal | `0.50` | Porcentaje inicial del Anclaje Plumado. |
| `equipment.armor-perk.caps.helmet` | decimal | `0.65` | Techo del porcentaje de la Guarda Craneal. |
| `equipment.armor-perk.caps.chestplate` | decimal | `0.60` | Techo del porcentaje del Amortiguador Cinético. |
| `equipment.armor-perk.caps.boots` | decimal | `0.75` | Techo del porcentaje del Anclaje Plumado. |

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

### Ajustar la curva de los perks de armadura

La Guarda Craneal, el Amortiguador Cinético y el Anclaje Plumado responden a un golpe con un porcentaje de él, y ese
porcentaje crece con la pieza: arranca en el suelo del hueco, y cada punto de Defensa y Dureza por encima de lo que
tira el hueco desnudo compra `scale-per-point`, hasta el techo de ese hueco. Toda la curva se puede reajustar en
`/mvtink reload` — el paso, dónde arranca cada hueco y dónde se detiene — así que los perks pueden pesar más o menos
sin recompilar.

* **Un porcentaje es un decimal**: `0.02` es 2%, `0.75` es 75%. Todos los valores se recortan — un paso negativo se
  trata como sin escalado, y un porcentaje mayor que `1.0` como `1.0`.
* **El suelo es donde arranca la curva**, así que subirlo hace que cada pieza de ese hueco proteja más desde el primer
  golpe, y bajarlo desplaza toda la curva hacia abajo.
* **Un techo por debajo del suelo de un hueco fija ese hueco en el techo.** Lo que dice el archivo siempre manda sobre
  lo que el perk traía de fábrica, así que bajar un techo por debajo de su suelo es una forma legítima de convertir el
  hueco en un porcentaje fijo.
* **Un hueco que falte conserva su valor de fábrica**, suelo o techo, así que borrar una clave lo restaura en la
  siguiente recarga.
* **Los leggings no tienen curva que ajustar**: el Impulso de Zancada responde con movilidad, y una clave de suelo o
  techo para él se avisa en el log en lugar de leerse.
* **Un reajuste llega al combate al instante y al lore desde ese momento.** Una pieza forjada antes del cambio
  conserva el porcentaje ya escrito en su lore hasta que evoluciona o se forja de nuevo, porque ese número forma parte
  del ítem.

Los techos de fábrica mantienen todos los valores en porcentajes de dos dígitos, que es lo que hace que la frase del
perk ocupe el mismo ancho en todos los tiers; un techo de `1.0` se imprime como `100%`, un carácter más ancho.

### Cómo se calcula un golpe modular

El arma por debajo es un ítem vanilla, así que el servidor usaría el daño del material base: una espada ancha forjada
de Piedra del Vacío golpearía exactamente igual de fuerte que la espada de madera sobre la que está construida. Con
la regla activa, la contribución vanilla se lee del atributo de daño de ataque en vivo del jugador y solo se sustituye
esa base, de modo que los críticos, las pociones de fuerza y los encantamientos siguen multiplicando el daño forjado
en lugar de perderse.

Poner `modular-attack-damage: false` restaura los valores de combate vanilla puros (útil para servidores con sus
propios plugins de daño), mientras que el desgaste vanilla sigue desactivado en ambos casos.

### Cómo protege la armadura modular

La armadura tiene el mismo problema en el lado defensivo: una pieza forjada es por debajo un ítem de armadura
vanilla, así que el servidor otorgaría la protección de ese material base — un casco con placa de diamante defendía
exactamente igual que el tier del que estuviera construido, sin importar qué minerales llevara dentro. Con
`modular-armor-defense: true` los modificadores de armadura de la pieza se **sustituyen** por los números calculados
en lugar de dejarlos como están:

* **Defensa** — de la placa, más el ordinal del tier de evolución.
* **Dureza** — del forro, más la mitad del ordinal del tier.
* **Resistencia al empuje** — del número de materiales del ribete, más un pequeño bonus de tier.

Cada modificador va atado al hueco en el que se lleva la pieza, así que la protección solo se aplica donde
toce, y el tooltip vanilla de atributos se oculta para no imprimir los mismos números dos veces. Como el tier forma
parte del cálculo, una armadura que evoluciona **se re-arma con sus nuevos números** en el mismo momento en que se
reescribe su lore.

---

## 🔐 Quién puede usar qué (`access`)

Que el codex, la forja o la brocha estén abiertos a todos es una decisión del servidor, no necesariamente de un
plugin de permisos. Cada superficie lleva un **modo**, leído de `config.yml`, así que un servidor sin plugin de
permisos decide igualmente quién puede forjar:

| Modo | Quién entra |
| --- | --- |
| `public` | **Todos**, sin consultar ningún nodo de permiso. |
| `op` | **Solo operadores** — para un servidor sin plugin de permisos. |
| `permission` | El nodo declarado en `plugin.yml`, para que LuckPerms, PermissionsEx o un `permissions.yml` vanilla lo restrinjan más. |

| Clave | Tipo | Por defecto | Qué controla |
| --- | --- | --- | --- |
| `access.codex` | string | `public` | Abrir el Codex de Aleaciones — tanto `/mvtink codex` como el botón del libro en la pestaña del Crisol. |
| `access.forge` | string | `public` | La Forja multibloque, su GUI, el Crisol de Aleaciones y el caldero de moldeo. |
| `access.archaeology` | string | `public` | Cepillar un bloque geológico válido para extraer minerales. |

```yaml
access:
  codex: public
  forge: public
  archaeology: public
```

Los subcomandos administrativos de `/mvtink` faltan a propósito en esa tabla. Dar ítems, forjar equipo, contar el
registro y recargar el plugin son tareas de administración, así que cada uno exige siempre un nodo: el paraguas
`multiversetinker.admin` concede los cinco, mientras que `multiversetinker.admin.craft`, `.give`, `.forge`, `.verify`
y `.reload` conceden un solo subcomando — así un servidor puede dar a un maestro de eventos el dador de ítems sin
la recarga. Apuntar el codex a otro jugador se queda con el paraguas. Los operadores tienen el paraguas (y todas
las porciones) por defecto y un plugin de permisos puede conceder el paraguas o un nodo suelto a quien confíe.
`/mvtink codex` es el único comando pensado para los jugadores, y `access.codex` decide si lo tienen. Por eso un
`config.yml` que aún lleve `access.admin-commands` se ignora en lugar de obedecerse, y el plugin lo advierte al
arrancar y en `/mvtink reload`.

```yaml
messages:
  access-denied:
    codex: "<red>No tienes permiso para abrir el Codex de Aleaciones.</red>"
    forge: "<red>No tienes permiso para usar la Forja y el Crisol de Aleaciones.</red>"
    archaeology: "<red>No tienes permiso para hacer arqueología geológica.</red>"
    admin-commands: "<red>No tienes permiso para ejecutar este comando.</red>"
```

### Cuál manda

El modo se aplica **antes** que el nodo, y valen las dos direcciones:

* `access.archaeology: op` rechaza incluso a un jugador con `multiversetinker.archaeology`.
* `access.archaeology: public` nunca consulta el nodo.
* `access.archaeology: permission` es el comportamiento clásico: decide el nodo, y su `default` en `plugin.yml` dice quién lo tiene de fábrica.

Cualquier valor no reconocido vuelve al valor por defecto, así que una errata nunca deja a un servidor sin forja, y se
aceptan las palabras que un dueño escribiría: `everyone` y `all` significan `public`, `ops` y `admin` significan `op`,
`node` significa `permission`. Cada decisión se reporta al arrancar y en `/mvtink reload`:

```
[MultiverseTinker] Access control — codex: public · forge: public · archaeology: public · admin-commands: permission
```

El mensaje `messages.access-denied.admin-commands` sigue siendo configurable aunque su superficie no lo sea, así que
las palabras que ve un jugador cuando un comando le queda fuera de alcance siguen siendo las de su servidor.

### Cuando una regla no puede funcionar

Dos reglas se avisan en ese mismo momento, porque ninguna es de las que un jugador reportaría:

| Gravedad | Cuándo | Ejemplo |
| --- | --- | --- |
| `UNUSABLE` | Una superficie está en `op` y el servidor **no tiene ningún operador**, así que nadie podrá alcanzarla. | `access.forge` is `"op"` and this server has no operators, so nobody can use the Forge, the Alloy Crucible and the casting cauldron. |
| `DANGEROUS` | Un `access.admin-commands` olvidado sigue en un valor que habría abierto los subcomandos administrativos. Se ignora, pero es el valor que antes entregaba a cualquier jugador el dador de ítems y el forjado instantáneo. | `config.yml` still asks for `access.admin-commands: public` … Delete the key to silence this. |

Cerrar `access.codex` así se reporta por lo que es — nadie puede ejecutar un solo comando `/mvtink` —, porque el codex
es el único comando que tienen los jugadores.

Un **yunque** vanilla normal no se toca para quien no puede usar la Forja: solo un multibloque reconocido responde a
la regla, así que el plugin nunca estorba al reparar en un yunque cualquiera.

---

## 🪨 Pesos del botín de arqueología (`rarity-weights`)

Un mineral se sortea dentro de su propia dimensión, y cada **rareza** lleva un peso: cuanto más alto, más a
menudo salen sus minerales de la piedra. Con los valores de fábrica un legendario es aproximadamente una
extracción de cada cien.

```yaml
rarity-weights:
  common: 50
  uncommon: 30
  rare: 14
  epic: 5
  legendary: 1
```

* Los pesos son **relativos**, así que cualquier conjunto de números sirve: doblar `common` y dejar el resto igual hace el mundo el doble de generoso con los minerales de superficie sin tocar el resto de la tabla.
* `0` saca una rareza de la geología por completo — `legendary: 0` significa que no cae ni un mineral legendario, en ninguna dimensión.
* Los valores negativos se leen como `0`, una clave ausente o no numérica conserva su valor de fábrica, y una sección cuyos pesos **todos** acaben a `0` vuelve a los de fábrica: una tabla sin ningún peso repartiría siempre el primer mineral de la dimensión, extracción tras extracción.
* La brocha de **prospector** sigue doblando la frecuencia de los minerales raros, épicos y legendarios, encima de los pesos que haya configurado el servidor.

Los cambios se aplican con `/mvtink reload` y se anuncian al arrancar:

```
[MultiverseTinker] Archaeology rarity weights — common 50 · uncommon 30 · rare 14 · epic 5 · legendary 1 (shipped)
```

---

## 🧭 Otras secciones

| Sección | Propósito |
| --- | --- |
| `access` | Quién puede usar el codex, la forja (GUI, crisol y moldeo), la arqueología y los comandos de admin, como `public`, `op` o `permission`. |
| `archaeology` | Sistema de cepillado: interruptor, duración del cepillado, coste de durabilidad de la brocha, probabilidad de éxito por dimensión, comportamiento de degradación del bloque (`DEGRADE` / `COOLDOWN` / `NONE`), enfriamiento anti-macro y rendimientos de la brocha. |
| `animations` | Animaciones exclusivas de los perks: interruptor, multiplicador de partículas, sonido y enfriamiento por tipo. |
| `signature-arts` | Artes de firma de las aleaciones: interruptor, multiplicadores de probabilidad y enfriamiento, títulos primordiales/míticos y entidades de bloque temporales. |
| `equipment` | Reglas del equipo modular: si un arma forjada pelea con su daño calculado y si la armadura defiende con la protección calculada a partir de sus minerales, o con los valores vanilla (el desgaste vanilla siempre está desactivado). |
| `smeltery` | Ajustes del crisol: probabilidad de consumir la lava fuente y multiplicador de lentitud de la fuente de calor con Bloque de Magma. |
| `rarity-weights` | Pesos por rareza de la tabla de botín de arqueología (`common` 50, `uncommon` 30, `rare` 14, `epic` 5, `legendary` 1). Los pesos son relativos, `0` saca una rareza de la geología por completo, los negativos se leen como 0, y una sección con todo a 0 vuelve a los valores de fábrica para que la tabla nunca quede seca. Una clave ausente o no numérica conserva su valor de fábrica. |
| `messages` | Textos de chat y barra de acción (formato MiniMessage) para cepillado, enfriamientos y el rechazo de cada superficie. |

---

## 🔗 Páginas relacionadas

* **[Guía del GUI de la Forja y Equipo Modular](Guia-GUI-Forja.md)**: qué imprime exactamente el lore envuelto — perks, enfoque de esencia, canales de rasgos y la animación exclusiva de cada tipo.
* **[Nombres de Perk](Nombres-de-Perk.md)**: los 139 epítetos minerales que nombran cada perk y por qué lo deciden los minerales, no solo la esencia.
* **[Rasgos de Forja y Efectos de Minerales](Rasgos-y-Efectos.md)**: la referencia de rasgos y afinidades tras esas filas de lore.
* **[Aleaciones Primordiales](Aleaciones-Primordiales.md)**: el archivo `dynamic-alloys.yml` que conserva las primordiales forjadas por los jugadores entre reinicios.
* **[Resumen de Mecánicas](Resumen-de-Mecanicas.md)**: cada mecánica, ítem y comando que configuran las claves de arriba.
