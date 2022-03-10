package mx.edu.itm.link.koufrashop.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.RegisterActivity
import mx.edu.itm.link.koufrashop.adapters.CarritoAdapter
import mx.edu.itm.link.koufrashop.models.Prenda
import mx.edu.itm.link.koufrashop.ui.home.HomeViewModel
import mx.edu.itm.link.koufrashop.utils.MyUtils
import mx.edu.itm.link.koufrashop.utils.MyUtils.Companion.dbGet
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var url : String
    private lateinit var url2 : String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerCarrito = view.findViewById<RecyclerView>(R.id.recyclerCarrito)

        var btnpago=view.findViewById(R.id.btnPagarCarrito) as ImageButton
        var total=view.findViewById(R.id.txtTotal) as TextView
        url = resources.getString(R.string.api)+"showCarrito.php"
        val carrito=ArrayList<Prenda>()

        object : MyUtils() {

            override fun formatResponse(response: String) {
                try {
                    val json= JSONObject(response)
                    val output=json.getJSONArray("output")
                    for (i in 0..output.length()-1){
                        val jsonCarrito=output.getJSONObject(i)
                        val objeto= Prenda(
                            jsonCarrito.getInt("id"),
                            jsonCarrito.getString("nombre"),
                            jsonCarrito.getString("talla"),
                            jsonCarrito.getInt("sucursales_id"),
                            jsonCarrito.getString("foto"),
                            jsonCarrito.getInt("categoria_id"),
                            jsonCarrito.getString("descripcion"),
                            jsonCarrito.getInt("cantidad"),
                            jsonCarrito.getDouble("precio"),
                            jsonCarrito.getString("color"),
                            jsonCarrito.getInt("departamento_id")
                        )
                        carrito.add(objeto)
                    }

                    recyclerCarrito.adapter=object: CarritoAdapter(view.context,R.layout.recycler_row_carrito,carrito){
                        override fun eliminar(id: String) {
                            url2 = resources.getString(R.string.api)+"deleteCarrito.php"
                            val params2 = HashMap<String,String>()
                            params2.put("id", id)
                            object : MyUtils() {
                                override fun formatResponse(response: String) {
                                }
                            }.consumePost(view.context, url2,params2)
                        }
                        override fun precio(posicion: Double) {
                            total.text=posicion.toString()
                        }
                    }
                    recyclerCarrito.layoutManager=LinearLayoutManager(view.context)

                }catch (e:Exception){
                    e.printStackTrace()
                    "Error, no hay negocios disponibles".toast(view.context)
                }
            }
        }.consumeGet(view.context, url)

        btnpago.setOnClickListener {
            val context=activity as Activity
            val user=context.dbGet()
            if(user==null){
                val i=Intent(context,RegisterActivity::class.java)
                startActivity(i)
            }else{
                val url = "${resources.getString(R.string.api)}addPedido.php"
                val params2 = HashMap<String,String>()
                params2.put("fecha", "2021-07-13")
                params2.put("estado", "En proceso")
                params2.put("correo", user!!.correo)
                params2.put("total", total.text.toString())

                object : MyUtils() {
                    override fun formatResponse(response: String) {
                        try {
                        }catch (e:Exception){
                            e.printStackTrace()
                            "Error al agregar".toast(view.context)
                        }
                    }
                }.consumePost(view.context, url,params2)

                try {
                    val url2="${resources.getString(R.string.api)}eliminarCarrito.php"
                    object : MyUtils() {
                        override fun formatResponse(response: String) {
                            try {
                                MotionToast.createToast(
                                    context,
                                    "Completado",
                                    "Pedido en proceso",
                                    MotionToast.TOAST_SUCCESS,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(context,R.font.helvetica_regular)
                                )
                            }catch (e:Exception){
                                e.printStackTrace()
                                "Error al eliminar carrito".toast(view.context)
                            }
                        }
                    }.consumeGet(view.context, url2)

                    val intent=Intent(context,HomeViewModel::class.java)
                    startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }




        }

    }
}