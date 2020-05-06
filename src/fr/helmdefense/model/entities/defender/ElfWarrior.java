package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Statistic;

public class ElfWarrior extends Entity {
	
	public ElfWarrior(int x, int y, Statistic stats) {
		super(x, y, stats);
	}
	
	public ElfWarrior(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
	}

}
