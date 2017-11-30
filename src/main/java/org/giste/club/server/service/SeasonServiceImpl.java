package org.giste.club.server.service;

import java.util.Optional;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.server.service.CrudServiceImpl;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

@Service
public class SeasonServiceImpl extends CrudServiceImpl<SeasonDto, Season> implements SeasonService {

	private LocaleMessage localeMessage;

	public SeasonServiceImpl(SeasonRepository repository, LocaleMessage localeMessage) {
		super(repository);
		this.localeMessage = localeMessage;
	}

	@Override
	public SeasonDto findCurrent() throws EntityNotFoundException {
		Optional<Season> season = ((SeasonRepository) getRepository()).findFirstByOrderByYearDesc();

		if (season.isPresent()) {
			return getDtoFromEntity(season.get());
		} else {
			final String code = localeMessage.getMessage("error.club.server.baseError")
					+ localeMessage.getMessage("error.entityNotFound.season.current.code");
			final String message = localeMessage.getMessage("error.entityNotFound.season.current.message");
			final String developerInfo = localeMessage.getMessage("error.entityNotFound.season.current.developerInfo");

			throw new EntityNotFoundException(true, code, message, developerInfo);
		}
	}

	@Override
	protected Season getEntityFromDto(SeasonDto dto) {
		return new Season(dto.getId(), dto.getYear());
	}

	@Override
	protected SeasonDto getDtoFromEntity(Season entity) {
		return new SeasonDto(entity.getId(), entity.getYear());
	}

	@Override
	protected Season updateEntityFromDto(Season entity, SeasonDto dto) {
		entity.setYear(dto.getYear());

		return entity;
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.season.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.season.id.message", params);
		final String developerInfo = localeMessage.getMessage("error.entityNotFound.season.id.developerInfo");

		return new EntityNotFoundException(id, code, message, developerInfo);
	}

	@Override
	public SeasonDto update(SeasonDto dto) throws EntityNotFoundException {
		// Method not allowed on seasons, throw exception
		throw new RuntimeException("Method not allowed");
	}

	
}
