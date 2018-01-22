package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.rest.server.controller.BaseRestControllerIntegrationTest;
import org.junit.Test;
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
		DbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/season-entries.xml")
public class SeasonRestControllerIntegrationTest extends BaseRestControllerIntegrationTest<SeasonDto> {

	private final static String PATH_SEASONS = "/seasons";
	private final static String PATH_SEASONS_CURRENT = "/seasons/current";

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
		return new SeasonDto(null, 2019);
	}

	@Override
	protected SeasonDto getInvalidDto(SeasonDto dto) {
		dto.setYear(2000);

		return dto;
	}

	@Override
	protected SeasonDto getUpdatedDto(SeasonDto dto) {
		dto.setYear(2019);

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

	@Test
	public void findCurrent() {
		SeasonDto season = getRestTemplate().getForObject(PATH_SEASONS_CURRENT, SeasonDto.class);

		assertThat(season, is(getDtoList().get(1)));
	}
}
