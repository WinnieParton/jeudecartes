package esgi.infra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.GetByIdHeroServiceService;
import esgi.infra.service.RetrieveHeroCombatsService;

@ExtendWith(MockitoExtension.class)
public class HistoryCombatHeroControllerTest {
    @Mock
    private RetrieveHeroCombatsService combatsService;

    @Mock
    private GetByIdHeroServiceService getByIdHeroServiceService;

    @InjectMocks
    private HistoryCombatHeroController historyCombatHeroController;

    @Test
    public void retrieveHeroCombats_existingHero_returnsOk() {
        // Given
        Long idhero = 1L;
        HeroDomain hero = new HeroDomain();
        hero.setId(idhero);
        when(getByIdHeroServiceService.getById(idhero)).thenReturn(hero);
        List<CombatDomain> combats = new ArrayList<>();
        combats.add(new CombatDomain());
        when(combatsService.retrieveHeroCombats(hero)).thenReturn(combats);

        // When
        ResponseEntity<?> result = historyCombatHeroController.retrieveHeroCombats(idhero);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
        assertEquals(combats, ((MessageResponse) result.getBody()).getMap().get("data"));
    }

    // @Test
    // public void retrieveHeroCombats_nonExistingHero_returnsNotFound() {
    //     when(getByIdHeroServiceService.getById(anyLong())).thenReturn(Optional.empty());
    
    //     ResponseEntity<?> result = historyCombatHeroController.retrieveHeroCombats(1L);
    
    //     assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    //     assertEquals("Hero not found !", ((MessageResponse) result.getBody()).getMap().get("message"));
    // }
}
