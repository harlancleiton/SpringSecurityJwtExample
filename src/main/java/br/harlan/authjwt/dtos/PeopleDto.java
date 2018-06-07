package br.harlan.authjwt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class PeopleDto {

	private String id;

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 5, max = 255, message = "O nome deve conter no mínimo 5 caracteres, e no máximo 255.")
	private String name;

	@Email(message = "Email invalido")
	@NotEmpty(message = "Email não pode ser vazio.")
	private String email;

	@NotEmpty(message = "Senha não pode ser vazia.")
	private String password;

	public PeopleDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "PeopleDto [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	
}
