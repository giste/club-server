package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.giste.club.common.dto.SeasonDto;
import org.giste.spring.data.service.BaseServiceJpaIntegrationTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:datasets/season-entries.xml")
public class SeasonServiceJpaIntegrationTest extends BaseServiceJpaIntegrationTest<SeasonDto> {

	@Autowired
	private SeasonService service;
	
	@Override
	protected SeasonService getService() {
		return service;
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
		SeasonDto dto = new SeasonDto();
		dto.setYear(2019);
		
		return dto;
	}

	@Override
	protected void setDuplicatedProperties(SeasonDto dto) {
		dto.setYear(getDtoList().get(0).getYear());
	}

	@Override
	protected void checkDuplicatedProperties(List<String> duplicatedProperties) {
		assertThat(duplicatedProperties.contains("year"), is(true));
	}

}
