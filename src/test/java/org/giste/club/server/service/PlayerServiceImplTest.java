package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.giste.club.common.dto.Gender;
import org.giste.club.common.dto.PlayerDto;
import org.giste.club.server.entity.Player;
import org.giste.club.server.repository.PlayerRepository;
import org.giste.spring.server.service.CrudeServiceImplTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PlayerServiceImplTest extends CrudeServiceImplTest<PlayerDto, Player> {

	@MockBean
	private PlayerRepository repository;
	@MockBean
	private LocaleMessage localeMessage;
	
	@Override
	protected PlayerRepository getRepositoryMock() {
		return repository;
	}

	@Override
	protected PlayerServiceImpl getService() {
		return new PlayerServiceImpl(repository, localeMessage);
	}

	@Override
	protected Player getNewEntity() {
		return new Player(1L, false, "name surname", 2001, false, Gender.MALE);
	}

	/* (non-Javadoc)
	 * @see org.giste.spring.server.service.CrudeServiceImplTest#checkFields(org.giste.util.dto.NonRemovableDto, org.giste.spring.server.entity.NonRemovableEntity)
	 */
	@Override
	protected void checkFields(PlayerDto dto, Player entity) {
		super.checkFields(dto, entity);
		assertThat(dto.getName(), is(entity.getName()));
		assertThat(dto.getBirthYear(), is(entity.getBirthYear()));
		assertThat(dto.isGoalie(), is(entity.isGoalie()));
		assertThat(dto.getGender(), is(entity.getGender()));
	}

}
