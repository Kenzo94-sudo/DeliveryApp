package com.example.idatdemo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ListaComprasActivity : AppCompatActivity() {

    private lateinit var lvproductos : ListView
    private lateinit var txtbuscar : TextInputEditText
    private lateinit var btninsert : MaterialButton
    private lateinit var tilproducto : TextInputLayout
    private val productos = mutableListOf<String>()
    private lateinit var productosadapter : ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_compras)

        lvproductos = findViewById(R.id.lvproductos)
        txtbuscar = findViewById(R.id.txtbuscar)
        tilproducto = findViewById(R.id.tilproducto)
        btninsert = findViewById(R.id.btninsert)
        productos.add("Arroz")
        productos.add("Azucar")
        productosadapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productos
        )

        lvproductos.adapter = productosadapter
        btninsert.setOnClickListener{
            val producto = txtbuscar.text.toString().trim()
            if(producto.isNotEmpty()){
                productos.add(producto)
                productosadapter.notifyDataSetChanged()
                txtbuscar.text?.clear()
            }
            else{
                //Toast.makeText(this,"Ingrese el valor",Toast.LENGTH_SHORT).show()
                tilproducto.error = "Ingrese un producto"
            }

        }
        lvproductos.setOnItemClickListener{
            _ , _ , position , _ ->
            val producto = productos[position]
            val intent = Intent(this,ProductoSeleccionadoActivity::class.java)
            intent.putExtra("nombreproducto",producto)
            startActivity(intent)
        }

        lvproductos.setOnItemLongClickListener{ _, _, position, _ ->
            val producto = productos[position]
            val dialogView = layoutInflater.inflate(R.layout.dialog_opciones, null)
            val tvTitulo = dialogView.findViewById<TextView>(R.id.tvTitulo)
            tvTitulo.text = "Opciones para: ${producto}"
            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()
            dialog.show()
            val btnCancelar = dialogView.findViewById<MaterialButton>(R.id.btnCancelar)
            btnCancelar.setOnClickListener {
                dialog.dismiss()
            }
            val ivCancelar = dialogView.findViewById<ImageView>(R.id.ivCancelar)
            ivCancelar.setOnClickListener {
                dialog.dismiss()
            }
            val btnEliminar = dialogView.findViewById<MaterialButton>(R.id.btnEliminar)
            btnEliminar.setOnClickListener {
                productos.removeAt(position)
                productosadapter.notifyDataSetChanged()
                Toast.makeText(this, "${producto} eliminado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}