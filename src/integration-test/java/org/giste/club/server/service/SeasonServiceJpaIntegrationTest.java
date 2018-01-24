package org.giste.club.server.service;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.dto.SeasonTestHelper;
import org.giste.spring.data.service.BaseServiceJpaIntegrationTest;
import org.giste.util.dto.DtoTestHelper;
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

	private SeasonTestHelper testHelper = new SeasonTestHelper();

	@Override
	protected SeasonService getService() {
		return service;
	}

	@Override
	protected DtoTestHelper<SeasonDto> getDtoTestHelper() {
		return testHelper;
	}

}
