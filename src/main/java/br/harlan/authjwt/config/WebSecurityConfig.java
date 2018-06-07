package br.harlan.authjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.harlan.authjwt.exceptions.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Utilizado pelo security para retornar a senha. Diz ao Spring Security que o
	 * metodo de verificação da senha é utilizando um BCryptPasswordEncoder
	 * 
	 * @param authenticationManagerBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * Filtro que irá interceptar todas as requisições.
	 * 
	 */
	// @Bean
	// public JwtAut

	/**
	 * Desabilita CRFS, pois por ser Stateless (sem sessão) o CRFS não precisa está
	 * habilitado. Em caso de exceção será chamada a exception padrão
	 * {@link JwtAuthenticationEntryPoint}. Autoriza requisições para antMatchers,
	 * nesse caso /auth/**. Todas as outras precisa de autenticação. Adiciona o
	 * filtro. Habilida o cache control do headers.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/auth/**").permitAll().anyRequest().authenticated();
		// http.addFilterBefore(filter, beforeFilter))
		http.headers().cacheControl();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
