package com.raihan.anicata.data.usecase

import com.raihan.anicata.data.mapper.media.toMediaGenre
import com.raihan.anicata.data.model.anime.genres.GenreAnime
import com.raihan.anicata.data.model.manga.genres.GenreManga
import com.raihan.anicata.data.model.media.MediaGenre
import com.raihan.anicata.data.repository.anime.AnimeGenreRepository
import com.raihan.anicata.data.repository.manga.MangaGenreRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGenreListUseCase(
    private val animeGenreRepository: AnimeGenreRepository,
    private val mangaGenreRepository: MangaGenreRepository
) {
    fun execute(
        category: String,
        filter: String
    ): Flow<ResultWrapper<List<MediaGenre>>> {

        val categoryLower = category.lowercase()

        /*if (categoryLower == "anime") {
            return animeGenreRepository.getGenreAnimeList(filter).map { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val mediaGenres = result.payload.map { (it as GenreAnime).toMediaGenre() }
                        ResultWrapper.Success(mediaGenres)
                    }
                    is ResultWrapper.Error -> result
                    is ResultWrapper.Loading -> result
                }
            }
        } else {
            // Asumsi Manga/Novel
            return mangaGenreRepository.getGenreMangaList(filter).map { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val mediaGenres = result.payload.map { (it as GenreManga).toMediaGenre() }
                        ResultWrapper.Success(mediaGenres)
                    }
                    is ResultWrapper.Error -> result
                    is ResultWrapper.Loading -> result
                }
            }
        }*/
        if (categoryLower == "anime") {
            return animeGenreRepository.getGenreAnimeList(filter).map { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        // FIX 1: Tambahkan '!!'
                        val mediaGenres = result.payload!!.map { it.toMediaGenre() }
                        ResultWrapper.Success(mediaGenres)
                    }
                    is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                    is ResultWrapper.Loading -> ResultWrapper.Loading()
                    // FIX 2: Tambahkan case yang hilang
                    is ResultWrapper.Empty -> ResultWrapper.Empty(emptyList())
                    is ResultWrapper.Idle -> ResultWrapper.Idle()
                }
            }
        } else {
            // Asumsi Manga/Novel
            return mangaGenreRepository.getGenreMangaList(filter).map { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        // FIX 1: Tambahkan '!!'
                        val mediaGenres = result.payload!!.map { it.toMediaGenre() }
                        ResultWrapper.Success(mediaGenres)
                    }
                    is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                    is ResultWrapper.Loading -> ResultWrapper.Loading()
                    // FIX 2: Tambahkan case yang hilang
                    is ResultWrapper.Empty -> ResultWrapper.Empty(emptyList())
                    is ResultWrapper.Idle -> ResultWrapper.Idle()
                }
            }
        }
    }
}