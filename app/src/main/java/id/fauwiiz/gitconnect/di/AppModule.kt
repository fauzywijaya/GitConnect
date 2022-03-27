package id.fauwiiz.gitconnect.di

import id.fauwiiz.gitconnect.BuildConfig
import id.fauwiiz.gitconnect.data.UserDataSource
import id.fauwiiz.gitconnect.data.UserRepository
import id.fauwiiz.gitconnect.data.remote.ApiService
import id.fauwiiz.gitconnect.ui.detail.DetailViewModel
import id.fauwiiz.gitconnect.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val apiModule = module {
    fun client() : ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
    single { client() }
}

val repositoryModule = module {
    fun repository(api: ApiService) : UserDataSource {
        return UserRepository(api)
    }

    single { repository(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}