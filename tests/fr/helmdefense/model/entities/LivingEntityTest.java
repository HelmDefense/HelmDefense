package fr.helmdefense.model.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class LivingEntityTest {
	private static Level level;
	private static Level level2;
	private static LivingEntity defenderA;
	private static LivingEntity defenderB;
	private static LivingEntity attackerA;
	private static LivingEntity attackerB;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
		level2 = YAMLLoader.loadLevel("Troll Level");
		defenderA = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 10, 10);
		defenderB = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 11, 10);
		attackerA =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 11);
		attackerB =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 12);
		attackerA.spawn(level);
		defenderA.spawn(level);
		defenderB.spawn(level);
	}

	@Test
	void testAttack() {
		//Cas : Perte de pv suite à une attaque portée par attackerA sur defenderA
		attackerA.attack(defenderA);
		assertEquals(defenderA.data().getStats().get(Attribute.HP) - attackerA.data().getStats().get(Attribute.DMG), defenderA.getHp());
	}
	
	@Test
	void testTeleport() {
		// Cas : téléportation de (10,10) -> (11,12)
		defenderA.teleport(11, 12);
		assertEquals(11, defenderA.getLoc().getX());
		assertEquals(12, defenderA.getLoc().getY());
	}
	
	
/* looseHp(int amount, Entity cause)
 * Retourne la quantité de Pv perdu(s) en cas de succés, -1 en cas d'échec
 */
	@Test 
	void testLooseHp() {
		// Cas : Perte de 10 Pv 
		assertEquals(10, attackerA.looseHp(10, attackerA));
		// Cas : Perte de la quantité totale de Pv ( entrainant la mort )
		int pv = attackerA.getHp();
		assertEquals(pv, attackerA.looseHp(pv, attackerA)); // mort de l'entité
		// Cas : Perte de 10 Pv en étant déjà mort
		assertEquals(-1, attackerA.looseHp(10, attackerA)); // entité déjà morte
		// Cas : Perte de -10 Pv
		assertEquals(-1, attackerB.looseHp(-10, attackerA));
	}
	
	@Test
	void testGainHp() {
		// Cas : gain de 10 Pv avec defenderB déjà à 100% Pv -> valeur MAX
		defenderB.gainHp(10);
		assertEquals(defenderB.stat(Attribute.HP), defenderB.getHp());
		defenderB.looseHp(20, defenderB); 
		// Cas : gain de 10 Pv -> DefenderB Pv : MAX - 20
		defenderB.gainHp(10); 
		assertEquals(defenderB.stat(Attribute.HP) - 10, defenderB.getHp());
		// Cas : gain de -10 Pv -> DefenderB Pv : MAX - 10 
		defenderB.gainHp(-10); 
		assertEquals(defenderB.stat(Attribute.HP) - 10, defenderB.getHp());
	}
	
	@Test
	void testIsAlive() {
		// Cas : AttackerA vivant -> true
		assertFalse(attackerA.isAlive()); 
		// Cas : AttackerA mort -> false
		attackerA.looseHp(attackerA.getHp(), attackerA);
		assertTrue(defenderA.isAlive());
	}
	
	@Test
	void testAddFlags() {
		// Cas : Ajout d'un flag non existant -> ajout
		assertEquals(0, defenderB.getFlags());
		defenderB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
		// Cas : Ajout d'un flag déja présent -> aucune modification
		defenderB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
	}
	
	@Test
	void testRemoveFlags() {
		// Cas : flag inexistant -> aucune modification
		defenderB.removeFlags(LivingEntity.FIRE);
		assertEquals(0, defenderB.getFlags());
		// Cas : flag existant -> suppression
		defenderB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
		defenderB.removeFlags(LivingEntity.FIRE);
		assertEquals(0, defenderB.getFlags());
	}
	
	@Test
	void testToggleFlags() {
		// Cas : Flag non présent -> ajout
		defenderB.toggleFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
		// Cas : Flag absent -> suppression
		defenderB.toggleFlags(LivingEntity.FIRE);
		assertEquals(0, defenderB.getFlags());
	}
	
	@Test
	void testTestFlags() {
		// Cas : Flag absent -> false
		defenderB.removeFlags(LivingEntity.FIRE);
		assertFalse(defenderB.testFlags(LivingEntity.FIRE));
		// Cas : Flag présent -> true
		defenderB.addFlags(LivingEntity.FIRE);
		assertTrue(defenderB.testFlags(LivingEntity.FIRE));
	}
	
	@Test
	void testSpawn() {
		// Cas : attackerB n'a pas encore spawn -> null
		assertNull(attackerB.getLevel());
		// Cas : spawn de attackerB -> Stockage du level dans l'entité et réciproquement
		attackerB.spawn(level);
		assertEquals(level, attackerB.getLevel());
		// Cas : attackerB est déjà dans le level -> aucune modification
		attackerB.spawn(level);
		assertEquals(level, attackerB.getLevel());
		// Cas : tentative de spawn d'attackerB dans un autre level -> aucune modification
		attackerB.spawn(level2);
		assertEquals(level, attackerB.getLevel());
	}
	
	@Test
	void testIsEnemy() {
		// Cas : attackerA est un enemi de defenderA -> true
		assertTrue(attackerA.isEnemy(defenderA));
		// Cas : defenderA est un allié de defenderB -> false
		assertFalse(defenderA.isEnemy(defenderB));
	}
}
