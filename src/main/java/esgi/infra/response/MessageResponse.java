package esgi.infra.response;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.SpecialityType;
import lombok.Getter;
import lombok.Setter;


/*@Getter
@Setter
public class MessageResponse {
    private Map<String, Object> map = new LinkedHashMap<String, Object>();

    public MessageResponse(String message) {
        this.map.put("message", message);
    }

    public MessageResponse(String message, Object data) {
        this.map.put("message", message);
        this.map.put("data", data);
    }
}*/

@ExtendWith(MockitoExtension.class)
public class MessageResponseTest {
    @Test
    public void testMessageResponse() {
        MessageResponse messageResponse = new MessageResponse("Success");
        assertEquals("Success", messageResponse.getMap().get("message"));
        assertNull(messageResponse.getMap().get("data"));
        Hero hero = new Hero("Superman", SpecialityType.Tank, RaretyType.commun);
        messageResponse = new MessageResponse("Hero created", hero);
        assertEquals("Hero created", messageResponse.getMap().get("message"));
        assertEquals(hero, messageResponse.getMap().get("data"));
    }
}


