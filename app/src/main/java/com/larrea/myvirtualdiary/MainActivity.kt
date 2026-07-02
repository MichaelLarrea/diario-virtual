package com.larrea.myvirtualdiary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.larrea.myvirtualdiary.adapter.NotaAdapter
import com.larrea.myvirtualdiary.data.Nota
import com.larrea.myvirtualdiary.notifications.RecordatorioWorker
import java.util.concurrent.TimeUnit
import viewmodel.NotaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var rvNotas: RecyclerView
    private lateinit var notaAdapter: NotaAdapter

    private val notaViewModel: NotaViewModel by viewModels()

    private val solicitarPermisoNotificaciones =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { concedido ->

            if (concedido) {
                programarNotificacionPrueba()
            } else {
                Toast.makeText(
                    this,
                    "Debes permitir las notificaciones para recibir recordatorios",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { vista, insets ->

            val barras =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            vista.setPadding(
                barras.left,
                barras.top,
                barras.right,
                barras.bottom
            )

            insets
        }

        configurarRecyclerView()
        configurarBotones()
        observarNotas()
    }

    private fun configurarRecyclerView() {

        rvNotas = findViewById(R.id.rvNotas)

        notaAdapter = NotaAdapter(
            listaNotas = emptyList(),

            alTocarNota = { notaSeleccionada ->
                abrirFormularioEdicion(notaSeleccionada)
            },

            alMantenerNota = { notaSeleccionada ->
                mostrarDialogoEliminar(notaSeleccionada)
            }
        )

        rvNotas.layoutManager = LinearLayoutManager(this)
        rvNotas.adapter = notaAdapter
    }

    private fun configurarBotones() {

        findViewById<Button>(R.id.btnNuevaNota)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    CrearNotaActivity::class.java
                )

                startActivity(intent)
            }

        findViewById<Button>(R.id.btnFrase)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    FraseActivity::class.java
                )

                startActivity(intent)
            }

        findViewById<Button>(R.id.btnProbarNotificacion)
            .setOnClickListener {

                comprobarPermisoYProgramar()
            }
    }

    private fun observarNotas() {

        notaViewModel.todasLasNotas.observe(this) { listaNotas ->
            notaAdapter.actualizarLista(listaNotas)
        }
    }

    private fun comprobarPermisoYProgramar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val permisoConcedido =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (permisoConcedido) {

                programarNotificacionPrueba()

            } else {

                solicitarPermisoNotificaciones.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

        } else {

            programarNotificacionPrueba()
        }
    }

    private fun programarNotificacionPrueba() {

        val solicitud =
            OneTimeWorkRequestBuilder<RecordatorioWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                "notificacion_prueba",
                ExistingWorkPolicy.REPLACE,
                solicitud
            )

        Toast.makeText(
            this,
            "La notificación aparecerá en 10 segundos",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun abrirFormularioEdicion(nota: Nota) {

        val intent = Intent(
            this,
            CrearNotaActivity::class.java
        )

        intent.putExtra("NOTA_ID", nota.id)
        intent.putExtra("NOTA_TITULO", nota.titulo)
        intent.putExtra("NOTA_CONTENIDO", nota.contenido)
        intent.putExtra("NOTA_FECHA", nota.fecha)

        startActivity(intent)
    }

    private fun mostrarDialogoEliminar(nota: Nota) {

        AlertDialog.Builder(this)
            .setTitle("Eliminar nota")
            .setMessage(
                "¿Seguro que deseas eliminar \"${nota.titulo}\"?"
            )
            .setPositiveButton("Eliminar") { _, _ ->

                notaViewModel.eliminar(nota)

                Snackbar.make(
                    findViewById(R.id.main),
                    "Nota eliminada",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Deshacer") {

                        notaViewModel.insertar(
                            nota.copy(id = 0)
                        )
                    }
                    .show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}