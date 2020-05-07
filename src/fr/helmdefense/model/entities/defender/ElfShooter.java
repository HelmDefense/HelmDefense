package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Statistic;

public class ElfShooter extends Defender {
	
	public ElfShooter(Statistic stats) {
		super(0, 0, stats);
	}
	
	public ElfShooter() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}

}
