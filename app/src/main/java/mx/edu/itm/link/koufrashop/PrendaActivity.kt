package mx.edu.itm.link.koufrashop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.edu.itm.link.koufrashop.adapters.PrendasAdapter
import mx.edu.itm.link.koufrashop.models.Prenda
import mx.edu.itm.link.koufrashop.utils.MyUtils
import org.json.JSONObject

class PrendaActivity : AppCompatActivity() {
    private lateinit var url : String
    lateinit var recyclerPrendas: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenda)
        val categoria=intent.getIntExtra("categoria",0)
        val regresar=findViewById<FloatingActionButton>(R.id.btnBackPrenda)
        url = resources.getString(R.string.api)+"prendas.php"
        recyclerPrendas=findViewById(R.id.recyclerPrendas)
        val params = HashMap<String,String>()
        params.put("categoria", categoria.toString())
        object : MyUtils() {
            override fun formatResponse(response: String) {
                try {
                    val json= JSONObject(response)
                    val output=json.getJSONArray("output")
                    val prendas=ArrayList<Prenda>()
                    for (i in 0..output.length()-1){
                        val jsonPrenda=output.getJSONObject(i)
                        val prenda= Prenda(
                            jsonPrenda.getInt("id"),
                            jsonPrenda.getString("nombre"),
                            jsonPrenda.getString("talla"),
                            jsonPrenda.getInt("sucursales_id"),
                            jsonPrenda.getString("foto"),
                            jsonPrenda.getInt("categoria_id"),
                            jsonPrenda.getString("descripcion"),
                            jsonPrenda.getInt("cantidad"),
                            jsonPrenda.getDouble("precio"),
                            jsonPrenda.getString("color"),
                            jsonPrenda.getInt("departamento_id")
                        )
                        prendas.add(prenda)
                    }

                    recyclerPrendas.layoutManager= LinearLayoutManager(this@PrendaActivity)
                    recyclerPrendas.adapter= object:PrendasAdapter(this@PrendaActivity,R.layout.recycler_row_prenda,prendas){
                        override fun verPrenda(prendaItem: Prenda) {
                           // println("Testeando :"+prendaItem.nombre)
                            val intent= Intent(context,ProductoActivity::class.java)
                            intent.putExtra("producto",prendaItem.id)
                            startActivity(intent)
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    "Error, No hay categorias".toast(this@PrendaActivity)
                    finish()
                }
            }
        }.consumePost(this@PrendaActivity, url,params)

        regresar.setOnClickListener {
            finish()
        }


    }
}