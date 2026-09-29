# ⚙️ Configuration Reference

MultiverseTinker writes `config.yml` into `plugins/MultiverseTinker/` on its first startup, filled with the
shipped defaults. Nothing in it requires a restart: edit the file, then run `/mvtink reload` to re-read the
configuration, the item registry and the loot tables at once.

---

## 📜 Item lore presentation (`lore`)

Minecraft renders **every lore row as a single line** and simply clips whatever is wider than the tooltip area.
That is why long weapon perks and trait descriptions used to run off the screen: the sentence was one row, not a
paragraph. MultiverseTinker word-wraps long rows instead, so the whole text stays readable.

The wrap preserves **colours, gradients and decorations** (the row is split into styled pieces and rebuilt, so a
gradient keeps its per-character colour). Rows that already fit are returned untouched, and blank separator rows
are never changed.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `lore.wrap-long-lines` | boolean | `true` | Split long lore rows at word boundaries. Set to `false` to restore the old single-row output (long lines will be clipped again). |
| `lore.max-line-width-pixels` | integer | `190` | Width budget of a normal lore row, in default-font pixels. A lowercase glyph averages ~6 px and the vanilla tooltip area is ~200 px wide. Lower it for narrower tooltips with more rows, raise it for wider rows with fewer rows. Values below `60` are clamped, because narrower rows stop being readable. |
| `lore.header-line-width-pixels` | integer | `320` | Wider budget reserved for the **two header rows** of modular equipment — the tier tag and the progress bar. Those rows are rewritten in place every time an item levels up, so they must stay the same rows before and after the update. Clamped to at least `max-line-width-pixels`. |

```yaml
# Item lore presentation (tooltips)
lore:
  # Split long lore rows at word boundaries so nothing runs off the screen
  wrap-long-lines: true

  # Maximum width of a wrapped lore row, in default-font pixels
  max-line-width-pixels: 190

  # Wider budget for the first two rows of modular equipment (tier tag + progress bar)
  header-line-width-pixels: 320
```

### How the width is measured

Wrapping is **measured, not counted**: instead of splitting at a fixed number of characters, the width of every
glyph is estimated with the default Minecraft font, and wide glyphs (bullets, bars, star markers) are deliberately
**over-estimated**. Over-estimating is safe — wrapping one word early costs a row, while wrapping too late clips
the text again.

Wrapped continuation rows get a **hanging indent**, so a row that started with `✦ ` or `• ` continues under its
own text instead of under the bullet:

```
✦ Weapon Perk: Infernal Sweeping
  Cleave: hits multiple adjacent foes
  and chains elemental traits. Imbued
  essence: searing hits that set
  foes ablaze.
```

### Turning it off

`lore.wrap-long-lines: false` disables the whole feature and prints the lore exactly as before. This is useful if
you run a resource pack with a very different font, or if you prefer clipping over extra rows.

On startup (and on every `/mvtink reload`) the console reports what was applied, for example:

```
Item lore wrapping enabled at 190px (headers 320px) per row.
```

---

## ✨ Signature perk animations (`animations`)

Perks are not the only thing each equipment type owns: every weapon, tool and armor piece has its own
**exclusive choreography**. No two types share a pattern, a particle pair or a sound, and a unit test fails
the build if they ever do — that is how "no favoritism" is enforced instead of promised.

An animation plays when that piece's perk actually fires, and it is tinted with the colour of the dominant
mineral the item was forged from, so a Cobalt broadsword and a Voidstone broadsword share the arc but not its hue.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `animations.enabled` | boolean | `true` | Play the perk choreographies at all. |
| `animations.particle-scale` | number | `1.0` | Particle-count multiplier for every animation, clamped to `0.25` – `3.0`. Lower it on busy servers, raise it for a louder spectacle. |
| `animations.sounds` | boolean | `true` | Play the signature sound that accompanies each pattern. |
| `animations.cooldown-millis` | integer | `400` | Minimum delay between two animations of the same type on the same player (clamped to `0` – `5000`). Fast procs — a sweep that chains, a chestplate that eats a blow — stay readable instead of strobing. |

```yaml
# Signature perk animations
animations:
  enabled: true
  particle-scale: 1.0
  sounds: true
  cooldown-millis: 400
```

### The sixteen choreographies

