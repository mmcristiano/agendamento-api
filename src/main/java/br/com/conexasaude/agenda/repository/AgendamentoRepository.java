package br.com.conexasaude.agenda.repository;

import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
}
