package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.giste.club.common.dto.CategoryDto;
import org.giste.spring.server.controller.CrudRestControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		// DirtiesContextTestExecutionListener.class,
		// TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("category-entries.xml")
public class CategoryRestControllerIntegrationTest extends CrudRestControllerIntegrationTest<CategoryDto> {

	private final static String PATH_CATEGORIES = "/categories";

	@Override
	protected void checkDto(CategoryDto dto, CategoryDto target, boolean checkId) {
		if (checkId) {
			assertThat(dto.getId(), is(target.getId()));
		}
		assertThat(dto.getName(), is(target.getName()));
		assertThat(dto.getMinAge(), is(target.getMinAge()));
		assertThat(dto.getMaxAge(), is(target.getMaxAge()));
		assertThat(dto.isMixed(), is(target.isMixed()));
	}

	@Override
	protected List<CategoryDto> getDtoList() {
		CategoryDto category1 = new CategoryDto(1L, "category 1", 13, 14, true);
		CategoryDto category2 = new CategoryDto(2L, "category 2", 15, 16, false);

		List<CategoryDto> categoryList = new ArrayList<>();
		categoryList.add(category1);
		categoryList.add(category2);

		return categoryList;
	}

	@Override
	protected CategoryDto getNewDto() {
		CategoryDto category = new CategoryDto();
		category.setName("new category");
		category.setMinAge(17);
		category.setMaxAge(18);
		category.setMixed(true);

		return category;
	}

	@Override
	protected CategoryDto getInvalidDto(CategoryDto dto) {
		dto.setMinAge(-2);
		dto.setMaxAge(102);

		return dto;
	}

	@Override
	protected CategoryDto getUpdatedDto(CategoryDto dto) {
		dto.setName("other category");
		dto.setMinAge(13);
		dto.setMaxAge(20);
		dto.setMixed(false);

		return dto;
	}

	@Override
	protected Class<CategoryDto> getDtoType() {
		return CategoryDto.class;
	}

	@Override
	protected Class<CategoryDto[]> getArrayType() {
		return CategoryDto[].class;
	}

	@Override
	protected String getPath() {
		return PATH_CATEGORIES;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001009";
	}

	@Override
	protected List<String> getInvalidFields() {
		return Arrays.asList("minAge", "maxAge");
	}

}
