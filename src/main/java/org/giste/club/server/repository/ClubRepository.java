package org.giste.club.server.repository;

import org.giste.club.server.entity.Club;

/**
 * Interface for club repository.
 * 
 * @author Giste
 */
public interface ClubRepository extends BaseRepository<Club> {

	/**
	 * Gets a club given its acronym.
	 * 
	 * @param acronym The acronym of the club to find.
	 * @return {@link Club} looked up.
	 */
	Club findByAcronym(String acronym);
}
