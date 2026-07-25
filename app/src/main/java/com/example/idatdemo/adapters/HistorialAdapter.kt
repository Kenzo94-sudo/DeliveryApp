package com.example.idatdemo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idatdemo.R
import com.example.idatdemo.detalleProducto
import com.example.idatdemo.entity.Producto

class HistorialAdapter(private val context : Context, private val lista : List<Producto>) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HistorialViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_historial, p0, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(p0: HistorialViewHolder, p1: Int) {
        val producto = lista[p1]
        Glide.with(context).load(producto.image).into(p0.ivImage)
        p0.tvTitle.text = producto.title
//        p0.tvDescription.text = producto.description
        p0.tvDescription.visibility = View.GONE
        p0.tvCategory.text = producto.category
        p0.tvPrice.text = "S/ ${String.format("%.2f", producto.price)}"
        p0.rbRating.rating = producto.rating.rate.toFloat()
        p0.cvProducto.setOnClickListener {
           val intent = Intent(context, detalleProducto::class.java)
            intent.putExtra("producto", producto)
            context.startActivity(intent)
            Toast.makeText(context, "seleccionado: ${producto.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class HistorialViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivImage : ImageView = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle : TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription : TextView = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvCategory : TextView = itemView.findViewById<TextView>(R.id.tvCategory)
        val tvPrice : TextView = itemView.findViewById<TextView>(R.id.tvPrice)
        val rbRating : RatingBar = itemView.findViewById<RatingBar>(R.id.rbRating)
        val cvProducto : CardView = itemView.findViewById<CardView>(R.id.cvProducto)
    }
}