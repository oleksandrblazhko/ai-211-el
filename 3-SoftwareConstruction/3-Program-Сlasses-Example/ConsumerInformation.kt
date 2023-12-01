package com.example.lab9.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsumerInformation(
    var ConsumerId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var age: Int
) : Parcelable
