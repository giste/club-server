package org.giste.club.server.repository;

import org.giste.club.server.entity.NonRemovableEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BaseRepository<T extends NonRemovableEntity> extends Repository<T, Long> {

	T findOne(Long id);
	
	Iterable<T> findAll();
	
	T save(T entity);
	
	void delete(T entity);
}
