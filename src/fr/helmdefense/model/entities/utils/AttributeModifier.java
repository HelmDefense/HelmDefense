package fr.helmdefense.model.entities.utils;

import java.util.List;

public class AttributeModifier {
	private int id;
	private Attribute attr;
	private Operation op;
	private double val;
	
	private static int ids = 0;
	
	public AttributeModifier(Attribute attr, Operation op, double val) {
		this.id = ++ids;
		this.attr = attr;
		this.op = op;
		this.val = val;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Attribute getAttr() {
		return this.attr;
	}
	
	public Operation getOp() {
		return this.op;
	}
	
	public double getVal() {
		return this.val;
	}
	
	public static double calculate(double base, Attribute attr, List<AttributeModifier> modifiers) {
		double result = base;
		for (AttributeModifier modifier : modifiers)
			if (modifier.getAttr() == attr)
				result += modifier.getVal() * (modifier.getOp() == Operation.MULTIPLY ? base : 1);
		
		return result;
	}
	
	public enum Operation {
		ADD,
		MULTIPLY;
	}
}