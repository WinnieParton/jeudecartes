package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.PlayerDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class GetByIdPlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void testGetById() {
        Long id = 1L;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(id);
        playerEntity.setPseudo("player1");
        playerEntity.setJeton(4);

        DeckEntity deckEntity = new DeckEntity();
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setId(1L);
        heroEntity.setName("hero1");
        heroEntity.setNbLifePoints(100);
        heroEntity.setExperience(20);
        heroEntity.setPower(30);
        heroEntity.setArmor(40);
        heroEntity.setSpeciality(SpecialityTypeDomain.ASSASSIN);
        heroEntity.setRarity(RaretyTypeDomain.COMMON);
        heroEntity.setLevel(1);
        heroEntity.setAvailable(true);
        heroEntity.setStatus(true);

        List<HeroEntity> heroEntities = new ArrayList<>();
        heroEntities.add(heroEntity);
        deckEntity.setHeros(heroEntities);
        playerEntity.setDeck(deckEntity);
        playerEntity.setNbrTirage(10);
        playerEntity.setNbrTiragePackArgent(5);
        playerEntity.setNbrTiragePackDiament(3);

        when(playerRepository.findById(id)).thenReturn(Optional.of(playerEntity));

        PlayerDomain player = playerServiceImpl.getById(id);

        assertEquals(id, player.getId());
        assertEquals("player1", player.getPseudo());
        assertEquals(4, player.getJeton());
        assertEquals(1, player.getDeck().getHeros().size());
        assertEquals(1L, player.getDeck().getHeros().get(0).getId().longValue());
        assertEquals("hero1", player.getDeck().getHeros().get(0).getName());
        assertEquals(100, player.getDeck().getHeros().get(0).getNbLifePoints());
        assertEquals(20, player.getDeck().getHeros().get(0).getExperience());
        assertEquals(30, player.getDeck().getHeros().get(0).getPower());
        assertEquals(40, player.getDeck().getHeros().get(0).getArmor());
        assertEquals("speciality", player.getDeck().getHeros().get(0).getSpeciality());
        assertEquals(RaretyTypeDomain.COMMON, player.getDeck().getHeros().get(0).getRarity());
        assertEquals(1, player.getDeck().getHeros().get(0).getLevel());
        assertTrue(player.getDeck().getHeros().get(0).isAvailable());
    }
}
