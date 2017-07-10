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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.spring.server.controller.CrudeRestControllerTest;
import org.giste.spring.server.service.CrudeService;
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
@WebMvcTest(ClubRestController.class)
public class ClubRestControllerTest extends CrudeRestControllerTest<ClubDto> {

	private final static String PATH_CLUBS = "/rest/clubs";
	private final static String PATH_CLUBS_ID = PATH_CLUBS + "/{id}";

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClubService clubService;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected CrudeService<ClubDto> getService() {
		return clubService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected ClubDto getNewDto() {
		return new ClubDto(1L, "Club 1", "CLB1", true);
	}

	@Override
	protected String getPath() {
		return "/rest/clubs";
	}

	@Override
	protected Class<ClubDto> getDtoType() {
		return ClubDto.class;
	}

	@Override
	protected ClubDto getInvalidDto(ClubDto dto) {
		dto.setName("Cl");
		dto.setAcronym("CBL_1");

		return dto;
	}

	@Override
	protected ResultActions checkProperties(ResultActions result, ClubDto clubDto) throws Exception {
		return super.checkProperties(result, clubDto)
				.andExpect(jsonPath("$.name", is(clubDto.getName())))
				.andExpect(jsonPath("$.acronym", is(clubDto.getAcronym())));
	}

	@Override
	protected ResultActions checkListProperties(ResultActions result, ClubDto target, int index) throws Exception {
		return super.checkListProperties(result, target, index)
				.andExpect(jsonPath("$[" + index + "].name", is(target.getName())))
				.andExpect(jsonPath("$[" + index + "].acronym", is(target.getAcronym())));
	}

	@Override
	protected void checkDto(ClubDto clubDto, ClubDto clubTargetDto) {
		super.checkDto(clubDto, clubTargetDto);
		assertThat(clubDto.getName(), is(clubTargetDto.getName()));
		assertThat(clubDto.getAcronym(), is(clubTargetDto.getAcronym()));
	}

	@Override
	protected void checkInvalidProperties(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.fieldErrorList", hasSize(2)))
				.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("name", "acronym")));

	}

	@Test
	public void createDuplicatedAcronym() throws Exception {
		final String DUPLICATED_ACRONYM = "AAA";
		final String DUPLICATED_ACRONYM_CODE = "10001001";
		final ClubDto clubDto = getNewDto();
		clubDto.setAcronym(DUPLICATED_ACRONYM);

		when(clubService.create(any(ClubDto.class)))
				.thenThrow(new DataIntegrityViolationException("Message"));
		when(localeMessage.getMessage("error.club.server.baseError")).thenReturn("1000");
		when(localeMessage.getMessage("error.duplicatedProperty.club.acronym.code")).thenReturn("1001");

		this.mvc.perform(post(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(clubDto)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(DUPLICATED_ACRONYM_CODE)));

		verify(clubService).create(any(ClubDto.class));
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void updateDuplicatedAcronym() throws Exception {
		final String DUPLICATED_ACRONYM = "AAA";
		final String DUPLICATED_ACRONYM_CODE = "10001001";
		final ClubDto clubDto = getNewDto();
		clubDto.setAcronym(DUPLICATED_ACRONYM);

		when(clubService.update(any(ClubDto.class)))
				.thenThrow(new DataIntegrityViolationException("Message"));
		when(localeMessage.getMessage("error.club.server.baseError")).thenReturn("1000");
		when(localeMessage.getMessage("error.duplicatedProperty.club.acronym.code")).thenReturn("1001");

		this.mvc.perform(put(PATH_CLUBS_ID, clubDto.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(clubDto)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(DUPLICATED_ACRONYM_CODE)));

		verify(clubService).update(any(ClubDto.class));
		verifyNoMoreInteractions(clubService);
	}

}
