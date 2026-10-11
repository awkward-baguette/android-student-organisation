package com.pes.studentorganiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pes.studentorganiser.ui.theme.StudentOrganiserTheme

data class Course (val courseName: String,
                   var courseCode: String,
                   var instructor: String,
                   var progress: Float)

data class Assignment(val title: String,
                      val course: String,
                      val dueDate: String,
                      val priority: String,
                      var isCompleted: Boolean)
{
    var isCompletedNow by mutableStateOf(isCompleted)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navC = rememberNavController()

            StudentOrganiserTheme() {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            // navigate to 'addTodo'
                            navC.navigate("add_assignment")
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    },
                    bottomBar = {BottomBar(navController = navC)}
                ) { innerPadding ->

                    var priority by rememberSaveable() {
                        mutableStateOf("Dashboard")
                    }

                    var darkMode by rememberSaveable() {
                        mutableStateOf(false)
                    }

                    val myCourses = remember {
                        mutableStateListOf(
                            Course("Digital communication", "UE24EC341", "Raghavendra MJ", 0.5f),
                            Course("Digital system design", "UE24EC342", "Sudheendra Kumar", 0.5f),
                            Course(
                                "Computer organisation and design",
                                "UE24EC351",
                                "Shantameena",
                                0.8f
                            ),
                            Course(
                                "Computer communication networks",
                                "UE24EC352",
                                "Pooja Kenchetty",
                                0.2f
                            ),
                        )
                    }
                    val myAssignments = remember {
                        mutableStateListOf(

                            Assignment(
                                title = "Calculus Problem Set 4",
                                course = "MATH 201",
                                dueDate = "2026-10-15",
                                priority = "High",
                                isCompleted = false
                            ),
                            Assignment(
                                title = "Data Structures Essay",
                                course = "CS 311",
                                dueDate = "2026-10-18",
                                priority = "Medium",
                                isCompleted = false
                            ),
                            Assignment(
                                title = "Quantum Physics Lab Report",
                                course = "PHYS 401",
                                dueDate = "2026-10-12",
                                priority = "High",
                                isCompleted = true
                            ),
                            Assignment(
                                title = "Macroeconomics Case Study",
                                course = "ECON 102",
                                dueDate = "2026-10-22",
                                priority = "Low",
                                isCompleted = false
                            ),
                            Assignment(
                                title = "Organic Synthesis Worksheet",
                                course = "CHEM 250",
                                dueDate = "2026-10-20",
                                priority = "Medium",
                                isCompleted = false
                            )
                        )
                    }

                    val toggleAssignment: (Assignment, Boolean) -> Unit = { a, done ->
                        val i = myAssignments.indexOfFirst { it.title == a.title }
                        if (i >= 0) myAssignments[i] = a.copy(isCompleted = done)
                    }

                    // list - is now observable- State
                    var CourseItems by remember {
                        mutableStateOf(myCourses)
                    }

                    var AssignItems by remember{
                        mutableStateOf(myAssignments)
                    }


                    //HomeScreen()
                    NavHost(
                        navC,
                        startDestination = if(priority=="Dashboard"){"dashboard"} else {"assignment"},
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        composable(route = "dashboard") {
                            DashboardScreen(activeCourses = CourseItems,
                                pendingTasks = AssignItems,
                                onToggle = toggleAssignment,
                        navController = navC)
                        }
                        composable(route = "assignment") {
                            AssignmentScreen(navC,
                                assignments = myAssignments,
                                onToggle = toggleAssignment)
                        }
                        composable(route = "profile") {
                            ProfileScreen(navC, gpa=3.8f, credits=48, currentSem = "Spring 2026")
                        }
                        composable(route = "settings") {
                            SettingScreen(navC, priority = priority,
                                changePriority = {priority = it},
                                darkMode = darkMode, switchMode = {darkMode = !darkMode})
                        }
                        composable(route = "add_assignment") {
                            AddAssignmentScreen(navC)
                        }
                    }
                    //ToDoListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
        }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentOrganiserTheme {
        Greeting("Android")
    }
}

@Composable
fun AppBar(title:String, canNavigateBack: Boolean, onBackClick: ()->Unit,
           modifier: Modifier = Modifier) {

    Row(modifier = modifier.fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "User Profile"
            )}
        Text(title)
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier,
                   navController: NavHostController) {
    BottomAppBar() {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            IconButton(onClick = {
                navController.navigate("dashboard")
            }){
                Icon(Icons.Default.Dashboard,
                    contentDescription = "Dashboard")
            }
            IconButton(onClick = {
                navController.navigate("assignment")
            }){
                Icon(Icons.Default.FilterList,
                    contentDescription = "List")
            }
            IconButton(onClick = {
                navController.navigate("profile")
            }){
                Icon(Icons.Default.Person,
                    contentDescription = "profile")
            }
            IconButton(onClick = {
                navController.navigate("settings")
            }){
                Icon(Icons.Default.Settings,
                    contentDescription = "settings")
            }
            }
        }
}