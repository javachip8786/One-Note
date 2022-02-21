package com.example.lefrien.dao

import com.example.lefrien.models.user
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class userDao {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(user: user?) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                user.uid?.let { it1 -> usersCollection.document(it1).set(it) }
            }
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()
    }
}