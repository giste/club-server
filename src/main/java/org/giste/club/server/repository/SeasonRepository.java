package org.giste.club.server.repository;

import java.util.Optional;

import org.giste.club.server.entity.Season;
import org.giste.spring.data.repository.CrudRepository;

/**
 * Interface to persist seasons.
 * 
 * @author Giste
 */
public interface SeasonRepository extends CrudRepository<Season> {

	/**
	 * Finds the current season. It's the first season when ordered by descending
	 * year.
	 * 
	 * @return The current season.
	 */
	Optional<Season> findFirstByOrderByYearDesc();

}
