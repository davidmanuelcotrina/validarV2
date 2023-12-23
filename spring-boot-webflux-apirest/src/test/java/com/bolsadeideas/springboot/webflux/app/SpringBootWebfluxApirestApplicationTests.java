package com.bolsadeideas.springboot.webflux.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebfluxApirestApplicationTests {

	@Autowired
	WebTestClient client;
	
	@Test
	public void listarTest() {
		client.get()
			.uri("/api/v2/productos")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange()
			.expectStatus()
			.isOk()
			.expectHeader()
			.contentType(MediaType.TEXT_EVENT_STREAM)
			.expectBodyList(Producto.class)
			.hasSize(92)
			;
	}

}
