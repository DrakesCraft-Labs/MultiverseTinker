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

## 🧭 Otras secciones

| Sección | Propósito |
| --- | --- |
| `archaeology` | Sistema de cepillado: interruptor, duración del cepillado, coste de durabilidad de la brocha, probabilidad de éxito por dimensión, comportamiento de degradación del bloque (`DEGRADE` / `COOLDOWN` / `NONE`), enfriamiento anti-macro y rendimientos de la brocha. |
| `smeltery` | Ajustes del crisol: probabilidad de consumir la lava fuente y multiplicador de lentitud de la fuente de calor con Bloque de Magma. |
| `rarity-weights` | Pesos de botín relativos por rareza de mineral (`common` … `legendary`). |
| `messages` | Textos de chat y barra de acción (formato MiniMessage) para cepillado, enfriamientos y permisos. |

---

## 🔗 Páginas relacionadas

* **[Guía del GUI de la Forja y Equipo Modular](Guia-GUI-Forja.md)**: qué imprime exactamente el lore envuelto — perks, enfoque de esencia y canales de rasgos.
* **[Rasgos de Forja y Efectos de Minerales](Rasgos-y-Efectos.md)**: la referencia de rasgos y afinidades tras esas filas de lore.
* **[Aleaciones Primordiales](Aleaciones-Primordiales.md)**: el archivo `dynamic-alloys.yml` que conserva las primordiales forjadas por los jugadores entre reinicios.
