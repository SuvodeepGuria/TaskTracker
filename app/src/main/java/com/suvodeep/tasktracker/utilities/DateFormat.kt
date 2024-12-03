package com.suvodeep.tasktracker.utilities

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DateFormat(time: Long): String{
    val date= Date(time)
    val format= SimpleDateFormat("EEE,dd-MM-yyyy . hh:mm a",Locale.getDefault())
    return format.format(date)
}