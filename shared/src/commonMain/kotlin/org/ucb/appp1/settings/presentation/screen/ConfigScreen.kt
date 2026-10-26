package org.ucb.appp1.settings.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.settings.presentation.viewmodel.ConfigEvent
import org.ucb.appp1.settings.presentation.viewmodel.ConfigViewModel

/** E25-2*/
@Composable
fun ConfigScreen() {
    val viewModel: ConfigViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Configuraciones (Room)")
        TextField(
            value = state.clave,
            label = { Text("Clave") },
            onValueChange = { viewModel.emitEvent(ConfigEvent.OnClaveChanged(it)) }
        )
        TextField(
            value = state.valor,
            label = { Text("Valor") },
            onValueChange = { viewModel.emitEvent(ConfigEvent.OnValorChanged(it)) }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.emitEvent(ConfigEvent.OnAdd) }) { Text("Agregar") }
            Button(onClick = { viewModel.emitEvent(ConfigEvent.OnDeleteAll) }) { Text("Borrar todo") }
        }
        LazyColumn {
            items(state.items, key = { it.id }) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${item.clave} = ${item.valor}")
                    Button(onClick = { viewModel.emitEvent(ConfigEvent.OnDelete(item.id)) }) { Text("X") }
                }
            }
        }
    }
}
