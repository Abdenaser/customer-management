package org.sid.customerservicecommand.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.command.CreateCustomerCommand;
import org.sid.command.DeleteCustomerCommand;
import org.sid.command.UpdateCustomerCommand;
import org.sid.events.CreatedCustomerEvent;
import org.sid.events.DeletedCustomerEvent;
import org.sid.events.UpdatedCustomerEvent;

@Aggregate
@NoArgsConstructor
public class CustomerAggregate {
    @AggregateIdentifier
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerNumber;
    private String city;
    private String street;

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand){
        //business logic
        AggregateLifecycle.apply(new CreatedCustomerEvent(createCustomerCommand.getId(),createCustomerCommand.getCustomerRequestDTO()));
    }

    @CommandHandler
    public void handel(UpdateCustomerCommand updateCustomerCommand){
        //business logic
        AggregateLifecycle.apply(new UpdatedCustomerEvent(updateCustomerCommand.getId(),updateCustomerCommand.getCustomerRequestDTO()));
    }

    @CommandHandler
    public void handel(DeleteCustomerCommand deleteCustomerCommand){
        //business logic
        AggregateLifecycle.apply(new DeletedCustomerEvent(deleteCustomerCommand.getId()));
    }

    @EventSourcingHandler
    public void on(CreatedCustomerEvent event){
        this.customerId = event.getId();
        this.customerNumber=event.getPayload().getCustomerNumber();
        this.customerEmail=event.getPayload().getCustomerEmail();
        this.customerName = event.getPayload().getCustomerName();
        this.city= event.getPayload().getCity();
        this.street= event.getPayload().getStreet();
    }

    @EventSourcingHandler
    public void on(UpdatedCustomerEvent event){
        this.customerNumber=event.getPayload().getCustomerNumber();
        this.customerEmail=event.getPayload().getCustomerEmail();
        this.customerName = event.getPayload().getCustomerName();
        this.city= event.getPayload().getCity();
        this.street= event.getPayload().getStreet();
    }

    @EventSourcingHandler
    public void on(DeletedCustomerEvent event){
        AggregateLifecycle.markDeleted();
    }

}
