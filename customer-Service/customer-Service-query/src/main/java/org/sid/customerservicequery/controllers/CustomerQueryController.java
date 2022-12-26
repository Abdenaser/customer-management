package org.sid.customerservicequery.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.sid.dtos.CustomerResponseDTO;
import org.sid.query.GetAllCustomerQuery;
import org.sid.query.GetOneCustomerQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/query")
public class CustomerQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/customer")
    public CompletableFuture<List<CustomerResponseDTO>> getListRadars(){
        return queryGateway.query(new GetAllCustomerQuery(), ResponseTypes.multipleInstancesOf(CustomerResponseDTO.class));
    }

    @GetMapping("/customer/{idCustomer}")
    public CompletableFuture<CustomerResponseDTO> getRadar(@PathVariable String idCustomer){
        return queryGateway.query(new GetOneCustomerQuery(idCustomer), ResponseTypes.instanceOf(CustomerResponseDTO.class));
    }
}
