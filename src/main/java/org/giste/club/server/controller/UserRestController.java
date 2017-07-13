package org.giste.club.server.controller;

import org.giste.club.common.dto.UserDto;
import org.giste.club.server.service.UserService;
import org.giste.spring.server.controller.CrudRestController;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class UserRestController extends CrudRestController<UserDto> {

	private final LocaleMessage localeMessage;

	/**
	 * Constructs a new user controller.
	 * 
	 * @param service Service used by controller.
	 * @param localeMessage Bean for retrieve localized messages.
	 */
	public UserRestController(UserService service, LocaleMessage localeMessage) {
		super(service);
		this.localeMessage = localeMessage;
	}

	@Override
	protected DuplicatedPropertyException getDuplicatedPropertyException(UserDto dto) {
		final String[] params = { dto.getEmail() };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.duplicatedProperty.user.email.code");
		final String message = localeMessage.getMessage("error.duplicatedProperty.user.email.message",
				params);
		final String developerInfo = localeMessage
				.getMessage("error.duplicatedProperty.user.email.developerInfo");

		return new DuplicatedPropertyException(code, message, developerInfo);
	}

}
