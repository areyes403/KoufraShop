package mx.edu.itm.link.koufrashop.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("contraseña")
    val contraseña: String,
    @SerializedName("domicilio")
    val domicilio: String
) : Serializable