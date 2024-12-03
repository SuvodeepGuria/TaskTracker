package com.suvodeep.tasktracker

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvodeep.tasktracker.components.InputField
import com.suvodeep.tasktracker.components.NewNotes
import com.suvodeep.tasktracker.components.TopBar
import com.suvodeep.tasktracker.taskTrackerData.Notes
import com.suvodeep.tasktracker.utilities.DateFormat

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun TaskTrackerApp(
    modifier: Modifier = Modifier,
    notes: List<Notes>,
    onAddNotes: (Notes) -> Unit,
    onRemoveNotes: (Notes) -> Unit,
    onUpdate: (Notes) -> Unit
) {
    val addButtonState = remember { mutableStateOf(false) }
    val titleState = remember { mutableStateOf("") }
    val notesState = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }
    val editingNote = remember { mutableStateOf<Notes?>(null) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            TopBar()
            if (addButtonState.value) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        modifier = modifier,
                        label = "Title",
                        value = titleState.value,
                        onValueChanged = { titleState.value = it },
                        maxLines = 1,
                        imeAction = ImeAction.Go,
                        onImeAction = { keyboardController?.hide() }
                    )
                    InputField(
                        modifier = modifier,
                        label = "Add note",
                        value = notesState.value,
                        onValueChanged = { notesState.value = it },
                        maxLines = 200,
                        imeAction = ImeAction.Default,
                        onImeAction = { keyboardController?.hide() }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier.clickable {
                            if (titleState.value.isNotEmpty() || notesState.value.isNotEmpty()) {
                                if (isEditing.value) {
                                    val updatedNote = editingNote.value!!.copy(
                                        title = titleState.value,
                                        notes = notesState.value
                                    )
                                    onUpdate(updatedNote)
                                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                                } else {
                                    onAddNotes(Notes(title = titleState.value, notes = notesState.value))
                                    Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show()
                                }

                                titleState.value = ""
                                notesState.value = ""
                                isEditing.value = false
                                editingNote.value = null
                                addButtonState.value = false
                            } else {
                                Toast.makeText(context, "Please add something to save", Toast.LENGTH_SHORT).show()
                            }
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isEditing.value) "Update" else "Save",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                }
            }else {
                Box{}
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier =Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start){
                Text(
                    text = "Saved Notes",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 20.dp, bottom = 5.dp)
                )
                if(addButtonState.value) {
                    Box(modifier = Modifier.fillMaxWidth().padding(start = 52.dp)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Hide",
                            modifier = Modifier.size(24.dp).clickable {
                                addButtonState.value = false
                            }
                        )
                    }
                }
            }
            LazyColumn {
                items(notes) { note ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topEnd = 30.dp, bottomStart = 30.dp))
                            .background(Color(176, 185, 225, 52))
                            .border(
                                width = 2.dp,
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(
                                    topEnd = 30.dp,
                                    bottomStart = 30.dp
                                )
                            )
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    addButtonState.value = true
                                    isEditing.value = true
                                    editingNote.value = note
                                    titleState.value = note.title
                                    notesState.value = note.notes
                                    Toast.makeText(context, "Editing note", Toast.LENGTH_SHORT).show()
                                }
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 10.dp)
                            ) {
                                Text(
                                    text = note.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = note.notes,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = DateFormat(
                                        note.dateTime.atZone(java.time.ZoneId.systemDefault())
                                            .toInstant().toEpochMilli()
                                    ),
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete Note",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        onRemoveNotes(note)
                                    }
                            )
                        }
                    }
                }
            }
        }

        if (!addButtonState.value) {
            NewNotes(
                modifier = Modifier
                    .padding(start = 300.dp, top = 105.dp)
                    .clickable {
                        addButtonState.value = true
                        isEditing.value = false
                        titleState.value = ""
                        notesState.value = ""
                    }
            )
        }
    }
}