package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Browsable alloy codex: every curated recipe, every vanilla catalyst, every composite and prime the
 * server has discovered, plus a combination explorer that lists - for any single material - every
 * partner the crucible would accept.
 *
 * <p>Browsing is completely side effect free: previewed combinations are named with the same helpers
 * the crucible uses, but nothing is registered until a player actually smelts it.</p>
 */
public class AlloyCodexGUI implements InventoryHolder {

    /** Sections reachable from the top navigation row. */
    public enum Section {
        LEGENDARY(Material.NETHERITE_INGOT, "<gradient:#ffd700:#ff8c00><b>Legendary Recipes</b></gradient>",
                "The 16 curated alloys with hand-written traits."),
        CATALYSTS(Material.NETHER_STAR, "<gradient:#9b59b6:#e056fd><b>Prime Catalysts</b></gradient>",
                "The 12 vanilla items that catalyse prime alloys."),
        COMPOSITES(Material.CRAFTING_TABLE, "<gradient:#3498db:#2980b9><b>Forged Composites</b></gradient>",
                "Every composite alloy this server has discovered."),
        PRIMES(Material.BEACON, "<gradient:#6c5ce7:#a29bfe><b>Prime Alloys</b></gradient>",
                "Every legendary fusion forged so far, with its spectacle."),
        EXPLORER(Material.COMPASS, "<gradient:#2ecc71:#27ae60><b>Combination Explorer</b></gradient>",
                "Pick a material and see every partner the crucible accepts."),
        SUMMARY(Material.BOOK, "<gradient:#f1c40f:#e67e22><b>Alloy Space Summary</b></gradient>",
                "How many alloys exist, by category.");

        private final Material icon;
        private final String title;
        private final String description;

        Section(Material icon, String title, String description) {
            this.icon = icon;
            this.title = title;
            this.description = description;
        }
    }

    private static final int SIZE = 54;
    private static final int CONTENT_START = 9;
    private static final int CONTENT_SLOTS = 36;
    private static final int SLOT_BACK = 0;
    private static final int SLOT_SECTION_FIRST = 1;
    private static final int SLOT_PRINT = 7;
    private static final int SLOT_CLOSE = 8;
    private static final int SLOT_PREV = 45;
    private static final int SLOT_INFO = 49;
    private static final int SLOT_NEXT = 53;

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final AlloyRegistry alloyRegistry;
    private final TinkerItemRegistry itemRegistry;
    private final Inventory inventory;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private Section section = Section.LEGENDARY;
    private int page = 0;
    /** Material selected in the explorer, or {@code null} while choosing one. */
    @Nullable
    private TinkerMaterial explorerTarget;
    /** True when the codex was opened from the forge GUI, so closing returns there. */
    private boolean cameFromForge;

