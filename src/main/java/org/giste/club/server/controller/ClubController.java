package org.giste.club.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for Club REST API.
 * 
 * @author Giste
 */
@RestController
@RequestMapping("/clubs")
public class ClubController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClubController.class);

	final private ClubService clubService;

	/**
	 * Constructor for Spring.
	 * 
	 * @param clubService Bean for service for managing club requests.
	 * @param messageSource Bean for i18n messages source.
	 */
	public ClubController(final ClubService clubService) {
		this.clubService = clubService;
	}

	/**
	 * Creates a new Club. Accepts POST method with a {@link ClubDto} object.
	 * 
	 * @param club ClubDto object with values for new club.
	 * @return ClubDto Object with the values of the new created Club.
	 * @throws DuplicatedClubAcronymException If Acronym for new club is in use
	 *             by another club.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ClubDto create(@RequestBody @Valid final ClubDto club) throws DuplicatedClubAcronymException {
		return clubService.create(club);
	}

	/**
	 * Retrieves one single club.
	 * 
	 * @param id The identifier of the club to retrieve.
	 * @return {@link ClubDto} with the data of the requested club.
	 * @throws ClubNotFoundException If the requested club can't be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ClubDto findById(@PathVariable("id") Long id) throws ClubNotFoundException {
		ClubDto club = clubService.findById(id);

		return club;
	}

	/**
	 * Retrieves all clubs.
	 * 
	 * @return List populated with the {@link ClubDto} of all existent clubs.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<ClubDto> findAll() {
		return clubService.findAll();
	}

	/**
	 * Updates one club.
	 * 
	 * @param id Identifier of the club to be updated.
	 * @param club {@link ClubDto} with the values of the club to update.
	 * @return {@link ClubDto} with the updated values of the club.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ClubDto update(@PathVariable("id") Long id, @RequestBody @Valid final ClubDto club) {

		// If club identifier is different, overwrite it.
		if (id != club.getId()) {
			LOGGER.debug("Identifier from ClubDto ({}) is different than identifier from URI ({})", club.getId(), id);
			club.setId(id);
		}

		return clubService.update(club);
	}
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.PUT, params = {"active"})
	public ClubDto changeState(@PathVariable("id") Long id, @RequestParam("active") boolean active) {
		return clubService.changeState(id, active);
	}
	
	
	/**
	 * Deletes one club.
	 * 
	 * @param id identifier of the club to delete.
	 * @return {@link ClubDto} with the values of the deleted club.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ClubDto deleteById(@PathVariable("id") Long id) {
		return clubService.deleteById(id);
	}
}
