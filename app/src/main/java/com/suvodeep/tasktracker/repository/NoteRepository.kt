package com.suvodeep.tasktracker.repository

import com.suvodeep.tasktracker.taskTrackerData.NoteDatabaseDao
import com.suvodeep.tasktracker.taskTrackerData.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository  @Inject constructor(private val databaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Notes) = databaseDao.insert(note)
    suspend fun update(note: Notes) = databaseDao.update(note)
    suspend fun deleteNote(note: Notes) = databaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = databaseDao.deleteAll()
    fun getAllNotes(): Flow<List<Notes>> = databaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}