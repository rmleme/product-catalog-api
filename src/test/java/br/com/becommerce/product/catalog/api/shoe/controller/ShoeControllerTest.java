package br.com.becommerce.product.catalog.api.shoe.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.becommerce.product.catalog.api.shoe.model.Color;
import br.com.becommerce.product.catalog.api.shoe.model.Shoe;
import br.com.becommerce.product.catalog.api.shoe.model.ShoeTest;
import br.com.becommerce.product.catalog.api.shoe.service.ShoeService;

@RunWith(MockitoJUnitRunner.class)
public class ShoeControllerTest {

	private static final long ID = 1;

	private static final int SIZE = 40;

	private static final String BRAND_NAME = "brandName";

	private static final double PRICE = 1;

	private static final Color COLOR = Color.BLACK;

	private static final String URI = "/v1/api/shoes";

	private Shoe shoe;

	@Mock
	private ShoeService mockShoeService;

	@InjectMocks
	private ShoeController controller;

	@Before
	public void setUp() throws Exception {
		shoe = new Shoe();
		shoe.setId(ID);
		shoe.setSize(SIZE);
		shoe.setBrandName(BRAND_NAME);
		shoe.setPrice(PRICE);
		shoe.setColor(COLOR);
	}

	@Test
	public void retrieveAllOk() throws Exception {
		List<Shoe> shoes = Arrays.asList(new Shoe[] { shoe });
		when(mockShoeService.retrieveAll()).thenReturn(shoes);

		assertEquals(ResponseEntity.ok(shoes), controller.retrieveAll());

		verify(mockShoeService).retrieveAll();
	}

	@Test
	public void retrieveAllNoContentsNull() throws Exception {
		when(mockShoeService.retrieveAll()).thenReturn(null);

		assertEquals(ResponseEntity.noContent().build(), controller.retrieveAll());

		verify(mockShoeService).retrieveAll();
	}

	@Test
	public void retrieveAllNoContentsEmpty() throws Exception {
		when(mockShoeService.retrieveAll()).thenReturn(new ArrayList<>());

		assertEquals(ResponseEntity.noContent().build(), controller.retrieveAll());

		verify(mockShoeService).retrieveAll();
	}

	@Test
	public void retrieveOk() throws Exception {
		when(mockShoeService.retrieve(ID)).thenReturn(shoe);

		assertEquals(ResponseEntity.ok(shoe), controller.retrieve(ID));

		verify(mockShoeService).retrieve(ID);
	}

	@Test
	public void retrieveNoContents() throws Exception {
		when(mockShoeService.retrieve(ID)).thenReturn(null);

		assertEquals(ResponseEntity.notFound().build(), controller.retrieve(ID));

		verify(mockShoeService).retrieve(ID);
	}

	@Test
	public void createOk() throws Exception {
		when(mockShoeService.create(shoe)).thenReturn(shoe);

		assertEquals(ResponseEntity.created(new URI(URI + "/" + ID)).build(),
				controller.create(shoe, UriComponentsBuilder.newInstance()));

		verify(mockShoeService).create(shoe);
	}

	@Test
	public void updateOk() throws Exception {
		when(mockShoeService.retrieve(ID)).thenReturn(shoe);
		when(mockShoeService.update(shoe)).thenReturn(shoe);

		ResponseEntity<Shoe> result = controller.update(ID, shoe);

		assertEquals(ResponseEntity.ok(shoe), result);
		ShoeTest.assertEquals(shoe, result.getBody());

		verify(mockShoeService).retrieve(ID);
		verify(mockShoeService).update(shoe);
	}

	@Test
	public void updateShoeNotFound() throws Exception {
		when(mockShoeService.retrieve(ID)).thenReturn(null);

		assertEquals(ResponseEntity.notFound().build(), controller.update(ID, shoe));

		verify(mockShoeService).retrieve(ID);
		verify(mockShoeService, never()).update(shoe);
	}

	@Test
	public void deleteOk() throws Exception {
		when(mockShoeService.delete(ID)).thenReturn(true);

		assertEquals(ResponseEntity.noContent().build(), controller.delete(ID));

		verify(mockShoeService).delete(ID);
	}

	@Test
	public void deleteShoeNotFound() throws Exception {
		when(mockShoeService.delete(ID)).thenReturn(false);

		assertEquals(ResponseEntity.notFound().build(), controller.delete(ID));

		verify(mockShoeService).delete(ID);
	}
}