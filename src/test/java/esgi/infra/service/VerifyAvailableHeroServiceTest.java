package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.HeroDomain;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.HerosServiceImpl;

@ExtendWith(MockitoExtension.class)
public class VerifyAvailableHeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HerosServiceImpl heroService;

    @Test
    public void testVerifyAvailableHeroService_HeroFound_ShouldReturnTrue() {
        Long heroId = 1L;
        HeroEntity heroEntity = mock(HeroEntity.class);
        when(heroRepository.findByIdAndAvailableTrue(heroId)).thenReturn(Optional.of(heroEntity));
        HeroDomain hero = new HeroDomain();
        hero.setId(heroId);
        assertTrue(heroService.verifyAvailableHeroService(hero));
    }

    @Test
    public void testVerifyAvailableHeroService_HeroNotFound_ShouldReturnFalse() {
        Long heroId = 1L;
        when(heroRepository.findByIdAndAvailableTrue(heroId)).thenReturn(Optional.empty());
        HeroDomain hero = new HeroDomain();
        hero.setId(heroId);
        assertFalse(heroService.verifyAvailableHeroService(hero));
    }
}
