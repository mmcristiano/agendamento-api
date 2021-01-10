package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.security.JwtUtil;
import br.com.conexasaude.agenda.dto.LoginDto;
import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.dto.parse.MedicoParser;
import br.com.conexasaude.agenda.model.Medico;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MedicoParser medicoParser;

    public MedicoDto login(LoginDto dto) {

        try {
            //Autenticar
            Authentication authenticate = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsuario(),
                            dto.getSenha(),
                            new ArrayList<>()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            // Recuperar medico por usuário
            Medico medico = medicoService.getByUsuario(dto.getUsuario());

            // Gerar token
            String token = jwtUtil.generateToken(medico.getUsuario());
            medico.setToken(token);

            // gravar token
            medicoService.save(medico);

            // Retornar DTO
            return medicoParser.parse(medico);

        } catch (BadCredentialsException e) {
            throw new ServiceException("Credenciais Inválidas", e);
        }

    }

    public void logoff(String token) {
        medicoService.removeToken(token);
    }
}
