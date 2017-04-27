package org.giste.club.server.controller;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for Club REST API.
 * 
 * @author Giste
 */
@RestController
@RequestMapping("/rest/clubs")
public class ClubController extends RestCrudeController<ClubDto> {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor for Spring.
	 * 
	 * @param clubService Bean for service for managing club requests.
	 */
	public ClubController(final ClubService clubService) {
		super(clubService);
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
		return service.deleteById(id);
	}
}
