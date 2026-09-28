# ⚒ Guía del GUI de la Forja del Multiverso y Equipo Modular

La **Forja del Multiverso** incluye una interfaz gráfica de usuario completa dividida en 5 secciones interactivas, accesible al hacer clic derecho en el yunque central de una estructura de forja activa.

---

## 🧭 Barra de Navegación Superior (Fila 0)

La fila superior (casillas 0–8) permite cambiar de sección en cualquier momento:
- **Casilla 0**: `[ 1. Codex & Guide ]` - Códice con guías del multiverso, mecánicas y estado de la estructura.
- **Casilla 2**: `[ 2. Molds & Parts ]` - Tallado rápido de moldes y forjado de piezas multimaterial.
- **Casilla 4**: `[ 3. Alloy Crucible ]` - Crisol para fundir y mezclar 2 materiales en aleaciones.
- **Casilla 6**: `[ 4. Weapon Assembly ]` - Ensamblado de armas modulares (Arcos, Espadas, Tridentes, Lanzas, Mazos, Ballestas y Escudos).
- **Casilla 8**: `[ 5. Tool Assembly ]` - Ensamblado de herramientas modulares (Picos, Hachas, Azadas, Palas y Caña de pescar).

---

## 📖 Sección 1: Códice Informativo y Guías
- Proporciona libros interactivos con información de:
  1. **Estructura Multibloque**: 243 bloques, yunque central, 4 columnas de lava esquineras, toba cincelada, baldosas de pizarra profunda y escaleras/losas de ladrillos de toba.
  2. **Forja Multimaterial**: Reglas de concentración (1 a 3 materiales por pieza con división porcentual de rasgos y estadísticas).
  3. **Crisol de Aleaciones**: Las 16 recetas de aleaciones y sus bonificadores.
  4. **Evolución por Rarezas/Tiers**: Progresión de Madera a Netherita mediante bajas o bloques rotos.
  5. **Habilidades Únicas**: Habilidades especializadas para cada una de las 7 armas y 5 herramientas.

---

## 🔨 Sección 2: Moldes y Forja de Piezas Multimaterial

### Tallado Rápido de Moldes
Haz clic en cualquiera de los moldes de la fila superior teniendo **1 Ladrillo de Arcilla** en tu inventario para tallar un molde reutilizable:
- **Molde de Cabeza** (`mvtink_cast_head`): Cabezas de armas y herramientas (Cabeza).
- **Molde de Mango** (`mvtink_cast_rod`): Mangos y varas (Mango).
- **Molde de Pomo** (`mvtink_cast_binding`): Pomos, uniones y contrapesos (Pomo).
- **Molde de Brazos del Arco** (`mvtink_cast_bow_limbs`): Brazos del arco (Brazos del Arco).
- **Molde de Cuerda Tensora** (`mvtink_cast_bowstring`): Cuerda elástica tensora (Cuerda Tensora).
- **Molde de Placa Frontal** (`mvtink_cast_shield_plate`): Placa frontal del escudo (Placa Frontal).
- **Molde de Umbo / Armazón** (`mvtink_cast_shield_boss`): Umbo central y armazón (Umbo / Armazón).

### Forja Multimaterial (1 a 3 Materiales)
- Coloca **1 Molde** en la **Casilla 28**.
- Coloca hasta 3 materiales (Lingotes, Gemas, Minerales o Cubos de metal fundido) en las **Casillas 30, 31 y 32**:
  - **1 Material**: 100% de concentración (efecto y estadísticas completas).
  - **2 Materiales**: 50% / 50% (por ejemplo, 50% Diamante + 50% Cuarzo otorga 50% de probabilidad o potencia a cada rasgo).
  - **3 Materiales**: 33.3% / 33.3% / 33.4% dividido equitativamente entre los 3 rasgos.
- Presiona el botón **⚒ Strike Anvil to Forge Part** (Casilla 38) para forjar la pieza terminada en la **Casilla 42**.
- ¡Los moldes son **reutilizables** y nunca se consumen!

---

## 🧪 Sección 3: Crisol de Aleaciones (Mezcla de Materiales)
- Coloca el Material 1 en la **Casilla 20** y el Material 2 en la **Casilla 24**.
- Presiona **🔥 Melt & Blend Alloy** (Casilla 31).
- Obtendrás **2x Lingotes de la Aleación** terminada en la **Casilla 33**.
- Consulta la [Guía de Mezcla de Materiales](Mezcla-de-Materiales.md) para ver las 16 recetas completas.

