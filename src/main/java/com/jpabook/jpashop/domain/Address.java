package com.jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipocde;

    protected Address() {
    }

    public Address(String city, String street, String zipocde) {
        this.city = city;
        this.street = street;
        this.zipocde = zipocde;
    }
}
