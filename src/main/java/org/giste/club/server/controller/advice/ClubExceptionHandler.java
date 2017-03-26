package org.giste.club.server.controller.advice;

import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.giste.spring.util.error.dto.RestErrorDto;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClubExceptionHandler {

	private LocaleMessage localeMessage;

	public ClubExceptionHandler(LocaleMessage localeMessage) {
		this.localeMessage = localeMessage;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ClubNotFoundException.class)
	@ResponseBody
	public RestErrorDto handleClubNotFound(ClubNotFoundException e) {
		return new RestErrorDto(HttpStatus.NOT_FOUND, "0", e.getLocalizedMessage(), e.getMessage());
	}

	/**
	 * Handles the {@link DuplicatedClubAcronymException} thrown when a club is
	 * created or updated and the selected acronym is in use by another club.
	 * 
	 * @param e {@link DuplicatedClubAcronymException} to be handled.
	 * @return
	 */
	@ExceptionHandler(DuplicatedClubAcronymException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public RestErrorDto handleDuplicatedClubAcronymException(DuplicatedClubAcronymException e) {

		final String[] params = { e.getDuplicatedAcronym() };

		final String code = localeMessage.getMessage("error.clubServer.baseError")
				+ localeMessage.getMessage("error.duplicatedClubAcronym.code");
		final String message = localeMessage.getMessage("error.duplicatedClubAcronym.message", params);
		final String developerInfo = localeMessage.getMessage("error.duplicatedClubAcronym.developerInfo");

		return new RestErrorDto(HttpStatus.CONFLICT, code, message, developerInfo);
	}
}
