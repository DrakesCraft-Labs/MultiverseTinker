# 🧪 Alloy Mixing & Metallurgy Guide

The **Multiverse Forge** features a dedicated **Alloy Crucible** (Section 3 of the Forge GUI) capable of superheating and fusing two distinct metallurgical or crystalline minerals into powerful, specialized alloys.

---

## ⚒ How Alloy Mixing Works

1. **Access the Crucible**:
   - Open an active **Multiverse Forge Anvil**.
   - Click on the **[ 3. Alloy Crucible ]** tab in the top navigation bar.
2. **Load Components**:
   - Place **Material 1** in **Slot 20** (Ingot, Gem, Mineral, or Molten Liquid Bucket).
   - Place **Material 2** in **Slot 24** (Ingot, Gem, Mineral, or Molten Liquid Bucket).
3. **Melt & Blend**:
   - Click the **🔥 Melt & Blend Alloy** button in **Slot 31**.
   - If the recipe is valid, the thermal resonance fuses the components and yields **2x Finished Alloy Ingots** in **Slot 33**.
   - If molten buckets were used, empty buckets are returned to your inventory.
4. **Usage in Forging**:
   - The resulting alloy ingots can be used directly as materials in **Section 2 (Molds & Parts)** to forge modular heads, handles, pommels, bow limbs, bowstrings, and shield components.

---

## 📜 Complete Alloy Recipes Table

| Alloy Name | ID | Material 1 | Material 2 | Color | Durability | Speed | Attack | Trait Name | Trait Effect |
| :--- | :--- | :--- | :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| **Bronze** | `mvtink_bronze` | Copper (`mvtink_copper`) | Tin (`mvtink_tin`) | `#cd7f32` | +350 | 7.5x | +5.5 | **Dense Temper** | High structural density. +350 Durability and -20% knockback received. |
| **Electrum** | `mvtink_electrum` | Gold (`mvtink_gold`) | Silver (`mvtink_silver`) | `#fff8a6` | +220 | 11.0x | +6.0 | **Lightning Conduit** | +25% attack speed and sparks shock damage on critical hits. |
| **Invar** | `mvtink_invar` | Iron (`mvtink_iron`) | Nickel (`mvtink_nickel`) | `#b0b8b0` | +450 | 8.0x | +6.5 | **Thermal Resilience** | Immune to fire durability wear and grants +450 Durability. |
| **Manyullyn** | `mvtink_manyullyn` | Cobalt (`mvtink_cobalt`) | Ardite (`mvtink_ardite`) | `#9b59b6` | +800 | 10.5x | +9.0 | **Insatiable** | Consecutive strikes ramp up attack damage by +1.0 (stacks up to +5.0). |
| **Rose Gold** | `mvtink_rose_gold` | Gold (`mvtink_gold`) | Copper (`mvtink_copper`) | `#b76e79` | +280 | 9.0x | +5.0 | **Midas Sparkle** | Opulent blend. +40% extra experience orbs gained from mining and kills. |
| **Astral Brass** | `mvtink_astral_brass` | Pyrite (`mvtink_pyrite`) | Astralite (`mvtink_astralite`) | `#f4d03f` | +500 | 8.5x | +6.0 | **Starlight Grace** | Permanent Feather Falling and radiant celestial night aura. |
| **Void Damascus** | `mvtink_void_damascus` | Tungsten (`mvtink_tungsten`) | Voidstone (`mvtink_voidstone`) | `#2c3e50` | +950 | 9.5x | +9.5 | **Abyssal Cleave** | True armor piercing attacks that bypass 30% of target defense. |
| **Cinder Steel** | `mvtink_cinder_steel` | Steel (`mvtink_steel`) | Netherite (`mvtink_netherite`) | `#e67e22` | +1100 | 10.0x | +9.0 | **Hellfire Core** | Burns targets for 8 seconds and tool will never burn in lava or fire. |
| **Prismatic Quartz** | `mvtink_prismatic_quartz` | Quartz (`mvtink_quartz`) | Amethyst (`mvtink_amethyst`) | `#e056fd` | +400 | 9.0x | +7.0 | **Resonance Shock** | Harmonic acoustic wave dealing 2.5 AOE sonic damage to surrounding foes. |
| **Shadow Platinum** | `mvtink_shadow_platinum` | Platinum (`mvtink_platinum`) | Obsidianite (`mvtink_obsidianite`) | `#636e72` | +750 | 9.0x | +8.0 | **Umbral Veil** | Sneaking grants brief invisibility and +50% backstab damage. |
| **Ender Brass** | `mvtink_ender_brass` | Redstone (`mvtink_redstone`) | Enderite (`mvtink_enderite`) | `#1abc9c` | +650 | 8.5x | +7.5 | **Phase Step** | Shift-Right-Click teleports player forward 10 blocks (spatial jump). |
| **Adamant Steel** | `mvtink_adamant_steel` | Adamantium (`mvtink_adamantium`) | Titanium (`mvtink_titanium`) | `#2ecc71` | +1600 | 11.5x | +8.5 | **Unbreakable Will** | Indomitable core. 80% chance to completely ignore durability loss. |
| **Hellfire Bismuth** | `mvtink_hellfire_bismuth` | Bismuth (`mvtink_bismuth`) | Fire Opal (`mvtink_fire_opal`) | `#ff7675` | +550 | 8.0x | +8.0 | **Combustion** | Critical hits trigger miniature thermal explosions without block damage. |
| **Glacial Silver** | `mvtink_glacial_silver` | Silver (`mvtink_silver`) | Cryolite (`mvtink_cryolite`) | `#74b9ff` | +480 | 8.0x | +6.5 | **Absolute Frost** | Freezes targets with Slowness III and powder-snow frostbite for 4s. |
| **Sanguine Gold** | `mvtink_sanguine_gold` | Gold (`mvtink_gold`) | Sanguinite (`mvtink_sanguinite`) | `#d63031` | +380 | 9.5x | +7.5 | **Vampiric Touch** | Restores 25% of all melee damage dealt as direct player health. |
| **Cosmic Netherite** | `mvtink_cosmic_netherite` | Netherite (`mvtink_netherite`) | Celestite (`mvtink_celestite`) | `#6c5ce7` | +1400 | 12.0x | +10.5 | **Cosmic Gravity** | Singularity strike. Pulls surrounding enemies within 6 blocks toward target. |

---

## 💡 Metallurgy Tips

- **Dual Ingot Yield**: Every successful alloy smelt yields **2 ingots**, preserving exact material conservation.
- **Cross-Dimensional Blends**: Combining Overworld precious metals with Nether or End minerals unlocks endgame capabilities such as **Cosmic Netherite** and **Void Damascus**.
- **Alloy Part Forging**: Alloy ingots can also be mixed with other minerals in Section 2 (Multi-Material Casting) for combined hybrid synergies!
