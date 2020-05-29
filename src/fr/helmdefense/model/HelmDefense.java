package fr.helmdefense.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class HelmDefense {
	private ReadOnlyIntegerWrapper starsProperty;
	
	public HelmDefense() {
		this.starsProperty = new ReadOnlyIntegerWrapper();
	}
	
	public final int getStars() {
		return this.starsProperty.get();
	}
	
	public final ReadOnlyIntegerProperty starsProperty() {
		return this.starsProperty.getReadOnlyProperty();
	}
}