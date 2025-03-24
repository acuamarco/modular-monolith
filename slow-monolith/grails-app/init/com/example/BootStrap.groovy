package com.example

class BootStrap
{
    TestDataService testDataService

    def init = { servletContext ->
        // This runs at startup
       testDataService.seed()
    }

    def destroy = {
    }
}
