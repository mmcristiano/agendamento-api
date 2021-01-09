package br.com.conexasaude.agenda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MedicoDto {

    private String token;
    private String medico;
    private String especialidade;
    @JsonProperty("agendamentos_hoje")
    private List<AgendamentoDto> agendamentosHoje;
}
