package br.com.conexasaude.agenda;

import br.com.conexasaude.agenda.dto.PacienteDto;
import br.com.conexasaude.agenda.model.Paciente;
import br.com.conexasaude.agenda.service.PacienteService;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PacienteTest {


    // Teste em banco real
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void retornaPacienteDadoIdExistenteNoDB() throws Exception {
        //
        PacienteDto p = PacienteDto.builder()
                .id(1L)
                .nome("Cristiano Maia")
                .cpf("49575606094")
                .telefone("021999999999")
                .idade(28)
                .build();

        //act
        ResponseEntity<PacienteDto> response =
        restTemplate.getForEntity("/paciente/" + p.getId(), PacienteDto.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(p);

    }

}
