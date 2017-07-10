package org.giste.club.server.controller;

import org.giste.club.common.dto.PlayerDto;
import org.giste.club.server.service.PlayerService;
import org.giste.spring.server.controller.CrudeRestController;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Player REST API.
 * 
 * @author Giste
 */
@RestController
@RequestMapping("/players")
class PlayerRestController extends CrudeRestController<PlayerDto> {

	private final LocaleMessage localeMessage;

	/**
	 * Constructs a new controller for managing players.
	 * 
	 * @param restService The service used by this controller to manage players.
	 * @param localeMessage The bean used for get localized messages.
	 */
	public PlayerRestController(PlayerService restService, LocaleMessage localeMessage) {
		super(restService);
		this.localeMessage = localeMessage;
	}

	@Override
	protected DuplicatedPropertyException getDuplicatedPropertyException(PlayerDto dto) {
		final String[] params = { dto.getName() };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.duplicatedProperty.player.name.code");
		final String message = localeMessage.getMessage("error.duplicatedProperty.player.name.message",
				params);
		final String developerInfo = localeMessage
				.getMessage("error.duplicatedProperty.player.name.developerInfo");

		return new DuplicatedPropertyException(code, message, developerInfo);
	}

}
