package com.pes.studentorganiser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun AssignmentScreen(navController: NavHostController,
                     assignments: List<Assignment>,
                     onToggle: (Assignment, Boolean) -> Unit,
                     modifier: Modifier = Modifier) {

    var selectedFilter by rememberSaveable {
        mutableStateOf(value = "All")
    }

    val filters = arrayOf("All", "Pending", "Completed")

    var filteredItems = when (selectedFilter) {
        "Pending" -> assignments.filter { !it.isCompleted }
        "Completed" -> assignments.filter { it.isCompleted }
        else -> assignments
    }

    Column(modifier = modifier.fillMaxSize()) {
        AppBar("My Assignments",
            navController.currentBackStackEntry != null,
            {navController.popBackStack()})
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp)
                .background(Color(150, 150, 180))
                .padding(10.dp)
        ) {
            Text("Select priority:")

            FilterSelector(
                filters,
                selectedFilter = selectedFilter,
                onSelection = {
                    selectedFilter = it
                })

            LazyColumn(
                modifier = Modifier.background(color = Color(180, 180, 200))
                    .padding(10.dp),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                items(filteredItems, key = {
                    it.title
                }) {
                    AssignmentCard(
                        it.title,
                        it.course,
                        it.dueDate,
                        it.priority,
                        it.isCompleted,
                        { newState -> onToggle(it, newState) })
                }
            }
        }
    }
}


@Composable
fun FilterSelector(filters: Array<String> ,
                     selectedFilter: String,
                     onSelection: (String) -> Unit,
                     modifier: Modifier = Modifier ) {
        Row(
            modifier = modifier.padding(all = 20.dp)
        ) {
            for (p in filters) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedFilter == p,
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
private fun ShowAssignments() {
    AssignmentScreen()

}*/