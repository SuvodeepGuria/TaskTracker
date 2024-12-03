package com.suvodeep.tasktracker.taskTrackerData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "Notes_Table")
data class Notes(
    @PrimaryKey
    val id: UUID=UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "notes")
    val notes: String,

    @ColumnInfo(name = "date")
    val dateTime:LocalDateTime=LocalDateTime.now()

)