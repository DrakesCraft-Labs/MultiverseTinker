/**
 * Every word the page says, in both languages.
 *
 * Only the page's own wording lives here. Names, traits, descriptions and essence effects are read
 * from the plugin's registries through `alloys.json` and are printed exactly as the game prints them,
 * which is why the Spanish side quotes them in English and says so.
 */

export const LANGUAGES = ['es', 'en'];

export const STRINGS = {
  es: {
    lang: 'ES',
    switchTo: 'View in English',
    switchHint: 'Cambiar el idioma de la página',
    title: 'Aleaciones de MultiverseTinker',
    subtitle: 'Explora cada material del plugin y previsualiza lo que forjaría el crisol con dos de ellos.',
    catalogSummary: 'materiales',
    generatedNote: 'Generado desde los registros del plugin: {materials} materiales, {recipes} recetas legendarias y {essences} esencias. Si un número cambia en el juego, cambia aquí.',
    gameTextNote: 'Los nombres, rasgos y efectos que imprime el juego se muestran tal cual, en inglés.',

    search: 'Buscar nombre, id, rasgo o epíteto…',
    filterKind: 'Categoría',
    filterOrigin: 'Dimensión',
    filterRarity: 'Rareza',
    filterType: 'Tipo',
    sortBy: 'Ordenar por',
    sortName: 'Nombre',
    sortDurability: 'Durabilidad',
    sortDamage: 'Daño',
    sortSpeed: 'Velocidad',
    anyValue: 'Todos',
    showing: '{shown} de {total} materiales',
    noMatches: 'Ningún material coincide con el filtro.',
    clearFilters: 'Limpiar filtros',

    selectHint: 'Elige un material de la lista para ver qué aporta.',
    overview: 'Ficha',
    idLabel: 'ID',
    copy: 'Copiar',
    copied: 'Copiado',
    originLabel: 'Origen',
    rarityLabel: 'Rareza',
    typeLabel: 'Tipo',
    meltLabel: 'Fundido',
    meltUnit: '{ticks}t · ~{seconds}s',
    sourceLabel: 'Se extrae de',
    fromRecipe: 'Crisol de Aleaciones · receta legendaria',
    fromCatalyst: 'Crisol de Aleaciones · catalizador',
    recipeShort: 'receta legendaria',
    catalystShort: 'catalizador',
    vanillaItem: 'Objeto vanilla',
    descriptionLabel: 'Descripción',
    mixableYes: 'Se mezcla libremente en el crisol',
    mixableNo: 'No se mezcla libremente',
    mixableNoWhy: 'Ya es una aleación o un catalizador: solo entra en el crisol como parte de una receta o de una fusión primordial.',

    statsTitle: 'Qué mejora',
    statsHint: 'Lo que aporta una pieza forjada con este material, comparado con el mejor del catálogo.',
    durability: 'Durabilidad',
    speed: 'Velocidad de minado',
    damage: 'Daño de ataque',
    rank: '#{rank} de {total}',

    traitTitle: 'Rasgo',
    channelWeapon: 'En armas',
    channelTool: 'En herramientas',
    channelArmor: 'En armadura',
    channelHint: 'Cómo se comporta el rasgo en cada tipo de equipo.',

    essencesTitle: 'Esencias',
    essencesHint: 'Las esencias deciden los efectos elementales que el material presta al equipo.',
    essenceEffects: 'Efectos por equipo',

    epithetTitle: 'Epíteto de perk',
    epithetHint: 'La palabra que este material presta al nombre del perk que forja.',

    recipeTitle: 'Receta legendaria',
    recipeHint: 'Funde los dos materiales padre en el Crisol de Aleaciones.',
    parentsLabel: 'Padres',
    primeTitle: 'Fusión primordial',
    primeHint: 'Empareja esta aleación legendaria con otro material para forjar una primordial.',
    ultimate: 'Ultimate',
    armorState: 'Estado de armadura',
    catalystsFor: 'Catalizadores compatibles',
    catalystPrimes: 'Forja {count} primordiales, una por aleación legendaria.',
    catalystTitle: 'Catalizador',
    catalystHint: 'Déjalo en el crisol junto a una aleación legendaria para forjar una primordial.',

    tryInCrucible: 'Probar en el crisol',
    crucibleTitle: 'Crisol de dos materiales',
    crucibleHint: 'Elige dos materiales y mira exactamente qué forjaría el crisol. Nada se forja aquí: es la misma cuenta que hace el plugin, hecha en tu navegador.',
    slotA: 'Material A',
    slotB: 'Material B',
    swap: 'Intercambiar',
    result: 'Resultado',
    badgeLegendary: 'Receta legendaria',
    badgeComposite: 'Compuesta nueva',
    badgePrime: 'Fusión primordial',
    badgeRefused: 'El crisol lo rechaza',
    refused: 'Este par no se puede forjar: se necesitan dos minerales, una receta curada, o una aleación legendaria con otro material.',
    refusedSame: 'Un material no se puede mezclar consigo mismo.',
    newComposite: 'Esta compuesta no existe hasta que alguien la funde: aparecerá en el codex del servidor.',
    massless: 'El catalizador no aporta masa: la primordial se construye desde la aleación que cataliza.',
    primeNoReforge: 'Las primordiales no se pueden volver a fundir.',
    samePair: 'El mismo par que el Crisol de Aleaciones acepta.',
    parentsNote: 'Padres: {a} + {b}',

    builderTitle: 'Calculadora de builds',
    builderHint: 'Elige un arma, una herramienta o una pieza de armadura, sus partes y su tier, y mira el daño y la durabilidad finales. Cada parte se forja con un solo material, y la cuenta es la del propio plugin, hecha en tu navegador.',
    builderMode: 'Equipo',
    builderType: 'Tipo',
    builderTier: 'Tier de evolución',
    builderResultTitle: 'Build resultante',
    builderWeaponNote: 'Un arma de tres piezas saca su daño de la cabeza y de un tercio del pomo; una de dos, de la mitad de su segunda pieza. El tier {tier} añade +{durability} de durabilidad y +{damage} de daño.',
    builderToolNote: 'El cabezal aporta el daño y la velocidad de minado, el mango un tercio de su impacto y el pomo la mitad de su durabilidad. El tier {tier} añade +{durability} de durabilidad, +{damage} de daño y multiplica la velocidad por {speed}.',
    builderArmorNote: 'La placa rueda la defensa, el forro la dureza y el ribete la resistencia al empuje. El tier {tier} suma su escalón a la defensa y la mitad a la dureza.',
    builderSourceNote: 'Son las fórmulas del propio plugin, con los números que el item lleva al forjarse. Aquí no se forja nada.',
    builderRefused: 'Esa combinación no se puede calcular: falta una parte o el equipo elegido ya no está en el catálogo.',
    defense: 'Defensa',
    toughness: 'Dureza',
    knockback: 'Resistencia al empuje',

    footerTitle: 'Más información',
    footerWiki: 'Wiki del proyecto',
    footerCombos: 'Las mejores combinaciones de espada y arco',
    footerReadme: 'Cómo se genera esta página',
    footerNote: 'Página estática generada desde el código del plugin. No necesita servidor.'
  },
  en: {
    lang: 'EN',
    switchTo: 'Ver en español',
    switchHint: 'Switch the page language',
    title: 'MultiverseTinker Alloys',
    subtitle: 'Browse every material the plugin ships and preview what the crucible would forge from two of them.',
    catalogSummary: 'materials',
    generatedNote: 'Generated from the plugin registries: {materials} materials, {recipes} legendary recipes and {essences} essences. When a number changes in game, it changes here.',
    gameTextNote: 'Names, traits and effects are shown exactly as the game prints them.',

    search: 'Search name, id, trait or epithet…',
    filterKind: 'Category',
    filterOrigin: 'Dimension',
    filterRarity: 'Rarity',
    filterType: 'Type',
    sortBy: 'Sort by',
    sortName: 'Name',
    sortDurability: 'Durability',
    sortDamage: 'Damage',
    sortSpeed: 'Speed',
    anyValue: 'All',
    showing: '{shown} of {total} materials',
    noMatches: 'No material matches the filter.',
    clearFilters: 'Clear filters',

    selectHint: 'Pick a material from the list to see what it contributes.',
    overview: 'Entry',
    idLabel: 'ID',
    copy: 'Copy',
    copied: 'Copied',
    originLabel: 'Origin',
    rarityLabel: 'Rarity',
    typeLabel: 'Type',
    meltLabel: 'Melts in',
    meltUnit: '{ticks}t · ~{seconds}s',
    sourceLabel: 'Found in',
    fromRecipe: 'Alloy Crucible · legendary recipe',
    fromCatalyst: 'Alloy Crucible · catalyst',
    recipeShort: 'legendary recipe',
    catalystShort: 'catalyst',
    vanillaItem: 'Vanilla item',
    descriptionLabel: 'Description',
    mixableYes: 'Blends freely in the crucible',
    mixableNo: 'Never blends freely',
    mixableNoWhy: 'It is already an alloy or a catalyst, so it only enters the crucible inside a recipe or a prime fusion.',

    statsTitle: 'What it improves',
    statsHint: 'What a part forged from this material contributes, compared with the best in the catalog.',
    durability: 'Durability',
    speed: 'Mining speed',
    damage: 'Attack damage',
    rank: '#{rank} of {total}',

    traitTitle: 'Trait',
    channelWeapon: 'On weapons',
    channelTool: 'On tools',
    channelArmor: 'On armor',
    channelHint: 'How the trait behaves on each kind of equipment.',

    essencesTitle: 'Essences',
    essencesHint: 'Essences decide the elemental effects a material lends to the equipment it is forged into.',
    essenceEffects: 'Effects per equipment',

    epithetTitle: 'Perk epithet',
    epithetHint: 'The word this material lends to the name of the perk it forges.',

    recipeTitle: 'Legendary recipe',
    recipeHint: 'Blend the two parent materials in the Alloy Crucible.',
    parentsLabel: 'Parents',
    primeTitle: 'Prime fusion',
    primeHint: 'Pair this legendary alloy with another material to forge a prime.',
    ultimate: 'Ultimate',
    armorState: 'Armor state',
    catalystsFor: 'Compatible catalysts',
    catalystPrimes: 'Forges {count} primes, one per legendary alloy.',
    catalystTitle: 'Catalyst',
    catalystHint: 'Leave it in the crucible next to a legendary alloy to forge a prime.',

    tryInCrucible: 'Try it in the crucible',
    crucibleTitle: 'Two-material crucible',
    crucibleHint: 'Pick two materials and see exactly what the crucible would forge. Nothing is forged here: it is the plugin\'s own arithmetic, done in your browser.',
    slotA: 'Material A',
    slotB: 'Material B',
    swap: 'Swap',
    result: 'Result',
    badgeLegendary: 'Legendary recipe',
    badgeComposite: 'New composite',
    badgePrime: 'Prime fusion',
    badgeRefused: 'The crucible refuses it',
    refused: 'This pair cannot be forged: it takes two minerals, a curated recipe, or a legendary alloy with another material.',
    refusedSame: 'A material cannot be blended with itself.',
    newComposite: 'This composite does not exist until somebody smelts it: it then appears in the server codex.',
    massless: 'The catalyst carries no mass: the prime is built from the alloy it catalyses.',
    primeNoReforge: 'Primes cannot be reforged.',
    samePair: 'The same pair the Alloy Crucible accepts.',
    parentsNote: 'Parents: {a} + {b}',

    builderTitle: 'Build calculator',
    builderHint: 'Pick a weapon, a tool or an armor piece, its parts and its tier, and read the final damage and durability. Each part is forged from one material, and the arithmetic is the plugin\'s own, done in your browser.',
    builderMode: 'Equipment',
    builderType: 'Type',
    builderTier: 'Evolution tier',
    builderResultTitle: 'Resulting build',
    builderWeaponNote: 'A three-part weapon takes its damage from the head and a third of the pommel; a two-part one takes half of its second part. The {tier} adds +{durability} durability and +{damage} damage.',
    builderToolNote: 'The head yields the damage and the mining speed, the handle a third of its impact and the pommel half of its durability. The {tier} adds +{durability} durability, +{damage} damage and multiplies the speed by {speed}.',
    builderArmorNote: 'The plate rolls Defense, the lining Toughness and the trim knockback resistance. The {tier} adds its step to Defense and half of it to Toughness.',
    builderSourceNote: 'These are the plugin\'s own formulas, with the numbers the item carries when it is forged. Nothing is forged here.',
    builderRefused: 'That combination cannot be priced: a part is missing, or the chosen equipment is no longer in the catalog.',
    defense: 'Defense',
    toughness: 'Toughness',
    knockback: 'Knockback resistance',

    footerTitle: 'More information',
    footerWiki: 'Project wiki',
    footerCombos: 'The best sword and bow combinations',
    footerReadme: 'How this page is generated',
    footerNote: 'A static page generated from the plugin code. It needs no server.'
  }
};

