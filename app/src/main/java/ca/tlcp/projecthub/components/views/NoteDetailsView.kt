package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    var noteBody by remember { mutableStateOf("\n" +
            "### **Important Links**\n" +
            "- [Markdown Syntax Guide](https://www.markdownguide.org/basic-syntax/)  \n" +
            "- [ProjectHub GitHub Repository](https://github.com/snehinsen/ProjectHub)  \n" +
            "- [API Documentation](https://api.projecthub.com/docs)  \n") }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { navController.navigate(Screen.projectsScreen.route) },
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
                Text("Back")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = noteName ?: "",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        NoteEditor(
            isEditorOpen,
            title = noteName ?: "Untitled",
            onDismiss = { isEditorOpen = false },
            onChange = { body: String -> noteBody = body }
        )

        MarkdownText(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            markdown = noteBody,
            style = TextStyle(
                color = Color.White,
                lineHeight = 18.sp
            )
        )
    }
}
