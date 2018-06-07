package br.harlan.authjwt.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	public static String generateBCrypt(String password) {
		if(password==null || password.isEmpty()) {
			return null;
		}
		BCryptPasswordEncoder bPasswordEncoder = new BCryptPasswordEncoder();
		return bPasswordEncoder.encode(password);
	}

	public static boolean ValidatePassword(String password, String passwordEncoded) {
		//BCryptPasswordEncoder bPasswordEncoder = new BCryptPasswordEncoder();
		BCryptPasswordEncoder bPasswordEncoder = new BCryptPasswordEncoder();
		return bPasswordEncoder.matches(password, passwordEncoded);
	}
}
