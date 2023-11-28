package com.example.lab9.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthCenterEmployee(
    var employeeHeathCenter: String? = null,
    var name: String? = null,
    var age: String? = null,
    var position: String? = null
) : Parcelable

