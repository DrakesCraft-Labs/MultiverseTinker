# 🔮 Referencia de Afinidades de Rasgos

Cada mineral, mineral vanilla y aleación de MultiverseTinker resuelve a un pequeño conjunto de
**Afinidades de Rasgos**. Las afinidades son lo que hace que una pieza forjada realmente *haga*
algo, y se aplican **de forma distinta en armas, herramientas y armaduras**: un mineral ígneo
incendia al golpear, auto-funde al minar y otorga Resistencia al Fuego al llevarlo puesto.

La clasificación es 100% determinista: el mismo mineral siempre enseña las mismas afinidades, por
lo que cada combinación de forja posee siempre la misma mezcla reproducible.

---

## 🧭 Cómo se asignan las afinidades

Cada material reúne hasta **3** afinidades, elegidas en este orden de prioridad:

1. **Esencia dimensional** — el origen geológico.
2. **Clase del material** — el tipo de forja del mineral.
3. **Estadísticas forjadas** — velocidad, daño, durabilidad o rareza extremos.

| Origen | Afinidad otorgada |
|---|---|
| Geología del Overworld | **Terrain** |
| Geología del Nether | **Infernal** |
| Geología del End | **Void** |
| Minerales vanilla | **Primal** |
| Metal / Aleación | **Tempered** |
| Gema | **Radiant** |
| Cristal | **Resonant** |
| Elemental | **Volatile** |
| Mineral | **Terrain** |
| Velocidad de minado ≥ 9.0x | **Swift** |
| Daño de ataque ≥ +3.5 | **Brutal** |
| Durabilidad ≥ +900 | **Bulwark** |
| Rareza Épica / Legendaria | **Ascendant** |

---

## ⚗️ Qué hace cada afinidad

| Afinidad | ⚔ Arma (al golpear) | ⛏ Herramienta (al romper bloque) | 🛡 Armadura (al recibir daño) |
|---|---|---|---|
| **Infernal** | Incendia al objetivo por más tiempo. | Probabilidad de **auto-fundir** el mineral excavado. | Otorga **Resistencia al Fuego**. |
| **Void** | Atrae al objetivo hacia el portador. | Probabilidad de obtener **experiencia extra** de la piedra. | Otorga **Caída Lenta**. |
| **Primal** | Daño de impacto plano adicional. | Probabilidad de **drops naturales extra**. | Otorga un pequeño escudo de **Absorción**. |
| **Tempered** | Filo endurecido, **+10% de daño** por potencia. | Probabilidad de **auto-reparar** la herramienta 1 de durabilidad. | Otorga **Resistencia**. |
| **Radiant** | Golpes radiantes **marcan al objetivo** con Brillantez. | Otorga **Visión Nocturna** al excavar. | **Sana al portador** al recibir daño. |
| **Resonant** | Onda armónica que daña a **enemigos cercanos**. | Repica y revela los **filones de mineral** alrededor. | Pulso de choque que **empuja a los atacantes**. |
| **Volatile** | Golpes inestables que **estallan en llamas**. | Probabilidad de encender una chispa por **experiencia extra**. | **Prende fuego a los atacantes cuerpo a cuerpo**. |
| **Terrain** | Golpes terrosos que **ralentizan** al objetivo. | Probabilidad de **bloques excavados extra**. | **Aturde a los atacantes** con Lentitud. |
| **Swift** | Cadencia eléctrica que otorga **Prisa** al portador. | Otorga **Prisa** al minar. | Otorga **Velocidad** al recibir daño. |
| **Brutal** | Fuerza aplastante: **+2.0 de daño** y fuerte empuje. | Probabilidad de **fragmentar mineral extra**. | **Refleja parte** del daño recibido. |
| **Bulwark** | Masa inamovible que otorga **Resistencia**. | Probabilidad de **ignorar por completo** el desgaste. | Otorga **Resistencia extra**. |
| **Ascendant** | Golpes trascendentes que otorgan **Regeneración**. | Probabilidad de **experiencia extra** al minar. | Otorga **Regeneración**. |

Todos los efectos escalan con la **concentración** de la pieza que los aporta (100% en una pieza
de un solo material, 50/50 en dos, 33/33/33 en tres).

---

## 🧪 Mezcla de aleaciones

Las aleaciones heredan las esencias de **ambos** minerales padre, cada una a la mitad de potencia:

```
Bronce (Cobre + Estaño) -> Terrain (Cobre) + Primal (Cobre)
                           + Terrain (Estaño) + Tempered (Estaño)
```

Como cada mezcla es pura y determinista, **cada par de minerales posee una combinación única de
efectos**: mezclar Estaño con Zinc nunca se comportará como mezclar Estaño con Cobalto. Las 16
recetas legendarias (Bronce, Electro, Invar, Manyullyn, Netherita Cósmica, …) además conservan su
rasgo exclusivo curado por encima de las afinidades heredadas.

> 💡 La línea **Mineral Affinities** de cada arma, herramienta o armadura forjada lista exactamente
> qué esencias porta esa pieza.

---

## ⚔️ Perks de arma según los materiales

Cada arma modular conserva una **mecánica característica** según su tipo (Tajo Enlazado, Piercing
Velocity, Oleada Hidráulica, …), pero la **esencia que nombra e impulsa ese perk proviene de los
materiales con los que fue forjada**:

| Pieza | Influencia sobre el perk |
|---|---|
| **Cabeza** (hoja, brazos del arco, dientes, mazo, placa del escudo) | Decide la **esencia de identidad** del arma — la esencia de mayor rango que enseña su mineral. |
| **Empuñadura / Pomo** (varilla, cuerda, atadura, umbón) | Deciden la **potencia**: cuánta parte del arma forjada porta realmente esa esencia. |

Así, el perk se muestra como `<Esencia> <Mecánica>`:

| Mineral de la cabeza | Perk resultante |
|---|---|
| Cobalto (Nether) | **Infernal Piercing Velocity** — los virotes incendian lo que golpean. |
| Piedra del Vacío (End) | **Void Piercing Velocity** — el impacto arrastra a los enemigos. |
| Diamante (gema vanilla) | **Radiant Tajo Enlazado** — los enemigos alcanzados quedan marcados con Brillantez. |
| Hierro (metal vanilla) | **Tempered Tajo Enlazado** — filo endurecido con daño extra. |
| Rubí (gema del Overworld) | **Terrain Embestida de Justa** — los golpes terrosos ralentizan al objetivo. |

Justo debajo del perk verás `• Essence Focus: <Esencia> essence (<n>%)`, la proporción de la masa
forjada del arma que porta la esencia de identidad. Un arma hecha íntegramente de un solo mineral
alcanza el 100%; una cabeza exótica con empuñadura común ronda el 50%.

En combate la esencia dominante se canaliza en el **golpe primario** del perk (el *perk echo*),
escalado por esa concentración — por lo que dos ballestas del mismo tier forjadas con minerales
distintos se comportan de forma realmente diferente.

> ⚡ Las armas cuyo enfoque de esencia alcanza el **80%** desatan además el
> **[ultimate](Ultimates-de-Esencia.md)** cinematográfico de esa esencia — meteoritos, vórtices,
> pilares de luz o jaulas que inmovilizan al enemigo — con 20 segundos de enfriamiento.
