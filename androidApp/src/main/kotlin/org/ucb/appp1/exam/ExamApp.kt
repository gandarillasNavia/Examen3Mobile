package org.ucb.appp1.exam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.ucb.appp1.App
import org.ucb.appp1.settings.presentation.screen.ConfigScreen

private val tabs = listOf("Room", "Contador", "Tareas", "Base")

@Composable
fun ExamApp(startTab: String?) {
    var tab by remember { mutableIntStateOf(0) }

    LaunchedEffect(startTab) {
        if (startTab == "tareas") tab = 2
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = { Text("•") },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (tab) {
                0 -> ConfigScreen()      // E25-2
                1 -> CounterScreen()     // E25-4 rtdb
                2 -> TasksScreen()       // E50-5 firrtore
                else -> App()           //
            }
        }
    }
}
