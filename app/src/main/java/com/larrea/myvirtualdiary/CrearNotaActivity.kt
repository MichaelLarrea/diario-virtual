package com.larrea.myvirtualdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.larrea.myvirtualdiary.data.Nota
import viewmodel.NotaViewModel

class CrearNotaActivity : AppCompatActivity() {

    private val notaViewModel: NotaViewModel by viewModels()

    private var notaId: Int = -1
    private var modoEdicion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_nota)

        val tvTituloFormulario =
            findViewById<TextView>(R.id.tvTituloFormulario)

        val etTitulo =
            findViewById<EditText>(R.id.etTitulo)

        val etContenido =
            findViewById<EditText>(R.id.etContenido)

        val etFecha =
            findViewById<EditText>(R.id.etFecha)

        val btnGuardarNota =
            findViewById<Button>(R.id.btnGuardarNota)

        notaId = intent.getIntExtra("NOTA_ID", -1)

        if (notaId != -1) {
            modoEdicion = true

            val tituloRecibido =
                intent.getStringExtra("NOTA_TITULO") ?: ""

            val contenidoRecibido =
                intent.getStringExtra("NOTA_CONTENIDO") ?: ""

            val fechaRecibida =
                intent.getStringExtra("NOTA_FECHA") ?: ""

            tvTituloFormulario.text = "Editar nota"
            btnGuardarNota.text = "Guardar cambios"

            etTitulo.setText(tituloRecibido)
            etContenido.setText(contenidoRecibido)
            etFecha.setText(fechaRecibida)
        }

        btnGuardarNota.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val contenido = etContenido.text.toString().trim()
            val fecha = etFecha.text.toString().trim()

            if (
                titulo.isEmpty() ||
                contenido.isEmpty() ||
                fecha.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (modoEdicion) {

                val notaActualizada = Nota(
                    id = notaId,
                    titulo = titulo,
                    contenido = contenido,
                    fecha = fecha
                )

                notaViewModel.actualizar(notaActualizada)

                Toast.makeText(
                    this,
                    "Nota actualizada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val nuevaNota = Nota(
                    titulo = titulo,
                    contenido = contenido,
                    fecha = fecha
                )

                notaViewModel.insertar(nuevaNota)

                Toast.makeText(
                    this,
                    "Nota guardada correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }

            finish()
        }
    }
}