package fr.helmdefense.model.entities.living;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class LivingEntityTest {
	private static Level level;
	private static Level level2;
	private static LivingEntity humanWarriorA;
	private static LivingEntity humanWarriorB;
	private static LivingEntity orcWarriorA;
	private static LivingEntity orcWarriorB;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
		level2 = YAMLLoader.loadLevel("Troll Level");
		humanWarriorA = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 10, 10);
		humanWarriorB = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 11, 10);
		orcWarriorA =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 11);
		orcWarriorB =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 12);
		orcWarriorA.spawn(level);
		orcWarriorA.setShield(1000);
		humanWarriorA.spawn(level);
		humanWarriorB.spawn(level);
	}

	@Test
	void testAttack() {
		//Cas : Perte de pv suite à une attaque portée par orcWarriorA sur humanWarriorA
		orcWarriorA.attack(humanWarriorA);
		assertEquals(humanWarriorA.stat(Attribute.HP) - orcWarriorA.stat(Attribute.DMG), humanWarriorA.getHp());
	}
	
	@Test
	void testTeleport() {
		// Cas : téléportation de (10,10) -> (11,12)
		humanWarriorA.teleport(11, 12);
		assertEquals(11, humanWarriorA.getLoc().getX());
		assertEquals(12, humanWarriorA.getLoc().getY());
	}
	
	
