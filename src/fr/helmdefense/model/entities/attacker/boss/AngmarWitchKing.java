package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class AngmarWitchKing extends Boss {
		
	public AngmarWitchKing(Location location) {
		super(location, "angmar-witch-king");
	}
	
	public AngmarWitchKing(int x, int y) {
		this(new Location(x, y));
	}
}
