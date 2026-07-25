package com.example.idatdemo

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.idatdemo.ui.InicioFragment
import com.example.idatdemo.ui.PerfilFragment
import com.google.android.material.navigation.NavigationView

class InicioActivity : AppCompatActivity() {
    private lateinit var dlayMenu : DrawerLayout
    private lateinit var nvMenu : NavigationView
    private lateinit var ivMenu : ImageView
    private lateinit var flayContenedor : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        dlayMenu = findViewById<DrawerLayout>(R.id.dlayMenu)
        nvMenu = findViewById<NavigationView>(R.id.nvMenu)
        ivMenu = findViewById<ImageView>(R.id.ivMenu)
        flayContenedor = findViewById<FrameLayout>(R.id.flayContenedor)

        ivMenu.setOnClickListener {
            dlayMenu.open()
        }

        nvMenu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itInicio -> replaceFragment(InicioFragment())
                R.id.itLista -> startActivity(Intent(this, ListaComprasActivity::class.java))
                R.id.itHistorial -> startActivity(Intent(this, HistorialActivity::class.java))
                R.id.itPerfil -> replaceFragment(PerfilFragment())
                R.id.itNuevoProducto -> startActivity(Intent(this, NuevoProductoActivity::class.java))
            }
            dlayMenu.closeDrawers()
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlayMenu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flayContenedor, fragment).commit()
    }
}