package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.config.Constantes;
import br.com.conexasaude.agenda.dto.LoginDto;
import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("auth")
@Api(value = "Login",description = "Login e Logoff na aplicação", tags = { "Login/Logout" })
public class LoginController {

    @Autowired
    private LoginService service;

    @ApiOperation(value = "Realiza o login e gera um token de authenticação para o usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna um token JWT com algumas informações adicionais do médico e seus agendamentos do dia"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/login")
    public ResponseEntity<MedicoDto> login(@Valid @RequestBody LoginDto dto) {
        return new ResponseEntity<MedicoDto>(service.login(dto),HttpStatus.OK);
    }

    @ApiOperation(value = "Realiza o logoff na aplicação e invalida o token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna OK e uma mensagem de Logoff realizado"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/logoff")
    public ResponseEntity<?> logoff(@RequestHeader(name = "Authorization") String token) {
        service.logoff(token.replace(Constantes.PREFIX_AUTH, "").trim());
        return ResponseEntity.ok().body("Logoff realizado.");
    }

}
