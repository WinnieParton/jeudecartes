package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.DeckDomain;
import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class VerifyHeroInDeckPlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void testVerifyHeroInDeckPlayerService() {
        PlayerDomain player = mock(PlayerDomain.class);
        HeroDomain hero = mock(HeroDomain.class);
        DeckDomain deck = mock(DeckDomain.class);
        List<HeroDomain> heros = Arrays.asList(hero);

        when(player.getDeck()).thenReturn(deck);
        when(deck.getHeros()).thenReturn(heros);

        Boolean result = playerServiceImpl.verifyHeroInDeckPlayerService(player, hero);

        assertTrue(result);
    }

}
