package org.giste.club.server.service;

import java.util.Optional;

import org.giste.club.common.dto.UserDto;
import org.giste.club.server.entity.User;
import org.giste.club.server.repository.UserRepository;
import org.giste.spring.server.service.CrudServiceImpl;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CrudServiceImpl<UserDto, User> implements UserService {

	private LocaleMessage localeMessage;

	public UserServiceImpl(UserRepository repository, LocaleMessage localeMessage) {
		super(repository);
		this.localeMessage = localeMessage;
	}

	@Override
	protected User getEntityFromDto(UserDto dto) {
		return new User(dto.getId(), dto.getEmail(), dto.getName(), dto.getPasswordHash(), dto.getRole());
	}

	@Override
	protected UserDto getDtoFromEntity(User entity) {
		return new UserDto(entity.getId(), entity.getEmail(), entity.getName(), entity.getPasswordHash(), entity.getRole());
	}

	@Override
	protected User updateEntityFromDto(User entity, UserDto dto) {
		entity.setEmail(dto.getEmail());
		entity.setName(dto.getName());
		entity.setPasswordHash(dto.getPasswordHash());
		entity.setRole(dto.getRole());

		return entity;
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.user.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.user.id.message", params);
		final String developerInfo = localeMessage.getMessage("error.entityNotFound.user.id.developerInfo");

		return new EntityNotFoundException(id, code, message, developerInfo);
	}

	@Override
	public UserDto findByEmail(String email) throws EntityNotFoundException {
		Optional<User> user = getRepository().findByEmail(email);

		if (user.isPresent()) {
			return getDtoFromEntity(user.get());
		} else {
			final String[] params = { email };
			final String code = localeMessage.getMessage("error.club.server.baseError")
					+ localeMessage.getMessage("error.entityNotFound.user.email.code");
			final String message = localeMessage.getMessage("error.entityNotFound.user.email.message", params);
			final String developerInfo = localeMessage.getMessage("error.entityNotFound.user.email.developerInfo");

			throw new EntityNotFoundException(email, code, message, developerInfo);
		}
	}

	@Override
	protected UserRepository getRepository() {
		return (UserRepository) super.getRepository();
	}

}
