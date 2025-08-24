package br.com.gahantognoli.front_gestao_vagas.modules.company.service;

import br.com.gahantognoli.front_gestao_vagas.modules.candidate.dto.JobDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;

@Service
public class ListAllJobsCompanyService {
    public List<JobDTO> execute(String token) {
        RestTemplate rt = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        var result = rt.exchange("http://localhost:8080/company/job", HttpMethod.GET, httpEntity, responseType);

        return result.getBody();
    }
}
