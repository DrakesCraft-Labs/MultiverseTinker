package com.chagui68.multiversetinker.site;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Keeps the interactive alloy site in sync with the code.
 *
 * <p>The page reads one generated file, {@code docs/data/alloys.json}, and this test re-renders it from
 * the live registries. A material, a trait, an essence or a stat that changes in the plugin therefore
 * fails the build until the site is regenerated, exactly like the wiki pages.</p>
 *
 * <p>To apply a deliberate change, run the same test with the write flag and commit the result:</p>
 *
 * <pre>./mvnw -o test -Dtest=SiteDataTest -Dmvtink.site.write=true</pre>
 */
class SiteDataTest {

    private static final Path ROOT = Path.of("").toAbsolutePath();

    private ServerMock server;
    private MultiverseTinker plugin;
    private SiteData data;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        data = new SiteData(plugin.getMaterialRegistry(), plugin.getAlloyRegistry());
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The site's data file still matches the registries")
    void siteDataMatchesTheRegistries() {
        Path file = ROOT.resolve(SiteData.PATH);

        // In write mode the same test repairs the file instead of complaining about it.
        if (Boolean.getBoolean(SiteData.WRITE_PROPERTY)) {
            data.write(ROOT);
        }

        assertTrue(Files.isRegularFile(file), SiteData.PATH + " is missing; run the test with"
                + " -D" + SiteData.WRITE_PROPERTY + "=true to generate it");

        String committed = normalize(SiteData.read(file));
        String rendered = normalize(data.json());
        if (!committed.equals(rendered)) {
            fail("The site's data file no longer matches the registries. Run\n"
                    + "  ./mvnw -o test -Dtest=SiteDataTest -D" + SiteData.WRITE_PROPERTY + "=true\n"
                    + "to regenerate it, then commit the result." + firstDifference(committed, rendered));
        }
    }

    @Test
    @DisplayName("Rendering the site never forges into the catalog it publishes")
    void renderingTheSiteLeavesTheRegistryAlone() {
        int before = plugin.getMaterialRegistry().getAll().size();
        String first = data.json();

        // The golden pairs are the trap here: they are real fusions, and a fusion registers a material.
        // Forging them against the live registry would push a dozen composites into the very catalog the
        // page had just published, and the second render would publish a different catalog than the first.
        assertEquals(before, plugin.getMaterialRegistry().getAll().size(),
                "Publishing the site must not add a material to the registry");

        SiteData again = new SiteData(plugin.getMaterialRegistry(), plugin.getAlloyRegistry());
        assertEquals(normalize(first), normalize(again.json()), "The site's data must be reproducible");
    }

    @Test
    @DisplayName("Every fusion branch the page reproduces is pinned to something the crucible forged")
    void goldenPairsCoverEveryFusionBranch() {
        String json = data.json();
        int golden = json.split("\"case\":", -1).length - 1;
        assertTrue(golden >= 15, "The golden pairs must cover the mixing rules, got " + golden);

        for (String branch : new String[]{"\"fusion\": \"legendary\"", "\"fusion\": \"prime\"",
                "\"fusion\": \"composite\""}) {
            assertTrue(json.contains(branch), "The golden pairs must pin a " + branch
                    + " fusion, or the page's math is only checked on one branch");
        }
        assertEquals(2, json.split("\"craftable\": false", -1).length - 1,
                "A pair the crucible refuses must be pinned too, so the page cannot claim everything blends");

        // A curated recipe has to come back as the recipe, not as the composite its parents would make:
        // copper and tin are both freely blendable, and the crucible still hands out Bronze.
        String golden2 = section(json, "golden");
        assertTrue(golden2.contains("\"name\": \"Bronze\"") && golden2.contains("\"id\": \"mvtink_bronze\""),
                "A curated pair must resolve to its recipe");
        assertTrue(golden2.contains("\"durability\": 350"),
                "The recorded numbers must be the ones the recipe ships, not the composite formula's");
    }

    @Test
    @DisplayName("The file publishes the shipped catalog, every entry complete and in a stable order")
    void shippedCatalogIsPublished() {
        String json = data.json();

        assertTrue(data.publishedMaterials() >= 130,
                "The site must publish the whole shipped catalog, got " + data.publishedMaterials());

        // The published catalog is the shipped one: a composite or a prime only exists on a server where a
        // player already forged it, so the page must never list one as something to look up.
        String catalog = section(json, "materials");
        assertFalse(catalog.contains("\"id\": \"mvtink_alloy_"), "A forged composite is not shipped data");
        assertFalse(catalog.contains("\"id\": \"mvtink_prime_"), "A forged prime is not shipped data");

        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            String id = material.getId();
            assertTrue(json.contains("\"id\": \"" + id + "\""), id + " must be published");
            assertFalse(TraitAffinity.of(material).isEmpty(), id + " must teach at least one essence");
        }

