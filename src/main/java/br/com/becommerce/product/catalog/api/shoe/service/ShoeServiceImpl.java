package br.com.becommerce.product.catalog.api.shoe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.becommerce.product.catalog.api.shoe.model.Shoe;
import br.com.becommerce.product.catalog.api.shoe.repository.ShoeRepository;

@Service
public class ShoeServiceImpl implements ShoeService {

	@Autowired
	private ShoeRepository shoeRepository;

	@Override
	public List<Shoe> retrieveAll() {
		return shoeRepository.findAll();
	}

	@Override
	public Shoe retrieve(long id) {
		return shoeRepository.findById(id).orElse(null);
	}

	@Override
	public Shoe create(Shoe shoe) {
		return shoeRepository.save(shoe);
	}

	@Override
	public Shoe update(Shoe shoe) {
		return shoeRepository.save(shoe);
	}

	@Override
	public boolean delete(long id) {
		try {
			shoeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
}