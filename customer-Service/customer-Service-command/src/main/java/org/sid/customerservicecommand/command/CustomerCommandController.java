package org.sid.customerservicecommand.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.sid.command.CreateCustomerCommand;
import org.sid.command.DeleteCustomerCommand;
import org.sid.command.UpdateCustomerCommand;
import org.sid.dtos.CustomerRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/command")
@Slf4j
@AllArgsConstructor
public class CustomerCommandController {

    private CommandGateway commandGateway;

    @PostMapping("/customer/create")
    public CompletableFuture<String> creatCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        return commandGateway.send(new CreateCustomerCommand(UUID.randomUUID().toString(),customerRequestDTO));
    }

    @PutMapping("/customer/update/{idCustomer}")
    public CompletableFuture<String> updateCustomer(@PathVariable String idCustomer,@RequestBody CustomerRequestDTO customerRequestDTO){
        return commandGateway.send(new UpdateCustomerCommand(idCustomer,customerRequestDTO));
    }

    @DeleteMapping("/customer/delete/{idCustomer}")
    public CompletableFuture<String> deleteCustomer(@PathVariable String idCustomer){
        return commandGateway.send(new DeleteCustomerCommand(idCustomer));
    }
}
