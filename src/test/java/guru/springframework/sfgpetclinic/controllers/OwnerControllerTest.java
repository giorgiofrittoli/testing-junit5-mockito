package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.BindingResultError;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> captor;

    @Test
    void processCreationFormErrors() {

        //given
        Owner owner = new Owner(11L, "john", "buck");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String view = ownerController.processCreationForm(owner, bindingResult);

        //then
        assertEquals("owners/createOrUpdateOwnerForm", view);
        then(ownerService).should(never()).save(any(Owner.class));

    }

    @Test
    void processCreationFormOK() {

        //given
        Owner owner = new Owner(11L, "john", "buck");
        given(ownerService.save(owner)).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);

        //when
        String view = ownerController.processCreationForm(owner, bindingResult);

        //then
        assertEquals("redirect:/owners/11", view);
        then(ownerService).should(atMost(1)).save(any(Owner.class));
    }

    @Test
    void processFindFormWildCardString() {
        //given
        Owner owner = new Owner(11L, "john", "buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        //when
        String view = ownerController.processFindForm(owner,bindingResult,null);

        //then
        assertEquals("%buck%",captor.getValue());
    }

    @Test
    void processFindFormWildCardStringAnnotation() {
        //given
        Owner owner = new Owner(11L, "john", "buck");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        //when
        String view = ownerController.processFindForm(owner,bindingResult,null);

        //then
        assertEquals("%buck%",captor.getValue());
    }
}