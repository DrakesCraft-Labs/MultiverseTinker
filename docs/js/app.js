/**
 * The interactive half of the alloy site.
 *
 * It reads one generated file (`docs/data/alloys.json`, rendered from the plugin registries by
 * `SiteDataTest`) and turns it into a catalog, a detail panel and a live crucible preview. Nothing here
 * states a game rule of its own: the preview calls `mix.js`, which is checked against pairs the real
 * crucible forged.
 */

import { createBuilder } from './builder.js';
import { format, LABELS, LANGUAGES, preferredLanguage, STRINGS } from './i18n.js';
import { COMPOSITE, fuse, LEGENDARY, PRIME } from './mix.js';

const STORAGE_KEY = 'mvtink.lang';

const state = {
  lang: preferredLanguage(safeRead(STORAGE_KEY)),
  kind: 'all',
  origin: '',
  rarity: '',
  type: '',
  sort: 'name',
  query: '',
  selected: null,
  slotA: '',
  slotB: '',
};

let data;
let index;
let maxStats;
let builder;

start().catch((error) => {
  document.getElementById('detail').textContent = `Could not load the catalog (${error.message}).`
    + ' Open the page from a web server, not from your filesystem.';
  console.error(error);
});

async function start() {
  const response = await fetch('data/alloys.json');
  if (!response.ok) throw new Error(`${response.status} ${response.statusText}`);
  data = await response.json();

  index = {
    byId: new Map(data.materials.map((entry) => [entry.id, entry])),
    recipeByPair: new Map(data.recipes.map((recipe) => [keyOf(recipe.a, recipe.b), recipe])),
    essenceOrder: data.essenceOrder,
    essences: new Map(data.essences.map((essence) => [essence.id, essence])),
  };

  maxStats = {
    durability: Math.max(...data.materials.map((entry) => entry.durability)),
    speed: Math.max(...data.materials.map((entry) => entry.speed)),
    damage: Math.max(...data.materials.map((entry) => entry.damage)),
  };

  const first = data.materials[0];
  state.selected = first.id;
  state.slotA = first.id;
  state.slotB = suggestionFor(first);

  // The build calculator keeps its own state and rebuilds its controls from it, so the page hands it
  // the shared helpers once and only asks it to draw.
  builder = createBuilder({
    equipment: data.equipment,
    materials: data.materials,
    t,
    format,
    label,
    number,
    el,
    mount,
    text,
  });

  document.documentElement.lang = state.lang;
  render();
}

const keyOf = (a, b) => (a <= b ? `${a}|${b}` : `${b}|${a}`);

/** The best-combination page of this language, which is named differently in each. */
const combosDoc = () => (state.lang === 'es'
  ? 'Wiki-es/Mejores-Combos-Espada-Arco.md'
  : 'Wiki-en/Best-Sword-and-Bow-Combos.md');
const t = (key) => STRINGS[state.lang][key] ?? key;
const label = (group, value) => LABELS[state.lang][group]?.[value] ?? value;
const material = (id) => index.byId.get(id) ?? null;

/** A partner that would actually forge something with this material, so the preview starts useful. */
function suggestionFor(entry) {
  const others = data.materials.filter((candidate) => candidate.id !== entry.id);
  const preferred = entry.kind === 'legendary' ? 'catalyst'
    : entry.kind === 'catalyst' ? 'legendary' : 'mineral';
  return (others.find((candidate) => candidate.kind === preferred && candidate.id !== entry.id)
    ?? others[0]).id;
}

// ==========================================
// SMALL DOM HELPERS
// ==========================================

