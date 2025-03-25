package com.example

class Customer {
    String name
    String email
    Date dateJoined
    Address billingAddress                  // tightly coupled
    Address shippingAddress                 // tightly coupled
    CustomerPreferences preferences
    List<Order> orders                      // bidirectional dependency, Order: Bidirectional relation leads to deep object graphs

    static hasMany = [orders: Order]

    static constraints = {
        name blank: false
        email email: true, unique: true
        dateJoined nullable: false
        preferences nullable: true
        shippingAddress nullabe: true
        billingAddress nullable: true
    }

    static mapping = {
        table 'customers'
        id column: 'customer_id', generator: 'identity'
        orders cascade: 'all-delete-orphan'     // hasMany + cascade: Tricky during deletes, makes logic stateful
        preferences fetch: 'join'               // fetch: 'join': Can cause performance issues and large joins
    }

    def isVIP() {
        return preferences?.status == 'VIP' && orders?.size() > 10 // Violates SRP (mixes entity + domain service logic), Depends on other domain objects (preferences, orders)
    }
}
