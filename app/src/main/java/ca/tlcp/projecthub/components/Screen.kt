package ca.tlcp.projecthub.components

sealed class Screen(val route: String) {
    object projectsScreen: Screen("projects")
    object projectDetailsScreen: Screen("pdetails")
    object noteDatialsScreen: Screen("ndetails")
    object noteEditorScreen: Screen("noteEditor")
}