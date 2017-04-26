package org.giste.club.server.service;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.giste.club.server.service.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link ClubService} using CRUD repository.
 * 
 * @author Giste
 */
@Service
public class ClubServiceImpl extends CrudeServiceImpl<ClubDto, Club> implements ClubService {

	public ClubServiceImpl(ClubRepository clubRepository) {
		super(clubRepository);
	}

	@Override
	public ClubDto create(ClubDto dto) {
		try {
			return super.create(dto);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicatedClubAcronymException(dto.getAcronym());
		}
	}

	@Override
	public ClubDto update(ClubDto dto) throws EntityNotFoundException {
		try {
			return super.update(dto);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicatedClubAcronymException(dto.getAcronym());
		}
	}

	@Override
	protected ClubDto getDtoFromEntity(Club entity) {
		return new ClubDto(entity.getId(), entity.getName(), entity.getAcronym(), entity.isEnabled());
	}

	@Override
	protected Club getEntityFromDto(ClubDto dto) {
		return new Club(dto.getId(), dto.getName(), dto.getAcronym(), dto.isEnabled());
	}

	@Override
	protected Club updateEntityFromDto(Club entity, ClubDto dto) {
		entity.setName(dto.getName());
		entity.setAcronym(dto.getAcronym());
		entity.setEnabled(dto.isEnabled());

		return entity;
	}
}
