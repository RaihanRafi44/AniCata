package com.raihan.anicata.di

import com.google.android.gms.auth.api.identity.Identity
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
import com.raihan.anicata.data.repository.anime.AnimeDetailRepository
import com.raihan.anicata.data.repository.anime.AnimeDetailRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeGenreRepository
import com.raihan.anicata.data.repository.anime.AnimeGenreRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeSearchRepository
import com.raihan.anicata.data.repository.anime.AnimeSearchRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeSeasonListRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonListRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeSeasonUpcomingRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonUpcomingRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeSeasonYearRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonYearRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.data.repository.anime.AnimeTopRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaDetailRepository
import com.raihan.anicata.data.repository.manga.MangaDetailRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaGenreRepository
import com.raihan.anicata.data.repository.manga.MangaGenreRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaSearchRepository
import com.raihan.anicata.data.repository.manga.MangaSearchRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaTopRepository
import com.raihan.anicata.data.repository.manga.MangaTopRepositoryImpl
import com.raihan.anicata.data.source.network.service.AniCataApiService
import com.raihan.anicata.data.usecase.GetGenreListUseCase
import com.raihan.anicata.data.usecase.GetMediaListUseCase
import com.raihan.anicata.ui.alllists.AllListsViewModel
import com.raihan.anicata.ui.login.GoogleAuthUiClient
import com.raihan.anicata.ui.login.LoginViewModel
import com.raihan.anicata.ui.top.anime.TopAnimeViewModel
import com.raihan.anicata.ui.top.manga.TopMangaViewModel
import com.raihan.anicata.ui.top.novel.TopNovelViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    private val networkModule =
        module{
            single<AniCataApiService> { AniCataApiService.invoke() }
        }

    private val firebaseModule =
        module{}

    private val authModule = module {
        single { Identity.getSignInClient(androidContext()) }
        single { GoogleAuthUiClient(androidContext(), get()) }
    }

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
        module{
            single<AnimeDetailRepository> { AnimeDetailRepositoryImpl(get()) }
            single<AnimeGenreRepository> { AnimeGenreRepositoryImpl(get()) }
            single<AnimeSearchRepository> { AnimeSearchRepositoryImpl(get()) }
            single<AnimeSeasonListRepository> { AnimeSeasonListRepositoryImpl(get()) }
            single<AnimeSeasonNowRepository> { AnimeSeasonNowRepositoryImpl(get()) }
            single<AnimeSeasonUpcomingRepository> { AnimeSeasonUpcomingRepositoryImpl(get()) }
            single<AnimeSeasonYearRepository> { AnimeSeasonYearRepositoryImpl(get()) }
            single<AnimeTopRepository> { AnimeTopRepositoryImpl(get()) }
            single<MangaDetailRepository> { MangaDetailRepositoryImpl(get()) }
            single<MangaGenreRepository> { MangaGenreRepositoryImpl(get()) }
            single<MangaSearchRepository> { MangaSearchRepositoryImpl(get()) }
            single<MangaTopRepository> { MangaTopRepositoryImpl(get()) }
        }

    private val useCase =
        module{
            single{ GetGenreListUseCase(get(),get())}
            single{ GetMediaListUseCase(get(), get()) }
        }

    private val viewModel =
        module{
            viewModelOf(::LoginViewModel)
            viewModelOf(::TopAnimeViewModel)
            viewModelOf(::TopMangaViewModel)
            viewModelOf(::TopNovelViewModel)
            viewModelOf(::AllListsViewModel)
        }

    val modules =
        listOf<Module>(
            networkModule,
            dataSource,
            repository,
            authModule,
            useCase,
            viewModel
        )
}