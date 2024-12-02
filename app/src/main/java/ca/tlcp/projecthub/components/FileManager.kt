package ca.tlcp.projecthub.components

import android.content.Context
import android.util.Log
import java.io.File

val context: Context? = Static.getAppContext()
const val ROOT_FOLDER = "projectHub"
val STORED_DATA_FOLDERS = listOf("projects")

val root: File? = context?.let { File(it.filesDir, ROOT_FOLDER) }

fun initFolderStructure() {
    if (root == null) {
        Log.e("InitFolderStructure", "Context is null. Cannot initialize folder structure.")
        return
    }

    if (!root.exists()) {
        if (!root.mkdirs()) {
            Log.e("InitFolderStructure", "Failed to create root folder at ${root.absolutePath}.")
            return
        }
    }

    STORED_DATA_FOLDERS.forEach { subFolder ->
        val folder = File(root, subFolder)
        if (!folder.exists() && !folder.mkdirs()) {
            Log.e("InitFolderStructure", "Failed to create folder: ${folder.absolutePath}.")
        }
    }
}

fun createProject(projectName: String): Boolean {
    if (root == null) {
        Log.e("CreateProject", "Context is null. Cannot create project.")
        return false
    }

    // Define the path to the project folder
    val projectDir = File(root, "projects${File.separator}$projectName")

    // Check if the project already exists
    if (projectDir.exists()) {
        Log.e("CreateProject", "Project '$projectName' already exists.")
        return false
    }

    return try {
        // Create the project directory and its subdirectories
        val notesDir = File(projectDir, "notes")
        val todosDir = File(projectDir, "TODOs")

        if (projectDir.mkdirs() && notesDir.mkdirs() && todosDir.mkdirs()) {
            Log.i("CreateProject", "Project '$projectName' created successfully.")
            true
        } else {
            Log.e("CreateProject", "Failed to create one or more directories for project '$projectName'.")
            false
        }
    } catch (e: Exception) {
        Log.e("CreateProject", "Error creating project '$projectName': ${e.message}")
        false
    }
}

fun getProjects(): List<String> {
    val rootDir = root
    if (rootDir == null) {
        Log.e("GetProjects", "Context is null. Cannot retrieve projects.")
        return emptyList()
    }

    val projectsFolder = File(rootDir, "projects")
    if (!projectsFolder.exists() || !projectsFolder.isDirectory) {
        Log.w("GetProjects", "Projects folder does not exist or is not a directory.")
        return emptyList()
    }

    return projectsFolder.listFiles()
        ?.filter { it.isDirectory }
        ?.map { it.name }
        ?: emptyList()
}

fun loadProjectNote(projectName: String, noteName: String): File? {
    if (root == null) {
        Log.e("LoadProjectData", "Context is null. Cannot load project data.")
        return null
    }

    val note = File(root, "projects${File.separator}$projectName/$noteName.md")
    return if (note.exists()) {
        return note
    } else {
        Log.e("LoadProjectNote", "Project '$projectName' does not exist.")
        return null
    }
}

fun loadProjectNotesList(projectName: String): List<String> {
    if (root == null) {
        Log.e("LoadProjectNotesList", "Context is null. Cannot load project notes.")
        return emptyList()
    }

    val noteFolder = File(root, "projects${File.separator}$projectName/notes/")
    if (!noteFolder.exists() || !noteFolder.isDirectory) {
        Log.e("LoadProjectNotesList", "Project '$projectName' does not exist or is not a directory.")
        return emptyList()
    }

    return noteFolder.listFiles()
        ?.filter { it.isFile && it.name.endsWith(".md") }
        ?.map { it.nameWithoutExtension }
        ?: emptyList()
}

fun createNote(projectName: String, noteName: String): Boolean {
    if (root == null) {
        Log.e("CreateNote", "Context is null. Cannot create note.")
        return false
    }

    val projectDir = File(root, "projects${File.separator}$projectName/notes/${noteName}.md")
    if (!projectDir.exists() || !projectDir.isDirectory) {
        Log.e("CreateNote", "Project '$projectName' does not exist or is not a directory.")
        return false
    }

    val noteFile = File(projectDir, "$noteName.md")
    return if (noteFile.exists()) {
        Log.w("CreateNote", "Note '$noteName' already exists in project '$projectName'.")
        false
    } else {
        try {
            if (noteFile.createNewFile()) {
                Log.i("CreateNote", "Note '$noteName' created successfully in project '$projectName'.")
                true
            } else {
                Log.e("CreateNote", "Failed to create note '$noteName' in project '$projectName'.")
                false
            }
        } catch (e: Exception) {
            Log.e("CreateNote", "Error creating note '$noteName': ${e.message}")
            false
        }
    }
}

fun deleteNote(projectName: String, noteName: String): Boolean {
    if (root == null) {
        Log.e("DeleteNote", "Context is null. Cannot delete note.")
        return false
    }

    val noteFile = File(root, "projects${File.separator}$projectName/notes/${noteName}.md")
    return if (noteFile.exists() && noteFile.isFile) {
        if (noteFile.delete()) {
            Log.i("DeleteNote", "Note '$noteName' deleted successfully from project '$projectName'.")
            true
        } else {
            Log.e("DeleteNote", "Failed to delete note '$noteName' from project '$projectName'.")
            false
        }
    } else {
        Log.w("DeleteNote", "Note '$noteName' does not exist in project '$projectName'.")
        false
    }
}


fun deleteProject(projectName: String): Boolean {
    val rootDir = root
    if (rootDir == null) {
        Log.e("DeleteProject", "Context is null. Cannot delete project.")
        return false
    }

    val projectDir = File(rootDir, "projects${File.separator}$projectName")
    return if (projectDir.exists() && projectDir.isDirectory) {
        if (projectDir.deleteRecursively()) {
            Log.i("DeleteProject", "Project '$projectName' deleted successfully.")
            true
        } else {
            Log.e("DeleteProject", "Failed to delete project '$projectName'.")
            false
        }
    } else {
        Log.w("DeleteProject", "Project '$projectName' does not exist.")
        false
    }
}

fun deleteAllProjects() {
    val rootDir = root
    if (rootDir == null) {
        Log.e("DeleteAllProjects", "Context is null. Cannot delete projects.")
        return
    }

    val projectsFolder = File(rootDir, "projects")
    if (!projectsFolder.exists() || !projectsFolder.isDirectory) {
        Log.w("DeleteAllProjects", "Projects folder does not exist or is not a directory.")
        return
    }

    projectsFolder.listFiles()?.forEach { file ->
        if (file.isDirectory) {
            file.deleteRecursively()
        } else {
            file.delete()
        }
    }
    Log.i("DeleteAllProjects", "All projects deleted successfully.")
}
