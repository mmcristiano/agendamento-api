package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.MedicoParser;
import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.MedicoService;
import br.com.conexasaude.agenda.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


//@RestController
//@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Autowired
    private MedicoParser parser;

    @GetMapping
    public ResponseEntity<List<MedicoDto>> get() {
        List<Medico> medicos = service.get();

        /* Retornar somente agendamentos de hoje.
        medicos.stream().forEach(
            medico -> {
                medico.getAgendamentos().removeIf(agendamento -> !agendamento.getDataHoraAgendamento().toLocalDate().equals(LocalDate.now()));
            }
        );

         */

        medicos.removeIf(medico ->
                    !medico.equals(service.getMedicoLogado()));

        return new ResponseEntity<List<MedicoDto>>(parser.parse(medicos),
                HttpStatus.OK);
    }

}
