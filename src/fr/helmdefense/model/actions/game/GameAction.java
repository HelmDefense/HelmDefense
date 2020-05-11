package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.level.Level;

public abstract class GameAction extends Action {
	private Level lvl;
	
	public GameAction(Level lvl) {
		this.lvl = lvl;
	}
	
	public Level getLvl() {
		return this.lvl;
	}
}