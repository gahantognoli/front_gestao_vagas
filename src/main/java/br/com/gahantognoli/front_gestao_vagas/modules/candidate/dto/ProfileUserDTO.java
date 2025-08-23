package br.com.gahantognoli.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserDTO {
    private UUID id;
    private String username;
    private String email;
    private String name;
    private String description;
}
