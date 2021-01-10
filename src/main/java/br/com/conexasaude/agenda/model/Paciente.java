package br.com.conexasaude.agenda.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 11)
    private String cpf;
    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotNull
    @Min(0)
    private Integer idade;
    @NotBlank
    @Size(max = 12)
    private String telefone;

    @OneToMany(orphanRemoval = true,
            mappedBy = "paciente")
    List<Agendamento> agendamentos;

}