/* looseHp(int amount, DamageCause cause, boolean ignoreShield)
 * Retourne la quantité de Pv perdue par l'unité en cas de succés, -1 en cas d'échec
 * Si ignoreShield = false, retire amount au bouclier ( si bouclier il y a),
 * sinon retire directement des pv a l'entité même si elle a un bouclier
 */
	@Test 
	void testLooseHp() {
		// Cas : Perte de 10 Pv sans ignorer le bouclier -> Entité : Pv max ; bouclier : 990
		assertEquals(0, orcWarriorA.looseHp(10, orcWarriorA, false));
		// Cas : Perte de la valeur du bouclier -> Entité : Pv max ; bouclier : 0
		int bouclier = orcWarriorA.getShield();
		assertEquals(0, orcWarriorA.looseHp(bouclier, orcWarriorA, false)); // fin du bouclier
		// Cas : nouveau bouclier ; Perte d'une valeur supérieure au bouclier sans l'ignorer -> Entité : Pv max - 100 ; bouclier : 0
		orcWarriorA.setShield(1000);
		assertEquals(100, orcWarriorA.looseHp(1100, orcWarriorA, false));
		// Cas : perte de 200 Pv, sans bouclier -> Entité : ( Pv max - 100 ) - 200 ; bouclier : 0
		assertEquals(200, orcWarriorA.looseHp(200, orcWarriorA));
		// Cas : Nouveau bouclier et perte de 100 Pv en ignorant le bouclier -> Entité : ( Pv max - 300 ) - 200 ; bouclier : 1000
		orcWarriorA.setShield(1000);
		assertEquals(200, orcWarriorA.looseHp(200, orcWarriorA, true));
		// Cas : Perte de la totalité de sa vie en ignorant le bouclier -> Entité : 0 Pv ; bouclier : 1000
		assertEquals(orcWarriorA.stat(Attribute.HP) - 500, orcWarriorA.looseHp(orcWarriorA.getHp(), orcWarriorA, true)); // mort de l'entité
		// Cas : Perte de la totalité de la vie de l'entité et de son bouclier
		humanWarriorA.setShield(1000);
		assertEquals(humanWarriorA.stat(Attribute.HP), humanWarriorA.looseHp(humanWarriorA.getHp() + humanWarriorA.getShield(), humanWarriorA, false)); // mort de l'entité
		// Cas : Perte de 10 Pv en étant déjà mort
		assertEquals(-1, orcWarriorA.looseHp(10, orcWarriorA)); 
		// Cas : Perte de -10 Pv
		assertEquals(-1, orcWarriorB.looseHp(-10, orcWarriorA));
	}
	
	@Test
	void testGainHp() {
		// Cas : gain de 10 Pv avec humanWarriorB déjà à 100% Pv -> valeur MAX
		orcWarriorB.gainHp(10);
		assertEquals(humanWarriorB.stat(Attribute.HP), humanWarriorB.getHp());
		humanWarriorB.looseHp(20, humanWarriorB); 
		// Cas : gain de 10 Pv -> humanWarriorB Pv : MAX - 20
		humanWarriorB.gainHp(10); 
		assertEquals(humanWarriorB.stat(Attribute.HP) - 10, humanWarriorB.getHp());
		// Cas : gain de -10 Pv -> humanWarriorB Pv : MAX - 10 
		humanWarriorB.gainHp(-10); 
		assertEquals(humanWarriorB.stat(Attribute.HP) - 10, humanWarriorB.getHp());
	}
	
	@Test
	void testSetShield() {
		assertFalse(humanWarriorA.hasShield()); 
		// Cas : Ajout d'un bouclier sur une entité qui n'en a pas
		humanWarriorA.setShield(1000);
		assertTrue(humanWarriorA.hasShield());
		assertEquals(1000, humanWarriorA.getShield());
		// Cas : ajout d'un bouclier alors qu'il en a déjà un -> le nouveau bouclier écrase le précédent
		humanWarriorA.setShield(2000);
		assertTrue(humanWarriorA.hasShield());
		assertEquals(2000, humanWarriorA.getShield());
		// Cas : ajout d'un bouclier négatif -> suppression du bouclier
		humanWarriorA.setShield(-1);
		assertFalse(humanWarriorA.hasShield());
	}
	
	@Test
	void testSetHp() {
		// Cas : définition d'une valeur positive
		humanWarriorA.setHp(10);
		assertEquals(10, humanWarriorA.getHp());
		// Cas : définition d'une valeur nulle -> mort de l'entité
		humanWarriorA.setHp(0);
		assertEquals(0, humanWarriorA.getHp());
		// Cas : définition d'une valeur négative -> mort de l'entité
		humanWarriorA.setHp(-1);
		assertEquals(-1, humanWarriorA.getHp());
	}
	
	@Test
	void testIsAlive() {
		// Cas : orcWarriorA vivant -> true
		assertTrue(orcWarriorA.isAlive()); 
		// Cas : orcWarriorA mort -> false
		orcWarriorA.setHp(0);
		assertFalse(orcWarriorA.isAlive());
	}
	
	@Test
	void testAddFlags() {
		// Cas : Ajout d'un flag non existant -> ajout
		assertEquals(0, humanWarriorB.getFlags());
		humanWarriorB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, humanWarriorB.getFlags());
		// Cas : Ajout d'un flag déja présent -> aucune modification
		humanWarriorB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, humanWarriorB.getFlags());
	}
	
	@Test
	void testRemoveFlags() {
		// Cas : flag inexistant -> aucune modification
		humanWarriorB.removeFlags(LivingEntity.FIRE);
		assertEquals(0, humanWarriorB.getFlags());
		// Cas : flag existant -> suppression
		humanWarriorB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, humanWarriorB.getFlags());
		humanWarriorB.removeFlags(LivingEntity.FIRE);
		assertEquals(0, humanWarriorB.getFlags());
	}
	
	@Test
	void testToggleFlags() {
		// Cas : Flag non présent -> ajout
		humanWarriorB.toggleFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, humanWarriorB.getFlags());
		// Cas : Flag absent -> suppression
		humanWarriorB.toggleFlags(LivingEntity.FIRE);
		assertEquals(0, humanWarriorB.getFlags());
	}
	
	@Test
	void testTestFlags() {
		// Cas : Flag absent -> false
		humanWarriorB.removeFlags(LivingEntity.FIRE);
		assertFalse(humanWarriorB.testFlags(LivingEntity.FIRE));
		// Cas : Flag présent -> true
		humanWarriorB.addFlags(LivingEntity.FIRE);
		assertTrue(humanWarriorB.testFlags(LivingEntity.FIRE));
	}
	
	@Test
	void testSpawn() {
		// Cas : orcWarriorB n'a pas encore spawn -> null
		assertNull(orcWarriorB.getLevel());
		// Cas : spawn de orcWarriorB -> Stockage du level dans l'entité et réciproquement
		orcWarriorB.spawn(level);
		assertEquals(level, orcWarriorB.getLevel());
		// Cas : orcWarriorB est déjà dans le level -> aucune modification
		orcWarriorB.spawn(level);
		assertEquals(level, orcWarriorB.getLevel());
		// Cas : tentative de spawn d'orcWarriorB dans un autre level -> aucune modification
		orcWarriorB.spawn(level2);
		assertEquals(level, orcWarriorB.getLevel());
	}
	
	@Test
	void testIsEnemy() {
		// Cas : orcWarriorA est un enemi de humanWarriorA -> true
		assertTrue(orcWarriorA.isEnemy(humanWarriorA));
		// Cas : humanWarriorA est un allié de humanWarriorB -> false
		assertFalse(humanWarriorA.isEnemy(humanWarriorB));
	}
}
