package hu.rekaertsey.client_data_manager_api.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.rekaertsey.client_data_manager_api.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getToken(@RequestBody Map<String, Object> claims) {
        return jwtService.generateJWT(claims);
    }

}
