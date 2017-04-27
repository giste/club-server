package org.giste.club.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.giste.club.server.service.CrudeService;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.giste.club.server.service.exception.DuplicatedClubAcronymException;
import org.giste.club.server.service.exception.EntityNotFoundException;
import org.giste.util.dto.BaseDto;
import org.giste.util.dto.NonRemovableDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Superclass for all the CRUDE controllers. Provide methods to create, read,
 * update, disable and enable the given entity. Entity has to be a subclass of
 * {@link NonRemovableDto}.
 * 
 * @author Giste
 *
 * @param <T> {@link NonRemovableDto} of the entity to be managed by the
 *            controller.
 */
public class RestCrudeController<T extends NonRemovableDto> {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected final CrudeService<T> service;

	public RestCrudeController(CrudeService<T> service) {
		this.service = service;
	}

	/**
	 * Creates a new Entity.
	 * 
	 * @param club BaseDto object with values for new club.
	 * @return BaseDto Object with the values of the new created Club.
	 * @throws DuplicatedClubAcronymException If Acronym for new club is in use
	 *             by another club.
	 */
	@PostMapping
	public T create(@RequestBody @Valid final T club) {
		return service.create(club);
	}

	/**
	 * Retrieves one single club.
	 * 
	 * @param id Identifier of the club to retrieve.
	 * @return {@link BaseDto} with the data of the requested club.
	 * @throws ClubNotFoundException If the requested club can't be found.
	 */
	@GetMapping(value = "/{id}")
	public T findById(@PathVariable("id") Long id) throws EntityNotFoundException {
		T club = service.findById(id);

		return club;
	}

	/**
	 * Retrieves all clubs.
	 * 
	 * @return List populated with the {@link BaseDto} of all existent clubs.
	 */
	@GetMapping
	public List<T> findAll() {
		return service.findAll();
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
	public T update(@PathVariable("id") Long id, @RequestBody @Valid final T club) throws EntityNotFoundException {

		// If club identifier is different, overwrite it.
		if (id != club.getId()) {
			LOGGER.debug("Identifier from BaseDto ({}) is different than identifier from URI ({})", club.getId(), id);
			club.setId(id);
		}

		return service.update(club);
	}

	/**
	 * Enables one club in the application.
	 * 
	 * @param id Identifier of the club to enable.
	 * @return {@link BaseDto} with the values of the enabled club.
	 * @throws ClubNotFoundException
	 */
	@PutMapping("/{id}/enable")
	public T enable(@PathVariable("id") Long id) throws EntityNotFoundException {
		return service.enable(id);
	}

	/**
	 * Disables one club in the application.
	 * 
	 * @param id Identifier of the club to disable.
	 * @return {@link BaseDto} with the values of the disabled club.
	 * @throws ClubNotFoundException
	 */
	@PutMapping("/{id}/disable")
	public T disable(@PathVariable("id") Long id) throws EntityNotFoundException {
		return service.disable(id);
	}

}