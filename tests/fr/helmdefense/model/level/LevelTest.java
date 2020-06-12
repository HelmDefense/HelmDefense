package fr.helmdefense.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.utils.yaml.YAMLLoader;

class LevelTest {
	private static Level level;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
	}

	@Test
	void testDebit() {
		assertTrue(level.debit(1));
		assertFalse(level.debit(-1));
		assertFalse(level.debit(level.getPurse() + 1));
		assertTrue(level.debit(level.getPurse()));
	}

	@Test
	void testEarnCoins() {
		level.earnCoins(5);
		assertEquals(5, level.getPurse());
		level.earnCoins(-10);
		assertEquals(5, level.getPurse());
	}
}
