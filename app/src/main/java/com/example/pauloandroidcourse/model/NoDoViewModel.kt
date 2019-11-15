package com.example.pauloandroidcourse.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pauloandroidcourse.util.NoDoRepository

class NoDoViewModel(
    application: Application
) : AndroidViewModel(application) {

    var repo: NoDoRepository
    var allNoDos: LiveData<List<NoDo>>

    init {
        repo = NoDoRepository(application)
        allNoDos = repo.allNoDos
    }

    fun insert(nodo: NoDo) {
        repo.insert(nodo)
    }

}