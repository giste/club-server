package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Entity for a club.
 * 
 * @author Giste
 */
@Entity
public class Club extends NonRemovableEntity {

	private static final long serialVersionUID = 8455773760954965058L;

	private String name;

	@Column(unique = true)
	private String acronym;

	public Club() {

	}

	/**
	 * Creates a new club.
	 * 
	 * @param id Identifier of this club.
	 * @param name Name of this club.
	 * @param acronym Acronym of this club.
	 * @param enabled Indicates if this club is enabled or disabled.
	 */
	public Club(long id, String name, String acronym, boolean enabled) {
		super(id, enabled);
		this.name = name;
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

}
