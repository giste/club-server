package org.giste.club.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl implements ClubService {

	private ClubRepository clubRepository;

	public ClubServiceImpl(ClubRepository clubRepository) {
		this.clubRepository = clubRepository;
	}

	@Override
	public ClubDto create(ClubDto club) throws DuplicatedClubAcronymException {
		Club newClub = new Club(club.getId(), club.getName(), club.getAcronym(), club.isEnabled());
		
		Club savedClub = clubRepository.save(newClub);
		
		return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
	}

	@Override
	public ClubDto findById(Long id) throws ClubNotFoundException {
			Club club = getOneSafe(id);

			return new ClubDto(club.getId(), club.getName(), club.getAcronym(), club.isEnabled());
	}

	@Override
	public List<ClubDto> findAll() {
		return clubRepository.findAll().stream()
				.map(entity -> new ClubDto(entity.getId(), entity.getName(), entity.getAcronym(), entity.isEnabled()))
				.collect(Collectors.toList());
	}

	@Override
	public ClubDto update(ClubDto club) throws ClubNotFoundException {
		// Find club to update.
		Club readClub = getOneSafe(club.getId());
		// Update club.
		readClub.setName(club.getName());
		readClub.setAcronym(club.getAcronym());
		readClub.setEnabled(club.isEnabled());
		Club savedClub = clubRepository.save(readClub);
		
		return new ClubDto(savedClub.getId(), savedClub.getName(), savedClub.getAcronym(), savedClub.isEnabled());
	}

	@Override
	public ClubDto deleteById(Long id) throws ClubNotFoundException {
		// Find club to delete.
		Club readClub = getOneSafe(id);
		
		clubRepository.delete(readClub);
		
		return new ClubDto(readClub.getId(), readClub.getName(), readClub.getAcronym(), readClub.isEnabled());
	}

	@Override
	public ClubDto changeState(long id, boolean active) throws ClubNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	private Club getOneSafe(Long id) {
		Club club = clubRepository.getOne(id);
		if (club == null) {
			throw new ClubNotFoundException(id);
		} else {
			return club;
		}
	}
}
