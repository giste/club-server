package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.util.service.BaseService;
import org.giste.util.service.exception.EntityNotFoundException;

/**
 * Service for Season entities.
 * 
 * @author Giste
 */
public interface SeasonService extends BaseService<SeasonDto> {

	/**
	 * Finds the current season, the one with most recent year.
	 * 
	 * @return The current season.
	 * @throws EntityNotFoundException If there is no season defined.
	 */
	SeasonDto findCurrent() throws EntityNotFoundException;
	
	/**
	 * Finds a season by its year.
	 * 
	 * @param year Year in which the looked up season begins. 
	 * @return The season that begins in the indicated year.
	 * @throws EntityNotFoundException If no season is found for that year.
	 */
	SeasonDto findByYear(Integer year) throws EntityNotFoundException;
}
