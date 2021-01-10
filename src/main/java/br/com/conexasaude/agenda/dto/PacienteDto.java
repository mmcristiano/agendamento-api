package br.com.conexasaude.agenda.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class PacienteDto {

    private Long id;
    @NotBlank
    private String cpf;
    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotNull
    @Min(0)
    private Integer idade;
    @NotBlank
    private String telefone;
}
