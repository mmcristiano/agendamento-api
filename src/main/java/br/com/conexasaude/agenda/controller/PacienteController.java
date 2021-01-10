package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.PacienteParser;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.PacienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/paciente")
@Api(value = "Paciente",description = "Realiza operações relacionadas a pacientes", tags = { "Paciente" })

public class PacienteController {

    @Autowired
    private PacienteParser parser;
    @Autowired
    private PacienteService service;

    @ApiOperation(value = "Retorna uma lista de pacientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de todos os pacientes"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<List<PacienteDto>> get() {
        return new ResponseEntity<List<PacienteDto>>(parser.parse(service.get()),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o paciente do identificador"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> get(@PathVariable("id") Long id) {
        Paciente paciente = service.get(id);

        return new ResponseEntity<PacienteDto>(parser.parse(paciente),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna informações do paciente criado"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<PacienteDto> save(@Valid  @RequestBody PacienteDto dto) {
        Paciente paciente= service.save(parser.parse(dto, new Paciente()));

        return new ResponseEntity<PacienteDto>(parser.parse(paciente),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualiza os dados de um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna as novas informações do usuário"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> update(@PathVariable("id") Long id,@Valid @RequestBody PacienteDto dto) {
        Paciente paciente = service.get(id);
        Paciente pacienteNew = parser.parse(dto, paciente);

        return new ResponseEntity<PacienteDto>(parser.parse(service.save(pacienteNew)), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna vazio quando operação realizada com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Paciente do identificador fornecido não foi encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
