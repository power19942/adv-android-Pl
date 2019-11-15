package com.example.pauloandroidcourse.util

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.pauloandroidcourse.data.NoDoRoomDatabase
import com.example.pauloandroidcourse.data.NoDoDao
import com.example.pauloandroidcourse.model.NoDo

class NoDoRepository(
    application: Application
) {

    var db: NoDoRoomDatabase?
    var noDoDao: NoDoDao

    var allNoDos: LiveData<List<NoDo>>

    init {
        db = NoDoRoomDatabase.getDatabase(application)
        noDoDao = db!!.noDoDao()
        allNoDos = noDoDao.getAllNoDos()
    }

    fun insert(nodo: NoDo) {
        InsertAsncTask(noDoDao).execute()
    }
}

class InsertAsncTask(var ayncDao: NoDoDao) : AsyncTask<NoDo, Void, Void>() {

    override fun doInBackground(vararg noDo: NoDo): Void? {
        ayncDao.insert(noDo[0])
        return null
    }


}
