package com.system.agrihelp.data.models

import com.google.firebase.Timestamp

data class Comment(
    val id: String? = null,
    val comment: String? = null,
    val date: Timestamp? = null,
    val username: String? = null,
    val userId: String? = null
)