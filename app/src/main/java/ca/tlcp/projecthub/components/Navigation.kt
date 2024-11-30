package ca.tlcp.projecthub.components

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.tlcp.projecthub.components.views.NoteDetailsView
import ca.tlcp.projecthub.components.views.ProjectDetailsView
import ca.tlcp.projecthub.components.views.ProjectsViewPage

@Composable
fun Navigator() {
    val navController = rememberNavController();
    NavHost(
        navController = navController,
        startDestination = Screen.projectsScreen.route) {
        composable(
            route = Screen.projectsScreen.route
        ) {
            ProjectsViewPage(navController);
        }
        composable(
            route = Screen.projectDetailsScreen.route
        ) {
            ProjectDetailsView(navController, "")
        }
        composable(
            route = Screen.noteDatialsScreen.route,
            arguments = listOf<NamedNavArgument>()
        ) {
            NoteDetailsView(navController)
        }
    }

}