function el(tag, props = {}, ...children) {
  const node = document.createElement(tag);
  for (const [key, value] of Object.entries(props)) {
    if (value === null || value === undefined || value === false) continue;
    if (key === 'class') node.className = value;
    else if (key === 'text') node.textContent = value;
    // A custom property (`--swatch`) cannot be written by assigning to `node.style`, so it goes through
    // `setProperty`; everything else keeps the plain assignment that handles camelCase for free.
    else if (key === 'style') {
      for (const [name, setting] of Object.entries(value)) {
        if (name.startsWith('--')) node.style.setProperty(name, setting);
        else Object.assign(node.style, { [name]: setting });
      }
    }
    else if (key.startsWith('on')) node.addEventListener(key.slice(2).toLowerCase(), value);
    else node.setAttribute(key, value === true ? '' : String(value));
  }
  for (const child of children.flat()) {
    if (child === null || child === undefined || child === false) continue;
    node.append(child instanceof Node ? child : document.createTextNode(String(child)));
  }
  return node;
}

function mount(id, ...children) {
  const node = document.getElementById(id);
  node.replaceChildren(...children.flat().filter(Boolean));
  return node;
}

function text(id, value) {
  document.getElementById(id).textContent = value;
}

function number(value, digits = 0) {
  return new Intl.NumberFormat(state.lang === 'es' ? 'es-ES' : 'en-US', {
    minimumFractionDigits: digits,
    maximumFractionDigits: digits,
  }).format(value);
}

// ==========================================
// LANGUAGE
// ==========================================

function setLanguage(code) {
  state.lang = code;
  safeWrite(STORAGE_KEY, code);
  document.documentElement.lang = code;
  render();
}

function renderLanguages() {
  mount('langs', LANGUAGES.map((code) => el('button', {
    class: 'lang-btn',
    type: 'button',
    title: t('switchHint'),
    'aria-pressed': String(state.lang === code),
    text: STRINGS[code].lang,
    onclick: () => setLanguage(code),
  })));
}

// ==========================================
// FILTERS
// ==========================================

function renderFilters() {
  mount('chips', ['all', 'mineral', 'legendary', 'catalyst'].map((kind) => el('button', {
    class: 'chip',
    type: 'button',
    'aria-pressed': String(state.kind === kind),
    text: kind === 'all' ? t('anyValue') : label('kind', kind),
    onclick: () => {
      state.kind = kind;
      render();
    },
  })));

  const dropdown = (key, group, labelText) => el('div', { class: 'field' },
    el('label', { for: `f-${key}`, text: labelText }),
    el('select', {
      id: `f-${key}`,
      // The page rebuilds its controls from `state` on every render, so a browser that restores the
      // previous form values onto a fresh select would disagree with the list it is filtering.
      autocomplete: 'off',
      onchange: (event) => {
        state[key] = event.target.value;
        render();
      },
    },
    el('option', { value: '', text: t('anyValue') }),
    Object.keys(LABELS[state.lang][group]).map((value) => el('option', {
      value,
      text: label(group, value),
      selected: state[key] === value,
    }))));

  mount('filters',
    el('div', { class: 'field' },
      el('label', { for: 'f-query', text: t('search') }),
      el('input', {
        id: 'f-query',
        type: 'search',
        autocomplete: 'off',
        placeholder: t('search'),
        value: state.query,
        oninput: (event) => {
          state.query = event.target.value;
          renderList();
        },
      })),
    dropdown('origin', 'origin', t('filterOrigin')),
    dropdown('rarity', 'rarity', t('filterRarity')),
    dropdown('type', 'type', t('filterType')),
    el('div', { class: 'field' },
      el('label', { for: 'f-sort', text: t('sortBy') }),
      el('select', {
        id: 'f-sort',
        autocomplete: 'off',
        onchange: (event) => {
          state.sort = event.target.value;
          renderList();
        },
      }, [['name', 'sortName'], ['durability', 'sortDurability'], ['damage', 'sortDamage'], ['speed', 'sortSpeed']]
        .map(([value, key]) => el('option', {
          value,
          text: t(key),
          selected: state.sort === value,
        })))));
}

// ==========================================
// RENDER
// ==========================================

