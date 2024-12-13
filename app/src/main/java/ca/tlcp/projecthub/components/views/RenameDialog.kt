package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RenameDialog(
    initialText: String = "",
    title: String = "Rename",
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var textState by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(color = Color.White, fontSize = MaterialTheme.typography.titleLarge.fontSize)
                )
                OutlinedTextField(
                    value = textState,
                    onValueChange = { newValue -> textState = newValue },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,
                    label = { Text("Enter a new name", color = Color.White) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirm(textState) }) {
                        Text("Rename", color = Color.White)
                    }
                }
            }
        }
    }
}
