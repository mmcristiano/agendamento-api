package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.config.Constantes;
import br.com.conexasaude.agenda.dto.LoginDto;
import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/login")
    public ResponseEntity<MedicoDto> login(@RequestBody LoginDto dto) {
        return new ResponseEntity<MedicoDto>(service.login(dto),HttpStatus.OK);
    }

    @PostMapping("/logoff")
    public ResponseEntity<?> logoff(@RequestHeader(name = "Authorization") String token) {
        service.logoff(token.replace(Constantes.PREFIX_AUTH, "").trim());
        return ResponseEntity.ok().body("Logoff realizado.");
    }

}
