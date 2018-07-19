package models;

import javax.persistence.*;

@Entity
@Table(name = "Permanents")
public class Permanent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	public String getId() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
