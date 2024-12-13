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
import ca.tlcp.projecthub.components.ViewComponents.Item
import ca.tlcp.projecthub.components.ViewComponents.NewItemCreator
import ca.tlcp.projecthub.components.loadProjectTODOList
import ca.tlcp.projecthub.components.saveTODO
import ca.tlcp.projecthub.ui.Colouring


@Composable
fun TODOsView(
    projectName: String,
    navController: NavController
) {
    val todoList = remember {
        mutableStateListOf<String>()
    }

    var task by remember {
        mutableStateOf("")
    }

    var isRenaming by remember { mutableStateOf(false) }
    var currentTODOName by remember { mutableStateOf("") }

    LaunchedEffect(projectName) {
        todoList.clear()
        todoList.addAll(loadProjectTODOList(projectName))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colouring.backgroundColour)
            .padding(16.dp)
    ) {
        Text(
            text = "TODOs",
            style = TextStyle(Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        if (todoList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(todoList) { task ->
                    Item(
                        title = task,
                        onDelete = {
                            todoList.remove(task)
                            saveTODO(projectName, todosStringify(todoList))
                            todoList.clear()
                            todoList.addAll(loadProjectTODOList(projectName))
                        },
                        onSelect = {

                        },
                        onRename = {
                            currentTODOName = task
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
                        isRenaming = false
                        todoList.apply {
                            remove(currentTODOName)
                            add(newName)
                            saveTODO(projectName, todosStringify(todoList))
                            clear()
                            addAll(loadProjectTODOList(projectName))
                        }
                        currentTODOName = ""
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
        NewItemCreator(
            label = "Task Name",
            inputValue = task,
            title = "What do you need TODO?",
            onInputValueChange = { newValue ->
                task = newValue
            }, onCreateItem = {
                todoList.add(task)
                val success = saveTODO(projectName, todosStringify(todoList))
                if (success) {
                    todoList.apply {
                        clear()
                        addAll(loadProjectTODOList(projectName))
                    }
                    task = "" // in case of crash
                }
            }, useDialog = true,
            onCreateItemNoDialog = {})
    }
}

private fun todosStringify(tasks: List<String>): String {
    var todosString = ""
    for (task in tasks) {
        todosString += "$task\n"
    }
    return todosString
}