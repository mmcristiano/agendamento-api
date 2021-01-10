package br.com.conexasaude.agenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Builder
@Data
public class AgendamentoDto {

    @JsonProperty("id_paciente")
    private Long idPaciente;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("data_hora_atendimento")
    @ApiModelProperty(required = true,example = "2021-01-11 09:00:00")
    @NotNull
    private LocalDateTime dataHoraAtendimento;

}
