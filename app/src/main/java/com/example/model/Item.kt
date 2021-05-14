package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item (
        var id : Int = 0,
        var qt : Int = 0
) : Parcelable