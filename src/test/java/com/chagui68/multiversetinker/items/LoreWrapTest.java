package com.chagui68.multiversetinker.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the lore word-wrapper: long rows must be split into readable rows without losing a single
 * character, colour or decoration, and short rows must be returned untouched.
 */
class LoreWrapTest {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    @AfterEach
    void restoreDefaults() {
        LoreWrap.reset();
    }

    @Test
    @DisplayName("Wrapping can be switched off from the configuration")
    void wrappingCanBeDisabled() {
        Component row = sampleWeaponPerkRow();
        LoreWrap.configure(false, LoreWrap.DEFAULT_MAX_PIXELS, LoreWrap.HEADER_MAX_PIXELS);

        assertFalse(LoreWrap.isEnabled());
        assertEquals(List.of(row), LoreWrap.wrap(row), "A disabled wrapper must not touch the row");
        assertEquals(List.of(row), LoreWrap.wrapAll(List.of(row)), "wrapAll must stay a no-op too");
    }

    @Test
    @DisplayName("The configured width is applied, clamped and used by every entry point")
    void configuredWidthIsApplied() {
        Component row = sampleWeaponPerkRow();

        LoreWrap.configure(true, 240, 400);
        assertEquals(240, LoreWrap.configuredMaxPixels());
        assertEquals(400, LoreWrap.configuredHeaderPixels());

        List<Component> wider = LoreWrap.wrap(row);
        assertTrue(wider.size() < LoreWrap.wrap(row, LoreWrap.DEFAULT_MAX_PIXELS).size(),
                "A wider budget must need fewer rows");
        for (Component line : wider) {
            assertTrue(LoreWrap.pixelWidth(PLAIN.serialize(line)) <= 240,
                    "Row exceeds the configured width: " + PLAIN.serialize(line));
        }

        LoreWrap.configure(true, 10, 5);
        assertEquals(LoreWrap.MIN_MAX_PIXELS, LoreWrap.configuredMaxPixels(),
                "An unreadable width must be clamped");
        assertEquals(LoreWrap.MIN_MAX_PIXELS, LoreWrap.configuredHeaderPixels(),
                "The header budget can never be narrower than the body budget");
    }

    @Test
    @DisplayName("Rows that already fit are returned untouched")
    void shortRowsAreUnchanged() {
        Component shortRow = MINI_MESSAGE.deserialize("<gray>  • Durability: <white>950 / 950</white>")
                .decoration(TextDecoration.ITALIC, false);

        List<Component> wrapped = LoreWrap.wrap(shortRow);

        assertEquals(1, wrapped.size());
        assertEquals(shortRow, wrapped.get(0), "A fitting row must keep its exact components");
    }

    @Test
    @DisplayName("A long row is split into rows that all fit the tooltip budget")
    void longRowsAreSplitWithinBudget() {
        Component row = sampleWeaponPerkRow();

        List<Component> wrapped = LoreWrap.wrap(row);

        assertTrue(wrapped.size() > 1, "A 100+ character row cannot stay on one line");
        for (Component line : wrapped) {
            int width = LoreWrap.pixelWidth(PLAIN.serialize(line));
            assertTrue(width <= LoreWrap.DEFAULT_MAX_PIXELS,
                    "Row of " + width + "px overflows the budget: " + PLAIN.serialize(line));
        }
    }

    @Test
    @DisplayName("Wrapping keeps every character (whitespace-normalised) and every colour")
    void wrappingLosesNoTextOrColour() {
        Component row = sampleWeaponPerkRow();
        List<Component> wrapped = LoreWrap.wrap(row);

        assertEquals(normalize(PLAIN.serialize(row)),
                normalize(wrapped.stream().map(PLAIN::serialize).collect(Collectors.joining(" "))),
                "Word wrapping must only replace spaces with line breaks");

        assertEquals(colorsOf(row), colorsOf(wrapped),
                "Every colour of the original row must survive the wrap");
    }

