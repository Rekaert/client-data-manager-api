package hu.rekaertsey.client_data_manager_api.unit.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nimbusds.jose.JWSAlgorithm;

import hu.rekaertsey.client_data_manager_api.config.AppJwtProperties;
import hu.rekaertsey.client_data_manager_api.service.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private AppJwtProperties appJwtProperties;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void testGenerateJWT_NotEmpty() {
        when(appJwtProperties.getKey()).thenReturn(new SecretKeySpec("test-secret-key#1-test-secret-key#2-test-secret-key#3".getBytes(), "HmacSHA256"));
        when(appJwtProperties.getAlgorithm()).thenReturn(JWSAlgorithm.HS256);
        when(appJwtProperties.getIssuer()).thenReturn("Issuer");
        when(appJwtProperties.getExpiresIn()).thenReturn(Duration.ofMinutes(60));

        Map<String, Object> claims = Map.of("scope", "ADMIN");

        String result = jwtService.generateJWT(claims);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGenerateJWT_InvalidKey() {
        when(appJwtProperties.getKey()).thenReturn(null);
        when(appJwtProperties.getAlgorithm()).thenReturn(JWSAlgorithm.HS256);

        Map<String, Object> claims = Map.of("scope", "USER");

        assertThrows(RuntimeException.class, () -> jwtService.generateJWT(claims));
    }

}
