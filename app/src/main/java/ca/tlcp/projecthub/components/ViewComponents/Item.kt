package ca.tlcp.projecthub.components.ViewComponents

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Item(
    title: String,
    onDelete: () -> Unit,
    onSelect: () -> Unit,
    onRename: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, color = Color.Gray)
            .background(Color.DarkGray)
            .padding(8.dp)
            .clickable { onSelect() }
    ) {
        Text(
            text = title,
            style =
            TextStyle(
                color = Color.White,
                fontSize = 35.sp),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        )
        IconButton(
            onClick = { onRename() },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Rename $title",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        IconButton(
            onClick = { onDelete() },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete $title",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
