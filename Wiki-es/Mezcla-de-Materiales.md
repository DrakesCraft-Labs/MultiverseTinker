# 🧪 Guía de Mezcla de Materiales y Aleaciones Metalúrgicas

La **Forja del Multiverso** cuenta con un **Crisol de Aleaciones** dedicado (Sección 3 de la interfaz GUI de la forja) capaz de sobrecalentar y fusionar dos minerales distintos (metales o cristales geológicos) en aleaciones personalizadas de alta especialización.

> ♻ **Mezcla universal**: se puede mezclar *cualquier* par de minerales distintos obtenidos con la **Brocha de Prospector** (geología del Overworld, Nether o End) o refinados de **menas vanilla de Minecraft**, no solo las 16 recetas legendarias. Cada par sintetiza su propia aleación con su propio rasgo, y ese rasgo se adapta a **armas, herramientas y armaduras** (ver [Afinidades de Rasgos](Afinidades-de-Rasgos.md)). Las aleaciones ya terminadas no pueden volver a mezclarse, evitando bucles infinitos en el crisol. Todo lo que forjas se guarda y se restaura al reiniciar.

> 📚 **¿Necesitas la lista completa?** Cada par mezclable — los **5.995** — con la aleación que forja y las esencias que hereda está en el **[Índice de Recetas](Indice-de-Recetas.md)**. Las fuentes, estadísticas y rasgos material por material están en la **[Referencia de Materiales](Fuentes-de-Materiales.md)**. Quedan por tanto **110 minerales mezclables** y **5.995 pares mezclables**, de los que 14 resuelven en una receta legendaria; la netherita vanilla es el único caso especial (ya está clasificada como aleación, por lo que solo se mezcla dentro de sus dos recetas curadas: **Cinder Steel** y **Netherita Cósmica**), lo que deja **5.997 aleaciones de minerales** en total en el crisol.

> 🌌 **Nivel primordial**: el crisol tiene una tercera categoría. Una **aleación legendaria** puede volver a fundirse — con otra legendaria, una compuesta, cualquier mineral o uno de los **12 catalizadores vanilla** (Estrella del Nether, Hielo Azul, Fragmento de Eco…) — produciendo una **aleación primordial** con stats superiores, ultimate propio y estado de armadura propio. Ver **[Aleaciones Primordiales](Aleaciones-Primordiales.md)**; con todas las compuestas descubiertas el crisol llega a **103.781** aleaciones distintas.

---

## ⚒ Cómo Funciona la Mezcla de Materiales

1. **Abrir el Crisol**:
   - Interactúa con el **Yunque central** de una Forja del Multiverso activa.
   - Haz clic en la pestaña **[ 3. Alloy Crucible ]** en la barra de navegación superior.
2. **Colocar los Componentes**:
   - Coloca el **Material 1** en la **Casilla 20** (Lingote, Gema, Mineral o Cubo con Líquido Fundido).
   - Coloca el **Material 2** en la **Casilla 24** (Lingote, Gema, Mineral o Cubo con Líquido Fundido).
3. **Fundir y Mezclar**:
   - Presiona el botón central **🔥 Melt & Blend Alloy** en la **Casilla 31**.
   - Si la combinación es una aleación válida, la resonancia térmica fundirá los materiales y producirá **2x Lingotes de la Aleación** resultante en la **Casilla 33**.
   - Si se usaron cubos de metal fundido, los cubos vacíos se devolverán a tu inventario.
4. **Uso en Construcción de Piezas**:
   - Los lingotes de aleación obtenidos pueden utilizarse inmediatamente en la **Sección 2 (Moldes y Piezas)** para forjar cabezas, mangos, pomos, brazos de arco, cuerdas o placas defensivas.

---

## 📜 Tabla Completa de las 16 Aleaciones

Las 16 recetas siguientes son **predefinidas**: siempre tienen prioridad y conservan su habilidad
exclusiva curada. Cualquier otro par legal de minerales brocha/vanilla produce una **aleación
compuesta dinámica** llamada `<Mineral A>-<Mineral B> Alloy`, heredando las afinidades de ambos
progenitores.

