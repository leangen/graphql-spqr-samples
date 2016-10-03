package io.leangen.spqr.samples.demo.dto;

/**
 * Created by loshmee on 3-10-16.
 */
public class Address {
    private String country;
    private String city;
    private String streetAndNumber;
    private String postalCode;

    public Address(){
    }

    public Address(String country, String city, String streetAndNumber, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetAndNumber = streetAndNumber;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (streetAndNumber != null ? !streetAndNumber.equals(address.streetAndNumber) : address.streetAndNumber != null)
            return false;
        return postalCode != null ? postalCode.equals(address.postalCode) : address.postalCode == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (streetAndNumber != null ? streetAndNumber.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }
}
