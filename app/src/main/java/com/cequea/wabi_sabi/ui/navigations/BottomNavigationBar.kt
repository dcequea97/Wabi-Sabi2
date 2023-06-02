package com.cequea.wabi_sabi.ui.navigations

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = WabiSabiTheme.colors.brandSecondary,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = WabiSabiTheme.colors.iconInteractive,
                unselectedContentColor = WabiSabiTheme.colors.iconInteractiveInactive,
                icon = {
                    NavigationIcon(icon = item.icon, name = item.name, selected = selected)
                }
            )
        }
    }
}