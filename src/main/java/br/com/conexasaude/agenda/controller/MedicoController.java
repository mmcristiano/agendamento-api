package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.PacienteDto;
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

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @GetMapping
    public ResponseEntity<?> get() {
        return new ResponseEntity<List<?>>(service.get(), HttpStatus.OK);
    }

}
