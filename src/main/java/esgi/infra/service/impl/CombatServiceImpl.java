package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.Combat;
import esgi.domain.Hero;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.combat.EngageCombatService;
import esgi.infra.service.combat.FindByHeroCombatService;
import esgi.infra.service.combat.RetrieveHeroCombatsService;
import esgi.infra.service.combat.VerifyStatusCombatService;

@Service
@Transactional
public class CombatServiceImpl implements EngageCombatService, RetrieveHeroCombatsService,
        VerifyStatusCombatService, FindByHeroCombatService {

    private final HeroRepository heroRepository;
    private final CombatRepository combatRepository;

    public CombatServiceImpl(HeroRepository heroRepository,
            CombatRepository combatRepository) {
        this.heroRepository = heroRepository;
        this.combatRepository = combatRepository;
    }

    @Override
    public Combat engageCombat(Hero atHero, Hero defHero) {

        HeroEntity attackerHero = heroRepository.findById(atHero.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hero attacked not found !"));

        HeroEntity defenderHero = heroRepository.findById(defHero.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hero defended not found !"));

        int attackerDamage = attackerHero.getPower();
        int defenderDamage = defenderHero.getPower() - attackerHero.getArmor();
        int newpointatt = 0, newpointdeff = 0;

        if (defenderHero.getNbLifePoints() - attackerDamage > 0)
            newpointdeff = defenderHero.getNbLifePoints() - attackerDamage;

        if (attackerHero.getNbLifePoints() - defenderDamage > 0)
            newpointatt = defenderHero.getNbLifePoints() - attackerDamage;

        defenderHero.setNbLifePoints(newpointdeff);
        attackerHero.setNbLifePoints(newpointatt);
        defHero.setNbLifePoints(newpointdeff);
        atHero.setNbLifePoints(newpointatt);

        String result;
        if (defenderHero.getNbLifePoints() <= 0) {
            result = "Attacker wins";
            defenderHero.setAvailable(false);
            attackerHero.setExperience(attackerHero.getExperience() + 1);
            defHero.setAvailable(false);
            atHero.setExperience(attackerHero.getExperience() + 1);
        } else if (attackerHero.getNbLifePoints() <= 0) {
            result = "Defender wins";
            attackerHero.setAvailable(false);
            atHero.setAvailable(false);
        } else {
            result = "Draw";
        }

        heroRepository.save(attackerHero);
        heroRepository.save(defenderHero);

        var combat = combatRepository.save(new CombatEntity(attackerHero, defenderHero, attackerDamage,
                defenderDamage, attackerHero.getNbLifePoints(), defenderHero.getNbLifePoints(),
                result));

        return new Combat(combat.getId(), atHero, atHero, attackerDamage, defenderDamage,
                newpointatt, newpointdeff, result, combat.getCreatedAt(), combat.getUpdatedAt());
    }

    @Override
    public List<Combat> retrieveHeroCombats(Hero hero) {
        var combatL = new ArrayList<Combat>();
        HeroEntity heroEntity = new HeroEntity(hero.getId(), hero.getName(), hero.getNbLifePoints(),
                hero.getExperience(), hero.getPower(), hero.getArmor(), hero.getSpeciality(),
                hero.getRarity(), hero.getLevel(), hero.isAvailable(), hero.isStatus(),
                hero.getCreatedAt(), hero.getUpdatedAt());

        for (CombatEntity combatEntity : combatRepository.findByAttackingHeroOrDefendingHero(heroEntity,
                heroEntity)) {
            Hero attackerHero = new Hero(combatEntity.getAttackingHero().getId(),
                    combatEntity.getAttackingHero().getName(),
                    combatEntity.getAttackingHero().getNbLifePoints(),
                    combatEntity.getAttackingHero().getExperience(),
                    combatEntity.getAttackingHero().getPower(),
                    combatEntity.getAttackingHero().getArmor(),
                    combatEntity.getAttackingHero().getSpeciality(),
                    combatEntity.getAttackingHero().getRarity(),
                    combatEntity.getAttackingHero().getLevel(),
                    combatEntity.getAttackingHero().isAvailable(),
                    combatEntity.getAttackingHero().isStatus(),
                    combatEntity.getAttackingHero().getCreatedAt(),
                    combatEntity.getAttackingHero().getUpdatedAt());

            Hero defenderHero = new Hero(combatEntity.getDefendingHero().getId(),
                    combatEntity.getDefendingHero().getName(),
                    combatEntity.getDefendingHero().getNbLifePoints(),
                    combatEntity.getDefendingHero().getExperience(),
                    combatEntity.getDefendingHero().getPower(),
                    combatEntity.getDefendingHero().getArmor(),
                    combatEntity.getDefendingHero().getSpeciality(),
                    combatEntity.getDefendingHero().getRarity(),
                    combatEntity.getDefendingHero().getLevel(),
                    combatEntity.getDefendingHero().isAvailable(),
                    combatEntity.getDefendingHero().isStatus(),
                    combatEntity.getDefendingHero().getCreatedAt(),
                    combatEntity.getDefendingHero().getUpdatedAt());
            combatL.add(
                    new Combat(combatEntity.getId(), attackerHero, defenderHero,
                            combatEntity.getDamageAttackerHero(),
                            combatEntity.getDamageDefenderHero(),
                            combatEntity.getNewLifePointsAttacker(),
                            combatEntity.getNewLifePointsDefender(),
                            combatEntity.getResult(),
                            combatEntity.getCreatedAt(), combatEntity.getUpdatedAt()));
        }

        return combatL;
    }

    @Override
    public Boolean verifyStatusCombat(Hero atHero, Hero defHero) {

        HeroEntity attackerHero = heroRepository.findById(atHero.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hero attacked not found !"));

        HeroEntity defenderHero = heroRepository.findById(defHero.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hero defended not found !"));

        Optional<CombatEntity> comb1 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                attackerHero,
                defenderHero);

        Optional<CombatEntity> comb2 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                defenderHero,
                attackerHero);

        return (comb1.isPresent() && comb1.get().getResult().equals("Draw")
                || comb2.isPresent() && comb2.get().getResult().equals("Draw")
                || !comb1.isPresent() || !comb1.isPresent());

    }

    @Override
    public Optional<Combat> findByHeroCombat(Hero atHero, Hero defHero) {

        HeroEntity attackerHero = heroRepository.findById(atHero.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Hero attacked with id " + atHero.getId() + " not found !"));

        HeroEntity defenderHero = heroRepository.findById(defHero.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Hero defended with id " + atHero.getId() + " not found !"));

        Optional<CombatEntity> comb1 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                attackerHero, defenderHero);

        if (comb1.isPresent()) {
            var at1 = new Hero(comb1.get().getAttackingHero().getId(),
                    comb1.get().getAttackingHero().getName(),
                    comb1.get().getAttackingHero().getNbLifePoints(),
                    comb1.get().getAttackingHero().getExperience(),
                    comb1.get().getAttackingHero().getPower(),
                    comb1.get().getAttackingHero().getArmor(),
                    comb1.get().getAttackingHero().getSpeciality(),
                    comb1.get().getAttackingHero().getRarity(),
                    comb1.get().getAttackingHero().getLevel(),
                    comb1.get().getAttackingHero().isAvailable(),
                    comb1.get().getAttackingHero().isStatus(),
                    comb1.get().getAttackingHero().getCreatedAt(),
                    comb1.get().getAttackingHero().getUpdatedAt());

            var at2 = new Hero(comb1.get().getDefendingHero().getId(),
                    comb1.get().getDefendingHero().getName(),
                    comb1.get().getDefendingHero().getNbLifePoints(),
                    comb1.get().getDefendingHero().getExperience(),
                    comb1.get().getDefendingHero().getPower(),
                    comb1.get().getDefendingHero().getArmor(),
                    comb1.get().getDefendingHero().getSpeciality(),
                    comb1.get().getDefendingHero().getRarity(),
                    comb1.get().getDefendingHero().getLevel(),
                    comb1.get().getDefendingHero().isAvailable(),
                    comb1.get().getDefendingHero().isStatus(),
                    comb1.get().getDefendingHero().getCreatedAt(),
                    comb1.get().getDefendingHero().getUpdatedAt());

            return Optional.of(new Combat(at1, at2, comb1.get().getDamageAttackerHero(),
                    comb1.get().getDamageDefenderHero(), comb1.get().getNewLifePointsAttacker(),
                    comb1.get().getNewLifePointsDefender(), comb1.get().getResult()));
        }

        Optional<CombatEntity> comb2 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                defenderHero,
                attackerHero);

        if (comb2.isPresent()) {
            var at1 = new Hero(comb2.get().getAttackingHero().getId(),
                    comb2.get().getAttackingHero().getName(),
                    comb2.get().getAttackingHero().getNbLifePoints(),
                    comb2.get().getAttackingHero().getExperience(),
                    comb2.get().getAttackingHero().getPower(),
                    comb2.get().getAttackingHero().getArmor(),
                    comb2.get().getAttackingHero().getSpeciality(),
                    comb2.get().getAttackingHero().getRarity(),
                    comb2.get().getAttackingHero().getLevel(),
                    comb2.get().getAttackingHero().isAvailable(),
                    comb2.get().getAttackingHero().isStatus(),
                    comb2.get().getAttackingHero().getCreatedAt(),
                    comb2.get().getAttackingHero().getUpdatedAt());

            var at2 = new Hero(comb2.get().getDefendingHero().getId(),
                    comb2.get().getDefendingHero().getName(),
                    comb2.get().getDefendingHero().getNbLifePoints(),
                    comb2.get().getDefendingHero().getExperience(),
                    comb2.get().getDefendingHero().getPower(),
                    comb2.get().getDefendingHero().getArmor(),
                    comb2.get().getDefendingHero().getSpeciality(),
                    comb2.get().getDefendingHero().getRarity(),
                    comb2.get().getDefendingHero().getLevel(),
                    comb2.get().getDefendingHero().isAvailable(),
                    comb2.get().getDefendingHero().isStatus(),
                    comb2.get().getDefendingHero().getCreatedAt(),
                    comb2.get().getDefendingHero().getUpdatedAt());

            return Optional.of(new Combat(at1, at2, comb2.get().getDamageAttackerHero(),
                    comb2.get().getDamageDefenderHero(), comb2.get().getNewLifePointsAttacker(),
                    comb2.get().getNewLifePointsDefender(), comb2.get().getResult()));
        } else
            return Optional.empty();
    }

    // public int attack(Hero hero) {
    // int damage = power - hero.getArmor();
    // if (specialty.equals("Tank")) {
    // if (hero.getSpecialty().equals("Mage")) {
    // damage += 20;
    // }
    // } else if (specialty.equals("Assassin")) {
    // if (hero.getSpecialty().equals("Tank")) {
    // damage += 30;
    // }
    // } else if (specialty.equals("Mage")) {
    // if (hero.getSpecialty().equals("Assassin")) {
    // damage += 25;
    // }
    // }
    // return damage;
    // }
    // }
}