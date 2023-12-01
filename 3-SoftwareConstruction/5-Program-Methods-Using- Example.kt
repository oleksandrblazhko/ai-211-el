// Імпорт інструкцій для класів, пов'язаних із Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// ...

private fun sendQuestion() {
    // Getting text entered in EditText
    val questionText = binding.qstEd1.text.toString()

    // Checking input validation: empty or more than 500 characters
    if (questionText.isEmpty() || questionText.length > 500) {
        // Showing a Toast message if validation fails
        Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show()
        return
    }

    // Generating a unique identifier for the health question
    val quesId = dbRef.push().key!!

    // Creating a HealthQuestion object from the entered question and the current date
    val question = HealthQuestion(quesId, questionText)

    // Creating and displaying a ProgressDialog for the saving process
    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Saving...")
    progressDialog.show()

    // Saving the health question to Firebase Realtime Database
    dbRef.child(quesId).setValue(question)
        .addOnCompleteListener { task ->
            // Dismissing the ProgressDialog after the database operation completes
            progressDialog.dismiss()

            // Determining if the database operation was successful
            val resultMessage = if (task.isSuccessful) {
                // Setting a success message
                "Question successfully saved"
            } else {
                // Setting a failure message, including an error message if available
                "Error: ${task.exception?.message}"
            }

            // Showing a dialog window with the result message
            showResultDialog(resultMessage)
        }
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
