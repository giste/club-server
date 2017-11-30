package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.server.controller.CrudRestControllerIntegrationTest;
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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("season-entries.xml")
public class SeasonRestControllerIntegrationTest extends CrudRestControllerIntegrationTest<SeasonDto> {

	private final static String PATH_SEASONS = "/seasons";
	
	@Override
	protected void checkDto(SeasonDto dto, SeasonDto target, boolean checkId) {
		if (checkId) {
			assertThat(dto.getId(), is(target.getId()));
		}
		assertThat(dto.getYear(), is(target.getYear()));

	}

	@Override
	protected List<SeasonDto> getDtoList() {
		SeasonDto season1 = new SeasonDto(1L, 2017);
		SeasonDto season2 = new SeasonDto(2L, 2018);

		List<SeasonDto> seasonList = new ArrayList<>();
		seasonList.add(season1);
		seasonList.add(season2);

		return seasonList;
	}

	@Override
	protected SeasonDto getNewDto() {
		SeasonDto season = new SeasonDto();
		season.setYear(2019);

		return season;
	}

	@Override
	protected SeasonDto getInvalidDto(SeasonDto dto) {
		dto.setYear(2016);

		return dto;
	}

	@Override
	protected SeasonDto getUpdatedDto(SeasonDto dto) {
		return dto;
	}

	@Override
	protected Class<SeasonDto> getDtoType() {
		return SeasonDto.class;
	}

	@Override
	protected Class<SeasonDto[]> getArrayType() {
		return SeasonDto[].class;
	}

	@Override
	protected String getPath() {
		return PATH_SEASONS;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001001";
	}

	@Override
	protected List<String> getInvalidFields() {
		return Arrays.asList("year");
	}

	@Override
	public void updateIsValid() {
		SeasonDto season = getUpdatedDto(getDtoList().get(0));

		checkUpdateNotAllowed(season);
	}

	@Override
	public void updateIsInvalid() {
		SeasonDto season = getInvalidDto(getDtoList().get(0));

		checkUpdateNotAllowed(season);
	}

	@Override
	public void updateNotFound() {
		SeasonDto season = getInvalidDto(getDtoList().get(0));
		season.setId(100L);

		checkUpdateNotAllowed(season);
	}

	private void checkUpdateNotAllowed(SeasonDto season) {
		String response = getRestTemplate()
				.exchange(getPathId(), HttpMethod.PUT, new HttpEntity<>(season), String.class, season.getId())
				.getBody();

		assertThat(response, containsString("Request method 'PUT' not supported"));
	}
	
	@Test
	public void createDuplicatedYear() {
		SeasonDto season = new SeasonDto();
		season.setYear(2018);
		
		RestErrorDto restError = getRestTemplate().postForObject(getPathBase(), season, RestErrorDto.class);

		assertThat(restError.getStatus(), is(HttpStatus.CONFLICT));
		assertThat(restError.getCode(), is("10001002"));
	}
	
	@Test
	public void findCurrentIsOk() {
		SeasonDto season = getRestTemplate().getForObject(getPathBase() + "/current", SeasonDto.class);
		
		assertThat(season.getYear(), is(2018));
	}
}
