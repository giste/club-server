package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.dto.SeasonTestHelper;
import org.giste.spring.rest.server.controller.BaseRestControllerIntegrationTest;
import org.giste.util.dto.DtoTestHelper;
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

	private SeasonTestHelper testHelper = new SeasonTestHelper();

	@Override
	protected String getPath() {
		return PATH_SEASONS;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001001";
	}

	@Override
	protected DtoTestHelper<SeasonDto> getDtoTestHelper() {
		return testHelper;
	}

	@Test
	public void findCurrent() {
		SeasonDto season = getRestTemplate().getForObject(PATH_SEASONS_CURRENT, SeasonDto.class);

		assertThat(season, is(testHelper.getDtoList().get(1)));
	}

}
