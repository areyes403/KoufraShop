package mx.edu.itm.link.koufrashop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.edu.itm.link.koufrashop.R
import mx.edu.itm.link.koufrashop.models.Pedido



class PedidosAdapter(val context: Context, val res: Int, val list: ArrayList<Pedido>) : RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosAdapter.PedidosViewHolder {
        return PedidosViewHolder(
            LayoutInflater.from(context).inflate(res,null)
        )
    }

    override fun onBindViewHolder(holder: PedidosAdapter.PedidosViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PedidosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(pedido:Pedido){
            val id=itemView.findViewById<TextView>(R.id.idPedidoRow)
            val estado=itemView.findViewById<TextView>(R.id.estadoPedidoRow)
            val total=itemView.findViewById<TextView>(R.id.totalPedidoRow)
            val fecha=itemView.findViewById<TextView>(R.id.fechaPedidoRow)
            id.text=pedido.id.toString()
            estado.text=pedido.estado
            total.text=pedido.total
            fecha.text=pedido.fecha

        }
    }
}