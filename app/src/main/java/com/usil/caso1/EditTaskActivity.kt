package com.usil.caso1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditTaskActivity : AppCompatActivity() {
    private lateinit var edtTask: EditText
    private var position: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_task)
        edtTask = findViewById(R.id.edtTask)


        val task = intent.getStringExtra("task")
        position = intent.getIntExtra("position", -1)
        edtTask.setText(task)
        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val updatedTask = edtTask.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("task", updatedTask)
            resultIntent.putExtra("position", position)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        val btnDelete: Button = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("position", position)
            setResult(RESULT_FIRST_USER, resultIntent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}