package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.giste.club.common.dto.Gender;
import org.giste.club.common.dto.PlayerDto;
import org.giste.spring.server.controller.CrudeRestControllerIntegrationTest;
import org.giste.util.StringUtil;
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
@DatabaseSetup("player-entries.xml")
public class PlayerRestControllerIntegrationTest extends CrudeRestControllerIntegrationTest<PlayerDto> {

	private final static String PATH_PLAYERS = "/rest/players";

	@Override
	protected void checkDto(PlayerDto dto, PlayerDto target, boolean checkId) {
		if (checkId) {
			assertThat(dto.getId(), is(target.getId()));
		}

		assertThat(dto.isEnabled(), is(target.isEnabled()));
		assertThat(dto.getName(), is(target.getName()));
		assertThat(dto.getBirthYear(), is(target.getBirthYear()));
		assertThat(dto.isGoalie(), is(target.isGoalie()));
		assertThat(dto.getGender(), is(target.getGender()));
	}

	@Override
	protected List<PlayerDto> getDtoList() {
		PlayerDto player1 = new PlayerDto(1L, false, "name1 surname1", 2001, false, Gender.MALE);
		PlayerDto player2 = new PlayerDto(2L, true, "name2 surname2", 2003, false, Gender.FEMALE);

		List<PlayerDto> dtoList = new ArrayList<>();
		dtoList.add(player1);
		dtoList.add(player2);

		return dtoList;
	}

	@Override
	protected PlayerDto getNewDto() {
		PlayerDto dto = new PlayerDto();
		dto.setName("new player");
		dto.setBirthYear(2000);
		dto.setGoalie(true);
		dto.setGender(Gender.FEMALE);

		return dto;
	}

	@Override
	protected PlayerDto getInvalidDto(PlayerDto dto) {
		dto.setName(StringUtil.ofLength(41));
		dto.setBirthYear(null);
		dto.setGender(null);

		return dto;
	}

	@Override
	protected PlayerDto getUpdatedDto(PlayerDto dto) {
		dto.setName("updated name");
		dto.setBirthYear(2002);
		dto.setGoalie(true);
		dto.setGender(Gender.FEMALE);

		return dto;
	}

	@Override
	protected Class<PlayerDto> getDtoType() {
		return PlayerDto.class;
	}

	@Override
	protected Class<PlayerDto[]> getArrayType() {
		return PlayerDto[].class;
	}

	@Override
	protected String getPath() {
		return PATH_PLAYERS;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001007";
	}

	@Override
	protected PlayerDto getDisabledDto() {
		return getDtoList().get(0);
	}

	@Override
	protected PlayerDto getEnabledDto() {
		return getDtoList().get(1);
	}

	@Override
	protected List<String> getInvalidFields() {
		return Arrays.asList("name", "birthYear", "gender");
	}

}
