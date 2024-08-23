package com.musahalilecer.todolist.Activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.musahalilecer.todolist.Class.ToDoAdapter
import com.musahalilecer.todolist.Class.ToDoList
import com.musahalilecer.todolist.R
import com.musahalilecer.todolist.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toDoList: ArrayList<ToDoList>
    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toDoList = ArrayList<ToDoList>()
        toDoAdapter = ToDoAdapter(toDoList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = toDoAdapter

        try {
            database = this.openOrCreateDatabase("todolist", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM todolist",null)
            val tittleIx = cursor.getColumnIndex("tittle")
        //    val descriptionIx = cursor.getColumnIndex("description")
            val idIx = cursor.getColumnIndex("id")

            while(cursor.moveToNext()){
                val tittle = cursor.getString(tittleIx)
            //    val description = cursor.getColumnName(descriptionIx)
                val id = cursor.getInt(idIx)

                val todo = ToDoList(tittle,id)
                toDoList.add(todo)
            }
            toDoAdapter.notifyDataSetChanged()
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addTodo){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::database.isInitialized && database.isOpen) {
            database.close()
        }
    }
}