function render() {
  text('title', t('title'));
  text('subtitle', t('subtitle'));
  text('generated', `✦ ${format(t('generatedNote'), {
    materials: number(data.counts.materials),
    recipes: data.counts.recipes,
    essences: data.counts.essences,
  })}`);
  text('gameText', `🌐 ${t('gameTextNote')}`);
  text('crucibleTitle', t('crucibleTitle'));
  text('crucibleHint', t('crucibleHint'));
  text('footerTitle', t('footerTitle'));
  text('footerNote', t('footerNote'));

  const repo = 'https://github.com/DrakesCraft-Labs/MultiverseTinker/blob/main';
  const wiki = state.lang === 'es' ? 'Wiki-es' : 'Wiki-en';
  mount('footerLinks',
    el('li', {}, el('a', { href: `${repo}/${wiki}`, text: t('footerWiki') })),
    el('li', {}, el('a', { href: `${repo}/${combosDoc()}`, text: t('footerCombos') })),
    el('li', {}, el('a', {
      href: `${repo}/src/test/java/com/chagui68/multiversetinker/site/SiteData.java`,
      text: t('footerReadme'),
    })));

  const clear = document.getElementById('clear');
  clear.textContent = t('clearFilters');
  clear.onclick = () => {
    state.kind = 'all';
    state.origin = '';
    state.rarity = '';
    state.type = '';
    state.query = '';
    render();
  };

  renderLanguages();
  renderFilters();
  renderList();
  renderDetail();
  renderCrucible();
  builder.render();
}

// ==========================================
// LIST
// ==========================================

function visible() {
  const query = state.query.trim().toLowerCase();
  const matches = data.materials.filter((entry) => {
    if (state.kind !== 'all' && entry.kind !== state.kind) return false;
    if (state.origin && entry.origin !== state.origin) return false;
    if (state.rarity && entry.rarity !== state.rarity) return false;
    if (state.type && entry.type !== state.type) return false;
    if (!query) return true;
    return `${entry.name} ${entry.id} ${entry.trait} ${entry.epithet}`.toLowerCase().includes(query);
  });

  matches.sort((left, right) => {
    if (state.sort === 'name') return left.name.localeCompare(right.name, state.lang);
    return (right[state.sort] - left[state.sort]) || left.name.localeCompare(right.name, state.lang);
  });
  return matches;
}

function renderList() {
  const matches = visible();
  text('count', format(t('showing'), {
    shown: number(matches.length),
    total: number(data.materials.length),
  }));

  if (matches.length === 0) {
    mount('list', el('p', { class: 'hint', text: t('noMatches') }));
    return;
  }

  mount('list', matches.map((entry) => el('button', {
    class: 'card',
    type: 'button',
    role: 'option',
    'aria-selected': String(entry.id === state.selected),
    style: { '--swatch': entry.color },
    onclick: () => select(entry.id),
  },
  el('span', { class: 'icon' }),
  el('span', {},
    el('div', { class: 'title', text: entry.name }),
    el('div', { class: 'sub', text: `${label('rarity', entry.rarity)} · ${where(entry, true)}` })),
  el('span', { class: 'trait', text: entry.trait }))));
}

function select(id) {
  state.selected = id;
  renderList();
  renderDetail();
}

// ==========================================
// DETAIL
// ==========================================

function renderDetail() {
  const entry = material(state.selected);
  if (!entry) {
    mount('detail', el('p', { class: 'empty', text: t('selectHint') }));
    return;
  }

  mount('detail',
    detailHead(entry),
    factGrid(entry),
    statsSection(entry),
    traitSection(entry),
    essenceSection(entry),
    epithetSection(entry),
    ...recipeSections(entry),
    el('div', {}, el('button', {
      class: 'cta',
      type: 'button',
      text: `⚗ ${t('tryInCrucible')}`,
      onclick: () => {
        state.slotA = entry.id;
        state.slotB = suggestionFor(entry);
        renderCrucible();
        document.getElementById('crucible').scrollIntoView({ behavior: 'smooth', block: 'start' });
      },
    })));
}

