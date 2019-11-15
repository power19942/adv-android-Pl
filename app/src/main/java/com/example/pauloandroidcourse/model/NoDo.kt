package com.example.pauloandroidcourse.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodo_table")
class NoDo(
    @PrimaryKey(autoGenerate = true) private var id: Int,
    @NonNull
    @ColumnInfo(name = "nodo_col") var noDo: String
)