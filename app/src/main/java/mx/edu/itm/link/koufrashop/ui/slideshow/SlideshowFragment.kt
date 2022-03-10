package mx.edu.itm.link.koufrashop.ui.slideshow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.RegisterActivity
import mx.edu.itm.link.koufrashop.adapters.PedidosAdapter
import mx.edu.itm.link.koufrashop.models.Pedido
import mx.edu.itm.link.koufrashop.utils.MyUtils
import mx.edu.itm.link.koufrashop.utils.MyUtils.Companion.dbGet
import mx.edu.itm.link.koufrashop.utils.MyUtils.Companion.dbRemove
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast

class SlideshowFragment : Fragment() {

    private lateinit var slideShowViewModel: SlideshowViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideShowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario=view.context.dbGet()
        val nombre=view.findViewById(R.id.txtPerfilNombre) as TextView
        val telefono=view.findViewById(R.id.txtPerfilTelefono) as TextView
        val correo=view.findViewById(R.id.txtPerfilCorreo) as TextView
        val domicilio=view.findViewById(R.id.txtPerfilDomicilio) as TextView
        val btn=view.findViewById(R.id.btnCerrarSesion) as FloatingActionButton

        try {
            if (usuario == null) {
                val intent= Intent(view.context,RegisterActivity::class.java)
                startActivity(intent)
            }else{
                if (login(usuario!!.correo,usuario!!.contraseña)){
                    //Toast.makeText(activity as Activity, "Se encontro el usuario"+ view.context.dbGet()!!.nombre, Toast.LENGTH_SHORT).show()
                    nombre.text=usuario!!.nombre
                    telefono.text=usuario!!.telefono
                    correo.text=usuario!!.correo
                    domicilio.text=usuario!!.domicilio
                    var recyclerPedidos=view.findViewById(R.id.recyclerPedidos) as RecyclerView
                    val params2 = HashMap<String,String>()
                    params2.put("correo",view.context.dbGet()!!.correo)
                    val url3 = "${resources.getString(R.string.api)}showPedidos.php"
                    try {
                        object : MyUtils() {
                            override fun formatResponse(response: String) {
                                try {
                                    val json= JSONObject(response)
                                    var array=ArrayList<Pedido>()
                                    val output=json.getJSONArray("output")
                                    for (i in 0..output.length()-1){
                                        val jsonPedido=output.getJSONObject(i)
                                        val ped= Pedido(
                                            jsonPedido.getInt("id"),
                                            jsonPedido.getString("fecha"),
                                            jsonPedido.getString("estado"),
                                            jsonPedido.getString("correo"),
                                            jsonPedido.getString("total"),)
                                        array.add(ped)
                                    }
                                    recyclerPedidos.adapter= PedidosAdapter(view.context,R.layout.recycler_row_pedido,array)
                                    recyclerPedidos.layoutManager=LinearLayoutManager(view.context)
                                } catch (e: Exception) {
                                    MotionToast.createToast(
                                        activity as Activity,
                                        "Error ☹️",
                                        "Algo salio mal",
                                        MotionToast.TOAST_ERROR,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(activity as Activity,R.font.helvetica_regular)
                                    )
                                    "No se pudo conectar, intente mas tarde".toast(activity as Activity)
                                }
                            }
                        }.consumePost(activity as Activity, url3,params2)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
        btn.setOnClickListener {
            val cont=activity as Activity
            cont.dbRemove()
            cont.finish()
            Toast.makeText(cont, "Sesion finalizada", LENGTH_SHORT).show()
        }

    }

    private fun login(correo: String, contraseña: String):Boolean {
        var resultado=false
        val url = "${resources.getString(R.string.api)}encuentra.php"
        val params = HashMap<String,String>()
        params.put("correo", correo)
        params.put("contraseña", contraseña)
        try {
            object : MyUtils() {
                override fun formatResponse(response: String) {
                    try {
                        Log.i("Consume", response)
                        MotionToast.createToast(
                            activity as Activity,
                            "Exitoso️",
                            "Bienvenido",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(activity as Activity,R.font.helvetica_regular)
                        )
                    } catch (e: Exception) {
                        Log.e("FR", "Error:\n$e")
                        MotionToast.createToast(
                            activity as Activity,
                            "Error ☹️",
                            "No se encuentra el usuario",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(activity as Activity,R.font.helvetica_regular)
                        )
                        "No se pudo conectar, intente mas tarde".toast(activity as Activity)
                    }
                }
            }.consumePost(activity as Activity, url, params)
            resultado=true
        }catch (e:Exception){
            e.printStackTrace()
            resultado=false
        }
       return resultado
    }

}