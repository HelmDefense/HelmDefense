package fr.helmdefense.model.entities.utils;

import java.util.List;
import java.util.function.BiFunction;

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
	
	public double apply(double base) {
		return this.op.calc(base, this.val);
	}
	
	public static double calculate(double base, Attribute attr, List<AttributeModifier> modifiers) {
		double result = base;
		for (AttributeModifier modifier : modifiers)
			if (modifier.getAttr() == attr)
				result += modifier.apply(base);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "AttributeModifier [id=" + id + ", attr=" + attr + ", op=" + op + ", val=" + val + "]";
	}

	public enum Operation {
		ADD((b, v) -> v),
		MULTIPLY((b, v) -> b * v);
		
		private BiFunction<Double, Double, Double> op;
		
		private Operation(BiFunction<Double, Double, Double> op) {
			this.op = op;
		}
		
		public double calc(double base, double value) {
			return this.op.apply(base, value);
		}
	}
}