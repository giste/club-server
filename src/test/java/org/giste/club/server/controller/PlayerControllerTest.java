package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.giste.club.common.dto.Gender;
import org.giste.club.common.dto.PlayerDto;
import org.giste.club.server.service.PlayerService;
import org.giste.spring.server.controller.CrudeRestControllerTest;
import org.giste.spring.server.service.CrudeService;
import org.giste.spring.util.locale.LocaleMessage;
import org.giste.util.StringUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest extends CrudeRestControllerTest<PlayerDto> {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PlayerService playerService;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected CrudeService<PlayerDto> getService() {
		return playerService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected PlayerDto getNewDto() {
		return new PlayerDto(1L, false, "name surname", 2003, false, Gender.FEMALE);
	}

	@Override
	protected PlayerDto getInvalidDto(PlayerDto dto) {
		dto.setName(StringUtil.ofLength(41));
		dto.setBirthYear(null);
		dto.setGender(null);

		return dto;
	}

	@Override
	protected String getBasePath() {
		return "/rest/players";
	}

	@Override
	protected Class<PlayerDto> getDtoType() {
		return PlayerDto.class;
	}

	@Override
	protected void checkInvalidProperties(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.fieldErrorList", hasSize(3)))
				.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("name", "birthYear", "gender")));
	}

	@Override
	protected ResultActions checkProperties(ResultActions result, PlayerDto target) throws Exception {
		return super.checkProperties(result, target)
				.andExpect(jsonPath("$.name", is(target.getName())))
				.andExpect(jsonPath("$.birthYear", is(target.getBirthYear())))
				.andExpect(jsonPath("$.goalie", is(target.isGoalie())))
				.andExpect(jsonPath("$.gender", is(target.getGender().toString())));
	}

	@Override
	protected ResultActions checkListProperties(ResultActions result, PlayerDto target, int index) throws Exception {
		return super.checkListProperties(result, target, index)
				.andExpect(jsonPath("$[" + index + "].name", is(target.getName())))
				.andExpect(jsonPath("$[" + index + "].birthYear", is(target.getBirthYear())))
				.andExpect(jsonPath("$[" + index + "].goalie", is(target.isGoalie())))
				.andExpect(jsonPath("$[" + index + "].gender", is(target.getGender().toString())));
	}

	@Override
	protected void checkDto(PlayerDto dto, PlayerDto target) {
		super.checkDto(dto, target);
		assertThat(dto.getName(), is(target.getName()));
		assertThat(dto.getBirthYear(), is(target.getBirthYear()));
		assertThat(dto.isGoalie(), is(target.isGoalie()));
		assertThat(dto.getGender(), is(target.getGender()));
	}

}
