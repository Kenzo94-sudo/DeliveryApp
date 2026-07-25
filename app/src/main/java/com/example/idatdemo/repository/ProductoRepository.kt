package com.example.idatdemo.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.idatdemo.data.AppDatabaseHelper
import com.example.idatdemo.entity.Producto

class ProductoRepository(context : Context) {
    private val dbHelper = AppDatabaseHelper(context)

    fun insertar(producto: Producto): Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
//            put("id", producto.id)
            put("title", producto.title)
            put("price", producto.price)
            put("description", producto.description)
            put("category", producto.category)
            put("image", producto.image)
        }
        val id = db.insert("Producto", null, valores)
        db.close()
        return id
    }
}

//    fun listarProductos() : List<Producto> {
//        val db = dbHelper.readableDatabase
//        val lista = mutableListOf<Producto>()
//        val cursor : Cursor = db.rawQuery("SELECT * FROM Producto", null)
//        while (cursor.moveToNext()) {
//            lista.add(
//                Producto(
//                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
//                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
//                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
//                    category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
//                    image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
//                )
//            )
//        }
//        cursor.close()
//        db.close()
//        return lista
//    }

//    fun buscar(query : String) : List<Producto> {
//        val db = dbHelper.readableDatabase
//        val lista = mutableListOf<Producto>()
//        val cursor : Cursor = db.rawQuery("SELECT * FROM Producto WHERE title LIKE '%$query%'", null)
//        while (cursor.moveToNext()) {
//            lista.add(
//                Producto(
//                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
//                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
//                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
//                    category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
//                    image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
//                )
//            )
//        }
//        cursor.close()
//        db.close()
//        return lista
//    }
//}
