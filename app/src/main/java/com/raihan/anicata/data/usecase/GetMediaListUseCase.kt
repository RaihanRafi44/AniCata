package com.raihan.anicata.data.usecase

import com.raihan.anicata.data.mapper.media.toMediaItem
import com.raihan.anicata.data.model.media.MediaItem
import com.raihan.anicata.data.repository.anime.AnimeSearchRepository
import com.raihan.anicata.data.repository.manga.MangaSearchRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMediaListUseCase(
    private val animeSearchRepository: AnimeSearchRepository,
    private val mangaSearchRepository: MangaSearchRepository
) {
    fun execute(
        category: String,
        page: Int,
        limit: Int,
        type: String?,
        genres: String?,
        orderBy: String,
        sort: String
    ): Flow<ResultWrapper<Pair<List<MediaItem>, Int?>>> {

        val categoryLower = category.lowercase()

        /*if (categoryLower == "anime") {
            return animeSearchRepository.getSearchAnimeList(
                query = null,
                page = page,
                limit = limit,
                type = type,
                score = null,
                genres = genres,
                orderBy = orderBy,
                sort = sort
            ).map { result ->
                // Mapping terjadi di sini, di dalam Use Case
                when (result) {
                    is ResultWrapper.Success -> {
                        val mediaList = result.payload?.first?.map { it.toMediaItem() }
                        val totalPages = result.payload?.second
                        ResultWrapper.Success(Pair(mediaList, totalPages))
                    }
                    is ResultWrapper.Error -> result
                    is ResultWrapper.Loading -> result
                }
            }
        } else {
            // Asumsi Manga/Novel
            return mangaSearchRepository.getSearchMangaList(
                query = "",
                page = page,
                limit = limit,
                type = type ?: "",
                score = 0,
                genres = genres ?: "",
                orderBy = orderBy,
                sort = sort
            ).map { result ->
                // Mapping terjadi di sini, di dalam Use Case
                when (result) {
                    is ResultWrapper.Success -> {
                        val mediaList = result.payload?.first?.map { it.toMediaItem() }
                        val totalPages = result.payload?.second
                        ResultWrapper.Success(Pair(mediaList, totalPages))
                    }
                    is ResultWrapper.Error -> result
                    is ResultWrapper.Loading -> result
                }
            }
        }*/
        if (categoryLower == "anime") {
            return animeSearchRepository.getSearchAnimeList(
                query = null,
                page = page,
                limit = limit,
                type = type,
                score = null,
                genres = genres,
                orderBy = orderBy,
                sort = sort
            ).map { result ->
                // Mapping terjadi di sini, di dalam Use Case
                when (result) {
                    is ResultWrapper.Success -> {
                        // FIX 1: Tambahkan '!!' karena payload di base class nullable
                        val mediaList = result.payload!!.first.map { it.toMediaItem() }
                        val totalPages = result.payload.second
                        ResultWrapper.Success(Pair(mediaList, totalPages))
                    }

                    is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                    is ResultWrapper.Loading -> ResultWrapper.Loading()
                    // FIX 2: Tambahkan case yang hilang
                    is ResultWrapper.Empty -> ResultWrapper.Empty(Pair(emptyList(), 0))
                    is ResultWrapper.Idle -> ResultWrapper.Idle()
                }
            }
        } else {
            // Asumsi Manga/Novel
            return mangaSearchRepository.getSearchMangaList(
                query = null,
                page = page,
                limit = limit,
                type = type,
                score = null,
                genres = genres,
                orderBy = orderBy,
                sort = sort
            ).map { result ->
                // Mapping terjadi di sini, di dalam Use Case
                when (result) {
                    is ResultWrapper.Success -> {
                        // FIX 1: Tambahkan '!!'
                        val mediaList = result.payload!!.first.map { it.toMediaItem() }
                        val totalPages = result.payload.second
                        ResultWrapper.Success(Pair(mediaList, totalPages))
                    }

                    is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                    is ResultWrapper.Loading -> ResultWrapper.Loading()
                    // FIX 2: Tambahkan case yang hilang
                    is ResultWrapper.Empty -> ResultWrapper.Empty(Pair(emptyList(), 0))
                    is ResultWrapper.Idle -> ResultWrapper.Idle()
                }
            }
        }
    }
}