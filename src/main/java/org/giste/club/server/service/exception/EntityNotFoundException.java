package org.giste.club.server.service.exception;

/**
 * Exception thrown when an entity is looked up by its identifier and it's not found.
 * 
 * @author Giste
 */
public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 629573734793213907L;
	
	private long id;

	/**
	 * Constructs a new EntityNotFoundExeption.
	 * 
	 * @param id Identifier of the entity not found.
	 */
	public EntityNotFoundException(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
