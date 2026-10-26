package org.ucb.appp1.notes.data.dto

import kotlinx.serialization.Serializable

/** Documento de la coleccion "notes" en Firestore. Valores por defecto: obligatorios para leer. */
@Serializable
data class NoteDto(
    val title: String = "",
    val content: String = ""
)
