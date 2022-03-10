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

import mx.edu.itm.link.koufrashop.PrendaActivity
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.models.Categorias


class CategoriasAdapter(val context: Context,
                        val res: Int,
                        val list: ArrayList<Categorias>) :
    RecyclerView.Adapter<CategoriasAdapter.CategoriaViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasAdapter.CategoriaViewHolder {
        return CategoriaViewHolder(
            LayoutInflater.from(context).inflate(res,null)
        )
    }

    override fun onBindViewHolder(holder: CategoriasAdapter.CategoriaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(categ:Categorias){
            val img=itemView.findViewById<ImageView>(R.id.imgPhotoCategoria)
            val nombre=itemView.findViewById<TextView>(R.id.textNameCategoria)

            categ.foto?.let {
                var urlPhoto = itemView.resources.getString(R.string.api)
                urlPhoto += "assets/images/categoria/$it"
                Picasso.get().load(urlPhoto).into(img);
            }
            nombre.text=categ.nombre

            itemView.setOnClickListener{
                val intent=Intent(context,PrendaActivity::class.java)
                intent.putExtra("categoria",categ.id)
                context.startActivity(intent)
            }
        }
    }
}