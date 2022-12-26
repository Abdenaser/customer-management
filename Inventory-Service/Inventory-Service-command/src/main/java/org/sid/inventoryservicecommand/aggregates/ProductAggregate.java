package org.sid.inventoryservicecommand.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.command.CreateProductCommand;
import org.sid.command.DeleteProductCommand;
import org.sid.command.UpdateProductCommand;
import org.sid.enums.ProductState;
import org.sid.events.CreatedProductEvent;
import org.sid.events.DeletedProductEvent;
import org.sid.events.UpdatedProductEvent;
import org.sid.exceptions.NegativePriceException;
import org.sid.exceptions.NegativeQuantityException;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {
    @AggregateIdentifier
    private String idProduct;
    private String productName;
    private float price;
    private int productQTE;

    private ProductState productState;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws NegativePriceException, NegativeQuantityException {
        if(createProductCommand.getProductRequestDTO().getProductQTE()<0){
            throw new NegativeQuantityException("negative quantity of product");
        }
        if(createProductCommand.getProductRequestDTO().getPrice()<0){
            throw new NegativePriceException("negative price of product");
        }
        AggregateLifecycle.apply(new CreatedProductEvent(createProductCommand.getId(),createProductCommand.getProductRequestDTO()));
    }

    @CommandHandler
    public void handler(UpdateProductCommand updateProductCommand) throws NegativeQuantityException, NegativePriceException {
        if(updateProductCommand.getProductRequestDTO().getProductQTE()<0){
            throw new NegativeQuantityException("negative quantity of product");
        }
        if(updateProductCommand.getProductRequestDTO().getPrice()<0){
            throw new NegativePriceException("negative price of product");
        }
        AggregateLifecycle.apply(new UpdatedProductEvent(updateProductCommand.getId(),updateProductCommand.getProductRequestDTO()));
    }

    @CommandHandler
    public void handel(DeleteProductCommand deleteCustomerCommand){
        //business logic
        AggregateLifecycle.apply(new DeletedProductEvent(deleteCustomerCommand.getId()));
    }

    @EventSourcingHandler
    public void on(CreatedProductEvent event){
        this.idProduct=event.getId();
        this.productName=event.getPayload().getProductName();
        this.price=event.getPayload().getPrice();
        this.productQTE=event.getPayload().getProductQTE();
        this.productState=event.getPayload().getProductState();
    }

    @EventSourcingHandler
    public void on(UpdatedProductEvent event){
        this.productName=event.getPayload().getProductName();
        this.price=event.getPayload().getPrice();
        this.productQTE=event.getPayload().getProductQTE();
        this.productState=event.getPayload().getProductState();
    }

    @EventSourcingHandler
    public void on(DeletedProductEvent event){
        AggregateLifecycle.markDeleted();
    }
}
