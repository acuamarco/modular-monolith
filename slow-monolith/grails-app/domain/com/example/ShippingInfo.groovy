package com.example

class ShippingInfo {
    // 🔥 Tight coupling to Order domain — implies shared lifecycle and deep object graph
    Order order

    String shippingMethod
    String trackingNumber
    String status
    Date shippedAt
    Date deliveredAt
    // 🔥 Coupling to another shared entity (likely reused) — Address is used across multiple domains
    Address destination

    static constraints = {
        // 🔥 Implies 1:1 hard binding with Order — very inflexible
        order nullable: false, unique: true
        shippingMethod blank: false
        trackingNumber nullable: true
        status inList: ['PENDING', 'SHIPPED', 'DELIVERED'], blank: false
        shippedAt nullable: true
        deliveredAt nullable: true
        destination nullable: false
    }

    static mapping = {
        table 'shipping_info'
        id column: 'shipping_info_id', generator: 'identity'
        // 🔥 Hidden join: eager load creates large object graph when fetching Order
        order fetch: 'join'
    }

    def isDelivered() {
        // 🔥 Domain logic embedded in entity — small now, but this can sprawl
        return status == 'DELIVERED' && deliveredAt != null
    }
}