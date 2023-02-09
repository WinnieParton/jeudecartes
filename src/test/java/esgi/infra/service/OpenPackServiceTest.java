package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.HeroDomain;
import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.DeckServiceImpl;

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
        PlayerDomain player = new PlayerDomain(1L, "player1", 10, null, 0, 0, 0, null, null);
        PackHeroTypeDomain packType = PackHeroTypeDomain.argent;
        List<HeroEntity> heroes = new ArrayList<>();

        heroes.add(new HeroEntity(1L, "hero1", 10, 1, 2, 3,
                SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.RARE, 1,
                true, true, null, null));
        heroes.add(new HeroEntity(2L, "hero2", 10, 1, 2, 3,
                SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON, 1, true, true, null, null));
        heroes.add(new HeroEntity(3L, "hero3", 10, 1, 2, 3,
                SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null));

        when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.LEGENDARY)).thenReturn(heroes);
        when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.RARE)).thenReturn(heroes);
        when(heroRepository.findByRarityAndStatusTrue(RaretyTypeDomain.COMMON)).thenReturn(heroes);
        when(playerRepository.save(null)).thenReturn(player);
        when(deckRepository.save(null)).thenReturn(null);

        List<HeroDomain> result = deckServiceImpl.openPack(player, packType);
        assertEquals(3, result.size());
    }
}