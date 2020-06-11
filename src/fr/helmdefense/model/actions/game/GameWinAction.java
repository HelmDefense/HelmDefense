package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;

/**
 * Action triggered when a game is won.
 * 
 * @author	indyteo
 * @see		GameAction
 */
public class GameWinAction extends GameAction {
	private int remainingDoors;
	
	public GameWinAction(Level lvl, int remainingDoors) {
		super(lvl);
		this.remainingDoors = remainingDoors;
	}
	
	/**
	 * Return the number of doors that are still alive.
	 * 
	 * @return	The number of doors that are still alive.
	 */
	public int getRemainingDoors() {
		return this.remainingDoors;
	}
}