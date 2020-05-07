package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.attacker.boss.TwoTowers;

public class MordorTower extends TwoTowers {

	public MordorTower(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange,
			double shootRange) {
		super(x, y, 75000, 0, 0, 0, 0, 0);
	}
}
