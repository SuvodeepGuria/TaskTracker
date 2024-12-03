package com.suvodeep.tasktracker.taskTrackerData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from Notes_Table")
    fun getNotes(): Flow<List<Notes>>

    @Query("SELECT * from Notes_Table where id =:id")
     suspend fun getNoteById(id: String): Notes

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Update//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Notes)

    @Query("DELETE from Notes_Table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Notes)


}