| Equipment | Animation | Pattern | Signature sound |
| --- | --- | --- | --- |
| Broadsword | Sweeping Arc | `SWEEP_ATTACK` + tint | `ENTITY_PLAYER_ATTACK_SWEEP` |
| Longbow | Volley Trail | `CRIT` + `END_ROD` | `ENTITY_ARROW_SHOOT` |
| Heavy Crossbow | Piercing Lance | `ELECTRIC_SPARK` + `CRIT` | `ITEM_CROSSBOW_SHOOT` |
| Elder Trident | Hydraulic Surge | `SPLASH` + `ELECTRIC_SPARK` | `ITEM_TRIDENT_RIPTIDE_1` |
| Kinetic Spear | Jousting Thrust | `CLOUD` + `CRIT` | `ENTITY_PLAYER_ATTACK_STRONG` |
| War Mace | Seismic Smash | `EXPLOSION` + `CLOUD` | `ITEM_MACE_SMASH_GROUND_HEAVY` |
| Tower Shield | Retaliation Bulwark | `ENCHANTED_HIT` + `END_ROD` | `ITEM_SHIELD_BLOCK` |
| Pickaxe | Vein Resonance | `ENCHANTED_HIT` + `ELECTRIC_SPARK` | `BLOCK_AMETHYST_BLOCK_CHIME` |
| Battleaxe | Lumber Cleave | `CRIT` + `CHERRY_LEAVES` | `BLOCK_WOOD_BREAK` |
| Excavator | Seismic Tremor | `CLOUD` + `CAMPFIRE_COSY_SMOKE` | `BLOCK_GRAVEL_BREAK` |
| Scythe | Harvest Swirl | `HAPPY_VILLAGER` + `NOTE` | `ITEM_CROP_PLANT` |
| Fishing Rod | Abyssal Dredge | `BUBBLE` + `SPLASH` | `ENTITY_FISHING_BOBBER_SPLASH` |
| Helmet | Cranium Halo | `END_ROD` + `ENCHANTED_HIT` | `BLOCK_AMETHYST_BLOCK_RESONATE` |
| Chestplate | Kinetic Dome | `ENCHANTED_HIT` + `CLOUD` | `BLOCK_ANVIL_LAND` |
| Leggings | Stride Coil | `CLOUD` + `ELECTRIC_SPARK` | `ENTITY_PHANTOM_FLAP` |
| Boots | Grounding Puff | `SNOWFLAKE` + `CLOUD` | `BLOCK_POWDER_SNOW_BREAK` |

When the animation plays:

* **Broadsword** — the sweep chained at least one adjacent foe.
* **Longbow / Crossbow / Trident (thrown)** — the arrow, bolt or trident landed on something.
* **Trident (melee), Spear, Mace** — the surge, the sprint/horseback thrust or the downward smash triggered.
* **Tower Shield** — a blocked blow was reflected.
* **Pickaxe, Scythe, Fishing Rod** — the 15% extra-drop proc succeeded.
* **Battleaxe, Excavator** — the tree felling or the sneaking 3×3 dig ran.
* **Armor** — the piece answered a hit (helmet, chestplate, leggings while striding) or absorbed a fall (boots).

---

## ⚔️ Modular equipment rules (`equipment`)

A forged weapon, tool or armor piece carries its **own durability counter**. Vanilla wear is therefore off: the
item is unbreakable for the server (the flag is hidden, so the tooltip never prints an "Unbreakable" row) and the
remaining durability is reported by the item's own `• Durability: current / max` lore row, which turns
green → yellow → red as the piece wears down. Without this, Minecraft and the plugin would both spend durability on
the same swing and a sword would break on a vanilla schedule while its modular counter sat untouched.

| Key | Type | Default | What it does |
| --- | --- | --- | --- |
| `equipment.modular-attack-damage` | boolean | `true` | `true` makes a weapon hit for the attack damage rolled from its minerals — the value printed in its lore. `false` keeps the base vanilla material's damage. Durability stays modular either way. |

```yaml
equipment:
  modular-attack-damage: true
```

### How a modular strike is calculated

The weapon is a vanilla item underneath, so the server would otherwise use the base material's damage — a
broadsword forged from Voidstone would hit exactly as hard as the wooden sword it is built on. With the rule on,
the vanilla contribution is read from the player's live attack-damage attribute and only that base is replaced, so
critical hits, strength potions and enchantments keep multiplying the forged damage instead of being discarded.

Setting `modular-attack-damage: false` restores pure vanilla combat values (handy for servers that use their own
damage plugins), while vanilla wear stays disabled in both cases.

---

## 🧭 Other sections

| Section | Purpose |
| --- | --- |
| `archaeology` | Brushing system: enable flag, brushing duration, brush durability cost, per-dimension success chance, block degradation behaviour (`DEGRADE` / `COOLDOWN` / `NONE`), anti-macro cooldown and brush yields. |
| `animations` | Signature perk animations: enable flag, particle multiplier, sound toggle and per-type cooldown. |
| `equipment` | Modular equipment rules: whether a forged weapon fights with its rolled attack damage or the vanilla material value (vanilla wear is always disabled). |
| `smeltery` | Crucible tuning: chance to consume the lava source and the Magma Block heat-source slowdown multiplier. |
| `rarity-weights` | Relative drop weights per mineral rarity (`common` … `legendary`). |
| `messages` | Chat and action bar texts (MiniMessage format) for brushing, cooldowns and permissions. |

---

## 🔗 Related pages

* **[Forge GUI & Modular Equipment Guide](Forge-GUI-Guide.md)**: what the wrapped lore actually prints — perks, essence focus, trait channels and the exclusive animation of every type.
* **[Traits & Mineral Effects](Traits-and-Effects.md)**: the trait and affinity reference behind those lore rows.
* **[Prime Alloys](Prime-Alloys.md)**: the `dynamic-alloys.yml` file that stores player-forged primes across restarts.
