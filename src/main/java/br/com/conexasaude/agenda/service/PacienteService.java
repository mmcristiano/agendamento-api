package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.repository.PacienteRepository;
import org.hibernate.service.spi.ServiceException;
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

    public Paciente save(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente get(Long id) {
        return repository.findById(id).orElseThrow( () -> new ServiceException("Not found."));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
