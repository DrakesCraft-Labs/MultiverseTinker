# 🔮 Trait Affinities Reference

Every mineral, vanilla ore and alloy in MultiverseTinker resolves to a small set of **Trait
Affinities**. Affinities are what make a forged part actually *do* something, and they apply
**differently for weapons, tools and armor** — a fiery mineral burns on hit, auto-smelts while
mining, and grants Fire Resistance when worn.

The mapping is 100% deterministic: the same mineral always teaches the same affinities, so a
given forge combination always owns the same, reproducible signature blend.

---

## 🧭 How affinities are assigned

Each material collects up to **3** affinities, chosen in this priority order:

1. **Dimensional essence** — where the geology comes from.
2. **Material class** — the forge type of the mineral.
3. **Forged statistics** — extreme speed, damage, durability or rarity.

| Source | Affinity granted |
|---|---|
| Overworld geology | **Terrain** |
| Nether geology | **Infernal** |
| The End geology | **Void** |
| Vanilla ores | **Primal** |
| Metal / Alloy | **Tempered** |
| Gem | **Radiant** |
| Crystal | **Resonant** |
| Elemental | **Volatile** |
| Mineral | **Terrain** |
| Mining Speed ≥ 9.0x | **Swift** |
| Attack Damage ≥ +3.5 | **Brutal** |
| Durability ≥ +900 | **Bulwark** |
| Rarity Epic / Legendary | **Ascendant** |

---

## ⚗️ What each affinity does

| Affinity | ⚔ Weapon (on hit) | ⛏ Tool (on block break) | 🛡 Armor (when struck) |
|---|---|---|---|
| **Infernal** | Sets the target ablaze for longer durations. | Chance to **auto-smelt** the excavated ore into its finished form. | Grants **Fire Resistance**. |
| **Void** | Warps the target toward the wielder. | Chance to rip **bonus experience** from stone. | Grants **Slow Falling**. |
| **Primal** | Flat bonus impact damage. | Chance for **bonus natural drops**. | Grants a small **Absorption** shield. |
| **Tempered** | Hardened edge, **+10% damage** per potency. | Chance to **self-repair** the tool by 1 durability. | Grants **Resistance**. |
| **Radiant** | Radiant blows **mark the target** with Glowing. | Grants **Night Vision** while excavating. | **Mends the wielder** when struck. |
| **Resonant** | Harmonic shockwave damages **nearby foes**. | Chimes and reveals the **surrounding ore seams**. | Concussive pulse **shoves attackers** away. |
| **Volatile** | Unstable strikes **erupt in flame**. | Chance to ignite a spark for **bonus experience**. | **Sets melee attackers alight**. |
| **Terrain** | Earthen blows **slow struck foes**. | Chance for **bonus excavated blocks**. | **Stuns attackers** with heavy Slow. |
| **Swift** | Lightning cadence grants **Haste** to the wielder. | Grants **Haste** while mining. | Grants **Speed** when struck. |
| **Brutal** | Crushing force: **+2.0 damage** and heavy knockback. | Chance to **shatter out extra ore**. | **Reflects a portion** of the damage back. |
| **Bulwark** | Immovable mass grants the wielder **Resistance**. | Chance to **absorb durability wear** entirely. | Grants **extra Resistance**. |
| **Ascendant** | Transcendent strikes grant **Regeneration**. | Chance for **bonus experience** while mining. | Grants **Regeneration**. |

All affinity effects scale with the **concentration ratio** of the part that contributes them
(100% for a single-material part, 50/50 for two, 33/33/33 for three).

---

## 🧪 Alloy blending

Alloys inherit the essences of **both** parent minerals, each at half potency:

```
Bronze (Copper + Tin) -> Terrain (Copper) + Primal (Copper)
                         + Terrain (Tin)  + Tempered (Tin)
```

Because every blend is pure and deterministic, **each mineral pair owns a unique combination of
effects** — mixing Tin with Zinc will never behave like mixing Tin with Cobalt. The 16 legendary
recipes (Bronze, Electrum, Invar, Manyullyn, Cosmic Netherite, …) additionally keep their curated
signature trait on top of the inherited affinities.

> 💡 The **Mineral Affinities** line on every forged weapon, tool and armor piece lists exactly
> which essences that piece carries.
