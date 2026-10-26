package org.ucb.appp1.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.settings.domain.model.ConfigItem
import org.ucb.appp1.settings.domain.repository.ConfigRepository

data class ConfigState(
    val clave: String = "",
    val valor: String = "",
    val items: List<ConfigItem> = emptyList()
)

sealed interface ConfigEvent {
    data class OnClaveChanged(val value: String) : ConfigEvent
    data class OnValorChanged(val value: String) : ConfigEvent
    object OnAdd : ConfigEvent
    data class OnDelete(val id: Int) : ConfigEvent
    object OnDeleteAll : ConfigEvent
}

class ConfigViewModel(
    private val repository: ConfigRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ConfigState())
    val state: StateFlow<ConfigState> = _state.asStateFlow()

    init {
        viewModelScope.launch { repository.seedIfEmpty() }
        viewModelScope.launch {
            repository.observeAll().collect { list -> _state.update { it.copy(items = list) } }
        }
    }

    fun emitEvent(event: ConfigEvent) {
        when (event) {
            is ConfigEvent.OnClaveChanged -> _state.update { it.copy(clave = event.value) }
            is ConfigEvent.OnValorChanged -> _state.update { it.copy(valor = event.value) }
            ConfigEvent.OnAdd -> viewModelScope.launch {
                val s = _state.value
                if (s.clave.isNotBlank()) {
                    repository.add(s.clave, s.valor)
                    _state.update { it.copy(clave = "", valor = "") }
                }
            }
            is ConfigEvent.OnDelete -> viewModelScope.launch { repository.delete(event.id) }
            ConfigEvent.OnDeleteAll -> viewModelScope.launch { repository.deleteAll() }
        }
    }
}
