package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.giste.club.common.dto.CategoryDto;
import org.giste.club.server.service.CategoryService;
import org.giste.spring.server.controller.CrudRestControllerTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryRestController.class)
public class CategoryRestControllerTest extends CrudRestControllerTest<CategoryDto> {

	private final static String PATH_CATEGORIES = "/categories";

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CategoryService categoryService;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected CategoryService getService() {
		return categoryService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected CategoryDto getNewDto() {
		return new CategoryDto(1L, "category 1", 10, 11, true);
	}

	@Override
	protected CategoryDto getInvalidDto(CategoryDto dto) {
		dto.setMinAge(105);
		dto.setMaxAge(103);

		return dto;
	}

	@Override
	protected String getPath() {
		return PATH_CATEGORIES;
	}

	@Override
	protected Class<CategoryDto> getDtoType() {
		return CategoryDto.class;
	}

	@Override
	protected void checkInvalidProperties(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.fieldErrorList", hasSize(2)))
				.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("maxAge", "maxAge")));
				//.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("minAge", "maxAge")));
	}

	@Override
	protected ResultActions checkProperties(ResultActions result, CategoryDto target) throws Exception {
		return super.checkProperties(result, target)
				.andExpect(jsonPath("$.name", is(target.getName())))
				.andExpect(jsonPath("$.minAge", is(target.getMinAge())))
				.andExpect(jsonPath("$.maxAge", is(target.getMaxAge())))
				.andExpect(jsonPath("$.mixed", is(target.isMixed())));
	}

	@Override
	protected ResultActions checkListProperties(ResultActions result, CategoryDto target, int index) throws Exception {
		return super.checkListProperties(result, target, index)
				.andExpect(jsonPath("$[" + index + "].name", is(target.getName())))
				.andExpect(jsonPath("$[" + index + "].minAge", is(target.getMinAge())))
				.andExpect(jsonPath("$[" + index + "].maxAge", is(target.getMaxAge())))
				.andExpect(jsonPath("$[" + index + "].mixed", is(target.isMixed())));
	}

	@Override
	protected void checkDto(CategoryDto dto, CategoryDto target) {
		super.checkDto(dto, target);
		assertThat(dto.getName(), is(target.getName()));
		assertThat(dto.getMinAge(), is(target.getMinAge()));
		assertThat(dto.getMaxAge(), is(target.getMaxAge()));
		assertThat(dto.isMixed(), is(target.isMixed()));
	}

	@Test
	public void createDuplicatedName() throws Exception {
		final String DUPLICATED_NAME = "duplicated category";
		final String DUPLICATED_NAME_CODE = "10001010";
		final CategoryDto userDto = getNewDto();
		userDto.setName(DUPLICATED_NAME);

		when(categoryService.create(any(CategoryDto.class)))
				.thenThrow(new DataIntegrityViolationException("Message"));
		when(localeMessage.getMessage("error.club.server.baseError")).thenReturn("1000");
		when(localeMessage.getMessage("error.duplicatedProperty.category.name.code")).thenReturn("1010");

		this.mvc.perform(post(PATH_CATEGORIES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(userDto)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(DUPLICATED_NAME_CODE)));

		verify(categoryService).create(any(CategoryDto.class));
		verifyNoMoreInteractions(categoryService);
	}

}
