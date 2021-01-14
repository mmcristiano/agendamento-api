package br.com.conexasaude.agenda.service;


import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.repository.PacienteRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    PacienteService service;
    @Mock
    PacienteRepository repository;

    private Paciente pacienteMock;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service).build();
        pacienteMock = Paciente.builder()
                .id(1L)
                .nome("Cristiano Morais")
                .idade(30)
                .cpf("94285648008")
                .telefone("22777777777")
                .build();
    }


    @Test
    @DisplayName("deve retornar lista de pacientes")
    void deveRetornarListaDePacientes() {
        List<Paciente> pacientes = Collections.singletonList(pacienteMock);

        doReturn(pacientes).when(repository).findAll();

        List<Paciente> listaPacientes = service.get();

        assertNotNull(listaPacientes);
        assertEquals(listaPacientes.get(0).getNome(), pacienteMock.getNome());
        assertEquals(listaPacientes.get(0).getCpf(), pacienteMock.getCpf());
        assertEquals(listaPacientes.get(0).getIdade(), pacienteMock.getIdade());
        assertEquals(listaPacientes.get(0).getTelefone(), pacienteMock.getTelefone());

        verify(repository, times(1)).findAll();
    }


    @Test
    @DisplayName("deve retornar paciente de acordo com Id informado")
    void deveRetornarPacientePeloId() {
        Long idPaciente = 1L;

        doReturn(Optional.of(pacienteMock)).when(repository).findTopById(idPaciente);

        Paciente paciente = service.get(idPaciente);

        assertNotNull(paciente);
        assertEquals(paciente.getNome(), paciente.getNome());
        assertEquals(paciente.getCpf(), paciente.getCpf());
        assertEquals(paciente.getIdade(), paciente.getIdade());
        assertEquals(paciente.getTelefone(), paciente.getTelefone());

        verify(repository, times(1)).findTopById(idPaciente);
    }

    @Test
    @DisplayName("deve retornar erro ao buscar paciente pelo id")
    void deveRetornarErroPacientePeloId() {
        Long idPacienteInexistente = 1L;

        doReturn(Optional.empty()).when(repository).findTopById(idPacienteInexistente);

        Throwable exception = assertThrows(ServiceException.class,
                () -> service.get(idPacienteInexistente));

        assertThat(exception.getMessage()).isEqualTo("Paciente não encontrado.");

        verify(repository, times(1)).findTopById(idPacienteInexistente);
    }

    @Test
    @DisplayName("deve salvar paciente")
    void deveSalvarPaciente() {
        doReturn(pacienteMock).when(repository).save(pacienteMock);

        Paciente pacienteSalvo = service.save(pacienteMock);

        assertEquals(pacienteSalvo.getNome(), pacienteMock.getNome());
        assertEquals(pacienteSalvo.getTelefone(), pacienteMock.getTelefone());
        assertEquals(pacienteSalvo.getCpf(), pacienteMock.getCpf());
        assertEquals(pacienteSalvo.getIdade(), pacienteMock.getIdade());

        verify(repository, times(1)).save(pacienteSalvo);
    }

    @Test
    @DisplayName("deve remover paciente")
    void deveRemoverPaciente() {
        service.delete(pacienteMock.getId());

        verify(repository, times(1)).deleteById(pacienteMock.getId());
    }
}
