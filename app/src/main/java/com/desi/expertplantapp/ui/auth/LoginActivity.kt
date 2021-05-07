package com.desi.expertplantapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.desi.expertplantapp.ui.MainActivity
import com.desi.expertplantapp.R
import com.desi.expertplantapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // Initialize Firebase Auth

        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else Toast.makeText(baseContext, "Please check your email to verify!.", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
            R.id.btn_login -> doLogin()
        }
    }

    private fun doLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        binding.apply {
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

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
}