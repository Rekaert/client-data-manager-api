package hu.rekaertsey.client_data_manager_api.service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.SignedJWT;

import hu.rekaertsey.client_data_manager_api.config.AppJwtProperties;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final AppJwtProperties appJwtProperties;

    public String generateJWT(Map<String, Object> claims) {
        var key = appJwtProperties.getKey();
        var algorithm = appJwtProperties.getAlgorithm();

        var header = new JWSHeader(algorithm);
        var claimsSet = buildClaimsSet(claims);

        var jwt = new SignedJWT(header, claimsSet);

        try {
            var signer = new MACSigner(key);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("Unable to generate JWT", e);
        }

        return jwt.serialize();
    }

    private JWTClaimsSet buildClaimsSet(Map<String, Object> claims) {
        var issuer = appJwtProperties.getIssuer();
        var issuedAt = Instant.now();
        var expirationTime = issuedAt.plus(appJwtProperties.getExpiresIn());

        var builder = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expirationTime));

        claims.forEach(builder::claim);

        return builder.build();
    }

}
