package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.HeroDomain;
import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.OpenPackService;
import esgi.infra.service.VerifyJetonService;

@Service
@Transactional
public class DeckServiceImpl implements VerifyJetonService, OpenPackService {
    private final PlayerRepository playerRepository;
    private final HeroRepository heroRepository;
    private final DeckRepository deckRepository;

    public DeckServiceImpl(PlayerRepository playerRepository, HeroRepository heroRepository,
            DeckRepository deckRepository) {
        this.playerRepository = playerRepository;
        this.heroRepository = heroRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    public Boolean verifJetonByPackPlayer(PackHeroTypeDomain packHero, PlayerDomain player) {
        if (packHero.equals(PackHeroTypeDomain.argent) && player.getJeton() >= 1 ||
                packHero.equals(PackHeroTypeDomain.diamant) && player.getJeton() >= 2)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;

    }

    @Override
    public List<HeroDomain> openPack(PlayerDomain player, PackHeroTypeDomain packType) {
        int tokenCost;
        int numberOfHeros;
        Map<String, Double> rarityProbability;
        // définir les caractéristiques du pack en fonction de son type
        if (PackHeroTypeDomain.argent.equals(packType)) {
            tokenCost = 1;
            numberOfHeros = 3;
            rarityProbability = Map.of(RaretyTypeDomain.LEGENDARY.name(), 0.05, RaretyTypeDomain.RARE.name(), 0.2,
                    RaretyTypeDomain.COMMON.name(), 0.75);
            player.setNbrTiragePackArgent(player.getNbrTiragePackArgent() + 1);
        } else if (PackHeroTypeDomain.diamant.equals(packType)) {
            tokenCost = 2;
            numberOfHeros = 5;
            rarityProbability = Map.of(RaretyTypeDomain.LEGENDARY.name(), 0.15, RaretyTypeDomain.RARE.name(), 0.35,
                    RaretyTypeDomain.COMMON.name(), 0.5);
            player.setNbrTiragePackDiament(player.getNbrTiragePackDiament() + 1);

        } else {
            throw new IllegalArgumentException("Invalid pack type");
        }

        // déduire les jetons du solde du joueur
        player.setJeton(player.getJeton() - tokenCost);
        player.setNbrTirage(player.getNbrTirage() + 1);
        // générer les cartes du pack en utilisant les probabilités de rareté
        List<HeroEntity> heros = generateHeros(numberOfHeros, rarityProbability);

        var h = new ArrayList<HeroEntity>();

        for (HeroDomain her : player.getDeck().getHeros()) {
            h.add(new HeroEntity(her.getId(), her.getName(),
                    her.getNbLifePoints(), her.getExperience(), her.getPower(),
                    her.getArmor(), her.getSpeciality(), her.getRarity(), her.getLevel(),
                    her.isAvailable(), her.isStatus(), her.getCreatedAt(), her.getUpdatedAt()));
        }

        PlayerEntity play = new PlayerEntity(player.getId(), player.getPseudo(),
                player.getJeton(),
                new DeckEntity(player.getDeck().getId(), h, player.getDeck().getCreatedAt(),
                        player.getDeck().getUpdatedAt()),
                player.getNbrTirage(),
                player.getNbrTiragePackArgent(), player.getNbrTiragePackDiament(),
                player.getCreatedAt(), player.getUpdatedAt());

        // ajouter les cartes au deck du joueur
        play.getDeck().addAll(heros);

        // sauvegarder les modifications du joueur en base de données
        playerRepository.save(play);
        deckRepository.save(play.getDeck());

        var heroL = new ArrayList<HeroDomain>();

        for (HeroEntity heroEntity : heros) {
            var hero = new HeroDomain(heroEntity.getId(), heroEntity.getName(),

                    heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                    heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                    heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                    heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

            heroL.add(hero);
        }
        return heroL;
    }

    private List<HeroEntity> generateHeros(int numberOfHeros, Map<String, Double> rarityProbability) {
        List<HeroEntity> heros = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfHeros; i++) {
            double probability = random.nextDouble();
            RaretyTypeDomain rarity;
            if (probability < rarityProbability.get(RaretyTypeDomain.LEGENDARY.name())) {
                rarity = RaretyTypeDomain.LEGENDARY;
            } else if (probability < rarityProbability.get(RaretyTypeDomain.RARE.name())) {
                rarity = RaretyTypeDomain.RARE;
            } else {
                rarity = RaretyTypeDomain.COMMON;
            }
            // sélectionner un héros aléatoire de la rareté choisie
            List<HeroEntity> heroes = heroRepository.findByRarityAndStatusTrue(rarity);
            if (!heroes.isEmpty()) {
                HeroEntity hero = heroes.get(random.nextInt(heroes.size()));
                // modifier le status
                hero.setStatus(false);
                heroRepository.save(hero);
                // créer une carte à partir du héros sélectionné
                heros.add(hero);
            } else
                i--;
        }

        return heros;
    }

}
