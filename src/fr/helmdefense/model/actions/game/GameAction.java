package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.level.Level;

/**
 * Super-type for all level-related actions.
 * 
 * @author	indyteo
 * @see		Action
 */
public abstract class GameAction extends Action {
	private Level lvl;
	
	public GameAction(Level lvl) {
		this.lvl = lvl;
	}
	
	/**
	 * Return the level involved in this action.
	 * 
	 * @return	The {@link Level} involved in this
	 * 			action.
	 */
	public Level getLvl() {
		return this.lvl;
	}
}