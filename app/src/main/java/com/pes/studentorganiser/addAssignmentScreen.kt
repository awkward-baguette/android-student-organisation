package com.pes.studentorganiser

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AddAssignmentScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    //state- oberservable - recomposition is triggered when the value is changed

    var name by rememberSaveable() {
        mutableStateOf("")
    }
    var course by rememberSaveable() {
        mutableStateOf("")
    }
    var date by rememberSaveable() {
        mutableStateOf("")
    }
    var desc by rememberSaveable() {
        mutableStateOf("")
    }
    var selectedPriority by rememberSaveable {
        mutableStateOf(value = "")
    }
    var tobeNotified by remember {
        mutableStateOf(value = false)
    }
    var priorities = arrayOf("High" , "Medium" , "Low")

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly) {
        AppBar("Add New Assignment",
            navController.currentBackStackEntry != null,
            {navController.popBackStack()})

        LazyColumn(modifier = modifier
            .fillMaxSize()
            .background(Color(200,200,210))
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)){
            item {
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Title")
                    OutlinedTextField(
                        value = name,
                        singleLine = true,
                        onValueChange = { name = it },
                        label = { Text("Enter title ") })
                }
            }

            item {
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Course")
                    OutlinedTextField(
                        value = course,
                        singleLine = true,
                        onValueChange = { course = it },
                        label = { Text("Enter Course Name/Code ") })
                }
            }

            item {
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Due Date")
                        OutlinedTextField(
                            value = date,
                            singleLine = true,
                            onValueChange = { date = it },
                            label = { Text("Due Date ") })
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            "Calendar image"
                        )
                }
            }

            item {
                PrioritySelector(
                    priorities,
                    selectedPriority = selectedPriority,
                    onSelection = {
                        selectedPriority = it
                    }
                )
            }

            item {
                Column() {
                    Text("Description")
                    OutlinedTextField(
                        value = desc,
                        minLines = 3,
                        maxLines = 5,
                        onValueChange = { desc = it },
                        label = { Text("Description/Notes ") })
                }
            }

            item {
                NotificationSection(
                    selection = tobeNotified,
                    onSelection = {
                        tobeNotified = it
                    })
            }

            item {
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = {}) { Text("Cancel") }
                    Button(
                        enabled = (name.isNotEmpty() and course.isNotEmpty() and date.isNotEmpty()),
                        onClick = {}) { Text("Save Assignment") }
                }
            }

        }
    }
}


@Composable
fun NotificationSection(selection: Boolean,
                        onSelection: (Boolean) -> Unit ,
                        modifier: Modifier = Modifier ){
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Switch(
            checked = selection,
            onCheckedChange = {
                onSelection(it)

            })


        Text("Remind Me")
    }
}


//custom composables - UI
//1.modifier parameter - default argument
//2.apply modifier to the first composable inside the function
//child composable - stateless composition
//
@Composable
fun PrioritySelector(priorities: Array<String> ,
                     selectedPriority: String,
                     onSelection: (String) -> Unit,
                     modifier: Modifier = Modifier ) {
    Column(modifier = modifier
        .padding( all =  20.dp ) ) {

        Text("Select Priority")
        for (p in priorities) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedPriority == p,
                    onClick = {
                        onSelection(p)

                    })

                Text(p)
            }

        }


    }
}

/*@Preview(showBackground = true)
@Composable
private fun showAdd() {
    AddAssignmentScreen()
}*/