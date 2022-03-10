package mx.edu.itm.link.koufrashop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import mx.edu.itm.link.koufrashop.models.Carrito
import mx.edu.itm.link.koufrashop.models.Prenda
import mx.edu.itm.link.koufrashop.utils.MyUtils
import org.json.JSONObject

class ProductoActivity : AppCompatActivity() {

    private lateinit var url : String
    private lateinit var url2 : String
    lateinit var name:TextView
    lateinit var talla:TextView
    lateinit var precio:TextView
    lateinit var color:TextView
    lateinit var descripcion:TextView
    lateinit var foto:ImageView
    lateinit var button:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)
        val producto=intent.getIntExtra("producto",0)
        //println("Producto: "+producto.toString())
        val back=findViewById<FloatingActionButton>(R.id.btnBackProducto)
        name=findViewById(R.id.txtProductoName)
        talla=findViewById(R.id.txtProductoTalla)
        precio=findViewById(R.id.txtProductoPrecio)
        color=findViewById(R.id.txtProductoColor)
        descripcion=findViewById(R.id.txtProductoDescripcion)
        foto=findViewById(R.id.imgProducto)
        button=findViewById(R.id.btnAgregarCarrito)
        url = resources.getString(R.string.api)+"encuentraprenda.php"
        val params = HashMap<String,String>()
        params.put("id", producto.toString())
        val params2 = HashMap<String,String>()
        object : MyUtils() {
            @SuppressLint("WrongConstant")
            override fun formatResponse(response: String) {
                try {
                    val json= JSONObject(response)
                    val output=json.getJSONArray("output")
                    val jsonProducto=output.getJSONObject(0)
                    val producto= Prenda(
                        jsonProducto.getInt("id"),
                        jsonProducto.getString("nombre"),
                        jsonProducto.getString("talla"),
                        jsonProducto.getInt("sucursales_id"),
                        jsonProducto.getString("foto"),
                        jsonProducto.getInt("categoria_id"),
                        jsonProducto.getString("descripcion"),
                        jsonProducto.getInt("cantidad"),
                        jsonProducto.getDouble("precio"),
                        jsonProducto.getString("color"),
                        jsonProducto.getInt("departamento_id")
                    )
                    name.text=producto.nombre
                    color.text=producto.color
                    talla.text=producto.talla
                    descripcion.text=producto.descripcion
                    precio.text=producto.precio.toString()
                    Picasso.get().load( resources.getString(R.string.api)+"assets/images/categoria/productos/"+producto.foto)
                        .error(R.mipmap.ic_launcher_round).into(foto)

                    val objeto=Carrito(
                        producto.id,
                        producto.nombre,
                        producto.talla,
                        producto.sucursales_id,
                        producto.foto,
                        producto.categoria_id,
                        producto.descripcion,
                        1,
                        producto.precio,
                        producto.color,
                        producto.departamento_id
                    )
                    button.setOnClickListener {
                        try {
                            Toast.makeText(this@ProductoActivity, "Se agrego al carrito", 1).show()
                            url2 = resources.getString(R.string.api)+"addCarrito.php"
                            params2.put("id", objeto.id.toString())
                            params2.put("nombre",objeto.nombre)
                            params2.put("talla",objeto.talla)
                            params2.put("sucursales_id",objeto.sucursales_id.toString())
                            params2.put("foto",objeto.foto)
                            params2.put("categoria_id",objeto.categoria_id.toString())
                            params2.put("descripcion",objeto.descripcion)
                            params2.put("cantidad",objeto.cantidad.toString())
                            params2.put("precio",objeto.precio.toString())
                            params2.put("color",objeto.color)
                            params2.put("departamento_id",objeto.departamento_id.toString())
                            object : MyUtils() {
                                override fun formatResponse(response: String) {
                                }
                            }.consumePost(this@ProductoActivity, url2,params2)
                            finish()

                        }catch (e:Exception){
                            e.printStackTrace()
                            Toast.makeText(this@ProductoActivity, "Ocurrio un error al agregar el producto, intente mas tarde", 1).show()
                            finish()

                        }


                    }

                }catch (e:Exception){
                    e.printStackTrace()
                    "Error, el producto no esta disponible".toast(this@ProductoActivity)
                    finish()
                }
            }
        }.consumePost(this, url,params)
        back.setOnClickListener {
            finish()
        }
    }
}