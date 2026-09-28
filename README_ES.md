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

[📖 Wiki en Español](Wiki-es/Home.md) · [📖 English Wiki](Wiki-en/Home.md) · [English (README)](README.md)

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
* **Crisol de Fundición (Smeltery Crucible)**: Estación de fundición que exige **Lava directamente debajo** para operar, con interfaz gráfica interactiva y tiempos de fusión diferenciados.
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
* **Brocha de Prospector (`mvtink_brush_prospector`)**: Herramienta especializada con **+40% de velocidad**, **+15% de probabilidad de éxito**, **doble suerte para minerales Raros/Épicos/Legendarios** y **50% de probabilidad de no gastar durabilidad**.

---

### 2. 🌋 Crisol de Fundición (*Tinker Smeltery Crucible*)
* **Colocación**: Requiere un **bloque de Lava directamente debajo**.
* **GUI Diagnóstica Dinámica**:
  * ❌ **Sin Lava**: El indicador central se transforma en una barrera roja explicando la causa:
    > *"❌ Inactive: No Heat Source. Place a source block of Lava directly beneath this Smeltery block to ignite the melting crucible!"*
  * 🔥 **Con Lava**: Se enciende con fuego activo, chispas y barra de progreso porcentual.
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

## 🍳 Recetas de Supervivencia

| Objeto | Cuadrícula (3×3) | Ingredientes |
|---|---|---|
| **Crisol de Fundición** | <pre>S F S<br/>M B M<br/>S S S</pre> | S = Piedra lisa · F = Alto horno · M = Bloque de magma · B = Balde |
| **Brocha de Prospector** | <pre>· G ·<br/>C B C<br/>· R ·</pre> | G = Lingote de oro · C = Lingote de cobre · B = Brocha · R = Amatista |
| **Molde de Lingotes** | <pre>B B B<br/>B · B<br/>B B B</pre> | B = Ladrillo de arcilla (centro vacío) |
| **Molde de Pepitas** | <pre>B · B<br/>· C ·<br/>B · B</pre> | B = Ladrillo de arcilla · C = Bola de arcilla |
| **Molde de Bloques** | <pre>B B B<br/>B I B<br/>B B B</pre> | B = Ladrillo de arcilla · I = Bloque de hierro |

---

## 💻 Comandos y Permisos

* `/mvtink give <jugador> <mvtink_id> [cantidad]` — Entrega cualquier mineral (raw, ingot, nugget, block, molten bucket), molde, crisol o brocha.
* `/mvtink list [OVERWORLD|NETHER|THE_END]` — Lista los 90 minerales con sus rasgos, rarezas y colores.
* `/mvtink reload` — Recarga la configuración y las tablas de arqueología.

---

<div align="center">

**DrakesCraft Labs** · Diseñado por **Chagui68**  
Licencia: **GPL-3.0**

</div>
