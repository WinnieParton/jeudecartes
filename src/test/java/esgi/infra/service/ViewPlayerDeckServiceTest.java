package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
public class ViewPlayerDeckServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void viewPlayerDeckTest() {
        // given
        PlayerDomain player = mock(PlayerDomain.class);
        DeckDomain deck = mock(DeckDomain.class);
        List<HeroDomain> heros = new ArrayList<>();
        heros.add(mock(HeroDomain.class));
        heros.add(mock(HeroDomain.class));
        when(player.getDeck()).thenReturn(deck);
        when(deck.getHeros()).thenReturn(heros);

        // when
        List<HeroDomain> result = playerServiceImpl.viewPlayerDeck(player);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(player, times(1)).getDeck();
        verify(deck, times(1)).getHeros();
    }
}
