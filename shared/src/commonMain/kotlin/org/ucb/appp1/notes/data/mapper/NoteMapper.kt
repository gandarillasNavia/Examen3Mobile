package org.ucb.appp1.notes.data.mapper

import org.ucb.appp1.notes.data.dto.NoteDto
import org.ucb.appp1.notes.domain.model.NoteModel

fun NoteDto.toDomain() = NoteModel(title = title, content = content)

fun NoteModel.toDto() = NoteDto(title = title, content = content)
