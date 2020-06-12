package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;

/**
 * Action triggered when a game is won.
 * 
 * @author	indyteo
 * @see		GameAction
 */
public class GameWinAction extends GameAction {
	public GameWinAction(Level lvl) {
		super(lvl);
	}
}