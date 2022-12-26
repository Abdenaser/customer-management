package org.sid.inventoryservicequery.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.sid.dtos.CustomerResponseDTO;
import org.sid.dtos.ProductResponseDTO;
import org.sid.query.GetAllCustomerQuery;
import org.sid.query.GetAllProductQuery;
import org.sid.query.GetOneCustomerQuery;
import org.sid.query.GetOneProductQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query")
@Slf4j
@AllArgsConstructor
public class ProductQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/products")
    public CompletableFuture<List<ProductResponseDTO>> getListRadars(){
        return queryGateway.query(new GetAllProductQuery(), ResponseTypes.multipleInstancesOf(ProductResponseDTO.class));
    }

    @GetMapping("/product/{idProduct}")
    public CompletableFuture<ProductResponseDTO> getRadar(@PathVariable String idProduct){
        return queryGateway.query(new GetOneProductQuery(idProduct), ResponseTypes.instanceOf(ProductResponseDTO.class));
    }
}
