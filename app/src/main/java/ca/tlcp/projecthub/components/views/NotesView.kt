package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.Item
import ca.tlcp.projecthub.components.ViewComponents.NewItemCreator
import ca.tlcp.projecthub.components.createNote
import ca.tlcp.projecthub.components.deleteNote
import ca.tlcp.projecthub.components.loadProjectNotesList
import ca.tlcp.projecthub.components.renameNote
import ca.tlcp.projecthub.ui.Colouring

@Composable
fun NotesView(projectName: String, navController: NavController) {
    var name by remember { mutableStateOf("") }
    val notesList = remember {
        mutableStateListOf<String>()
    }

    var isRenaming by remember { mutableStateOf(false) }
    var currentNoteName by remember { mutableStateOf("") }
    var requiresRename = false
    LaunchedEffect(projectName) {
        notesList.clear()
        notesList.addAll(loadProjectNotesList(projectName))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colouring.backgroundColour)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp)
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
                    .fillMaxHeight(0.8f)
            ) {
                items(notesList) { note ->
                    Item(
                        title = note,
                        onDelete = {
                            deleteNote(projectName, note    )
                            notesList.clear()
                            notesList.addAll(loadProjectNotesList(projectName))
                        },
                        onSelect = {
                            navController.navigate(
                                Screen.noteDatialsScreen.route + "/$projectName/$note"
                            )
                        },
                        onRename = {
                            currentNoteName = note
                            isRenaming = true
                        }
                    )
                }
            }
            if (isRenaming) {
                RenameDialog(
                    initialText = currentNoteName,
                    title = "Rename Project",
                    onConfirm = { newName ->
                        renameNote(projectName, currentNoteName, newName)
                        isRenaming = false
                        currentNoteName = ""
                        notesList.apply {
                            clear()
                            addAll(loadProjectNotesList(projectName))
                        }
                    },
                    onDismiss = {
                        isRenaming = false
                        currentNoteName = ""
                    }
                )
            }
        } else {
            Column(Modifier.fillMaxHeight(0.8f)) {
                Text(
                    text = "You don't have any Notes yet. Create one to get started.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }
        NewItemCreator(
            label = "Note Name",
            inputValue = name,
            onInputValueChange = { newValue ->
                name = newValue
        }, onCreateItem = {

        }, useDialog = false,
            onCreateItemNoDialog = {
                name = "Untitled Note"
                val success = createNote(projectName, name)
                if (success) {
                    navController.navigate(Screen.noteEditorScreen.route + "/$projectName/$name/true")
                }
            },
        )
    }
}
