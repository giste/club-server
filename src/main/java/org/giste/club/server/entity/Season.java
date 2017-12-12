package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.giste.spring.data.entity.BaseEntity;

/**
 * Entity for a season. A season is anchored to the year it begins. The season
 * with greatest year is the current one.
 * 
 * @author Giste
 */
@Entity
public class Season extends BaseEntity {

	// Year when the season begins.
	@Column(unique = true)
	private Integer year;

	/**
	 * Constructor without parameters for JPA.
	 */
	protected Season() {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Season other = (Season) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
