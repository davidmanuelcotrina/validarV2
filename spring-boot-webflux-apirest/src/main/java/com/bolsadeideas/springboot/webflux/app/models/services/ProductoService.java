package com.bolsadeideas.springboot.webflux.app.models.services;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Mono;

public interface ProductoService {

	public Mono<Producto> save(Producto producto);
	public Mono<Producto> findById(String id);
	
}
