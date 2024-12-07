package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewItemBar(
    label: String,
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    onCreateItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Increased height for more breathing room
            .padding(horizontal = 16.dp, vertical = 12.dp) // Added vertical padding
            .background(Color.DarkGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Increased spacing between elements
    ) {
        OutlinedTextField(
            value = inputValue,
            onValueChange = { onInputValueChange(it) },
            label = {
                Text(
                    text = label,
                    style = TextStyle(color = Color.White, fontSize = 14.sp)
                )
            },
            singleLine = true,
            modifier = Modifier
                .weight(1f) // Use weight for proportional width allocation
                .fillMaxHeight(),
            textStyle = TextStyle(color = Color.White, fontSize = 15.sp)
        )
        IconButton(
            onClick = {
                if (inputValue.isNotBlank()) {
                    onCreateItem()
                }
            },
            modifier = Modifier.size(56.dp) // Slightly larger button for better balance
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
