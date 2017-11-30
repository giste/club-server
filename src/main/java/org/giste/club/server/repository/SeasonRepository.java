package org.giste.club.server.repository;

import java.util.Optional;

import org.giste.club.server.entity.Season;
import org.giste.spring.server.repository.CrudRepository;

public interface SeasonRepository extends CrudRepository<Season> {
	
	/**
	 * Finds the season filtered by current state.
	 * 
	 * @return The current season.
	 */
	Optional<Season> findFirstByOrderByYearDesc();

}
