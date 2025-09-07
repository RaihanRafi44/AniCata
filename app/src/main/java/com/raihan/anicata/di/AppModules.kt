package com.raihan.anicata.di

import com.raihan.anicata.data.datasource.anime.AnimeDetailFullApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeDetailFullDataSource
import com.raihan.anicata.data.datasource.anime.AnimeGenreApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeGenreDataSource
import com.raihan.anicata.data.datasource.anime.SearchAnimeApiDataSource
import com.raihan.anicata.data.datasource.anime.SearchAnimeDataSource
import com.raihan.anicata.data.datasource.anime.SeasonListApiDataSource
import com.raihan.anicata.data.datasource.anime.SeasonListDataSource
import com.raihan.anicata.data.datasource.anime.SeasonNowApiDataSource
import com.raihan.anicata.data.datasource.anime.SeasonNowDataSource
import com.raihan.anicata.data.datasource.anime.SeasonUpcomingApiDataSource
import com.raihan.anicata.data.datasource.anime.SeasonUpcomingDataSource
import com.raihan.anicata.data.datasource.anime.SeasonYearApiDataSource
import com.raihan.anicata.data.datasource.anime.SeasonYearDataSource
import com.raihan.anicata.data.datasource.anime.TopAnimeApiDataSource
import com.raihan.anicata.data.datasource.anime.TopAnimeDataSource
import com.raihan.anicata.data.datasource.manga.MangaDetailFullApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaDetailFullDataSource
import com.raihan.anicata.data.datasource.manga.MangaGenreApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaGenreDataSource
import com.raihan.anicata.data.datasource.manga.SearchMangaApiDataSource
import com.raihan.anicata.data.datasource.manga.SearchMangaDataSource
import com.raihan.anicata.data.datasource.manga.TopMangaApiDataSource
import com.raihan.anicata.data.datasource.manga.TopMangaDataSource
import com.raihan.anicata.data.source.network.service.AniCataApiService
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val networkModule =
        module{
            single<AniCataApiService> { AniCataApiService.invoke() }
        }

    private val firebaseModule =
        module{}

    private val dataSource =
        module{
            single<AnimeDetailFullDataSource> {AnimeDetailFullApiDataSource(get()) }
            single<AnimeGenreDataSource> { AnimeGenreApiDataSource(get()) }
            single<SearchAnimeDataSource> { SearchAnimeApiDataSource(get()) }
            single<SeasonListDataSource> { SeasonListApiDataSource(get()) }
            single<SeasonNowDataSource> { SeasonNowApiDataSource(get()) }
            single<SeasonUpcomingDataSource> { SeasonUpcomingApiDataSource(get()) }
            single<SeasonYearDataSource> { SeasonYearApiDataSource(get()) }
            single<TopAnimeDataSource> { TopAnimeApiDataSource(get()) }
            single<MangaDetailFullDataSource> { MangaDetailFullApiDataSource(get()) }
            single<MangaGenreDataSource> { MangaGenreApiDataSource(get()) }
            single<SearchMangaDataSource> { SearchMangaApiDataSource(get()) }
            single<TopMangaDataSource> { TopMangaApiDataSource(get()) }
        }

    private val repository =
        module{}

    private val viewModel =
        module{}

    val modules =
        listOf<Module>(
            networkModule,
            dataSource
        )
}