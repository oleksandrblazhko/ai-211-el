package com.example.lab9.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var ConsumerId: String? = null,
    var login: String? = null,
    var password: String? = null
) : Parcelable
