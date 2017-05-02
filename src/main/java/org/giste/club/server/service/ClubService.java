package org.giste.club.server.service;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.exception.EntityNotFoundException;

/**
 * Interface for Club Service.
 * 
 * @author Giste
 */
public interface ClubService extends CrudeService<ClubDto> {

	/**
	 * Finds a club given its acronym.
	 * 
	 * @param acronym The acronym of the club to retrieve.
	 * @return {@link ClubDto} of the retrieved club.
	 * @throws EntityNotFoundException If the club can't be found.
	 */
	ClubDto findByAcronym(String acronym) throws EntityNotFoundException;
}
