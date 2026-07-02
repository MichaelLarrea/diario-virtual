package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.larrea.myvirtualdiary.data.AppDatabase
import com.larrea.myvirtualdiary.data.Nota
import com.larrea.myvirtualdiary.data.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository

    val todasLasNotas: LiveData<List<Nota>>

    init {
        val notaDao = AppDatabase
            .obtenerBaseDeDatos(application)
            .notaDao()

        repository = NotaRepository(notaDao)

        todasLasNotas = repository.todasLasNotas
    }

    fun insertar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertar(nota)
        }
    }

    fun actualizar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizar(nota)
        }
    }

    fun eliminar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminar(nota)
        }
    }
}