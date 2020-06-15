package fr.helmdefense.model.entities.projectile;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.yaml.YAMLLoader;

class ProjectileTest {
	private static Level level;
	private static LivingEntity entity;
	private static LivingEntity victim;
	private static Projectile projectile;
	private static int vitesse;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
		entity = new LivingEntity(LivingEntityType.ARCHER, 5, 5);
		victim = new LivingEntity(LivingEntityType.ORC_WARRIOR, 7, 5);
		vitesse = 10; // case / sec
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), vitesse);
		entity.spawn(level);
		victim.spawn(level);
		projectile.spawn(level);
		projectile.addAbilities();
	}

	/* Projectile.move() est déclenchée à chaque tick pour la tester, on utilise la GameLoop du level
	 * que l'on fait avancer manuellement avec la méthode step()
	 */
	@Test
	void testMove() {
		/* Cas : projectile a une vitesse de 10 cases / sec, il se déplace donc d'une case / tick
		 * le projectile part de (5, 5) qui est la Location de sa source,en direction de victim, en (7, 5)
		 * la loop défile d'une tick, on se déplace donc sur l'axe O(x)
		 */
		level.getGameloop().step();
		assertEquals(6, projectile.getLoc().getX());
		assertEquals(5, projectile.getLoc().getY());
		// Cas : idem que le précedent, mais cette fois sur l'axe O(y) et projectile va en direction de victim (10,15)
		victim.teleport(5, 7);
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), vitesse);
		projectile.spawn(level);
		level.getGameloop().step();
		assertEquals(5, projectile.getLoc().getX());
		assertEquals(6, projectile.getLoc().getY());
		/* Cas : projectile part de (4, 9) en direction de victim (7, 1)
		 * Déplacement en diagonale
		 * Cette fois ci on utilise assertEquals(double attendue, double valeurActuelle, double delta) car la valeurActuelle 
		 * est décimale
		*/
		entity.teleport(4, 9);
		victim.teleport(7, 1);
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), vitesse);
		/* vecteur Source -> victim = (-3,8)
		 * norme du vecteur = sqrt((-3)^2 +8^2) = sqrt(73)
		 * vecteur unitaire = (-3 / sqrt(73), 8 / sqrt(73))  = (-0.3511 ; 0.9363);
		 */
		projectile.spawn(level);
		level.getGameloop().step();
		assertEquals(-0.3511, entity.getLoc().getX() - projectile.getLoc().getX(), 0.0001);
		assertEquals(0.9363, entity.getLoc().getY() - projectile.getLoc().getY(), 0.0001);	
	}

	@Test
	void testSetDeleteOnHit() {
		// true par défaut
		if (! projectile.isDeleteOnHit())
			fail("Erreur, la flêche n'a pas été initialisée correctement");

		// Cas : définition de deleteOnHit à false
		projectile.setDeleteOnHit(false);
		assertFalse(projectile.isDeleteOnHit());

		// Cas : définition de deleteOnHit à true
		projectile.setDeleteOnHit(true);
		assertTrue(projectile.isDeleteOnHit());
	}

}
