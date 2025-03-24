package com.example

import org.springframework.stereotype.Service

@Service
class OrderService {

    static Map<String, String> getOrderDetails(Long orderId) {
        Order order = Order.get(orderId)

        if (!order) {
            throw new RuntimeException("Order not found")
        }

//        // Cross-domain access: directly querying Customer domain
//        // Cross-domain access: directly querying Address domain
        return [
                orderId         : order.id as String,
                orderDate       : order.date as String,
                status          : order.status,
                customerName    : order.customer.name,
                customerEmail   : order.customer.email,
                shippingCity    : order.shippingInfo.destination.city,
                shippingZipCode : order.shippingInfo.destination.postalCode
        ]
    }
}
