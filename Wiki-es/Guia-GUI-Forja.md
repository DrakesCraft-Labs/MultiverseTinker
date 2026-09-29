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

### Selector Simétrico de Moldes y Tallado Directo
La fila 1 cuenta con controles limpios y completamente intuitivos:
- **Ranura 12**: Molde Anterior (`◀`) - Cambia al tipo de molde anterior.
- **Ranura 13**: **⚒ Tallar Molde** (Muestra el molde seleccionado; **al hacer clic lo tallas directamente en tu inventario** consumiendo **1 Ladrillo de Arcilla**).
- **Ranura 14**: Siguiente Molde (`▶`) - Cambia al tipo de molde siguiente.

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
- Cada par de los **110 minerales mezclables** (97 geológicos + 13 vanilla distintos de la netherita) produce su propia aleación: 5.995 pares mezclables, de los que 14 coinciden con una receta legendaria. La netherita vanilla es el único material vanilla que no se puede mezclar libremente (ya es una aleación), pero sí funciona dentro de sus dos recetas curadas (**Cinder Steel** y **Netherita Cósmica**), así que el crisol puede producir **5.997 aleaciones de minerales** en total.
- **Fusión primordial**: una aleación legendaria puede volver a entrar en el crisol junto a otra aleación, cualquier mineral o uno de los **12 objetos catalizadores vanilla** (Estrella del Nether, Aliento de Dragón, Hielo Azul, Hielo Compacto, Fragmento de Eco, Corazón del Mar, Tótem de Inmortalidad, Cristal del End, Ancla de Reaparición, Cristales de Prismarina, Cúmulo de Amatista, Escombros Antiguos). La aleación primordial resultante es de rareza Legendary, supera a cualquier compuesta y desbloquea los ultimates primordiales y los 9 estados de armadura primordiales. Ver [Aleaciones Primordiales](Aleaciones-Primordiales.md). Un servidor nuevo expone **8.085** combinaciones forjables, hasta **103.781** cuando se descubren todas las compuestas.
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
  - **Mazo de Guerra (War Mace)**: *Golpe Sísmico* — Al atacar cayendo/saltando o agachado, libera una onda expansiva que inflige el 65% de daño a todos los enemigos en 4 bloques, los lanza por el aire y les transmite los rasgos elementales.
  - **Arco Largo (Longbow)**: *Andanada Imbuida* — Las flechas disparadas heredan íntegramente los rasgos elementales de las extremidades y cuerda tensora (quemando, envenenando, ralentizando, etc.), y un arco **enfocado** dispara una **flecha de réplica** contra el mismo objetivo: 25% de probabilidad base que sube hasta el 60% con el 100% de enfoque de esencia. La flecha de réplica no lleva datos de composición, así que el perk salta una sola vez por disparo.
  - **Ballesta Pesada (Heavy Crossbow)**: *Piercing Velocity* — Los virotes perforan armaduras (+6.0 daño directo) y provocan una explosión cinética abrasiva (al impactar a un enemigo o a un bloque), dañando en 5.0 y empujando a todas las criaturas en 4 bloques.
  - **Tridente Anciano (Elder Trident)**: *Oleada Hidráulica* — Tanto cuerpo a cuerpo como al ser arrojado, si el objetivo o el lanzador se encuentran en agua o bajo la lluvia, invoca un relámpago con trueno, partículas de agua y un estallido de +5.0 de daño hidráulico adicional.
  - **Lanza Cinética (Kinetic Spear)**: *Embestida de Justa* — Posee el modelo y alcance cuerpo a cuerpo extendido de la lanza oficial vanilla 1.21.11; al atacar esprintando o montado sobre un caballo/camello, asesta un golpe crítico con +30% de daño y un fuerte empuje hacia adelante.
  - **Escudo Torre (Tower Shield)**: *Barrera de Represalia* — Bloquear ataques cuerpo a cuerpo o proyectiles (flechas, tridentes) devuelve un 35% del daño al atacante, lo empuja hacia atrás y le aplica los rasgos elementales de la placa frontal y el umbón.
  - **Espada Ancha (Broadsword)**: *Tajo Elemental Enlazado* — Los tajos de barrido causan el 40% de daño a los enemigos adyacentes y propagan todos los rasgos elementales activos a cada uno de ellos.
- **Perks según los Materiales**: cada ventaja anterior se **nombra e impulsa según el mineral de la cabeza** (una cabeza de Cobalto da *Infernal Piercing Velocity*, una de Piedra del Vacío da *Void Piercing Velocity*), mientras la empuñadura y el pomo fijan el porcentaje de **Essence Focus** del lore. La esencia dominante se canaliza en el golpe primario del perk, por lo que armas del mismo tipo forjadas con minerales distintos combaten de forma diferente.
- **Cuándo salta cada rasgo**: cada fila de rasgo del lore indica el momento exacto en que esa arma lo dispara — `on sweep`, `on arrow hit` (arco), `on bolt impact`, `on surge`, `on thrust`, `on smash` y `on block` —, así que una espada ancha, un arco y un escudo torre nunca muestran el mismo bloque de rasgos.

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
- **Ventajas de Minería**: el mecanismo pertenece al tipo de herramienta, la esencia a sus minerales. La cabeza fija la identidad elemental (y por tanto el nombre del perk y su efecto extra), el mango y el pomo fijan el Essence Focus:
  - **Pico** (*Vein Resonance*): 15% de probabilidad de minerales extra y Prisa minera. Una cabeza de Cobalto imprime *Infernal Vein Resonance*; una de Piedra del Vacío, *Void Vein Resonance*.
  - **Hacha de Batalla** (*Lumber Cleave*): Derriba el tronco completo y desactiva escudos en golpes críticos.
  - **Pala Excavadora** (*Seismic Tremor*): Excava un área de 3x3 de tierra, arena y grava al agacharse.
  - **Guadaña** (*Harvest Scythe*): Cosecha cultivos maduros en 3x3 y replanta automáticamente desde el inventario.
  - **Caña de Pescar** (*Abyssal Dredge*): 15% de probabilidad de pescar minerales geológicos raros.
  - Cada herramienta imprime `✦ Tool Perk: <Esencia> <Mecánica>` más su propia línea `• Essence Focus: <Esencia> essence (<n>%)`, y sus filas de rasgo indican cuándo saltan: `while mining`, `while chopping`, `while digging`, `while harvesting` o `while fishing`.

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

