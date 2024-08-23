package com.musahalilecer.todolist.Class

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musahalilecer.todolist.Activity.DetailsActivity
import com.musahalilecer.todolist.databinding.RecyclerViewBinding

class ToDoAdapter(val toDoList: ArrayList<ToDoList>): RecyclerView.Adapter<ToDoAdapter.TodoHolder>() {
    class TodoHolder(val binding: RecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = RecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoHolder(binding)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.binding.textView.text = toDoList.get(position).tittle
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",toDoList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }
}