package org.giste.club.server.controller;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.spring.server.controller.CrudeRestController;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Club REST API.
 * 
 * @author Giste
 */
@RestController
@RequestMapping("/rest/clubs")
class ClubRestController extends CrudeRestController<ClubDto> {

	private final LocaleMessage localeMessage;

	/**
	 * Constructs a REST controller for club entities. This controller is a
	 * subclass of {@link CrudeRestController}.
	 * 
	 * @param clubService {@link ClubService} for managing club requests.
	 * @param localeMessage Bean for getting localized messages.
	 */
	public ClubRestController(final ClubService clubService, LocaleMessage localeMessage) {
		super(clubService);
		this.localeMessage = localeMessage;
	}

	@Override
	protected DuplicatedPropertyException getDuplicatedPropertyException(ClubDto clubDto) {
		final String[] params = { clubDto.getAcronym() };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.duplicatedProperty.club.acronym.code");
		final String message = localeMessage.getMessage("error.duplicatedProperty.club.acronym.message",
				params);
		final String developerInfo = localeMessage
				.getMessage("error.duplicatedProperty.club.acronym.developerInfo");

		return new DuplicatedPropertyException(code, message, developerInfo);
	}

}
