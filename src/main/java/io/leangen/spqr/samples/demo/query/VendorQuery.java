package io.leangen.spqr.samples.demo.query;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.spqr.samples.demo.dto.Vendor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by loshmee on 4-10-16.
 */
@Component
public class VendorQuery {
    private Set<Vendor> mockVendorStorage;

    public VendorQuery() {
        mockVendorStorage = new LinkedHashSet<>();
    }
    
    /**
     * Save new Vendor.
     *
     * Invoke with:
     *
     * mutation NewVendorMutation{createVendor(vendor:{name: "vendor0", address:{ country: "Netherlands", city: "Amsterdam", streetAndNumber: "Damrak 1", postalCode: "2000XX"}}){id, name, address{streetAndNumber}, productsInStock{product{name}}}}
     *
     * @param vendor
     * @return
     */
    @GraphQLMutation(name = "createVendor")
    public Vendor createVendor(@GraphQLArgument(name = "vendor", required = true) Vendor vendor){
        Vendor createdVendor =  new Vendor((long) mockVendorStorage.size(),
                vendor.getName(),
                vendor.getAddress());
        mockVendorStorage.add(createdVendor);
        return createdVendor;
    }

    @GraphQLQuery(name = "vendorById")
    public Vendor getVendor(@GraphQLArgument(name = "id", required = true) Long id){
        final Optional<Vendor> searchResult = this.mockVendorStorage.stream()
                .filter(vendor -> vendor.getId().equals(id))
                .findFirst();
        return searchResult.orElseThrow(()->new RuntimeException("Vendor not found"));
    }

    @GraphQLQuery(name = "vendorsByName")
    public Set<Vendor> getVendors(@GraphQLArgument(name = "name", required = true) String name){
        return this.mockVendorStorage.stream()
                .filter(vendor -> vendor.getName().equals(name))
                .collect(Collectors.toSet());
    }
}
