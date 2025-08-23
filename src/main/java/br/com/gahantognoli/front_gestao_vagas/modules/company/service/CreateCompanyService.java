package br.com.gahantognoli.front_gestao_vagas.modules.company.service;

import br.com.gahantognoli.front_gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.gahantognoli.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCompanyService {
    public void execute(CreateCompanyDTO createCompanyDTO) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateCompanyDTO> request = new HttpEntity<>(createCompanyDTO, headers);

        rt.postForEntity("http://localhost:8080/company", request, String.class);
    }
}
