package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.level.Wave;

public class GameNewWaveAction extends GameAction {
	private Wave oldWave;
	private Wave newWave;
	
	public GameNewWaveAction(Level lvl, Wave oldWave, Wave newWave) {
		super(lvl);
		this.oldWave = oldWave;
		this.newWave = newWave;
	}
	
	public Wave getOldWave() {
		return this.oldWave;
	}
	
	public Wave getNewWave() {
		return this.newWave;
	}
}