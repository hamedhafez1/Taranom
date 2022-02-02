package ir.roela.taranom.model

import com.google.gson.annotations.SerializedName

data class Poem(
    @SerializedName("poet")
    val poet: String,
    @SerializedName("b1")
    val b_1: Bit,
    @SerializedName("url")
    val url: String
)

data class Bit(
    @SerializedName("m1")
    val m_1: String,
    @SerializedName("m2")
    val m_2: String
)
