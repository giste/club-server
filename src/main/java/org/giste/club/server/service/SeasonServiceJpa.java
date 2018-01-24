package org.giste.club.server.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.data.service.BaseServiceJpa;
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

	/**
	 * Creates the service to manage seasons.
	 * 
	 * @param repository {@link SeasonRepository} for persist seasons.
	 * @param helper Helper class for mapping between season entity and DTO.
	 */
	public SeasonServiceJpa(SeasonRepository repository, SeasonHelper helper) {
		super(repository, helper);
	}

	@Override
	public SeasonDto findCurrent() throws EntityNotFoundException {

		Optional<Season> season = getRepository().findFirstByOrderByYearDesc();

		if (!season.isPresent()) {
			throw new EntityNotFoundException(getEntityHelper().getEntityName(), "current", null);
		}

		return getEntityHelper().toDto(season.get());
	}

	@Override
	public SeasonDto findByYear(Integer year) throws EntityNotFoundException {
		Optional<Season> season = getRepository().findByYear(year);

		if (!season.isPresent()) {
			throw new EntityNotFoundException(getEntityHelper().getEntityName(), "year", year);
		}

		return getEntityHelper().toDto(season.get());
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
			throw new DuplicatedPropertyException(getEntityHelper().getEntityName(), duplicatedProperties);
		}
	}

}
