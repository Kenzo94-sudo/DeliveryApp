package com.example.idatdemo


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idatdemo.adapters.HistorialAdapter
import com.example.idatdemo.data.apí.FakeStoreApiClient
import com.example.idatdemo.entity.Producto
import com.example.idatdemo.entity.Rating
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detalleProducto : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        val producto = intent.getParcelableExtra<Producto>("producto")

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val ivImage = findViewById<ImageView>(R.id.ivImage)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        var rbRating = findViewById<RatingBar>(R.id.rbRating)

        tvTitle.text = producto?.title
        tvPrice.text = "S/ ${String.format("%.2f", producto?.price)}"
        Glide.with(this).load(producto?.image).into(ivImage)
        tvDescription.text = producto?.description
        rbRating.rating = producto?.rating?.rate?.toFloat() ?: 0f

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}