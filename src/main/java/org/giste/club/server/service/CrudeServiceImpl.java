package org.giste.club.server.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.giste.club.server.entity.NonRemovableEntity;
import org.giste.club.server.service.exception.EntityNotFoundException;
import org.giste.util.dto.NonRemovableDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

public abstract class CrudeServiceImpl<DTO extends NonRemovableDto, ENT extends NonRemovableEntity> implements CrudeService<DTO> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected CrudRepository<ENT, Long> repository;

	public CrudeServiceImpl(CrudRepository<ENT, Long> repository) {
		this.repository = repository;
	}

	@Override
	public DTO create(DTO dto) {
		ENT entity = getEntityFromDto(dto);

		ENT savedEntity = repository.save(entity);

		return getDtoFromEntity(savedEntity);
	}

	@Override
	public DTO findById(Long id) throws EntityNotFoundException {
		ENT entity = getOneSafe(id);

		return getDtoFromEntity(entity);
	}

	@Override
	public List<DTO> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(entity -> getDtoFromEntity(entity))
				.collect(Collectors.toList());
	}

	@Override
	public DTO update(DTO dto) throws EntityNotFoundException {
		// Find entity to update.
		ENT entity = getOneSafe(dto.getId());
		// Update entity.
		entity = updateEntityFromDto(entity, dto);
		ENT savedEntity = repository.save(entity);

		return getDtoFromEntity(savedEntity);
	}

	@Override
	public DTO deleteById(Long id) throws EntityNotFoundException {
		// Find entity to delete.
		ENT entity = getOneSafe(id);

		repository.delete(entity);

		return getDtoFromEntity(entity);
	}

	@Override
	public DTO enable(Long id) throws EntityNotFoundException {
		// Find entity to enable.
		ENT entity = getOneSafe(id);

		// Enable entity.
		entity.setEnabled(true);
		ENT savedEntity = repository.save(entity);

		return getDtoFromEntity(savedEntity);
	}

	@Override
	public DTO disable(Long id)  throws EntityNotFoundException{
		// Find entity to disable.
		ENT entity = getOneSafe(id);

		// Disable entity.
		entity.setEnabled(false);
		ENT savedEntity = repository.save(entity);

		return getDtoFromEntity(savedEntity);
	}

	private ENT getOneSafe(Long id) throws EntityNotFoundException {
		ENT entity = repository.findOne(id);
		if (entity == null) {
			LOGGER.debug("Throwing EntityNotFoundException");
			throw new EntityNotFoundException(id);
		} else {
			return entity;
		}
	}

	/**
	 * Gets a {@link NonRemovableEntity} from a given {@link NonRemovableDto}.
	 * 
	 * @param dto {@link NonRemovableDto} for getting the entity.
	 * @return The entity.
	 */
	protected abstract ENT getEntityFromDto(DTO dto);

	/**
	 * Gets a {@link NonRemovableDto} from a given {@link NonRemovableEntity}.
	 * 
	 * @param entity {@link NonRemovableEntity} for getting the DTO.
	 * @return The {@link NonRemovableDto}.
	 */
	protected abstract DTO getDtoFromEntity(ENT entity);

	/**
	 * Updates a given {@link NonRemovableEntity} with the values from a {@link NonRemovableDto}.
	 * 
	 * @param entity The {@link NonRemovableEntity} to update.
	 * @param dto The {@link NonRemovableDto} with the values for updating the entity.
	 * @return The updated {@link NonRemovableEntity}
	 */
	protected abstract ENT updateEntityFromDto(ENT entity, DTO dto);
}