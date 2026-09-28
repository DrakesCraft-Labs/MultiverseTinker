package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialRegistryTest {

    private MaterialRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new MaterialRegistry();
    }

    @Test
    @DisplayName("Debe registrar exactamente 90 materiales geologicos (30 por dimension)")
    void testTotalMaterialsCount() {
        Collection<TinkerMaterial> all = registry.getAll();
        assertEquals(90, all.size(), "Deben haber exactamente 90 materiales registrados (30 por dimension)");
    }

    @Test
    @DisplayName("Todos los IDs deben comenzar estrictamente con 'mvtink_'")
    void testMaterialIdsPrefix() {
        for (TinkerMaterial mat : registry.getAll()) {
            assertTrue(mat.getId().startsWith("mvtink_"),
                    "El ID " + mat.getId() + " debe comenzar con 'mvtink_'");
            assertNotNull(mat.getName());
            assertNotNull(mat.getTraitName());
            assertNotNull(mat.getTraitDescription());
            assertNotNull(mat.getColorHex());
        }
    }

    @Test
    @DisplayName("Los 25 materiales solicitados inicialmente deben estar presentes")
    void testRequested25MaterialsExist() {
        String[] requested = {
                "mvtink_tin", "mvtink_zinc", "mvtink_silver", "mvtink_cobalt", "mvtink_ardite",
                "mvtink_ruby", "mvtink_enderite", "mvtink_sapphire", "mvtink_talc", "mvtink_gypsum",
                "mvtink_pyrite", "mvtink_fluorite", "mvtink_galena", "mvtink_magnetite", "mvtink_sulfur",
                "mvtink_adamita", "mvtink_adamantium", "mvtink_stibnite", "mvtink_borax", "mvtink_beryllium",
                "mvtink_calcite_gem", "mvtink_celestine", "mvtink_chromite", "mvtink_graphite", "mvtink_jade"
        };

        for (String id : requested) {
            assertNotNull(registry.get(id), "El mineral solicitado '" + id + "' debe existir en el registro");
        }
    }

    @Test
    @DisplayName("Distribucion equitativa exacta: 30 en Overworld, 30 en Nether, 30 en The End")
    void testDimensionDistribution() {
        List<TinkerMaterial> overworld = registry.getByOrigin(MineralOrigin.OVERWORLD);
        List<TinkerMaterial> nether = registry.getByOrigin(MineralOrigin.NETHER);
        List<TinkerMaterial> end = registry.getByOrigin(MineralOrigin.THE_END);

        assertEquals(30, overworld.size(), "Overworld debe tener exactamente 30 minerales");
        assertEquals(30, nether.size(), "Nether debe tener exactamente 30 minerales");
        assertEquals(30, end.size(), "The End debe tener exactamente 30 minerales");
        assertEquals(90, overworld.size() + nether.size() + end.size());
    }
}
