package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.PlayerDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SearchPlayersServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testSearchPlayers_PlayersExist() {
        List<PlayerEntity> players = Arrays.asList(
                new PlayerEntity("player1"),
                new PlayerEntity("player2"),
                new PlayerEntity("player3"));
        when(playerRepository.findAll()).thenReturn(players);

        List<PlayerDomain> result = playerService.searchPlayers();

        assertEquals(3, result.size());
        assertEquals("player1", result.get(0).getPseudo());
        assertEquals("player2", result.get(1).getPseudo());
        assertEquals("player3", result.get(2).getPseudo());
    }

    @Test
    public void testSearchPlayers_PlayersDoNotExist() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<PlayerDomain> result = playerService.searchPlayers();

        assertEquals(0, result.size());
    }

    @Test
    public void testSearchPlayers() {
        List<PlayerEntity> playerEntities = new ArrayList<>();
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntities.add(playerEntity);

        DeckEntity deckEntity = new DeckEntity();
        playerEntity.setDeck(deckEntity);

        List<HeroEntity> heroEntities = new ArrayList<>();
        HeroEntity heroEntity = new HeroEntity();
        heroEntities.add(heroEntity);

        deckEntity.setHeros(heroEntities);

        when(playerRepository.findAll()).thenReturn(playerEntities);

        List<PlayerDomain> result = playerService.searchPlayers();
        assertEquals(1, result.size());
    }
}