function detailHead(entry) {
  return el('div', { class: 'detail-head', style: { '--swatch': entry.color } },
    el('span', { class: 'icon' }),
    el('div', {},
      el('h2', { text: entry.name }),
      el('div', { class: 'id' }, entry.id, ' ', el('button', {
        class: 'inline-btn',
        type: 'button',
        text: t('copy'),
        onclick: (event) => copy(entry.id, event.target),
      }))),
    el('span', { class: 'badge', text: label('kind', entry.kind) }));
}

async function copy(value, button) {
  try {
    await navigator.clipboard.writeText(value);
    const previous = button.textContent;
    button.textContent = t('copied');
    setTimeout(() => {
      button.textContent = previous;
    }, 1200);
  } catch {
    // A browser that refuses the clipboard still shows the id next to the button.
  }
}

/**
 * Where a material comes from.
 *
 * The registry gives every alloy a placeholder Overworld origin, so quoting it would claim Cosmic
 * Netherite is dug out of stone. What is true instead is how it is made, which is what the recipe and
 * catalyst sections spell out in full.
 */
function where(entry, short = false) {
  if (entry.kind === 'legendary') return t(short ? 'recipeShort' : 'fromRecipe');
  if (entry.kind === 'catalyst') return t(short ? 'catalystShort' : 'fromCatalyst');
  return `${label('origin', entry.origin)} · ${entry.source}`;
}

/** A catalyst is a real vanilla item, so its "type" is the item a player actually drops in the crucible. */
function typeFact(entry) {
  if (entry.kind === 'catalyst' && entry.catalyst) {
    return `${t('vanillaItem')} · ${entry.catalyst.item}`;
  }
  return label('type', entry.type);
}

function factGrid(entry) {
  const facts = [
    [t('originLabel'), where(entry)],
    [t('rarityLabel'), label('rarity', entry.rarity)],
    [t('typeLabel'), typeFact(entry)],
    [t('meltLabel'), format(t('meltUnit'), { ticks: entry.melt, seconds: Math.round(entry.melt / 20) })],
    [entry.mixable ? t('mixableYes') : t('mixableNo'), entry.mixable ? '✔' : '✘'],
  ];

  return el('div', {},
    el('dl', { class: 'kv' }, facts.map(([term, value]) => el('div', {},
      el('dt', { text: term }),
      el('dd', { text: value })))),
    entry.mixable ? null : el('p', { class: 'hint', text: t('mixableNoWhy') }));
}

function statsSection(entry) {
  const total = data.materials.length;
  const rows = [
    ['durability', t('durability'), entry.durability, 0, ''],
    ['speed', t('speed'), entry.speed, 1, 'x'],
    ['damage', t('damage'), entry.damage, 1, ''],
  ];

  return el('section', {},
    el('h3', { class: 'section', text: t('statsTitle') }),
    el('p', { class: 'hint', text: t('statsHint') }),
    rows.map(([key, name, value, digits, suffix]) => {
      const rank = 1 + data.materials.filter((other) => other[key] > value).length;
      return el('div', { class: 'stat' },
        el('div', { class: 'row' },
          el('span', { text: name }),
          el('span', {}, el('b', { text: `${number(value, digits)}${suffix}` }), ' ',
            el('span', { class: 'sub', text: format(t('rank'), { rank, total }) }))),
        el('div', { class: 'bar' },
          el('span', { style: { width: `${Math.max(3, (value / maxStats[key]) * 100)}%` } })));
    }));
}

