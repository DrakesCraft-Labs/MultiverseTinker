# 🔮 Referencia de Afinidades de Rasgos

Cada mineral, mineral vanilla y aleación de MultiverseTinker resuelve a un pequeño conjunto de
**Afinidades de Rasgos**. Las afinidades son lo que hace que una pieza forjada realmente *haga*
algo, y se aplican **de forma distinta en armas, herramientas y armaduras**: un mineral ígneo
incendia al golpear, auto-funde al minar y otorga Resistencia al Fuego al llevarlo puesto.

La clasificación es 100% determinista: el mismo mineral siempre enseña las mismas afinidades, por
lo que cada combinación de forja posee siempre la misma mezcla reproducible.

> 📚 La lista exacta de esencias de cada material está en la **[Referencia de Materiales](Fuentes-de-Materiales.md)**, y las esencias que hereda cada mezcla en el **[Índice de Recetas](Indice-de-Recetas.md)**.

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

**Además, una aleación *es* sus padres en cuanto a esencia.** Cuando se vierte un lingote forjado,
las esencias de sus dos minerales se funden en la identidad propia de la aleación (hasta 3,
ordenadas por prioridad dimensional), así que una cabeza de aleación aporta la esencia de identidad
del arma igual que lo haría su mineral:

| Aleación | Padres | Identidad de esencia |
|---|---|---|
| Bronce | Cobre + Estaño (Overworld) | **Tempered / Terrain** |
| Manyullyn | Ardita + Cobalto (Nether) | **Infernal** |
| Void Damascus | Tungsteno (Nether) + Piedra del Vacío (End) | **Infernal / Void** |

Como cada mezcla es pura y determinista, **cada par de minerales posee una combinación única de
efectos**: mezclar Estaño con Zinc nunca se comportará como mezclar Estaño con Cobalto. Las 16
recetas legendarias (Bronce, Electro, Invar, Manyullyn, Netherita Cósmica, …) además conservan su
rasgo exclusivo curado por encima de las afinidades heredadas.

> 💡 La línea **Mineral Affinities** de cada arma, herramienta o armadura forjada lista exactamente
> qué esencias porta esa pieza.

---

## ⚔️ Perks de arma según los materiales

Cada arma modular conserva una **mecánica característica** según su tipo (Tajo Enlazado, Piercing
Velocity, Oleada Hidráulica, …). El **nombre del perk proviene de todos los minerales con los que se
forjó** (ver [Nombres de Perk](Nombres-de-Perk.md)), mientras la **esencia que lo impulsa viene del
mineral de la cabeza**:

| Pieza | Influencia sobre el perk |
|---|---|
| **Todas las partes** | Aporta el **epíteto** de su propio mineral, así el nombre identifica el build completo. |
| **Cabeza** (hoja, brazos del arco, dientes, mazo, placa del escudo) | Decide la **esencia de identidad** del arma — la esencia de mayor rango que enseña su mineral. |
| **Empuñadura / Pomo** (varilla, cuerda, atadura, umbón) | Deciden la **potencia**: cuánta parte del arma forjada porta realmente esa esencia. |

Así, el perk se muestra como `<epítetos> <Mecánica>`:

| Mineral de la cabeza | Perk resultante |
|---|---|
| Cobalto (Nether) | **Lightfooted … Piercing Velocity** — la esencia Infernal de la cabeza incendia los virotes. |
| Piedra del Vacío (End) | **Warping … Piercing Velocity** — la esencia Void de la cabeza arrastra a los enemigos. |
| Diamante (gema vanilla) | **Adamant Tajo Enlazado** — la esencia Radiant de la cabeza marca a los enemigos con Brillantez. |
| Hierro (metal vanilla) | **Ironclad Tajo Enlazado** — la esencia Tempered de la cabeza endurece el filo. |
| Rubí (gema del Overworld) | **Rubicund Embestida de Justa** — la esencia Terrain de la cabeza ralentiza al objetivo. |
| Manyullyn (aleación del Nether) | **Insatiable Tajo Enlazado** — la aleación pelea con las esencias de sus minerales padre, no con una esencia metálica genérica. |
| Void Damascus (aleación Nether + End) | **Damascened Tajo Enlazado** — la esencia de vacío del padre del End gana la identidad. |

> 🔎 **¿Por qué el mineral y no solo la esencia?** La Amatista y el Diamante enseñan *exactamente las
> mismas esencias*, así que nombrar el perk solo con la esencia daba el mismo nombre a dos builds
> realmente distintos. Cada mineral tiene un epíteto único ([los 139](Nombres-de-Perk.md)), así que solo
> dos builds idénticos comparten nombre.

Justo debajo del perk verás `• Essence Focus: <Esencia> essence (<n>%)`, la proporción de la masa
forjada del arma que porta la esencia de identidad. Un arma hecha íntegramente de un solo mineral
alcanza el 100%; una cabeza exótica con empuñadura común ronda el 50%.

En combate la esencia dominante se canaliza en el **golpe primario** del perk (el *perk echo*),
escalado por esa concentración — por lo que dos ballestas del mismo tier forjadas con minerales
distintos se comportan de forma realmente diferente.

> ⚡ Las armas cuyo enfoque de esencia alcanza el **80%** desatan además el
> **[ultimate](Ultimates-de-Esencia.md)** cinematográfico de esa esencia — meteoritos, vórtices,
> pilares de luz o jaulas que inmovilizan al enemigo — con 20 segundos de enfriamiento.

---

## 🌌 Afinidades primordiales y estados de armadura

Las aleaciones primordiales heredan **ambos** padres legendarios (más la esencia Primal/Tempered del
catalizador), así que una primordial sigue enseñando todas las esencias de sus ingredientes
legendarios. Encima de eso, cada primordial porta un **espectáculo** y un **estado defensivo**:

| Ingrediente primordial | Ultimate (arma) | Estado (armadura) |
|---|---|---|
| Hielo Azul / Hielo Compacto | Absolute Zero ❄ / Glacier Tomb ❄ | Frostbound *(congela a los atacantes)* |
| Estrella del Nether / Tótem de Inmortalidad | Supernova / Prismatic Ascension | Prime Aegis |
| Aliento de Dragón / Ancla de Reaparición | Meteor Cascade ☄ | Ember Veil |
| Fragmento de Eco / Cúmulo de Amatista | Event Horizon | Void Shell |
| Corazón del Mar / Escombros Antiguos | Tectonic Rift | Gravitic Anchor / Tectonic Guard |
| Cristal del End | Supernova | Stormcall |
| Cristales de Prismarina | Prismatic Ascension | Prism Bulwark |
| Sin catalizador (respaldo de esencia) | sigue la esencia dominante | sigue la esencia dominante |

Los estados de armadura son la nueva capa defensiva de endgame por encima de los procs de afinidad
normales: mira el desglose completo en **[Aleaciones Primordiales](Aleaciones-Primordiales.md)**.
