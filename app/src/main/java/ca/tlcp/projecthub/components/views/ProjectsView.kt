package ca.tlcp.projecthub.components.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.Item
import ca.tlcp.projecthub.components.ViewComponents.NewItemCreator
import ca.tlcp.projecthub.components.ViewComponents.SearchBar
import ca.tlcp.projecthub.components.*
import ca.tlcp.projecthub.ui.Colouring

@Composable
fun ProjectsViewPage(navController: NavController) {
    val projects = remember { mutableStateListOf<String>() }
    val projectListBackup = remember { mutableStateListOf<String>() }
    var projectName by remember { mutableStateOf("") }
    var isRenaming by remember { mutableStateOf(false) }
    var currentProjectName by remember { mutableStateOf("") }
    var todoName by remember {
        mutableStateOf("")
    }
    var creatingTask by remember { mutableStateOf(false) }

    val prefs = Static.getSharedPreferences()

    val isFirstRun = prefs?.getBoolean("first_run", true)

    LaunchedEffect(Unit) {
        projects.clear()
        projects.addAll(getProjects())
        projectListBackup.clear()
        projectListBackup.addAll(getProjects())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Colouring.backgroundColour)
            .padding(
                top = 40.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 2.dp
            )
    ) {
        Text(
            text = "My Projects",
            style = TextStyle(color = Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (isFirstRun == false) {
            if (projects.isNotEmpty()) {
                SearchBar { searchQuery ->
                    projects.clear()
                    projects.addAll(
                        if (searchQuery.isNotEmpty()) {
                            projectListBackup.filter {
                                it
                                    .contains(
                                        searchQuery, ignoreCase = true
                                    )
                            }
                        } else {
                            projectListBackup
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    items(projects) { project ->
                        Item(
                            title = project,
                            onDelete = {
                                deleteProject(project)
                                projects.clear()
                                projects.addAll(getProjects())
                            },
                            onSelect = {
                                Log.i(Screen.projectsScreen.route, project)
                                navController
                                    .navigate(
                                        Screen
                                            .projectDetailsScreen
                                            .route + project)
                            },
                            onRename = {
                                currentProjectName = project
                                isRenaming = true
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(0.5f)
                            ) {
                                Column {
                                    Text(
                                        text = "${loadProjectNotesList(project).size}",
                                        style = TextStyle(color = Color.White, fontSize = 20.sp)
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = "Notes",
                                        style =
                                            TextStyle(
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                    )
                                }
                                Spacer(
                                    Modifier
                                        .weight(1f)
                                )
                                Column() {
                                    Text(
                                        text = "${loadProjectTODOList(project).size}",
                                        style = TextStyle(color = Color.White, fontSize = 20.sp)
                                    )

                                    Spacer(Modifier.height(2.dp))

                                    Text(
                                        text = "TODOs",
                                        style =
                                            TextStyle(
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                    )
                                }
                                Spacer(
                                    Modifier
                                        .weight(2f)
                                )
                            }
                        }
                    }
                }

            }
            else {
                Text(
                    text = "You don't have any projects yet, create one to get started.",
                    style = TextStyle(
                        color = Color.White
                    )
                )
                Spacer(
                    Modifier.fillMaxHeight(0.88f)
                )
            }

            NewItemCreator(
                label = "Project Name",
                inputValue = projectName,
                onInputValueChange = { projectName = it },
                title = "Create Project",
                onCreateItem = {
                    if (projectName.isNotBlank()) {
                        if (createProject(projectName)) {
                            projects.add(projectName)
                            projectListBackup.add(projectName)
                            navController.navigate(Screen.projectDetailsScreen.route + projectName)
                            projectName = ""
                        }
                    }
                }, useDialog = true,
                onCreateItemNoDialog = {}
            )

            if (isRenaming) {
                RenameDialog(
                    initialText = currentProjectName,
                    title = "Rename Project",
                    onConfirm = { newName ->
                        renameProject(currentProjectName, newName)
                        isRenaming = false
                        currentProjectName = ""
                        projects.clear()
                        projects.addAll(getProjects())
                    },
                    onDismiss = {
                        isRenaming = false
                        currentProjectName = ""
                    }
                )
            }
        } else {
            // First time use experience
            val defaultNoteName = "Untitled Note"
            val defaultProjectName = "Untitled Project"
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
                verticalArrangement =
                    Arrangement.Center,
                horizontalAlignment =
                    Alignment
                        .CenterHorizontally
            ) {
                TextButton(
                    onClick = {
                        if (createProject(defaultProjectName)) {
                            if (createNote(
                                    defaultProjectName,
                                    defaultNoteName
                                )
                            ) {
                                navController.navigate(
                                    Screen
                                        .noteEditorScreen
                                        .route
                                            + "/$defaultProjectName/$defaultNoteName/true"
                                )
                            }
                        }
                    },
                    modifier =
                        Modifier
                            .background(
                                Colouring
                                    .cardColour
                            )
                            .border(
                                width = 0.dp,
                                color = Colouring.cardColour,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .fillMaxWidth(0.5f)
                    ,
                    shape =
                        RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "Create Note",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
                Spacer(
                    Modifier.height(10.dp)
                )
                TextButton(
                    onClick = {
                        if (createProject(defaultProjectName)) {
                            creatingTask = true
                        }
                    },
                    modifier =
                        Modifier
                            .background(
                                Colouring
                                    .cardColour
                            )
                            .border(
                                width = 0.dp,
                                color = Colouring.cardColour,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .fillMaxWidth(0.5f),
                    shape =
                        RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "Create ToDo",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }

            if (creatingTask == true) {
                Dialog(onDismissRequest = { creatingTask = false }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.DarkGray, MaterialTheme.shapes.medium)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "What do you want TODO?",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = MaterialTheme
                                        .typography
                                        .titleLarge
                                        .fontSize
                                )
                            )
                            OutlinedTextField(
                                value = todoName,
                                onValueChange = { newValue -> todoName = newValue },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(color = Color.White),
                                singleLine = true,
                                label = { Text("Task", color = Color.White) }
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        todoName = ""
                                        creatingTask = false
                                    }
                                ) {
                                    Text("Cancel", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        if (
                                            saveTODO(
                                                defaultProjectName,
                                                todoName
                                            )
                                        ) {
                                            prefs?.edit()?.putBoolean("first_run", false)?.apply()
                                            creatingTask = false
                                            todoName = ""
                                        }
                                        navController
                                            .navigate(
                                                Screen
                                                    .projectDetailsScreen
                                                    .route + "$defaultProjectName/1"
                                            )
                                    }
                                ) {
                                    Text("Add task", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
