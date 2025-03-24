package com.example

class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/order/$orderId"(controller: "order", action: "show", method: 'GET')
        "/order/report/today"(controller: "order", action: "todaysOrdersReport", method: 'GET')
    }
}
