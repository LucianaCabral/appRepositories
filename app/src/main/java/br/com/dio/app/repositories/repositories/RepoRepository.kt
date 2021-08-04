package br.com.dio.app.repositories.repositories

import br.com.dio.app.repositories.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepositories(user:String): Flow<List<Repo>>
}