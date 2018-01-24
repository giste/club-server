package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.dto.SeasonTestHelper;
import org.giste.club.server.service.SeasonService;
import org.giste.spring.rest.server.controller.BaseRestControllerTest;
import org.giste.util.dto.DtoTestHelper;
import org.giste.util.service.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(SeasonRestController.class)
public class SeasonRestControllerTest extends BaseRestControllerTest<SeasonDto> {

	private final static String PATH_SEASONS = "/seasons";
	private final static String PATH_SEASONS_CURRENT = "/seasons/current";

	@Autowired
	private MockMvc mvc;

	private SeasonTestHelper testHelper = new SeasonTestHelper();

	@MockBean
	private SeasonService seasonService;

	@Override
	protected SeasonService getService() {
		return seasonService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected String getPath() {
		return PATH_SEASONS;
	}

	@Override
	protected DtoTestHelper<SeasonDto> getDtoTestHelper() {
		return testHelper;
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
		EntityNotFoundException enfe = new EntityNotFoundException("Season", "current", null, "Message");
		when(seasonService.findCurrent()).thenThrow(enfe);
		when(localeMessage.getMessage("error.entityNotFound.Season.current.code")).thenReturn("Code");
		when(localeMessage.getMessage("error.entityNotFound.Season.current.message")).thenReturn("Message");
		when(localeMessage.getMessage("error.entityNotFound.Season.current.developerInfo")).thenReturn("devInfo");

		this.mvc.perform(get(PATH_SEASONS_CURRENT)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("Code")))
				.andExpect(jsonPath("$.message", is("Message")))
				.andExpect(jsonPath("$.developerInfo", is("devInfo")));

		verify(seasonService).findCurrent();
		verifyNoMoreInteractions(seasonService);
	}

}
