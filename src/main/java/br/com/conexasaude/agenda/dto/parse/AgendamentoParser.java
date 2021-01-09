package br.com.conexasaude.agenda.dto.parse;

import br.com.conexasaude.agenda.dto.PacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.conexasaude.agenda.dto.AgendamentoDto;
import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.PacienteService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AgendamentoParser {

    @Autowired
    private PacienteService pacienteService;

    public Agendamento parse(AgendamentoDto dto, Agendamento agendamento) {
        Paciente paciente = pacienteService.get(dto.getIdPaciente());

        agendamento.setDataHoraAgendamento(dto.getDataHoraAtendimento());
        agendamento.setPaciente(agendamento.getPaciente());
        agendamento.setPaciente(paciente);

        return agendamento;
    }

    public AgendamentoDto parse(Agendamento agendamento) {
        return AgendamentoDto.builder()
                .dataHoraAtendimento(agendamento.getDataHoraAgendamento())
                .idPaciente(agendamento.getPaciente().getId())
                .build();
    }

    public List<AgendamentoDto> parse(List<Agendamento> agendamentos) {
        List<AgendamentoDto> agendamentosDto = new LinkedList<>();
        agendamentos.forEach(agendamento -> agendamentosDto.add(this.parse(agendamento)));

        return agendamentosDto;
    }


}
