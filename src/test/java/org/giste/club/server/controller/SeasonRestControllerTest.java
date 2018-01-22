package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.service.SeasonService;
import org.giste.spring.rest.server.controller.BaseRestControllerTest;
import org.giste.spring.util.locale.LocaleMessage;
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
		EntityNotFoundException enfe = new EntityNotFoundException(null, "Code", "Message");
		when(seasonService.findCurrent()).thenThrow(enfe);

		this.mvc.perform(get(PATH_SEASONS_CURRENT)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(enfe.getCode())))
				.andExpect(jsonPath("$.message", is(enfe.getMessage())));

		verify(seasonService).findCurrent();
		verifyNoMoreInteractions(seasonService);
	}

	@Override
	protected Optional<List<String>> getInvalidProperties() {
		List<String> invalidProperties = new ArrayList<>();

		invalidProperties.add("year");

		return Optional.of(invalidProperties);
	}

	@Override
	protected Map<String, Object> getPropertiesToCheck(SeasonDto dto) {
		Map<String, Object> properties = new HashMap<>();

		properties.put("year", dto.getYear());

		return properties;
	}

	@Override
	protected Optional<Set<String>> getDuplicatedProperties() {
		Set<String> duplicatedProperties = new HashSet<>();

		duplicatedProperties.add("year");

		return Optional.of(duplicatedProperties);
	}
}
