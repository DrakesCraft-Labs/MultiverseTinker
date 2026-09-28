# 🌋 Crisol de Fundición y Templado en Caldero

MultiverseTinker implementa una progresión metalúrgica basada en dos estaciones fundamentales: el **Crisol de Fundición (Smeltery Crucible)** y el **Caldero de Templado con Agua**.

---

## 1. El Crisol de Fundición (`mvtink_smeltery`)

El Crisol de Fundición es un horno refractario de alta resistencia diseñado para derretir minerales en bruto y transformarlos en metales líquidos fundidos.

### ⚠ Fuentes Térmicas Aceptadas (`BlockFace.DOWN`)
El Crisol de Fundición requiere de forma obligatoria una fuente de calor válida directamente debajo:

| Bloque Fuente de Calor | Velocidad de Fundición | Probabilidad de Consumo | Comportamiento |
|---|---|---|---|
| **Lava** (`Material.LAVA`) | **100%** (Óptima) | **10%** por mineral cocinado | Fundición a máxima velocidad; tiene un 10% de probabilidad de consumirse (convertirse en aire) al fundir un ítem. |
| **Bloque de Magma** (`Material.MAGMA_BLOCK`) | **70%** (-30% de velocidad) | **0%** (Inagotable) | Fundición 30% más lenta (+30% tiempo requerido); fuente permanente y segura que nunca se consume. |

* Si se coloca sobre aire, agua, piedra o cualquier bloque no térmico, el crisol permanecerá apagado.

### Interfaz Gráfica Dinámica (27 Ranuras)
Al dar click derecho sobre el crisol se despliega su GUI personalizada:
* **Ranura 10**: Entrada de Minerales en Bruto (`mvtink_*_raw`).
* **Ranura 12**: Entrada de Baldes Vacíos (`Material.BUCKET`).
* **Ranura 14**: Indicador de Calor y Progreso Diagnóstico:
  * ❌ **Apagado / Sin Calor**: Si no hay calor debajo, muestra una barrera con instrucciones:
    > *"❌ Inactive: No Heat Source. Place a block of Lava or Magma Block directly beneath this Smeltery block to ignite the melting crucible!"*
  * 🔥 **Calentado / Listo**: Refleja la fuente térmica detectada:
    * *Lava Detectada*: Salida térmica al 100%, con aviso del 10% de probabilidad de consumo.
    * *Bloque de Magma Detectado*: Salida térmica al 70% (-30% velocidad), confirmando estabilidad infinita.
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
