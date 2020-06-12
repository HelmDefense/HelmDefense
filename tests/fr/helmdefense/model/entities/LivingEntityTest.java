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
	private static LivingEntity defenderA;
	private static LivingEntity defenderB;
	private static LivingEntity attackerA;
	private static LivingEntity attackerB;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
		defenderA = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 10, 10);
		defenderB = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 11, 10);
		attackerA =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 11);
		attackerB =  new LivingEntity(LivingEntityType.ORC_WARRIOR, 11, 12);
		defenderA.spawn(level);
		defenderB.spawn(level);
		attackerA.spawn(level);
	}

	@Test
	void testAttack() {
		attackerA.attack(defenderA);
		assertEquals(defenderA.data().getStats().get(Attribute.HP) - attackerA.data().getStats().get(Attribute.DMG), defenderA.getHp());
	}
	
	@Test
	void testTeleport() {
		defenderA.teleport(11, 12);
		assertEquals(11, defenderA.getLoc().getX());
		assertEquals(12, defenderA.getLoc().getY());
	}
	
	@Test 
	void testLooseHp() {
		assertEquals(10, attackerA.looseHp(10, attackerA));
		int pv = attackerA.getHp();
		assertEquals(pv, attackerA.looseHp(pv, attackerA)); // mort de l'entité
		assertEquals(-1, attackerA.looseHp(10, attackerA)); 
	}
	
	@Test
	void testGainHp() {
		defenderB.gainHp(10); // defenderB a déjà 100% de vie donc ne gagne rien
		assertEquals(defenderB.stat(Attribute.HP), defenderB.getHp());
		defenderB.looseHp(20, defenderB); // defenderB HP : MAX HP - 20
		defenderB.gainHp(10); // defenderB HP : MAX HP - 10
		assertEquals(defenderB.stat(Attribute.HP) - 10, defenderB.getHp());
		defenderB.gainHp(-10); 
		assertEquals(defenderB.stat(Attribute.HP) - 10, defenderB.getHp());
	}
	
	@Test
	void testIsAlive() {
		assertFalse(attackerA.isAlive()); // mort dans testLooseHp()
		assertTrue(defenderB.isAlive());
	}
	
	@Test
	void testAddFlags() {
		assertEquals(0, defenderB.getFlags());
		defenderB.addFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
		System.out.println("A");
	}
	
	@Test
	void testRemoveFlags() {
		defenderB.removeFlags(LivingEntity.FIRE);
		assertEquals(0, defenderB.getFlags());
	}
	
	@Test
	void testToggleFlags() {
		defenderB.toggleFlags(LivingEntity.FIRE);
		assertEquals(LivingEntity.FIRE, defenderB.getFlags());
		defenderB.toggleFlags(LivingEntity.FIRE);
		assertEquals(0, defenderB.getFlags());
		System.out.println("B");
	}
	
	@Test
	void testTestFlags() {
		System.out.println("C");
		assertTrue(defenderB.testFlags(LivingEntity.FIRE));
		defenderB.toggleFlags(LivingEntity.FIRE);
		assertFalse(defenderB.testFlags(LivingEntity.FIRE));
	}
	
	@Test
	void testSpawn() {
		assertNull(attackerB.getLevel());
		attackerB.spawn(level);
		assertEquals(level, attackerB.getLevel());
		
	}
	
	@Test
	void testIsEnemy() {
		assertTrue(attackerA.isEnemy(defenderA));
		assertFalse(defenderA.isEnemy(defenderB));
	}
}