| Nombre de la Aleación | ID | Material 1 | Material 2 | Color Hex | Durabilidad | Velocidad | Daño | Nombre del Rasgo | Efecto del Rasgo |
| :--- | :--- | :--- | :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| **Bronze (Bronce)** | `mvtink_bronze` | Cobre (`mvtink_copper`) | Estaño (`mvtink_tin`) | `#cd7f32` | +350 | 7.5x | +5.5 | **Dense Temper** | Alta densidad estructural. +350 de durabilidad y -20% de empuje recibido. |
| **Electrum (Electro)** | `mvtink_electrum` | Oro (`mvtink_gold`) | Plata (`mvtink_silver`) | `#fff8a6` | +220 | 11.0x | +6.0 | **Lightning Conduit** | +25% de velocidad de ataque y descarga chispas de choque en golpes críticos. |
| **Invar** | `mvtink_invar` | Hierro (`mvtink_iron`) | Níquel (`mvtink_nickel`) | `#b0b8b0` | +450 | 8.0x | +6.5 | **Thermal Resilience** | Inmune al desgaste por fuego y lava, otorga +450 de durabilidad. |
| **Manyullyn** | `mvtink_manyullyn` | Cobalto (`mvtink_cobalt`) | Ardite (`mvtink_ardite`) | `#9b59b6` | +800 | 10.5x | +9.0 | **Insatiable** | Golpes consecutivos aumentan el daño de ataque en +1.0 (acumulable hasta +5.0). |
| **Rose Gold (Oro Rosa)** | `mvtink_rose_gold` | Oro (`mvtink_gold`) | Cobre (`mvtink_copper`) | `#b76e79` | +280 | 9.0x | +5.0 | **Midas Sparkle** | Mezcla opulenta. +40% de orbes de experiencia al minar y eliminar enemigos. |
| **Astral Brass (Latón Astral)** | `mvtink_astral_brass` | Pirita (`mvtink_pyrite`) | Astralita (`mvtink_astralite`) | `#f4d03f` | +500 | 8.5x | +6.0 | **Starlight Grace** | Caída de pluma permanente y aura celestial luminosa por las noches. |
| **Void Damascus (Damasco del Vacío)** | `mvtink_void_damascus` | Tungsteno (`mvtink_tungsten`) | Piedra del Vacío (`mvtink_voidstone`) | `#2c3e50` | +950 | 9.5x | +9.5 | **Abyssal Cleave** | Ataques perforantes que ignoran el 30% de la armadura del objetivo. |
| **Cinder Steel (Acero de Ceniza)** | `mvtink_cinder_steel` | Acero (`mvtink_steel`) | Netherita (`mvtink_netherite`) | `#e67e22` | +1100 | 10.0x | +9.0 | **Hellfire Core** | Incendia enemigos por 8 segundos y el objeto nunca se quema en lava o fuego. |
| **Prismatic Quartz (Cuarzo Prismático)** | `mvtink_prismatic_quartz` | Cuarzo (`mvtink_quartz`) | Amatista (`mvtink_amethyst`) | `#e056fd` | +400 | 9.0x | +7.0 | **Resonance Shock** | Onda acústica armónica que inflige 2.5 de daño sónico en área a enemigos cercanos. |
| **Shadow Platinum (Platino Sombrío)** | `mvtink_shadow_platinum` | Platino (`mvtink_platinum`) | Obsidianita (`mvtink_obsidianite`) | `#636e72` | +750 | 9.0x | +8.0 | **Umbral Veil** | Agacharse otorga invisibilidad breve y +50% de daño por la espalda. |
| **Ender Brass (Latón de Ender)** | `mvtink_ender_brass` | Redstone (`mvtink_redstone`) | Enderita (`mvtink_enderite`) | `#1abc9c` | +650 | 8.5x | +7.5 | **Phase Step** | Shift + Clic derecho teletransporta al jugador 10 bloques hacia adelante. |
| **Adamant Steel (Acero Adamantino)** | `mvtink_adamant_steel` | Adamantium (`mvtink_adamantium`) | Titanio (`mvtink_titanium`) | `#2ecc71` | +1600 | 11.5x | +8.5 | **Unbreakable Will** | Núcleo indestructible. 80% de probabilidad de no consumir durabilidad. |
| **Hellfire Bismuth (Bismuto Ígneo)** | `mvtink_hellfire_bismuth` | Bismuto (`mvtink_bismuth`) | Ópalo de Fuego (`mvtink_fire_opal`) | `#ff7675` | +550 | 8.0x | +8.0 | **Combustion** | Golpes críticos desatan microexplosiones térmicas sin dañar bloques. |
| **Glacial Silver (Plata Glacial)** | `mvtink_glacial_silver` | Plata (`mvtink_silver`) | Criolita (`mvtink_cryolite`) | `#74b9ff` | +480 | 8.0x | +6.5 | **Absolute Frost** | Congela al objetivo con Lentitud III y efecto de frío de nieve en polvo por 4s. |
| **Sanguine Gold (Oro Sanguíneo)** | `mvtink_sanguine_gold` | Oro (`mvtink_gold`) | Sanguinita (`mvtink_sanguinite`) | `#d63031` | +380 | 9.5x | +7.5 | **Vampiric Touch** | Restaura el 25% de todo el daño cuerpo a cuerpo infligido como vida propia. |
| **Cosmic Netherite (Netherita Cósmica)** | `mvtink_cosmic_netherite` | Netherita (`mvtink_netherite`) | Celestina (`mvtink_celestine`) | `#6c5ce7` | +1400 | 12.0x | +10.5 | **Cosmic Gravity** | Singularidad cósmica. Atrae a los enemigos a 6 bloques hacia el objetivo golpeado. |

