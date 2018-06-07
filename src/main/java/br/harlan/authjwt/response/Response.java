package br.harlan.authjwt.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Harlan Cleiton
 * @email harlan@itech.net.br
 * 
 * Encapsulates the data to be sent and any errors generated in the process.
 *
 * @param <T>
 */
public class Response <T> {
	private T data;
	
	private List<String> errors;
	
	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "Response [data=" + data + ", errors=" + errors + "]";
	}
}