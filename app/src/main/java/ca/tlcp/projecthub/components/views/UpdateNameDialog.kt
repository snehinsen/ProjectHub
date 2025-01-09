package ca.tlcp.projecthub.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun UpdateNameDialog(
    initialText: String = "",
    title: String = "Rename",
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfermLabel: String = "Rename",
    onAbortLabel: String = "Cancel",
    onAbort: () -> Unit = onDismiss
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(initialText, TextRange(0, initialText.length)))
    }

    val focus = remember { FocusRequester() }

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
                Row {
                    Text(
                        text = title,
                        style = TextStyle(color = Color.White, fontSize = MaterialTheme.typography.titleLarge.fontSize)
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { newValue -> textFieldValue = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focus)
                        .onFocusChanged { state ->
                            if (state.isFocused) {
                                textFieldValue = textFieldValue.copy(
                                    selection = TextRange(0, textFieldValue.text.length)
                                )
                            }
                        },
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,
                    label = { Text("Enter a new name", color = Color.White) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onAbort) {
                        Text(onAbortLabel, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onConfirm(textFieldValue.text) }) {
                        Text(onConfermLabel, color = Color.White)
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        focus.requestFocus()
    }
}
