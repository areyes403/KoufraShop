package mx.edu.itm.link.koufrashop.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.jvm.Throws

class LocalDB(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            var sql = """
               CREATE TABLE usuario(
                    id INTEGER PRIMARY KEY,
                    nombre TEXT,
                    telefono TEXT,
                    correo TEXT,
                    contraseña TEXT,
                    domicilio TEXT
               ) 
            """
            it.execSQL(sql)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @Throws
    fun addUsr(usuario: Usuario) {
        val db = writableDatabase
        var sql = "DELETE FROM usuario"
        db.execSQL(sql)
        sql = "INSERT INTO usuario VALUES(${usuario.id},'${usuario.nombre}','${usuario.telefono}','${usuario.correo}','${usuario.contraseña}','${usuario.domicilio}')"
        db.execSQL(sql)
        db.close()
    }

    @Throws
    fun getUsr() : Usuario? {
        val db = readableDatabase

        var sql = "SELECT * FROM usuario"

        val cursor = db.rawQuery(sql, null)

        var usuario : Usuario? = null
        if(cursor.moveToNext()) {
            usuario = Usuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
        }
        db.close()

        return usuario
    }

    @Throws
    fun removeUsr() {
        val db = writableDatabase

        var sql = "DELETE FROM usuario"

        db.execSQL(sql)

        db.close()
    }


}