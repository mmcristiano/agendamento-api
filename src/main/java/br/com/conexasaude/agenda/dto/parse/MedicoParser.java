package br.com.conexasaude.agenda.dto.parse;

import br.com.conexasaude.agenda.dto.AgendamentoDto;
import br.com.conexasaude.agenda.dto.MedicoDto;


import br.com.conexasaude.agenda.model.Medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class MedicoParser {

@Autowired
    private AgendamentoParser agendamentoParser;

    public List<MedicoDto> parse(List<Medico> medicos) {
        List<MedicoDto> medicosDto = new LinkedList<>();
        medicos.forEach(medico -> medicosDto.add(this.parse(medico)));

        return medicosDto;
    }

    public MedicoDto parse(Medico medico) {
        List<AgendamentoDto> agendamentos = agendamentoParser.parse(medico.getAgendamentos());

        return MedicoDto.builder()
                .token(medico.getToken())
                .medico(medico.getNome())
                .especialidade(medico.getEspecialidade())
                .agendamentosHoje(agendamentos)
                .build();
    }

}
