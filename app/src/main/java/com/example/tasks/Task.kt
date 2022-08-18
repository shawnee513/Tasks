package com.example.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    //@ColumnInfo annotation is only needed if you want the column name to be different than the property name
    //@Ignore annotation can be used if you don't want the data in the column to persist, the column won't be created
    @ColumnInfo(name = "task_name")
    var taskName: String = "",

    @ColumnInfo(name = "task_done")
    var taskDone: Boolean = false
)
