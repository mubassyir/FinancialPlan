package com.mubassyir.financialplan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mubassyir.financialplan.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            btnLogin.setOnClickListener {
                Intent(this@RegisterActivity,LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            btnRegister.setOnClickListener {
                val fullName = etFullName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (fullName.isEmpty()) {
                    etFullName.also {
                        it.error = "Field required"
                        it.requestFocus()
                    }
                    return@setOnClickListener
                }

                if (email.isEmpty()) {
                    etEmail.also {
                        it.error = "Field required"
                        it.requestFocus()
                    }
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.also {
                        it.error = "Field required"
                        it.requestFocus()
                    }
                    return@setOnClickListener
                }

                val editor = getSharedPreferences("UserData", MODE_PRIVATE).edit()
                editor.putString("email", email)
                editor.putString("password",password)
                editor.apply()
                Toast.makeText(this@RegisterActivity,"Register Successfull",Toast.LENGTH_SHORT).show()
            }
        }
    }
}