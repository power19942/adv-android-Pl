package com.example.pauloandroidcourse.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pauloandroidcourse.model.NoDo

@Dao
interface NoDoDao {

    @Insert
    fun insert(nodo: NoDo)

    @Query("delete from nodo_table")
    fun deleteAll()

    @Query("delete from nodo_table where id = :id")
    fun delete(id: Int)

    @Query("update nodo_table set nodo_col = :text where id = :id")
    fun updateItem(id: Int, text: String)

    @Query("select * from nodo_table order by nodo_col desc")
    fun getAllNoDos(): LiveData<List<NoDo>>
}