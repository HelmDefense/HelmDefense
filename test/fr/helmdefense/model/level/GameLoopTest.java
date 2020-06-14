package fr.helmdefense.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.utils.yaml.YAMLLoader;

class GameLoopTest {
	private static GameLoop loop;
	private static Level level;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		loop = new GameLoop(ticks -> {
			Actions.trigger(new GameTickAction(level, ticks));
		});
	}

	@Test
	void testTogglePause() {
		// Cas : la loop avance, togglePause -> la loop n'avance plus
		assertTrue(loop.isPlaying());
		loop.togglePause();
		assertFalse(loop.isPlaying());
		// Cas : la loop est arrêtée, togglePause -> la loop avance
		loop.togglePause();
		assertTrue(loop.isPlaying());
	}

	@Test
	void testSetSpeedness() {
		assertEquals(1, loop.getSpeedness()); // loop définie a 1 par défaut
		// Cas : définition d'une vitesse positive
		loop.setSpeedness(2);
		assertEquals(2, loop.getSpeedness());
		// Cas : définition d'une vitesse négative -> retour en arrière dans le jeu
		loop.setSpeedness(-1);
		assertEquals(-1, loop.getSpeedness());
		// Cas : définition d'une vitesse nulle -> arrêt du jeu
		loop.setSpeedness(0);
		assertEquals(0, loop.getSpeedness());
	}

	@Test
	void testStep() {
		// Cas : avancement d'une tick
		long ticks = loop.getTicks();
		loop.step();
		assertEquals(ticks + 1, loop.getTicks());
	}

}
