package mx.edu.itm.link.koufrashop

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import mx.edu.itm.link.koufrashop.models.Usuario
import mx.edu.itm.link.koufrashop.utils.MyUtils
import www.sanju.motiontoast.MotionToast

class RegisterActivity : AppCompatActivity() {

    lateinit var registrar: Button
    lateinit var usuario: EditText
    lateinit var telefono: EditText
    lateinit var email: EditText
    lateinit var contraseña: EditText
    lateinit var domiclio:EditText
    lateinit var login: Button

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registrar=findViewById(R.id.btnSaveRegister)
        usuario=findViewById(R.id.txtName)
        telefono=findViewById(R.id.txtTelefono)
        email=findViewById(R.id.txtCorreo)
        contraseña=findViewById(R.id.txtContraseña)
        domiclio=findViewById(R.id.txtDomicilio)
        login=findViewById(R.id.btnLoginRegister)

        var nombreUsuario=usuario.text
        var telefonoUsuario=telefono.text
        var emailUsuario=email.text
        var contraseñaUsuario=contraseña.text
        var domicilioUsuario=domiclio.text

        registrar.setOnClickListener {
            if (register(nombreUsuario.toString(),telefonoUsuario.toString(),emailUsuario.toString(),contraseñaUsuario.toString(),domicilioUsuario.toString())){
                MotionToast.createToast(
                    this@RegisterActivity,
                    "Completado",
                    "Registro completado exitosamente",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@RegisterActivity,R.font.helvetica_regular))
                val intent= Intent(this@RegisterActivity,HomeActivity::class.java)
                startActivity(intent)

            }else{
                MotionToast.createToast(
                    this@RegisterActivity,
                    "Error",
                    "Algo salio mal, intentelo mas tarde",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@RegisterActivity,R.font.helvetica_regular))
                finish()
            }
        }

        login.setOnClickListener {
            val intent=Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }

    }


    private fun register(nombreF:String,telefonoF:String,correoF:String,contraseñaF:String, domicilioF:String):Boolean{
        var verificar=false
        try{
            val url = "${resources.getString(R.string.api)}register.php"
            val params = HashMap<String,String>()
            params.put("nombre", nombreF)
            params.put("telefono", telefonoF)
            params.put("correo", correoF)
            params.put("contrasenia", contraseñaF)
            params.put("domicilio",domicilioF)

            object : MyUtils() {
                override fun formatResponse(response: String) {
                    try {
                        val user=Usuario(1, nombreF, telefonoF, correoF, contraseñaF, domicilioF)
                        this@RegisterActivity.dbSet(user)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }.consumePost(this, url, params)
            verificar=true
        }catch (e:Exception){
            e.printStackTrace()
            verificar= false
        }
        println(verificar)
        return verificar
    }
}