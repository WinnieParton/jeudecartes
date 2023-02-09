package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.HerosServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetByIdHeroServiceServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HerosServiceImpl herosService;
    @Test
    public void testGetById() {
        HeroEntity heroEntity = new HeroEntity(1L, "Superman", 100, 0, 10, 5, SpecialityTypeDomain.TANK,
                RaretyTypeDomain.RARE, 1, true, true, null, null);

        when(heroRepository.findById(1L)).thenReturn(Optional.of(heroEntity));

        HeroDomain hero = herosService.getById(1L);

        assertEquals(1L, hero.getId().longValue());
        assertEquals("Superman", hero.getName());
        assertEquals(100, hero.getNbLifePoints());
        assertEquals(0, hero.getExperience());
        assertEquals(10, hero.getPower());
        assertEquals(5, hero.getArmor());
        assertEquals(SpecialityTypeDomain.TANK, hero.getSpeciality());
        assertEquals(RaretyTypeDomain.RARE, hero.getRarity());
        assertEquals(1, hero.getLevel());
        assertTrue(hero.isAvailable());
        assertTrue(hero.isStatus());
    }

    @org.junit.Test(expected  = IllegalArgumentException.class)
    public void testGetById_HeroNotFound_ShouldThrowException() {
        when(heroRepository.findById(1L)).thenReturn(Optional.empty());

        herosService.getById(1L);
    }

}
