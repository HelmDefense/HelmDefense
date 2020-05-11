package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;

public class GameTickAction extends GameAction {
	private long ticks;
	
	public GameTickAction(Level lvl, long ticks) {
		super(lvl);
		this.ticks = ticks;
	}
	
	public long getTicks() {
		return this.ticks;
	}
}