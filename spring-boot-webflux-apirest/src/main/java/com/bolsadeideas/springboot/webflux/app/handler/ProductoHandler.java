package com.bolsadeideas.springboot.webflux.app.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import com.bolsadeideas.springboot.webflux.app.models.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {

	@Value("${config.uploads.path}")
	private String path;
	
	@Autowired
	private ProductoService ser;
	
	@Autowired
	private Validator validator;
	
	public Mono<ServerResponse> editar(ServerRequest request){
		String id = request.pathVariable("id");
		Mono<Producto> productoReq = request.bodyToMono(Producto.class);
		
//		 productoDB.subscribe(pr -> System.out.println("********* ********** " +pr));
		
		return productoReq
				.flatMap(pr -> {
					Errors errors = new BeanPropertyBindingResult(pr, Producto.class.getName());
					validator.validate(pr, errors);
					if( errors.hasErrors() ) {
						return Flux.fromIterable(errors.getFieldErrors())
								.map(fieldError -> "el campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
								.collectList()
								.flatMap(itemList -> ServerResponse.badRequest().bodyValue(itemList) )
								;
					}else {
						Mono<Producto> productoDB = ser.findById(id);
						System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
						return productoDB
								.doOnNext(prrr -> {
									System.out.println("********************    " + prrr);
								})
								.zipWith(productoReq, (proDB, proRq) -> {
									proDB.setNombre(proRq.getNombre());
									proDB.setPrecio(proRq.getPrecio());
									proDB.setValidarEntero(proRq.getValidarEntero());
									proDB.setEmail(proRq.getEmail());
									proDB.setCategoria(proRq.getCategoria());
									proDB.setCreateAt(proRq.getCreateAt());
									proDB.setComentario(proRq.getComentario());
									proDB.setComentarioCaracteresEspeciales(proRq.getComentarioCaracteresEspeciales());
									return proDB;
								})
								.doOnNext(prrr -> {
									System.out.println("********************    " + prrr);
								})
								.flatMap( prr -> ser.save(prr))
								.flatMap( prr -> ServerResponse
													.ok()
													.contentType(MediaType.APPLICATION_NDJSON)
													.body(BodyInserters.fromValue(prr))
								)
								.switchIfEmpty(ServerResponse.notFound().build())
								;
					}
				})
				;

	}
		
	
}
