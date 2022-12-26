package org.sid.inventoryservicequery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.sid.events.*;
import org.sid.exceptions.CustomerNotFoundException;
import org.sid.exceptions.ProductNotFoundException;
import org.sid.inventoryservicequery.entities.Product;
import org.sid.inventoryservicequery.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ProductEventHandler {
    private ProductRepository productRepository;

    @EventHandler
    public void on(CreatedProductEvent event){
        Product product = new Product();
        product.setId(event.getId());
        product.setName(event.getPayload().getProductName());
        product.setQTE(event.getPayload().getProductQTE());
        product.setPrice(event.getPayload().getPrice());
        product.setProductState(event.getPayload().getProductState());
        productRepository.save(product);
    }

    @EventHandler
    public void on(UpdatedProductEvent event) throws  ProductNotFoundException {
        Product product = productRepository.findById(event.getId()).orElseThrow(()->new ProductNotFoundException(String.format("customer %s not found", event.getId())));
        product.setName(event.getPayload().getProductName());
        product.setQTE(event.getPayload().getProductQTE());
        product.setPrice(event.getPayload().getPrice());
        product.setProductState(event.getPayload().getProductState());

        productRepository.save(product);
    }

    @EventHandler
    public void on(DeletedProductEvent event) throws ProductNotFoundException {
        Product product = productRepository.findById(event.getId()).orElseThrow(()->new ProductNotFoundException(String.format("customer %s not found", event.getId())));
        productRepository.delete(product);
    }
}
