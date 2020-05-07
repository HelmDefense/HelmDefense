package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Statistic;

public class Archer extends Defender {
	
	public Archer(Statistic stats) {
		super(0, 0, stats);
	}
	
	public Archer() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}

}
