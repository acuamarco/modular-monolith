package com.example

import grails.gorm.transactions.Transactional

@Transactional
class TestDataService {

    static final List<String> FIRST_NAMES = ['John', 'Jane', 'Alice', 'Bob', 'Eve', 'Charlie', 'Dave', 'Grace']
    static final List<String> LAST_NAMES = ['Doe', 'Smith', 'Brown', 'Johnson', 'Taylor', 'Anderson']
    static final List<String> STREETS = ['Main St', 'Elm St', 'Maple Ave', 'Oak Dr', 'Cedar Rd']
    static final List<String> CITIES = ['Springfield', 'Shelbyville', 'Centerville']
    static final List<String> STATES = ['IL', 'CA', 'TX', 'NY']
    static final List<String> COUNTRIES = ['US', 'CA']
    static final List<String> LANGUAGES = ['EN', 'ES', 'FR']
    static final List<String> STATUSES = ['VIP', 'REGULAR', 'BLOCKED']
    static final List<String> ORDER_STATUSES = ['PENDING', 'SHIPPED', 'DELIVERED']
    static final List<String> SHIPPING_METHODS = ['UPS', 'FedEx', 'USPS']

    Random random = new Random()

    def seed() {
        (1..100).each { i ->
            def billingAddress = createRandomAddress()
            def shippingAddress = createRandomAddress()

            def customer = new Customer(
                    name: "${randomName()}",
                    email: "user${i}@example.com",
                    dateJoined: new Date(),
                    billingAddress: billingAddress,
                    shippingAddress: shippingAddress
            ).save(flush: true, failOnError: true)

            new CustomerPreferences(
                    customer: customer,
                    language: randomPick(LANGUAGES),
                    receivesPromotions: random.nextBoolean(),
                    status: randomPick(STATUSES)
            ).save(flush: true, failOnError: true)

            def order = new Order(
                    customer: customer,
                    date: new Date(),
                    totalAmount: random.nextInt(500) + 50,
                    status: randomPick(ORDER_STATUSES)
            ).save(flush: true, failOnError: true)

            def shippingInfo = new ShippingInfo(
                    order: order,
                    shippingMethod: randomPick(SHIPPING_METHODS),
                    trackingNumber: UUID.randomUUID().toString().take(10),
                    status: order.status,
                    shippedAt: order.status in ['SHIPPED', 'DELIVERED'] ? new Date() : null,
                    deliveredAt: order.status == 'DELIVERED' ? new Date() : null,
                    destination: shippingAddress
            ).save(flush: true, failOnError: true)

            order.shippingInfo = shippingInfo
            order.save(flush: true, failOnError: true)
        }
    }

    private Address createRandomAddress() {
        new Address(
                address1: "${random.nextInt(999)} ${randomPick(STREETS)}",
                address2: "Apt ${random.nextInt(999)}",
                city: randomPick(CITIES),
                state: randomPick(STATES),
                postalCode: "${random.nextInt(89999) + 10000}",
                country: randomPick(COUNTRIES)
        ).save(flush: true, failOnError: true)
    }

    private String randomName() {
        "${randomPick(FIRST_NAMES)} ${randomPick(LAST_NAMES)}"
    }

    private <T> T randomPick(List<T> items) {
        items[random.nextInt(items.size())]
    }
}
