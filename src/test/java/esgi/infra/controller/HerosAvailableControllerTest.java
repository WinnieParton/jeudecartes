package esgi.infra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import esgi.domain.HeroDomain;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.FindAllAvailableHeroService;

@ExtendWith(MockitoExtension.class)
public class HerosAvailableControllerTest {
    private HerosAvailableController herosAvailableController;
    private FindAllAvailableHeroService herosService;

    @BeforeEach
    public void setUp() {
        herosService = mock(FindAllAvailableHeroService.class);
        herosAvailableController = new HerosAvailableController(herosService);
    }

    @Test
    public void getHerosAvailable_returnsSuccess() {
        List<HeroDomain> heroList = new ArrayList<>();
        heroList.add(new HeroDomain());
        when(herosService.findAllHerosAvailable()).thenReturn(heroList);
        ResponseEntity<?> result = herosAvailableController.getHerosAvailable();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
    }

}
