package br.com.conexasaude.agenda.repository;

import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {
    Optional<Medico> findByUsuario(String nome);

    Optional<Medico> findTopByToken(String token);
}
