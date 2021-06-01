package com.practice.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
public class Address {
    private String street;
    private String detailAddress;
    private String zipcode;

    protected Address(){}

    public Address(String street, String detailAddress, String zipcode) {
        this.street = street;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}
