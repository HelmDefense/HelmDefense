package fr.helmdefense.model.level;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.utils.yaml.YAMLLoader;
import javafx.beans.property.IntegerProperty;

class InventoryTest {
	private static Inventory inventory;
	private static LivingEntityType type;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		inventory = new Inventory();
		type = LivingEntityType.HUMAN_WARRIOR;
		
		Iterator<Entry<LivingEntityType, IntegerProperty>> it = inventory.getContent().entrySet().iterator();
		while ( it.hasNext()) 
			it.remove();
	}
		
	@Test
	void testAddEntityLivingEntityType() {
		// Cas : ajout d'une entité absente
		assertTrue(inventory.getContent().isEmpty());
		inventory.addEntity(type);
		assertFalse(inventory.getContent().isEmpty());
		assertEquals(1, inventory.getContent().get(type).getValue());
		// Cas : ajout d'une entité déjà présente -> les entités s'empilent
		inventory.addEntity(type, 10);
		assertEquals(11, inventory.getContent().get(type).getValue());
		// Cas : ajout d'une valeur négative d'entités -> aucune modification
		inventory.addEntity(type, -1);
		assertEquals(11, inventory.getContent().get(type).getValue());
	}

	@Test
	void testRemoveEntity() {
		// Cas : suppression d'une entité absente
		assertFalse(inventory.hasEntity(type));
		inventory.removeEntity(type);
		assertThrows(NullPointerException.class, () -> inventory.getContent().get(type).getValue());
		// Cas : suppression d'une entité présente
		inventory.addEntity(type);
		assertTrue(inventory.hasEntity(type));
		inventory.removeEntity(type);
		assertFalse(inventory.hasEntity(type));
	}

}
