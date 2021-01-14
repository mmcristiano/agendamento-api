package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.dto.parse.PacienteParser;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.PacienteService;
import br.com.conexasaude.agenda.util.JwtUtil;
import br.com.conexasaude.agenda.util.ObjectMapperUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PacienteControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    JwtUtil jwtUtil;

    @MockBean
    PacienteService service;

    @MockBean
    PacienteParser parser;

    MockMvc mockMvc;

    private Paciente pacienteMock;
    private PacienteDto pacienteMockDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        pacienteMock = Paciente.builder()
                .nome("Cristiano Maia")
                .cpf("49575606094")
                .telefone("021999999999")
                .idade(49)
                .build();
        pacienteMockDTO = PacienteDto.builder()
                .nome("Cristiano Maia")
                .cpf("49575606094")
                .telefone("021999999999")
                .idade(49)
                .build();
    }

    @Test
    @DisplayName("deve retornar lista de pacientes")
    void deveListarTodos() throws Exception {
        mockMvc.perform(
                get("/paciente")
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("deve cadastrar um paciente")
    void deveCadastrar() throws Exception {
        when(parser.parse(any(PacienteDto.class), any(Paciente.class))).thenReturn(pacienteMock);
        when(parser.parse(any(Paciente.class))).thenReturn(pacienteMockDTO);
        when(service.save(any(Paciente.class))).thenReturn(pacienteMock);


        mockMvc.perform(
                post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.asJsonString(pacienteMockDTO))
        ).andExpect(status().isCreated());
    }


    @Test
    @DisplayName("deve retonar paciente buscando por id")
    void deveRetornarPorId() throws Exception {
        when(service.get(1l)).thenReturn(pacienteMock);

        mockMvc.perform(
                get("/paciente")
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("deve editar paciente")
    void deveRetornarPacienteEditado() throws Exception {
        when(service.get(1L)).thenReturn(pacienteMock);
        when(parser.parse(any(PacienteDto.class), any(Paciente.class))).thenReturn(pacienteMock);
        when(parser.parse(any(Paciente.class))).thenReturn(pacienteMockDTO);

        mockMvc.perform(
                put("/paciente/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.asJsonString(pacienteMockDTO))
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("deve remover paciente")
    void deveRemoverPaciente() throws Exception {
        when(service.get(1L)).thenReturn(pacienteMock);

        mockMvc.perform(
                delete("/paciente/" + 1)
        ).andExpect(status().isNoContent());
    }

}
