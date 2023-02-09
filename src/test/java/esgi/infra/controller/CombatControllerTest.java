package esgi.infra.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.service.EngageCombatService;
import esgi.infra.service.FindByHeroCombatService;
import esgi.infra.service.GetByIdHeroServiceService;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.VerifyHeroInDeckPlayerService;
import esgi.infra.service.VerifyStatusCombatService;

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
        private FindByHeroCombatService findByHeroCombatService;
        @InjectMocks
        private CombatController combatController;

        @Test
        public void engageCombat_validData_returnsOk() {
        }

        @Test
        public void Combat_invalidData_returnsBadRequest() {
        }
}
