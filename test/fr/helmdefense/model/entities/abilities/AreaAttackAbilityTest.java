package fr.helmdefense.model.entities.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.abilities.list.AreaAttackAbility;
import fr.helmdefense.model.entities.abilities.list.AreaDirectAttackAbility;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class AreaAttackAbilityTest {
	private static LivingEntity entity;
	private static LivingEntity entityAlly;
	private static LivingEntity victimA;
	private static LivingEntity victimB;
	private static LivingEntity victimC;
	private static LivingEntity victimD;
	private static LivingEntity victimE;
	private static LivingEntity victimF;
	private static LivingEntity victimG;
	private static Level level;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Troll Level");
		/* ajout a entity l'abilité
		 *de faire des dégats de zone dans un cercle de rayon 1 case autour de sa victime
		 */
		AreaAttackAbility ability = new AreaDirectAttackAbility(Tier.TIER_1, Tier.Specification.NO_SPECIFICATION, 1d); 
		entity = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 5, 5);
		entity.addAbilities(ability); 
		entityAlly = new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 6, 7);
		/* entityAlly est une unité du même camp que entity,
		 * et ne doit pas être touché par l'attaque bien qu'elle soit dans la zone
		 */
		double xA = 6, yA = 6;
		victimA = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA, yA); 
		/* victimA est la première victime de entity, c'est autour de cette dernière
		 *  que les dégats de zone sont appliqués, il devient donc le centre du cercle de dégats
		 */
		victimB = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA, yA + 1);
		// victimB est 1 case en dessous de victimA ; dans la zone
		victimC = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA + 1, yA); 
		// victimC est 1 case à droite de victimA ; dans la zone
		victimD = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA + 0.5, yA + 0.5);
		// victimD est en bas à droite de victimA ; dans la zone
		victimE = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA, yA + 1.1);
		// victimE est en dessous de victimA ; en dehors de la zone
		victimF = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA + 1.1, yA);
		// victimF est à droite de victimA ; en dehors de la zone
		victimG = new LivingEntity(LivingEntityType.ORC_WARRIOR, xA + 1, yA + 1); 
		// victimG est en dessous et à droite de victimA ; en dehors de la zone
		
		entity.spawn(level);
		entityAlly.spawn(level);
		victimA.spawn(level);
		victimB.spawn(level);
		victimC.spawn(level);
		victimD.spawn(level);
		victimE.spawn(level);
		victimF.spawn(level);
		victimG.spawn(level);
	}

	@Test
	void testAreaAttackAbility() {
		/* entity attaque victimA
		 * Cette attaque est écoutée par l'abilité AreaAttackAbility,
		 * qui s'occupe d'appliquer les dégats de zone autour de victimA
		 */
		entity.attack(victimA);
		double damages = entity.stat(Attribute.DMG);
		assertEquals(victimA.stat(Attribute.HP) - damages, victimA.getHp());
		assertEquals(victimB.stat(Attribute.HP) - damages, victimB.getHp());
		assertEquals(victimC.stat(Attribute.HP) - damages, victimC.getHp());
		assertEquals(victimD.stat(Attribute.HP) - damages, victimD.getHp());
		// les entités dans la zone perdent "damages" points de vie
		assertEquals(victimE.stat(Attribute.HP), victimE.getHp());
		assertEquals(victimF.stat(Attribute.HP), victimF.getHp());
		assertEquals(victimG.stat(Attribute.HP), victimG.getHp());
		// les entités ennemies hors de la zone ne perdent pas de points de vie
		assertEquals(entityAlly.stat(Attribute.HP), entityAlly.getHp());
		// entityAlly est bien dans la zone, mais n'a pas été touché
		
	}

}
