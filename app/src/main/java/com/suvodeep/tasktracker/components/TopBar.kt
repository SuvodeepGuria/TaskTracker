package com.suvodeep.tasktracker.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suvodeep.tasktracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    remember { mutableIntStateOf(25) }
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Bold,
            color = Color.Black)
    },
        actions = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Notifications",
                modifier = Modifier.padding(end = 20.dp).size(25.dp).clickable {  },
                tint = Color.Black)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(Color(0xF3C2A0E8))
        )
}