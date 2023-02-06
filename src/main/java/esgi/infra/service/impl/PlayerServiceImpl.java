package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.Deck;
import esgi.domain.Hero;
import esgi.domain.Player;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.player.CreatePlayerService;
import esgi.infra.service.player.GetByIdPlayerService;
import esgi.infra.service.player.IsPseudoPlayerExistService;
import esgi.infra.service.player.SearchPlayersService;
import esgi.infra.service.player.VerifyHeroInDeckPlayerService;
import esgi.infra.service.player.ViewPlayerDeckService;

@Service
@Transactional
public class PlayerServiceImpl
        implements CreatePlayerService, SearchPlayersService,
        ViewPlayerDeckService, IsPseudoPlayerExistService, GetByIdPlayerService, VerifyHeroInDeckPlayerService {

    private PlayerRepository playerRepository;

    private DeckRepository deckRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, DeckRepository deckRepository) {
        this.playerRepository = playerRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    public Player createPlayer(String pseudo) {
        PlayerEntity play = new PlayerEntity(pseudo);
        DeckEntity deck = new DeckEntity();
        deck = deckRepository.save(deck);
        play.setDeck(deck);
        play = playerRepository.save(play);

        return new Player(play.getId(), play.getPseudo(), play.getCreatedAt(), play.getUpdatedAt());
    }

    @Override
    public List<Player> searchPlayers() {
        var player = new ArrayList<Player>();
        for (PlayerEntity playEntity : playerRepository.findAll()) {
            var heroL = new ArrayList<Hero>();
            for (HeroEntity heroEntity : playEntity.getDeck().getHeros()) {
                var hero = new Hero(heroEntity.getId(), heroEntity.getName(),
                        heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                        heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                        heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                        heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

                heroL.add(hero);
            }

            var deck = new Deck(playEntity.getDeck().getId(), heroL, playEntity.getDeck().getCreatedAt(),
                    playEntity.getDeck().getUpdatedAt());

            var play = new Player(playEntity.getId(), playEntity.getPseudo(), playEntity.getJeton(),
                    deck, playEntity.getNbrTirage(), playEntity.getNbrTiragePackArgent(),
                    playEntity.getNbrTiragePackDiament(),
                    playEntity.getCreatedAt(), playEntity.getUpdatedAt());

            player.add(play);
        }
        return player;
    }

    @Override
    public List<Hero> viewPlayerDeck(Player player) {
        return player.getDeck().getHeros();
    }

    @Override
    public Optional<Player> findByPseudo(String pseudo) {

        Optional<PlayerEntity> playEntity = playerRepository.findByPseudo(pseudo);

        if (playEntity.isPresent()) {
            var heroL = new ArrayList<Hero>();
            for (HeroEntity heroEntity : playEntity.get().getDeck().getHeros()) {
                var hero = new Hero(heroEntity.getId(), heroEntity.getName(),
                        heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                        heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                        heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                        heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

                heroL.add(hero);
            }

            var deck = new Deck(playEntity.get().getDeck().getId(), heroL, playEntity.get().getDeck().getCreatedAt(),
                    playEntity.get().getDeck().getUpdatedAt());

            var play = new Player(playEntity.get().getId(), playEntity.get().getPseudo(), playEntity.get().getJeton(),
                    deck, playEntity.get().getNbrTirage(), playEntity.get().getNbrTiragePackArgent(),
                    playEntity.get().getNbrTiragePackDiament(),
                    playEntity.get().getCreatedAt(), playEntity.get().getUpdatedAt());

            return Optional.of(play);
        } else
            return Optional.empty();
    }

    @Override
    public Optional<Player> getById(Long id) {
        Optional<PlayerEntity> playEntity = playerRepository.findById(id);

        if (playEntity.isPresent()) {
            var heroL = new ArrayList<Hero>();
            for (HeroEntity heroEntity : playEntity.get().getDeck().getHeros()) {
                var hero = new Hero(heroEntity.getId(), heroEntity.getName(),
                        heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                        heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                        heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                        heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

                heroL.add(hero);
            }

            var deck = new Deck(playEntity.get().getDeck().getId(), heroL, playEntity.get().getDeck().getCreatedAt(),
                    playEntity.get().getDeck().getUpdatedAt());

            var play = new Player(playEntity.get().getId(), playEntity.get().getPseudo(), playEntity.get().getJeton(),
                    deck, playEntity.get().getNbrTirage(), playEntity.get().getNbrTiragePackArgent(),
                    playEntity.get().getNbrTiragePackDiament(),
                    playEntity.get().getCreatedAt(), playEntity.get().getUpdatedAt());

            return Optional.of(play);
        } else
            return Optional.empty();
    }

    @Override
    public Boolean verifyHeroInDeckPlayerService(Player player, Hero hero) {
        player.getDeck().getHeros().contains(hero);
        return player.getDeck().getHeros().contains(hero);
    }

}
