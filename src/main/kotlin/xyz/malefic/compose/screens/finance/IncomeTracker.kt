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
 * Income Tracker screen for Orange County Budget App
 * Allows users to track their income sources and analyze income trends
 */
@Composable
fun IncomeTracker(navi: Navigator) {
    // Sample income data
    val incomeEntries = remember {
        listOf(
            IncomeEntry("Primary Job", 5200.00, "Monthly", "Employment"),
            IncomeEntry("Freelance Work", 1200.00, "Monthly", "Self-Employment"),
            IncomeEntry("Rental Income", 2100.00, "Monthly", "Real Estate"),
            IncomeEntry("Dividends", 350.00, "Quarterly", "Investments"),
            IncomeEntry("Side Gig", 600.00, "Monthly", "Self-Employment"),
            IncomeEntry("Tax Refund", 1500.00, "Annual", "Government")
        )
    }
    
    // Calculate monthly income (converting non-monthly to monthly equivalent)
    val monthlyIncome = incomeEntries.sumOf { 
        when (it.frequency) {
            "Monthly" -> it.amount
            "Bi-weekly" -> it.amount * 26 / 12
            "Weekly" -> it.amount * 52 / 12
            "Quarterly" -> it.amount / 3
            "Annual" -> it.amount / 12
            else -> it.amount
        }
    }
    
    // Group by source type
    val incomeByType = incomeEntries.groupBy { it.sourceType }
        .mapValues { (_, entries) -> 
            entries.sumOf {
                when (it.frequency) {
                    "Monthly" -> it.amount
                    "Bi-weekly" -> it.amount * 26 / 12
                    "Weekly" -> it.amount * 52 / 12
                    "Quarterly" -> it.amount / 3
                    "Annual" -> it.amount / 12
                    else -> it.amount
                }
            }
        }
    
    val scrollState = rememberScrollState()
    
    BackgroundBox(contentAlignment = Alignment.TopCenter) {
        ColumnFactory {
            // Header
            Heading1("Income Tracker")
            Heading2("Track and analyze your income sources")
            
            // Monthly income summary
            ColumnFactory {
                Heading2("Monthly Income Summary")
                
                RowFactory {
                    TextFactory("Total Monthly Income")()
                    TextFactory("$${String.format("%.2f", monthlyIncome)}")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                Heading2("Income by Source Type")
                incomeByType.forEach { (type, amount) ->
                    RowFactory {
                        TextFactory(type)()
                        TextFactory("$${String.format("%.2f", amount)}")()
                    } /= {
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        horizontalArrangement = Arrangement.SpaceBetween
                    }
                }
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Income entries
            ColumnFactory {
                Heading2("Income Sources")
                
                // Column headers
                RowFactory {
                    TextFactory("Source")()
                    TextFactory("Amount")()
                    TextFactory("Frequency")()
                    TextFactory("Type")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                // Income entries
                incomeEntries.forEach { entry ->
                    RowFactory {
                        TextFactory(entry.source)()
                        TextFactory("$${String.format("%.2f", entry.amount)}")()
                        TextFactory(entry.frequency)()
                        TextFactory(entry.sourceType)()
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
            
            // Orange County income tips
            ColumnFactory {
                Heading2("Orange County Income Tips")
                
                TextFactory("• The median household income in Orange County is approximately $95,000")()
                TextFactory("• High-demand industries in OC include healthcare, technology, and tourism")()
                TextFactory("• Consider local networking events in Irvine and Newport Beach for career opportunities")()
                TextFactory("• Many OC residents supplement income with gig economy work")()
                TextFactory("• Real estate investment is popular in Orange County for passive income")()
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Navigation buttons
            ButtonFactory { TextFactory("Add Income Source")() } / {
                onClick = { /* Would implement add income functionality */ }
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
 * Data class representing an income entry
 */
data class IncomeEntry(
    val source: String,
    val amount: Double,
    val frequency: String, // Monthly, Bi-weekly, Weekly, Quarterly, Annual
    val sourceType: String  // Employment, Self-Employment, Investments, etc.
)