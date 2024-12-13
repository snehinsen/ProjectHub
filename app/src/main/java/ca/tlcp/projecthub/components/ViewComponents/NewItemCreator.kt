package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun NewItemCreator(
    label: String,
    title: String = "",
    inputValue: String,
    onInputValueChange: (String) -> Unit = {},
    onCreateItem: () -> Unit,
    onCreateItemNoDialog: () -> Unit = {},
    useDialog: Boolean = false,
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End // Align button to the right
    ) {
        IconButton(
            onClick = {
                if (useDialog == true) {
                    isDialogOpen = true
                } else {
                    onCreateItemNoDialog()
                }
            },
            modifier = Modifier
                .wrapContentSize()
                .padding(0.dp)
                .background(Color.White, shape = RoundedCornerShape(50)) // Rounded white background
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (isDialogOpen) {
        Dialog(
            onDismissRequest = { isDialogOpen = false }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color
                            .DarkGray,
                        MaterialTheme
                            .shapes
                            .medium
                    )
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
                        value = inputValue,
                        onValueChange = { newValue -> onInputValueChange(newValue) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White),
                        singleLine = true,
                        label = { Text(label, color = Color.White) }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {isDialogOpen = false}) {
                            Text("Cancel", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                isDialogOpen = false
                                onCreateItem()
                            }
                        ) {
                            Text("Add", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
