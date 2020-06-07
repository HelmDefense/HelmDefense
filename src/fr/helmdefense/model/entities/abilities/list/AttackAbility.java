package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public abstract class AttackAbility extends Ability {
    private long lastAtk;
    protected LivingEntity entity;

    public AttackAbility(Tier unlock, Tier.Specification tierSpecification) {
        super(unlock, tierSpecification);
        this.lastAtk = 0;
    }

    @ActionHandler
    public void onSpawn(EntitySpawnAction action) {
        this.entity = (LivingEntity) action.getEntity();
        this.init();
    }

    @ActionHandler
    public void onTick(GameTickAction action) {
        if (this.entity == null)
            return;

        LivingEntity enemy = this.getClosestEnemy();

        if (enemy != null
                && action.getTicks() - this.lastAtk > 10 / this.entity.stat(Attribute.ATK_SPD)) {
        	if (this.canAttack(enemy)) {
	            this.lastAtk = action.getTicks();
	            this.attack(enemy);
        	}
        }
    }

    private LivingEntity getClosestEnemy() {
        LivingEntity closest = null, testing;
        boolean taunt = false;
        double dMax = Double.MAX_VALUE, d;
        for (Entity entity : this.entity.getLevel().getEntities()) {
            if (entity instanceof LivingEntity
            		&& this.entity.isEnemy(testing = (LivingEntity) entity)
                    && (d = entity.getLoc().distance(this.entity.getLoc())) < dMax
                    && (! taunt || testing.testFlags(LivingEntity.TAUNT))) {
                dMax = d;
                closest = testing;
                taunt = testing.testFlags(LivingEntity.TAUNT);
            }
        }
        return closest;
    }

    protected abstract void attack(LivingEntity enemy);
    
    protected abstract boolean canAttack(LivingEntity enemy);

    protected void init() {}
}