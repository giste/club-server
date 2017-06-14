package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.giste.club.common.dto.Role;
import org.giste.club.common.dto.UserDto;
import org.giste.club.server.entity.User;
import org.giste.club.server.repository.UserRepository;
import org.giste.spring.server.service.CrudServiceImplTest;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceImplTest extends CrudServiceImplTest<UserDto, User> {

	@MockBean
	private UserRepository repository;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected UserRepository getRepositoryMock() {
		return repository;
	}

	@Override
	protected UserServiceImpl getTestService() {
		return new UserServiceImpl(repository, localeMessage);
	}

	@Override
	protected User getNewEntity() {
		return new User(1L, "user1@mail.org", "1234567890", Role.ADMIN);
	}

	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

	@Override
	protected void checkProperties(UserDto dto, User entity) {
		super.checkProperties(dto, entity);
		assertThat(dto.getEmail(), is(entity.getEmail()));
		assertThat(dto.getPasswordHash(), is(entity.getPasswordHash()));
		assertThat(dto.getRole(), is(entity.getRole()));
	}

	@Test
	public void findByEmailIsValid() {
		User user = getNewEntity();

		when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		UserDto userDto = getTestService().findByEmail(user.getEmail());

		verify(repository).findByEmail(user.getEmail());
		verifyNoMoreInteractions(repository);

		checkProperties(userDto, user);
	}

	@Test
	public void findByEmailEntityNotFound() {
		final String email = "user@email.org";
		when(repository.findByEmail(email)).thenReturn(Optional.ofNullable(null));

		try {
			getTestService().findByEmail(email);
			fail("Expected EntityNotFoundException");
		} catch (EntityNotFoundException e) {
			assertThat(e.getId(), is(email));
		}

	}
}
