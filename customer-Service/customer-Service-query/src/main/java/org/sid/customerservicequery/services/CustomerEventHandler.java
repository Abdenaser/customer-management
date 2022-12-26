package org.sid.customerservicequery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.sid.customerservicequery.entities.Customer;
import org.sid.customerservicequery.repositories.CustomerRepository;
import org.sid.events.CreatedCustomerEvent;
import org.sid.events.DeletedCustomerEvent;
import org.sid.events.UpdatedCustomerEvent;
import org.sid.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CustomerEventHandler {
    private CustomerRepository customerRepository;

    @EventHandler
    public void on(CreatedCustomerEvent event){
        Customer customer = new Customer();
        customer.setId(event.getId());
        customer.setName(event.getPayload().getCustomerName());
        customer.setCity(event.getPayload().getCity());
        customer.setEmail(event.getPayload().getCustomerEmail());
        customer.setStreet(event.getPayload().getStreet());
        customer.setPhoneNumber(event.getPayload().getCustomerNumber());
        customerRepository.save(customer);
    }

    @EventHandler
    public void on(UpdatedCustomerEvent event) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(event.getId()).orElseThrow(()->new CustomerNotFoundException(String.format("customer %s not found", event.getId())));
        customer.setName(event.getPayload().getCustomerName());
        customer.setCity(event.getPayload().getCity());
        customer.setEmail(event.getPayload().getCustomerEmail());
        customer.setStreet(event.getPayload().getStreet());
        customer.setPhoneNumber(event.getPayload().getCustomerNumber());

        customerRepository.save(customer);
    }

    @EventHandler
    public void on(DeletedCustomerEvent event) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(event.getId()).orElseThrow(()->new CustomerNotFoundException(String.format("customer %s not found", event.getId())));
        customerRepository.delete(customer);
    }
}
