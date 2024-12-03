package com.suvodeep.tasktracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.suvodeep.tasktracker.ui.theme.TaskTrackerTheme
import com.suvodeep.tasktracker.viewModel.AppViewModel
import com.suvodeep.tasktracker.viewModel.FlashScreenWithTimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskTrackerTheme {
                val appViewModel: AppViewModel = viewModel()
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = "flashScreen") {
                        composable("flashScreen") {
                            FlashScreenWithTimer(
                                onFlashScreenFinished = {
                                    navController.navigate("taskTracker")
                                }
                            )
                        }
                        composable("taskTracker") {
                            TaskTracker(
                                appViewModel = appViewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TaskTracker(appViewModel: AppViewModel, modifier: Modifier = Modifier) {
    val notes = appViewModel.noteList.collectAsState().value
    val context = LocalContext.current

    TaskTrackerApp(
        modifier = modifier,
        notes = notes,
        onRemoveNotes = {
            appViewModel.removeNote(it)
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        },
        onAddNotes = {
            appViewModel.addNote(it)
            Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
        },
        onUpdate = {
            appViewModel.updateNote(it)
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
        }
    )
}
