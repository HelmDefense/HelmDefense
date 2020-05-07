package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Statistic;

public class HumanWarrior extends Defender {

	public HumanWarrior(Statistic stats) {
		super(0, 0, stats);
	}
	
	public HumanWarrior() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}
	
	

}
