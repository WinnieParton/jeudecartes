package esgi.infra.service;

import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.service.impl.DeckServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyJetonServiceTest {

    @InjectMocks
    private DeckServiceImpl deckService;
    
    @Mock
    private PlayerDomain player;

    @Test
    public void testVerifJetonByPackPlayerForArgent() {
      when(player.getJeton()).thenReturn(1);
      Boolean result = deckService.verifJetonByPackPlayer(PackHeroTypeDomain.argent, player);
      assertEquals(Boolean.TRUE, result);
    }
  
    @Test
    public void testVerifJetonByPackPlayerForDiamant() {
      when(player.getJeton()).thenReturn(2);
      Boolean result = deckService.verifJetonByPackPlayer(PackHeroTypeDomain.diamant, player);
      assertEquals(Boolean.TRUE, result);
    }
  
    @Test
    public void testVerifJetonByPackPlayerForArgentWhenJetonIsLessThan1() {
      when(player.getJeton()).thenReturn(0);
      Boolean result = deckService.verifJetonByPackPlayer(PackHeroTypeDomain.argent, player);
      assertEquals(Boolean.FALSE, result);
    }
  
    @Test
    public void testVerifJetonByPackPlayerForDiamantWhenJetonIsLessThan2() {
      when(player.getJeton()).thenReturn(1);
      Boolean result = deckService.verifJetonByPackPlayer(PackHeroTypeDomain.diamant, player);
      assertEquals(Boolean.FALSE, result);
    }
}
