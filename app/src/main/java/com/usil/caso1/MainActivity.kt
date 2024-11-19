package com.usil.caso1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val tasks=ArrayList<String>()
    private lateinit var adapter:ArrayAdapter<String>
    private val REQUEST_CODE_CREATE = 1
    private val REQUEST_CODE_EDIT = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnCreate: Button = findViewById(R.id.btnCreate)
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,tasks)
        val listView:ListView = findViewById(R.id.listView)
        listView.adapter=adapter
        btnCreate.setOnClickListener{
            val intent = Intent(this,CreateTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("task", tasks[position])
            intent.putExtra("position", position)
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE && resultCode == RESULT_OK) {
            val newTask = data?.getStringExtra("task")
            if (newTask != null) {
                tasks.add(newTask)
                adapter.notifyDataSetChanged()
            }
        }else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            val editedTask = data?.getStringExtra("task")
            val position = data?.getIntExtra("position", -1)
            if (editedTask != null && position != -1) {
                tasks[position!!] = editedTask
                adapter.notifyDataSetChanged()
            }
        }else if (resultCode == RESULT_FIRST_USER) {

            val position = data?.getIntExtra("position", -1)
            if (position != -1) {
                tasks.removeAt(position!!)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}