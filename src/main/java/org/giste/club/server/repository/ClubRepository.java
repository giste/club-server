package org.giste.club.server.repository;

import org.giste.club.server.entity.Club;
import org.giste.spring.server.repository.CrudeRepository;

/**
 * Interface for club repository.
 * 
 * @author Giste
 */
public interface ClubRepository extends CrudeRepository<Club> {

	/**
	 * Gets a club given its acronym.
	 * 
	 * @param acronym The acronym of the club to find.
	 * @return {@link Club} looked up.
	 */
	Club findByAcronym(String acronym);
}
