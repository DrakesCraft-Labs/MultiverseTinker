# ⚒️ Estructura Multibloque de la Forja Multiverse

La **Forja Multiverse** es una estructura metalúrgica monumental ($11 \times 7 \times 11$) erigida en torno a un Yunque central. Al ser validada, la estructura se activa con secuencias de partículas dinámicas, dota al yunque de un aura ambiental continua, y reemplaza la interfaz estándar del yunque con la **GUI personalizada de la Forja Multiverse** para forjar piezas y ensamblar herramientas modulares avanzadas.

---

## 🏛️ Arquitectura y Dimensiones

* **Dimensiones**: $11 \times 11$ bloques en planta, $7$ bloques de altura ($11 \times 7 \times 11$).
* **Núcleo Central**: Yunque central (`minecraft:anvil`) ubicado en la capa $Y=1$ (coordenadas relativas $(0, 0, 0)$).
* **Composición**: Total de **243 bloques sólidos**:
  * **32** Ladrillos de Toba Cincelados (`minecraft:chiseled_tuff_bricks`)
  * **29** Baldosas de Pizarra Profunda (`minecraft:deepslate_tiles`)
  * **16** Ladrillos de Pizarra Profunda (`minecraft:deepslate_bricks`)
  * **97** Losas de Ladrillos de Toba (`minecraft:tuff_brick_slab`)
  * **48** Escaleras de Ladrillos de Toba (`minecraft:tuff_brick_stairs`)
  * **20** Bloques de Fuente de Lava (`minecraft:lava`) albergados en 4 pilares en las esquinas
  * **1** Yunque Central (`minecraft:anvil`, `chipped_anvil` o `damaged_anvil`)

### Tolerancia y Rotaciones
La forja admite las **4 rotaciones cardinales** ($0^\circ, 90^\circ, 180^\circ, 270^\circ$). Los jugadores pueden orientar el frontal hacia el Norte, Sur, Este u Oeste. Las losas, escaleras y variantes de yunque cuentan con un margen de tolerancia para que pequeñas variaciones de orientación no frustren la activación.

---

## ⚡ Activación y Simulación de Partículas

Al hacer clic derecho en un yunque situado dentro de una estructura válida:
1. **Barrer de Validación**:
   * **Fase 1**: Llamas de alta temperatura y ascuas de almas ascienden por los 4 pilares de lava de las esquinas.
   * **Fase 2**: Partículas radiantes de encantamiento (`ENCHANT`, `WAX_ON`) barren hacia el interior por el suelo convergiendo en el yunque.
   * **Fase 3**: Destello fulgurante (`TOTEM_OF_UNDYING` + `TRIAL_SPAWNER_DETECTION_OMINOUS`) en el yunque acompañado de sonidos de faro y yunque resonante.
   * **Título en Pantalla**: Muestra `MULTIVERSE FORGE - ¡Estructura validada! La Forja está activa.`
2. **Aura Ambiental del Yunque**:
   * El yunque central emite constantemente una pequeña aura orbital de `SMALL_FLAME`, `WAX_OFF` y humo cálido con crujidos tenues de combustión.
   * Si el yunque o pilares clave se destruyen, la forja se desactiva de forma automática.

---

## 🖥️ GUI Personalizada de la Forja

Al interactuar con el yunque validado, se cancela la interfaz de yunque de Minecraft y se abre la **GUI Personalizada de la Forja Multiverse** (54 ranuras).

### 1. Sección Izquierda: Forjado de Partes
* **Ranura 10 (Entrada de Material)**: Coloca un Balde de Metal Fundido (`mvtink_<id>_molten_bucket`).
* **Ranura 12 (Entrada de Molde / Cast)**: Coloca el molde correspondiente:
  * **Molde de Cabeza** (`mvtink_cast_head`): Produce la Cabeza de Herramienta (`mvtink_<id>_head`).
  * **Molde de Palo / Varilla** (`mvtink_cast_rod`): Produce el Mango o Varilla (`mvtink_<id>_rod`).
  * **Molde de Mango / Unión** (`mvtink_cast_binding`): Produce la Unión (`mvtink_<id>_binding`).
  * También compatible con moldes de Lingote, Pepita y Bloque.
* **Ranura 20 (Golpe de Martillo)**: Clic para martillar el metal fundido. Devuelve un balde vacío y deposita la pieza forjada en la **Ranura 21**.
* **Ranura 18 (Tallador Rápido de Moldes)**: Haz clic con Ladrillos de Arcilla en el inventario para tallar moldes al instante.

### 2. Sección Derecha: Ensamblado de Herramientas Modulares
* **Ranura 24 (Selector de Tipo de Herramienta)**: Clic para alternar entre **Pico**, **Espada**, **Hacha**, **Pala** y **Azada**.
* **Ranura 30 (Cabeza)**: Determina el daño de ataque, velocidad de minado y rasgo principal.
* **Ranura 32 (Palo / Mango)**: Determina el multiplicador de durabilidad y rasgo de agarre.
* **Ranura 34 (Unión / Guarda)**: Añade durabilidad auxiliar y rasgo secundario de utilidad.
* **Ranura 41 (Botón de Ensamblaje)**: Fusiona las 3 partes en una **Herramienta Modular** terminada (`mvtink_is_modular_tool`) que hereda todos los rasgos físicos de los 3 minerales.

---

## 🛠️ Comandos Administrativos

* `/mvtink forge build [0|90|180|270]` — Construye al instante la estructura completa en la ubicación del jugador.
* `/mvtink forge check` — Analiza el yunque al que estás mirando y reporta el porcentaje de coincidencia y bloques faltantes.
* `/mvtink forge gui` — Abre directamente la interfaz gráfica de la forja para administradores.

> 📖 El **Codex de Aleaciones** (`/mvtink codex`) no necesita ningún permiso: es el menú público de referencia para todos los jugadores, y también se abre desde el botón del libro en la pestaña del Crisol de Aleaciones. Su pestaña **Catálogo de Minerales** navega tanto la lista curada de materiales como la lista plana de **todos los ids de ítem registrados** (**2.656**), filtrable por tipo de ítem.
