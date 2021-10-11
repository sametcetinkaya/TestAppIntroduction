package com.sametcetinkaya.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sametcetinkaya.artbooktesting.R
import com.sametcetinkaya.artbooktesting.repo.ArtRepository
import com.sametcetinkaya.artbooktesting.repo.ArtRepositoryInterface
import com.sametcetinkaya.artbooktesting.roomdb.ArtDao
import com.sametcetinkaya.artbooktesting.roomdb.ArtDatabase
import com.sametcetinkaya.artbooktesting.service.ArtAPI
import com.sametcetinkaya.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java, "ArtBookDB"
        ).build()

    @Singleton
    @Provides
    fun injectDao(database : ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : ArtAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ArtAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api: ArtAPI) = ArtRepository(dao, api) as ArtRepositoryInterface

}