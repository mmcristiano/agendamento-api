package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.model.Usuario;
import br.com.conexasaude.agenda.repository.MedicoRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicoService implements UserDetailsService {

    @Autowired
    private MedicoRepository repository;

    public List<Medico> get() {
        return repository.findAll();
    }

    public Medico getMedicoLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            throw new ServiceException("Não foi possível recuperar o usuário logado.");
        }


        return getByUsuario(authentication.getPrincipal().toString());
    }

    public Medico getById(Long id) {
        return repository.findById(id).orElseThrow( () -> new ServiceException("Médico não encontrado."));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new Usuario(getByUsuario(s));
    }

    public Medico getByUsuario(String nome) {
        Optional<Medico> usuario = repository.findByUsuario(nome);

        return usuario.orElseThrow(() ->
                new ServiceException("Usuário " + nome + " não existente."));

    }

    public Medico save(Medico medico) {
        return repository.save(medico);
    }


    public void removeToken(String token) {
            Optional<Medico> medico = repository.findTopByToken(token);

            if (!medico.isPresent()) {
                throw new ServiceException("Token não encontrado: " + token);
            }

            medico.get().setToken(null);
            repository.save(medico.get());
    }
}
