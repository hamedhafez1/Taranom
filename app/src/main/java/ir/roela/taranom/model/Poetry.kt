package ir.roela.taranom.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Poetry(
    @SerializedName("poet")
    @Expose
    val poet: String,
    @SerializedName("m1")
    @Expose
    val bit_1: String,
    @SerializedName("m2")
    @Expose
    val bit_2: String,
    @SerializedName("url")
    @Expose
    val url: String
)
