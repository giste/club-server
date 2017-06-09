package org.giste.club.server.service;

import javax.transaction.Transactional;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.spring.server.service.CrudeServiceImpl;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link ClubService} using CRUD repository.
 * 
 * @author Giste
 */
@Service
@Transactional
public class ClubServiceImpl extends CrudeServiceImpl<ClubDto, Club> implements ClubService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private LocaleMessage localeMessage;

	/**
	 * Creates a new ClubService.
	 * 
	 * @param clubRepository {@link ClubRepository} for managing club entities.
	 * @param localeMessage {@link LocaleMessage} bean for getting localized
	 *            messages.
	 */
	public ClubServiceImpl(ClubRepository clubRepository, LocaleMessage localeMessage) {
		super(clubRepository);
		this.localeMessage = localeMessage;
	}

	@Override
	public ClubDto findByAcronym(String acronym) throws EntityNotFoundException {
		Club club = ((ClubRepository) repository).findByAcronym(acronym);

		if (club == null) {
			final String[] params = { acronym };
			final String code = localeMessage.getMessage("error.club.server.baseError")
					+ localeMessage.getMessage("error.entityNotFound.club.acronym.code");
			final String message = localeMessage.getMessage("error.entityNotFound.club.acronym.message", params);
			final String developerInfo = localeMessage.getMessage("error.entityNotFound.club.acronym.developerInfo");

			throw new EntityNotFoundException(acronym, code, message, developerInfo);
		}

		return getDtoFromEntity(club);
	}

	@Override
	protected ClubDto getDtoFromEntity(Club club) {
		return new ClubDto(club.getId(), club.getName(), club.getAcronym(), club.isEnabled());
	}

	@Override
	protected Club getEntityFromDto(ClubDto clubDto) {
		return new Club(clubDto.getId(), clubDto.getName(), clubDto.getAcronym(), clubDto.isEnabled());
	}

	@Override
	protected Club updateEntityFromDto(Club club, ClubDto clubDto) {
		club.setName(clubDto.getName());
		club.setAcronym(clubDto.getAcronym());
		club.setEnabled(clubDto.isEnabled());

		return club;
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.club.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.club.id.message", params);
		final String developerInfo = localeMessage.getMessage("error.entityNotFound.club.id.developerInfo");

		return new EntityNotFoundException(id, code, message, developerInfo);
	}

	@Override
	protected void checkDuplicatedProperties(ClubDto clubDto) throws DuplicatedPropertyException {
		checkAcronym(clubDto);
	}

	/**
	 * Check if the acronym is duplicated and throws DuplicatedPropertyException
	 * if that is the case.
	 * 
	 * @param clubDto DTO of the club to check.
	 */
	private void checkAcronym(ClubDto clubDto) throws DuplicatedPropertyException {
		try {
			final ClubDto readClub = this.findByAcronym(clubDto.getAcronym());
			if (clubDto.getId() != readClub.getId()) {
				// There is a club with the same acronym and different Id. Throw
				// exception.
				final String[] params = { clubDto.getAcronym() };
				final String code = localeMessage.getMessage("error.club.server.baseError")
						+ localeMessage.getMessage("error.duplicatedProperty.club.acronym.code");
				final String message = localeMessage.getMessage("error.duplicatedProperty.club.acronym.message",
						params);
				final String developerInfo = localeMessage
						.getMessage("error.duplicatedProperty.club.acronym.developerInfo");
				DuplicatedPropertyException dpe = new DuplicatedPropertyException(code, message, developerInfo);

				LOGGER.debug("checkAcronym: Throwing DuplicatedPropertyException {}", dpe);

				throw dpe;
			}
		} catch (EntityNotFoundException e) {
			// Nothing to do, the acronym is not in use.
		}
	}

}
