package br.com.conexasaude.agenda.controller;


import br.com.conexasaude.agenda.dto.LoginDto;
import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.service.LoginService;
import br.com.conexasaude.agenda.service.MedicoService;
import br.com.conexasaude.agenda.util.JwtUtil;
import br.com.conexasaude.agenda.util.ObjectMapperUtil;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    JwtUtil jwtUtil;

    @Spy
    MedicoService medicoService = new MedicoService();

    @Spy
    LoginService loginService = new LoginService();

    @MockBean
    LoginService service;

    MockMvc mockMvc;

    private LoginDto loginDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        loginDTO = LoginDto.builder()
                .usuario("antonio.chacra")
                .senha("antonio.chacra")
                .build();
    }


    @Test
    @DisplayName("deve realizar login")
    void deveRealizarLogin() throws Exception {
        when(service.login(loginDTO)).thenReturn(MedicoDto.builder().build());

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.asJsonString(loginDTO))
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("deve realizar logoff")
    void deveRealizarLogoff() throws Exception {
        String token = jwtUtil.generateToken(loginDTO.getUsuario());

        mockMvc.perform(
                post("/auth/logoff")
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.asJsonString(loginDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void lancaServiceExceptionDadoTokenNaoEncontrado() {
        String tokenInexistente = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWx2ZXJ01y5jYW1hfmhvIiwiaWF0IjoxNjEwMjU5MjQ3LCJleHAiOjE2MTAzNDU2NDd9.08SxppflNGrU2WJkMbVhwU3XxJYS__XElINrZkcDlclA3EYC44a5iGK9kBK_AhGBYJ8u23ziAUZh42gmvCFNpw";

        doThrow(new ServiceException("Token não encontrado: "+ tokenInexistente)).when(medicoService).removeToken(tokenInexistente);

        Throwable exception = assertThrows(ServiceException.class,() -> medicoService.removeToken(tokenInexistente));

        assertThat(exception.getMessage()).isEqualTo("Token não encontrado: "+ tokenInexistente);

        verify(medicoService, times(1)).removeToken(tokenInexistente);


    }

    @Test
    void lancaBadCredentialsExceptionDadoCredenciaisInvalidas() {

        LoginDto loginDto = new LoginDto().builder().usuario("user").senha("pass").build();

        doThrow(new ServiceException("Credenciais Inválidas.")).when(loginService).login(loginDto);

        Throwable exception = assertThrows(ServiceException.class,() -> loginService.login(loginDto));

        assertThat(exception.getMessage()).isEqualTo("Credenciais Inválidas.");

        verify(loginService, times(1)).login(loginDto);


    }

}
