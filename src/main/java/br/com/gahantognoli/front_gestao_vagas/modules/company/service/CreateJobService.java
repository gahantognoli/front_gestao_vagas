package br.com.gahantognoli.front_gestao_vagas.modules.company.service;

import br.com.gahantognoli.front_gestao_vagas.modules.company.dto.CreateJobsDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CreateJobService {
    public String execute(String token, CreateJobsDTO createJobsDTO){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CreateJobsDTO> requestEntity = new HttpEntity<>(createJobsDTO, headers);

        return rt.postForObject("http://localhost:8080/company/job", requestEntity, String.class);
    }
}
