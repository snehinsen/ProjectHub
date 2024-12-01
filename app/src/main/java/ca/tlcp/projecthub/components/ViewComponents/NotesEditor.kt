package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

@Composable
fun NoteEditor(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    update: (String) -> Unit,
    title: String,
    body: String
) {
    if (isOpen) {
        Popup(onDismissRequest = { onDismiss() }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    // Title Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                style = TextStyle(color = Color.White, fontSize = 20.sp),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        TextButton(
                            onClick = { onDismiss() },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text("Done", style = TextStyle(fontSize = 20.sp, color = Color.White))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    MarkdownEditor(
                        markdown = body,
                        onChange = { updatedText -> update(updatedText) }
                    )
                }
            }
        }
    }
}
