package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownEditor(markdown: String, onChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
            .padding(8.dp) // Optional padding for better spacing
    ) {
        OutlinedTextField(
            value = markdown,
            onValueChange = { updatedText ->
                onChange(updatedText) // Notify parent of changes
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp // Adjust font size for readability
            ),
            singleLine = false,
            maxLines = Int.MAX_VALUE // No limit on lines
        )
    }
}
