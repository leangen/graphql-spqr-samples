package io.leangen.spqr.samples.demo.query;

import io.leangen.spqr.samples.demo.dto.Product;
import io.leangen.spqr.samples.demo.dto.ProductInStock;
import io.leangen.spqr.samples.demo.dto.Vendor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by loshmee on 3-10-16.
 */
@Component
public class VendorQuery {
    private Set<Vendor> mockVendorStorage;

    public VendorQuery() {
        this.mockVendorStorage = new LinkedHashSet<>();
    }

    public Set<ProductInStock> getProductsInStock(Long vendorId){
        Set<ProductInStock> mockResult = new HashSet<>();
        Product product1 = new Product(0L,"MockProduct1", "Product 1 description");
        mockResult.add(new ProductInStock(product1, 10L));
        Product product2 = new Product(1L,"MockProduct2", "Product 2 description");
        mockResult.add(new ProductInStock(product2, 20L));

        return mockResult;
    }

    public Vendor createVendor(Vendor vendor){
        Vendor createdVendor =  new Vendor((long) mockVendorStorage.size(),
                                            vendor.getName(),
                                            vendor.getAddress());
        mockVendorStorage.add(createdVendor);
        return createdVendor;
    }

    public Vendor getVendor(Long id){
        final Optional<Vendor> searchResult = this.mockVendorStorage.stream()
                .filter(vendor -> vendor.getId().equals(id))
                .findFirst();
        return searchResult.orElseThrow(()->new RuntimeException("Vendor not found"));
    }

    public Set<Vendor> getVendor(String name){
        return this.mockVendorStorage.stream()
                .filter(vendor -> vendor.getName().equals(name))
                .collect(Collectors.toSet());
    }
}
