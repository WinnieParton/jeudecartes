package esgi.infra.response;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
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
}
