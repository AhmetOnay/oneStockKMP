package screens.details.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.*
import viewmodels.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun NoteTab(stockDetailViewModel: StockDetailViewModel) {
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val notes by stockDetailViewModel.notes.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Add a note") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val noteText = text
                if (!noteText.isNullOrBlank()) {
                    scope.launch {
                        stockDetailViewModel.addNewNote(noteText)
                    }
                }
                text = ""
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Add Note")
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(notes) { note ->
                NoteItem(note = note) { noteId ->
                    scope.launch {
                        stockDetailViewModel.deleteNote(noteId)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDeleteClick: (Long) -> Unit) {
   val dateText = formatTimestamp(note.timestamp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${note.text}\n$dateText",
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onDeleteClick(note.id) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colors.error
            )
        }
    }
}

fun formatTimestamp(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    
    // Formatting pattern: "yyyy-MM-dd HH:mm:ss" - manually formatting it as kotlinx-datetime does not support direct pattern formatting
    return buildString {
        append(localDateTime.year.toString().padStart(4, '0'))
        append('-')
        append(localDateTime.monthNumber.toString().padStart(2, '0'))
        append('-')
        append(localDateTime.dayOfMonth.toString().padStart(2, '0'))
        append(' ')
        append(localDateTime.hour.toString().padStart(2, '0'))
        append(':')
        append(localDateTime.minute.toString().padStart(2, '0'))
        append(':')
        append(localDateTime.second.toString().padStart(2, '0'))
    }
}