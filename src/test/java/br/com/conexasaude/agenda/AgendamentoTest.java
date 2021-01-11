package br.com.conexasaude.agenda;

import br.com.conexasaude.agenda.repository.MedicoRepository;
import br.com.conexasaude.agenda.repository.PacienteRepository;

import br.com.conexasaude.agenda.service.MedicoService;
import br.com.conexasaude.agenda.service.PacienteService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoTest {


    @InjectMocks
    PacienteService pacienteService;
    @Mock
    PacienteRepository pacienteRepository;

    @Spy
    MedicoService medicoService = new MedicoService();


    @BeforeEach
    void setup() {
        medicoService = Mockito.spy(new MedicoService());
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
        doThrow(new ServiceException("Não foi possível recuperar o usuário logado.")).when(medicoService).getMedicoLogado();

        Throwable exception = assertThrows(ServiceException.class,() -> medicoService.getMedicoLogado());

        assertThat(exception.getMessage()).isEqualTo("Não foi possível recuperar o usuário logado.");

        verify(medicoService, times(1)).getMedicoLogado();
    }


}
