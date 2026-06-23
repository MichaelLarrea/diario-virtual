package com.larrea.myvirtualdiary

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.larrea.myvirtualdiary.data.AppDatabase
import kotlinx.coroutines.launch
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        val textoRegistro = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            if (validarFormulario()) {
                iniciarSesion()
            }
        }

        textoRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validarFormulario(): Boolean {

        val correo = etEmail.text.toString().trim()
        val contrasena = etPassword.text.toString()

        tilEmail.error = null
        tilPassword.error = null

        if (correo.isEmpty()) {
            tilEmail.error = "El correo es obligatorio"
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilEmail.error = "Ingresa un correo válido"
            etEmail.requestFocus()
            return false
        }

        if (contrasena.isEmpty()) {
            tilPassword.error = "La contraseña es obligatoria"
            etPassword.requestFocus()
            return false
        }

        if (contrasena.length < 6) {
            tilPassword.error =
                "La contraseña debe tener al menos 6 caracteres"
            etPassword.requestFocus()
            return false
        }

        return true
    }

    private fun iniciarSesion() {

        val correo = etEmail.text.toString().trim().lowercase()
        val contrasena = etPassword.text.toString()
        val contrasenaHash = generarHash(contrasena)

        val baseDatos = AppDatabase.obtenerBaseDeDatos(this)
        val usuarioDao = baseDatos.usuarioDao()

        btnLogin.isEnabled = false

        lifecycleScope.launch {

            try {
                val usuario = usuarioDao.verificarCredenciales(
                    correo,
                    contrasenaHash
                )

                if (usuario != null) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT
                    ).show()

                    abrirPantallaPrincipal()
                } else {
                    tilPassword.error = "Correo o contraseña incorrectos"
                    etPassword.requestFocus()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "No se pudo iniciar sesión",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                btnLogin.isEnabled = true
            }
        }
    }

    private fun abrirPantallaPrincipal() {

        val intent = Intent(this, MainActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }

    private fun generarHash(texto: String): String {

        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(texto.toByteArray())

        return bytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }
}