package org.giste.club.server.service;

import java.util.Optional;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.data.repository.CrudRepository;
import org.giste.spring.rest.server.service.CrudServiceImpl;
import org.giste.spring.rest.server.service.EntityMapper;
import org.giste.spring.rest.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

@Service
public class SeasonServiceImpl extends CrudServiceImpl<SeasonDto, Season> implements SeasonService {

	private LocaleMessage localeMessage;
	
	public SeasonServiceImpl(CrudRepository<Season> repository, LocaleMessage localeMessage, EntityMapper<Season, SeasonDto> mapper) {
		super(repository, mapper);
		this.localeMessage = localeMessage;
	}

	@Override
	public SeasonDto findCurrent() throws EntityNotFoundException {
		
		Optional<Season> season = getRepository().findFirstByOrderByYearDesc();
		
		if (!season.isPresent()) {
			final String code = localeMessage.getMessage("error.club.server.baseError")
					+ localeMessage.getMessage("error.entityNotFound.season.current.code");
			final String message = localeMessage.getMessage("error.entityNotFound.season.current.message");
			final String developerInfo = localeMessage.getMessage("error.entityNotFound.season.current.developerInfo");

			throw new EntityNotFoundException(null, code, message, developerInfo);
		}
		
		return getEntityMapper().toDto(season.get());
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
	protected SeasonRepository getRepository() {
		return (SeasonRepository) super.getRepository();
	}

}
