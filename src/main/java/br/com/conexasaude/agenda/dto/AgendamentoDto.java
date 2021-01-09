package br.com.conexasaude.agenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class AgendamentoDto {

    @JsonProperty("id_paciente")
    private Long idPaciente;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("data_hora_atendimento")
    private LocalDateTime dataHoraAtendimento;

}
