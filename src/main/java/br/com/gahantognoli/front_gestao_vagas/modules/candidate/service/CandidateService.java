package br.com.gahantognoli.front_gestao_vagas.modules.candidate.service;

import br.com.gahantognoli.front_gestao_vagas.modules.candidate.dto.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateService {

    public Token login(String username, String password) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(data, headers);

        return rt.postForObject("http://localhost:8080/auth/candidate", requestEntity, Token.class);
    }
}
