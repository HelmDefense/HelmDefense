package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Statistic;

public class ElfWarrior extends Defender {
	
	public ElfWarrior(Statistic stats) {
		super(0, 0, stats);
	}
	
	public ElfWarrior() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}

}