    public AlloyCodexGUI(@Nonnull MultiverseTinker plugin,
                         @Nonnull TinkerItemRegistry itemRegistry,
                         @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
        this.alloyRegistry = (plugin.getAlloyRegistry() != null) ? plugin.getAlloyRegistry() : new AlloyRegistry();
        this.inventory = Bukkit.createInventory(this, SIZE,
                miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>Alloy Codex</b></gradient>"));
        render();
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /** Marks the codex as opened from the forge so closing it returns to the forge GUI. */
    public void setCameFromForge(boolean cameFromForge) {
        this.cameFromForge = cameFromForge;
    }

    public boolean isCameFromForge() {
        return cameFromForge;
    }

    @Nullable
    public TinkerMaterial getExplorerTarget() {
        return explorerTarget;
    }

    // ==========================================
    // RENDERING
    // ==========================================
    public void render() {
        inventory.clear();
        renderNavBar();

        List<ItemStack> content = contentFor(section);
        int pages = Math.max(1, (int) Math.ceil(content.size() / (double) CONTENT_SLOTS));
        page = Math.max(0, Math.min(page, pages - 1));

        int from = page * CONTENT_SLOTS;
        for (int i = 0; i < CONTENT_SLOTS && from + i < content.size(); i++) {
            inventory.setItem(CONTENT_START + i, content.get(from + i));
        }

        if (content.isEmpty()) {
            inventory.setItem(22, emptyState(section));
        }

        inventory.setItem(SLOT_PREV, page > 0
                ? button(Material.ARROW, "<gold>◀ Previous Page</gold>", List.of("Page " + (page + 1) + " of " + pages))
                : decor(Material.GRAY_STAINED_GLASS_PANE, " "));
        inventory.setItem(SLOT_NEXT, page + 1 < pages
                ? button(Material.ARROW, "<gold>Next Page ▶</gold>", List.of("Page " + (page + 1) + " of " + pages))
                : decor(Material.GRAY_STAINED_GLASS_PANE, " "));
        inventory.setItem(SLOT_INFO, button(Material.PAPER,
                "<yellow><b>" + section.title.replaceAll("<[^>]*>", "") + "</b></yellow>",
                List.of("<gray>" + section.description + "</gray>",
                        "",
                        "<gray>Entries: <yellow>" + content.size() + "</yellow> · Page <yellow>" + (page + 1)
                                + "</yellow>/<yellow>" + pages + "</yellow></gray>")));
    }

    private void renderNavBar() {
        inventory.setItem(SLOT_BACK, button(cameFromForge ? Material.ANVIL : Material.BARRIER,
                cameFromForge ? "<gold>◀ Back to the Forge</gold>" : "<red>◀ Close</red>",
                List.of(cameFromForge ? "Return to the Multiverse Forge GUI." : "Close the codex.")));

        Section[] sections = Section.values();
        for (int i = 0; i < sections.length && i < SLOT_CLOSE - SLOT_SECTION_FIRST; i++) {
            Section candidate = sections[i];
            boolean active = candidate == section;
            inventory.setItem(SLOT_SECTION_FIRST + i, button(candidate.icon,
                    (active ? "<green>▶ </green>" : "<gray>  </gray>") + candidate.title,
                    List.of("<gray>" + candidate.description + "</gray>",
                            "",
                            active ? "<green>✔ Currently open</green>" : "<yellow>Click to open</yellow>")));
        }

        inventory.setItem(SLOT_PRINT, button(Material.WRITABLE_BOOK,
                "<yellow><b>Print Summary to Chat</b></yellow>",
                List.of("<gray>Writes the alloy space summary to chat,", "<gray>useful for screenshots and logs.</gray>")));
        inventory.setItem(SLOT_CLOSE, button(Material.BARRIER, "<red><b>Close</b></red>", List.of("<gray>Exit the codex.</gray>")));
    }

    /**
     * Explains an empty section instead of leaving the player guessing.
     *
     * <p>The codex only lists what <b>this server</b> has actually forged, so {@code Prime Alloys}
     * and {@code Forged Composites} start empty on every fresh world: nothing is broken, the list
     * simply fills itself as the crucible is used. This is exactly why the Prime Alloys tab looks
     * empty until the first legendary fusion happens.</p>
     */
    @Nonnull
    private ItemStack emptyState(@Nonnull Section section) {
        return switch (section) {
            case PRIMES -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No prime alloys forged yet</b></gray>",
                    List.of("<gray>Nothing is missing here - this tab only lists the",
                            "<gray>primes <white>this server</white> has already fused.</gray>",
                            "",
                            "<gold>How to forge a prime alloy:</gold>",
                            "<gray>1. Forge a <yellow>Legendary alloy</yellow> in the Alloy Crucible.</gray>",
                            "<gray>2. Fuse it again with another alloy, any mineral,</gray>",
                            "<gray>   or one of the <yellow>12 vanilla catalysts</yellow>.</gray>",
                            "",
                            "<gray>The prime comes out Legendary, with its own",
                            "<gray>cinematic ultimate and armor state, and is listed here.",
                            "",
                            "<yellow>► Tip: the Combination Explorer lists every</yellow>",
                            "<yellow>partner each legendary alloy accepts.</yellow>"));
            case COMPOSITES -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No composite alloys forged yet</b></gray>",
                    List.of("<gray>Blend any <yellow>two minerals</yellow> in the Alloy Crucible and</gray>",
                            "<gray>the composite they create is recorded here.</gray>",
                            "",
                            "<gray>This tab stays empty until this server forges one.</gray>",
                            "",
                            "<yellow>► Tip: browse the Combination Explorer to see how</yellow>",
                            "<yellow>many pairs each mineral accepts.</yellow>"));
            case EXPLORER -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No partner for this material</b></gray>",
                    List.of("<gray>This material cannot be blended right now.</gray>",
                            "",
                            "<gray>Prime alloys cannot be fused again, and a</gray>",
                            "<gray>material needs a distinct partner to mix with.</gray>",
                            "",
                            "<yellow>► Tip: press any entry to pick another material.</yellow>"));
            default -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>Nothing here yet</b></gray>",
                    List.of("Forge something in the Alloy Crucible and it appears here."));
        };
    }

    private List<ItemStack> contentFor(Section section) {
        return switch (section) {
            case LEGENDARY -> legendaryEntries();
            case CATALYSTS -> catalystEntries();
            case COMPOSITES -> forgedEntries(false);
            case PRIMES -> forgedEntries(true);
            case EXPLORER -> explorerTarget == null ? explorerChoices() : explorerPartners();
            case SUMMARY -> summaryEntries();
        };
    }

    private List<ItemStack> legendaryEntries() {
        List<ItemStack> entries = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial parentA = materialRegistry.get(alloy.mat1Id());
            TinkerMaterial parentB = materialRegistry.get(alloy.mat2Id());
            TinkerMaterial result = materialRegistry.get(alloy.id());

            List<String> lore = new ArrayList<>();
            lore.add("<gray>Parents: <yellow>" + nameOf(parentA, alloy.mat1Id()) + "</yellow> + <yellow>"
                    + nameOf(parentB, alloy.mat2Id()) + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + alloy.id() + "</dark_gray>");
            lore.add("");
            if (result != null) {
                lore.add("<gray>Durability: <green>+" + result.getDurabilityBonus() + "</green> · Speed: <aqua>"
                        + String.format(Locale.US, "%.1fx", result.getMiningSpeed()) + "</aqua> · Damage: <red>+"
                        + String.format(Locale.US, "%.1f", result.getAttackDamageBonus()) + "</red></gray>");
                lore.add("<gray>Essences: <light_purple>" + essenceLine(result) + "</light_purple></gray>");
            }
            lore.add("");
            lore.add("<gold>✦ " + alloy.traitName() + "</gold>");
            lore.add("<dark_aqua>" + alloy.traitDescription() + "</dark_aqua>");
            lore.add("");
            lore.add("<gray>Can be fused again into a <yellow>prime alloy</yellow> with another alloy,");
            lore.add("<gray>a mineral or one of the 12 vanilla catalysts.</gray>");
            entries.add(entry(result, Material.IRON_INGOT,
                    "<gradient:" + alloy.colorHex() + ":#ffffff><b>" + alloy.name() + "</b></gradient>", lore));
        }
        return entries;
    }

    private List<ItemStack> catalystEntries() {
        List<ItemStack> entries = new ArrayList<>();
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            List<String> lore = new ArrayList<>();
            lore.add("<gray>Item: <yellow>" + catalyst.getItem().name() + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + catalyst.getMaterialId() + "</dark_gray>");
            lore.add("");
            lore.add("<gold>⚡ Ultimate: </gold><aqua>" + catalyst.getUltimate().getDisplayName() + "</aqua>");
            lore.add("<gray>  " + catalyst.getUltimate().getDescription() + "</gray>");
            lore.add("<gold>🛡 Armor State: </gold><light_purple>" + catalyst.getArmorState().getDisplayName() + "</light_purple>");
            lore.add("<gray>  " + catalyst.getArmorState().getDescription() + "</gray>");
            lore.add("");
            lore.add("<gray>Fuse with any of the <yellow>16 legendary alloys</yellow> → <bold>"
                    + catalyst.compatiblePrimes() + "</bold> prime alloys.</gray>");
            entries.add(entry(material, catalyst.getItem(), "<gradient:" + catalyst.getColorHex()
                    + ":#ffffff><b>" + catalyst.getDisplayName() + " (Catalyst)</b></gradient>", lore));
        }
        return entries;
    }

    /** Composites or primes, both limited to what the server has actually forged. */
    private List<ItemStack> forgedEntries(boolean primes) {
        List<ItemStack> entries = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            // The 16 curated recipes have their own section; only what players forged belongs here.
            if (AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            boolean isPrime = AlloyRegistry.isPrimeParents(alloy.mat1Id(), alloy.mat2Id());
            if (isPrime != primes) continue;

            TinkerMaterial result = materialRegistry.get(alloy.id());
            List<String> lore = new ArrayList<>();
            lore.add("<gray>Parents: <yellow>" + nameOf(materialRegistry.get(alloy.mat1Id()), alloy.mat1Id())
                    + "</yellow> + <yellow>" + nameOf(materialRegistry.get(alloy.mat2Id()), alloy.mat2Id()) + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + alloy.id() + "</dark_gray>");
            lore.add("");
            if (result != null) {
                lore.add("<gray>Durability: <green>+" + result.getDurabilityBonus() + "</green> · Speed: <aqua>"
                        + String.format(Locale.US, "%.1fx", result.getMiningSpeed()) + "</aqua> · Damage: <red>+"
                        + String.format(Locale.US, "%.1f", result.getAttackDamageBonus()) + "</red></gray>");
                lore.add("<gray>Essences: <light_purple>" + essenceLine(result) + "</light_purple></gray>");
                if (primes) {
                    lore.add("");
                    lore.add("<gold>⚡ Prime ultimate: </gold><aqua>" + PrimeUltimate.of(result).getDisplayName() + "</aqua>");
                    lore.add("<gold>🛡 Prime state: </gold><light_purple>"
                            + PrimeArmorState.of(result).getDisplayName() + "</light_purple>");
                }
            }
            lore.add("");
            lore.add("<gold>✦ " + alloy.traitName() + "</gold>");
            lore.add("<dark_aqua>" + alloy.traitDescription() + "</dark_aqua>");
            entries.add(entry(result, primes ? Material.BEACON : Material.IRON_INGOT,
                    "<gradient:" + alloy.colorHex() + ":#ffffff><b>" + alloy.name() + "</b></gradient>", lore));
        }
        return entries;
    }

    /** Explorer step 1: everything you can pick a partner for. */
    private List<ItemStack> explorerChoices() {
        List<ItemStack> entries = new ArrayList<>();

        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial material = materialRegistry.get(alloy.id());
            if (material == null) continue;
            entries.add(explorerChoice(material, alloy.colorHex(), "<gradient:" + alloy.colorHex()
                    + ":#ffffff><b>" + alloy.name() + "</b></gradient>", "Legendary alloy"));
        }

        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            if (material == null) continue;
            entries.add(explorerChoice(material, catalyst.getColorHex(), "<gradient:" + catalyst.getColorHex()
                    + ":#ffffff><b>" + catalyst.getDisplayName() + "</b></gradient>", "Vanilla catalyst"));
        }

        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (!AlloyRegistry.isMixable(material)) continue;
            entries.add(explorerChoice(material, material.getColorHex(), "<gradient:" + material.getColorHex()
                    + ":#ffffff><b>" + material.getName() + "</b></gradient>",
                    material.getOrigin().name() + " " + material.getType().name().toLowerCase(Locale.ROOT)));
        }
        return entries;
    }

    private ItemStack explorerChoice(@Nonnull TinkerMaterial material, @Nonnull String colorHex,
                                     @Nonnull String title, @Nonnull String kind) {
        List<String> lore = new ArrayList<>();
        lore.add("<gray>" + kind + "</gray>");
        lore.add("<dark_gray>ID: " + material.getId() + "</dark_gray>");
        lore.add("");
        lore.add("<gray>Essences: <light_purple>" + essenceLine(material) + "</light_purple></gray>");
        lore.add("");
        lore.add("<yellow>▶ Click to list every compatible partner</yellow>");
        return entry(material, Material.IRON_INGOT, title, lore);
    }

    /** Explorer step 2: every partner the crucible accepts for the selected material. */
    private List<ItemStack> explorerPartners() {
        List<ItemStack> entries = new ArrayList<>();
        TinkerMaterial target = explorerTarget;
        if (target == null) return entries;

        for (TinkerMaterial candidate : materialRegistry.getAll()) {
            if (candidate.getId().equalsIgnoreCase(target.getId())) continue;
            if (AlloyRegistry.isPrime(candidate)) continue; // primes cannot be reforged
            if (!alloyRegistry.isCraftablePair(target, candidate)) continue;
            entries.add(partnerEntry(target, candidate));
        }
        return entries;
    }

    private ItemStack partnerEntry(@Nonnull TinkerMaterial a, @Nonnull TinkerMaterial b) {
        boolean prime = AlloyRegistry.isPrimePair(a, b);
        boolean curated = alloyRegistry.findAlloy(a.getId(), b.getId()) != null;

        String previewId = AlloyRegistry.dynamicId(a, b);
        String previewName = AlloyRegistry.dynamicName(a, b);

        String tag = curated ? "<gold>[CURATED RECIPE]</gold>" : prime ? "<light_purple>[PRIME FUSION]</light_purple>"
                : "<aqua>[NEW COMPOSITE]</aqua>";

        List<String> lore = new ArrayList<>();
        lore.add(tag);
        lore.add("<gray>With: <yellow>" + b.getName() + "</yellow> <dark_gray>(" + b.getId() + ")</dark_gray>");
        lore.add("<gray>Result: <white>" + previewName + "</white></gray>");
        lore.add("<dark_gray>ID: " + previewId + "</dark_gray>");
        lore.add("");
        lore.add("<gray>Essences of the pair: <light_purple>" + essenceLine(a, b) + "</light_purple></gray>");
        if (prime) {
            VanillaCatalyst sigil = VanillaCatalyst.byMaterialId(a.getId());
            if (sigil == null) sigil = VanillaCatalyst.byMaterialId(b.getId());
            if (sigil != null) {
                lore.add("<gold>⚡ Ultimate: </gold><aqua>" + sigil.getUltimate().getDisplayName() + "</aqua>");
                lore.add("<gold>🛡 State: </gold><light_purple>" + sigil.getArmorState().getDisplayName() + "</light_purple>");
            }
            lore.add("<gray>Anything can be fused into a prime, but primes cannot be reforged.</gray>");
        }
        lore.add("");
        lore.add("<dark_gray>Preview only — smelt it in the crucible to unlock it.</dark_gray>");
        return entry(b, prime ? Material.BEACON : Material.IRON_INGOT,
                "<gradient:" + b.getColorHex() + ":#ffffff><b>" + b.getName() + "</b></gradient>", lore);
    }

    private List<ItemStack> summaryEntries() {
        List<ItemStack> entries = new ArrayList<>();

        long mixable = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count();
        long mineralPairs = mixable * (mixable - 1) / 2;
        int curated = AlloyRegistry.LEGENDARY_IDS.size();
        int forged = alloyRegistry.getDynamicAlloyCount();
        int primes = alloyRegistry.getPrimeAlloyCount();
        long catalysts = VanillaCatalyst.values().length;
        long legendaryFusions = permittedPairs(mixable, catalysts);

        entries.add(info(Material.DIAMOND_PICKAXE, "<aqua><b>Mineral alloys</b></aqua>", List.of(
                "<gray>Blendable minerals: <yellow>" + mixable + "</yellow></gray>",
                "<gray>Distinct mineral pairs: <yellow>" + mineralPairs + "</yellow></gray>",
                "<dark_gray>Any two brush/vanilla minerals fuse into a unique composite.</dark_gray>")));

        entries.add(info(Material.NETHERITE_INGOT, "<gold><b>Curated legendary recipes</b></gold>", List.of(
                "<gray>Legendary alloys: <yellow>" + curated + "</yellow></gray>",
                "<gray>Free-form mineral pairs: <yellow>14</yellow></gray>",
                "<gray>Extra netherite recipes: <yellow>2</yellow> (Cinder Steel, Cosmic Netherite)</gray>")));

        entries.add(info(Material.BLAZE_POWDER, "<light_purple><b>Prime fusions</b></light_purple>", List.of(
                "<gray>Vanilla catalysts: <yellow>" + catalysts + "</yellow></gray>",
                "<gray>Legendary × catalyst: <yellow>" + (curated * catalysts) + "</yellow></gray>",
                "<gray>Legendary × legendary: <yellow>" + (curated * (curated - 1) / 2) + "</yellow></gray>",
                "<gray>Legendary × mineral: <yellow>" + (curated * mixable) + "</yellow></gray>",
                "<gray>Legendary × other alloy: <yellow>" + (curated * (mineralPairs - 14 + 1)) + "</yellow></gray>")));

        entries.add(info(Material.WRITTEN_BOOK, "<yellow><b>Discovered on this server</b></yellow>", List.of(
                "<gray>Composite alloys: <yellow>" + forged + "</yellow></gray>",
                "<gray>Prime alloys: <yellow>" + primes + "</yellow></gray>",
                "<gray>Total registered alloys: <yellow>" + alloyRegistry.getAllAlloys().size() + "</yellow></gray>")));

        entries.add(info(Material.END_PORTAL_FRAME, "<gradient:#6c5ce7:#a29bfe><b>Grand total</b></gradient>", List.of(
                "<gray>Base combinations: <yellow>" + (mineralPairs + 2) + "</yellow></gray>",
                "<gray>With every composite known: <yellow>" + (mineralPairs + 2 + legendaryFusions) + "</yellow></gray>",
                "<dark_gray>Every prime is unique: id, colour, stats, essence blend,</dark_gray>",
                "<dark_gray>ultimate and armor state are all derived deterministically.</dark_gray>")));

        entries.add(info(Material.COMPASS, "<green><b>How to read this codex</b></green>", List.of(
                "<gray>1. <yellow>Legendary Recipes</yellow>: the 16 curated alloys.</gray>",
                "<gray>2. <yellow>Prime Catalysts</yellow>: the 12 vanilla items.</gray>",
                "<gray>3. <yellow>Forged Composites / Prime Alloys</yellow>: what this server made.</gray>",
                "<gray>4. <yellow>Combination Explorer</yellow>: pick a material, see all partners.</gray>")));

        return entries;
    }

    /** Legendary × (other alloys + minerals + catalysts), the reachable prime ceiling. */
    private long permittedPairs(long mixable, long catalysts) {
        long otherAlloys = alloyRegistry.getAllAlloys().size() - AlloyRegistry.LEGENDARY_IDS.size();
        return AlloyRegistry.LEGENDARY_IDS.size() * (otherAlloys + mixable + catalysts);
    }

    // ==========================================
    // CLICKS
    // ==========================================
    public void handleClick(@Nonnull Player player, int rawSlot) {
        switch (rawSlot) {
            case SLOT_CLOSE -> {
                player.closeInventory();
                return;
            }
            case SLOT_BACK -> {
                if (cameFromForge) {
                    player.closeInventory();
                    ForgeGUI forge = new ForgeGUI(plugin, itemRegistry, materialRegistry);
                    // Delay one tick so the close event finishes before the new inventory opens.
                    Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(forge.getInventory()));
                } else {
                    player.closeInventory();
                }
                return;
            }
            case SLOT_PRINT -> {
                printSummary(player);
                return;
            }
            case SLOT_PREV -> {
                if (page > 0) page--;
                render();
                return;
            }
            case SLOT_NEXT -> {
                page++;
                render();
                return;
            }
            default -> {
                // handled below
            }
        }

        Section[] sections = Section.values();
        int sectionIndex = rawSlot - SLOT_SECTION_FIRST;
        if (sectionIndex >= 0 && sectionIndex < sections.length) {
            section = sections[sectionIndex];
            page = 0;
            explorerTarget = null;
            render();
            return;
        }

        if (rawSlot < CONTENT_START || rawSlot >= CONTENT_START + CONTENT_SLOTS) return;

        if (section == Section.EXPLORER) {
            if (explorerTarget == null) {
                TinkerMaterial clicked = selectedChoice(rawSlot);
                if (clicked == null) return;
                explorerTarget = clicked;
                page = 0;
                render();
            } else {
                // Already listing partners: go back to the material picker.
                explorerTarget = null;
                page = 0;
                render();
            }
        }
    }

    /** Maps a clicked explorer slot back to the material it belongs to. */
    @Nullable
    private TinkerMaterial selectedChoice(int rawSlot) {
        List<ItemStack> choices = explorerChoices();
        int index = page * CONTENT_SLOTS + (rawSlot - CONTENT_START);
        if (index < 0 || index >= choices.size()) return null;

        // The choice list is built in a fixed order (legendaries, catalysts, minerals), so the index
        // identifies the material through the same traversal.
        List<TinkerMaterial> ordered = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial material = materialRegistry.get(alloy.id());
            if (material != null) ordered.add(material);
        }
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            if (material != null) ordered.add(material);
        }
        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (AlloyRegistry.isMixable(material)) ordered.add(material);
        }
        return index < ordered.size() ? ordered.get(index) : null;
    }

    private void printSummary(@Nonnull Player player) {
        long mixable = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count();
        long mineralPairs = mixable * (mixable - 1) / 2;
        long ceiling = mineralPairs + 2 + permittedPairs(mixable, VanillaCatalyst.values().length);

        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>══════ MULTIVERSE ALLOY CODEX ══════</b></gradient>"));
        player.sendMessage(miniMessage.deserialize("<gray>Blendable minerals: <yellow>" + mixable
                + "</yellow> · mineral pairs: <yellow>" + mineralPairs + "</yellow></gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Legendary recipes: <yellow>" + AlloyRegistry.LEGENDARY_IDS.size()
                + "</yellow> · vanilla catalysts: <yellow>" + VanillaCatalyst.values().length + "</yellow></gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Forged on this server: <yellow>"
                + alloyRegistry.getDynamicAlloyCount() + "</yellow> composites, <yellow>"
                + alloyRegistry.getPrimeAlloyCount() + "</yellow> primes</gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Maximum distinct alloys once every composite is known: <yellow>"
                + ceiling + "</yellow></gray>"));
    }

    // ==========================================
    // ITEM HELPERS
    // ==========================================
    @Nonnull
    private ItemStack entry(@Nullable TinkerMaterial material, @Nonnull Material fallback,
                            @Nonnull String title, @Nonnull List<String> lore) {
        ItemStack item;
        if (material != null) {
            ItemStack ingot = TinkerItemBuilder.createIngot(material);
            item = (ingot.getType() == Material.AIR) ? new ItemStack(fallback) : ingot;
        } else {
            item = new ItemStack(fallback);
        }
        applyMeta(item, title, lore);
        return item;
    }

    @Nonnull
    private ItemStack button(@Nonnull Material icon, @Nonnull String title, @Nonnull List<String> lore) {
        ItemStack item = new ItemStack(icon);
        applyMeta(item, title, lore);
        return item;
    }

    /** Explanatory entry used by the summary section; same rendering as a button. */
    @Nonnull
    private ItemStack info(@Nonnull Material icon, @Nonnull String title, @Nonnull List<String> lore) {
        return button(icon, title, lore);
    }

    @Nonnull
    private ItemStack decor(@Nonnull Material icon, @Nonnull String title) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void applyMeta(@Nonnull ItemStack item, @Nonnull String title, @Nonnull List<String> loreLines) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        for (String line : loreLines) {
            // Codex entries carry long trait descriptions: wrap them so nothing is clipped.
            lore.addAll(LoreWrap.wrap(miniMessage.deserialize(line).decoration(TextDecoration.ITALIC, false)));
        }
        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    @Nonnull
    private String nameOf(@Nullable TinkerMaterial material, @Nonnull String fallbackId) {
        return material != null ? material.getName() : fallbackId;
    }

    @Nonnull
    private String essenceLine(@Nonnull TinkerMaterial material, @Nullable TinkerMaterial... others) {
        StringBuilder builder = new StringBuilder();
        for (TraitAffinity affinity : TraitAffinity.of(material)) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(affinity.getDisplayName());
        }
        for (TinkerMaterial other : others) {
            if (other == null) continue;
            for (TraitAffinity affinity : TraitAffinity.of(other)) {
                if (builder.indexOf(affinity.getDisplayName()) >= 0) continue;
                if (builder.length() > 0) builder.append(", ");
                builder.append(affinity.getDisplayName());
            }
        }
        return builder.length() == 0 ? "Primal" : builder.toString();
    }

    /** Number of entries the given section would show right now (used by tests and diagnostics). */
    public int entryCount(@Nonnull Section section) {
        return contentFor(section).size();
    }

    @Nonnull
    public Section getSection() {
        return section;
    }
}
