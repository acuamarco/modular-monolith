package com.example

class UrlMappings {
    static mappings = {
        "/order/$orderId"(controller: "order", action: "show", method: 'GET')
        "/order/report"(controller: "order", action: "report", method: 'GET')
    }
}
