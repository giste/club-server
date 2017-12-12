package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.spring.rest.server.service.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Implements the mapper between entity and DTO for a Season.
 * 
 * @author Giste
 */
@Component
public class SeasonEntityMapper implements EntityMapper<Season, SeasonDto> {

	@Override
	public SeasonDto toDto(Season entity) {
		return new SeasonDto(entity.getId(), entity.getYear());
	}

	@Override
	public Season fromDto(SeasonDto dto) {
		return new Season(dto.getId(), dto.getYear());
	}

	@Override
	public Season updateFromDto(Season entity, SeasonDto dto) {
		entity.setYear(dto.getYear());
		
		return entity;
	}

}
