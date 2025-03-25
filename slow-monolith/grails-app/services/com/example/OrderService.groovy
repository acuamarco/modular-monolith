package com.example

import org.springframework.stereotype.Service

@Service
class OrderService {
    CustomerService customerService

    static Map<String, String> getOrderDetails(Long orderId) {
        // 🔥 Pitfall: Directly accesses the database from within the service layer, which can make the code harder to test and maintain. A better approach would be to use a repository or DAO (Data Access Object) to handle database operations.
        // This would separate the concerns of data access and business logic, making the code more modular and testable.
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

        // 🔥 Pitfall: Cross-domain service call inside domain logic
        List<Customer> customers = customerService.getCustomersByIds(customerIds as List)

        // 🔥 Pitfall: Building a map in memory (vs. optimized query or join)
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
                shippingCity    : order.shippingInfo.destination.city,          // Cross-domain access: directly querying ShippingInfo domain
                shippingZipCode : order.shippingInfo.destination.postalCode
        ]
    }
}
