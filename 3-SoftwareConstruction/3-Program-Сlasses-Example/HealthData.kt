package com.example.lab9.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class HealthData(
    var dataId: String? = null,
    var userId: String? = null,
    var date: Date,
    var healthDataText: String? = null
) : Parcelable
