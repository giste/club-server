package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.giste.club.common.dto.ClubDto;
import org.giste.spring.server.controller.CrudeRestControllerIntegrationTest;
import org.giste.spring.util.error.dto.RestErrorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
@DatabaseSetup("club-entries.xml")
public class ClubRestControllerIntegrationTest extends CrudeRestControllerIntegrationTest<ClubDto> {

	private final static String PATH_CLUBS = "/rest/clubs";

	@Test
	public void createDuplicatedAcronym() {
		ClubDto newClub = new ClubDto();
		newClub.setName("New Club");
		newClub.setAcronym("CLB1");
		newClub.setEnabled(true);

		RestErrorDto restError = getRestTemplate().postForObject(getPathBase(), newClub,
				RestErrorDto.class);

		assertThat(restError.getStatus(), is(HttpStatus.CONFLICT));
		assertThat(restError.getCode(), is("10001001"));
	}

	@Test
	public void updateDuplicatedAcronym() {
		ClubDto club = new ClubDto();
		club.setId(1L);
		club.setName("Updated Club");
		club.setAcronym("CLB2");
		club.setEnabled(true);

		RestErrorDto restError = getRestTemplate().exchange(getPathId(), HttpMethod.PUT, new HttpEntity<>(club),
				RestErrorDto.class, club.getId()).getBody();

		assertThat(restError.getStatus(), is(HttpStatus.CONFLICT));
		assertThat(restError.getCode(), is("10001001"));
	}

	@Override
	protected void checkDto(ClubDto club, ClubDto target, boolean checkId) {
		if (checkId) {
			assertThat(club.getId(), is(target.getId()));
		}
		assertThat(club.getName(), is(target.getName()));
		assertThat(club.getAcronym(), is(target.getAcronym()));
		assertThat(club.isEnabled(), is(target.isEnabled()));
	}

	@Override
	protected List<ClubDto> getDtoList() {
		ClubDto club1 = new ClubDto(1L, "Club 1", "CLB1", false);
		ClubDto club2 = new ClubDto(2L, "Club 2", "CLB2", true);

		List<ClubDto> dtoList = new ArrayList<>();
		dtoList.add(club1);
		dtoList.add(club2);

		return dtoList;
	}

	@Override
	protected ClubDto getNewDto() {
		ClubDto dto = new ClubDto();
		dto.setName("New club");
		dto.setAcronym("CLB");
		dto.setEnabled(false);

		return dto;
	}

	@Override
	protected ClubDto getInvalidDto(ClubDto dto) {
		dto.setName("Nm");
		dto.setAcronym("CLB_1");

		return dto;
	}

	@Override
	protected ClubDto getUpdatedDto(ClubDto dto) {
		dto.setName("Updated club");
		dto.setAcronym("CBLN");
		dto.setEnabled(!dto.isEnabled());

		return dto;
	}

	@Override
	protected int getInvalidProperties() {
		return 2;
	}

	@Override
	protected Class<ClubDto> getDtoType() {
		return ClubDto.class;
	}

	@Override
	protected Class<ClubDto[]> getArrayType() {
		return ClubDto[].class;
	}

	@Override
	protected String getPath() {
		return PATH_CLUBS;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001002";
	}

	@Override
	protected ClubDto getDisabledDto() {
		return getDtoList().get(0);
	}

	@Override
	protected ClubDto getEnabledDto() {
		return getDtoList().get(1);
	}
	
}
