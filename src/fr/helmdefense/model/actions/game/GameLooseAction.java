package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;

/**
 * Action triggered when a game is lost.
 * 
 * @author	indyteo
 * @see		GameAction
 */
public class GameLooseAction extends GameAction {
	public GameLooseAction(Level lvl) {
		super(lvl);
	}
}