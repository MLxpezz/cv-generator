package com.cv_generator.configuration.security.filters;

import com.cv_generator.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthorizationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //obtengo el token de la peticion
        String tokenFromRequest = request.getHeader("Authorization");

        //valido que contenga el prefijo Bearer y no venga vacio
        if (tokenFromRequest != null && tokenFromRequest.startsWith("Bearer ")) {
            //quito el prefijo Bearer al token para trabajar con el
            String token = tokenFromRequest.substring(7);

            //Valido el token
            if (jwtUtils.isTokenValid(token)) {
                //obtengo el email del token
                String email = jwtUtils.getEmailFromToken(token);

                //creo un objeto de autenticacion para pasarlo al contexto de seguridad y autorizarlo
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
