package br.harlan.authjwt.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	private final String CLAIM_KEY_USERNAME = "sub";
	private final String CLAIM_KEY_ROLE = "role";
	private final String CLAIM_KEY_CREATED = "created";
	// 86400000 = 1 dia
	private final Integer EXPIRATION_MULTIPLIER = 86400000;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Obtem o username(email) contido no token JWT.
	 * 
	 * @param token
	 * @return username
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			username = null;
		}
		return username;
	}

	/**
	 * Retorna a data de expiração de um token
	 * 
	 * @param token
	 * @return expiration
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			e.printStackTrace();
			expiration = null;
		}
		return expiration;
	}

	/**
	 * Cria (refresh) um novo token
	 * 
	 * @param token
	 * @return refreshedToken
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			e.printStackTrace();
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Obtem um novo token com base na informação do usuario
	 * 
	 * @param userDetails
	 * @return String
	 */
	public String getToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * Verifica se o token está valido
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean validateToken(String token) {
		return !expiredToken(token);
	}

	/**
	 * Verifica se o token está expirado
	 * 
	 * @param token
	 * @return boolean
	 */
	private boolean expiredToken(String token) {
		Date expiredDate = getExpirationDateFromToken(token);
		if (expiredDate == null) {
			return false;
		}
		return expiredDate.before(new Date());
	}

	/**
	 * Realiza o parse do token JWT para extrair as informações contidas no corpo
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * Gera um novo token JWT contendo os dados (claims) fornecidos
	 * 
	 * @param claims
	 * @return String
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * EXPIRATION_MULTIPLIER);
	}
}
