package br.com.conexasaude.agenda.service;


import br.com.conexasaude.agenda.model.Medico;
import br.com.conexasaude.agenda.repository.MedicoRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    MedicoService service;
    @Mock
    MedicoRepository medicoRepository;

    private Medico medicoMock;
    private String usuarioMock;
    private String tokenMock;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service).build();

        usuarioMock = "antonio.chacra";
        medicoMock = Medico.builder()
                .nome("Antonio Roberto Chacra")
                .usuario("antonio.chacra")
                .especialidade("Endocrinologia")
                .senha("antonio.chacra")
                .build();
        tokenMock = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDU5MTY3OTYsInN1YiI6ImplZmYuYmV6b3MiLCJleHAiOjE2OTI" +
                "zMTY3OTZ9.3tKYSWIVONKHfqTpbIHVpq-TOpU_TfKTrlxEj9EQ5b-Q81MxS3y3wYbFAzkP-3ci_9NoggK9BUaWuHal-pkM4w";
    }

    @Test
    @DisplayName("deve retornar userDetails de acordo com usuario passado")
    void deveRetornarUserDetailsPorUsuario() {
        doReturn(Optional.of(medicoMock)).when(medicoRepository).findByUsuario(usuarioMock);

        UserDetails userDetails = service.loadUserByUsername(usuarioMock);

        assertEquals(userDetails.getUsername(), usuarioMock);
        assertEquals(userDetails.getPassword(), medicoMock.getSenha());

        verify(medicoRepository, times(1)).findByUsuario(usuarioMock);
    }


    @Test
    @DisplayName("deve retornar medico de acordo com usuario passado")
    void deveRetornarMedicoPorUsuario() {
        doReturn(Optional.of(medicoMock)).when(medicoRepository).findByUsuario(usuarioMock);

        Medico medico = service.getByUsuario(usuarioMock);

        assertEquals(medico.getNome(), medicoMock.getNome());
        assertEquals(medico.getUsuario(), medicoMock.getUsuario());
        assertEquals(medico.getSenha(), medicoMock.getSenha());
        assertEquals(medico.getEspecialidade(), medicoMock.getEspecialidade());

        verify(medicoRepository, times(1)).findByUsuario(usuarioMock);
    }


    @Test
    @DisplayName("deve retornar erro ao buscar medico pelo usuario")
    void deveRetornarErroBuscarMedicoPorUsuario() {
        doReturn(Optional.empty()).when(medicoRepository).findByUsuario(usuarioMock);

        Throwable exception = assertThrows(ServiceException.class,
                () -> service.getByUsuario(usuarioMock));

        assertThat(exception.getMessage()).isEqualTo("Usuário " + usuarioMock + " não existente.");

        verify(medicoRepository, times(1)).findByUsuario(usuarioMock);
    }


    @Test
    @DisplayName("deve atribuir token ao medico e salvar")
    void deveAtribuirTokenMedicoSalvar() {
        medicoMock.setToken(tokenMock);
        service.save(medicoMock);

        assertEquals(medicoMock.getToken(), tokenMock);

        verify(medicoRepository, times(1)).save(medicoMock);
    }


    @Test
    @DisplayName("deve remover token do medico e salvar")
    void deveRemoverTokenMedicoSalvar() {
        medicoMock.setToken(tokenMock);

        doReturn(Optional.of(medicoMock)).when(medicoRepository).findTopByToken(tokenMock);

        service.removeToken(tokenMock);

        assertNull(medicoMock.getToken());

        verify(medicoRepository, times(1)).findTopByToken(tokenMock);
    }


    @Test
    @DisplayName("deve retornar erro ao buscar medico pelo token")
    void deveRetornarErroBuscarMedicoPorToken() {
        doReturn(Optional.empty()).when(medicoRepository).findTopByToken(tokenMock);

        Throwable exception = assertThrows(ServiceException.class,
                () -> service.removeToken(tokenMock));

        assertThat(exception.getMessage()).isEqualTo("Token não encontrado: " + tokenMock);

        verify(medicoRepository, times(1)).findTopByToken(tokenMock);
    }


    @Test
    @DisplayName("deve retornar erro ao obter medico logado")
    void deveRetornarMedicoLogado() {
        Throwable exception = assertThrows(ServiceException.class,
                () -> service.getMedicoLogado());

        assertThat(exception.getMessage()).isEqualTo("Não foi possível recuperar o usuário logado.");
    }
}
