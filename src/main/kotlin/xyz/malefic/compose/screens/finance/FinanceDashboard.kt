package xyz.malefic.compose.screens.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
 * Finance Dashboard screen for Orange County Budget App
 * Serves as the main entry point for the finance application
 */
@Composable
fun FinanceDashboard(navi: Navigator) {
    BackgroundBox(contentAlignment = Alignment.Center) {
        ColumnFactory {
            // Header
            Heading1("Orange County Budget Tracker")
            Heading2("Manage your finances with ease")
            
            // Quick summary section
            RowFactory {
                SummaryCard("Budget", "$5,000.00")
                SummaryCard("Expenses", "$3,250.75")
                SummaryCard("Savings", "$1,749.25")
            } /= {
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                horizontalArrangement = Arrangement.SpaceEvenly
            }
            
            // Navigation buttons
            ButtonFactory { TextFactory("Track Expenses")() } / {
                onClick = { navi.navigate("finance/expenses") }
            } *= {
                space(16.dp)
            }
            
            ButtonFactory { TextFactory("Manage Budget")() } / {
                onClick = { navi.navigate("finance/budget") }
            } *= {
                space(16.dp)
            }
            
            ButtonFactory { TextFactory("OC Tax Information")() } / {
                onClick = { navi.navigate("finance/taxes") }
            } *= {
                space(16.dp)
            }
            
            ButtonFactory { TextFactory("Income Tracker")() } / {
                onClick = { navi.navigate("finance/income") }
            } *= {
                space(16.dp)
            }
            
        } /= {
            horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.spacedBy(8.dp)
        }
    }
}

/**
 * Card component to display summary information
 */
@Composable
private fun SummaryCard(title: String, amount: String) {
    ColumnFactory {
        Heading2(title)
        TextFactory(amount)()
    } /= {
        horizontalAlignment = Alignment.CenterHorizontally
        verticalArrangement = Arrangement.Center
    }
}