package com.example.pauloandroidcourse.model

import com.google.firebase.Timestamp


class Journal {
    var title: String? = null
    var thought: String? = null
    var imageUrl: String? = null
    var userId: String? = null
    var timeAdded: Timestamp? = null
    var name: String? = null

    //We Must have an empty constructor for Firestore
    constructor()

    constructor(
        title: String,
        thought: String,
        imageUrl: String,
        userId: String,
        timeAdded: Timestamp,
        name: String
    ) {
        this.title = title
        this.thought = thought
        this.imageUrl = imageUrl
        this.userId = userId
        this.timeAdded = timeAdded
        this.name = name
    }
}