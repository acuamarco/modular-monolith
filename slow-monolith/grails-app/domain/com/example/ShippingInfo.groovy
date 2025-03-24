package com.example

/*
Order order	    Creates a strong dependency back to the core Order domain
unique: true	Enforces a 1:1 relation that’s hard to change later
fetch: 'join'	Introduces hidden joins, can hurt performance
isDelivered()	Makes ShippingInfo a rich domain class, hard to reuse outside the entity context
No DTO	        You can’t easily serialize just what you need without leaking the entity
*/
class ShippingInfo {
    Order order                            // tight coupling to domain
    String shippingMethod
    String trackingNumber
    String status
    Date shippedAt
    Date deliveredAt
    Address destination

    static constraints = {
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
        order fetch: 'join'
    }

    def isDelivered() {
        return status == 'DELIVERED' && deliveredAt != null
    }
}