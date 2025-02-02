package com.example.khabar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.khabar.ui.theme.YellowMellow

@Composable
fun CountrySelectionScreen(
    onCountrySelected: (String) -> Unit,
    onLanguageSelected: (String) -> Unit,
    navigateToLogin: () -> Unit
) {
    var selectedCountry by remember { mutableStateOf("🇺🇸 USA") }
    var selectedLanguage by remember { mutableStateOf("English (US)") }
    var mapId by remember { mutableStateOf(R.drawable.ic_map_usa) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowMellow)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(mapId),
            contentDescription = "map_image",
            colorFilter = ColorFilter.tint(color = Color.DarkGray)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownSelector(
                label = "Select Country",
                options = countries.map { it.flag + " " + it.name },
                selectedOption = selectedCountry,
                onOptionSelected = {
                    mapId = when  {
                        it.contains("Nigeria") -> R.drawable.ic_map_nigeria
                        it.contains("Sudan") -> R.drawable.ic_map_sudan
                        it.contains("South Africa") -> R.drawable.ic_map_south_africa
                        it.contains("India") -> R.drawable.ic_map_india
                        else -> R.drawable.ic_map_usa
                    }
                    selectedCountry = it;
                    onCountrySelected(it)
                }
            )

            DropdownSelector(
                label = "Select Language",
                options = languages.map { it.name },
                selectedOption = selectedLanguage,
                onOptionSelected = { selectedLanguage = it; onLanguageSelected(it) }
            )
        }
        Button(
            onClick = {
                navigateToLogin()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Proceed", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(size = 48.dp), color = YellowMellow)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

data class Country(val name: String, val flag: String)
data class Language(val name: String)

val languages = listOf(Language("English (US)"), Language("English (UK)"))
val countries = listOf(
    Country(name = "Nigeria", flag = "🇳🇬"),
    Country(name = "Sudan", flag = "🇸🇩"),
    Country(name = "South Africa", flag = "🇿🇦"),
    Country(name = "India", flag = "🇮🇳"),
    Country(name = "USA", flag = "🇺🇸")
)
