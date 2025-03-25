package com.example

import org.springframework.stereotype.Service

@Service
class OrderService {
    CustomerService customerService

    static Map<String, String> getOrderDetails(Long orderId) {
        // 🔥 Pitfall: Direct database access – no repository/DAO abstraction
        Order order = Order.get(orderId)
        if (!order) {
            throw new RuntimeException("Order not found")
        }
        return getOrderMap(order, order.customer)
    }

    List<Map<String, String>> getOrderReport(List<Long> orderIds) {
        if (!orderIds) {
            return []
        }

        List<Order> orders = Order.findAllByIdInList(orderIds)
        Set<Long> customerIds = orders.collect { it.customer?.id }.findAll().toSet()

        // 🔥 Pitfall: Tight coupling across domains – service-to-service communication
        List<Customer> customers = customerService.getCustomersByIds(customerIds as List)
        Map<Long, Customer> customerMap = customers.collectEntries { [(it.id): it] }

        return orders.collect { order ->
            Customer customer = customerMap[order.customer?.id as Long]
            return getOrderMap(order, customer)
        }
    }

    static def getOrderMap(Order order, Customer customer) {
        return [
                orderId         : order.id as String,
                orderDate       : order.date as String,
                status          : order.status,
                customerName    : customer.name,
                customerEmail   : customer.email,
                // 🔥 Pitfall: Cross-domain data access – `OrderService` reaches deep into ShippingInfo
                shippingCity    : order.shippingInfo.destination.city,
                shippingZipCode : order.shippingInfo.destination.postalCode
        ]
    }
}
