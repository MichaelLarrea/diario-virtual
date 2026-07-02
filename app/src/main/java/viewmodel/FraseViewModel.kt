package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larrea.myvirtualdiary.api.ApiState
import com.larrea.myvirtualdiary.api.FraseResponse
import com.larrea.myvirtualdiary.api.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FraseViewModel : ViewModel() {

    private val _estadoApi =
        MutableStateFlow<ApiState<FraseResponse>>(ApiState.Loading)

    val estadoApi: StateFlow<ApiState<FraseResponse>> =
        _estadoApi.asStateFlow()

    fun cargarFrase() {
        viewModelScope.launch {

            _estadoApi.value = ApiState.Loading

            try {
                delay(5000)

                val respuesta =
                    RetrofitClient.apiService.obtenerFraseAleatoria()

                _estadoApi.value = ApiState.Success(respuesta)

            } catch (e: Exception) {
                _estadoApi.value = ApiState.Error(
                    e.message ?: "No se pudo conectar con la API"
                )
            }
        }
    }

    fun simularError() {
        _estadoApi.value = ApiState.Error(
            "Error de conexión. Intenta nuevamente."
        )
    }
}