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

    val projectDir = File(root, "projects${File.separator}$projectName")
    return if (projectDir.exists()) {
        Log.e("CreateProject", "Project '$projectName' already exists.")
        false
    } else {
        if (projectDir.mkdirs()) {
            Log.i("CreateProject", "Project '$projectName' created successfully.")
            true
        } else {
            Log.e("CreateProject", "Failed to create project '$projectName'.")
            false
        }
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

fun loadProjectData(projectName: String): File? {
    if (root == null) {
        Log.e("LoadProjectData", "Context is null. Cannot load project data.")
        return null
    }

    val projectDir = File(root, "projects${File.separator}$projectName")
    return if (projectDir.exists()) {
        projectDir
    } else {
        Log.e("LoadProjectData", "Project '$projectName' does not exist.")
        null
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
