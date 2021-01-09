package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    MedicoService medicoService;

    @Autowired
    private AgendamentoRepository repository;

    public Agendamento agendarConsulta(Agendamento agendamento) {
        agendamento.setMedico(medicoService.getMedicoLogado());
        return repository.save(agendamento);
    }

}
