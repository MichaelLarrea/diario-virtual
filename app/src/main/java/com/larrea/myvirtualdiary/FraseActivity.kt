package com.larrea.myvirtualdiary

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.larrea.myvirtualdiary.api.ApiState
import kotlinx.coroutines.launch
import viewmodel.FraseViewModel

class FraseActivity : AppCompatActivity() {

    private val viewModel: FraseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frase)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvEstado = findViewById<TextView>(R.id.tvEstado)
        val tvFrase = findViewById<TextView>(R.id.tvFrase)
        val tvAutor = findViewById<TextView>(R.id.tvAutor)
        val btnCargar = findViewById<Button>(R.id.btnCargar)
        val btnSimularError = findViewById<Button>(R.id.btnSimularError)

        // Estado inicial visible
        progressBar.visibility = View.VISIBLE
        tvEstado.visibility = View.VISIBLE
        tvEstado.text = "Cargando información..."
        tvFrase.text = ""
        tvAutor.text = ""

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.estadoApi.collect { estado ->

                    when (estado) {

                        is ApiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            tvEstado.visibility = View.VISIBLE
                            tvEstado.text = "Cargando información..."
                            tvFrase.text = ""
                            tvAutor.text = ""
                        }

                        is ApiState.Success -> {
                            progressBar.visibility = View.GONE
                            tvEstado.visibility = View.VISIBLE
                            tvEstado.text = "Datos cargados correctamente"
                            tvFrase.text = "“${estado.data.quote}”"
                            tvAutor.text = estado.data.author
                        }

                        is ApiState.Error -> {
                            progressBar.visibility = View.GONE
                            tvEstado.visibility = View.VISIBLE
                            tvEstado.text = "Error"
                            tvFrase.text = estado.mensaje
                            tvAutor.text = ""
                        }
                    }
                }
            }
        }

        btnCargar.setOnClickListener {
            viewModel.cargarFrase()
        }

        btnSimularError.setOnClickListener {
            viewModel.simularError()
        }

        // Inicia la consulta al abrir la pantalla
        viewModel.cargarFrase()
    }
}