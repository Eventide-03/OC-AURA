package xyz.malefic.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.compose.comps.text.typography.Heading1

@Composable
fun GoalTracker(navi: Navigator) {
    var currentScreen by remember { mutableStateOf("main") }

    when (currentScreen) {
        "main" ->
            MainScreen(
                onEditClick = { currentScreen = "edit" },
                onAddClick = { currentScreen = "add" },
                onOverviewClick = { currentScreen = "overview" },
                onSettingsClick = { currentScreen = "settings" },
            )
        "edit" -> EditGoalScreen(onBack = { currentScreen = "main" })
        "add" -> AddGoalScreen(onBack = { currentScreen = "main" })
        "overview" -> GoalsOverviewScreen(onBack = { currentScreen = "main" })
        "settings" -> SettingsScreen(onBack = { currentScreen = "main" })
    }
}

@Composable
fun MainScreen(
    onEditClick: () -> Unit,
    onAddClick: () -> Unit,
    onOverviewClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    BackgroundBox(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFDFF2D8))
                .padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Vacation 🔽", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text("00 DAYS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("43%", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier =
                    Modifier
                        .background(Color(0xFF437D3D), shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("TARGET: \$00.00", color = Color.White, fontWeight = FontWeight.Bold)
                Text("$00.00 SAVED", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onEditClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("EDIT GOALS")
            }
            Button(
                onClick = onOverviewClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("VIEW ALL GOALS")
            }
            Button(
                onClick = onAddClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("ADD NEW GOAL")
            }
            Button(
                onClick = onSettingsClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("SETTINGS")
            }
        }
    }
}

@Composable
fun EditGoalScreen(onBack: () -> Unit) {
    BackgroundBox(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Heading1("EDIT GOAL")
            Text("Vacation 🔽", fontSize = 20.sp)
            Text("Target Savings:", fontWeight = FontWeight.Bold)
            Text("$00.00")
            Text("Time remaining:", fontWeight = FontWeight.Bold)
            Text("00 DAYS")
            Text("Amount Saved:", fontWeight = FontWeight.Bold)
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B))) {
                Text("$00.00")
            }
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                Text("← Back")
            }
        }
    }
}

@Composable
fun AddGoalScreen(onBack: () -> Unit) {
    var goalText by remember { mutableStateOf("") }

    BackgroundBox(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Heading1("ADD NEW GOALS")
            Text("What to include in your goal:", fontWeight = FontWeight.Bold)
            Text("• time limit\n• target savings amount\n• name of goal")
            TextField(
                value = goalText,
                onValueChange = { goalText = it },
                label = { Text("Type goal here...") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("CREATE GOAL")
            }
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("← Back")
            }
        }
    }
}

@Composable
fun GoalsOverviewScreen(onBack: () -> Unit) {
    BackgroundBox(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF7F4))
                .padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "GOALS OVERVIEW",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF497547),
                modifier = Modifier.padding(bottom = 32.dp),
            )

            listOf("Vacation", "Emergency fund", "Birthday gift").forEach {
                Text(text = "$it:", modifier = Modifier.padding(bottom = 4.dp))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                            .background(Color(0xFFA9DB8E), shape = RoundedCornerShape(50))
                            .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "$00.00/$00.00",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD8EFC3))) {
                Text("← Back", color = Color(0xFF497547), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var salary by remember { mutableStateOf("$00.00") }
    var name by remember { mutableStateOf("First, Last") }
    var age by remember { mutableStateOf("00") }

    BackgroundBox(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFF497547))
                .padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "SETTINGS",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp),
            )

            Text(text = "Monthly Net Salary:", color = Color.White)
            Text(
                text = salary,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(text = "Name", color = Color.White)
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(text = "Age", color = Color.White)
            TextField(
                value = age,
                onValueChange = { age = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
            )

            Button(
                onClick = { /* Save */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA9DB8E)),
            ) {
                Text("SAVE CHANGES", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD8EFC3))) {
                Text("← Back", color = Color(0xFF497547), fontWeight = FontWeight.Bold)
            }
        }
    }
}
