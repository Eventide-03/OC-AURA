package xyz.malefic.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.compose.comps.text.typography.Heading1
import xyz.malefic.compose.comps.text.typography.Heading2
import xyz.malefic.compose.engine.factory.ButtonFactory
import xyz.malefic.compose.engine.factory.ColumnFactory
import xyz.malefic.compose.engine.factory.TextFactory
import xyz.malefic.compose.engine.factory.div
import xyz.malefic.compose.engine.factory.divAssign
import xyz.malefic.compose.engine.factory.timesAssign
import xyz.malefic.compose.engine.fuel.space
import xyz.malefic.ext.string.either

@Composable
fun Home(navi: Navigator) {
    var text by remember { mutableStateOf("Hello, Mobile User!") }

    BackgroundBox(contentAlignment = Alignment.Center) {
        ColumnFactory {
            // Mobile app title
            Heading1("OC AURA Mobile")

            // Add some space
            ButtonFactory { } / {} *= {
                space(8.dp)
            }

            // Welcome message
            ButtonFactory { TextFactory(text)() } / {
                onClick = { text = text.either("Hello, Mobile User!", "Welcome to OC AURA!") }
            } *= {
                space(24.dp)
            }

            // Budget tracker button with mobile-friendly styling
            ButtonFactory { TextFactory("Orange County Budget Tracker")() } / {
                onClick = { navi.navigate("finance/dashboard") }
            } *= {
                space(16.dp)
            }

            // Goal Tracker button
            ButtonFactory { TextFactory("Goal Tracker")() } / {
                onClick = { navi.navigate("goaltracker") }
            } *= {
                space(16.dp)
            }

            // User profile button
            ButtonFactory { TextFactory("User Profile")() } / {
                onClick = { navi.navigate("app1/123456") }
            } *= {
                space(16.dp)
            }

            // Settings button
            ButtonFactory { TextFactory("Settings")() } / {
                onClick = { navi.navigate("app1/123456/Om Gupta") }
            } *= {
                space(16.dp)
            }

            // Hidden page button
            ButtonFactory { TextFactory("More Options")() } /= {
                onClick = { navi.navigate("hidden/boo!") }
            }
        } /= {
            horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Center
        }
    }
}
