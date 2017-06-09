package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.giste.spring.server.entity.NonRemovableEntity;

/**
 * Entity for a club. A club has a name and an acronym.
 * 
 * @author Giste
 */
@Entity
public class Club extends NonRemovableEntity {

	private static final long serialVersionUID = 8455773760954965058L;

	private String name;

	@Column(unique = true)
	private String acronym;

	/**
	 * Creates a new club without values.
	 */
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

	/**
	 * Gets the name of the club.
	 * 
	 * @return The name of the club.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the club.
	 * 
	 * @param name The name of the club.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the acronym of the club.
	 * 
	 * @return The acronym of the club.
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * Sets the acronym of the club.
	 * 
	 * @param acronym The acronym of the club.
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

}
