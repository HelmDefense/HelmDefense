package fr.helmdefense.model.map;

import java.util.ArrayList;
import java.util.List;

import fr.helmdefense.model.entities.utils.coords.Location;

public enum Dir {
	S(0b0001, 0, 1),
	W(0b0010, -1, 0),
	N(0b0100, 0, -1),
	E(0b1000, 1, 0);
	
	private int v;
	private int dx;
	private int dy;
	
	private Dir(int v, int dx, int dy) {
		this.v = v;
		this.dx = dx;
		this.dy = dy;
	}
	
	public Location n(Location loc) {
		return loc.copy().add(this.dx, this.dy);
	}
	
	public int v() {
		return this.v;
	}
	
	public static List<Dir> list(int paths) {
		List<Dir> list = new ArrayList<Dir>();
		for (Dir d : values())
			if ((paths & d.v()) != 0)
				list.add(d);
		
		return list;
	}
}