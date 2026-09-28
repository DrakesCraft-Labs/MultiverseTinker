# ⚒ Guía de la Interfaz (GUI) y Equipamiento de la Forja Multiverse

La **Forja Multiverse** cuenta con una interfaz gráfica completa y reorganizada de 6 secciones, accesible al hacer clic derecho en el yunque central de una estructura Multibloque activa.

---

## 🧭 Barra Superior de Navegación (Fila 0)

La fila superior (ranuras 0 a 8) contiene controles persistentes perfectamente alineados:
- **Ranura 0**: Panel de Cristal Borde.
- **Ranura 1**: `[ 1. Códice y Guía ]` - Enciclopedia dentro del juego, guías de tiers y estructura multibloque.
- **Ranura 2**: `[ 2. Moldes y Partes ]` - Selector compacto de moldes y forjado de componentes multimaterial.
- **Ranura 3**: `[ 3. Crisol de Aleaciones ]` - Estación para mezclar 2 materiales y crear lingotes de aleación.
- **Ranura 4**: Estandarte divisor central de la Forja Multiverse.
- **Ranura 5**: `[ 4. Ensamblado de Armas ]` - Taller para construir armas modulares con selector cíclico.
- **Ranura 6**: `[ 5. Ensamblado de Herramientas ]` - Taller para construir herramientas modulares.
- **Ranura 7**: `[ 6. Ensamblado de Armaduras ]` - ¡Nueva sección para forjar y evolucionar armaduras modulares!
- **Ranura 8**: Panel de Cristal Borde.

---

## 📖 Sección 1: Códice Informativo y Mecánicas
Muestra guías interactivas que explican:
1. **Estructura Multibloque**: 243 bloques, yunque central, 4 columnas de lava en esquinas, toba cincelada, baldosas de pizarra profunda y escaleras/losas de toba.
2. **Forjado Multimaterial**: Reglas de concentración (1, 2 o 3 materiales por parte con potencia proporcional de rasgos).
3. **Crisol de Aleaciones**: Las 16 recetas de aleaciones registradas.
4. **Tiers de Evolución**: Progresión de Madera a Netherite mediante bajas (armas), bloques rotos (herramientas) y daño absorbido (armaduras).
5. **Ventajas Especializadas**: Mecánicas exclusivas para las 7 armas, 5 herramientas y 4 piezas de armadura.

---

## 🔨 Sección 2: Moldes y Forjado de Partes Multimaterial

### Selector Simétrico de Moldes
La fila 1 cuenta con controles limpios e intuitivos idénticos a los selectores de armas, herramientas y armaduras:
- **Ranura 12**: Molde Anterior (`◀`)
- **Ranura 13**: **Selector de Molde** (Muestra el molde activo; clic izquierdo para avanzar, clic derecho para retroceder).
- **Ranura 14**: Siguiente Molde (`▶`)
- **Ranura 16**: **⚒ Tallar Molde** (Consume **1 Ladrillo de Arcilla** del inventario para obtener el molde seleccionado).

#### Moldes Disponibles:
- **Head Cast** (`mvtink_cast_head`): Cabezas de herramientas y hojas de armas.
- **Handle Cast** (`mvtink_cast_rod`): Mangos y astas.
- **Pommel Cast** (`mvtink_cast_binding`): Pomos, guardas y uniones.
- **Bow Limbs Cast** (`mvtink_cast_bow_limbs`): Brazos de arcos.
- **Bowstring Mold** (`mvtink_cast_bowstring`): Cuerdas tensoras.
- **Shield Plate Cast** (`mvtink_cast_shield_plate`): Placas frontales de escudo.
- **Shield Boss Cast** (`mvtink_cast_shield_boss`): Umbón y armazón central.
- **Armor Plate Cast** (`mvtink_cast_armor_plate`): Placas pesadas de armadura.
- **Armor Lining Cast** (`mvtink_cast_armor_lining`): Malla interior y acolchado.
- **Armor Trim Cast** (`mvtink_cast_armor_trim`): Ribetes, remaches y cierres.
- **Moldes de Lingote / Pepita / Bloque**: Conversión de metales fundidos.

