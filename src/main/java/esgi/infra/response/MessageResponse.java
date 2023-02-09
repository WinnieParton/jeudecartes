package esgi.infra.response;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
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
