package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.CombatHistoryEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatHistoryRepository;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.EngageCombatService;
import esgi.infra.service.FindByHeroCombatService;
import esgi.infra.service.RetrieveHeroCombatsService;
import esgi.infra.service.VerifyStatusCombatService;

@Service
@Transactional
public class CombatServiceImpl implements EngageCombatService, RetrieveHeroCombatsService,
          VerifyStatusCombatService, FindByHeroCombatService {

     private final HeroRepository heroRepository;
     private final CombatRepository combatRepository;
     private final CombatHistoryRepository combatHistoryRepository;

     public CombatServiceImpl(HeroRepository heroRepository, CombatRepository combatRepository,
               CombatHistoryRepository combatHistoryRepository) {
          this.heroRepository = heroRepository;
          this.combatRepository = combatRepository;
          this.combatHistoryRepository = combatHistoryRepository;
     }

     public boolean isAlive(Long heroId) {
          HeroEntity hero = heroRepository.findById(heroId)
                    .orElseThrow(() -> new IllegalArgumentException("Hero not found !"));
          return hero.getNbLifePoints() > 0;
     }

     public int calculateDamage(HeroEntity attacker, HeroEntity defender) {
          int damage = attacker.getPower() - defender.getArmor();
          if (defender.getSpeciality().equals(SpecialityTypeDomain.MAGE)
                    && attacker.getSpeciality().equals(SpecialityTypeDomain.TANK)) {
               damage += 20;
          } else if (defender.getSpeciality().equals(SpecialityTypeDomain.TANK)
                    && attacker.getSpeciality().equals(SpecialityTypeDomain.ASSASSIN)) {
               damage += 30;
          } else if (defender.getSpeciality().equals(SpecialityTypeDomain.ASSASSIN)
                    && attacker.getSpeciality().equals(SpecialityTypeDomain.MAGE)) {
               damage += 25;
          }
          return damage;
     }

     public int attack(HeroEntity hero, HeroEntity enemy) {

          int damage = calculateDamage(hero, enemy);
          if (enemy.getNbLifePoints() - damage > 0)
               enemy.setNbLifePoints(enemy.getNbLifePoints() - damage);
          else
               enemy.setNbLifePoints(0);
          heroRepository.save(enemy);

          return damage;
     }

     public void addExperiencePoints(HeroEntity hero, int points) {
          hero.setExperience(hero.getExperience() + points);
          if (hero.getExperience() % 5 == 0) {
               hero.setLevel(hero.getLevel() + 1);
               hero.setNbLifePoints((int) (hero.getNbLifePoints() * 1.1));
               hero.setPower((int) (hero.getPower() * 1.1));
               hero.setArmor((int) (hero.getArmor() * 1.1));
          }
          heroRepository.save(hero);
     }

     @Override
     public String engageCombat(HeroDomain atHero, HeroDomain defHero) {

          var result = "";
          var comb = new CombatEntity(heroRepository.findById(atHero.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hero attacked not found !")),
                    heroRepository.findById(defHero.getId())
                              .orElseThrow(() -> new IllegalArgumentException(
                                        "Hero defended not found !")));

          CombatEntity combat = combatRepository.save(comb);

          while (isAlive(atHero.getId()) && isAlive(defHero.getId())) {
               CombatHistoryEntity combatHistory = new CombatHistoryEntity();

               HeroEntity attackerHero = heroRepository.findById(atHero.getId())
                         .orElseThrow(() -> new IllegalArgumentException("Hero attacked not found !"));

               HeroEntity defenderHero = heroRepository.findById(defHero.getId())
                         .orElseThrow(() -> new IllegalArgumentException("Hero defended not found !"));

               var defenderDamage = attack(attackerHero, defenderHero);
               combatHistory.setDamageDefenderHero(defenderDamage);
               if (defenderHero.getNbLifePoints() - defenderDamage >= 0)
                    combatHistory.setNewLifePointsDefender(defenderHero.getNbLifePoints() - defenderDamage);

               if (isAlive(defHero.getId())) {
                    var attackerDamage = attack(defenderHero, attackerHero);
                    combatHistory.setDamageAttackerHero(attackerDamage);
                    if (attackerHero.getNbLifePoints() - attackerDamage >= 0)
                         combatHistory.setNewLifePointsAttacker(
                                   attackerHero.getNbLifePoints() - attackerDamage);
               }

               if (isAlive(atHero.getId()) && isAlive(defHero.getId()))
                    combatHistory.setResult("Draw");
               else if (isAlive(atHero.getId())) {
                    result = "Hero attacked " + atHero.getId() + " won the combat";
                    combatHistory.setResult(result);
                    combat.setResult(result);
                    addExperiencePoints(attackerHero, 1);
                    defenderHero.setAvailable(false);
                    heroRepository.save(defenderHero);

               } else {
                    result = "Hero defended " + defHero.getId() + " won the combat";
                    combat.setResult(result);
                    addExperiencePoints(defenderHero, 1);
                    attackerHero.setAvailable(false);
                    heroRepository.save(attackerHero);
                    combatHistory.setResult(result);

               }
               // save historic
               combatHistory = combatHistoryRepository.save(combatHistory);

               // ajout historic combat
               combat.addCombatHistory(combatHistory);
               combatRepository.save(combat);

          }

          return result;

     }

     @Override
     public List<CombatDomain> retrieveHeroCombats(HeroDomain hero) {
          var combatL = new ArrayList<CombatDomain>();
          HeroEntity heroEntity = new HeroEntity(hero.getId(), hero.getName(), hero.getNbLifePoints(),
                    hero.getExperience(), hero.getPower(), hero.getArmor(), hero.getSpeciality(),
                    hero.getRarity(), hero.getLevel(), hero.isAvailable(), hero.isStatus(),
                    hero.getCreatedAt(), hero.getUpdatedAt());

          for (CombatEntity combatEntity : combatRepository.findByAttackingHeroOrDefendingHero(heroEntity,
                    heroEntity)) {
               HeroDomain attackerHero = new HeroDomain(combatEntity.getAttackingHero().getId(),
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

               HeroDomain defenderHero = new HeroDomain(combatEntity.getDefendingHero().getId(),
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
               combatL.add(new CombatDomain(combatEntity.getId(), attackerHero, defenderHero,
                         combatEntity.getResult(),
                         combatEntity.getCreatedAt(), combatEntity.getUpdatedAt()));
          }

          return combatL;
     }

     @Override
     public Boolean verifyStatusCombat(HeroDomain atHero, HeroDomain defHero) {

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
                    || !comb1.isPresent() || !comb2.isPresent());

     }

     @Override
     public Optional<CombatDomain> findByHeroCombat(HeroDomain atHero, HeroDomain defHero) {

          HeroEntity attackerHero = heroRepository.findById(atHero.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                              "Hero attacked with id " + atHero.getId() + " not found !"));

          HeroEntity defenderHero = heroRepository.findById(defHero.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                              "Hero defended with id " + atHero.getId() + " not found !"));

          Optional<CombatEntity> comb1 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                    attackerHero, defenderHero);

          if (comb1.isPresent()) {
               var at1 = new HeroDomain(comb1.get().getAttackingHero().getId(),
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

               var at2 = new HeroDomain(comb1.get().getDefendingHero().getId(),
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

               return Optional.of(new CombatDomain(comb1.get().getId(), at1, at2,
                         comb1.get().getResult(), comb1.get().getCreatedAt(),
                         comb1.get().getUpdatedAt()));
          }

          Optional<CombatEntity> comb2 = combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(
                    defenderHero,
                    attackerHero);

          if (comb2.isPresent()) {
               var at1 = new HeroDomain(comb2.get().getAttackingHero().getId(),
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

               var at2 = new HeroDomain(comb2.get().getDefendingHero().getId(),
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

               return Optional.of(new CombatDomain(comb2.get().getId(), at1, at2,
                         comb2.get().getResult(), comb2.get().getCreatedAt(),
                         comb2.get().getUpdatedAt()));
          } else
               return Optional.empty();
     }

}