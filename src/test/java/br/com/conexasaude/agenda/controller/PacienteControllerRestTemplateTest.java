package br.com.conexasaude.agenda.controller;

import br.com.conexasaude.agenda.dto.PacienteDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PacienteControllerRestTemplateTest {



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
