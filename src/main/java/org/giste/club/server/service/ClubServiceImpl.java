package org.giste.club.server.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl implements ClubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClubService.class);

	private ClubRepository clubRepository;

	public ClubServiceImpl(ClubRepository clubRepository) {
		this.clubRepository = clubRepository;
	}

	@Override
	public ClubDto create(ClubDto club) throws DuplicatedClubAcronymException {
		Club newClub = new Club(club.getId(), club.getName(), club.getAcronym(), club.isEnabled());

		try {
			Club savedClub = clubRepository.save(newClub);

			return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
		} catch (DataIntegrityViolationException e) {
			LOGGER.debug("Catched DataIntegrityViolationException {}", e);

			throw new DuplicatedClubAcronymException(club.getAcronym());
		}
	}

	@Override
	public ClubDto findById(Long id) {
		Club club = getOneSafe(id);

		return new ClubDto(club.getId(), club.getName(), club.getAcronym(), club.isEnabled());
	}

	@Override
	public List<ClubDto> findAll() {
		return StreamSupport.stream(clubRepository.findAll().spliterator(), false)
				.map(entity -> new ClubDto(entity.getId(), entity.getName(), entity.getAcronym(), entity.isEnabled()))
				.collect(Collectors.toList());
	}

	@Override
	public ClubDto update(ClubDto club) throws DuplicatedClubAcronymException {
		// Find club to update.
		Club readClub = getOneSafe(club.getId());
		// Update club.
		readClub.setName(club.getName());
		readClub.setAcronym(club.getAcronym());
		readClub.setEnabled(club.isEnabled());
		try {
			Club savedClub = clubRepository.save(readClub);

			return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
		} catch (DataIntegrityViolationException e) {
			LOGGER.debug("Catched DataIntegrityViolationException {}", e);

			throw new DuplicatedClubAcronymException(club.getAcronym());
		}

	}

	@Override
	public ClubDto deleteById(Long id) {
		// Find club to delete.
		Club readClub = getOneSafe(id);

		clubRepository.delete(readClub);

		return new ClubDto(readClub.getId(), readClub.getName(), readClub.getAcronym(), readClub.isEnabled());
	}

	@Override
	public ClubDto enable(Long id) {
		// Find club to enable.
		Club readClub = getOneSafe(id);

		// Enable club.
		readClub.setEnabled(true);
		Club savedClub = clubRepository.save(readClub);

		return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
	}

	@Override
	public ClubDto disable(Long id) {
		// Find club to disable.
		Club readClub = getOneSafe(id);

		// Disable club.
		readClub.setEnabled(false);
		Club savedClub = clubRepository.save(readClub);

		return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
	}

	private Club getOneSafe(Long id) {
		Club club = clubRepository.findOne(id);
		if (club == null) {
			throw new ClubNotFoundException(id);
		} else {
			return club;
		}
	}
}
