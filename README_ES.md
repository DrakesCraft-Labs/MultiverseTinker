<div align="center">

# ⚒️ MultiverseTinker (Español)

**Herramientas Modulares, Arqueología Geológica, Crisol de Fundición y Metalurgia para Paper 1.21+ (Java 21)**

<p>
  <img src="https://img.shields.io/badge/Paper-1.21.11-38BDF8?style=for-the-badge&logo=minecraft&logoColor=white" alt="Paper 1.21.11"/>
  <img src="https://img.shields.io/badge/Java-21-F89820?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Licencia-GPLv3-blue?style=for-the-badge" alt="GPLv3"/>
  <img src="https://img.shields.io/badge/Autor-Chagui68-22C55E?style=for-the-badge" alt="Chagui68"/>
  <img src="https://img.shields.io/badge/Minerales-90_Total-purple?style=for-the-badge" alt="90 Minerales"/>
</p>

Parte del **Ecosistema Soberano Multiverse de Chagui68** junto a [MultiverseNets](https://github.com/DrakesCraft-Labs/MultiverseNets), [MultiverseCreatures](https://github.com/DrakesCraft-Labs/MultiverseCreatures) y [MultiverseProgramming](https://github.com/DrakesCraft-Labs/MultiverseProgramming).

[📖 Wiki en Español](Wiki-es/Home.md) · [🏛️ Estructura Forja](Wiki-es/Estructura-Forja.md) · [⚡ Rasgos de Forja](Wiki-es/Rasgos-y-Efectos.md) · [📖 English Wiki](Wiki-en/Home.md) · [🏛️ Forge Multiblock](Wiki-en/Forge-Structure.md) · [⚡ Forge Traits](Wiki-en/Traits-and-Effects.md) · [English (README)](README.md)

</div>

> ### 🏰 ¡Únete a la Comunidad Oficial de DrakesCraft!
> * 🎮 **IP del Servidor**: `mc.drakescraft.cl` *(Java 1.21.11 & Bedrock)*
> * 💬 **Discord Oficial**: [discord.gg/drakescraft](https://discord.gg/rv3vtXZTk7)
> * 🌐 **Web & Guías**: [web.drakescraft.cl](https://web.drakescraft.cl) — 🛒 **Tienda**: [web.drakescraft.cl/store](https://web.drakescraft.cl/store.html)

---

## 🌟 ¿Qué es MultiverseTinker?

**MultiverseTinker** traslada la metalurgia modular, aleaciones avanzadas y geología arqueológica inspiradas en Tinkers' Construct a Minecraft moderno como un **plugin 100% nativo y standalone para Paper/Purpur 1.21+** y **Java 21**, sin dependencias forzosas.

* **Cero Problemas de Generación de Terreno**: Los minerales se descubren mediante un sistema interactivo de **Arqueología y Cepillado Geológico** sobre roca, netherrack y piedra del end sin alterar los generadores de chunks.
* **90 Minerales Únicos**: Distribuidos de forma equilibrada en **exactamente 30 minerales por cada dimensión** (Overworld, Nether y The End).
* **5 Formas Físicas por Mineral**: Cada mineral cuenta con su **Mineral en Bruto (Raw)**, **Balde Fundido Líquido**, **Lingote o Gema**, **Pepita** y **Bloque Compacto** con recetas reversibles de $9\times$.
* **Crisol de Fundición (Smeltery Crucible)**: Estación de fundición que opera con **Lava** (100% velocidad, 10% probabilidad de consumo) o **Bloque de Magma** (70% velocidad, estabilidad inagotable) directamente debajo, con interfaz gráfica interactiva y tiempos de fusión diferenciados.
* **Enfriamiento en Caldero con Moldes**: Moldes cerámicos reutilizables (*Ingot Cast*, *Nugget Cast*, *Block Cast*) templan los baldes fundidos en calderos de agua con efectos de vapor y enfriamiento.
* **Aislamiento Total**: Cada ítem, receta y tag en `PersistentDataContainer` (PDC) lleva el prefijo reservado **`mvtink_`**.

---

## ⚙️ Sistemas Principales

### 1. 🔍 Arqueología Geológica y Cepillado
Extracción de minerales en bruto manteniendo el click derecho con una brocha sobre bloques geológicos naturales:
* **Overworld**: Piedra, Adoquín, Pizarra, Andesita, Diorita, Granito, Toba (Estaño, Zinc, Plata, Rubí, Zafiro, Titanio, Platino, etc.).
* **The Nether**: Netherrack, Piedra Negra, Basalto (Cobalto, Ardita, Azufre, Sangrita, Tungsteno, Witherita, etc.).
* **The End**: Piedra del End (Enderita, Adamantium, Adamita, Celestina, Vacuita, Cosmium, Singularita, etc.).
* **Degradación Geológica**: El bloque se desgasta de forma natural con el uso sostenido (`Piedra -> Adoquín -> Grava -> Aire`) con enfriamiento anti-macros.
* **Rendimiento de Brocha Normal**: Extrae Minerales en Bruto (70%) o Pepitas individuales (30%). No puede extraer bloques completos de almacenamiento.
* **Brocha de Prospector (`mvtink_brush_prospector`)**: Herramienta especializada con **+40% de velocidad**, **+15% de probabilidad de éxito**, **doble suerte para minerales Raros/Épicos/Legendarios**, **50% de probabilidad de no gastar durabilidad**, y la capacidad única de desenterrar **Bloques de Almacenamiento completos** (20% de probabilidad jackpot), Minerales en Bruto (55%) o 1–3 Pepitas (25%).

---

### 2. 🌋 Crisol de Fundición (*Tinker Smeltery Crucible*)
* **Colocación y Fuentes de Calor (`BlockFace.DOWN`)**:
  * **Lava**: 100% de velocidad de fundición. Cuenta con un **10% de probabilidad** de consumirse (convirtiéndose en aire con sonido de extinción y humo) al terminar de fundir un mineral.
  * **Bloque de Magma**: 70% de velocidad de fundición (-30% de velocidad / toma 30% más tiempo). Fuente inagotable y segura que nunca se consume.
* **GUI Diagnóstica Dinámica**:
  * ❌ **Sin Calor**: El indicador central se transforma en una barrera roja explicando la necesidad de colocar Lava o Bloque de Magma.
  * 🔥 **Con Calor Activo**: Se enciende con fuego, siseo y barra de progreso porcentual indicando la fuente de calor activa (Lava al 100% o Magma al 70%).
* **Operación**: Se coloca el mineral en bruto en la ranura 10 y un balde vacío en la ranura 12. Al completarse el tiempo de fundición, entrega el **Balde de Mineral Fundido** (`mvtink_<id>_molten_bucket`).

---

### 3. 💧 Enfriamiento en Caldero de Agua (*Casting System*)
* Se llena un caldero con agua.
* Se sostiene el **Balde de Mineral Fundido** en la mano principal y el **Molde (*Cast*)** en la mano secundaria:
  * **Molde de Lingotes** (`mvtink_cast_ingot`): Genera 1 Lingote.
  * **Molde de Pepitas** (`mvtink_cast_nugget`): Genera 9 Pepitas.
  * **Molde de Bloques** (`mvtink_cast_block`): Genera 1 Bloque.
* Click derecho al caldero con agua:
  * Produce nubes de vapor denso y sonido de templado (`BLOCK_LAVA_EXTINGUISH` + clink de yunque).
  * Consume un nivel de agua del caldero por evaporación.
  * Devuelve el balde vacío y entrega el material solidificado.

---

### 4. 🏛️ Estructura Multibloque de la Forja (`forge.nbt`)
* **Construcción Monumental ($11 \times 7 \times 11$)**:
  * Centrada en torno a un Yunque central, construida con Ladrillos de Toba Cincelados, Baldosas y Ladrillos de Pizarra Profunda, Losas/Escaleras de Ladrillos de Toba y 4 pilares esquineros de Lava térmica (243 bloques en total).
  * Admite las 4 rotaciones cardinales ($0^\circ, 90^\circ, 180^\circ, 270^\circ$).
* **Simulación y Barrido de Partículas**:
  * Al completar la estructura, hacer click derecho al yunque detona una animación de validación con llamaradas por los pilares de lava, barrido de glifos por el suelo y destello con sonido de faro.
* **Aura Ambiental en el Yunque**:
  * Los yunques de forjas activas irradian una pequeña aura continua de brasas giratorias, llamas suaves y humo cálido.
* **GUI Personalizada de la Forja**:
  * Sustituye la interfaz de yunque de Minecraft por un panel exclusivo de 54 ranuras:
    * **Forja de Partes**: Combina Balde Fundido + Molde (Cabeza, Palo, Mango) -> ¡Piezas forjadas del material!
    * **Ensamblado Modular**: Une [Cabeza] + [Palo] + [Mango] -> Herramienta modular terminada (Pico, Espada, Hacha, Pala, Azada) que hereda los rasgos físicos de los 3 minerales.

---

## 🍳 Recetas de Supervivencia

| Objeto | Cuadrícula (3×3) | Ingredientes |
|---|---|---|
| **Crisol de Fundición** | <pre>S F S<br/>M B M<br/>S S S</pre> | S = Piedra lisa · F = Alto horno · M = Bloque de magma · B = Balde |
| **Brocha de Prospector** | <pre>· G ·<br/>C B C<br/>· R ·</pre> | G = Lingote de oro · C = Lingote de cobre · B = Brocha · R = Amatista |
| **Molde de Lingotes** | <pre>B B B<br/>B · B<br/>B B B</pre> | B = Ladrillo de arcilla (centro vacío) |
| **Molde de Pepitas** | <pre>B · B<br/>· C ·<br/>B · B</pre> | B = Ladrillo de arcilla · C = Bola de arcilla |
| **Molde de Bloques** | <pre>B B B<br/>B I B<br/>B B B</pre> | B = Ladrillo de arcilla · I = Bloque de hierro |
| **Molde de Cabeza** | <pre>B G B<br/>B · B<br/>B B B</pre> | B = Ladrillo de arcilla · G = Lingote de oro |
| **Molde de Palo / Varilla** | <pre>B C B<br/>B · B<br/>B · B</pre> | B = Ladrillo de arcilla · C = Lingote de cobre |
| **Molde de Mango / Unión** | <pre>B I B<br/>· C ·<br/>B B B</pre> | B = Ladrillo de arcilla · I = Lingote de hierro · C = Bola de arcilla |

---

## 💻 Comandos y Permisos

* `/mvtink forge build [0|90|180|270]` — Construye la estructura completa de la forja en la ubicación del jugador.
* `/mvtink forge check` — Valida el yunque al que estás apuntando y muestra el porcentaje de coincidencia.
* `/mvtink forge gui` — Abre directamente la interfaz gráfica de la Forja Multiverse.
* `/mvtink give <jugador> <mvtink_id> [cantidad]` — Entrega cualquier ítem (en bruto, lingote, pepita, bloque, balde fundido, piezas de herramienta, moldes, crisol, brocha).
* `/mvtink list [OVERWORLD|NETHER|THE_END]` — Lista los 90 minerales con sus rasgos, rarezas y colores.
* `/mvtink reload` — Recarga la configuración y las tablas de arqueología.

---

<div align="center">

**DrakesCraft Labs** · Diseñado por **Chagui68**  
Licencia: **GPL-3.0**

</div>
