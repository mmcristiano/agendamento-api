package br.com.conexasaude.agenda.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class Usuario extends User {

    private Medico medico;

    public Usuario(Medico medico) {
        super(medico.getUsuario(), medico.getSenha(), Collections.emptyList());
        this.medico = medico;
    }

}
