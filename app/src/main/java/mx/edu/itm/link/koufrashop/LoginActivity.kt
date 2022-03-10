package mx.edu.itm.link.koufrashop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import mx.edu.itm.link.koufrashop.models.Usuario
import mx.edu.itm.link.koufrashop.utils.MyUtils
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast

class LoginActivity : AppCompatActivity() {
    lateinit var btnIngresar:Button
    lateinit var correo:TextView
    lateinit var contraseña:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIngresar=findViewById(R.id.btnIngresarLogin)
        correo=findViewById(R.id.txtCorreoLogin)
        contraseña=findViewById(R.id.txtContraseñaLogin)

        btnIngresar.setOnClickListener {
            login(correo.text.toString(),contraseña.text.toString())
        }


    }

    private fun login(usuarioF:String,contraseñaF:String){
        try {

            val url = "${resources.getString(R.string.api)}encuentra.php"
            Log.d("URL",url)
            val params = HashMap<String,String>()
            params.put("correo", usuarioF)
            params.put("contraseña", contraseñaF)
            object : MyUtils() {
                override fun formatResponse(response: String) {
                    try {
                        val json= JSONObject(response)
                        val output=json.getJSONArray("output")
                        val jsonUser=output.getJSONObject(0)
                        var user=Usuario(
                            jsonUser.getInt("id"),
                            jsonUser.getString("nombre"),
                            jsonUser.getString("telefono"),
                            jsonUser.getString("correo"),
                            jsonUser.getString("contrasenia"),
                            jsonUser.getString("direccion")
                        )
                        this@LoginActivity.dbSet(user)
                        MotionToast.createToast(
                            this@LoginActivity,
                            "Exitoso",
                            "Bienvenido",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@LoginActivity,R.font.helvetica_regular)
                        )
                        val int=Intent(this@LoginActivity,HomeActivity::class.java)
                        startActivity(int)

                        }catch (e:Exception){
                        e.printStackTrace()
                        "Error, usuario o contraseña incorrectos".toast(this@LoginActivity)
                        finish()
                    }
                }
            }.consumePost(this@LoginActivity, url,params)


        }catch (e:Exception){

        }

    }
}