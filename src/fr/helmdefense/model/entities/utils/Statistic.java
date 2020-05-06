package fr.helmdefense.model.entities.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Statistic {
	private IntegerProperty hpProperty;
	private IntegerProperty dmgProperty;
	private DoubleProperty mvtSpdProperty;
	private DoubleProperty atkSpdProperty;
	private DoubleProperty atkRangeProperty;
	private DoubleProperty shootRangeProperty;
	
	public Statistic(int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		this.hpProperty = new SimpleIntegerProperty(hp);
		this.dmgProperty = new SimpleIntegerProperty(dmg);
		this.mvtSpdProperty = new SimpleDoubleProperty(mvtSpd);
		this.atkSpdProperty = new SimpleDoubleProperty(atkSpd);
		this.atkRangeProperty = new SimpleDoubleProperty(atkRange);
		this.shootRangeProperty = new SimpleDoubleProperty(shootRange);
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public final int getDmg() {
		return this.dmgProperty.get();
	}
	
	public final double getMvtSpd() {
		return this.mvtSpdProperty.get();
	}
	
	public final double getAtkSpd() {
		return this.atkSpdProperty.get();
	}
	
	public final double getAtkRange() {
		return this.atkRangeProperty.get();
	}
	
	public final double getShootRange() {
		return this.shootRangeProperty.get();
	}
	
	public final void bindHp(Property<? super Number> observable) {
		observable.bind(this.hpProperty);
	}
	
	public final void bindDmg(Property<? super Number> observable) {
		observable.bind(this.dmgProperty);
	}
	
	public final void bindMvtSpd(Property<? super Number> observable) {
		observable.bind(this.mvtSpdProperty);
	}
	
	public final void bindAtkSpd(Property<? super Number> observable) {
		observable.bind(this.atkSpdProperty);
	}
	
	public final void bindAtkRange(Property<? super Number> observable) {
		observable.bind(this.atkRangeProperty);
	}
	
	public final void bindShootRange(Property<? super Number> observable) {
		observable.bind(this.shootRangeProperty);
	}
}