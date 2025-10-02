package com.distribuidora.backend.auth;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class GoogleTokenVerifier {

    public static Map<String, String> verify(String idToken) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
                    .queryParam("id_token", idToken)
                    .toUriString();

            RestTemplate rt = new RestTemplate();
            Map<String, Object> response = rt.getForObject(url, Map.class);
            if (response == null) return null;

            String email = (String) response.get("email");
            String name = (String) response.get("name");
            if (email == null) return null;

            Map<String, String> out = new HashMap<>();
            out.put("email", email);
            out.put("name", name);
            return out;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
