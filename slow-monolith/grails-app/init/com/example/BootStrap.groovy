package com.example

class BootStrap
{
    TestDataService testDataService
    def init = { servletContext ->
       testDataService.seed()
    }
}
