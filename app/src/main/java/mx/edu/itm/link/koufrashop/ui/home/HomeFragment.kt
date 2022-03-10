package mx.edu.itm.link.koufrashop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.adapters.DepartamentoAdapter
import mx.edu.itm.link.koufrashop.models.Departamento
import mx.edu.itm.link.koufrashop.utils.MyUtils
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var url : String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerDepartamentos = view.findViewById<RecyclerView>(R.id.recyclerDepartamentos)

        url = resources.getString(R.string.api)+"departamentos.php"
        object : MyUtils() {

            override fun formatResponse(response: String) {
                try {
                    val json=JSONObject(response)
                    val output=json.getJSONArray("output")
                    val departamentos=ArrayList<Departamento>()
                    for (i in 0..output.length()-1){
                        val jsonDepa=output.getJSONObject(i)
                        val departamento=Departamento(
                            jsonDepa.getInt("id"),
                            jsonDepa.getString("departamento"),
                            jsonDepa.getString("foto")
                        )
                        departamentos.add(departamento)
                    }

                    recyclerDepartamentos.adapter=DepartamentoAdapter(view.context,R.layout.recycler_row_departamento,departamentos)
                    recyclerDepartamentos.layoutManager=LinearLayoutManager(view.context)


                }catch (e:Exception){
                    e.printStackTrace()
                    "Error, no hay negocios disponibles".toast(view.context)
                }
            }
        }.consumeGet(view.context, url)
    }
}