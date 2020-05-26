package fr.helmdefense.model.entities.utils;

import java.util.function.Function;

public enum Attribute {
	HP(s -> (double) s.getHp()),
	DMG(s -> (double) s.getDmg()),
	MVT_SPD(s -> s.getMvtSpd()),
	ATK_SPD(s -> s.getAtkSpd()),
	ATK_RANGE(s -> s.getAtkRange()),
	SHOOT_RANGE(s -> s.getShootRange());
	
	private Function<Statistic, Double> statExtractor;
	
	private Attribute(Function<Statistic, Double> statExtractor) {
		this.statExtractor = statExtractor;
	}
	
	public double get(Statistic stat) {
		return this.statExtractor.apply(stat);
	}
}