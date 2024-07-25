package com.example.movieapp.Activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // İyileştirilmiş kullanım
        setContentView(binding.root) // Bağlama ile setContentView çağrısı

        val inputUsername = binding.etUsername
        val inputPassword = binding.etPassword
        val btnLogin = binding.btnLogin
        val tvForgetPassword = binding.tvForgetPassword
        val tvRegister = binding.tvRegister

        btnLogin.setOnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()
            login(username, password)
        }

        // Şifremi unuttum işlemi
        tvForgetPassword.setOnClickListener {
            // Şifremi unuttum işlemi
        }

        // Kayıt ol işlemi
        tvRegister.setOnClickListener {
            // Kayıt ol işlemi
        }
    }

    private fun login(username: String, password: String) {
        when {
            username.isEmpty() || password.isEmpty() -> {
                Toast.makeText(this, "Tüm alanları doldurmalısınız", Toast.LENGTH_LONG).show()
            }

            username == "test" && password == "test" -> {
                Toast.makeText(this, "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                // Giriş başarılı ise başka bir işleme geçiş yapabilirsiniz, örneğin:
                startActivity(Intent(this, MainActivity::class.java))
            }

            else -> {
                Toast.makeText(this, "Kullanıcı adı veya şifreniz yanlış", Toast.LENGTH_LONG).show()
            }
        }
    }
}
