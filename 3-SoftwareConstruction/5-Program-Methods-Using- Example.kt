// Імпорт інструкцій для класів, пов'язаних із Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// ...

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


// Additional method to demonstrate reading data from the database
private fun readHealthQuestions() {
     // Display ProgressDialog during read operation
     val progressDialog = ProgressDialog(this)
     progressDialog.setMessage("Reading...")
     progressDialog.show()

     // Reading data from the database
     dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
         override fun onDataChange(snapshot: DataSnapshot) {
             progressDialog.dismiss()

             // Processing of received data
             val questionsList = mutableListOf<HealthQuestion>()
             for (childSnapshot in snapshot.children) {
                 val question = childSnapshot.getValue(HealthQuestion::class.java)
                 question?.let {
                     questionsList.add(it)
                 }
             }

             // Display received questions in a dialog box or perform additional actions
             val resultMessage = if (questionsList.isNotEmpty()) {
                 "Successfully retrieved ${questionsList.size} questions"
             } else {
                 "No questions found"
             }

             showResultDialog(resultMessage)
         }

         override fun onCancelled(error: DatabaseError) {
             progressDialog.dismiss()
             showResultDialog("Error reading data: ${error.message}")
         }
     })
}
