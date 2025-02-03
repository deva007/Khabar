package com.example.khabar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun CountrySelectionScreen(
    onCountrySelected: (String) -> Unit,
    onLanguageSelected: (String) -> Unit,
    navigateToLogin: () -> Unit
) {
    var selectedCountry by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "map_image"
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownSelector(
                label = "Select Country",
                options = countries.map { it.flag + " " + it.name },
                selectedOption = selectedCountry,
                onOptionSelected = { selectedCountry = it; onCountrySelected(it) }
            )

            DropdownSelector(
                label = "Select Language",
                options = languages.map { it.name },
                selectedOption = selectedLanguage,
                onOptionSelected = { selectedLanguage = it; onLanguageSelected(it) }
            )
        }

        Button(
            onClick = navigateToLogin,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Proceed", color = Color.White)
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
