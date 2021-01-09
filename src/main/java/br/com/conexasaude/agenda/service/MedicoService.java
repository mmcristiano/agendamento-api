package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.repository.MedicoRepository;
import br.com.conexasaude.agenda.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public List<Medico> get() {
        return repository.findAll();
    }

}
