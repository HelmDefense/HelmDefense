package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.defender.Defender;
import fr.helmdefense.model.entities.utils.Statistic;

public class Aragorn extends Defender {

	public Aragorn(Statistic stats) {
		super(0, 0, stats);
	}
	
	public Aragorn() {
		super(0, 0, 1000, 200, 1, 1, 1, 0);
	}
}
