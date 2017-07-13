package org.giste.club.server.controller;

import org.giste.club.common.dto.CategoryDto;
import org.giste.spring.server.controller.CrudRestController;
import org.giste.spring.server.service.CrudService;
import org.giste.spring.server.service.exception.DuplicatedPropertyException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for categories.
 * 
 * @author Giste
 */
@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryRestController extends CrudRestController<CategoryDto> {

	private final LocaleMessage localeMessage;
	
	public CategoryRestController(CrudService<CategoryDto> service, LocaleMessage localeMessage) {
		super(service);
		this.localeMessage=localeMessage;
	}

	@Override
	protected DuplicatedPropertyException getDuplicatedPropertyException(CategoryDto dto) {
		final String[] params = { dto.getName() };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.duplicatedProperty.category.name.code");
		final String message = localeMessage.getMessage("error.duplicatedProperty.category.name.message",
				params);
		final String developerInfo = localeMessage
				.getMessage("error.duplicatedProperty.category.name.developerInfo");

		return new DuplicatedPropertyException(code, message, developerInfo);
	}

}
