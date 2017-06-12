package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.giste.club.common.dto.Role;
import org.giste.club.common.dto.UserDto;
import org.giste.spring.server.controller.CrudRestControllerIntegrationTest;
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
		// DirtiesContextTestExecutionListener.class,
		// TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("user-entries.xml")
public class UserRestControllerIntegrationTest extends CrudRestControllerIntegrationTest<UserDto> {

	private final static String PATH_USERS = "/rest/users";
	
	@Override
	protected void checkDto(UserDto dto, UserDto target, boolean checkId) {
		if(checkId) {
			assertThat(dto.getId(), is(target.getId()));
		}
		assertThat(dto.getEmail(), is(target.getEmail()));
		assertThat(dto.getPasswordHash(), is(target.getPasswordHash()));
		assertThat(dto.getRole(), is(target.getRole()));
	}

	@Override
	protected List<UserDto> getDtoList() {
		UserDto user1 = new UserDto(1L, "user1@email.org", "hash1", Role.USER);
		UserDto user2 = new UserDto(2L, "user2@email.org", "hash2", Role.ADMIN);
		
		List<UserDto> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		
		return userList;
	}

	@Override
	protected UserDto getNewDto() {
		UserDto user = new UserDto();
		user.setEmail("newuser@email.org");
		user.setPasswordHash("hash");
		user.setRole(Role.USER);
		
		return user;
	}

	@Override
	protected UserDto getInvalidDto(UserDto dto) {
		dto.setEmail("email");
		dto.setRole(null);
		
		return dto;
	}

	@Override
	protected UserDto getUpdatedDto(UserDto dto) {
		dto.setEmail("othermail@email.org");
		dto.setPasswordHash("otherhash");
		dto.setRole(Role.ROOT);
		
		return dto;
	}

	@Override
	protected int getInvalidProperties() {
		return 2;
	}

	@Override
	protected Class<UserDto> getDtoType() {
		return UserDto.class;
	}

	@Override
	protected Class<UserDto[]> getArrayType() {
		return UserDto[].class;
	}

	@Override
	protected String getPath() {
		return PATH_USERS;
	}

	@Override
	protected String getNotFoundErrorCode() {
		return "10001004";
	}

}
