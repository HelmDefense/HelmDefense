package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.abilities.list.BombExplodeOnDeathAbility;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class BombExplodeOnDeathAbilityTest {
	private static LivingEntity bomber;
	private static LivingEntity defenderA;
	private static LivingEntity defenderB;
	private static Level level;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		// Création et ajout à bomber de l'abilité de faire exploser sa bombe à sa mort 
		BombExplodeOnDeathAbility ability = new BombExplodeOnDeathAbility(Tier.TIER_1, Tier.Specification.NO_SPECIFICATION);
		bomber = new LivingEntity(LivingEntityType.ORC_BOMBER, 5, 5);
		bomber.addAbilities(ability);
		// defenderA dans la zone de la bombe, defenderB non
		defenderA = new LivingEntity(LivingEntityType.ARCHER, 6, 6);
		defenderB = new LivingEntity(LivingEntityType.ARCHER, 10, 10);
		bomber.spawn(level);
		defenderA.spawn(level);
		defenderB.spawn(level);
	}

	@Test
	void testBombExplodeOnDeathAbility() {
		// Mort de bomber sous les coups de defenderA
		bomber.looseHp(bomber.getHp(), defenderA);
		// defenderA perd des Pv car il est dans la zone
		assertEquals(defenderA.stat(Attribute.HP) - bomber.stat(Attribute.DMG), defenderA.getHp());
		// defenderB ne perd rien car il est en dehors
		assertEquals(defenderB.stat(Attribute.HP), defenderB.getHp());
	}

}
