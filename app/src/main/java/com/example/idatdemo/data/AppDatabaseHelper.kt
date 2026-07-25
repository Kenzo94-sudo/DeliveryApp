package com.example.idatdemo.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDatabaseHelper(context : Context) : SQLiteOpenHelper(context, "productos.db", null, 1) {
    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("""
            CREATE TABLE Producto (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT,
                price REAL,
                description TEXT,
                category TEXT,
                image TEXT
            );
        """.trimIndent())
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS Producto")
        onCreate(p0)
    }
}