package com.musahalilecer.todolist.Class

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.musahalilecer.todolist.Activity.DeletesActivity
import com.musahalilecer.todolist.databinding.ActivityDeletesBinding
import com.musahalilecer.todolist.databinding.RecyclerView2Binding

class DeletesAdapter(val list: ArrayList<DeletedToDoList>): RecyclerView.Adapter<DeletesAdapter.DeletesHolder>() {
    class DeletesHolder(val binding: RecyclerView2Binding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeletesHolder {
        val binding = RecyclerView2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeletesHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DeletesHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.textViewDeletes.text = currentItem.tittle

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DeletesActivity::class.java)
            intent.putExtra("delete", "new")
            intent.putExtra("id", currentItem.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}