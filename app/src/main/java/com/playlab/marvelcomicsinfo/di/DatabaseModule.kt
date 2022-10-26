package com.playlab.marvelcomicsinfo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabase
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.repository.ComicRepository
import com.playlab.marvelcomicsinfo.repository.DefaultComicRepository
import com.playlab.marvelcomicsinfo.repository.DefaultThemeRepository
import com.playlab.marvelcomicsinfo.repository.ThemeRepository
import com.playlab.marvelcomicsinfo.util.Constants.MARVEL_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MarvelDatabase {
        return Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            MARVEL_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile("settings")
            }
        )

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideDefaultComicRepository(
        apiInterface: ApiInterface,
        marvelDatabase: MarvelDatabase,
        dataStore: PreferencesDataStore
        ) : ComicRepository =
        DefaultComicRepository(apiInterface, marvelDatabase, dataStore)

    @Provides
    @Singleton
    fun provideDefaultThemeRepository(
        dataStore: PreferencesDataStore
    ): ThemeRepository =
        DefaultThemeRepository(dataStore)

}