package com.desi.expertplantapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.desi.expertplantapp.R
import com.desi.expertplantapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // Initialize Firebase Auth

        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> registerUser()
            R.id.btn_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun registerUser() {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        binding.apply {
            if (username.isEmpty()) {
                etUsername.error = "Please enter Username!"
                etUsername.requestFocus()
                return
            }
            if (email.isEmpty()) {
                etEmail.error = "Please enter Username!"
                etEmail.requestFocus()
                return
            }
            if (password.isEmpty()) {
                etPassword.error = "Please enter Password!"
                etPassword.requestFocus()
                return
            }
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("Register", "createUserWithEmail:success")
                val user = auth.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener{
                    if (it.isSuccessful) {
                        Toast.makeText(baseContext, "Email verification send!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w("Register", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}