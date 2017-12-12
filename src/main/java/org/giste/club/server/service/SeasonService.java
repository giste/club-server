package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.rest.server.service.CrudService;
import org.giste.spring.rest.server.service.exception.EntityNotFoundException;

public interface SeasonService extends CrudService<SeasonDto> {

	SeasonDto findCurrent() throws EntityNotFoundException;
}
