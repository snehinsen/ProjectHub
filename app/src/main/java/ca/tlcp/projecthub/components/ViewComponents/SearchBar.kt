package ca.tlcp.projecthub.components.ViewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var search by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = {quarry ->
                search = quarry
                onSearch(search)
            },
            label = { Text("Search", style =
                TextStyle(color = Color.White)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.White),
//                .background(Color.DarkGray),
            textStyle = TextStyle(color = Color.White)
        )
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}