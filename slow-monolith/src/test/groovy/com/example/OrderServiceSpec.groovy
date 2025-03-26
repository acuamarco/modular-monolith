package com.example

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class OrderServiceSpec extends Specification implements ServiceUnitTest<OrderService> {

    def setup() {
        // 🔥 Pitfall: OrderService directly depends on CustomerService from another domain
        service.customerService = Mock(CustomerService)
    }

    void "test getOrder returns order info"() {
        given:
        def customer = new Customer(name: "John", email: "john@example.com")
        customer.id = 1L
        def order = new Order(
                customer: customer,
                date: new Date(),
                status: "SHIPPED",
                shippingInfo: new ShippingInfo(
                        destination: new Address(city: "San Jose", postalCode: "12345")
                )
        )
        order.id = 101L
        // 🔥 Pitfall: Static method mocking required due to lack of repository abstraction
        // Harder to test in isolation and violates SRP (data access logic is embedded)
        GroovyMock(Order, global: true)
        Order.get(101L) >> order

        when:
        Order result = service.getOrder(101L)

        then:
        result.id == 101
        result.customer.name == "John"
    }

    void "test getOrderReport returns report for multiple orders"() {
        given:
        def customer1 = new Customer(name: "Alice", email: "alice@example.com")
        customer1.id = 1L
        def customer2 = new Customer(name: "Bob", email: "bob@example.com")
        customer2.id = 2L

        def order1 = new Order(
                customer: customer1,
                date: new Date(),
                status: "PENDING",
                shippingInfo: new ShippingInfo(destination: new Address(city: "CR", postalCode: "111"))
        )
        order1.id = 201L
        def order2 = new Order(
                customer: customer2,
                date: new Date(),
                status: "DELIVERED",
                shippingInfo: new ShippingInfo(destination: new Address(city: "NY", postalCode: "222"))
        )
        order2.id = 202L

        // 🔥 Pitfall: Static method mocking required due to lack of repository abstraction
        // Harder to test in isolation and violates SRP (data access logic is embedded)
        GroovyMock(Order, global: true)
        Order.findAllByIdInList([201L, 202L]) >> [order1, order2]

        // 🔥 Cross-domain contract dependency — test must know CustomerService structure and behavior
        service.customerService.getCustomersByIds([1L, 2L]) >> [customer1, customer2]

        when:
        def report = service.getOrderReport([201L, 202L])

        then:
        report.size() == 2
        report.find { it.customerName == "Alice" }.shippingZipCode == "111"
        report.find { it.customerName == "Bob" }.status == "DELIVERED"
    }
}
