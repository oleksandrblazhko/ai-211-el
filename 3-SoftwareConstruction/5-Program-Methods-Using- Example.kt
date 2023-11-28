// Код активності (MainActivity)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ініціалізація посилання на базу даних Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference("HealthQuestion")

        // Обробник натискання кнопки "Надіслати"
        binding.btnSendQuestion.setOnClickListener {
            saveHealthQuestion()
        }
    }

    // Метод для збереження питань про здоров'я
    private fun saveHealthQuestion() {
        val questionText = binding.qstEd1.text.toString()

        // Перевірка, чи не порожнє введене питання та його довжина не перевищує 500 символів
        if (questionText.isEmpty() || questionText.length > 500) {
            Toast.makeText(this, "Будь ласка, введіть питання", Toast.LENGTH_SHORT).show()
            return
        }

        // Створення унікального ідентифікатора для нового питання
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

                // Виведення результату в діалоговому вікні
                val resultMessage = if (task.isSuccessful) {
                    "Питання успішно збережено"
                } else {
                    "Помилка: ${task.exception?.message}"
                }

                showResultDialog(resultMessage)
            }
    }

    // Метод для відображення результату в діалоговому вікні
    private fun showResultDialog(resultMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(resultMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}

// Модель HealthQuestion
@Parcelize
data class HealthQuestion(
    var quesId: String? = null,
    var question: String? = null,
    var date: Date = Date()
) : Parcelable
