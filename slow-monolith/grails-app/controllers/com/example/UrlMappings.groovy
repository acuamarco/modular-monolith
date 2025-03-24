package com.example

class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/test/data"(controller: "testData", action: "init")
        "/order/$orderId"(controller: "order", action: "show")
    }
}
