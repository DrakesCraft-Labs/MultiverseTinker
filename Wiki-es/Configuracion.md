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
✦ Weapon Perk: Infernal Sweeping
  Cleave: hits multiple adjacent foes
  and chains elemental traits. Imbued
  essence: searing hits that set
  foes ablaze.
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

## ⚔️ Reglas del equipo modular (`equipment`)

Un arma, herramienta o pieza de armadura forjada lleva su **propio contador de durabilidad**. Por eso el desgaste
vanilla está apagado: el ítem es irrompible para el servidor (el flag va oculto, así el tooltip nunca imprime una
fila "Unbreakable") y la durabilidad restante la informa su propia fila de lore `• Durability: actual / máxima`, que
pasa de verde → amarillo → rojo según se desgasta. Sin esto, Minecraft y el plugin gastarían durabilidad en el mismo
golpe y una espada se rompería con el calendario vanilla mientras su contador modular seguía intacto.

| Clave | Tipo | Por defecto | Qué hace |
| --- | --- | --- | --- |
| `equipment.modular-attack-damage` | booleano | `true` | `true` hace que el arma golpee con el daño de ataque calculado a partir de sus minerales — el valor que imprime su lore. `false` conserva el daño del material vanilla base. La durabilidad sigue siendo modular en ambos casos. |

```yaml
equipment:
  modular-attack-damage: true
```

### Cómo se calcula un golpe modular

El arma por debajo es un ítem vanilla, así que el servidor usaría el daño del material base: una espada ancha forjada
de Piedra del Vacío golpearía exactamente igual de fuerte que la espada de madera sobre la que está construida. Con
la regla activa, la contribución vanilla se lee del atributo de daño de ataque en vivo del jugador y solo se sustituye
esa base, de modo que los críticos, las pociones de fuerza y los encantamientos siguen multiplicando el daño forjado
en lugar de perderse.

Poner `modular-attack-damage: false` restaura los valores de combate vanilla puros (útil para servidores con sus
propios plugins de daño), mientras que el desgaste vanilla sigue desactivado en ambos casos.

---

## 🧭 Otras secciones

| Sección | Propósito |
| --- | --- |
| `archaeology` | Sistema de cepillado: interruptor, duración del cepillado, coste de durabilidad de la brocha, probabilidad de éxito por dimensión, comportamiento de degradación del bloque (`DEGRADE` / `COOLDOWN` / `NONE`), enfriamiento anti-macro y rendimientos de la brocha. |
| `animations` | Animaciones exclusivas de los perks: interruptor, multiplicador de partículas, sonido y enfriamiento por tipo. |
| `equipment` | Reglas del equipo modular: si un arma forjada pelea con su daño calculado o con el del material vanilla (el desgaste vanilla siempre está desactivado). |
| `smeltery` | Ajustes del crisol: probabilidad de consumir la lava fuente y multiplicador de lentitud de la fuente de calor con Bloque de Magma. |
| `rarity-weights` | Pesos de botín relativos por rareza de mineral (`common` … `legendary`). **Reservado:** hoy la tabla de botín de arqueología usa los pesos internos (común 50, poco común 30, raro 14, épico 5, legendario 1), así que editar esta clave todavía no tiene efecto. |
| `messages` | Textos de chat y barra de acción (formato MiniMessage) para cepillado, enfriamientos y permisos. |

---

## 🔗 Páginas relacionadas

* **[Guía del GUI de la Forja y Equipo Modular](Guia-GUI-Forja.md)**: qué imprime exactamente el lore envuelto — perks, enfoque de esencia, canales de rasgos y la animación exclusiva de cada tipo.
* **[Rasgos de Forja y Efectos de Minerales](Rasgos-y-Efectos.md)**: la referencia de rasgos y afinidades tras esas filas de lore.
* **[Aleaciones Primordiales](Aleaciones-Primordiales.md)**: el archivo `dynamic-alloys.yml` que conserva las primordiales forjadas por los jugadores entre reinicios.
* **[Resumen de Mecánicas](Resumen-de-Mecanicas.md)**: cada mecánica, ítem y comando que configuran las claves de arriba.
