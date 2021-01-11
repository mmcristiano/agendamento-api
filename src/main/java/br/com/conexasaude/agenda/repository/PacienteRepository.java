package br.com.conexasaude.agenda.repository;

import br.com.conexasaude.agenda.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Optional<Paciente> findTopById(Long id);
}
