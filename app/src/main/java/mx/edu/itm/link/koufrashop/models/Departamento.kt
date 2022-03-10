package mx.edu.itm.link.koufrashop.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Departamento(
    @SerializedName("id")
    val id: Int,
    @SerializedName("departamento")
    val departamento: String,
    @SerializedName("foto")
    val foto: String,

):Serializable
