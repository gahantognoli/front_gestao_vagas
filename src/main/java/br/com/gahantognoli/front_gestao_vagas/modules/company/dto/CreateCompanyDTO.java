package br.com.gahantognoli.front_gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateCompanyDTO {
    private String name;
    private String email;
    private String username;
    private String password;
    private String website;
    private String description;
}
