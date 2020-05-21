package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Tier;

public abstract class AttackAbility extends Ability {

    private long lastAtk;
    protected LivingEntity entity;
    protected double range;

    public AttackAbility(Tier unlock) {
        super(unlock);
        this.lastAtk = 0;
        this.range = 0;
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
                && enemy.getLoc().distance(this.entity.getLoc()) < range + 0.5
                && action.getTicks() - this.lastAtk > 10 / this.entity.data().getStats(Tier.TIER_1).getAtkSpd()) {
            this.lastAtk = action.getTicks();
            this.attack(enemy);
        }
    }

    private LivingEntity getClosestEnemy() {
        LivingEntity closest = null;
        double dMax = Double.MAX_VALUE, d;
        for (Entity entity : this.entity.getLevel().getEntities()) {
            if ((this.entity instanceof Defender ? entity instanceof Attacker : entity instanceof Defender)
                    && (d = entity.getLoc().distance(this.entity.getLoc())) < dMax) {
                dMax = d;
                closest = (LivingEntity) entity;
            }
        }
        return closest;
    }

    protected abstract void attack(LivingEntity enemy);

    protected void init() {}

}
