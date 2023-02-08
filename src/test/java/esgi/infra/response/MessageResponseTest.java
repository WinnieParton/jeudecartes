package esgi.infra.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.Hero;
import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;

@ExtendWith(MockitoExtension.class)
public class MessageResponseTest {
    @Test
    public void testMessageResponse() {
        MessageResponse messageResponse = new MessageResponse("Success");
        assertEquals("Success", messageResponse.getMap().get("message"));
        assertNull(messageResponse.getMap().get("data"));
        Hero hero = new Hero("Superman", SpecialityType.TANK, RaretyType.COMMON);
        messageResponse = new MessageResponse("Hero created", hero);
        assertEquals("Hero created", messageResponse.getMap().get("message"));
        assertEquals(hero, messageResponse.getMap().get("data"));
    }
}
