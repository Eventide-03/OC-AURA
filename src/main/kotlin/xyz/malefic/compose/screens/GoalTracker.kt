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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.compose.comps.text.typography.Heading1
import xyz.malefic.compose.models.Goal
import xyz.malefic.compose.services.AIService

@Composable
fun GoalTracker(navi: Navigator) {
    var currentScreen by remember { mutableStateOf("main") }
    var goals by remember { mutableStateOf(listOf<Goal>()) }
    var currentGoal by remember { mutableStateOf<Goal?>(null) }
    val geminiApiKey = "AIzaSyDHee9PL_yR_L4Lshk25u3HLBN5pEZ8fgw"
    val aiService = remember { AIService(geminiApiKey) }
    val coroutineScope = rememberCoroutineScope()

    when (currentScreen) {
        "main" ->
            MainScreen(
                currentGoal = currentGoal,
                onEditClick = { currentScreen = "edit" },
                onAddClick = { currentScreen = "add" },
                onOverviewClick = { currentScreen = "overview" },
                onSettingsClick = { currentScreen = "settings" },
                onUpdateAmount = { amount ->
                    currentGoal = currentGoal?.copy(currentAmount = amount)
                },
            )
        "edit" ->
            EditGoalScreen(
                goal = currentGoal,
                onBack = { currentScreen = "main" },
                onUpdateGoal = { updatedGoal ->
                    currentGoal = updatedGoal
                    goals = goals.map { if (it.id == updatedGoal.id) updatedGoal else it }
                },
            )
        "add" ->
            AddGoalScreen(
                onBack = { currentScreen = "main" },
                onAddGoal = { goal ->
                    goals = goals + goal
                    currentGoal = goal
                    currentScreen = "main"
                },
                aiService = aiService,
                coroutineScope = coroutineScope,
            )
        "overview" ->
            GoalsOverviewScreen(
                goals = goals,
                onBack = { currentScreen = "main" },
                onSelectGoal = { goal ->
                    currentGoal = goal
                    currentScreen = "main"
                },
            )
        "settings" -> SettingsScreen(onBack = { currentScreen = "main" })
    }
}

@Composable
fun MainScreen(
    currentGoal: Goal?,
    onEditClick: () -> Unit,
    onAddClick: () -> Unit,
    onOverviewClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onUpdateAmount: (String) -> Unit,
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
            if (currentGoal != null) {
                Text(currentGoal.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("${currentGoal.getProgressPercentage()}%", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier =
                        Modifier
                            .background(Color(0xFF437D3D), shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("TARGET: ${currentGoal.targetAmount}", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("${currentGoal.currentAmount} SAVED", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("${currentGoal.getRemainingAmount()} REMAINING", color = Color.White, fontSize = 16.sp)
                }

                // Add amount input
                var amountInput by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextField(
                        value = amountInput,
                        onValueChange = { amountInput = it },
                        label = { Text("Add amount") },
                        modifier = Modifier.weight(1f),
                    )
                    Button(
                        onClick = {
                            if (amountInput.isNotEmpty()) {
                                val current =
                                    currentGoal.currentAmount
                                        .replace("$", "")
                                        .replace(",", "")
                                        .toDoubleOrNull() ?: 0.0
                                val newAmount = amountInput.toDoubleOrNull() ?: 0.0
                                val total = current + newAmount
                                onUpdateAmount("$${String.format("%.2f", total)}")
                                amountInput = ""
                            }
                        },
                    ) {
                        Text("Add")
                    }
                }
            } else {
                Text("No Goal Selected", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("0%", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier =
                        Modifier
                            .background(Color(0xFF437D3D), shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("TARGET: $0.00", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("$0.00 SAVED", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
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
fun EditGoalScreen(
    goal: Goal?,
    onBack: () -> Unit,
    onUpdateGoal: (Goal) -> Unit,
) {
    var editedGoal by remember { mutableStateOf(goal) }

    BackgroundBox(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Heading1("EDIT GOAL")

            if (editedGoal != null) {
                Text(editedGoal!!.name, fontSize = 20.sp)
                Text("Target Amount:", fontWeight = FontWeight.Bold)
                Text(editedGoal!!.targetAmount)
                Text("Current Amount:", fontWeight = FontWeight.Bold)
                Text(editedGoal!!.currentAmount)
                Text("Time Frame:", fontWeight = FontWeight.Bold)
                Text(editedGoal!!.timeFrame)
                Text("Monthly Savings:", fontWeight = FontWeight.Bold)
                Text(editedGoal!!.monthlySavings)

                // Edit current amount
                var newAmount by remember { mutableStateOf(editedGoal!!.currentAmount) }
                TextField(
                    value = newAmount,
                    onValueChange = {
                        newAmount = it
                        editedGoal = editedGoal?.copy(currentAmount = it)
                    },
                    label = { Text("Current Amount") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Button(
                    onClick = {
                        editedGoal?.let { onUpdateGoal(it) }
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                ) {
                    Text("Save Changes")
                }
            }

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            ) {
                Text("← Back")
            }
        }
    }
}

@Composable
fun AddGoalScreen(
    onBack: () -> Unit,
    onAddGoal: (Goal) -> Unit,
    aiService: AIService,
    coroutineScope: CoroutineScope,
) {
    var goalText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var generatedDetails by remember { mutableStateOf<xyz.malefic.compose.services.GoalDetails?>(null) }

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

            if (isLoading) {
                CircularProgressIndicator()
                Text("Generating goal details with AI...")
            }

            if (generatedDetails != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFFE8F5E8),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text("AI Generated Details:", fontWeight = FontWeight.Bold)
                        Text("Estimated Cost: ${generatedDetails!!.estimatedCost}")
                        Text("Target Amount: ${generatedDetails!!.targetAmount}")
                        Text("Time Frame: ${generatedDetails!!.timeFrame}")
                        Text("Monthly Savings: ${generatedDetails!!.monthlySavings}")
                        Text("Description: ${generatedDetails!!.description}")
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = {
                        if (goalText.isNotEmpty()) {
                            isLoading = true
                            coroutineScope.launch {
                                val details = aiService.generateGoalDetails(goalText)
                                generatedDetails = details
                                isLoading = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Generate with AI")
                }

                Button(
                    onClick = {
                        if (goalText.isNotEmpty() && generatedDetails != null) {
                            val goal =
                                Goal(
                                    name = goalText,
                                    description = generatedDetails!!.description,
                                    targetAmount = generatedDetails!!.targetAmount,
                                    timeFrame = generatedDetails!!.timeFrame,
                                    monthlySavings = generatedDetails!!.monthlySavings,
                                    estimatedCost = generatedDetails!!.estimatedCost,
                                )
                            onAddGoal(goal)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA1D48B)),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("CREATE GOAL")
                }
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
fun GoalsOverviewScreen(
    goals: List<Goal>,
    onBack: () -> Unit,
    onSelectGoal: (Goal) -> Unit,
) {
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

            if (goals.isEmpty()) {
                Text("No goals created yet. Add your first goal!")
            } else {
                goals.forEach { goal ->
                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                        backgroundColor = Color(0xFFA9DB8E),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Text(
                                text = goal.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                            Text("Progress: ${goal.getProgressPercentage()}%")
                            Text("Target: ${goal.targetAmount}")
                            Text("Current: ${goal.currentAmount}")
                            Text("Time Frame: ${goal.timeFrame}")

                            Button(
                                onClick = { onSelectGoal(goal) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF437D3D)),
                            ) {
                                Text("Select Goal", color = Color.White)
                            }
                        }
                    }
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
