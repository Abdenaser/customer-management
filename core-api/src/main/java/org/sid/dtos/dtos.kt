package org.sid.dtos

import org.sid.enums.ProductState
import java.util.*

data class CustomerRequestDTO(
    val customerName:String ="",
    val customerEmail:String ="",
    val customerNumber:String ="",
    val city:String ="",
    val street:String ="",
);

data class CustomerResponseDTO(
    var idCustomer:String="",
    var customerName:String ="",
    var customerEmail:String ="",
    var customerNumber:String ="",
    var city:String ="",
    var street:String ="",
);

data class  ProductRequestDTO(
    val productName:String ="",
    val price:Float =0f,
    val productQTE:Int =0,
    val productState:ProductState =ProductState.PRODUCTION,
)

data class  ProductResponseDTO(
    var idProduct:String="",
    var productName:String ="",
    var price:Float =0f,
    var productQTE:Int =0,
    var productState:ProductState =ProductState.PRODUCTION,
)




