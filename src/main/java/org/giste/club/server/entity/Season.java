package org.giste.club.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.giste.spring.server.entity.BaseEntity;

/**
 * Entity for a season. A season is anchored to the year it begins. The season
 * with greatest year is the current one.
 * 
 * @author Giste
 */
@Entity
public class Season extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1947801841843398806L;

	// Year when the season begins.
	@Column(unique = true)
	private Integer year;

	/**
	 * Constructor without parameters.
	 */
	public Season() {

	}

	/**
	 * Creates a new season.
	 * 
	 * @param id Identifier of this season.
	 * @param year Year when the season begins.
	 */
	public Season(Long id, Integer year) {
		super(id);
		this.year = year;
	}

	/**
	 * Gets the year this season begins.
	 * 
	 * @return The year this season begins.
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Sets the year this season begins.
	 * 
	 * @param year The year this season begins.
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return String.format("Season [id=%s, year=%s]", id, year);
	}

}
