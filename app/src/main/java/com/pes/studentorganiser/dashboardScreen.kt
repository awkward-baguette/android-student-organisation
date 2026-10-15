package com.pes.studentorganiser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
@Composable
fun DashboardScreen(studentName: String = "Shrivatsa", activeCourses: List<Course>,
                    pendingTasks: List<Assignment>,
                    onToggle: (Assignment, Boolean) -> Unit ,
                    navController: NavHostController,
                    modifier: Modifier = Modifier) {

    var noOfCourses = activeCourses.size
    var noOfTasks = pendingTasks.filter{it.isCompleted}.size

    Column(modifier = modifier
        .fillMaxSize()
        .padding(15.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AppBar("Welcome back, $studentName!",
            navController.currentBackStackEntry != null,
            {navController.popBackStack()})
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(
                        0,
                        0,
                        100,
                        alpha = 100
                    )
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(1.dp)
            ) {
                Text("Active courses: $noOfCourses", modifier = Modifier.padding(10.dp))
            }
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(
                        0,
                        0,
                        100,
                        alpha = 100
                    )
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(1.dp)
            ) {
                Text("Pending tasks: $noOfTasks", modifier = Modifier.padding(10.dp))
            }
        }

        LazyColumn(
            modifier = modifier.padding(5.dp)
                .background(Color(50, 50, 50, 100))
                .height(300.dp),
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Text("Your courses: ") }

            items(activeCourses, key = {
                it.courseCode
            }) { courseItem ->
                CourseInfo(
                    courseItem.courseName,
                    courseItem.courseCode,
                    courseItem.instructor,
                    courseItem.progress
                )
            }
        }

        Column(modifier = modifier.background(color = Color(180,180,200,100))
            .padding(10.dp))
        {
            Text(
                "Due soon:", modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(10.dp)) {
                for (assign in pendingTasks.sortedBy { it.dueDate }.slice(0..1))
                    AssignmentCard(
                        assign.title,
                        assign.course,
                        assign.dueDate,
                        assign.priority,
                        assign.isCompleted,
                        { newState -> onToggle(assign, newState)})
            }
        }
    }
}

@Composable
fun CourseInfo(courseName: String, courseCode:
String, instructor: String, progress:
               Float, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.elevatedCardColors(containerColor = Color(0,0,100, alpha = 50))) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(courseName)
                Text(courseCode)
            }
            Text(
                "Instructor: $instructor",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                "progress: $progress",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AssignmentCard(title: String, course: String,
                   dueDate: String, priority: String, isCompleted:
                   Boolean, onToggleComplete: (Boolean) -> Unit,
                   modifier: Modifier = Modifier) {
    val cardColor = if(priority == "Low"){
        CardDefaults.elevatedCardColors(containerColor = Color(180,240,180,100))}
    else if(priority == "Medium"){
        CardDefaults.elevatedCardColors(containerColor = Color(180,180,240,100))}
    else if(priority == "High"){
        CardDefaults.elevatedCardColors(containerColor = Color(240,180,180,100))}
    else{
        CardDefaults.elevatedCardColors(containerColor = Color(50,50,100, alpha = 50))
    }

    Card(colors = cardColor) {
        Row(modifier = modifier.fillMaxWidth())
        {
            Column(
                modifier = modifier
                    .padding(10.dp)
            ) {
                Text(title)
                Text(course)
                Text("Due on: $dueDate")
            }
            Checkbox(checked = isCompleted, onCheckedChange = onToggleComplete )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ShowDash(){
 //   DashboardScreen("Shrivatsa",2,4, )
//}