        // Every essence the page can print comes from the plugin, Spanish shorthand included.
        for (TraitAffinity essence : TraitAffinity.values()) {
            assertTrue(json.contains("\"id\": \"" + essence.name() + "\""),
                    essence.name() + " must be published as an essence");
        }

        Set<String> published = new LinkedHashSet<>();
        int index = 0;
        while ((index = json.indexOf("\"kind\":", index)) >= 0) {
            int end = json.indexOf('\n', index);
            published.add(json.substring(index, end).trim());
            index = end;
        }
        assertFalse(published.isEmpty());

        // A file that parses is the page's problem to prove, but a stray NaN or a trailing fragment would
        // break JSON.parse in the browser, so the shape is checked here too.
        assertTrue(json.startsWith("{\n"), "The file must be a JSON object");
        assertTrue(json.endsWith("}\n"), "The file must end with the closing brace and a newline");
        assertFalse(json.contains("NaN"), "JSON has no NaN");
        assertEquals(json.length() - json.replace("{", "").length(),
                json.length() - json.replace("}", "").length(), "The braces must balance");
    }

    @Test
    @DisplayName("The equipment tables publish every tier and every family the Forge builds")
    void equipmentTablesCoverTheForge() {
        String json = data.json();

        // The calculator offers what the plugin has and nothing else: a tier the page forgets is a build
        // nobody can price, and one it invents is a build that does not exist.
        assertEquals(EvolutionTier.values().length, count(json, "\"speedMultiplier\":"),
                "Every tier must be published exactly once");
        assertEquals(ModularWeaponType.values().length, count(json, "\"twoPart\":"),
                "Every weapon must be published exactly once");
        assertEquals(ModularArmorType.values().length, count(json, "\"baseDefense\":"),
                "Every armor slot must be published exactly once");

        for (ModularToolType tool : ModularToolType.values()) {
            assertTrue(json.contains("\"displayName\": \"" + tool.getDisplayName() + "\""),
                    tool.name() + " must be published as equipment");
        }
        // The retired sword alias shares its display name with the weapon it duplicates, so a single
        // occurrence is the proof the tools table skipped it instead of listing the same item twice.
        assertEquals(1, count(json, "\"displayName\": \"" + ModularToolType.SWORD.getDisplayName() + "\""),
                "The deprecated tool alias must not be published next to the weapon it duplicates");
    }

    @Test
    @DisplayName("The builds the page prices are builds the Forge really assembled")
    void goldenBuildsCoverEveryFamily() {
        String json = data.json();

        // Only a build row carries a tier, so this counts the builds without parsing the file.
        int builds = count(json, "\"tier\":");
        assertTrue(builds >= 8, "The golden builds must cover the Forge, got " + builds);

        for (String kind : new String[]{"\"kind\": \"weapon\"", "\"kind\": \"tool\"", "\"kind\": \"armor\""}) {
            assertTrue(json.contains(kind), "The golden builds must pin a " + kind);
        }
        // A weapon reads its damage from a different part depending on how many it takes, so both
        // arities are pinned: one alone would let the calculator use the other's rule unnoticed.
        assertTrue(json.contains("two-part weapon"), "A two-part weapon must be pinned");
        assertTrue(json.contains("three-part weapon"), "A three-part weapon must be pinned");

        // A build is assembled from shipped materials: a composite or a prime only exists on a server
        // where somebody already forged it, so a build forged from one would name a part the reader
        // cannot pick from the catalog.
        String goldenBuilds = json.substring(json.indexOf("\"goldenBuilds\": ["));
        assertTrue(goldenBuilds.contains("\"parts\": ["), "A build must name the parts it was assembled from");
        assertFalse(goldenBuilds.contains("mvtink_alloy_"), "A build must be forged from shipped materials");
        assertFalse(goldenBuilds.contains("mvtink_prime_"), "A build must be forged from shipped materials");
    }

    /**
     * How often a fragment appears, which is how a generated section's size is checked without parsing
     * the JSON twice.
     */
    private static int count(String json, String fragment) {
        int total = 0;
        for (int index = json.indexOf(fragment); index >= 0; index = json.indexOf(fragment, index + fragment.length())) {
            total++;
        }
        return total;
    }

    /**
     * One top-level array of the file, so a check can be scoped to the catalog or to the golden pairs.
     *
     * <p>Both hold material ids, and only one of them is allowed to name a forged alloy.</p>
     */
    private static String section(String json, String key) {
        int start = json.indexOf("\"" + key + "\": [");
        assertTrue(start >= 0, key + " must be an array in the file");
        int end = json.indexOf("\n  ]", start);
        assertTrue(end > start, key + " must close at the top level");
        return json.substring(start, end);
    }

    /** Compares line by line, so a Windows checkout with CRLF endings is not reported as drift. */
    private static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    /** Points at the first line that differs, which is all a reader needs to fix the file. */
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
        return " (the rendered file differs only in its length)";
    }

    private static String shorten(String line) {
        return line.length() > 160 ? line.substring(0, 157) + "..." : line;
    }
}
