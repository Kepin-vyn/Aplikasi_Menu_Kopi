package com.example.aplikasi_menu_kopi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class LoginActivity : ComponentActivity() {
    
    // Identitas Logcat menggunakan NIM
    private val TAG = "42430055"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        try {
            // Inisialisasi layout dan komponen UI
            setContentView(R.layout.activity_login)
            Log.d(TAG, "LoginActivity: Layout brewlist berhasil dimuat")
            
            val etUsername = findViewById<EditText>(R.id.etUsername)
            val btnLogin = findViewById<Button>(R.id.btnLogin)

            // Aksi tombol login
            btnLogin.setOnClickListener {
                try {
                    val username = etUsername.text.toString().trim()
                    Log.d(TAG, "User mencoba login dengan nama: $username")

                    if (username.isEmpty()) {
                        etUsername.error = "Nama tidak boleh kosong"
                        Log.w(TAG, "Login gagal: Nama kosong")
                        Toast.makeText(this, "Silakan masukkan nama Anda", Toast.LENGTH_SHORT).show()
                    } else {
                        // Navigasi ke MainActivity jika login berhasil
                        Log.i(TAG, "Login berhasil untuk user: $username")
                        Toast.makeText(this, "Selamat datang di brewlist, $username!", Toast.LENGTH_SHORT).show()
                        
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    // Log error saat memproses login
                    Log.e(TAG, "Terjadi kesalahan saat memproses login: ${e.message}", e)
                    Toast.makeText(this, "Kesalahan sistem: Gagal memproses login", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            // Log error saat inisialisasi activity
            Log.e(TAG, "Gagal menginisialisasi LoginActivity: ${e.message}", e)
            Toast.makeText(this, "Aplikasi mengalami kendala saat memuat halaman", Toast.LENGTH_LONG).show()
        }
    }
}
