package com.example

class OrderController {
    static responseFormats = ['json', 'xml']

    OrderService orderService

    def show(Long orderId) {
        def result = orderService.getOrderDetails(orderId)
        respond result
    }

    def report() {
        def result = orderService.getOrderReport()
        respond result
    }
}