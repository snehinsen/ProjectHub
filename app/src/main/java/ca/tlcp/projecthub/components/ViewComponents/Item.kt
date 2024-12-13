package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.tlcp.projecthub.ui.Colouring
import ca.tlcp.projecthub.ui.formatTitle

@Composable
fun Item(
    title: String,
    onDelete: () -> Unit,
    onSelect: () -> Unit,
    onRename: () -> Unit,
    extraDetails: @Composable () -> Unit = {},

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(
                color = Colouring.cardColour,
                shape = RoundedCornerShape(10.dp))
            .clickable { onSelect() }
            .padding(8.dp),
    ) {
        Column(Modifier.wrapContentSize()) {
            Row {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 35.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(
                        onClick = { onRename() },
                        Modifier.padding(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Rename $title",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = { onDelete() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete $title",
                            tint = Color.White
                        )
                    }
                }
            }
            extraDetails()
        }
    }
}
