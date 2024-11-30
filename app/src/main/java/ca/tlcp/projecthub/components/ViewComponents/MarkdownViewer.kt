package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.w3c.dom.Text


@Composable
fun MarkdownEditor(markdown: String) {
    var content by remember {
        mutableStateOf(markdown)
    }
    Row(Modifier.fillMaxSize()) {
        TextField(value = content, onValueChange = {text: String ->
            content = text
        }, Modifier.fillMaxHeight()
            .background(Color.Black),
            textStyle = TextStyle(Color.White))
        MarkdownText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            markdown = content,
            maxLines = 3,
            style = TextStyle(
                color = Color.Blue,
                fontSize = 12.sp,
                lineHeight = 10.sp,
                textAlign = TextAlign.Justify,
            ))
    }
}