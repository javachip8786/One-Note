package com.example.lefrien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.tasks.OnCanceledListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener








class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            nextActivity()
        }


        loginBotton.setOnClickListener {
            signIn()
        }
        regBotton.setOnClickListener {
            register()
        }
    }

    private fun signIn() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        loginBotton.isEnabled = false
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
            loginBotton.isEnabled = true
            return@signIn
        }
        else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "You are Logged in",
                            Toast.LENGTH_SHORT
                        ).show()


                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user id",FirebaseAuth.getInstance().currentUser!!.uid)
                        intent.putExtra("email id", email)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                        regBotton.isEnabled = true
                    }
                }

            loginBotton.isEnabled = true
        }
    }


    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun nextActivity() {
//        Log.i(TAG, "main activity")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}