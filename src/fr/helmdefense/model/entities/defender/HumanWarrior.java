package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Statistic;

public class HumanWarrior extends Entity {
	private int cout;

	public HumanWarrior(int x, int y, Statistic stats) {
		super(x, y, stats);
		this.cout = 0;
	}
	
	public HumanWarrior(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
		this.cout = 0;
	}
	
	

}
