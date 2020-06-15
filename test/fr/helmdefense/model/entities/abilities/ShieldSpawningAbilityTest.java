package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class ShieldSpawningAbilityTest {
	private static LivingEntity urukHai;
	private static Level level;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		urukHai = new LivingEntity(LivingEntityType.URUK_HAI, 5, 5);
		urukHai.data().setTier(Tier.TIER_3, level);
	}

	@Test
	void testOnSpawnAction() {
		/* l'abilité ShieldSpawningAbility ajoute un bouclier lors du spawn
		 * de l'entité ayant l'abilité
		 */
		assertFalse(urukHai.hasShield());
		urukHai.spawn(level);
		assertTrue(urukHai.hasShield());
	}

}
