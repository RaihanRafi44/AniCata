package com.raihan.anicata.di

import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raihan.anicata.data.datasource.anime.AnimeCharactersApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeCharactersDataSource
import com.raihan.anicata.data.datasource.anime.AnimeDetailFullApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeDetailFullDataSource
import com.raihan.anicata.data.datasource.anime.AnimeGenreApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeGenreDataSource
import com.raihan.anicata.data.datasource.anime.AnimeStaffApiDataSource
import com.raihan.anicata.data.datasource.anime.AnimeStaffDataSource
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
import com.raihan.anicata.data.datasource.local.RecentlyViewedDataSource
import com.raihan.anicata.data.datasource.local.RecentlyViewedDataSourceImpl
import com.raihan.anicata.data.datasource.manga.MangaCharactersApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaCharactersDataSource
import com.raihan.anicata.data.datasource.manga.MangaDetailFullApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaDetailFullDataSource
import com.raihan.anicata.data.datasource.manga.MangaGenreApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaGenreDataSource
import com.raihan.anicata.data.datasource.manga.MangaStaffApiDataSource
import com.raihan.anicata.data.datasource.manga.MangaStaffDataSource
import com.raihan.anicata.data.datasource.manga.SearchMangaApiDataSource
import com.raihan.anicata.data.datasource.manga.SearchMangaDataSource
import com.raihan.anicata.data.datasource.manga.TopMangaApiDataSource
import com.raihan.anicata.data.datasource.manga.TopMangaDataSource
import com.raihan.anicata.data.datasource.user.BookmarkMangaDataSource
import com.raihan.anicata.data.datasource.user.BookmarkMangaDataSourceImpl
import com.raihan.anicata.data.datasource.user.FavoriteAnimeDataSource
import com.raihan.anicata.data.datasource.user.FavoriteAnimeDataSourceImpl
import com.raihan.anicata.data.datasource.user.FavoriteMangaDataSource
import com.raihan.anicata.data.datasource.user.FavoriteMangaDataSourceImpl
import com.raihan.anicata.data.datasource.user.LibraryDataSource
import com.raihan.anicata.data.datasource.user.LibraryDataSourceImpl
import com.raihan.anicata.data.datasource.user.UserDataSource
import com.raihan.anicata.data.datasource.user.UserDataSourceImpl
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepository
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepositoryImpl
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
import com.raihan.anicata.data.repository.anime.AnimeStaffRepository
import com.raihan.anicata.data.repository.anime.AnimeStaffRepositoryImpl
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.data.repository.anime.AnimeTopRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaCharacterRepository
import com.raihan.anicata.data.repository.manga.MangaCharacterRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaDetailRepository
import com.raihan.anicata.data.repository.manga.MangaDetailRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaGenreRepository
import com.raihan.anicata.data.repository.manga.MangaGenreRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaSearchRepository
import com.raihan.anicata.data.repository.manga.MangaSearchRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaStaffRepository
import com.raihan.anicata.data.repository.manga.MangaStaffRepositoryImpl
import com.raihan.anicata.data.repository.manga.MangaTopRepository
import com.raihan.anicata.data.repository.manga.MangaTopRepositoryImpl
import com.raihan.anicata.data.repository.user.BookmarkMangaRepository
import com.raihan.anicata.data.repository.user.BookmarkMangaRepositoryImpl
import com.raihan.anicata.data.repository.user.FavoriteAnimeRepository
import com.raihan.anicata.data.repository.user.FavoriteAnimeRepositoryImpl
import com.raihan.anicata.data.repository.user.FavoriteMangaRepository
import com.raihan.anicata.data.repository.user.FavoriteMangaRepositoryImpl
import com.raihan.anicata.data.repository.user.LibraryRepository
import com.raihan.anicata.data.repository.user.LibraryRepositoryImpl
import com.raihan.anicata.data.repository.user.RecentlyViewedRepository
import com.raihan.anicata.data.repository.user.RecentlyViewedRepositoryImpl
import com.raihan.anicata.data.repository.user.UserRepository
import com.raihan.anicata.data.repository.user.UserRepositoryImpl
import com.raihan.anicata.data.source.firebase.FirebaseService
import com.raihan.anicata.data.source.firebase.FirebaseServiceImpl
import com.raihan.anicata.data.source.local.AppDatabase
import com.raihan.anicata.data.source.local.database.dao.RecentlyViewedDao
import com.raihan.anicata.data.source.network.service.AniCataApiService
import com.raihan.anicata.data.usecase.GetGenreListUseCase
import com.raihan.anicata.data.usecase.GetMediaListUseCase
import com.raihan.anicata.data.usecase.GetSeasonalUseCase
import com.raihan.anicata.ui.alllists.AllListsViewModel
import com.raihan.anicata.ui.archive.anime.ArchiveAnimeViewModel
import com.raihan.anicata.ui.archive.manga.ArchiveMangaViewModel
import com.raihan.anicata.ui.detail.anime.DetailAnimeViewModel
import com.raihan.anicata.ui.detail.manga.DetailMangaViewModel
import com.raihan.anicata.ui.home.HomeViewModel
import com.raihan.anicata.ui.login.GoogleAuthUiClient
import com.raihan.anicata.ui.login.LoginViewModel
import com.raihan.anicata.ui.search.ResultSearchViewModel
import com.raihan.anicata.ui.search.SearchViewModel
import com.raihan.anicata.ui.seasonalanime.SeasonalViewModel
import com.raihan.anicata.ui.top.anime.TopAnimeViewModel
import com.raihan.anicata.ui.top.manga.TopMangaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    private val networkModule =
        module{
            single<AniCataApiService> { AniCataApiService.invoke() }
        }

    private val firebaseModule = module {
        single<FirebaseAuth> { FirebaseAuth.getInstance()}
        single<FirebaseService> { FirebaseServiceImpl(get(), get()) }
        single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    }

    private val authModule = module {
        single { Identity.getSignInClient(androidContext()) }
        single { GoogleAuthUiClient(androidContext(), get()) }
    }

    private val localModule =
        module{
            single { com.google.gson.Gson() }
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<RecentlyViewedDao> { get<AppDatabase>().recentlyViewedDao() }
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
            single<AnimeCharactersDataSource> { AnimeCharactersApiDataSource(get()) }
            single<AnimeStaffDataSource> { AnimeStaffApiDataSource(get()) }
            single<MangaDetailFullDataSource> { MangaDetailFullApiDataSource(get()) }
            single<MangaGenreDataSource> { MangaGenreApiDataSource(get()) }
            single<SearchMangaDataSource> { SearchMangaApiDataSource(get()) }
            single<TopMangaDataSource> { TopMangaApiDataSource(get()) }
            single<MangaCharactersDataSource> { MangaCharactersApiDataSource(get()) }
            single<MangaStaffDataSource> { MangaStaffApiDataSource(get()) }
            single<UserDataSource> { UserDataSourceImpl(get()) }
            single<LibraryDataSource> { LibraryDataSourceImpl(get()) }
            single<FavoriteAnimeDataSource> { FavoriteAnimeDataSourceImpl(get()) }
            single<BookmarkMangaDataSource> { BookmarkMangaDataSourceImpl(get()) }
            single<FavoriteMangaDataSource> { FavoriteMangaDataSourceImpl(get()) }
            single<RecentlyViewedDataSource> { RecentlyViewedDataSourceImpl(get()) }
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
            single<AnimeCharacterRepository> { AnimeCharacterRepositoryImpl(get()) }
            single<AnimeStaffRepository> { AnimeStaffRepositoryImpl(get()) }
            single<MangaDetailRepository> { MangaDetailRepositoryImpl(get()) }
            single<MangaGenreRepository> { MangaGenreRepositoryImpl(get()) }
            single<MangaSearchRepository> { MangaSearchRepositoryImpl(get()) }
            single<MangaTopRepository> { MangaTopRepositoryImpl(get()) }
            single<MangaCharacterRepository> { MangaCharacterRepositoryImpl(get()) }
            single<MangaStaffRepository> { MangaStaffRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<LibraryRepository> { LibraryRepositoryImpl(get()) }
            single<FavoriteAnimeRepository> { FavoriteAnimeRepositoryImpl(get()) }
            single<BookmarkMangaRepository> { BookmarkMangaRepositoryImpl(get()) }
            single<FavoriteMangaRepository> { FavoriteMangaRepositoryImpl(get()) }
            single<RecentlyViewedRepository> { RecentlyViewedRepositoryImpl(get()) }
        }

    private val useCase =
        module{
            single{ GetGenreListUseCase(get(),get())}
            single{ GetMediaListUseCase(get(), get()) }
            single{ GetSeasonalUseCase(get(), get()) }
        }

    private val viewModel =
        module{
            viewModelOf(::LoginViewModel)
            viewModelOf(::TopAnimeViewModel)
            viewModelOf(::TopMangaViewModel)
            viewModelOf(::AllListsViewModel)
            viewModelOf(::SeasonalViewModel)
            viewModelOf(::SearchViewModel)
            viewModelOf(::ResultSearchViewModel)
            viewModelOf(::DetailAnimeViewModel)
            viewModelOf(::DetailMangaViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::ArchiveAnimeViewModel)
            viewModelOf(::ArchiveMangaViewModel)
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            dataSource,
            repository,
            authModule,
            useCase,
            firebaseModule,
            viewModel
        )
}