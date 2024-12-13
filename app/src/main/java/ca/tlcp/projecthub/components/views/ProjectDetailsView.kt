package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.R
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.ui.Colouring

@Composable
fun ProjectDetailsView(
    navController: NavController,
    project: String?,
    startingTab: Int = 0
) {
    var selectedTabIndex by remember { mutableIntStateOf(startingTab) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colouring.backgroundColour)
            .padding(top = 40.dp, start = 5.dp, end = 5.dp)
    ) {
        Row {
            TextButton(onClick = {
                navController.navigate(Screen.projectsScreen.route)
            }, modifier = Modifier.height(50.dp)) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                Text("Back")
            }
            Spacer(Modifier.weight(1f))

            Text(
                text = project.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(
                    Color.White,
                    20.sp
                )
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Rename $project",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete $project",
                    tint = Color.White
                )
            }

        }
        // TODO IMPLEMENT SEARCH THIS TIME AS A POPUP AND BUTTON COMBO

//        SearchBar() { searchQuery ->
//
//        }
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .background(Colouring.backgroundColour)
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Text(
                        text = "Notes",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                },

                modifier = Modifier.background(Colouring.backgroundColour),
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = {
                    Text(
                        text = "Todos",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                },

                modifier = Modifier.background(Colouring.backgroundColour),
            )
        }

        when (selectedTabIndex) {
            0 -> {
                NotesView(project.toString(), navController)
            }
            1 -> {
                TODOsView(project.toString(), navController)
            }
        }
    }
}
