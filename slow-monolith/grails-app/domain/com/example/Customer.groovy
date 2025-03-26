package com.example

class Customer {
    String name
    String email
    Date dateJoined

    // 🔥 Tight coupling to another domain object
    Address billingAddress

    // 🔥 Same here — likely should be value objects or owned types
    Address shippingAddress

    // 🔥 Bidirectional relationship (bad in modular monolith)
    List<Order> orders

    // 💬 Pitfall: This creates a bidirectional dependency between Customer and Order, which:
    // Builds tight coupling across domain modules.
    // Creates deep object graphs.
    // Makes serialization, lazy loading, and memory usage harder to reason about.
    static hasMany = [orders: Order]

    static constraints = {
        name blank: false
        email email: true, unique: true
        dateJoined nullable: false
        shippingAddress nullable: true
        billingAddress nullable: true
    }

    static mapping = {
        table 'customers'
        id column: 'customer_id', generator: 'identity'
        // 🔥 Risky — cascading deletes across modules can cause unintended data loss
        orders cascade: 'all-delete-orphan'
    }

    def isVIP() {
        // 🔥 Violates SRP: mixes entity + domain service logic, and deep dependency on other domain concepts
        return orders?.size() > 10
    }
}
