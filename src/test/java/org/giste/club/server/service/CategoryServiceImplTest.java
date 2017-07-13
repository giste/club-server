package org.giste.club.server.service;

import org.giste.club.common.dto.CategoryDto;
import org.giste.club.server.entity.Category;
import org.giste.club.server.repository.CategoryRepository;
import org.giste.spring.server.repository.CrudRepository;
import org.giste.spring.server.service.CrudServiceImpl;
import org.giste.spring.server.service.CrudServiceImplTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest extends CrudServiceImplTest<CategoryDto, Category> {

	@MockBean
	private CategoryRepository repository;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected CrudServiceImpl<CategoryDto, Category> getTestService() {
		return new CategoryServiceImpl(repository, localeMessage);
	}

	@Override
	protected CrudRepository<Category> getRepositoryMock() {
		return repository;
	}

	@Override
	protected Category getNewEntity() {
		return new Category(1L, "category", 10, 11, true);
	}

	@Override
	protected Class<Category> getEntityType() {
		return Category.class;
	}

}
