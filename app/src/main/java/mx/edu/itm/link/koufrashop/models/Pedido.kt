package mx.edu.itm.link.koufrashop.models

import com.google.gson.annotations.SerializedName

data class Pedido(
    @SerializedName("id")
    val id: Int,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("total")
    val total: String,
)
