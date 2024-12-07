package ca.tlcp.projecthub.components

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

val context: Context? = Static.getAppContext()
const val ROOT_FOLDER = "projectHub"
val STORED_DATA_FOLDERS = listOf("projects")

val root: File? = context?.let { File(it.filesDir, ROOT_FOLDER) }
// Setup on app launch
fun initFolderStructure() {
    if (root == null) {
        Log.e("InitFolderStructure", "Context is null. Cannot initialize folder structure.")
        return
    }

    if (!root.exists() && !root.mkdirs()) {
        Log.e("InitFolderStructure", "Failed to create root folder at ${root.absolutePath}.")
        return
    }

    STORED_DATA_FOLDERS.forEach { subFolder ->
        val folder = File(root, subFolder)
        if (!folder.exists() && !folder.mkdirs()) {
            Log.e("InitFolderStructure", "Failed to create folder: ${folder.absolutePath}.")
        }
    }
}

// Path verification
private fun validateProjectPath(projectName: String): File? {
    val projectDir = File(root, "projects/$projectName")
    return if (projectDir.canonicalPath.startsWith(File(root, "projects").canonicalPath)) {
        projectDir
    } else {
        Log.e("ValidatePath", "Invalid project path: $projectName")
        null
    }
}

// PROJECT FUNCTIONS
fun createProject(projectName: String): Boolean {
    if (root == null) {
        Log.e("CreateProject", "Context is null. Cannot create project.")
        return false
    }

    val projectDir = validateProjectPath(projectName) ?: return false
    if (projectDir.exists()) {
        Log.e("CreateProject", "Project '$projectName' already exists.")
        return false
    }

    return try {
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

fun renameProject(projectName: String, newName: String): Boolean {
    if (root == null) {
        Log.e("RenameProject", "Context is null. Cannot create project.")
        return false
    }

    val projectDir = validateProjectPath(projectName) ?: return false
    val newProjectDir = File(root, "projects/$newName"
    )
    if (!projectDir.exists() || newProjectDir.exists()) {
        Log.e("RenameProject", "Project '$projectName' can't be renamed to $newName.")
        return false
    }

    return try {

        if (projectDir.renameTo(newProjectDir)) {
            Log.i("RenameProject", "Project '$projectName' renamed to $newName successfully.")
            true
        } else {
            Log.e("RenameProject", "Failed to rename one or more directories for project '$projectName'.")
            false
        }
    } catch (e: Exception) {
        Log.e("CreateProject", "Error creating project '$projectName': ${e.message}")
        false
    }
}


fun getProjects(): List<String> {
    if (root == null) {
        Log.e("GetProjects", "Context is null. Cannot retrieve projects.")
        return emptyList()
    }

    val projectsFolder = File(root, "projects")
    if (!projectsFolder.exists() || !projectsFolder.isDirectory) {
        Log.w("GetProjects", "Projects folder does not exist or is not a directory.")
        return emptyList()
    }

    Log.i("", projectsFolder.listFiles().toString())

    return projectsFolder.listFiles()
        ?.filter { it.isDirectory && File(it, "notes").exists() && File(it, "notes").isDirectory }
        ?.map { it.name }
        ?: emptyList()
}

fun deleteProject(projectName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
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

// NOTES FUNCTIONS
fun createNote(projectName: String, noteName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val noteFolder = File(projectDir, "notes")

    if (!noteFolder.exists() || !noteFolder.isDirectory) {
        Log.e("CreateNote", "Notes folder for project '$projectName' does not exist.")
        return false
    }

    val noteFile = File(noteFolder, "${noteName}.md")
    return if (noteFile.exists()) {
        Log.w("CreateNote", "Note '$noteName' already exists in project '$projectName'.")
        false
    } else {
        try {
            if (noteFile.createNewFile()) {
                Log.i("CreateNote", "Note '$noteName' created successfully in project '$projectName'.")
                Log.i("CreateNote", "Note '$noteName' in path '${noteFile.absolutePath}'.")
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

fun renameNote(projectName: String, noteName: String, newNoteName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val noteFolder = File(projectDir, "notes")

    if (!noteFolder.exists() || !noteFolder.isDirectory) {
        Log.e("RenameNote", "Notes folder for project '$projectName' does not exist.")
        return false
    }

    val noteFile = File(noteFolder, "${noteName}.md")
    val newNoteFile = File(noteFolder, "${newNoteName}.md")
    return if (noteFile.renameTo(newNoteFile)) {
        Log.w("RenameNote", "Note '$noteName' renamed to $newNoteName successfully.")
        true
    } else {
        false
    }
}

fun saveNote(projectName: String, noteName: String, noteBody: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val noteFile = File(projectDir, "notes/$noteName.md")

    return try {
        val notesDir = File(projectDir, "notes")
        if (!notesDir.exists()) {
            notesDir.mkdirs()
        }

        val outputStream = FileOutputStream(noteFile)
        val writer = OutputStreamWriter(outputStream)
        writer.write(noteBody)
        writer.close()
        outputStream.close()

        Log.i("SaveProjectNote", "Note '$noteName' saved successfully in project '$projectName'.")
        true
    } catch (e: Exception) {
        Log.e("SaveProjectNote", "Error saving note '$noteName' in project '$projectName': ${e.message}")
        false
    }
}

fun loadProjectNotesList(projectName: String): List<String> {
    val projectDir = validateProjectPath(projectName) ?: return emptyList()
    val noteFolder = File(projectDir, "notes")
    if (!noteFolder.exists() || !noteFolder.isDirectory) {
        Log.e("LoadProjectNotesList", "Notes folder for project '$projectName' does not exist.")
        return emptyList()
    }

    return noteFolder.listFiles()
        ?.filter { it.isFile && it.name.endsWith(".md") }
        ?.map { it.nameWithoutExtension }
        ?: emptyList()
}

fun loadNote(projectName: String, noteName: String): String? {
    val projectDir = validateProjectPath(projectName) ?: return null
    val noteFile = File(projectDir, "notes/$noteName.md")

    return if (noteFile.exists()) {
        try {
            val inputStream = FileInputStream(noteFile)
            val reader = InputStreamReader(inputStream)
            var content = reader.readText()
            reader.close()
            inputStream.close()
            content
        } catch (e: Exception) {
            Log.e("LoadProjectNote", "Error reading note '$noteName' from project '$projectName': ${e.message}")
            null
        }
    } else {
        Log.e("LoadProjectNote", "Note '$noteName' does not exist in project '$projectName'.")
        null
    }
}

fun deleteNote(projectName: String, noteName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val noteFile = File(projectDir, "notes/${noteName}.md")
    return if (noteFile.exists() && noteFile.isFile) {
        if (noteFile.deleteRecursively()) {
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

// TODOs FUNCTIONS

fun createTODO(projectName: String, todoName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val todoFolder = File(projectDir, "todos")

    if (!todoFolder.exists() || !todoFolder.isDirectory) {
        Log.e("CreateTODO", "TODOs folder for project '$projectName' does not exist.")
        todoFolder.mkdirs()
    }

    val todoFile = File(todoFolder, "${todoName}.txt")
    return if (todoFile.exists()) {
        Log.w("CreateTODO", "TODO '$todoName' already exists in project '$projectName'.")
        false
    } else {
        try {
            if (todoFile.createNewFile()) {
                Log.i("CreateTODO", "Todo '$todoName' created successfully in project '$projectName'.")
                true
            } else {
                Log.e("CreateTODO", "Failed to create TODO '$todoName' in project '$projectName'.")
                false
            }
        } catch (e: Exception) {
            Log.e("CreateTODO", "Error creating note '$todoName': ${e.message}")
            false
        }
    }
}

fun renameTODO(projectName: String, todoName: String, newTodoName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val todoFolder = File(projectDir, "todos")

    if (!todoFolder.exists() || !todoFolder.isDirectory) {
        Log.e("RenameTUDO", "TODOs folder for project '$projectName' does not exist.")
        return false
    }

    val todoFile = File(todoFolder, "${todoName}.md")
    val newTodoFile = File(todoFolder, "${newTodoName}.txt")
    return if (todoFile.renameTo(newTodoFile)) {
        Log.w("RenameTODO", "TODO '$todoName' renamed to $newTodoName successfully.")
        true
    } else {
        false
    }
}

fun saveTODO(projectName: String, todoName: String, itemsAsString: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val todoFile = File(projectDir, "todos/$todoName.txt")

    return try {
        val todosDir = File(projectDir, "todos")
        if (!todosDir.exists()) {
            todosDir.mkdirs()
        }

        val outputStream = FileOutputStream(todoFile)
        val writer = OutputStreamWriter(outputStream)
        writer.write(itemsAsString)
        writer.close()
        outputStream.close()

        Log.i("SaveTODO", "TODO '$todoName' saved successfully in project '$projectName'.")
        true
    } catch (e: Exception) {
        Log.e("SaveTODO", "Error saving TODO '$todoName' in project '$projectName': ${e.message}")
        false
    }
}

fun loadProjectTODOsList(projectName: String): List<String> {
    val projectDir = validateProjectPath(projectName) ?: return emptyList()
    val todoFolder = File(projectDir, "todos")
    if (!todoFolder.exists() || !todoFolder.isDirectory) {
        Log.e("LoadTODOsList", "TODOs folder for project '$projectName' does not exist.")
        return emptyList()
    }

    return todoFolder.listFiles()
        ?.filter { it.isFile && it.name.endsWith(".txt") }
        ?.map { it.nameWithoutExtension }
        ?: emptyList()
}

fun loadTODO(projectName: String, listName: String): String? {
    val projectDir = validateProjectPath(projectName) ?: return null
    val todoFile = File(projectDir, "todos/$listName.txt")

    return if (todoFile.exists()) {
        try {
            val inputStream = FileInputStream(todoFile)
            val reader = InputStreamReader(inputStream)
            var content = reader.readText()
            reader.close()
            inputStream.close()
            content
        } catch (e: Exception) {
            Log.e("LoadProjectNote", "Error reading note '$listName' from project '$projectName': ${e.message}")
            ""
        }
    } else {
        Log.e("LoadTODO", "TODO '$listName' does not exist in project '$projectName'.")
        ""
    }
}

fun deleteTODO(projectName: String, todoName: String): Boolean {
    val projectDir = validateProjectPath(projectName) ?: return false
    val noteFile = File(projectDir, "todos/${todoName}.txt")
    return if (noteFile.exists() && noteFile.isFile) {
        if (noteFile.deleteRecursively()) {
            Log.i("DeleteTODO", "TODO '$todoName' deleted successfully from project '$projectName'.")
            true
        } else {
            Log.e("DeleteTODO", "Failed to delete TODO '$todoName' from project '$projectName'.")
            false
        }
    } else {
        Log.w("DeleteTODO", "TODO '$todoName' does not exist in project '$projectName'.")
        false
    }
}


// DEBUGGING FUNCTIONS
fun deleteAllProjects() {
    val projectsFolder = File(root, "projects")
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
