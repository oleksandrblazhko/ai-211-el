package com.example.lab9

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab9.databinding.ActivityMainBinding
import com.example.lab9.model.HealthQuestion
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Ініціалізація посилання на Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference("HealthQuestion")

        // Встановлення слухача натискання для кнопки
        binding.btn1.setOnClickListener {
            // Виклик функції для збереження питання про здоров'я
            saveHealthQuestion()
        }
    }

    fun sendQuestions(question: String, submission_date: Date): Int {
    // Checking input validation: question length should be between 1 and 500 characters
    if (question.isEmpty() || question.length > 500) {
        return -2 // Return -2 if the question length is invalid
    }

    // Check if the submission_date is not the current day (assuming the current date is currentDate)
    val currentDate = Date() // Get current date
    if (submission_date != currentDate) {
        return -3 // Return -3 for any other error
    }

    // Your logic to send the question to the database
    // Assuming the sending process was successful
    return 1 // Return 1 if the question was successfully sent
}

    private fun showResultDialog(resultMessage: String) {
        // Створення AlertDialog із визначеним повідомленням про результат
        val builder = AlertDialog.Builder(this)
        builder.setMessage(resultMessage)
            .setPositiveButton("OK") { dialog, _ ->
                // Закриття діалогового вікна при натисканні на позитивний кнопка
                dialog.dismiss()
            }

        // Відображення створеного AlertDialog
        val dialog = builder.create()
        dialog.show()
    }
}
