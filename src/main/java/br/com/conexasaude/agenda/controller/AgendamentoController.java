package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.AgendamentoDto;
import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.AgendamentoParser;
import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.AgendamentoService;
import br.com.conexasaude.agenda.service.PacienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("agendamento")
@Api(value = "Agendamento",description = "Realiza agendamento de consulta", tags = { "Agendar Consulta" })
public class AgendamentoController {

    @Autowired
    private AgendamentoParser parser;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private PacienteService pacienteService;


    @ApiOperation(value = "Realiza agendamento de consulta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna a consulta criada"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<AgendamentoDto> agendarConsulta(@Valid  @RequestBody AgendamentoDto dto) {

        Agendamento parse = parser.parse(dto, new Agendamento());

        Agendamento agendamento = agendamentoService.agendarConsulta(parse);

        return new ResponseEntity<AgendamentoDto>(parser.parse(agendamento),
                HttpStatus.CREATED);

    }
}