Esos tres números no son decorativos: los modificadores de armadura de la pieza se sustituyen por la **Defensa**, la **Dureza** y la resistencia al empuje calculadas, atadas al hueco en el que se lleva (ver [Configuración](Configuracion.md#cómo-protege-la-armadura-modular)).

### Evolución de Tiers de Armaduras
- Cada pieza inicia en **Tier Cuero (Leather Tier)** (`0` daño absorbido).
- Al absorber daño en combate, la armadura acumula progreso y evoluciona en este orden exacto:
  `Cuero (0 daño) → Cobre (50 daño) → Malla (150 daño) → Hierro (350 daño) → Oro (750 daño) → Diamante (1500 daño) → Netherite (3000 daño)`.
- Conforme evoluciona, el material base de Minecraft se transforma automáticamente (Cuero → Malla → Hierro → Oro → Diamante → Netherite) y la pieza **recalcula su propia protección**: la Defensa (ranura base + placa + tier) y la Dureza (ranura base + forro + tier) que imprime su lore crecen con cada tier, y esos números calculados — no los del material vanilla — son los que aplica el servidor. Una pieza con placa de diamante no defiende como el diamante solo por estar construida sobre él.
- **Ventajas Especiales de Armadura**: la ranura posee la defensa, el mineral de la placa posee su esencia:
  - **Casco** (*Cranium Ward*): Reduce el daño crítico a la cabeza e inmunidad a peligros ambientales.
  - **Pechera** (*Kinetic Dampener*): Absorbe el 25% de impactos fuertes y libera energía defensiva.
  - **Pantalones** (*Stride Momentum*): Reduce el agotamiento al correr y acelera la recuperación de movimiento.
  - **Botas** (*Feathered Grounding*): Anula hasta el 50% del daño por caída y previene resbalones.
  - Una pechera de Piedra del Vacío imprime *Void Kinetic Dampener* y una Infernal *Infernal Kinetic Dampener*: mismo mecanismo de ranura, esencia y reacción distintas, y **Essence Focus** distinto. Las filas de rasgo de armadura se etiquetan `when struck`, porque la armadura siempre responde a un golpe.

---

> ⚙️ **Ancho del tooltip**: las filas largas de perks y rasgos se envuelven para que nada se corte en pantalla. El ancho de fila, el presupuesto más amplio de la cabecera y el interruptor de la función viven en `config.yml` — consulta la **[Referencia de Configuración](Configuracion.md)**.

---

## ✨ Animaciones Exclusivas

Los perks son solo la mitad de la promesa: cada tipo de equipo posee además una **animación exclusiva** que salta justo cuando su perk dispara. Ningún patrón, par de partículas ni sonido se comparte entre dos tipos — un test hace fallar el build si alguna vez ocurre, así que ninguna familia puede verse más terminada que otra.

* **Espada Ancha** — *Sweeping Arc*: un arco horizontal se talla en el aire cada vez que el barrido encadena un enemigo.
* **Arco Largo** — *Volley Trail*: una estela punteada te une con la flecha que acaba de impactar.
* **Ballesta Pesada** — *Piercing Lance*: una línea tensa de chispas atraviesa el punto de impacto del virote.
* **Tridente Anciano** — *Hydraulic Surge*: una columna ascendente de agua y chispas envuelve al portador.
* **Lanza Cinética** — *Jousting Thrust*: una franja de baja presión marca el alcance de la estocada.
* **Mazo de Guerra** — *Seismic Smash*: un anillo de choque se rompe hacia fuera desde el impacto.
* **Escudo Torre** — *Retaliation Bulwark*: una muralla curva de luz defensiva se alza frente a quien bloquea.
* **Pico** — *Vein Resonance*: una vena de luz recorre la cara del mineral extraído.
* **Hacha de Batalla** — *Lumber Cleave*: astillas y hojas estallan hacia los lados del tronco talado.
* **Pala Excavadora** — *Seismic Tremor*: un anillo de polvo cabalga sobre la tierra removida.
* **Guadaña** — *Harvest Swirl*: chispas de cosecha espiralan sobre los cultivos segados.
* **Caña de Pescar** — *Abyssal Dredge*: agua y burbujas gotean por la línea al subir algo.
* **Casco** — *Cranium Halo*: un halo de luz defensiva se cierra alrededor de tu cabeza.
* **Pechera** — *Kinetic Dome*: un domo de fuerza amortiguadora se hincha de la placa y engulle el golpe.
* **Pantalones** — *Stride Coil*: una espiral de impulso se enciende alrededor de las piernas al correr.
* **Botas** — *Grounding Puff*: un colchón de aire y escarcha sale bajo las suelas al aterrizar.

Cada animación se **tiñe con el color del mineral dominante** del ítem, así la coreografía pertenece al tipo y el tono al build. La intensidad, el sonido y el enfriamiento viven en `config.yml` — consulta la **[Referencia de Configuración](Configuracion.md)** para la tabla completa de partículas y sonidos.

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
