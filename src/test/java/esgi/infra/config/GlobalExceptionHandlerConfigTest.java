package esgi.infra.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerConfigTest {

    @InjectMocks
    private GlobalExceptionHandlerConfig globalExceptionHandlerConfig;

    @Mock
    private Exception exception;

    @Test
    public void handleException_returnsCorrectResponseEntity() {
        String expectedMessage = "test exception";
        when(exception.getMessage()).thenReturn(expectedMessage);

        ResponseEntity<?> response = globalExceptionHandlerConfig.handleException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(expectedMessage, responseBody.get("message"));
    }
}
