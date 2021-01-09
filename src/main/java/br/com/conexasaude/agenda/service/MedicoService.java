package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.repository.MedicoRepository;
import org.hibernate.service.spi.ServiceException;
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

    public Medico getMedicoLogado() {
        return getById(1L);
    }

    public Medico getById(Long id) {
        return repository.findById(id).orElseThrow( () -> new ServiceException("Not found."));
    }

}
