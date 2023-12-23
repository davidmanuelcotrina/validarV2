package com.bolsadeideas.springboot.webflux.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bolsadeideas.springboot.webflux.app.handler.ProductoHandler;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;


@Configuration
public class RouterFunctionConfig {

    @Bean
    RouterFunction<ServerResponse> routes(ProductoHandler handler){
		return route(PUT("/api/v2/productos/{id}"), handler::editar)
				;
	}
}
