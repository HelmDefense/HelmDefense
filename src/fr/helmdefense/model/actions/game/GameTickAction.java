package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;

/**
 * Action triggered every game tick.
 * 
 * @author	indyteo
 * @see		GameAction
 */
public class GameTickAction extends GameAction {
	private long ticks;
	
	public GameTickAction(Level lvl, long ticks) {
		super(lvl);
		this.ticks = ticks;
	}
	
	/**
	 * Return the number of ticks since the game started.
	 * 
	 * @return	The number of ticks since the game started.
	 */
	public long getTicks() {
		return this.ticks;
	}
}