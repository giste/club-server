package org.giste.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.giste.club.common.dto.Role;
import org.giste.club.common.dto.UserDto;
import org.giste.club.server.service.UserService;
import org.giste.spring.server.controller.CrudRestControllerTest;
import org.giste.spring.util.locale.LocaleMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest extends CrudRestControllerTest<UserDto> {

	private final static String PATH_USERS = "/rest/users";

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;
	@MockBean
	private LocaleMessage localeMessage;

	@Override
	protected UserService getService() {
		return userService;
	}

	@Override
	protected MockMvc getMockMvc() {
		return mvc;
	}

	@Override
	protected UserDto getNewDto() {
		return new UserDto(1L, "user1@email.org", "user1", "1234567890", Role.USER);
	}

	@Override
	protected UserDto getInvalidDto(UserDto dto) {
		dto.setEmail("1111");
		dto.setName(null);

		return dto;
	}

	@Override
	protected String getPath() {
		return PATH_USERS;
	}

	@Override
	protected Class<UserDto> getDtoType() {
		return UserDto.class;
	}

	@Override
	protected void checkInvalidProperties(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.fieldErrorList", hasSize(2)))
				.andExpect(jsonPath("$.fieldErrorList[*].field", containsInAnyOrder("email", "name")));
	}

	@Override
	protected ResultActions checkProperties(ResultActions result, UserDto target) throws Exception {
		return super.checkProperties(result, target)
				.andExpect(jsonPath("$.email", is(target.getEmail())))
				.andExpect(jsonPath("$.name", is(target.getName())))
				.andExpect(jsonPath("$.passwordHash", is(target.getPasswordHash())))
				.andExpect(jsonPath("$.role", is(target.getRole().toString())));
	}

	@Override
	protected ResultActions checkListProperties(ResultActions result, UserDto target, int index) throws Exception {
		return super.checkListProperties(result, target, index)
				.andExpect(jsonPath("$[" + index + "].email", is(target.getEmail())))
				.andExpect(jsonPath("$[" + index + "].name", is(target.getName())))
				.andExpect(jsonPath("$[" + index + "].passwordHash", is(target.getPasswordHash())))
				.andExpect(jsonPath("$[" + index + "].role", is(target.getRole().toString())));
	}

	@Override
	protected void checkDto(UserDto dto, UserDto target) {
		super.checkDto(dto, target);
		assertThat(dto.getEmail(), is(target.getEmail()));
		assertThat(dto.getName(), is(target.getName()));
		assertThat(dto.getPasswordHash(), is(target.getPasswordHash()));
		assertThat(dto.getRole(), is(target.getRole()));
	}

	@Test
	public void createDuplicatedEmail() throws Exception {
		final String DUPLICATED_EMAIL = "user1@email.org";
		final String DUPLICATED_EMAIL_CODE = "10001006";
		final UserDto userDto = getNewDto();
		userDto.setEmail(DUPLICATED_EMAIL);

		when(userService.create(any(UserDto.class)))
				.thenThrow(new DataIntegrityViolationException("Message"));
		when(localeMessage.getMessage("error.club.server.baseError")).thenReturn("1000");
		when(localeMessage.getMessage("error.duplicatedProperty.user.email.code")).thenReturn("1006");

		this.mvc.perform(post(PATH_USERS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(userDto)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(DUPLICATED_EMAIL_CODE)));

		verify(userService).create(any(UserDto.class));
		verifyNoMoreInteractions(userService);
	}

}
