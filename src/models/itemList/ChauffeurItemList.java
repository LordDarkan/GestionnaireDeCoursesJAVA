package models.itemList;

import javafx.scene.paint.Color;
import models.Chauffeur;

public class ChauffeurItemList {
	private Long id;
	private String name;
	private String firstname;
	private Color color = Color.WHITE;
	
	public ChauffeurItemList(Chauffeur c) {
		id = c.getId();
		name = c.getName();
		firstname = c.getFirstname();
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		// -fx-background-color:#C2FF80;
		return color;
	}

	@Override
	public String toString() {
		return String.format("%s %s", name, firstname);
	}
}
