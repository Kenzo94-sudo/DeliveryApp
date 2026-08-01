package com.example.idatdemo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idatdemo.adapters.HistorialAdapter
import com.example.idatdemo.data.apí.FakeStoreApiClient
//import com.example.idatdemo.data.apí.FakeStoreApiClient
import com.example.idatdemo.entity.Producto
import com.example.idatdemo.entity.Rating
import com.example.idatdemo.repository.ProductoRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialActivity : AppCompatActivity() {
    private lateinit var rvHistorial : RecyclerView
    private lateinit var historialAdapter : HistorialAdapter
    private lateinit var etBuscar : EditText
    private lateinit var ivBuscar : ImageView
    private val productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial)

        rvHistorial = findViewById<RecyclerView>(R.id.rvHistorial)
        etBuscar = findViewById<EditText>(R.id.etTitle)
        ivBuscar = findViewById<ImageView>(R.id.ivBuscar)
        rvHistorial.layoutManager = LinearLayoutManager(this)

//        val productos = listOf(
//            Producto(
//                1,
//                "Producto 1",
//                10.0,
//                "Descripción del producto 1",
//                "Categoría A",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXCI3SefznBLZfQ7m7CmqnBY42utUwgGYycYHz2owxko3DmQz53XkgaHvmpvwHEg-fOGlv&s=10"
//            ),
//            Producto(2, "Producto 2", 20.0, "Descripción del producto 2", "Categoría B", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXCI3SefznBLZfQ7m7CmqnBY42utUwgGYycYHz2owxko3DmQz53XkgaHvmpvwHEg-fOGlv&s=10"),
//            Producto(3, "Producto 3", 30.0, "Descripción del producto 3", "Categoría C", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXCI3SefznBLZfQ7m7CmqnBY42utUwgGYycYHz2owxko3DmQz53XkgaHvmpvwHEg-fOGlv&s=10")
//        )

//        val productoRepository = ProductoRepository(this)
////        productos.addAll(productoRepository.listarProductos())
        historialAdapter = HistorialAdapter(this, productos)
        rvHistorial.adapter = historialAdapter

        cargarProductosDesdeFirebase()
//        cargarProductosDesdeApi()

//        ivBuscar.setOnClickListener{
//            productos.clear()
//            productos.addAll(productoRepository.buscar(etBuscar.text.toString()))
//            historialAdapter.notifyDataSetChanged()
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarProductosDesdeApi() {
        FakeStoreApiClient.apiService.getProducts().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful && response.body() != null) {
                    productos.clear()
                    productos.addAll(response.body()!!)
                    historialAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("Error al cargar", t.message.toString())
                Toast.makeText(this@HistorialActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarProductosDesdeFirebase() {
        val referencia = FirebaseDatabase.getInstance().getReference("productos")
        Log.i("FIREBASE", "REFERENCIA:" + referencia)
        referencia.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productos.clear()
                    for (item in snapshot.children) {
                        val title = item.child("title").getValue(String::class.java)
                        val description = item.child("description").getValue(String::class.java)
                        val price = item.child("price")
                        val category = item.child("category").getValue(String::class.java)
                        val image = item.child("image").getValue(String::class.java)
                        productos.add(Producto(
                            id = 0,
                            title = title.toString(),
                            description = description.toString(),
                            price = price.value.toString().toDouble(),
                            category = category.toString(),
                            image = image.toString(),
                            rating = Rating(
                                rate = 0.0,
                                count = 0
                            )

                        ))
                    }
                    historialAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }
}