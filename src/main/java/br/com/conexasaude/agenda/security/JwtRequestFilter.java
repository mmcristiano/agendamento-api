package br.com.conexasaude.agenda.security;


import br.com.conexasaude.agenda.config.Constantes;
import io.jsonwebtoken.ExpiredJwtException;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {



        String requestTokenHeader = request.getHeader("Authorization");

        String usuario = null;
        String token = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(Constantes.PREFIX_AUTH)) {
            token = requestTokenHeader.replace(Constantes.PREFIX_AUTH, "").trim();
            try {
                usuario = jwtUtil.parseToken(token);

                if (null != usuario) {
                    if (jwtUtil.isTokenValido(token, usuario)) {
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                                usuario, null, new ArrayList<>()));
                    }
                }

            } catch (IllegalArgumentException e) {
                throw new ServiceException("Não foi possivel recuperar o token.");
            } catch (ExpiredJwtException e) {
                throw new ServiceException("Token expirado");
            }
        }


        chain.doFilter(request, response);
    }
}
