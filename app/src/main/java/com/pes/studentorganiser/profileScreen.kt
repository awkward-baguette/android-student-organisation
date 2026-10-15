package com.pes.studentorganiser

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController,
                  modifier: Modifier = Modifier,
                  gpa: Float, credits: Int,
                  currentSem: String) {
    Column() {
        AppBar("Student Profile",
            navController.currentBackStackEntry != null,
            {navController.popBackStack()})
        StudentProfile(
            "Shrivatsa", "PES1UG24EC205",
            "BTech Electronics and Communication",
            modifier = modifier
        )

        Card(modifier = modifier.padding(20.dp)
            .fillMaxWidth()) {
            Column(modifier = modifier.fillMaxWidth()
                .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("GPA $gpa")
                Text("Completed credits: $credits")
                Text("Current Sem: $currentSem")
            }
        }

        Row(modifier = modifier.fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {}) {Text("Edit Profile") }
            Button(onClick = {}) {Text("Export academic Summary") }
        }
    }
}

@Composable
fun StudentProfile(studentName: String, studentId: String,
                   major: String, avatarRes: Int? = null,
                   modifier: Modifier = Modifier) {

    OutlinedCard(modifier = modifier.fillMaxWidth()
        .padding(20.dp),
        colors = CardDefaults.outlinedCardColors(
        containerColor = Color(200,200,255, 100)),
        border = CardDefaults.outlinedCardBorder(true)) {

        Column(
            modifier = modifier.fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = modifier.size(150.dp)
                    .border(2.dp, Color.Black, CircleShape)
                    .clip(CircleShape)
            ) {
                if (avatarRes != null) {
                    val avatar = painterResource(avatarRes)
                    Image(avatar, "Profile picture",
                        Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    Icon(modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Profile"
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)){
                Text(studentName)
                Text(studentId)
            }
            Text("Major: $major")
        }
    }
}


/*@Preview(showBackground = true)
@Composable
private fun showProfile() {
    ProfileScreen(gpa=3.82f, credits = 48, currentSem = "Fall 2026")
}*/