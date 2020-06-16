package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class GoldStealingAbility {
	private static LivingEntity defender;
	private static LivingEntity attacker;
	private static Level level;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		defender = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 5, 5);
		attacker = new LivingEntity(LivingEntityType.GOBLIN, 5, 5);
		defender.spawn(level);
		attacker.spawn(level);
		defender.looseHp(defender.getHp() - 1, attacker);
	}

	@Test
	void testOnEntityKillAction() {
		/* le gobelin vole 30 coins au tier 1, 60 au tier 2 et 100 au tier 3
		 * Ici on retire 30 car attacker est un gobelin de tier 1
		 */
		if ( attacker.data().getTier() != Tier.TIER_1)
			fail("Tier incorrect");
		int moneyBeforeStealing = level.getPurse();
		attacker.attack(defender);
		assertEquals(moneyBeforeStealing - 30, level.getPurse());
	}

}
