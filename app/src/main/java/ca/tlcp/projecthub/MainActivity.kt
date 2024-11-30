package ca.tlcp.projecthub

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import ca.tlcp.projecthub.components.Navigator
import ca.tlcp.projecthub.components.Static
import ca.tlcp.projecthub.components.initFolderStructure

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Static.setAppContext(applicationContext)
        initFolderStructure()
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContent {
            Navigator()
        }
    }
}



@Preview
@Composable
fun DefaultPreview() {
    Navigator()
}
