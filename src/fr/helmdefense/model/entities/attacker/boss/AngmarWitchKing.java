package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class AngmarWitchKing extends Boss {
		
	public AngmarWitchKing(int x, int y, String name) {
		super(new Location(x, y),"angmarwitchking" + name);
	}
	
	public AngmarWitchKing(Location location, String name) {
		super(location, "angmarwitchking"+name);
	}

}
