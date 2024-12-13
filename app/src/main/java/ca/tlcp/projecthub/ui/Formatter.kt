package ca.tlcp.projecthub.ui

fun formatTitle(title: String, max: Int = 14): String {
    return if (title.length > max) {
        "${title.substring(0,max)}..."
    } else {
        title
    }
}