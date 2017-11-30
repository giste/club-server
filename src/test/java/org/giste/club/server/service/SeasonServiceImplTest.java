package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.giste.club.common.dto.SeasonDto;
import org.giste.club.server.entity.Season;
import org.giste.club.server.repository.SeasonRepository;
import org.giste.spring.server.service.CrudServiceImplTest;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SeasonServiceImplTest extends CrudServiceImplTest<SeasonDto, Season> {

	@MockBean
	private SeasonRepository repository;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected SeasonServiceImpl getTestService() {
		return new SeasonServiceImpl(repository, localeMessage);
	}

	@Override
	protected SeasonRepository getRepositoryMock() {
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
	protected void checkProperties(SeasonDto dto, Season entity) {
		super.checkProperties(dto, entity);
		assertThat(dto.getYear(), is(entity.getYear()));
	}

	@Test
	public void findCurrentIsOk() {
		Season season = new Season(1L, 2017);
		when(repository.findFirstByOrderByYearDesc()).thenReturn(Optional.of(season));

		SeasonDto seasonDto = getTestService().findCurrent();

		verify(repository).findFirstByOrderByYearDesc();
		verifyNoMoreInteractions(repository);

		checkProperties(seasonDto, season);
	}

	@Test
	public void findCurrentEntityNotFound() {
		when(repository.findFirstByOrderByYearDesc()).thenReturn(Optional.ofNullable(null));

		try {
			getTestService().findCurrent();
			fail("EntityNotFoundException expected");
		} catch (EntityNotFoundException e) {
			assertThat(e.getId(), is(true));
		}
	}

	@Override
	public void updateIsValid() {
		// Do nothing. Method not implemented
	}

	@Override
	public void updateEntityNotFound() {
		// Do nothing. Method not implemented
	}

	@Test
	public void updateRuntimeException() {
		try {
			super.updateIsValid();
			fail("RuntimeException expected");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is("Method not allowed"));
		}
	}
}
