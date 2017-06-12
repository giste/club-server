package org.giste.club.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.giste.club.common.dto.Role;
import org.giste.spring.server.entity.BaseEntity;

/**
 * Represents a user of a club.
 * 
 * @author Giste
 */
@Entity
public class User extends BaseEntity {

	private static final long serialVersionUID = 3596825783606418196L;

	@Column(unique = true)
	private String email;

	private String passwordHash;

	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Default constructor.
	 */
	public User() {
		super();
	}

	/**
	 * Constructs a user with its identifier.
	 * 
	 * @param id The identifier of the user.
	 * @param email The user email.
	 * @param passwordHash The user password.
	 * @param role The user role.
	 */
	public User(Long id, String email, String passwordHash, Role role) {
		super(id);
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	/**
	 * Gets the user email.
	 * 
	 * @return The user email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user email.
	 * 
	 * @param email The user email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password hash for the user.
	 * 
	 * @return The password hash.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the password hash for this user.
	 * 
	 * @param passwordHash The password hash.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the role of this user.
	 * 
	 * @return The role of this user.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role of this user.
	 * 
	 * @param role The role of this user.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}