---

## 💡 Consejos Metalúrgicos

- **Rendimiento Doble**: Cada fundición de aleación produce **2 lingotes**, manteniendo la conservación exacta de materiales (1 + 1 = 2).
- **Mezclas Interdimensionales**: Unir metales nobles del Overworld con minerales del Nether o del End permite forjar aleaciones legendarias como la **Netherita Cósmica** y el **Damasco del Vacío**.
- **Aleaciones como Componentes**: Puedes mezclar aleaciones con minerales puros en la Sección 2 (Forja Multimaterial) para obtener sinergias híbridas de hasta 3 materiales por pieza.
- **Solo entradas mezclables**: El crisol rechaza objetos tinker no minerales (moldes, piezas, fundidora, brocha) y aleaciones ya terminadas. Solo minerales distintos — un mineral no puede emparejarse consigo mismo.
- **Excepción de la netherita**: el `mvtink_netherite` vanilla ya está clasificado como aleación, así que no puede mezclarse libremente con cualquier mineral. Sí funciona dentro de sus dos recetas curadas, de modo que **las 16 aleaciones legendarias son forjables**; cualquier otro emparejamiento con netherita se rechaza.
- **Herencia de esencia**: una aleación porta las esencias de **ambos** minerales padre, así que un arma de Manyullyn se lee como **Infernal** (padres del Nether) y una de Void Damascus como **Void**, en lugar de que todas las aleaciones compartan una única esencia metálica genérica.
- **Tus descubrimientos persisten**: cada aleación compuesta que forjas se guarda en `plugins/MultiverseTinker/dynamic-alloys.yml` y se restaura en el siguiente arranque, así que los lingotes creados hace días siguen sirviendo como componentes de forja. El códice del crisol indica además cuántas de las **8.085 combinaciones base** has descubierto (hasta **103.781** cuando conoces todas las compuestas).
- **Fusión primordial**: empareja una aleación legendaria con otra aleación, un mineral o un objeto catalizador vanilla para forjar una **aleación primordial** — el nivel de endgame con ultimates congelantes, tormentas de meteoritos y los 9 estados de armadura nuevos. Reglas completas en [Aleaciones Primordiales](Aleaciones-Primordiales.md).
