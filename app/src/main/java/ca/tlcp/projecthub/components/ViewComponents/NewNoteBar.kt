package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.createProject
import androidx.navigation.compose.composable
import ca.tlcp.projecthub.components.createNote


@Composable
fun NewNoteBar(projectName: String, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var isAlertActive by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Note Name",
                style = TextStyle(color = Color.White)) },
            singleLine = true,
            modifier = Modifier
                .height(63.dp)
                .padding(0.dp),
            textStyle = TextStyle(color = Color.White)
        )
        IconButton(onClick = {
            if (title.isNotBlank()) {
                if (!createProject(title)) {
                    @Composable {
                        AlertDialog(
                            confirmButton = @Composable {
                                TextButton(onClick = {
                                    isAlertActive = true
                                }) {Text("OK")}
                            }, onDismissRequest = {
                                isAlertActive = false
                            }, modifier = Modifier
                        )
                    }
                }
                createNote(projectName, title)
                navController.navigate(Screen.projectDetailsScreen.route + "/$projectName")
                title = ""
            }
        }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create Project" ,
                tint = Color.White,
                modifier = Modifier.size(100.dp))
        }
    }
}
