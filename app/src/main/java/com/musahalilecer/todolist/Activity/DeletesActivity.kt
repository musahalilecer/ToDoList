package com.musahalilecer.todolist.Activity

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.musahalilecer.todolist.Class.DeletedToDoList
import com.musahalilecer.todolist.Class.DeletesAdapter
import com.musahalilecer.todolist.databinding.ActivityDeletesBinding


class DeletesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeletesBinding
    private lateinit var deletedLists: ArrayList<DeletedToDoList>
    private lateinit var deletesAdapter: DeletesAdapter
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        deletedLists = ArrayList<DeletedToDoList>()
        deletesAdapter = DeletesAdapter(deletedLists)
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = deletesAdapter

        try {
            database = this.openOrCreateDatabase("todolist", MODE_PRIVATE, null)
            val cursor: Cursor = database.rawQuery("SELECT * FROM deleted_todolist", null)
            val idIx = cursor.getColumnIndex("id")
            val titleIx = cursor.getColumnIndex("tittle")
            val descriptionIx = cursor.getColumnIndex("description")

            while (cursor.moveToNext()) {
                val id = cursor.getInt(idIx)
                val title = cursor.getString(titleIx)
                val description = cursor.getString(descriptionIx)

                val deletedToDo = DeletedToDoList(title,description,id)
                deletedLists.add(deletedToDo)
            }
            cursor.close()
            deletesAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    //    deletesAdapter.notifyDataSetChanged()
    }
    fun reload(view: View){
        try {
            deletedLists.clear()
            val cursor: Cursor = database.rawQuery("SELECT * FROM deleted_todolist", null)
            val idIx = cursor.getColumnIndex("id")
            val titleIx = cursor.getColumnIndex("tittle")
            val descriptionIx = cursor.getColumnIndex("description")

            while (cursor.moveToNext()) {
                val id = cursor.getInt(idIx)
                val title = cursor.getString(titleIx)
                val description = cursor.getString(descriptionIx)

                val deletedToDo = DeletedToDoList(title,description,id)
                deletedLists.add(deletedToDo)
            }
            deletesAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    fun delete(view: View){
        try {
            if (deletedLists.isNotEmpty()) {
                val selectedId = deletedLists[0].id
                val sqlString = "DELETE FROM deleted_todolist WHERE id = ?"
                val statement = database.compileStatement(sqlString)
                statement.bindLong(1, selectedId.toLong())
                statement.execute()

                deletedLists.removeAt(0)
                deletesAdapter.notifyItemRemoved(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::database.isInitialized && database.isOpen) {
            database.close()
        }
    }
}