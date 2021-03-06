package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.data.repository.CrudRepository;
import org.giste.spring.data.service.BaseServiceJpaTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.giste.util.service.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SeasonServiceJpaTest extends BaseServiceJpaTest<SeasonDto, Season> {

	@MockBean
	private SeasonRepository repository;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected SeasonServiceJpa getTestService() {
		return new SeasonServiceJpa(repository, getEntityHelper());
	}

	@Override
	protected CrudRepository<Season> getRepositoryMock() {
		return repository;
	}

	@Override
	protected Season getNewEntity() {
		return new Season(1L, 2017);
	}

	@Override
	protected Class<Season> getEntityType() {
		return Season.class;
	}

	@Override
	protected SeasonHelper getEntityHelper() {
		return new SeasonHelper();
	}

	@Test
	public void findCurrentIsOk() {
		Season season = new Season(1L, 2017);
		when(repository.findFirstByOrderByYearDesc()).thenReturn(Optional.of(season));

		SeasonDto dto = getTestService().findCurrent();

		assertThat(dto.getId(), is(season.getId()));
		assertThat(dto.getYear(), is(season.getYear()));
	}

	@Test
	public void findCurrentEntityNotFound() {
		when(repository.findFirstByOrderByYearDesc()).thenReturn(Optional.ofNullable(null));

		try {
			getTestService().findCurrent();
			fail("EntityNotFoundException expected");
		} catch (EntityNotFoundException e) {
			// assertThat(e.getId(), is(null));
		}
	}

	@Override
	protected void mockDuplicatedPropertyNotFound() {
		when(repository.findByYear(any(Integer.class))).thenReturn(Optional.ofNullable(null));

	}

	@Override
	protected void mockDuplicatedPropertyFound() {
		Season season = new Season(1L, 2017);
		when(repository.findByYear(season.getYear())).thenReturn(Optional.of(season));

	}

	@Override
	protected void verifyCallToCheckDuplicatedProperties() {
		verify(repository).findByYear(2017);
	}
}
