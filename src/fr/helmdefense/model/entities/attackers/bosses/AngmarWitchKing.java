package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public class AngmarWitchKing extends Boss {
		
	public AngmarWitchKing(Location location) {
		super(location);
	}
	
	public AngmarWitchKing(int x, int y) {
		this(new Location(x, y));
	}
}
