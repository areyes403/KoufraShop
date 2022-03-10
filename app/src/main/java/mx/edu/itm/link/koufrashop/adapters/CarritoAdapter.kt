package mx.edu.itm.link.koufrashop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.models.Prenda

abstract class CarritoAdapter(val context: Context, val res: Int, val list: ArrayList<Prenda>): RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    var pre=0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoAdapter.CarritoViewHolder {
        val view= LayoutInflater.from(context).inflate(res,null)
        var holder =CarritoViewHolder(view)
        holder.bind()
        return holder
    }

    override fun onBindViewHolder(holder: CarritoAdapter.CarritoViewHolder, position: Int) {
        pre=pre+list.get(position).precio
        precio(pre)
        val prenda =list[position]
        holder.img?.let { imgPhoto->
            var urlPhoto = imgPhoto.resources.getString(R.string.api)
            urlPhoto += "assets/images/categoria/productos/${list.get(position).foto}"
            Picasso.get().load(urlPhoto).into(holder.img);
        }
        holder.nombre?.text=list.get(position).nombre
        holder.color?.text=list.get(position).color
        holder.precio?.text=list.get(position).precio.toString()
        holder.tall?.text=list.get(position).talla
        holder.boton?.setOnClickListener {
            try {
                eliminar(list.get(position).id.toString())
                list.removeAt(position)
                pre=0.0
                notifyDataSetChanged()
                Snackbar.make(it, "Producto Eliminado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()

            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img:ImageView?=null
        var tall:TextView?=null
        var precio:TextView?=null
        var nombre:TextView?=null
        var color:TextView?=null
        var boton:FloatingActionButton?=null
        fun bind(){
            img=itemView.findViewById(R.id.imgPhotoPrenda) as ImageView
            tall=itemView.findViewById(R.id.txtPrendaTalla) as TextView
            precio=itemView.findViewById(R.id.txtPrendaPrecio) as TextView
            nombre=itemView.findViewById(R.id.textNamePrenda) as TextView
            color=itemView.findViewById(R.id.txtPrendaColor) as TextView
            boton=itemView.findViewById(R.id.btnEliminarCarrito) as FloatingActionButton

        }

    }

    abstract fun eliminar(id:String)
    abstract fun precio(precio:Double)


}