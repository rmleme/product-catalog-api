package br.com.becommerce.product.catalog.api.shoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.becommerce.product.catalog.api.shoe.model.Shoe;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {
}