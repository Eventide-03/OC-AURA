package xyz.malefic.compose.screens.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
 * Tax Information screen for Orange County Budget App
 * Provides tax information specific to Orange County, California residents
 */
@Composable
fun TaxInformation(navi: Navigator) {
    val scrollState = rememberScrollState()
    
    BackgroundBox(contentAlignment = Alignment.TopCenter) {
        ColumnFactory {
            // Header
            Heading1("Orange County Tax Information")
            Heading2("Tax rates and information for OC residents")
            
            // California State Income Tax
            ColumnFactory {
                Heading2("California State Income Tax Rates (2023)")
                
                TaxBracketRow("$0 - $10,099", "1%")
                TaxBracketRow("$10,100 - $23,942", "2%")
                TaxBracketRow("$23,943 - $37,788", "4%")
                TaxBracketRow("$37,789 - $52,455", "6%")
                TaxBracketRow("$52,456 - $66,295", "8%")
                TaxBracketRow("$66,296 - $338,639", "9.3%")
                TaxBracketRow("$338,640 - $406,364", "10.3%")
                TaxBracketRow("$406,365 - $677,275", "11.3%")
                TaxBracketRow("$677,276+", "12.3%")
                
                TextFactory("Note: These are marginal tax rates. Each rate applies only to income within that bracket.")()
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Property Tax
            ColumnFactory {
                Heading2("Orange County Property Tax")
                
                TextFactory("The basic property tax rate in Orange County is 1% of assessed value, plus additional voter-approved local taxes.")()
                
                RowFactory {
                    TextFactory("Average Effective Property Tax Rate")()
                    TextFactory("0.74%")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                TextFactory("Property taxes in Orange County are due in two installments:")()
                TextFactory("• First Installment: November 1 (delinquent after December 10)")()
                TextFactory("• Second Installment: February 1 (delinquent after April 10)")()
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Sales Tax
            ColumnFactory {
                Heading2("Orange County Sales Tax")
                
                RowFactory {
                    TextFactory("California State Base Sales Tax")()
                    TextFactory("7.25%")()
                } /= {
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                }
                
                TextFactory("Additional local sales taxes by city:")()
                
                SalesTaxRow("Anaheim", "7.75%")
                SalesTaxRow("Costa Mesa", "7.75%")
                SalesTaxRow("Irvine", "7.75%")
                SalesTaxRow("Huntington Beach", "7.75%")
                SalesTaxRow("Newport Beach", "7.75%")
                SalesTaxRow("Orange", "7.75%")
                SalesTaxRow("Santa Ana", "9.25%")
                SalesTaxRow("La Habra", "8.75%")
            } /= {
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            }
            
            // Tax Resources
            ColumnFactory {
                Heading2("Orange County Tax Resources")
                
                TextFactory("• Orange County Treasurer-Tax Collector: ttc.ocgov.com")()
                TextFactory("• Orange County Assessor: ocgov.com/gov/assessor")()
                TextFactory("• California Franchise Tax Board: ftb.ca.gov")()
                TextFactory("• California Department of Tax and Fee Administration: cdtfa.ca.gov")()
            } /= {
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            }
            
            // Navigation button
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
 * Helper composable for displaying tax bracket information
 */
@Composable
private fun TaxBracketRow(incomeRange: String, rate: String) {
    RowFactory {
        TextFactory(incomeRange)()
        TextFactory(rate)()
    } /= {
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        horizontalArrangement = Arrangement.SpaceBetween
    }
}

/**
 * Helper composable for displaying sales tax information
 */
@Composable
private fun SalesTaxRow(city: String, rate: String) {
    RowFactory {
        TextFactory(city)()
        TextFactory(rate)()
    } /= {
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
        horizontalArrangement = Arrangement.SpaceBetween
    }
}