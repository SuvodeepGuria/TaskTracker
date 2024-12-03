package com.suvodeep.tasktracker.taskTrackerData

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suvodeep.tasktracker.utilities.LocalDateTimeConverter
import com.suvodeep.tasktracker.utilities.UUIDConverter

@Database(entities = [Notes::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)//, UUIDConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}
