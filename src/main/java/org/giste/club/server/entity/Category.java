package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.giste.spring.server.entity.BaseEntity;

/**
 * Represents a category for a team. A category has a name and has a minimum and
 * a maximum age for its players. Only the birth year is taken into account for
 * calculating if a player belongs to a category. A category can be mixed (males
 * and females) or not.
 * 
 * @author Giste
 */
@Entity
public class Category extends BaseEntity {

	private static final long serialVersionUID = -1844344884823439333L;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "min_age", nullable = false)
	private int minAge;

	@Column(name = "max_age", nullable = false)
	private int maxAge;

	@Column(name = "mixed", nullable = false)
	private boolean mixed;

	/**
	 * Constructs a new empty category.
	 */
	public Category() {
		super();
	}

	/**
	 * Construct a category with given values.
	 * 
	 * @param id The category identifier.
	 * @param name The category name.
	 * @param minAge The minimum age for this category.
	 * @param maxAge The maximum age for this category.
	 * @param mixed <code>true</code> if it's a mixed category and
	 *            <code>false</code> otherwise.
	 */
	public Category(Long id, String name, int minAge, int maxAge, boolean mixed) {
		super(id);
		this.name = name;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.mixed = mixed;
	}

	/**
	 * Gets the category name.
	 * 
	 * @return The category name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the category name.
	 * 
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the minimum age for players of this category.
	 * 
	 * @return The minimum age for players of this category.
	 */
	public int getMinAge() {
		return minAge;
	}

	/**
	 * Sets the minimum age for players of this category.
	 * 
	 * @param minAge The minimum age for players of this category.
	 */
	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	/**
	 * Gets the maximum age for players of this category.
	 * 
	 * @return The maximum age for players of this category.
	 */
	public int getMaxAge() {
		return maxAge;
	}

	/**
	 * Sets the maximum age for players of this category.
	 * 
	 * @param maxAge The maximum age for players of this category.
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * Gets if the category is mixed.
	 * 
	 * @return <code>true</code> if it's a mixed category and
	 *            <code>false</code> otherwise.
	 */
	public boolean isMixed() {
		return mixed;
	}

	/**
	 * Gets if the category is mixed.
	 * 
	 * @param mixed Indicator of mixed category.
	 */
	public void setMixed(boolean mixed) {
		this.mixed = mixed;
	}

}
