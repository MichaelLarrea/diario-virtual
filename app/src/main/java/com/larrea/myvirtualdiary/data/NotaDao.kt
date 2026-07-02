package com.larrea.myvirtualdiary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nota: Nota)

    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun getAll(): LiveData<List<Nota>>

    @Update
    suspend fun update(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)
}