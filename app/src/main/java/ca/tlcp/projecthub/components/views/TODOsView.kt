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
import ca.tlcp.projecthub.components.ViewComponents.NewItemBar
import ca.tlcp.projecthub.components.createTODO
import ca.tlcp.projecthub.components.deleteTODO
import ca.tlcp.projecthub.components.loadProjectTODOsList
import ca.tlcp.projecthub.components.renameNote


@Composable
fun TODOsView(
    projectName: String,
    navController: NavController
) {
    val todosList = remember {
        mutableStateListOf<String>()
    }

    var newTODOName by remember {
        mutableStateOf("")
    }

    var isRenaming by remember { mutableStateOf(false) }
    var currentTODOName by remember { mutableStateOf("") }

    LaunchedEffect(projectName) {
        todosList.clear()
        todosList.addAll(loadProjectTODOsList(projectName))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "TODOs",
            style = TextStyle(Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        if (todosList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(todosList) { list ->
                    Item(
                        title = list,
                        onDelete = {
                            deleteTODO(projectName, list)
                            todosList.clear()
                            todosList.addAll(loadProjectTODOsList(projectName))
                        },
                        onSelect = {
                            navController.navigate(
                                Screen.todoDetailsScreen.route + "/$projectName/$list"
                            )

                        },
                        onRename = {
                            currentTODOName = list
                            isRenaming = true
                        }
                    )
                }
            }
            if (isRenaming) {
                RenameDialog(
                    initialText = currentTODOName,
                    title = "Rename Project",
                    onConfirm = { newName ->
                        renameNote(projectName, currentTODOName, newName)
                        isRenaming = false
                        currentTODOName = ""
                        todosList.apply {
                            clear()
                            addAll(loadProjectTODOsList(projectName))
                        }
                    },
                    onDismiss = {
                        isRenaming = false
                        currentTODOName = ""
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
        NewItemBar(
            label = "TODO Name",
            inputValue = newTODOName,
            onInputValueChange = { newValue ->
                newTODOName = newValue
            }, onCreateItem = {
                val success = createTODO(projectName, newTODOName)
                if (success) {
//                    navController.navigate(Screen.noteEditorScreen.route + "/$projectName/$newTODOName")
                    todosList.apply {
                        clear()
                        addAll(loadProjectTODOsList(projectName))
                    }
                    newTODOName = "" // in case of crash
                }
            })
    }
}
