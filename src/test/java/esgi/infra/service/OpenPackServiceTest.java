package esgi.infra.service;

import esgi.domain.*;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.DeckServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenPackServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private DeckServiceImpl deckServiceImpl;

    @Test
    public void openPackTest() {
        PlayerDomain player = new PlayerDomain(1L, "player1", 4, new DeckDomain(), 0, 0, 0, null, null);
        PlayerEntity playerE = new PlayerEntity(1L, "player1", 4, new DeckEntity(), 0, 0, 0, null, null);
        PackHeroTypeDomain packType = PackHeroTypeDomain.argent;
        List<HeroEntity> heroes = new ArrayList<>();
        heroes.add(new HeroEntity(1L, "hero1", 10, 1, 2, 3,
                SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.RARE, 1,
                true, true, null, null));
        heroes.add(new HeroEntity(2L, "hero2", 10, 1, 2, 3,
                SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON, 1, true, true, null, null));
        heroes.add(new HeroEntity(3L, "hero3", 10, 1, 2, 3,
                SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null));

        lenient().when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.LEGENDARY)).thenReturn(heroes);
        lenient().when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.RARE)).thenReturn(heroes);
        lenient().when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.COMMON)).thenReturn(heroes);
        playerE.getDeck().addAll(heroes);
        player.getDeck().setHeros(new ArrayList<HeroDomain>());
        when(playerRepository.save(any())).thenReturn(playerE);
        when(deckRepository.save(any())).thenReturn(playerE.getDeck());

        List<HeroDomain> result = deckServiceImpl.openPack(player, packType);
        assertEquals(3, result.size());
    }
}