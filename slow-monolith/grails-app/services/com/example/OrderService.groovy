package com.example

import org.springframework.stereotype.Service

@Service
class OrderService {

    static Map<String, String> getOrderDetails(Long orderId) {
        Order order = Order.get(orderId)                                // Directly accesses the database from within the service layer, which can make the code harder to test and maintain. A better approach would be to use a repository or DAO (Data Access Object) to handle database operations. This would separate the concerns of data access and business logic, making the code more modular and testable.
        if (!order) {
            throw new RuntimeException("Order not found")
        }
        return getOrderMap(order)
    }

    static List<Map<String, String>> getOrderReport() {
        def orders = Order.list() as List<Order>
        orders.collect { order -> getOrderMap(order)}
    }

    static def getOrderMap(Order order) {
        return [
                orderId         : order.id as String,
                orderDate       : order.date as String,
                status          : order.status,
                customerName    : order.customer.name,                          // Cross-domain access: directly querying Customer domain
                customerEmail   : order.customer.email,
                shippingCity    : order.shippingInfo.destination.city,          // Cross-domain access: directly querying Address domain
                shippingZipCode : order.shippingInfo.destination.postalCode
        ]
    }
}
