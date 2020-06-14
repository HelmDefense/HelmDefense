package fr.helmdefense.model.entities.projectile;

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
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		YAMLLoader.loadEntityData();
		level = YAMLLoader.loadLevel("Classic Testing Level");
		entity = new LivingEntity(LivingEntityType.ARCHER, 10, 10);
		victim = new LivingEntity(LivingEntityType.ORC_WARRIOR, 15, 10);
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), 3);
		entity.spawn(level);
		victim.spawn(level);
		projectile.spawn(level);
	}

	/* Projectile.move() est déclenchée à chaque tick pour la tester, on utilise la GameLoop du level
	 * que l'on fait avancer manuellement avec la méthode step()
	 */
	@Test
	void testMove() {
		/* Cas : projectile a une vitesse de 3 cases / sec
		 * le projectile part de (10, 10) qui est la Location de sa source,en direction de victim, en (15,10)
		 * la loop défile d'une tick, on se déplace donc sur l'axe O(x)
		 */
		level.getGameloop().step();
		assertTrue(10 < projectile.getLoc().getCenterX());
		assertTrue(projectile.getLoc().getY() == 10);
		// Cas : idem que le précedent, mais cette fois sur l'axe O(y) et projectile va en direction de victim2
		victim.teleport(10, 15);
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), 3);
		level.getGameloop().step();
		assertTrue(projectile.getLoc().getX() == 10);
		assertTrue(projectile.getLoc().getY() > 10);
		/* Cas : projectile part de (10,10) en direction de victim3 (15,15)
		 * Déplacement en diagonale
		 */
		victim.teleport(15, 15);
		projectile = new Projectile(ProjectileType.ARROW, entity, victim.getLoc(), 3);
		level.getGameloop().step();
		assertTrue(projectile.getLoc().getX() > 10);
		assertTrue(projectile.getLoc().getY() > 10);
	}

	@Test
	void testSetDeleteOnHit() {
		assertTrue(projectile.isDeleteOnHit()); // true par défaut
		
		// Cas : définition de deleteOnHit à true
		projectile.setDeleteOnHit(true);
		assertTrue(projectile.isDeleteOnHit());
		// Cas : définition de deleteOnHit à false
		projectile.setDeleteOnHit(false);
		assertFalse(projectile.isDeleteOnHit());
	}

}
