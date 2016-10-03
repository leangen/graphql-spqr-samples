package io.leangen.spqr.samples.demo.dto;

/**
 * Created by loshmee on 3-10-16.
 */
public class ProductInStock {
    private Product product;
    private long stockSize;

    public ProductInStock(){
    }

    public ProductInStock(Product product, long stockSize) {
        this.product = product;
        this.stockSize = stockSize;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getStockSize() {
        return stockSize;
    }

    public void setStockSize(long stockSize) {
        this.stockSize = stockSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInStock that = (ProductInStock) o;

        return product != null ? product.equals(that.product) : that.product == null;

    }

    @Override
    public int hashCode() {
        return product != null ? product.hashCode() : 0;
    }
}
