package ir.roela.taranom.model

import com.google.gson.annotations.SerializedName

data class Poetry(
    @SerializedName("poet")
    val poet: String,
    @SerializedName("m1")
    val bit_1: String,
    @SerializedName("m2")
    val bit_2: String,
    @SerializedName("url")
    val url: String
)
