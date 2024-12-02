package ca.tlcp.projecthub.components

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.tlcp.projecthub.components.ViewComponents.NoteEditor
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
            route = "${Screen.noteDatialsScreen.route}/{noteName}",
            arguments = listOf(
                navArgument("noteName") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { entry ->
            val noteName = entry.arguments?.getString("noteName")
            NoteDetailsView(noteName, navController)
        }
        composable(
            route = Screen.noteDatialsScreen.route + "/{noteName}/{noteBody}",
            arguments = listOf(
                navArgument("noteName") {
                    type = NavType.StringType
                },
                navArgument("noteBody") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val noteName = entry.arguments?.getString("noteName") ?: "Untitled"
            val noteBody = entry.arguments?.getString("noteBody") ?: ""
            NoteEditor(
                title = noteName,
                body = noteBody,
                navController = navController
            )
        }
    }
}
