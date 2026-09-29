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

## 🧭 Other sections

| Section | Purpose |
| --- | --- |
| `archaeology` | Brushing system: enable flag, brushing duration, brush durability cost, per-dimension success chance, block degradation behaviour (`DEGRADE` / `COOLDOWN` / `NONE`), anti-macro cooldown and brush yields. |
| `smeltery` | Crucible tuning: chance to consume the lava source and the Magma Block heat-source slowdown multiplier. |
| `rarity-weights` | Relative drop weights per mineral rarity (`common` … `legendary`). |
| `messages` | Chat and action bar texts (MiniMessage format) for brushing, cooldowns and permissions. |

---

## 🔗 Related pages

* **[Forge GUI & Modular Equipment Guide](Forge-GUI-Guide.md)**: what the wrapped lore actually prints — perks, essence focus and trait channels.
* **[Traits & Mineral Effects](Traits-and-Effects.md)**: the trait and affinity reference behind those lore rows.
* **[Prime Alloys](Prime-Alloys.md)**: the `dynamic-alloys.yml` file that stores player-forged primes across restarts.
