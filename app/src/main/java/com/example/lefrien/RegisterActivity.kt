package com.example.lefrien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.regBotton
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.firestore.FirebaseFirestore
import com.example.lefrien.models.user

import com.google.firebase.auth.ktx.auth


class RegisterActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    // Create a new user with a first and last name
    val cities = db.collection("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        regBotton.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val name = Name.text.toString();
            val age = Age.text.toString();
            val des = "";




//            regBotton.isEnabled = false
            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //Storing in Cloud FireBase
//                        val firebaseuser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(
                            this@RegisterActivity,
                            "You are Registered",
                            Toast.LENGTH_SHORT
                        ).show()


                        // Create a new user with a first and last name
                        val data1 = user(Firebase.auth.currentUser!!.uid,name, age,des, email)

//                        data1?.let{
//                            GlobalScope.launch(Dispatchers.IO) {
                        data1.uid?.let { it1 -> cities.document(it1).set(data1) }
//                            }
//                        }

                        //Finished Storing in Cloud FireBase


                        val intent = Intent(this@RegisterActivity,MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        intent.putExtra("user id",firebaseuser.uid)
//                        intent.putExtra("email id", email)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT).show()
//                        regBotton.isEnabled = true
                    }
                }


        }

    }
}