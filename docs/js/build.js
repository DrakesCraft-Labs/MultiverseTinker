/**
 * The Forge's equipment arithmetic, ported from `TinkerItemBuilder` so the page can price a build
 * without the server.
 *
 * This is the second place the site states a rule of its own, so it is kept small and it is checked
 * against `docs/data/alloys.json` — a set of builds the Forge really assembled, recorded by
 * `SiteDataTest`. `node docs/js/selfcheck.mjs` replays them; a mismatch means this file has drifted
 * from the plugin and the page would be quoting numbers the item will not have.
 *
 * Everything that is a *number* comes from the file rather than from here: the tier ladder, the base
 * durability of an armor slot, its bare protection. What lives here is only how they are added up.
 *
 * Faithfulness notes:
 *  - A part takes one to three minerals and splits its stats between them, exactly as
 *    `PartComposition.fromMaterials` does, down to the `0.333 / 0.333 / 0.334` of a three-mineral
 *    part that makes the ratios add up to one.
 *  - Durability is Java's `int` arithmetic in places (`part3 / 2`, `binding / 2`, `trim / 2`), which
 *    truncates: `Math.trunc` mirrors that instead of rounding.
 *  - Attack damage and mining speed are doubles in the plugin and only become `float` when the item
 *    stores them, so a build's damage is compared to the recorded value with slack.
 */

export const WEAPON = 'weapon';
export const TOOL = 'tool';
export const ARMOR = 'armor';

/** The bare swing every modular weapon and tool is measured from. */
const BASE_DAMAGE = 4.0;

/** How a part splits between its minerals, straight from `PartComposition.fromMaterials`. */
const PART_RATIOS = [[1.0], [0.5, 0.5], [0.333, 0.333, 0.334]];

/** Java's `int` division: it truncates, so the durability of a pommel is floored a half. */
const half = (value) => Math.trunc(value / 2);

/** One row of an equipment table, or `null` when the id is not published. */
export function findRow(rows, id) {
  return rows.find((row) => row.id === id) ?? null;
}

/** How a part splits its stats between the minerals cast into it. */
export function compose(materials) {
  if (!Array.isArray(materials) || materials.length < 1 || materials.length > 3) {
    throw new Error(`a part takes one to three minerals, got ${materials ? materials.length : materials}`);
  }
  const ratios = PART_RATIOS[materials.length - 1];
  return materials.map((material, index) => ({ material, ratio: ratios[index] }));
}

/**
 * What one forged part contributes, as the Forge reads it: the durability a material rolls, the speed
 * it yields and the attack impact it adds, each weighted by its share of the part.
 */
export function compositionStats(entries) {
  const total = (field) => entries.reduce((sum, entry) => sum + entry.material[field] * entry.ratio, 0);
  return {
    // The mineral the game names the part after, which is the first one cast into it.
    material: entries[0].material,
    count: entries.length,
    durability: Math.round(total('durability')),
    speed: total('speed'),
    damage: total('damage'),
  };
}

/**
 * The final numbers of one build, or `null` when the equipment, the tier or a part is not published.
 *
 * @param equipment the `equipment` tables from `alloys.json`
 * @param kind      `WEAPON`, `TOOL` or `ARMOR`
 * @param typeId    the equipment id, e.g. `SWORD` or `CHESTPLATE`
 * @param tierId    the evolution tier id, e.g. `IRON`
 * @param materials the mineral of each part, in the order the equipment lists them
 */
export function buildStats(equipment, kind, typeId, tierId, materials) {
  const tier = findRow(equipment.tiers, tierId);
  if (!tier || !Array.isArray(materials) || materials.some((material) => !material)) return null;

  const parts = materials.map((material) => compositionStats(compose([material])));
  const table = kind === WEAPON ? equipment.weapons : kind === TOOL ? equipment.tools : kind === ARMOR ? equipment.armor : null;
  const type = table ? findRow(table, typeId) : null;
  if (!type) return null;
  if (parts.length !== type.parts.length) return null;

  if (kind === WEAPON) return weapon(type, tier, parts);
  if (kind === TOOL) return tool(type, tier, parts);
  return armor(type, tier, parts);
}

/**
 * A weapon's durability and damage.
 *
 * <p>The arity matters more than it looks: a three-part weapon reads its damage from the head and a
 * third of the pommel — the handle adds nothing but durability — while a two-part one reads half of
 * its second part. The tier name in the display is its own, which is why armor carries a second one.</p>
 */
function weapon(type, tier, parts) {
  const [first, second, third] = parts;

  const durability = first.durability + second.durability
    + (type.twoPart ? 0 : half(third.durability)) + tier.bonusDurability;
  const damage = BASE_DAMAGE + first.damage
    + (type.twoPart ? second.damage / 2 : third.damage / 3) + tier.bonusDamage;

  return { kind: WEAPON, name: `${tier.displayName} ${parts[0].material.name} ${type.displayName}`, durability, damage };
}

/** A tool's durability, mining speed and attack damage: the tier scales the head it is swung with. */
function tool(type, tier, parts) {
  const [head, rod, binding] = parts;

  return {
    kind: TOOL,
    name: `${tier.displayName} ${head.material.name} ${type.displayName}`,
    durability: head.durability + rod.durability + half(binding.durability) + tier.bonusDurability,
    speed: head.speed * tier.speedMultiplier,
    damage: BASE_DAMAGE + head.damage + rod.damage / 3 + tier.bonusDamage,
  };
}

/**
 * An armor piece's durability and the protection it rolls.
 *
 * <p>Each part owns one number: the plate rolls Defense, the lining Toughness and the trim knockback
 * resistance. The tier adds its own step to Defense and half of it to Toughness, which is what makes a
 * piece defend harder as it levels.</p>
 */
function armor(type, tier, parts) {
  const [plate, lining, trim] = parts;

  return {
    kind: ARMOR,
    name: `${tier.armorDisplayName} ${plate.material.name} ${type.displayName}`,
    durability: type.baseDurability + plate.durability + lining.durability
      + half(trim.durability) + tier.bonusDurability,
    defense: type.baseDefense + Math.round(plate.damage / 3) + tier.ordinal,
    toughness: type.baseToughness + lining.damage / 4 + (tier.ordinal * 0.5),
    knockback: (trim.count * 0.05) + (tier.ordinal * 0.02),
  };
}
