package org.giste.club.server.service.exception;

/**
 * Exception to be thrown when the club with the requested identifier can't be found.
 * 
 * @author Giste
 */
public class ClubNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2989138023595002294L;

	private final long idNotFound;
	private static final String message = "There is no club with id: %s";

	/**
	 * Constructs the exception with the requested club identifier as a parameter.
	 * 
	 * @param id The requested club identifier.
	 */
	public ClubNotFoundException(long id) {
		super(String.format(message, id));
		this.idNotFound = id;
	}

	/**
	 * Gets the club identifier that originated this exception.
	 * 
	 * @return The identifier of the club not found.
	 */
	public long getIdNotFound() {
		return idNotFound;
	}

}
