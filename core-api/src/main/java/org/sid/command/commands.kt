package org.sid.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.sid.dtos.*

abstract class BaseCommand<T>(
    @TargetAggregateIdentifier
    open val id : T
)

data class CreateCustomerCommand(
    override val id : String,
    val customerRequestDTO: CustomerRequestDTO
): BaseCommand<String>(id)


data class UpdateCustomerCommand(
override val id : String,
val customerRequestDTO: CustomerRequestDTO
): BaseCommand<String>(id)

data class DeleteCustomerCommand(
    override val id : String,
): BaseCommand<String>(id)

data class  CreateProductCommand(
override val id : String,
val productRequestDTO: ProductRequestDTO
): BaseCommand<String>(id)

data class  UpdateProductCommand(
    override val id : String,
    val productRequestDTO: ProductRequestDTO
): BaseCommand<String>(id)

data class DeleteProductCommand(
    override val id : String
): BaseCommand<String>(id)



