package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharacterApp()
        }
    }
}

@Composable
fun CharacterApp() {
    var taskCount by remember { mutableStateOf(0) }
    var CharacterDescription by remember { mutableStateOf(TextFieldValue("")) }
    var characterList by remember { mutableStateOf(mutableListOf<String>()) }
    var selectedCharacters by remember { mutableStateOf(mutableSetOf<String>()) }

    // UI structure
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // text field
        OutlinedTextField(
            value = CharacterDescription,
            onValueChange = { CharacterDescription = it },//newValue->CharacterDescription= newValue
            label = { Text("Enter Character Names") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // save button
        Button(
            onClick = {
                if (CharacterDescription.text.isNotEmpty()) {
                    // add in list
                    characterList.add(CharacterDescription.text)
                    taskCount++
                    CharacterDescription = TextFieldValue("") // clear it
                }
            },
            modifier = Modifier
                .width(160.dp)
                .height(40.dp)
        ) {
            Text("Save Character")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // counter
        Text(
            text = "Total Characters: $taskCount",
        )

        Spacer(modifier = Modifier.height(16.dp))

        // display characters with checkboxes
        Column(modifier = Modifier.fillMaxWidth()) {
            characterList.forEach { character ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // checkboxes
                    Checkbox(
                        checked = selectedCharacters.contains(character),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedCharacters.add(character)
                            } else {
                                selectedCharacters.remove(character)
                            }
                        }
                    )

                    // Character name text
                    Text(
                        text = character,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Delete button
        Button(
            onClick = {
                characterList = characterList.filterNot { selectedCharacters.contains(it) }.toMutableList()
                selectedCharacters.clear()
                taskCount = characterList.size
            },
            modifier = Modifier
                .width(280.dp)
                .height(40.dp)

        ) {
            Text("Delete Selected Characters")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CharacterApp()
}
