package org.sid.events

import org.sid.dtos.CustomerRequestDTO
import org.sid.dtos.ProductRequestDTO

abstract class BaseEvent<T>(
    open val id : T
);

data class CreatedCustomerEvent(
    override val id : String,
    val payload: CustomerRequestDTO
): BaseEvent<String>(id);

data class UpdatedCustomerEvent(
    override val id : String,
    val payload: CustomerRequestDTO
): BaseEvent<String>(id);

data class DeletedCustomerEvent(
override val id : String,
): BaseEvent<String>(id);

data class CreatedProductEvent(
    override val id : String,
    val payload: ProductRequestDTO
): BaseEvent<String>(id);

data class UpdatedProductEvent(
override val id : String,
val payload: ProductRequestDTO
): BaseEvent<String>(id);

data class DeletedProductEvent(
override val id : String,
): BaseEvent<String>(id);

