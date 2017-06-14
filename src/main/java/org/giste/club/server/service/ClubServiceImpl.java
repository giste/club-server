package org.giste.club.server.service;

import javax.transaction.Transactional;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.spring.server.service.CrudeServiceImpl;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link ClubService} using CRUD repository.
 * 
 * @author Giste
 */
@Service
@Transactional
public class ClubServiceImpl extends CrudeServiceImpl<ClubDto, Club> implements ClubService {

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
		Club club = ((ClubRepository) getRepository()).findByAcronym(acronym);

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
	protected ClubRepository getRepository() {
		return (ClubRepository) super.getRepository();
	}

}
