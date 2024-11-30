package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.ViewComponents.Note
import ca.tlcp.projecthub.components.ViewComponents.NoteEditor

@Composable
fun NotesView(navController: NavController) {
    val notesList = remember { mutableStateListOf<String>("Untitled Note") }
    var currentNoteName by remember { mutableStateOf("")}
    var isEditorOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top=30.dp ,start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Notes",
            style = TextStyle(Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        if (notesList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(notesList) { note ->
                    Note(title = note, onDelete = { notesList.remove(note)}, onSelect = {
                        currentNoteName = note
                        isEditorOpen = true
                    })
                }

            }
            NoteEditor(
                isEditorOpen, title = currentNoteName,
                onDismiss = {
                    isEditorOpen = false
                }
            )
        } else {
            Column(Modifier.fillMaxHeight(0.9f)) {
                Text(text = "You don't have any Notes yet. Create one to get started.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }
    }
}