/**
 * The build calculator: pick a weapon, a tool or an armor piece, its parts and its tier, and read back
 * the numbers the Forge would give the item.
 *
 * The arithmetic itself lives in `build.js`; this file only builds the controls and prints the result.
 * Nothing here knows a game rule — the equipment tables and the part names arrive from
 * `docs/data/alloys.json`, which is rendered from the plugin's own enums.
 */

import { ARMOR, buildStats, findRow, TOOL, WEAPON } from './build.js';

/** The equipment families the calculator offers, in the order the buttons read. */
const KINDS = [WEAPON, TOOL, ARMOR];

/**
 * Builds the calculator and returns its `render`, which is idempotent: the page re-runs it whenever the
 * language changes, and the controls are rebuilt from this module's own state, exactly like the
 * crucible's selectors.
 *
 * @param env the page's shared helpers: the published tables, the materials, and the DOM/i18n functions
 */
export function createBuilder({ equipment, materials, t, format, label, number, el, mount, text }) {
  const byId = new Map(materials.map((entry) => [entry.id, entry]));
  const minerals = materials.filter((entry) => entry.kind === 'mineral');

  const state = {
    kind: WEAPON,
    // A build's type belongs to its family: switching to tools must not carry the sword over.
    types: { [WEAPON]: 'SWORD', [TOOL]: 'PICKAXE', [ARMOR]: 'CHESTPLATE' },
    tier: 'IRON',
    parts: {
      // Each part starts on a different mineral, so the first build already shows three contributions.
      [WEAPON]: [0, 1, 2].map((offset) => minerals[offset % minerals.length].id),
      [TOOL]: [3, 4, 5].map((offset) => minerals[offset % minerals.length].id),
      [ARMOR]: [6, 7, 8].map((offset) => minerals[offset % minerals.length].id),
    },
  };

  const table = () => (state.kind === WEAPON ? equipment.weapons
    : state.kind === TOOL ? equipment.tools : equipment.armor);
  const currentType = () => findRow(table(), state.types[state.kind]);
  const currentTier = () => findRow(equipment.tiers, state.tier);
  const tierName = (row) => (state.kind === ARMOR ? row.armorDisplayName : row.displayName);

  // ==========================================
  // CONTROLS
  // ==========================================

  function field(labelText, control) {
    return el('div', { class: 'field' }, el('label', { text: labelText }), control);
  }

  function select(value, onChange, options) {
    return el('select', {
      autocomplete: 'off',
      onchange: (event) => {
        onChange(event.target.value);
        render();
      },
    }, options.map((option) => el('option', { value: option.value, text: option.text, selected: value === option.value })));
  }

  /** The same dropdown, but with its options grouped by the kind of material they are. */
  function groupedSelect(value, onChange, groups) {
    return el('select', {
      autocomplete: 'off',
      onchange: (event) => {
        onChange(event.target.value);
        render();
      },
    }, groups.map(([groupLabel, entries]) => el('optgroup', { label: groupLabel },
      entries.map((entry) => el('option', { value: entry.id, text: entry.name, selected: value === entry.id })))));
  }

  /** A part picker: the full catalog, grouped the way the crucible groups it. */
  function partSelect(slot) {
    const chosen = state.parts[state.kind][slot];
    const groups = ['mineral', 'legendary', 'catalyst']
      .map((kind) => [label('kind', kind), materials.filter((entry) => entry.kind === kind)]);

    return groupedSelect(chosen, (value) => {
      state.parts[state.kind][slot] = value;
    }, groups);
  }

  function controls() {
    const type = currentType();
    return [
      field(t('builderMode'), select(state.kind, (value) => {
        state.kind = value;
      }, KINDS.map((kind) => ({ value: kind, text: label('equipmentKind', kind) })))),
      field(t('builderType'), select(state.types[state.kind], (value) => {
        state.types[state.kind] = value;
      }, table().map((row) => ({ value: row.id, text: row.displayName })))),
      field(t('builderTier'), select(state.tier, (value) => {
        state.tier = value;
      }, equipment.tiers.map((row) => ({ value: row.id, text: tierName(row) })))),
      ...type.parts.map((partName, slot) => field(partName, partSelect(slot))),
    ];
  }

  // ==========================================
  // RESULT
  // ==========================================

  function rows(result) {
    // Armor is the one family with no attack damage at all: it answers a hit with protection instead,
    // so the row is left out rather than printed as a blank.
    const out = [[t('durability'), number(result.durability)]];
    if (result.kind !== ARMOR) out.push([t('damage'), number(result.damage, 1)]);
    if (result.kind === TOOL) out.push([t('speed'), `${number(result.speed, 1)}x`]);
    if (result.kind === ARMOR) {
      out.push([t('defense'), number(result.defense)]);
      out.push([t('toughness'), number(result.toughness, 1)]);
      out.push([t('knockback'), `${number(result.knockback * 100)}%`]);
    }
    return out;
  }

  /** What the tier itself contributes, worded for this family. */
  function note(tier) {
    const values = {
      tier: tierName(tier),
      durability: number(tier.bonusDurability),
      damage: number(tier.bonusDamage, 1),
      speed: number(tier.speedMultiplier, 2),
    };
    return format(t(state.kind === WEAPON ? 'builderWeaponNote'
      : state.kind === TOOL ? 'builderToolNote' : 'builderArmorNote'), values);
  }

  function resultCard() {
    const type = currentType();
    const tier = currentTier();
    const chosen = state.parts[state.kind].slice(0, type.parts.length);
    const result = buildStats(equipment, state.kind, type.id, tier.id, chosen.map((id) => byId.get(id)));

    if (!result) {
      mount('builderResult', el('p', { class: 'hint', text: t('builderRefused') }));
      return;
    }

    mount('builderResult', el('div', {
      class: 'result-card',
      style: { '--swatch': byId.get(chosen[0]).color },
    },
    el('header', {},
      el('span', { class: 'icon' }),
      el('div', {},
        el('h4', { text: result.name }),
        el('div', { class: 'id', text: `${label('equipmentKind', state.kind)} · ${tierName(tier)}` })),
      el('span', { class: 'badge', text: t('builderResultTitle') })),
    el('dl', { class: 'kv' }, rows(result).map(([term, value]) => el('div', {},
      el('dt', { text: term }),
      el('dd', { text: value })))),
    el('p', { class: 'note', text: note(tier) }),
    el('p', { class: 'note', text: t('builderSourceNote') })));
  }

  // ==========================================
  // RENDER
  // ==========================================

  function render() {
    text('builderTitle', t('builderTitle'));
    text('builderHint', t('builderHint'));
    mount('builderControls', controls());
    resultCard();
  }

  return { render };
}
