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

## 4. Comparativa: Brocha Estándar vs. Brocha de Prospector

MultiverseTinker distingue dos niveles de excavación con distribuciones de recompensa claramente diferenciadas:

| Característica / Recompensa | Brocha Normal Vanilla (`BRUSH`) | Brocha de Prospector (`mvtink_brush_prospector`) |
|---|---|---|
| **Velocidad de Excavación** | Estándar (6 progreso / pulso) | **+40% más rápida** (10 progreso / pulso) |
| **Probabilidad Base de Éxito** | 45% Overworld / 40% Nether / 35% End | **+15% de bono** (60% / 55% / 50%) |
| **Suerte Rara / Épica / Legendaria** | Peso estándar ($1\times$) | **Multiplicador de peso $2\times$** |
| **Conservación de Cerdas** | Ninguna (1 durabilidad por ciclo) | **50% de probabilidad** de no gastar uso |
| **Mineral en Bruto (`mvtink_*_raw`)** | **70%** (se funde para hacer 1 Lingote) | **55%** |
| **Pepitas (`mvtink_*_nugget`)** | **30%** (1 Pepita) | **25%** (1 a 3 Pepitas) |
| **Bloque Compacto (`mvtink_*_block`)** | ❌ **0% (Nunca extrae bloques)** | ⭐ **20% (Jackpot: ¡Bloque completo de 9x!)** |

### Sinergia con el Molde de Bloques (`mvtink_cast_block`)
Mientras que la brocha normal solo permite recolectar material crudo para lingotes individuales o pequeñas pepitas, la Brocha de Prospector da la oportunidad de descubrir vetas densas y obtener directamente bloques del material. Esto da una utilidad real al **Molde de Bloques** en el sistema de caldero de fundición, permitiendo templar bloques masivos para almacenar y manipular metales de alta gama sin necesidad del crafteo manual $9\times$.
