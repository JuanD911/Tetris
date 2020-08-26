package Persistencia;

import java.io.Serializable;

public class Player implements Serializable {

	private String nombre;
	private String password;
	
	
	public Player (String nombre, String password) {
		super();
		this.nombre = nombre;
		this.password = password;
	}

	// Getters y Setters.
	public String getNombre() {
		return nombre;
	}


	public String getPassword() {
		return password;
	}
	
}