package com.musahalilecer.todolist.Activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.musahalilecer.todolist.Class.ToDoList
import com.musahalilecer.todolist.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = this.openOrCreateDatabase("todolist", MODE_PRIVATE,null)

        database.execSQL("CREATE TABLE IF NOT EXISTS todolist (id INTEGER PRIMARY KEY, tittle VARCHAR, description VARCHAR)")

        val intent = intent
        val info = intent.getStringExtra("info")
        if(info.equals("new")){
            binding.tittleText.setText("")
            binding.descriptionText.setText("")
            binding.saveButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.INVISIBLE
            binding.idText.visibility = View.INVISIBLE
        }
        else {
            binding.saveButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.VISIBLE
            binding.idText.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)
            val cursor = database.rawQuery("SELECT * FROM todolist WHERE id= ?", arrayOf(selectedId.toString()))
        /*    val tittleIx = cursor.getColumnIndex("tittle")
            val descriptionIx = cursor.getColumnIndex("description")
            while(cursor.moveToNext()){
                binding.tittleText.setText(tittleIx.toString())
                binding.descriptionText.setText(descriptionIx.toString())
            }
         */
            val tittleIx = cursor.getColumnIndex("tittle")
            val descriptionIx = cursor.getColumnIndex("description")
            while (cursor.moveToNext()) {
                val tittleValue = cursor.getString(tittleIx)
                val descriptionValue = cursor.getString(descriptionIx)
                binding.tittleText.setText(tittleValue)
                binding.descriptionText.setText(descriptionValue)
            }

            cursor.close()
        }

    /*    database = this.openOrCreateDatabase("todolist", MODE_PRIVATE,null)

        val intent = intent
        val info = intent.getStringExtra("info")
        if(intent.equals("new")){
            binding.tittleText.setText("")
            binding.descriptionText.setText("")
            binding.saveButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.VISIBLE
        }
        else {
            binding.saveButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)
            val cursor = database.rawQuery("SELECT * FROM todolist WHERE id= ?", arrayOf(selectedId.toString()))
            val tittleIx = cursor.getColumnIndex("tittle")
            val descriptionIx = cursor.getColumnIndex("description")
            while(cursor.moveToNext()){
                binding.tittleText.setText(tittleIx.toString())
                binding.descriptionText.setText(descriptionIx.toString())
            }
            cursor.close()
        }

     */
    }


    fun save(view: View){
        val tittle = binding.tittleText.text.toString()
        val descripton = binding.descriptionText.text.toString()

        try {
            val sqlString = "INSERT INTO todolist (tittle,description) VALUES (?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,tittle)
            statement.bindString(2,descripton)
            statement.execute()

            Toast.makeText(this,"Kayıt Başarıyla Eklendi",Toast.LENGTH_LONG).show()

            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
    fun delete(view: View){

            try {
                val selectedId = intent.getIntExtra("id", -1)
                if (selectedId != -1) {
                    val sqlString = "DELETE FROM todolist WHERE id = ?"
                    val statement = database.compileStatement(sqlString)
                    statement.bindLong(1, selectedId.toLong())
                    statement.execute()

                    // Opsiyonel: Silme işlemi başarılı olduğunda kullanıcıyı bilgilendirme (toast mesajı)
                    Toast.makeText(this, "Kayıt başarıyla silindi", Toast.LENGTH_LONG).show()

                    // Opsiyonel: Ana ekrana geri dönme
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    // Opsiyonel: Eğer selectedId geçerli değilse (örneğin, -1 ise), kullanıcıyı bilgilendirme
                    Toast.makeText(this, "Geçerli bir öğe seçilmedi", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Opsiyonel: Hata durumunda kullanıcıyı bilgilendirme
                Toast.makeText(this, "Silme işlemi sırasında bir hata oluştu", Toast.LENGTH_SHORT).show()
            }
        val intent = Intent(this,DeletesActivity::class.java)
        intent.putExtra("delete","new")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::database.isInitialized && database.isOpen) {
            database.close()
        }
    }
}