/** The wording of the registry enums, which the plugin only expresses in English. */
export const LABELS = {
  es: {
    kind: { mineral: 'Mineral', legendary: 'Aleación legendaria', catalyst: 'Catalizador' },
    equipmentKind: { weapon: 'Arma', tool: 'Herramienta', armor: 'Armadura' },
    origin: { OVERWORLD: 'Overworld', NETHER: 'El Nether', THE_END: 'El End', VANILLA: 'Menas vanilla' },
    rarity: { COMMON: 'Común', UNCOMMON: 'Poco común', RARE: 'Rara', EPIC: 'Épica', LEGENDARY: 'Legendaria' },
    type: {
      METAL: 'Metal fundible',
      GEM: 'Gema preciosa',
      MINERAL: 'Mineral terrestre',
      CRYSTAL: 'Cristal resonante',
      ELEMENTAL: 'Elemento primordial',
      ALLOY: 'Aleación'
    }
  },
  en: {
    kind: { mineral: 'Mineral', legendary: 'Legendary alloy', catalyst: 'Catalyst' },
    equipmentKind: { weapon: 'Weapon', tool: 'Tool', armor: 'Armor' },
    origin: { OVERWORLD: 'Overworld', NETHER: 'The Nether', THE_END: 'The End', VANILLA: 'Vanilla ores' },
    rarity: { COMMON: 'Common', UNCOMMON: 'Uncommon', RARE: 'Rare', EPIC: 'Epic', LEGENDARY: 'Legendary' },
    type: {
      METAL: 'Smeltable metal',
      GEM: 'Precious gem',
      MINERAL: 'Earth mineral',
      CRYSTAL: 'Resonant crystal',
      ELEMENTAL: 'Primordial element',
      ALLOY: 'Alloy'
    }
  }
};

/** Fills the `{placeholders}` of a string. */
export function format(template, values = {}) {
  return template.replace(/\{(\w+)\}/g, (match, key) => (key in values ? String(values[key]) : match));
}

/** The language to start in: the reader's own, then whatever they chose last time. */
export function preferredLanguage(stored) {
  if (LANGUAGES.includes(stored)) return stored;
  const browser = typeof navigator === 'undefined' ? 'en' : navigator.language || 'en';
  return browser.toLowerCase().startsWith('es') ? 'es' : 'en';
}
