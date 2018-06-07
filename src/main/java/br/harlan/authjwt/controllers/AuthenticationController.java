package br.harlan.authjwt.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.harlan.authjwt.dtos.JwtAuthenticationDto;
import br.harlan.authjwt.dtos.TokenDto;
import br.harlan.authjwt.response.Response;
import br.harlan.authjwt.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDto>> generateTokenJwt(
			@Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result)
			throws AuthenticationException {
		Response<TokenDto> response = new Response<TokenDto>();

		if (result.hasErrors()) {
			LOGGER.error("Erro ao validar autenticacao: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		LOGGER.info("Gerando token para email: {}", authenticationDto.getEmail());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.getToken(userDetails);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	/**
	 * Gera um novo token com uma nova data de expiração
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<Response<TokenDto>> generateRefreshTokenJwt(HttpServletRequest request) {
		LOGGER.info("Gerando refresh de token.");
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(BEARER_PREFIX.length()));
			LOGGER.info("{} - Token: {}", BEARER_PREFIX.length(), token.get());
		} else if (!token.isPresent())
			response.getErrors().add("Token não informado");
		else if (!jwtTokenUtil.validateToken(token.get()))
			response.getErrors().add("Token inválido ou expirado.");
		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}
		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenDto(refreshedToken));
		return ResponseEntity.ok(response);
	}
}
