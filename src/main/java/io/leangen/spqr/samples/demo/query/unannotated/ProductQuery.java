package io.leangen.spqr.samples.demo.query.unannotated;

import io.leangen.spqr.samples.demo.dto.Product;
import io.leangen.spqr.samples.demo.dto.ProductInStock;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loshmee on 3-10-16.
 */
@Component
public class ProductQuery {

    /**
     * Fetching a mock list of a vendor's products in stock
     *
     * Invoke with:
     * {getProductsInStock(vendorId: 2){product{name, description},stockSize}}
     *
     * @param vendorId
     * @return
     */
    public Set<ProductInStock> getProductsInStock(Long vendorId){
        Set<ProductInStock> mockResult = new HashSet<>();
        Product product1 = new Product(0L,"MockProduct1", "Product 1 description");
        mockResult.add(new ProductInStock(product1, 10L));
        Product product2 = new Product(1L,"MockProduct2", "Product 2 description");
        mockResult.add(new ProductInStock(product2, 20L));

        return mockResult;
    }
}
