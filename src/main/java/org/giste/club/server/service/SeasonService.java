package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.data.service.BaseService;
import org.giste.spring.data.service.exception.EntityNotFoundException;

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
}
