package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> get() {
        return repository.findAll();
    }

}
