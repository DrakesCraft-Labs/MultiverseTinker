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
✦ Weapon Perk: Fluxforged Resonant Auric
  Sweeping Cleave: hits multiple
  adjacent foes and chains elemental
  traits. Imbued with Infernal,
  Tempered, Swift essence: searing
  hits that set foes ablaze.
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
| `equipment.modular-armor-defense` | boolean | `true` | `true` makes armor defend with the **Defense**, **Toughness** and knockback resistance rolled from its minerals and its evolution tier — the values printed in its lore. `false` keeps the vanilla protection of the tier material. Already-forged pieces are refreshed when their tier evolves. |
| `equipment.armor-perk.scale-per-point` | decimal | `0.02` | Mitigation one point of rolled Defense and Toughness buys a slot's armor perk. `0` makes the perk a flat share per slot. |
| `equipment.armor-perk.caps.helmet` | decimal | `0.65` | Ceiling of the Cranium Ward's share. |
| `equipment.armor-perk.caps.chestplate` | decimal | `0.60` | Ceiling of the Kinetic Dampener's share. |
| `equipment.armor-perk.caps.boots` | decimal | `0.75` | Ceiling of the Feathered Grounding's share. |

```yaml
equipment:
  modular-attack-damage: true
  modular-armor-defense: true
  armor-perk:
    scale-per-point: 0.02
    caps:
      helmet: 0.65
      chestplate: 0.60
      boots: 0.75
```

### Tuning the armor perk curve

Cranium Ward, Kinetic Dampener and Feathered Grounding answer a hit with a share of it, and that share grows with
the piece: every point of Defense and Toughness above what the bare slot rolls buys `scale-per-point`, up to the cap
of that slot. Both ends of the curve are a server's to retune on `/mvtink reload`, so the perks can be made to matter
more or less without a rebuild.

* **A share is a decimal**: `0.02` is 2%, `0.75` is 75%. Every value is clamped — a negative step is treated as no
  scaling at all, and a share above `1.0` as `1.0`.
* **A cap under a slot's floor pins that slot at the cap.** What the file says always wins over what the perk shipped
  with, so lowering a cap below its floor is a legitimate way to nerf the slot into a flat share.
* **A slot left out keeps its shipped ceiling**, so deleting a key restores it on the next reload.
* **Leggings have no share to cap**: Stride Momentum answers a hit with mobility, and a cap key for it is reported in
  the log instead of being read.
* **A retune reaches combat at once and lore from then on.** A piece forged before the change keeps the percentage
  already written into its lore until it evolves or is forged again, because that number is part of the item.

The shipped ceilings keep every value a two-digit percentage, which is what keeps the perk sentence the same width at
every tier; a cap of `1.0` is printed as `100%`, one character wider.

### How a modular strike is calculated

The weapon is a vanilla item underneath, so the server would otherwise use the base material's damage — a
broadsword forged from Voidstone would hit exactly as hard as the wooden sword it is built on. With the rule on,
the vanilla contribution is read from the player's live attack-damage attribute and only that base is replaced, so
critical hits, strength potions and enchantments keep multiplying the forged damage instead of being discarded.

Setting `modular-attack-damage: false` restores pure vanilla combat values (handy for servers that use their own
damage plugins), while vanilla wear stays disabled in both cases.

### How modular armor protects

Armor has the same problem on the defensive side: a forged piece is a vanilla armor item underneath, so the server
would grant the protection of that base material — a helmet with a diamond plate used to defend exactly like the tier
it happened to be built from, no matter which minerals went into it. With `modular-armor-defense: true` the piece's
armor modifiers are **replaced** by the rolled numbers instead of being left alone:

* **Defense** — from the plate, plus the evolution tier's ordinal.
* **Toughness** — from the lining, plus half the tier's ordinal.
* **Knockback resistance** — from the trim's material count, plus a small tier bonus.

Each modifier is bound to the slot the piece is worn in, so the protection only applies where it belongs, and the
vanilla attribute tooltip is hidden so the same numbers are not printed twice. Because the tier is part of the roll,
an armor piece that evolves **re-arms with its new numbers** at the same moment its lore is rewritten.

---

## 🔐 Who may use what (`access`)

Whether the codex, the forge or the brush is open to everyone is a server decision, not necessarily a
permissions-plugin one. Each surface carries a **mode**, read from `config.yml`, so a server that runs no
permissions plugin still decides who may forge:

| Mode | Who gets in |
| --- | --- |
| `public` | **Everyone**, without consulting any permission node. |
| `op` | **Server operators only** — for a server with no permissions plugin. |
| `permission` | The node declared in `plugin.yml`, so LuckPerms, PermissionsEx or a vanilla `permissions.yml` can narrow it further. |

| Key | Type | Default | What it controls |
| --- | --- | --- | --- |
| `access.codex` | string | `public` | Opening the Alloy Codex — `/mvtink codex` and the book button in the crucible tab alike. |
| `access.forge` | string | `public` | The multiblock Forge, its GUI, the Alloy Crucible and the casting cauldron. |
| `access.archaeology` | string | `public` | Brushing a valid geological block for minerals. |

```yaml
access:
  codex: public
  forge: public
  archaeology: public
```

The administrative `/mvtink` subcommands are deliberately absent from that table. Giving items, forging equipment,
counting the registry and reloading the plugin are administrator work, so each of them always requires a node: the
umbrella `multiversetinker.admin` grants all five, while `multiversetinker.admin.craft`, `.give`, `.forge`, `.verify`
and `.reload` grant one subcommand each — so a server can hand an event host the item giver without the reload.
Aiming the codex at another player stays with the umbrella. Operators hold the umbrella (and every slice) by default
and a permissions plugin can grant either the umbrella or a single node to a player it trusts. `/mvtink codex` is the
only command meant for players, and `access.codex` is what decides whether they get it. A config that still carries
`access.admin-commands` is therefore ignored, not obeyed, and the plugin warns about it on startup and on
`/mvtink reload`.

