package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.defender.Defender;
import fr.helmdefense.model.entities.utils.Statistic;

public class Gimli extends Defender {

	public Gimli(Statistic stats) {
		super(0, 0, stats);
	}
	
	public Gimli() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}
}