### Forjado Multimaterial con Separación Física y 3 Materiales Obligatorios
La zona de forja en la fila 3 está visualmente y físicamente dividida mediante barrotes de hierro reforzados para evitar confusiones:
- **Ranura 28**: **Molde / Cast** Requerido (¡Reutilizable, nunca se destruye!).
- **Ranura 29**: ▌ Barrotes de Hierro (Separador físico).
- **Ranuras 30, 31 y 32**: **Los 3 Materiales OBLIGATORIOS** (Lingotes, Gemas, Minerales o Cubos de Metal Fundido).
  - Los 3 materiales son estrictamente necesarios para fundir y templar la pieza.
  - La concentración se divide de manera proporcional (33.3% / 33.3% / 33.4%), permitiendo combinar hasta 3 rasgos de minerales diferentes en una sola pieza.
- **Ranura 33**: ▌ Barrotes de Hierro (Separador físico).
- **Ranura 34**: **Ranura de Salida** (Muestra la parte forjada lista para recoger).
- Haz clic en el botón central **⚒ Golpear Yunque y Forjar Parte** (Ranura 40) para completar el forjado.

---

## 🧪 Sección 3: Crisol de Aleaciones
- Coloca el Material 1 en la **Ranura 29** y el Material 2 en la **Ranura 33**.
- Haz clic en **♨ Encender Crisol y Fundir Aleación** (Ranura 31).
- Obtén **2x Lingotes de Aleación Terminados** en la **Ranura 40**.
- Haz clic en el **Códice de Recetas** (Ranura 49) para consultar en el chat las 16 fórmulas registradas.
- Consulta la [Guía de Mezcla de Materiales](Mezcla-de-Materiales.md) para más detalles.

---

## ⚔ Sección 4: Ensamblado de Armas Modulares

Haz clic en el **Selector de Armas** (Ranura 13) para alternar entre los 7 tipos:

### Armas de 3 Partes (Cabeza, Mango, Pomo)
- **Espada Ancha**: Cabeza (Hoja) + Mango (Empuñadura) + Pomo (Guarda).
- **Ballesta Pesada**: Cabeza (Arco) + Mango (Culata) + Pomo (Mecanismo).
- **Tridente Anciano**: Cabeza (Puntas) + Mango (Asta) + Pomo (Contrapeso).
- **Lanza Cinética**: Cabeza (Punta) + Mango (Asta Larga) + Pomo (Regatón).
- **Mazo de Guerra**: Cabeza (Maza Pesada) + Mango (Asta Reforzada) + Pomo (Pomo Alargado).

### Armas de 2 Partes
- **Arco Largo**: Brazos del Arco + Cuerda Tensora.
- **Escudo Torre**: Placa Frontal + Umbón Central.

### Evolución de Tiers de Armas
- Cada arma inicia en **Tier Madera** (`0` bajas).
- Derrotar enemigos incrementa el contador de bajas y avanza el arma:
  `Madera → Piedra (15 bajas) → Cobre (40) → Hierro (80) → Oro (150) → Diamante (300) → Netherite (600)`.
- **Ventajas de Combate Únicas**:
  - **Mazo de Guerra**: Caídas de ataque generan ondas de choque sísmicas en área.
  - **Arco Largo**: Las flechas disparadas heredan los rasgos elementales del arco.
  - **Ballesta Pesada**: Los virotes ignoran armadura y causan explosión cinética.
  - **Tridente Anciano**: Lanza rayos hidráulicos en agua o lluvia (+5.0 daño).
  - **Lanza Cinética**: Alcance extendido y +30% daño al atacar esprintando.
  - **Escudo Torre**: Refleja el 35% del daño bloqueado hacia el atacante.
  - **Espada Ancha**: Los barridos propagan los rasgos elementales a enemigos adyacentes.

---

## ⛏ Sección 5: Ensamblado de Herramientas Modulares

Haz clic en el **Selector de Herramientas** (Ranura 13) para alternar entre los 5 tipos:
- **Pico Modular**: Cabeza + Mango + Pomo.
- **Hacha de Batalla**: Cabeza + Mango + Pomo.
- **Pala Excavadora**: Cabeza + Mango + Pomo.
- **Guadaña (Azada)**: Cabeza + Mango + Pomo.
- **Caña de Pescar**: Cabeza + Mango + Pomo.

