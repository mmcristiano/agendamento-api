package br.com.conexasaude.agenda.controller;
import br.com.conexasaude.agenda.dto.AgendamentoDto;
import br.com.conexasaude.agenda.util.JwtUtil;
import br.com.conexasaude.agenda.util.ObjectMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendamentoControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    JwtUtil jwtUtil;

    MockMvc mockMvc;

    private AgendamentoDto agendamentoDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        agendamentoDTO = AgendamentoDto.builder()
                .idPaciente(1L)
                .build();
    }


    @Test
    @WithMockUser(value = "spring")
    @DisplayName("deve retornar erro, pois falta data de atendimento")
    void deveRetornarErro400() throws Exception {

        mockMvc.perform(
                post("/agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.asJsonString(agendamentoDTO))
        ).andExpect(status().is4xxClientError());
    }

}
