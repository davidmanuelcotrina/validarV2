package com.bolsadeideas.springboot.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.documents.Categoria;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import com.bolsadeideas.springboot.webflux.app.models.services.CategoriaService;
import com.bolsadeideas.springboot.webflux.app.models.services.ProductoService;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApirestApplication implements CommandLineRunner{

	@Autowired
	private ProductoService dao;
	
	@Autowired
	private CategoriaService daocat;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApirestApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApirestApplication.class, args);
		log.debug(args.toString());
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("categorias").subscribe();
		
		Categoria electronico = new Categoria("Electronico");
		Categoria deporte = new Categoria("Deporte");
		Categoria computacion = new Categoria("Computacion");
		Categoria muebles = new Categoria("Muebles");
		
		Flux.just(electronico, deporte, computacion, muebles)
			.flatMap(cat -> {
				return daocat.save(cat);
			})
			.thenMany(
					Flux.just( new Producto("TV Panasonic pantalla LCD", 456.12, electronico),
							new Producto("Sony camara HGD digital", 177.05, electronico),
							new Producto("Apple ipod", 46.33, electronico),
							new Producto("Sony notebook", 846.59, computacion),
							new Producto("Hewlett packard Multifuncional", 200.89, computacion),
							new Producto("Bianchi bicicleta", 70.89, deporte),
							new Producto("HP hotebook Omen 17", 2500.89, computacion),
							new Producto("Mica comoda 5 cajones", 150.89, muebles),
							new Producto("TV sony bravia OLED 4k Ultra HD", 2255.89, electronico)
						)
					.flatMap(producto -> {
						//producto.setCreateAt(new Date());
						return dao.save(producto);
					})
			)
			.subscribe( producto -> log.info("Insert: " + producto.getId() +" "+ producto.getNombre() ));
	}
	
   
}
