package esgi.infra.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esgi.domain.Hero;
import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.heros.CreateHeroService;
import esgi.infra.service.heros.FindAllAvailableHeroService;
import esgi.infra.service.heros.GetByIdHeroServiceService;
import esgi.infra.service.heros.VerifyAvailableHeroService;

@Service
public class HerosServiceImpl implements CreateHeroService, FindAllAvailableHeroService, GetByIdHeroServiceService,
        VerifyAvailableHeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HerosServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public List<Hero> findAllHerosAvailable() {
        var heroL = new ArrayList<Hero>();
        for (HeroEntity heroEntity : heroRepository.findByStatusTrue()) {
            var hero = new Hero(heroEntity.getId(), heroEntity.getName(),
                    heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                    heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                    heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                    heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

            heroL.add(hero);
        }
        return heroL;
    }

    @Override
    public Hero createHero(String name, SpecialityType speciality, RaretyType rarity) {
        HeroEntity heroEntity = new HeroEntity(name, speciality, rarity);
        calculateBaseStatistics(heroEntity);
        heroEntity = heroRepository.save(heroEntity);

        return new Hero(heroEntity.getId(), heroEntity.getName(),
                heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());
    }

    @Override
    public Hero getById(Long id) {
        HeroEntity heroEntity = heroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hero with id " + id + " not found !"));

        return new Hero(heroEntity.getId(), heroEntity.getName(),
                heroEntity.getNbLifePoints(), heroEntity.getExperience(),
                heroEntity.getPower(), heroEntity.getArmor(), heroEntity.getSpeciality(),
                heroEntity.getRarity(), heroEntity.getLevel(), heroEntity.isAvailable(),
                heroEntity.isStatus(), heroEntity.getCreatedAt(), heroEntity.getUpdatedAt());

    }

    public HeroEntity calculateBaseStatistics(HeroEntity hero) {
        // initial base statistics for each speciality
        if (hero.getSpeciality().equals(SpecialityType.TANK)) {
            hero.setNbLifePoints(1000);
            hero.setPower(100);
            hero.setArmor(20);
        } else if (hero.getSpeciality().equals(SpecialityType.ASSASSIN)) {
            hero.setNbLifePoints(800);
            hero.setPower(200);
            hero.setArmor(5);
        } else if (hero.getSpeciality().equals(SpecialityType.MAGE)) {
            hero.setNbLifePoints(700);
            hero.setPower(150);
            hero.setArmor(10);
        }

        var nbLifePoints = hero.getNbLifePoints();

        var power = hero.getPower();

        var armor = hero.getArmor();
        // apply rarity bonuses
        if (hero.getRarity().equals(RaretyType.COMMON)) {
            // no bonus
        } else if (hero.getRarity().equals(RaretyType.RARE)) {
            nbLifePoints += hero.getNbLifePoints() * 0.1;
            hero.setNbLifePoints(nbLifePoints);
            power += hero.getPower() * 0.1;
            hero.setPower(power);
            armor += hero.getArmor() * 0.1;
            hero.setArmor(armor);
        } else if (hero.getRarity().equals(RaretyType.LEGENDARY)) {
            nbLifePoints += hero.getNbLifePoints() * 0.2;
            hero.setNbLifePoints(nbLifePoints);
            power += hero.getPower() * 0.2;
            hero.setPower(power);
            armor += hero.getArmor() * 0.2;
            hero.setArmor(armor);
        }

        return hero;
    }

    @Override
    public Boolean verifyAvailableHeroService(Hero hero) {

        return heroRepository.findByIdAndAvailableTrue(hero.getId()).isPresent();
    }

}