### Evolución de Tiers de Herramientas
- Inicia en **Tier Madera** (`0` bloques rotos).
- Minar bloques avanza la herramienta:
  `Madera → Piedra (50 bloques) → Cobre (150) → Hierro (350) → Oro (750) → Diamante (1500) → Netherite (3000)`.
- **Ventajas de Minería**:
  - **Pico**: Resonancia de Veta Profunda (15% probabilidad de minerales extra y Prisa).
  - **Hacha**: Tala leños completos y desactiva escudos en golpes críticos.
  - **Pala**: Temblor Sísmico excava áreas de 3x3 al agacharse.
  - **Guadaña**: Cosecha cultivos en 3x3 y replanta automáticamente desde el inventario.
  - **Caña de Pescar**: Dragado Abisal (15% de pescar minerales geológicos raros).

---

## 🛡 Sección 6: Ensamblado de Armaduras Modulares (¡NUEVO!)

Haz clic en el **Selector de Armaduras** (Ranura 13) para alternar entre las 4 piezas:
- **Casco Modular**: Placa de Armadura + Malla Interior + Ribete.
- **Pechera Modular**: Placa de Armadura + Malla Interior + Ribete.
- **Pantalones Modulares**: Placa de Armadura + Malla Interior + Ribete.
- **Botas Modulares**: Placa de Armadura + Malla Interior + Ribete.

### Roles de los Componentes de Armadura
- **Placa de Armadura (Armor Plate)**: Blindaje exterior pesado. Determina los puntos de armadura base, durabilidad principal y rasgos defensivos primarios.
- **Malla Interior (Armor Lining)**: Malla de cota de malla y acolchado flexible. Determina la dureza de armadura (toughness) y rasgos secundarios.
- **Ribete de Armadura (Armor Trim)**: Refuerzos, remaches y hebillas. Determina la resistencia al empuje (knockback resistance) y rasgos de utilidad pasiva.

### Evolución de Tiers de Armaduras
- Cada pieza inicia en **Tier Cuero (Leather Tier)** (`0` daño absorbido).
- Al absorber daño en combate, la armadura acumula progreso y evoluciona en este orden exacto:
  `Cuero (0 daño) → Cobre (50 daño) → Malla (150 daño) → Hierro (350 daño) → Oro (750 daño) → Diamante (1500 daño) → Netherite (3000 daño)`.
- Conforme evoluciona, el material base de Minecraft se transforma automáticamente, aumentando drásticamente los puntos de armadura, durabilidad y dureza.
- **Ventajas Especiales de Armadura**:
  - **Casco (Cranium Ward)**: Reduce el daño crítico a la cabeza e inmunidad a peligros ambientales.
  - **Pechera (Kinetic Dampener)**: Absorbe el 25% de impactos fuertes y libera energía defensiva.
  - **Pantalones (Stride Momentum)**: Reduce el agotamiento al correr y acelera la recuperación de movimiento.
  - **Botas (Feathered Grounding)**: Anula hasta el 50% del daño por caída y previene resbalones.

---

## ⚡ Comando de Creación Directa (Admin)

Para administradores o pruebas rápidas sin necesidad de armar la estructura física de la Forja:
```bash
/mvtink craft <weapon|tool|armor> <type> <m1> <m2> [m3] [tier]
```
- **Categorías**:
  - `weapon`: `SWORD`, `BOW`, `TRIDENT`, `SPEAR`, `MACE`, `CROSSBOW`, `SHIELD`.
  - `tool`: `PICKAXE`, `AXE`, `HOE`, `SHOVEL`, `FISHING_ROD`.
  - `armor`: `HELMET`, `CHESTPLATE`, `LEGGINGS`, `BOOTS`.
- **Materiales**: Cualquier mineral o aleación del plugin (por ejemplo: `gold`, `diamond`, `ruby`, `borax`, `titanium`, `manyullyn`, etc.).
- **Tier Opcional**: `WOOD`, `STONE`, `COPPER`, `IRON`, `GOLD`, `DIAMOND`, `NETHERITE` (por defecto `WOOD`).
- *Ejemplo*: `/mvtink craft weapon SWORD gold ruby sapphire NETHERITE` genera una Espada Ancha de Netherite con 30% daño de oro, filo ígneo de rubí y congelación de zafiro.
