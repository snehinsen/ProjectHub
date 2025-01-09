package ca.tlcp.projecthub.components.ViewComponents

import android.R.attr.contentDescription
import android.R.attr.tint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ca.tlcp.projecthub.ui.Colouring

@Composable
fun Task(
    title: String,
    onCheck: (Boolean) -> Unit,
    onRename: () -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }
    var showFullTitle by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(color = Colouring.cardColour, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
            .clickable { showFullTitle = true }
    ) {
        if (showFullTitle) {
            Dialog(onDismissRequest = { showFullTitle = false }) {
                Box(
                    modifier = Modifier
                        .background(color = Colouring.backgroundColour, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            IconButton(onClick = { showFullTitle = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(
                            Modifier
                                .height(5.dp)
                        )

                        Text(
                            text = title,
                            style =
                                TextStyle(
                                    color =
                                        Color
                                            .White,
                                    fontSize = 35.sp
                                )
                        )
                    }
                }
            }
        }

        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                onCheck(isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Green,
                uncheckedColor = Color.White,
                checkmarkColor = Color.White
            ),
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = title,
            style = TextStyle(color = Color.White, fontSize = 35.sp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        IconButton(onClick = onRename) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Rename $title",
                tint = Color.White
            )
        }
    }
}
