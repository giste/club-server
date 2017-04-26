package org.giste.club.server.service.exception;

/**
 * Exception to be thrown when the club with the requested identifier can't be found.
 * 
 * @author Giste
 */
public class ClubNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = -2989138023595002294L;

	/**
	 * Constructs the exception with the requested club identifier as a parameter.
	 * 
	 * @param id The requested club identifier.
	 */
	public ClubNotFoundException(long id) {
		super(id);
	}
}
