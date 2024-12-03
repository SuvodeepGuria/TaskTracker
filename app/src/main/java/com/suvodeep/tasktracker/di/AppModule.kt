package com.suvodeep.tasktracker.di

import android.content.Context
import androidx.room.Room
import com.suvodeep.tasktracker.taskTrackerData.NoteDatabase
import com.suvodeep.tasktracker.taskTrackerData.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDatabase(noteDatabase: NoteDatabase): NoteDatabaseDao=noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):NoteDatabase=Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notes_db"
    ).fallbackToDestructiveMigration().build()
}