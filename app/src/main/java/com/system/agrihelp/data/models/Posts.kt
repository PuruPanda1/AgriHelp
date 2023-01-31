package com.system.agrihelp.data.models

import com.google.firebase.Timestamp

data class Posts(
    val comments: Long? = null,
    val date: Timestamp? = null,
    val description: String? = null,
    val hashtags: String? = null,
    val id: String? = null,
    val likes: Long? = null,
    val title: String? = null,
    val username: String? = null,
    val userId: String? = null
)
