package mx.edu.itm.link.koufrashop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.edu.itm.link.koufrashop.R

import mx.edu.itm.link.koufrashop.models.Prenda


abstract class PrendasAdapter(val context: Context, val res: Int, val list: ArrayList<Prenda>): RecyclerView.Adapter<PrendasAdapter.PrendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrendasAdapter.PrendaViewHolder {
        return PrendaViewHolder(
                LayoutInflater.from(context).inflate(res,null)
                )
    }

    override fun onBindViewHolder(holder: PrendasAdapter.PrendaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PrendaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(prend:Prenda){

            val img=itemView.findViewById<ImageView>(R.id.imgPhotoPrenda)
            val tall=itemView.findViewById<TextView>(R.id.txtPrendaTalla)
            val precio=itemView.findViewById<TextView>(R.id.txtPrendaPrecio)
            val nombre=itemView.findViewById<TextView>(R.id.textNamePrenda)
            val color=itemView.findViewById<TextView>(R.id.txtPrendaColor)

            prend.foto?.let {
                var urlPhoto = itemView.resources.getString(R.string.api)
                urlPhoto += "assets/images/categoria/productos/$it"
                Picasso.get().load(urlPhoto).into(img);
            }

            tall.text=prend.talla
            precio.text=prend.precio.toString()
            nombre.text=prend.nombre
            color.text=prend.color

            itemView.setOnClickListener {

                verPrenda(prend)


            }

        }
    }

    abstract fun verPrenda(prendaItem: Prenda)

}