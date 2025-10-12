package com.bms.clone.di

import androidx.room.Room
import com.bms.clone.data.local.database.BMSDatabase
import com.bms.clone.data.remote.api.HttpClientFactory
import com.bms.clone.data.remote.api.TMDBApiService
import com.bms.clone.data.remote.api.TMDBApiServiceImpl
import com.bms.clone.data.repository.MovieRepositoryImpl
import com.bms.clone.data.repository.TicketRepositoryImpl
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.domain.repository.TicketRepository
import com.bms.clone.domain.usecases.movie.GetMovieDetailsUseCase
import com.bms.clone.domain.usecases.movie.GetMovieReviewsUseCase
import com.bms.clone.domain.usecases.movie.GetNowPlayingMoviesUseCase
import com.bms.clone.domain.usecases.movie.GetPopularMoviesUseCase
import com.bms.clone.domain.usecases.ticket.DeleteTicketUseCase
import com.bms.clone.domain.usecases.ticket.GetAllTicketsUseCase
import com.bms.clone.domain.usecases.ticket.GetTicketByIdUseCase
import com.bms.clone.domain.usecases.ticket.SaveTicketUsecase
import com.bms.clone.presentation.viewmodels.BookingViewModel
import com.bms.clone.presentation.viewmodels.HomeViewModel
import com.bms.clone.presentation.viewmodels.MovieDetailViewModel
import com.bms.clone.presentation.viewmodels.MoviesViewModel
import com.bms.clone.presentation.viewmodels.SeatSelectionViewModel
import com.bms.clone.presentation.viewmodels.TicketsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            BMSDatabase::class.java,
            "bms_database"
        ).build()
    }

    single { get<BMSDatabase>().ticketDao()}
}

val networkModule = module {
    single { HttpClientFactory.create() }
    single<TMDBApiService> { TMDBApiServiceImpl(get()) }
}

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<TicketRepository> { TicketRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetNowPlayingMoviesUseCase(get()) }
    factory { GetMovieDetailsUseCase(get()) }
    factory { GetMovieReviewsUseCase(get()) }

    factory { SaveTicketUsecase(get()) }
    factory { GetAllTicketsUseCase(get()) }
    factory { GetTicketByIdUseCase(get()) }
    factory { DeleteTicketUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
    viewModel { MoviesViewModel(get()) }
    viewModel { SeatSelectionViewModel() }
    viewModel { TicketsViewModel(get(), get()) }
    viewModel { BookingViewModel(get()) }
}

val appModules = listOf(databaseModule, networkModule, repositoryModule, useCaseModule, viewModelModule)