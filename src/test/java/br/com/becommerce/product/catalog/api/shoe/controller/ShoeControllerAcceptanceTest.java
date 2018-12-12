package br.com.becommerce.product.catalog.api.shoe.controller;

import static br.com.becommerce.product.catalog.api.shoe.model.Constants.BRAND_NAME;
import static br.com.becommerce.product.catalog.api.shoe.model.Constants.COLOR;
import static br.com.becommerce.product.catalog.api.shoe.model.Constants.PRICE;
import static br.com.becommerce.product.catalog.api.shoe.model.Constants.SIZE;
import static br.com.becommerce.product.catalog.api.shoe.model.Constants.URI;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.becommerce.product.catalog.api.Application;
import br.com.becommerce.product.catalog.api.shoe.model.Shoe;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { ShoeControllerAcceptanceTest.Initializer.class })
public class ShoeControllerAcceptanceTest {

	@ClassRule
	public static MySQLContainer mysql = new MySQLContainer();

	private Shoe shoe;

	private String serverUrl;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int serverPort;

	@Before
	public void setUp() throws Exception {
		shoe = new Shoe();
		shoe.setSize(SIZE);
		shoe.setBrandName(BRAND_NAME);
		shoe.setPrice(PRICE);
		shoe.setColor(COLOR);

		serverUrl = "http://localhost:" + serverPort + URI;
	}

	@Test
	public void testCrud() throws Exception {
		// Create
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Shoe> request = new HttpEntity<>(shoe, headers);
		ResponseEntity<String> resultPost = restTemplate.postForEntity(new URI(serverUrl), request, String.class);
		assertEquals(HttpStatus.CREATED.value(), resultPost.getStatusCodeValue());

		// Retrieve
		ResponseEntity<String> resultGet = restTemplate.getForEntity(new URI(serverUrl), String.class);
		List<Shoe> shoes = objectMapper.readValue(resultGet.getBody(), new TypeReference<List<Shoe>>() {
		});
		assertEquals(HttpStatus.OK.value(), resultGet.getStatusCodeValue());
		assertEquals(1, shoes.size());

		// Update
		shoes.get(0).setBrandName("anotherBrandName");
		request = new HttpEntity<>(shoes.get(0), headers);
		ResponseEntity<Shoe> resultPut = restTemplate.exchange(serverUrl + "/{id}", HttpMethod.PUT, request, Shoe.class,
				shoes.get(0).getId());
		assertEquals(HttpStatus.OK.value(), resultPut.getStatusCodeValue());
		assertEquals(shoes.get(0).getBrandName(), resultPut.getBody().getBrandName());

		// Delete
		ResponseEntity<String> resultDelete = restTemplate.exchange(serverUrl + "/{id}", HttpMethod.DELETE, null,
				String.class, shoes.get(0).getId());
		assertEquals(HttpStatus.NO_CONTENT.value(), resultDelete.getStatusCodeValue());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=" + mysql.getJdbcUrl(),
							"spring.datasource.username=" + mysql.getUsername(),
							"spring.datasource.password=" + mysql.getPassword())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}