package org.giste.club.server.controller;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.service.SeasonService;
import org.giste.spring.data.service.exception.DuplicatedPropertyException;
import org.giste.spring.data.service.exception.EntityNotFoundException;
import org.giste.spring.rest.server.controller.BaseRestController;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for season entities.
 * 
 * @author Giste
 */
@RestController
@RequestMapping(value = "/seasons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SeasonRestController extends BaseRestController<SeasonDto> {

	private final LocaleMessage localeMessage;

	public SeasonRestController(SeasonService service, LocaleMessage localeMessage) {
		super(service);
		this.localeMessage = localeMessage;
	}

	@Override
	protected DuplicatedPropertyException getDuplicatedPropertyException(SeasonDto dto) {
		final String[] params = { dto.getYear().toString() };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.duplicatedProperty.season.year.code");
		final String message = localeMessage.getMessage("error.duplicatedProperty.season.year.message",
				params);
		final String developerInfo = localeMessage
				.getMessage("error.duplicatedProperty.season.year.developerInfo");

		return new DuplicatedPropertyException(code, message, developerInfo);
	}

	/**
	 * Gets the current season. It's the one of the most recent year.
	 * 
	 * @return The current season.
	 * @throws EntityNotFoundException If there is no season defined.
	 */
	@GetMapping("/current")
	private SeasonDto findCurrent() throws EntityNotFoundException {
		return getService().findCurrent();
	}

	@Override
	protected SeasonService getService() {
		return (SeasonService) super.getService();
	}

}
