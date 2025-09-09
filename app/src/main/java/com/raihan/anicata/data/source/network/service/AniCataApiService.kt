package com.raihan.anicata.data.source.network.service

import  com.raihan.anicata.BuildConfig
import com.raihan.anicata.data.source.network.model.anime.full.AnimeDetailFullResponse
import com.raihan.anicata.data.source.network.model.anime.genres.GenreAnimeResponse
import com.raihan.anicata.data.source.network.model.anime.search.SearchAnimeResponse
import com.raihan.anicata.data.source.network.model.anime.seasons.lists.SeasonListsResponse
import com.raihan.anicata.data.source.network.model.anime.seasons.now.SeasonNowResponse
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.SeasonUpcomingResponse
import com.raihan.anicata.data.source.network.model.anime.seasons.year.SeasonYearResponse
import com.raihan.anicata.data.source.network.model.anime.top.anime.TopAnimeResponse
import com.raihan.anicata.data.source.network.model.manga.full.MangaDetailFullResponse
import com.raihan.anicata.data.source.network.model.manga.genres.GenreMangaResponse
import com.raihan.anicata.data.source.network.model.manga.search.SearchMangaResponse
import com.raihan.anicata.data.source.network.model.manga.top.manga.TopMangaResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface AniCataApiService {

    @GET("anime/{id}/full")
    suspend fun getAnimeDetailFull(
        @Path("id") id: Int
    ): AnimeDetailFullResponse

    @GET("manga/{id}/full")
    suspend fun getMangaDetailFull(
        @Path("id") id: Int
    ): MangaDetailFullResponse

    @GET("seasons/now")
    suspend fun getSeasonNow(
        @Query("filter") filter: String,
        @Query("continuing") continuing: Boolean
    ): SeasonNowResponse

    @GET("seasons/{year}/{season}")
    suspend fun getSeasonYear(
        @Path("year") year: Int,
        @Path("season") season: String,
        @Query("filter") filter: String,
        @Query("continuing") continuing: Boolean
    ): SeasonYearResponse

    @GET("seasons")
    suspend fun getSeasonList(): SeasonListsResponse

    @GET("seasons/upcoming")
    suspend fun getSeasonUpcoming(
        @Query("filter") filter: String,
        @Query("continuing") continuing: Boolean
    ): SeasonUpcomingResponse

    @GET("genres/anime")
    suspend fun getGenreAnime(
        @Query("filter") filter: String
    ): GenreAnimeResponse

    @GET("genres/manga")
    suspend fun getGenreManga(
        @Query("filter") filter: String
    ): GenreMangaResponse

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("type") type: String,
        @Query("filter") filter: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): TopAnimeResponse

    @GET("top/manga")
    suspend fun getTopManga(
        @Query("type") type: String,
        @Query("filter") filter: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): TopMangaResponse

    @GET("anime")
    suspend fun getSearchAnime(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("score") score: Int,
        @Query("genres") genres: String,
        @Query("order_by") orderBy: String,
        @Query("sort") sort: String
    ): SearchAnimeResponse

    @GET("manga")
    suspend fun getSearchManga(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("score") score: Int,
        @Query("genres") genres: String,
        @Query("order_by") orderBy: String,
        @Query("sort") sort: String
    ): SearchMangaResponse

    companion object {
        @JvmStatic
        operator fun invoke(): AniCataApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(AniCataApiService::class.java)
        }
    }
}