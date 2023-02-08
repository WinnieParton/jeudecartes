package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.Hero;
import esgi.domain.PackHeroType;
import esgi.domain.Player;
import esgi.domain.RaretyType;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.desk.OpenPackService;
import esgi.infra.service.desk.VerifyJetonService;

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
    public Boolean verifJetonByPackPlayer(PackHeroType packHero, Player player) {
        if (packHero.equals(PackHeroType.argent) && player.getJeton() >= 1 ||
                packHero.equals(PackHeroType.diamant) && player.getJeton() >= 2)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;

    }

    @Override
    public List<Hero> openPack(Player player, PackHeroType packType) {
        int tokenCost;
        int numberOfHeros;
        Map<String, Double> rarityProbability;
        // définir les caractéristiques du pack en fonction de son type
        if (PackHeroType.argent.equals(packType)) {
            tokenCost = 1;
            numberOfHeros = 3;
            rarityProbability = Map.of(RaretyType.LEGENDARY.name(), 0.05, RaretyType.RARE.name(), 0.2,
                    RaretyType.COMMON.name(), 0.75);
            player.setNbrTiragePackArgent(player.getNbrTiragePackArgent() + 1);
        } else if (PackHeroType.diamant.equals(packType)) {
            tokenCost = 2;
            numberOfHeros = 5;
            rarityProbability = Map.of(RaretyType.LEGENDARY.name(), 0.15, RaretyType.RARE.name(), 0.35,
                    RaretyType.COMMON.name(), 0.5);
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

        for (Hero her : player.getDeck().getHeros()) {
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

        var heroL = new ArrayList<Hero>();

        for (HeroEntity heroEntity : heros) {
            var hero = new Hero(heroEntity.getId(), heroEntity.getName(),
              
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
            RaretyType rarity;
            if (probability < rarityProbability.get(RaretyType.LEGENDARY.name())) {
                rarity = RaretyType.LEGENDARY;
            } else if (probability < rarityProbability.get(RaretyType.RARE.name())) {
                rarity = RaretyType.RARE;
            } else {
                rarity = RaretyType.COMMON;
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
