package br.com.conexasaude.agenda.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PacienteDto {

    private Long id;
    private String cpf;
    private String nome;
    private Integer idade;
    private String telefone;
}
