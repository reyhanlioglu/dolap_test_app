package com.example.dolaptestapp.product_detail.model


import com.google.gson.annotations.SerializedName



data class ProductDetails(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("desc")
    val description: String?,

    @SerializedName("image")
    val imageUrl: String?,

    @SerializedName("price")
    val price: Price?
)


data class Price(
    @SerializedName("value")
    var value: Double?,

    @SerializedName("currency")
    var currency: String?
)




