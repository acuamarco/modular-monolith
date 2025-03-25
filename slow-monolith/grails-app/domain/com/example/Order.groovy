package com.example

class Order {
    // 🔥 Direct domain dependency on Customer — cross-module reference
    Customer customer
    Date date
    BigDecimal totalAmount
    String status
    // 🔥 Direct domain dependency on ShippingInfo — cross-module reference
    ShippingInfo shippingInfo


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
        // 🔥 Eager fetches increase coupling and load entire object graphs
        customer fetch: 'join'
        shippingInfo fetch: 'join'
    }
}
