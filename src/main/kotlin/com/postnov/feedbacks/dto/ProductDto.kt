package com.postnov.feedbacks.dto

data class ProductDto(
    val data: ProductsList?
)

data class ProductsList(
    val products: List<Products>?
)

data class Products(
    val id: Int?,
    val root: Int?,
    val name: String?,
)
