package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.NoteEditor
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun NoteDetailsView(noteName: String?, navController: NavController) {
    var isEditorOpen by remember { mutableStateOf(false) }
    var noteBody by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize()
        .padding(top = 50.dp)) {
        Row {
            Row {
                TextButton(onClick = {
                    navController.navigate(Screen.projectsScreen.route)
                }, modifier = Modifier.height(50.dp)) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    Text("Back")
                }
            }
            Text(noteName.toString(),
                style = TextStyle(
                    Color.White,
                    fontSize = 30.sp
                ))
        }

        NoteEditor(
            isEditorOpen, title = noteName.toString(),
            onDismiss = {
                isEditorOpen = false
            }, onChange = { body: String ->
                noteBody = body
            }
        )

        MarkdownText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            markdown = noteBody,
            maxLines = 3,
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 10.sp,
                textAlign = TextAlign.Justify,
            ))
    }
}