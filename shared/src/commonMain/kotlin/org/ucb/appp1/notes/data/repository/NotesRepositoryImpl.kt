package org.ucb.appp1.notes.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.notes.data.datasource.NotesRemoteDataSource
import org.ucb.appp1.notes.data.mapper.toDomain
import org.ucb.appp1.notes.data.mapper.toDto
import org.ucb.appp1.notes.domain.model.NoteModel
import org.ucb.appp1.notes.domain.repository.NotesRepository

class NotesRepositoryImpl(
    private val dataSource: NotesRemoteDataSource
) : NotesRepository {
    override fun observe(): Flow<List<NoteModel>> =
        dataSource.observe().map { list -> list.map { it.toDomain() } }

    override suspend fun add(note: NoteModel): Result<Unit> =
        runCatching { dataSource.add(note.toDto()) }
}
