package esgi.infra.controller;

import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.dto.CombatDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CombatControllerTest {
        @Mock
        private GetByIdPlayerService getByIdPlayerService;
        @Mock
        private EngageCombatService engageCombatService;
        @Mock
        private GetByIdHeroServiceService getByIdHeroServiceService;
        @Mock
        private VerifyHeroInDeckPlayerService verifyHeroInDeckPlayerService;
        @Mock
        private VerifyStatusCombatService verifyStatusCombatService;
        @Mock
        private VerifyAvailableHeroService verifyAvailableHeroService;
        @Mock
        private FindByHeroCombatService findByHeroCombatService;
        @InjectMocks
        private CombatController combatController;

        @Test
        public void engageCombat_ShouldReturnSuccessResponse_WhenHeroAttackerAndHeroDefenderAreAvailableAndInDeck() {
                // Given
                CombatDto gameStartDto = new CombatDto();
                gameStartDto.setAttackerPlayer(1L);
                gameStartDto.setAttackerHero(1L);
                gameStartDto.setDefenderPlayer(2L);
                gameStartDto.setDefenderHero(2L);

                PlayerDomain currentPlayer = new PlayerDomain();
                currentPlayer.setId(1L);

                HeroDomain currentHero = new HeroDomain();
                currentHero.setId(1L);

                PlayerDomain adversePlayer = new PlayerDomain();
                adversePlayer.setId(2L);

                HeroDomain adverseHero = new HeroDomain();
                adverseHero.setId(2L);

                when(getByIdPlayerService.getById(gameStartDto.getAttackerPlayer())).thenReturn(currentPlayer);
                when(getByIdHeroServiceService.getById(gameStartDto.getAttackerHero())).thenReturn(currentHero);
                when(verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(currentPlayer, currentHero)).thenReturn(true);
                when(verifyAvailableHeroService.verifyAvailableHeroService(currentHero)).thenReturn(true);

                when(getByIdPlayerService.getById(gameStartDto.getDefenderPlayer())).thenReturn(adversePlayer);
                when(getByIdHeroServiceService.getById(gameStartDto.getDefenderHero())).thenReturn(adverseHero);
                when(verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(adversePlayer, adverseHero)).thenReturn(true);
                when(verifyAvailableHeroService.verifyAvailableHeroService(adverseHero)).thenReturn(true);

                when(verifyStatusCombatService.verifyStatusCombat(currentHero, adverseHero)).thenReturn(false);

                // When
                ResponseEntity<?> result = combatController.engageCombat(gameStartDto);

                // Then
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals("Combat finish !", ((MessageResponse) result.getBody()).getMap().get("message"));
                assertNotNull(((MessageResponse) result.getBody()).getMap().get("data"));

                when(verifyStatusCombatService.verifyStatusCombat(currentHero, adverseHero)).thenReturn(true);
                when(engageCombatService.engageCombat(currentHero, adverseHero)).thenReturn("result");

                // When
                ResponseEntity<?> result1 = combatController.engageCombat(gameStartDto);

                // Then
                assertEquals(HttpStatus.CREATED, result1.getStatusCode());
                assertNotNull(result1.getBody());
                assertEquals("Action succes !", ((MessageResponse) result1.getBody()).getMap().get("message"));
                assertNotNull(((MessageResponse) result1.getBody()).getMap().get("data"));

        }

}
