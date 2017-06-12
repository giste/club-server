package org.giste.club.server.repository;

import java.util.Optional;

import org.giste.club.server.entity.User;
import org.giste.spring.server.repository.CrudRepository;

/**
 * Interface for user persistence management.
 * 
 * @author Giste
 */
public interface UserRepository extends CrudRepository<User> {
	
	Optional<User> findByEmail(String email);
}
