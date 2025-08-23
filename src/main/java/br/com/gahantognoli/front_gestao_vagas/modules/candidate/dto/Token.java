package br.com.gahantognoli.front_gestao_vagas.modules.candidate.dto;

import lombok.Data;

import java.util.List;

@Data
public class Token {
    private  String access_token;
    private List<String> roles;
    private Long expires_in;
}
