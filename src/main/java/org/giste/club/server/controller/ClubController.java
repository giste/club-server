package org.giste.club.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.giste.club.server.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for Club REST API.
 * 
 * @author Giste
 */
@RestController
@RequestMapping("/rest/clubs")
public class ClubController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	final private ClubService clubService;

	/**
	 * Constructor for Spring.
	 * 
	 * @param clubService Bean for service for managing club requests.
	 */
	public ClubController(final ClubService clubService) {
		this.clubService = clubService;
	}

	/**
	 * Creates a new Club.
	 * 
	 * @param club BaseDto object with values for new club.
	 * @return BaseDto Object with the values of the new created Club.
	 * @throws DuplicatedClubAcronymException If Acronym for new club is in use
	 *             by another club.
	 */
	@PostMapping
	public ClubDto create(@RequestBody @Valid final ClubDto club) throws DuplicatedClubAcronymException {
		return clubService.create(club);
	}

	/**
	 * Retrieves one single club.
	 * 
	 * @param id Identifier of the club to retrieve.
	 * @return {@link BaseDto} with the data of the requested club.
	 * @throws ClubNotFoundException If the requested club can't be found.
	 */
	@GetMapping(value = "/{id}")
	public ClubDto findById(@PathVariable("id") Long id) throws EntityNotFoundException {
		ClubDto club = clubService.findById(id);

		return club;
	}

	/**
	 * Retrieves all clubs.
	 * 
	 * @return List populated with the {@link BaseDto} of all existent clubs.
	 */
	@GetMapping
	public List<ClubDto> findAll() {
		return clubService.findAll();
	}

	/**
	 * Updates one club.
	 * 
	 * @param id Identifier of the club to be updated.
	 * @param club {@link BaseDto} with the values of the club to update.
	 * @return {@link BaseDto} with the updated values of the club.
	 * @throws ClubNotFoundException 
	 */
	@PutMapping(value = "/{id}")
	public ClubDto update(@PathVariable("id") Long id, @RequestBody @Valid final ClubDto club) throws DuplicatedClubAcronymException, EntityNotFoundException {

		// If club identifier is different, overwrite it.
		if (id != club.getId()) {
			LOGGER.debug("Identifier from BaseDto ({}) is different than identifier from URI ({})", club.getId(), id);
			club.setId(id);
		}

		return clubService.update(club);
	}
	
	/**
	 * Deletes one club.
	 * 
	 * @param id Identifier of the club to delete.
	 * @return {@link BaseDto} with the values of the deleted club.
	 * @throws ClubNotFoundException 
	 */
	@DeleteMapping(value = "/{id}")
	public ClubDto deleteById(@PathVariable("id") Long id) throws EntityNotFoundException {
		return clubService.deleteById(id);
	}
	
	/**
	 * Enables one club in the application.
	 * 
	 * @param id Identifier of the club to enable.
	 * @return {@link BaseDto} with the values of the enabled club.
	 * @throws ClubNotFoundException 
	 */
	@PutMapping("/{id}/enable")
	public ClubDto enable(@PathVariable("id") Long id) throws EntityNotFoundException {
		return clubService.enable(id);
	}

	/**
	 * Disables one club in the application.
	 * 
	 * @param id Identifier of the club to disable.
	 * @return {@link BaseDto} with the values of the disabled club.
	 * @throws ClubNotFoundException 
	 */
	@PutMapping("/{id}/disable")
	public ClubDto disable(@PathVariable("id") Long id) throws EntityNotFoundException {
		return clubService.disable(id);
	}
}