function traitSection(entry) {
  // The plugin only overrides the weapon and armor wording, so most materials say the same thing on
  // every channel. Grouping by text keeps one line for that case while still naming all three.
  const channels = [
    [t('channelWeapon'), entry.weapon],
    [t('channelTool'), entry.traitDesc],
    [t('channelArmor'), entry.armor],
  ];

  const grouped = new Map();
  for (const [who, what] of channels) {
    if (!what) continue;
    grouped.set(what, [...(grouped.get(what) ?? []), who]);
  }
  const blocks = [...grouped].map(([what, names]) => el('div', { class: 'channel' },
    el('div', { class: 'who', text: names.join(' · ') }),
    el('div', { class: 'what game-text', text: what })));

  return el('section', {},
    el('h3', { class: 'section', text: t('traitTitle') }),
    el('p', { class: 'hint', text: t('channelHint') }),
    el('div', { class: 'trait-block' },
      el('div', {}, el('strong', { text: entry.trait })),
      ...blocks));
}

function essenceSection(entry) {
  const effects = entry.essences.map((id) => index.essences.get(id)).filter(Boolean);
  if (effects.length === 0) return null;

  const channels = [[t('channelWeapon'), 'weapon'], [t('channelTool'), 'tool'], [t('channelArmor'), 'armor']];

  return el('section', {},
    el('h3', { class: 'section', text: t('essencesTitle') }),
    el('p', { class: 'hint', text: t('essencesHint') }),
    el('div', { class: 'tags' }, effects.map((essence) => el('span', {
      class: 'tag',
      style: { '--swatch': essence.color },
      title: essence.name,
    },
    el('span', { class: 'dot' }),
    el('strong', { text: essence.name }),
    state.lang === 'es' ? el('span', { class: 'es', text: essence.es }) : null))),
    el('div', { style: { 'margin-top': '12px' } }, channels.map(([who, field]) => el('div', { class: 'channel' },
      el('div', { class: 'who', text: who }),
      el('div', { class: 'what' }, effects.map((essence) => el('div', { class: 'game-text' },
        `${essence.name}: ${essence[field]}`)))))));
}

function epithetSection(entry) {
  return el('section', {},
    el('h3', { class: 'section', text: t('epithetTitle') }),
    el('p', { class: 'hint', text: t('epithetHint') }),
    el('div', { class: 'tags' }, el('span', { class: 'tag' }, el('strong', { text: entry.epithet }))));
}

/** The recipe of a legendary, the sigil of a catalyst, and what a legendary can be primed into. */
function recipeSections(entry) {
  const sections = [];

  if (entry.kind === 'legendary' && entry.parents) {
    sections.push(el('section', {},
      el('h3', { class: 'section', text: t('recipeTitle') }),
      el('p', { class: 'hint', text: t('recipeHint') }),
      el('div', { class: 'parents' },
        entry.parents.map(material).filter(Boolean).map((parent) => el('button', {
          class: 'parent',
          type: 'button',
          style: { '--swatch': parent.color },
          text: parent.name,
          onclick: () => select(parent.id),
        }))),
      el('section', {},
        el('h3', { class: 'section', text: t('primeTitle') }),
        el('p', { class: 'hint', text: t('primeHint') }))));
  }

  if (entry.kind === 'catalyst' && entry.catalyst) {
    const sigil = entry.catalyst;
    sections.push(el('section', {},
      el('h3', { class: 'section', text: t('catalystTitle') }),
      el('p', { class: 'hint', text: t('catalystHint') }),
      el('div', { class: 'catalyst-block' },
        el('div', { class: 'channel' },
          el('div', { class: 'who', text: t('ultimate') }),
          el('div', { class: 'what' }, el('strong', { text: sigil.ultimate }), ' ',
            el('span', { class: 'game-text', text: sigil.ultimateDesc }))),
        el('div', { class: 'channel' },
          el('div', { class: 'who', text: t('armorState') }),
          el('div', { class: 'what' }, el('strong', { text: sigil.armorState }), ' ',
            el('span', { class: 'game-text', text: sigil.armorStateDesc }))),
        el('p', { class: 'note', text: format(t('catalystPrimes'), { count: sigil.primes }) }))));
  }

  return sections;
}

