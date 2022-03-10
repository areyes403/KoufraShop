package mx.edu.itm.link.koufrashop.models

data class Prenda (
    val id:Int,
    val nombre:String,
    val talla:String,
    val sucursales_id:Int,
    val foto:String,
    val categoria_id:Int,
    val descripcion:String,
    val cantidad:Int,
    val precio:Double,
    val color:String,
    val departamento_id:Int
        )
