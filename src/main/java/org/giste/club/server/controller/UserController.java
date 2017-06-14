package org.giste.club.server.controller;

import org.giste.club.common.dto.UserDto;
import org.giste.club.server.service.UserService;
import org.giste.spring.server.controller.CrudRestController;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
public class UserController extends CrudRestController<UserDto> {

	final LocaleMessage localeMessage;

	/**
	 * Constructs a new user controller.
	 * 
	 * @param service Service used by controller.
	 * @param localeMessage Bean for retrieve localized messages.
	 */
	public UserController(UserService service, LocaleMessage localeMessage) {
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
