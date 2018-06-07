package br.harlan.authjwt.exceptions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Exception padrão que será responsavel por retornar qualquer problema na
 * autenticação.
 * 
 * @author harlan.cleiton
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final static String MESSAGE = "Acesso negado. Você deve está autenticado no sistema para acessar a URL solicitada.";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MESSAGE);
	}

}
