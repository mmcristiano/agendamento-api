package br.com.conexasaude.agenda.service;

import br.com.conexasaude.agenda.model.Agendamento;
import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.repository.AgendamentoRepository;
import br.com.conexasaude.agenda.repository.PacienteRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    PacienteService pacienteService;
    @Mock
    PacienteRepository pacienteRepository;

    @Spy
    MedicoService medicoServiceSpy = new MedicoService();



    private MockMvc mockMvc;
    @Mock
    AgendamentoRepository repository;
    @Mock
    MedicoService medicoService;
    @InjectMocks
    AgendamentoService service;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service).build();
    }

    @Test
    @DisplayName("deve retornar o agendamento com o médico preenchido")
    void deveRetornarAgendamentoComMedicoPreenchido() {
        LocalDateTime dataHora = LocalDateTime.now();

        Agendamento agendamentoMock = Agendamento.builder()
                .dataHoraAgendamento(dataHora)
                .paciente(Paciente.builder()
                        .nome("Cristiano Morais")
                        .telefone("21998888888")
                        .cpf("48979081081")
                        .idade(36)
                        .build())
                .build();

        Medico medico = Medico.builder()
                .token("eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDU4NDA0MTAsInN1YiI6ImplZmYuYmV6b3MiLCJleHAiOjE2OTIy" +
                        "NDA0MTB9.d4JtH13a_IivLsZg5WJDIyEfXnsrymoxCmyjsjbyC4d0Z2THsVFvy_1i1hr_nP-iTWArvxzzBczPFpOYmnbTGw")
                .nome("antonio.chacra")
                .usuario("antonio.chacra")
                .build();

        doReturn(agendamentoMock).when(repository).save(agendamentoMock);
        doReturn(medico).when(medicoService).getMedicoLogado();

        Agendamento agendamento = service.agendarConsulta(agendamentoMock);

        assertEquals(agendamento.getMedico().getNome(), medico.getNome());
        assertEquals(agendamento.getPaciente().getNome(), agendamentoMock.getPaciente().getNome());
        assertEquals(agendamento.getPaciente().getTelefone(), agendamentoMock.getPaciente().getTelefone());
        assertEquals(agendamento.getPaciente().getCpf(), agendamentoMock.getPaciente().getCpf());
        assertEquals(agendamento.getPaciente().getIdade(), agendamentoMock.getPaciente().getIdade());
        assertEquals(agendamento.getDataHoraAgendamento(), agendamentoMock.getDataHoraAgendamento());


        verify(repository, Mockito.times(1)).save(agendamentoMock);
        verify(medicoService, Mockito.times(1)).getMedicoLogado();
    }


    @Test
    void lancaServiceExceptionDadoPacienteNaoEncontrado() {

        Long id = 0L;
        doReturn(Optional.empty()).when(pacienteRepository).findTopById(id);

        Throwable exception = assertThrows(ServiceException.class,() -> pacienteService.get(id));

        assertThat(exception.getMessage()).isEqualTo("Paciente não encontrado.");

        verify(pacienteRepository, times(1)).findTopById(id);
    }

    @Test
    void lancaServiceExceptionDadoMedicoNaoLogado() {
        doThrow(new ServiceException("Não foi possível recuperar o usuário logado.")).when(medicoServiceSpy).getMedicoLogado();

        Throwable exception = assertThrows(ServiceException.class,() -> medicoServiceSpy.getMedicoLogado());

        assertThat(exception.getMessage()).isEqualTo("Não foi possível recuperar o usuário logado.");

        verify(medicoServiceSpy, times(1)).getMedicoLogado();
    }

}
