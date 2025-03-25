package com.example

class Address {
    String address1
    String address2
    String city
    String state
    String postalCode
    String country

    static constraints = {
        address1 blank: false
        address2 blank: false
        city blank: false
        state nullable: true
        postalCode blank: false
        country blank: false
    }

    static mapping = {
        table 'addresses'
    }

    String toString() {
        "${address1} ${address2}, ${city}, ${country}"
    }
}