package com.example.lefrien

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lefrien.models.ToDo
import com.example.lefrien.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_post.*

class AddPost : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    // Create a new user with a first and last name
    val cities = db.collection("users")

    private lateinit var auth: FirebaseAuth

    private lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        auth = Firebase.auth
        val query = db.collection("users")

        todoAdapter= ToDoAdapter(mutableListOf())

        rvToDoItems.adapter = todoAdapter

        rvToDoItems.layoutManager = LinearLayoutManager(this)

        auth.currentUser?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val product: user? = task.result.toObject(user::class.java)
//                        Toast.makeText(this,"onComplete: $product",Toast.LENGTH_SHORT).show()
                        Log.d(ContentValues.TAG, "onComplete: $product")
                        if (product != null) {
                            if (product.list != null) {
                                var arraylist = product.list
                                if (arraylist != null) {
                                    for(i in arraylist){
                                        val todo = ToDo(i)
                                        todoAdapter.addToDo(todo)
                                    }
                                }
                            }
                        }

                    } else {
//                        Toast.makeText(this,"not successful",Toast.LENGTH_SHORT).show()
                        Log.e(ContentValues.TAG, "onComplete: ", task.exception)
                    }
                }
        }

        BtnAddToDo.setOnClickListener {
            val todoTitle = edToEditTitle.text.toString()
            var mutableList = mutableListOf<String>()
            if(todoTitle.isNotEmpty()){
                auth.currentUser?.let {
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(it.uid)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val product: user? = task.result.toObject(user::class.java)
                                Log.d(ContentValues.TAG, "onComplete: $product")
                                if (product != null) {
                                        for(i in product.list!!){
                                            mutableList.add(i);
                                        }
                                }

                            } else {
                                Log.e(ContentValues.TAG, "onComplete: ", task.exception)
                            }
                        }
                }
                mutableList.add(todoTitle);
                db.collection("users").document(auth.currentUser!!.uid).update("list",mutableList)
                val todo = ToDo(todoTitle)
                todoAdapter.addToDo(todo)
                edToEditTitle.text.clear()
            }
        }

        BtnDelete.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}