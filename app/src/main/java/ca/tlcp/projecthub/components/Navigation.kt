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
            route = Screen.projectDetailsScreen.route + "/{projectName}/{tab}",
            arguments = listOf<NamedNavArgument>(
                navArgument("projectName") {
                    type = NavType.StringType
                },
                navArgument("tab") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { entry ->
            val args = entry.arguments
            ProjectDetailsView(
                navController,
                args?.getString("projectName"),
                args?.getInt("tab")?.toInt() ?: 0
            )
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
            route = "${Screen.noteEditorScreen.route}/{projectName}/{noteName}/{needsRename}",
            arguments = listOf(
                navArgument("noteName") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("projectName") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("needsRename") {
                    type = NavType.BoolType
                    nullable = false
                    defaultValue = false
                }
            )
        ) { entry ->
            val args = entry.arguments
            EditNoteView(
                projectName = args?.getString("projectName") ?: "",
                noteName = args?.getString("noteName") ?: "",
                needsRename = args?.getBoolean("needsRename"),
                navController = navController)
        }
    }
}
