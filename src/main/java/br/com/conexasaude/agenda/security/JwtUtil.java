package br.com.conexasaude.agenda.security;


import br.com.conexasaude.agenda.model.Medico;


import br.com.conexasaude.agenda.service.MedicoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    @Value("${jwt.secret.expiration}")
    private Long expirationToken;

    @Autowired
    private MedicoService medicoService;

    public String generateToken(String username) {

        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationToken * 1000));


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)

                .compact();
    }

    public Boolean isTokenValido(String token, String usuario) {
        Medico medico = medicoService.getByUsuario(usuario);

        if (Objects.isNull(medico.getToken())) {
            return false;
        }
        return medico.getToken().equals(token) && !isTokenExpirado(token);
    }

    private Boolean isTokenExpirado(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        Date dataExpiracao = claims.getExpiration();
        return dataExpiracao.before(new Date());
    }

    public String parseToken(String token) {
        try {
            System.out.println(token);

            Claims body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();


            return body.getSubject();

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }



}