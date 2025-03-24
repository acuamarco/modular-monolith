package com.example


class Order {
    Customer customer                        // direct domain dependency
    Date date
    BigDecimal totalAmount
    String status
    ShippingInfo shippingInfo               // another domain dependency


    static constraints = {
        customer nullable: false
        date nullable: false
        totalAmount nullable: false, min: 0.0
        status nullable: false, inList: ['PENDING', 'SHIPPED', 'DELIVERED']
        shippingInfo nullable: true
    }

    static mapping = {
        table 'orders'
        id column: 'order_id', generator: 'identity'
        customer fetch: 'join'              // eager fetch = tight coupling
        shippingInfo fetch: 'join'
    }
}