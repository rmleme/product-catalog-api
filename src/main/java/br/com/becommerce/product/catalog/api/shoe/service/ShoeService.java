package br.com.becommerce.product.catalog.api.shoe.service;

import java.util.List;

import br.com.becommerce.product.catalog.api.shoe.model.Shoe;

public interface ShoeService {

	List<Shoe> retrieveAll();

	Shoe retrieve(long id);

	Shoe create(Shoe Shoe);

	Shoe update(Shoe shoe);

	boolean delete(long id);
}