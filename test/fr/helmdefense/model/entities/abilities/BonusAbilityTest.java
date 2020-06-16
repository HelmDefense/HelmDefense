package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.abilities.list.BonusAbility;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class BonusAbilityTest {
	private static LivingEntity entity;
	private static Level level;
	private static double boostPercentage;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		entity = new LivingEntity(LivingEntityType.ORC_WARRIOR, 5, 5);
		boostPercentage = 0.5;
		// ajout a entity de d'un bonus de 50% de vitesse de mouvement, déclenché au spawn de l'entité
		entity.addAbilities(new BonusAbility(Tier.TIER_1, Specification.NO_SPECIFICATION, "MVT_SPD", "MULTIPLY", boostPercentage));
	}

	@Test
	void testRangeBonus() {
		entity.spawn(level);
		assertEquals(entity.data().getStats().get(Attribute.MVT_SPD) * (1 + boostPercentage), entity.stat(Attribute.MVT_SPD));
	}

}
