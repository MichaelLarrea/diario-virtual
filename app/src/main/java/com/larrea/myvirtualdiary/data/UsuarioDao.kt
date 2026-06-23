package com.larrea.myvirtualdiary.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): Usuario?

    @Query(
        "SELECT * FROM usuarios " +
                "WHERE correo = :correo AND contrasena = :contrasena LIMIT 1"
    )
    suspend fun verificarCredenciales(
        correo: String,
        contrasena: String
    ): Usuario?
}