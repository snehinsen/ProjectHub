package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

@Composable
fun NoteEditor(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onChange: (String) -> Unit,
    title: String
) {
    if (isOpen) {
        Popup(onDismissRequest = { onDismiss() }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
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
                                modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                            )
                        }

                        TextButton(
                            onClick = { onDismiss() },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text("Done", style = TextStyle(fontSize = 20.sp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    MarkdownEditor("") {onChange}
                }
            }
        }
    }
}
