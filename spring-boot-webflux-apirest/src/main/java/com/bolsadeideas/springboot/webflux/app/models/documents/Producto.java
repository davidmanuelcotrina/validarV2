package com.bolsadeideas.springboot.webflux.app.models.documents;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection="productos")
@Data
public class Producto{

	@Id
	private String id;
		
	@NotEmpty
	@Size(min = 1, max = 50)
	private String nombre;
	
	@NotNull
	@DecimalMin("0.1")
	private Double precio;
	
	@Valid
	private int validarEntero;
	
	@Email
	private String email;

	private String foto;
	
	@Valid
	@NotNull
	private Categoria categoria;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message="debe ser menor a la fecha actual") 
	private Date createAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private Date fechaVenta;
	
	@NotBlank
	private String comentario;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{2,12}$",
            message = "debe ser de 2 a 12 tamaño win caracteres especiales")
    private String comentarioCaracteresEspeciales;
		
	public Producto() {		
	}
	
	public Producto(String nombre, Double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}
	
	public Producto(String nombre, Double precio, Categoria categoria) {
		this(nombre, precio);
		this.categoria=categoria;
	}
	
	
	
	
}
