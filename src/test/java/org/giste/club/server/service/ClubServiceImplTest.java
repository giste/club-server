package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.spring.server.service.CrudeServiceImpl;
import org.giste.spring.server.service.CrudeServiceImplTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ClubServiceImplTest extends CrudeServiceImplTest<ClubDto, Club> {

	@MockBean
	private ClubRepository repository;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected ClubRepository getRepositoryMock() {
		return repository;
	}

	@Override
	protected CrudeServiceImpl<ClubDto, Club> getService() {
		return new ClubServiceImpl(repository, localeMessage);
	}

	@Override
	protected Club getNewEntity() {
		return new Club(1L, "Club 1", "CBL1", true);
	}

	@Override
	protected void checkFields(ClubDto dto, Club entity) {
		super.checkFields(dto, entity);
		assertThat(dto.getName(), is(entity.getName()));
		assertThat(dto.getAcronym(), is(entity.getAcronym()));
	}

	@Override
	protected void verifyDuplicatedProperties(ClubDto dto) {
		verify(repository).findByAcronym((dto).getAcronym());
	}
}
