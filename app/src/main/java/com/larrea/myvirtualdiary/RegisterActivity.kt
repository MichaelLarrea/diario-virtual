package com.larrea.myvirtualdiary

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
import com.larrea.myvirtualdiary.data.Usuario
import kotlinx.coroutines.launch
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilRegisterEmail)
        tilPassword = findViewById(R.id.tilRegisterPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etRegisterEmail)
        etPassword = findViewById(R.id.etRegisterPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnRegister = findViewById(R.id.btnRegister)

        val textoLogin = findViewById<TextView>(R.id.tvGoToLogin)

        btnRegister.setOnClickListener {
            if (validarRegistro()) {
                guardarUsuario()
            }
        }

        textoLogin.setOnClickListener {
            finish()
        }
    }

    private fun validarRegistro(): Boolean {

        val nombre = etName.text.toString().trim()
        val correo = etEmail.text.toString().trim()
        val contrasena = etPassword.text.toString()
        val confirmarContrasena = etConfirmPassword.text.toString()

        tilName.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilConfirmPassword.error = null

        if (nombre.isEmpty()) {
            tilName.error = "El nombre es obligatorio"
            etName.requestFocus()
            return false
        }

        if (nombre.length < 3) {
            tilName.error = "El nombre debe tener al menos 3 caracteres"
            etName.requestFocus()
            return false
        }

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

        if (confirmarContrasena.isEmpty()) {
            tilConfirmPassword.error = "Confirma la contraseña"
            etConfirmPassword.requestFocus()
            return false
        }

        if (contrasena != confirmarContrasena) {
            tilConfirmPassword.error = "Las contraseñas no coinciden"
            etConfirmPassword.requestFocus()
            return false
        }

        return true
    }

    private fun guardarUsuario() {

        val nombre = etName.text.toString().trim()
        val correo = etEmail.text.toString().trim().lowercase()
        val contrasena = etPassword.text.toString()

        val baseDatos = AppDatabase.obtenerBaseDeDatos(this)
        val usuarioDao = baseDatos.usuarioDao()

        btnRegister.isEnabled = false

        lifecycleScope.launch {

            try {
                val usuarioExistente = usuarioDao.buscarPorCorreo(correo)

                if (usuarioExistente != null) {
                    tilEmail.error = "Este correo ya está registrado"
                    etEmail.requestFocus()
                    return@launch
                }

                val usuario = Usuario(
                    nombre = nombre,
                    correo = correo,
                    contrasena = generarHash(contrasena)
                )

                usuarioDao.insertarUsuario(usuario)

                Toast.makeText(
                    this@RegisterActivity,
                    "Usuario registrado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "No se pudo registrar el usuario",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                btnRegister.isEnabled = true
            }
        }
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