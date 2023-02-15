package esgi.infra.service;

import esgi.domain.DeckDomain;
import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyHeroInDeckPlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void testVerifyHeroInDeckPlayerService() {
        PlayerDomain player = mock(PlayerDomain.class);
        HeroDomain hero = mock(HeroDomain.class);
        DeckDomain deck = mock(DeckDomain.class);
        List<HeroDomain> heros = Collections.singletonList(hero);

        when(player.getDeck()).thenReturn(deck);
        when(deck.getHeros()).thenReturn(heros);

        Boolean result = playerServiceImpl.verifyHeroInDeckPlayerService(player, hero);

        assertTrue(result);
    }

}
