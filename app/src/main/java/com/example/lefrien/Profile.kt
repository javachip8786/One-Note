package com.example.lefrien

import android.content.ContentValues.TAG
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.tasks.OnCanceledListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.regBotton
import kotlinx.android.synthetic.main.activity_register.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lefrien.dao.userDao
import com.example.lefrien.models.user
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.R.string
import android.annotation.SuppressLint
import com.google.firebase.firestore.DocumentSnapshot

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        val query = db.collection("users")

        auth.currentUser?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val product: user? = task.result.toObject(user::class.java)
//                        Toast.makeText(this,"onComplete: $product",Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onComplete: $product")
                        if (product != null) {
                            name_is.text = product.name.toString()
                            age_is.text = product.age.toString()
                            des_is.text = product.des.toString()
                        }

                    } else {
//                        Toast.makeText(this,"not successful",Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "onComplete: ", task.exception)
                    }
                }
        }


        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        edit_Des.setOnClickListener {
            descUpdate()

        }

        edit_Name.setOnClickListener {
            nameUpdate()

        }

    }

    private fun nameUpdate() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("update your username")
            .setView(editText)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("OK",null)
            .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val emojiEntered = editText.text.toString()
            if(emojiEntered.isBlank()){
                Toast.makeText(this, "Please enter something", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val currentuser = auth.currentUser
            if(currentuser == null){
                Toast.makeText(this, "Please sign in first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            db.collection("users").document(currentuser.uid)
                .update("name",emojiEntered)
            dialog.dismiss()
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun descUpdate() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("update your description")
            .setView(editText)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("OK",null)
            .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val emojiEntered = editText.text.toString()
            if(emojiEntered.isBlank()){
                Toast.makeText(this, "Please enter something", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val currentuser = auth.currentUser
            if(currentuser == null){
                Toast.makeText(this, "Please sign in first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            db.collection("users").document(currentuser.uid)
                .update("des",emojiEntered)
            dialog.dismiss()
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }

    }


}