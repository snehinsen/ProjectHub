package ca.tlcp.projecthub.components.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.NewProjectBar
import ca.tlcp.projecthub.components.ViewComponents.Project
import ca.tlcp.projecthub.components.ViewComponents.SearchBar
import ca.tlcp.projecthub.components.deleteAllProjects
import ca.tlcp.projecthub.components.deleteProject
import ca.tlcp.projecthub.components.getProjects

@Composable
fun ProjectsViewPage(navController: NavController) {
    val projects = remember { mutableStateListOf<String>() }
    val projectListBackup = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        projects.clear()
        val fetchedProjects = getProjects()
        projects.addAll(fetchedProjects)
        projectListBackup.addAll(fetchedProjects) // Save the initial list to backup
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp)
    ) {
        SearchBar() { searchQuery: String ->
            if (searchQuery.isNotEmpty()) {
                projects.clear()
                projects.addAll(projectListBackup)
                val filteredProjects = projects.filter { it.contains(searchQuery, ignoreCase = true) }
                projects.clear()
                projects.addAll(filteredProjects)
            } else {
                projects.clear()
                projects.addAll(projectListBackup)
            }
        }

        TextButton(onClick = {
            deleteAllProjects()
            projects.clear() // Clear the list after deletion
            projectListBackup.clear() // Clear the backup list as well
        }) {
            Text(
                "Delete All",
                style = TextStyle(color = Color.White)
            )
        }

        Text(
            text = "My Projects",
            style = TextStyle(Color.White, fontSize = 24.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (projects.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            ) {
                items(projects) { project ->
                    Project(
                        title = project,
                        onDelete = {
                            projects.remove(project)
                            deleteProject(project)
                            projects.clear()
                            projects.addAll(getProjects())
                        },
                        onSelect = {
                            Log.i(Screen.projectsScreen.route + "/$project", "")
                            navController.navigate(Screen.projectDetailsScreen.route + "$project")
                        }
                    )
                }
            }
        } else {
            Column(Modifier.fillMaxHeight(0.85f)) {
                Text(
                    text = "You don't have any projects yet. Create one to get started.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }

        NewProjectBar(navController)
    }
}
