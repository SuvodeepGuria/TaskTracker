package com.suvodeep.tasktracker.viewModel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvodeep.tasktracker.flashScreen.FlashScreen
import com.suvodeep.tasktracker.repository.NoteRepository
import com.suvodeep.tasktracker.taskTrackerData.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Notes>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    if (listOfNotes.isEmpty()) {
                        Log.d("Empty", "Empty list found")
                    }
                    _noteList.value = listOfNotes
                }
        }
    }

    fun addNote(note: Notes) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Notes) = viewModelScope.launch { repository.update(note) }
    fun removeNote(note: Notes) = viewModelScope.launch { repository.deleteNote(note) }
    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllNotes() }
}


@Composable
fun FlashScreenWithTimer(onFlashScreenFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onFlashScreenFinished()
    }
    FlashScreen()
}
