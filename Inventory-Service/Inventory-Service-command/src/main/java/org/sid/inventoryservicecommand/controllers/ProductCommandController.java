package org.sid.inventoryservicecommand.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.sid.command.*;
import org.sid.dtos.CustomerRequestDTO;
import org.sid.dtos.ProductRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/command")
public class ProductCommandController {
    private CommandGateway commandGateway;

    @PostMapping("/product/create")
    public CompletableFuture<String> creatCustomer(@RequestBody ProductRequestDTO productRequestDTO){
        return commandGateway.send(new CreateProductCommand(UUID.randomUUID().toString(),productRequestDTO));
    }

    @PutMapping("/product/update/{idProduct}")
    public CompletableFuture<String> updateCustomer(@PathVariable String idProduct,@RequestBody ProductRequestDTO productRequestDTO){
        return commandGateway.send(new UpdateProductCommand(idProduct,productRequestDTO));
    }

    @DeleteMapping("/product/delete/{idProduct}")
    public CompletableFuture<String> deleteCustomer(@PathVariable String idProduct){
        return commandGateway.send(new DeleteProductCommand(idProduct));
    }
}
