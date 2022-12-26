package org.sid.inventoryservicequery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.sid.dtos.CustomerResponseDTO;
import org.sid.dtos.ProductResponseDTO;
import org.sid.exceptions.CustomerNotFoundException;
import org.sid.inventoryservicequery.entities.Product;
import org.sid.inventoryservicequery.repositories.ProductRepository;
import org.sid.query.GetAllCustomerQuery;
import org.sid.query.GetAllProductQuery;
import org.sid.query.GetOneCustomerQuery;
import org.sid.query.GetOneProductQuery;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ProductQueryHandler {

    private ProductRepository productRepository;
    @QueryHandler
    public List<ProductResponseDTO> handler(GetAllProductQuery query){
        return productRepository.findAll().stream().map(this::getProductResponseDTO
        ).collect(Collectors.toList());
    }

    @QueryHandler
    public ProductResponseDTO handler(GetOneProductQuery query) throws CustomerNotFoundException {
        Product product = productRepository.findById(query.getIdCustomer()).orElseThrow(()->new CustomerNotFoundException(String.format("customer %s not found", query.getIdCustomer())));
        return getProductResponseDTO(product);
    }

    @NotNull
    private ProductResponseDTO getProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setIdProduct(product.getId());
        productResponseDTO.setProductName(product.getName());
        productResponseDTO.setProductQTE(product.getQTE());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setProductState(product.getProductState());
        return productResponseDTO;
    }
}
