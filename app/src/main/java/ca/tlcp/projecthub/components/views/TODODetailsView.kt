package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import ca.tlcp.projecthub.components.ViewComponents.Item
import ca.tlcp.projecthub.components.ViewComponents.NewItemBar
import ca.tlcp.projecthub.components.loadProjectTODOsList
import ca.tlcp.projecthub.components.loadTODO
import ca.tlcp.projecthub.components.saveTODO

@Composable
fun TODODetailsView(
    projectName: String,
    listName: String,
    navController: NavController
) {
    val todoList = remember { mutableStateListOf<String>() }
    var newItemName by remember { mutableStateOf("") }
    var isRenaming by remember { mutableStateOf(false) }
    var currentItemName by remember { mutableStateOf("") }

    LaunchedEffect(projectName, listName) {
        todoList.clear()
        todoList.addAll(
            loadTODO(projectName, listName)
                ?.split("\n")
                ?.filter { it.isNotBlank() } ?: emptyList()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 50.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(Color.DarkGray),
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
                text = listName,
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )

            Spacer(modifier = Modifier.weight(2f))
        }

        Text(
            text = "Tasks",
            style = TextStyle(Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (todoList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                items(todoList) { item ->
                    Item(
                        title = item,
                        onDelete = {
                            todoList.remove(item)
                            saveTODO(projectName, listName, formatBody(todoList))
                        },
                        onSelect = { /* No action on select */ },
                        onRename = {
                            currentItemName = item
                            isRenaming = true
                        }
                    )
                }
            }

            // Rename Dialog
            if (isRenaming) {
                RenameDialog(
                    initialText = currentItemName,
                    title = "Rename Task",
                    onConfirm = { newName ->
                        val index = todoList.indexOf(currentItemName)
                        if (index != -1) {
                            todoList[index] = newName
                            saveTODO(projectName, listName, formatBody(todoList))
                        }
                        isRenaming = false
                        currentItemName = ""
                    },
                    onDismiss = {
                        isRenaming = false
                        currentItemName = ""
                    }
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = "You don't have any tasks yet. Create one to get started.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }

        // New Item Bar
        NewItemBar(
            label = "TODO Name",
            inputValue = newItemName,
            onInputValueChange = { newItemName = it },
            onCreateItem = {
                if (newItemName.isNotBlank()) {
                    todoList.add(newItemName)
                    saveTODO(projectName, listName, formatBody(todoList))
                    newItemName = ""
                }
            }
        )
    }
}

// TodoLIst formatting function
private fun formatBody(items: List<String>): String { return items.joinToString("\n") }
