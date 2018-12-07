package br.com.becommerce.product.catalog.api.shoe.model;

import org.junit.Assert;

import br.com.becommerce.product.catalog.api.shoe.model.Shoe;

public class ShoeTest {

	public static void assertEquals(Shoe expected, Shoe actual) {
		if (expected == null) {
			Assert.assertNull(actual);
			return;
		}
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getSize(), actual.getSize());
		Assert.assertEquals(expected.getBrandName(), actual.getBrandName());
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0);
		Assert.assertEquals(expected.getColor(), actual.getColor());
	}
}