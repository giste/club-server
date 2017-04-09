package org.giste.club.server.service;

import java.util.List;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;

/**
 * Interface for Club Service.
 * 
 * @author Giste
 */
public interface ClubService {

	/**
	 * Creates a new club.
	 * 
	 * @param club {@link ClubDto} with the values for the new club.
	 * @return {@link ClubDto} with the values of the created club.
	 * @throws DuplicatedClubAcronymException If another club is using the same
	 *             acronym.
	 */
	ClubDto create(ClubDto club) throws DuplicatedClubAcronymException;

	/**
	 * Retrieves one club by its identifier.
	 * 
	 * @param id Identifier of the club looked up.
	 * @return {@link ClubDto} with the values of the found club.
	 * @throws ClubNotFoundException If there is no club with this identifier.
	 */
	ClubDto findById(Long id);

	/**
	 * Retrieves all clubs.
	 * 
	 * @return List populated with {@link ClubDto} for each club.
	 */
	List<ClubDto> findAll();

	/**
	 * Updates the values of one club.
	 * 
	 * @param club {@link ClubDto} with the values of the club to update.
	 * @return {@link ClubDto} with the updated values of the club.
	 * @throws ClubNotFoundException If the club to update does not exist.
	 */
	ClubDto update(ClubDto club) throws DuplicatedClubAcronymException;

	/**
	 * Deletes one club.
	 * 
	 * @param id identifier of the club to delete.
	 * @return {@link ClubDto} with the values of the deleted club.
	 * @throws ClubNotFoundException If the club to delete does not exist.
	 */
	ClubDto deleteById(Long id);
	
	/**
	 * Enables one club in the application.
	 * 
	 * @param id Identifier of the club to be enabled.
	 * @return {@link ClubDto} with the values of the enabled club.
	 * @throws ClubNotFoundException If the club to enable does not exist.
	 */
	ClubDto enable(Long id);

	/**
	 * Disables one club in the application.
	 * 
	 * @param id Identifier of the club to be disabled.
	 * @return {@link ClubDto} with the values of the disabled club.
	 * @throws ClubNotFoundException If the club to disable does not exist.
	 */
	ClubDto disable(Long id);
}
