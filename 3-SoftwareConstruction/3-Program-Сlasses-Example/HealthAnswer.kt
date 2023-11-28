package com.example.lab9.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class HealthAnswer(
    var answerId: String? = null,
    var answerText: String? = null,
    var date: Date
) : Parcelable
