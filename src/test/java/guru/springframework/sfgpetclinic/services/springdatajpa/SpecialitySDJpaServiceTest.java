package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //check numero volte chiamato il metodo del obj

        verify(specialtyRepository, times(2)).deleteById(1L);

        verify(specialtyRepository, atLeast(1)).deleteById(anyLong());

        verify(specialtyRepository, atMost(5)).deleteById(1L);

        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void deleteByIdBDD() {
        //given - none

        //hen
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        //then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void delete() {

        //given
        Speciality speciality = new Speciality();

        //when
        specialitySDJpaService.delete(speciality);

        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality specialtyFound = specialitySDJpaService.findById(1L);

        assertEquals(speciality, specialtyFound);

        verify(specialtyRepository, times(1)).findById(1L);
    }

    @Test
    void findByIDBDDTest() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality specialtyFound = specialitySDJpaService.findById(1L);

        //then
        assertEquals(speciality, specialtyFound);
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDoThrow() {
        //when
        doThrow(new RuntimeException("when")).when(specialtyRepository).deleteById(anyLong());

        //then
        assertThrows(RuntimeException.class, () -> specialtyRepository.deleteById(anyLong()));
        verify(specialtyRepository).deleteById(anyLong());
    }

    @Test
    void testDoThrowBDD() {

        //given
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("boom"));


        assertThrows(RuntimeException.class, () -> specialtyRepository.findById(anyLong()));
        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testDoThrowBDD2() {


        willThrow(new RuntimeException()).given(specialtyRepository).findById(anyLong());


        assertThrows(RuntimeException.class, () -> specialtyRepository.findById(anyLong()));
        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testSaveLamba() {
        //given
        String stringToMatch = "TO_MATCH";
        Speciality speciality = new Speciality();
        speciality.setDescription(stringToMatch);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        given(specialtyRepository.save(argThat(spec -> spec.getDescription().equals("TO_MATCH")))).willReturn(savedSpeciality);

        //when
        Speciality returnSpeciality = specialitySDJpaService.save(speciality);

        //then
        assertEquals(1L, returnSpeciality.getId().longValue());

    }

    @Test
    void testSaveLambaNotMatch() {
        //given
        String stringToMatch = "TO_MATCH";
        Speciality speciality = new Speciality();
        speciality.setDescription(stringToMatch);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        given(specialtyRepository.save(argThat(spec -> spec.getDescription().equals("asd")))).willReturn(savedSpeciality);

        //when
        Speciality returnSpeciality = specialitySDJpaService.save(speciality);

        //then
        assertNull(returnSpeciality);

    }
}