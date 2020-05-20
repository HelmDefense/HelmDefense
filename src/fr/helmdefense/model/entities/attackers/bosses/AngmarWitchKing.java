package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.coords.Location;

public class AngmarWitchKing extends Boss {
	private Nazgul nazgul;
		
	public AngmarWitchKing(Location location) {
		super(location);
		this.nazgul = new Nazgul(location);
	}
	
	public AngmarWitchKing(double x, double y) {
		this(new Location(x, y));
	}
	
	public Nazgul getNazgul() {
		return this.nazgul.isAlive() ? this.nazgul : null;
	}
}
