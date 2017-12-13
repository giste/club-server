package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.service.SeasonService;
import org.giste.spring.rest.server.controller.CrudRestControllerTest;
import org.giste.spring.rest.server.service.exception.EntityNotFoundException;
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
@WebMvcTest(SeasonRestController.class)
public class SeasonRestControllerTest extends CrudRestControllerTest<SeasonDto> {

	private final static String PATH_SEASONS = "/seasons";
	private final static String PATH_SEASONS_CURRENT = "/seasons/current";

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SeasonService seasonService;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected SeasonService getService() {
		return seasonService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected SeasonDto getNewDto() {
		return new SeasonDto(1L, 2017);
	}

	@Override
	protected SeasonDto getInvalidDto(SeasonDto dto) {
		dto.setYear(2000);

		return dto;
	}

	@Override
	protected String getPath() {
		return PATH_SEASONS;
	}

	@Override
	protected Class<SeasonDto> getDtoType() {
		return SeasonDto.class;
	}

	@Override
	protected void checkInvalidProperties(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.fieldErrorList", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("year")));
	}

	@Override
	protected ResultActions checkProperties(ResultActions result, SeasonDto target) throws Exception {
		return super.checkProperties(result, target)
				.andExpect(jsonPath("$.year", is(target.getYear())));
	}

	@Override
	protected ResultActions checkListProperties(ResultActions result, SeasonDto target, int index) throws Exception {
		return super.checkListProperties(result, target, index)
				.andExpect(jsonPath("$[" + index + "].year", is(target.getYear())));
	}

	@Test
	public void createDuplicatedYear() throws Exception {
		final Integer DUPLICATED_YEAR = 2017;
		final String DUPLICATED_YEAR_CODE = "10001003";
		final SeasonDto seasonDto = getNewDto();
		seasonDto.setYear(DUPLICATED_YEAR);

		when(seasonService.create(any(SeasonDto.class)))
				.thenThrow(new DataIntegrityViolationException("Message"));
		when(localeMessage.getMessage("error.club.server.baseError")).thenReturn("1000");
		when(localeMessage.getMessage("error.duplicatedProperty.season.year.code")).thenReturn("1003");

		this.mvc.perform(post(PATH_SEASONS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(seasonDto)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(DUPLICATED_YEAR_CODE)));

		verify(seasonService).create(any(SeasonDto.class));
		verifyNoMoreInteractions(seasonService);
	}

	@Test
	public void findCurrentIsOk() throws Exception {
		SeasonDto season = new SeasonDto(1L, 2017);

		when(seasonService.findCurrent()).thenReturn(season);

		this.mvc.perform(get(PATH_SEASONS_CURRENT)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.year", is(2017)));

		verify(seasonService).findCurrent();
		verifyNoMoreInteractions(seasonService);
	}

	@Test
	public void findCurrentEntityNotFound() throws Exception {
		EntityNotFoundException enfe = new EntityNotFoundException(null, "Code", "Message", "Developer info");
		when(seasonService.findCurrent()).thenThrow(enfe);

		this.mvc.perform(get(PATH_SEASONS_CURRENT)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(enfe.getCode())))
				.andExpect(jsonPath("$.message", is(enfe.getMessage())))
				.andExpect(jsonPath("$.developerInfo", is(enfe.getDeveloperInfo())));

		verify(seasonService).findCurrent();
		verifyNoMoreInteractions(seasonService);
	}
}
