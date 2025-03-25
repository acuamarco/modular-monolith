package com.example
class CustomerPreferences {
    Customer customer                   // direct domain dependency, creates tight bidirectional dependency with Customer
    String language
    boolean receivesPromotions
    String status

    static constraints = {
        customer nullable: false, unique: true
        language nullable: false, inList: ['EN', 'ES', 'FR']
        receivesPromotions nullable: false
        status nullable: false, inList: ['REGULAR', 'VIP', 'BLOCKED']
    }

    static mapping = {
        table 'customer_preferences'
        id column: 'customer_preference_id', generator: 'identity'
        customer fetch: 'join'              // fetch: 'join' can cause N+1 problems or heavy joins
    }

    def isVipEligible() {
        return status == 'VIP' && receivesPromotions
    }
}
