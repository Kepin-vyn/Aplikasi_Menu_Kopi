package com.example.aplikasi_menu_kopi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validasi Input
            if (username.isEmpty()) {
                etUsername.error = "Username tidak boleh kosong"
            } else if (password.isEmpty()) {
                etPassword.error = "Password tidak boleh kosong"
            } else {
                // Contoh validasi sederhana
                if (username == "admin" && password == "admin123") {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    
                    // Navigasi ke MainActivity menggunakan Intent
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
                } else {
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
