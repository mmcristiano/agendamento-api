package br.com.conexasaude.agenda.dto.parse;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.model.Paciente;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PacienteParser {

    public Paciente parse(PacienteDto dto, Paciente paciente) {
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf().replaceAll("[^0-9]", ""));
        paciente.setIdade(dto.getIdade());
        paciente.setTelefone(dto.getTelefone().replaceAll("[^0-9]", ""));

        return paciente;
    }

    public PacienteDto parse(Paciente paciente) {
        return PacienteDto.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .cpf(paciente.getCpf())
                .idade(paciente.getIdade())
                .telefone(paciente.getTelefone())
                .build();
    }

    public Paciente parse(PacienteDto dto) {
        return Paciente.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .idade(dto.getIdade())
                .telefone(dto.getTelefone())
                .build();
    }

    public List<PacienteDto> parse(List<Paciente> pacientes) {
        List<PacienteDto> pacientesDTO = new LinkedList<>();
        pacientes.forEach(paciente -> pacientesDTO.add(this.parse(paciente)));

        return pacientesDTO;
    }


}