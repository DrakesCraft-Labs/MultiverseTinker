#!/usr/bin/env node
/**
 * Checks the page's port of the crucible against the plugin's own output.
 *
 * `docs/data/alloys.json` carries a handful of pairs the real crucible forged, recorded by
 * `SiteDataTest` from `AlloyRegistry` itself. This script replays every one of them through
 * `docs/js/mix.js` and fails when the page would predict a different alloy — a name, an id, a stat or
 * an essence that drifted means the site is promising something the crucible will not do.
 *
 * The same file carries builds the Forge really assembled, through `TinkerItemBuilder`; those are
 * replayed through `docs/js/build.js` so the build calculator cannot quote a number the item will not
 * have.
 *
 * It also validates the file's shape, so a broken catalog fails here rather than in a browser.
 *
 * Run it with `node docs/js/selfcheck.mjs`. The Pages workflow runs it before every deploy.
 */

import { readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

import { ARMOR, buildStats, TOOL, WEAPON } from './build.js';
import { canForge, COMPOSITE, fuse, LEGENDARY, pairKey, PRIME } from './mix.js';

const here = dirname(fileURLToPath(import.meta.url));
const data = JSON.parse(readFileSync(join(here, '..', 'data', 'alloys.json'), 'utf8'));

const byId = new Map(data.materials.map((material) => [material.id, material]));
const recipeByPair = new Map(data.recipes.map((recipe) => [pairKey(recipe.a, recipe.b), recipe]));
const index = { byId, recipeByPair, essenceOrder: data.essenceOrder };

const failures = [];
const check = (condition, message) => {
  if (!condition) failures.push(message);
};

/** The plugin computes a mining speed as a float, so the recorded value is compared with slack. */
const close = (expected, actual) => Math.abs(expected - actual) <= 1e-4 * Math.max(1, Math.abs(expected));

// ==========================================
// The catalog
// ==========================================
const essenceIds = new Set(data.essences.map((essence) => essence.id));
check(data.schema === 1, `unknown schema ${data.schema}`);
check(data.materials.length === data.counts.materials, 'counts.materials does not match the catalog');
check(data.materials.length >= 130, `the catalog only holds ${data.materials.length} materials`);
check(data.recipes.length === data.counts.recipes, 'counts.recipes does not match the recipes');
check(data.essences.length === data.counts.essences, 'counts.essences does not match the essences');
check(data.essenceOrder.length === data.essences.length, 'the essence order must cover every essence');

const kinds = new Set();
for (const material of data.materials) {
  const where = material.id ?? '<no id>';
  check(typeof material.id === 'string' && material.id.startsWith('mvtink_'), `${where} has no valid id`);
  check(Boolean(material.name), `${where} has no name`);
  check(['mineral', 'legendary', 'catalyst'].includes(material.kind), `${where} has kind ${material.kind}`);
  check(/^#[0-9A-Fa-f]{6}$/.test(material.color), `${where} has colour ${material.color}`);
  check(material.epithet !== undefined, `${where} has no perk epithet`);
  check(Number.isInteger(material.durability) && material.durability > 0, `${where} has no durability`);
  check(material.speed > 0 && material.damage > 0, `${where} has no forge stats`);
  check(material.essences.length >= 1 && material.essences.length <= 3, `${where} teaches ${material.essences.length} essences`);
  for (const essence of material.essences) {
    check(essenceIds.has(essence), `${where} teaches the unknown essence ${essence}`);
  }
  check(data.essenceOrder.includes(material.essences[0]), `${where} teaches an essence outside the order`);
  kinds.add(material.kind);

  if (material.kind === 'legendary') {
    check(Array.isArray(material.parents) && material.parents.length === 2, `${where} is not a recipe`);
    for (const parent of material.parents ?? []) {
      check(byId.has(parent), `${where} has the unknown parent ${parent}`);
    }
  }
  if (material.kind === 'catalyst') {
    check(Boolean(material.catalyst?.ultimate), `${where} grants no ultimate`);
    check(Boolean(material.catalyst?.armorState), `${where} grants no armor state`);
  }
}

for (const recipe of data.recipes) {
  const alloy = byId.get(recipe.id);
  check(Boolean(alloy), `the recipe ${recipe.id} is not in the catalog`);
  check(alloy?.kind === 'legendary', `the recipe ${recipe.id} is not a legendary`);
  check(byId.has(recipe.a) && byId.has(recipe.b), `the recipe ${recipe.id} names an unknown parent`);
}

// ==========================================
// The pairs the crucible actually forged
// ==========================================
const labels = { [LEGENDARY]: 'recipe', [PRIME]: 'prime', [COMPOSITE]: 'composite' };
let replayed = 0;

for (const row of data.golden) {
  const first = byId.get(row.a);
  const second = byId.get(row.b);
  const pair = `${row.a} + ${row.b}`;
  if (!first || !second) {
    failures.push(`golden pair ${pair} names a material that is not published`);
    continue;
  }

  check(canForge(first, second, index) === row.craftable,
    `the page and the crucible disagree about whether ${pair} can be forged (${row.case})`);
  check(canForge(first, second, index) === canForge(second, first, index),
    `${row.case}: the order of the parents must not change the answer`);
  if (!row.craftable) continue;

  const result = fuse(first, second, index);
  if (!result) {
    failures.push(`${row.case}: the page refuses ${pair}, the crucible forges ${row.name}`);
    continue;
  }
  replayed++;

  check(result.kind === row.fusion, `${row.case}: expected a ${labels[row.fusion]}, the page says ${result.kind}`);
  check(result.name === row.name, `${row.case}: expected the name "${row.name}", the page says "${result.name}"`);
  check(result.id === row.id, `${row.case}: expected the id "${row.id}", the page says "${result.id}"`);
  check(result.durability === row.durability,
    `${row.case}: expected ${row.durability} durability, the page says ${result.durability}`);
  check(close(row.speed, result.speed), `${row.case}: expected ${row.speed} speed, the page says ${result.speed}`);
  check(close(row.damage, result.damage), `${row.case}: expected ${row.damage} damage, the page says ${result.damage}`);
  check(result.essences.join(',') === row.essences.join(','),
    `${row.case}: expected the essences ${row.essences.join(', ')}, the page says ${result.essences.join(', ')}`);

  // The same pair, handed over in the other order, has to land on the same alloy.
  const swapped = fuse(second, first, index);
  check(swapped?.id === result.id && swapped?.name === result.name,
    `${row.case}: swapping the parents changed the result`);

  const prefix = row.fusion === PRIME ? 'mvtink_prime_' : 'mvtink_alloy_';
  if (row.fusion !== LEGENDARY) {
    check(result.id.startsWith(prefix), `${row.case}: a ${labels[row.fusion]} must be prefixed ${prefix}`);
  }
}

if (replayed < 12) failures.push(`only ${replayed} golden pairs were replayed, which proves too little`);
for (const kind of ['mineral', 'legendary', 'catalyst']) {
  check(kinds.has(kind), `the catalog publishes no ${kind}`);
}
for (const fusion of [LEGENDARY, PRIME, COMPOSITE]) {
  check(data.golden.some((row) => row.fusion === fusion), `no golden pair pins a ${labels[fusion]} fusion`);
}

// ==========================================
// The builds the Forge actually assembled
// ==========================================
const buildLabels = { [WEAPON]: 'weapon', [TOOL]: 'tool', [ARMOR]: 'armor' };
let priced = 0;

for (const row of data.goldenBuilds) {
  const parts = row.parts.map((id) => byId.get(id));
  const built = `${row.tier} ${row.type}`;
  if (parts.some((material) => !material)) {
    failures.push(`golden build ${built} names a material that is not published`);
    continue;
  }

  const result = buildStats(data.equipment, row.kind, row.type, row.tier, parts);
  if (!result) {
    failures.push(`golden build ${built}: the page cannot price it (${row.case})`);
    continue;
  }
  priced++;

  check(result.kind === row.kind, `${row.case}: expected a ${buildLabels[row.kind]}, the page says ${result.kind}`);
  check(result.durability === row.durability,
    `${row.case}: expected ${row.durability} durability, the page says ${result.durability}`);
  if (row.damage !== undefined) {
    check(close(row.damage, result.damage),
      `${row.case}: expected ${row.damage} damage, the page says ${result.damage}`);
  }
  if (row.speed !== undefined) {
    check(close(row.speed, result.speed),
      `${row.case}: expected ${row.speed} speed, the page says ${result.speed}`);
  }
  if (row.defense !== undefined) {
    check(result.defense === row.defense,
      `${row.case}: expected ${row.defense} defense, the page says ${result.defense}`);
  }
  if (row.toughness !== undefined) {
    check(close(row.toughness, result.toughness),
      `${row.case}: expected ${row.toughness} toughness, the page says ${result.toughness}`);
  }
  if (row.knockback !== undefined) {
    check(close(row.knockback, result.knockback),
      `${row.case}: expected ${row.knockback} knockback resistance, the page says ${result.knockback}`);
  }
}

if (priced < 8) failures.push(`only ${priced} golden builds were priced, which proves too little`);
for (const kind of [WEAPON, TOOL, ARMOR]) {
  check(data.goldenBuilds.some((row) => row.kind === kind), `no golden build pins a ${buildLabels[kind]}`);
}
// A weapon reads its damage from different parts depending on how many it takes, so both arities are
// pinned: one arity alone would let the calculator use the other's rule unnoticed.
for (const arity of [2, 3]) {
  check(data.goldenBuilds.some((row) => row.kind === WEAPON && row.parts.length === arity),
    `no golden build pins a ${arity}-part weapon`);
}

// ==========================================
// The equipment tables the calculator reads
// ==========================================
check(data.equipment.tiers.length >= 7, `the tier ladder only has ${data.equipment.tiers.length} tiers`);
check(data.equipment.weapons.length >= 6, `only ${data.equipment.weapons.length} weapons are published`);
check(data.equipment.armor.length === 4, `armor must publish its four slots, got ${data.equipment.armor.length}`);
check(data.equipment.tools.length >= 5, `only ${data.equipment.tools.length} tools are published`);
check(!data.equipment.tools.some((tool) => data.equipment.weapons.some((weapon) => weapon.id === tool.id)),
  'a retired alias must not be published as both a tool and a weapon');

for (const tier of data.equipment.tiers) {
  check(Number.isInteger(tier.ordinal) && tier.bonusDurability >= 0 && tier.bonusDamage >= 0
    && tier.speedMultiplier >= 1, `the ${tier.id} tier has no usable bonus`);
  check(Boolean(tier.displayName) && Boolean(tier.armorDisplayName), `the ${tier.id} tier has no name`);
}
for (const family of ['weapons', 'tools', 'armor']) {
  for (const row of data.equipment[family]) {
    check(Boolean(row.displayName), `${row.id} has no name`);
    check(Array.isArray(row.parts) && row.parts.length >= 2 && row.parts.every(Boolean),
      `${row.id} does not name its parts`);
  }
}

// ==========================================
// Report
// ==========================================
if (failures.length > 0) {
  console.error(`\n✗ The page's game math has drifted from the plugin (${failures.length} problems):`);
  for (const failure of failures) console.error(`   - ${failure}`);
  console.error('\n   docs/js/mix.js must reproduce AlloyRegistry and docs/js/build.js must reproduce'
    + ' TinkerItemBuilder. Fix it, then run this check again.\n');
  process.exit(1);
}

console.log(`✓ ${data.materials.length} materials, ${data.recipes.length} recipes and `
  + `${data.essences.length} essences are published`);
console.log(`✓ ${replayed} pairs forged by the plugin were replayed through docs/js/mix.js unchanged`);
console.log(`✓ ${priced} builds assembled by the plugin were priced through docs/js/build.js unchanged`);
