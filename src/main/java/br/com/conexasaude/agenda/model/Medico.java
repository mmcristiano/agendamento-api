package br.com.conexasaude.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String usuario;
    @NotBlank
    @Size(max = 64)
    private String senha;
    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Size(max = 100)
    private String especialidade;

    private String token;

    @OneToMany(mappedBy = "medico")
    List<Agendamento> agendamentos;

}
