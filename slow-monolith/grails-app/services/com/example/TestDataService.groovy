package com.example

import grails.gorm.transactions.Transactional

@Transactional
class TestDataService {
    def seed() {
        def address1 = new Address(
                address1: '123 Main St',
                address2: 'Suite 100',
                city: 'Springfield',
                state: 'IL',
                postalCode: '62701',
                country: 'US'
        ).save(flush: true, failOnError: true)

        def customer1 = new Customer(
                name: 'John Doe',
                email: 'some@email.com',
                dateJoined: new Date(),
                billingAddress: address1,
                shippingAddress: address1
        ).save(flush: true, failOnError: true)

        def preferences = new CustomerPreferences(
                customer: customer1,
                language: 'EN',
                receivesPromotions: true,
                status: 'VIP'
        ).save(flush: true, failOnError: true)

        def address2 = new Address(
                address1: '456 Elm St',
                address2: 'Apt 200',
                city: 'Springfield',
                state: 'IL',
                postalCode: '62701',
                country: 'US'
        ).save(flush: true, failOnError: true)

        def customer2 = new Customer(
                name: 'Jane Doe',
                email: 'email2@email.com',
                dateJoined: new Date(),
                billingAddress: address2,
                shippingAddress: address2
        ).save(flush: true, failOnError: true)

        def preferences2 = new CustomerPreferences(
                customer: customer2,
                language: 'EN',
                receivesPromotions: false,
                status: 'REGULAR'
        ).save(flush: true, failOnError: true)


        Order order1 = new Order(
                customer: customer1,
                date: new Date(),
                totalAmount: 100,
                status: 'PENDING'
        ).save(flush: true, failOnError: true)

        ShippingInfo shippingInfo1 = new ShippingInfo(
                order: order1,
                shippingMethod: 'UPS',
                trackingNumber: '123456',
                status: 'PENDING',
                destination: address1
        ).save(flush: true, failOnError: true)
        order1.shippingInfo = shippingInfo1


        Order order2 = new Order(
                customer: customer2,
                date: new Date(),
                totalAmount: 200,
                status: 'SHIPPED'
        ).save(flush: true, failOnError: true)

        ShippingInfo shippingInfo2 = new ShippingInfo(
                order: order2,
                shippingMethod: 'FedEx',
                trackingNumber: '654321',
                status: 'SHIPPED',
                shippedAt: new Date(),
                destination: address2
        ).save(flush: true, failOnError: true)
        order2.shippingInfo = shippingInfo2

        Order order3 = new Order(
                customer: customer1,
                date: new Date(),
                totalAmount: 300,
                status: 'DELIVERED'
        ).save(flush: true, failOnError: true)

        ShippingInfo shippingInfo3 = new ShippingInfo(
                order: order3,
                shippingMethod: 'USPS',
                trackingNumber: '987654',
                status: 'DELIVERED',
                shippedAt: new Date(),
                deliveredAt: new Date(),
                destination: address1
        ).save(flush: true, failOnError: true)
        order3.shippingInfo = shippingInfo3
    }
}
