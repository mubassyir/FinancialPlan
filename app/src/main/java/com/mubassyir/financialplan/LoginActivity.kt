package com.mubassyir.financialplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mubassyir.financialplan.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //checking if user already login before
        val prefs = getSharedPreferences("login", MODE_PRIVATE)
        if(prefs.getBoolean("isUserLogin",false)){
            launchMainActivity()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        with(binding) {
            btnRegister.setOnClickListener {
                Intent(this@LoginActivity,RegisterActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (email.isEmpty()) {
                    etEmail.also {
                        it.error = "Field is required"
                        it.requestFocus()
                    }
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    etPassword.also {
                        it.error = "Field is required"
                        it.requestFocus()
                    }
                    return@setOnClickListener
                }
                checkUser(email,password)
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun checkUser(email:String, password:String) {
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val prefsEmail = prefs.getString("email", null)
        val prefPass= prefs.getString("password", null)

        try {
            if (prefsEmail!!.isEmpty()){
                Toast.makeText(this, "No User registered", Toast.LENGTH_SHORT).show()
                return
            }

            if (prefsEmail != email){
                Toast.makeText(this,"email does`t match",Toast.LENGTH_SHORT).show()
            }

            if (prefsEmail == email && prefPass.equals(password)){
                val setIsLogin = getSharedPreferences("login", MODE_PRIVATE).edit()
                setIsLogin.putBoolean("isUserLogin",true)
                setIsLogin.apply()
                launchMainActivity()
            }
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    private fun launchMainActivity(){
        Intent(this,MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}