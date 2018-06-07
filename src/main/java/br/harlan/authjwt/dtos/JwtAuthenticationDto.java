package br.harlan.authjwt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {

	@Email(message = "Email invalido")
	@NotEmpty(message = "Email não pode ser vazio.")
	private String email;

	@NotEmpty(message = "Senha não pode ser vazia.")
	private String password;

	public JwtAuthenticationDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
