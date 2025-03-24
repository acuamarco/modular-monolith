package com.example

/*
Customer customer: Creates tight bidirectional dependency with Customer
fetch: 'join': Can cause N+1 problems or heavy joins
isVipEligible(): Makes it a rich domain class, hard to reuse outside GORM
unique: true: Enforces 1:1 relationship at the DB layer, locks design
You can’t reuse CustomerPreferences logic without loading a full Customer
Makes refactoring harder — eg. moving CustomerPreferences to its own module
DTOs or projections become tricky because you can’t use isVipEligible() outside GORM
*/
class CustomerPreferences {
    Customer customer                   // direct domain dependency
    String language
    boolean receivesPromotions
    String status                       // e.g., REGULAR, VIP, BLOCKED

    static constraints = {
        customer nullable: false, unique: true
        language nullable: false, inList: ['EN', 'ES', 'FR']
        receivesPromotions nullable: false
        status nullable: false, inList: ['REGULAR', 'VIP', 'BLOCKED']
    }

    static mapping = {
        table 'customer_preferences'
        id column: 'customer_preference_id', generator: 'identity'
        customer fetch: 'join'
    }

    def isVipEligible() {
        return status == 'VIP' && receivesPromotions
    }
}