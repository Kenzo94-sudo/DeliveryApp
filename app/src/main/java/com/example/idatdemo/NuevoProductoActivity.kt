package com.example.idatdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idatdemo.entity.Producto
import com.example.idatdemo.entity.Rating
import com.example.idatdemo.repository.ProductoRepository
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.UUID

class NuevoProductoActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etPrice: EditText
    private lateinit var etDescription: EditText
    private lateinit var etCategory: EditText
    private lateinit var etImage: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_producto)

        etTitle = findViewById(R.id.etTitle)
        etPrice = findViewById(R.id.etPrice)
        etDescription = findViewById(R.id.etDescription)
        etCategory = findViewById(R.id.etCategory)
        etImage = findViewById(R.id.etImage)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val title = etTitle.text.toString()
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val description = etDescription.text.toString()
            val category = etCategory.text.toString()
            val image = etImage.text.toString()
            val db = Firebase.database.reference
            val productoMap = mapOf(
                "title" to title,
                "price" to price,
                "description" to description,
                "category" to category,
                "image" to image,
            )
            //Integrar el id auto incremental
            val id = UUID.randomUUID().toString()
            db.child("productos").child(id).setValue(productoMap)
                //Agregar mensaje de exito
                .addOnSuccessListener {
                    Log.e("FIREBASE", "Producto insertado")
                    Toast.makeText(this, "Producto insertado", Toast.LENGTH_SHORT).show()
                    finish()
                }
                //Agregar mensaje de errores
                .addOnFailureListener { error ->
                    Log.e("FIREBASE", error.toString())
                    Toast.makeText(this, "No se puede insertar", Toast.LENGTH_SHORT).show()
                }
//            val productoRepository = ProductoRepository(this)
//            val idProducto = productoRepository.insertar(
//                Producto(
//                    id = 0,
//                    title = title,
//                    price = price,
//                    description = description,
//                    category = category,
//                    image = image,
//                    rating = Rating(
//                        rate = 0.0,
//                        count = 0
//                    )
//                )
//            )
//            Toast.makeText(this, "Producto insertado con ID: $idProducto", Toast.LENGTH_SHORT).show()
//            finish()
//        }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
