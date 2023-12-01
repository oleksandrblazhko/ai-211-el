package com.example.lab9.model

import android.os.Parcelable
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionAboutHealth(
    var quesId: String? = null,
    var question: String? = null,
    var date: Date = Date()
) : Parcelable