---

## ⚔ Sección 4: Construcción de Armas Modulares

Haz clic en el **Selector de Arma** en la **Casilla 13** para alternar entre los 7 tipos de armas:

### Armas de 3 Piezas (Cabeza, Mango, Pomo)
- **Espada Modular (Broadsword)**: Cabeza (Hoja) + Mango (Empuñadura) + Pomo (Guarda/Pomo).
- **Ballesta Pesada Modular**: Cabeza (Arco frontal) + Mango (Culata) + Pomo (Mecanismo/Gatillo).
- **Tridente Ancestral Modular**: Cabeza (Puntas) + Mango (Asta) + Pomo (Contrapeso).
- **Lanza Cinética Modular**: Cabeza (Punta de lanza) + Mango (Asta larga) + Pomo (Regatón).
- **Mazo de Guerra Modular**: Cabeza (Cabeza pesada) + Mango (Mango reforzado) + Pomo (Pomo con aletas).

### Armas de 2 Piezas
- **Arco Modular (Longbow)**: Brazos del arco + Cuerda tensora.
- **Escudo Torre Modular**: Placa frontal + Umbo / Armazón.

### Evolución de Armas por Bajas y Habilidades Especiales
- Toda arma empieza en **Rareza de Madera (Wood Tier)** con `0` bajas.
- Derrotar enemigos incrementa el contador de bajas y sube el arma de nivel:
  `Madera → Piedra (15 bajas) → Cobre (40) → Hierro (80) → Oro (150) → Diamante (300) → Netherite (600)`.
- **Habilidades Especiales en Combate**:
  - **Mazo de Guerra**: Golpes en caída desatan una onda de choque sísmica en el suelo que daña y lanza por los aires a los enemigos cercanos.
  - **Arco**: Las flechas heredan los rasgos elementales de los brazos y de la cuerda.
  - **Ballesta Pesada**: Los proyectiles ignoran el 30% de la armadura enemiga y desatan impacto explosivo.
  - **Tridente**: Ataques cuerpo a cuerpo o arrojados bajo el agua o lluvia invocan rayos hidráulicos (+5.0 de daño adicional).
  - **Lanza Cinética**: Alcance de ataque extendido y +30% de daño al golpear en carrera (embestida de justa).
  - **Escudo Torre**: Bloquear refleja el 35% del daño al atacante y le aplica los efectos elementales del escudo.
  - **Espada**: Ataques de barrido encadenan los rasgos elementales a todos los enemigos adyacentes.

---

## ⛏ Sección 5: Construcción de Herramientas Modulares

Haz clic en el **Selector de Herramienta** en la **Casilla 13** para alternar entre los 5 tipos de herramientas:
- **Pico Modular**: Cabeza (Cabeza de pico) + Mango (Mango) + Pomo (Pomo/Unión).
- **Hacha de Batalla Modular**: Cabeza (Filo de hacha) + Mango (Mango) + Pomo (Pomo/Unión).
- **Pala Excavadora Modular**: Cabeza (Pala) + Mango (Mango) + Pomo (Pomo/Unión).
- **Guadaña / Azada Modular**: Cabeza (Hoja de azada) + Mango (Mango) + Pomo (Pomo/Unión).
- **Caña de Pescar Modular**: Cabeza (Punta y sedal) + Mango (Cuerpo de caña) + Pomo (Carrete y agarre).

### Evolución de Herramientas por Bloques Rotos y Habilidades Especiales
- Toda herramienta empieza en **Rareza de Madera (Wood Tier)** con `0` bloques rotos.
- Romper bloques incrementa el contador y sube la herramienta de nivel:
  `Madera → Piedra (50 bloques) → Cobre (150) → Hierro (350) → Oro (750) → Diamante (1500) → Netherite (3000)`.
- **Habilidades Especiales de Minería**:
  - **Pico**: Resonancia de Vetas profundas otorga un 15% de probabilidad de soltar mineral extra y recibir Prisa minera I por 6s.
  - **Hacha**: Tala columnas enteras de madera y deshabilita los escudos enemigos en golpes críticos.
  - **Pala Excavadora**: Minar agachado rompe un área de 3x3 de bloques sueltos similares (tierra, arena, grava).
  - **Azada**: Cosecha cultivos maduros en un área de 3x3 y replanta automáticamente las semillas de tu inventario.
  - **Caña de Pescar**: Dragado abisal otorga un 15% de probabilidad de pescar minerales raros del fondo de las aguas.
