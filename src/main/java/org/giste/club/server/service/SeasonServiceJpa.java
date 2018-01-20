package org.giste.club.server.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.data.repository.CrudRepository;
import org.giste.spring.data.service.BaseServiceJpa;
import org.giste.spring.data.service.EntityMapper;
import org.giste.spring.util.locale.LocaleMessage;
import org.giste.util.service.exception.DuplicatedPropertyException;
import org.giste.util.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SeasonService}.
 * 
 * @author Giste
 */
@Service
public class SeasonServiceJpa extends BaseServiceJpa<SeasonDto, Season> implements SeasonService {

	private LocaleMessage localeMessage;

	/**
	 * Creates the service to manage seasons.
	 * 
	 * @param repository {@link SeasonRepository} for persist seasons.
	 * @param localeMessage Bean for load localized messages.
	 * @param mapper Helper class for mapping between season entity and DTO.
	 */
	public SeasonServiceJpa(CrudRepository<Season> repository, LocaleMessage localeMessage,
			EntityMapper<Season, SeasonDto> mapper) {
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

			throw new EntityNotFoundException(null, code, message);
		}

		return getEntityMapper().toDto(season.get());
	}

	@Override
	public SeasonDto findByYear(Integer year) throws EntityNotFoundException {
		Optional<Season> season = getRepository().findByYear(year);

		if (!season.isPresent()) {
			final String code = localeMessage.getMessage("error.club.server.baseError")
					+ localeMessage.getMessage("error.entityNotFound.season.current.code");
			final String message = localeMessage.getMessage("error.entityNotFound.season.current.message");

			throw new EntityNotFoundException(null, code, message);
		}

		return getEntityMapper().toDto(season.get());
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.season.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.season.id.message", params);

		return new EntityNotFoundException(id, code, message);
	}

	@Override
	protected SeasonRepository getRepository() {
		return (SeasonRepository) super.getRepository();
	}

	@Override
	protected void checkForDuplicatedProperties(SeasonDto dto, Season entity) throws DuplicatedPropertyException {
		Set<String> duplicatedProperties = new HashSet<>();

		Optional<Season> existingEntity = getRepository().findByYear(dto.getYear());
		if (existingEntity.isPresent()) {
			duplicatedProperties.add("year");
		}

		if (!duplicatedProperties.isEmpty()) {
			throw new DuplicatedPropertyException("code", "message", "simpleEntity", duplicatedProperties);
		}
	}

}
