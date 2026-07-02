package com.larrea.myvirtualdiary.data

import androidx.lifecycle.LiveData

class NotaRepository(
    private val notaDao: NotaDao
) {

    val todasLasNotas: LiveData<List<Nota>> = notaDao.getAll()

    suspend fun insertar(nota: Nota) {
        notaDao.insert(nota)
    }

    suspend fun actualizar(nota: Nota) {
        notaDao.update(nota)
    }

    suspend fun eliminar(nota: Nota) {
        notaDao.delete(nota)
    }
}