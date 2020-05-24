package fr.helmdefense.model.actions.game;

import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.level.Wave;

/**
 * Action triggered when the wave change.
 * 
 * @author	indyteo
 * @see		GameAction
 */
public class GameNewWaveAction extends GameAction {
	private Wave oldWave;
	private Wave newWave;
	
	public GameNewWaveAction(Level lvl, Wave oldWave, Wave newWave) {
		super(lvl);
		this.oldWave = oldWave;
		this.newWave = newWave;
	}
	
	/**
	 * Return the wave that just ended.
	 * 
	 * @return	The {@link Wave} that just ended, {@code null}
	 * 			if {@link #getNewWave()} is the first wave.
	 */
	public Wave getOldWave() {
		return this.oldWave;
	}
	
	/**
	 * Return the wave that just began.
	 * 
	 * @return	The {@link Wave} that just began, {@code null}
	 * 			if {@link #getOldWave()} was the last wave.
	 */
	public Wave getNewWave() {
		return this.newWave;
	}
}