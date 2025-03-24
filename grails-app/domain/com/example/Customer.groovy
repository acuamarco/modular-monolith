package com.example
/*
Tight Coupling Indicators
CustomerPreferences: Cross-domain reference; prevents clean separation
Order: Bidirectional relation leads to deep object graphs
hasMany + cascade: Tricky during deletes, makes logic stateful
fetch: 'join': Can cause performance issues and large joins
*/
class Customer {
    String name
    String email
    Date dateJoined

    CustomerPreferences preferences         // tightly coupled
    List<Order> orders                      // bidirectional dependency

    static hasMany = [orders: Order]

    static constraints = {
        name blank: false
        email email: true, unique: true
        dateJoined nullable: false
        preferences nullable: true
    }

    static mapping = {
        table 'customers'
        id column: 'customer_id', generator: 'identity'
        orders cascade: 'all-delete-orphan'
        preferences fetch: 'join'
    }


    /*
    Violates SRP (mixes entity + domain service logic)
    Depends on other domain objects (preferences, orders)
    * */
    def isVIP() {
        return preferences?.status == 'VIP' && orders?.size() > 10
    }

}