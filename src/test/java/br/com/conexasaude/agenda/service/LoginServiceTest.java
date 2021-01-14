package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.dto.LoginDto;
import br.com.conexasaude.agenda.dto.MedicoDto;
import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    LoginService service;

    @Mock
    MedicoService medicoService;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    AuthenticationManager authManager;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service).build();
    }

    @Test
    @DisplayName("deve retornar o token, informações do medico e atendimentos do dia")
    void deveRetornarTokenAtendimentosMedico() {
        String tokenMock = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDU5MTY3OTYsInN1YiI6ImplZmYuYmV6b3MiLCJleHAiOjE2OTIzMT" +
                "Y3OTZ9.3tKYSWIVONKHfqTpbIHVpq-TOpU_TfKTrlxEj9EQ5b-Q81MxS3y3wYbFAzkP-3ci_9NoggK9BUaWuHal-pkM4w";

        LoginDto loginDTOMock = LoginDto.builder()
                .usuario("antonio.chacra")
                .senha("antonio.chacra")
                .build();

        Paciente pacienteMock = Paciente.builder()
                .nome("Cristiano")
                .cpf("15832556720")
                .telefone("22997777777")
                .idade(71)
                .id(1L)
                .build();

        Medico medicoMock = Medico.builder()
                .nome("Antonio Roberto Chacra")
                .usuario("antonio.chacra")
                .especialidade("Endocrinologia")
                .token(tokenMock)
                .agendamentos(Collections.singletonList(Agendamento.builder()
                        .paciente(pacienteMock)
                        .id(1L)
                        .dataHoraAgendamento(LocalDateTime.now())
                        .build()))
                .build();

        List<Agendamento> agendamentosHojeMock = Collections.singletonList(Agendamento.builder()
                .dataHoraAgendamento(LocalDateTime.now())
                .medico(medicoMock)
                .paciente(pacienteMock)
                .build());

        UsernamePasswordAuthenticationToken authenticationTokenMock = new UsernamePasswordAuthenticationToken(
                loginDTOMock.getUsuario(),
                loginDTOMock.getSenha(),
                new ArrayList<>()
        );


        doReturn(medicoMock).when(medicoService).getByUsuario(loginDTOMock.getUsuario());
        doReturn(tokenMock).when(jwtUtil).generateToken(medicoMock.getUsuario());

        MedicoDto medicoDTO = service.login(loginDTOMock);

        assertEquals(medicoDTO.getToken(), medicoMock.getToken());
        assertEquals(medicoDTO.getMedico(), medicoMock.getNome());
        assertEquals(medicoDTO.getEspecialidade(), medicoMock.getEspecialidade());
        assertEquals(medicoDTO.getAgendamentosHoje().get(0).getIdPaciente(), medicoMock.getAgendamentos().get(0).getPaciente().getId());
        assertEquals(medicoDTO.getAgendamentosHoje().get(0).getDataHoraAtendimento(), medicoMock.getAgendamentos().get(0).getDataHoraAgendamento());

        verify(jwtUtil, Mockito.times(1)).generateToken(medicoMock.getUsuario());
        verify(authManager, Mockito.times(1)).authenticate(authenticationTokenMock);
        verify(medicoService, Mockito.times(1)).getByUsuario(medicoMock.getUsuario());
    }
}
