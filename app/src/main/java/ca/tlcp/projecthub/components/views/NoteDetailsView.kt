package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import ca.tlcp.projecthub.components.loadNote
import com.colintheshots.twain.MarkdownText

@Composable
fun NoteDetailsView(
    projectName: String,
    noteName: String,
    navController: NavController
) {
    val noteBody = loadNote(projectName,noteName).toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { navController.navigate(Screen.projectDetailsScreen.route + projectName) },
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Back", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = noteName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = {
                    navController.navigate(Screen.noteEditorScreen.route + "/$projectName/$noteName")
                },
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

        }
        MarkdownText(
            markdown = noteBody,
            style = TextStyle(Color.White),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
        )
    }

}
