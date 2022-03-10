package mx.edu.itm.link.koufrashop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.edu.itm.link.koufrashop.adapters.CategoriasAdapter
import mx.edu.itm.link.koufrashop.models.Categorias
import mx.edu.itm.link.koufrashop.utils.MyUtils
import org.json.JSONObject


class CategoriasActivity : AppCompatActivity() {

    private lateinit var url : String
    lateinit var recyclerCategorias:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

         recyclerCategorias =findViewById(R.id.recyclerCategorias)
        url = resources.getString(R.string.api)+"categoria.php"
        var departamento=intent.getStringExtra("departamento_id")
        val regresar=findViewById<FloatingActionButton>(R.id.btnBackCategorias)
        val params = HashMap<String,String>()
        params.put("departamento", departamento.toString())
        object : MyUtils() {
            override fun formatResponse(response: String) {
                try {
                    val json= JSONObject(response)
                    val output=json.getJSONArray("output")
                    val categorias=ArrayList<Categorias>()
                    for (i in 0..output.length()-1){
                        val jsonCate=output.getJSONObject(i)
                        val categoria= Categorias(
                            jsonCate.getInt("id"),
                            jsonCate.getString("nombre"),
                            jsonCate.getString("foto"),
                            jsonCate.getInt("departamento_id")
                        )
                        categorias.add(categoria)
                    }
                    recyclerCategorias.adapter= CategoriasAdapter(this@CategoriasActivity,R.layout.recycler_row_categoria,categorias)
                    recyclerCategorias.layoutManager= LinearLayoutManager(this@CategoriasActivity)
                }catch (e:Exception){
                    e.printStackTrace()
                    "Error, No hay categorias".toast(this@CategoriasActivity)
                    finish()
                }
            }
        }.consumePost(this@CategoriasActivity, url,params)
        regresar.setOnClickListener {
            finish()
        }
    }
}