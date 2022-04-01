package id.fauwiiz.gitconnect.di

import android.app.Application
import androidx.room.Room
import id.fauwiiz.gitconnect.BaseApplication
import id.fauwiiz.gitconnect.BuildConfig
import id.fauwiiz.gitconnect.data.UserDataSource
import id.fauwiiz.gitconnect.data.UserRepository
import id.fauwiiz.gitconnect.data.local.LocalDataSource
import id.fauwiiz.gitconnect.data.local.room.UserDao
import id.fauwiiz.gitconnect.data.local.room.UserDatabase
import id.fauwiiz.gitconnect.data.remote.ApiService
import id.fauwiiz.gitconnect.ui.detail.DetailViewModel
import id.fauwiiz.gitconnect.ui.favorite.FavoriteViewModel
import id.fauwiiz.gitconnect.ui.home.HomeViewModel
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {
    fun client() : ApiService {

        val hostname = "api.github.com"

        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/uyPYgclc5Jt69vKu92vci6etcBDY8UNTyrHQZJpVoZY=")
            .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
            .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
            .build()

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
val databaseModule = module{
    fun provideDatabase(application: Application) : UserDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("GitConnect".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(application, UserDatabase::class.java, "favorite.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    fun provideDao(database: UserDatabase) :  UserDao =  database.dao()

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }

}
val repositoryModule = module {
    fun repository(api: ApiService, local: LocalDataSource) : UserDataSource {
        return UserRepository(api, local)
    }

    single { LocalDataSource(get()) }
    single { repository(get(), get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}