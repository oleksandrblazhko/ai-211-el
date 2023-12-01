// Імпорт інструкцій для класів, пов'язаних із Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// ...

private fun sendQuestion() {
    val questionText = binding.qstEd1.text.toString()

    // Перевірка, чи введене питання не порожнє і його довжина не перевищує 500 символів
    if (questionText.isEmpty() || questionText.length > 500) {
        Toast.makeText(this, "Будь ласка, введіть дійсне питання", Toast.LENGTH_SHORT).show()
        return
    }

    // Унікальний ідентифікатор для нового питання
    val quesId = dbRef.push().key!!

    // Створення об'єкту HealthQuestion з введеним питанням
    val question = HealthQuestion(quesId, questionText)

    // Відображення ProgressDialog під час збереження
    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Збереження...")
    progressDialog.show()

    // Збереження питання в базу даних
    dbRef.child(quesId).setValue(question)
        .addOnCompleteListener { task ->
            progressDialog.dismiss()

            // Відображення результату в діалоговому вікні
            val resultMessage = if (task.isSuccessful) {
                "Питання успішно збережено"
            } else {
                "Помилка: ${task.exception?.message}"
            }

            showResultDialog(resultMessage)
        }
}

// Додатковий метод для демонстрації читання даних з бази даних
private fun readHealthQuestions() {
    // Відображення ProgressDialog під час операції читання
    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Читання...")
    progressDialog.show()

    // Читання даних з бази даних
    dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            progressDialog.dismiss()

            // Обробка отриманих даних
            val questionsList = mutableListOf<HealthQuestion>()
            for (childSnapshot in snapshot.children) {
                val question = childSnapshot.getValue(HealthQuestion::class.java)
                question?.let {
                    questionsList.add(it)
                }
            }

            // Відображення отриманих питань у діалоговому вікні або виконання додаткових дій
            val resultMessage = if (questionsList.isNotEmpty()) {
                "Успішно отримано ${questionsList.size} питань"
            } else {
                "Питань не знайдено"
            }

            showResultDialog(resultMessage)
        }

        override fun onCancelled(error: DatabaseError) {
            progressDialog.dismiss()
            showResultDialog("Помилка читання даних: ${error.message}")
        }
    })
}
