package com.pes.studentorganiser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingScreen(navController: NavHostController,
                  modifier: Modifier = Modifier, priority: String,
                  changePriority: (String) -> Unit,
                  darkMode: Boolean,
                  switchMode:(Boolean) -> Unit) {

    var notif by rememberSaveable() {
        mutableStateOf(false)
    }

    Column() {
        AppBar("Settings",
            navController.currentBackStackEntry != null,
            {navController.popBackStack()})
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(200, 200, 210, 100))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Text("Dark Mode")
                Switch(checked = darkMode, onCheckedChange = switchMode)
            }
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Text("Push notifications")
                Checkbox(checked = notif, onCheckedChange = { notif = !notif })
            }

            Row(verticalAlignment = Alignment.CenterVertically) { Text("Select Priority")
            Row(
                modifier = modifier.padding(all = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = "Dashboard" == priority,
                        onClick = {
                            changePriority("Dashboard")
                        })
                    Text("Dashboard")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = "Assignments" == priority,
                        onClick = {
                            changePriority("Assignments")
                        })
                    Text("Assignments")
                }
            }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
private fun showSettings() {
    SettingScreen(darkMode= false,
    switchMode = {})
}*/