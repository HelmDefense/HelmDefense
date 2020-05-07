package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.defender.Defender;
import fr.helmdefense.model.entities.utils.Statistic;

public class Legolas extends Defender {

	public Legolas(Statistic stats) {
		super(0, 0, stats);
	}
	
	public Legolas() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}
}
