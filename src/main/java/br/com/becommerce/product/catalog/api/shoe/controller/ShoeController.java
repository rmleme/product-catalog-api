package br.com.becommerce.product.catalog.api.shoe.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.becommerce.product.catalog.api.shoe.model.Shoe;
import br.com.becommerce.product.catalog.api.shoe.service.ShoeService;

@RestController
@RequestMapping("/v1/api/catalog/shoes")
public class ShoeController {

	@Autowired
	private ShoeService shoeService;

	@GetMapping
	public ResponseEntity<List<Shoe>> retrieveAll() {
		List<Shoe> shoes = shoeService.retrieveAll();
		if (shoes == null || shoes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(shoes);
	}

	@GetMapping("{id}")
	public ResponseEntity<Shoe> retrieve(@PathVariable long id) {
		Shoe shoe = shoeService.retrieve(id);
		if (shoe == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(shoe);
	}

	@PostMapping
	public ResponseEntity<Shoe> create(@Valid @RequestBody Shoe shoe, UriComponentsBuilder ucBuilder) {
		shoeService.create(shoe);
		return ResponseEntity.created(ucBuilder.path("/v1/api/catalog/shoes/{id}").buildAndExpand(shoe.getId()).toUri())
				.build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Shoe> update(@Valid @PathVariable long id, @RequestBody Shoe shoe) {
		Shoe currentShoe = shoeService.retrieve(id);
		if (currentShoe == null) {
			return ResponseEntity.notFound().build();
		}
		currentShoe.setSize(shoe.getSize());
		currentShoe.setBrandName(shoe.getBrandName());
		currentShoe.setPrice(shoe.getPrice());
		currentShoe.setColor(shoe.getColor());
		currentShoe = shoeService.update(currentShoe);
		return ResponseEntity.ok(currentShoe);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Shoe> delete(@PathVariable long id) {
		boolean deleted = shoeService.delete(id);
		if (!deleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}