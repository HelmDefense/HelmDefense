package fr.helmdefense.model.entities.utils;

import java.util.List;
import java.util.function.BiFunction;

public class AttributeModifier {
	private int id;
	private String name;
	private Attribute attr;
	private Operation op;
	private double val;
	private long start;
	private int duration;
	
	private static int ids = 0;
	
	public AttributeModifier(Attribute attr, Operation op, double val) {
		this(null, attr, op, val);
	}
	
	public AttributeModifier(String name, Attribute attr, Operation op, double val) {
		this(name, attr, op, val, -1, -1);
	}
	
	public AttributeModifier(Attribute attr, Operation op, double val, long start, int duration) {
		this(null, attr, op, val, start, duration);
	}
	
	public AttributeModifier(String name, Attribute attr, Operation op, double val, long start, int duration) {
		this.id = ++ids;
		if (name == null)
			this.name = "M" + this.id;
		else
			this.name = name;
		this.attr = attr;
		this.op = op;
		this.val = val;
		this.start = start;
		this.duration = duration;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
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
	
	public long getStart() {
		return this.start;
	}
	
	public void setStart(long start) {
		this.start = start;
	}
	
	public int getDuration() {
		return this.duration;
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
		return "AttributeModifier [id=" + id + ", name=" + name + ", attr=" + attr + ", op=" + op + ", val=" + val
				+ ", start=" + start + ", duration=" + duration + "]";
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