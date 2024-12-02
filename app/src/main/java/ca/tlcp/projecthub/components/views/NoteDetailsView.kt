package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
    var noteBody by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { navController.navigate(Screen.projectsScreen.route) },
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Back", color = Color.White, fontSize = 14.sp)
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
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {navController.navigate(Screen.projectsScreen.route)},
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Note",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Edit", color = Color.White, fontSize = 14.sp)
            }
        }

        MarkdownText(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxSize(),
            markdown = noteBody,
            style = TextStyle(
                color = Color.White,
                lineHeight = 16.sp,
                fontSize = 14.sp
            )
        )
    }
}
