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

    private fun saveHealthQuestion() {
        // Отримання тексту, введеного в EditText
        val questionText = binding.qstEd1.text.toString()

        // Перевірка валідації введення: порожнє або більше 500 символів
        if (questionText.isEmpty() || questionText.length > 500) {
            // Показ Toast-повідомлення, якщо валідація не вдається
            Toast.makeText(this, "Будь ласка, введіть питання", Toast.LENGTH_SHORT).show()
            return
        }

        // Генерація унікального ідентифікатора для питання про здоров'я
        val quesId = dbRef.push().key!!

        // Створення об'єкту HealthQuestion із введеного питання та поточної дати
        val question = HealthQuestion(quesId, questionText)

        // Створення та відображення ProgressDialog для процесу збереження
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Збереження...")
        progressDialog.show()

        // Збереження питання про здоров'я в Firebase Realtime Database
        dbRef.child(quesId).setValue(question)
            .addOnCompleteListener { task ->
                // Закриття ProgressDialog після завершення операції бази даних
                progressDialog.dismiss()

                // Визначення, чи була успішна операція бази даних
                val resultMessage = if (task.isSuccessful) {
                    // Встановлення повідомлення про успіх
                    "Питання успішно збережено"
                } else {
                    // Встановлення повідомлення про невдачу, включаючи повідомлення про помилку
                    "Помилка: ${task.exception?.message}"
                }

                // Відображення діалогового вікна із повідомленням про результат
                showResultDialog(resultMessage)
            }
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
