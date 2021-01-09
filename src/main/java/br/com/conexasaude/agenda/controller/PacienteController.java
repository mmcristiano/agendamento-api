package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.PacienteParser;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.PacienteService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteParser parser;
    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<PacienteDto>> get() {
        return new ResponseEntity<List<PacienteDto>>(parser.parse(service.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> get(@PathVariable("id") Long id) {
        Paciente paciente = service.get(id);

        return new ResponseEntity<PacienteDto>(parser.parse(paciente),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PacienteDto> save(@RequestBody PacienteDto dto) {
        Paciente paciente= service.save(parser.parse(dto, new Paciente()));

        return new ResponseEntity<PacienteDto>(parser.parse(paciente),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> update(@PathVariable("id") Long id, @RequestBody PacienteDto dto) {
        Paciente paciente = service.get(id);
        Paciente pacienteNew = parser.parse(dto, paciente);

        return new ResponseEntity<PacienteDto>(parser.parse(service.save(pacienteNew)), HttpStatus.OK);
    }

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
