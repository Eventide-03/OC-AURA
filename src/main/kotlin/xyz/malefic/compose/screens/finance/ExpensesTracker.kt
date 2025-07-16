package xyz.malefic.compose.screens.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
 * Expense Tracker screen for Orange County Budget App
 * Allows users to track and categorize their expenses
 */
@Composable
fun ExpensesTracker(navi: Navigator) {
    // Sample expense data for Orange County residents
    val expenses = remember {
        listOf(
            Expense("Rent/Mortgage", 2100.00, "Housing"),
            Expense("Groceries", 450.75, "Food"),
            Expense("Dining Out", 320.50, "Food"),
            Expense("Gas", 180.25, "Transportation"),
            Expense("Toll Roads (73, 241, 261)", 85.00, "Transportation"),
            Expense("Utilities", 195.00, "Housing"),
            Expense("Internet", 75.00, "Utilities"),
            Expense("Cell Phone", 95.00, "Utilities"),
            Expense("Disneyland Annual Pass", 50.00, "Entertainment"),
            Expense("Beach Parking", 25.00, "Recreation"),
            Expense("Gym Membership", 45.00, "Health"),
            Expense("Health Insurance", 350.00, "Health")
        )
    }
    
    // Calculate totals by category
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
    
    val scrollState = rememberScrollState()
    
    BackgroundBox(contentAlignment = Alignment.TopCenter) {
        ColumnFactory {
            // Header
            Heading1("Expense Tracker")
            Heading2("Track your Orange County expenses")
            
            // Category summary
            ColumnFactory {
                Heading2("Expense Categories")
                categoryTotals.forEach { (category, total) ->
                    RowFactory {
                        TextFactory(category)()
                        TextFactory("$${String.format("%.2f", total)}")()
                    } /= {
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        horizontalArrangement = Arrangement.SpaceBetween
                    }
                }
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Expense list
            ColumnFactory {
                Heading2("Recent Expenses")
                expenses.forEach { expense ->
                    RowFactory {
                        ColumnFactory {
                            TextFactory(expense.description)()
                            TextFactory(expense.category)()
                        } /= {
                            horizontalAlignment = Alignment.Start
                        }
                        TextFactory("$${String.format("%.2f", expense.amount)}")()
                    } /= {
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        horizontalArrangement = Arrangement.SpaceBetween
                    }
                }
            } /= {
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            }
            
            // Navigation buttons
            ButtonFactory { TextFactory("Add New Expense")() } / {
                onClick = { /* Would implement add expense functionality */ }
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
 * Data class representing an expense
 */
data class Expense(
    val description: String,
    val amount: Double,
    val category: String
)