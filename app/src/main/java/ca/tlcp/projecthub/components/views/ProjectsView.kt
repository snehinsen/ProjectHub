package ca.tlcp.projecthub.components.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.Item
import ca.tlcp.projecthub.components.ViewComponents.NewItemBar
import ca.tlcp.projecthub.components.ViewComponents.SearchBar
import ca.tlcp.projecthub.components.createProject
import ca.tlcp.projecthub.components.deleteProject
import ca.tlcp.projecthub.components.getProjects
import ca.tlcp.projecthub.components.renameProject

@Composable
fun ProjectsViewPage(navController: NavController) {
    val projects = remember { mutableStateListOf<String>() }
    val projectListBackup = remember { mutableStateListOf<String>() }
    var projectName by remember { mutableStateOf("") }
    var isRenaming by remember { mutableStateOf(false) }
    var currentProjectName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        projects.apply {
            clear()
            addAll(getProjects())
        }
        projectListBackup.apply {
            clear()
            addAll(getProjects())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        SearchBar { searchQuery ->
            projects.clear()
            if (searchQuery.isNotEmpty()) {
                projects.addAll(
                    projectListBackup.filter { it.contains(searchQuery, ignoreCase = true) }
                )
            } else {
                projects.addAll(projectListBackup)
            }
        }

        Text(
            text = "My Projects",
            style = TextStyle(color = Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (projects.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
                            navController.navigate(Screen.projectDetailsScreen.route + project)
                        },
                        onRename = {
                            currentProjectName = project
                            isRenaming = true
                        }
                    )
                }
            }

            if (isRenaming) {
                RenameDialog(
                    initialText = currentProjectName,
                    title = "Rename Project",
                    onConfirm = { newName ->
                        renameProject(currentProjectName, newName)
                        isRenaming = false
                        currentProjectName = ""
                        projects.apply {
                            clear()
                            addAll(getProjects())
                        }
                    },
                    onDismiss = {
                        isRenaming = false
                        currentProjectName = ""
                    }
                )
            }
        } else {
            Text(
                text = "You don't have any projects yet. Create one to get started.",
                style = TextStyle(color = Color.Gray),
                modifier = Modifier.fillMaxHeight(0.85f)
            )
        }

        NewItemBar(
            label = "Project Name",
            inputValue = projectName,
            onInputValueChange = { projectName = it },
            onCreateItem = {
                if (projectName.isNotBlank()) {
                    if (createProject(projectName)) {
                        projects.add(projectName)
                        projectListBackup.add(projectName)
                        navController.navigate(Screen.projectDetailsScreen.route + projectName)
                        projectName = ""
                    }
                }
            }
        )
    }
}
