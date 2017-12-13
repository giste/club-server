package org.giste.club.server.controller;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.service.SeasonService;
import org.giste.spring.rest.server.controller.CrudRestController;
import org.giste.spring.rest.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.rest.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seasons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SeasonRestController extends CrudRestController<SeasonDto> {

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

	@GetMapping("/current")
	private SeasonDto findCurrent() throws EntityNotFoundException {
		return getService().findCurrent();
	}

	@Override
	protected SeasonService getService() {
		return (SeasonService) super.getService();
	}
	
}
