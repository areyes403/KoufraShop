package mx.edu.itm.link.koufrashop.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categorias(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("departamento_id")
    val departamento_id: Int,


):Serializable