// ==========================================
// CRUCIBLE
// ==========================================

function buildSelectors() {
  const groups = ['mineral', 'legendary', 'catalyst']
    .map((kind) => [kind, data.materials.filter((entry) => entry.kind === kind)]);

  const selector = (slot, letter) => el('div', { class: 'field' },
    el('label', { for: `s-${letter}`, text: t(slot) }),
    el('select', {
      id: `s-${letter}`,
      autocomplete: 'off',
      onchange: (event) => {
        state[slot] = event.target.value;
        renderCrucible();
      },
    }, groups.map(([kind, entries]) => el('optgroup', { label: label('kind', kind) },
      entries.map((entry) => el('option', {
        value: entry.id,
        text: entry.name,
        selected: state[slot] === entry.id,
      }))))));

  mount('crucibleSelectors',
    selector('slotA', 'A'),
    selector('slotB', 'B'),
    el('button', {
      class: 'inline-btn',
      type: 'button',
      text: t('swap'),
      onclick: () => {
        const first = state.slotA;
        state.slotA = state.slotB;
        state.slotB = first;
        renderCrucible();
      },
    }));
}

function renderCrucible() {
  buildSelectors();

  const first = material(state.slotA);
  const second = material(state.slotB);
  if (!first || !second) return;

  if (first.id === second.id) {
    mount('result', refusedCard(t('refusedSame')));
    return;
  }

  const result = fuse(first, second, index);
  if (!result) {
    mount('result', refusedCard(t('refused')));
    return;
  }

  const badge = result.kind === LEGENDARY ? t('badgeLegendary')
    : result.kind === PRIME ? t('badgePrime') : t('badgeComposite');

  const notes = [t('samePair')];
  if (result.kind === COMPOSITE) notes.push(t('newComposite'));
  if (result.kind === PRIME) notes.push(t('primeNoReforge'));
  if (result.massless) notes.push(t('massless'));

  const rows = [
    [t('durability'), number(result.durability)],
    [t('speed'), `${number(result.speed, 1)}x`],
    [t('damage'), number(result.damage, 1)],
  ];

  mount('result', el('div', { class: 'result-card', style: { '--swatch': result.color } },
    el('header', {},
      el('span', { class: 'icon' }),
      el('div', {},
        el('h4', { text: result.name }),
        el('div', { class: 'id' }, result.id)),
      el('span', { class: 'badge', text: badge })),
    el('p', { class: 'hint', text: format(t('parentsNote'), { a: first.name, b: second.name }) }),
    el('div', { class: 'kv' }, rows.map(([term, value]) => el('div', {},
      el('dt', { text: term }),
      el('dd', { text: value })))),
    el('div', { class: 'tags' }, result.essences.map((id) => {
      const essence = index.essences.get(id);
      if (!essence) return null;
      return el('span', { class: 'tag', style: { '--swatch': essence.color } },
        el('span', { class: 'dot' }),
        el('strong', { text: essence.name }));
    })),
    result.catalyst
      ? el('div', { class: 'channel' },
        el('div', { class: 'who', text: `${t('ultimate')} · ${t('armorState')}` }),
        el('div', { class: 'what' },
          el('strong', { text: result.catalyst.ultimate }), ' · ',
          el('strong', { text: result.catalyst.armorState })))
      : null,
    el('p', { class: 'note', text: notes.join(' ') })));
}

function refusedCard(reason) {
  return el('div', { class: 'result-card refused' },
    el('header', {}, el('span', { class: 'badge', text: t('badgeRefused') })),
    el('p', { class: 'note', text: reason }));
}

// ==========================================
// STORAGE
// ==========================================

function safeRead(key) {
  try {
    return localStorage.getItem(key);
  } catch {
    return null;
  }
}

function safeWrite(key, value) {
  try {
    localStorage.setItem(key, value);
  } catch {
    // A reader with storage disabled still gets the language they clicked.
  }
}
