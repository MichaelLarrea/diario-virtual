package com.larrea.myvirtualdiary.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.larrea.myvirtualdiary.R

class RecordatorioWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        crearCanalNotificaciones()

        mostrarNotificacion()

        return Result.success()
    }

    private fun crearCanalNotificaciones() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val canal = NotificationChannel(
                CANAL_ID,
                "Recordatorios del diario",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description =
                    "Recordatorios para escribir una nueva entrada en el diario"
            }

            val notificationManager =
                applicationContext.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            notificationManager.createNotificationChannel(canal)
        }
    }

    private fun mostrarNotificacion() {

        val notificacion = NotificationCompat.Builder(
            applicationContext,
            CANAL_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("MyVirtualDiary")
            .setContentText(
                "¿Cómo estuvo tu día? Escribe una nueva entrada en tu diario."
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat
                .from(applicationContext)
                .notify(NOTIFICACION_ID, notificacion)
        }
    }

    companion object {
        const val CANAL_ID = "canal_recordatorios_diario"
        const val NOTIFICACION_ID = 1001
    }
}