package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class BountyHuntingAbilityTest {
	private static LivingEntity defender;
	private static LivingEntity attacker;
	private static Level level;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		defender = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 5, 5);
		attacker = new LivingEntity(LivingEntityType.ORC_WARRIOR, 5, 5);
		defender.spawn(level);
		attacker.spawn(level);
		attacker.looseHp(attacker.getHp() - 1, defender);
	}

	@Test
	void test() {
		int moneyBeforeEarningCoins = level.getPurse();
		defender.attack(attacker);
		assertEquals(moneyBeforeEarningCoins + attacker.stat(Attribute.REWARD), level.getPurse());
	}
}
