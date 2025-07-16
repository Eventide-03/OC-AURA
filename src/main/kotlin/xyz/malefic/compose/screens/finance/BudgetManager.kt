package xyz.malefic.compose.screens.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.compose.comps.text.typography.Heading1
import xyz.malefic.compose.comps.text.typography.Heading2
import xyz.malefic.compose.engine.factory.ButtonFactory
import xyz.malefic.compose.engine.factory.ColumnFactory
import xyz.malefic.compose.engine.factory.RowFactory
import xyz.malefic.compose.engine.factory.TextFactory
import xyz.malefic.compose.engine.factory.div
import xyz.malefic.compose.engine.factory.divAssign
import xyz.malefic.compose.engine.factory.timesAssign
import xyz.malefic.compose.engine.fuel.space

/**
 * Budget Manager screen for Orange County Budget App
 * Allows users to set and manage their budget by category
 */
@Composable
fun BudgetManager(navi: Navigator) {
    // Sample budget data for Orange County residents
    val budgetItems = remember {
        listOf(
            BudgetItem("Housing", 2500.00, 2100.00),
            BudgetItem("Food", 800.00, 771.25),
            BudgetItem("Transportation", 400.00, 265.25),
            BudgetItem("Utilities", 350.00, 270.00),
            BudgetItem("Entertainment", 200.00, 50.00),
            BudgetItem("Health", 500.00, 395.00),
            BudgetItem("Recreation", 150.00, 25.00),
            BudgetItem("Savings", 500.00, 0.00),
            BudgetItem("Emergency Fund", 200.00, 0.00)
        )
    }
    
    val totalBudget = budgetItems.sumOf { it.budgetAmount }
    val totalSpent = budgetItems.sumOf { it.spentAmount }
    val remaining = totalBudget - totalSpent
    
    val scrollState = rememberScrollState()
    
    BackgroundBox(contentAlignment = Alignment.TopCenter) {
        ColumnFactory {
            // Header
            Heading1("Budget Manager")
            Heading2("Plan your Orange County budget")
            
            // Budget summary
            ColumnFactory {
                RowFactory {
                    TextFactory("Total Budget")()
                    TextFactory("$${String.format("%.2f", totalBudget)}")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                RowFactory {
                    TextFactory("Total Spent")()
                    TextFactory("$${String.format("%.2f", totalSpent)}")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                RowFactory {
                    TextFactory("Remaining")()
                    TextFactory("$${String.format("%.2f", remaining)}")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Budget breakdown
            ColumnFactory {
                Heading2("Budget Breakdown")
                
                // Column headers
                RowFactory {
                    TextFactory("Category")()
                    TextFactory("Budget")()
                    TextFactory("Spent")()
                    TextFactory("Remaining")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                // Budget items
                budgetItems.forEach { item ->
                    val itemRemaining = item.budgetAmount - item.spentAmount
                    RowFactory {
                        TextFactory(item.category)()
                        TextFactory("$${String.format("%.2f", item.budgetAmount)}")()
                        TextFactory("$${String.format("%.2f", item.spentAmount)}")()
                        TextFactory("$${String.format("%.2f", itemRemaining)}")()
                    } /= {
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        horizontalArrangement = Arrangement.SpaceBetween
                    }
                }
            } /= {
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            }
            
            // Navigation buttons
            ButtonFactory { TextFactory("Adjust Budget")() } / {
                onClick = { /* Would implement budget adjustment functionality */ }
            } *= {
                space(16.dp)
            }
            
            ButtonFactory { TextFactory("Back to Dashboard")() } / {
                onClick = { navi.navigate("finance/dashboard") }
            } *= {
                space(16.dp)
            }
            
        } /= {
            horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Top
            modifier = Modifier.padding(16.dp)
        }
    }
}

/**
 * Data class representing a budget item
 */
data class BudgetItem(
    val category: String,
    val budgetAmount: Double,
    val spentAmount: Double
)