    @Test
    @DisplayName("Wrapped rows keep the bullet and hanging indentation")
    void wrappedRowsKeepIndentation() {
        Component row = Component.text("  • " + "Subterranean Strikes smash through every defense of the target",
                NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false);

        List<Component> wrapped = LoreWrap.wrap(row);

        assertTrue(wrapped.size() > 1);
        assertTrue(PLAIN.serialize(wrapped.get(0)).startsWith("  • "),
                "The bullet stays on the first row: " + PLAIN.serialize(wrapped.get(0)));
        assertTrue(PLAIN.serialize(wrapped.get(1)).startsWith("    "),
                "Continuation rows line up under the text, past the bullet: " + PLAIN.serialize(wrapped.get(1)));

        Component headerRow = Component.text("✦ Weapon Perk: " + "Infernal Sweeping Cleave hits every foe around you",
                NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
        List<Component> headerWrapped = LoreWrap.wrap(headerRow);
        assertTrue(headerWrapped.size() > 1);
        assertTrue(PLAIN.serialize(headerWrapped.get(1)).startsWith("  "),
                "Section rows hang past their ✦ marker: " + PLAIN.serialize(headerWrapped.get(1)));
    }

    @Test
    @DisplayName("Blank separator rows survive wrapping untouched")
    void blankRowsArePreserved() {
        List<Component> lore = new ArrayList<>();
        lore.add(MINI_MESSAGE.deserialize("<gold>✦ Weapon Perk: </gold><gray>Something short</gray>"));
        lore.add(Component.empty());
        lore.add(sampleWeaponPerkRow());

        List<Component> wrapped = LoreWrap.wrapAll(lore);

        assertTrue(wrapped.size() > 3, "The long row must grow the list");
        assertTrue(wrapped.get(1).equals(Component.empty()), "The blank separator must stay a blank row");
    }

    @Test
    @DisplayName("Header rows keep the wider budget so progress bars stay on one row")
    void headerRowsUseTheirOwnBudget() {
        Component tierTag = MINI_MESSAGE.deserialize("<gray>[Wood Tier]").decoration(TextDecoration.ITALIC, false);
        Component bar = MINI_MESSAGE.deserialize(
                        "<green>▮▮▮▮▮▮▮</green><gray>▯▯▯</gray> <yellow>10/15 Kills</yellow> <gray>(Next: Stone Tier)</gray>")
                .decoration(TextDecoration.ITALIC, false);

        List<Component> wrapped = LoreWrap.wrapAll(List.of(tierTag, bar, sampleWeaponPerkRow()), 2);

        assertEquals(tierTag, wrapped.get(0));
        assertEquals(bar, wrapped.get(1), "A progress bar wider than the tooltip budget stays on one row");
        assertTrue(wrapped.size() > 3, "The body row is still wrapped");
    }

    @Test
    @DisplayName("Gradient rows are wrapped without dropping their per-character colours")
    void gradientRowsSurviveWrapping() {
        Component row = MINI_MESSAGE.deserialize(
                "<gradient:#AF601A:#f39c12>Tectonic Rupture</gradient> <gray>cracking blows bypass twenty percent of armor and trigger seismic vibrations.</gray>")
                .decoration(TextDecoration.ITALIC, false);

        List<Component> wrapped = LoreWrap.wrap(row);

        assertTrue(wrapped.size() > 1);
        assertEquals(normalize(PLAIN.serialize(row)),
                normalize(wrapped.stream().map(PLAIN::serialize).collect(Collectors.joining(" "))));
        assertTrue(colorsOf(wrapped).size() > 3,
                "A gradient keeps its interpolated colours, got " + colorsOf(wrapped).size());
    }

    /**
     * A row shaped exactly like a forged weapon's perk row: a gold label, an aqua perk name and a
     * long gray description, i.e. the kind of row that used to run off the screen.
     */
    private static Component sampleWeaponPerkRow() {
        return Component.text("✦ Weapon Perk: ", NamedTextColor.GOLD)
                .append(Component.text("Infernal Sweeping Cleave: ", NamedTextColor.AQUA))
                .append(Component.text("hits multiple adjacent foes and chains elemental traits. "
                        + "Imbued with Infernal, Tempered essence: burning strikes that linger on every victim.",
                        NamedTextColor.GRAY))
                .decoration(TextDecoration.ITALIC, false);
    }

    private static String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    /**
     * Effective colour/decoration signature of every text run of a row, used to prove that wrapping
     * loses nothing: parent styles are inherited exactly like the wrapper and the client do.
     */
    private static Set<String> colorsOf(Component component) {
        Set<String> colors = new LinkedHashSet<>();
        collectStyles(component, Style.empty(), colors);
        return colors;
    }

    private static Set<String> colorsOf(List<Component> components) {
        Set<String> colors = new LinkedHashSet<>();
        for (Component component : components) {
            collectStyles(component, Style.empty(), colors);
        }
        return colors;
    }

    private static void collectStyles(Component component, Style inherited, Set<String> colors) {
        Style style = inherited.merge(component.style());
        if (component instanceof TextComponent text && !text.content().isEmpty()) {
            colors.add(Objects.toString(style.color()) + "/" + style.decoration(TextDecoration.ITALIC));
        }
        for (Component child : component.children()) {
            collectStyles(child, style, colors);
        }
    }
}
