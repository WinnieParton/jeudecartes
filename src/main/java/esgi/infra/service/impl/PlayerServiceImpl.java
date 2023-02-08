package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.DeckDomain;
import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.CreatePlayerService;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.IsPseudoPlayerExistService;
import esgi.infra.service.SearchPlayersService;
import esgi.infra.service.VerifyHeroInDeckPlayerService;
import esgi.infra.service.ViewPlayerDeckService;

@Service
@Transactional
public class PlayerServiceImpl
        implements CreatePlayerService, SearchPlayersService,
        ViewPlayerDeckService, IsPseudoPlayerExistService, GetByIdPlayerService, VerifyHeroInDeckPlayerService {

    private final PlayerRepository playerRepository;

    private final DeckRepository deckRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, DeckRepository deckRepository) {
        this.playerRepository = playerRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    public PlayerDomain createPlayer(String pseudo) {
        PlayerEntity play = new PlayerEntity(pseudo);
        DeckEntity deck = new DeckEntity();
        deck = deckRepository.save(deck);
        play.setDeck(deck);
        play = playerRepository.save(play);

        return new PlayerDomain(play.getId(), play.getPseudo(), play.getCreatedAt(), play.getUpdatedAt());
    }

    @Override
    public List<PlayerDomain> searchPlayers() {
        var player = new ArrayList<PlayerDomain>();
        for (PlayerEntity playEntity : playerRepository.findAll()) {
            var heroL = new ArrayList<HeroDomain>();
            for (HeroEntity heroEntity : playEntity.getDeck().getHeros()) {
                var hero = new HeroDomain(heroEntity.getId(), heroEntity.getName(),
                        heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                        heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                        heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                        heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

                heroL.add(hero);
            }

            var deck = new DeckDomain(playEntity.getDeck().getId(), heroL, playEntity.getDeck().getCreatedAt(),
                    playEntity.getDeck().getUpdatedAt());

            var play = new PlayerDomain(playEntity.getId(), playEntity.getPseudo(), playEntity.getJeton(),
                    deck, playEntity.getNbrTirage(), playEntity.getNbrTiragePackArgent(),
                    playEntity.getNbrTiragePackDiament(),
                    playEntity.getCreatedAt(), playEntity.getUpdatedAt());

            player.add(play);
        }
        return player;
    }

    @Override
    public List<HeroDomain> viewPlayerDeck(PlayerDomain player) {
        return player.getDeck().getHeros();
    }

    @Override
    public Optional<PlayerDomain> findByPseudo(String pseudo) {

        Optional<PlayerEntity> playEntity = playerRepository.findByPseudo(pseudo);

        if (!playEntity.isEmpty()) {
            var heroL = new ArrayList<HeroDomain>();
            for (HeroEntity heroEntity : playEntity.get().getDeck().getHeros()) {
                var hero = new HeroDomain(heroEntity.getId(), heroEntity.getName(),
                        heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                        heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                        heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                        heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

                heroL.add(hero);
            }

            var deck = new DeckDomain(playEntity.get().getDeck().getId(), heroL, playEntity.get().getDeck().getCreatedAt(),
                    playEntity.get().getDeck().getUpdatedAt());

            var play = new PlayerDomain(playEntity.get().getId(), playEntity.get().getPseudo(),
                    playEntity.get().getJeton(), deck, playEntity.get().getNbrTirage(),
                    playEntity.get().getNbrTiragePackArgent(), playEntity.get().getNbrTiragePackDiament(),
                    playEntity.get().getCreatedAt(), playEntity.get().getUpdatedAt());

            return Optional.of(play);
        } else
            return Optional.empty();
    }

    @Override
    public PlayerDomain getById(Long id) {
        PlayerEntity playEntity = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player with id " + id + " not found !"));

        var heroL = new ArrayList<HeroDomain>();
        for (HeroEntity heroEntity : playEntity.getDeck().getHeros()) {
            var hero = new HeroDomain(heroEntity.getId(), heroEntity.getName(),
                    heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                    heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                    heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                    heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

            heroL.add(hero);
        }

        var deck = new DeckDomain(playEntity.getDeck().getId(), heroL, playEntity.getDeck().getCreatedAt(),
                playEntity.getDeck().getUpdatedAt());

        return new PlayerDomain(playEntity.getId(), playEntity.getPseudo(), playEntity.getJeton(),
                deck, playEntity.getNbrTirage(), playEntity.getNbrTiragePackArgent(),
                playEntity.getNbrTiragePackDiament(),
                playEntity.getCreatedAt(), playEntity.getUpdatedAt());
    }

    @Override
    public Boolean verifyHeroInDeckPlayerService(PlayerDomain player, HeroDomain hero) {
        player.getDeck().getHeros().contains(hero);
        return player.getDeck().getHeros().contains(hero);
    }

}
