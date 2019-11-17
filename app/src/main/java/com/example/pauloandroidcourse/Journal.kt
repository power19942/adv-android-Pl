package com.example.pauloandroidcourse

class Journal {
    var title: String? = null
    var thought: String? = null

    //We Must have an empty constructor for Firestore
    constructor()
    
    constructor(title: String, thought: String) {
        this.title = title
        this.thought = thought
    }
}