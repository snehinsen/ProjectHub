package ca.tlcp.projecthub.components

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.tlcp.projecthub.components.views.EditNoteView
import ca.tlcp.projecthub.components.views.NoteDetailsView
import ca.tlcp.projecthub.components.views.ProjectDetailsView
import ca.tlcp.projecthub.components.views.ProjectsViewPage
import ca.tlcp.projecthub.components.views.TODODetailsView

@Composable
fun Navigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.projectsScreen.route
    ) {
        composable(
            route = Screen.projectsScreen.route
        ) {
            ProjectsViewPage(navController)
        }
        composable(
            route = Screen.projectDetailsScreen.route + "{projectName}",
            arguments = listOf<NamedNavArgument>(
                navArgument("projectName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val projectName = entry.arguments?.getString("projectName")
            ProjectDetailsView(navController, projectName)
        }
        composable(
            route = "${Screen.noteDatialsScreen.route}/{projectName}/{noteName}",
            arguments = listOf(
                navArgument("noteName") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("projectName") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val args = entry.arguments
            NoteDetailsView(
                projectName = args?.getString("projectName") ?: "",
                noteName = args?.getString("noteName") ?: "",
                navController = navController)
        }

        composable(
            route = "${Screen.noteEditorScreen.route}/{projectName}/{noteName}",
            arguments = listOf(
                navArgument("noteName") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("projectName") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val args = entry.arguments
            EditNoteView(
                projectName = args?.getString("projectName") ?: "",
                noteName = args?.getString("noteName") ?: "",
                navController = navController)
        }

        composable(
            route = "${Screen.todoDetailsScreen.route}/{projectName}/{todoName}",
            arguments = listOf(
                navArgument("todoName") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("projectName") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val args = entry.arguments
            TODODetailsView(
                projectName = args?.getString("projectName") ?: "",
                listName = args?.getString("todoName") ?: "",
                navController = navController)
        }

    }
}