```yaml
messages:
  access-denied:
    codex: "<red>You do not have permission to open the Alloy Codex.</red>"
    forge: "<red>You do not have permission to use the Forge and the Alloy Crucible.</red>"
    archaeology: "<red>You do not have permission to perform geological archaeology.</red>"
    admin-commands: "<red>You do not have permission to execute this command.</red>"
```

### Which one wins

The mode is applied **before** the node, and both directions hold:

* `access.archaeology: op` turns away even a player holding `multiversetinker.archaeology`.
* `access.archaeology: public` never consults the node at all.
* `access.archaeology: permission` is the classic behaviour: the node decides, and its `default` in `plugin.yml` says who gets it out of the box.

Anything unrecognised falls back to the default, so a typo can never lock a server out of its own forge, and the words a
server owner is likely to write are accepted: `everyone` and `all` mean `public`, `ops` and `admin` mean `op`, `node`
means `permission`. Every decision is reported on startup and on `/mvtink reload`:

```
[MultiverseTinker] Access control — codex: public · forge: public · archaeology: public · admin-commands: permission
```

The `messages.access-denied.admin-commands` message stays configurable even though its surface is not, so the wording a player
sees when a command is out of their reach is still a server's own.

### When a rule cannot work

Two rules are also called out at the same moment, because neither is something a player would report:

| Severity | When | Example |
| --- | --- | --- |
| `UNUSABLE` | A surface is set to `op` and the server has **no operators at all**, so nobody can ever reach it. | `access.forge` is `"op"` and this server has no operators, so nobody can use the Forge, the Alloy Crucible and the casting cauldron. |
| `DANGEROUS` | A leftover `access.admin-commands` is still set to a value that would have opened the administrative subcommands. It is ignored, but it is the value that used to hand every player the item giver and the instant forger. | `config.yml` still asks for `access.admin-commands: public` … Delete the key to silence this. |

Closing `access.codex` that way is reported as what it is — nobody can run a single `/mvtink` command — since the codex
is the only command players have.

A plain vanilla **anvil** is left untouched for a player who may not use the Forge: only a recognised multiblock
answers to the rule, so the plugin never gets in the way of ordinary anvil repairs.

---

## 🪨 Archaeology drop weights (`rarity-weights`)

A mineral is rolled inside its own dimension, and each **rarity** carries a weight: the higher it is, the more
often that rarity's minerals come out of the stone. With the shipped values a legendary is roughly one
extraction in a hundred.

```yaml
rarity-weights:
  common: 50
  uncommon: 30
  rare: 14
  epic: 5
  legendary: 1
```

* Weights are **relative**, so any set of numbers works: doubling `common` and leaving the rest alone makes the world twice as generous with the shallow minerals without changing the rest of the table.
* `0` takes a rarity off geology entirely — `legendary: 0` means no legendary ore drops at all, from any dimension.
* Negative values are read as `0`, a missing or non-numeric key keeps its shipped value, and a section whose weights **all** end at `0` falls back to the shipped ones: a table with no weight at all would otherwise hand out the first mineral of the dimension on every single extraction.
* The **prospector** brush still rolls rare, epic and legendary minerals twice as often, on top of whatever weights the server configured.

Changes apply on `/mvtink reload` and are echoed on startup:

```
[MultiverseTinker] Archaeology rarity weights — common 50 · uncommon 30 · rare 14 · epic 5 · legendary 1 (shipped)
```

---

## 🧭 Other sections

| Section | Purpose |
| --- | --- |
| `access` | Who may use the codex, the forge (GUI, crucible and casting), archaeology and the admin commands, as `public`, `op` or `permission`. |
| `archaeology` | Brushing system: enable flag, brushing duration, brush durability cost, per-dimension success chance, block degradation behaviour (`DEGRADE` / `COOLDOWN` / `NONE`), anti-macro cooldown and brush yields. |
| `animations` | Signature perk animations: enable flag, particle multiplier, sound toggle and per-type cooldown. |
| `equipment` | Modular equipment rules: whether a forged weapon fights with its rolled attack damage and whether armor defends with the protection rolled from its minerals, or the vanilla material values instead (vanilla wear is always disabled). |
| `smeltery` | Crucible tuning: chance to consume the lava source and the Magma Block heat-source slowdown multiplier. |
| `rarity-weights` | Rarity weights of the archaeology drop table (`common` 50, `uncommon` 30, `rare` 14, `epic` 5, `legendary` 1). Weights are relative, `0` takes a rarity off geology entirely, negatives are read as 0, and an all-zero section falls back to the shipped values so the table can never run dry. A missing or non-numeric key keeps its shipped value. |
| `messages` | Chat and action bar texts (MiniMessage format) for brushing, cooldowns and the refusal of each surface. |

---

## 🔗 Related pages

* **[Forge GUI & Modular Equipment Guide](Forge-GUI-Guide.md)**: what the wrapped lore actually prints — perks, essence focus, trait channels and the exclusive animation of every type.
* **[Perk Names](Perk-Names.md)**: the 139 mineral epithets that name every perk, and why the minerals — not just the essence — decide it.
* **[Traits & Mineral Effects](Traits-and-Effects.md)**: the trait and affinity reference behind those lore rows.
* **[Prime Alloys](Prime-Alloys.md)**: the `dynamic-alloys.yml` file that stores player-forged primes across restarts.
* **[Mechanics Overview](Mechanics-Overview.md)**: every mechanic, item and command the keys above configure.
