package fr.helmdefense.model.entities.utils;

public enum Tier {
	TIER_1(1, false),
	TIER_2(2, false),
	TIER_3(3, true);
	
	private int n;
	private boolean specification;
	
	public static final Tier DEFAULT = TIER_1;
	
	private Tier(int n, boolean specification) {
		this.n = n;
		this.specification = specification;
	}
	
	public Tier previous() {
		Tier previous = null;
		for (Tier t : values()) {
			if (t == this)
				return previous;
			previous = t;
		}
		return null;
	}
	
	public Tier next() {
		boolean found = false;
		for (Tier t : values()) {
			if (found)
				return t;
			if (t == this)
				found = true;
		}
		return null;
	}
	
	public boolean hasSpecification() {
		return this.specification;
	}
	
	public static Tier number(int n) {
		if (n < 1 || n > 3)
			return null;
		return valueOf("TIER_" + n);
	}
	
	@Override
	public String toString() {
		return "Tier " + this.n;
	}
	
	public static enum Specification {
		NO_SPECIFICATION,
		A,
		B;
		
		public static final Specification DEFAULT = NO_SPECIFICATION;
	}
}