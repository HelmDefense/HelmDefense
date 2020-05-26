package fr.helmdefense.model.entities.utils;

public enum Tier {
	TIER_1(1),
	TIER_2(2),
	TIER_3(3);
	
	private int n;
	
	private Tier(int n) {
		this.n = n;
	}
	
	public Tier next() {
		boolean found = false;
		for(Tier t : values()) {
			if(found)
				return t;
			if(t == this)
				found = true;
		}
		return null;
	}
	
	public static Tier number(int n) {
		if(n < 1 || n > 3)
			return null;
		return valueOf("TIER_" + n);
	}
	
	public String toString() {
		return "Tier " + this.n;
	}
}