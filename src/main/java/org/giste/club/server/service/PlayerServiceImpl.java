package org.giste.club.server.service;

import org.giste.club.common.dto.PlayerDto;
import org.giste.club.server.entity.Player;
import org.giste.spring.server.repository.CrudeRepository;
import org.giste.spring.server.service.CrudeServiceImpl;
import org.giste.spring.server.service.exception.EntityNotFoundException;
import org.giste.spring.util.locale.LocaleMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PlayerService}.
 * 
 * @author Giste
 */
@Service
public class PlayerServiceImpl extends CrudeServiceImpl<PlayerDto, Player> implements PlayerService {

	private LocaleMessage localeMessage;

	/**
	 * Constructs a new service for managing players.
	 * 
	 * @param repository The repository used by this service to persist players.
	 * @param localeMessage The bean used for get localized messages.
	 */
	public PlayerServiceImpl(CrudeRepository<Player> repository, LocaleMessage localeMessage) {
		super(repository);
		this.localeMessage = localeMessage;
	}

	@Override
	protected Player getEntityFromDto(PlayerDto dto) {
		return new Player(dto.getId(), dto.isEnabled(), dto.getName(), dto.getBirthYear(), dto.isGoalie(),
				dto.getGender());
	}

	@Override
	protected PlayerDto getDtoFromEntity(Player entity) {
		return new PlayerDto(entity.getId(), entity.isEnabled(), entity.getName(), entity.getBirthYear(),
				entity.isGoalie(), entity.getGender());
	}

	@Override
	protected Player updateEntityFromDto(Player entity, PlayerDto dto) {
		entity.setName(dto.getName());
		entity.setBirthYear(dto.getBirthYear());
		entity.setGoalie(dto.isGoalie());
		entity.setGender(dto.getGender());

		return entity;
	}

	@Override
	protected EntityNotFoundException getEntityNotFoundException(Long id) {
		final Long[] params = { id };
		final String code = localeMessage.getMessage("error.club.server.baseError")
				+ localeMessage.getMessage("error.entityNotFound.player.id.code");
		final String message = localeMessage.getMessage("error.entityNotFound.player.id.message", params);
		final String developerInfo = localeMessage.getMessage("error.entityNotFound.player.id.developerInfo");

		return new EntityNotFoundException(id, code, message, developerInfo);
	}

}
