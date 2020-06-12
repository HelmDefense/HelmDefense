package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.level.Level;

public class GameAttackerPassedAction extends GameAction {
	private LivingEntity attacker;
	
	public GameAttackerPassedAction(Level lvl, LivingEntity attacker) {
		super(lvl);
		this.attacker = attacker;
	}
	
	public LivingEntity getAttacker() {
		return this.attacker;
	}
}