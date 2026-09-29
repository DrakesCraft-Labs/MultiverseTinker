package com.chagui68.multiversetinker.wiki;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Keeps the wiki in sync with the code.
 *
 * <p>The pages carry two markers around every block that mirrors a registry
 * ({@code <!-- mvtink:generated -->} … {@code <!-- mvtink:generated:end -->}). This test re-renders those
 * blocks from the live registries and fails when a committed page has drifted, so the documentation can
 * never quietly go stale.</p>
 *
 * <p>To apply a deliberate change, run the same test with the write flag and commit the result:</p>
 *
 * <pre>./mvnw -o test -Dtest=WikiDocumentationTest -Dmvtink.wiki.write=true</pre>
 */
class WikiDocumentationTest {

    private static final Path ROOT = Path.of("").toAbsolutePath();

    private ServerMock server;
    private MultiverseTinker plugin;
    private WikiPages pages;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        pages = new WikiPages(plugin.getMaterialRegistry(), plugin.getAlloyRegistry());
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Every registry-driven block of the wiki still matches the registries")
    void generatedBlocksMatchTheRegistries() {
        Map<String, List<String>> expected = pages.expectedBlocks(ROOT);

        // In write mode the same test repairs the pages instead of complaining about them.
        if (Boolean.getBoolean(WikiPages.WRITE_PROPERTY)) {
            pages.write(ROOT);
            expected = pages.expectedBlocks(ROOT);
        }

        List<String> drifted = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : expected.entrySet()) {
            Path file = ROOT.resolve(entry.getKey());
            assertTrue(Files.isRegularFile(file), entry.getKey() + " is missing from the wiki");

            List<String> committed = WikiPages.generatedBlocks(WikiPages.read(file));
            List<String> rendered = entry.getValue();
            if (committed.size() != rendered.size()) {
                drifted.add(entry.getKey() + " holds " + committed.size() + " generated blocks but the registries"
                        + " render " + rendered.size());
                continue;
            }
            for (int index = 0; index < rendered.size(); index++) {
                String actual = normalize(committed.get(index));
                String wanted = normalize(rendered.get(index));
                if (!actual.equals(wanted)) {
                    drifted.add(entry.getKey() + blockLabel(rendered.size(), index)
                            + firstDifference(actual, wanted));
                }
            }
        }

