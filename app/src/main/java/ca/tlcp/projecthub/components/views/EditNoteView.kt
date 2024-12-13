package ca.tlcp.projecthub.components.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.deleteNote
import ca.tlcp.projecthub.components.loadNote
import ca.tlcp.projecthub.components.renameNote
import ca.tlcp.projecthub.components.saveNote
import ca.tlcp.projecthub.ui.Colouring
import ca.tlcp.projecthub.ui.formatTitle
import com.colintheshots.twain.MarkdownEditor

@Composable
fun EditNoteView(
    projectName: String,
    noteName: String,
    needsRename: Boolean? = false,
    navController: NavController
) {
    var noteBody by remember { mutableStateOf(loadNote(projectName,noteName)) }
    var isRenaming by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colouring.backgroundColour)
            .padding(top = 40.dp, start = 10.dp, end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Text(
                text = noteName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    if (needsRename == true) {
                        isRenaming = true
                    } else {
                        saveNote(projectName, noteName, noteBody.toString())
                        navController.navigate(Screen.noteDatialsScreen.route + "/$projectName/$noteName")
                    }
                },
                modifier = Modifier.wrapContentSize()
            ) {
                Text("Done", color = Color.White, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MarkdownEditor(
            value = noteBody ?: "",
            onValueChange = { newText ->
                noteBody = newText
                if (saveNote(projectName,noteName, newText)) {
                    Log.i("SaveNote", "Note '$noteName' saved successfully.")
                } else {
                    Log.e("SaveNote", "Failed to save the note.")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Colouring.notesColour)
        )
    }
    if (isRenaming) {
        RenameDialog(
            noteName, title = "Note Name",
            onConfirm = { newName ->
                if (newName != noteName) {
                    val success = renameNote(projectName, noteName, newName)
                    if (success) {
                        saveNote(projectName, noteName, noteBody.toString())
                        deleteNote(projectName, noteName)// redundant check
                        navController.navigate(Screen.noteDatialsScreen.route + "/$projectName/$newName")
                    }
                }
            },
            onDismiss = {isRenaming = false}
        )
    }
}
