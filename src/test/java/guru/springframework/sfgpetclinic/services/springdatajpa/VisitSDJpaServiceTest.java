package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>(Arrays.asList(new Visit(), new Visit()));

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> visitsFound = visitSDJpaService.findAll();

        assertEquals(visits, visitsFound);
        verify(visitRepository, times(1)).findAll();
    }

    @Test
    void findAllBDD() {
        //given
        Set<Visit> visits = new HashSet<>(Arrays.asList(new Visit(), new Visit()));
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> visitsFound = visitSDJpaService.findAll();

        //then
        assertEquals(visits, visitsFound);
        then(visitRepository).should().findAll();
    }

    @Test
    void findById() {
        Visit visit = new Visit();

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit visitFound = visitSDJpaService.findById(anyLong());

        assertEquals(visit, visitFound);
        verify(visitRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit();

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = visitSDJpaService.save(new Visit());

        assertEquals(visit,savedVisit);
        verify(visitRepository,times(1)).save(any(Visit.class));

    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}