        assertTrue(drifted.isEmpty(), () -> "The wiki pages below no longer match the registries. Run\n"
                + "  ./mvnw -o test -Dtest=WikiDocumentationTest -Dmvtink.wiki.write=true\n"
                + "to regenerate them, then commit the result. Drifted pages:\n  - "
                + String.join("\n  - ", drifted));
    }

    @Test
    @DisplayName("The best-build tables rank the same builds however often they are rendered")
    void bestBuildTablesAreReproducible() {
        String page = "Wiki-en/Best-Sword-and-Bow-Combos.md";
        List<String> first = pages.render(page, "");
        assertEquals(2, first.size(), "The combination page carries a sword table and a bow table");

        // The tables rank the whole prime tier, so one render registers thousands of composites and primes —
        // every one of them an alloy the crucible would happily blend again. The page has to rank the shipped
        // materials yet again rather than its own output: ranking the live registry would make the tables
        // depend on how often they were rendered, and pairing thousands of alloys with each other costs
        // minutes, so a regression here does more than fail an assertion — it stops the build finishing at all.
        int forged = plugin.getMaterialRegistry().getAll().size();
        assertTrue(forged > 1_000, "Forging the tier must fill the registry, or this test proves nothing");

        List<String> second = pages.render(page, "");
        assertEquals(first, second, "The best-build tables must be reproducible");
        assertEquals(forged, plugin.getMaterialRegistry().getAll().size(),
                "A second render must reuse the forges the first one made, not invent new ones");
    }

    @Test
    @DisplayName("The mineral catalogs still count the minerals the registry holds")
    void mineralCatalogsCountWhatTheRegistryHolds() {
        Map<String, Boolean> catalogs = new LinkedHashMap<>();
        for (String path : WikiPages.CATALOG_PATHS) {
            catalogs.put(path, path.startsWith("Wiki-es/"));
        }

        for (Map.Entry<String, Boolean> catalog : catalogs.entrySet()) {
            String content = normalize(WikiPages.read(ROOT.resolve(catalog.getKey())));
            List<String> headings = pages.catalogHeadings(catalog.getValue());
            assertFalse(headings.isEmpty(), catalog.getKey() + " must have sections to count");

            List<String> missing = new ArrayList<>();
            for (String heading : headings) {
                if (!content.contains(heading + "\n")) missing.add(heading);
            }

            // This is the check the old catalog never had: it advertised a number of materials the registry
            // had already outgrown, and nothing noticed. The tables themselves stay hand-written (their trait
            // wording is curated and translated), but the counts can no longer drift, and a heading that
            // changed means the totals in the prose above it need the same edit.
            assertTrue(missing.isEmpty(), () -> catalog.getKey() + " no longer counts what the registry holds,"
                    + " expected these headings:\n  " + String.join("\n  ", missing));
        }
    }

    @Test
    @DisplayName("Every mineral pair is on a pair page exactly once, in both languages")
    void pairPagesCoverEveryMineralPairOnce() {
        Set<String> minerals = mixableMinerals();
        assertTrue(minerals.size() > 100, "The crucible blends the whole mineral catalog, got " + minerals.size());

        // A pair has to be documented once per language, so the check runs language by language.
        Map<Boolean, Set<String>> alloys = new LinkedHashMap<>();
        Map<Boolean, Set<String>> documentedParents = new LinkedHashMap<>();
        Map<Boolean, Integer> rows = new LinkedHashMap<>();
        for (boolean spanish : List.of(false, true)) {
            alloys.put(spanish, new LinkedHashSet<>());
            documentedParents.put(spanish, new LinkedHashSet<>());
            rows.put(spanish, 0);
        }

        for (String path : pages.generatedPaths()) {
            if (!isPairPage(path)) continue;
            boolean spanish = path.startsWith("Wiki-es/");
            String content = WikiPages.read(ROOT.resolve(path));
            String block = WikiPages.generatedBlocks(content).get(0);

            List<TinkerMaterial> parents = pages.declaredParents(content);
            assertFalse(parents.isEmpty(), path + " must declare the parents it documents");
            for (TinkerMaterial parent : parents) {
                assertTrue(minerals.contains(parent.getId()), path + " documents " + parent.getId()
                        + ", which is not a mixable mineral");
                assertTrue(documentedParents.get(spanish).add(parent.getId()),
                        parent.getId() + " is documented on more than one pair page: " + path);
            }

            for (String line : block.split("\n")) {
                if (!line.startsWith("| ") || line.startsWith("| Partner") || line.startsWith("| Socio")) continue;
                if (line.startsWith("|---|---")) continue;
                String[] cells = line.split("\\|", -1);
                assertEquals(8, cells.length, "A pair row must keep its six columns: " + line);
                String alloyId = cells[3].trim().replace("`", "");
                assertTrue(alloyId.startsWith("mvtink_"), "A pair row must name the alloy it makes: " + line);
                assertTrue(alloys.get(spanish).add(alloyId),
                        "The alloy " + alloyId + " is documented twice, in " + path);
                rows.merge(spanish, 1, Integer::sum);
            }
        }

        // Every pair the crucible accepts must have its row, and its alloy must be the one the crucible makes.
        Set<String> expectedAlloys = new LinkedHashSet<>();
        for (String first : minerals) {
            for (String second : minerals) {
                if (second.compareTo(first) <= 0) continue;
                TinkerAlloy alloy = plugin.getAlloyRegistry().findAlloy(first, second);
                expectedAlloys.add(alloy != null
                        ? alloy.id()
                        : AlloyRegistry.dynamicId(material(first), material(second)));
            }
        }

        for (boolean spanish : List.of(false, true)) {
            String language = spanish ? "the Spanish pages" : "the English pages";
            assertEquals(expectedAlloys.size(), rows.get(spanish), "Every pair must appear exactly once in " + language);
            assertEquals(expectedAlloys, alloys.get(spanish), language + " must document the alloys the crucible makes");
            assertEquals(minerals, documentedParents.get(spanish), "Every mineral that owns a pair must own"
                    + " exactly one section in " + language);
        }
    }

    /** Both languages spell the recipe pages differently, so neither name may be missed. */
    private static boolean isPairPage(String path) {
        return path.contains("Pairs") || path.contains("Pares");
    }

    /** The ids of the minerals the crucible blends: the brush and vanilla ones, never an alloy. */
    private Set<String> mixableMinerals() {
        Set<String> minerals = new LinkedHashSet<>();
        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            if (AlloyRegistry.isMixable(material)) minerals.add(material.getId());
        }
        return minerals;
    }

    private TinkerMaterial material(String id) {
        TinkerMaterial material = plugin.getMaterialRegistry().get(id);
        assertNotNull(material, id + " must be registered");
        return material;
    }

    /** Names the table a page carries more than one of, so a drift message points at the right one. */
    private static String blockLabel(int blocks, int index) {
        return blocks == 1 ? "" : " (generated block " + (index + 1) + " of " + blocks + ")";
    }

    /** Compares line by line, so a Windows checkout with CRLF endings is not reported as drift. */
    private static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    /** Points at the first line that differs, which is all a reader needs to fix the page. */
    private static String firstDifference(String committed, String rendered) {
        String[] left = committed.split("\n", -1);
        String[] right = rendered.split("\n", -1);
        for (int index = 0; index < Math.max(left.length, right.length); index++) {
            String expected = index < right.length ? right[index] : "<missing>";
            String actual = index < left.length ? left[index] : "<missing>";
            if (!expected.equals(actual)) {
                return "\n      line " + (index + 1) + "\n      committed: " + shorten(actual)
                        + "\n      registry:  " + shorten(expected);
            }
        }
        return " (the rendered block differs only in its length)";
    }

    private static String shorten(String line) {
        return line.length() > 160 ? line.substring(0, 157) + "..." : line;
    }
}
