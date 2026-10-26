package org.ucb.appp1.notes.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.notes.presentation.viewmodel.NotesEffect
import org.ucb.appp1.notes.presentation.viewmodel.NotesEvent
import org.ucb.appp1.notes.presentation.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NotesEffect.ShowToast -> println("ERROR ${effect.message}")
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { navController.popBackStack() }) { Text("Volver") }
        TextField(
            value = state.title,
            label = { Text("Titulo") },
            onValueChange = { viewModel.emitEvent(NotesEvent.OnTitleChanged(it)) }
        )
        TextField(
            value = state.content,
            label = { Text("Contenido") },
            onValueChange = { viewModel.emitEvent(NotesEvent.OnContentChanged(it)) }
        )
        Button(onClick = { viewModel.emitEvent(NotesEvent.OnSave) }) { Text("Guardar") }
        if (state.isLoading) Text("Cargando...")
        LazyColumn {
            items(state.notes) { note ->
                Text("${note.title}: ${note.content}")
            }
        }
    }
}
