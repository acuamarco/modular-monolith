package com.example

import org.springframework.stereotype.Service

@Service
class CustomerService {

    Customer getCustomerById(Long customerId) {
        return Customer.get(customerId)
    }

    List<Customer> getCustomersByIds(List<Long> customerIds) {
        if (!customerIds) {
            return []
        }
        return Customer.findAllByIdInList(customerIds)
    }
}