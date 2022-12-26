package org.sid.customerservicequery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.sid.customerservicequery.entities.Customer;
import org.sid.customerservicequery.repositories.CustomerRepository;
import org.sid.dtos.CustomerResponseDTO;
import org.sid.exceptions.CustomerNotFoundException;
import org.sid.query.GetAllCustomerQuery;
import org.sid.query.GetOneCustomerQuery;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CustomerQueryHandler {

    private CustomerRepository customerRepository;
    @QueryHandler
    public List<CustomerResponseDTO> handler(GetAllCustomerQuery query){
        return customerRepository.findAll().stream().map(this::getCustomerResponseDTO
        ).collect(Collectors.toList());
    }

    @QueryHandler
    public CustomerResponseDTO handler(GetOneCustomerQuery query) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(query.getIdCustomer()).orElseThrow(()->new CustomerNotFoundException(String.format("customer %s not found", query.getIdCustomer())));
        return getCustomerResponseDTO(customer);
    }

    @NotNull
    private CustomerResponseDTO getCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setIdCustomer(customer.getId());
        customerResponseDTO.setCustomerName(customer.getName());
        customerResponseDTO.setCustomerEmail(customer.getEmail());
        customerResponseDTO.setCustomerNumber(customer.getPhoneNumber());
        customerResponseDTO.setCity(customer.getCity());
        customerResponseDTO.setStreet(customer.getStreet());
        return customerResponseDTO;
    }

}
