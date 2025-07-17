package xyz.malefic.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import xyz.malefic.compose.comps.precompose.NavWindow
import xyz.malefic.compose.nav.RouteManager
import xyz.malefic.compose.nav.RouteManager.RoutedNavHost
import xyz.malefic.compose.nav.RouteManager.navi
import xyz.malefic.compose.nav.config.MalefiConfigLoader
import xyz.malefic.compose.screens.GoalTracker
import xyz.malefic.compose.theming.MaleficTheme
import xyz.malefic.ext.list.get
import xyz.malefic.ext.stream.grass

/**
 * Entry point of the application that sets up the main navigation window.
 * It determines the theme based on the system's current theme (dark or light),
 * applies the selected theme, and initializes the route manager with the
 * composable map and configuration loader. The navigation menu is then displayed.
 */
fun main() =
    application {
        NavWindow(onCloseRequest = ::exitApplication, title = "Goal Tracker App") {
            // Initialize the route manager
            RouteManager.initialize(
                composableMap,
                grass("/routes.mcf")!!,
                MalefiConfigLoader(),
            )

            // Determine the theme file path based on the system's theme (dark or light)
            val themeInputStream =
                grass(
                    if (isSystemInDarkTheme()) "/theme/dark.json" else "/theme/light.json",
                ) ?: throw IllegalArgumentException("Theme file not found")

            // Apply the selected theme and invoke the Navigation Menu
            MaleficTheme(themeInputStream) {
                NavigationMenu()
            }
        }
    }

/**
 * Composable function that defines the navigation menu layout. It shows only the GoalTracker.
 */
@Composable
fun NavigationMenu() {
    RoutedNavHost()
}

/**
 * A map of composable functions used for routing. Only GoalTracker is included.
 */
val composableMap: Map<String, @Composable (List<String?>) -> Unit> =
    mapOf(
        "GoalTracker" to { _ -> GoalTracker(navi) },
    )
