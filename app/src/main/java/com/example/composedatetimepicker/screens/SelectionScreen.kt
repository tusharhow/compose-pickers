package com.example.composedatetimepicker.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.emoji.EmojiDialog
import com.maxkeppeler.sheets.emoji.models.EmojiCategoryAppearance
import com.maxkeppeler.sheets.emoji.models.EmojiConfig
import com.maxkeppeler.sheets.emoji.models.EmojiSelection
import java.time.LocalDate
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectionScreen() {

    val calState = rememberSheetState()
    val clockState = rememberSheetState()
    val emojiState = rememberSheetState()

    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val selectedEmoji = remember { mutableStateOf<String?>(null) }

    CalendarDialog(state = calState, selection = CalendarSelection.Date { date ->
        selectedDate.value = date
        Log.d("TAG", "SelectionScreen: ${selectedTime.value}")
    })

    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true,
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedTime.value = LocalTime.of(hours, minutes)
            Log.d("TAG", "Selected time: $hours:$minutes")
        })

    EmojiDialog(
        state = emojiState, config = EmojiConfig(
            categoryAppearance = EmojiCategoryAppearance.TEXT,
        ),
        selection = EmojiSelection.Unicode(
            withButtonView = false,
            onPositiveClick = { emoji ->
                Log.d("TAG", "Selected emoji: $emoji")
                selectedEmoji.value = emoji
            }
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = selectedDate.value?.toString() ?: "No date selected")
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = selectedTime.value?.toString() ?: "No time selected")
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = selectedEmoji.value ?: "No emoji selected")
        Spacer(modifier = Modifier.size(50.dp))
        Button(onClick = { calState.show() }) {
            Text(text = "Select Date")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = { clockState.show() }) {
            Text(text = "Select Time")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = { emojiState.show() }) {
            Text(text = "Select Emoji")
        }
    }
}



