package com.example

class OrderController {
    static responseFormats = ['json', 'xml']

    OrderService orderService

    def show(Long orderId) {
        def result = orderService.getOrderDetails(orderId)
        respond result
    }

    def report() {
        def result = orderService.getOrderReport([1L, 2L, 3L, 4L, 5L])
        respond result
    }
}