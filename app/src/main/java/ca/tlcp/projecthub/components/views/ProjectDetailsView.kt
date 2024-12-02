package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.SearchBar

@Composable
fun ProjectDetailsView(navController: NavController, project: String?) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Notes", "TODOs")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
    ) {
        Row {
            TextButton(onClick = {
                navController.navigate(Screen.projectsScreen.route)
            }, modifier = Modifier.height(50.dp)) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                Text("Back")
            }

        }
        SearchBar() { searchQuary ->

        }
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .background(Color.DarkGray)
        ) {
            for (index in 0 until tabs.size) {
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(tabs[index], style = TextStyle(color = Color.White)) },
                    modifier = Modifier.background(Color.DarkGray),
                )
            }

        }

        when (selectedTabIndex) {
            0 -> {
                NotesView(project.toString(), navController)
            }
            1 -> {
                TodoView()
            }
        }
    }
}
