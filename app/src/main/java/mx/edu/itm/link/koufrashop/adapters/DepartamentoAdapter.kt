package mx.edu.itm.link.koufrashop.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.edu.itm.link.koufrashop.CategoriasActivity
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.models.Departamento

 class DepartamentoAdapter(val context: Context,
                                   val res: Int,
                                   val list: ArrayList<Departamento>):
    RecyclerView.Adapter<DepartamentoAdapter.DepaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartamentoAdapter.DepaViewHolder {
        return DepaViewHolder(
            LayoutInflater.from(context).inflate(res,null)
        )
    }

    override fun onBindViewHolder(holder: DepartamentoAdapter.DepaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class DepaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(depa:Departamento){
            val img=itemView.findViewById<ImageView>(R.id.imgPhotoDepartamento)
            val departamento=itemView.findViewById<TextView>(R.id.textNameDepartamento)


            depa.foto?.let {
                var urlPhoto = itemView.resources.getString(R.string.api)
                urlPhoto += "assets/images/$it"
                Picasso.get().load(urlPhoto).into(img);
            }

            departamento.text=depa.departamento
            //println("Adapter"+depa.id.toString())

            itemView.setOnClickListener{
                val intent=Intent(context, CategoriasActivity::class.java)
                intent.putExtra("departamento_id",depa.id.toString())
                context.startActivity(intent)
            }
        }


    }


}