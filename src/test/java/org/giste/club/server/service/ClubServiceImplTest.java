package org.giste.club.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.giste.club.common.dto.ClubDto;
import org.giste.club.server.entity.Club;
import org.giste.club.server.repository.ClubRepository;
import org.giste.club.server.service.exception.ClubNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ClubServiceImplTest {

	@MockBean
	private ClubRepository clubRepository;

	private ClubService clubService;

	@Before
	public void setup() {
		clubService = new ClubServiceImpl(clubRepository);
	}

	@Test
	public void findAllIsValid() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		final Club club2 = new Club(1L, "Club 2", "CLUB2", false);
		List<Club> clubList = new ArrayList<Club>();
		clubList.add(club1);
		clubList.add(club2);
		when(clubRepository.findAll()).thenReturn(clubList);

		List<ClubDto> readList = clubService.findAll();

		verify(clubRepository).findAll();
		verifyNoMoreInteractions(clubRepository);
		assertThat(readList.size(), is(clubList.size()));
	}

	@Test
	public void findAllIsEmpty() {
		List<Club> clubList = new ArrayList<Club>();
		when(clubRepository.findAll()).thenReturn(clubList);

		List<ClubDto> readList = clubService.findAll();

		verify(clubRepository).findAll();
		verifyNoMoreInteractions(clubRepository);
		assertThat(readList.size(), is(clubList.size()));
	}

	@Test
	public void findByIdIsValid() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		when(clubRepository.getOne(1L)).thenReturn(club1);

		ClubDto clubDto = clubService.findById(club1.getId());

		verify(clubRepository).getOne(1L);
		verifyNoMoreInteractions(clubRepository);
		assertThat(clubDto.getId().longValue(), is(club1.getId().longValue()));
		assertThat(clubDto.getName(), is(club1.getName()));
		assertThat(clubDto.getAcronym(), is(club1.getAcronym()));
		assertThat(clubDto.isEnabled(), is(club1.isEnabled()));
	}

	@Test
	public void findByIdClubNotFound() {
		when(clubRepository.findOne(anyLong())).thenThrow(new EntityNotFoundException());

		try {
			clubService.findById(1L);
			
			fail("Expected ClubNotFoundException");
		} catch (ClubNotFoundException e) {
			assertThat(e.getIdNotFound(), is(1L));
		}
	}

	@Test
	public void createIsOk() throws Exception {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		final ClubDto clubDto1 = new ClubDto(1L, "Club 1", "CLUB1", false);
		when(clubRepository.save(any(Club.class))).thenReturn(club1);
		
		ClubDto readClub = clubService.create(clubDto1);
		
		ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
		verify(clubRepository).save(clubCaptor.capture());
		verifyNoMoreInteractions(clubRepository);
		assertThat(readClub.getId(), is(club1.getId()));
		assertThat(readClub.getName(), is(club1.getName()));
		assertThat(readClub.getAcronym(), is(club1.getAcronym()));
		assertThat(readClub.isEnabled(), is(club1.isEnabled()));

		Club capturedClub = clubCaptor.getValue();
		assertThat(capturedClub.getId(), is(clubDto1.getId()));
		assertThat(capturedClub.getName(), is(clubDto1.getName()));
		assertThat(capturedClub.getAcronym(), is(clubDto1.getAcronym()));
		assertThat(capturedClub.isEnabled(), is(clubDto1.isEnabled()));
	}
	
	@Test
	public void updateIsOk() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		final ClubDto clubDto1 = new ClubDto(1L, "Club 1", "CLUB1", true);
		when(clubRepository.getOne(clubDto1.getId())).thenReturn(club1);
		when(clubRepository.save(any(Club.class))).thenReturn(club1);
		
		ClubDto readClub = clubService.update(clubDto1);
		
		ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
		verify(clubRepository).getOne(clubDto1.getId());
		verify(clubRepository).save(clubCaptor.capture());
		verifyNoMoreInteractions(clubRepository);
		assertThat(readClub.getId(), is(club1.getId()));
		assertThat(readClub.getName(), is(club1.getName()));
		assertThat(readClub.getAcronym(), is(club1.getAcronym()));
		assertThat(readClub.isEnabled(), is(club1.isEnabled()));
		
		Club capturedClub = clubCaptor.getValue();
		assertThat(capturedClub.getId(), is(clubDto1.getId()));
		assertThat(capturedClub.getName(), is(clubDto1.getName()));
		assertThat(capturedClub.getAcronym(), is(clubDto1.getAcronym()));
		assertThat(capturedClub.isEnabled(), is(clubDto1.isEnabled()));
	}
	
	@Test
	public void deleteByIdIsOk() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		when(clubRepository.getOne(club1.getId())).thenReturn(club1);
		
		ClubDto readClub = clubService.deleteById(club1.getId());
		
		verify(clubRepository).getOne(club1.getId());
		ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
		verify(clubRepository).delete(clubCaptor.capture());
		verifyNoMoreInteractions(clubRepository);

		assertThat(readClub.getId(), is(club1.getId()));
		assertThat(readClub.getName(), is(club1.getName()));
		assertThat(readClub.getAcronym(), is(club1.getAcronym()));
		assertThat(readClub.isEnabled(), is(club1.isEnabled()));
		
		Club capturedClub = clubCaptor.getValue();
		assertThat(capturedClub.getId(), is(club1.getId()));
		assertThat(capturedClub.getName(), is(club1.getName()));
		assertThat(capturedClub.getAcronym(), is(club1.getAcronym()));
		assertThat(capturedClub.isEnabled(), is(club1.isEnabled()));
	}
	
	@Test
	public void enableIsOk() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", false);
		final Club enabledClub1 = new Club(1L, "Club 1", "CLUB1", true);
		when(clubRepository.getOne(club1.getId())).thenReturn(club1);
		when(clubRepository.save(any(Club.class))).thenReturn(enabledClub1);
		
		ClubDto readClub = clubService.enable(club1.getId());
		
		verify(clubRepository).getOne(club1.getId());
		ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
		verify(clubRepository).save(clubCaptor.capture());
		
		assertThat(readClub.getId(), is(enabledClub1.getId()));
		assertThat(readClub.getName(), is(enabledClub1.getName()));
		assertThat(readClub.getAcronym(), is(enabledClub1.getAcronym()));
		assertThat(readClub.isEnabled(), is(enabledClub1.isEnabled()));

		Club capturedClub = clubCaptor.getValue();
		assertThat(capturedClub.getId(), is(club1.getId()));
		assertThat(capturedClub.getName(), is(club1.getName()));
		assertThat(capturedClub.getAcronym(), is(club1.getAcronym()));
		assertThat(capturedClub.isEnabled(), is(club1.isEnabled()));
	}

	@Test
	public void disableIsOk() {
		final Club club1 = new Club(1L, "Club 1", "CLUB1", true);
		final Club disabledClub1 = new Club(1L, "Club 1", "CLUB1", false);
		when(clubRepository.getOne(club1.getId())).thenReturn(club1);
		when(clubRepository.save(any(Club.class))).thenReturn(disabledClub1);
		
		ClubDto readClub = clubService.disable(club1.getId());
		
		verify(clubRepository).getOne(club1.getId());
		ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
		verify(clubRepository).save(clubCaptor.capture());
		
		assertThat(readClub.getId(), is(disabledClub1.getId()));
		assertThat(readClub.getName(), is(disabledClub1.getName()));
		assertThat(readClub.getAcronym(), is(disabledClub1.getAcronym()));
		assertThat(readClub.isEnabled(), is(disabledClub1.isEnabled()));

		Club capturedClub = clubCaptor.getValue();
		assertThat(capturedClub.getId(), is(club1.getId()));
		assertThat(capturedClub.getName(), is(club1.getName()));
		assertThat(capturedClub.getAcronym(), is(club1.getAcronym()));
		assertThat(capturedClub.isEnabled(), is(club1.isEnabled()));
	}
}
