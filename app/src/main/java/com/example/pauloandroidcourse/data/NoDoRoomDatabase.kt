package com.example.pauloandroidcourse.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pauloandroidcourse.model.NoDo
import androidx.room.Room
import android.os.AsyncTask.execute
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.AsyncTask
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.R.attr.name


@Database(entities = [NoDo::class], version = 1)
abstract class NoDoRoomDatabase : RoomDatabase() {


    abstract fun noDoDao(): NoDoDao


    companion object {

        @Volatile
        var INSTANCE: NoDoRoomDatabase? = null

        fun getDatabase(context: Context): NoDoRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NoDoRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        //create our db
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NoDoRoomDatabase::class.java, "nodo_database"
                        )
                            .addCallback(roomDatabaseCallBack)
                            .build()
                    }
                }
            }
            return INSTANCE
        }


        private val roomDatabaseCallBack = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }


        }
    }
}

class PopulateDbAsync(db: NoDoRoomDatabase?) : AsyncTask<Void, Void, Void>() {
    private val noDoDao: NoDoDao = db!!.noDoDao()

    override fun doInBackground(vararg voids: Void): Void? {
        //noDoDao.deleteAll(); //removes all items from our table
        //for testing
        var noDo = NoDo(0, "Buy a new Ferrari")
        noDoDao.insert(noDo)

        noDo = NoDo(0, "Buy a Big house");
        noDoDao.insert(noDo)

        return null
    }
}
