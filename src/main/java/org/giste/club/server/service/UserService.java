package org.giste.club.server.service;

import org.giste.club.common.dto.UserDto;
import org.giste.spring.server.service.CrudService;
import org.giste.spring.server.service.exception.EntityNotFoundException;

/**
 * Interface for service to manage users.
 * 
 * @author Giste
 */
public interface UserService extends CrudService<UserDto> {

	UserDto findByEmail(String email) throws EntityNotFoundException;
}
