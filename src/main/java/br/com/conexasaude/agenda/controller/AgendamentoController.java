package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.AgendamentoDto;
import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.AgendamentoParser;
import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoParser parser;

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDto> agendarConsulta(@RequestBody AgendamentoDto dto) {
        Agendamento parse = parser.parse(dto, new Agendamento());

        Agendamento agendamento = agendamentoService.agendarConsulta(parse);

        return new ResponseEntity<AgendamentoDto>(parser.parse(agendamento),
                HttpStatus.CREATED);

    }
}
