package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.giste.club.common.dto.Gender;
import org.giste.spring.server.entity.NonRemovableEntity;

/**
 * Represents a player of the club.
 * 
 * @author Giste
 */
@Entity
public class Player extends NonRemovableEntity {

	private static final long serialVersionUID = -4101436904122552862L;

	@Column(name = "name", nullable = false, length = 40, unique = true)
	private String name;

	@Column(name = "birth_year", nullable = false)
	private Integer birthYear;

	@Column(name = "goalie", nullable = false)
	private boolean goalie;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	/**
	 * Default constructor.
	 */
	public Player() {

	}

	/**
	 * Constructs a new player.
	 * 
	 * @param id Identifier of the player.
	 * @param enabled Indicates if the player is enabled in the application.
	 * @param name Name of the player.
	 * @param birthYear year of the birth of the player. It's used for assign a
	 *            category to it.
	 * @param goalie Indicates if the player is a goalie.
	 * @param gender Gender of the player.
	 */
	public Player(Long id, boolean enabled, String name, Integer birthYear, boolean goalie, Gender gender) {
		super(id, enabled);
		this.name = name;
		this.birthYear = birthYear;
		this.goalie = goalie;
		this.gender = gender;
	}

	/**
	 * Gets the players name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the players name.
	 * 
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the year of birth.
	 * 
	 * @return The year of birth.
	 */
	public Integer getBirthYear() {
		return birthYear;
	}

	/**
	 * Sets the year of birth.
	 * 
	 * @param birthYear The year of birth.
	 */
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	/**
	 * Gets if the player is a goalie.
	 * 
	 * @return <code>true</code> if the player is a goalie or <code>false</code>
	 *         otherwise.
	 */
	public boolean isGoalie() {
		return goalie;
	}

	/**
	 * Gets if the player is a goalie.
	 * 
	 * @param goalie <code>true</code> if the player is a goalie or
	 *            <code>false</code> otherwise.
	 */
	public void setGoalie(boolean goalie) {
		this.goalie = goalie;
	}

	/**
	 * Gets the player's gender.
	 * 
	 * @return The gender of the player.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the player's gender.
	 * 
	 * @param gender The gender of the player.
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
