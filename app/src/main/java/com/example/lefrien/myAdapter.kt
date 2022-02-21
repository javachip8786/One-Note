package com.example.lefrien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lefrien.models.user
import kotlinx.android.synthetic.main.activity_register.*

class myAdapter(private val userList: ArrayList<user>): RecyclerView.Adapter<myAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myAdapter.MyViewHolder, position: Int) {
        val user : user = userList[position]
        holder.name.text = user.name
        holder.age.text = user.age
        holder.des.text = user.des
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvfirstName);
        val age: TextView = itemView.findViewById(R.id.tvage);
        val des: TextView = itemView.findViewById(R.id.tvdesc);
    }
}