package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.server.service.CrudService;
import org.giste.spring.server.service.exception.EntityNotFoundException;

public interface SeasonService extends CrudService<SeasonDto> {

	/**
	 * Find the current season.
	 * 
	 * @return The current season.
	 * @throws EntityNotFoundException if there is no current season.
	 */
	SeasonDto findCurrent() throws EntityNotFoundException;
	
}
