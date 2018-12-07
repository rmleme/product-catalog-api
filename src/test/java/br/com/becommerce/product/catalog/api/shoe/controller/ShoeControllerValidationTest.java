package br.com.becommerce.product.catalog.api.shoe.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.becommerce.product.catalog.api.shoe.model.Color;
import br.com.becommerce.product.catalog.api.shoe.model.Shoe;
import br.com.becommerce.product.catalog.api.shoe.service.ShoeService;

@RunWith(SpringRunner.class)
@WebMvcTest(ShoeController.class)
public class ShoeControllerValidationTest {

	private static final int SIZE = 40;

	private static final String BRAND_NAME = "brandName";

	private static final double PRICE = 1;

	private static final Color COLOR = Color.BLACK;

	private static final String URI = "/v1/api/shoes";

	private Shoe shoe;

	private MockHttpServletRequestBuilder builder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShoeService mockShoeService;

	@Before
	public void setUp() throws Exception {
		shoe = new Shoe();
		shoe.setSize(SIZE);
		shoe.setBrandName(BRAND_NAME);
		shoe.setPrice(PRICE);
		shoe.setColor(COLOR);

		builder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void createOk() throws Exception {
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isCreated());
	}

	@Test
	public void createSizeNull() throws Exception {
		String serializedShoe = "{ \"brand_name\": \"Democrata\", \"price\": 199.9, \"color\": \"BLACK\" }";
		mockMvc.perform(builder.content(serializedShoe)).andExpect(status().isBadRequest());
	}

	@Test
	public void createSizeLessThanMinimal() throws Exception {
		shoe.setSize(9);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createSizeHigherThanMaximum() throws Exception {
		shoe.setSize(51);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createBrandNameNull() throws Exception {
		shoe.setBrandName(null);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createBrandNameBlank() throws Exception {
		shoe.setBrandName("");
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createBrandNameWhitespacesOnly() throws Exception {
		shoe.setBrandName(" ");
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createBrandNameHigherThanMaximum() throws Exception {
		shoe.setBrandName(StringUtils.repeat('a', 33));
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createPriceNull() throws Exception {
		String serializedShoe = "{ \"brand_name\": \"Democrata\", \"color\": \"BLACK\" }";
		mockMvc.perform(builder.content(serializedShoe)).andExpect(status().isBadRequest());
	}

	@Test
	public void createPriceNegative() throws Exception {
		shoe.setPrice(-1);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createColorNull() throws Exception {
		shoe.setColor(null);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(shoe))).andExpect(status().isBadRequest());
	}

	@Test
	public void createColorInvalid() throws Exception {
		String serializedShoe = "{ \"brand_name\": \"Democrata\", \"price\": 199.9, \"color\": \"GREEN\" }";
		mockMvc.perform(builder.content(serializedShoe)).andExpect(status().isBadRequest());
	}
}