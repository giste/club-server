package org.giste.club.server.service;

import org.giste.club.common.dto.CategoryDto;
import org.giste.club.server.entity.Category;
import org.giste.club.server.repository.CategoryRepository;
import org.giste.spring.server.service.CrudServiceImpl;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CategoryService}.
 * 
 * @author Giste
 */
@Service
public class CategoryServiceImpl extends CrudServiceImpl<CategoryDto, Category> implements CategoryService {

	private LocaleMessage localeMessage;
	
	/**
	 * Constructs a service for managing categories.
	 * 
	 * @param repository Repository for categories persistence.
	 * @param localeMessage Bean for retrieving localized messages.
	 */
	public CategoryServiceImpl(CategoryRepository repository, LocaleMessage localeMessage) {
		super(repository);
		this.localeMessage=localeMessage;
	}

	@Override
	protected Category getEntityFromDto(CategoryDto dto) {
		return new Category(dto.getId(), dto.getName(), dto.getMinAge(), dto.getMaxAge(), dto.isMixed());
	}

	@Override
	protected CategoryDto getDtoFromEntity(Category entity) {
		return new CategoryDto(entity.getId(), entity.getName(), entity.getMinAge(), entity.getMaxAge(), entity.isMixed());
	}

	@Override
	protected Category updateEntityFromDto(Category entity, CategoryDto dto) {
		entity.setName(dto.getName());
		entity.setMinAge(dto.getMinAge());
		entity.setMaxAge(dto.getMaxAge());
		entity.setMixed(dto.isMixed());
		
		return entity;
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.category.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.category.id.message", params);
		final String developerInfo = localeMessage.getMessage("error.entityNotFound.category.id.developerInfo");

		return new EntityNotFoundException(id, code, message, developerInfo);
	}

}
