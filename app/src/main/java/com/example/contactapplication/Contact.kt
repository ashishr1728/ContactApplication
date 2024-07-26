package com.example.contactapplication

import android.net.Uri

data class Contact (
    val profile: Uri,
    val nameText: String,
    val numberText: String
)