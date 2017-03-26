package org.giste.club.server.controller.advice;

import java.util.List;

import org.giste.spring.util.error.dto.FieldErrorDto;
import org.giste.spring.util.error.dto.RestErrorDto;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handler for validation exceptions. It constructs a {@link ValidationErrorDto}
 * with a list of individual {@link FieldErrorDto} for each validation error.
 * 
 * @author Giste
 */
@ControllerAdvice
public class ValidationErrorHandler {

	private LocaleMessage localeMessage;

	/**
	 * Constructs the handler.
	 * 
	 * @param localeMessage Bean for localize messages.
	 */
	public ValidationErrorHandler(LocaleMessage localeMessage) {
		this.localeMessage = localeMessage;
	}

	/**
	 * Process the validation errors.
	 * 
	 * @param e Exception that fires this handler.
	 * @return {@link ValidationErrorDto} with the list of {@link FieldErrorDto}
	 *         for each field with validation error.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorDto processValidationError(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		RestErrorDto validationErrorDto = new RestErrorDto(HttpStatus.BAD_REQUEST, "10001000", "Validation error", "");

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = localeMessage.getMessage(fieldError);
			validationErrorDto.addFieldError(new FieldErrorDto(fieldError.getField(), localizedErrorMessage));
		}

		return validationErrorDto;
	}
}
