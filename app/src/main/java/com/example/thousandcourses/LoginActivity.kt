package com.example.thousandcourses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thousandcourses.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginButton()
        setupSocialButtons()
        setupInputValidation()
    }

    private fun setupInputValidation() {
        // Проверяем поля в реальном времени
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)
    }

    private fun validateInputs() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        // Простая валидация email (как в ТЗ)
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 3 // Минимум 3 символа

        binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()

            // Переход на главный экран
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USER_EMAIL", email) // Можно передать email
            startActivity(intent)

            // Закрываем экран входа, чтобы нельзя было вернуться назад
            finish()
        }
    }

    private fun setupSocialButtons() {
        binding.btnVK.setOnClickListener {
            openUrl("https://vk.com/")
        }

        binding.btnOK.setOnClickListener {
            openUrl("https://ok.ru/")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}