package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Statistic;

public class Catapult extends Defender {
	
	public Catapult(Statistic stats) {
		super(0, 0, stats);
	}
	
	public Catapult() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}

}
