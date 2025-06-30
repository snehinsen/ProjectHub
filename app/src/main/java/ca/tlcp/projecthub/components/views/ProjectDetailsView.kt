package ca.tlcp.projecthub.components.views

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.tlcp.projecthub.components.Screen
import ca.tlcp.projecthub.components.ViewComponents.NavItem
import ca.tlcp.projecthub.components.renameProject
import ca.tlcp.projecthub.ui.Colouring

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailsView(
    navController: NavController,
    project: String?,
    startingTab: Int = 0
) {

    val navigationItems = listOf(
        NavItem("Notes", Icons.Outlined.Notes),
        NavItem("TODOs", Icons.Outlined.Checklist)
    )

    var selectedTabIndex by remember { mutableIntStateOf(startingTab) }

    val navColouring = NavigationBarItemColors(
        selectedIconColor = Color.Green,
        selectedTextColor = Color.Green,
        selectedIndicatorColor = Colouring.cardColour,
        unselectedIconColor = Color.White,
        unselectedTextColor = Color.White,
        disabledIconColor = Color.DarkGray,
        disabledTextColor = Color.DarkGray,
    )

    var isRenamingProject by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor =
                    Colouring
                        .backgroundColour,
                contentColor =
                    Color
                        .White,
            ) {
                navigationItems
                    .forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            label = { Text(item.label) },
                            colors = navColouring,
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                )
                            },
                            modifier = Modifier,
                        )
                    }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colouring.backgroundColour)
                .padding(horizontal = 5.dp)
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
                        isRenamingProject = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Rename $project",
                        tint = Color.White
                    )
                }
            }
            // TODO IMPLEMENT SEARCH THIS TIME AS A POPUP AND BUTTON COMBO

//        SearchBar() { searchQuery ->
//
//        }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.87f)
            ) {
                when (selectedTabIndex) {
                    0 -> {
                        NotesView(project.toString(), navController)
                    }

                    1 -> {
                        TODOsView(project.toString())
                    }
                }
            }
            if (isRenamingProject) {
                UpdateNameDialog(
                    initialText = project.toString(),
                    title = "Rename Project",
                    onConfirm = { newName ->
                        isRenamingProject = false
                        renameProject(project.toString(), newName)

                        navController
                            .navigate(
                                Screen
                                    .projectDetailsScreen
                                    .route
                                        + "/$newName/$selectedTabIndex"

                            )
                    },
                    onDismiss = {
                        isRenamingProject = false
                    },
                    onAbortLabel = "Cancel"
                ) {
                    isRenamingProject = false
                }
            }
        }
    }
}