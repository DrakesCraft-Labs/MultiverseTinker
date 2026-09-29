/**
 * The Alloy Crucible's mixing rules, ported from `AlloyRegistry` so the page can preview a pair
 * without the server.
 *
 * This is the only place the site states a rule of its own, so it is deliberately small and it is
 * checked against `docs/data/alloys.json` — a set of pairs the real crucible forged, recorded by the
 * plugin's own test suite. `node docs/js/selfcheck.mjs` replays them; a mismatch means this file has
 * drifted from the plugin and the page would be promising a fusion the crucible will not perform.
 *
 * Faithfulness notes:
 *  - Mining speed is a `float` in the plugin, so every step goes through `Math.fround` and the value
 *    that comes out is bit-for-bit the one the plugin stores. Without it the page could print 8.8
 *    while the game prints 8.9 for the same alloy.
 *  - Durability and attack damage are computed in Java's `int`/`double` arithmetic, which is what
 *    JavaScript does natively.
 */

export const COMPOSITE = 'composite';
export const PRIME = 'prime';
export const LEGENDARY = 'legendary';

/** Float32 constants, because the plugin adds these as `float` literals. */
const SPEED_BONUS = Math.fround(1.1);
const SPEED_BONUS_COMPOSITE = Math.fround(0.6);

/** Order-independent key of a pair, the same canonicalisation the plugin's pair index uses. */
export function pairKey(first, second) {
  return first <= second ? `${first}|${second}` : `${second}|${first}`;
}

/** The pair in id order: `(a, b)` and `(b, a)` must forge the same alloy. */
export function canonicalPair(first, second) {
  return first.id <= second.id ? [first, second] : [second, first];
}

/**
 * Whether the crucible accepts this pair as a *prime fusion*: at least one legendary parent, fused
 * with another legendary, a composite alloy, a vanilla catalyst or a freely blendable mineral.
 */
export function isPrimePair(first, second) {
  if (!first || !second || first.id === second.id) return false;
  if (first.kind === LEGENDARY) return isPrimePartner(second);
  if (second.kind === LEGENDARY) return isPrimePartner(first);
  return false;
}

function isPrimePartner(partner) {
  if (partner.kind === LEGENDARY) return true;
  // A catalyst is typed as an alloy, and so is vanilla netherite, which is why a prime can take either.
  if (partner.type === 'ALLOY') return true;
  return partner.mixable === true;
}

/** Whether the crucible may forge this exact pair at all. */
export function canForge(first, second, index) {
  if (!first || !second || first.id === second.id) return false;
  if (first.mixable && second.mixable) return true;
  if (isPrimePair(first, second)) return true;
  return Boolean(recipeFor(first, second, index));
}

function recipeFor(first, second, index) {
  return index.recipeByPair?.get(pairKey(first.id, second.id)) ?? null;
}

/**
 * What the crucible would hand a player for this pair, or `null` when it refuses it.
 *
 * A curated recipe always wins: copper and tin are both freely blendable *and* the Bronze recipe, and
 * the crucible makes Bronze, not `Copper-Tin Alloy`.
 */
export function fuse(first, second, index) {
  if (!first || !second || first.id === second.id) return null;

  const recipe = recipeFor(first, second, index);
  if (recipe) {
    const material = index.byId.get(recipe.id);
    if (material) {
      return {
        kind: LEGENDARY,
        name: material.name,
        id: material.id,
        color: material.color,
        durability: material.durability,
        speed: material.speed,
        damage: material.damage,
        essences: material.essences,
        trait: material.trait,
        traitDesc: material.traitDesc,
        parents: [first.id, second.id],
      };
    }
  }

  const prime = isPrimePair(first, second);
  if (!prime && !(first.mixable && second.mixable)) return null;

  const [head, tail] = canonicalPair(first, second);

  // Catalysts carry no metallurgical mass: a prime forged with one is built from the alloy it
  // catalysed, doubled, so the two parents below collapse onto the same material.
  const statA = head.kind === 'catalyst' ? tail : head;
  const statB = tail.kind === 'catalyst' ? head : tail;

  const durability = prime
    ? Math.round((statA.durability + statB.durability) * 0.78) + 120
    : Math.round((statA.durability + statB.durability) * 0.7) + 60;

  const averageSpeed = Math.fround(Math.fround(statA.speed + statB.speed) / 2);
  const speed = Math.fround(averageSpeed + (prime ? SPEED_BONUS : SPEED_BONUS_COMPOSITE));

  const damage = prime
    ? (statA.damage + statB.damage) / 2 + 2.2
    : (statA.damage + statB.damage) / 2 + 1.2;

  const name = prime
    ? `${head.name} ${tail.name} Prime`
    : `${head.name}-${tail.name} Alloy`;

  const id = prime
    ? `mvtink_prime_${strip(head.id)}_${strip(tail.id)}`
    : `mvtink_alloy_${strip(head.id)}_${strip(tail.id)}`;

  const catalystMaterial = head.kind === 'catalyst' ? head : tail.kind === 'catalyst' ? tail : null;

  return {
    kind: prime ? PRIME : COMPOSITE,
    name,
    id,
    color: blend(head.color, tail.color),
    durability,
    speed,
    damage,
    essences: inherit(first, second, index),
    trait: prime ? `Prime ${first.trait}` : `${first.trait}-${second.trait}`,
    traitDesc: prime
      ? catalystMaterial
        ? `Prime fusion catalysed by ${catalystMaterial.name}. ${catalystMaterial.description}`
        : `Prime fusion of ${first.name} and ${second.name}. Inherits the full essence of both legendary components.`
      : `Composite metallurgy combining ${first.name} and ${second.name} properties.`,
    parents: [head.id, tail.id],
    catalyst: prime ? catalystMaterial?.catalyst ?? null : null,
    massless: prime && Boolean(catalystMaterial),
  };
}

/** The essences a fused pair keeps: the union of both parents, strongest identity first, capped at 3. */
export function inherit(first, second, index) {
  const union = new Set([...first.essences, ...second.essences]);
  const ordered = index.essenceOrder.filter((essence) => union.has(essence));
  for (const essence of union) {
    if (!ordered.includes(essence)) ordered.push(essence);
  }
  return ordered.slice(0, 3);
}

function strip(id) {
  return id.replace('mvtink_', '');
}

/** The average of two colours, exactly as the plugin blends them. */
export function blend(first, second) {
  const left = parseHex(first);
  const right = parseHex(second);
  if (!left || !right) return '#D4AF37';
  const channel = (a, b) => Math.trunc((a + b) / 2);
  return `#${toHex(channel(left[0], right[0]))}${toHex(channel(left[1], right[1]))}${toHex(channel(left[2], right[2]))}`;
}

function parseHex(hex) {
  const text = String(hex ?? '').replace('#', '');
  if (text.length !== 6) return null;
  const value = Number.parseInt(text, 16);
  if (Number.isNaN(value)) return null;
  return [(value >> 16) & 0xff, (value >> 8) & 0xff, value & 0xff];
}

function toHex(value) {
  return value.toString(16).toUpperCase().padStart(2, '0');
}
