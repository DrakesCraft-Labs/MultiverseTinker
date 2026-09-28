# 🌋 Crisol de Fundición y Templado en Caldero

MultiverseTinker implementa una progresión metalúrgica basada en dos estaciones fundamentales: el **Crisol de Fundición (Smeltery Crucible)** y el **Caldero de Templado con Agua**.

---

## 1. El Crisol de Fundición (`mvtink_smeltery`)

El Crisol de Fundición es un horno refractario de alta resistencia diseñado para derretir minerales en bruto y transformarlos en metales líquidos fundidos.

### ⚠ Requisito Térmico Obligatorio (Lava Debajo)
* El Crisol de Fundición **exige de forma estricta un bloque fuente de Lava directamente debajo (`BlockFace.DOWN`)**.
* Si se ubica sobre aire, agua, piedra o cualquier otro bloque, el crisol permanecerá apagado.

### Interfaz Gráfica Dinámica (27 Ranuras)
Al dar click derecho sobre el crisol se despliega su GUI personalizada:
* **Ranura 10**: Entrada de Minerales en Bruto (`mvtink_*_raw`).
* **Ranura 12**: Entrada de Baldes Vacíos (`Material.BUCKET`).
* **Ranura 14**: Indicador de Calor y Progreso Diagnóstico:
  * ❌ **Apagado / Sin Calor**: Si no hay lava debajo, aparece un icono de barrera con instrucciones:
    > *"❌ Inactive: No Heat Source. Place a source block of Lava directly beneath this Smeltery block to ignite the melting crucible!"*
  * 🔥 **Calentado / Listo**: Al detectar lava, muestra polvo de blaze / carga ígnea con estado óptimo.
  * ⚡ **Fundiendo**: Muestra el porcentaje en tiempo real y una barra de progreso animada:
    > *"🔥 Smelting in Progress... [██████----] 60%"*
* **Ranura 16**: Ranura de Salida con el **Balde de Mineral Fundido** (`mvtink_<id>_molten_bucket`).

---

## 2. Templado y Solidificación en Caldero

Una vez que el metal líquido se encuentra en el balde fundido, se debe templar utilizando un **Caldero de Agua** vanilla y un **Molde (*Cast*)** reutilizable.

### Moldes Reutilizables de Fundición
* **Molde de Lingotes** (`mvtink_cast_ingot`): Solidifica el metal en 1 Lingote.
* **Molde de Pepitas** (`mvtink_cast_nugget`): Solidifica el metal en 9 Pepitas.
* **Molde de Bloques** (`mvtink_cast_block`): Solidifica el metal en 1 Bloque Compacto.

### Procedimiento de Templado
1. Coloca un caldero y llénalo de agua (`Material.WATER_CAULDRON`).
2. Sostén el **Balde de Mineral Fundido** en la **Mano Principal**.
3. Sostén el **Molde Elegido** en la **Mano Secundaria** (o tenlo en el inventario).
4. **Click Derecho sobre el Caldero de Agua**:
   * Desata una reacción térmica: vapor denso (`BLOCK_LAVA_EXTINGUISH`), golpe sonoro de yunque (`BLOCK_ANVIL_USE`) y humo.
   * Evapora 1 nivel de agua del caldero.
   * Vacía el balde fundido, devolviendo un `BUCKET` vacío.
   * Preserva el molde (es reutilizable indefinidamente).
   * Entrega la pieza sólida completada al jugador.
