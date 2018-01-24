package org.giste.club.server.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.giste.club.common.dto.SeasonDto;
import org.giste.util.dto.DtoTestHelper;

/**
 * Helper for testing seasons.
 * 
 * @author Giste
 */
public class SeasonTestHelper implements DtoTestHelper<SeasonDto> {

	@Override
	public List<SeasonDto> getDtoList() {
		SeasonDto season1 = new SeasonDto(1L, 2017);
		SeasonDto season2 = new SeasonDto(2L, 2018);

		List<SeasonDto> seasonList = new ArrayList<>();
		seasonList.add(season1);
		seasonList.add(season2);

		return seasonList;
	}

	@Override
	public SeasonDto getNewDto() {
		return new SeasonDto(null, 2019);
	}

	@Override
	public void invalidateDto(SeasonDto dto) {
		dto.setYear(2000);
	}

	@Override
	public void modifyDto(SeasonDto dto) {
		dto.setYear(2030);
	}

	@Override
	public Class<SeasonDto> getDtoType() {
		return SeasonDto.class;
	}

	@Override
	public Class<SeasonDto[]> getArrayType() {
		return SeasonDto[].class;
	}

	@Override
	public Optional<List<String>> getInvalidProperties() {
		List<String> invalidProperties = new ArrayList<>();
		invalidProperties.add("year");
		
		return Optional.of(invalidProperties);
	}

	@Override
	public Optional<Set<String>> getDuplicatedProperties() {
		Set<String> invalidProperties = new HashSet<>();
		invalidProperties.add("year");
		
		return Optional.of(invalidProperties);
	}

	@Override
	public Map<String, Object> getPropertiesToCheck(SeasonDto dto) {
		Map<String, Object> properties = new HashMap<>();
		properties.put("year", dto.getYear());

		return properties;
	}

}
