package io.leangen.spqr.samples.demo.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loshmee on 3-10-16.
 */
public class Vendor {
    private Long id;
    private String name;
    private Address address;
    private Set<ProductInStock> productsInStock;

    public Vendor(){
    }

    public Vendor(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        productsInStock = new HashSet<ProductInStock>();
    }

    public Vendor(String name, Address address) {
        this.id = null;
        this.name = name;
        this.address = address;
        productsInStock = new HashSet<ProductInStock>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProductsInStock(Set<ProductInStock> productsInStock) {
        this.productsInStock = productsInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendor vendor = (Vendor) o;

        return id != null ? id.equals(vendor.id) : vendor.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Set<ProductInStock> getProductsInStock() {
        return productsInStock;
    }
}
