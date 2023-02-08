package esgi.infra.service.desk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.DeckServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OpenPackServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private DeckServiceImpl deckServiceImpl;

    @Test
    public void testOpenPack() {
        // given
        // Player player = new Player();
        // player.setJeton(4);
        // Deck deck = new Deck();
        // player.setDeck(deck);
        // Hero hero1 = new Hero();
        // hero1.setRarity(RaretyType.LEGENDARY);
        // hero1.setAvailable(true);

        // Hero hero2 = new Hero();
        // hero2.setRarity(RaretyType.RARE);
        // hero2.setAvailable(true);

        // Hero hero3 = new Hero();
        // hero3.setRarity(RaretyType.COMMON);
        // hero3.setAvailable(true);

        // List<Hero> heroList = Arrays.asList(hero1, hero2, hero3);

        // PackHeroType packType = PackHeroType.diamant;

        // // when
        // when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        // when(heroRepository.findByRarityAndStatusTrue(RaretyType.LEGENDARY)).thenReturn(Arrays.asList(hero1));
        // when(heroRepository.findByRarityAndStatusTrue(RaretyType.RARE)).thenReturn(Arrays.asList(hero2));
        // when(heroRepository.findByRarityAndStatusTrue(RaretyType.COMMON)).thenReturn(Arrays.asList(hero3));

        // List<Hero> result = deckServiceImpl.openPack(player, packType);

        // // then
        // assertEquals(3, result.size());
        // assertEquals(3, player.getDeck().getHeros().size());
        // assertEquals(3, player.getNbrTiragePackDiament());
        // assertEquals(3, player.getNbrTirage());
        // assertEquals(2, player.getJeton());
        // assertEquals(heroList, result);
        // assertTrue(!hero1.isAvailable());
        // assertTrue(!hero2.isAvailable());
        // assertTrue(!hero3.isAvailable());

    }
}
