package org.giste.club.server.controller;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.service.ClubService;
import org.giste.spring.server.controller.RestCrudeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 * Constructs a REST controller for club entities. This controller is a
	 * subclass of {@link RestCrudeController}.
	 * 
	 * @param clubService {@link ClubService} for managing club requests.
	 */
	public ClubController(final ClubService clubService) {
		super(clubService);
	}
	
	
}
