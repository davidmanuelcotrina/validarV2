package com.bolsadeideas.springboot.webflux.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.webflux.app.models.dao.CategoriaDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Categoria;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaDao dao;
	
	@Override
	public Flux<Categoria> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Mono<Categoria> findById(String id) {
		
		return dao.findById(id);
	}

	@Override
	public Mono<Categoria> save(Categoria categoria) {
		
		return dao.save(categoria);
	}

}
