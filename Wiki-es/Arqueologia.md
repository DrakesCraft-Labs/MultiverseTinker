# 🔍 Arqueología Geológica y Excavación con Brocha

MultiverseTinker sustituye la generación tradicional de menas en chunks por un **Sistema de Arqueología Geológica** interactivo. Esto elimina el lag generado al cargar o crear chunks, evita cortes visuales en el terreno y garantiza total compatibilidad con generadores de mundos personalizados.

---

## 1. Cómo Funciona

1. Equípate con una **Brocha** en la mano principal (la brocha vanilla `BRUSH` o la `Brocha de Prospector` / `Prospector Brush`).
2. **Mantén presionado el clic derecho** de forma continua sobre un bloque geológico válido.
3. Cada pulso avanza el progreso de excavación arqueológica emitiendo sonidos de cepillado (`ITEM_BRUSH_BRUSHING_GENERIC`) y partículas de polvo de piedra.
4. Al completar la duración requerida (por defecto: 30 ticks = 1.5 segundos), el mineral en bruto es extraído junto a un repique geológico (`BLOCK_AMETHYST_BLOCK_CHIME`) y partículas brillantes.

---

## 2. Bloques Objetivo por Dimensión

| Dimensión | Bloques Válidos | Minerales Obtenibles | Probabilidad Base de Éxito |
|---|---|---|---|
| **Overworld** | `STONE`, `COBBLESTONE`, `DEEPSLATE`, `COBBLED_DEEPSLATE`, `ANDESITE`, `DIORITE`, `GRANITE`, `TUFF` | 30 Minerales del Overworld | **45%** |
| **The Nether** | `NETHERRACK`, `BLACKSTONE`, `BASALT` | 30 Minerales del Nether | **40%** |
| **The End** | `END_STONE` | 30 Minerales del End | **35%** |

---

## 3. Degradación Geológica y Protección Anti-Macros

* **Sistema de Desgaste (Degradación)**: Las formaciones rocosas sufren erosión realista tras cada excavación:
  * Piedra $\rightarrow$ Adoquín (Cobblestone) $\rightarrow$ Grava $\rightarrow$ Aire
  * Pizarra Profunda (Deepslate) $\rightarrow$ Pizarra Labrada $\rightarrow$ Grava $\rightarrow$ Aire
  * Piedra Negra (Blackstone) $\rightarrow$ Basalto $\rightarrow$ Netherrack $\rightarrow$ Aire
  * Piedra del End (End Stone) $\rightarrow$ Aire
* **Protección contra Macros / Grindeo**: Cada bloque posee un enfriamiento interno de 15 segundos para evitar explotación con auto-clickers o macros automatizados.

---

## 4. Brocha de Prospector (`mvtink_brush_prospector`)

Una herramienta arqueológica especializada de supervivencia fabricada con oro, cobre, un fragmento de amatista y una brocha vanilla:

* **+40% Velocidad de Excavación**: Completa la prospección en solo 3 pulsos (<1 segundo).
* **+15% Bono a Probabilidad de Éxito**: Overworld 60%, Nether 55%, The End 50%.
* **Suerte Geológica**: **Duplica la probabilidad (2x)** de obtener minerales Raros, Épicos y Legendarios.
* **Cerdas Reforzadas**: **50% de probabilidad** de anular el consumo de durabilidad tras cada extracción completada.
* **Efectos Visuales**: Destellos dorados y partículas mágicas de encantamiento durante el proceso.
